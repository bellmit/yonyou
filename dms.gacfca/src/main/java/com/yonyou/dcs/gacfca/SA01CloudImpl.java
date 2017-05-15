package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;

@Service
public class SA01CloudImpl extends BaseCloudImpl implements SA01Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SA01CloudImpl.class);
	
	@Autowired
	RepairOrderResultStatusDao dao ;
	
	@Override
	public String sendData(String dealerCode, SalesorderShoppingDTO vo){
		try {
			logger.info("================车辆调拨申请下发开始（SA01Cloud）====================");
			List<SalesorderShoppingDTO> vos = new ArrayList<SalesorderShoppingDTO>();
			if (vo != null) {
				vos.add(vo);
			}
			if (null == vos || vos.size() == 0) {
				logger.info("================车辆调拨申请下发开始（SA01Cloud）,无数据====================");
				return null;
			}
			
			Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCode);
			if(null!=dmsDealer&&dmsDealer.size()>0){
//				 //下发操作
//				 int flag = send(list,dmsDealer);
//				 if(flag==1){
//					 logger.info("================URL功能列表下发成功（HMCICO11）====================");
//				 }else{
//					 logger.info("================URL功能列表下发失败（HMCICO11）====================");
//				 	 logger.error("Cann't send to " + dealerCode);
//				 }
			}else{
				logger.info("经销商:"+dealerCode+"没有维护对应的下端的entityCode");
			}
			logger.info("================车辆调拨申请下发结束（SA01Cloud）====================");
		}catch (Exception e) {
			logger.info("================车辆调拨申请下发异常（SA01Cloud）====================");
		}
		return null;
	}
}