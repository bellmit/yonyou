package com.yonyou.dms.vehicle.dao.realitySales.retailReportQuery;

import java.util.ArrayList;
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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class RetailReportQueryDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 下载
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryRetailReportQueryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		 String sql = getQuerySql(queryParam,params,loginInfo);
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
	     return resultList;
	}

	/**
	 * 查询SQL拼接
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params,LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.BIG_AREA_NAME, --   大区 \n");
		sql.append("       T.SMALL_AREA_NAME, --  小区 \n");
		sql.append("       T.SWT_CODE, --  SAP代码 \n");
		sql.append("       T.DEALER_CODE, --  经销商代码 \n");
		sql.append("       T.DEALER_NAME, --  经销商名称 \n");
		sql.append("       C.CTM_ID, \n");
		sql.append("       C.CTM_NAME, --  客户姓名/公司名称 \n");
		sql.append("       C.MAIN_PHONE, --  联系电话 \n");
		sql.append("       C.CTM_TYPE, --  客户类型 \n");
		sql.append("       M.BRAND_CODE, --  品牌 \n");
		sql.append("       M.SERIES_NAME, --  车系 \n");
		sql.append("       M.MODEL_NAME, --  车型 \n");
		sql.append("       M.GROUP_NAME, --  车款 \n");
		sql.append("       M.MODEL_YEAR, --  年款 \n");
		sql.append("       M.COLOR_NAME, --  颜色 \n");
		sql.append("       M.TRIM_NAME, --  内饰 \n");
		sql.append("       M.GROUP_TYPE, --  进口车或国产车\n");
		sql.append("       V.VIN, --  底盘号 \n");
		sql.append("       V.VIN AS VINS, --  底盘号 \n");
		sql.append("       RR.REMARK, --  车辆分配备注 \n");
		sql.append("       V.VEHICLE_USAGE, --  车辆用途 \n");
		sql.append("       N.STATUS, --  开票状态 \n");
		sql.append("       date_format(SR.INVOICE_DATE,'%Y-%m-%d') AS INVOICE_DATE, --  开票日期 \n");
		sql.append("       N.NVDR_STATUS, --  NVDR状态 \n");
		sql.append("       date_format(N.CREATE_DATE,'%Y-%m-%d %H:%i:%s') AS CREATE_DATE, --  零售上报日期 \n");
		sql.append("       date_format(N.UPDATE_DATE,'%Y-%m-%d') AS NVDR_DATE, --  零售上报审批日期 \n");
		sql.append("       date_format(CV.PAYMENG_DATE,'%Y-%m-%d') AS PAYMENG_DATE, --  批售日期 \n");
		sql.append("       N.PATCH_UPLOAD_USER_ID, --  补传人ID \n");
		sql.append("       N.PATCH_UPLOAD_USER_NAME, --  补传人 \n");
		sql.append("       date_format(N.PATCH_UPLOAD_DATE,'%Y-%m-%d') AS PATCH_UPLOAD_DATE, --  补传日期 \n");
//		date_format(CV.PAYMENG_DATE,'%Y-%m-%d')
		sql.append("       N.NVDR_ID, --  零售车辆ID \n");
		sql.append("       N.REPORT_TYPE, --  零售上报类型 \n");
		sql.append("       IFNULL(RR.OTHER_REMARK, '') AS OTHER_REMARK, \n");
		sql.append("	   TVO.ORDER_ID,");
		sql.append("	   TVO.ORDER_TYPE");
		sql.append("  FROM TT_VS_NVDR N \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VIN = N.VIN \n");
		sql.append(" INNER JOIN ("+ getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TT_VS_SALES_REPORT SR ON SR.VEHICLE_ID = V.VEHICLE_ID AND SR.STATUS = 10011001 \n");
		sql.append("  LEFT JOIN TT_VS_CUSTOMER C ON C.CTM_ID = SR.CTM_ID \n");
		sql.append("  LEFT JOIN TT_RESOURCE_REMARK RR ON RR.VIN = V.VIN \n");
		sql.append("  LEFT JOIN TM_CTCAI_VEHICLE CV ON CV.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append("  LEFT JOIN (SELECT O2.ORG_ID AS ORG_ID2, --  大区ID \n");
		sql.append("             D.DEALER_ID, --  经销商ID \n");
		sql.append("             D.DEALER_CODE, --  经销商CODE \n");
		sql.append("             D.DEALER_SHORTNAME AS DEALER_NAME, --  经销商简称 \n");
		sql.append("             O2.ORG_DESC AS BIG_AREA_NAME, --  大区名称 \n");
		sql.append("             O3.ORG_DESC AS SMALL_AREA_NAME, --  小区名称 \n");
		sql.append("             C.SWT_CODE --  SAP代码 \n");
		sql.append("        FROM TM_ORG O2, TM_ORG O3, TM_DEALER_ORG_RELATION DOR, TM_DEALER D, TM_COMPANY C \n");
		sql.append("       WHERE O2.ORG_ID = O3.PARENT_ORG_ID  \n");
		sql.append("         AND O3.ORG_ID = DOR.ORG_ID  \n");
		sql.append("         AND DOR.DEALER_ID = D.DEALER_ID  \n");
		sql.append("         AND D.COMPANY_ID = C.COMPANY_ID \n");
		sql.append("         AND O3.ORG_LEVEL = 3 \n");
		sql.append("         AND O2.ORG_LEVEL = 2) T ON T.DEALER_ID = N.BUSINESS_ID \n");
		sql.append("	LEFT JOIN (SELECT ORDER_ID,ORDER_TYPE,VIN FROM  TT_VS_ORDER WHERE ORDER_STATUS NOT IN (20071008,20071009)) TVO ON N.VIN = TVO.VIN \n");
		sql.append(" WHERE 1 = 1 AND N.IS_DEL = 0 \n");
		sql.append(" "+ control(" M.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs()) + " \n");
		if (!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			sql.append("   AND T.DEALER_ID in ? \n"); // ORDER_TYPE_DOMESTIC_03
			params.add(getDealersByArea(loginInfo.getOrgId().toString()));
		}
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
		// 零售上报类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("reportType"))) {
			sql.append("   AND N.REPORT_TYPE = ? \n");
			params.add(queryParam.get("reportType"));
		}
		// NVDR状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("nvdrStatus"))) {
			sql.append("   AND N.NVDR_STATUS = ? \n");
			params.add(queryParam.get("nvdrStatus"));
		}
		// 开票状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("reportStatus"))) {
			sql.append("   AND N.STATUS = ? \n");
			params.add(queryParam.get("reportStatus"));
		}
		// 经销商
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append("   AND T.DEALER_CODE IN ('"+queryParam.get("dealerCode").replaceAll(",", "','")+"') \n");
		}
		// 底盘号(VIN)
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vin = "";
			vin = queryParam.get("vin").replaceAll("\\^", "\n");
			vin = queryParam.get("vin").replaceAll("\\,", "\n");
			sql.append( getVins(vin, "V") );
		}
		// 零售上报日期 BEGIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("   AND DATE_FORMAT(N.CREATE_DATE,'%Y-%m-%d') >= '"+ queryParam.get("beginDate") +"' \n");
			sql.append("   AND DATE_FORMAT(N.CREATE_DATE,'%Y-%m-%d') <= '"+ queryParam.get("endDate") +"' \n");
		}
		// 零售上报审批日期 BEGIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate2"))) {
			sql.append("   AND DATE_FORMAT(N.UPDATE_DATE,'%Y-%m-%d') >= '"+ queryParam.get("beginDate2") +"' \n");
			sql.append("   AND DATE_FORMAT(N.UPDATE_DATE,'%Y-%m-%d') <= '"+ queryParam.get("endDate2") +"' \n");
		}
		
		logger.debug("零售上报查询生成SQL："+sql.toString() + params.toString()+"--");
		return sql.toString();
	}

	/**
	 * 根据ID获取详细信息
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryDetail(Long nvdrId, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map1 = new HashMap<String, Object>();
		//获取orderId
		String sql1 = getOrderMassgeSql(nvdrId);
		map1 = OemDAOUtil.findFirst(sql1, null);
		Long orderId = 0L;
		Integer orderType = 0;
		if(map1.get("ORDER_ID") != null){
			orderId = Long.parseLong(map1.get("ORDER_ID").toString());
			orderType = Integer.parseInt(map1.get("ORDER_TYPE").toString());
		}
//		Long orderId = Long.parseLong(map1.get("ORDER_ID").toString());
//		Integer orderType = Integer.parseInt(map1.get("ORDER_TYPE").toString());
		//车辆信息和客户信息
		String sql = getDetailQuerySql(nvdrId,orderId,loginInfo,orderType);
		map = OemDAOUtil.findFirst(sql, null);
			
		
		return map;
	}

	/**
	 * 直销订单 详细查询SQL 拼接
	 * @param id1 ORDER_ID
	 * @param loginInfo
	 * @return
	 */
	private String getDetailQuerySql(Long nvdrId, Long orderId ,LoginInfoDto loginInfo,int orderType) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVO.VIN, -- 车架号 \n");
		sql.append("       TVN.SALE_TYPE, -- 销售类型 \n");
