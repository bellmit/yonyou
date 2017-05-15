
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : SalesPerformanceServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年2月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月17日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 销售业绩一览报表
 * 
 * @author zhanshiwei
 * @date 2017年2月17日
 */
@Service
public class SalesPerformanceServiceImpl implements SalesPerformanceService {
	@Autowired
	private OperateLogService operateLogService;
	
    @Override
    public PageInfoDto querySalesPerformance(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        String soldBy = queryParam.get("soldBy");
        String model = queryParam.get("modelCode");
        String yearMonth = queryParam.get("yearMonth");
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(6, 7);
        String functionCode = "606005";
        Long userId = FrameworkUtil.getLoginInfo().getUserId();
        String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

        sql.append(" SELECT   Z.DEALER_CODE,Z.USER_NAME SOLD_BY,Z.USER_ID," + "coalesce(A.DELIVERY_COUNT,0) GOAL_SALES,\n");
        sql.append("coalesce(B.REAL_SALES,0) REAL_SALES,\n");
        sql.append("CASE WHEN A.DELIVERY_COUNT =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(B.REAL_SALES,0)*100.00/coalesce(A.DELIVERY_COUNT,1) as decimal(10,2)))||'%' END  RATE_SALES,\n");
        sql.append(" (CASE WHEN SALES_AMOUNT IS NOT NULL THEN SALES_AMOUNT ELSE 0 END)    AS   SALES_AMOUNT,(CASE WHEN JINGPIN_COUNT IS NOT NULL THEN JINGPIN_COUNT ELSE 0 END)    AS   JINGPIN_COUNT,(CASE WHEN JINGPIN_AMOUNT IS NOT NULL THEN JINGPIN_AMOUNT ELSE 0 END)    AS   JINGPIN_AMOUNT,(CASE WHEN JINGPIN_COST IS NOT NULL THEN JINGPIN_COST ELSE 0 END)    AS   JINGPIN_COST,(CASE WHEN INSURANCE IS NOT NULL THEN INSURANCE ELSE 0 END)    AS   INSURANCE,(CASE WHEN FINANCE IS NOT NULL THEN FINANCE ELSE 0 END)    AS   FINANCE,(CASE WHEN OLD_CAR IS NOT NULL THEN OLD_CAR ELSE 0 END)    AS   OLD_CAR,(CASE WHEN H_CUSTOMER IS NOT NULL THEN H_CUSTOMER ELSE 0 END)    AS   H_CUSTOMER,(CASE WHEN A_CUSTOMER IS NOT NULL THEN A_CUSTOMER ELSE 0 END)    AS   A_CUSTOMER,(CASE WHEN B_CUSTOMER IS NOT NULL THEN B_CUSTOMER ELSE 0 END)    AS   B_CUSTOMER,(CASE WHEN C_CUSTOMER IS NOT NULL THEN C_CUSTOMER ELSE 0 END)    AS   C_CUSTOMER,(CASE WHEN N_CUSTOMER IS NOT NULL THEN N_CUSTOMER ELSE 0 END)    AS   N_CUSTOMER, \n");
        sql.append("CASE WHEN COME_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(CUSTOMER_All,0)*100.00/coalesce(COME_All,1) as decimal(10,2)))||'%' END  CREATE_RATE,\n");
        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(TEST,0)*100.00/coalesce(CUSTOMER_All,1) as decimal(10,2)))||'%' END  TEST_RATE,\n");
        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(BACK,0)*100.00/coalesce(CUSTOMER_All,1)  as decimal(10,2)))||'%' END  BACK_RATE,\n");
        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(ZT_SALES,0)*100.00/coalesce(CUSTOMER_All,1)   as decimal(10,2)))||'%' END  DEAL_RATE,\n");
        sql.append("CASE WHEN ZT_SALES =0 THEN CONCAT(0.00)||'%' ELSE \n");
        sql.append("CONCAT(cast(coalesce(TRANS_CUSTOMER,0)*100.00/coalesce(ZT_SALES,1) as decimal(10,2)))||'%' END  TRANS_RATE,\n");
        sql.append("coalesce(CUSTOMER_All,0) CREATE5," + "coalesce(TEST,0) TEST," + "coalesce(BACK,0) BACK,\n");
        sql.append("coalesce(ZT_SALES,0) DEAL," + "coalesce(TRANS_CUSTOMER,0) TRANS,\n");
        sql.append(" (CASE WHEN DCC_CUSTOMER IS NOT NULL THEN DCC_CUSTOMER ELSE 0 END)    AS   DCC_CUSTOMER,(CASE WHEN COMPLAIN_COUNT IS NOT NULL THEN COMPLAIN_COUNT ELSE 0 END)    AS   COMPLAIN_COUNT \n");
        sql.append(" FROM TM_USER Z \n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" LEFT JOIN ( select sum(DELIVERY_COUNT) DELIVERY_COUNT,DEALER_CODE,month,model_code,sold_by from TT_PLAN_PERSONAL_ITEM \n");
            sql.append(" group by DEALER_CODE,sold_by,model_code,month )A\n");
            sql.append(" ON A.DEALER_CODE=Z.DEALER_CODE AND A.MONTH='" + year + "-" + month + "' \n");
            sql.append(" AND A.MODEL_CODE='" + model + "' AND Z.USER_ID=A.SOLD_BY \n");
        } else {
            sql.append(" LEFT JOIN  (select sum(DELIVERY_COUNT) DELIVERY_COUNT,DEALER_CODE,month,sold_by from \n");
            sql.append(" TT_PLAN_PERSONAL group by DEALER_CODE,sold_by,month )A ON A.DEALER_CODE=Z.DEALER_CODE AND A.MONTH='");
            sql.append(year + "-" + month + "' AND Z.USER_ID=A.SOLD_BY \n");
        }
        sql.append(" LEFT JOIN (");
        sql.append(" SELECT SOLD_BY,count(1) REAL_SALES, sum(OLD_CAR) OLD_CAR,Sum(INSURANCE) INSURANCE,SUM(UPHOLSTER_SUM) JINGPIN_AMOUNT,\n");
        sql.append(" SUM(PART_COST_AMOUNT) JINGPIN_COST," + " SUM(VEHICLE_PRICE) SALES_AMOUNT,\n");
        sql.append(" COUNT(IS_JINGPIN) JINGPIN_COUNT,COUNT(IS_FINANCE) FINANCE\n");
        sql.append(" FROM (SELECT A.SOLD_BY,A.VEHICLE_PRICE,case when A.IS_PERMUTED=12781001  then 1 else 0 end  OLD_CAR,case when A.INSURANCE_SUM>0.00 \n");
        sql.append(" then 1 else 0 end  INSURANCE,A.UPHOLSTER_SUM,PART_COST_AMOUNT,IS_JINGPIN,IS_FINANCE FROM tt_sales_order A LEFT JOIN (SELECT DISTINCT SO_NO IS_JINGPIN \n");
        sql.append(" FROM TT_SO_UPHOLSTER  WHERE DEALER_CODE='" + entityCode + "'");
        sql.append(" union SELECT DISTINCT SO_NO IS_JINGPIN FROM TT_SO_PART  WHERE DEALER_CODE='" + entityCode + "' \n");
        sql.append(" )B ON  B.IS_JINGPIN=A.SO_NO ");
        sql.append(" LEFT JOIN (SELECT SUM(PART_COST_AMOUNT) PART_COST_AMOUNT,SO_NO FROM TT_SO_PART WHERE DEALER_CODE='");
        sql.append(entityCode + "' GROUP BY SO_NO " + " )CP ON CP.SO_NO=A.SO_NO " + " LEFT JOIN ( \n");
        sql.append(" SELECT DISTINCT SO_NO IS_FINANCE  FROM TT_CUSTOMER_GATHERING WHERE ( PAY_TYPE_CODE='0010' OR PAY_TYPE_CODE='0011' OR \n");
        sql.append(" PAY_TYPE_CODE='0012') AND DEALER_CODE='" + entityCode + "'" + " )C ON   C.IS_FINANCE=A.SO_NO \n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" INNER JOIN TM_VS_PRODUCT VS ON VS.PRODUCT_CODE=A.PRODUCT_CODE"
                       + " AND VS.DEALER_CODE=A.DEALER_CODE AND VS.MODEL_CODE='" + model + "' \n");
        } ////////// 车型 条件 销售单上只有产品代码的
        sql.append(" WHERE MONTH(A.CREATED_AT)=" + month + " and year(A.CREATED_AT)=" + year
                   + "  and A.DEALER_CODE='");
        sql.append(entityCode + "'");
        sql.append(" AND  (A.SO_STATUS=13011035 or A.SO_STATUS=13011075 ) and a.business_type!=13001005 ) ff \n");
        sql.append(" GROUP BY SOLD_BY" + " )B  ON Z.USER_ID=B.SOLD_BY");
        sql.append(" left join ( select p.sold_by ,sum(case when p.intent_level=13101001 then 1 else 0 end ) H_CUSTOMER,\n");
        sql.append(" sum(case when p.intent_level=13101002 then 1 else 0 end ) A_CUSTOMER,sum(case when p.intent_level=13101003 then 1 else 0 end ) B_CUSTOMER, \n");
        sql.append(" sum(case when p.intent_level=13101004 then 1 else 0 end ) C_CUSTOMER,sum(case when p.intent_level=13101005 then 1 else 0 end ) N_CUSTOMER \n");
        sql.append("  from tm_potential_customer P");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  \n");
            sql.append(" TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
            sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 AND D.INTENT_MODEL='" + model);
            sql.append("'");
        } // 车型 条件
        sql.append(" where MONTH(p.CREATED_AT)=" + month + "" + " and year(p.CREATED_AT)=" + year);
        sql.append(" and p.DEALER_CODE='" + entityCode + "'" + " group by p.sold_by \n");
        sql.append(" )C1 on C1.SOLD_BY=Z.USER_ID " + " left  join \n");
        sql.append(" ( select  p.sold_by, count(distinct p.customer_no) TEST from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i \n");
        sql.append(" on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
        sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 \n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" AND D.INTENT_MODEL='" + model + "' \n");
        } // 车型 条件
        sql.append(" where  MONTH(p.ACTION_DATE)=" + month + " and year(p.ACTION_DATE)=" + year + " and \n");
        sql.append(" p.REAL_VISIT_ACTION like '%试乘试驾%' and p.DEALER_CODE='" + entityCode + "' \n");
        sql.append(" and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b \n");
        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE ");
        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and a.DEALER_CODE='");
        sql.append(entityCode + "' and a.customer_no=p.customer_no) \n");
        sql.append(" and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where \n");
        sql.append(" REAL_VISIT_ACTION like '%试乘试驾%'and date(ACTION_DATE)<'" + year + "-" + month);
        sql.append("-1' and DEALER_CODE='" + entityCode + "' and customer_no=p.customer_no) group by sold_by \n");
        sql.append(" ) C2 on C2.sold_by=Z.USER_ID \n");
        sql.append(" left join ( select   distinct p.sold_by, count(distinct p.customer_no) back from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i  on \n");
        sql.append(" i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
        sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" AND D.INTENT_MODEL='" + model + "'");
        } // 车型 条件
        sql.append(" where  MONTH(p.ACTION_DATE)=" + month + " and year(p.ACTION_DATE)=" + year + " and \n");
        sql.append(" p.IS_SUCCESS_BOOKING=12781001 and p.DEALER_CODE='" + entityCode);
        sql.append("' and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where\n");
        sql.append(" IS_SUCCESS_BOOKING=12781001 and date(ACTION_DATE)<'" + year + "-" + month);
        sql.append("-1' and  p.customer_no=TT_SALES_PROMOTION_PLAN.customer_no and DEALER_CODE='" + entityCode);
        sql.append("') group by sold_by" + " ) C3 on C3.sold_by=Z.USER_ID " + " LEFT JOIN\n");
        sql.append(" ( SELECT COUNT(1) DCC_CUSTOMER ,P.SOLD_BY FROM tm_potential_customer P \n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and i.intent_id=p.intent_id and p.customer_no=i.customer_no inner join  \n");
            sql.append(" TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
            sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 AND D.INTENT_MODEL='" + model);
            sql.append("'");
        } // 车型 条件   DATE_FORMAT(P.DCC_DATE,'%Y-%m-%d') AS DCC_DATE
        sql.append(" WHERE MONTH(P.DCC_DATE)=" + month + " and year(P.DCC_DATE)=" + year + " and P.DEALER_CODE='");
        sql.append(entityCode + "'" + " GROUP BY SOLD_BY " + " )DC on DC.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
        sql.append(" ( SELEcT COUNT(1) COMPLAIN_COUNT,B.BE_COMPLAINT_EMP FROM TT_CUSTOMER_COMPLAINT B WHERE  MONTH(B.COMPLAINT_DATE)=");
        sql.append(month + " " + " and year(B.COMPLAINT_DATE)=" + year);
        sql.append(" AND B.IS_VALID=12781001 and B.COMPLAINT_MAIN_TYPE=13541001 and B.DEALER_CODE='" + entityCode);
        sql.append("'" + " GROUP BY B.BE_COMPLAINT_EMP " + " )CM on CM.BE_COMPLAINT_EMP=Z.EMPLOYEE_NO" + " LEFT JOIN \n");
        sql.append(" ( SELEcT (count(*)-count(customer_no)+count(distinct customer_no)) COME_ALL,B.SOLD_BY FROM TT_VISITING_RECORD B WHERE  MONTH(B.CREATED_AT)=");
        sql.append(month + " " + " and year(B.CREATED_AT)=" + year + " and B.DEALER_CODE='" + entityCode + "'");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" AND B.INTENT_MODEL='" + model + "'");
        } // 车型 条件
        sql.append(" GROUP BY B.SOLD_BY " + " )RE on RE.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
        sql.append(" ( SELEcT count(distinct a.customer_no) CUSTOMER_All,A.SOLD_BY from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d \n");
        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE \n");
        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='");
        sql.append(entityCode + "'");
        sql.append(" AND i.DEALER_CODE=a.DEALER_CODE and a.customer_no=i.customer_no and i.intent_id=a.intent_id AND d.DEALER_CODE=a.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" AND D.INTENT_MODEL='" + model + "'");
        } // 车型 条件
        sql.append(" GROUP BY A.SOLD_BY " + " )RE2 on RE2.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
        sql.append(" ( SELEcT count(distinct a.customer_no) ZT_SALES,sum(case when a.CUS_SOURCE=13111005  then 1 else 0 end ) TRANS_CUSTOMER,A.SOLD_BY\n");
        sql.append("  from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d \n");
        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE \n");
        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='");
        sql.append(entityCode + "'");
        sql.append(" AND  exists(SELECT CUSTOMER_NO from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) and business_type=13001001 \n");
        sql.append(" and DEALER_CODE='" + entityCode + "' AND CUSTOMER_NO=A.CUSTOMER_NO)");
        sql.append(" AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 \n");
        if (!StringUtils.isNullOrEmpty(model)) {
            sql.append(" AND D.INTENT_MODEL='" + model + "'");
        } // 后面加的 展厅交车 转介绍数 当月
        sql.append(" GROUP BY A.SOLD_BY " + " )ZT on ZT.SOLD_BY=Z.USER_ID" + " where Z.DEALER_CODE='" + entityCode);
        sql.append("' AND Z.IS_CONSULTANT=12781001 ");
        sql.append(" and (z.USER_STATUS=12101001 or (z.USER_STATUS=12101002 and z.LOGIN_LAST_TIME>='" + year + "-");
        sql.append(month + "-1'" + " ))");
        if (!StringUtils.isNullOrEmpty(soldBy)) {
            sql.append(" AND Z.USER_ID=" + soldBy);
        } // 车型 条件
       
         sql.append(DAOUtilGF.getFunRangeByStr("Z", "USER_ID", userId, orgCode, functionCode, entityCode));
       
        List<Object> queryList = new ArrayList<Object>();
        System.err.println(sql.toString());
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        return id;
    }

    @Override
    public PageInfoDto querySalesPerformanceDetail(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        String soldBy = queryParam.get("soldBy");
        String yearMonth = queryParam.get("yearMonth");
       
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(6, 7);
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

        sql.append(" SELECT  Z.DEALER_CODE,Z.SERIES_CODE,Z.SERIES_NAME," + "coalesce(B.REAL_SALES,0) REAL_SALES," + "coalesce(JINGPIN_COUNT,0)JINGPIN_COUNT,"
                   + "coalesce(JINGPIN_AMOUNT,0)JINGPIN_AMOUNT," + "coalesce(JINGPIN_COST,0)JINGPIN_COST," + "coalesce(INSURANCE,0)INSURANCE," + "coalesce(FINANCE,0)FINANCE," + "coalesce(OLD_CAR,0)OLD_CAR," + "coalesce(H_CUSTOMER,0)H_CUSTOMER,"
                   + "coalesce(A_CUSTOMER,0)A_CUSTOMER," + "coalesce(B_CUSTOMER,0)B_CUSTOMER," + "coalesce(C_CUSTOMER,0)C_CUSTOMER," + "coalesce(N_CUSTOMER,0)N_CUSTOMER,"
                   + "CONCAT(cast(coalesce(CUSTOMER_All,0)*100.00/coalesce(COME_All,1) as decimal(10,2)))||'%' CREATE_RATE,"
                   + "CONCAT(cast(coalesce(TEST,0)*100.00/coalesce(CUSTOMER_All,1) as decimal(10,2)))||'%' TEST_RATE,"
                   + "CONCAT(cast(coalesce(BACK,0)*100.00/coalesce(CUSTOMER_All,1)  as decimal(10,2)))||'%' BACK_RATE,"
                   + "CONCAT(cast(coalesce(ZT_SALES,0)*100.00/coalesce(CUSTOMER_All,1)   as decimal(10,2)))||'%' DEAL_RATE,"
                   + "CONCAT(cast(coalesce(TRANS_CUSTOMER,0)*100.00/coalesce(ZT_SALES,1) as decimal(10,2)))||'%'  TRANS_RATE,"
                   + "coalesce(CUSTOMER_All,0) CREATE5," + "coalesce(TEST,0) TEST," + "coalesce(BACK,0) BACK,"
                   + "coalesce(ZT_SALES,0) DEAL," + "coalesce(TRANS_CUSTOMER,0) TRANS " + " FROM TM_SERIES Z ");
        sql.append(" LEFT JOIN ("
                   + " SELECT SERIES_CODE,count(1) REAL_SALES, sum(OLD_CAR) OLD_CAR,Sum(INSURANCE) INSURANCE,SUM(UPHOLSTER_SUM) JINGPIN_AMOUNT,"
                   + " SUM(PART_COST_AMOUNT) JINGPIN_COST,"
                   + " COUNT(IS_JINGPIN) JINGPIN_COUNT,COUNT(IS_FINANCE) FINANCE"
                   + " FROM (SELECT VS.SERIES_CODE,case when A.IS_PERMUTED=12781001  then 1 else 0 end  OLD_CAR,case when A.INSURANCE_SUM>0.00 "
                   + " then 1 else 0 end  INSURANCE,A.UPHOLSTER_SUM,PART_COST_AMOUNT,IS_JINGPIN,IS_FINANCE FROM tt_sales_order A LEFT JOIN (SELECT DISTINCT SO_NO IS_JINGPIN "
                   + " FROM TT_SO_UPHOLSTER  WHERE DEALER_CODE='" + entityCode + "'"
                   + " union SELECT DISTINCT SO_NO IS_JINGPIN FROM TT_SO_PART  WHERE DEALER_CODE='" + entityCode + "'"
                   + " )B ON  B.IS_JINGPIN=A.SO_NO "
                   + " LEFT JOIN (SELECT SUM(PART_COST_AMOUNT) PART_COST_AMOUNT,SO_NO FROM TT_SO_PART WHERE DEALER_CODE='"
                   + entityCode + "' GROUP BY SO_NO " + " )CP ON CP.SO_NO=A.SO_NO " + " LEFT JOIN ("
                   + " SELECT DISTINCT SO_NO IS_FINANCE  FROM TT_CUSTOMER_GATHERING WHERE ( PAY_TYPE_CODE='0010' OR PAY_TYPE_CODE='0011' OR"
                   + " PAY_TYPE_CODE='0012') AND DEALER_CODE='" + entityCode + "'" + " )C ON   C.IS_FINANCE=A.SO_NO"
                   + " INNER JOIN TM_VS_PRODUCT VS ON VS.PRODUCT_CODE=A.PRODUCT_CODE"
                   + " AND VS.DEALER_CODE=A.DEALER_CODE WHERE MONTH(A.CREATED_AT)=" + month + " and year(A.CREATED_AT)="
                   + year + "  and A.DEALER_CODE='" + entityCode + "'"
                   + " AND  (A.SO_STATUS=13011035 or A.SO_STATUS=13011075 ) and a.business_type!=13001005 AND a.SOLD_BY="
                   + soldBy + ") ff" + " GROUP BY SERIES_CODE" + " )B  ON Z.SERIES_CODE=B.SERIES_CODE"
                   + " left join ( select d.INTENT_SERIES , sum(case when p.intent_level=13101001 then 1 else 0 end ) H_CUSTOMER,"
                   + " sum(case when p.intent_level=13101002 then 1 else 0 end ) A_CUSTOMER,sum(case when p.intent_level=13101003 then 1 else 0 end ) B_CUSTOMER,"
                   + " sum(case when p.intent_level=13101004 then 1 else 0 end ) C_CUSTOMER,sum(case when p.intent_level=13101005 then 1 else 0 end ) N_CUSTOMER"
                   + "  from tm_potential_customer P"
                   + " inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  "
                   + " TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001" + " where MONTH(p.CREATED_AT)=" + month
                   + "" + " and year(p.CREATED_AT)=" + year + " and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY="
                   + soldBy + " group by d.INTENT_SERIES " + " )C1 on C1.INTENT_SERIES=Z.SERIES_CODE " + " left  join "
                   + " ( select  d.INTENT_SERIES, count(distinct p.customer_no) TEST from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i "
                   + " on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " + " where  MONTH(p.ACTION_DATE)="
                   + month + " and year(p.ACTION_DATE)=" + year + " and "
                   + " p.REAL_VISIT_ACTION like '%试乘试驾%' and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY=" + soldBy
                   + "" + " and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b "
                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and a.DEALER_CODE='"
                   + entityCode + "' and a.customer_no=p.customer_no)"
                   + " and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where "
                   + " REAL_VISIT_ACTION like '%试乘试驾%'and date(ACTION_DATE)<'" + year + "-" + month
                   + "-1' and DEALER_CODE='" + entityCode + "' and customer_no=p.customer_no) group by INTENT_SERIES"
                   + " ) C2 on C2.INTENT_SERIES=Z.SERIES_CODE "
                   + " left join ( select   distinct d.INTENT_SERIES, count(distinct p.customer_no) back from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i  on "
                   + " i.DEALER_CODE=p.DEALER_CODE and i.intent_id=p.intent_id and p.customer_no=i.customer_no inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001" + " where  MONTH(p.ACTION_DATE)="
                   + month + " and year(p.ACTION_DATE)=" + year + " and "
                   + " p.IS_SUCCESS_BOOKING=12781001 and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY=" + soldBy
                   + " and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where"
                   + " IS_SUCCESS_BOOKING=12781001 and date(ACTION_DATE)<'" + year + "-" + month + "-1' and"
                   + " TT_SALES_PROMOTION_PLAN.customer_no=p.customer_no and  DEALER_CODE='" + entityCode
                   + "') group by INTENT_SERIES" + " ) C3 on C3.INTENT_SERIES=Z.SERIES_CODE " + " LEFT JOIN "
                   + " ( SELEcT COUNT(1) COME_ALL,B.INTENT_SERIES FROM TT_VISITING_RECORD B WHERE  MONTH(B.CREATED_AT)="
                   + month + " " + " and year(B.CREATED_AT)=" + year + " AND B.SOLD_BY=" + soldBy
                   + " and B.DEALER_CODE='" + entityCode + "'" + " GROUP BY B.INTENT_SERIES "
                   + " )RE on RE.INTENT_SERIES=Z.SERIES_CODE" + " LEFT JOIN "
                   + " ( SELEcT count(distinct a.customer_no) CUSTOMER_All,D.INTENT_SERIES from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d "
                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='"
                   + entityCode + "' AND A.SOLD_BY=" + soldBy + ""
                   + " AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001"
                   + " GROUP BY D.INTENT_SERIES " + " )RE2 on RE2.INTENT_SERIES=Z.SERIES_CODE" + " LEFT JOIN "
                   + " ( SELEcT count(distinct a.customer_no) ZT_SALES,sum(case when a.CUS_SOURCE=13111005  then 1 else 0 end ) TRANS_CUSTOMER,D.INTENT_SERIES"
                   + "  from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d "
                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='"
                   + entityCode + "' AND A.SOLD_BY=" + soldBy + ""
                   + " AND  exists(SELECT CUSTOMER_NO from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) and business_type=13001001 "
                   + " and DEALER_CODE='" + entityCode + "' AND CUSTOMER_NO=A.CUSTOMER_NO)"
                   + " AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001"
                   + " GROUP BY D.INTENT_SERIES " + " )ZT on ZT.INTENT_SERIES=Z.SERIES_CODE" + " where Z.DEALER_CODE='"
                   + entityCode + "' ");
        // if(Utility.testString(soldBy)){
        // sql.append(" AND Z.USER_ID="+soldBy);}//车型 条件

        List<Object> queryList = new ArrayList<Object>();
        System.err.println(sql.toString());
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);

        return id;
    }

	@Override
	public List<Map> SalesPerformanceExprot(Map<String, String> queryParam) throws ServiceBizException {
		 StringBuffer sql = new StringBuffer("");
	        String soldBy = queryParam.get("soldBy");
	        String model = queryParam.get("modelCode");
	        String yearMonth = queryParam.get("yearMonth");
	        String year = yearMonth.substring(0, 4);
	        String month = yearMonth.substring(6, 7);
	        String functionCode = "606005";
	        Long userId = FrameworkUtil.getLoginInfo().getUserId();
	        String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
	        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

	        sql.append(" SELECT   Z.DEALER_CODE,Z.USER_NAME SOLD_BY,Z.USER_ID," + "coalesce(A.DELIVERY_COUNT,0) GOAL_SALES,\n");
	        sql.append("coalesce(B.REAL_SALES,0) REAL_SALES,\n");
	        sql.append("CASE WHEN A.DELIVERY_COUNT =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(B.REAL_SALES,0)*100.00/coalesce(A.DELIVERY_COUNT,1) as decimal(10,2)))||'%' END  RATE_SALES,\n");
	        sql.append(" (CASE WHEN SALES_AMOUNT IS NOT NULL THEN SALES_AMOUNT ELSE 0 END)    AS   SALES_AMOUNT,(CASE WHEN JINGPIN_COUNT IS NOT NULL THEN JINGPIN_COUNT ELSE 0 END)    AS   JINGPIN_COUNT,(CASE WHEN JINGPIN_AMOUNT IS NOT NULL THEN JINGPIN_AMOUNT ELSE 0 END)    AS   JINGPIN_AMOUNT,(CASE WHEN JINGPIN_COST IS NOT NULL THEN JINGPIN_COST ELSE 0 END)    AS   JINGPIN_COST,(CASE WHEN INSURANCE IS NOT NULL THEN INSURANCE ELSE 0 END)    AS   INSURANCE,(CASE WHEN FINANCE IS NOT NULL THEN FINANCE ELSE 0 END)    AS   FINANCE,(CASE WHEN OLD_CAR IS NOT NULL THEN OLD_CAR ELSE 0 END)    AS   OLD_CAR,(CASE WHEN H_CUSTOMER IS NOT NULL THEN H_CUSTOMER ELSE 0 END)    AS   H_CUSTOMER,(CASE WHEN A_CUSTOMER IS NOT NULL THEN A_CUSTOMER ELSE 0 END)    AS   A_CUSTOMER,(CASE WHEN B_CUSTOMER IS NOT NULL THEN B_CUSTOMER ELSE 0 END)    AS   B_CUSTOMER,(CASE WHEN C_CUSTOMER IS NOT NULL THEN C_CUSTOMER ELSE 0 END)    AS   C_CUSTOMER,(CASE WHEN N_CUSTOMER IS NOT NULL THEN N_CUSTOMER ELSE 0 END)    AS   N_CUSTOMER, \n");
	        sql.append("CASE WHEN COME_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(CUSTOMER_All,0)*100.00/coalesce(COME_All,1) as decimal(10,2)))||'%' END  CREATE_RATE,\n");
	        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(TEST,0)*100.00/coalesce(CUSTOMER_All,1) as decimal(10,2)))||'%' END  TEST_RATE,\n");
	        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(BACK,0)*100.00/coalesce(CUSTOMER_All,1)  as decimal(10,2)))||'%' END  BACK_RATE,\n");
	        sql.append("CASE WHEN CUSTOMER_All =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(ZT_SALES,0)*100.00/coalesce(CUSTOMER_All,1)   as decimal(10,2)))||'%' END  DEAL_RATE,\n");
	        sql.append("CASE WHEN ZT_SALES =0 THEN CONCAT(0.00)||'%' ELSE \n");
	        sql.append("CONCAT(cast(coalesce(TRANS_CUSTOMER,0)*100.00/coalesce(ZT_SALES,1) as decimal(10,2)))||'%' END  TRANS_RATE,\n");
	        sql.append("coalesce(CUSTOMER_All,0) CREATE5," + "coalesce(TEST,0) TEST," + "coalesce(BACK,0) BACK,\n");
	        sql.append("coalesce(ZT_SALES,0) DEAL," + "coalesce(TRANS_CUSTOMER,0) TRANS,\n");
	        sql.append(" (CASE WHEN DCC_CUSTOMER IS NOT NULL THEN DCC_CUSTOMER ELSE 0 END)    AS   DCC_CUSTOMER,(CASE WHEN COMPLAIN_COUNT IS NOT NULL THEN COMPLAIN_COUNT ELSE 0 END)    AS   COMPLAIN_COUNT \n");
	        sql.append(" FROM TM_USER Z \n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" LEFT JOIN ( select sum(DELIVERY_COUNT) DELIVERY_COUNT,DEALER_CODE,month,model_code,sold_by from TT_PLAN_PERSONAL_ITEM \n");
	            sql.append(" group by DEALER_CODE,sold_by,model_code,month )A\n");
	            sql.append(" ON A.DEALER_CODE=Z.DEALER_CODE AND A.MONTH='" + year + "-" + month + "' \n");
	            sql.append(" AND A.MODEL_CODE='" + model + "' AND Z.USER_ID=A.SOLD_BY \n");
	        } else {
	            sql.append(" LEFT JOIN  (select sum(DELIVERY_COUNT) DELIVERY_COUNT,DEALER_CODE,month,sold_by from \n");
	            sql.append(" TT_PLAN_PERSONAL group by DEALER_CODE,sold_by,month )A ON A.DEALER_CODE=Z.DEALER_CODE AND A.MONTH='");
	            sql.append(year + "-" + month + "' AND Z.USER_ID=A.SOLD_BY \n");
	        }
	        sql.append(" LEFT JOIN (");
	        sql.append(" SELECT SOLD_BY,count(1) REAL_SALES, sum(OLD_CAR) OLD_CAR,Sum(INSURANCE) INSURANCE,SUM(UPHOLSTER_SUM) JINGPIN_AMOUNT,\n");
	        sql.append(" SUM(PART_COST_AMOUNT) JINGPIN_COST," + " SUM(VEHICLE_PRICE) SALES_AMOUNT,\n");
	        sql.append(" COUNT(IS_JINGPIN) JINGPIN_COUNT,COUNT(IS_FINANCE) FINANCE\n");
	        sql.append(" FROM (SELECT A.SOLD_BY,A.VEHICLE_PRICE,case when A.IS_PERMUTED=12781001  then 1 else 0 end  OLD_CAR,case when A.INSURANCE_SUM>0.00 \n");
	        sql.append(" then 1 else 0 end  INSURANCE,A.UPHOLSTER_SUM,PART_COST_AMOUNT,IS_JINGPIN,IS_FINANCE FROM tt_sales_order A LEFT JOIN (SELECT DISTINCT SO_NO IS_JINGPIN \n");
	        sql.append(" FROM TT_SO_UPHOLSTER  WHERE DEALER_CODE='" + entityCode + "'");
	        sql.append(" union SELECT DISTINCT SO_NO IS_JINGPIN FROM TT_SO_PART  WHERE DEALER_CODE='" + entityCode + "' \n");
	        sql.append(" )B ON  B.IS_JINGPIN=A.SO_NO ");
	        sql.append(" LEFT JOIN (SELECT SUM(PART_COST_AMOUNT) PART_COST_AMOUNT,SO_NO FROM TT_SO_PART WHERE DEALER_CODE='");
	        sql.append(entityCode + "' GROUP BY SO_NO " + " )CP ON CP.SO_NO=A.SO_NO " + " LEFT JOIN ( \n");
	        sql.append(" SELECT DISTINCT SO_NO IS_FINANCE  FROM TT_CUSTOMER_GATHERING WHERE ( PAY_TYPE_CODE='0010' OR PAY_TYPE_CODE='0011' OR \n");
	        sql.append(" PAY_TYPE_CODE='0012') AND DEALER_CODE='" + entityCode + "'" + " )C ON   C.IS_FINANCE=A.SO_NO \n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" INNER JOIN TM_VS_PRODUCT VS ON VS.PRODUCT_CODE=A.PRODUCT_CODE"
	                       + " AND VS.DEALER_CODE=A.DEALER_CODE AND VS.MODEL_CODE='" + model + "' \n");
	        } ////////// 车型 条件 销售单上只有产品代码的
	        sql.append(" WHERE MONTH(A.CREATED_AT)=" + month + " and year(A.CREATED_AT)=" + year
	                   + "  and A.DEALER_CODE='");
	        sql.append(entityCode + "'");
	        sql.append(" AND  (A.SO_STATUS=13011035 or A.SO_STATUS=13011075 ) and a.business_type!=13001005 ) ff \n");
	        sql.append(" GROUP BY SOLD_BY" + " )B  ON Z.USER_ID=B.SOLD_BY");
	        sql.append(" left join ( select p.sold_by ,sum(case when p.intent_level=13101001 then 1 else 0 end ) H_CUSTOMER,\n");
	        sql.append(" sum(case when p.intent_level=13101002 then 1 else 0 end ) A_CUSTOMER,sum(case when p.intent_level=13101003 then 1 else 0 end ) B_CUSTOMER, \n");
	        sql.append(" sum(case when p.intent_level=13101004 then 1 else 0 end ) C_CUSTOMER,sum(case when p.intent_level=13101005 then 1 else 0 end ) N_CUSTOMER \n");
	        sql.append("  from tm_potential_customer P");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  \n");
	            sql.append(" TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
	            sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 AND D.INTENT_MODEL='" + model);
	            sql.append("'");
	        } // 车型 条件
	        sql.append(" where MONTH(p.CREATED_AT)=" + month + "" + " and year(p.CREATED_AT)=" + year);
	        sql.append(" and p.DEALER_CODE='" + entityCode + "'" + " group by p.sold_by \n");
	        sql.append(" )C1 on C1.SOLD_BY=Z.USER_ID " + " left  join \n");
	        sql.append(" ( select  p.sold_by, count(distinct p.customer_no) TEST from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i \n");
	        sql.append(" on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
	        sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 \n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" AND D.INTENT_MODEL='" + model + "' \n");
	        } // 车型 条件
	        sql.append(" where  MONTH(p.ACTION_DATE)=" + month + " and year(p.ACTION_DATE)=" + year + " and \n");
	        sql.append(" p.REAL_VISIT_ACTION like '%试乘试驾%' and p.DEALER_CODE='" + entityCode + "' \n");
	        sql.append(" and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b \n");
	        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
	        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE ");
	        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and a.DEALER_CODE='");
	        sql.append(entityCode + "' and a.customer_no=p.customer_no) \n");
	        sql.append(" and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where \n");
	        sql.append(" REAL_VISIT_ACTION like '%试乘试驾%'and date(ACTION_DATE)<'" + year + "-" + month);
	        sql.append("-1' and DEALER_CODE='" + entityCode + "' and customer_no=p.customer_no) group by sold_by \n");
	        sql.append(" ) C2 on C2.sold_by=Z.USER_ID \n");
	        sql.append(" left join ( select   distinct p.sold_by, count(distinct p.customer_no) back from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i  on \n");
	        sql.append(" i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
	        sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" AND D.INTENT_MODEL='" + model + "'");
	        } // 车型 条件
	        sql.append(" where  MONTH(p.ACTION_DATE)=" + month + " and year(p.ACTION_DATE)=" + year + " and \n");
	        sql.append(" p.IS_SUCCESS_BOOKING=12781001 and p.DEALER_CODE='" + entityCode);
	        sql.append("' and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where\n");
	        sql.append(" IS_SUCCESS_BOOKING=12781001 and date(ACTION_DATE)<'" + year + "-" + month);
	        sql.append("-1' and  p.customer_no=TT_SALES_PROMOTION_PLAN.customer_no and DEALER_CODE='" + entityCode);
	        sql.append("') group by sold_by" + " ) C3 on C3.sold_by=Z.USER_ID " + " LEFT JOIN\n");
	        sql.append(" ( SELECT COUNT(1) DCC_CUSTOMER ,P.SOLD_BY FROM tm_potential_customer P \n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and i.intent_id=p.intent_id and p.customer_no=i.customer_no inner join  \n");
	            sql.append(" TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE \n");
	            sql.append(" and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 AND D.INTENT_MODEL='" + model);
	            sql.append("'");
	        } // 车型 条件
	        sql.append(" WHERE MONTH(P.DCC_DATE)=" + month + " and year(P.DCC_DATE)=" + year + " and P.DEALER_CODE='");
	        sql.append(entityCode + "'" + " GROUP BY SOLD_BY " + " )DC on DC.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
	        sql.append(" ( SELEcT COUNT(1) COMPLAIN_COUNT,B.BE_COMPLAINT_EMP FROM TT_CUSTOMER_COMPLAINT B WHERE  MONTH(B.COMPLAINT_DATE)=");
	        sql.append(month + " " + " and year(B.COMPLAINT_DATE)=" + year);
	        sql.append(" AND B.IS_VALID=12781001 and B.COMPLAINT_MAIN_TYPE=13541001 and B.DEALER_CODE='" + entityCode);
	        sql.append("'" + " GROUP BY B.BE_COMPLAINT_EMP " + " )CM on CM.BE_COMPLAINT_EMP=Z.EMPLOYEE_NO" + " LEFT JOIN \n");
	        sql.append(" ( SELEcT (count(*)-count(customer_no)+count(distinct customer_no)) COME_ALL,B.SOLD_BY FROM TT_VISITING_RECORD B WHERE  MONTH(B.CREATED_AT)=");
	        sql.append(month + " " + " and year(B.CREATED_AT)=" + year + " and B.DEALER_CODE='" + entityCode + "'");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" AND B.INTENT_MODEL='" + model + "'");
	        } // 车型 条件
	        sql.append(" GROUP BY B.SOLD_BY " + " )RE on RE.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
	        sql.append(" ( SELEcT count(distinct a.customer_no) CUSTOMER_All,A.SOLD_BY from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d \n");
	        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
	        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE \n");
	        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='");
	        sql.append(entityCode + "'");
	        sql.append(" AND i.DEALER_CODE=a.DEALER_CODE and a.customer_no=i.customer_no and i.intent_id=a.intent_id AND d.DEALER_CODE=a.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" AND D.INTENT_MODEL='" + model + "'");
	        } // 车型 条件
	        sql.append(" GROUP BY A.SOLD_BY " + " )RE2 on RE2.SOLD_BY=Z.USER_ID" + " LEFT JOIN \n");
	        sql.append(" ( SELEcT count(distinct a.customer_no) ZT_SALES,sum(case when a.CUS_SOURCE=13111005  then 1 else 0 end ) TRANS_CUSTOMER,A.SOLD_BY\n");
	        sql.append("  from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d \n");
	        sql.append(" where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month);
	        sql.append(" and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE \n");
	        sql.append(" and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='");
	        sql.append(entityCode + "'");
	        sql.append(" AND  exists(SELECT CUSTOMER_NO from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) and business_type=13001001 \n");
	        sql.append(" and DEALER_CODE='" + entityCode + "' AND CUSTOMER_NO=A.CUSTOMER_NO)");
	        sql.append(" AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 \n");
	        if (!StringUtils.isNullOrEmpty(model)) {
	            sql.append(" AND D.INTENT_MODEL='" + model + "'");
	        } // 后面加的 展厅交车 转介绍数 当月
	        sql.append(" GROUP BY A.SOLD_BY " + " )ZT on ZT.SOLD_BY=Z.USER_ID" + " where Z.DEALER_CODE='" + entityCode);
	        sql.append("' AND Z.IS_CONSULTANT=12781001 ");
	        sql.append(" and (z.USER_STATUS=12101001 or (z.USER_STATUS=12101002 and z.LOGIN_LAST_TIME>='" + year + "-");
	        sql.append(month + "-1'" + " ))");
	        if (!StringUtils.isNullOrEmpty(soldBy)) {
	            sql.append(" AND Z.USER_ID=" + soldBy);
	        } // 车型 条件
	       
	         sql.append(DAOUtilGF.getFunRangeByStr("Z", "USER_ID", userId, orgCode, functionCode, entityCode));
		System.err.println(sql.toString());
		List<Object> insuranceSql =new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sql.toString(), insuranceSql);
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("销售业绩一览");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;
	}
	
	
	@Override
	public List<Map> SeriesExprot(Map<String, String> queryParam) throws ServiceBizException {
		  StringBuffer sql = new StringBuffer("");
	        String soldBy = queryParam.get("userId");
	        String yearMonth = queryParam.get("yearMonth");
	        String year = yearMonth.substring(0, 4);
	        String month = yearMonth.substring(6, 7);
	        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

	        sql.append(" SELECT  Z.DEALER_CODE,Z.SERIES_CODE,Z.SERIES_NAME," + "coalesce(B.REAL_SALES,0) REAL_SALES," + "coalesce(JINGPIN_COUNT,0)JINGPIN_COUNT,"
	                   + "coalesce(JINGPIN_AMOUNT,0)JINGPIN_AMOUNT," + "coalesce(JINGPIN_COST,0)JINGPIN_COST," + "coalesce(INSURANCE,0)INSURANCE," + "coalesce(FINANCE,0)FINANCE," + "coalesce(OLD_CAR,0)OLD_CAR," + "coalesce(H_CUSTOMER,0)H_CUSTOMER,"
	                   + "coalesce(A_CUSTOMER,0)A_CUSTOMER," + "coalesce(B_CUSTOMER,0)B_CUSTOMER," + "coalesce(C_CUSTOMER,0)C_CUSTOMER," + "coalesce(N_CUSTOMER,0)N_CUSTOMER,"
	                   + "CONCAT(cast(coalesce(CUSTOMER_All,0)*100.00/coalesce(COME_All,1) as decimal(10,2)))||'%' CREATE_RATE,"
	                   + "CONCAT(cast(coalesce(TEST,0)*100.00/coalesce(CUSTOMER_All,1) as decimal(10,2)))||'%' TEST_RATE,"
	                   + "CONCAT(cast(coalesce(BACK,0)*100.00/coalesce(CUSTOMER_All,1)  as decimal(10,2)))||'%' BACK_RATE,"
	                   + "CONCAT(cast(coalesce(ZT_SALES,0)*100.00/coalesce(CUSTOMER_All,1)   as decimal(10,2)))||'%' DEAL_RATE,"
	                   + "CONCAT(cast(coalesce(TRANS_CUSTOMER,0)*100.00/coalesce(ZT_SALES,1) as decimal(10,2)))||'%'  TRANS_RATE,"
	                   + "coalesce(CUSTOMER_All,0) CREATE5," + "coalesce(TEST,0) TEST," + "coalesce(BACK,0) BACK,"
	                   + "coalesce(ZT_SALES,0) DEAL," + "coalesce(TRANS_CUSTOMER,0) TRANS " + " FROM TM_SERIES Z ");
	        sql.append(" LEFT JOIN ("
	                   + " SELECT SERIES_CODE,count(1) REAL_SALES, sum(OLD_CAR) OLD_CAR,Sum(INSURANCE) INSURANCE,SUM(UPHOLSTER_SUM) JINGPIN_AMOUNT,"
	                   + " SUM(PART_COST_AMOUNT) JINGPIN_COST,"
	                   + " COUNT(IS_JINGPIN) JINGPIN_COUNT,COUNT(IS_FINANCE) FINANCE"
	                   + " FROM (SELECT VS.SERIES_CODE,case when A.IS_PERMUTED=12781001  then 1 else 0 end  OLD_CAR,case when A.INSURANCE_SUM>0.00 "
	                   + " then 1 else 0 end  INSURANCE,A.UPHOLSTER_SUM,PART_COST_AMOUNT,IS_JINGPIN,IS_FINANCE FROM tt_sales_order A LEFT JOIN (SELECT DISTINCT SO_NO IS_JINGPIN "
	                   + " FROM TT_SO_UPHOLSTER  WHERE DEALER_CODE='" + entityCode + "'"
	                   + " union SELECT DISTINCT SO_NO IS_JINGPIN FROM TT_SO_PART  WHERE DEALER_CODE='" + entityCode + "'"
	                   + " )B ON  B.IS_JINGPIN=A.SO_NO "
	                   + " LEFT JOIN (SELECT SUM(PART_COST_AMOUNT) PART_COST_AMOUNT,SO_NO FROM TT_SO_PART WHERE DEALER_CODE='"
	                   + entityCode + "' GROUP BY SO_NO " + " )CP ON CP.SO_NO=A.SO_NO " + " LEFT JOIN ("
	                   + " SELECT DISTINCT SO_NO IS_FINANCE  FROM TT_CUSTOMER_GATHERING WHERE ( PAY_TYPE_CODE='0010' OR PAY_TYPE_CODE='0011' OR"
	                   + " PAY_TYPE_CODE='0012') AND DEALER_CODE='" + entityCode + "'" + " )C ON   C.IS_FINANCE=A.SO_NO"
	                   + " INNER JOIN TM_VS_PRODUCT VS ON VS.PRODUCT_CODE=A.PRODUCT_CODE"
	                   + " AND VS.DEALER_CODE=A.DEALER_CODE WHERE MONTH(A.CREATED_AT)=" + month + " and year(A.CREATED_AT)="
	                   + year + "  and A.DEALER_CODE='" + entityCode + "'"
	                   + " AND  (A.SO_STATUS=13011035 or A.SO_STATUS=13011075 ) and a.business_type!=13001005 AND a.SOLD_BY="
	                   + soldBy + ") ff" + " GROUP BY SERIES_CODE" + " )B  ON Z.SERIES_CODE=B.SERIES_CODE"
	                   + " left join ( select d.INTENT_SERIES , sum(case when p.intent_level=13101001 then 1 else 0 end ) H_CUSTOMER,"
	                   + " sum(case when p.intent_level=13101002 then 1 else 0 end ) A_CUSTOMER,sum(case when p.intent_level=13101003 then 1 else 0 end ) B_CUSTOMER,"
	                   + " sum(case when p.intent_level=13101004 then 1 else 0 end ) C_CUSTOMER,sum(case when p.intent_level=13101005 then 1 else 0 end ) N_CUSTOMER"
	                   + "  from tm_potential_customer P"
	                   + " inner join  TT_CUSTOMER_INTENT i on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  "
	                   + " TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
	                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001" + " where MONTH(p.CREATED_AT)=" + month
	                   + "" + " and year(p.CREATED_AT)=" + year + " and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY="
	                   + soldBy + " group by d.INTENT_SERIES " + " )C1 on C1.INTENT_SERIES=Z.SERIES_CODE " + " left  join "
	                   + " ( select  d.INTENT_SERIES, count(distinct p.customer_no) TEST from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i "
	                   + " on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
	                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " + " where  MONTH(p.ACTION_DATE)="
	                   + month + " and year(p.ACTION_DATE)=" + year + " and "
	                   + " p.REAL_VISIT_ACTION like '%试乘试驾%' and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY=" + soldBy
	                   + "" + " and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b "
	                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
	                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
	                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and a.DEALER_CODE='"
	                   + entityCode + "' and a.customer_no=p.customer_no)"
	                   + " and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where "
	                   + " REAL_VISIT_ACTION like '%试乘试驾%'and date(ACTION_DATE)<'" + year + "-" + month
	                   + "-1' and DEALER_CODE='" + entityCode + "' and customer_no=p.customer_no) group by INTENT_SERIES"
	                   + " ) C2 on C2.INTENT_SERIES=Z.SERIES_CODE "
	                   + " left join ( select   distinct d.INTENT_SERIES, count(distinct p.customer_no) back from TT_SALES_PROMOTION_PLAN p inner join  TT_CUSTOMER_INTENT i  on "
	                   + " i.DEALER_CODE=p.DEALER_CODE and i.intent_id=p.intent_id and p.customer_no=i.customer_no inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE "
	                   + " and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001" + " where  MONTH(p.ACTION_DATE)="
	                   + month + " and year(p.ACTION_DATE)=" + year + " and "
	                   + " p.IS_SUCCESS_BOOKING=12781001 and p.DEALER_CODE='" + entityCode + "' AND P.SOLD_BY=" + soldBy
	                   + " and not exists (select customer_no from TT_SALES_PROMOTION_PLAN where"
	                   + " IS_SUCCESS_BOOKING=12781001 and date(ACTION_DATE)<'" + year + "-" + month + "-1' and"
	                   + " TT_SALES_PROMOTION_PLAN.customer_no=p.customer_no and  DEALER_CODE='" + entityCode
	                   + "') group by INTENT_SERIES" + " ) C3 on C3.INTENT_SERIES=Z.SERIES_CODE " + " LEFT JOIN "
	                   + " ( SELEcT COUNT(1) COME_ALL,B.INTENT_SERIES FROM TT_VISITING_RECORD B WHERE  MONTH(B.CREATED_AT)="
	                   + month + " " + " and year(B.CREATED_AT)=" + year + " AND B.SOLD_BY=" + soldBy
	                   + " and B.DEALER_CODE='" + entityCode + "'" + " GROUP BY B.INTENT_SERIES "
	                   + " )RE on RE.INTENT_SERIES=Z.SERIES_CODE" + " LEFT JOIN "
	                   + " ( SELEcT count(distinct a.customer_no) CUSTOMER_All,D.INTENT_SERIES from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d "
	                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
	                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
	                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='"
	                   + entityCode + "' AND A.SOLD_BY=" + soldBy + ""
	                   + " AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001"
	                   + " GROUP BY D.INTENT_SERIES " + " )RE2 on RE2.INTENT_SERIES=Z.SERIES_CODE" + " LEFT JOIN "
	                   + " ( SELEcT count(distinct a.customer_no) ZT_SALES,sum(case when a.CUS_SOURCE=13111005  then 1 else 0 end ) TRANS_CUSTOMER,D.INTENT_SERIES"
	                   + "  from tm_potential_customer a,TT_VISITING_RECORD b,TT_CUSTOMER_INTENT i,TT_CUSTOMER_INTENT_DETAIL d "
	                   + " where a.customer_no=b.customer_no and  MONTH(B.CREATED_AT)=" + month
	                   + " and MONTH(a.CREATED_AT)=" + month + " and a.DEALER_CODE=b.DEALER_CODE "
	                   + " and year(B.CREATED_AT)=" + year + " and year(a.CREATED_AT)=" + year + " and B.DEALER_CODE='"
	                   + entityCode + "' AND A.SOLD_BY=" + soldBy + ""
	                   + " AND  exists(SELECT CUSTOMER_NO from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) and business_type=13001001 "
	                   + " and DEALER_CODE='" + entityCode + "' AND CUSTOMER_NO=A.CUSTOMER_NO)"
	                   + " AND i.DEALER_CODE=A.DEALER_CODE and A.customer_no=i.customer_no and i.intent_id=A.intent_id AND d.DEALER_CODE=A.DEALER_CODE  and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001"
	                   + " GROUP BY D.INTENT_SERIES " + " )ZT on ZT.INTENT_SERIES=Z.SERIES_CODE" + " where Z.DEALER_CODE='"
	                   + entityCode + "' ");
		System.err.println(sql.toString());
		List<Object> insuranceSql =new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sql.toString(), insuranceSql);
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("销售业绩一览");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;
		
	}

}
