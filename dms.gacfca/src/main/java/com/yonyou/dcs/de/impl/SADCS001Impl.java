/**
 * 
 */
package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.VehicleShippingVO;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.de.SADCS001;
import com.yonyou.dcs.gacfca.SADCS001Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS001Impl extends BaseImpl implements SADCS001 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS001Impl.class);
	
	@Autowired
	SADCS001Cloud sadcs001;
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	
	
	@Override
	public String sendData(String dealerId, String vehicleId,String dealerCode) throws Exception {
		logger.info("================车辆发运信息下发开始（SADCS001）====================");
		try {
				send(sadcs001.getDataList(dealerId,vehicleId),dealerCode);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================车辆发运信息下发结束（SADCS001）====================");
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	private void send(LinkedList<VehicleShippingDto> dataList, String dealerCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<VehicleShippingVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(saleMaterialPriceDao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("SADMS001", dmsCode, body);
					logger.info("SADCS001发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("SADCS001发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS001发送数据为空=====");
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
	private void setVos(List<VehicleShippingVO> vos, LinkedList<VehicleShippingDto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			VehicleShippingDto dto=dataList.get(i);
			VehicleShippingVO vo=new VehicleShippingVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
	
	

}