//		date_format(TVSR.INVOICE_DATE,'%Y-%m-%d %H:%i:%s')
		sql.append("       IFNULL(date_format(TVO.INVOICE_DATE,'%Y-%m-%d'),date_format(TVSR.INVOICE_DATE,'%Y-%m-%d')) INVOICE_DATE, --  开票日期 \n");
		sql.append("       CASE WHEN TVN.ODOMETER IS NULL THEN TBCDP.MILEAGE ELSE TVN.ODOMETER	END AS MILEAGE,-- 交付时公里数 \n");
//						   CASE WHEN I.DAMAGE_FLAG = '1' THEN '是' ELSE '否' END AS DAMAGE_FLAG
		sql.append("       TV.QUALIFIED_NO, -- 合格证号 \n");
		sql.append("       TV.ENGINE_NO,-- 发动机号 \n");
		sql.append("       TVO.VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       VM.BRAND_NAME,-- 品牌 \n");
		sql.append("       VM.SERIES_NAME, -- 车系 \n");
		sql.append("       VM.GROUP_NAME, -- 车型 \n");
		sql.append("       VM.COLOR_NAME,-- 颜色 \n");
		sql.append("       VM.TRIM_NAME, -- 内饰 \n");
		sql.append("       VM.MODEL_YEAR, -- 年款 \n");
		sql.append("       VM.MODEL_CODE,-- CPOS \n");
		sql.append("       FF.FILEURL, -- 发票附件上传 \n");
		sql.append("       TD.DEALER_CODE,-- 经销商代码 \n");
		sql.append("       TBCD.CUSTOMER_NO,-- 客户编号 \n");
		sql.append("       TBCD.CUSTOMER_NAME,-- 客户名称 \n");
		sql.append("       TBCD.CUSTOMER_TYPE,-- 客户类型 \n");
		sql.append("       TBCD.LINKMAN_NAME,-- 联系人 \n");
		sql.append("       TBCD.LINKMAN_TEL,-- 联系方式 \n");
		sql.append("       TBCD.LINKMAN_SEX,-- 联系人性别 \n");
		sql.append("       TBCD.LINKMAN_ADDR, -- 联系人地址 \n");
		sql.append("       TBCD.ID_TYPE,-- 证件类型 \n");
		sql.append("       TBCD.ID_NO,-- 证件号码 \n");
		sql.append("       TBCD.PAYMENT_BANK,-- 打款银行 \n");
		sql.append("       TBCD.BANK_NO,-- 银行账号 \n");
		sql.append("       TBCD.REAMK, -- 备注 \n");
		sql.append("       TVO.INVOICE_TYPE -- 开票类型 \n");
		sql.append("  FROM TT_VS_ORDER TVO \n");
		sql.append("  LEFT JOIN TT_VS_NVDR TVN ON TVO.VIN = TVN.VIN \n");
		sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT_PDICHECK TBCDP ON TBCDP.ORDER_ID=TVO.ORDER_ID \n");
		sql.append("  LEFT JOIN TM_DEALER TD ON TD.DEALER_ID = TVO.DEALER_ID \n");
		sql.append("  LEFT JOIN TM_VEHICLE_DEC TV ON TV.VIN = TVO.VIN \n");
		sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT_ORDER TBCDO ON TBCDO.ORDER_ID = TVO.ORDER_ID \n");
		sql.append("  LEFT JOIN TT_BIG_CUSTOMER_DIRECT TBCD ON TBCDO.DIRECT_ID = TBCD.DIRECT_ID \n");
		sql.append("  LEFT JOIN ("+ getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TVO.MATERAIL_ID \n");
		sql.append("  LEFT JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID \n");
		sql.append("  LEFT JOIN FS_FILEUPLOAD FF ON TVSR.REPORT_ID = FF.YWZJ \n");
		if(OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 == orderType ){
			sql.append("  where tvo.ORDER_ID=" + orderId +" \n");
		}else{
			sql.append("  where TVN.NVDR_ID=" + nvdrId +" \n");
		}
		logger.debug("详细信息查询生成SQL："+sql.toString());
		
		return sql.toString();
	}
	
	/**----------------------------------经销商端-----------------------------------------------------------**/

	/**
	 * 零售上报查询查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 零售上报查询查询SQL组装
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getDealerQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TT1.*,TT2.CTM_NAME,TT2.CTM_TYPE,TT2.MAIN_PHONE FROM ( \n ");
		sql.append("	SELECT T5.*,T6.CTM_ID,DATE_FORMAT(T6.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE FROM( \n");
		sql.append("  		SELECT DATE_FORMAT(T1.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,T1.NVDR_STATUS,T1.STATUS,T1.NVDR_ID,T2.DEALER_CODE,T2.DEALER_SHORTNAME DEALER_NAME,T3.SERIES_CODE CODE2,  \n");
		sql.append("      			T3.MODEL_CODE CODE3,T3.MODEL_NAME,T3.GROUP_NAME,T3.COLOR_NAME,T4.VIN,T4.VIN AS VINS,T4.VEHICLE_ID ,T4.DEALER_ID,DATE_FORMAT(t1.UPDATE_DATE,'%Y-%m-%d') NVDR_DATE \n");
		sql.append("       		FROM TT_VS_NVDR T1,TM_DEALER T2 , ("+ getVwMaterialSql()+") T3 , TM_VEHICLE_DEC T4  \n");
		sql.append("			WHERE T1.VIN = T4.VIN AND T3.MATERIAL_ID = T4.MATERIAL_ID   \n");
		sql.append(" "+ control(" T3.SERIES_ID ", loginInfo.getDealerSeriesIDs(),loginInfo.getPoseSeriesIDs()) + " \n");
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append("   AND T3.BRAND_ID = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append("   AND T3.SERIES_ID = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append("   AND T3.GROUP_ID = '" + queryParam.get("groupName")+ "' \n");
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append("   AND T3.MODEL_YEAR = ? \n");
			params.add(queryParam.get("modelYear"));
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append("   AND T3.COLOR_CODE = '"+queryParam.get("colorName")+"' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append("   AND T3.TRIM_CODE = '"+queryParam.get("trimName")+"' \n");
		}
		//开票状态
		if (!"".equals(CommonUtils.checkNull(queryParam.get("reportStatus")))) {
			sql.append(" and T1.STATUS='").append(queryParam.get("reportStatus")).append("'");
		}
		//客户名称
		if (!"".equals(CommonUtils.checkNull(queryParam.get("ctmName")))) {
			sql.append(" and TT2.CTM_NAME='").append(queryParam.get("ctmName")).append("'");
		}
		//上报日期
		if (!"".equals(CommonUtils.checkNull(queryParam.get("beginDate")))) {
			sql.append(" and T1.CREATE_DATE > DATE_FORMAT('" + queryParam.get("beginDate")+ " 00:00:00','%Y-%m-%d %H:%i:%s')");
			sql.append(" and T1.CREATE_DATE < DATE_FORMAT('" + queryParam.get("endDate")+ " 23:59:59','%Y-%m-%d %H:%i:%s')");
		}
		//开票日期
		if (!"".equals(CommonUtils.checkNull(queryParam.get("beginDate2")))) {
			sql.append(" and T1.UPDATE_DATE > DATE_FORMAT('" + queryParam.get("beginDate2")+ " 00:00:00','%Y-%m-%d %H:%i:%s')");
			sql.append(" and T1.UPDATE_DATE < DATE_FORMAT('" + queryParam.get("endDate2")+ " 23:59:59','%Y-%m-%d %H:%i:%s') ");
		}
		if (!"".equals(CommonUtils.checkNull(queryParam.get("groupCode")))) {
			String groupCode = queryParam.get("groupCode");
			groupCode = groupCode.replaceAll(",", "','");
			sql.append(" and T3.CODE3 in('").append(queryParam.get("groupCode")).append("')");
		}
		if (!"".equals(CommonUtils.checkNull(loginInfo.getDealerCode()))) {
			String dealerCode = loginInfo.getDealerCode().replaceAll(",", "','");
			sql.append(" and T2.DEALER_CODE in('").append(dealerCode).append("')");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))&& !"".equals(queryParam.get("vin").replaceAll(" ", ""))) {
			String vin = queryParam.get("vin");
			String tmp = vin.replaceAll("\\^\\^", "\n");
			if (tmp.indexOf("\n") == -1)
				sql.append(" " + getVins(tmp, "T4"));
			else
				sql.append(" " + getVins(tmp, "T4"));
		}
		if (!"".equals(CommonUtils.checkNull(queryParam.get("reportStatus")))) {
			sql.append(" and T1.STATUS='").append(queryParam.get("reportStatus")).append("'");
		}
		sql.append("       	  AND T1.BUSINESS_ID = T2.DEALER_ID ) T5 LEFT JOIN TT_VS_SALES_REPORT T6  \n");
		sql.append("  ON T5.VEHICLE_ID = T6.VEHICLE_ID) TT1 LEFT JOIN TT_VS_CUSTOMER TT2 ON TT1.CTM_ID = TT2.CTM_ID  WHERE 0 = 0 \n");
		
		logger.debug("(经销商端)零售上报查询生成SQL："+sql.toString() + params.toString()+"--");
		return sql.toString();
	}

	/**
	 * 详细信息查询
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t6.CTM_ID FROM TT_VS_NVDR t1 \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC t4 ON T1.VIN = T4.VIN \n");
		sql.append(" LEFT JOIN TT_VS_SALES_REPORT t6 ON T4.VEHICLE_ID = T6.VEHICLE_ID \n");
		sql.append(" WHERE T1.VIN = '"+id+"'");
		map2 = OemDAOUtil.findFirst(sql.toString(),null);
		Long ctmId = (Long) map2.get("CTM_ID");
		CustcomVehicleQueryDao customerVehicleQueryDao= new CustcomVehicleQueryDao();
		try {
			Map<String, Object> orderInfos = customerVehicleQueryDao.getDetailCustomerVehicleQueryInfo(id,ctmId);
			if(orderInfos==null||orderInfos.size()<1){
//				request.setAttribute("orderId", orderInfo.get("ORDER_ID"));
//				act.setOutData("orderInfo", orderInfo);
////				act.setOutData("poseIds", poseIds);
//				act.setForword(SALE_ORDER_DETAIL_NO_INFO);
			}else{
				List<Map> vehList = (List<Map>) orderInfos.get("vehList");
				List<Map> cusList = (List<Map>) orderInfos.get("cusList");
				List<Map> lxrList = (List<Map>) orderInfos.get("lxrList");
				List<Map> secList = (List<Map>) orderInfos.get("secList");
				List<Map> detList = (List<Map>) orderInfos.get("detList");
				
				Map<String, Object> vehMap = new HashMap<String, Object>();
				Map<String, Object> cusMap = new HashMap<String, Object>();
				Map<String, Object> lxrMap = new HashMap<String, Object>();
				Map<String, Object> secMap = new HashMap<String, Object>();
				Map<String, Object> detMap = new HashMap<String, Object>();
				
				if(vehList.size()>0) vehMap = (Map<String, Object>)vehList.get(0);
				if(cusList.size()>0) cusMap = (Map<String, Object>)cusList.get(0);
				if(lxrList.size()>0) lxrMap = (Map<String, Object>)lxrList.get(0);
				if(secList.size()>0) secMap = (Map<String, Object>)secList.get(0);
				if(detList.size()>0) detMap = (Map<String, Object>)detList.get(0);
				//客户类型
				if(cusMap.get("CTM_TYPE")!=null ){
					String ctmTypeDesc = customerVehicleQueryDao.getDesc(cusMap.get("CTM_TYPE")+"");
					cusMap.put("CTM_TYPE_DESC", ctmTypeDesc);
				}
				//证件类型
				if(cusMap.get("CARD_TYPE")!=null ){
					String detDesc = customerVehicleQueryDao.getDesc(cusMap.get("CARD_TYPE")+"");
					cusMap.put("CARD_TYPE_DESC", detDesc);
				}
				//客户性别
				if(cusMap.get("SEX")!=null ){
					String sexDesc = customerVehicleQueryDao.getDesc(cusMap.get("SEX")+"");
					cusMap.put("SEX_DESC", sexDesc);
				}
				//婚姻状况
				if(detMap.get("IS_MARRIED")!=null ){
					String detDesc = customerVehicleQueryDao.getDesc(detMap.get("IS_MARRIED")+"");
					detMap.put("IS_MARRIED_DESC", detDesc);
				}				
				//学历
				if(detMap.get("EDUCATION")!=null ){
					String detDesc = customerVehicleQueryDao.getDesc(detMap.get("EDUCATION")+"");
					detMap.put("EDUCATION_DESC", detDesc);
				}
				//所在行业
				if(detMap.get("INDUSTRY")!=null ){
					String detDesc = customerVehicleQueryDao.getDesc(detMap.get("INDUSTRY")+"");
					detMap.put("INDUSTRY_DESC", detDesc);
				}
				//家庭月收入
				if(detMap.get("INCOME")!=null ){
					String detDesc = customerVehicleQueryDao.getDesc(detMap.get("INCOME")+"");
					detMap.put("INCOME_DESC", detDesc);
				}
				//所在省份
				if(detMap.get("PROVINCE")!=null ){
					String detDesc = customerVehicleQueryDao.getTrDesc(detMap.get("PROVINCE")+"");
					detMap.put("PROVINCE_DESC", detDesc);
				}
				//所在城市
				if(detMap.get("CITY")!=null ){
					String detDesc = customerVehicleQueryDao.getTrDesc(detMap.get("CITY")+"");
					detMap.put("CITY_DESC", detDesc);
				}
				//使用性质
				if(secMap.get("USE_TYPE")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("USE_TYPE")+"");
					secMap.put("USE_TYPE_DESC", secDesc);
				}
				//交强险信息
				if(secMap.get("TRAFFIC_INSURE_INFO")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("TRAFFIC_INSURE_INFO")+"");
					secMap.put("TRAFFIC_INSURE_INFO_DESC", secDesc);
				}
				//机动车行驶证
				if(secMap.get("DRIVING_LICENSE")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("DRIVING_LICENSE")+"");
					secMap.put("DRIVING_LICENSE_DESC", secDesc);
				}
				//机动车行驶证
				if(secMap.get("DRIVING_LICENSE")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("DRIVING_LICENSE")+"");
					secMap.put("DRIVING_LICENSE_DESC", secDesc);
				}
				//登记证书
				if(secMap.get("REGISTRY")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("REGISTRY")+"");
					secMap.put("REGISTRY_DESC", secDesc);
				}
				//来历凭证
				if(secMap.get("ORIGIN_CERTIFICATE")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("ORIGIN_CERTIFICATE")+"");
					secMap.put("ORIGIN_CERTIFICATE_DESC", secDesc);
				}
				//购置锐凭证
				if(secMap.get("PURCHASE_TAX")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("PURCHASE_TAX")+"");
					secMap.put("PURCHASE_TAX_DESC", secDesc);
				}
				//车船使用锐
				if(secMap.get("VEHICLE_AND_VESSEL_TAX")!=null ){
					String secDesc = customerVehicleQueryDao.getDesc(secMap.get("VEHICLE_AND_VESSEL_TAX")+"");
					secMap.put("VEHICLE_AND_VESSEL_TAX_DESC", secDesc);
				}
				
				map.putAll(vehMap);
				map.putAll(cusMap);
				map.putAll(lxrMap);
				map.putAll(secMap);
				map.putAll(detMap);
				
				return map;
				
			}
			
		} catch (Exception e) {
			ServiceBizException e1 = new ServiceBizException(e);
			logger.error(loginInfo.toString(),e1);
		}
		return map;	
	}
	
	public String getOrderMassgeSql(Long nvdrId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT TVO.ORDER_TYPE ,TVO.ORDER_ID \n");
		sql.append(" FROM TT_VS_NVDR N  \n");
		sql.append("LEFT JOIN (SELECT ORDER_ID,ORDER_TYPE,VIN FROM  TT_VS_ORDER WHERE ORDER_STATUS NOT IN (20071008,20071009)) TVO ON N.VIN = TVO.VIN  \n");
		sql.append("WHERE N.NVDR_ID = "+nvdrId);
		return sql.toString();
	}

	/**
	 * 下载(经销商)
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public List<Map> queryDealerRetailReportQueryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		 String sql = getDealerQuerySql(queryParam,params,loginInfo);
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
	     return resultList;
	}

}
