package com.yonyou.dms.vehicle.dao.dealerStorage.vehicleAcceptance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class DealerVehicleCheckMaintainDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 待验收车辆查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,loginInfo, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, LoginInfoDto loginInfo, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       O.ORDER_NO, -- 订单号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       DATE_FORMAT(V.STOCKOUT_DEALER_DATE, '%Y-%m-%d') AS ACTUAL_DATE, -- 发运日期 \n");
		sql.append("       V.VEHICLE_ID -- 车辆ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_03 + "' \n");
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append("   AND O.ORDER_NO = ? \n");
			params.add(queryParam.get("orderNo"));
		}
		
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND V.VIN = ? \n");
			params.add(queryParam.get("vin"));
		}
		sql.append("   AND (    V.NODE_STATUS = '" + OemDictCodeConstants.VEHICLE_NODE_12 + "' \n");
		sql.append("        AND O.ORDER_STATUS = '" + OemDictCodeConstants.ORDER_STATUS_06 + "' \n");
		sql.append("        AND O.DEALER_ID = '" + loginInfo.getDealerId() + "') \n");
		sql.append("    OR ((   V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_17 + "' \n");
		sql.append("         OR V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_18 + "') \n");
		sql.append("        AND (O.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_10 + "' \n");
		sql.append("         OR O.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_11 + "') \n");
		sql.append("        AND O.RECEIVES_DEALER_ID = '" + loginInfo.getDealerId() + "') \n");
		
		logger.debug(sql.toString()+ " "+ params.toString());
		return sql.toString();
	}

	/**
	 * 根据ID获取待验收车辆信息
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDetail(Long id, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDetailQuerySql(id ,loginInfo);
		map = OemDAOUtil.findFirst(sql, null);
		Calendar c = Calendar.getInstance();
		int nowHour = c.get(Calendar.HOUR_OF_DAY);
		Date date = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String nowDateStr = dateformat.format(date); 
		map.put("NOW_HOUR", nowHour);
		map.put("NOW_DATE_STR",nowDateStr);
		
		return map;
	}

	/**
	 * 详细获取SQL组装
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	private String getDetailQuerySql(Long id, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       DATE_FORMAT(V.STOCKOUT_DEALER_DATE,'%Y-%m-%d') AS ACTUAL_DATE, -- 发车日期 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       V.VEHICLE_ID -- 车辆ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = O.MATERAIL_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = O.DEALER_ID \n");
		sql.append(" WHERE (V.NODE_STATUS = '" + OemDictCodeConstants.VEHICLE_NODE_12 + "' \n");
		sql.append("    OR V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_17 + "' \n");
		sql.append("    OR V.NODE_STATUS = '" + OemDictCodeConstants.K4_VEHICLE_NODE_18 + "') \n");
		sql.append("   AND V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_03 + "' \n");
		sql.append("   AND V.VEHICLE_ID = '" + id + "' \n");
		sql.append("   AND O.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		
		logger.debug("详细："+sql.toString());
		return sql.toString();
	}

	public void insertTiK4VsNvdr(String arriveDate, String dealerCode, Long userId, String vin) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("INSERT INTO TI_K4_VS_NVDR \n");
		sql.append("  (ACTION_CODE, ACTION_DATE, ACTION_TIME, INSPECTION_DATE, VIN, DEALER_CODE, VEHICLE_USE, ROW_ID, IS_RESULT, CREATE_BY, CREATE_DATE, IS_DEL) \n");
		sql.append("SELECT 'ZPOD', -- 交易代码 \n");
		sql.append("       DATE_FORMAT(NOW(), '%Y-%m-%d') AS ACTION_DATE, -- 交易日期 \n");
		sql.append("       DATE_FORMAT(NOW(), '%H:%i:%s') AS ACTION_TIME, -- 交易时间 \n");
		sql.append("       '" + arriveDate + "' AS INSPECTION_DATE, -- 验收日期 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       '" + dealerCode + "' AS DEALER_CODE, -- 经销商代码 \n");
		sql.append("       CASE WHEN IFNULL(R.RELATION_CODE, '-1') = '1' THEN '68' ELSE R.RELATION_CODE END AS VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       concat('S0008',  YEAR(NOW()) , MONTH(NOW()) , (SELECT MAX(IF_ID)+1 FROM TI_K4_VS_NVDR))  AS ROW_ID,  \n");
		sql.append("       '" + OemDictCodeConstants.IF_TYPE_NO + "' AS IS_RESULT, -- 是否上报 \n");
		sql.append("       '" + userId + "' AS CREATE_BY, -- 创建人ID \n");
		sql.append("        CURRENT_TIMESTAMP() AS CREATE_DATE, -- 创建日期 \n");
		sql.append("       '" + OemDictCodeConstants.IS_DEL_00 + "' AS IS_DEL \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TC_RELATION R ON CHAR(R.CODE_ID) = V.VEHICLE_USAGE \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND V.VIN = ? \n");
		params.add("'"+vin+"'");
		logger.debug("插入国产车验收接口表SQL："+sql.toString());
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}

}
