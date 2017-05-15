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
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class InventoryQueryDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 经销商库存汇总查询
	 */
	public PageInfoDto queryInventoryTotalList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		logger.info("============经销商库存汇总查询===============");
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params, loginInfo);
		PageInfoDto resultList = OemDAOUtil.pageQuery(sql, params);
		return resultList;
	}

	/**
	 * 经销商库存汇总查询SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getTotalQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ( \n");
		sql.append(" SELECT TT1.DEALER_NAME, TT1.DEALER_CODE, COUNT(TT1.VIN) VIN \n");
		sql.append("    FROM (SELECT TT.DEALER_NAME, TT.DEALER_CODE, TT.VIN, NVDR \n");
		sql.append("            FROM (SELECT T2.SERIES_CODE, \n");
		sql.append("                         T2.MODEL_CODE, \n");
		sql.append("                         T2.COLOR_NAME, \n");
		sql.append("                         T1.VIN, \n");
		sql.append("                         T4.DEALER_SHORTNAME DEALER_NAME, \n");
		sql.append("                         T4.DEALER_CODE, \n");
		sql.append("                         T1.LIFE_CYCLE, \n");
		sql.append("                         date_format(T1.DEALER_STORAGE_DATE,'%Y-%m-%d') DEALER_STORAGE_DATE, \n");
		sql.append("                         (DATEDIFF(NOW(), T1.ARRIVE_PORT_DATE)) D, \n");
		sql.append("                         IFNULL((SELECT 1 FROM TT_VS_NVDR \n");
		sql.append("                              WHERE T1.VIN = VIN \n");
		sql.append("                                AND REPORT_TYPE = '" + OemDictCodeConstants.RETAIL_REPORT_TYPE_01 + "' LIMIT 1 ), \n");
		sql.append("                             0) NVDR \n");
		sql.append("                    FROM TM_VEHICLE_DEC T1, (" + getVwMaterialSql() + ") T2, TM_DEALER T4 \n");
		sql.append("                   WHERE T1.MATERIAL_ID = T2.MATERIAL_ID \n");
		// 业务范围 Begin
		sql.append("                    "
				+ control("T2.SERIES_ID", loginInfo.getDealerSeriesIDs(), loginInfo.getPoseSeriesIDs()) + " \n");
		// 业务范围 End..
		sql.append("                     AND T1.LIFE_CYCLE IN (" + OemDictCodeConstants.LIF_CYCLE_03 + ","
				+ OemDictCodeConstants.LIF_CYCLE_04 + ") \n");
		sql.append("                     AND T1.DEALER_ID = T4.DEALER_ID \n");

		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {

			if (!getDealersByArea(loginInfo.getOrgId().toString()).equals("")) {
				sql.append("      AND  T1.DEALER_ID IN (" + getDealersByArea(loginInfo.getOrgId().toString()) + ")\n");
			}
		}

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and T2.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and T2.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and T2.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and T2.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and T2.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and T2.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}

		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCodeStr = StringUtils.compileStr(queryParam.get("dealerCode"));
			sql.append(" AND T4.DEALER_CODE IN (" + dealerCodeStr + ") \n");
		}
		// // 库存超天
		if (!StringUtils.isNullOrEmpty(queryParam.get("days"))) {
			sql.append(" AND DATEDIFF(CURRENT DATE - T1.ARRIVE_PORT_DATE) >= ? \n");
			params.add(queryParam.get("days"));
		}
		// 物料类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupType"))) {
			sql.append("   AND T2.GROUP_TYPE = ? \n");
			params.add(queryParam.get("groupType"));
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append(getVins(vin, "T1"));
		}

		sql.append(" ORDER BY TO_DAYS(NOW()) - TO_DAYS(T1.ARRIVE_PORT_DATE) DESC )tt ");
		sql.append("           WHERE 1 = 1 \n");
		// 是否交车
		if (!StringUtils.isNullOrEmpty(queryParam.get("nvdr"))) {
			String nvdr = OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(queryParam.get("nvdr")));
			sql.append(" AND NVDR = ? \n");
			params.add(nvdr);
		}
		sql.append(")tt1");
		sql.append(" GROUP BY DEALER_NAME, DEALER_CODE \n");
		sql.append(" ) A \n");

		logger.debug(sql.toString());

		return sql.toString();
	}

	/**
	 * 经销商库存汇总查询信息下载
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryTotalForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getTotalQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.findAll(sql, params);
		return resultList;
	}

	/**
	 * 经销商库存明细查询(oem)
	 */
	public PageInfoDto queryInventoryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		logger.info("============经销商明细查询===============");
		List<Object> params = new ArrayList<Object>();
		String sql = getInventoryQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 经销商库存明细查询SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getInventoryQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       CASE WHEN M.GROUP_TYPE = '90081001' THEN '国产' "
				+ "				WHEN M.GROUP_TYPE = '90081002' THEN '进口' "
				+ "				END AS GROUP_TYPE, -- 类型\n");
		sql.append("       V.NODE_STATUS, -- 车辆节点状态 \n");
		sql.append("       V.LIFE_CYCLE, -- 车辆生命周期 \n");
		sql.append("       date_format(H.YOUI_DATE,'%Y-%m-%d') AS YOUI_DATE, -- 开票日期\n");
		sql.append("       date_format(H.ZVHC_DATE,'%Y-%m-%d') AS ZVHC_DATE, -- 发运日期\n");
		sql.append("       date_format(H.ZPD2_DATE,'%Y-%m-%d') AS ZPD2_DATE, -- 到店日期\n");
		sql.append("       date_format(V.DEALER_STORAGE_DATE,'%Y-%m-%d') AS DEALER_STORAGE_DATE, -- 验收日期 \n");
		sql.append("       date_format(N.CREATE_DATE, '%Y-%m-%d %H:%i:%s') AS CREATE_DATE, -- 零售上报日期\n");
		sql.append("       date_format(N.UPDATE_DATE,'%Y-%m-%d') AS NVDR_DATE, -- 零售上报审批日期\n");
		sql.append("       CASE WHEN IFNULL(N.NVDR_ID,0) = '0' THEN '否'  \n");
		sql.append("       		ELSE '是' END AS NVDR -- 是否交车 \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" LEFT JOIN TT_VEHICLE_NODE_HISTORY H ON H.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append(" LEFT JOIN TT_VS_NVDR N ON N.VIN = V.VIN AND N.REPORT_TYPE = '"
				+ OemDictCodeConstants.RETAIL_REPORT_TYPE_01 + "' \n");
		sql.append(" WHERE V.LIFE_CYCLE IN ('" + OemDictCodeConstants.LIF_CYCLE_03 + "', '"
				+ OemDictCodeConstants.LIF_CYCLE_04 + "') \n");

		// 业务范围 Begin
		sql.append(
				"    " + control("M.SERIES_ID", loginInfo.getDealerSeriesIDs(), loginInfo.getPoseSeriesIDs()) + " \n");
		// 业务范围 End..

		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			if (!getDealersByArea(loginInfo.getOrgId().toString()).equals("")) {
				sql.append("   AND D.DEALER_ID in (" + getDealersByArea(loginInfo.getOrgId().toString()) + ") \n");
			}
		}

		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and M.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and M.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and M.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and M.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and M.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and M.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}

		// 经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {

			String dealerCodeStr = StringUtils.compileStr(queryParam.get("dealerCode"));
			sql.append(" AND D.DEALER_CODE IN (" + dealerCodeStr + ") \n");
		}

		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append(getVinsAuto(vin, "V"));
		}

		// 是否交车
		if (!StringUtils.isNullOrEmpty(queryParam.get("nvdr"))) {
			String nvdr = OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(queryParam.get("nvdr")));
			sql.append("   AND CASE WHEN IFNULL(N.NVDR_ID,0) = '0' THEN '0' ELSE '1' END  =  1 =  ? \n");
			params.add(nvdr);
		}

		// 物料类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupType"))) {
			sql.append("   AND M.GROUP_TYPE = ? \n");
			params.add(queryParam.get("groupType"));
		}

		logger.debug("SQL-------: " + sql.toString() + "  " + params.toString());

		return sql.toString();
	}

	/**
	 * 经销商库存明细查询信息下载
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInventoryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getInventoryQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

}
