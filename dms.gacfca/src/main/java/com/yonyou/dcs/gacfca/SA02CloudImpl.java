package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.UrlFunctionDao;
import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SA02CloudImpl extends BaseCloudImpl implements SA02Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SA02CloudImpl.class);
	
	@Autowired 
	UrlFunctionDao dao;
	
	@Override
	public String sendData(List<String> dealerCodes, SalesorderShoppingDTO vo){
		try {
			logger.info("================调拨审批结果下发开始（SA02Cloud）====================");
			List<SalesorderShoppingDTO> vos = new ArrayList<SalesorderShoppingDTO>();
			if (vo != null) {
				vos.add(vo);
			}
			if (null == vos || vos.size() == 0) {
				logger.info("================调拨审批结果下发结束（SA02Cloud）无数据====================");
				return null;
			}
			List<String> dmsCodes = new ArrayList<String>();
			List<String> errCodes = new ArrayList<String>();
			for (String dealerCode : dealerCodes) {
				try {
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCode);
					// 可下发的经销商列表
					dmsCodes.add(dmsDealer.get("DMS_CODE").toString());
				} catch (Exception e) {
					logger.error("Cann't send to " + dealerCode, e);
					errCodes.add(dealerCode);
				}
			}
			if(null!=dmsCodes&&dmsCodes.size()>0){
//				 //下发操作
//				 int flag = send(list,dmsCodes);
//				 if(flag==1){
//					 logger.info("================URL功能列表下发成功（HMCICO11）====================");
//				 }else{
//					 logger.info("================URL功能列表下发失败（HMCICO11）====================");
//				 	 logger.error("Cann't send to " + dealerCode);
//				 }
			}
			logger.info("================调拨审批结果下发结束（SA02Cloud）====================");
		}catch (Exception e) {
			logger.info("================调拨审批结果下发异常（SA02Cloud）====================");
		}
		return null;
	}
}
