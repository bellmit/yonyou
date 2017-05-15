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
public class InsuranceCardDetailDlrDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 *查询
	 */	
	public PageInfoDto InsuranceCardDetailQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> InsuranceCardDetailDownload(Map<String, String> queryParam) {
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
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" \n");
		sql.append("		SELECT TB.BIG_AREA_NAME,-- 大区 \n");
		sql.append("		       TB.SMALL_AREA_NAME,-- 小区 \n");
		sql.append("		       TB.DEALER_CODE,-- 经销商代码 \n");
		sql.append("		       TB.DEALER_SHORTNAME,-- 经销商简称 \n");
		sql.append("		       TB.PROVINCE,-- 省份 \n");
		sql.append("		       TB.CITY,-- 城市 \n");
		sql.append("		       T1.INSURE_ORDER_CODE,-- 投保单号 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221001 THEN 91201001 -- 如果新续保为DMS上报值，则转换成DCS值，再显示（以下同） \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221002 THEN 91201002 \n");
		sql.append("		          WHEN T1.INSURE_ORDER_TYPE = 12221003 THEN 91201003 \n");
		sql.append("		          ELSE T1.INSURE_ORDER_TYPE \n");
		sql.append("		       END AS INSURE_ORDER_TYPE,-- 新续保 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.IS_INS_LOCAL = 12231001 THEN 91231001 \n");
		sql.append("		          WHEN T1.IS_INS_LOCAL = 12231002 THEN 91231002 \n");
		sql.append("		          ELSE T1.IS_INS_LOCAL \n");
		sql.append("		       END AS IS_INS_LOCAL, -- 是否本地投保 \n");
		sql.append("		       CASE \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501001 THEN 91221001 \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501002 THEN 91221002 \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501003 THEN 91221003 \n");
		sql.append("		          WHEN T1.INS_CHANNELS = 11501004 THEN 91221004 \n");
		sql.append("		          ELSE T1.INS_CHANNELS \n");
		sql.append("		       END AS INS_CHANNELS, -- 投保渠道 \n");
		sql.append("		       T4.INSURANCE_COMPANY_CODE,-- 保险公司代码 \n");
		sql.append("		       T4.INS_COMPANY_SHORT_NAME,-- 保险公司简称 \n");
		sql.append("		       T1.BRAND_NAME,-- 品牌 \n");
		sql.append("		       T1.SERIES_NAME,-- 车系 \n");
		sql.append("		       T1.MODEL_NAME,-- 车型 \n");
		sql.append("		       T1.GROUP_NAME,-- 车款 \n");
		sql.append("		       T1.MODEL_YEAR,-- 年款 \n");
		sql.append("		       T1.VIN,-- vin \n");
		sql.append("		       T6.CARD_NO,-- 卡券类型ID \n");
		sql.append("		       T7.VOUCHER_NAME,-- 卡券类型名称 \n");
		sql.append("		       T7.SINGLE_AMOUNT,-- 单张金额 \n");
		sql.append("		       T7.VOUCHER_STANDARD,-- 发券标准 \n");
		sql.append("		       T7.USE_RANGE,-- 使用范围 \n");
		sql.append("		       T7.REPAIR_TYPE_NAME,-- 维修类型 \n");
		sql.append("		       T6.VOUCHER_ID,-- 卡券ID \n");
		sql.append("		       T6.USE_STATUS,-- 卡券状态 \n");
		sql.append("		       T6.ACTIVITY_CODE,-- 营销活动ID \n");
		sql.append("		       T8.ACTIVITY_NAME,-- 营销活动名称 \n");
		sql.append("		       date_format(T8.ACTIVITY_START_DATE,'%Y-%c-%d') ACTIVITY_START_DATE,-- 活动开始日期 \n");
		sql.append("		       date_format(T8.ACTIVITY_END_DATE,'%Y-%c-%d') ACTIVITY_END_DATE,-- 活动结束日期 \n");
		sql.append("		       date_format(T6.ISSUE_DATE,'%Y-%c-%d') ISSUE_DATE-- 卡券发放日期 \n");
		sql.append("		 FROM tt_insure_order T1 \n");
		sql.append("		 INNER JOIN tt_wechat_card_info T6 ON T1.INSURE_ORDER_CODE = T6.INSURE_ORDER_CODE AND T1.DEALER_CODE = T6.DEALER_CODE \n");
		sql.append("		 LEFT JOIN tm_vehicle_dec T3 ON T1.VIN = T3.VIN \n");
		sql.append("		 LEFT JOIN tt_insurance_company_main_dcs T4 ON T1.INSURATION_CODE = T4.INSURANCE_COMPANY_CODE \n");
		sql.append("		 LEFT JOIN tm_voucher_dcs T7 ON T6.CARD_NO = T7.VOUCHER_NO AND T6.ACTIVITY_CODE = T7.ACTIVITY_CODE \n");
		sql.append("		 LEFT JOIN tm_insurance_activity_dcs T8 ON T6.ACTIVITY_CODE = T8.ACTIVITY_CODE \n");
		sql.append("		 INNER JOIN ( \n");
		sql.append("				SELECT TD.DEALER_ID,-- 经销商ID \n");
		sql.append("		       TD.DEALER_CODE,-- 经销商代码 \n");
		sql.append("		       TD.DEALER_SHORTNAME,-- 经销商简称 \n");
		sql.append("		       TOR2.ORG_ID BIG_AREA_ID,-- 大区ID \n");
		sql.append("		       TOR2.ORG_NAME BIG_AREA_NAME,-- 大区名称 \n");
		sql.append("		       TOR1.ORG_ID SMALL_AREA_ID,-- 小区ID \n");
		sql.append("		       TOR1.ORG_NAME SMALL_AREA_NAME,-- 小区名称 \n");
		sql.append("		       PRO.REGION_NAME PROVINCE,-- 省份名称 \n");
		sql.append("		       CITY.REGION_NAME CITY-- 城市名称 \n");
		sql.append("				FROM tm_dealer TD \n");
		sql.append("				INNER JOIN tm_dealer_org_relation TDOR ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("				INNER JOIN tm_org TOR1 ON TDOR.ORG_ID = TOR1.ORG_ID \n");
		sql.append("				INNER JOIN tm_org TOR2 ON TOR1.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.BUSS_TYPE = 12351002 \n");
		sql.append("				LEFT JOIN tm_region_dcs PRO ON TD.PROVINCE_ID = PRO.REGION_CODE \n");
		sql.append("				LEFT JOIN tm_region_dcs CITY ON TD.CITY_ID = CITY.REGION_CODE \n");
		sql.append("		) TB ON T1.DEALER_CODE = CASE WHEN RIGHT(TB.DEALER_CODE,LENGTH(TB.DEALER_CODE)-(LENGTH(TB.DEALER_CODE)-1))='A' THEN REPLACE(TB.DEALER_CODE,'A','') ELSE TB.DEALER_CODE END \n");
		sql.append(" WHERE 1 = 1 AND TB.DEALER_CODE = ('"+loginInfo.getDealerCode()+"') \n");
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("				and TB.BIG_AREA_ID ='"+queryParam.get("bigArea")+"' \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("				and TB.SMALL_AREA_ID = '"+queryParam.get("smallArea")+"' \n");
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
		//卡券类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("voucherName"))){
			sql.append("				and	 T6.CARD_NO = '"+queryParam.get("voucherName")+"' \n");
		}
		//卡券状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("USE_STATUS"))){
			sql.append("				AND T6.USE_STATUS = '"+queryParam.get("USE_STATUS")+"' \n");
		}
		//日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))&&!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("				AND T6.ISSUE_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d') \n");
			sql.append("			    AND T6.ISSUE_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//卡券类型ID
		if(!StringUtils.isNullOrEmpty(queryParam.get("voucherCode"))){
			sql.append("				and 		T6.CARD_NO like '%"+queryParam.get("voucherCode")+"%' \n");
		}
		//营销活动ID
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("				and 		T6.ACTIVITY_CODE like '%"+queryParam.get("activityCode")+"%' \n");
		}
		//投保单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityName"))){
			sql.append("				and 		T8.ACTIVITY_NAME like '%"+queryParam.get("activityName")+"%' \n");
		}
		//卡券ID
		if(!StringUtils.isNullOrEmpty(queryParam.get("voucherId"))){
			sql.append("				and 		T6.VOUCHER_ID like '%"+queryParam.get("voucherId")+"%' \n");
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
	 *卡券类型名称
	 */
	public List<Map> getvoucherName(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT distinct VOUCHER_NO,  -- 卡券类型ID  \n");
		sql.append("		        VOUCHER_NAME  -- 卡券名称        \n");
		sql.append("		 FROM TM_VOUCHER_dcs \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
