package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.SalesTraceTaskDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesTraceTaskPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SalesTraceTaskServiceImpl implements SalesTraceTaskService {
	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto querySalesTraceTask(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pagedto = null;
		String sqlTmp = "";
		List<Object> queryList = new ArrayList<Object>();
		if (queryParam.get("isDistribute") == null || queryParam.get("isDistribute").equals("")) {
			StringBuffer sb = new StringBuffer(" select aa.*   ,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME` ");
			sb.append(" ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION ");
			sb.append(" from ( (SELECT TRANCER,QUESTIONNAIRE_NAME,BRAND_CODE,SERIES_CODE,MODEL_CODE,VIN, ");
			sb.append(
					"SO_NO,BUSINESS_TYPE,STOCK_OUT_DATE,CUSTOMER_NO,CONFIRMED_DATE,CUSTOMER_NAME,CUSTOMER_STATUS, CUSTOMER_TYPE, ");
			sb.append(
					"GENDER,PROVINCE,CITY,DISTRICT,ADDRESS,CONTACTOR_PHONE,fpcs,hfcs,CONTACTOR_MOBILE,SALES_DATE,DEALER_CODE ");
			sb.append(
					"FROM(select if(@pdept = concat(b.VIN,b.SO_NO,b.dealer_code), @rank := @rank + 1,  @rank := 1) as snid,@pdept:=concat(b.VIN,b.SO_NO,b.dealer_code),@rownum:=@rownum+1,  b.* ");
			sb.append("FROM ");
			sb.append(" (SELECT ");
			sb.append(" '' AS TRANCER, ");
			sb.append(" '' AS QUESTIONNAIRE_NAME,A.DEALER_CODE,");
			sb.append("( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE,");
			sb.append("( select C.SERIES_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE,");
			sb.append("( select C.MODEL_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append("A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,A.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS, ");
			sb.append("d.CREATED_AT RELA_CREATE_DATE,");
			sb.append("B.CONTACTOR_PHONE,");

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" 0 as fpcs,0 as hfcs,");
			} else {
				sb.append(
						"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and SO_NO=a.SO_NO and vin=a.vin)  as fpcs,");
				sb.append(
						"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and ");
				sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			}

			sb.append(" B.CONTACTOR_MOBILE,(select E.SALES_DATE from (" + CommonConstants.VM_VEHICLE);
			sb.append(") E where E.DEALER_CODE = D.DEALER_CODE ");
			sb.append("AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN) AS SALES_DATE  FROM TT_SALES_ORDER A ");
			sb.append(
					"inner join TT_PO_CUS_RELATION D on  A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY AND A.CUSTOMER_NO = D.PO_CUSTOMER_NO ");
			sb.append("AND A.VIN = D.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B  on  B.DEALER_CODE = D.DEALER_CODE AND B.D_KEY = D.D_KEY AND B.CUSTOMER_NO = D.CUSTOMER_NO ");
			sb.append(" where  1=1 " + " AND A.BUSINESS_TYPE not in(13001005,13001004)");
			sb.append(" AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035 ) ");

			if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and a.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and (select count(E.SALES_DATE) from (" + CommonConstants.VM_VEHICLE
						+ ") E where E.DEALER_CODE = D.DEALER_CODE ");
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
				sb.append(" AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN)>0 ");
			}

			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and b.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and a.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781001")) {// 分配为是
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n"
						+ "where ta.DEALER_CODE=a.DEALER_CODE \n " + "and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n"
						+ ")>0 ");
			} else if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n"
						+ "where ta.DEALER_CODE=a.DEALER_CODE \n" + "and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n"
						+ ")<=0 ");
			}
			sb.append(""
					+ " order by concat(a.VIN,A.SO_NO,A.dealer_code) asc,  d.CREATEd_at desc) b,   (select @rownum := 0, @pdept := null, @rank := 0) a");
			sb.append(") NN WHERE NN.SNID=1) union (");
			sb.append(" SELECT " + " '' AS TRANCER, " + " '' AS QUESTIONNAIRE_NAME,A1.DEALER_CODE,");
			sb.append("( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(
					CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(
					CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append(
					"A1.VIN,A1.sec_so_no as SO_NO, 13001001 as BUSINESS_TYPE,a2.STOCK_OUT_DATE,B1.CUSTOMER_NO,a2.CONFIRMED_DATE,");
			sb.append(
					" B1.CUSTOMER_NAME,B1.CUSTOMER_STATUS,B1.CUSTOMER_TYPE,B1.GENDER,B1.PROVINCE,B1.CITY,B1.DISTRICT,B1.ADDRESS,B1.CONTACTOR_PHONE,");

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" 0 as fpcs,0 as hfcs,");
			} else {
				sb.append(
						"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and SO_NO=a1.sec_so_no and vin=a1.vin)  as fpcs,");
				sb.append(
						"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no and ");
				sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			}
			sb.append(" B1.CONTACTOR_MOBILE,E1.SALES_DATE  FROM TT_SEC_SALES_ORDER_ITEM A1 " + "inner join (");
			sb.append(CommonConstants.VM_VEHICLE + ") E1 on  E1.DEALER_CODE = A1.DEALER_CODE AND E1.VIN=A1.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B1  on  B1.DEALER_CODE = A1.DEALER_CODE AND B1.D_KEY = A1.D_KEY AND B1.CUSTOMER_NO = E1.CUSTOMER_NO ");
			sb.append(
					"left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A1.DEALER_CODE AND a2.SEC_SO_NO=A1.SEC_SO_NO ");
			sb.append(" where A1.D_KEY = 1=1" + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035 ) ");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append(
						"AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series")
						+ "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A1.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and E1.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A2.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and B1.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and B1.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781001")) {// 分配为是
				sb.append(" and  (\n" + "select  count(ta1.SO_NO)  from TT_SALES_TRACE_TASK  ta1\n"
						+ "where ta1.DEALER_CODE=a1.DEALER_CODE \n "
						+ "and  ta1.SO_NO=a1.sec_so_no and ta1.vin=a1.vin\n" + ")>0 ");
			} else if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" and  (\n" + "select  count(ta1.SO_NO)  from TT_SALES_TRACE_TASK  ta1\n"
						+ "where ta1.DEALER_CODE=a1.DEALER_CODE \n" + "and  ta1.SO_NO=a1.sec_so_no and ta1.vin=a1.vin\n"
						+ ")<=0 ");
			}
			sb.append("  ) ) aa  " + "  LEFT JOIN tm_brand bb ON aa.brand_code = bb.BRAND_CODE  ");
			sb.append(
					"  LEFT JOIN tm_series cc ON aa.brand_code = cc.BRAND_CODE AND aa.series_code = cc.`SERIES_CODE`  ");
			sb.append(
					"  LEFT JOIN tm_model dd ON aa.brand_code = dd.BRAND_CODE AND aa.series_code = dd.`SERIES_CODE`  AND aa.model_code = dd.`MODEL_CODE`  ");
			sb.append("where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");

			System.out.println(
					"--------------------------------------是否回访为空-----------------------------------------------------------------");
			System.out.println(sb.toString());

			pagedto = DAOUtil.pageQuery(sb.toString(), queryList);
		}
		if (null != queryParam.get("isDistribute") && queryParam.get("isDistribute").equals("12781001")) {
			sqlTmp = " " + "select  bb.*,\n";
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷的时候
				sqlTmp = sqlTmp + "   (select b.QUESTIONNAIRE_NAME from TT_SALES_TRACE_TASK_QUESTION a, ("
						+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") b\n"
						+ "   where a.DEALER_CODE=b.DEALER_CODE  and a.QUESTIONNAIRE_CODE=b.QUESTIONNAIRE_CODE and a.DEALER_CODE=bb.DEALER_CODE \n"
						+ "   and a.TRACE_TASK_ID=bb.TRACE_TASK_ID\n" + "  and a.QUESTIONNAIRE_CODE='"
						+ queryParam.get("questionnaireCode") + "'  " + "  limit 0,1 "
						+ "   )   as QUESTIONNAIRE_NAME,";
			} else {
				sqlTmp = sqlTmp + "  '' as  QUESTIONNAIRE_NAME,  ";
			}
			sqlTmp = sqlTmp + "   ''  as  QUESTIONNAIRE_CODE\n" +

					"from (\n" + "(SELECT ( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,"
					+ "A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,"
					+ " A.CONFIRMED_DATE,B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,"
					+ " B.ADDRESS,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE," + "(select E.SALES_DATE from ("
					+ CommonConstants.VM_VEHICLE + ") E where E.DEALER_CODE = G.DEALER_CODE "
					+ "AND E.CUSTOMER_NO = G.CUSTOMER_NO AND E.VIN=G.VIN) AS SALES_DATE,"
					+ "d.TRACE_TASK_ID,d.TRANCER,a.DEALER_CODE"
					// 分配次数 回访次数 begin
					+ " , (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,"
					+ "  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and "
					+ "( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs "
					// 分配次数 回访次数 end
					+ " FROM TT_SALES_ORDER A inner join  TT_PO_CUS_RELATION G on A.DEALER_CODE = G.DEALER_CODE  AND A.D_KEY = G.D_KEY   "
					+ "AND A.CUSTOMER_NO = G.PO_CUSTOMER_NO "
					+ " left join TM_CUSTOMER B on B.DEALER_CODE = G.DEALER_CODE AND B.D_KEY = G.D_KEY AND B.CUSTOMER_NO = G.CUSTOMER_NO "
					+ " left join TT_SALES_TRACE_TASK D on A.DEALER_CODE = D.DEALER_CODE AND B.CUSTOMER_NO = D.CUSTOMER_NO AND A.SO_NO = D.SO_NO "
					+ "AND A.VIN = D.VIN AND A.VIN = G.VIN" + " where A.D_KEY = 0 "
					+ " AND A.BUSINESS_TYPE not in( 13001005 ,13001004) "
					+ " AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035)\n" + ") union (" +
					// ---------------------------
					"SELECT ( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,"
					+ "A1.VIN,A1.sec_so_no as so_no,13001005 as BUSINESS_TYPE,A2.STOCK_OUT_DATE,B1.CUSTOMER_NO,"
					+ " A2.CONFIRMED_DATE,B1.CUSTOMER_NAME,B1.CUSTOMER_STATUS,B1.CUSTOMER_TYPE,B1.GENDER,B1.PROVINCE,B1.CITY,B1.DISTRICT,"
					+ " B1.ADDRESS,B1.CONTACTOR_PHONE,B1.CONTACTOR_MOBILE,H1.SALES_DATE,d1.TRACE_TASK_ID,d1.TRANCER,a1.DEALER_CODE"
					// 分配次数 回访次数 begin
					+ " , (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no)  as fpcs,"
					+ "  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no and "
					+ "( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs "
					// 分配次数 回访次数 end
					+ " FROM TT_SEC_SALES_ORDER_ITEM A1 " + " inner join (" + CommonConstants.VM_VEHICLE
					+ ") H1 on a1.DEALER_CODE = H1.DEALER_CODE AND a1.VIN = H1.VIN "
					+ " inner join TM_CUSTOMER B1 on B1.DEALER_CODE = a1.DEALER_CODE AND B1.D_KEY = a1.D_KEY AND B1.CUSTOMER_NO = h1.CUSTOMER_NO "
					+ " left join TT_SALES_TRACE_TASK D1 on A1.DEALER_CODE = D1.DEALER_CODE AND B1.CUSTOMER_NO = D1.CUSTOMER_NO AND "
					+ "A1.sec_so_no = D1.SO_NO AND A1.VIN = D1.VIN "
					+ " left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A1.DEALER_CODE AND a2.SEC_SO_NO=A1.SEC_SO_NO "
					+ " where A1.D_KEY = 0 " + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035)\n" +
					// -------------------------
					"))bb   where 1=1" + "\n" + "  ";
			StringBuffer sb = new StringBuffer("");
			sb.append(" select aa.*,BB1.`BRAND_NAME`,CC1.`SERIES_NAME`,DD1.`MODEL_NAME`"
					+ " ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION " + "  from (   ");
			sb.append(sqlTmp);
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and bb.BRAND_CODE = '" + queryParam.get("brand") + "'");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and bb.MODEL_CODE = '" + queryParam.get("model") + "'");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and bb.SERIES_CODE = '" + queryParam.get("series") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and bb.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and bb.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and bb.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and bb.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and bb.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷的时候
				sb.append(" and  (\n"
						+ "select  count(qu.TRACE_TASK_ID)   from  TT_SALES_TRACE_TASK_QUESTION  qu  where  qu.DEALER_CODE=bb.DEALER_CODE  and qu.TRACE_TASK_ID=bb.TRACE_TASK_ID\n"
						+ "and qu.QUESTIONNAIRE_CODE='" + queryParam.get("questionnaireCode") + "'  " + ")>0 ");
			} else {// 问卷为空
				sb.append(" and  (\n"
						+ "select  count(qu.TRACE_TASK_ID)   from  TT_SALES_TRACE_TASK_QUESTION  qu  where  qu.DEALER_CODE=bb.DEALER_CODE  and qu.TRACE_TASK_ID=bb.TRACE_TASK_ID\n"
						+ ")>0  ");
			}
			sb.append("  )  aa  " + "  LEFT JOIN tm_brand bb1 ON aa.brand_code = bb1.BRAND_CODE  "
					+ "  LEFT JOIN tm_series cc1 ON aa.brand_code = cc1.BRAND_CODE AND aa.series_code = cc1.`SERIES_CODE`  "
					+ "  LEFT JOIN tm_model dd1 ON aa.brand_code = dd1.BRAND_CODE AND aa.series_code = dd1.`SERIES_CODE`  AND aa.model_code = dd1.`MODEL_CODE`  "
					+ "where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");

			System.out.println(
					"--------------------------------------是否回访为是-----------------------------------------------------------------");
			System.out.println(sb.toString());

			pagedto = DAOUtil.pageQuery(sb.toString(), queryList);
		}
		if (null != queryParam.get("isDistribute") && queryParam.get("isDistribute").equals("12781002")) {
			StringBuffer sb = new StringBuffer("");
			sb.append(" select aa.*,BB1.`BRAND_NAME`,CC1.`SERIES_NAME`,DD1.`MODEL_NAME`  ");
			sb.append(" ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION " + "from ((   ");
			sb.append(" SELECT  a.DEALER_CODE, ");
			sb.append("  (select   tr.TRANCER  from  TT_SALES_TRACE_TASK  tr,TM_EMPLOYEE  em\n");
			sb.append("where tr.DEALER_CODE=em.DEALER_CODE and em.EMPLOYEE_NO=tr.TRANCER\n");
			sb.append("and tr.DEALER_CODE=a.DEALER_CODE  and tr.customer_no=b.customer_no\n");
			sb.append("and  tr.so_no=a.so_no and tr.vin=a.vin " + " limit 0,1 " + " ) " + "  AS TRANCER, ");
			sb.append("'' AS QUESTIONNAIRE_NAME," + "( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append("A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,A.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS,B.CONTACTOR_PHONE,");
			// 分配次数 回访次数 begin
			sb.append(
					"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,");
			sb.append(
					"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and ");
			sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			// 分配次数 回访次数 end

			sb.append(" B.CONTACTOR_MOBILE," + "(select E.SALES_DATE from (" + CommonConstants.VM_VEHICLE);
			sb.append(") E where E.DEALER_CODE = D.DEALER_CODE ");
			sb.append("AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN) AS SALES_DATE FROM TT_SALES_ORDER A ");
			sb.append(
					"inner join TT_PO_CUS_RELATION D on  A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY AND A.CUSTOMER_NO = D.PO_CUSTOMER_NO AND A.VIN = D.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B  on  B.DEALER_CODE = D.DEALER_CODE AND B.D_KEY = D.D_KEY AND B.CUSTOMER_NO = D.CUSTOMER_NO ");
			sb.append(" WHERE  1=1 " + " AND A.BUSINESS_TYPE not in( 13001005,13001004 ) ");
			sb.append(" AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035 ) ");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and (select count(E.SALES_DATE) from (" + CommonConstants.VM_VEHICLE
						+ ") E where E.DEALER_CODE = D.DEALER_CODE ");
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
				sb.append(" AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN)>0 ");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and b.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and a.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append("and tb.QUESTIONNAIRE_CODE ='" + queryParam.get("questionnaireCode") + "' \n");
				sb.append(")<=0\n" + ")>0 ");
			} else {// 选空的时候
				sb.append("  and   (\n" + "select  count(ta.SO_NO)   from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append(")<=0\n" + ")>0 ");
			}
			sb.append(") union ( SELECT  a.DEALER_CODE, ");
			sb.append("  (select   tr.TRANCER  from  TT_SALES_TRACE_TASK  tr,TM_EMPLOYEE  em\n");
			sb.append("where tr.DEALER_CODE=em.DEALER_CODE and em.EMPLOYEE_NO=tr.TRANCER\n");
			sb.append("and tr.DEALER_CODE=a.DEALER_CODE  and tr.customer_no=b.customer_no\n");
			sb.append("and  tr.so_no=a.SEC_SO_NO and tr.vin=a.vin " + " limit 0,1 " + " ) " + "  AS TRANCER, ");
			sb.append("'' AS QUESTIONNAIRE_NAME," + "( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append(
					"A.VIN,A.SEC_SO_NO as SO_NO, 13001005 as BUSINESS_TYPE,A2.STOCK_OUT_DATE,B.CUSTOMER_NO,A2.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS,B.CONTACTOR_PHONE,");
			// 分配次数 回访次数 begin
			sb.append(
					"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,");
			sb.append(
					"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and");
			sb.append(" ( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			// 分配次数 回访次数 end
			sb.append(" B.CONTACTOR_MOBILE,E.SALES_DATE  FROM TT_SEC_SALES_ORDER_ITEM A " + "inner JOIN (");
			sb.append(CommonConstants.VM_VEHICLE + ") E ON E.DEALER_CODE = a.DEALER_CODE  AND E.VIN=a.VIN ");
			sb.append(
					"inner JOIN TM_CUSTOMER B ON B.DEALER_CODE = a.DEALER_CODE AND B.D_KEY = a.D_KEY AND B.CUSTOMER_NO = e.CUSTOMER_NO ");
			sb.append(
					" left JOIN TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A.DEALER_CODE AND a2.SEC_SO_NO=A.SEC_SO_NO ");
			sb.append(" WHERE 1=1 " + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035 )");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A2.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and B.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and B.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SEC_SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append("and tb.QUESTIONNAIRE_CODE ='" + queryParam.get("questionnaireCode") + "' \n" + ")<=0\n");
				sb.append(")>0 ");
			} else {// 选空的时候
				sb.append("  and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SEC_SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append(")<=0\n" + ")>0 ");
			}
			sb.append("  ) ) aa  " + "  LEFT JOIN tm_brand bb1 ON aa.brand_code = bb1.BRAND_CODE  ");
			sb.append(
					"  LEFT JOIN tm_series cc1 ON aa.brand_code = cc1.BRAND_CODE AND aa.series_code = cc1.`SERIES_CODE`  ");
			sb.append(
					"  LEFT JOIN tm_model dd1 ON aa.brand_code = dd1.BRAND_CODE AND aa.series_code = dd1.`SERIES_CODE`  AND aa.model_code = dd1.`MODEL_CODE`  ");
			sb.append("where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");
			System.out.println(
					"--------------------------------------是否回访为否-----------------------------------------------------------------");
			System.out.println(sb.toString());

			pagedto = DAOUtil.pageQuery(sb.toString(), queryList);
		}

		return pagedto;
	}

	/**
	 * 再分配
	 * 
	 * @author LGQ
	 * @date 2016年12月29日
	 * @param potentialCusPo
	 */
	@Override
	public void modifySoldBy(SalesTraceTaskDTO salesTraceTaskDTO) throws ServiceBizException {
		String[] cusNo = salesTraceTaskDTO.getNoList1().split(",");
		String[] vin = salesTraceTaskDTO.getNoList2().split(",");
		String[] soNo = salesTraceTaskDTO.getNoList3().split(",");
		String[] brandCode = salesTraceTaskDTO.getNoList4().split(",");
		String[] seriesCode = salesTraceTaskDTO.getNoList5().split(",");
		String[] modelCode = salesTraceTaskDTO.getNoList6().split(",");
		String[] customerStatus = salesTraceTaskDTO.getNoList7().split(",");
		String[] customerType = salesTraceTaskDTO.getNoList8().split(",");
		String[] businessType = salesTraceTaskDTO.getNoList9().split(",");
		String[] customerName = salesTraceTaskDTO.getNoList10().split(",");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		for (int i = 0; i < vin.length; i++) {
			// 检查是已经和问题产生关联关系
			String cusNo1 = cusNo[i];
			String vin1 = vin[i];
			String soNo1 = soNo[i];
			String brandCode1 = brandCode[i];
			String seriesCode1 = seriesCode[i];
			String modelCode1 = modelCode[i];
			String customerStatus1 = customerStatus[i];
			String customerType1 = customerType[i];
			String businessType1 = businessType[i];
			String customerName1 = customerName[i];
			List<Object> task = new ArrayList<Object>();
			task.add(cusNo1);
			task.add(vin1);
			task.add(soNo1);
			task.add(loginInfo.getDealerCode());
			List<TtSalesTraceTaskPO> rsList = TtSalesTraceTaskPO.findBySQL("select * from Tt_Sales_Trace_Task "
					+ "where CUSTOMER_NO = ? AND VIN = ? AND SO_NO = ? AND DEALER_CODE= ? ", task.toArray());
			if (rsList != null && rsList.size() > 0) {
				for (TtSalesTraceTaskPO ttSalesTraceTaskPO : rsList) {
					String tranceStatus = ttSalesTraceTaskPO.get("TRACE_STATUS").toString();
					if (tranceStatus == "12371003" || tranceStatus == "12371004") {
						// 未跟踪的客户
						List<Object> task1 = new ArrayList<Object>();
						task1.add(cusNo1);
						task1.add(vin1);
						task1.add(soNo1);
						task1.add("12371001");
						task1.add(loginInfo.getDealerCode());
						List<TtSalesTraceTaskPO> rsnogzList = TtSalesTraceTaskPO.findBySQL(
								"select * from Tt_Sales_Trace_Task "
										+ "where CUSTOMER_NO = ? AND VIN = ? AND SO_NO = ? AND TRACE_TASK = ? AND DEALER_CODE= ? ",
								task.toArray());

						// 继续跟踪的客户
						List<Object> task2 = new ArrayList<Object>();
						task2.add(cusNo1);
						task2.add(vin1);
						task2.add(soNo1);
						task2.add("12371002");
						task2.add(loginInfo.getDealerCode());
						List<TtSalesTraceTaskPO> rsjxgzList = TtSalesTraceTaskPO.findBySQL(
								"select * from Tt_Sales_Trace_Task "
										+ "where CUSTOMER_NO = ? AND VIN = ? AND SO_NO = ? AND TRANCE_TASK = ? AND DEALER_CODE= ? ",
								task.toArray());

						if ((rsnogzList == null || rsnogzList.size() == 0)
								&& (rsjxgzList == null || rsjxgzList.size() == 0)) {
							TtSalesTraceTaskPO salesTraceTaskPo = TtSalesTraceTaskPO
									.findByCompositeKeys(loginInfo.getDealerCode(), cusNo1, vin1, soNo1);
							salesTraceTaskPo.saveIt();
						}
					}
				}
			} else {
				// for (TtSalesTraceTaskPO ttSalesTraceTaskPO : rsList) {
				// String traceTaskId =
				// ttSalesTraceTaskPO.get("TRANCE_TASK_ID").toString();
				// TtSalesTraceTaskPO salesTraceTaskPo =
				// TtSalesTraceTaskPO.findByCompositeKeys(loginInfo.getDealerCode(),traceTaskId);
				// salesTraceTaskPo.setString("EMPLOYEE_NO",
				// salesTraceTaskDTO.getTrance());// 跟踪员
				// salesTraceTaskPo.saveIt();
				// }
				String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
				TtSalesTraceTaskPO po = new TtSalesTraceTaskPO();
				Long traceTaskId = commonNoService.getId("TT_SALES_TRACE_TASK");
				po.setString("TRACE_TASK_ID", traceTaskId);
				po.setString("DEALER_CODE", dealerCode);
				po.setString("CUSTOMER_NO", cusNo1);
				po.setString("CUSTOMER_NAME", customerName1);
				po.setString("CUSTOMER_STATUS", customerStatus1);
				po.setString("CUSTOMER_TYPE", customerType1);
				po.setString("BUSINESS_TYPE", businessType1);
				po.setString("SO_NO", soNo1);
				po.setString("VIN", vin1);
				po.setString("BRAND", brandCode1);
				po.setString("SERIES", seriesCode1);
				po.setString("MODEL", modelCode1);
				po.setString("TRANCER", salesTraceTaskDTO.getTrance());
				po.setString("TRACE_STATUS", "12371001");// 未跟踪
				po.saveIt();

			}
		}
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
	@Override
	public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = null;
		String sqlTmp = "";
		List<Object> queryList = new ArrayList<Object>();
		if (queryParam.get("isDistribute") == null || queryParam.get("isDistribute").equals("")) {
			StringBuffer sb = new StringBuffer(" select aa.*   ,BB.`BRAND_NAME`,CC.`SERIES_NAME`,DD.`MODEL_NAME` ");
			sb.append(" ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION ");
			sb.append(" from ( (SELECT TRANCER,QUESTIONNAIRE_NAME,BRAND_CODE,SERIES_CODE,MODEL_CODE,VIN, ");
			sb.append(
					"SO_NO,BUSINESS_TYPE,STOCK_OUT_DATE,CUSTOMER_NO,CONFIRMED_DATE,CUSTOMER_NAME,CUSTOMER_STATUS, CUSTOMER_TYPE, ");
			sb.append(
					"GENDER,PROVINCE,CITY,DISTRICT,ADDRESS,CONTACTOR_PHONE,fpcs,hfcs,CONTACTOR_MOBILE,SALES_DATE,DEALER_CODE ");
			sb.append(
					"FROM(select if(@pdept = concat(b.VIN,b.SO_NO,b.dealer_code), @rank := @rank + 1,  @rank := 1) as snid,@pdept:=concat(b.VIN,b.SO_NO,b.dealer_code),@rownum:=@rownum+1,  b.* ");
			sb.append("FROM ");
			sb.append(" (SELECT ");
			sb.append(" '' AS TRANCER, ");
			sb.append(" '' AS QUESTIONNAIRE_NAME,A.DEALER_CODE,");
			sb.append("( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE,");
			sb.append("( select C.SERIES_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE,");
			sb.append("( select C.MODEL_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append("A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,A.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS, ");
			sb.append("d.CREATED_AT RELA_CREATE_DATE,");
			sb.append("B.CONTACTOR_PHONE,");

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" 0 as fpcs,0 as hfcs,");
			} else {
				sb.append(
						"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and SO_NO=a.SO_NO and vin=a.vin)  as fpcs,");
				sb.append(
						"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and ");
				sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			}

			sb.append(" B.CONTACTOR_MOBILE,(select E.SALES_DATE from (" + CommonConstants.VM_VEHICLE);
			sb.append(") E where E.DEALER_CODE = D.DEALER_CODE ");
			sb.append("AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN) AS SALES_DATE  FROM TT_SALES_ORDER A ");
			sb.append(
					"inner join TT_PO_CUS_RELATION D on  A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY AND A.CUSTOMER_NO = D.PO_CUSTOMER_NO ");
			sb.append("AND A.VIN = D.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B  on  B.DEALER_CODE = D.DEALER_CODE AND B.D_KEY = D.D_KEY AND B.CUSTOMER_NO = D.CUSTOMER_NO ");
			sb.append(" where  1=1 " + " AND A.BUSINESS_TYPE not in(13001005,13001004)");
			sb.append(" AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035 ) ");

			if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and a.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and (select count(E.SALES_DATE) from (" + CommonConstants.VM_VEHICLE
						+ ") E where E.DEALER_CODE = D.DEALER_CODE ");
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
				sb.append(" AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN)>0 ");
			}

			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and b.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and a.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781001")) {// 分配为是
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n"
						+ "where ta.DEALER_CODE=a.DEALER_CODE \n " + "and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n"
						+ ")>0 ");
			} else if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n"
						+ "where ta.DEALER_CODE=a.DEALER_CODE \n" + "and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n"
						+ ")<=0 ");
			}
			sb.append(""
					+ " order by concat(a.VIN,A.SO_NO,A.dealer_code) asc,  d.CREATEd_at desc) b,   (select @rownum := 0, @pdept := null, @rank := 0) a");
			sb.append(") NN WHERE NN.SNID=1) union (");
			sb.append(" SELECT " + " '' AS TRANCER, " + " '' AS QUESTIONNAIRE_NAME,A1.DEALER_CODE,");
			sb.append("( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(
					CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(
					CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
			sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append(
					"A1.VIN,A1.sec_so_no as SO_NO, 13001001 as BUSINESS_TYPE,a2.STOCK_OUT_DATE,B1.CUSTOMER_NO,a2.CONFIRMED_DATE,");
			sb.append(
					" B1.CUSTOMER_NAME,B1.CUSTOMER_STATUS,B1.CUSTOMER_TYPE,B1.GENDER,B1.PROVINCE,B1.CITY,B1.DISTRICT,B1.ADDRESS,B1.CONTACTOR_PHONE,");

			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" 0 as fpcs,0 as hfcs,");
			} else {
				sb.append(
						"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and SO_NO=a1.sec_so_no and vin=a1.vin)  as fpcs,");
				sb.append(
						"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no and ");
				sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			}
			sb.append(" B1.CONTACTOR_MOBILE,E1.SALES_DATE  FROM TT_SEC_SALES_ORDER_ITEM A1 " + "inner join (");
			sb.append(CommonConstants.VM_VEHICLE + ") E1 on  E1.DEALER_CODE = A1.DEALER_CODE AND E1.VIN=A1.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B1  on  B1.DEALER_CODE = A1.DEALER_CODE AND B1.D_KEY = A1.D_KEY AND B1.CUSTOMER_NO = E1.CUSTOMER_NO ");
			sb.append(
					"left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A1.DEALER_CODE AND a2.SEC_SO_NO=A1.SEC_SO_NO ");
			sb.append(" where A1.D_KEY = 1=1" + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035 ) ");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append(
						"AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY ");
				sb.append("AND A1.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series")
						+ "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A1.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and E1.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A2.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and B1.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and B1.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781001")) {// 分配为是
				sb.append(" and  (\n" + "select  count(ta1.SO_NO)  from TT_SALES_TRACE_TASK  ta1\n"
						+ "where ta1.DEALER_CODE=a1.DEALER_CODE \n "
						+ "and  ta1.SO_NO=a1.sec_so_no and ta1.vin=a1.vin\n" + ")>0 ");
			} else if (queryParam.get("ifdistribution") != null && !"".equals(queryParam.get("ifdistribution"))
					&& queryParam.get("ifdistribution").equals("12781002")) {
				sb.append(" and  (\n" + "select  count(ta1.SO_NO)  from TT_SALES_TRACE_TASK  ta1\n"
						+ "where ta1.DEALER_CODE=a1.DEALER_CODE \n" + "and  ta1.SO_NO=a1.sec_so_no and ta1.vin=a1.vin\n"
						+ ")<=0 ");
			}
			sb.append("  ) ) aa  " + "  LEFT JOIN tm_brand bb ON aa.brand_code = bb.BRAND_CODE  ");
			sb.append(
					"  LEFT JOIN tm_series cc ON aa.brand_code = cc.BRAND_CODE AND aa.series_code = cc.`SERIES_CODE`  ");
			sb.append(
					"  LEFT JOIN tm_model dd ON aa.brand_code = dd.BRAND_CODE AND aa.series_code = dd.`SERIES_CODE`  AND aa.model_code = dd.`MODEL_CODE`  ");
			sb.append("where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");

			System.out.println(
					"--------------------------------------是否回访为空-----------------------------------------------------------------");
			System.out.println(sb.toString());

			list = DAOUtil.findAll(sb.toString(), queryList);
			for(int i = 0;i<list.size();i++){
				
				if("10061001".equals(list.get(i).get("GENDER").toString())){
					list.get(i).put("GENDER", "男");
				}else if("10061002".equals(list.get(i).get("GENDER").toString())){
					list.get(i).put("GENDER", "女");
				}
				System.err.println(list.get(i).get("GENDER"));
			}
			
		}
		if (null != queryParam.get("isDistribute") && queryParam.get("isDistribute").equals("12781001")) {
			sqlTmp = " " + "select  bb.*,\n";
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷的时候
				sqlTmp = sqlTmp + "   (select b.QUESTIONNAIRE_NAME from TT_SALES_TRACE_TASK_QUESTION a, ("
						+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") b\n"
						+ "   where a.DEALER_CODE=b.DEALER_CODE  and a.QUESTIONNAIRE_CODE=b.QUESTIONNAIRE_CODE and a.DEALER_CODE=bb.DEALER_CODE \n"
						+ "   and a.TRACE_TASK_ID=bb.TRACE_TASK_ID\n" + "  and a.QUESTIONNAIRE_CODE='"
						+ queryParam.get("questionnaireCode") + "'  " + "  limit 0,1 "
						+ "   )   as QUESTIONNAIRE_NAME,";
			} else {
				sqlTmp = sqlTmp + "  '' as  QUESTIONNAIRE_NAME,  ";
			}
			sqlTmp = sqlTmp + "   ''  as  QUESTIONNAIRE_CODE\n" +

					"from (\n" + "(SELECT ( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY "
					+ "AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,"
					+ "A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,"
					+ " A.CONFIRMED_DATE,B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,"
					+ " B.ADDRESS,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE," + "(select E.SALES_DATE from ("
					+ CommonConstants.VM_VEHICLE + ") E where E.DEALER_CODE = G.DEALER_CODE "
					+ "AND E.CUSTOMER_NO = G.CUSTOMER_NO AND E.VIN=G.VIN) AS SALES_DATE,"
					+ "d.TRACE_TASK_ID,d.TRANCER,a.DEALER_CODE"
					// 分配次数 回访次数 begin
					+ " , (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,"
					+ "  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and "
					+ "( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs "
					// 分配次数 回访次数 end
					+ " FROM TT_SALES_ORDER A inner join  TT_PO_CUS_RELATION G on A.DEALER_CODE = G.DEALER_CODE  AND A.D_KEY = G.D_KEY   "
					+ "AND A.CUSTOMER_NO = G.PO_CUSTOMER_NO "
					+ " left join TM_CUSTOMER B on B.DEALER_CODE = G.DEALER_CODE AND B.D_KEY = G.D_KEY AND B.CUSTOMER_NO = G.CUSTOMER_NO "
					+ " left join TT_SALES_TRACE_TASK D on A.DEALER_CODE = D.DEALER_CODE AND B.CUSTOMER_NO = D.CUSTOMER_NO AND A.SO_NO = D.SO_NO "
					+ "AND A.VIN = D.VIN AND A.VIN = G.VIN" + " where A.D_KEY = 0 "
					+ " AND A.BUSINESS_TYPE not in( 13001005 ,13001004) "
					+ " AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035)\n" + ") union (" +
					// ---------------------------
					"SELECT ( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT
					+ ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  ("
					+ CommonConstants.VM_VS_PRODUCT + ") C where A1.DEALER_CODE = C.DEALER_CODE AND A1.D_KEY = C.D_KEY "
					+ "AND A1.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,"
					+ "A1.VIN,A1.sec_so_no as so_no,13001005 as BUSINESS_TYPE,A2.STOCK_OUT_DATE,B1.CUSTOMER_NO,"
					+ " A2.CONFIRMED_DATE,B1.CUSTOMER_NAME,B1.CUSTOMER_STATUS,B1.CUSTOMER_TYPE,B1.GENDER,B1.PROVINCE,B1.CITY,B1.DISTRICT,"
					+ " B1.ADDRESS,B1.CONTACTOR_PHONE,B1.CONTACTOR_MOBILE,H1.SALES_DATE,d1.TRACE_TASK_ID,d1.TRANCER,a1.DEALER_CODE"
					// 分配次数 回访次数 begin
					+ " , (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no)  as fpcs,"
					+ "  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a1.DEALER_CODE and customer_no=b1.customer_no and "
					+ "( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs "
					// 分配次数 回访次数 end
					+ " FROM TT_SEC_SALES_ORDER_ITEM A1 " + " inner join (" + CommonConstants.VM_VEHICLE
					+ ") H1 on a1.DEALER_CODE = H1.DEALER_CODE AND a1.VIN = H1.VIN "
					+ " inner join TM_CUSTOMER B1 on B1.DEALER_CODE = a1.DEALER_CODE AND B1.D_KEY = a1.D_KEY AND B1.CUSTOMER_NO = h1.CUSTOMER_NO "
					+ " left join TT_SALES_TRACE_TASK D1 on A1.DEALER_CODE = D1.DEALER_CODE AND B1.CUSTOMER_NO = D1.CUSTOMER_NO AND "
					+ "A1.sec_so_no = D1.SO_NO AND A1.VIN = D1.VIN "
					+ " left join TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A1.DEALER_CODE AND a2.SEC_SO_NO=A1.SEC_SO_NO "
					+ " where A1.D_KEY = 0 " + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035)\n" +
					// -------------------------
					"))bb   where 1=1" + "\n" + "  ";
			StringBuffer sb = new StringBuffer("");
			sb.append(" select aa.*,BB1.`BRAND_NAME`,CC1.`SERIES_NAME`,DD1.`MODEL_NAME`"
					+ " ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION " + "  from (   ");
			sb.append(sqlTmp);
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and bb.BRAND_CODE = '" + queryParam.get("brand") + "'");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and bb.MODEL_CODE = '" + queryParam.get("model") + "'");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and bb.SERIES_CODE = '" + queryParam.get("series") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and bb.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and bb.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and bb.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and bb.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and bb.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷的时候
				sb.append(" and  (\n"
						+ "select  count(qu.TRACE_TASK_ID)   from  TT_SALES_TRACE_TASK_QUESTION  qu  where  qu.DEALER_CODE=bb.DEALER_CODE  and qu.TRACE_TASK_ID=bb.TRACE_TASK_ID\n"
						+ "and qu.QUESTIONNAIRE_CODE='" + queryParam.get("questionnaireCode") + "'  " + ")>0 ");
			} else {// 问卷为空
				sb.append(" and  (\n"
						+ "select  count(qu.TRACE_TASK_ID)   from  TT_SALES_TRACE_TASK_QUESTION  qu  where  qu.DEALER_CODE=bb.DEALER_CODE  and qu.TRACE_TASK_ID=bb.TRACE_TASK_ID\n"
						+ ")>0  ");
			}
			sb.append("  )  aa  " + "  LEFT JOIN tm_brand bb1 ON aa.brand_code = bb1.BRAND_CODE  "
					+ "  LEFT JOIN tm_series cc1 ON aa.brand_code = cc1.BRAND_CODE AND aa.series_code = cc1.`SERIES_CODE`  "
					+ "  LEFT JOIN tm_model dd1 ON aa.brand_code = dd1.BRAND_CODE AND aa.series_code = dd1.`SERIES_CODE`  AND aa.model_code = dd1.`MODEL_CODE`  "
					+ "where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");

			System.out.println(
					"--------------------------------------是否回访为是-----------------------------------------------------------------");
			System.out.println(sb.toString());

			list = DAOUtil.findAll(sb.toString(), queryList);
		}
		if (null != queryParam.get("isDistribute") && queryParam.get("isDistribute").equals("12781002")) {
			StringBuffer sb = new StringBuffer("");
			sb.append(" select aa.*,BB1.`BRAND_NAME`,CC1.`SERIES_NAME`,DD1.`MODEL_NAME`  ");
			sb.append(" ,(CASE WHEN aa.fpcs = 0 THEN 12781002  ELSE 12781001 END ) AS IFDISTRIBUTION " + "from ((   ");
			sb.append(" SELECT  a.DEALER_CODE, ");
			sb.append("  (select   tr.TRANCER  from  TT_SALES_TRACE_TASK  tr,TM_EMPLOYEE  em\n");
			sb.append("where tr.DEALER_CODE=em.DEALER_CODE and em.EMPLOYEE_NO=tr.TRANCER\n");
			sb.append("and tr.DEALER_CODE=a.DEALER_CODE  and tr.customer_no=b.customer_no\n");
			sb.append("and  tr.so_no=a.so_no and tr.vin=a.vin " + " limit 0,1 " + " ) " + "  AS TRANCER, ");
			sb.append("'' AS QUESTIONNAIRE_NAME," + "( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append("A.VIN,A.SO_NO,A.BUSINESS_TYPE,A.STOCK_OUT_DATE,B.CUSTOMER_NO,A.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS,B.CONTACTOR_PHONE,");
			// 分配次数 回访次数 begin
			sb.append(
					"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,");
			sb.append(
					"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and ");
			sb.append("( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			// 分配次数 回访次数 end

			sb.append(" B.CONTACTOR_MOBILE," + "(select E.SALES_DATE from (" + CommonConstants.VM_VEHICLE);
			sb.append(") E where E.DEALER_CODE = D.DEALER_CODE ");
			sb.append("AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN) AS SALES_DATE FROM TT_SALES_ORDER A ");
			sb.append(
					"inner join TT_PO_CUS_RELATION D on  A.DEALER_CODE = D.DEALER_CODE AND A.D_KEY = D.D_KEY AND A.CUSTOMER_NO = D.PO_CUSTOMER_NO AND A.VIN = D.VIN ");
			sb.append(
					"inner join TM_CUSTOMER B  on  B.DEALER_CODE = D.DEALER_CODE AND B.D_KEY = D.D_KEY AND B.CUSTOMER_NO = D.CUSTOMER_NO ");
			sb.append(" WHERE  1=1 " + " AND A.BUSINESS_TYPE not in( 13001005,13001004 ) ");
			sb.append(" AND (A.SO_STATUS = 13011075 OR A.SO_STATUS = 13011035 ) ");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and (select count(E.SALES_DATE) from (" + CommonConstants.VM_VEHICLE
						+ ") E where E.DEALER_CODE = D.DEALER_CODE ");
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
				sb.append(" AND E.CUSTOMER_NO = D.CUSTOMER_NO AND E.VIN=D.VIN)>0 ");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and b.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and a.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append("and tb.QUESTIONNAIRE_CODE ='" + queryParam.get("questionnaireCode") + "' \n");
				sb.append(")<=0\n" + ")>0 ");
			} else {// 选空的时候
				sb.append("  and   (\n" + "select  count(ta.SO_NO)   from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append(")<=0\n" + ")>0 ");
			}
			sb.append(") union ( SELECT  a.DEALER_CODE, ");
			sb.append("  (select   tr.TRANCER  from  TT_SALES_TRACE_TASK  tr,TM_EMPLOYEE  em\n");
			sb.append("where tr.DEALER_CODE=em.DEALER_CODE and em.EMPLOYEE_NO=tr.TRANCER\n");
			sb.append("and tr.DEALER_CODE=a.DEALER_CODE  and tr.customer_no=b.customer_no\n");
			sb.append("and  tr.so_no=a.SEC_SO_NO and tr.vin=a.vin " + " limit 0,1 " + " ) " + "  AS TRANCER, ");
			sb.append("'' AS QUESTIONNAIRE_NAME," + "( select C.BRAND_CODE from  (" + CommonConstants.VM_VS_PRODUCT);
			sb.append(") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as BRAND_CODE," + "( select C.SERIES_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as SERIES_CODE," + "( select C.MODEL_CODE from  (");
			sb.append(CommonConstants.VM_VS_PRODUCT + ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
			sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE ) as MODEL_CODE,");
			sb.append(
					"A.VIN,A.SEC_SO_NO as SO_NO, 13001005 as BUSINESS_TYPE,A2.STOCK_OUT_DATE,B.CUSTOMER_NO,A2.CONFIRMED_DATE,");
			sb.append(
					" B.CUSTOMER_NAME,B.CUSTOMER_STATUS,B.CUSTOMER_TYPE,B.GENDER,B.PROVINCE,B.CITY,B.DISTRICT,B.ADDRESS,B.CONTACTOR_PHONE,");
			// 分配次数 回访次数 begin
			sb.append(
					"  (select  count(1) as fpcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no)  as fpcs,");
			sb.append(
					"  (select  count(1) as hfcs  from    TT_SALES_TRACE_TASK  where DEALER_CODE=a.DEALER_CODE and customer_no=b.customer_no and");
			sb.append(" ( (TRACE_STATUS=12371003) or (TRACE_STATUS=12371004))   )  as hfcs ,");
			// 分配次数 回访次数 end
			sb.append(" B.CONTACTOR_MOBILE,E.SALES_DATE  FROM TT_SEC_SALES_ORDER_ITEM A " + "inner JOIN (");
			sb.append(CommonConstants.VM_VEHICLE + ") E ON E.DEALER_CODE = a.DEALER_CODE  AND E.VIN=a.VIN ");
			sb.append(
					"inner JOIN TM_CUSTOMER B ON B.DEALER_CODE = a.DEALER_CODE AND B.D_KEY = a.D_KEY AND B.CUSTOMER_NO = e.CUSTOMER_NO ");
			sb.append(
					" left JOIN TT_SECOND_SALES_ORDER a2 on  a2.DEALER_CODE = A.DEALER_CODE AND a2.SEC_SO_NO=A.SEC_SO_NO ");
			sb.append(" WHERE 1=1 " + " AND (A2.SO_STATUS = 13011075 OR A2.SO_STATUS = 13011035 )");
			if (queryParam.get("brand") != null && !"".equals(queryParam.get("brand"))) {
				sb.append(" and (select count(C.BRAND_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.BRAND_CODE='" + queryParam.get("brand") + "')>0 ");
			}
			if (queryParam.get("model") != null && !"".equals(queryParam.get("model"))) {
				sb.append(" and (select count(C.MODEL_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append("AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.MODEL_CODE='" + queryParam.get("model")
						+ "')>0 ");
			}
			if (queryParam.get("series") != null && !"".equals(queryParam.get("series"))) {
				sb.append(" and (select count(C.SERIES_CODE) from  (" + CommonConstants.VM_VS_PRODUCT
						+ ") C where A.DEALER_CODE = C.DEALER_CODE AND A.D_KEY = C.D_KEY ");
				sb.append(
						"AND A.PRODUCT_CODE = C.PRODUCT_CODE and C.SERIES_CODE='" + queryParam.get("series") + "')>0 ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sb.append(" and A.vin like '%" + queryParam.get("vin") + "%'");
			}
			if ((queryParam.get("startDate") != null && !"".equals(queryParam.get("startDate")))
					|| (queryParam.get("endDate") != null && !"".equals(queryParam.get("endDate")))) {
				sb.append(" and E.SALES_DATE between '" + queryParam.get("startDate") + "' and '"
						+ queryParam.get("endDate") + "'");
			}
			if ((queryParam.get("confirmedDateBegin") != null && !"".equals(queryParam.get("confirmedDateBegin")))
					|| (queryParam.get("confirmedDateEnd") != null && !"".equals(queryParam.get("confirmedDateEnd")))) {
				sb.append(" and A2.CONFIRMED_DATE  between '" + queryParam.get("confirmedDateBegin") + "' and '"
						+ queryParam.get("confirmedDateEnd") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
				sb.append(" and B.CUSTOMER_NO like '%" + queryParam.get("customerCode") + "%'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
				sb.append(" and B.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
			}
			if (queryParam.get("questionnaireCode") != null && !"".equals(queryParam.get("questionnaireCode"))) {// 选具体问卷
				sb.append(" and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SEC_SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append("and tb.QUESTIONNAIRE_CODE ='" + queryParam.get("questionnaireCode") + "' \n" + ")<=0\n");
				sb.append(")>0 ");
			} else {// 选空的时候
				sb.append("  and  (\n" + "select  count(ta.SO_NO)  from TT_SALES_TRACE_TASK  ta\n");
				sb.append("where ta.DEALER_CODE=a.DEALER_CODE  and  ta.CUSTOMER_NO=b.CUSTOMER_NO\n");
				sb.append("and  ta.SO_NO=a.SEC_SO_NO and ta.vin=a.vin\n" + "and (\n");
				sb.append(
						"select  count(tb.TRACE_TASK_ID) from  TT_SALES_TRACE_TASK_QUESTION tb  where tb.DEALER_CODE=ta.DEALER_CODE  and tb.TRACE_TASK_ID=ta.TRACE_TASK_ID\n");
				sb.append(")<=0\n" + ")>0 ");
			}
			sb.append("  ) ) aa  " + "  LEFT JOIN tm_brand bb1 ON aa.brand_code = bb1.BRAND_CODE  ");
			sb.append(
					"  LEFT JOIN tm_series cc1 ON aa.brand_code = cc1.BRAND_CODE AND aa.series_code = cc1.`SERIES_CODE`  ");
			sb.append(
					"  LEFT JOIN tm_model dd1 ON aa.brand_code = dd1.BRAND_CODE AND aa.series_code = dd1.`SERIES_CODE`  AND aa.model_code = dd1.`MODEL_CODE`  ");
			sb.append("where 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("fpcs"))) {
				sb.append(" and aa.fpcs = '" + queryParam.get("fpcs") + "'");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("hfcs"))) {
				sb.append(" and aa.hfcs = '" + queryParam.get("hfcs") + "'");
			}
			sb.append(" ORDER BY  aa.STOCK_OUT_DATE DESC");
			System.out.println(
					"--------------------------------------是否回访为否-----------------------------------------------------------------");
			System.out.println(sb.toString());

			list = DAOUtil.findAll(sb.toString(), queryList);
		}
		return list;

	}

}
