package com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 车辆保修历史查询
 * @author Administrator
 *
 */
@Repository
public class VehicleWarrantyHisQueryDao extends OemBaseDAO{
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	
	public PageInfoDto  VehicleWarrantyHisQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
     private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append(" select * from (");
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       R.REPAIR_ID, -- 维修ID \n");
		sql.append("       R.CLAIM_NUMBER, -- 索赔单号 \n");
		sql.append("       R.REPAIR_NO, -- 维修工单号 \n");
		sql.append("       R.REPAIR_TYPE, -- 维修类型 \n");
		sql.append("       R.VIN, -- 车架号 \n");
		sql.append("       SR.BUYDEALERNAME, -- 购车经销商 \n");
		sql.append("       V.MODEL_CODE, -- 车型代码 \n");
		sql.append("       V.MODEL_NAME, -- 车型名称 \n");
		sql.append("       V.MODEL_YEAR, -- 车型 \n");
		sql.append("       V.LICENSE_NO, -- 车牌号 \n");
		sql.append("       R.MILEAGE MILEAGE, -- 里程数 \n");
		sql.append("       R.CUSTOMER_NAME, -- 客户姓名 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       COALESCE(R.MAIN_PART, C.MAIN_PART) AS MAIN_PART, -- 主因零部件 \n");
		sql.append("       DATE_FORMAT(R.REPAIR_DATE, '%Y-%m-%d') AS REPAIR_DATE_CHAR -- 维修日期 \n");
		sql.append("  FROM TT_WR_REPAIR_dcs R \n");
		sql.append(" INNER JOIN (SELECT D.DEALER_ID, D.DEALER_CODE, D.DEALER_SHORTNAME \n");
		sql.append("               FROM TM_DEALER D) D ON D.DEALER_ID = R.DEALER_ID \n");
		sql.append("  LEFT JOIN (SELECT M.MODEL_CODE, M.MODEL_NAME, M.MODEL_YEAR, V.LICENSE_NO, V.MILEAGE, V.ENGINE_NO, V.VIN, V.VEHICLE_ID \n");
		sql.append("               FROM TM_VEHICLE_dec V INNER JOIN ("+getVwMaterialSql() +") M ON M.MATERIAL_ID = V.MATERIAL_ID) V ON V.VIN = R.VIN \n");
		sql.append("  LEFT JOIN (SELECT C.RO_NO, C.MAIN_PART FROM TT_WR_CLAIM_dcs C) C ON C.RO_NO = R.REPAIR_NO \n");
		sql.append("  LEFT JOIN (SELECT SR.VEHICLE_ID, D.DEALER_SHORTNAME AS BUYDEALERNAME \n");
		sql.append("               FROM TT_VS_SALES_REPORT SR INNER JOIN TM_DEALER D ON D.DEALER_ID = SR.DEALER_ID) SR ON SR.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append(" WHERE R.IS_DEL = '0' \n");
		sql.append("   AND R.REPAIR_TYPE <> '" + OemDictCodeConstants.REPAIR_FIXED_TYPE_14 + "' \n");	// PDI检查
		sql.append("   AND R.STATUS = '" + OemDictCodeConstants.REPAIR_ORD_BALANCE_TYPE_02 + "' \n");	// 已结算
		sql.append("   AND R.CREATE_DATE > '2012-11-04' \n");
		sql.append("   AND R.REFUND != 12781001 \n");//不是反结算
		
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND R.VIN LIKE '%" +queryParam.get("vin")+ "' \n");
		}
		
		sql.append(" \n");
		sql.append("UNION -- 拼接查询语句 \n");
		sql.append(" \n");
		
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       NULL AS REPAIR_ID, -- 维修ID \n");
		sql.append("       '' AS CLAIM_NUMBER, -- 索赔单号 \n");
		sql.append("       '' AS REPAIR_NO, -- 维修工单号 \n");
		sql.append("       '" + OemDictCodeConstants.CLAIM_TYPE_01 + "' AS REPAIR_TYPE, -- 维修类型 \n");
		sql.append("       C.VIN, -- 车架号 \n");
		sql.append("       SR.BUYDEALERNAME, -- 购车经销商 \n");
		sql.append("       V.MODEL_CODE, -- 车型代码 \n");
		sql.append("       V.MODEL_NAME, -- 车型名称 \n");
		sql.append("       V.MODEL_YEAR, -- 车型年 \n");
		sql.append("       V.LICENSE_NO, -- 车牌号 \n");
		sql.append("       C.MILLEAGE MILEAGE, -- 里程数 \n");
		sql.append("       '' AS CUSTOMER_NAME, -- 客户姓名 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       C.MAIN_PART, -- 主因零部件 \n");
		sql.append("       DATE_FORMAT(C.APPLY_DATE, '%Y-%m-%d') AS REPAIR_DATE_CHAR -- 维修日期 \n");
		sql.append("  FROM TT_WR_CLAIM_dcs C \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = C.DEALER_ID \n");
		sql.append("  LEFT JOIN (SELECT M.MODEL_CODE, M.MODEL_NAME, M.MODEL_YEAR, V.LICENSE_NO, V.MILEAGE, V.ENGINE_NO, V.VIN, V.VEHICLE_ID \n");
		sql.append("               FROM TM_VEHICLE_dec V INNER JOIN ("+getVwMaterialSql() +") M ON M.MATERIAL_ID = V.MATERIAL_ID) V ON V.VIN = C.VIN \n");
		sql.append("  LEFT JOIN (SELECT SR.VEHICLE_ID, D.DEALER_SHORTNAME AS BUYDEALERNAME \n");
		sql.append("               FROM TT_VS_SALES_REPORT SR INNER JOIN TM_DEALER D ON D.DEALER_ID = SR.DEALER_ID) SR ON SR.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append(" WHERE C.STATUS NOT IN ('" + OemDictCodeConstants.CLAIM_STATUS_04 + "', '" + OemDictCodeConstants.CLAIM_STATUS_05 + "') \n");
		sql.append("   AND C.IS_DEL = '0' \n");
		sql.append("   AND C.CLAIM_TYPE <> '" + OemDictCodeConstants.CLAIM_TYPE_02 + "' \n");	// 定期保养
		sql.append("   AND C.CREATE_DATE < '2012-11-05' \n");

		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND C.VIN LIKE '%" + queryParam.get("vin") + "%' \n");
		}
		
		// 车型
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sql.append("   AND (V.MODEL_CODE = '" + queryParam.get("model") + "' OR V.MODEL_NAME = '" +queryParam.get("model")+ "') \n");
		}
		
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("model_year"))) {
			sql.append("   AND V.MODEL_YEAR = '" + queryParam.get("model_year") + "' \n");
		}
		
		// 车牌号
		if (!StringUtils.isNullOrEmpty(queryParam.get("license_no"))) {
			sql.append("   AND V.LICENSE_NO = '" +queryParam.get("license_no")+ "' \n");
		}
		sql.append("   ) ff  \n");
		 System.out.println(sql.toString());
			return sql.toString();
	}
     
     /**
      * 车辆保修历史信息下载
      */
 	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}
     
     

	
}
