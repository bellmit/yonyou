package com.yonyou.dms.vehicle.dao.insurancereport;

import java.util.ArrayList;
import java.util.Date;
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
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortExcelErrorDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyExcelTempDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyMainDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortDownDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortExcelErrorDcsPO;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
@Repository
public class PolicyDetailManageDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 *查询
	 */	
	public PageInfoDto PolilcyDetailManageQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> PolilcyDetailManageDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT \n");
		sql.append("					 TB.BIG_AREA_NAME,-- 大区 \n");
		sql.append("		       TB.SMALL_AREA_NAME,-- 小区 \n");
		sql.append("		       TB.DEALER_CODE,-- 经销商代码 \n");
		sql.append("		       TB.DEALER_NAME,-- 经销商全称 \n");
		sql.append("		       TB.DEALER_SHORTNAME,-- 经销商简称 \n");
		sql.append("		       TB.PROVINCE,-- 省份 \n");
		sql.append("		       TB.CITY,-- 城市 \n");
		sql.append("		       T1.INSURE_ORDER_CODE,-- 投保单号 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221001 THEN "+OemDictCodeConstants.INSURE_ORDER_TYPE_01+" -- 如果新续保为DMS上报值，则转换成DCS值，再显示（以下同） \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221002 THEN "+OemDictCodeConstants.INSURE_ORDER_TYPE_02+" \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221003 THEN "+OemDictCodeConstants.INSURE_ORDER_TYPE_03+" \n");
		sql.append("		          ELSE T1.INSURE_ORDER_TYPE \n");
		sql.append("		       END AS INSURE_ORDER_TYPE,-- 投保类型（新续保） \n");
		sql.append("		       date_format( T1.INS_SALES_DATE ,'%Y-%c-%d ') INS_SALES_DATE, -- 保险销售日期 \n");
		sql.append("		       T7.CTM_NAME, -- 车主姓名 \n");
		sql.append("		       T1.LICENSE, -- 车牌号 \n");
		sql.append("		       T1.VIN, -- VIN码 \n");
		sql.append("		       T1.ENGINE_NO, -- 发动机号 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.SALES_MODEL = 12281001 THEN '本地销售' -- 91271001本地销售 \n");
		sql.append("		          WHEN T1.SALES_MODEL = 12281002 THEN '二网销售' -- 91271002二网销售 \n");
		sql.append("		          ELSE null -- T1.SALES_MODEL \n");
		sql.append("		       END AS SALES_MODEL, -- 销售模式 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.IS_INS_CREDIT = 12241001 THEN "+OemDictCodeConstants.IF_TYPE_YES+" -- 是 \n");
		sql.append("		          WHEN T1.IS_INS_CREDIT = 12241002 THEN "+OemDictCodeConstants.IF_TYPE_NO+"  -- 否 \n");
		sql.append("		          ELSE T1.IS_INS_CREDIT \n");
		sql.append("		       END AS IS_INS_CREDIT, -- 是否信贷投保 \n");
		sql.append("		       T1.INSURED_NAME, -- 投保人 \n");
		sql.append("		       T1.INSURE_ORDER_NAME, -- 被保险人 \n");
		sql.append("		       date_format( T1.FIRST_REGISTER_DATE ,'%Y-%c-%d ') FIRST_REGISTER_DATE, -- 初登日期 \n");
		sql.append("		       T1.COM_INS_CODE, -- 交强险保单号 \n");
		sql.append("		       T1.BUSI_INS_CODE, -- 商业险保单号 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.IS_INS_LOCAL = 12231001 THEN "+OemDictCodeConstants.IS_INS_LOCAL_01+"  \n");
		sql.append("		          WHEN T1.IS_INS_LOCAL = 12231002 THEN "+OemDictCodeConstants.IS_INS_LOCAL_02+"  \n");
		sql.append("		          ELSE T1.IS_INS_LOCAL \n");
		sql.append("		       END AS IS_INS_LOCAL, -- 是否本地投保 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501001 THEN "+OemDictCodeConstants.INS_CHANNELS_01+" \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501002 THEN "+OemDictCodeConstants.INS_CHANNELS_02+" \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501003 THEN "+OemDictCodeConstants.INS_CHANNELS_03+" \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501004 THEN "+OemDictCodeConstants.INS_CHANNELS_04+" \n");
		sql.append("		          ELSE T1.INS_CHANNELS \n");
		sql.append("		       END AS INS_CHANNELS, -- 投保渠道 \n");
		sql.append("		       T4.INSURANCE_COMPANY_CODE,-- 保险公司代码 \n");
		sql.append("		       T4.INS_COMPANY_SHORT_NAME,-- 保险公司简称 \n");
		sql.append("		       T1.BRAND_NAME,-- 品牌 \n");
		sql.append("		       T1.SERIES_NAME,-- 车系 \n");
		sql.append("		       T1.MODEL_NAME,-- 车型 \n");
		sql.append("		       T1.GROUP_NAME,-- 车款 \n");
		sql.append("		       T1.MODEL_YEAR,-- 年款 \n");
		sql.append("		       T8.DEALER_SHORTNAME BUY_DEALER,-- 购车经销商 \n");
		sql.append("			   date_format( T6.INVOICE_DATE ,'%Y-%c-%d ')  INVOICE_DATE,-- 开票日期 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.FORM_STATUS = 12291004 THEN "+OemDictCodeConstants.FORM_STATUS_01+" \n");
		sql.append("		          WHEN T1.FORM_STATUS = 12291005 THEN "+OemDictCodeConstants.FORM_STATUS_02+" \n");
		sql.append("		          ELSE T1.FORM_STATUS \n");
		sql.append("		       END AS FORM_STATUS, -- 保单状态 \n");
		sql.append("		       T5.INSURANCE_SORT_CODE,-- 险种代码 \n");
		sql.append("		       T5.INSURANCE_SORT_NAME,-- 险种名称 \n");
		sql.append("		       T2.INSURE_ORDER_AMOUNT,-- 保额 \n");
		sql.append("		       T1.TOTAL_PREFERENTIAL,-- 应收金额 \n");
		sql.append("		       date_format( T2.BEGIN_DATE ,'%Y-%c-%d ')  BEGIN_DATE,-- 开始日期 \n");
		sql.append("		       date_format( T2.END_DATE ,'%Y-%c-%d ')  END_DATE,-- 结束日期 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T2.IS_PRESENTED = 12781001 THEN "+OemDictCodeConstants.IF_TYPE_YES+" -- 是 \n");
		sql.append("		          WHEN T2.IS_PRESENTED = 12781002 THEN "+OemDictCodeConstants.IF_TYPE_NO+"  -- 否 \n");
		sql.append("		          ELSE T2.IS_PRESENTED \n");
		sql.append("		       END AS IS_PRESENTED -- 是否赠送 \n");
		sql.append("		 FROM tt_insure_order T1 \n");
		sql.append("		 INNER JOIN tt_insure_order_detail T2 ON T1.INSURE_ORDER_CODE = T2.INSURE_ORDER_CODE AND T1.DEALER_CODE = T2.DEALER_CODE \n");
		sql.append("		 INNER JOIN ( \n");
		sql.append("				SELECT TD.DEALER_ID,--  经销商ID \n");
		sql.append("					 TD.DEALER_CODE,-- 经销商代码 \n");
		sql.append("					 TD.DEALER_NAME,-- 经销商全称 \n");
		sql.append("					 TD.DEALER_SHORTNAME,-- 经销商简称 \n");
		sql.append("					 TOR2.ORG_ID BIG_AREA_ID,-- 大区ID \n");
		sql.append("					 TOR2.ORG_NAME BIG_AREA_NAME,-- 大区名称 \n");
		sql.append("					 TOR1.ORG_ID SMALL_AREA_ID,-- 小区ID \n");
		sql.append("					 TOR1.ORG_NAME SMALL_AREA_NAME,-- 小区名称 \n");
		sql.append("					 PRO.REGION_NAME PROVINCE,-- 省份名称 \n");
		sql.append("					 CITY.REGION_NAME CITY-- 城市名称 \n");
		sql.append("				FROM tm_dealer TD \n");
		sql.append("				INNER JOIN tm_dealer_org_relation TDOR ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("				INNER JOIN TM_ORG TOR1 ON TDOR.ORG_ID = TOR1.ORG_ID \n");
		sql.append("				INNER JOIN TM_ORG TOR2 ON TOR1.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.BUSS_TYPE = 12351002 \n");
		sql.append("				LEFT JOIN tm_region_dcs PRO ON TD.PROVINCE_ID = PRO.REGION_CODE \n");
		sql.append("				LEFT JOIN tm_region_dcs CITY ON TD.CITY_ID = CITY.REGION_CODE \n");
		sql.append("			)TB ON T1.DEALER_CODE = case when right(TB.DEALER_CODE,LENGTH(TB.DEALER_CODE)-(LENGTH(TB.DEALER_CODE)-1))='A' then replace(TB.DEALER_CODE,'A','') else TB.DEALER_CODE end \n");
		sql.append("		 LEFT JOIN tm_vehicle_dec T3 ON T1.VIN = T3.VIN \n");
		sql.append("		 LEFT JOIN tt_insurance_company_main_dcs T4 ON T1.INSURATION_CODE = T4.INSURANCE_COMPANY_CODE \n");
		sql.append("		 LEFT JOIN tt_insurance_sort_dcs T5 ON T2.INSURANCE_SORT_CODE = T5.INSURANCE_SORT_CODE \n");
		sql.append("		 LEFT JOIN tt_vs_sales_report T6 ON T3.VEHICLE_ID = T6.VEHICLE_ID \n");
		sql.append("		 LEFT JOIN tt_vs_customer T7 ON T6.CTM_ID = T7.CTM_ID \n");
		sql.append("		 LEFT JOIN TM_DEALER T8 ON T3.DEALER_ID = T8.DEALER_ID \n");
		sql.append("		 WHERE 1 = 1 \n");
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("BIG_AREA_ID"))){
			sql.append("				and TB.BIG_AREA_ID ='"+queryParam.get("BIG_AREA_ID")+"' \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("				and TB.SMALL_AREA_ID ='"+queryParam.get("smallArea")+"' \n");
		}
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			String DealerCode =queryParam.get("dealerCode");
			DealerCode = DealerCode.replaceAll("\\,", "\n");
			DealerCode = DealerCode.replaceAll("[\\t\\n\\r]", "','");
			DealerCode = DealerCode.replaceAll(",''", "");
			sql.append("				and TB.DEALER_CODE in ('"+DealerCode+"') \n");
		}
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append("				 and  T1.BRAND_NAME  = (Select GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_ID ='"+queryParam.get("brandCode")+"' ) \n");
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("SERIES"))){
			sql.append("				and  T1.SERIES_NAME  = (Select GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_ID ='"+queryParam.get("SERIES")+"' ) \n");
		}
		//车型
		if(!StringUtils.isNullOrEmpty(queryParam.get("MODEL"))){
			sql.append("				and  T1.MODEL_NAME = (Select GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_ID ='"+queryParam.get("MODEL")+"' ) \n");
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("GROUP"))){
			sql.append("				 and  T1.GROUP_NAME = (Select GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_ID ='"+queryParam.get("GROUP")+"' ) \n");
		}
		//公司简称
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyCode"))){
			sql.append("			    and  T4.INSURANCE_COMPANY_CODE= '"+queryParam.get("companyCode")+"'  \n");
		}
		//新续保
		if(!StringUtils.isNullOrEmpty(queryParam.get("insureOrderType"))){
			String insureOrderType = queryParam.get("insureOrderType");
			if(insureOrderType.equals("91201001")){
				sql.append("				AND  T1.INSURE_ORDER_TYPE = '12221001' \n");
			}else if(insureOrderType.equals("91201002")){
				sql.append("				AND  T1.INSURE_ORDER_TYPE = '12221002' \n");
			}else if(insureOrderType.equals("91201003")){
				sql.append("				AND  T1.INSURE_ORDER_TYPE = '12221003' \n");
			}
		}
		//保险公司简称
		if(!StringUtils.isNullOrEmpty(queryParam.get("companyCode"))){
			sql.append("				and	 T4.INSURANCE_COMPANY_CODE = '"+queryParam.get("companyCode")+"' \n");
		}
		//险种
		if(!StringUtils.isNullOrEmpty(queryParam.get("sortCode"))){
			sql.append("				AND T5.INSURANCE_SORT_CODE = '"+queryParam.get("sortCode")+"' \n");
		}
		//保单状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("formStatus"))){
			String  formStatus = queryParam.get("formStatus");
			if(formStatus.equals("91211001")){
				sql.append("				AND  T1.FORM_STATUS = '12291004' \n");
			}else if(formStatus.equals("91211002")){
				sql.append("				AND  T1.FORM_STATUS = '12291005' \n");
			}
		}
		//日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))&&!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("				AND T1.INS_SALES_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d') \n");
			sql.append("			    AND T1.INS_SALES_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//投保单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("insureOrderCode"))){
			sql.append("				and 		T1.INSURE_ORDER_CODE like '%"+queryParam.get("insureOrderCode")+"%' \n");
		}
		//vin
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("				and 		T1.VIN like '%"+queryParam.get("vin")+"%' \n");
		}
		return sql.toString();
	}
	/**
	 * 车型
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getModel(Long series) {
		StringBuffer  sql = new StringBuffer("\n");
		sql.append("  SELECT DISTINCT \n");
		sql.append("	 C.GROUP_ID AS MODEL_ID, -- 车型ID \n");
		sql.append("	 C.GROUP_CODE AS MODEL_CODE, -- 车型代码 \n");
		sql.append("	 C.GROUP_NAME AS MODEL_NAME, -- 车型名称 \n");
		sql.append("	 C.PARENT_GROUP_ID -- 父级ID \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP C \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append("     WHERE C.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL+"' \n");
		sql.append("	 AND S.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR+"' \n");
		sql.append("	 AND C.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("     AND C.PARENT_GROUP_ID = '"+series+"'  \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 车款
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getGroup(Long model) {
		StringBuffer  sql = new StringBuffer("\n");
		sql.append("  SELECT DISTINCT \n");
		sql.append("	 C.GROUP_ID AS GROUP_ID, -- 车款ID \n");
		sql.append("	 C.GROUP_CODE AS GROUP_CODE, -- 车款代码 \n");
		sql.append("	 C.GROUP_NAME AS GROUP_NAME, -- 车款名称 \n");
		sql.append("	 C.PARENT_GROUP_ID -- 父级ID \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP C \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append("     WHERE C.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_TYPE+"' \n");
		sql.append("	 AND S.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL+"' \n");
		sql.append("	 AND C.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("     AND C.PARENT_GROUP_ID = '"+model+"'  \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 保险公司查询
	 */
	public List<Map> getcompanyCode(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT INSURANCE_COMPANY_CODE, #--保险公司代码 \n");
		sql.append("		       INS_COMPANY_SHORT_NAME #--保险公司名称 \n");
		sql.append("		  FROM tt_insurance_company_main_dcs \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 险种
	 */
	public List<Map> getsortCode(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT INSURANCE_SORT_CODE ,#--险种代码 \n");
		sql.append("		       INSURANCE_SORT_NAME #--险种名称 \n");
		sql.append("		  FROM tt_insurance_sort_dcs \n");
		sql.append("		 WHERE STATUS='12781001' \n");
		sql.append("		  ORDER BY SORT_NUM \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
