package com.infoeai.eai.action.bsuv.lms;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.lms.BSUVDCSTOLMSDAO;
import com.infoeai.eai.wsServer.bsuv.lms.CloseOutOrderDcsToLmsVO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * (DCS-LMS)官网批售订单配车成功状态推送接口
 * @author wangJian
 * date 2016-04-29
 *
 */
@Service
public class DCSTOLMS010Impl  extends BaseService implements DCSTOLMS010{
	
	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS010Impl.class);

	@Autowired
	BSUVDCSTOLMSDAO dao;
	
	@SuppressWarnings("rawtypes")
	public CloseOutOrderDcsToLmsVO[] orderPeiCheSuccessState(String FROM,String TO) throws Exception {
		//事务开启
		beginDbService();
		
		CloseOutOrderDcsToLmsVO[] voList = null;
		CloseOutOrderDcsToLmsVO vo = null;
		List<Map> list = null;
		Date startDate = new Date();
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		String excString="";
		try {
			logger.info("====官网批售订单配车成功状态推送 is begin====");
			list = dao.getOrderState(FROM,TO,OemDictCodeConstants.K4_VEHICLE_NODE_14);
			voList = new CloseOutOrderDcsToLmsVO[list.size()];
			
			for(int i =0;i<list.size();i++){
				vo = new CloseOutOrderDcsToLmsVO();
				vo.setEC_ORDER_NO(CommonUtils.checkNull(list.get(i).get("EC_ORDER_NO")));
				vo.setORDER_STATUS(list.get(i).get("CHANGE_CODE") != null?Integer.valueOf(list.get(i).get("CHANGE_CODE").toString()):0);
				vo.setDATE(CommonUtils.checkNull(list.get(i).get("CHANGE_DATE")));
				voList[i] = vo;
				
				TtVsOrderPO tvOrder = TtVsOrderPO.findFirst(" EC_ORDER_NO = ? ", CommonUtils.checkNull(list.get(i).get("EC_ORDER_NO")));
				tvOrder.setTimestamp("ALLOT_LMS_DATE",new Date());
				tvOrder.setLong("UPDATE_BY",22222L);
				tvOrder.setTimestamp("UPDATE_DATE",new Date());
				tvOrder.saveIt();
			}
			dbService.endTxn(true);
		}catch(Exception e) {
			logger.info("====官网批售订单配车成功状态推送 is error====");
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			dbService.endTxn(false);
		}finally{
			Base.detach();
			dbService.clean();
			logger.info("====官网批售订单配车成功状态推送 is finish====");
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "(DCS-LMS)官网批售订单配车成功状态推送", startDate, list.size(), ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================(DCS-LMS)官网批售订单配车成功状态推送=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
		}
		return voList;
	}
	


}
