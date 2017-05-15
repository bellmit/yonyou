package com.infoeai.eai.dao.ctcai;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class AutoScanBookedFuturesResourceDao extends OemBaseDAO {
	
	public List<Map> queryCommonResourceOrderPayUnconfirmed(String vin){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TVO.VIN, \n");
		sql.append("       TVO.COMMONALITY_ID, \n");
		sql.append("       TVO.DEALER_ID, \n");
		sql.append("       TVO.ORDER_ID, \n");
		sql.append("       TVO.ORDER_NO, \n");
		sql.append("       TVH.VEHICLE_ID, \n");
		sql.append("       TVH.NODE_STATUS \n");
		sql.append("  FROM TT_VS_ORDER TVO, TT_VS_COMMON_RESOURCE TVCR, TM_VEHICLE TVH \n");
		sql.append(" WHERE TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("   AND TVO.VIN = TVH.VIN \n");
		sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.ORDER_STATUS_04
				+ " -- 定金未确认 \n");
		sql.append("   AND TVO.ORDER_TYPE = " + OemDictCodeConstants.ORDER_TYPE_02
				+ " -- 期货 \n");
		sql.append("   AND TVO.VIN = '" + vin + "'\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public void cancelOrder(String orderId, String vehicleId, String commonalityId) {
		Date now = new Date();
		//设置订单表的公共资源ID为空,订单状态为已取消
		TtVsOrderPO.update("COMMONALITY_ID = ?,ORDER_STATUS = ?", "ORDER_ID = ?",0L,OemDictCodeConstants.ORDER_STATUS_08,orderId);
		
		//车辆返回期货资源池
		TtVsCommonResourcePO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?,ISSUED_DATE = ?", "COMMON_ID = ?",
				OemDictCodeConstants.COMMON_RESOURCE_STATUS_02,99999999L,now,now,Long.valueOf(commonalityId));
		
		TtVsCommonResourceDetailPO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "COMMON_ID = ?",
				OemDictCodeConstants.STATUS_ENABLE,99999999L,now,Long.valueOf(commonalityId));
		
		//车辆表经销商dealerID置为null
	    TmVehiclePO.update("DEALER_ID = ?", "VEHICLE_ID = ?", null,vehicleId);
	    
	    //匹配更改日志表
	    TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO();
	    mcPo.setLong("ORDER_ID", new Long(orderId));
	    mcPo.setLong("OLD_VEHICLE_ID", new Long(vehicleId));
	    mcPo.setLong("CHG_VEHICLE_ID", new Long(vehicleId));//原车辆ID
	    mcPo.setInteger("CANCEL_TYPE", 1001);//1001订单取消 1002 订单撤单
	    mcPo.setString("CANCEL_REASON", "Job自动处理：订单取消");
	    mcPo.setLong("UPDATE_BY", 99999999L);
	    mcPo.setTimestamp("UPDATE_DATE", now);
	    mcPo.saveIt();
		
	}

	public void cancelOrderAndCommonResource(String orderId, String vehicleId, String commonalityId) {
		Date now = new Date();
		//设置订单表的公共资源ID为空,订单状态为已取消
		TtVsOrderPO.update("COMMONALITY_ID = ?,ORDER_STATUS = ?", "ORDER_ID = ?",0L,OemDictCodeConstants.ORDER_STATUS_08,orderId);
		
		//车辆表经销商dealerID置为null
	    TmVehiclePO.update("DEALER_ID = ?", "VEHICLE_ID = ?", null,vehicleId);
	    
	    //期货资源信息取消
	    TtVsCommonResourcePO.update("STATUS = ?,RESOURCE_SCOPE = ?,ISSUED_DATE = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "COMMON_ID = ?",
				OemDictCodeConstants.COMMON_RESOURCE_STATUS_03,null,null,99999999L,now,Long.valueOf(commonalityId));
	    
	    TtVsCommonResourceDetailPO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "COMMON_ID = ?",
				OemDictCodeConstants.STATUS_DISABLE,99999999L,now,Long.valueOf(commonalityId));
	    
	    //匹配更改日志表
	    TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO();
	    mcPo.setLong("ORDER_ID", new Long(orderId));
	    mcPo.setLong("OLD_VEHICLE_ID", new Long(vehicleId));
	    mcPo.setLong("CHG_VEHICLE_ID", new Long(vehicleId));//原车辆ID
	    mcPo.setInteger("CANCEL_TYPE", 1001);//1001订单取消 1002 订单撤单
	    mcPo.setString("CANCEL_REASON", "Job自动处理：订单取消、期货资源信息取消");
	    mcPo.setLong("UPDATE_BY", 99999999L);
	    mcPo.setTimestamp("UPDATE_DATE", now);
	    mcPo.saveIt();
		
	}

}
