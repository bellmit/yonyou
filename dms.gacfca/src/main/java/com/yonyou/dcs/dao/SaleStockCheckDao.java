package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.dms.common.domains.PO.basedata.TiVehicleInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 
* @ClassName: SaleStockCheckDao 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月11日 下午6:28:13 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SaleStockCheckDao extends OemBaseDAO {
	
	/**
	 * 车辆ID信息是否已存在验收表中查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map getVehicleIdByVinForExist(String vin,String dealerId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t1.VEHICLE_ID ");
		sql.append(" From TM_VEHICLE_DEC t1 \n");
		sql.append(" WHERE t1.LIFE_CYCLE='").append(OemDictCodeConstants.LIF_CYCLE_03).append("'  \n");
		sql.append(" AND t1.DEALER_ID='").append(dealerId).append("'  \n");
		sql.append(" AND t1.VIN = '"+vin+"' \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}

	public List<TmVehiclePO> selectVehicleByVin(TmVehiclePO vehicle) {
		List<TmVehiclePO> vehicleList = TmVehiclePO.findBySQL("SELECT tvd.* FROM tm_vehicle_dec tvd WHERE 1=1 AND tvd.VIN = ? ", vehicle.get("VIN"));
		return vehicleList;
	}

	public List<TtVsInspectionPO> selectTtVsInspection(TtVsInspectionPO inspection) {
		List<Object> params = new ArrayList<Object>();
		params.add(inspection.get("VEHICLE_ID"));
		List<TtVsInspectionPO> inspectionList = TmVehiclePO.findBySQL("SELECT tvi.* FROM tt_vs_inspection tvi WHERE 1=1 AND tvi.IS_DEL = 0 AND tvi.VEHICLE_ID = ? ", params);
		return inspectionList;
	}

	/**
	 * 车辆ID信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map getVehicleIdByVin(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VEHICLE_ID  \n");
		sql.append(" From TM_VEHICLE_DEC \n");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND VIN = '"+vin+"' \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	/**
	 * 车辆ID信息是否已存在验收表中查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public List<Map> getWxVehicleExist(String vin,Long inspectionId) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VE.VEHICLE_ID FROM TT_VS_INSPECTION ISP,TM_VEHICLE_DEC  VE WHERE ISP.VEHICLE_ID=VE.VEHICLE_ID ");
		sql.append("  AND ISP.INSPECTION_ID!= "+inspectionId+" \n");	//排除刚新增的验收信息
		sql.append("  AND VE.VIN= '"+vin+"' \n");
		List<Map> map = OemDAOUtil.findAll(sql.toString(), null);
		return map;
	}

	public List<TtVsNvdrPO> selectTtVsNvdr(String vin ) {
		List<TtVsNvdrPO> vsNvdrList = TmVehiclePO.findBySQL("SELECT tvn.* FROM tt_vs_nvdr tvn WHERE 1=1 AND tvn.VIN = ? AND tvn.REPORT_TYPE = ? ", vin, OemDictCodeConstants.RETAIL_REPORT_TYPE_03);
		return vsNvdrList;
	}

	public List<TiVehicleInspectionPO> selectTiVehicleInspectionByVin(String vin) {
		List<TiVehicleInspectionPO> tiVehicleInspectionList = TmVehiclePO.findBySQL("SELECT tvi.* FROM ti_vehicle_inspection tvi WHERE 1=1 AND tvi.VIN = ? ", vin);
		return tiVehicleInspectionList;
	}

	/**
	 * 
	* @Title: updateTtVsOrder 
	* @Description:  根据车架号更新订单状态 
	* @param @param resultMap    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateTtVsOrder(Map resultMap) {
		TtVsOrderPO.update(" ORDER_STATUS=? , UPDATE_BY=? , UPDATE_DATE=? ", " VIN=? ", OemDictCodeConstants.SALE_ORDER_TYPE_12,DEConstant.DE_CREATE_BY,new Date(),resultMap.get("VIN").toString());
	}

	/**
	 * 
	* @Title: updateTmVehicle 
	* @Description: // 更新车辆主数据表 
	* @param @param vo
	* @param @param vehicleId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateTmVehicle(VsStockEntryItemDto vo, String vehicleId) {
		Date nowDate = new Date();
		TmVehiclePO.update(" LIFE_CYCLE=? , NODE_STATUS=? , DEALER_STORAGE_DATE=? , NODE_DATE=? , UPDATE_BY=? , UPDATE_DATE=? ", " VEHICLE_ID=? ", OemDictCodeConstants.LIF_CYCLE_04, OemDictCodeConstants.K4_VEHICLE_NODE_19, vo.getInspectionDate(), vo.getInspectionDate(), DEConstant.DE_CREATE_BY, nowDate, Long.parseLong(vehicleId));
	}

	/**
	 * 
	* @Title: updateTtVehicleNodeHistory 
	* @Description: 更新车辆节点日期记录表（存储日期）
	* @param @param vehicleId
	* @param @param vo    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateTtVehicleNodeHistory(String vehicleId, VsStockEntryItemDto vo) {
		Date nowDate = new Date();
		TtVehicleNodeHistoryPO.update(" ZPOD_DATE=? , IS_DEL=0 , UPDATE_BY=? , UPDATE_DATE=? ", " VEHICLE_ID=? ", vo.getInspectionDate(), DEConstant.DE_CREATE_BY, nowDate, Long.parseLong(vehicleId)) ;
	}

	public List<TmVehicleNodeHistoryPO> selectTmVehicleNodeHistory(String vehicleId) {
		List<TmVehicleNodeHistoryPO> tmVehicleNodeHistoryList = TmVehiclePO.findBySQL("SELECT tvnh.* FROM tm_vehicle_node_history tvnh WHERE 1=1 AND tvnh.VEHICLE_ID = ? ", vehicleId);
		return tmVehicleNodeHistoryList;
	}

	/**
	 * 
	* @Title: updateTmVehicleNodeHistory 
	* @Description: //更新节点车辆状态时间 
	* @param @param vehicleId    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateTmVehicleNodeHistory(String vehicleId) {
		Date nowDate = new Date();
		TmVehicleNodeHistoryPO.update(" SALE_DATE=? ", " VEHICLE_ID=? ", nowDate, vehicleId);
	}
	
	
	
	
	

}
