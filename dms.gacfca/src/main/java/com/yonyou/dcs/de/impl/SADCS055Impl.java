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
import com.infoservice.dms.cgcsl.vo.TtActivityResultVO;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.de.SADCS055;
import com.yonyou.dcs.gacfca.SADCS015Cloud;
import com.yonyou.dcs.gacfca.SADCS055Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS055Impl extends BaseImpl implements SADCS055 {

	private static final Logger logger = LoggerFactory.getLogger(SADCS055Impl.class);
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	@Autowired
	SADCS055Cloud cloud;
	@Override
	public String sendData() throws Exception {
		logger.info("================服务活动下发执行开始（SADCS055）====================");
		
		logger.info("================服务活动下发开始（SADCS055）====================");
		try {
				send(cloud.getDataList());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		logger.info("================服务活动下发结束（SADCS055）====================");
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private void send(List<TtActivityResultDto> dataList) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<TtActivityResultVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(saleMaterialPriceDao.getDmsDealerCode(dataList.get(0).getEntityCode()).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("SEDMS055", dmsCode, body);
					logger.info("SADCS055发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("SADCS055发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS055发送数据为空=====");
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
	private void setVos(List<TtActivityResultVO> vos, List<TtActivityResultDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			TtActivityResultDto dto=dataList.get(i);
			TtActivityResultVO vo=new TtActivityResultVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
