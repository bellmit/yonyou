package com.infoeai.eai.action.bsuv.lms;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.lms.BSUVDCSTOLMSDAO;
import com.infoeai.eai.wsServer.bsuv.lms.ResaleOrderDcsToLmsVO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * (DCS-LMS)零售订单
 * @author wangJian
 * date 2016-04-29
 *
 */
@Service
public class DCSTOLMS009Impl extends BaseService implements DCSTOLMS009{
	
	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS009Impl.class);
	
	@Autowired
	BSUVDCSTOLMSDAO dao;
	
	/**
	 * 零售订单提交推送
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public ResaleOrderDcsToLmsVO[] reSaleOrderInfo(String FROM,String TO) throws Exception {
		//事务开启
		beginDbService();
		
		ResaleOrderDcsToLmsVO[] voList = null;
		ResaleOrderDcsToLmsVO vo = null;
		List<Map> list = null;
		Date startDate = new Date();
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		String excString="";
		try {
			logger.info("====零售订单提交推送 is begin====");
			String statu = "";//无效
			
			list = dao.getOrderInfo(FROM,TO,statu);
			voList = new ResaleOrderDcsToLmsVO[list.size()];
			
			for(int i = 0;i<list.size();i++){
				vo = new ResaleOrderDcsToLmsVO();
				vo.setEC_ORDER_NO(CommonUtils.checkNull(list.get(i).get("EC_ORDER_NO")));
				vo.setDEALER_CODE(CommonUtils.checkNull(list.get(i).get("DEALER_CODE")));
				vo.setBRAND_CODE(CommonUtils.checkNull(list.get(i).get("BRAND_CODE")));
				vo.setSERIES_CODE(CommonUtils.checkNull(list.get(i).get("SERIES_CODE")));
				vo.setMODEL_CODE(CommonUtils.checkNull(list.get(i).get("MODEL_CODE")));
				vo.setGROUP_CODE(CommonUtils.checkNull(list.get(i).get("GROUP_CODE")));
				vo.setTRIM_CODE(CommonUtils.checkNull(list.get(i).get("TRIM_CODE")));
				vo.setCOLOR_CODE(CommonUtils.checkNull(list.get(i).get("COLOR_CODE")));
				String customerName=new String(Base64.encode(CommonUtils.checkNull(list.get(i).get("CUSTOMER_NAME")).getBytes("UTF-8")));
				vo.setCUSTOMER_NAME(customerName);
				vo.setTEL(CommonUtils.checkNull(list.get(i).get("TEL")));
				vo.setID_CRAD(CommonUtils.checkNull(list.get(i).get("ID_CRAD")));
				vo.setDEPOSIT_DATE(CommonUtils.checkNull(list.get(i).get("DEPOSIT_DATE")));
				vo.setRETAIL_FINANCE(list.get(i).get("RETAIL_FINANCE") != null?list.get(i).get("RETAIL_FINANCE").toString():"");
				vo.setDEPOSIT_AMOUNT(list.get(i).get("DEPOSIT_AMOUNT")!= null?Float.valueOf(list.get(i).get("DEPOSIT_AMOUNT").toString()):0f);
				vo.setREVOKE_DATE(CommonUtils.checkNull(list.get(i).get("REVOKE_DATE")));
				vo.setSUBMIT_DATE(CommonUtils.checkNull(list.get(i).get("SUBMIT_DATE")));
				vo.setDELIVER_DATE(CommonUtils.checkNull(list.get(i).get("DELIVER_DATE")));
				vo.setSALE_DATE(CommonUtils.checkNull(list.get(i).get("SALE_DATE")));
				vo.setORDER_STATUS(list.get(i).get("ORDER_STATUS") != null?Integer.valueOf(list.get(i).get("ORDER_STATUS").toString()):0);
				vo.setESC_COMFIRM_TYPE(list.get(i).get("ESC_COMFIRM_TYPE") !=null?Integer.valueOf(list.get(i).get("ESC_COMFIRM_TYPE").toString()):0);
				voList[i] = vo;
			}
			dbService.endTxn(true);
		}catch(Exception e) {
			logger.info("====零售订单推送 is error====");
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			dbService.endTxn(false);
		}finally{
			logger.info("====零售订单推送 is finish====");
			Base.detach();
			dbService.clean();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "(DCS-LMS)零售订单推送", startDate, list.size(), ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================(DCS-LMS)零售订单推送=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
		}
		
		return voList;
	}
	
	
}
