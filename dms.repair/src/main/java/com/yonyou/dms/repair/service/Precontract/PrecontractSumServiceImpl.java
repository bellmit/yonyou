
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : PrecontractSumServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年4月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月6日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.Precontract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author Administrator
* @date 2017年4月6日
*/
@Service
public class PrecontractSumServiceImpl implements PrecontractSumService {
    /**
     * 预约汇总表查询
    * @author yujiangheng
    * @date 2017年5月16日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.Precontract.PrecontractSumService#QueryPrecontractSum(java.util.Map)
     */
    @Override
    public List<Map> QueryPrecontractSum(Map<String, String> queryParam) throws ServiceBizException {
  
            String dealerCode =  queryParam.get("DEALER_CODE");
            String starttime =queryParam.get("starttime");
            String endtime =queryParam.get("endtime");
            
            String radio =queryParam.get("radiobutton");
            String employeeNO =queryParam.get("Service");
            System.out.println("------------------"+radio);
            StringBuffer sb = new StringBuffer();
            StringBuffer sql = new StringBuffer();
            String employeeOr = "SERVICE_ADVISOR";
            if (radio.equals("Technician")) {
                employeeOr = "CHIEF_TECHNICIAN";
                employeeNO=queryParam.get("Technician");
            }
            
              sb.append("SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,(CASE WHEN THECOUNT2 IS NULL THEN 0 ELSE THECOUNT2 END) AS REPAIR_ORDER_COUNT, ");   
              sb.append("  0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T2.DEALER_CODE ");   
              sb.append("FROM ( "  ); 
              sb.append("    SELECT COUNT(DISTINCT VIN) AS THECOUNT2, " + employeeOr + ",T.DEALER_CODE "); 
              sb.append("        FROM TT_REPAIR_ORDER T WHERE " + employeeOr + " IS NOT NULL "  ); 
              Utility.sqlToDate(sb, starttime ,endtime, "RO_CREATE_DATE", null);
              sb.append("        GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
              sb.append("    ) T2 " ); 
              sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T2." + employeeOr + " AND G.DEALER_CODE=T2.DEALER_CODE "); 
                // 准时进厂
              sb.append(" UNION ALL "); 
              sb.append(" SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,(CASE WHEN THECOUNT3 IS NULL THEN 0 ELSE THECOUNT3 END) AS IN_ON_TIME,0 AS IN_BEFORE_TIME, "); 
              sb.append("0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T3.DEALER_CODE "); 
              sb.append("FROM ( "  ); 
              sb.append(  "   SELECT COUNT(DISTINCT A.VIN) AS THECOUNT3,A." + employeeOr + ",A.DEALER_CODE " ); 
              sb.append(   "   FROM TT_BOOKING_ORDER T "  ); 
              sb.append(  "     LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE " ); 
              sb.append( "   WHERE BOOKING_ORDER_STATUS=12541005 " ); 
              Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
              sb.append(   "    GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
              sb.append(  "  ) T3 " ); 
              sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T3." + employeeOr + " AND G.DEALER_CODE=T3.DEALER_CODE "); 
                // 提前进厂
              sb.append(" UNION ALL " ); 
              sb.append(" SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,(CASE WHEN THECOUNT5 IS NULL THEN 0 ELSE THECOUNT5 END) AS IN_BEFORE_TIME, "); 
              sb.append(" 0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T5.DEALER_CODE "); 
              sb.append(" FROM ( ");  
              sb.append("        SELECT COUNT(DISTINCT A.VIN) AS THECOUNT5,A." + employeeOr + ",A.DEALER_CODE "); 
              sb.append("         FROM TT_BOOKING_ORDER T "); 
              sb.append("        LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE "); 
              sb.append("         WHERE BOOKING_ORDER_STATUS=12541004 " ); 
              Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );   
              sb.append("        GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
              sb.append("     ) T5 "); 
              sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T5." + employeeOr + " AND G.DEALER_CODE=T5.DEALER_CODE "); 
                // 延迟进厂
              sb.append( "UNION ALL "); 
              sb.append("SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME, "); 
              sb.append("(CASE WHEN THECOUNT6 IS NULL THEN 0 ELSE THECOUNT6 END) AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T6.DEALER_CODE "); 
              sb.append("FROM ( "); 
              sb.append( "         SELECT COUNT(DISTINCT A.VIN) AS THECOUNT6,A." + employeeOr + ",A.DEALER_CODE "); 
              sb.append( "         FROM TT_BOOKING_ORDER T "); 
              sb.append( "         LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE "); 
              sb.append( "         WHERE BOOKING_ORDER_STATUS=12541003 " ); 
              Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
              sb.append( "         GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
              sb.append( "     ) T6 "); 
              sb.append( "LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T6." + employeeOr + " AND G.DEALER_CODE=T6.DEALER_CODE "); 
                // 取消进厂 
              sb.append( "UNION ALL "); 
              sb.append( "SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME, "); 
              sb.append( "(CASE WHEN THECOUNT7 IS NULL THEN 0 ELSE THECOUNT7 END) AS IN_CANCEL,0 AS IN_NOT,T7.DEALER_CODE "); 
              sb.append("FROM ( "); 
              sb.append( "         SELECT COUNT(DISTINCT T.VIN) THECOUNT7," + employeeOr +",T.DEALER_CODE "); 
              sb.append( "         FROM TT_BOOKING_ORDER T "); 
              sb.append( "         WHERE T.BOOKING_ORDER_STATUS=12541002 " );
              Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
              sb.append(         " AND VIN IS NOT NULL "); 
              sb.append( "         GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
              sb.append( "     ) T7 "); 
              sb.append("LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T7." + employeeOr + " AND G.DEALER_CODE=T7.DEALER_CODE "); 
                // 未进厂
               sb.append( "UNION ALL "); 
               sb.append( "SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME,0 AS IN_CANCEL, "); 
               sb.append( "(CASE WHEN THECOUNT8 IS NULL THEN 0 ELSE THECOUNT8 END) AS IN_NOT,T8.DEALER_CODE "); 
               sb.append( "FROM ( "); 
               sb.append(  "         SELECT COUNT(DISTINCT T.VIN) THECOUNT8," + employeeOr + ",T.DEALER_CODE "); 
               sb.append(  "         FROM TT_BOOKING_ORDER T "); 
               sb.append(  "         WHERE T.BOOKING_ORDER_STATUS=12541001 " );
               Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
               sb.append(   " AND VIN IS NOT NULL "); 
               sb.append(  "         GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
               sb.append(  "     ) T8 "); 
               sb.append(  "LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T8." + employeeOr + " AND G.DEALER_CODE=T8.DEALER_CODE "); 
               
               sql.append(" SELECT VM.EMPLOYEE_NO,VM.EMPLOYEE_NAME,SUM(REPAIR_ORDER_COUNT) REPAIR_ORDER_COUNT, ");
               sql.append("CONCAT(CAST(COALESCE(ROUND((IN_ON_TIME+IN_BEFORE_TIME+IN_AFTER_TIME)/REPAIR_ORDER_COUNT,2),0)*100 AS CHAR),'%')  BOOKING_RATE,  ");
               sql.append(" CONCAT(CAST(COALESCE(ROUND(IN_ON_TIME/REPAIR_ORDER_COUNT,2),0)*100 AS CHAR),'%')  AVAILABILE_BOOKING_RATE,  ");
               sql.append(" CASE WHEN (SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT))=0 THEN '0.00%' ELSE CONCAT(CAST(COALESCE(ROUND(IN_ON_TIME/(SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT)),2),0)*100 AS CHAR),'%') END  SUCESS_RATE,");
               sql.append(" (SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT)) PRECON_COUNT, "); 
               sql.append( "SUM(IN_ON_TIME) IN_ON_TIME,SUM(IN_BEFORE_TIME) IN_BEFORE_TIME,SUM(IN_AFTER_TIME) IN_AFTER_TIME,SUM(IN_CANCEL) IN_CANCEL,SUM(IN_NOT) IN_NOT,VM.DEALER_CODE,A.DEALER_SHORTNAME "); 
               sql.append( "FROM ( "); 
               sql.append( sb); 
               sql.append( "     )  vm  LEFT JOIN TM_dealer_BASICINFO  A ON A.DEALER_CODE=vm.DEALER_CODE "); 
               sql.append( "WHERE 1=1 "); 
               Utility.sqlToEquals(sql, dealerCode, "vm.DEALER_CODE", null);
               Utility.sqlToEquals(sql, employeeNO, "EMPLOYEE_NO", null);
               sql.append( "GROUP BY vm.EMPLOYEE_NO,vm.EMPLOYEE_NAME,vm.DEALER_CODE "); 
            return DAOUtil.findAll(sql.toString(), null);
        }
    /**
     * 预约汇总导出数据查询
    * @author yujiangheng
    * @date 2017年5月16日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.Precontract.PrecontractSumService#queryToExport(java.util.Map)
     */
    @Override
    public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException {

        String dealerCode =  queryParam.get("DEALER_CODE");
        String starttime =queryParam.get("starttime");
        String endtime =queryParam.get("endtime");
        String radio =queryParam.get("radiobutton");
        String employeeNO =queryParam.get("Service");
        
        StringBuffer sb = new StringBuffer();
        StringBuffer sql = new StringBuffer();
        String employeeOr = "SERVICE_ADVISOR";
        if (radio.equals("Technician")) {
            employeeOr = "CHIEF_TECHNICIAN";
            employeeNO=queryParam.get("Technician");
        }
        
          sb.append("SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,(CASE WHEN THECOUNT2 IS NULL THEN 0 ELSE THECOUNT2 END) AS REPAIR_ORDER_COUNT, ");   
          sb.append("  0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T2.DEALER_CODE ");   
          sb.append("FROM ( "  ); 
          sb.append("    SELECT COUNT(DISTINCT VIN) AS THECOUNT2, " + employeeOr + ",T.DEALER_CODE "); 
          sb.append("        FROM TT_REPAIR_ORDER T WHERE " + employeeOr + " IS NOT NULL "  ); 
          Utility.sqlToDate(sb, starttime ,endtime, "RO_CREATE_DATE", null);
          sb.append("        GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
          sb.append("    ) T2 " ); 
          sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T2." + employeeOr + " AND G.DEALER_CODE=T2.DEALER_CODE "); 
            // 准时进厂
          sb.append(" UNION ALL "); 
          sb.append(" SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,(CASE WHEN THECOUNT3 IS NULL THEN 0 ELSE THECOUNT3 END) AS IN_ON_TIME,0 AS IN_BEFORE_TIME, "); 
          sb.append("0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T3.DEALER_CODE "); 
          sb.append("FROM ( "  ); 
          sb.append(  "   SELECT COUNT(DISTINCT A.VIN) AS THECOUNT3,A." + employeeOr + ",A.DEALER_CODE " ); 
          sb.append(   "   FROM TT_BOOKING_ORDER T "  ); 
          sb.append(  "     LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE " ); 
          sb.append( "   WHERE BOOKING_ORDER_STATUS=12541005 " ); 
          Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
          sb.append(   "    GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
          sb.append(  "  ) T3 " ); 
          sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T3." + employeeOr + " AND G.DEALER_CODE=T3.DEALER_CODE "); 
            // 提前进厂
          sb.append(" UNION ALL " ); 
          sb.append(" SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,(CASE WHEN THECOUNT5 IS NULL THEN 0 ELSE THECOUNT5 END) AS IN_BEFORE_TIME, "); 
          sb.append(" 0 AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T5.DEALER_CODE "); 
          sb.append(" FROM ( ");  
          sb.append("        SELECT COUNT(DISTINCT A.VIN) AS THECOUNT5,A." + employeeOr + ",A.DEALER_CODE "); 
          sb.append("         FROM TT_BOOKING_ORDER T "); 
          sb.append("        LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE "); 
          sb.append("         WHERE BOOKING_ORDER_STATUS=12541004 " ); 
          Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );   
          sb.append("        GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
          sb.append("     ) T5 "); 
          sb.append(" LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T5." + employeeOr + " AND G.DEALER_CODE=T5.DEALER_CODE "); 
            // 延迟进厂
          sb.append( "UNION ALL "); 
          sb.append("SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME, "); 
          sb.append("(CASE WHEN THECOUNT6 IS NULL THEN 0 ELSE THECOUNT6 END) AS IN_AFTER_TIME,0 AS IN_CANCEL,0 AS IN_NOT,T6.DEALER_CODE "); 
          sb.append("FROM ( "); 
          sb.append( "         SELECT COUNT(DISTINCT A.VIN) AS THECOUNT6,A." + employeeOr + ",A.DEALER_CODE "); 
          sb.append( "         FROM TT_BOOKING_ORDER T "); 
          sb.append( "         LEFT JOIN TT_REPAIR_ORDER A ON T.BOOKING_ORDER_NO=A.BOOKING_ORDER_NO AND T.DEALER_CODE=A.DEALER_CODE "); 
          sb.append( "         WHERE BOOKING_ORDER_STATUS=12541003 " ); 
          Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
          sb.append( "         GROUP BY A." + employeeOr + ",A.DEALER_CODE "); 
          sb.append( "     ) T6 "); 
          sb.append( "LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T6." + employeeOr + " AND G.DEALER_CODE=T6.DEALER_CODE "); 
            // 取消进厂 
          sb.append( "UNION ALL "); 
          sb.append( "SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME, "); 
          sb.append( "(CASE WHEN THECOUNT7 IS NULL THEN 0 ELSE THECOUNT7 END) AS IN_CANCEL,0 AS IN_NOT,T7.DEALER_CODE "); 
          sb.append("FROM ( "); 
          sb.append( "         SELECT COUNT(DISTINCT T.VIN) THECOUNT7," + employeeOr +",T.DEALER_CODE "); 
          sb.append( "         FROM TT_BOOKING_ORDER T "); 
          sb.append( "         WHERE T.BOOKING_ORDER_STATUS=12541002 " );
          Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
          sb.append(         " AND VIN IS NOT NULL "); 
          sb.append( "         GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
          sb.append( "     ) T7 "); 
          sb.append("LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T7." + employeeOr + " AND G.DEALER_CODE=T7.DEALER_CODE "); 
            // 未进厂
           sb.append( "UNION ALL "); 
           sb.append( "SELECT G.EMPLOYEE_NO,G.EMPLOYEE_NAME,0 AS REPAIR_ORDER_COUNT,0 AS IN_ON_TIME,0 AS IN_BEFORE_TIME,0 AS IN_AFTER_TIME,0 AS IN_CANCEL, "); 
           sb.append( "(CASE WHEN THECOUNT8 IS NULL THEN 0 ELSE THECOUNT8 END) AS IN_NOT,T8.DEALER_CODE "); 
           sb.append( "FROM ( "); 
           sb.append(  "         SELECT COUNT(DISTINCT T.VIN) THECOUNT8," + employeeOr + ",T.DEALER_CODE "); 
           sb.append(  "         FROM TT_BOOKING_ORDER T "); 
           sb.append(  "         WHERE T.BOOKING_ORDER_STATUS=12541001 " );
           Utility.sqlToDate(sb,starttime, endtime,"BOOKING_COME_TIME", null );
           sb.append(   " AND VIN IS NOT NULL "); 
           sb.append(  "         GROUP BY " + employeeOr + ",T.DEALER_CODE "); 
           sb.append(  "     ) T8 "); 
           sb.append(  "LEFT JOIN TM_EMPLOYEE G ON G.EMPLOYEE_NO=T8." + employeeOr + " AND G.DEALER_CODE=T8.DEALER_CODE "); 
           
           sql.append(" SELECT VM.EMPLOYEE_NO,VM.EMPLOYEE_NAME,SUM(REPAIR_ORDER_COUNT) REPAIR_ORDER_COUNT, ");
           sql.append("CONCAT(CAST(COALESCE(ROUND((IN_ON_TIME+IN_BEFORE_TIME+IN_AFTER_TIME)/REPAIR_ORDER_COUNT,2),0)*100 AS CHAR),'%')  BOOKING_RATE,  ");
           sql.append(" CONCAT(CAST(COALESCE(ROUND(IN_ON_TIME/REPAIR_ORDER_COUNT,2),0)*100 AS CHAR),'%')  AVAILABILE_BOOKING_RATE,  ");
           sql.append(" CASE WHEN (SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT))=0 THEN '0.00%' ELSE CONCAT(CAST(COALESCE(ROUND(IN_ON_TIME/(SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT)),2),0)*100 AS CHAR),'%') END  SUCESS_RATE,");
           sql.append(" (SUM(IN_ON_TIME)+SUM(IN_BEFORE_TIME)+SUM(IN_AFTER_TIME)+SUM(IN_CANCEL)+SUM(IN_NOT)) PRECON_COUNT, "); 
           sql.append( "SUM(IN_ON_TIME) IN_ON_TIME,SUM(IN_BEFORE_TIME) IN_BEFORE_TIME,SUM(IN_AFTER_TIME) IN_AFTER_TIME,SUM(IN_CANCEL) IN_CANCEL,SUM(IN_NOT) IN_NOT,VM.DEALER_CODE,A.DEALER_SHORTNAME "); 
           sql.append( "FROM ( "); 
           sql.append( sb); 
           sql.append( "     )  vm  LEFT JOIN TM_dealer_BASICINFO  A ON A.DEALER_CODE=vm.DEALER_CODE "); 
           sql.append( "WHERE 1=1 "); 
           Utility.sqlToEquals(sql, dealerCode, "vm.DEALER_CODE", null);
           Utility.sqlToEquals(sql, employeeNO, "EMPLOYEE_NO", null);
           sql.append( "GROUP BY vm.EMPLOYEE_NO,vm.EMPLOYEE_NAME,vm.DEALER_CODE "); 
        return DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 查询服务专员
    * @author yujiangheng
    * @date 2017年5月16日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.Precontract.PrecontractSumService#getAllServiceSelect()
     */
    @Override
    public List<Map> getAllServiceSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT A.dealer_code,A.employee_no,B.employee_name FROM TM_USER A LEFT JOIN tm_employee B");
        sqlSb.append( " ON A.dealer_code=B.dealer_code AND A.employee_no=B.employee_no  WHERE A.IS_SERVICE_ADVISOR="+CommonConstants.DICT_IS_YES );
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), null);
        return result;
    }
    /**
     * 查询指定技师
    * @author yujiangheng
    * @date 2017年5月16日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.Precontract.PrecontractSumService#getAllTechnicianSelect()
     */
    @Override
    public List<Map> getAllTechnicianSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT dealer_code,employee_no,employee_name FROM tm_employee t WHERE t.IS_TECHNICIAN="+CommonConstants.DICT_IS_YES);
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), null);
        return result;
    }

   
}
