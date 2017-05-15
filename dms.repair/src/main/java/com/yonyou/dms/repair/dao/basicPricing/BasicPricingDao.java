package com.yonyou.dms.repair.dao.basicPricing;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class BasicPricingDao extends OemBaseDAO {
	// 查询
	public PageInfoDto basicPricingQuery(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		System.out.println(sql);
		return OemDAOUtil.pageQuery(sql, null);
	}

	// 下载
	public List<Map> doDownload(Map<String, String> queryParam) {
		String sql = getQuerySql(queryParam, null);
		return OemDAOUtil.downloadPageQuery(sql, null);
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t.* from (   SELECT DISTINCT \n");
		sql.append("       M.BRAND_NAME,  \n");
		sql.append("       M.SERIES_CODE,  \n");
		sql.append("       M.SERIES_NAME, \n");
		sql.append("       M.MODEL_CODE, \n");
		sql.append("       M.MODEL_NAME,  \n");
		sql.append("       M.GROUP_CODE, \n");
		sql.append("       M.GROUP_NAME,  \n");
		sql.append("       M.MODEL_YEAR, \n");
		sql.append("       MP.BASE_PRICE,  \n");
		sql.append("       MP.MSRP,\n");
		sql.append("        DATE_FORMAT(MP.ENABLE_DATE,'%Y-%m-%d ')  AS ENABLE_DATE, \n");
		sql.append("       DATE_FORMAT   (MP.DISABLE_DATE ,'%Y-%m-%d ') AS DISABLE_DATE,  \n");
		sql.append("       (CASE WHEN MP.ENABLE_DATE > current_timestamp THEN '未启用' \n");
		sql.append("             WHEN MP.DISABLE_DATE < current_timestamp THEN '已过期' \n");
		sql.append("             ELSE '已启用' END) AS VALID_STATE  \n");
		sql.append("  FROM TM_MATERIAL_PRICE MP \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = MP.MATERIAL_ID \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sql.append("   AND M.BRAND_ID = '" + queryParam.get("brandId") + "' \n");
		}

		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append("   AND M.SERIES_ID = '" + queryParam.get("seriesName") + "' \n");
		}

		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append("   AND M.GROUP_ID = '" + queryParam.get("groupName") + "' \n");
		}

		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append("   AND M.MODEL_YEAR = '" + queryParam.get("modelYear") + "' \n");
		}

		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append("   AND M.COLOR_CODE = '" + queryParam.get("colorName") + "' \n");
		}

		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append("   AND M.TRIM_CODE = '" + queryParam.get("trimName") + "' \n");
		}

		// 有效期状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("validState"))) {
			if (queryParam.get("validState").equals(OemDictCodeConstants.VALID_STATE2)) {
				sql.append("   AND MP.ENABLE_DATE > current_timestamp \n");
			} else if (queryParam.get("validState").equals(OemDictCodeConstants.VALID_STATE1)) {
				sql.append("   AND current_date BETWEEN MP.ENABLE_DATE AND MP.DISABLE_DATE \n");
			} else if (queryParam.get("validState").equals(OemDictCodeConstants.VALID_STATE3)) {
				sql.append("   AND MP.DISABLE_DATE < current_timestamp \n");
			}
		}
		sql.append("   )t \n");
		return sql.toString();
	}

}
