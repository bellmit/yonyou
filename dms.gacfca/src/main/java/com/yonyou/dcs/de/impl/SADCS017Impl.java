/**
 * 
 */
package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SADMS017VO;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.de.SADCS017;
import com.yonyou.dcs.gacfca.SADCS017Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS017Impl extends BaseImpl implements SADCS017 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS017Impl.class);
	
	@Autowired
	SADCS017Cloud cloud;
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	@Override
	public String sendData(String replaceApplyNo) throws Exception {
		logger.info("================二手车置换返利审批数据下发执行开始（SADCS017）====================");
			
		logger.info("================二手车置换返利审批数据下发开始（SADCS017）====================");
		try {
				send(cloud.getDataList(replaceApplyNo));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================二手车置换返利审批数据下发结束（SADCS017）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private void send(List<SADMS017Dto> dataList) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<SADMS017VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(saleMaterialPriceDao.getDmsDealerCode(dataList.get(0).getDealerCode()).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("SADMS017", dmsCode, body);
					logger.info("SADCS017发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("SADCS017发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS017发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setVos(List<SADMS017VO> vos, List<SADMS017Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADMS017Dto dto=dataList.get(i);
			SADMS017VO vo=new SADMS017VO();
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
