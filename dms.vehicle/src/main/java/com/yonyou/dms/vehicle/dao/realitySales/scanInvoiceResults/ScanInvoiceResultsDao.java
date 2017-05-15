package com.yonyou.dms.vehicle.dao.realitySales.scanInvoiceResults;

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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.realitySales.retailReportQuery.CustcomVehicleQueryDao;

@Repository
public class ScanInvoiceResultsDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 发票扫描结果查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getTotalQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 发票扫描结果查询/下载SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getTotalQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM ( \n ");
		sql.append(" SELECT T1.DEALER_CODE,   \n ");
		sql.append("        T1.SWT_CODE,      \n ");
		sql.append("        T1.DEALER_NAME,   \n ");
		sql.append("        T1.CTM_NAME,      \n ");
		sql.append("        T1.CTM_ID,        \n ");
		sql.append("        T1.CARD_NUM,      \n ");
		sql.append("        (CASE WHEN T1.CARD_NUM = SRI.CARD_NUM THEN '0' ELSE SRI.CARD_NUM END) IS_CARD_NUM,       \n ");
		sql.append("        (CASE WHEN INSTR(T3.VIN,SUBSTR(SRI.VIN,1,10)) <>0 OR        \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,2,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,3,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,4,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,5,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,6,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,7,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,8,10)) THEN '识别成功'         \n ");
		sql.append("        WHEN LENGTH(T3.VIN) > LENGTH(SRI.VIN) OR  LENGTH(T3.VIN) < 10         \n ");
		sql.append("        OR LENGTH(SRI.VIN) < 10 THEN '识别失败' ELSE '识别失败' END) IS_YN,          \n ");
		
		sql.append("        (SELECT COUNT(*) FROM FS_FILEUPLOAD F WHERE (F.YWZJ = T1.REPORT_ID OR F.REMARK_YWZJ = T1.REPORT_ID)) SCAN_NUM, \n");
		sql.append("        T1.MAIN_PHONE,        \n ");
		sql.append("        T1.CTM_TYPE,          \n ");
		sql.append("        T2.SERIES_CODE CODE2, \n ");
		sql.append("        T2.MODEL_CODE CODE3,  \n ");
		sql.append("        T2.GROUP_NAME,        \n ");
		sql.append("        T2.COLOR_NAME,        \n ");
		sql.append("        T3.VIN,               \n ");
		sql.append("        (CASE WHEN INSTR(T3.VIN,SUBSTR(SRI.VIN,1,10)) <>0 OR        \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,2,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,3,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,4,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,5,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,6,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,7,10)) <>0 OR         \n ");
		sql.append("        		  INSTR(T3.VIN,SUBSTR(SRI.VIN,8,10)) THEN '0'   \n ");
		sql.append("        WHEN LENGTH(T3.VIN) > LENGTH(SRI.VIN) OR  LENGTH(T3.VIN) < 10         \n ");
		sql.append("        OR LENGTH(SRI.VIN) < 10 THEN '1' ELSE '1' END) IS_VIN,          \n ");
		
		sql.append("        DATE_FORMAT(T1.SALES_DATE, '%Y-%m-%d') SALES_DATE1,             \n ");
		sql.append("        T1.INVOICE_DATE,                                              \n ");
		sql.append("        (CASE WHEN TO_DAYS(T1.INVOICE_DATE)- TO_DAYS(SRI.INVOICE_DATE)=0 THEN '0' ELSE DATE_FORMAT(SRI.INVOICE_DATE,'%Y-%m-%d') END) IS_INVOICE_DATE, \n");
		sql.append("        DATE_FORMAT(T1.SALES_DATE, '%Y-%m-%d') SALES_DATE2, \n");
		sql.append("        (CASE WHEN TO_DAYS(T1.SALES_DATE)- TO_DAYS(case when SRI.INVOICE_DATE is null then T1.INVOICE_DATE ELSE SRI.INVOICE_DATE END)<=3 THEN '否' ELSE '是' END) ONE_DAY_SCAN,  -- 72  3 天内扫描 \n ");
		sql.append("        (CASE WHEN TO_DAYS(T1.SALES_DATE)- TO_DAYS(case when SRI.INVOICE_DATE is null then T1.INVOICE_DATE ELSE SRI.INVOICE_DATE END)<=5 THEN '否' ELSE '是' END) TWO_DAY_SCAN,  -- 120 5天内扫描 \n ");
		sql.append("        T1.INVOICE_NO,         \n ");
		sql.append("        (CASE WHEN T1.INVOICE_NO = SRI.INVOICE_NO THEN '0' ELSE SRI.INVOICE_NO END) IS_INVOICE_NO, \n ");
		sql.append("        T1.FILEURL \n");
		sql.append("   FROM (SELECT AA3.DEALER_CODE,                        \n ");
		sql.append("                TC.SWT_CODE,                            \n ");
		sql.append("                AA3.DEALER_SHORTNAME DEALER_NAME,       \n ");
		sql.append("                AA1.CTM_NAME,                           \n ");
		sql.append("                AA1.CTM_ID,                             \n ");
		sql.append("                AA1.MAIN_PHONE,                         \n ");
		sql.append("                AA1.CTM_TYPE,                           \n ");
		sql.append("                AA2.SALES_DATE,                         \n ");
		sql.append("                AA2.VEHICLE_ID,                         \n ");
		sql.append("                AA2.PRICE,                              \n ");
		sql.append("                AA2.REPORT_ID,                          \n ");
		sql.append("                AA1.CARD_NUM,                           \n ");
		sql.append("                AA2.INVOICE_DATE,                       \n ");
		sql.append("                AA2.INVOICE_NO,FF.FILEURL               \n ");
		sql.append("           FROM TT_VS_CUSTOMER AA1,                     \n ");
		sql.append("                TT_VS_SALES_REPORT AA2,                 \n ");
		sql.append("                TM_DEALER AA3,                          \n ");
		sql.append("                TM_COMPANY TC,  FS_FILEUPLOAD FF        \n ");
		sql.append("          WHERE     AA2.STATUS = '10011001'             \n ");
		sql.append("                AND AA1.CTM_ID = AA2.CTM_ID             \n ");
		sql.append("                AND AA2.DEALER_ID = AA3.DEALER_ID       \n ");
		sql.append("                AND AA3.COMPANY_ID = TC.COMPANY_ID AND FF.YWZJ = AA2.REPORT_ID) T1, \n ");
		sql.append("        (" + getVwMaterialSql() + ") T2,         \n ");
		sql.append("        TM_VEHICLE_DEC T3,                              \n ");
		sql.append("        TT_VS_SALES_REPORT_INVOICE SRI                  \n ");
		sql.append("  WHERE     0 = 0                                       \n ");
		sql.append("        AND T1.REPORT_ID = SRI.REPORT_ID                \n ");
		sql.append("        AND T3.MATERIAL_ID = T2.MATERIAL_ID             \n ");
		sql.append("        AND T1.VEHICLE_ID = T3.VEHICLE_ID               \n ");
		sql.append(
				" " + control(" T2.SERIES_ID ", loginInfo.getDealerSeriesIDs(), loginInfo.getPoseSeriesIDs()) + " \n");
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

		if (loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())) {
			sql.append("      AND  T3.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
			String groupCode = queryParam.get("groupCode");
			groupCode = groupCode.replaceAll(",", "','");
			sql.append(" and T2.MODEL_CODE in('" + queryParam.get("groupCode") + "') \n");
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" and T3.VIN = ? \n");
			params.add(queryParam.get("vin"));
		}
		// 客户名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmName"))) {
			sql.append(" and T1.CTM_NAME= ? \n");
			params.add(queryParam.get("ctmName"));
		}
		// 联系电话
		if (!StringUtils.isNullOrEmpty(queryParam.get("mainPhone"))) {
			sql.append(" and T1.MAIN_PHONE= ? \n");
			params.add(queryParam.get("mainPhone"));
		}
		// 客户类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("ctmType"))) {
			sql.append(" and T1.CTM_TYPE= ? \n");
			params.add(queryParam.get("ctmType"));
		}
		// 交车日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and date(T1.SALES_DATE)>= ? \n");
			params.add(queryParam.get("beginDate"));
			sql.append(" and date(T1.SALES_DATE)<=? \n");
			params.add(queryParam.get("endDate"));
		}
		sql.append(" ) T WHERE 1=1 \n");
		 //是否相同
		 if (!StringUtils.isNullOrEmpty(queryParam.get("isSame"))) {
			 String isSame = OemDictCodeConstantsUtils.getIf_type(Integer.parseInt(CommonUtils.checkNull(queryParam.get("isSame"))));
			 if("0".equals(isSame)){
			 sql.append(" AND T.IS_VIN = '0' \n");
			 }
		 }

		// 是否72小时内扫描
		if (!StringUtils.isNullOrEmpty(queryParam.get("oneScan"))) {
			String oneScan = OemDictCodeConstantsUtils
					.getIf_type(Integer.parseInt(CommonUtils.checkNull(queryParam.get("oneScan"))));
			if ("0".equals(oneScan)) {
				sql.append(" AND T.One_day_scan = '0' ");
			} else if ("1".equals(oneScan)) {
				sql.append(" AND T.One_day_scan <> '0' ");
			}
		}
		// 是否120小时内扫描
		if (!StringUtils.isNullOrEmpty(queryParam.get("twoScan"))) {
			String twoScan = OemDictCodeConstantsUtils
					.getIf_type(Integer.parseInt(CommonUtils.checkNull(queryParam.get("twoScan"))));
			if ("0".equals(twoScan)) {
				sql.append(" AND T.two_day_scan = '0' ");
			} else if ("1".equals(twoScan)) {
				sql.append(" AND T.two_day_scan <> '0' ");
			}
		}
		logger.debug("发票扫描结果查询SQL ：  " + sql.toString() + "---" + params.toString());
		return sql.toString();
	}

	/**
	 * 发票扫描结果下载查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> scanInvoiceResultstQueryForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getTotalQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	/**
	 * 详细信息查询
	 * @param id
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	public Map<String, Object> queryDealerDetail(Long ctmId, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		CustcomVehicleQueryDao customerVehicleQueryDao= new CustcomVehicleQueryDao();
		try {
			Map<String, Object> orderInfos = customerVehicleQueryDao.getDetailCustomerVehicleQueryInfo(null,ctmId);
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

}
