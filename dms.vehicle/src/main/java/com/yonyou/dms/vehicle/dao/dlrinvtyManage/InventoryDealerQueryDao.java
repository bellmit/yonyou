package com.yonyou.dms.vehicle.dao.dlrinvtyManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class InventoryDealerQueryDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 汇总查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryInventoryGroupList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		logger.info("============库存汇总查询===============");
		List<Object> params = new ArrayList<Object>();
		String sql = getGroupQuerySql(queryParam, params, loginInfo);
		PageInfoDto resultList = OemDAOUtil.pageQuery(sql, params);
		return resultList;
	}

	/**
	 * 汇总查询SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getGroupQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n select * from (");
		sql.append("SELECT VM.BRAND_NAME, -- 品牌 \n");
		sql.append("       VM.SERIES_NAME, -- 车系 \n");
		sql.append("       VM.MODEL_CODE, -- CPOS \n");
		sql.append("       VM.GROUP_NAME, -- 车款 \n");
		sql.append("       VM.MODEL_YEAR, -- 年款 \n");
		sql.append("       VM.COLOR_NAME, -- 颜色 \n");
		sql.append("       VM.TRIM_NAME, -- 内饰 \n");
		sql.append("       COUNT(*) AS TOTAL1 -- 合计 \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") VM ON VM.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append("  LEFT JOIN TT_VS_NVDR N ON N.VIN = V.VIN AND N.REPORT_TYPE = '"
				+ OemDictCodeConstants.RETAIL_REPORT_TYPE_01 + "' \n");
		sql.append(" WHERE V.LIFE_CYCLE IN (" + OemDictCodeConstants.LIF_CYCLE_03 + ", "
				+ OemDictCodeConstants.LIF_CYCLE_04 + ") \n");
		sql.append("   AND V.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND V.VIN LIKE UPPER('%" + queryParam.get("vin") + "%') \n");
		}
		// 是否交车
		if (!StringUtils.isNullOrEmpty(queryParam.get("nvdr"))) {
			String nvdr = OemDictCodeConstantsUtils
					.getIf_type(Integer.parseInt(CommonUtils.checkNull(queryParam.get("nvdr"))));
			sql.append("   AND IF(N.VIN = V.VIN, 1, 0) = '" + nvdr + "' \n");
		}
		sql.append(" GROUP BY VM.BRAND_NAME, VM.SERIES_NAME, VM.MODEL_CODE, VM.GROUP_NAME, VM.MODEL_YEAR, VM.COLOR_NAME, VM.TRIM_NAME \n");
		sql.append(" ) a \n");  
		logger.debug("库存汇总查询SQL ： " + sql.toString() + "  **  " + params);
		return sql.toString();
	}

	/**
	 * 明细查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryInventoryDetailList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		logger.info("============明细查询===============");
		List<Object> params = new ArrayList<Object>();
		String sql = getInventoryDetailQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 明细查询SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getInventoryDetailQuerySql(Map<String, String> queryParam, List<Object> params,
			LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT VM.BRAND_NAME, -- 品牌 \n");
		sql.append("       VM.SERIES_NAME, -- 车系 \n");
		sql.append("       VM.MODEL_CODE, -- CPOS \n");
		sql.append("       VM.GROUP_NAME, -- 车款 \n");
		sql.append("       VM.MODEL_YEAR, -- 年款 \n");
		sql.append("       VM.COLOR_NAME, -- 颜色 \n");
		sql.append("       VM.TRIM_NAME, -- 内饰 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       CHAR(DATE(V.DEALER_STORAGE_DATE)) AS DEALER_STORAGE_DATE, -- 验收日期 \n");
		sql.append("        IF(N.VIN=V.VIN, '是', '否') AS NVDR -- 是否交车 \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") VM ON VM.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append("  LEFT JOIN TT_VS_NVDR N ON N.VIN = V.VIN AND N.REPORT_TYPE = '"
				+ OemDictCodeConstants.RETAIL_REPORT_TYPE_01 + "' \n");
		sql.append(" WHERE V.LIFE_CYCLE IN (" + OemDictCodeConstants.LIF_CYCLE_03 + ", "
				+ OemDictCodeConstants.LIF_CYCLE_04 + ") \n");
		sql.append("   AND V.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND V.VIN LIKE UPPER('%" + queryParam.get("vin") + "%') \n");
		}
		// 是否交车
		if (!StringUtils.isNullOrEmpty(queryParam.get("nvdr"))) {
			String nvdr = OemDictCodeConstantsUtils
					.getIf_type(Integer.parseInt(CommonUtils.checkNull(queryParam.get("nvdr"))));
			sql.append("   AND IF(N.VIN=V.VIN, 1, 0) = '" + nvdr + "' \n");
		}
		logger.debug("库存明细查询SQL ： " + sql.toString() + " ** " + params);
		return sql.toString();
	}

	/**
	 * 明细下载
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryTotalForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getInventoryDetailQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

}
