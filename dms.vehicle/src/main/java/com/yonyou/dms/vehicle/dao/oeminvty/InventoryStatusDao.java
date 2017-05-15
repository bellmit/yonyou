package com.yonyou.dms.vehicle.dao.oeminvty;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class InventoryStatusDao extends OemBaseDAO {
	// 汇总查询
	public PageInfoDto inventoryStatusQueryCollect(Map<String, String> queryParam) {

		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String colorName = queryParam.get("colorName");
		String trimCode = queryParam.get("trimName");
		String beginDate = queryParam.get("beginDate");
		String endDate = queryParam.get("endDate");
		String vin = queryParam.get("vin");
		String days = queryParam.get("days");
		String selctstore = queryParam.get("selctStore");

		StringBuffer sql = new StringBuffer();

		sql.append(
				"SELECT TB1.BRAND_CODE,  TB1.SERIES_CODE,TB1.SERIES_NAME, TB1.GROUP_NAME,  TB1.MODEL_YEAR,  TB1.COLOR_NAME,  TB1.TRIM_NAME,\n");
		sql.append(
				"    (TB1.SUM - COALESCE (TB2.`MATCH`, 0)) AS UNMATCH,   COALESCE(TB2.MATCH, 0) AS `MATCH`, TB1.SUM\n");
		sql.append(" FROM (  \n");
		sql.append(
				" SELECT M.BRAND_CODE,M.SERIES_CODE,M.SERIES_NAME,M.GROUP_NAME,  M.MODEL_YEAR,  M.COLOR_NAME,M.MODEL_NAME, M.TRIM_NAME,\n");
		sql.append("	       COUNT(*) AS SUM \n");
		sql.append(" FROM TM_VEHICLE_dec V \n");
		sql.append("INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE V.LIFE_CYCLE ='" + OemDictCodeConstants.LIF_CYCLE_02 + "'\n");
		sql.append("   AND M.GROUP_TYPE =' " + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		String brandId = queryParam.get("brandId");
		// 品牌
		if (!StringUtils.isNullOrEmpty(brandId)) {
			sql.append("   AND M.BRAND_ID = '" + brandId + "' \n");
		}

		// 车系
		if (!StringUtils.isNullOrEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}

		// 车款
		if (!StringUtils.isNullOrEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (!StringUtils.isNullOrEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (!StringUtils.isNullOrEmpty(colorName)) {
			sql.append("   AND M.COLOR_CODE = '" + colorName + "' \n");
		}

		// 内饰
		if (!StringUtils.isNullOrEmpty(trimCode)) {
			sql.append("   AND M.TRIM_CODE = '" + trimCode + "' \n");
		}

		// 入库日期 Begin
		if (!StringUtils.isNullOrEmpty(beginDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) >= '" + beginDate + "' \n");
		}

		// 入库日期 End
		if (!StringUtils.isNullOrEmpty(endDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) <= '" + endDate + "' \n");
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(vin)) {
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + getVinsAuto(vin, "V") + " \n");
		}

		// 库龄超天

		if (!StringUtils.isNullOrEmpty(days)) {
			sql.append("   AND  datediff(current_date,V.ARRIVE_PORT_DATE)>= '" + days + "' \n");
		}

		if (selctstore != null) {

			String selctStore = OemDictCodeConstantsUtils.getSelctStore(Integer.parseInt(queryParam.get("selctStore")));
			// CG/CTCAI库存 "1:CG;2:CTCAI"
			if (!StringUtils.isNullOrEmpty(selctStore)) {
				if (selctStore.equals("1")) {
					sql.append("   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007) \n");
				} else {
					sql.append("   AND V.NODE_STATUS IN (11511008,11511010,11511011,11511009,11511018,11511019) \n");
				}
			} else {
				sql.append(
						"   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007,11511008,11511010,11511011,11511009,11511018,11511019) \n");
			}
		}

		sql.append(
				" GROUP BY M.BRAND_CODE, M.SERIES_CODE,M.SERIES_NAME, M.GROUP_NAME, M.MODEL_YEAR, M.TRIM_NAME, M.COLOR_NAME \n");
		sql.append(" ORDER BY M.MODEL_YEAR \n");
		sql.append(") TB1  \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("	SELECT M.BRAND_CODE, \n");
		sql.append("	       M.SERIES_CODE,  \n");
		sql.append("	       M.GROUP_NAME,  \n");
		sql.append("	       M.MODEL_YEAR,  \n");
		sql.append("	       M.COLOR_NAME,  \n");
		sql.append("	       M.TRIM_NAME,  \n");
		sql.append("	       COUNT(*) AS `MATCH` \n");
		sql.append("	  FROM TM_VEHICLE_dec V  \n");
		sql.append("	 INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID  \n");
		sql.append("	 INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN  \n");
		sql.append("	 WHERE V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_02 + "'  \n");
		sql.append("   AND M.GROUP_TYPE =" + OemDictCodeConstants.GROUP_TYPE_IMPORT + " \n");
		sql.append("	   AND O.ORDER_STATUS <> '" + OemDictCodeConstants.ORDER_STATUS_08 + "' \n");
		sql.append("   AND O.ORDER_STATUS <> '" + OemDictCodeConstants.ORDER_STATUS_09 + "' \n");

		// 品牌
		if (!StringUtils.isNullOrEmpty(brandId)) {
			sql.append("   AND M.BRAND_ID = '" + brandId + "' \n");
		}

		// 车系
		if (!StringUtils.isNullOrEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}

		// 车款
		if (!StringUtils.isNullOrEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (!StringUtils.isNullOrEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (!StringUtils.isNullOrEmpty(colorName)) {
			sql.append("   AND M.COLOR_code = '" + colorName + "' \n");
		}

		// 内饰
		if (!StringUtils.isNullOrEmpty(trimCode)) {
			sql.append("   AND M.TRIM_code = '" + trimCode + "' \n");
		}

		// 入库日期 Begin
		if (!StringUtils.isNullOrEmpty(beginDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) >= '" + beginDate + "' \n");
		}

		// 入库日期 End
		if (!StringUtils.isNullOrEmpty(endDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) <= '" + endDate + "' \n");
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(vin)) {
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + getVinsAuto(vin, "V") + " \n");
		}

		// 库龄超天
		if (!StringUtils.isNullOrEmpty(days)) {
			sql.append("   AND  datediff(current_date,V.ARRIVE_PORT_DATE)>= '" + days + "' \n");
		}

		if (selctstore != null) {

			String selctStore = OemDictCodeConstantsUtils.getSelctStore(Integer.parseInt(queryParam.get("selctStore")));
			// CG/CTCAI库存 "1:CG;2:CTCAI"
			if (!StringUtils.isNullOrEmpty(selctStore)) {
				if (selctStore.equals("1")) {
					sql.append("   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007) \n");
				} else {
					sql.append("   AND V.NODE_STATUS IN (11511008,11511010,11511011,11511009,11511018,11511019) \n");
				}
			} else {
				sql.append(
						"   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007,11511008,11511010,11511011,11511009,11511018,11511019) \n");
			}
		}
		sql.append(
				"   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007,11511008,11511010,11511011,11511009,11511018,11511019) \n");
		sql.append(
				" GROUP BY M.BRAND_CODE, M.SERIES_CODE,M.SERIES_NAME, M.GROUP_NAME, M.MODEL_YEAR, M.TRIM_NAME, M.COLOR_NAME \n");
		sql.append(" ORDER BY M.MODEL_YEAR  \n");
		sql.append("	)  TB2 ON TB1.BRAND_CODE = TB2.BRAND_CODE \n");
		sql.append("	   AND TB1.SERIES_CODE = TB2.SERIES_CODE  \n");
		sql.append("   AND TB1.GROUP_NAME = TB2.GROUP_NAME  \n");
		sql.append("   AND TB1.MODEL_YEAR = TB2.MODEL_YEAR  \n");
		sql.append("	   AND TB1.COLOR_NAME = TB2.COLOR_NAME  \n");
		System.out.println(sql.toString());
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	// 明细查询
	public PageInfoDto inventoryStatusQueryDetails(Map<String, String> queryParam) {

		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.pageQuery(sql, null);
	}

	public List<Map> downloadDetailsl(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		String seriesName = queryParam.get("seriesName");
		String groupName = queryParam.get("groupName");
		String modelYear = queryParam.get("modelYear");
		String colorName = queryParam.get("colorName");
		String trimCode = queryParam.get("trimName");
		String beginDate = queryParam.get("beginDate");
		String endDate = queryParam.get("endDate");
		String vin = queryParam.get("vin");
		String days = queryParam.get("days");
		String selctStore = queryParam.get("selctStore");
		// CG/CTCAI库存 "1:CG;2:CTCAI"
		if (selctStore != null) {
			if (selctStore.equals(String.valueOf(OemDictCodeConstants.selctStore1))) {
				selctStore = "1";
			}
			if (selctStore.equals(String.valueOf(OemDictCodeConstants.selctStore2))) {
				selctStore = "2";
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VEHICLE_ID,  \n");
		sql.append("       M.BRAND_CODE,  \n");
		sql.append("       M.SERIES_CODE, M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE,M.MODEL_NAME,  \n");
		sql.append("       M.COLOR_NAME,  \n");
		sql.append("       M.TRIM_NAME,\n");
		sql.append("       V.VIN,  \n");
		sql.append("       date_format (V.ORG_STORAGE_DATE, '%y-%m-%d') AS ORG_STORAGE_DATE, \n");
		sql.append("       date_format (V.ZBIL_DATE, '%y-%m-%d') AS ZBIL_DATE,\n");
		sql.append("       (datediff(current_date,V.ARRIVE_PORT_DATE)) D \n");
		sql.append("  FROM TM_VEHICLE_dec V, (" + getVwMaterialSql() + ") M \n");
		sql.append(" WHERE V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append("   AND M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT + "' \n");
		sql.append("   AND V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_02 + "' \n");
		String brandName = queryParam.get("brandId");
		// 品牌
		if (!StringUtils.isNullOrEmpty(brandName)) {
			sql.append("   AND M.BRAND_ID = '" + brandName + "' \n");
		}

		// 车系
		if (!StringUtils.isNullOrEmpty(seriesName)) {
			sql.append("   AND M.SERIES_ID = '" + seriesName + "' \n");
		}

		// 车款
		if (!StringUtils.isNullOrEmpty(groupName)) {
			sql.append("   AND M.GROUP_ID = '" + groupName + "' \n");
		}

		// 年款
		if (!StringUtils.isNullOrEmpty(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = '" + modelYear + "' \n");
		}

		// 颜色
		if (!StringUtils.isNullOrEmpty(colorName)) {
			sql.append("   AND M.COLOR_CODE = '" + colorName + "' \n");
		}

		// 内饰
		if (!StringUtils.isNullOrEmpty(trimCode)) {
			sql.append("   AND M.TRIM_CODE = '" + trimCode + "' \n");
		}

		// 入库日期 Begin
		if (!StringUtils.isNullOrEmpty(beginDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) >= '" + beginDate + "' \n");
		}

		// 入库日期 End
		if (!StringUtils.isNullOrEmpty(endDate)) {
			sql.append("   AND DATE(V.ORG_STORAGE_DATE) <= '" + endDate + "' \n");
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(vin)) {
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + getVinsAuto(vin, "V") + " \n");
		}

		// 库龄超天
		if (!StringUtils.isNullOrEmpty(days)) {
			sql.append("   AND  datediff(current_date,V.ARRIVE_PORT_DATE)>= '" + days + "' \n");
		}

		// CG/CTCAI库存 "1:CG;2:CTCAI"
		if (!StringUtils.isNullOrEmpty(selctStore)) {
			if (selctStore.equals("1")) {
				sql.append("   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007) \n");
			} else {
				sql.append("   AND V.NODE_STATUS IN (11511008,11511010,11511011,11511009,11511018,11511019) \n");
			}
		} else {
			sql.append(
					"   AND V.NODE_STATUS IN (11511004,11511005,11511006,11511014,11511013,11511007,11511008,11511010,11511011,11511009,11511018,11511019) \n");
		}

		return sql.toString();
	}

	public List<Map> findnodeStatus(Long id) {
		String sql = "select CODE_ID,CODE_DESC from TC_CODE_DCS where type=" + OemDictCodeConstants.VEHICLE_NODE
				+ " and num>=(select num from TC_CODE_dcs where CODE_ID=" + id + ") order by num";
		List<Map> list = OemDAOUtil.findAll(sql, null);
		System.out.println(sql);
		return list;
	}

}
