package com.yonyou.dms.vehicle.dao.dealerStorage.checkManagement;

import java.util.ArrayList;
import java.util.HashMap;
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

/**
 * 车辆验收查询DAO
 * 
 * @author DC
 * 
 */
@Repository
public class CheckMaintainDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, loginInfo, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 下载查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryVehicleCheckForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, loginInfo, params);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, LoginInfoDto loginInfo, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" \n");
		sql.append("SELECT C.SWT_CODE, -- SAP代码 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       O.ORDER_NO, -- 订单号 \n");
		sql.append("       O.ORDER_NO AS ORDER_NO1, -- 订单号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- 车型(CPOS) \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append(
				"       CASE WHEN I.DAMAGE_FLAG = '1' THEN '10041001' ELSE '10041002' END AS DAMAGE_FLAG, -- 是否质损 \n ");
		sql.append("       date_format(V.ZBIL_DATE,'%Y-%m-%d')  AS ARRIVE_DATE, -- 发车日期 \n");
		sql.append("       date_format(V.DEALER_STORAGE_DATE,'%Y-%m-%d')  AS ACTUAL_DATE, -- 验收日期 \n");
		sql.append("       I.INSPECTION_PERSON, -- 验收人 \n");
		sql.append("       I.INSPECTION_ID -- 验收ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" INNER JOIN TT_VS_INSPECTION I ON I.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = O.DEALER_ID \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_COMPANY C ON C.COMPANY_ID = D.COMPANY_ID \n");
		sql.append(" WHERE V.OEM_COMPANY_ID = '" + loginInfo.getCompanyId() + "' \n");

		sql.append("   AND V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_04 + "' \n");
		sql.append("   AND O.ORDER_STATUS IN ('" + OemDictCodeConstants.ORDER_STATUS_06 + "','"
				+ OemDictCodeConstants.SALE_ORDER_TYPE_10 + "') \n");
		// 业务范围
		sql.append("  " + control("M.SERIES_ID", loginInfo.getDealerSeriesIDs(), loginInfo.getPoseSeriesIDs()) + " \n");

		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			sql.append("   AND O.DEALER_ID in (" + getDealersByArea(loginInfo.getOrgId().toString()) + ") \n");
		}

		// 发车日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginArriveDate"))) {
			sql.append("   AND date_format(V.ZBIL_DATE,'%Y-%m-%d') >= '" + queryParam.get("beginArriveDate") + "' ");
			sql.append("   AND date_format(V.ZBIL_DATE,'%Y-%m-%d') <= '" + queryParam.get("endArriveDate") + "'");
		}

		// 验收日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginActualDate"))) {
			sql.append("   AND date_format(V.DEALER_STORAGE_DATE,'%Y-%m-%d') >= '" + queryParam.get("beginActualDate")
					+ "'");
			sql.append("   AND date_format(V.DEALER_STORAGE_DATE,'%Y-%m-%d') <= '" + queryParam.get("endActualDate")
					+ "'");
		}
		// 是否质损
		if (!StringUtils.isNullOrEmpty(queryParam.get("damageFlag"))) {
			sql.append("   AND I.DAMAGE_FLAG = ?");
			params.add(OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(queryParam.get("damageFlag"))));
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append("   AND D.DEALER_CODE IN ('"+queryParam.get("dealerCode").replaceAll(",", "','")+"')");
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append("   AND O.ORDER_NO LIKE ?");
			params.add("%" + queryParam.get("orderNo") + "%");
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + getVinsAuto(vin, "V") + " \n");
		}
		logger.info("车厂端查询/下载SQL组装" + sql.toString() + "  " + params.toString());
		return sql.toString();
	}

	/**
	 * 根据ID获取详细信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDetail(String id, LoginInfoDto loginInfo,Long inspectionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDetailQuerySql(id, loginInfo,inspectionId);
		map = OemDAOUtil.findFirst(sql, null);
		return map;
	}

	/**
	 * 详细查询SQL组装
	 * 
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	private String getDetailQuerySql(String id, LoginInfoDto loginInfo,Long inspectionId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT O.ORDER_NO, -- 订单号 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME, -- 经销商简称 \n");
		sql.append("       V.ENGINE_NO, -- 发动机号 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- 车型(CPOS) \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       DATE_FORMAT(V.ZBIL_DATE,'%Y-%m-%d') AS ARRIVE_DATE, -- 发车日期 \n");
		sql.append("       DATE_FORMAT(V.STOCKOUT_DEALER_DATE,'%Y-%m-%d') AS ARRIVE_DATE, -- 实际到车日期 \n");
		sql.append("       I.ARRIVE_TIME, -- 实际到车时间 \n");
		sql.append("       I.INSPECTION_PERSON, -- 验收人员 \n");
		sql.append("	   I.INSPECTION_ID, -- 验收ID \n"); // 详细查询采购订单表需要字段
		sql.append("       I.REMARK -- 备注说明 \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" INNER JOIN TT_VS_INSPECTION I ON I.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = O.DEALER_ID \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_COMPANY C ON C.COMPANY_ID = D.COMPANY_ID \n");
		sql.append(" WHERE V.OEM_COMPANY_ID = '" + loginInfo.getCompanyId() + "' \n");
		sql.append("   AND V.LIFE_CYCLE = '" + OemDictCodeConstants.LIF_CYCLE_04 + "' \n");
		sql.append("   AND (O.ORDER_STATUS = '" + OemDictCodeConstants.ORDER_STATUS_06 + "' \n");
		sql.append("    OR O.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_10 + "') \n");
		sql.append("   AND I.INSPECTION_ID = '" + inspectionId + "' \n");
		sql.append("   AND O.ORDER_NO = '" + id + "' \n");

		logger.info("车厂段详细查询SQL：" + sql.toString());
		return sql.toString();
	}

	/**
	 * ----------------------------------------经销商端功能代码-------------------------
	 * -----------------------
	 **/

	/**
	 * 车辆验收信息查询(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerQuerySql(queryParam, loginInfo, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * SQL组装(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @param params
	 * @return
	 */
	private String getDealerQuerySql(Map<String, String> queryParam, LoginInfoDto loginInfo, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT O.ORDER_NO, -- 订单号 \n");
		sql.append("                M.BRAND_NAME, -- 品牌 \n");
		sql.append("                M.SERIES_NAME, -- 车系 \n");
		sql.append("                M.MODEL_CODE, -- CPOS \n");
		sql.append("                M.MODEL_YEAR, -- 年款 \n");
		sql.append("                M.GROUP_NAME, -- 车款 \n");
		sql.append("                M.COLOR_NAME, -- 颜色 \n");
		sql.append("                M.TRIM_NAME, -- 内饰 \n");
		sql.append("                V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("                V.VIN, -- 车架号 \n");
		sql.append(
				"                CASE WHEN I.DAMAGE_FLAG = '1' THEN '10041001' ELSE '10041002' END AS DAMAGE_FLAG, -- 是否质损  \n");
		sql.append("                I.INSPECTION_ID, -- 验收ID \n");
		sql.append("                I.INSPECTION_PERSON, -- 验收人 \n");
		sql.append("                DATE_FORMAT(V.ZBIL_DATE,'%Y-%m-%d') AS ARRIVE_DATE, -- 发车日期 \n");
		sql.append(
				"                CASE WHEN  ISNULL(DATE_FORMAT(V.DEALER_STORAGE_DATE,'%Y-%m-%d')) THEN '' ELSE (DATE_FORMAT(V.DEALER_STORAGE_DATE,'%Y-%m-%d ')) END AS ACTUAL_DATE -- 验收日期 \n");
		sql.append("  FROM TM_VEHICLE_DEC V, TT_VS_ORDER O, (" + getVwMaterialSql() + ") M, TT_VS_INSPECTION I \n");
		sql.append(" WHERE V.VIN = O.VIN \n");
		sql.append("   AND V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append("   AND V.OEM_COMPANY_ID = '" + OemDictCodeConstants.OEM_ACTIVITIES + "' \n");
		sql.append("   AND O.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append("   AND V.VEHICLE_ID = I.VEHICLE_ID \n");
		sql.append("   AND O.ORDER_STATUS in( '" + OemDictCodeConstants.ORDER_STATUS_06 + "' ,'"
				+ OemDictCodeConstants.SALE_ORDER_TYPE_10 + "')\n");

		// 发车日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginArriveDate"))) {
			sql.append("   AND DATE_FORMAT(V.ZBIL_DATE,'%Y-%m-%d') >= '" + queryParam.get("beginArriveDate") + "' \n");
			sql.append("   AND DATE_FORMAT(V.ZBIL_DATE,'%Y-%m-%d') <= '" + queryParam.get("endArriveDate") + "' \n");
		}
		// 验收日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginActualDate"))) {
			sql.append("   AND DATE_FORMAT(V.DEALER_STORAGE_DATE,'%Y-%m-%d') >= '" + queryParam.get("beginActualDate")
					+ "' \n");
			sql.append("   AND DATE_FORMAT(V.DEALER_STORAGE_DATE,'%Y-%m-%d') <= '" + queryParam.get("endActualDate")
					+ "' \n");
		}
		// 是否质损
		if (!StringUtils.isNullOrEmpty(queryParam.get("damageFlag"))) {
			sql.append("   AND I.DAMAGE_FLAG = ?");
			params.add(OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(queryParam.get("damageFlag"))));
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + getVinsAuto(vin, "V") + " \n");
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			sql.append("   AND O.ORDER_NO LIKE ? \n");
			params.add("%" + queryParam.get("orderNo") + "%");
		}

		logger.info("经销商端 查询 sql ：" + sql.toString() + "" + params.toString());
		return sql.toString();
	}

	/**
	 * 根据ID获取详细信息(经销商端)
	 * 
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDealerDetail(Long id, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDealerDetailQuerySql(id, loginInfo);
		map = OemDAOUtil.findFirst(sql, null);
		return map;
	}
	
	/**
	 * 根据ID获取详细信息(经销商端)
	 * 
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryDealerDetail2(Long id) {
		String sql = getDealerDetailQuerySql2(id);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), null);
		return pageInfoDto;
	}

	/**
	 * 经销商端根据ID获取详细
	 * 
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	private String getDealerDetailQuerySql(Long id, LoginInfoDto loginInfo) {
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
		sql.append("       DATE_FORMAT(I.ARRIVE_DATE,'%Y-%m-%d') AS ARRIVE_DATE, -- 实际到车日期 \n");
		sql.append("       I.ARRIVE_TIME, -- 实际到车时间 \n");
		sql.append("       I.INSPECTION_PERSON, -- 验收人 \n");
		sql.append("       I.REMARK -- 备注 \n");
		sql.append("  FROM TT_VS_INSPECTION I \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = I.VEHICLE_ID \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.VIN = V.VIN \n");
		sql.append(" WHERE V.OEM_COMPANY_ID = '" + loginInfo.getOemCompanyId() + "' \n");
		sql.append("   AND O.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append("   AND O.ORDER_STATUS IN ('" + OemDictCodeConstants.ORDER_STATUS_06 + "','"
				+ OemDictCodeConstants.SALE_ORDER_TYPE_10 + "') \n");
		sql.append("   AND I.INSPECTION_ID = '" + id + "' \n");

		logger.debug("经销商端根据ID获取详细信息SQL： " + sql.toString());

		return sql.toString();
	}

	/**
	 * 信息下载(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> findDealerVehicleCheckSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getDealerQuerySql(queryParam, loginInfo, params);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	/** 采购订单信息 **/
	public String getDealerDetailQuerySql2(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DAMAGE_PART,DAMAGE_DESC  \n");
		sql.append(" From TT_VS_INSPECTION_DETAIL \n");
		sql.append(" WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(id)) {
			// 页面查询条件
			sql.append(" AND INSPECTION_ID='" + id + "' \n");
		}
		logger.debug("经销商端根据ID获取详细信息 采购订单信息SQL： " + sql.toString());
		return sql.toString();
	}

}
