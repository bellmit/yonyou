package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务描述：客流HSL趋势分析
 * @author Benzc
 * @date 2017年2月6日
 */
@Service
public class HslTrendAnalysisServiceImpl implements HslTrendAnalysisService{

	@Override
	public PageInfoDto queryHslTrendAnalysis(Map<String, String> queryParam) throws ServiceBizException {
		String dealercode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgcode = FrameworkUtil.getLoginInfo().getOrgCode();
		String functionCode= "606004";//客流HSL趋势分析
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT DEALER_CODE,HSL.SERIES_CODE,HSL.W8,HSL.W7,HSL.W6,HSL.W5,HSL.W4,HSL.W3,HSL.W2,HSL.W1,HSL.WN,HSL.TYPE,S.SERIES_NAME, ");// LIM 加个distict
		sb.append(" round((HSL.W1+HSL.W2+HSL.W3+HSL.W4+HSL.W5+HSL.W6+HSL.W7+HSL.W8)*1.00/8,2) as AVERAGE ");
		sb.append(" FROM ");
		sb.append(" (SELECT " );
		sb.append("    D.SERIES_CODE, " );
		sb.append("    '留档' AS TYPE, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W1' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W1, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W2' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W2, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W3' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W3, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W4' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W4, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W5' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W5, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W6' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W6, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W7' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W7, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W8' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W8, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'WN' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS WN " );
		sb.append("FROM " );
		sb.append(" TM_SERIES D LEFT JOIN ");
		sb.append("    ( " );
				
				//前八周
		sb.append("        SELECT " );
		sb.append("            'W8' AS DATETIME , " );
		sb.append("            C.INTENT_SERIES, " );
		sb.append("            COUNT(1) AS total_count " );
		sb.append("        FROM " );
		sb.append("            TM_POTENTIAL_CUSTOMER a " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
		sb.append("        ON " );
		sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
		sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
		sb.append("        ON " );
		sb.append("            B.INTENT_ID=C.INTENT_ID " );
		sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
		sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
		sb.append("        WHERE " );
		sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+56) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+48) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				
				//前七周
				sb.append("        SELECT " );
				sb.append("            'W7' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+49) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+41) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("     GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前六周
				sb.append("        SELECT " );
				sb.append("            'W6' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+42) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+34) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前五周
				sb.append("        SELECT " );
				sb.append("            'W5' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C ");
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+35) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+27) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前四周
				sb.append("        SELECT " );
				sb.append("            'W4' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+28) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+20) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前三周
				sb.append("        SELECT " );
				sb.append("            'W3' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count ");
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+21) DAY),' 00:00:00')) " );
				sb.append("        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+13) DAY),' 00:00:00')) ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
		sb.append("    GROUP BY " );
		sb.append("            INTENT_SERIES " );

		sb.append("        UNION ALL " );
		//前二周
		sb.append("        SELECT " );
		sb.append("            'W2' AS DATETIME , " );
		sb.append("            C.INTENT_SERIES, " );
		sb.append("            COUNT(1) AS total_count " );
		sb.append("        FROM " );
		sb.append("            TM_POTENTIAL_CUSTOMER a " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
		sb.append("        ON " );
		sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
		sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
		sb.append("        ON " );
		sb.append("            B.INTENT_ID=C.INTENT_ID " );
		sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
		sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
		sb.append("        WHERE " );
		sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("    AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+14) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+6) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前一周
				sb.append("        SELECT " );
				sb.append("            'W1' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("  AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+7) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1-1) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//当前周
				sb.append("        SELECT " );
				sb.append("            'WN' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("  GROUP BY INTENT_SERIES " );
				sb.append("    ) " );
				sb.append("    a " );
				sb.append(" ON D.SERIES_CODE = A.INTENT_SERIES ");
				sb.append("GROUP BY " );
				sb.append("  SERIES_CODE " );
				sb.append(" " );
				sb.append("UNION ALL " );
				sb.append(" " );
				sb.append("SELECT " );
				sb.append("     D.SERIES_CODE, " );
				sb.append("    'HSL' AS TYPE, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W1' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W1, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W2' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W2, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W3' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W3, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W4' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W4, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W5' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W5, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W6' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W6, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W7' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W7, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W8' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W8, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'WN' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS WN " );
				sb.append("FROM " );
				sb.append(" TM_SERIES D LEFT JOIN ");
				sb.append("    ( " );
				//前八周
				sb.append("        SELECT " );
				sb.append("            'W8' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("  AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+56) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+48) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前七周
				sb.append("        SELECT " );
				sb.append("            'W7' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+49) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+41) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前六周
				sb.append("        SELECT " );
				sb.append("            'W6' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+42) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+34) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前五周
				sb.append("        SELECT " );
				sb.append("            'W5' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+35) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+27) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前四周
				sb.append("        SELECT " );
				sb.append("            'W4' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+28) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+20) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前三周
				sb.append("        SELECT " );
				sb.append("            'W3' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+21) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+13) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("     GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前两周
				sb.append("        SELECT " );
				sb.append("            'W2' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+14) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+6) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前一周
				sb.append("        SELECT " );
				sb.append("            'W1' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("    AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+7) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1-1) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//当前周
				sb.append("        SELECT " );
				sb.append("            'WN' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );
				sb.append("    ) " );
				sb.append("    a " );
				sb.append(" ON D.SERIES_CODE = A.INTENT_SERIES ");
				sb.append(" GROUP BY " );
				sb.append("     SERIES_CODE)HSL ");
//				sql.append(" LEFT JOIN TM_SERIES S ON S.SERIES_CODE = HSL.SERIES_CODE  ");// 有重复数据 lim发现 没有entity过滤 临时这里改一下了
				sb.append("  JOIN TM_SERIES S ON S.SERIES_CODE = HSL.SERIES_CODE  and s.dealer_code='"+dealercode+"' ");
				sb.append(" WHERE 1=1  ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
            sb.append(" AND A.HSL.SERIES_CODE = ?");
            queryList.add(queryParam.get("series"));
        }
		sb.append(" ORDER BY HSL.SERIES_CODE DESC ");
		
		System.err.println(sb.toString());
		
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        return result;
	}
    
	/**
	 * 导出
	 * @author Benzc
	 * @date 2017年2月7日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryHslTrendAnalysisforExport(Map<String, String> queryParam) throws ServiceBizException {
		String dealercode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgcode = FrameworkUtil.getLoginInfo().getOrgCode();
		String functionCode= "606004";//客流HSL趋势分析
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT DEALER_CODE,HSL.SERIES_CODE,HSL.W8,HSL.W7,HSL.W6,HSL.W5,HSL.W4,HSL.W3,HSL.W2,HSL.W1,HSL.WN,HSL.TYPE,S.SERIES_NAME, ");// LIM 加个distict
		sb.append(" round((HSL.W1+HSL.W2+HSL.W3+HSL.W4+HSL.W5+HSL.W6+HSL.W7+HSL.W8)*1.00/8,2) as AVERAGE ");
		sb.append(" FROM ");
		sb.append(" (SELECT " );
		sb.append("    D.SERIES_CODE, " );
		sb.append("    '留档' AS TYPE, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W1' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W1, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W2' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W2, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W3' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W3, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W4' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W4, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W5' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W5, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W6' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W6, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W7' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W7, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'W8' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS W8, " );
		sb.append("    COALESCE(MAX( " );
		sb.append("        CASE " );
		sb.append("            WHEN a.datetime = 'WN' " );
		sb.append("            THEN a.total_count " );
		sb.append("        END),0) AS WN " );
		sb.append("FROM " );
		sb.append(" TM_SERIES D LEFT JOIN ");
		sb.append("    ( " );
				
				//前八周
		sb.append("        SELECT " );
		sb.append("            'W8' AS DATETIME , " );
		sb.append("            C.INTENT_SERIES, " );
		sb.append("            COUNT(1) AS total_count " );
		sb.append("        FROM " );
		sb.append("            TM_POTENTIAL_CUSTOMER a " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
		sb.append("        ON " );
		sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
		sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
		sb.append("        ON " );
		sb.append("            B.INTENT_ID=C.INTENT_ID " );
		sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
		sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
		sb.append("        WHERE " );
		sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+56) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+48) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				
				//前七周
				sb.append("        SELECT " );
				sb.append("            'W7' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+49) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+41) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("     GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前六周
				sb.append("        SELECT " );
				sb.append("            'W6' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+42) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+34) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前五周
				sb.append("        SELECT " );
				sb.append("            'W5' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C ");
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+35) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+27) DAY),' 00:00:00')) ");
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前四周
				sb.append("        SELECT " );
				sb.append("            'W4' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+28) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+20) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前三周
				sb.append("        SELECT " );
				sb.append("            'W3' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count ");
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+21) DAY),' 00:00:00')) " );
				sb.append("        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+13) DAY),' 00:00:00')) ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
		sb.append("    GROUP BY " );
		sb.append("            INTENT_SERIES " );

		sb.append("        UNION ALL " );
		//前二周
		sb.append("        SELECT " );
		sb.append("            'W2' AS DATETIME , " );
		sb.append("            C.INTENT_SERIES, " );
		sb.append("            COUNT(1) AS total_count " );
		sb.append("        FROM " );
		sb.append("            TM_POTENTIAL_CUSTOMER a " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
		sb.append("        ON " );
		sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
		sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
		sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
		sb.append("        ON " );
		sb.append("            B.INTENT_ID=C.INTENT_ID " );
		sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
		sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
		sb.append("        WHERE " );
		sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("    AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+14) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+6) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前一周
				sb.append("        SELECT " );
				sb.append("            'W1' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("  AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+7) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1-1) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//当前周
				sb.append("        SELECT " );
				sb.append("            'WN' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("  GROUP BY INTENT_SERIES " );
				sb.append("    ) " );
				sb.append("    a " );
				sb.append(" ON D.SERIES_CODE = A.INTENT_SERIES ");
				sb.append("GROUP BY " );
				sb.append("  SERIES_CODE " );
				sb.append(" " );
				sb.append("UNION ALL " );
				sb.append(" " );
				sb.append("SELECT " );
				sb.append("     D.SERIES_CODE, " );
				sb.append("    'HSL' AS TYPE, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W1' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W1, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W2' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W2, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W3' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W3, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W4' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W4, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W5' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W5, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W6' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W6, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W7' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W7, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'W8' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS W8, " );
				sb.append("    COALESCE(MAX( " );
				sb.append("        CASE " );
				sb.append("            WHEN a.datetime = 'WN' " );
				sb.append("            THEN a.total_count " );
				sb.append("        END),0) AS WN " );
				sb.append("FROM " );
				sb.append(" TM_SERIES D LEFT JOIN ");
				sb.append("    ( " );
				//前八周
				sb.append("        SELECT " );
				sb.append("            'W8' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("  AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+56) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+48) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前七周
				sb.append("        SELECT " );
				sb.append("            'W7' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+49) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+41) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前六周
				sb.append("        SELECT " );
				sb.append("            'W6' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+42) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+34) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前五周
				sb.append("        SELECT " );
				sb.append("            'W5' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) " );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+35) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+27) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前四周
				sb.append("        SELECT " );
				sb.append("            'W4' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+28) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+20) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前三周
				sb.append("        SELECT " );
				sb.append("            'W3' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+21) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+13) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("     GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前两周
				sb.append("        SELECT " );
				sb.append("            'W2' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+14) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1+6) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//前一周
				sb.append("        SELECT " );
				sb.append("            'W1' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("    AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2+7) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-1-1) DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("    GROUP BY " );
				sb.append("            INTENT_SERIES " );

				sb.append("        UNION ALL " );
				//当前周
				sb.append("        SELECT " );
				sb.append("            'WN' AS DATETIME , " );
				sb.append("            C.INTENT_SERIES, " );
				sb.append("            COUNT(1) AS total_count " );
				sb.append("        FROM " );
				sb.append("            TM_POTENTIAL_CUSTOMER a " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT b " );
				sb.append("        ON " );
				sb.append("            a.CUSTOMER_NO=b.CUSTOMER_NO " );
				sb.append("        AND A.DEALER_CODE=B.DEALER_CODE " );
				sb.append("        LEFT JOIN TT_CUSTOMER_INTENT_DETAIL C " );
				sb.append("        ON " );
				sb.append("            B.INTENT_ID=C.INTENT_ID " );
				sb.append("        AND A.DEALER_CODE=C.DEALER_CODE " );
				sb.append(" 		 AND C.IS_MAIN_MODEL = 12781001 ");
				sb.append("        WHERE " );
				sb.append("            INTENT_SERIES IS NOT NULL " );
				sb.append("        AND A.INTENT_LEVEL IN (13101001,13101002,13101003,13101004) "  );
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" AND A.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else{
        	if(userid != null){
        		sb.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealercode));
        	}
        }
		sb.append("   AND FOUND_DATE >=DATE(CONCAT(DATE_SUB(CURRENT_DATE,INTERVAL (DAYOFWEEK(CURRENT_DATE)-2) DAY),' 00:00:00')) " +
				"        AND FOUND_DATE<DATE(CONCAT(DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY),' 00:00:00')) " );
				sb.append(" AND A.DEALER_CODE='"+dealercode+"' " );
				sb.append("   GROUP BY " );
				sb.append("            INTENT_SERIES " );
				sb.append("    ) " );
				sb.append("    a " );
				sb.append(" ON D.SERIES_CODE = A.INTENT_SERIES ");
				sb.append(" GROUP BY " );
				sb.append("     SERIES_CODE)HSL ");
//				sql.append(" LEFT JOIN TM_SERIES S ON S.SERIES_CODE = HSL.SERIES_CODE  ");// 有重复数据 lim发现 没有entity过滤 临时这里改一下了
				sb.append("  JOIN TM_SERIES S ON S.SERIES_CODE = HSL.SERIES_CODE  and s.dealer_code='"+dealercode+"' ");
				sb.append(" WHERE 1=1  ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
            sb.append(" AND A.HSL.SERIES_CODE = ?");
            queryList.add(queryParam.get("series"));
        }
		sb.append(" ORDER BY HSL.SERIES_CODE DESC ");
        List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
        return list;
	}


}
