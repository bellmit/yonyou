package com.yonyou.dms.customer.service.insurance;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.InsuranceExpireRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.common.domains.PO.customer.InsuranceExpireRemindPO;
import com.yonyou.dms.common.domains.PO.customer.OwnerMemoPO;
import com.yonyou.dms.common.domains.PO.customer.VehicleMemoPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class insuranceCustomerServiceImpl implements insuranceCustomerService {

	/**
	 * 分页查询
	 */
	@Override
	public PageInfoDto queryInsuranceInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"select min(cc.IS_SELF_COMPANY_INSURANCE)as IS_SELF_COMPANY_INSURANCE,cc.DEALER_CODE,F.INSURATION_NAME INSURATION_SHORT_NAME,IS_VALID,DCRC_ADVISOR,BOOKING_ORDER_NO,BOOKING_COME_TIME, cc.phone, cc.mobile,cc.INSURANCE_END_DATE,cc.DELIVERER, "
						+ " cc.DELIVERER_PHONE,DELIVERER_MOBILE, cc.SALES_DATE, cc.LICENSE, cc.OWNER_NAME,cc.CONTACTOR_PHONE, cc.CONTACTOR_MOBILE, "
						+ "cc.CONTACTOR_NAME,cc.CONTACTOR_ADDRESS, cc.CONTACTOR_ZIP_CODE, cc.REMIND_DATE, cc.REMINDER, cc.CUSTOMER_FEEDBACK, cc.REMIND_CONTENT, cc.REMIND_FAIL_REASON, "
						+ "cc.REMIND_STATUS, cc.REMINDER_NAME, cc.VIN,it.INSURANCE_TYPE_NAME, cc.OWNER_NO, cc.OWNER_PROPERTY, cc.BRAND, cc.SERIES, cc.INSURANCE_BEGIN_DATE, cc.ADDRESS,"
						+ " cc.MAINTAIN_ADVISOR,cc.MODEL,SERVICE_ADVISOR,cc.is_return_factory as is_remind_back, db.dealer_shortname from ("
						+ "" + "select distinct case when (IS_SELF_COMPANY_INSURANCE=" + (DictCodeConstants.DICT_IS_NO)
						+ " or IS_SELF_COMPANY_INSURANCE=0) " + " then " + DictCodeConstants.DICT_IS_NO
						+ " when IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_YES + " then "
						+ DictCodeConstants.DICT_IS_YES
						+ " end IS_SELF_COMPANY_INSURANCE,IS_VALID,DCRC_ADVISOR,DEALER_CODE,phone,mobile,INSURANCE_END_DATE,DELIVERER, "
						+ " DELIVERER_PHONE,DELIVERER_MOBILE,SALES_DATE,INSURATION_CODE,LICENSE,OWNER_NAME,CONTACTOR_PHONE, "
						+ " CONTACTOR_MOBILE,CONTACTOR_NAME,CONTACTOR_ADDRESS,CONTACTOR_ZIP_CODE,REMIND_DATE,REMINDER,CUSTOMER_FEEDBACK,REMIND_CONTENT, "
						+ " REMIND_FAIL_REASON,REMIND_STATUS,REMINDER_NAME,VIN,INSURANCE_TYPE_CODE, OWNER_NO,OWNER_PROPERTY,BRAND,SERIES, "
						+ " INSURANCE_BEGIN_DATE, ADDRESS,MAINTAIN_ADVISOR,MODEL, SERVICE_ADVISOR,IS_RETURN_FACTORY  from  "
						+ " (SELECT S.INSURANCE_TYPE_CODE, S.IS_SELF_COMPANY_INSURANCE,B.IS_VALID,B.DCRC_ADVISOR,B.DEALER_CODE,c.phone,c.mobile, "
						+ " B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE," + " S.INSURANCE_END_DATE,"
						+ " S.INSURATION_CODE, " + " C.OWNER_NAME, " + " C.CONTACTOR_PHONE, " + " C.CONTACTOR_MOBILE, "
						+ " C.CONTACTOR_NAME, " + " C.CONTACTOR_ADDRESS, " + " C.CONTACTOR_ZIP_CODE, "
						+ " D.REMIND_DATE, " + " D.REMINDER, "
						+ " D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT,D.REMIND_FAIL_REASON, " + " D.REMIND_STATUS,"
						+ " B.VIN, " + " B.OWNER_NO, " + " C.OWNER_PROPERTY, " + " B.BRAND, " + " B.SERIES, "
						+ " B.LICENSE, " + " B.SALES_DATE,D.IS_RETURN_FACTORY, " + " G.EMPLOYEE_NAME AS REMINDER_NAME, "
						+ " S.INSURANCE_BEGIN_DATE," + " C.ADDRESS, "
						+ " B.MODEL,B.SERVICE_ADVISOR,b.INSURANCE_ADVISOR  as MAINTAIN_ADVISOR" + " FROM "
						+ " ("+CommonConstants.VM_VEHICLE+") B left outer join ("+CommonConstants.VM_OWNER+") C on B.DEALER_CODE = C.DEALER_CODE "
						+ " AND B.OWNER_NO=C.OWNER_NO left outer join " + "(select a.* from "
						+ " TT_INSURANCE_EXPIRE_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_INSURANCE_EXPIRE_REMIND where DEALER_CODE in ("
						+ "select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") vm  where DEALER_CODE='" + dealerCode
						+ "' and BIZ_CODE = 'TT_ALL_REMIND')" + " and LAST_TAG=" + DictCodeConstants.DICT_IS_YES
						+ " and D_KEY=" + CommonConstants.D_KEY + " GROUP BY VIN"
						+ ") b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN "
						+ " left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE"
						+ " LEFT JOIN ( "
						+ " select VL.ITEM_ID,VL.DEALER_CODE,VL.VIN,VL.IS_VALID,VL.INSURANCE_BEGIN_DATE,VL.INSURANCE_END_DATE,VL.INSURATION_CODE,VL.IS_SELF_COMPANY_INSURANCE,S2.INSURANCE_TYPE_CODE  "
						+ " from TT_SERVICE_INSURANCE vl  "
						+ " INNER JOIN (  select max(f.item_id) AS ITEM_ID,INSURANCE_TYPE_CODE  from TT_SERVICE_INSURANCE f group by f.DEALER_CODE,f.vin ) S2 ON S2.ITEM_ID=VL.ITEM_ID "
						+ "     where 1=1 AND VL.IS_VALID=" + DictCodeConstants.DICT_IS_YES + " "
						+ " ) S on  S.DEALER_CODE = B.DEALER_CODE  and S.VIN = B.vin " + " WHERE B.DEALER_CODE = '"
						+ dealerCode + "' ) m where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceTypeCode"))) {
			sql.append(" and exists (select x.vin from tt_service_insurance x  where x.DEALER_CODE='" + dealerCode
					+ "' and x.vin=m.vin and x.insurance_type_code= '" + queryParam.get("insuranceTypeCode") + "'  )");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("maintainAdvisor"))) {
			sql.append(" AND m.MAINTAIN_ADVISOR='" + queryParam.get("maintainAdvisor") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dcrcadvisor"))) {
			sql.append( " AND DCRC_ADVISOR='" + queryParam.get("dcrcadvisor") + "'" );
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sql.append( " AND BRAND='" + queryParam.get("brand") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sql.append( " AND MODEL='" + queryParam.get("model") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sql.append( " AND SERIES='" + queryParam.get("series") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty")) && !queryParam.get("ownerProperty").trim().equals("-1")) {
			sql.append( " AND OWNER_PROPERTY=" + queryParam.get("ownerProperty") ); 
		}
//		if ((StringUtils.isNullOrEmpty(queryParam.get("IsOperatorMsg")) && (StringUtils.isNullOrEmpty(queryParam.get("IsOperatorMsg"))
//				&& queryParam.get("IsOperatorMsg").equals(DictCodeConstants.DICT_IS_YES)))){
//			sql.append( " AND (REMIND_STATUS = " + DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN
//					 + "  OR REMIND_STATUS is null or REMIND_STATUS = 0)" );
//		} else {
//			if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus")) ) {
//				sql.append( " AND REMIND_STATUS = " + queryParam.get("remindStatus") );
//			}
//		}
		sql.append( Utility.getLikeCond("m", "LICENSE", queryParam.get("license"), "AND") );
		sql.append( Utility.getLikeCond("m", "ADDRESS", queryParam.get("address"), "AND") );
		sql.append( Utility.getLikeCond("m", "VIN", queryParam.get("vin"), "AND") );
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSelfCompanyInsurance"))) {
			if (queryParam.get("isSelfCompanyInsurance").equals(DictCodeConstants.DICT_IS_YES)) {
				sql.append( " AND IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_YES + " " );
			} else {
				sql.append(  " AND (IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_NO
						+ "  or IS_SELF_COMPANY_INSURANCE IS NULL  or IS_SELF_COMPANY_INSURANCE=0 )" );
			}
		}
		sql.append( Utility.getDateCond("", "INSURANCE_BEGIN_DATE", queryParam.get("firstInsureStartDate"), queryParam.get("lastInsureStartDate")) );
		sql.append( Utility.getDateCond("", "INSURANCE_END_DATE", queryParam.get("firstInsureEndDate"), queryParam.get("lastInsureEndDate")) );
		sql.append( Utility.getDateCond("", "SALES_DATE", queryParam.get("firstSalesDate"), queryParam.get("lastSalesDate")) );
		sql.append( Utility.getDateCond("", "REMIND_DATE", queryParam.get("remindDateBegin"), queryParam.get("remindDateEnd")) );
		if (!StringUtils.isNullOrEmpty(queryParam.get("isFlag"))) {
			if (queryParam.get("isFlag").equals(DictCodeConstants.DICT_IS_YES)) {
				sql.append( " AND IS_VALID=" + queryParam.get("isFlag") + " " );
			} else {
				sql.append( " AND (IS_VALID=" + DictCodeConstants.DICT_IS_NO + " OR IS_VALID=0 or IS_VALID IS NULL ) " );
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isRemindBack"))  && queryParam.get("isRemindBack").equals("12781001")) {
			sql.append( " and is_return_factory=12781001 " );
		}
		sql.append(" ) cc "
				+ " LEFT JOIN TM_DEALER_BASICINFO db  ON cc.dealer_code=db.dealer_code "
				+ "  LEFT JOIN TM_INSURANCE_TYPE it ON it.`DEALER_CODE`=cc.`DEALER_CODE` AND it.`INSURANCE_TYPE_CODE` = cc.INSURANCE_TYPE_CODE  "
				+ " left  join ("+CommonConstants.VM_INSURANCE+") F on CC.INSURATION_code=F.INSURATION_code AND  F.DEALER_CODE = '"
				+ dealerCode + 
				"' left join (select max(BOOKING_ORDER_NO) as BOOKING_ORDER_NO,max(BOOKING_COME_TIME) as BOOKING_COME_TIME,BOOKING_ORDER_STATUS,vin,DEALER_CODE from  TT_BOOKING_ORDER "+
				"where (BOOKING_ORDER_STATUS="+DictCodeConstants.DICT_BOS_NOT_ENTER+" or BOOKING_ORDER_STATUS="+DictCodeConstants.DICT_BOS_DELAY_ENTER+" or BOOKING_ORDER_STATUS is null ) "+
				"group by DEALER_CODE,vin,BOOKING_ORDER_STATUS) m "+
				"on cc.vin=m.vin"+
				" and cc.DEALER_CODE = m.DEALER_CODE WHERE 1=1  " );
		this.setWhere(sql, queryParam, queryList);
		sql.append(" group by INSURATION_NAME,IS_VALID,DCRC_ADVISOR,BOOKING_ORDER_NO,BOOKING_COME_TIME,cc.phone, cc.mobile, cc.INSURANCE_END_DATE, cc.DELIVERER, cc.DELIVERER_PHONE, cc.DELIVERER_MOBILE," +
				" cc.SALES_DATE, cc.LICENSE,cc.OWNER_NAME,cc.CONTACTOR_PHONE, cc.CONTACTOR_MOBILE, cc.CONTACTOR_NAME, cc.CONTACTOR_ADDRESS," +
				" cc.CONTACTOR_ZIP_CODE, cc.REMIND_DATE, cc.REMINDER,CUSTOMER_FEEDBACK,cc.REMIND_CONTENT, cc.REMIND_FAIL_REASON, cc.REMIND_STATUS," +
				" cc.REMINDER_NAME, cc.VIN,it.INSURANCE_TYPE_NAME, cc.OWNER_NO, cc.OWNER_PROPERTY, cc.BRAND, cc.SERIES, cc.INSURANCE_BEGIN_DATE, cc.ADDRESS," +
				" cc.MAINTAIN_ADVISOR, cc.MODEL, cc.SERVICE_ADVISOR,cc.is_return_factory,cc.DEALER_CODE " );
		
//		if(isLimit.equals(DictDataConstant.DICT_IS_YES)){
//			sqlBasic=sqlBasic+ CommonSqlMethod.gerSpecialSql(CommonSqlMethod.getRowsNumber(Integer.parseInt(limit)),
//					 " and "  + CommonSqlMethod.getRowsNumber(Integer.parseInt(limit))) ;
//		}
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 设置查询条件
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {


		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateFrom"))) {
			sql.append(" AND cc.SALES_DATE >= ?");
			queryList.add(queryParam.get("salesDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateTo"))) {
			sql.append(" AND cc.SALES_DATE <= ?");
			queryList.add(queryParam.get("salesDateTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and cc.BRAND like ? ");
			queryList.add("%" + queryParam.get("brandCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sql.append(" and cc.SERIES like ? ");
			queryList.add("%" + queryParam.get("seriesCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sql.append(" and cc.MODEL like ? ");
			queryList.add("%" + queryParam.get("modelCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceBeginDateFrom"))) {
			sql.append(" AND vl.INSURANCE_BEGIN_DATE <= ?");
			queryList.add(queryParam.get("insuranceBeginDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceBeginDateTo"))) {
			sql.append(" AND vl.INSURANCE_END_DATE >= ?");
			queryList.add(queryParam.get("insuranceBeginDateTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND cc.VIN like ? ");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("iSValid"))) {
			sql.append(" AND IS_VALID like ? ");
			queryList.add("%" + queryParam.get("iSValid") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sql.append(" AND a.REMIND_STATUS like ? ");
			queryList.add("%" + queryParam.get("remindStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("deliverer"))) {
			sql.append(" AND a.DELIVERER like ? ");
			queryList.add("%" + queryParam.get("deliverer") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateFrom"))) {
			sql.append(" AND a.REMIND_DATE <= ?");
			queryList.add(queryParam.get("remindDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateTo"))) {
			sql.append(" AND a.REMIND_DATE >= ?");
			queryList.add(queryParam.get("remindDateTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sql.append(" AND a.REMIND_STATUS like ? ");
			queryList.add("%" + queryParam.get("remindStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sql.append(" AND cc.LICENSE like ? ");
			queryList.add("%" + queryParam.get("license") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorAddress"))) {
			sql.append(" AND cc.CONTACTOR_ADDRESS like ? ");
			queryList.add("%" + queryParam.get("contactorAddress") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("maintainAdvisor"))) {
			sql.append(" and cc.INSURANCE_ADVISOR like ? ");
			queryList.add("%" + queryParam.get("maintainAdvisor") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isReturnFactory"))) {
			sql.append(" and a.IS_RETURN_FACTORY like ? ");
			queryList.add("%" + queryParam.get("isReturnFactory") + "%");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceTypeName"))) {
			sql.append(" and it.INSURANCE_TYPE_NAME like ? ");
			queryList.add("%" + queryParam.get("insuranceTypeName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dcrcAdvisor"))) {
			sql.append(" AND cc.DCRC_ADVISOR like ? ");
			queryList.add("%" + queryParam.get("dcrcAdvisor") + "%");
		}
	
	}
	
	/**
	 * 	 
	 *查询险种定义 
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryInsurersTypeName(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT INSURANCE_TYPE_CODE,INSURANCE_TYPE_NAME,DEALER_CODE FROM TM_INSURANCE_TYPE WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}

	/**
	 * 导出
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> exportInsuranceInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"select min(cc.IS_SELF_COMPANY_INSURANCE)as IS_SELF_COMPANY_INSURANCE,cc.DEALER_CODE,F.INSURATION_NAME INSURATION_SHORT_NAME,IS_VALID,DCRC_ADVISOR,BOOKING_ORDER_NO,BOOKING_COME_TIME, cc.phone, cc.mobile,cc.INSURANCE_END_DATE,cc.DELIVERER, "
						+ " cc.DELIVERER_PHONE,DELIVERER_MOBILE, cc.SALES_DATE, cc.LICENSE, cc.OWNER_NAME,cc.CONTACTOR_PHONE, cc.CONTACTOR_MOBILE, "
						+ "cc.CONTACTOR_NAME,cc.CONTACTOR_ADDRESS, cc.CONTACTOR_ZIP_CODE, cc.REMIND_DATE, cc.REMINDER, cc.CUSTOMER_FEEDBACK, cc.REMIND_CONTENT, cc.REMIND_FAIL_REASON, "
						+ "cc.REMIND_STATUS, cc.REMINDER_NAME, cc.VIN,it.INSURANCE_TYPE_NAME, cc.OWNER_NO, cc.OWNER_PROPERTY, cc.BRAND, cc.SERIES, cc.INSURANCE_BEGIN_DATE, cc.ADDRESS,"
						+ " cc.MAINTAIN_ADVISOR,cc.MODEL,SERVICE_ADVISOR,cc.is_return_factory as is_remind_back, db.dealer_shortname from ("
						+ "" + "select distinct case when (IS_SELF_COMPANY_INSURANCE=" + (DictCodeConstants.DICT_IS_NO)
						+ " or IS_SELF_COMPANY_INSURANCE=0) " + " then " + DictCodeConstants.DICT_IS_NO
						+ " when IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_YES + " then "
						+ DictCodeConstants.DICT_IS_YES
						+ " end IS_SELF_COMPANY_INSURANCE,IS_VALID,DCRC_ADVISOR,DEALER_CODE,phone,mobile,INSURANCE_END_DATE,DELIVERER, "
						+ " DELIVERER_PHONE,DELIVERER_MOBILE,SALES_DATE,INSURATION_CODE,LICENSE,OWNER_NAME,CONTACTOR_PHONE, "
						+ " CONTACTOR_MOBILE,CONTACTOR_NAME,CONTACTOR_ADDRESS,CONTACTOR_ZIP_CODE,REMIND_DATE,REMINDER,CUSTOMER_FEEDBACK,REMIND_CONTENT, "
						+ " REMIND_FAIL_REASON,REMIND_STATUS,REMINDER_NAME,VIN,INSURANCE_TYPE_CODE, OWNER_NO,OWNER_PROPERTY,BRAND,SERIES, "
						+ " INSURANCE_BEGIN_DATE, ADDRESS,MAINTAIN_ADVISOR,MODEL, SERVICE_ADVISOR,IS_RETURN_FACTORY  from  "
						+ " (SELECT S.INSURANCE_TYPE_CODE, S.IS_SELF_COMPANY_INSURANCE,B.IS_VALID,B.DCRC_ADVISOR,B.DEALER_CODE,c.phone,c.mobile, "
						+ " B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE," + " S.INSURANCE_END_DATE,"
						+ " S.INSURATION_CODE, " + " C.OWNER_NAME, " + " C.CONTACTOR_PHONE, " + " C.CONTACTOR_MOBILE, "
						+ " C.CONTACTOR_NAME, " + " C.CONTACTOR_ADDRESS, " + " C.CONTACTOR_ZIP_CODE, "
						+ " D.REMIND_DATE, " + " D.REMINDER, "
						+ " D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT,D.REMIND_FAIL_REASON, " + " D.REMIND_STATUS,"
						+ " B.VIN, " + " B.OWNER_NO, " + " C.OWNER_PROPERTY, " + " B.BRAND, " + " B.SERIES, "
						+ " B.LICENSE, " + " B.SALES_DATE,D.IS_RETURN_FACTORY, " + " G.EMPLOYEE_NAME AS REMINDER_NAME, "
						+ " S.INSURANCE_BEGIN_DATE," + " C.ADDRESS, "
						+ " B.MODEL,B.SERVICE_ADVISOR,b.INSURANCE_ADVISOR  as MAINTAIN_ADVISOR" + " FROM "
						+ " ("+CommonConstants.VM_VEHICLE+") B left outer join ("+CommonConstants.VM_OWNER+") C on B.DEALER_CODE = C.DEALER_CODE "
						+ " AND B.OWNER_NO=C.OWNER_NO left outer join " + "(select a.* from "
						+ " TT_INSURANCE_EXPIRE_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_INSURANCE_EXPIRE_REMIND where DEALER_CODE in ("
						+ "select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") vm  where DEALER_CODE='" + dealerCode
						+ "' and BIZ_CODE = 'TT_ALL_REMIND')" + " and LAST_TAG=" + DictCodeConstants.DICT_IS_YES
						+ " and D_KEY=" + CommonConstants.D_KEY + " GROUP BY VIN"
						+ ") b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN "
						+ " left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE"
						+ " LEFT JOIN ( "
						+ " select VL.ITEM_ID,VL.DEALER_CODE,VL.VIN,VL.IS_VALID,VL.INSURANCE_BEGIN_DATE,VL.INSURANCE_END_DATE,VL.INSURATION_CODE,VL.IS_SELF_COMPANY_INSURANCE,S2.INSURANCE_TYPE_CODE  "
						+ " from TT_SERVICE_INSURANCE vl  "
						+ " INNER JOIN (  select max(f.item_id) AS ITEM_ID,INSURANCE_TYPE_CODE  from TT_SERVICE_INSURANCE f group by f.DEALER_CODE,f.vin ) S2 ON S2.ITEM_ID=VL.ITEM_ID "
						+ "     where 1=1 AND VL.IS_VALID=" + DictCodeConstants.DICT_IS_YES + " "
						+ " ) S on  S.DEALER_CODE = B.DEALER_CODE  and S.VIN = B.vin " + " WHERE B.DEALER_CODE = '"
						+ dealerCode + "' ) m where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceTypeCode"))) {
			sql.append(" and exists (select x.vin from tt_service_insurance x  where x.DEALER_CODE='" + dealerCode
					+ "' and x.vin=m.vin and x.insurance_type_code= '" + queryParam.get("insuranceTypeCode") + "'  )");
		}
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("maintainAdvisor"))) {
			sql.append(" AND m.MAINTAIN_ADVISOR='" + queryParam.get("maintainAdvisor") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dcrcadvisor"))) {
			sql.append( " AND DCRC_ADVISOR='" + queryParam.get("dcrcadvisor") + "'" );
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sql.append( " AND BRAND='" + queryParam.get("brand") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sql.append( " AND MODEL='" + queryParam.get("model") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sql.append( " AND SERIES='" + queryParam.get("series") + "'" );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty")) && !queryParam.get("ownerProperty").trim().equals("-1")) {
			sql.append( " AND OWNER_PROPERTY=" + queryParam.get("ownerProperty") ); 
		}
//		if ((StringUtils.isNullOrEmpty(queryParam.get("IsOperatorMsg")) && (StringUtils.isNullOrEmpty(queryParam.get("IsOperatorMsg"))
//				&& queryParam.get("IsOperatorMsg").equals(DictCodeConstants.DICT_IS_YES)))){
//			sql.append( " AND (REMIND_STATUS = " + DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN
//					 + "  OR REMIND_STATUS is null or REMIND_STATUS = 0)" );
//		} else {
//			if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus")) ) {
//				sql.append( " AND REMIND_STATUS = " + queryParam.get("remindStatus") );
//			}
//		}
		sql.append( Utility.getLikeCond("m", "LICENSE", queryParam.get("license"), "AND") );
		sql.append( Utility.getLikeCond("m", "ADDRESS", queryParam.get("address"), "AND") );
		sql.append( Utility.getLikeCond("m", "VIN", queryParam.get("vin"), "AND") );
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSelfCompanyInsurance"))) {
			if (queryParam.get("isSelfCompanyInsurance").equals(DictCodeConstants.DICT_IS_YES)) {
				sql.append( " AND IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_YES + " " );
			} else {
				sql.append(  " AND (IS_SELF_COMPANY_INSURANCE=" + DictCodeConstants.DICT_IS_NO
						+ "  or IS_SELF_COMPANY_INSURANCE IS NULL  or IS_SELF_COMPANY_INSURANCE=0 )" );
			}
		}
		sql.append( Utility.getDateCond("", "INSURANCE_BEGIN_DATE", queryParam.get("firstInsureStartDate"), queryParam.get("lastInsureStartDate")) );
		sql.append( Utility.getDateCond("", "INSURANCE_END_DATE", queryParam.get("firstInsureEndDate"), queryParam.get("lastInsureEndDate")) );
		sql.append( Utility.getDateCond("", "SALES_DATE", queryParam.get("firstSalesDate"), queryParam.get("lastSalesDate")) );
		sql.append( Utility.getDateCond("", "REMIND_DATE", queryParam.get("remindDateBegin"), queryParam.get("remindDateEnd")) );
		if (!StringUtils.isNullOrEmpty(queryParam.get("isFlag"))) {
			if (queryParam.get("isFlag").equals(DictCodeConstants.DICT_IS_YES)) {
				sql.append( " AND IS_VALID=" + queryParam.get("isFlag") + " " );
			} else {
				sql.append( " AND (IS_VALID=" + DictCodeConstants.DICT_IS_NO + " OR IS_VALID=0 or IS_VALID IS NULL ) " );
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isRemindBack"))  && queryParam.get("isRemindBack").equals("12781001")) {
			sql.append( " and is_return_factory=12781001 " );
		}
		sql.append(" ) cc "
				+ " LEFT JOIN TM_DEALER_BASICINFO db  ON cc.dealer_code=db.dealer_code "
				+ "  LEFT JOIN TM_INSURANCE_TYPE it ON it.`DEALER_CODE`=cc.`DEALER_CODE` AND it.`INSURANCE_TYPE_CODE` = cc.INSURANCE_TYPE_CODE  "
				+ " left  join ("+CommonConstants.VM_INSURANCE+") F on CC.INSURATION_code=F.INSURATION_code AND  F.DEALER_CODE = '"
				+ dealerCode + 
				"' left join (select max(BOOKING_ORDER_NO) as BOOKING_ORDER_NO,max(BOOKING_COME_TIME) as BOOKING_COME_TIME,BOOKING_ORDER_STATUS,vin,DEALER_CODE from  TT_BOOKING_ORDER "+
				"where (BOOKING_ORDER_STATUS="+DictCodeConstants.DICT_BOS_NOT_ENTER+" or BOOKING_ORDER_STATUS="+DictCodeConstants.DICT_BOS_DELAY_ENTER+" or BOOKING_ORDER_STATUS is null ) "+
				"group by DEALER_CODE,vin,BOOKING_ORDER_STATUS) m "+
				"on cc.vin=m.vin"+
				" and cc.DEALER_CODE = m.DEALER_CODE WHERE 1=1  " );
		this.setWhere(sql, queryParam, queryList);
		sql.append(" group by INSURATION_NAME,IS_VALID,DCRC_ADVISOR,BOOKING_ORDER_NO,BOOKING_COME_TIME,cc.phone, cc.mobile, cc.INSURANCE_END_DATE, cc.DELIVERER, cc.DELIVERER_PHONE, cc.DELIVERER_MOBILE," +
				" cc.SALES_DATE, cc.LICENSE,cc.OWNER_NAME,cc.CONTACTOR_PHONE, cc.CONTACTOR_MOBILE, cc.CONTACTOR_NAME, cc.CONTACTOR_ADDRESS," +
				" cc.CONTACTOR_ZIP_CODE, cc.REMIND_DATE, cc.REMINDER,CUSTOMER_FEEDBACK,cc.REMIND_CONTENT, cc.REMIND_FAIL_REASON, cc.REMIND_STATUS," +
				" cc.REMINDER_NAME, cc.VIN,it.INSURANCE_TYPE_NAME, cc.OWNER_NO, cc.OWNER_PROPERTY, cc.BRAND, cc.SERIES, cc.INSURANCE_BEGIN_DATE, cc.ADDRESS," +
				" cc.MAINTAIN_ADVISOR, cc.MODEL, cc.SERVICE_ADVISOR,cc.is_return_factory,cc.DEALER_CODE " );
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("REMIND_STATUS") != null && map.get("REMIND_STATUS") != "") {
				if (Integer.parseInt(
						map.get("REMIND_STATUS").toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN) {
					map.put("REMIND_STATUS", "继续提醒");
				} else if (Integer.parseInt(
						map.get("REMIND_STATUS").toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_FAIL) {
					map.put("REMIND_STATUS", "失败结束提醒");
				} else if (Integer.parseInt(map.get("REMIND_STATUS")
						.toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_SUCCESS) {
					map.put("REMIND_STATUS", "成功结束提醒");
				}
			}
			if (map.get("IS_VALID") != null && map.get("IS_VALID") != "") {
				if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_VALID", "是");
				} else if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_VALID", "否");
				}
			}

		}
		return list;
	}

	/**
	 * 回显查询车主信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryOwnerNoByid(String id) throws ServiceBizException {
		List<Map> list = queryQwnerBy(id);
		Map map = list.get(0);
		String ownerNo = "";
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,ve.*,ve.ENGINE_NO,A.OWNER_NAME FROM (" + CommonConstants.VM_OWNER + ") A "
				+ " LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO "
				+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE= A.DEALER_CODE AND ve.OWNER_NO=A.OWNER_NO "
				+ " WHERE A.DEALER_CODE = '" + dealerCode + "'  AND  A.OWNER_NO = '" + ownerNo + "' ");
		System.out.println(sql.toString());
		List<Map> resultList = Base.findAll(sql.toString());
		return resultList;
	}
	
	/**
	 * 根据车主编号进行回显 
	 * 
	 * @author Administrator
	 * @date 2017年3月31日
	 * @param id
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private List<Map> queryQwnerBy(String id) {
		String sb=new String(" SELECT  ow.OWNER_NO, ow.OWNER_NAME, ow.OWNER_SPELL, ow.GENDER, ow.CT_CODE, ow.CERTIFICATE_NO, ow.PROVINCE, ow.CITY, ow.DISTRICT, ow.ADDRESS, ow.ZIP_CODE, ow.INDUSTRY_FIRST," 
				+ " ow.INDUSTRY_SECOND, ow.TAX_NO, ow.PRE_PAY, ow.ARREARAGE_AMOUNT, ow.CUS_RECEIVE_SORT,ve.* "
				+ " FROM tm_owner ow LEFT JOIN tm_vehicle ve ON ow.dealer_code=ve.dealer_code AND ow.owner_no=ve.owner_no"
				+ " WHERE ow.owner_no ='"+id+"'	AND 1=1	 ");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * 修改车主
	 */
	@Override
	public void updateOwner(String ownerNo, OwnerDTO ownerDTO) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		CarownerPO ownerPO = CarownerPO.findByCompositeKeys(ownerDTO.getOwnerNo(),dealerCode);
		settOwnerPo(ownerPO, ownerDTO);
		ownerPO.saveIt();
		List<OwnerMemoPO> findBySQL = OwnerMemoPO.findBySQL("SELECT * FROM TM_OWNER_MEMO WHERE OWNER_NO = ? ", ownerNo);
		if (findBySQL.size() > 0) {
			OwnerMemoPO ownerMemoPO = findBySQL.get(0);
			ownerMemoPO.setString("MEMO_INFO", ownerDTO.getOwnerMemo());
			ownerMemoPO.saveIt();
		} else {
			OwnerMemoPO ownerMemoPO = new OwnerMemoPO();
			ownerMemoPO.setString("MEMO_INFO", ownerDTO.getOwnerMemo());
			ownerMemoPO.setString("OWNER_NO", ownerDTO.getOwnerNo());
			ownerMemoPO.saveIt();
		}
	}
	
	/**
	 * 设置车主属性
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param tmVehiclePO
	 * @param tmVehicleDTO
	 * @param b
	 */
	private void settOwnerPo(CarownerPO ownerPO, OwnerDTO ownerDTO) {

		ownerPO.setInteger("OWNER_PROPERTY", ownerDTO.getOwnerProperty());
		ownerPO.setString("OWNER_NAME", ownerDTO.getOwnerName());
		ownerPO.setString("OWNER_SPELL", ownerDTO.getOwnerSpell());
		ownerPO.setInteger("GENDER", ownerDTO.getGender());
		ownerPO.setInteger("CT_CODE", ownerDTO.getCtCode());
		ownerPO.setString("CERTIFICATE_NO", ownerDTO.getCertificateNo());
		ownerPO.setInteger("PROVINCE", ownerDTO.getProvince());
		ownerPO.setInteger("city", ownerDTO.getCity());
		ownerPO.setInteger("DISTRICT", ownerDTO.getDistrict());
		ownerPO.setString("ADDRESS", ownerDTO.getAddress());
		ownerPO.setString("ZIP_CODE", ownerDTO.getZipCode());
		ownerPO.setInteger("INDUSTRY_FIRST", ownerDTO.getIndustryFirst());
		ownerPO.setInteger("INDUSTRY_SECOND", ownerDTO.getIndustrySecond());
		ownerPO.setString("TAX_NO", ownerDTO.getTaxNo());
		ownerPO.setDouble("PRE_PAY", ownerDTO.getPrePay());
		ownerPO.setDouble("ARREARAGE_AMOUNT", ownerDTO.getArrearageAmount());
		ownerPO.setDouble("CUS_RECEIVE_SORT", ownerDTO.getCusReceiveSort());
		ownerPO.setString("CERTIFICATE_NO", ownerDTO.getCertificateNo());
		ownerPO.setString("SECOND_ADDRESS", ownerDTO.getSecondAddress());
		ownerPO.setString("PHONE", ownerDTO.getPhone());
		ownerPO.setString("E_MAIL", ownerDTO.getEMail());
		ownerPO.setDate("BIRTHDAY", ownerDTO.getBirthday());
		ownerPO.setString("MOBILE", ownerDTO.getMobile());
		ownerPO.setDouble("FAMILY_INCOME", ownerDTO.getFamilyIncome());
		ownerPO.setDouble("OWNER_MARRIAGE", ownerDTO.getOwnerMarriage());
		ownerPO.setDouble("EDU_LEVEL", ownerDTO.getEduLevel());
		ownerPO.setString("HOBBY", ownerDTO.getHobby());
		ownerPO.setString("CONTACTOR_NAME", ownerDTO.getContactorName());
		ownerPO.setInteger("CONTACTOR_GENDER", ownerDTO.getContactorGender());
		ownerPO.setString("CONTACTOR_PHONE", ownerDTO.getContactorPhone());
		ownerPO.setString("CONTACTOR_EMAIL", ownerDTO.getContactorEmail());
		ownerPO.setString("CONTACTOR_FAX", ownerDTO.getContactorFax());
		ownerPO.setString("CONTACTOR_MOBILE", ownerDTO.getContactorMobile());
		ownerPO.setInteger("CONTACTOR_PROVINCE", ownerDTO.getContactorProvince());
		ownerPO.setString("CONTACTOR_CITY", ownerDTO.getContactorCity());
		ownerPO.setString("CONTACTOR_DISTRICT", ownerDTO.getContactorDistrict());
		ownerPO.setString("CONTACTOR_ADDRESS", ownerDTO.getContactorAddress());
		ownerPO.setString("CONTACTOR_ZIP_CODE", ownerDTO.getContactorZipCode());
		ownerPO.setInteger("CONTACTOR_HOBBY_CONTACT", ownerDTO.getContactorHobbyContact());
		ownerPO.setInteger("CONTACTOR_VOCATION_TYPE", ownerDTO.getContactorVocationType());
		ownerPO.setInteger("CONTACTOR_POSITION", ownerDTO.getContactorPosition());
		ownerPO.setString("REMARK", ownerDTO.getRemark());

	}

	/**
	 * 修改车辆信息
	 */
	@Override
	public void updateVehicle(String vin, TmVehicleDTO tmVehicleDTO) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VehiclePO vehiclePO =VehiclePO.findByCompositeKeys(vin, dealerCode);
		setVehiclePo(vehiclePO, tmVehicleDTO);
		vehiclePO.saveIt();
		List<VehicleMemoPO> findBySQL = VehiclePO.findBySQL("SELECT * FROM TM_VEHICLE_MEMO WHERE VIN = ? ", vin);
		if (findBySQL.size() > 0) {
			VehicleMemoPO vehicleMemoPO = findBySQL.get(0);
			vehicleMemoPO.setString("MEMO_INFO", tmVehicleDTO.getMemoInfo());
			vehicleMemoPO.saveIt();
		} else {
			VehicleMemoPO vehicleMemoPO = new VehicleMemoPO();
			vehicleMemoPO.setString("MEMO_INFO", tmVehicleDTO.getMemoInfo());
			vehicleMemoPO.setString("VIN", tmVehicleDTO.getVin());
			vehicleMemoPO.saveIt();
		}
	}
	
	/**
	 * 设置车辆属性
	 * @param vehiclePO
	 * @param tmVehicleDTO
	 */
	private void setVehiclePo(VehiclePO vehiclePO, TmVehicleDTO tmVehicleDTO) {
		vehiclePO.setString("ENGINE_NO", tmVehicleDTO.getEngineNo());
		vehiclePO.setString("EXHAUST_QUANTITY", tmVehicleDTO.getExhaustQuantity());
		vehiclePO.setDate("PRODUCT_DATE", tmVehicleDTO.getProductDate());
		vehiclePO.setString("BRAND", tmVehicleDTO.getBrand());
		vehiclePO.setString("APACKAGE", tmVehicleDTO.getApAckAge());
		vehiclePO.setString("ENGINE_NO_OLD", tmVehicleDTO.getEngineNoOld());
		vehiclePO.setString("SHIFT_TYPE", tmVehicleDTO.getShifeType());
		vehiclePO.setInteger("FUEL_TYPE", tmVehicleDTO.getFuelType());
		vehiclePO.setInteger("BUSINESS_KIND", tmVehicleDTO.getBusinessKind());
		vehiclePO.setInteger("VEHICLE_PURPOSE", tmVehicleDTO.getVehiclePurpose());
		vehiclePO.setString("GEAR_BOX", tmVehicleDTO.getGearBox());
		vehiclePO.setString("KEY_NUMBER", tmVehicleDTO.getKeynumber());
		vehiclePO.setString("BRAND_MODEL", tmVehicleDTO.getBrandModel());
		vehiclePO.setString("INNER_COLOR", tmVehicleDTO.getInnerColor());

		vehiclePO.setString("INSURANCE_ADVISOR", tmVehicleDTO.getInsuranceAdvisor());
		vehiclePO.setString("MAINTAIN_ADVISOR", tmVehicleDTO.getMaintainAdvisor());
		vehiclePO.setString("PRODUCTING_AREA", tmVehicleDTO.getProductingArea());
		vehiclePO.setString("DCRC_ADVISOR", tmVehicleDTO.getDcrcAdvisor());

		vehiclePO.setString("VSN", tmVehicleDTO.getVsn());
		vehiclePO.setInteger("DISCHARGE_STANDARD", tmVehicleDTO.getDischargeStandrd());
		vehiclePO.setInteger("WAYS_TO_BUY", tmVehicleDTO.getWaysToBuy());
//		vehiclePO.setString("DEALER_SHORTNAME", tmVehicleDTO.getDealercode());
		// vehiclePO.setString("USER_NAME", tmVehicleDTO.get));
		vehiclePO.setString("SALES_MILEAGE", tmVehicleDTO.getSalesMileage());
		vehiclePO.setDate("LICENSE_DATE", tmVehicleDTO.getLicenseDate());
		vehiclePO.setInteger("IS_SELF_COMPANY", tmVehicleDTO.getIsSelfCompany());
		vehiclePO.setDate("BUSINESS_DATE", tmVehicleDTO.getBusinessDate());
		vehiclePO.setString("CONTRACT_NO", tmVehicleDTO.getContractNo());
		vehiclePO.setDate("CONTRACT_DATE", tmVehicleDTO.getContractDate());
		vehiclePO.setString("VIP_NO", tmVehicleDTO.getVipNo());
		vehiclePO.setString("DISCOUNT_MODE_CODE", tmVehicleDTO.getDiscountModeCode());
		vehiclePO.setString("DELIVERER", tmVehicleDTO.getDeliverer());
		vehiclePO.setString("DELIVERER_GENDER", tmVehicleDTO.getDelivererGender());
		vehiclePO.setString("DELIVERER_RELATION_TO_OWNER", tmVehicleDTO.getDelivererRelationToOwner());
		vehiclePO.setInteger("DELIVERER_HOBBY_CONTACT", tmVehicleDTO.getDelivererHobbyContact());
		vehiclePO.setString("DELIVERER_PHONE", tmVehicleDTO.getDelivererPhone());
		vehiclePO.setString("DELIVERER_MOBILE", tmVehicleDTO.getDelivererMobile());
		vehiclePO.setString("DELIVERER_CREDIT", tmVehicleDTO.getDelivererCredit());
		vehiclePO.setString("ZIP_CODE", tmVehicleDTO.getZipCode());
		vehiclePO.setDouble("IS_TRACE", tmVehicleDTO.getIstrace());
		vehiclePO.setDate("TRACE_TIME", tmVehicleDTO.getTraceTime());
		vehiclePO.setString("DELIVERER_ADDRESS", tmVehicleDTO.getDelivererAddress());
		vehiclePO.setString("DELIVERER_COMPANY", tmVehicleDTO.getDelivererCompany());
		vehiclePO.setString("REMARK", tmVehicleDTO.getRemark());
	}

	/**
	 * 查询保险保修
	 */
	@Override
	public PageInfoDto queryInsuranceTypeNameInfo(Map<String, String> queryParam, String vin)
			throws ServiceBizException, SQLException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT * FROM TT_SERVICE_INSURANCE WHERE VIN = '"+vin+"' ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 根据VIN、车主编号回显提醒
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryVehicelByid(String vin, String ownerNo) throws ServiceBizException {
		List<Map> list = queryBusinessType(vin, ownerNo);

		Map map = list.get(0);
		vin = "";
		ownerNo = "";
		if (map.get("VIN") != null && !map.get("VIN").equals("")) {
			vin = map.get("VIN").toString();
		}
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT ter.DEALER_CODE,ter.REMINDER,ve.`INSURANCE_END_DATE`,ti.`INSURATION_NAME` FROM  TT_INSURANCE_EXPIRE_REMIND ter LEFT JOIN tm_vehicle ve  ON ter.`DEALER_CODE`=ve.dealer_code "
				+ "AND ter.vin=ve.`VIN` LEFT JOIN tm_owner ow ON ow.`DEALER_CODE`=ter.`DEALER_CODE` AND ter.`OWNER_NO`=ow.`OWNER_NO` LEFT JOIN tm_insurance	ti ON ti.`DEALER_CODE`=ter.`DEALER_CODE` AND ti.`VER`=ter.`VER`"
				+ " AND ve.`INSURATION_CODE`=ti.`INSURATION_CODE` "
				+ " WHERE ter.DEALER_CODE = '"+dealerCode+"'   AND ter.VIN = '"+vin+"'  AND ow.OWNER_NO = '"+ownerNo+"' ");
//		System.err.println(sql.toString());
		List<Map> resultList = DAOUtil.findAll(sql.toString(),null);
		return resultList;
	}
	
	/**
	 * 根据VIN、车主编号回显提醒
	 * @param vin
	 * @param ownerNo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> queryBusinessType(String vin, String ownerNo) {

		String sb = new String("SELECT  ow.`OWNER_NAME`,ve.`VIN`,ow.OWNER_NO  FROM ("+CommonConstants.VM_VEHICLE+") ve  LEFT JOIN ("+CommonConstants.VM_OWNER+") ow ON ow.`DEALER_CODE`=ve.`DEALER_CODE` AND ow.`OWNER_NO`=ve.`OWNER_NO` "
				+ " WHERE ve.vin = '"+vin+"'  AND ow.OWNER_NO ='"+ownerNo+"' ");
//		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		queryList.add(ownerNo);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * 新增提醒记录
	 */
	@Override
	public void addeRemind(String vins, String ownerNo, InsuranceExpireRemindDTO insuranceExpireRemindDTO)
			throws ServiceBizException {

		InsuranceExpireRemindPO insuranceExpireRemindPO = new InsuranceExpireRemindPO();// 新增
		setvehiclePo(insuranceExpireRemindPO,insuranceExpireRemindDTO);
	}
	
	/**
	 * 设置提醒表属性
	 * 
	 * @author Administrator
	 * @param insuranceExpireRemindPO
	 * @param insuranceExpireRemindDTO
	 * @param flag
	 */

	private void setvehiclePo(InsuranceExpireRemindPO insuranceExpireRemindPO, InsuranceExpireRemindDTO insuranceExpireRemindDTO) {

		insuranceExpireRemindPO.setString("VIN", insuranceExpireRemindDTO.getVin());
		insuranceExpireRemindPO.setString("OWNER_NO", insuranceExpireRemindDTO.getOwnerNo());
		insuranceExpireRemindPO.setString("REMIND_CONTENT", insuranceExpireRemindDTO.getRemindContent());// 提醒内容
		insuranceExpireRemindPO.setString("CUSTOMER_FEEDBACK", insuranceExpireRemindDTO.getCustomerFeedback());// 客户反馈
		insuranceExpireRemindPO.setString("REMIND_WAY", insuranceExpireRemindDTO.getRemindWay());// 提醒方式
		insuranceExpireRemindPO.set("REMIND_DATE", new Timestamp(System.currentTimeMillis()));// 提醒时间
		insuranceExpireRemindPO.setString("LAST_TAG", DictCodeConstants.IS_YES);// 最新记录标识
		insuranceExpireRemindPO.setInteger("REMIND_STATUS", insuranceExpireRemindDTO.getRemindStatus());// 提醒状态
		insuranceExpireRemindPO.setString("REMARK", insuranceExpireRemindDTO.getRemark()); // 备注
		insuranceExpireRemindPO.setInteger("RENEWAL_FAILED_REASON", insuranceExpireRemindDTO.getRenewlFailedReason());//续保战败原因
		insuranceExpireRemindPO.setString("RENEWAL_REMARK", insuranceExpireRemindDTO.getRenewalRemark());// 战败备注
		insuranceExpireRemindPO.setDate("RENEWAL_FAILED_DATE", insuranceExpireRemindDTO.getRenewalFailedDate());// 续保战败日期
		insuranceExpireRemindPO.saveIt();

	}

	/**
	 * 根据vin分页查询提醒记录
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public PageInfoDto queryRemindre(String vin, String ownerNo) throws ServiceBizException {
		List<Object> queryList = new ArrayList<>();
		List<Map> list = queryBusinessType(vin, ownerNo);
		Map map = list.get(0);
		vin = "";
		ownerNo = "";
		if (map.get("VIN") != null && !map.get("VIN").equals("")) {
			vin = map.get("VIN").toString();
		}
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.*,V.NEW_VEHICLE_DATE, V.NEW_VEHICLE_MILEAGE, V.LICENSE, F.OWNER_NAME,db.DEALER_SHORTNAME,tu.USER_NAME , CASE WHEN E.DEALER_CODE != '"
						+ dealerCode + "'  THEN ''  ELSE E.EMPLOYEE_NAME  END EMPLOYEE_NAME FROM " + " ( SELECT " );
//						+ "( DictCodeConstants.DICT_REMINDER_TYPE_VERYCAR_ATTERM "
//						+ " ) AS REMIND_TYPE,REMIND_ID, OWNER_NO,VIN,REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER,REMIND_WAY,"
//						+ " LAST_TAG, REMIND_STATUS, VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE,0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_VEHICLE_REMIND A  WHERE A.DEALER_CODE IN "
//						+ " (SELECT  SHARE_ENTITY  FROM ( " + CommonConstants.VM_ENTITY_SHARE_WITH
//						+ ") vm2 WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//						+ DictCodeConstants.D_KEY + "  ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
		sb.append(" ( " + DictCodeConstants.DICT_REMINDER_TYPE_INSURANCE_ATTERM
				+ ") AS REMIND_TYPE,REMIND_ID,OWNER_NO,VIN,REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER, REMIND_WAY,LAST_TAG,REMIND_STATUS, "
				+ " VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE,A.RENEWAL_REMIND_STATUS,A.RENEWAL_FAILED_REASON,A.RENEWAL_REMARK, A.RENEWAL_FAILED_DATE AS RENEWAL_FAILED_DATE  FROM TT_INSURANCE_EXPIRE_REMIND A  WHERE A.DEALER_CODE IN "
				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
				+ ") vm1  WHERE DEALER_CODE = '" + dealerCode + "' AND biz_code = 'TT_ALL_REMIND') "
				+ "  AND A.D_KEY = " + DictCodeConstants.D_KEY + " ");
		if (vin != null && !vin.equals("") && ownerNo != null && !ownerNo.equals("")) {
			sb.append(" AND A.VIN = '" + vin + "' AND A.OWNER_NO = '" + ownerNo + "' ");
		}
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_NEW_CAR + ") AS REMIND_TYPE,"
//				+ " REMIND_ID, OWNER_NO,VIN, REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER, REMIND_WAY,LAST_TAG,REMIND_STATUS,VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE, "
//				+ " 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_NEWVEHICLE_REMIND A  WHERE A.DEALER_CODE IN  (SELECT  SHARE_ENTITY "
//				+ " FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH + ") vm3  WHERE DEALER_CODE = '" + dealerCode
//				+ "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = " + DictCodeConstants.D_KEY + " ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_OWNER_BIRTHDAY
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER, "
//				+ " '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_OWNER_BIRTHDAY_REMIND A  WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm4  WHERE DEALER_CODE = '" + dealerCode + "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " ");

		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_CUSTOMER_LOSE
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,  REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER, '' AS LAST_NEXT_MAINTAIN_DATE, "
//				+ " 0 AS LAST_NEXT_MAINTAIN_MILEAGE,  A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS,  0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_VEHICLE_LOSS_REMIND A  WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm5  WHERE DEALER_CODE = '" + dealerCode + "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " UNION SELECT ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" (" + DictCodeConstants.DICT_REMINDER_TYPE_TERMLY_MAINTAIN
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK,"
//				+ " REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER,  A.LAST_NEXT_MAINTAIN_DATE AS LAST_NEXT_MAINTAIN_DATE, LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE,  0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM "
//				+ " TT_TERMLY_MAINTAIN_REMIND A   WHERE A.DEALER_CODE IN  (SELECT  SHARE_ENTITY  FROM ("
//				+ CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " " );
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append("  UNION SELECT  (" + DictCodeConstants.DICT_REMINDER_TYPE_GUAR_ATTERM
//				+ ") AS REMIND_TYPE, REMIND_ID,  OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT,  CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS,"
//				+ " VER, '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_REPAIR_EXPIRE_REMIND A   WHERE A.DEALER_CODE IN "
//				+ "  (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm6  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY ="
//				+ DictCodeConstants.D_KEY + " ");

		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append("  UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_YEAR_CHECK
//				+ ") AS REMIND_TYPE,  REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS,"
//				+ " VER, '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,  '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_CHECK_EXPIRE_REMIND A WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm7 WHERE DEALER_CODE = '" + dealerCode + "'   AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY ="
//				+ DictCodeConstants.D_KEY + " ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
		sb.append(" ) A LEFT JOIN TM_DEALER_BASICINFO db  ON A.dealer_code = db.dealer_code "
				+ " LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") V  ON V.VIN = A.VIN  AND V.DEALER_CODE = '"
				+ dealerCode + "' " + " LEFT JOIN (" + CommonConstants.VM_OWNER + ") F  ON F.DEALER_CODE = '"
				+ dealerCode + "'  AND V.OWNER_NO = F.OWNER_NO "
				+ " LEFT JOIN TM_EMPLOYEE E  ON E.DEALER_CODE = A.DEALER_CODE  AND E.EMPLOYEE_NO = A.REMINDER "
				+ " LEFT JOIN tm_user tu  ON A.DEALER_CODE = tu.DEALER_CODE ");
		sb.append("  WHERE 1=1 AND A.VIN='"+vin+"' AND A.owner_no ='"+ownerNo+"' "  );
		sb.append(" GROUP BY A.REMIND_ID ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 根据提醒编号进行回显查询提醒记录信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryByRemindID(String remindId) throws ServiceBizException {
		InsuranceExpireRemindPO insuranceExpireRemindPO = InsuranceExpireRemindPO.findById(remindId);
		List<Map> list = new ArrayList<Map>();
		list.add(insuranceExpireRemindPO.toMap());
		return list;
	}

	/**
	 * 修改提醒记录
	 */
	@Override
	public void modifyRemindID(String id,InsuranceExpireRemindDTO insuranceExpireRemindDTO )
			throws ServiceBizException {

		InsuranceExpireRemindPO insuranceExpireRemindPO = InsuranceExpireRemindPO.findById(insuranceExpireRemindDTO.getRemindId());
		insuranceExpireRemindPO.setString("CUSTOMER_FEEDBACK",insuranceExpireRemindDTO.getCustomerFeedback());
		insuranceExpireRemindPO.setString("REMARK", insuranceExpireRemindDTO.getRemark());
		insuranceExpireRemindPO.saveIt();
	
	}

	/**
	 * 查询销售回访
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		stringBuffer.append(" SELECT aa.*,C.OWNER_NAME,C.PHONE,C.MOBILE,B.BRAND,B.SERIES,B.MODEL from (SELECT '定期保养提醒' description,cast(D.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" D.DEALER_CODE,D.OWNER_NO,D.REMIND_DATE,D.REMIND_CONTENT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_STATUS,D.VIN ");
		stringBuffer.append(" FROM  TT_VEHICLE_LOSS_REMIND D where D.D_KEY = 0 ");
			stringBuffer.append(" AND D.DEALER_CODE  = ?  ");
			queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (D.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or D.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" union all SELECT  '保修到期提醒' description,  cast(D.REMIND_ID as char(14))  REMIND_ID, "
				+ " D.DEALER_CODE, D.OWNER_NO, D.REMIND_DATE, D.REMIND_CONTENT,D.REMINDER, D.CUSTOMER_FEEDBACK,"
				+ " D.REMIND_STATUS, D.VIN FROM   TT_REPAIR_EXPIRE_REMIND D where D.D_KEY = 0 AND D.DEALER_CODE  = '"+dealerCode+"'\n" );
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (D.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or D.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		
		
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" ) aa left join ("+ CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE and B.VIN = aa.VIN left join ");
		stringBuffer.append(" ("+ CommonConstants.VM_OWNER +") C");
		stringBuffer.append(" on aa.DEALER_CODE = c.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO ");
		stringBuffer.append(" union all SELECT '客户生日提醒' description,cast(B.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" A.DEALER_CODE,B.OWNER_NO,B.REMIND_DATE,B.REMIND_CONTENT,B.REMINDER,B.CUSTOMER_FEEDBACK,B.REMIND_STATUS,B.VIN,A.CUSTOMER_NAME OWNER_NAME,A.CONTACTOR_PHONE PHONE, ");
		stringBuffer.append(" A.CONTACTOR_MOBILE MOBILE,C.BRAND,C.SERIES,C.MODEL FROM TM_POTENTIAL_CUSTOMER A LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE ");
		
		stringBuffer.append(" AND A.CUSTOMER_NO = B.OWNER_NO left join ("+ CommonConstants.VM_VEHICLE + ") C on B.DEALER_CODE = a.DEALER_CODE and C.VIN = B.VIN WHERE A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or B.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		stringBuffer.append(" AND A.D_KEY=0 AND A.CUSTOMER_TYPE=10181001 ");
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" union all SELECT '销售回访结果' description,cast(A.TRACE_TASK_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.CUSTOMER_NO OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT,");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.CUSTOMER_NAME OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,A.BRAND, ");
		stringBuffer.append(" A.SERIES,A.MODEL FROM  TT_SALES_TRACE_TASK A LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
		stringBuffer.append(" Left join (select distinct Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_SALES_TRACE_TASK_QUESTION Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE) ");
		stringBuffer.append(" QU on QU.TRACE_TASK_ID=A.TRACE_TASK_ID WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.CUSTOMER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		stringBuffer.append(" union all SELECT '回访结果' description,A.RO_NO REMIND_ID,A.DEALER_CODE,H.OWNER_NO,A.INPUT_DATE REMIND_DATE,A.REMARK REMIND_CONTENT,A.TRANCER REMINDER, ");
		stringBuffer.append(" '' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.OWNER_NAME,A.DELIVERER_MOBILE PHONE,A.DELIVERER_PHONE MOBILE,A.BRAND,A.SERIES,A.MODEL FROM  TT_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE ");
		stringBuffer.append(" AND TECH.RO_NO=E.RO_NO AND TECH.D_KEY=0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO ");
		stringBuffer.append(" AND A.DEALER_CODE=F.DEALER_CODE WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (H.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
		stringBuffer.append(" union all SELECT '流失报警回访结果' description,cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT, ");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,F.OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,H.BRAND,H.SERIES,H.MODEL FROM  TT_LOSS_VEHICLE_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN  ");
		stringBuffer.append(" AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE Left join(select distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
		stringBuffer.append(" from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_LOSS_VHCL_TRCE_TASK_QN Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE ) QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID ");
		stringBuffer.append(" WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}
	
	

}
