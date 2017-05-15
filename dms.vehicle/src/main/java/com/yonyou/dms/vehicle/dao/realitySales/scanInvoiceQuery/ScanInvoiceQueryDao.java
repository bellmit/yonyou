package com.yonyou.dms.vehicle.dao.realitySales.scanInvoiceQuery;

import java.util.ArrayList;
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
public class ScanInvoiceQueryDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 汇总查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryInventoryTotalList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 汇总SQL组装
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getTotalQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM ( \n");
		sql.append("SELECT D.DEALER_CODE, \n");
		sql.append("       D.DEALER_SHORTNAME AS DEALER_NAME, \n");
		sql.append("       COUNT(V.VIN) AS SALES_SUM \n");
		sql.append("  FROM TM_DEALER D \n");
		sql.append("  INNER JOIN (SELECT DISTINCT VEHICLE_ID, DEALER_ID, CTM_ID, SALES_DATE FROM TT_VS_SALES_REPORT \n");
		sql.append("  WHERE STATUS = '" + OemDictCodeConstants.STATUS_ENABLE+ "' AND IS_DEL<>'1') SR ON SR.DEALER_ID = D.DEALER_ID \n");
		sql.append("  INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = SR.VEHICLE_ID \n");
		sql.append("  INNER JOIN TT_VS_CUSTOMER C ON C.CTM_ID = SR.CTM_ID AND C.IS_DEL<>'1' \n");
		sql.append("  INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  WHERE 1 = 1 \n");
		sql.append("  "+ control("M.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs()) + " \n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and M.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and M.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and M.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and M.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and M.COLOR_CODE  = '"+queryParam.get("colorName")+"' \n");
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and M.TRIM_CODE  = '"+queryParam.get("trimName")+"' \n");
		}
		// 客户类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmType"))) {
			sql.append("   AND C.CTM_TYPE = ? \n");
			params.add(queryParam.get("ctmType"));
		}
		// 交车日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE_FORMAT(SR.SALES_DATE,'%Y-%m-%d') >= '"+queryParam.get("beginDate")+"' \n");
		}
		// 交车日期 End
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE_FORMAT(SR.SALES_DATE,'%Y-%m-%d') <= '"+queryParam.get("endDate")+"' \n");
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode").replaceAll(",", "','");
			sql.append("   AND D.DEALER_CODE IN ('"+ dealerCode +"') \n");
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append(getVins(vin, "V") );
		}
		// 销售顾问姓名
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesAdviser"))) {
			sql.append("   AND C.SALES_ADVISER like '%"+queryParam.get("salesAdviser")+"%' \n");
		}
		// 销售顾问编号
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldByid"))) {
			sql.append("  AND C.SOLD_BYID like '%"+queryParam.get("soldByid")+"%' \n");
		}
		sql.append(" GROUP BY D.DEALER_CODE, D.DEALER_SHORTNAME \n");
		sql.append(") A \n");
		logger.debug("汇总SQL ： "+sql.toString()+""+ params.toString());
		return sql.toString();
	}

	/**
	 * 明细查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryInventoryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getInventoryQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 明细SQL组装
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getInventoryQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT * FROM ( \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       O3.ORG_NAME AS SMALL_AREA, -- 小区 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       D.DEALER_SHORTNAME AS DEALER_NAME, -- 经销商简称 \n");
		sql.append("       C.SWT_CODE AS SAP_CODE, -- SAP代码 \n");
		sql.append("       VC.CTM_TYPE, -- 客户类型 \n");
		sql.append("       VC.SOLD_BYID,-- 销售顾问编号 \n");
		sql.append("       VC.SALES_ADVISER,-- 销售顾问名称 \n");
		sql.append("       VC.SOLD_MOBILE,-- 销售顾问电话 \n");
		sql.append("       VC.SOLD_EMAIL,-- 销售顾问邮箱 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- 车型代码 \n");
		sql.append("       M.MODEL_NAME, -- 车型描述 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.MODEL_YEAR, -- 颜色 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       date_format(SR.SALES_DATE,'%Y-%m-%d') AS SALES_DATE -- 交车时间 \n");
		sql.append("  FROM TT_VS_SALES_REPORT SR \n");
		sql.append("  INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = SR.VEHICLE_ID \n");
		sql.append("  INNER JOIN TM_DEALER D ON D.DEALER_ID = SR.DEALER_ID \n");
		sql.append("  INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  INNER JOIN TT_VS_CUSTOMER VC ON VC.CTM_ID = SR.CTM_ID AND VC.IS_DEL<>'1'  \n");
		sql.append("  INNER JOIN TM_COMPANY C ON C.COMPANY_ID = D.COMPANY_ID \n");
		sql.append("  LEFT JOIN TM_DEALER_ORG_RELATION DOR ON DOR.DEALER_ID = D.DEALER_ID \n");
		sql.append("  LEFT JOIN TM_ORG O3 ON O3.ORG_ID = DOR.ORG_ID AND O3.ORG_LEVEL = '3' \n");
		sql.append("  WHERE SR.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append(" 	 "+ control("M.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs()) + " \n");
		sql.append("	AND SR.IS_DEL<>'1' \n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and M.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and M.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and M.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and M.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and M.COLOR_CODE  = '"+queryParam.get("colorName")+"' \n");
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and M.TRIM_CODE  = '"+queryParam.get("trimName")+"' \n");
		}
		// 客户类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmType"))) {
			sql.append("   AND VC.CTM_TYPE = ? \n");
			params.add(queryParam.get("ctmType"));
		}
		// 交车日期 Begin
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE_FORMAT(SR.SALES_DATE,'%Y-%m-%d') >= '"+queryParam.get("beginDate")+"' \n");
		}
		// 交车日期 End
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE_FORMAT(SR.SALES_DATE,'%Y-%m-%d') <= '"+queryParam.get("endDate")+"' \n");
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCode = queryParam.get("dealerCode").replaceAll(",", "','");
			sql.append("   AND D.DEALER_CODE IN ('"+dealerCode+"') \n");
		}
		// 车架号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append(getVins(vin, "V"));
		}
		// 销售顾问姓名
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesAdviser"))) {
			sql.append("   AND C.SALES_ADVISER like '%"+queryParam.get("salesAdviser")+"%' \n");
		}
		// 销售顾问编号
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldByid"))) {
			sql.append("  AND C.SOLD_BYID like '%"+queryParam.get("soldByid")+"%' \n");
		}
		sql.append(")A \n");
		logger.debug("明细SQL ： "+ sql.toString()+" "+ params.toString());
		
		return sql.toString();
	}

	/**
	 * 汇总下载查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryTotalForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		 String sql = getTotalQuerySql(queryParam, params,loginInfo);
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
	     return resultList;
	}

	/**
	 * 明细下载查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryInventoryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		 String sql = getInventoryQuerySql(queryParam, params, loginInfo);
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
	     return resultList;
	}

}
