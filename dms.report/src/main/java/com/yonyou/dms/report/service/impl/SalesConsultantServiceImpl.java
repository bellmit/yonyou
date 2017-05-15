package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * 销售顾问业绩统计实现类
 * @author wangliang
 * @date 2017年2月07日
 */
@Service
public class SalesConsultantServiceImpl implements SalesConsultantService {
	
	  @Autowired
      private OperateLogService operateLogService;
	  
	
	
	public PageInfoDto querySalesConsultant(Map<String,String> queryParam) throws ServiceBizException {
		List<Object> whereSql = new ArrayList<Object>();
		String sb = querySalesConsultantStr(queryParam);
		PageInfoDto id =DAOUtil.pageQuery(sb.toString(), whereSql);
		System.out.println("****************************");
		System.out.println(sb.toString());
		System.out.println("****************************");
		return id;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> querySalesConsultantExport(Map<String, String> queryParam) throws ServiceBizException {
		String sb = querySalesConsultantStr(queryParam);
		List<Object> whereSql = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(sb.toString(), whereSql);
	
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("销售顾问业绩统计导出");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;
	}
	
	@SuppressWarnings("unused")
	public String querySalesConsultantStr(Map<String,String> queryParam)  throws ServiceBizException{
		StringBuffer sb = new StringBuffer();
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String startDate = queryParam.get("startdate");
		String endDate = queryParam.get("enddate");
		List<Object> queryList = new ArrayList<Object>();
		sb.append(" SELECT D.* , M.USER_NAME FROM ( ");
		sb.append(" SELECT A.SOLD_BY,B.NOT_FINISH_ORDER_COUNT,C.ADD_ORDER_COUNT,D.FINISHED_ORDER_COUNT, ");
/*		sb.append(" O.GARNITURE_GAIN_AMOUNT,O.GARNITURE_COST_AMOUNT,P.CREDIT_AMOUNT,Q.TAX_AMOUNT,X.LICENSE_AMOUNT ");*/
		sb.append(" (CASE WHEN I.VEHICLE_COST_AMOUNT IS NOT NULL AND I.VEHICLE_COST_AMOUNT != '' THEN   I.VEHICLE_COST_AMOUNT ELSE   0 END  ) AS VEHICLE_COST_AMOUNT, ");
		sb.append(" (CASE WHEN I.VEHICLE_PRICE IS NOT NULL  AND I.VEHICLE_PRICE != '' THEN   I.VEHICLE_PRICE ELSE   0 END ) AS VEHICLE_PRICE, ");
		sb.append(" (CASE WHEN I.VEHICLE_GAIN_AMOUNT IS NOT NULL  AND I.VEHICLE_GAIN_AMOUNT != '' THEN   I.VEHICLE_GAIN_AMOUNT ELSE   0 END ) AS VEHICLE_GAIN_AMOUNT, ");
		sb.append(" (CASE WHEN M.INSURANCE_AMOUNT IS NOT NULL  AND M.INSURANCE_AMOUNT != '' THEN   M.INSURANCE_AMOUNT ELSE   0 END ) AS INSURANCE_AMOUNT, ");
		sb.append(" (CASE WHEN  N.PART_SALES_AMOUNT IS NOT NULL AND N.PART_SALES_AMOUNT != '' THEN    N.PART_SALES_AMOUNT ELSE   0 END ) AS  PART_SALES_AMOUNT, ");
		sb.append(" (CASE WHEN  N.PART_GAIN_AMOUNT IS NOT NULL  AND N.PART_GAIN_AMOUNT != '' THEN    N.PART_GAIN_AMOUNT ELSE   0  END ) AS  PART_GAIN_AMOUNT,  ");
		sb.append(" (CASE WHEN  N.PART_COST_AMOUNT IS NOT NULL AND N.PART_COST_AMOUNT != '' THEN    N.PART_COST_AMOUNT ELSE   0 END ) AS  PART_COST_AMOUNT,  ");
		sb.append(" (CASE WHEN  O.GARNITURE_SALES_AMOUNT IS NOT NULL AND O.GARNITURE_SALES_AMOUNT != '' THEN    O.GARNITURE_SALES_AMOUNT ELSE   0 END ) AS  GARNITURE_SALES_AMOUNT, ");
		sb.append(" (CASE WHEN  VI.TEST_DRIVE_COUNT IS NOT NULL  AND VI.TEST_DRIVE_COUNT != ''THEN    VI.TEST_DRIVE_COUNT ELSE   0 END ) AS TEST_DRIVE_COUNT, ");
		sb.append(" (CASE WHEN  VI.VISIT_COUNT IS NOT NULL  AND VI.VISIT_COUNT != '' THEN    VI.VISIT_COUNT ELSE   0 END  ) AS VISIT_COUNT, ");
		sb.append(" (CASE WHEN  VI.VISIT_CUSTOMER_COUNT IS NOT NULL AND VI.VISIT_CUSTOMER_COUNT != '' THEN    VI.VISIT_CUSTOMER_COUNT ELSE   0 END ) AS VISIT_CUSTOMER_COUNT, ");
		sb.append(" (CASE WHEN VISIT_CUSTOMER_COUNT IS NOT NULL AND VISIT_COUNT <> 0 THEN VISIT_CUSTOMER_COUNT * 1.0000 / VISIT_COUNT ELSE 0 END ) AS VISIT_CUSTOMER_RATE, ");
		sb.append(" (CASE WHEN  O.GARNITURE_GAIN_AMOUNT IS NOT NULL AND O.GARNITURE_GAIN_AMOUNT != '' THEN    O.GARNITURE_GAIN_AMOUNT ELSE   0 END ) AS GARNITURE_GAIN_AMOUNT,  ");
		sb.append(" (CASE WHEN  O.GARNITURE_COST_AMOUNT IS NOT NULL AND O.GARNITURE_COST_AMOUNT != '' THEN    O.GARNITURE_COST_AMOUNT ELSE   0 END ) AS GARNITURE_COST_AMOUNT, ");
		sb.append(" (CASE WHEN  P.CREDIT_AMOUNT IS NOT NULL  AND P.CREDIT_AMOUNT != '' THEN   P.CREDIT_AMOUNT ELSE   0 END  ) AS CREDIT_AMOUNT, ");
		sb.append(" (CASE WHEN   Q.TAX_AMOUNT IS NOT NULL  AND  Q.TAX_AMOUNT != '' THEN    Q.TAX_AMOUNT ELSE   0 END  ) AS  TAX_AMOUNT, ");
		sb.append("(CASE WHEN   X.LICENSE_AMOUNT IS NOT NULL  AND  X.LICENSE_AMOUNT != '' THEN    X.LICENSE_AMOUNT ELSE   0 END ) AS  LICENSE_AMOUNT,");	
		sb.append(" A.DEALER_CODE  FROM ");
		sb.append(" (SELECT DISTINCT SOLD_BY,DEALER_CODE FROM TT_SALES_ORDER) A ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT SOLD_BY,DEALER_CODE,COUNT(SOLD_BY) AS NOT_FINISH_ORDER_COUNT FROM TT_SALES_ORDER Y ");
		sb.append(" WHERE SO_STATUS != "+ CommonConstants.DICT_SO_STATUS_NOT_AUDIT +" AND SO_STATUS != "+ CommonConstants.DICT_SO_STATUS_HAVE_CANCEL +" AND SO_STATUS != "+ CommonConstants.DICT_SO_STATUS_CLOSED +" AND SO_STATUS != "+ CommonConstants.DICT_SO_STATUS_HAVE_UNTREAD +" ");
		sb.append(Utility.getDateCond("Y", "SHEET_CREATE_DATE", startDate, endDate));
		sb.append(Utility.getStrCond("Y", "DEALER_CODE", DealerCode));
		sb.append(" GROUP BY SOLD_BY,DEALER_CODE) B ");
		sb.append(" ON A.DEALER_CODE = B.DEALER_CODE AND A.SOLD_BY = B.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT SOLD_BY,DEALER_CODE,COUNT(SOLD_BY) AS ADD_ORDER_COUNT FROM TT_SALES_ORDER Z ");
		sb.append(" WHERE SO_STATUS != "+ CommonConstants.DICT_SO_STATUS_NOT_AUDIT +"  ");
		sb.append(Utility.getDateCond("Z", "SHEET_CREATE_DATE", startDate, endDate));
		sb.append(Utility.getStrCond("Z", "DEALER_CODE", DealerCode));
		sb.append(" GROUP BY SOLD_BY,DEALER_CODE) C ");
		sb.append(" ON A.DEALER_CODE = C.DEALER_CODE AND A.SOLD_BY = C.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT SOLD_BY,DEALER_CODE,COUNT(SOLD_BY) AS FINISHED_ORDER_COUNT ");
		sb.append(" FROM TT_SALES_ORDER ");
		sb.append(" WHERE ( ");
		sb.append(" (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) ");
		sb.append(" AND ( BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" OR BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_ALLOCATION +") ");
		sb.append(Utility.getDateCond("", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(Utility.getStrCond("", "DEALER_CODE", DealerCode));
		sb.append(" GROUP BY SOLD_BY,DEALER_CODE) D ON A.DEALER_CODE = D.DEALER_CODE AND A.SOLD_BY = D.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT H.DEALER_CODE,SOLD_BY,SUM(VEHICLE_PRICE) AS VEHICLE_PRICE,SUM(VEHICLE_COST_AMOUNT) AS VEHICLE_COST_AMOUNT,SUM(VEHICLE_GAIN_AMOUNT) AS VEHICLE_GAIN_AMOUNT ");
		sb.append(" FROM ");
		sb.append(" (SELECT E.DEALER_CODE,SOLD_BY,VEHICLE_PRICE, ( ");
		sb.append(Utility.getChangeNull("B", "PURCHASE_PRICE", 0)+ " + " +Utility.getChangeNull("B", "ADDITIONAL_COST", 0));
		sb.append(" ) AS VEHICLE_COST_AMOUNT, (");
		sb.append(Utility.getChangeNull("E", "VEHICLE_PRICE", 0)+ " - " +Utility.getChangeNull("B", "PURCHASE_PRICE", 0)+ " - "  +Utility.getChangeNull("B", "ADDITIONAL_COST", 0));
		sb.append(" )AS VEHICLE_GAIN_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER E LEFT JOIN ");
		sb.append(" (SELECT DEALER_CODE,VIN,PURCHASE_PRICE,ADDITIONAL_COST FROM TM_VS_STOCK ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT DEALER_CODE,VIN,PURCHASE_PRICE,ADDITIONAL_COST FROM TT_VS_SHIPPING_NOTIFY A ");
		sb.append(" WHERE NOT EXISTS ");
		sb.append(" (SELECT C.DEALER_CODE,C.VIN FROM TM_VS_STOCK C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN)) B ");
		sb.append(" ON E.VIN = B.VIN AND E.DEALER_CODE = B.DEALER_CODE WHERE 1 = 1 ");
		sb.append(" AND (E.BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_GENERAL +" OR E.BUSINESS_TYPE = "+ CommonConstants.DICT_SO_TYPE_ALLOCATION +") ");
		sb.append(" AND ((SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) ");
		sb.append(Utility.getDateCond("E", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" ) H GROUP BY DEALER_CODE,SOLD_BY) I ON A.DEALER_CODE = I.DEALER_CODE AND A.SOLD_BY = I.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT A.DEALER_CODE,A.SOLD_BY,  ");
		sb.append(" (SUM(B.REAL_PRICE )-SUM(B.COST_PRICE)) ");
		sb.append("  AS INSURANCE_AMOUNT FROM TT_SALES_ORDER A,TT_SO_SERVICE B ");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) ");
		sb.append(" AND B.SERVICE_TYPE =  "+ CommonConstants.DICT_SERVICE_TYPE_INSURANCE +" AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" GROUP BY A.DEALER_CODE,A.SOLD_BY) M ");
		sb.append(" ON A.DEALER_CODE = M.DEALER_CODE AND A.SOLD_BY = M.SOLD_BY ");
		sb.append(" LEFT JOIN (SELECT A.DEALER_CODE,A.SOLD_BY,SUM(B.PART_SALES_AMOUNT) AS PART_SALES_AMOUNT,SUM(B.PART_COST_AMOUNT) AS PART_COST_AMOUNT, ");
		sb.append(" (SUM(B.PART_SALES_AMOUNT )-SUM(B.PART_COST_AMOUNT)) ");
		sb.append(" AS PART_GAIN_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER A,TT_SO_PART B WHERE A.DEALER_CODE = B.DEALER_CODE ");
		sb.append(" AND A.SO_NO = B.SO_NO  ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) ");
		sb.append(" AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" GROUP BY A.DEALER_CODE,A.SOLD_BY) N ");
		sb.append(" ON A.DEALER_CODE = N.DEALER_CODE AND A.SOLD_BY = N.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT A.DEALER_CODE,A.SOLD_BY,SUM(B.RECEIVEABLE_AMOUNT) AS GARNITURE_SALES_AMOUNT,SUM(B.COST_AMOUNT) AS GARNITURE_COST_AMOUNT, ");
		sb.append(" (SUM(B.RECEIVEABLE_AMOUNT )-SUM(B.COST_AMOUNT)) ");
		sb.append(" AS GARNITURE_GAIN_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER A,TT_SO_GARNITURE B WHERE A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" GROUP BY A.DEALER_CODE,A.SOLD_BY) O ");
		sb.append(" ON A.DEALER_CODE = O.DEALER_CODE AND A.SOLD_BY = O.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT A.DEALER_CODE,A.SOLD_BY,(SUM(B.REAL_PRICE )-SUM(B.COST_PRICE)) AS CREDIT_AMOUNT ");
		sb.append(" FROM TT_SALES_ORDER A,TT_SO_SERVICE B WHERE A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) AND B.SERVICE_TYPE = "+ CommonConstants.DICT_SERVICE_TYPE_CREDIT +" AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" ");
		sb.append(" GROUP BY A.DEALER_CODE,A.SOLD_BY) P ON A.DEALER_CODE = P.DEALER_CODE AND A.SOLD_BY = P.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT A.DEALER_CODE,A.SOLD_BY,(SUM(B.REAL_PRICE )-SUM(B.COST_PRICE)) AS TAX_AMOUNT FROM TT_SALES_ORDER A,TT_SO_SERVICE B ");
		sb.append(" WHERE A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) AND B.SERVICE_TYPE = "+ CommonConstants.DICT_SERVICE_TYPE_BUY_TAX +" AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" ");
		sb.append(" GROUP BY A.DEALER_CODE,A.SOLD_BY) Q ");
		sb.append(" ON A.DEALER_CODE = Q.DEALER_CODE AND A.SOLD_BY = Q.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT  A.DEALER_CODE,A.SOLD_BY, (SUM(B.REAL_PRICE )-SUM(B.COST_PRICE)) AS LICENSE_AMOUNT FROM TT_SALES_ORDER A,TT_SO_SERVICE B WHERE A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(Utility.getDateCond("A", "STOCK_OUT_DATE", startDate, endDate));
		sb.append(" AND ((A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_CLOSED +") OR (A.SO_STATUS = "+ CommonConstants.DICT_SO_STATUS_HAVE_OUT_STOCK +")) ");
		sb.append(" AND B.SERVICE_TYPE = "+ CommonConstants.DICT_SERVICE_TYPE_HANG_LICENSE +" AND A.BUSINESS_TYPE != "+ CommonConstants.DICT_SO_TYPE_RERURN +" ");
		sb.append(" GROUP BY A.DEALER_CODE,A.SOLD_BY) X ON A.DEALER_CODE = X.DEALER_CODE AND A.SOLD_BY = X.SOLD_BY ");
		sb.append(" LEFT JOIN ");
		sb.append(" (SELECT DEALER_CODE,SOLD_BY,COUNT(*) AS VISIT_COUNT,COUNT(CUSTOMER_NO) AS VISIT_CUSTOMER_COUNT,");
		sb.append(" SUM(CASE WHEN IS_TEST_DRIVE = 12781001 THEN 1 ELSE 0 END) AS TEST_DRIVE_COUNT ");
		sb.append(" FROM TT_VISITING_RECORD A WHERE 1 = 1 ");
		sb.append(Utility.getDateCond("", "VISIT_TIME", startDate, endDate));
		sb.append(" GROUP BY A.DEALER_CODE,A.SOLD_BY) VI ");
		sb.append(" ON A.DEALER_CODE = VI.DEALER_CODE AND A.SOLD_BY = VI.SOLD_BY ");
		sb.append(" WHERE A.DEALER_CODE = "+DealerCode+" ");
		sb.append(" AND EXISTS ");
		sb.append(" (SELECT 1 FROM TM_USER UU WHERE A.SOLD_BY = UU.USER_ID AND A.DEALER_CODE = UU.DEALER_CODE ");
		sb.append(" AND UU.IS_CONSULTANT = 12781001 AND USER_STATUS = 12101001) ");
		sb.append("  ) D LEFT JOIN tm_user M ON D.sold_by = M.USER_ID ");
		
		return sb.toString();
	}
	
}
