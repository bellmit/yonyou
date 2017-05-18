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

import com.infoservice.dms.cgcsl.vo.PoCusWholeClryslerVO;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.de.SADCS013;
import com.yonyou.dcs.gacfca.SADCS013Colud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS013Impl extends BaseImpl implements SADCS013 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS013Impl.class);
	
	@Autowired
	SADCS013Colud sadcs013;
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	@Override
	public String sendData(String wsNo, String dealerCode) throws Exception {
		logger.info("================大客户报备审批数据下发开始（SADCS013）====================");
		try {
				send(sadcs013.getDataList(wsNo,dealerCode),dealerCode);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================大客户报备审批数据下发结束（SADCS013）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private void send(List<PoCusWholeClryslerDto> dataList, String dealerCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<PoCusWholeClryslerVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(saleMaterialPriceDao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("OSD0401", dmsCode, body);
					logger.info("SADCS013发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("SADCS013发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS013发送数据为空=====");
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
	private void setVos(List<PoCusWholeClryslerVO> vos, List<PoCusWholeClryslerDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			PoCusWholeClryslerDto dto=dataList.get(i);
			PoCusWholeClryslerVO vo=new PoCusWholeClryslerVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
	

}
