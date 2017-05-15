
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : queryHopeCusServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月20日    zhanshiwei    1.0
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

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* 有望客户跟踪进度
* @author zhanshiwei
* @date 2017年1月20日
*/
@Service
public class queryHopeCusServiceImpl implements queryHopeCusService{

    @Override
    public PageInfoDto queryHopeCustomer(Map<String, String> param) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        String entityCode=FrameworkUtil.getLoginInfo().getDealerCode();
        String begindate1=param.get("startdate");
        String begindate=begindate1+"-01 00:00:00";//本月开始
        String enddate = DateUtil.formatDefaultDate(DateUtil.getLastDayOfMonth(DateUtil.parseDefaultDate(begindate)))+" 23:59:59";//本月结束
        String   soldby = param.get("soldBy");// Long.parseLong();//销售顾问
       
        sql.append(
                

                "\n" +
                "select  m.*,\n" + 
                "t.item_id\n" + 
                ",(select DISTINCT tn.MODEL_NAME  from    TT_CUSTOMER_INTENT_DETAIL a LEFT JOIN TM_MODEL tn ON a.INTENT_MODEL=tn.MODEL_CODE AND a.DEALER_CODE=tn.DEALER_CODE\n" + 
                "where  ITEM_ID =t.item_id and a.DEALER_CODE='"+entityCode+"' )  INTENT_MODEL\n" + 
                "\n" + 
                ",(select DISTINCT tc.COLOR_NAME  from    TT_CUSTOMER_INTENT_DETAIL a LEFT JOIN TM_COLOR tc ON a.INTENT_COLOR=tc.COLOR_CODE AND a.DEALER_CODE=tc.DEALER_CODE\n" + 
                "where  ITEM_ID =t.item_id and a.DEALER_CODE='"+entityCode+"' )  INTENT_COLOR\n" + 
                "  from  ( "
        
        
        );
        sql
                .append("SELECT CUSTOMER_NO,CUSTOMER_NAME,(case when CONTACTOR_PHONE is null or CONTACTOR_PHONE=''  then CONTACTOR_MOBILE else CONTACTOR_PHONE end ) as CONTACTOR_PHONE,CUS_SOURCE," 
                        //"INTENT_MODEL,INTENT_COLOR,"
                        + "LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31 " +

                        "      \n" +//---00
                        "      ,INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
/*                      ",(select a.INTENT_MODEL  from    TT_CUSTOMER_INTENT_DETAIL a\n" + 
                        "where  ITEM_ID =( select max(b.item_id)  from TT_CUSTOMER_INTENT_DETAIL b where  b.INTENT_ID=INTENT_ID and b.entity_code=ENTITY_CODE\n" + 
                        "))  INTENT_MODEL\n" + 
                        "\n" + 
                        ",(select a.INTENT_COLOR  from    TT_CUSTOMER_INTENT_DETAIL a\n" + 
                        "where  ITEM_ID =( select max(b.item_id)  from TT_CUSTOMER_INTENT_DETAIL b where  b.INTENT_ID=INTENT_ID and b.entity_code=ENTITY_CODE\n" + 
                        "))  INTENT_COLOR\n" + */
                        "     \n"+// ---11111111111

                        "  FROM ("
                        + "SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE" 
                            //  ",INTENT_MODEL,INTENT_COLOR,"
                        + ",LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31" +

                        "\n" +//---00
                        ",INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
                        "\n"+
                        //---11111111111
        
                        " FROM ("
                        + "SELECT A1.*" +
                                //",A2.INTENT_MODEL,A2.INTENT_COLOR,A2.INTENT_ID " +
                        "  FROM("
                        + "SELECT F1.*,F2.INTENT_ID" +

                        "\n" +//--0000000000000
                        ",f2.INIT_LEVEL,f2.DEALER_CODE\n" + 
                        "\n"+//--1111111111111111
        
                        " FROM("
                        + "SELECT G.*,H.CUS_SOURCE,H.CONTACTOR_PHONE,H.CONTACTOR_MOBILE,H.LAST_NEXT_GRADE,H.REMARK FROM ("
                        + "SELECT E.CUSTOMER_NAME,E.CUSTOMER_NO,E.SOLD_BY,"
                        + "sum(case when (DAY(E.ACTION_DATE)=1) then next_grade end)as C1,"
                        + "sum(case when (DAY(E.ACTION_DATE)=2) then next_grade end)as C2,"
                        + "sum(case when (DAY(E.ACTION_DATE)=3) then next_grade end)as C3,"
                        + "sum(case when (DAY(E.ACTION_DATE)=4) then next_grade end)as C4,"
                        + "sum(case when (DAY(E.ACTION_DATE)=5) then next_grade end)as C5,"
                        + "sum(case when (DAY(E.ACTION_DATE)=6) then next_grade end)as C6,"
                        + "sum(case when (DAY(E.ACTION_DATE)=7) then next_grade end)as C7,"
                        + "sum(case when (DAY(E.ACTION_DATE)=8) then next_grade end)as C8,"
                        + "sum(case when (DAY(E.ACTION_DATE)=9) then next_grade end)as C9,"
                        + "sum(case when (DAY(E.ACTION_DATE)=10) then next_grade end)as C10,"
                        + "sum(case when (DAY(E.ACTION_DATE)=11) then next_grade end)as C11,"
                        + "sum(case when (DAY(E.ACTION_DATE)=12) then next_grade end)as C12,"
                        + "sum(case when (DAY(E.ACTION_DATE)=13) then next_grade end)as C13,"
                        + "sum(case when (DAY(E.ACTION_DATE)=14) then next_grade end)as C14,"
                        + "sum(case when (DAY(E.ACTION_DATE)=15) then next_grade end)as C15,"
                        + "sum(case when (DAY(E.ACTION_DATE)=16) then next_grade end)as C16,"
                        + "sum(case when (DAY(E.ACTION_DATE)=17) then next_grade end)as C17,"
                        + "sum(case when (DAY(E.ACTION_DATE)=18) then next_grade end)as C18,"
                        + "sum(case when (DAY(E.ACTION_DATE)=19) then next_grade end)as C19,"
                        + "sum(case when (DAY(E.ACTION_DATE)=20) then next_grade end)as C20,"
                        + "sum(case when (DAY(E.ACTION_DATE)=21) then next_grade end)as C21,"
                        + "sum(case when (DAY(E.ACTION_DATE)=22) then next_grade end)as C22,"
                        + "sum(case when (DAY(E.ACTION_DATE)=23) then next_grade end)as C23,"
                        + "sum(case when (DAY(E.ACTION_DATE)=24) then next_grade end)as C24,"
                        + "sum(case when (DAY(E.ACTION_DATE)=25) then next_grade end)as C25,"
                        + "sum(case when (DAY(E.ACTION_DATE)=26) then next_grade end)as C26,"
                        + "sum(case when (DAY(E.ACTION_DATE)=27) then next_grade end)as C27,"
                        + "sum(case when (DAY(E.ACTION_DATE)=28) then next_grade end)as C28,"
                        + "sum(case when (DAY(E.ACTION_DATE)=29) then next_grade end)as C29,"
                        + "sum(case when (DAY(E.ACTION_DATE)=30) then next_grade end)as C30,"
                        + "sum(case when (DAY(E.ACTION_DATE)=31) then next_grade end)as C31 "
                        + " FROM(SELECT A.CUSTOMER_NO,A.CUSTOMER_NAME, DATE_FORMAT(ACTION_DATE,'%Y-%m-%d') AS"
                        + "  ACTION_DATE,NEXT_GRADE,a.CREATED_AT,A.SOLD_BY FROM TT_SALES_PROMOTION_PLAN A"
                        + " INNER JOIN (" +
                        //"SELECT CUSTOMER_NO,CUSTOMER_NAME,"+CommonSqlMethod.formatDate("", "SCHEDULE_DATE")+"AS "
                        //+ " SCHEDULE_DATE,MAX(CREATE_DATE) AS CREATE_DATE FROM TT_SALES_PROMOTION_PLAN "
                        //+ " WHERE 1=1 "+Utility.getDateCond("", "SCHEDULE_DATE", begindate, enddate)
                        //+ " AND "
                        //+ " PROM_RESULT IS NOT NULL AND PROM_RESULT<>0 "
                        //+ " GROUP BY CUSTOMER_NO,CUSTOMER_NAME,SCHEDULE_DATE "
                        //+ " )B ON (A.CUSTOMER_NO = B.CUSTOMER_NO " +
                        //"AND "+CommonSqlMethod.formatDate("A", "SCHEDULE_DATE")+"="+CommonSqlMethod.formatDate("B", "SCHEDULE_DATE")+" AND "
                        //+ " A.CREATE_DATE = B.CREATE_DATE)" +

                        "select customer_no, max(item_id) item_id from TT_SALES_PROMOTION_PLAN\n" +
                        "where PROM_RESULT is not null and PROM_RESULT<> 0 and DEALER_CODE='"+entityCode+"' \n" + 
                        Utility.getDateCond(null, "ACTION_DATE", begindate, enddate)+" "+
                        "group by customer_no\n" + 
                        "     )B\n" + 
                        "      ON A.CUSTOMER_NO = B.CUSTOMER_NO and a.item_id=b.item_id \n" + 
                        "   where a.DEALER_CODE='"+entityCode+"' \n" + 
                        //"            a.SCHEDULE_DATE >='2010-11-01 00:00:00'\n" + 
                        //"     AND  a.SCHEDULE_DATE <= '2010-11-30 23:59:59'"+
                        Utility.getDateCond("a", "ACTION_DATE", begindate, enddate)+" "+
                                                                                            
                        ")E GROUP BY E.CUSTOMER_NAME,E.CUSTOMER_NO,E.SOLD_BY "
                        + " )G,(SELECT C.*  " +
                        

                        "\n" +//--0000aa
                        "\n" + 
                        ",(\n" + 
                        "select  a.NEXT_GRADE  from  TT_SALES_PROMOTION_PLAN A\n" + 
                        "where a.DEALER_CODE='"+entityCode+"' and a.item_id = (\n" + 
                        "select max(b.item_id)\n" + 
                        "    FROM TT_SALES_PROMOTION_PLAN b\n" + 
                        "                    WHERE (b.PROM_RESULT IS NOT NULL)\n" + 
                        "                      AND (b.PROM_RESULT <> 0)\n" + 
                        "                      AND 1 = 1\n" 
                        //"                      AND b.SCHEDULE_DATE >= '2010-11-01 00:00:00'\n" + 
                        //"                      AND b.SCHEDULE_DATE <= '2010-11-30 23:59:59'\n" +
                        +Utility.getDateCond("b", "ACTION_DATE", begindate, enddate)+" "+
                        "          and b.customer_no=C.customer_no and b.DEALER_CODE='"+entityCode+"'\n" + 
                        ")\n" + 
                        ") LAST_NEXT_GRADE\n" + 
                        "\n "+
                        //---111111111111111111aaaa

                        
                        " FROM ( "
                        + " SELECT B.*,A.CUSTOMER_NAME,A.CUS_SOURCE,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.FOUND_DATE,A.REMARK FROM TM_POTENTIAL_CUSTOMER A,"
                        + " (select customer_no from TM_POTENTIAL_CUSTOMER "
                        + " where DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)
                        + " UNION SELECT CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN "
                        + " WHERE ( PROM_RESULT IS NOT NULL) AND ACTION_DATE IS NOT NULL "
                        + " AND( PROM_RESULT<>0) AND(PRIOR_GRADE IS NOT NULL) AND (PRIOR_GRADE<>0)"
                        + " AND (NEXT_GRADE IS NOT NULL) AND( NEXT_GRADE<>0) "
                        + " AND DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "ACTION_DATE", begindate, enddate)+" GROUP BY CUSTOMER_NO "
                        + " )B WHERE A.CUSTOMER_NO=B.CUSTOMER_NO and a.DEALER_CODE='"+entityCode+"' ORDER BY A.CUSTOMER_NO"
                        + ")C " +
                        /*
                        "LEFT JOIN(SELECT B.*,A.NEXT_GRADE AS LAST_NEXT_GRADE "
                        + " FROM TT_SALES_PROMOTION_PLAN A,(SELECT customer_no, MAX(CREATE_DATE) AS CREATE_DATE "
                        + " FROM TT_SALES_PROMOTION_PLAN WHERE (PROM_RESULT IS NOT NULL) AND (PROM_RESULT<>0) "
                        + " AND 1=1 "+Utility.getDateCond("", "SCHEDULE_DATE", begindate, enddate)+" GROUP BY "
                        + " CUSTOMER_NO )B WHERE A.CREATE_DATE=B.CREATE_DATE AND A.customer_no=B.customer_no"
                        + ")D ON C.CUSTOMER_NO=D.CUSTOMER_NO " +
                        */
                        ")H WHERE G.CUSTOMER_NO=H.CUSTOMER_NO"
                        + ")F1,TM_POTENTIAL_CUSTOMER F2 WHERE F1.CUSTOMER_NO=F2.CUSTOMER_NO and F2.DEALER_CODE='"+entityCode+"'"
                        + ")A1 " 
                        //"LEFT JOIN(SELECT H1.INTENT_ID,H1.INTENT_MODEL,H1.INTENT_COLOR,H1.CREATE_DATE "
                        //+ "FROM TT_CUSTOMER_INTENT_DETAIL H1 INNER JOIN("
                        //+ "SELECT INTENT_ID,MAX(CREATE_DATE)AS CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL "
                        //+ "GROUP BY INTENT_ID)H2 ON H1.INTENT_ID=H2.INTENT_ID AND H1.CREATE_DATE=H2.CREATE_DATE"
                        //+ ")A2 ON A1.INTENT_ID=A2.INTENT_ID"
                        + "   )U2  " +
                        "  UNION "
                        + "SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE" 
                                //",INTENT_MODEL,INTENT_COLOR,"
                        + ",LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31 " +

                        "      \n" +//---000
                        ",INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
                        "\n"+//---11111
        
                        "   FROM ("
                        + "SELECT F.*,0 AS LAST_NEXT_GRADE FROM ("
                        + "   SELECT D.*" +
                        //      ",E.INTENT_MODEL,INTENT_COLOR " +
                        "  FROM ("
                        + "SELECT B.* FROM (SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE,INTENT_ID," +

                        "    \n" +//---000
                        "INIT_LEVEL,DEALER_CODE,\n" + 
                        " \n"//---11111
                        
                        +" SOLD_BY,REMARK,"
                        + " sum(case when (DAY(A.FOUND_DATE)=1) then INIT_LEVEL end)as C1,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=2) then INIT_LEVEL end)as C2,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=3) then INIT_LEVEL end)as C3,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=4) then INIT_LEVEL end)as C4,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=5) then INIT_LEVEL end)as C5,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=6) then INIT_LEVEL end)as C6,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=7) then INIT_LEVEL end)as C7,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=8) then INIT_LEVEL end)as C8,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=9) then INIT_LEVEL end)as C9,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=10) then INIT_LEVEL end)as C10,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=11) then INIT_LEVEL end)as C11,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=12) then INIT_LEVEL end)as C12,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=13) then INIT_LEVEL end)as C13,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=14) then INIT_LEVEL end)as C14,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=15) then INIT_LEVEL end)as C15,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=16) then INIT_LEVEL end)as C16,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=17) then INIT_LEVEL end)as C17,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=18) then INIT_LEVEL end)as C18,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=19) then INIT_LEVEL end)as C19,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=20) then INIT_LEVEL end)as C20,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=21) then INIT_LEVEL end)as C21,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=22) then INIT_LEVEL end)as C22,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=23) then INIT_LEVEL end)as C23,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=24) then INIT_LEVEL end)as C24,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=25) then INIT_LEVEL end)as C25,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=26) then INIT_LEVEL end)as C26,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=27) then INIT_LEVEL end)as C27,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=28) then INIT_LEVEL end)as C28,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=29) then INIT_LEVEL end)as C29,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=30) then INIT_LEVEL end)as C30,"
                        + "sum(case when  (DAY(A.FOUND_DATE)=31) then INIT_LEVEL end)as C31 "
                        + "FROM TM_POTENTIAL_CUSTOMER A WHERE a.DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)+" GROUP BY CUSTOMER_NO,CUSTOMER_NAME,"
                        + " CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE,INTENT_ID," +

                        "     \n" +// ---000
                        "INIT_LEVEL,DEALER_CODE,\n" + 
                        " \n"+//---11111
        
                        "SOLD_BY,REMARK )B "
                        + " INNER JOIN(SELECT CUSTOMER_NO FROM TM_POTENTIAL_CUSTOMER a "
                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)
                        + " and not exists("
                        + " SELECT CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN b "
                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "ACTION_DATE", begindate, enddate)
                        + " AND (NEXT_GRADE IS NOT NULL) AND (NEXT_GRADE<>0) and a.CUSTOMER_NO=b.CUSTOMER_NO)"
                        + " )C ON B.CUSTOMER_NO=C.CUSTOMER_NO"
                        + " )D " +
                        /*
                        "LEFT JOIN (SELECT H1.INTENT_ID,H1.INTENT_MODEL,H1.INTENT_COLOR,"
                        + " H1.CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL H1 INNER JOIN( "
                        + " SELECT INTENT_ID,MAX(CREATE_DATE)AS CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL "
                        + " GROUP BY INTENT_ID)H2 ON H1.INTENT_ID=H2.INTENT_ID AND H1.CREATE_DATE=H2.CREATE_DATE"
                        + " )E ON D.INTENT_ID=E.INTENT_ID " +
                        */
                        "  )F " + ")U1 " + ")U3 WHERE 1=1 and DEALER_CODE='"+entityCode+"'  ");

        if (!StringUtils.isNullOrEmpty(soldby))
        {
            sql.append(" AND U3.SOLD_BY=" + soldby + "");
        }
        sql.append(

                ") m left join (\n" +
                "      select a.INTENT_ID,max(a.ITEM_ID)  item_id from TT_CUSTOMER_INTENT_DETAIL a\n" + 
                "  where a.DEALER_CODE='"+entityCode+"' group by a.INTENT_ID\n" + 
                "\n" + 
                "   )   t on t.INTENT_ID=m.INTENT_ID "
        
        );
        
        List<Object> params = new ArrayList<>();
        final PageInfoDto dto = DAOUtil.pageQuery(sql.toString(), params);
        StringBuffer sql1 = new StringBuffer();
        sql1
                .append("SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.INTENT_LEVEL,"+"DAY(B.CREATED_AT)"+" AS D "
                        + " FROM TM_POTENTIAL_CUSTOMER A,( "
                        + " SELECT DEALER_CODE,CUSTOMER_NO,MAX(CREATED_AT) AS CREATED_AT FROM TT_SALES_ORDER"
                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" GROUP BY CUSTOMER_NO )B WHERE A.CUSTOMER_NO=B.CUSTOMER_NO AND A.INTENT_LEVEL=13101008");

        List<Object> params1 = new ArrayList<>();
   
       DAOUtil.findAll(sql1.toString(), params1,new DefinedRowProcessor(){             
                @SuppressWarnings("unchecked")
                @Override
                protected void process(Map<String, Object> row) {
                    for(int i=0;i<dto.getRows().size()&&!StringUtils.isNullOrEmpty(row);i++){
                        if(StringUtils.isEquals(row.get("CUSTOMER_NO"), dto.getRows().get(i).get("CUSTOMER_NO").toString())){
                            dto.getRows().get(i).put("C"+row.get("D"),  row.get("INTENT_LEVEL"));
                        }
                    }
                }
            });
       StringBuffer sql2 = new StringBuffer();
       sql2
               .append("SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.INTENT_LEVEL,A.CREATED_AT,"+"DAY(A.CREATED_AT)"+" AS D FROM ("
                       + "SELECT DEALER_CODE,CUSTOMER_NO,INTENT_LEVEL,"+"date(CREATED_AT)"+" AS CREATED_AT "
                       + "FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" AND INTENT_LEVEL=13101009"
                       + ")A LEFT JOIN ("
                       + "SELECT DEALER_CODE, CUSTOMER_NO,"+"date(MAX(CREATED_AT))"+" AS CREATED_AT "
                       + "FROM TT_PO_CUS_RELATION WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" GROUP BY CUSTOMER_NO,DEALER_CODE"
                       + ")B ON A.CUSTOMER_NO=B.CUSTOMER_NO AND A.DEALER_CODE=B.DEALER_CODE");
       List<Object> params2 = new ArrayList<>();
       DAOUtil.findAll(sql2.toString(), params2,new DefinedRowProcessor(){             
           @SuppressWarnings("unchecked")
           @Override
           protected void process(Map<String, Object> row) {
               for(int i=0;i<dto.getRows().size()&&!StringUtils.isNullOrEmpty(row);i++){
                   if(StringUtils.isEquals(row.get("CUSTOMER_NO"), dto.getRows().get(i).get("CUSTOMER_NO").toString())){
                       dto.getRows().get(i).put("C"+row.get("D"),  row.get("INTENT_LEVEL"));
                   }
               }
           }
       });
        return dto;
    }

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> printHopeCustomer(String startDate, String soldbys) throws ServiceBizException {
		 StringBuffer sql = new StringBuffer();
	        String entityCode=FrameworkUtil.getLoginInfo().getDealerCode();
	        System.err.println(startDate);
	        System.err.println(soldbys);
	        String begindate1=startDate;
	        String begindate=begindate1+"-01 00:00:00";//本月开始
	        String enddate = DateUtil.formatDefaultDate(DateUtil.getLastDayOfMonth(DateUtil.parseDefaultDate(begindate)))+" 23:59:59";//本月结束
	        //String   soldby = param.get("soldBy");// Long.parseLong();//销售顾问
	       
	        sql.append(
	                

	                "\n" +
	                "select  m.*,\n" + 
	                "t.item_id\n" + 
	                ",(select DISTINCT tn.MODEL_NAME  from    TT_CUSTOMER_INTENT_DETAIL a LEFT JOIN TM_MODEL tn ON a.INTENT_MODEL=tn.MODEL_CODE AND a.DEALER_CODE=tn.DEALER_CODE\n" + 
	                "where  ITEM_ID =t.item_id and a.DEALER_CODE='"+entityCode+"' )  INTENT_MODEL\n" + 
	                "\n" + 
	                ",(select DISTINCT tc.COLOR_NAME  from    TT_CUSTOMER_INTENT_DETAIL a LEFT JOIN TM_COLOR tc ON a.INTENT_COLOR=tc.COLOR_CODE AND a.DEALER_CODE=tc.DEALER_CODE\n" + 
	                "where  ITEM_ID =t.item_id and a.DEALER_CODE='"+entityCode+"' )  INTENT_COLOR\n" + 
	                "  from  ( "
	        
	        
	        );
	        sql
	                .append("SELECT CUSTOMER_NO,CUSTOMER_NAME,(case when CONTACTOR_PHONE is null or CONTACTOR_PHONE=''  then CONTACTOR_MOBILE else CONTACTOR_PHONE end ) as CONTACTOR_PHONE,CUS_SOURCE," 
	                        //"INTENT_MODEL,INTENT_COLOR,"
	                        + "LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
	                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31 " +

	                        "      \n" +//---00
	                        "      ,INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
	/*                      ",(select a.INTENT_MODEL  from    TT_CUSTOMER_INTENT_DETAIL a\n" + 
	                        "where  ITEM_ID =( select max(b.item_id)  from TT_CUSTOMER_INTENT_DETAIL b where  b.INTENT_ID=INTENT_ID and b.entity_code=ENTITY_CODE\n" + 
	                        "))  INTENT_MODEL\n" + 
	                        "\n" + 
	                        ",(select a.INTENT_COLOR  from    TT_CUSTOMER_INTENT_DETAIL a\n" + 
	                        "where  ITEM_ID =( select max(b.item_id)  from TT_CUSTOMER_INTENT_DETAIL b where  b.INTENT_ID=INTENT_ID and b.entity_code=ENTITY_CODE\n" + 
	                        "))  INTENT_COLOR\n" + */
	                        "     \n"+// ---11111111111

	                        "  FROM ("
	                        + "SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE" 
	                            //  ",INTENT_MODEL,INTENT_COLOR,"
	                        + ",LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
	                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31" +

	                        "\n" +//---00
	                        ",INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
	                        "\n"+
	                        //---11111111111
	        
	                        " FROM ("
	                        + "SELECT A1.*" +
	                                //",A2.INTENT_MODEL,A2.INTENT_COLOR,A2.INTENT_ID " +
	                        "  FROM("
	                        + "SELECT F1.*,F2.INTENT_ID" +

	                        "\n" +//--0000000000000
	                        ",f2.INIT_LEVEL,f2.DEALER_CODE\n" + 
	                        "\n"+//--1111111111111111
	        
	                        " FROM("
	                        + "SELECT G.*,H.CUS_SOURCE,H.CONTACTOR_PHONE,H.CONTACTOR_MOBILE,H.LAST_NEXT_GRADE,H.REMARK FROM ("
	                        + "SELECT E.CUSTOMER_NAME,E.CUSTOMER_NO,E.SOLD_BY,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=1) then next_grade end)as C1,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=2) then next_grade end)as C2,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=3) then next_grade end)as C3,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=4) then next_grade end)as C4,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=5) then next_grade end)as C5,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=6) then next_grade end)as C6,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=7) then next_grade end)as C7,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=8) then next_grade end)as C8,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=9) then next_grade end)as C9,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=10) then next_grade end)as C10,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=11) then next_grade end)as C11,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=12) then next_grade end)as C12,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=13) then next_grade end)as C13,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=14) then next_grade end)as C14,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=15) then next_grade end)as C15,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=16) then next_grade end)as C16,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=17) then next_grade end)as C17,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=18) then next_grade end)as C18,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=19) then next_grade end)as C19,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=20) then next_grade end)as C20,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=21) then next_grade end)as C21,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=22) then next_grade end)as C22,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=23) then next_grade end)as C23,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=24) then next_grade end)as C24,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=25) then next_grade end)as C25,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=26) then next_grade end)as C26,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=27) then next_grade end)as C27,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=28) then next_grade end)as C28,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=29) then next_grade end)as C29,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=30) then next_grade end)as C30,"
	                        + "sum(case when (DAY(E.ACTION_DATE)=31) then next_grade end)as C31 "
	                        + " FROM(SELECT A.CUSTOMER_NO,A.CUSTOMER_NAME,DATE_FORMAT(ACTION_DATE,'%Y-%m-%d') AS "
	                        + " ACTION_DATE,NEXT_GRADE,a.CREATED_AT,A.SOLD_BY FROM TT_SALES_PROMOTION_PLAN A"
	                        + " INNER JOIN (" +
	                        //"SELECT CUSTOMER_NO,CUSTOMER_NAME,"+CommonSqlMethod.formatDate("", "SCHEDULE_DATE")+"AS "
	                        //+ " SCHEDULE_DATE,MAX(CREATE_DATE) AS CREATE_DATE FROM TT_SALES_PROMOTION_PLAN "
	                        //+ " WHERE 1=1 "+Utility.getDateCond("", "SCHEDULE_DATE", begindate, enddate)
	                        //+ " AND "
	                        //+ " PROM_RESULT IS NOT NULL AND PROM_RESULT<>0 "
	                        //+ " GROUP BY CUSTOMER_NO,CUSTOMER_NAME,SCHEDULE_DATE "
	                        //+ " )B ON (A.CUSTOMER_NO = B.CUSTOMER_NO " +
	                        //"AND "+CommonSqlMethod.formatDate("A", "SCHEDULE_DATE")+"="+CommonSqlMethod.formatDate("B", "SCHEDULE_DATE")+" AND "
	                        //+ " A.CREATE_DATE = B.CREATE_DATE)" +

	                        "select customer_no, max(item_id) item_id from TT_SALES_PROMOTION_PLAN\n" +
	                        "where PROM_RESULT is not null and PROM_RESULT<> 0 and DEALER_CODE='"+entityCode+"' \n" + 
	                        Utility.getDateCond(null, "ACTION_DATE", begindate, enddate)+" "+
	                        "group by customer_no\n" + 
	                        "     )B\n" + 
	                        "      ON A.CUSTOMER_NO = B.CUSTOMER_NO and a.item_id=b.item_id \n" + 
	                        "   where a.DEALER_CODE='"+entityCode+"' \n" + 
	                        //"            a.SCHEDULE_DATE >='2010-11-01 00:00:00'\n" + 
	                        //"     AND  a.SCHEDULE_DATE <= '2010-11-30 23:59:59'"+
	                        Utility.getDateCond("a", "ACTION_DATE", begindate, enddate)+" "+
	                                                                                            
	                        ")E GROUP BY E.CUSTOMER_NAME,E.CUSTOMER_NO,E.SOLD_BY "
	                        + " )G,(SELECT C.*  " +
	                        

	                        "\n" +//--0000aa
	                        "\n" + 
	                        ",(\n" + 
	                        "select  a.NEXT_GRADE  from  TT_SALES_PROMOTION_PLAN A\n" + 
	                        "where a.DEALER_CODE='"+entityCode+"' and a.item_id = (\n" + 
	                        "select max(b.item_id)\n" + 
	                        "    FROM TT_SALES_PROMOTION_PLAN b\n" + 
	                        "                    WHERE (b.PROM_RESULT IS NOT NULL)\n" + 
	                        "                      AND (b.PROM_RESULT <> 0)\n" + 
	                        "                      AND 1 = 1\n" 
	                        //"                      AND b.SCHEDULE_DATE >= '2010-11-01 00:00:00'\n" + 
	                        //"                      AND b.SCHEDULE_DATE <= '2010-11-30 23:59:59'\n" +
	                        +Utility.getDateCond("b", "ACTION_DATE", begindate, enddate)+" "+
	                        "          and b.customer_no=C.customer_no and b.DEALER_CODE='"+entityCode+"'\n" + 
	                        ")\n" + 
	                        ") LAST_NEXT_GRADE\n" + 
	                        "\n "+
	                        //---111111111111111111aaaa

	                        
	                        " FROM ( "
	                        + " SELECT B.*,A.CUSTOMER_NAME,A.CUS_SOURCE,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.FOUND_DATE,A.REMARK FROM TM_POTENTIAL_CUSTOMER A,"
	                        + " (select customer_no from TM_POTENTIAL_CUSTOMER "
	                        + " where DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)
	                        + " UNION SELECT CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN "
	                        + " WHERE ( PROM_RESULT IS NOT NULL) AND ACTION_DATE IS NOT NULL "
	                        + " AND( PROM_RESULT<>0) AND(PRIOR_GRADE IS NOT NULL) AND (PRIOR_GRADE<>0)"
	                        + " AND (NEXT_GRADE IS NOT NULL) AND( NEXT_GRADE<>0) "
	                        + " AND DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "ACTION_DATE", begindate, enddate)+" GROUP BY CUSTOMER_NO "
	                        + " )B WHERE A.CUSTOMER_NO=B.CUSTOMER_NO and a.DEALER_CODE='"+entityCode+"' ORDER BY A.CUSTOMER_NO"
	                        + ")C " +
	                        /*
	                        "LEFT JOIN(SELECT B.*,A.NEXT_GRADE AS LAST_NEXT_GRADE "
	                        + " FROM TT_SALES_PROMOTION_PLAN A,(SELECT customer_no, MAX(CREATE_DATE) AS CREATE_DATE "
	                        + " FROM TT_SALES_PROMOTION_PLAN WHERE (PROM_RESULT IS NOT NULL) AND (PROM_RESULT<>0) "
	                        + " AND 1=1 "+Utility.getDateCond("", "SCHEDULE_DATE", begindate, enddate)+" GROUP BY "
	                        + " CUSTOMER_NO )B WHERE A.CREATE_DATE=B.CREATE_DATE AND A.customer_no=B.customer_no"
	                        + ")D ON C.CUSTOMER_NO=D.CUSTOMER_NO " +
	                        */
	                        ")H WHERE G.CUSTOMER_NO=H.CUSTOMER_NO"
	                        + ")F1,TM_POTENTIAL_CUSTOMER F2 WHERE F1.CUSTOMER_NO=F2.CUSTOMER_NO and F2.DEALER_CODE='"+entityCode+"'"
	                        + ")A1 " 
	                        //"LEFT JOIN(SELECT H1.INTENT_ID,H1.INTENT_MODEL,H1.INTENT_COLOR,H1.CREATE_DATE "
	                        //+ "FROM TT_CUSTOMER_INTENT_DETAIL H1 INNER JOIN("
	                        //+ "SELECT INTENT_ID,MAX(CREATE_DATE)AS CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL "
	                        //+ "GROUP BY INTENT_ID)H2 ON H1.INTENT_ID=H2.INTENT_ID AND H1.CREATE_DATE=H2.CREATE_DATE"
	                        //+ ")A2 ON A1.INTENT_ID=A2.INTENT_ID"
	                        + "   )U2  " +
	                        "  UNION "
	                        + "SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE" 
	                                //",INTENT_MODEL,INTENT_COLOR,"
	                        + ",LAST_NEXT_GRADE,SOLD_BY,REMARK,C1,C2,C3,C4,C5,C6,C7,C8,C9,C10,C11,C12,C13,C14,C15,C16,C17,"
	                        + "C18,C19,C20,C21,C22,C23,C24,C25,C26,C27,C28,C29,C30,C31 " +

	                        "      \n" +//---000
	                        ",INIT_LEVEL,DEALER_CODE,INTENT_ID\n" + 
	                        "\n"+//---11111
	        
	                        "   FROM ("
	                        + "SELECT F.*,0 AS LAST_NEXT_GRADE FROM ("
	                        + "   SELECT D.*" +
	                        //      ",E.INTENT_MODEL,INTENT_COLOR " +
	                        "  FROM ("
	                        + "SELECT B.* FROM (SELECT CUSTOMER_NO,CUSTOMER_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE,INTENT_ID," +

	                        "    \n" +//---000
	                        "INIT_LEVEL,DEALER_CODE,\n" + 
	                        " \n"//---11111
	                        
	                        +" SOLD_BY,REMARK,"
	                        + " sum(case when (DAY(A.FOUND_DATE)=1) then INIT_LEVEL end)as C1,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=2) then INIT_LEVEL end)as C2,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=3) then INIT_LEVEL end)as C3,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=4) then INIT_LEVEL end)as C4,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=5) then INIT_LEVEL end)as C5,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=6) then INIT_LEVEL end)as C6,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=7) then INIT_LEVEL end)as C7,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=8) then INIT_LEVEL end)as C8,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=9) then INIT_LEVEL end)as C9,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=10) then INIT_LEVEL end)as C10,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=11) then INIT_LEVEL end)as C11,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=12) then INIT_LEVEL end)as C12,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=13) then INIT_LEVEL end)as C13,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=14) then INIT_LEVEL end)as C14,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=15) then INIT_LEVEL end)as C15,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=16) then INIT_LEVEL end)as C16,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=17) then INIT_LEVEL end)as C17,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=18) then INIT_LEVEL end)as C18,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=19) then INIT_LEVEL end)as C19,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=20) then INIT_LEVEL end)as C20,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=21) then INIT_LEVEL end)as C21,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=22) then INIT_LEVEL end)as C22,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=23) then INIT_LEVEL end)as C23,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=24) then INIT_LEVEL end)as C24,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=25) then INIT_LEVEL end)as C25,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=26) then INIT_LEVEL end)as C26,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=27) then INIT_LEVEL end)as C27,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=28) then INIT_LEVEL end)as C28,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=29) then INIT_LEVEL end)as C29,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=30) then INIT_LEVEL end)as C30,"
	                        + "sum(case when  (DAY(A.FOUND_DATE)=31) then INIT_LEVEL end)as C31 "
	                        + "FROM TM_POTENTIAL_CUSTOMER A WHERE a.DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)+" GROUP BY CUSTOMER_NO,CUSTOMER_NAME,"
	                        + " CONTACTOR_PHONE,CONTACTOR_MOBILE,CUS_SOURCE,INTENT_ID," +

	                        "     \n" +// ---000
	                        "INIT_LEVEL,DEALER_CODE,\n" + 
	                        " \n"+//---11111
	        
	                        "SOLD_BY,REMARK )B "
	                        + " INNER JOIN(SELECT CUSTOMER_NO FROM TM_POTENTIAL_CUSTOMER a "
	                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "FOUND_DATE", begindate, enddate)
	                        + " and not exists("
	                        + " SELECT CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN b "
	                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "ACTION_DATE", begindate, enddate)
	                        + " AND (NEXT_GRADE IS NOT NULL) AND (NEXT_GRADE<>0) and a.CUSTOMER_NO=b.CUSTOMER_NO)"
	                        + " )C ON B.CUSTOMER_NO=C.CUSTOMER_NO"
	                        + " )D " +
	                        /*
	                        "LEFT JOIN (SELECT H1.INTENT_ID,H1.INTENT_MODEL,H1.INTENT_COLOR,"
	                        + " H1.CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL H1 INNER JOIN( "
	                        + " SELECT INTENT_ID,MAX(CREATE_DATE)AS CREATE_DATE FROM TT_CUSTOMER_INTENT_DETAIL "
	                        + " GROUP BY INTENT_ID)H2 ON H1.INTENT_ID=H2.INTENT_ID AND H1.CREATE_DATE=H2.CREATE_DATE"
	                        + " )E ON D.INTENT_ID=E.INTENT_ID " +
	                        */
	                        "  )F " + ")U1 " + ")U3 WHERE 1=1 and DEALER_CODE='"+entityCode+"'  ");

	        if (!StringUtils.isNullOrEmpty(soldbys))
	        {
	            sql.append(" AND U3.SOLD_BY=" + soldbys + "");
	        }
	        sql.append(

	                ") m left join (\n" +
	                "      select a.INTENT_ID,max(a.ITEM_ID)  item_id from TT_CUSTOMER_INTENT_DETAIL a\n" + 
	                "  where a.DEALER_CODE='"+entityCode+"' group by a.INTENT_ID\n" + 
	                "\n" + 
	                "   )   t on t.INTENT_ID=m.INTENT_ID "
	        
	        );
	        
	        List<Object> params = new ArrayList<>();
	        final List<Map> dto = DAOUtil.findAll(sql.toString(), params);
	        StringBuffer sql1 = new StringBuffer();
	        sql1
	                .append("SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.INTENT_LEVEL,"+"DAY(B.CREATED_AT)"+" AS D "
	                        + " FROM TM_POTENTIAL_CUSTOMER A,( "
	                        + " SELECT DEALER_CODE,CUSTOMER_NO,MAX(CREATED_AT) AS CREATED_AT FROM TT_SALES_ORDER"
	                        + " WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" GROUP BY CUSTOMER_NO )B WHERE A.CUSTOMER_NO=B.CUSTOMER_NO AND A.INTENT_LEVEL=13101008");

	        List<Object> params1 = new ArrayList<>();
	   
	       DAOUtil.findAll(sql1.toString(), params1,new DefinedRowProcessor(){             
	                @SuppressWarnings("unchecked")
	                @Override
	                protected void process(Map<String, Object> row) {
	                    for(int i=0;i<dto.size()&&!StringUtils.isNullOrEmpty(row);i++){
	                        if(StringUtils.isEquals(row.get("CUSTOMER_NO"), dto.get(i).get("CUSTOMER_NO").toString())){
	                            dto.get(i).put("C"+row.get("D"), row.get("INTENT_LEVEL"));
	                        }
	                    }
	                }
	            });
	       StringBuffer sql2 = new StringBuffer();
	       sql2
	               .append("SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.INTENT_LEVEL,A.CREATED_AT,"+"DAY(A.CREATED_AT)"+" AS D FROM ("
	                       + "SELECT DEALER_CODE,CUSTOMER_NO,INTENT_LEVEL,"+"date(CREATED_AT)"+" AS CREATED_AT "
	                       + "FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" AND INTENT_LEVEL=13101009"
	                       + ")A LEFT JOIN ("
	                       + "SELECT DEALER_CODE, CUSTOMER_NO,"+"date(MAX(CREATED_AT))"+" AS CREATED_AT "
	                       + "FROM TT_PO_CUS_RELATION WHERE DEALER_CODE='"+entityCode+"' "+Utility.getDateCond("", "CREATED_AT", begindate, enddate)+" GROUP BY CUSTOMER_NO,DEALER_CODE"
	                       + ")B ON A.CUSTOMER_NO=B.CUSTOMER_NO AND A.DEALER_CODE=B.DEALER_CODE");
	       List<Object> params2 = new ArrayList<>();
	       DAOUtil.findAll(sql2.toString(), params2,new DefinedRowProcessor(){             
	           @SuppressWarnings("unchecked")
	           @Override
	           protected void process(Map<String, Object> row) {
	               for(int i=0;i<dto.size()&&!StringUtils.isNullOrEmpty(row);i++){
	                   if(StringUtils.isEquals(row.get("CUSTOMER_NO"), dto.get(i).get("CUSTOMER_NO").toString())){
	                       dto.get(i).put("C"+row.get("D"), row.get("INTENT_LEVEL"));
	                   }
	               }
	           }
	       });
	        return dto;
	}

	
}
