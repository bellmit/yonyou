package com.yonyou.dms.customer.service.customerManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.javalite.activejdbc.Base;

import com.yonyou.dms.common.domains.PO.basedata.SalesTraceAnswerDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesTraceTaskLogPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesTraceTaskQuestionPO;
import com.yonyou.dms.common.domains.PO.basedata.TraceTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesTraceTaskPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.QuestionnaireInputDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SalesTraceResultInputServiceImpl implements SalesTraceResultInputService {
	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto querySalesTraceResultInput(Map<String, String> queryParam) throws ServiceBizException {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT AAA.*,FF.USER_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.EMPLOYEE_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  "
				+ DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,E.QUESTIONNAIRE_CODE,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME " + " from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE
				+ ")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE
				+ ") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE 1=1 ");

		/*if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
			sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO + "  or A.TRACE_STATUS = "
					+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
		} else {*/
			Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
		//}

		Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
		Utility.sqlToEquals(sb, queryParam.get("trancer"), "TRANCER", "A");
		Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
		Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
		Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
				"CONFIRMED_DATE", "B");
		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union " + "  (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE 1=1  ");
			/*if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
				sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO
						+ "  or A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
			} else {*/
				Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
			//}
			Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
			Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
			Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
			Utility.sqlToEquals(sb, queryParam.get("trancer"), "TRANCER", "A");
			Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
			Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
			Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
					"CONFIRMED_DATE", "A2");

			sb.append(" ) ");

		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_EMPLOYEE EE ON B.TRANCER = EE.EMPLOYEE_NO " + "  ) AAA");
		sb.append("  LEFT JOIN TM_USER FF ON AAA.SOLD_BY = FF.USER_ID ");

		System.out.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println(IfUnion);
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();

		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
		return id;
	}

	/**
	 * excel导出查询方法
	 * 
	 * @author wangxin
	 * @date 2017年1月4日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#querySafeToExport(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = null;
		List<Object> queryList = new ArrayList<Object>();

		StringBuffer sb = new StringBuffer();
		/*sb.append(
				"SELECT AAA.*,FF.USER_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.USER_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
		sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT);
		sb.append(" END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME " + " from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
		sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
		sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE 1=1 ");

		if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
			sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO + "  or A.TRACE_STATUS = "
					+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
		} else {
			Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
		}
		Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
		Utility.sqlToLike(sb, queryParam.get("trancer"), "TRANCER", "A");
		Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
		Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
		Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
				"CONFIRMED_DATE", "B");
		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union   (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE 1=1  ");
			if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
				sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO
						+ "  or A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
			} else {
				Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
			}
			Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
			Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
			Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
			Utility.sqlToLike(sb, queryParam.get("trancer"), "TRANCER", "A");
			Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
			Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
			Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
					"CONFIRMED_DATE", "A2");

			sb.append(" ) ");

		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_USER EE ON B.TRANCER = EE.USER_ID " + "  ) AAA");
		sb.append("  LEFT JOIN TM_USER FF ON AAA.SOLD_BY = FF.USER_ID ");*/
		
		sb.append(
				"SELECT AAA.*,FF.USER_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.EMPLOYEE_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  "
				+ DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,E.QUESTIONNAIRE_CODE,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME " + " from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE
				+ ")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE
				+ ") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE 1=1 ");

		/*if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
			sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO + "  or A.TRACE_STATUS = "
					+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
		} else {*/
			Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
		//}

		Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
		Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
		Utility.sqlToEquals(sb, queryParam.get("trancer"), "TRANCER", "A");
		Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
		Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
		Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
				"CONFIRMED_DATE", "B");
		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union " + "  (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE 1=1  ");
			/*if (StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
				sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO
						+ "  or A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
			} else {*/
				Utility.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
			//}
			Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
			Utility.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
			Utility.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
			Utility.sqlToEquals(sb, queryParam.get("trancer"), "TRANCER", "A");
			Utility.sqlToLike(sb, queryParam.get("questionnaire_name"), "QUESTIONNAIRE_NAME", "E");
			Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "SALES_DATE", "VE");
			Utility.sqlToDate(sb, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"),
					"CONFIRMED_DATE", "A2");

			sb.append(" ) ");

		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_EMPLOYEE EE ON B.TRANCER = EE.EMPLOYEE_NO " + "  ) AAA");
		sb.append("  LEFT JOIN TM_USER FF ON AAA.SOLD_BY = FF.USER_ID ");
		

		list = DAOUtil.findAll(sb.toString(), queryList);

		/*for (Map map : list) {
			if (map.get("GENDER") != null && map.get("GENDER") != "") {
				if (Integer.parseInt(map.get("GENDER").toString()) == 10061001) {
					map.put("GENDER", "男");
				} else if (Integer.parseInt(map.get("GENDER").toString()) == 10061002) {
					map.put("GENDER", "女");
				}
			}

			if (map.get("IS_SELECTED") != null && map.get("IS_SELECTED") != "") {
				if (Integer.parseInt(map.get("IS_SELECTED").toString()) == 12781001) {
					map.put("IS_SELECTED", "是");
				} else if (Integer.parseInt(map.get("IS_SELECTED").toString()) == 12781002) {
					map.put("IS_SELECTED", "否");
				}
			}

			if (map.get("TRACE_STATUS") != null && map.get("TRACE_STATUS") != "") {
				if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371001) {
					map.put("TRACE_STATUS", "未跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371002) {
					map.put("TRACE_STATUS", "继续跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371003) {
					map.put("TRACE_STATUS", "成功结束跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371004) {
					map.put("TRACE_STATUS", "失败结束跟踪");
				}
			}

			if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
				if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181001) {
					map.put("CUSTOMER_TYPE", "个人");
				} else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181002) {
					map.put("CUSTOMER_TYPE", "公司");
				}
			}

			if (map.get("OWNER_MARRIAGE") != null && map.get("OWNER_MARRIAGE") != "") {
				if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11191001) {
					map.put("OWNER_MARRIAGE", "未婚");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11191002) {
					map.put("OWNER_MARRIAGE", "已婚");
				}
			}

			if (map.get("AGE_STAGE") != null && map.get("AGE_STAGE") != "") {
				if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421001) {
					map.put("AGE_STAGE", "18岁以下");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421002) {
					map.put("AGE_STAGE", "18岁－24岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421003) {
					map.put("AGE_STAGE", "25岁－34岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421004) {
					map.put("AGE_STAGE", "35岁－44岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421005) {
					map.put("AGE_STAGE", "45岁－64岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421006) {
					map.put("AGE_STAGE", "65岁以上");
				}
			}

			if (map.get("EDUCATION_LEVEL") != null && map.get("EDUCATION_LEVEL") != "") {
				if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161001) {
					map.put("EDUCATION_LEVEL", "小学");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161002) {
					map.put("EDUCATION_LEVEL", "初中");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161003) {
					map.put("EDUCATION_LEVEL", "高中/中专/技校");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161004) {
					map.put("EDUCATION_LEVEL", "大专");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161005) {
					map.put("EDUCATION_LEVEL", "大本");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161006) {
					map.put("EDUCATION_LEVEL", "硕士或以上");
				}
			}

			if (map.get("INDUSTRY_FIRST") != null && map.get("INDUSTRY_FIRST") != "") {
				if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011001) {
					map.put("INDUSTRY_FIRST", "公检法司");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011002) {
					map.put("INDUSTRY_FIRST", "机关");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011003) {
					map.put("INDUSTRY_FIRST", "事业");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011004) {
					map.put("INDUSTRY_FIRST", "企业");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011005) {
					map.put("INDUSTRY_FIRST", "运营");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011006) {
					map.put("INDUSTRY_FIRST", "其他");
				}
			}

			if (map.get("INDUSTRY_SECOND") != null && map.get("INDUSTRY_SECOND") != "") {
				if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021001) {
					map.put("INDUSTRY_SECOND", "公安");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021002) {
					map.put("INDUSTRY_SECOND", "检察院");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021003) {
					map.put("INDUSTRY_SECOND", "法院");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021004) {
					map.put("INDUSTRY_SECOND", "司法");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021101) {
					map.put("INDUSTRY_SECOND", "安全");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021102) {
					map.put("INDUSTRY_SECOND", "政府");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021103) {
					map.put("INDUSTRY_SECOND", "党政人大政协");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021104) {
					map.put("INDUSTRY_SECOND", "军队武警消防");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021105) {
					map.put("INDUSTRY_SECOND", "工商");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021106) {
					map.put("INDUSTRY_SECOND", "税务");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021107) {
					map.put("INDUSTRY_SECOND", "海关");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021108) {
					map.put("INDUSTRY_SECOND", "质检药检");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021109) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021201) {
					map.put("INDUSTRY_SECOND", "医疗卫生");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021202) {
					map.put("INDUSTRY_SECOND", "科学教育");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021203) {
					map.put("INDUSTRY_SECOND", "协会");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021204) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021301) {
					map.put("INDUSTRY_SECOND", "金融");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021302) {
					map.put("INDUSTRY_SECOND", "能源");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021303) {
					map.put("INDUSTRY_SECOND", "商业");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021304) {
					map.put("INDUSTRY_SECOND", "制造");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021305) {
					map.put("INDUSTRY_SECOND", "交通");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021306) {
					map.put("INDUSTRY_SECOND", "通讯");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021307) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021401) {
					map.put("INDUSTRY_SECOND", "租赁");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021402) {
					map.put("INDUSTRY_SECOND", "驾校");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021403) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021501) {
					map.put("INDUSTRY_SECOND", "其他");
				}
			}

			if (map.get("VOCATION_TYPE") != null && map.get("VOCATION_TYPE") != "") {
				if (Integer.parseInt(map.get("VOCATION_TYPE").toString()) == 11111001) {
					map.put("VOCATION_TYPE", "私营公司老板/自由职业者/个体户");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111002) {
					map.put("VOCATION_TYPE", "中层管理人员 (如：部门经理等)");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111003) {
					map.put("VOCATION_TYPE", "销售人员/销售代表");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111004) {
					map.put("VOCATION_TYPE", "私营公司职员/主管");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111005) {
					map.put("VOCATION_TYPE", "家庭主妇/夫");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111006) {
					map.put("VOCATION_TYPE", "退休");
				}
			}

			if (map.get("HOBBY") != null && map.get("HOBBY") != "") {
				if (Integer.parseInt(map.get("HOBBY").toString()) == 11171001) {
					map.put("HOBBY", "高尔夫");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171002) {
					map.put("HOBBY", "音乐");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171003) {
					map.put("HOBBY", "旅行");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171004) {
					map.put("HOBBY", "看电影");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171005) {
					map.put("HOBBY", "羽毛球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171006) {
					map.put("HOBBY", "DIY");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171007) {
					map.put("HOBBY", "健身");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171008) {
					map.put("HOBBY", "飞行");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171009) {
					map.put("HOBBY", "足球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171010) {
					map.put("HOBBY", "园艺");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171011) {
					map.put("HOBBY", "绘画");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171012) {
					map.put("HOBBY", "橄榄球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171013) {
					map.put("HOBBY", "航海");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171014) {
					map.put("HOBBY", "游泳");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171015) {
					map.put("HOBBY", "网球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171016) {
					map.put("HOBBY", "美食");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171017) {
					map.put("HOBBY", "时尚");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171018) {
					map.put("HOBBY", "政治");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171019) {
					map.put("HOBBY", "读书");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171020) {
					map.put("HOBBY", "社交");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171021) {
					map.put("HOBBY", "看电视");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171022) {
					map.put("HOBBY", "戏剧");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171023) {
					map.put("HOBBY", "户外运动");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171024) {
					map.put("HOBBY", "自驾游");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171025) {
					map.put("HOBBY", "摄影");
				}
			}

			if (map.get("CUS_SOURCE") != null && map.get("CUS_SOURCE") != "") {
				if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111001) {
					map.put("CUS_SOURCE", "来店/展厅顾客");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111003) {
					map.put("CUS_SOURCE", "活动-展厅活动");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111004) {
					map.put("CUS_SOURCE", "保客增购");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111005) {
					map.put("CUS_SOURCE", "保客推荐");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111007) {
					map.put("CUS_SOURCE", "其他");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111008) {
					map.put("CUS_SOURCE", "陌生拜访/电话销售");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111012) {
					map.put("CUS_SOURCE", "网络/电子商务");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111013) {
					map.put("CUS_SOURCE", "路过");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111014) {
					map.put("CUS_SOURCE", "代理商/代销网点");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111015) {
					map.put("CUS_SOURCE", "来电顾客");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111016) {
					map.put("CUS_SOURCE", "DCC转入");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111017) {
					map.put("CUS_SOURCE", "活动-车展");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111018) {
					map.put("CUS_SOURCE", "活动-外场试驾活动");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111019) {
					map.put("CUS_SOURCE", "活动-巡展/外展");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111020) {
					map.put("CUS_SOURCE", "保客置换");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111021) {
					map.put("CUS_SOURCE", "官网客户");
				}
			}
		}*/

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> querySafeToExport1(String id) throws ServiceBizException {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = null;
		List<Object> queryList = new ArrayList<Object>();
		String[] ids = id.split(",");
		id = ids[0];
		System.out.println(id);

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT AAA.*,FF.USER_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.USER_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
		sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT);
		sb.append(" END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME " + " from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
		sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
		sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE A.VIN = '" + id + "' ");

		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union   (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE A.VIN = '" + id + "' ");

			sb.append(" ) ");

		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_USER EE ON B.TRANCER = EE.USER_ID " + "  ) AAA");
		sb.append("  LEFT JOIN TM_USER FF ON AAA.SOLD_BY = FF.USER_ID");

		list = DAOUtil.findAll(sb.toString(), queryList);
		System.err.println("导出SQL-------------------------:    " + sb.toString());
		for (Map map : list) {
			if (map.get("GENDER") != null && map.get("GENDER") != "") {
				if (Integer.parseInt(map.get("GENDER").toString()) == 10061001) {
					map.put("GENDER", "男");
				} else if (Integer.parseInt(map.get("GENDER").toString()) == 10061002) {
					map.put("GENDER", "女");
				}
			}

			if (map.get("IS_SELECTED") != null && map.get("IS_SELECTED") != "") {
				if (Integer.parseInt(map.get("IS_SELECTED").toString()) == 12781001) {
					map.put("IS_SELECTED", "是");
				} else if (Integer.parseInt(map.get("IS_SELECTED").toString()) == 12781002) {
					map.put("IS_SELECTED", "否");
				}
			}

			if (map.get("TRACE_STATUS") != null && map.get("TRACE_STATUS") != "") {
				if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371001) {
					map.put("TRACE_STATUS", "未跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371002) {
					map.put("TRACE_STATUS", "继续跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371003) {
					map.put("TRACE_STATUS", "成功结束跟踪");
				} else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371004) {
					map.put("TRACE_STATUS", "失败结束跟踪");
				}
			}

			if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
				if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181001) {
					map.put("CUSTOMER_TYPE", "个人");
				} else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181002) {
					map.put("CUSTOMER_TYPE", "公司");
				}
			}

			if (map.get("OWNER_MARRIAGE") != null && map.get("OWNER_MARRIAGE") != "") {
				if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11191001) {
					map.put("OWNER_MARRIAGE", "未婚");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11191002) {
					map.put("OWNER_MARRIAGE", "已婚");
				}
			}

			if (map.get("AGE_STAGE") != null && map.get("AGE_STAGE") != "") {
				if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421001) {
					map.put("AGE_STAGE", "18岁以下");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421002) {
					map.put("AGE_STAGE", "18岁－24岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421003) {
					map.put("AGE_STAGE", "25岁－34岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421004) {
					map.put("AGE_STAGE", "35岁－44岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421005) {
					map.put("AGE_STAGE", "45岁－64岁");
				} else if (Integer.parseInt(map.get("AGE_STAGE").toString()) == 13421006) {
					map.put("AGE_STAGE", "65岁以上");
				}
			}

			if (map.get("EDUCATION_LEVEL") != null && map.get("EDUCATION_LEVEL") != "") {
				if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161001) {
					map.put("EDUCATION_LEVEL", "小学");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161002) {
					map.put("EDUCATION_LEVEL", "初中");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161003) {
					map.put("EDUCATION_LEVEL", "高中/中专/技校");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161004) {
					map.put("EDUCATION_LEVEL", "大专");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161005) {
					map.put("EDUCATION_LEVEL", "大本");
				} else if (Integer.parseInt(map.get("EDUCATION_LEVEL").toString()) == 11161006) {
					map.put("EDUCATION_LEVEL", "硕士或以上");
				}
			}

			if (map.get("INDUSTRY_FIRST") != null && map.get("INDUSTRY_FIRST") != "") {
				if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011001) {
					map.put("INDUSTRY_FIRST", "公检法司");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011002) {
					map.put("INDUSTRY_FIRST", "机关");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011003) {
					map.put("INDUSTRY_FIRST", "事业");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011004) {
					map.put("INDUSTRY_FIRST", "企业");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011005) {
					map.put("INDUSTRY_FIRST", "运营");
				} else if (Integer.parseInt(map.get("INDUSTRY_FIRST").toString()) == 30011006) {
					map.put("INDUSTRY_FIRST", "其他");
				}
			}

			if (map.get("INDUSTRY_SECOND") != null && map.get("INDUSTRY_SECOND") != "") {
				if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021001) {
					map.put("INDUSTRY_SECOND", "公安");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021002) {
					map.put("INDUSTRY_SECOND", "检察院");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021003) {
					map.put("INDUSTRY_SECOND", "法院");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021004) {
					map.put("INDUSTRY_SECOND", "司法");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021101) {
					map.put("INDUSTRY_SECOND", "安全");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021102) {
					map.put("INDUSTRY_SECOND", "政府");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021103) {
					map.put("INDUSTRY_SECOND", "党政人大政协");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021104) {
					map.put("INDUSTRY_SECOND", "军队武警消防");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021105) {
					map.put("INDUSTRY_SECOND", "工商");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021106) {
					map.put("INDUSTRY_SECOND", "税务");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021107) {
					map.put("INDUSTRY_SECOND", "海关");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021108) {
					map.put("INDUSTRY_SECOND", "质检药检");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021109) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021201) {
					map.put("INDUSTRY_SECOND", "医疗卫生");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021202) {
					map.put("INDUSTRY_SECOND", "科学教育");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021203) {
					map.put("INDUSTRY_SECOND", "协会");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021204) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021301) {
					map.put("INDUSTRY_SECOND", "金融");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021302) {
					map.put("INDUSTRY_SECOND", "能源");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021303) {
					map.put("INDUSTRY_SECOND", "商业");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021304) {
					map.put("INDUSTRY_SECOND", "制造");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021305) {
					map.put("INDUSTRY_SECOND", "交通");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021306) {
					map.put("INDUSTRY_SECOND", "通讯");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021307) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021401) {
					map.put("INDUSTRY_SECOND", "租赁");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021402) {
					map.put("INDUSTRY_SECOND", "驾校");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021403) {
					map.put("INDUSTRY_SECOND", "其他");
				} else if (Integer.parseInt(map.get("INDUSTRY_SECOND").toString()) == 30021501) {
					map.put("INDUSTRY_SECOND", "其他");
				}
			}

			if (map.get("VOCATION_TYPE") != null && map.get("VOCATION_TYPE") != "") {
				if (Integer.parseInt(map.get("VOCATION_TYPE").toString()) == 11111001) {
					map.put("VOCATION_TYPE", "私营公司老板/自由职业者/个体户");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111002) {
					map.put("VOCATION_TYPE", "中层管理人员 (如：部门经理等)");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111003) {
					map.put("VOCATION_TYPE", "销售人员/销售代表");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111004) {
					map.put("VOCATION_TYPE", "私营公司职员/主管");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111005) {
					map.put("VOCATION_TYPE", "家庭主妇/夫");
				} else if (Integer.parseInt(map.get("OWNER_MARRIAGE").toString()) == 11111006) {
					map.put("VOCATION_TYPE", "退休");
				}
			}

			if (map.get("HOBBY") != null && map.get("HOBBY") != "") {
				if (Integer.parseInt(map.get("HOBBY").toString()) == 11171001) {
					map.put("HOBBY", "高尔夫");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171002) {
					map.put("HOBBY", "音乐");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171003) {
					map.put("HOBBY", "旅行");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171004) {
					map.put("HOBBY", "看电影");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171005) {
					map.put("HOBBY", "羽毛球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171006) {
					map.put("HOBBY", "DIY");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171007) {
					map.put("HOBBY", "健身");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171008) {
					map.put("HOBBY", "飞行");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171009) {
					map.put("HOBBY", "足球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171010) {
					map.put("HOBBY", "园艺");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171011) {
					map.put("HOBBY", "绘画");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171012) {
					map.put("HOBBY", "橄榄球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171013) {
					map.put("HOBBY", "航海");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171014) {
					map.put("HOBBY", "游泳");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171015) {
					map.put("HOBBY", "网球");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171016) {
					map.put("HOBBY", "美食");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171017) {
					map.put("HOBBY", "时尚");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171018) {
					map.put("HOBBY", "政治");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171019) {
					map.put("HOBBY", "读书");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171020) {
					map.put("HOBBY", "社交");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171021) {
					map.put("HOBBY", "看电视");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171022) {
					map.put("HOBBY", "戏剧");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171023) {
					map.put("HOBBY", "户外运动");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171024) {
					map.put("HOBBY", "自驾游");
				} else if (Integer.parseInt(map.get("HOBBY").toString()) == 11171025) {
					map.put("HOBBY", "摄影");
				}
			}

			if (map.get("CUS_SOURCE") != null && map.get("CUS_SOURCE") != "") {
				if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111001) {
					map.put("CUS_SOURCE", "来店/展厅顾客");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111003) {
					map.put("CUS_SOURCE", "活动-展厅活动");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111004) {
					map.put("CUS_SOURCE", "保客增购");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111005) {
					map.put("CUS_SOURCE", "保客推荐");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111007) {
					map.put("CUS_SOURCE", "其他");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111008) {
					map.put("CUS_SOURCE", "陌生拜访/电话销售");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111012) {
					map.put("CUS_SOURCE", "网络/电子商务");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111013) {
					map.put("CUS_SOURCE", "路过");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111014) {
					map.put("CUS_SOURCE", "代理商/代销网点");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111015) {
					map.put("CUS_SOURCE", "来电顾客");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111016) {
					map.put("CUS_SOURCE", "DCC转入");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111017) {
					map.put("CUS_SOURCE", "活动-车展");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111018) {
					map.put("CUS_SOURCE", "活动-外场试驾活动");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111019) {
					map.put("CUS_SOURCE", "活动-巡展/外展");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111020) {
					map.put("CUS_SOURCE", "保客置换");
				} else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 13111021) {
					map.put("CUS_SOURCE", "官网客户");
				}
			}

		}

		return list;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map> querySafeToExport2(String id, Map<String, String> queryParam) throws ServiceBizException {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = null;
		List<Object> queryList = new ArrayList<Object>();
		String[] ids = id.split(",");
		id = ids[0];
		System.out.println(id);

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT OO.*, BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME` FROM(select\n" + "aa.*,\n");
		sb.append("C.OWNER_NAME,\n" + "C.PHONE,\n" + "C.MOBILE,\n" + "B.BRAND,\n" + "B.SERIES,\n" + "B.MODEL\n");
		sb.append("from\n" + "(\n" + "-- 1. 定期保养提醒\n" + "SELECT\n" + "'定期保养提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_TERMLY_MAINTAIN_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 3.验车到期提醒\n" + "SELECT\n" + "'验车到期提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_VEHICLE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 4.新车提醒\n" + "SELECT\n" + "'新车提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_NEWVEHICLE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 5.车主生日函\n" + "SELECT\n" + "'车主生日函' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_OWNER_BIRTHDAY_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 6.客户流失报警\n" + "SELECT\n" + "'客户流失报警' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM   TT_VEHICLE_LOSS_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 7.保修到期提醒\n" + "SELECT\n" + "'保修到期提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM   TT_REPAIR_EXPIRE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append(") aa\n" + "left join (" + CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE\n");
		sb.append("and B.VIN = aa.VIN\n" + "left join  (" + CommonConstants.VM_OWNER);
		sb.append(") C on aa.DEALER_CODE = c.DEALER_CODE\n" + "AND B.OWNER_NO = C.OWNER_NO\n" + "\n" + "union all\n");
		sb.append("-- 8.客户生日提醒\n" + "SELECT\n" + "'客户生日提醒' description,\n");
		sb.append("cast(B.REMIND_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "B.OWNER_NO,\n");
		sb.append("B.REMIND_DATE,\n" + "B.REMIND_CONTENT,\n" + "B.REMINDER,\n" + "B.CUSTOMER_FEEDBACK,\n");
		sb.append("B.REMIND_STATUS,\n" + "B.VIN,\n" + "A.CUSTOMER_NAME OWNER_NAME,\n" + "A.CONTACTOR_PHONE PHONE,\n");
		sb.append("A.CONTACTOR_MOBILE MOBILE,\n" + "C.BRAND,\n" + "C.SERIES,\n" + "C.MODEL\n");
		sb.append("FROM TM_POTENTIAL_CUSTOMER A\n");
		sb.append("LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE\n");
		sb.append("AND A.CUSTOMER_NO = B.OWNER_NO\n" + "left join (" + CommonConstants.VM_VEHICLE);
		sb.append(") C on B.DEALER_CODE = a.DEALER_CODE\n" + "and C.VIN = B.VIN\n" + "WHERE 1=1\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (B.OWNER_NO = '" + id + "' or B.VIN = '" + id + "')");
		sb.append("AND A.D_KEY=0\n" + "AND A.CUSTOMER_TYPE=10181001\n");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "\n" + "-- 9.销售回访结果\n" + "SELECT\n" + "'销售回访结果' description,\n");
		sb.append(
				"cast(A.TRACE_TASK_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "A.CUSTOMER_NO OWNER_NO ,\n");
		sb.append("A.INPUT_DATE REMIND_DATE,\n" + "QU.QUESTIONNAIRE_NAME REMIND_CONTENT,\n" + "A.TRANCER REMINDER,\n");
		sb.append("'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n" + "A.CUSTOMER_NAME OWNER_NAME,\n");
		sb.append("E.CONTACTOR_PHONE PHONE,\n" + "E.CONTACTOR_MOBILE MOBILE,\n" + "A.BRAND,\n" + "A.SERIES,\n");
		sb.append("A.MODEL\n" + "FROM  TT_SALES_TRACE_TASK A\n");
		sb.append("LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("AND E.D_KEY = 0\n" + "LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "Left join\n" + "(\n");
		sb.append("   select\n" + "   distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME\n" + "   from (");
		sb.append(CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N\n");
		sb.append("   inner join TT_SALES_TRACE_TASK_QUESTION Q   on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE\n");
		sb.append(")\n" + "QU   on QU.TRACE_TASK_ID=A.TRACE_TASK_ID\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (A.CUSTOMER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append("union all\n" + "-- 10.回访结果查询\n" + "SELECT\n" + "'回访结果' description,\n" + "A.RO_NO REMIND_ID,\n");
		sb.append("A.DEALER_CODE,\n" + "H.OWNER_NO,\n" + "A.INPUT_DATE REMIND_DATE,\n" + "A.REMARK REMIND_CONTENT,\n");
		sb.append("A.TRANCER REMINDER,\n" + "'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n");
		sb.append("A.OWNER_NAME,\n" + "A.DELIVERER_MOBILE PHONE,\n" + "A.DELIVERER_PHONE MOBILE,\n" + "A.BRAND,\n");
		sb.append("A.SERIES,\n" + "A.MODEL\n" + "FROM  TT_TRACE_TASK A\n");
		sb.append("LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE\n" + "AND TECH.RO_NO=E.RO_NO\n");
		sb.append("AND TECH.D_KEY=0\n" + "LEFT JOIN  (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (H.OWNER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append("union all\n" + "\n" + "-- 11.流失报警回访结果\n" + "SELECT\n" + "'流失报警回访结果' description,\n");
		sb.append("cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "A.OWNER_NO ,\n");
		sb.append("A.INPUT_DATE REMIND_DATE,\n" + "QU.QUESTIONNAIRE_NAME REMIND_CONTENT,\n" + "A.TRANCER REMINDER,\n");
		sb.append("'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n" + "F.OWNER_NAME,\n");
		sb.append("E.CONTACTOR_PHONE PHONE,\n" + "E.CONTACTOR_MOBILE MOBILE,\n" + "H.BRAND,\n" + "H.SERIES,\n");
		sb.append("H.MODEL\n" + "FROM  TT_LOSS_VEHICLE_TRACE_TASK A\n");
		sb.append("LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("AND E.D_KEY = 0\n" + "LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN  (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "Left join\n" + "(\n");
		sb.append("   select\n" + "   distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME\n" + "   from (");
		sb.append(CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N\n");
		sb.append("   inner join TT_LOSS_VHCL_TRCE_TASK_QN Q   on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE\n" + ")\n");
		sb.append("QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (A.OWNER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append(" )  OO   " + " LEFT JOIN tm_brand bb ON OO.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON OO.brand = cc.BRAND_CODE AND OO.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON OO.brand = dd.BRAND_CODE AND OO.series = dd.`SERIES_CODE`  AND OO.model = dd.`MODEL_CODE`  ");

		list = DAOUtil.findAll(sb.toString(), queryList);

		return list;

	}

	@Override
	public List<Map> queryBigCustomerHistoryIntent(String id) throws ServiceBizException {

		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT AAA.*,FF.USER_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.USER_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
		sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT);
		sb.append(" END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME " + " from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
		sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
		sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE A.VIN = '" + id + "' ");

		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union    (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE A.VIN = '" + id + "' ");

			sb.append(" ) ");

		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_USER EE ON B.TRANCER = EE.USER_ID " + "  ) AAA");
		sb.append("  LEFT JOIN TM_USER FF ON AAA.SOLD_BY = FF.USER_ID");

		System.err.println(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		System.err.println(IfUnion);
		System.err.println("aslkdjalskjd" + sb.toString());
		List<Object> queryList = new ArrayList<Object>();

		List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
		return list;

	}

	@Override
	public PageInfoDto querysaleanddcrc(String id, Map<String, String> queryParam) throws ServiceBizException {
		String[] ids = id.split(",");
		id = ids[0];
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT OO.*, BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME` FROM(select\n" + "aa.*,\n");
		sb.append("C.OWNER_NAME,\n" + "C.PHONE,\n" + "C.MOBILE,\n" + "B.BRAND,\n" + "B.SERIES,\n" + "B.MODEL\n");
		sb.append("from\n" + "(\n" + "-- 1. 定期保养提醒\n" + "SELECT\n" + "'定期保养提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_TERMLY_MAINTAIN_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 3.验车到期提醒\n" + "SELECT\n" + "'验车到期提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_VEHICLE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 4.新车提醒\n" + "SELECT\n" + "'新车提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_NEWVEHICLE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 5.车主生日函\n" + "SELECT\n" + "'车主生日函' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM  TT_OWNER_BIRTHDAY_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 6.客户流失报警\n" + "SELECT\n" + "'客户流失报警' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM   TT_VEHICLE_LOSS_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "-- 7.保修到期提醒\n" + "SELECT\n" + "'保修到期提醒' description,\n");
		sb.append("cast(D.REMIND_ID as char(14))  REMIND_ID,\n" + "D.DEALER_CODE,\n" + "D.OWNER_NO,\n");
		sb.append("D.REMIND_DATE,\n" + "D.REMIND_CONTENT,\n" + "D.REMINDER,\n" + "D.CUSTOMER_FEEDBACK,\n");
		sb.append("D.REMIND_STATUS,\n" + "D.VIN\n" + "FROM   TT_REPAIR_EXPIRE_REMIND D\n" + "where D.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (D.OWNER_NO = '" + id + "' or D.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append(") aa\n" + "left join (" + CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE\n");
		sb.append("and B.VIN = aa.VIN\n" + "left join  (" + CommonConstants.VM_OWNER);
		sb.append(") C on aa.DEALER_CODE = c.DEALER_CODE\n" + "AND B.OWNER_NO = C.OWNER_NO\n" + "\n" + "union all\n");
		sb.append("-- 8.客户生日提醒\n" + "SELECT\n" + "'客户生日提醒' description,\n");
		sb.append("cast(B.REMIND_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "B.OWNER_NO,\n");
		sb.append("B.REMIND_DATE,\n" + "B.REMIND_CONTENT,\n" + "B.REMINDER,\n" + "B.CUSTOMER_FEEDBACK,\n");
		sb.append("B.REMIND_STATUS,\n" + "B.VIN,\n" + "A.CUSTOMER_NAME OWNER_NAME,\n" + "A.CONTACTOR_PHONE PHONE,\n");
		sb.append("A.CONTACTOR_MOBILE MOBILE,\n" + "C.BRAND,\n" + "C.SERIES,\n" + "C.MODEL\n");
		sb.append("FROM TM_POTENTIAL_CUSTOMER A\n");
		sb.append("LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE\n");
		sb.append("AND A.CUSTOMER_NO = B.OWNER_NO\n" + "left join (" + CommonConstants.VM_VEHICLE);
		sb.append(") C on B.DEALER_CODE = a.DEALER_CODE\n" + "and C.VIN = B.VIN\n" + "WHERE 1=1\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (B.OWNER_NO = '" + id + "' or B.VIN = '" + id + "')");
		sb.append("AND A.D_KEY=0\n" + "AND A.CUSTOMER_TYPE=10181001\n");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "REMIND_DATE", "");
		sb.append("union all\n" + "\n" + "-- 9.销售回访结果\n" + "SELECT\n" + "'销售回访结果' description,\n");
		sb.append(
				"cast(A.TRACE_TASK_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "A.CUSTOMER_NO OWNER_NO ,\n");
		sb.append("A.INPUT_DATE REMIND_DATE,\n" + "QU.QUESTIONNAIRE_NAME REMIND_CONTENT,\n" + "A.TRANCER REMINDER,\n");
		sb.append("'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n" + "A.CUSTOMER_NAME OWNER_NAME,\n");
		sb.append("E.CONTACTOR_PHONE PHONE,\n" + "E.CONTACTOR_MOBILE MOBILE,\n" + "A.BRAND,\n" + "A.SERIES,\n");
		sb.append("A.MODEL\n" + "FROM  TT_SALES_TRACE_TASK A\n");
		sb.append("LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("AND E.D_KEY = 0\n" + "LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "Left join\n" + "(\n");
		sb.append("   select\n" + "   distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME\n" + "   from (");
		sb.append(CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N\n");
		sb.append("   inner join TT_SALES_TRACE_TASK_QUESTION Q   on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE\n");
		sb.append(")\n" + "QU   on QU.TRACE_TASK_ID=A.TRACE_TASK_ID\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (A.CUSTOMER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append("union all\n" + "-- 10.回访结果查询\n" + "SELECT\n" + "'回访结果' description,\n" + "A.RO_NO REMIND_ID,\n");
		sb.append("A.DEALER_CODE,\n" + "H.OWNER_NO,\n" + "A.INPUT_DATE REMIND_DATE,\n" + "A.REMARK REMIND_CONTENT,\n");
		sb.append("A.TRANCER REMINDER,\n" + "'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n");
		sb.append("A.OWNER_NAME,\n" + "A.DELIVERER_MOBILE PHONE,\n" + "A.DELIVERER_PHONE MOBILE,\n" + "A.BRAND,\n");
		sb.append("A.SERIES,\n" + "A.MODEL\n" + "FROM  TT_TRACE_TASK A\n");
		sb.append("LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE\n" + "AND TECH.RO_NO=E.RO_NO\n");
		sb.append("AND TECH.D_KEY=0\n" + "LEFT JOIN  (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (H.OWNER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append("union all\n" + "\n" + "-- 11.流失报警回访结果\n" + "SELECT\n" + "'流失报警回访结果' description,\n");
		sb.append("cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,\n" + "A.DEALER_CODE,\n" + "A.OWNER_NO ,\n");
		sb.append("A.INPUT_DATE REMIND_DATE,\n" + "QU.QUESTIONNAIRE_NAME REMIND_CONTENT,\n" + "A.TRANCER REMINDER,\n");
		sb.append("'' CUSTOMER_FEEDBACK,\n" + "0 REMIND_STATUS,\n" + "A.VIN,\n" + "F.OWNER_NAME,\n");
		sb.append("E.CONTACTOR_PHONE PHONE,\n" + "E.CONTACTOR_MOBILE MOBILE,\n" + "H.BRAND,\n" + "H.SERIES,\n");
		sb.append("H.MODEL\n" + "FROM  TT_LOSS_VEHICLE_TRACE_TASK A\n");
		sb.append("LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO\n" + "AND A.DEALER_CODE=E.DEALER_CODE\n");
		sb.append("AND E.D_KEY = 0\n" + "LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN\n");
		sb.append("AND A.DEALER_CODE = H.DEALER_CODE\n" + "LEFT JOIN  (" + CommonConstants.VM_OWNER);
		sb.append(") F ON H.OWNER_NO=F.OWNER_NO\n" + "AND A.DEALER_CODE=F.DEALER_CODE\n" + "Left join\n" + "(\n");
		sb.append("   select\n" + "   distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME\n" + "   from (");
		sb.append(CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N\n");
		sb.append("   inner join TT_LOSS_VHCL_TRCE_TASK_QN Q   on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE\n" + ")\n");
		sb.append("QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID\n" + "WHERE  A.D_KEY = 0\n");
		if (id != null && !id.equals(""))
			sb.append(" AND (A.OWNER_NO = '" + id + "' or A.VIN = '" + id + "')");
		Utility.sqlToDate(sb, queryParam.get("beginRemindDate"), queryParam.get("endRemindDate"), "INPUT_DATE", "A");
		sb.append(" )  OO   " + " LEFT JOIN tm_brand bb ON OO.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON OO.brand = cc.BRAND_CODE AND OO.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON OO.brand = dd.BRAND_CODE AND OO.series = dd.`SERIES_CODE`  AND OO.model = dd.`MODEL_CODE`  ");

		System.out.println(
				"------------------------------------------------------------------------------------------------------");
		System.out.println(sb.toString());

		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	@Override
	public List<Map> qrySalesConsultant(String orgCode) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder(
				"select QUESTIONNAIRE_CODE,QUESTIONNAIRE_NAME,DEALER_CODE from TT_TRACE_QUESTIONNAIRE");
		List<Object> params = new ArrayList<Object>();

		List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
		return list;
	}

	@Override
	public List<Map> queryQusetionnaire(String id1, String id2, String id3, String id4) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		System.err.println("id1 :"+id1);
		System.err.println("id2 :"+id2);
		System.err.println("id3 :"+id3);
		System.err.println("id4 :"+id4);
		sb.append("SELECT DISTINCT D.QUESTIONNAIRE_CODE,D.DEALER_CODE,D.QUESTIONNAIRE_NAME,D.QUESTIONNAIRE_TYPE,");
		sb.append(" C.QUESTION_CODE,C.ANSWER_GROUP_NO,C.QUESTION_NAME,C.QUESTION_CONTENT,E.TRACE_TASK_QUESTION_ID,");
		sb.append(
				"C.QUESTION_TYPE,Coalesce(C.SORT_NUM,0) AS SORT_NUM,C.IS_MUST_FILLED,E.`ANSWER`,E.TRACE_TASK_ID,E.REMARK   FROM TT_TRACE_QUESTIONNAIRE D  ");
		sb.append("LEFT JOIN   (SELECT A.QUESTIONNAIRE_CODE AS QUESTIONNAIRE_CODE,A.SORT_NUM,B.DEALER_CODE,");
		sb.append("B.QUESTION_CODE AS QUESTION_CODE ,B.ANSWER_GROUP_NO AS ANSWER_GROUP_NO,");
		sb.append(
				"B.QUESTION_NAME AS QUESTION_NAME,B.QUESTION_CONTENT AS QUESTION_CONTENT,B.QUESTION_TYPE AS  QUESTION_TYPE,");
		sb.append("B.IS_MUST_FILLED AS IS_MUST_FILLED FROM TT_QUESTION_RELATION A LEFT JOIN TT_TRACE_QUESTION B  ON A.QUESTION_CODE=B.QUESTION_CODE AND ");
		sb.append("A.DEALER_CODE=B.DEALER_CODE WHERE 1=1 ");
		if ("12371003".equals(id2) || "12371004".equals(id2)) {
		} else {
			sb.append(" AND B.IS_VALID= 12781001 ");
		}
		sb.append(" ) C  ON C.QUESTIONNAIRE_CODE=D.QUESTIONNAIRE_CODE  ");
		if (!StringUtils.isNullOrEmpty(id3)) {
			sb.append(" LEFT JOIN TT_SALES_TRACE_TASK_QUESTION E ON E.TRACE_TASK_ID = '" + id3 + "'");
			sb.append(" AND D.QUESTIONNAIRE_CODE = E.`QUESTIONNAIRE_CODE` ");
			sb.append(" AND C.QUESTION_CODE = E.`QUESTION_CODE` ");
		}
		if ("12371003".equals(id2) || "12371004".equals(id2)) {
			sb.append(
					" right join TT_SALES_TRACE_TASK_QUESTION G on D.DEALER_CODE=G.DEALER_CODE AND  C.QUESTION_CODE = g.QUESTION_CODE ");
			sb.append("WHERE D.IS_SERVICE_QUESTIONNAIRE = 12781002 AND G.TRACE_TASK_ID =  " + id2 + " ");
		}
		if ("12371001".equals(id2) || "12371002".equals(id2)) {
			sb.append(" WHERE D.IS_VALID= 12781001 AND D.IS_SERVICE_QUESTIONNAIRE = 12781002");
		}
		sb.append(" AND not C.QUESTION_CODE IS NULL and d.QUESTIONNAIRE_TYPE!=11311003 ");
		if (!StringUtils.isNullOrEmpty(id1)) {
			if (!id1.equals("空") && !id1.equals("-1")) {
				sb.append(" AND D.QUESTIONNAIRE_CODE ='" + id1 + " '");
			} else {
				sb.append(" AND 1=1");
			}
		}
		sb.append(" order by  D.QUESTIONNAIRE_CODE,SORT_NUM desc");

		System.err.println("问卷SQL 123123:   " + sb.toString());
		System.err.println("前台传值测试" + id2);

		List<Object> queryList = new ArrayList<Object>();

		List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
		for (int i = 0; i < list.size(); i++) {
			String answer = "";
			Map vv = list.get(i);
			String sql = "select * from tt_answer where ANSWER_GROUP_NO = '" + vv.get("ANSWER_GROUP_NO") + "'";

			System.err.println("问卷答案SQL:   " + sql.toString());
			List<Object> queryList1 = new ArrayList<Object>();
			List<Map> mm = DAOUtil.findAll(sql.toString(), queryList1);
			for (int j = 0; j < mm.size(); j++) {
				Map ans = mm.get(j);
				if (j < (mm.size() - 1)) {
					answer += ans.get("ANSWER") + ",";
				} else if (j == (mm.size() - 1)) {
					answer += ans.get("ANSWER");
				}

			}
			vv.put("ANSWER_ALL", answer);
		}
		return list;
	}

	@Override
	public Map<String, Object> findById(String id, String id1) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		/*sb.append(
				"SELECT AAA.*,FF.EMPLOYEE_NAME AS SOLD_NAME FROM ( select distinct B.*,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME`,EE.EMPLOYEE_NAME  from((select ");
		sb.append(" A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE, ");
		sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
		sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
		sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT);
		sb.append(" END AS IS_SELECTED,  ");
		sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
		sb.append(" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
		sb.append(
				" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,E.QUESTIONNAIRE_CODE,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
		sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME, D.QUESTION_CODE ");
		sb.append(" from TT_SALES_TRACE_TASK A ");
		sb.append(" inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
		sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
		sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(
				" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
		sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
		sb.append(" WHERE 1=1 ");
		sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO + "  or A.TRACE_STATUS = "
				+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
		Utility.sqlToEquals(sb, id, "TRACE_TASK_ID", "A");
		Utility.sqlToEquals(sb, id1, "QUESTIONNAIRE_NAME", "E");
		sb.append(")");
		String IfUnion = Utility.getDefaultValue("1234");
		if (IfUnion.equals("12781001")) {
			sb.append(" union  (select ");
			sb.append(
					" A.DEALER_CODE,B.SEC_SO_NO as SO_NO,A.CUSTOMER_NO as PO_CUSTOMER_NO, 13001001 as BUSINESS_TYPE, ");
			sb.append(" CASE WHEN (A.TRACE_STATUS=" + DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END + ") OR (  ");
			sb.append(" A.TRACE_STATUS= " + DictCodeConstants.DICT_TRACING_STATUS_FAIL_END + ") THEN  ");
			sb.append(DictCodeConstants.IS_YES + " " + " ELSE  " + DictCodeConstants.IS_NOT + " END AS IS_SELECTED,  ");
			sb.append(" A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL, ");
			sb.append(
					" A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY, ");
			sb.append(
					" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,E.QUESTIONNAIRE_CODE,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
			sb.append(" ,a2.CONFIRMED_DATE,OWNER_MARRIAGE,a2.TAKE_CAR_MAN as CONTACTOR_NAME ");
			sb.append(" from TT_SALES_TRACE_TASK  A ");
			sb.append(
					" inner join TT_SEC_SALES_ORDER_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SEC_SO_NO ");
			sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO ");
			sb.append(" inner join  (" + CommonConstants.VM_VEHICLE);
			sb.append(")  ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
			sb.append(
					" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
			sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
			sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
			sb.append(
					" left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = B.DEALER_CODE AND a2.SEC_SO_NO=B.SEC_SO_NO ");
			sb.append(
					" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE  ) F  ");
			sb.append("  ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE ");
			sb.append(" WHERE 1=1  ");

			sb.append(" AND (A.TRACE_STATUS = " + DictCodeConstants.DICT_TRACING_STATUS_NO + "  or A.TRACE_STATUS = "
					+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE + ")");
			Utility.sqlToEquals(sb, id, "TRACE_TASK_ID", "A");
			Utility.sqlToEquals(sb, id1, "QUESTIONNAIRE_NAME", "E");
			sb.append(" ) ");
		}
		sb.append(" )  B   " + " LEFT JOIN tm_brand bb ON B.brand = bb.BRAND_CODE ");
		sb.append(" LEFT JOIN tm_series cc ON B.brand = cc.BRAND_CODE AND B.series = cc.`SERIES_CODE`   ");
		sb.append(
				" LEFT JOIN tm_model dd ON B.brand = dd.BRAND_CODE AND B.series = dd.`SERIES_CODE`  AND B.model = dd.`MODEL_CODE`  ");
		sb.append(" LEFT JOIN TM_EMPLOYEE EE ON B.TRANCER = EE.EMPLOYEE_NO " + "  ) AAA");
		sb.append("  LEFT JOIN TM_EMPLOYEE FF ON AAA.SOLD_BY = FF.EMPLOYEE_NO  limit 0,1");*/
		
		sb.append(" SELECT E.QUESTIONNAIRE_CODE,A.TRACE_STATUS,A.DEALER_CODE,A.TRACE_TASK_ID,"
				+ " D.QUESTION_CODE FROM TT_SALES_TRACE_TASK A  ");
		sb.append(
				" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE"
				+ " AND A.TRACE_TASK_ID = D.TRACE_TASK_ID ");
		sb.append(" left JOIN (" + CommonConstants.VT_TRACE_QUESTIONNAIRE);
		sb.append(") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
		sb.append(" WHERE 1=1  ");
		Utility.sqlToEquals(sb, id, "TRACE_TASK_ID", "A");
		if("{[QUESTIONNAIRE_CODE]}".equals(id1)){
			Utility.sqlToEquals(sb, "", "QUESTIONNAIRE_CODE", "E");
		}else{
			Utility.sqlToEquals(sb, id1, "QUESTIONNAIRE_CODE", "E");
		}
		System.err.println("123123123:   " + sb.toString());

		return DAOUtil.findFirst(sb.toString(), null);
	}

	@Override
	public String updateTracingtaskSales(String id, QuestionnaireInputDTO questionnaireInputDTO)
			throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtSalesTraceTaskPO ttPo = TtSalesTraceTaskPO.findByCompositeKeys(dealerCode, id);
		if (!StringUtils.isNullOrEmpty(questionnaireInputDTO.getTaskRemark())) {
			ttPo.setString("TASK_REMARK", questionnaireInputDTO.getTaskRemark());
		}
		if (!StringUtils.isNullOrEmpty(questionnaireInputDTO.getTraceStatus())) {
			ttPo.setString("TRACE_STATUS", questionnaireInputDTO.getTraceStatus());
		}
		// ttPo.setString("INPUTER", loginInfo.getEmployeeNo());
		ttPo.setDate("INPUT_DATE", new Date());
		ttPo.saveIt();

		for (Map map : questionnaireInputDTO.getDms_table2()) {
			String troubleShooting = null;
			String sortNum = null;
			String aQuestionCode = null;
			String aAnswerNo = null;
			System.err.println(map);
			System.err.println("TRACE_TASK_ID :  " + map.get("TRACE_TASK_ID"));
			System.err.println("QUESTION_CODE :  " + map.get("QUESTION_CODE"));
			List<Map> list = this.queryTraceTaskQuestoryAndDetailSales(id, map.get("QUESTION_CODE").toString());
			for (Map map2 : list) {
				if (list.size() > 0) {
					if (!StringUtils.isNullOrEmpty(map2.get("TROUBLE_SHOOTING"))) {
						troubleShooting = map2.get("TROUBLE_SHOOTING").toString();
					}
					if (!StringUtils.isNullOrEmpty(map2.get("SORT_NUM"))) {
						sortNum = map2.get("SORT_NUM").toString();
					}
					if (!StringUtils.isNullOrEmpty(map2.get("QUESTION_CODE"))) {
						aQuestionCode = map2.get("QUESTION_CODE").toString();
					}
					if (!StringUtils.isNullOrEmpty(map2.get("ANSWER_NO"))) {
						aAnswerNo = map2.get("ANSWER_NO").toString();
					}
				}
			}
			if (map.get("TRACE_TASK_QUESTION_ID") != null) {
				SalesTraceAnswerDetailPO sadPo = SalesTraceAnswerDetailPO.findById(map.get("TRACE_TASK_QUESTION_ID"));
				if (!StringUtils.isNullOrEmpty(sadPo)) {
					sadPo.delete();
				}
				TtSalesTraceTaskPO ttPo1 = TtSalesTraceTaskPO.findByCompositeKeys(dealerCode, id);
				ttPo1.saveIt();
			}
			if (map.get("QUESTION_CODE") != null) {
				Long traceTaskQuestionId = 0L;
				// 检查问题是否存在00000000000000000000000000000000000000
				if (!StringUtils.isNullOrEmpty(map.get("TRACE_TASK_QUESTION_ID"))
						&& !"0".equals(map.get("TRACE_TASK_QUESTION_ID"))) {
					traceTaskQuestionId = Long.parseLong(map.get("TRACE_TASK_QUESTION_ID").toString());
					SalesTraceTaskQuestionPO taskpo = SalesTraceTaskQuestionPO.findByCompositeKeys(dealerCode, id,
							traceTaskQuestionId);
					if (!StringUtils.isNullOrEmpty(map.get("ANSWER"))) {
						taskpo.setString("ANSWER", map.get("ANSWER").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("REMARK"))) {
						taskpo.setString("REMARK", map.get("REMARK").toString());
					}
					taskpo.saveIt();
				} else {
					SalesTraceTaskQuestionPO taskaddpo = new SalesTraceTaskQuestionPO();
					traceTaskQuestionId = commonNoService.getId("TT_SALES_TRACE_TASK_QUESTION");
					taskaddpo.setString("DEALER_CODE", dealerCode);
					taskaddpo.setString("TRACE_TASK_QUESTION_ID", traceTaskQuestionId);
					taskaddpo.setString("TRACE_TASK_ID", id);
					if (!StringUtils.isNullOrEmpty(map.get("QUESTIONNAIRE_CODE"))) {
						taskaddpo.setString("QUESTIONNAIRE_CODE", map.get("QUESTIONNAIRE_CODE").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("QUESTION_CODE"))) {
						taskaddpo.setString("QUESTION_CODE", map.get("QUESTION_CODE").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("ANSWER_NAME"))) {
						taskaddpo.setString("ANSWER", map.get("ANSWER_NAME").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("REMARK"))) {
						taskaddpo.setString("REMARK", map.get("REMARK").toString());
					}
					if (!StringUtils.isNullOrEmpty(troubleShooting)) {
						taskaddpo.setString("TROUBLE_SHOOTING", troubleShooting);
					}
					if (!StringUtils.isNullOrEmpty(sortNum)) {
						taskaddpo.setString("SORT_NUM", sortNum);
					}
					taskaddpo.saveIt();
				}

				SalesTraceAnswerDetailPO detailpo = new SalesTraceAnswerDetailPO();
				if (!StringUtils.isNullOrEmpty(aQuestionCode) && !aQuestionCode.equals("0")) {
					if (map.get("QUESTION_CODE").toString().equals(aQuestionCode)) {
						Long detailId = commonNoService.getId("TT_SALES_TRACE_ANSWER_DETAIL");
						detailpo.setString("DEALER_CODE", dealerCode);
						detailpo.setString("TRACE_ANSWER_DETAIL_ID", detailId);
						detailpo.setString("TRACE_TASK_QUESTION_ID", traceTaskQuestionId);
						if (!StringUtils.isNullOrEmpty(aAnswerNo)) {
							detailpo.setString("ANSWER_NO", aAnswerNo);
						}
						detailpo.setString("TRACE_TASK_ID", map.get("TRACE_TASK_ID").toString());
						detailpo.saveIt();
					}
				}
			}

		}
		for (Map mapT3 : questionnaireInputDTO.getDms_table3()) {
			String tracingTaskLogIds = null;
			String logDesc = null;
			String logDate = null;
			String updateStatus = null;
			if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_TASK_LOG_ID"))) {
				tracingTaskLogIds = mapT3.get("TRACE_TASK_LOG_ID").toString();
			}
			if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_LOG_DESC"))) {
				logDesc = mapT3.get("TRACE_LOG_DESC").toString();
			}
			if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_LOG_DATE"))) {
				logDate = mapT3.get("TRACE_LOG_DATE").toString();
			}
			if (!StringUtils.isNullOrEmpty(mapT3.get("UPDATE_STATUS"))) {
				updateStatus = mapT3.get("UPDATE_STATUS").toString();
			}
			if (!StringUtils.isNullOrEmpty(updateStatus)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				if ("U".equals(updateStatus)) {
					SalesTraceTaskLogPO po = SalesTraceTaskLogPO.findById(tracingTaskLogIds);
					try {
						po.setDate("TRACE_LOG_DATE", sdf.parse(logDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_LOG_DESC"))) {
						po.setString("TRACE_LOG_DESC", logDesc);
					}
					po.saveIt();

				}
				if ("A".equals(updateStatus)) {
					SalesTraceTaskLogPO po = new SalesTraceTaskLogPO();
					Long traceTaskLogId = commonNoService.getId("TT_SALES_TRACE_TASK_LOG");
					po.setString("DEALER_CODE", dealerCode);
					po.setString("TRACE_TASK_ID", id);
					po.setString("TRACE_TASK_LOG_ID", traceTaskLogId);
					try {
						po.setDate("TRACE_LOG_DATE", sdf.parse(logDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_LOG_DESC"))) {
						po.setString("TRACE_LOG_DESC", logDesc);
					}
					System.err.println("跟踪记录新增PO: " + po);
					po.insert();
					mapT3.put("TRACE_TASK_LOG_ID", traceTaskLogId);
					System.err.println("跟踪记录新增PO: " + po.getLongId());
				}

			} else {
				SalesTraceTaskLogPO po1 = SalesTraceTaskLogPO.findById(tracingTaskLogIds);

				if (!po1.getString("TRACE_LOG_DATE").toString().equals(logDate)
						|| po1.getString("TRACE_LOG_DESC").toString().equals(logDesc)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SalesTraceTaskLogPO po = SalesTraceTaskLogPO.findById(tracingTaskLogIds);
					try {
						po.setDate("TRACE_LOG_DATE", sdf.parse(logDate));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					if (!StringUtils.isNullOrEmpty(mapT3.get("TRACE_LOG_DESC"))) {
						po.setString("TRACE_LOG_DESC", logDesc);
					}
					po.saveIt();
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT TRACE_TASK_LOG_ID,DEALER_CODE FROM TT_SALES_TRACE_TASK_LOG WHERE TRACE_TASK_ID = '" + id + "'");
		List<Map> list = Base.findAll(sb.toString());
		System.err.println("table3比较ID: " + list);
		List<Map> list1 = questionnaireInputDTO.getDms_table3();
		List<String> list2 = new ArrayList<String>();
		for (int k = 0; k < list1.size(); k++) {
			if (!StringUtils.isNullOrEmpty(list1.get(k).get("TRACE_TASK_LOG_ID"))) {
				String str = list1.get(k).get("TRACE_TASK_LOG_ID").toString();
				list2.add(str);
			}
		}
		for (int j = 0; j < list.size(); j++) {
			String ttLogid = list.get(j).get("TRACE_TASK_LOG_ID").toString();
			if (list2.contains(ttLogid)) {
				System.err.println("ok" + ttLogid);
			} else {
				System.err.println("不ok" + ttLogid);
				SalesTraceTaskLogPO po = SalesTraceTaskLogPO.findById(ttLogid);
				po.delete();

				List<TraceTaskPO> ttlist = TraceTaskPO.find("DEALER_CODE = ? AND TRACE_GROUP_ID = ?", dealerCode,
						ttLogid);
				for (int s = 0; s < ttlist.size(); s++) {
					ttlist.get(s).saveIt();
				}
			}
		}

		return id;
	}

	@Override
	public List<Map> querySalesTraceTaskLog(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SALES_TRACE_TASK_LOG WHERE TRACE_TASK_ID = '" + id + "'");
		System.err.println(" 表3SQL:  " + sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	public List<Map> queryTraceTaskQuestoryAndDetailSales(String traceTaskId, String quesCode)
			throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				" SELECT A.TRACE_TASK_ID,A.DEALER_CODE,A.TRACE_TASK_QUESTION_ID,A.QUESTIONNAIRE_CODE,A.QUESTION_CODE,A.ANSWER, ");
		sql.append(" A.SORT_NUM,A.REMARK,A.TROUBLE_SHOOTING,B.TRACE_ANSWER_DETAIL_ID,B.ANSWER_NO ");
		sql.append(" FROM TT_SALES_TRACE_TASK_QUESTION A ");
		sql.append(
				" LEFT JOIN TT_SALES_TRACE_TASK D ON A.DEALER_CODE=D.DEALER_CODE   AND D.TRACE_TASK_ID= A.TRACE_TASK_ID ");
		sql.append(
				" LEFT JOIN  TT_TRACE_QUESTION C ON  A.QUESTION_CODE = C.QUESTION_CODE AND A.DEALER_CODE=C.DEALER_CODE ");
		sql.append(" LEFT JOIN TT_SALES_TRACE_ANSWER_DETAIL B ON A.DEALER_CODE=B.DEALER_CODE ");
		sql.append(" AND A.TRACE_TASK_QUESTION_ID=B.TRACE_TASK_QUESTION_ID  WHERE A.TRACE_TASK_ID = ");
		sql.append(traceTaskId + " AND A.DEALER_CODE = '" + dealerCode + "'" + " AND C.IS_VALID = 12781001 ");
		sql.append(" AND  A.`QUESTION_CODE` = '" + quesCode + "'");
		System.err.println("我在寻找答案: " + sql.toString());
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		return list;
	}

}
