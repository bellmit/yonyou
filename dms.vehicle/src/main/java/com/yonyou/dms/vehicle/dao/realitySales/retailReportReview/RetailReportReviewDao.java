package com.yonyou.dms.vehicle.dao.realitySales.retailReportReview;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
@SuppressWarnings("all")
public class RetailReportReviewDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 零售上报审核查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/** 
	 * 零售上报审核下载
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryRetailReportQueryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TT2.CTM_ID,TT1.*,TT.*,TT2.CTM_NAME,TT2.MAIN_PHONE,TT2.CTM_TYPE,TRR.REMARK,TRR.OTHER_REMARK FROM (\n");
		sql.append(" SELECT T5.*,T6.CTM_ID,date_format(T6.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE FROM (\n");
		sql.append(" SELECT T1.BUSINESS_ID,t3.SERIES_CODE,T1.STATUS,date_format(T1.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE,T1.REPORT_TYPE,T1.NVDR_ID,T2.DEALER_CODE,T2.DEALER_SHORTNAME DEALER_NAME,T3.BRAND_NAME,T3.SERIES_NAME,T3.BRAND_ID,T3.SERIES_ID,T3.GROUP_ID,T3.TRIM_CODE,T3.COLOR_CODE,T3.BRAND_CODE,T3.MODEL_YEAR,T3.MODEL_NAME,T3.GROUP_NAME, \n ");
		sql.append("  T3.COLOR_NAME,T3.TRIM_NAME,T4.VIN, T4.VEHICLE_ID ,T4.DEALER_ID,T4.VEHICLE_USAGE,\n");
		sql.append(
				"  (select tc.SWT_CODE from TM_COMPANY tc,TM_DEALER td where td.COMPANY_ID = tc.COMPANY_ID and td.DEALER_ID = t2.DEALER_ID) SWI_CODE\n");
		sql.append(
				" FROM TT_VS_NVDR T1,TM_DEALER T2 , ("+ getVwMaterialSql()+") T3,TM_VEHICLE_DEC T4 WHERE T1.VIN = T4.VIN AND T4.MATERIAL_ID = T3.MATERIAL_ID \n");
		sql.append("  AND T1.BUSINESS_ID = T2.DEALER_ID "
					+ "AND T1.NVDR_STATUS =  "+ OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02 + " AND T1.IS_DEL != 1 \n");// 无效
		//零售上报日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and T1.CREATE_DATE > date_format('" + queryParam.get("beginDate") + " 00:00:00','%Y-%m-%d %H:%i:%s')");
			sql.append(" and T1.CREATE_DATE < date_format('" + queryParam.get("endDate") + " 23:59:59','%Y-%m-%d %H:%i:%s')");
		}
		sql.append(" ) T5 LEFT JOIN TT_VS_SALES_REPORT T6 ON T5.VEHICLE_ID = T6.VEHICLE_ID) TT1 \n");

		sql.append(
				" LEFT JOIN TT_VS_CUSTOMER TT2  ON TT1.CTM_ID = TT2.CTM_ID LEFT JOIN TT_RESOURCE_REMARK TRR ON TT1.VIN = TRR.VIN\n");
		sql.append(
				"   left join (select TOR2.ORG_ID ORG_ID2,TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TOR2.ORG_DESC ORG_DESC2,TOR3.ORG_DESC ORG_DESC3,TC.SWT_CODE\n");
		sql.append("	  			from TM_ORG                     TOR2,\n");
		sql.append(" 		  			 TM_ORG                     TOR3,\n");
		sql.append(" 					 TM_DEALER_ORG_RELATION     TDOR,\n");
		sql.append("					 TM_DEALER                  TD,\n");
		sql.append("					 TM_COMPANY                 TC\n");
		sql.append("				 where TOR3.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("				   and TOR3.ORG_ID = TDOR.ORG_ID\n");
		sql.append("			       and TDOR.DEALER_ID = TD.DEALER_ID\n");
		sql.append("				   and TD.COMPANY_ID = TC.COMPANY_ID\n");
		sql.append(" 				   and TOR3.ORG_LEVEL = 3\n");
		sql.append("				   and TOR2.ORG_LEVEL = 2) tt\n");
		sql.append("      on tt1.BUSINESS_ID=tt.DEALER_ID \n");
		sql.append("      LEFT JOIN  TM_VEHICLE_DEC TM ON TM.VIN = TT1.VIN \n");
		sql.append("      LEFT JOIN  ("+ getVwMaterialSql()+") VW ON TM.MATERIAL_ID = VW.MATERIAL_ID \n");
		sql.append(" where 1=1 \n");
		sql.append( control(" VW.SERIES_ID ", loginInfo.getDealerSeriesIDs(), loginInfo.getPoseSeriesIDs()) + " \n");
		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			sql.append("      AND  tt1.DEALER_ID in ("+  getDealersByArea(loginInfo.getOrgId().toString()) +")\n");
		}
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and TT1.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and TT1.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and TT1.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and TT1.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and TT1.COLOR_CODE  = '"+queryParam.get("colorName")+"' \n");
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and TT1.TRIM_CODE  = '"+queryParam.get("trimName")+"' \n");
		}
		//vin
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append( getVinsAuto(vin, "tt1"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("   AND TT1.DEALER_CODE IN  ("+queryParam.get("dealerCode")+") \n");
		}
		//开票状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("reportStatus"))){
			sql.append("   AND TT1.STATUS = ? \n");
			params.add(queryParam.get("reportStatus"));
		}
		//零售上报类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("reportType"))){
			sql.append("   AND TT1.REPORT_TYPE = ? \n");
			params.add(queryParam.get("reportType"));
		}
		logger.debug(sql.toString() +"   "+params.toString());
		return sql.toString();
	}
	
	/**
	 * NVDR_ID获取SQL
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getNvdrIdQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT tt2.CTM_ID,TT1.*,TT2.CTM_NAME,TT2.MAIN_PHONE,TT2.CTM_TYPE FROM( \n");
		sql.append("  SELECT T5.*,T6.CTM_ID,T6.SALES_DATE FROM   \n ");
		sql.append("  (SELECT T1.STATUS,T1.CREATE_DATE,T1.REPORT_TYPE,T1.NVDR_ID,T2.DEALER_CODE,T2.DEALER_NAME,T3.SERIES_CODE CODE2,T3.MODEL_CODE CODE3,T3.MODEL_NAME,T3.BRAND_CODE,T3.SERIES_CODE,T3.GROUP_NAME,T3.MODEL_YEAR,T3.TRIM_NAME,  \n ");
		sql.append("  T3.COLOR_NAME,T4.VIN, T4.VEHICLE_ID ,T4.DEALER_ID\n");
		sql.append(" FROM TT_VS_NVDR T1,TM_DEALER T2 , ("+ getVwMaterialSql()+") T3,TM_VEHICLE_DEC T4 WHERE T1.VIN = T4.VIN AND T4.MATERIAL_ID = T3.MATERIAL_ID \n");
		sql.append("  AND T1.BUSINESS_ID = T2.DEALER_ID  AND T1.NVDR_STATUS = "+ OemDictCodeConstants.VEHICLE_RETAIL_STATUS_02 + "\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and T1.CREATE_DATE >= TO_DATE('" + queryParam.get("beginDate")+ " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" and T1.CREATE_DATE <= TO_DATE('" + queryParam.get("endDate")+ " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
		}
		sql.append(" ) T5 LEFT JOIN TT_VS_SALES_REPORT T6 ON T5.VEHICLE_ID = T6.VEHICLE_ID) TT1 \n");

		sql.append(" LEFT JOIN TT_VS_CUSTOMER TT2  ON TT1.CTM_ID = TT2.CTM_ID  WHERE 0 = 0  \n");
		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			sql.append("      AND  tt1.DEALER_ID in ("+  getDealersByArea(loginInfo.getOrgId().toString()) + ")\n");
		}
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and M.BRAND_CODE  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and M.SERIES_NAME  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and M.GROUP_NAME  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and M.MODEL_YEAR  = '"+queryParam.get("modelYear")+"' \n");
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and M.COLOR_NAME  = '"+queryParam.get("colorName")+"' \n");
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and M.TRIM_NAME  = '"+queryParam.get("trimName")+"' \n");
		}
		//vin
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = queryParam.get("vin");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append(getVinsAuto(vin, "tt1"));
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("   AND TT1.DEALER_CODE = ? \n");
			params.add(queryParam.get("dealerCode"));
		}
		//开票状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("reportStatus"))){
			sql.append("   AND TT1.REPORT_STATUS = ? \n");
			params.add(queryParam.get("reportStatus"));
		}
		//零售上报类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("reportType"))){
			sql.append("   AND TT1.REPORT_TYPE = ? \n");
			params.add(queryParam.get("reportType"));
		}
		logger.debug(sql.toString() +"   "+params.toString());
		return sql.toString();
	}

	/**
	 * 根据ID获取详细信息
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDetail(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		TtVsNvdrPO po = TtVsNvdrPO.findById(id);
		if(po!=null){
			map = po.toMap();
			Timestamp aaa = (Timestamp) map.get("delivery_date");
			String deliveryDate = String.valueOf(aaa);
			Timestamp bbb = (Timestamp) map.get("registration_date");
			String registrationDate = String.valueOf(bbb);
			map.put("DELIVERY_DATE", deliveryDate.substring(0,deliveryDate.length()-2));
			map.put("REGISTRATION_DATE", registrationDate.substring(0,registrationDate.length()-2));
			TmDealerPO dea = TmDealerPO.findFirst(" DEALER_ID = ? ", po.getLong("BUSINESS_ID"));
			if(null!=dea){
				map2 = dea.toMap();
			}
			map.putAll(map2);
		}
		return map;
	}
	
	/**
	 * 更新零售表交车状态
	 * @param nvdrIds
	 * @param loginInfo
	 */
	public void updateTtVsNvdr(String nvdrIds,LoginInfoDto loginInfo){
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE TT_VS_NVDR TVN  \n");
		sql.append("   SET NVDR_STATUS = ? , \n");
		queryParam.add(OemDictCodeConstants.VEHICLE_RETAIL_STATUS_03);
		sql.append("       UPDATE_BY =?, \n");
		queryParam.add(loginInfo.getUserId());
		sql.append("       UPDATE_DATE = NOW() \n");
		sql.append(" WHERE NVDR_ID IN (" + nvdrIds + ")");
		logger.debug("更新零售表交车状态SQL :"+sql.toString() + " " +  queryParam.toString());
		OemDAOUtil.execBatchPreparement(sql.toString(), queryParam);
	}


	/**
	 * 进口车零售接口表写入数据
	 * @param nvdrIds
	 * @return
	 */
	public void insertTiVehicleRetail(String nvdrIds,LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO TI_VEHICLE_RETAIL (VIN, DEALER_CODE, VEHICLE_USAGE, RETAIL_DATE, IS_SCAN, CREATE_BY, CREATE_DATE)  \n");
		sql.append("SELECT N.VIN,  \n");
		sql.append("       (SELECT DEALER_CODE FROM TM_DEALER WHERE DEALER_ID = V.DEALER_ID) AS DEALER_CODE,  \n");
		sql.append("       IFNULL((SELECT RELATION_CODE FROM TC_RELATION WHERE CHAR(CODE_ID) = V.VEHICLE_USAGE), 68) AS VEHICLE_USAGE,   \n");
		sql.append("       CASE WHEN N.REPORT_TYPE = 20851002 THEN (SELECT MAX(SR.SALES_DATE) FROM TT_VS_SALES_REPORT SR WHERE SR.VEHICLE_ID = V.VEHICLE_ID) \n");
		sql.append("            WHEN N.REPORT_TYPE = 20851001 THEN N.CREATE_DATE END AS SALES_DATE,  \n");
		sql.append("       '0', ?, NOW()  \n");
		queryParam.add(loginInfo.getUserId());
		sql.append("  FROM TT_VS_NVDR N, TM_VEHICLE_DEC V, ("+getVwMaterialSql()+") M \n");
		sql.append(" WHERE N.VIN = V.VIN \n");
		sql.append("   AND V.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append("   AND M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_IMPORT+ "' \n");
		sql.append("   AND N.NVDR_ID IN (" + nvdrIds + ")  \n");
		logger.debug("进口车零售接口表写入数据SQL :"+sql.toString() + " " +  queryParam.toString());
		OemDAOUtil.execBatchPreparement(sql.toString(), queryParam);
	}


	/**
	 * 国产车零售接口表写入
	 * @param nvdrIds
	 * @param loginInfo
	 * @return
	 */
	public void insertTiK4VsNvdr(String nvdrIds,LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO TI_K4_VS_NVDR (ACTION_CODE,ACTION_DATE,ACTION_TIME,INSPECTION_DATE,VIN,DEALER_CODE,VEHICLE_USE, \n");
		sql.append("   SALE_DATE,REGISTRATION_NUMBER,REGISTRATION_DATE,CTM_TYPE,CTM_NAME,COMPANY_NAME,FIRST_NAME,SECOND_NAME, \n");
		sql.append("   TEL_NUMBER1,TEL_NUMBER2,MOBILE_PHONE,FAX_NUMBER,E_MAIL,ADDRESS,COUNTRY,PROVINCE,CITY,POSTAL_CODE,BIRTHDAY, \n");
		sql.append("   FLG,ROW_ID,IS_RESULT,IS_MESSAGE,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE,IS_DEL) \n");
		sql.append("SELECT 'ZRGS' AS ACTION_CODE, -- 交易代码 \n");
		sql.append("       DATE_FORMAT(NOW(), '%Y-%m-%d') AS ACTION_DATE, -- 交易日期 \n");
		sql.append("       DATE_FORMAT(NOW(), '%H:%i:%s') AS ACTION_TIME, -- 交易时间 \n");
		sql.append("       '' AS INSPECTION_DATE, -- 验收日期 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       IFNULL(CM.SAP_CODE, '68') AS VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       DATE_FORMAT(N.CREATE_DATE, '%Y-%m-%d') AS SALES_DATE, -- 零售日期  \n");
		sql.append("       N.REGISTRATION_NUMBER, -- 登记号码 \n");
		sql.append("       DATE_FORMAT(N.REGISTRATION_DATE, '%Y-%m-%d') AS REGISTRATION_DATE, -- 登记日期  \n");
		sql.append("       C.CTM_TYPE, -- 个人 OR 组织 \n");
		sql.append("       C.CTM_NAME, -- 抬头 \n");
		sql.append("       C.COMPANY_NAME, -- 公司名称 \n");
		sql.append("       '' AS FIRST_NAME, -- 姓 \n");
		sql.append("       '' AS SECOND_NAME, -- 名 \n");
		sql.append("       N.PRIMARY_PHONE AS TEL_NUMBER1, -- 联系电话1 \n");
		sql.append("       N.BUSINESS_PHONE AS TEL_NUMBER2, -- 联系电话2 \n");
		sql.append("       N.MOBILE_PHONE, -- 移动电话 \n");
		sql.append("       N.FAX_NUMBER, -- 传真号码 \n");
		sql.append("       N.EMAIL AS E_MAIL, -- 邮箱地址 \n");
		sql.append("       N.STREET_ADDRESS AS ADDRESS, -- 联系地址 \n");
		sql.append("       N.COUNTRY, -- 国家 \n");
		sql.append("       N.PROVINCE, -- 省份 \n");
		sql.append("       N.CITY, -- 城市 \n");
		sql.append("       N.POSTAL_CODE, -- 邮编 \n");
		sql.append("       DATE_FORMAT(C.BIRTHDAY, '%Y-%m-%d') AS BIRTHDAY, -- 生日 \n");
		sql.append("       '' AS FLG, -- 标识 \n");
		sql.append("       'S0009' ||  YEAR(NOW()) || MONTH(NOW()) || (SELECT MAX(IF_ID)+1 FROM TI_K4_VS_NVDR)  AS ROW_ID, \n");
		sql.append("       '" + OemDictCodeConstants.IF_TYPE_NO+ "' AS IS_RESULT, -- 是否上报 \n");
		sql.append("       '' AS IS_MESSAGE, -- 消息 \n");
		sql.append("       ? AS CREATE_BY, -- 创建人ID \n");
		queryParam.add(loginInfo.getUserId());
		sql.append("       NOW() AS CREATE_DATE, -- 创建日期 \n");
		sql.append("       NULL AS UPDATE_BY, \n");
		sql.append("       NULL AS UPDATE_DATE, \n");
		sql.append("       '" + OemDictCodeConstants.IS_DEL_00 + "' AS IS_DEL \n");
		sql.append("  FROM TT_VS_NVDR N \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VIN = N.VIN \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append("  LEFT JOIN TC_CODE_K4_MAPPING CM ON CM.CODE_ID = V.VEHICLE_USAGE \n");
		sql.append("  LEFT JOIN TT_VS_SALES_REPORT SR ON SR.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append("  LEFT JOIN TT_VS_CUSTOMER C ON C.CTM_ID = SR.CTM_ID  \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC+ "' \n");
		sql.append("   AND N.NVDR_ID IN (" + nvdrIds + ") \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), queryParam);
	}


	/**
	 * 查询未验收的车辆
	 * @param nvdrId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> lackInspectionVinQuery(String nvdrId) {
		String sql = lackInspectionVinQuerySql(nvdrId);
		List<Map> list = OemDAOUtil.findAll(sql, null);
		return list;
	}

	/**
	 * 未验收车辆查询SQL
	 * @param nvdrIds
	 * @return
	 */
	private String lackInspectionVinQuerySql(String nvdrIds) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       T.VIN, -- 车架号 \n");
		sql.append("       D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       M.GROUP_TYPE -- 业务类别 \n");
		sql.append("  FROM TT_VS_NVDR T \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VIN = T.VIN \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE T.NVDR_ID IN (" + nvdrIds + ") \n");
		sql.append("   AND (T.VIN NOT IN \n");
		sql.append("        (SELECT VIN FROM TI_VEHICLE_INSPECTION WHERE VIN IN \n");
		sql.append("         (SELECT VIN FROM TT_VS_NVDR WHERE NVDR_ID IN ("+ nvdrIds + "))) OR \n");
		sql.append("        T.VIN NOT IN \n");
		sql.append("        (SELECT VIN FROM TI_K4_VS_NVDR WHERE ACTION_CODE = 'ZPOD' AND VIN IN \n");
		sql.append("         (SELECT VIN FROM TT_VS_NVDR WHERE NVDR_ID IN ("+ nvdrIds + ")))) \n");
		logger.debug("未验收车辆查询SQL :"+sql.toString());
		return sql.toString();
	}

	/**
	 * 插入国产车验收接口表
	 * @param arriveDate
	 * @param dealerCode
	 * @param userId
	 * @param vin
	 */
	public void insertTiK4VsNvdr2(String arriveDate, String dealerCode, Long userId, String vin) {
		String sql = insertTiK4VsNvdr2Sql(arriveDate,dealerCode,userId,vin);
		OemDAOUtil.execBatchPreparement(sql, null);
		
	}
	/**
	 * 插入国产车验收接口表SQL
	 * @param arriveDate
	 * @param dealerCode
	 * @param userId
	 * @param vin
	 * @return
	 */
	private String insertTiK4VsNvdr2Sql(String arriveDate, String dealerCode, Long userId, String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("INSERT INTO TI_K4_VS_NVDR \n");
		sql.append("  (ACTION_CODE, ACTION_DATE, ACTION_TIME, INSPECTION_DATE, VIN, DEALER_CODE, VEHICLE_USE, ROW_ID, IS_RESULT, CREATE_BY, CREATE_DATE, IS_DEL) \n");
		sql.append("SELECT 'ZPOD', -- 交易代码 \n");
		sql.append("       DATE_FORMAT(CURRENT TIMESTAMP, '%Y-%m-%d') AS ACTION_DATE, -- 交易日期 \n");
		sql.append("       DATE_FORMAT(CURRENT TIMESTAMP, '%H:%i:%s') AS ACTION_TIME, -- 交易时间 \n");
		sql.append("       '" + arriveDate + "' AS INSPECTION_DATE, -- 验收日期 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       '" + dealerCode + "' AS DEALER_CODE, -- 经销商代码 \n");
		sql.append("       CASE WHEN IFNULL(R.RELATION_CODE, '-1') = '-1' THEN '68' ELSE 'R.RELATION_CODE' END AS VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       'S0008' ||  YEAR(NOW()) || MONTH(NOW()) || (SELECT MAX(IF_ID)+1 FROM TI_K4_VS_NVDR)  AS ROW_ID, \n");
		sql.append("       '" + OemDictCodeConstants.IF_TYPE_NO + "' AS IS_RESULT, -- 是否上报 \n");
		sql.append("       '" + userId + "' AS CREATE_BY, -- 创建人ID \n");
		sql.append("       NOW() AS CREATE_DATE, -- 创建日期 \n");
		sql.append("       '" + OemDictCodeConstants.IS_DEL_00 + "' AS IS_DEL \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN ("+ getVwMaterialSql() +") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TC_RELATION R ON CHAR(R.CODE_ID) = V.VEHICLE_USAGE \n");
		sql.append(" WHERE M.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND V.VIN = '" + vin + "' \n");
		logger.debug("插入国产车验收接口表SQL :"+sql.toString());
		return sql.toString();
	}

}
