package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
/**
 * 
* @ClassName: BusinessTypeDao 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月12日 上午10:05:22 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class BusinessTypeDao extends OemBaseDAO{
	
	/**
	 * 获得车辆业务类别
	 * @param vehicleId
	 * @param vin
	 * @throws Exception
	 */
	public static final String getVehicleBusinessType(String vehicleId, String vin)
			throws Exception {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.GROUP_TYPE -- 业务类型 \n");
		sql.append("  FROM ("+getVwMaterialSql()+") M \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append(" WHERE M.BRAND_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.SERIES_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.MODEL_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND M.GROUP_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		
		// 车辆ID
		if (!CheckUtil.checkNull(vehicleId)) {
			sql.append("   AND V.VEHICLE_ID = '" + vehicleId + "' \n");
		}
		
		// 车架号
		if (!CheckUtil.checkNull(vin)) {
			sql.append("   AND V.VIN = '" + vin + "' \n");
		}
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		
		String businessType = null;
		
		if (null != list && list.size() > 0) {
			businessType = list.get(0).get("GROUP_TYPE").toString();
		}

		return businessType;
	}

}
