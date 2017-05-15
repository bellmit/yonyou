package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoLogger;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 销售统计分析报表
 * @author wangliang
 * @date 2016年9月27日
 */

@Service
public class SalesStatisticsServiceImpl implements SalesStatisticsService{
	
	@Autowired
    private OperateLogService operateLogService;

	@Override
	public PageInfoDto querySalesStatisticsChecked(Map<String, String> queryParam) throws ServiceBizException {
/*		StringBuffer sb = new StringBuffer(" SELECT BB.BRAND_CODE,BB.dealer_code,BB.ORG_NAME,BB.ORG_CODE,BB.USER_NAME,BB.ADD_TOTAL,BB.COUNT_TOTAL,BB.ACCOUNT_TOTAL,BB.modelOrder_total,BB.count_modelcount,BB.intent_model_total,BB.cjiaolv,BB.PERMUTED_TOTAL,BB.HSL_REPLACE, ");
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String date = queryParam.get("startdate");
		String endDate = queryParam.get("enddate");
		String soldBy = queryParam.get("soldBy");
		String orgCode = queryParam.get("orgCode");
		sb.append(" CONCAT(CAST((CASE WHEN BB.HSL_REPLACE = 0 THEN 0 ELSE CAST(BB.HSL_REPLACE AS DECIMAL (10, 2)) * 100  END /  CASE WHEN BB.ADD_TOTAL = 0 THEN 1  ELSE CAST(BB.ADD_TOTAL AS DECIMAL (10, 2))  END ) AS DECIMAL (10, 2)), '%') AS REPLACE_RATE, ");
		sb.append(" CONCAT(CAST((CASE WHEN BB.PERMUTED_TOTAL = 0 THEN 0 ELSE CAST(BB.PERMUTED_TOTAL AS DECIMAL (10, 2)) * 100  END / CASE WHEN BB.HSL_REPLACE = 0  THEN 1  ELSE CAST(BB.HSL_REPLACE AS DECIMAL (10, 2))  END ) AS DECIMAL (10, 2) ) , '%' ) AS CONVER_RATE, ");
		sb.append(" CONCAT(CAST((CASE WHEN BB.PERMUTED_total = 0 THEN 0 ELSE CAST(BB.PERMUTED_total AS DECIMAL (10, 2)) * 100  END / CASE WHEN BB.ACCOUNT_TOTAL = 0  THEN 1  ELSE CAST(BB.ACCOUNT_TOTAL AS DECIMAL (10, 2))   END ) AS DECIMAL (10, 2)),'%') AS PERMUTED_RATE  ");
		sb.append(" FROM(SELECT AA.BRAND_CODE,AA.dealer_code,AA.ORG_NAME,AA.ORG_CODE,AA.USER_NAME,SUM(AA.ADD_TOTAL) AS ADD_TOTAL,SUM(COUNT_TOTAL) AS COUNT_TOTAL,SUM(AA.ACCOUNT_TOTAL) AS ACCOUNT_TOTAL,SUM(AA.modelOrder_total) AS modelOrder_total,SUM(AA.count_modelcount) AS count_modelcount,SUM(AA.intent_model_total) AS intent_model_total,0.00 AS cjiaolv,SUM(AA.PERMUTED_total) AS PERMUTED_total,SUM(AA.HSL_REPLACE) AS HSL_REPLACE ");
		sb.append(" FROM");
		sb.append(" (SELECT '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,c.add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM");
		sb.append(" (SELECT a.dealer_code,COUNT(*) AS add_total,a.sold_by FROM TM_POTENTIAL_CUSTOMER a INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = a.dealer_code AND a.customer_no = i.customer_no AND i.intent_id = a.intent_id INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = a.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE a.dealer_code = "+DealerCode+" " );
		sb.append(" AND (a.intent_level = 13101001 OR a.intent_level = 13101002 OR a.intent_level = 13101003 OR a.intent_level = 13101008 OR a.intent_level = 13101009) AND a.d_key = "+ CommonConstants.D_KEY +"" );
		sb.append(Utility.getDateCond("a", "FOUND_DATE", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" " );
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,c.add_total AS HSL_REPLACE,sold_by " );
		sb.append(" FROM ");
		sb.append(" (SELECT a.dealer_code,COUNT(1) AS add_total,a.sold_by FROM TM_POTENTIAL_CUSTOMER a INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = a.dealer_code AND a.customer_no = i.customer_no AND i.intent_id = a.intent_id INNER JOIN TT_CUSTOMER_VEHICLE_LIST e ON e.dealer_code = a.dealer_code AND e.customer_no = a.customer_no AND e.FILE_MESSAGE_B IS NOT NULL AND e.FILE_URLMESSAGE_B IS NOT NULL AND e.FILE_URLMESSAGE_A IS NOT NULL AND e.FILE_URLMESSAGE_C IS NOT NULL AND e.FILE_MESSAGE_A IS NOT NULL AND e.FILE_MESSAGE_C IS NOT NULL ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = a.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 ");
		sb.append(" WHERE a.dealer_code = "+DealerCode+" ");
		sb.append(" AND (a.intent_level = 13101001 OR a.intent_level = 13101002 OR a.intent_level = 13101003 OR a.intent_level = 13101004 OR a.intent_level = 13101008 OR a.intent_level = 13101009) ");
		sb.append(" AND a.REBUY_CUSTOMER_TYPE = 10541002 AND a.d_key = "+ CommonConstants.D_KEY +" ");
		sb.append(Utility.getDateCond("a", "REPLACE_DATE", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" " );
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,b.user_name,0 AS add_total,c.count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM ");
		sb.append(" (SELECT  a.dealer_code,COUNT(*) AS count_total,a.sold_by,0 AS customer_total,0 AS buy_total FROM tt_sales_order a WHERE a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +" " );
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append("AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_HAVE_CANCEL+" ");
		sb.append(" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_HAVE_UNTREAD+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_NOT_AUDIT+" " );
		sb.append(" and a.BUSINESS_TYPE = " + CommonConstants.DICT_SO_TYPE_GENERAL+" ");
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,C.ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM (SELECT dealer_code,COUNT(*) AS ACCOUNT_total,sold_by FROM tt_sales_order WHERE dealer_code = "+DealerCode+" ");
		sb.append(Utility.getDateCond("", "sheet_CREATE_DATE", date, endDate));
		sb.append(" AND ( SO_STATUS = "+CommonConstants.DICT_SO_STATUS_CLOSED+" OR SO_STATUS = "+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" OR so_status = 13011010 OR so_status = 13011015 OR so_status = 13011020 OR so_status = 13011025 OR so_status = 13011030) ");
		sb.append(" AND BUSINESS_TYPE = "+CommonConstants.DICT_SO_TYPE_GENERAL+" GROUP BY dealer_code,sold_by) C ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT  '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,c.count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM ");
		sb.append(" (SELECT a.dealer_code,COUNT(1) AS count_modelcount,a.sold_by FROM TM_POTENTIAL_CUSTOMER a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key AND a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +"  ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,c.modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM(SELECT  a.dealer_code,COUNT(*) AS modelOrder_total,a.sold_by ");
		sb.append(" FROM tt_sales_order a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d ");
		sb.append(" WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key ");
		sb.append(" AND a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +" ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append("  AND (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") or SO_STATUS= "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +" AND BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" GROUP BY a.dealer_code,a.sold_by) C  ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,c.intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by FROM");
		sb.append(" (SELECT a.dealer_code,COUNT(*) AS intent_model_total,a.sold_by FROM tt_sales_order a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d  WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key AND a.dealer_code = "+DealerCode+" AND EXISTS ");
		sb.append(" (SELECT p.MODEL_CODE FROM ");
		sb.append(" ("+ CommonConstants.VM_VS_PRODUCT +") p ");
		sb.append(" WHERE d.INTENT_MODEL = p.MODEL_CODE AND p.PRODUCT_CODE = a.PRODUCT_CODE) AND a.d_key = "+ CommonConstants.D_KEY +"  ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append(" AND (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") or SO_STATUS= "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +" AND BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" GROUP BY a.dealer_code,a.sold_by) C ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,C.PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM");
		sb.append(" (SELECT A.dealer_code,COUNT(*) AS PERMUTED_total,A.sold_by FROM tt_sales_order A WHERE A.dealer_code = "+DealerCode+" AND A.BUSINESS_TYPE = 13001001 AND A.PERMUTED_VIN IS NOT NULL AND A.FILE_OLD_A IS NOT NULL AND A.FILE_URLOLD_A IS NOT NULL ");
		sb.append(Utility.getDateCond("a", "PERMUTED_DATE", date, endDate));
		sb.append(" GROUP BY A.dealer_code,A.sold_by) C LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code ");
		sb.append(" AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code) AA   ");
		if (soldBy != null && !"".equals(soldBy)) {
			sb.append("   WHERE  AA.sold_by='" + soldBy + "' ");
		}
		if (soldBy != null && !"".equals(soldBy)){
		  if (orgCode != null && !"".equals(orgCode)){
			sb.append(" AND    AA.org_code = '" + orgCode + "' ");
		   }
		} else{
			if (orgCode != null && !"".equals(orgCode)){
				sb.append("  WHERE   AA.org_code = '" + orgCode + "' ");
			 }
		}
		sb.append(" GROUP BY AA.dealer_code,AA.ORG_CODE,AA.ORG_NAME,AA.USER_NAME,AA.BRAND_CODE)BB ");*/
		
		String sb = querySalesStatisticsCheckedStr(queryParam);
		System.out.println("************************");
		System.out.println(sb.toString());
		System.out.println("************************");
		List<Object> whereSql = new ArrayList<Object>();
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), whereSql);
		return id;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesStaticExport(Map<String, String> queryParam) throws ServiceBizException {
		String sb = querySalesStatisticsCheckedStr(queryParam);
		List<Object> whereSql = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sb.toString(), whereSql);
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("销售统计分析导出");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;	
	}
	
	
	public String querySalesStatisticsCheckedStr(Map<String, String> queryParam) throws ServiceBizException {
		
		StringBuffer sb = new StringBuffer(" SELECT BB.BRAND_CODE,BB.dealer_code,BB.ORG_NAME,BB.ORG_CODE,BB.USER_NAME,BB.ADD_TOTAL,BB.COUNT_TOTAL,BB.ACCOUNT_TOTAL,BB.modelOrder_total,BB.count_modelcount,BB.intent_model_total,BB.cjiaolv,BB.PERMUTED_TOTAL,BB.HSL_REPLACE, ");
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String date = queryParam.get("startdate");
		String endDate = queryParam.get("enddate");
		String soldBy = queryParam.get("soldBy");
		String orgCode = queryParam.get("orgCode");
		sb.append(" CONCAT(CAST((CASE WHEN BB.HSL_REPLACE = 0 THEN 0 ELSE CAST(BB.HSL_REPLACE AS DECIMAL (10, 2)) * 100  END /  CASE WHEN BB.ADD_TOTAL = 0 THEN 1  ELSE CAST(BB.ADD_TOTAL AS DECIMAL (10, 2))  END ) AS DECIMAL (10, 2)), '%') AS REPLACE_RATE, ");
		sb.append(" CONCAT(CAST((CASE WHEN BB.PERMUTED_TOTAL = 0 THEN 0 ELSE CAST(BB.PERMUTED_TOTAL AS DECIMAL (10, 2)) * 100  END / CASE WHEN BB.HSL_REPLACE = 0  THEN 1  ELSE CAST(BB.HSL_REPLACE AS DECIMAL (10, 2))  END ) AS DECIMAL (10, 2) ) , '%' ) AS CONVER_RATE, ");
		sb.append(" CONCAT(CAST((CASE WHEN BB.PERMUTED_total = 0 THEN 0 ELSE CAST(BB.PERMUTED_total AS DECIMAL (10, 2)) * 100  END / CASE WHEN BB.ACCOUNT_TOTAL = 0  THEN 1  ELSE CAST(BB.ACCOUNT_TOTAL AS DECIMAL (10, 2))   END ) AS DECIMAL (10, 2)),'%') AS PERMUTED_RATE  ");
		sb.append(" FROM(SELECT AA.BRAND_CODE,AA.dealer_code,AA.ORG_NAME,AA.ORG_CODE,AA.USER_NAME,SUM(AA.ADD_TOTAL) AS ADD_TOTAL,SUM(COUNT_TOTAL) AS COUNT_TOTAL,SUM(AA.ACCOUNT_TOTAL) AS ACCOUNT_TOTAL,SUM(AA.modelOrder_total) AS modelOrder_total,SUM(AA.count_modelcount) AS count_modelcount,SUM(AA.intent_model_total) AS intent_model_total,0.00 AS cjiaolv,SUM(AA.PERMUTED_total) AS PERMUTED_total,SUM(AA.HSL_REPLACE) AS HSL_REPLACE ");
		sb.append(" FROM");
		sb.append(" (SELECT '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,c.add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM");
		sb.append(" (SELECT a.dealer_code,COUNT(*) AS add_total,a.sold_by FROM TM_POTENTIAL_CUSTOMER a INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = a.dealer_code AND a.customer_no = i.customer_no AND i.intent_id = a.intent_id INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = a.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE a.dealer_code = "+DealerCode+" " );
		sb.append(" AND (a.intent_level = 13101001 OR a.intent_level = 13101002 OR a.intent_level = 13101003 OR a.intent_level = 13101008 OR a.intent_level = 13101009) AND a.d_key = "+ CommonConstants.D_KEY +"" );
		sb.append(Utility.getDateCond("a", "FOUND_DATE", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" " );
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,c.add_total AS HSL_REPLACE,sold_by " );
		sb.append(" FROM ");
		sb.append(" (SELECT a.dealer_code,COUNT(1) AS add_total,a.sold_by FROM TM_POTENTIAL_CUSTOMER a INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = a.dealer_code AND a.customer_no = i.customer_no AND i.intent_id = a.intent_id INNER JOIN TT_CUSTOMER_VEHICLE_LIST e ON e.dealer_code = a.dealer_code AND e.customer_no = a.customer_no AND e.FILE_MESSAGE_B IS NOT NULL AND e.FILE_URLMESSAGE_B IS NOT NULL AND e.FILE_URLMESSAGE_A IS NOT NULL AND e.FILE_URLMESSAGE_C IS NOT NULL AND e.FILE_MESSAGE_A IS NOT NULL AND e.FILE_MESSAGE_C IS NOT NULL ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = a.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 ");
		sb.append(" WHERE a.dealer_code = "+DealerCode+" ");
		sb.append(" AND (a.intent_level = 13101001 OR a.intent_level = 13101002 OR a.intent_level = 13101003 OR a.intent_level = 13101004 OR a.intent_level = 13101008 OR a.intent_level = 13101009) ");
		sb.append(" AND a.REBUY_CUSTOMER_TYPE = 10541002 AND a.d_key = "+ CommonConstants.D_KEY +" ");
		sb.append(Utility.getDateCond("a", "REPLACE_DATE", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" " );
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,b.user_name,0 AS add_total,c.count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM ");
		sb.append(" (SELECT  a.dealer_code,COUNT(*) AS count_total,a.sold_by,0 AS customer_total,0 AS buy_total FROM tt_sales_order a WHERE a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +" " );
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append("AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_HAVE_CANCEL+" ");
		sb.append(" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_HAVE_UNTREAD+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK+" AND a.SO_STATUS <> "+CommonConstants.DICT_SO_STATUS_NOT_AUDIT+" " );
		sb.append(" and a.BUSINESS_TYPE = " + CommonConstants.DICT_SO_TYPE_GENERAL+" ");
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,C.ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM (SELECT dealer_code,COUNT(*) AS ACCOUNT_total,sold_by FROM tt_sales_order WHERE dealer_code = "+DealerCode+" ");
		sb.append(Utility.getDateCond("", "sheet_CREATE_DATE", date, endDate));
		sb.append(" AND ( SO_STATUS = "+CommonConstants.DICT_SO_STATUS_CLOSED+" OR SO_STATUS = "+CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" OR so_status = 13011010 OR so_status = 13011015 OR so_status = 13011020 OR so_status = 13011025 OR so_status = 13011030) ");
		sb.append(" AND BUSINESS_TYPE = "+CommonConstants.DICT_SO_TYPE_GENERAL+" GROUP BY dealer_code,sold_by) C ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT  '' AS BRAND_CODE,C.dealer_code,AI.org_NAME,AI.org_code,b.user_name,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,c.count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM ");
		sb.append(" (SELECT a.dealer_code,COUNT(1) AS count_modelcount,a.sold_by FROM TM_POTENTIAL_CUSTOMER a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key AND a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +"  ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append(" GROUP BY a.sold_by,a.dealer_code) C ");
		sb.append(" LEFT JOIN TM_USER B ON C.dealer_code = b.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code WHERE C.dealer_code = "+DealerCode+" ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,c.modelOrder_total,0 AS intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM(SELECT  a.dealer_code,COUNT(*) AS modelOrder_total,a.sold_by ");
		sb.append(" FROM tt_sales_order a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d ");
		sb.append(" WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key ");
		sb.append(" AND a.dealer_code = "+DealerCode+" AND a.d_key = "+ CommonConstants.D_KEY +" ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append("  AND (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") or SO_STATUS= "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +" AND BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" GROUP BY a.dealer_code,a.sold_by) C  ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id ");
		sb.append(" LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,c.intent_model_total,0 AS PERMUTED_total,0 AS HSL_REPLACE,sold_by FROM");
		sb.append(" (SELECT a.dealer_code,COUNT(*) AS intent_model_total,a.sold_by FROM tt_sales_order a,TT_CUSTOMER_INTENT b,TT_CUSTOMER_INTENT_DETAIL d  WHERE a.CUSTOMER_NO = b.CUSTOMER_NO AND b.INTENT_ID = d.INTENT_ID AND a.dealer_code = b.dealer_code AND b.dealer_code = d.dealer_code AND a.d_key = b.d_key AND b.d_key = d.d_key AND a.dealer_code = "+DealerCode+" AND EXISTS ");
		sb.append(" (SELECT p.MODEL_CODE FROM ");
		sb.append(" ("+ CommonConstants.VM_VS_PRODUCT +") p ");
		sb.append(" WHERE d.INTENT_MODEL = p.MODEL_CODE AND p.PRODUCT_CODE = a.PRODUCT_CODE) AND a.d_key = "+ CommonConstants.D_KEY +"  ");
		sb.append(Utility.getDateCond("a", "CREATED_AT", date, endDate));
		sb.append(" AND (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") or SO_STATUS= "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +" AND BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" GROUP BY a.dealer_code,a.sold_by) C ");
		sb.append(" LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT '' AS BRAND_CODE,C.dealer_code,AI.ORG_NAME,AI.org_code,B.USer_NAME,0 AS add_total,0 AS count_total,0 AS ACCOUNT_total,0 AS count_modelcount,0 AS modelOrder_total,0 AS intent_model_total,C.PERMUTED_total,0 AS HSL_REPLACE,sold_by ");
		sb.append(" FROM");
		sb.append(" (SELECT A.dealer_code,COUNT(*) AS PERMUTED_total,A.sold_by FROM tt_sales_order A WHERE A.dealer_code = "+DealerCode+" AND A.BUSINESS_TYPE = 13001001 AND A.PERMUTED_VIN IS NOT NULL AND A.FILE_OLD_A IS NOT NULL AND A.FILE_URLOLD_A IS NOT NULL ");
		sb.append(Utility.getDateCond("a", "PERMUTED_DATE", date, endDate));
		sb.append(" GROUP BY A.dealer_code,A.sold_by) C LEFT JOIN tm_user B ON C.dealer_code = B.dealer_code ");
		sb.append(" AND C.sold_by = b.user_id LEFT JOIN TM_ORGANIZATION AI ON C.dealer_code = AI.dealer_code AND B.org_code = AI.org_code) AA   ");
		if (soldBy != null && !"".equals(soldBy)) {
			sb.append("   WHERE  AA.sold_by='" + soldBy + "' ");
		}
		if (soldBy != null && !"".equals(soldBy)){
		  if (orgCode != null && !"".equals(orgCode)){
			sb.append(" AND    AA.org_code = '" + orgCode + "' ");
		   }
		} else{
			if (orgCode != null && !"".equals(orgCode)){
				sb.append("  WHERE   AA.org_code = '" + orgCode + "' ");
			 }
		}
		sb.append(" GROUP BY AA.dealer_code,AA.ORG_CODE,AA.ORG_NAME,AA.USER_NAME,AA.BRAND_CODE)BB ");
		System.err.println(sb.toString());
		return sb.toString();
	}
	
	
	
}
