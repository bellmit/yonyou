package com.yonyou.dms.schedule.service.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.SA012Dto;
import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;
import com.yonyou.dms.common.domains.PO.basedata.TmAscBasicinfoPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SADMS013ServiceImpl implements SADMS013Service {
	@Override
	public LinkedList<SA013Dto> getSADMS013(String entityCode) throws ServiceBizException{
		try {
			
			
		  
			LinkedList<SA013Dto> resultList = new LinkedList<SA013Dto>();
			Date date = new Date();
			
			Format format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calm = Calendar.getInstance();
			calm.setTime(date);
			
			int rowNum = 0;
			if (calm.get(Calendar.DAY_OF_WEEK) == 1) { // 周日
				rowNum = 8;
			} else { // 依次是 2-7
				rowNum = calm.get(Calendar.DAY_OF_WEEK);
			}
			        for (int i = 0; i < rowNum - 1; i++) {
		                SA013Dto dto = new SA013Dto();
		                int callIn=0;
		                int walkIn=0;
		                int noOfSc=0;
		                List<Map> listNoserice = new ArrayList();
		                listNoserice = QueryNoSeriesAnalysisReport(entityCode,i);
		                if (listNoserice != null && listNoserice.size() > 0) {
		                    Map map = listNoserice.get(0);
		                    if(!StringUtils.isNullOrEmpty(map.get("CALL_IN")))
		                        callIn=Integer.parseInt(map.get("CALL_IN").toString());
		                    if(!StringUtils.isNullOrEmpty(map.get("WALK_IN")))
		                        walkIn=Integer.parseInt(map.get("WALK_IN").toString());
		                    if(!StringUtils.isNullOrEmpty(map.get("NO_OF_SC")))
		                        noOfSc=Integer.parseInt(map.get("NO_OF_SC").toString());
		          
		                }
		                dto.setDealerCode(entityCode);
                        dto.setCallIn(callIn);
                        dto.setWalkIn(walkIn);
                        dto.setNoOfSc(noOfSc);
		                dto.setCurrentDate(new Date());
		                calm.add(Calendar.DATE, -i);
		                dto.setDataTime(calm.getTime());
		                String startDate = format.format(dto.getDataTime()).toString() + " 00:00:00";
		                String endDate = format.format(dto.getDataTime()).toString() + " 23:59:59";
		                calm.setTime(date);
		                LinkedList<SA012Dto> saList = new LinkedList<SA012Dto>();
		                List<Map> listSeries = new ArrayList();
		                listSeries = QuerySeriesAnalysisReport(entityCode,startDate, endDate);
		                if (listSeries != null && listSeries.size() > 0) {
		                    for (int j = 0; j < listSeries.size(); j++) {
		                        Map map =  listSeries.get(j);
		                        SA012Dto sDto = new SA012Dto();
		                        sDto.setSeriesCode((String) map.get("SERIES_CODE"));
		                        sDto.setWalkFound(Integer.parseInt(map.get("WALK_FOUND").toString())); // 有效客流
		                        sDto.setHotSalesLeads(Integer.parseInt(map.get("HOT_SALES_LEADS").toString())); // hsl
		                        sDto.setSalesOrders(Integer.parseInt(map.get("SALES_ORDERS").toString())); // 订单数量
		                        sDto.setConversionRatio(Double.parseDouble(map.get("CONVERSION_RATIO").toString()));
		                        sDto.setTestDrive(Integer.parseInt(map.get("TEST_DRIVE").toString())); // 试乘试驾数
		                        sDto.setHslSumreplace(Integer.parseInt(map.get("HSL_SUMREPLACE").toString())); // 置换意向客户数
		                        sDto.setSalesSumreplace(Integer.parseInt(map.get("SALES_SUMREPLACE").toString())); // 置换成交数
		                        sDto.setHslReplace(Integer.parseInt(map.get("HSL_REPLACE").toString()));
		                        sDto.setSalesReplace(Integer.parseInt(map.get("SALES_REPLACE").toString()));
		                        sDto.setDccOfHot(Integer.parseInt(map.get("DCC_OF_HOT").toString())); // HSL DCC转入的潜客数量
		                        sDto.setInvoiceScanNum(Integer.parseInt(map.get("SCAN_NUM").toString())); // 开票数
		                        sDto.setLcreplaceOrders(Integer.parseInt(map.get("LCR_ORDER").toString()));
		                        saList.add(sDto);
		                    }
		                } else {
		                    SA012Dto savo = new SA012Dto();
		                    savo.setWalkFound(0); // 有效客流
		                    savo.setHotSalesLeads(0); // hsl
		                    savo.setSalesOrders(0); // 订单数量
		                    savo.setConversionRatio(0.00);
		                    savo.setTestDrive(0); // 试乘试驾数
		                    savo.setHslSumreplace(0); // 置换意向客户数
		                    savo.setSalesReplace(0); // 置换成交数
		                    savo.setHslReplace(0);
		                    savo.setSalesReplace(0);
		                    savo.setDccOfHot(0); // HSL DCC转入的潜客数量
		                    savo.setInvoiceScanNum(0); // 开票数
		                    savo.setLcreplaceOrders(0);
		                    saList.add(savo);
		                }
		                dto.setList(saList);
		                resultList.add(dto);
		            }
			
	
			return resultList;
		} catch (Exception e) {
			return null;
		}

	}

	private List<Map> QueryNoSeriesAnalysisReport(String entitycode,int subDays) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  SUM(call_In) AS call_In,SUM(walk_In) AS walk_In,SUM(no_Of_Sc) AS no_Of_Sc  FROM ");
		sb.append(" (SELECT COUNT(1) AS call_In,0 AS walk_In,0 AS no_Of_Sc FROM TT_VISITING_RECORD  WHERE DEALER_CODE='"+entitycode+"' AND IS_VALID = 12781001  AND VISIT_TYPE = ' "+CommonConstants.DICT_VISIT_TYPE_BY_CALL+" '  AND TO_DAYS (VISIT_TIME) = (TO_DAYS (NOW()) - 1)  UNION ALL  SELECT  0 AS call_In, ");
		sb.append(" COUNT(1) AS walk_In,0 AS no_Of_Sc FROM TT_VISITING_RECORD WHERE DEALER_CODE='"+entitycode+"' AND IS_VALID = 12781001 AND (VISIT_TYPE != ' "+CommonConstants.DICT_VISIT_TYPE_BY_CALL+" ' OR VISIT_TYPE IS NULL)  ");
		sb.append(" AND TO_DAYS (VISIT_TIME) = (TO_DAYS (NOW()) - 1) UNION ALL ");
		sb.append(" SELECT 0 AS call_In,0 AS walk_In,COUNT(1) AS no_Of_Sc FROM tm_user WHERE DEALER_CODE='"+entitycode+"' AND  is_consultant = ' "+CommonConstants.DICT_IS_YES +" ' AND user_status = ' "+CommonConstants.DICT_IN_USE_START +" ') a ");
		List<Map> list = Base.findAll(sb.toString());
		return list;
	}

	private static List<Map> QuerySeriesAnalysisReport(String entityCode,String startDate, String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  series_code,SUM(walk_Found)  walk_found,SUM(hot_sales_leads) hot_sales_leads,SUM(dcc_of_hot) dcc_of_hot,SUM(HSL_REPLACE)  HSL_REPLACE,SUM(HSL_SUMREPLACE) HSL_SUMREPLACE,SUM(sales_orders)  sales_orders,SUM(SALES_REPLACE)  SALES_REPLACE,SUM(SALES_SUMREPLACE)  SALES_SUMREPLACE, ");
		sb.append(" (SUM(sales_orders) * 1.00 / (CASE WHEN SUM(hot_sales_leads) = 0 THEN 1 ELSE SUM(hot_sales_leads) END)) AS conversion_ratio, ");
		sb.append(" SUM(test_drive)  test_drive,SUM(scan_num) AS  scan_num,SUM(LCR_ORDER) AS  LCR_ORDER  ");
		sb.append(" FROM ");
		sb.append(" (SELECT d.intent_series series_code,COUNT(p.customer_no) walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE, 0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER  FROM tm_potential_customer P  INNER JOIN TT_CUSTOMER_INTENT i  ON i.dealer_code = p.dealer_code  AND p.customer_no = i.customer_no  AND i.intent_id = p.intent_id  INNER JOIN TT_CUSTOMER_INTENT_DETAIL d  ON d.dealer_code = p.dealer_code  AND d.INTENT_ID = i.INTENT_ID  AND d.IS_MAIN_MODEL = 12781001  ");
		sb.append(" INNER JOIN ");
		sb.append(" (SELECT v2.dealer_code,v2.customer_no,v2.IS_VALID,v2.VISIT_TYPE,v2.VISIT_TIME FROM(SELECT b.dealer_code,b.customer_no,b.IS_VALID,b.VISIT_TYPE,b.VISIT_TIME,IF(@pdept=CONCAT(b.customer_no,b.dealer_code),@rank:=@rank+1,@rank:=1) AS rank,@pdept:=CONCAT(b.customer_no,b.dealer_code),@rownum:=@rownum+1 FROM  ");
		sb.append(" (SELECT  v1.dealer_code,v1.customer_no,v1.IS_VALID, v1.VISIT_TYPE,v1.VISIT_TIME FROM TT_VISITING_RECORD v1 INNER JOIN tm_potential_customer v11 ON v1.dealer_code = v11.dealer_code AND v1.customer_no = v11.customer_no AND v1.IS_VALID = 12781001  AND (v1.VISIT_TYPE != 13091001 OR v1.VISIT_TYPE IS NULL)  AND v1.VISIT_TIME > ' "+startDate+" ' AND v1.VISIT_TIME <= ' "+ endDate +" ' ORDER BY CONCAT(v1.customer_name,v1.dealer_code),v1.VISIT_TIME DESC) b,(SELECT @rownum :=0 , @pdept := NULL ,@rank:=0) a) v2  WHERE v2.rank = 1) v  ");
		sb.append(" ON v.dealer_code = p.dealer_code AND v.customer_no = p.customer_no AND v.IS_VALID = 12781001  ");
		sb.append(" AND (v.VISIT_TYPE != 13091001 OR v.VISIT_TYPE IS NULL)  ");
		sb.append(" AND v.VISIT_TIME > ' "+startDate+" ' AND v.VISIT_TIME <= ' "+ endDate +" '  ");
		sb.append("  WHERE p.DEALER_CODE ='"+entityCode+"' GROUP BY d.intent_series  ");
		sb.append(" UNION ALL  ");
		sb.append(" SELECT d.intent_series series_code,0 AS walk_Found,COUNT(1) AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER  ");
		sb.append(" FROM tm_potential_customer P INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = p.dealer_code AND p.customer_no = i.customer_no AND i.intent_id = p.intent_id ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = p.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE ");
		sb.append("   p.DEALER_CODE ='"+entityCode+"' AND (p.intent_level = 13101001 OR p.intent_level = 13101002 OR p.intent_level = 13101003 OR p.intent_level = 13101004 OR p.intent_level = 13101008 OR p.intent_level = 13101009)  ");
		sb.append(" AND p.FOUND_DATE > ' "+startDate+" ' AND p.FOUND_DATE <= ' "+ endDate +" ' GROUP BY d.intent_series  ");
		sb.append(" UNION ALL  ");
		sb.append(" SELECT  d.intent_series series_code,0 AS walk_Found,0 AS hot_sales_leads,COUNT(1) AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER FROM tm_potential_customer P  ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = p.dealer_code AND p.customer_no = i.customer_no AND i.intent_id = p.intent_id INNER JOIN TT_CUSTOMER_INTENT_DETAIL d  ON d.dealer_code = p.dealer_code  AND d.INTENT_ID = i.INTENT_ID  AND d.IS_MAIN_MODEL = 12781001  WHERE   ");
		sb.append(" p.DEALER_CODE ='"+entityCode+"' AND (p.intent_level = 13101001 OR p.intent_level = 13101002 OR p.intent_level = 13101003 OR p.intent_level = 13101004 OR p.intent_level = 13101008  OR p.intent_level = 13101009 )  AND p.CUS_SOURCE = "+ CommonConstants.DICT_CUS_SOURCE_BY_DCC +"  AND p.FOUND_DATE > ' "+startDate+" '  AND p.FOUND_DATE <= ' "+ endDate +" ' ");
		sb.append(" GROUP BY d.intent_series ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT d.intent_series series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,COUNT(1) AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER  FROM tm_potential_customer P ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = p.dealer_code AND p.customer_no = i.customer_no AND i.intent_id = p.intent_id INNER JOIN TT_CUSTOMER_VEHICLE_LIST e ON e.dealer_code = p.dealer_code AND e.customer_no = p.customer_no  ");
		sb.append(" AND NOT (e.File_Message_A IS NOT NULL AND e.File_Message_B IS NOT NULL AND e.File_Message_C IS NOT NULL) ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = p.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE  ");
		sb.append(" p.DEALER_CODE ='"+entityCode+"' AND ( p.intent_level = 13101001 OR p.intent_level = 13101002 OR p.intent_level = 13101003 OR p.intent_level = 13101004 OR p.intent_level = 13101008 OR p.intent_level = 13101009)  ");
		sb.append(" AND p.REBUY_CUSTOMER_TYPE = 10541002 AND p.REPLACE_DATE > ' "+startDate+" ' AND p.REPLACE_DATE <= ' "+ endDate +" '  GROUP BY d.intent_series ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT d.intent_series series_code, 0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,COUNT(1) AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER FROM tm_potential_customer P  ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = p.dealer_code AND p.customer_no = i.customer_no AND i.intent_id = p.intent_id  ");
		sb.append(" INNER JOIN TT_CUSTOMER_VEHICLE_LIST e ON e.dealer_code = p.dealer_code AND e.customer_no = p.customer_no AND e.FILE_MESSAGE_B IS NOT NULL AND e.FILE_URLMESSAGE_B IS NOT NULL  AND e.FILE_URLMESSAGE_A IS NOT NULL  AND e.FILE_URLMESSAGE_C IS NOT NULL  AND e.FILE_MESSAGE_A IS NOT NULL  AND e.FILE_MESSAGE_C IS NOT NULL  ");
		sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code = p.dealer_code AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE  ");
		sb.append("  p.DEALER_CODE ='"+entityCode+"' AND (p.intent_level = 13101001 OR p.intent_level = 13101002 OR p.intent_level = 13101003 OR p.intent_level = 13101004 OR p.intent_level = 13101008 OR p.intent_level = 13101009)  ");
		sb.append(" AND p.REBUY_CUSTOMER_TYPE = 10541002 AND p.REPLACE_DATE > ' "+startDate+" ' AND p.REPLACE_DATE <= ' "+ endDate +" ' GROUP BY d.intent_series  ");
		sb.append(" UNION ALL  ");
		sb.append(" SELECT p.series_code series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,COUNT(1) AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER   ");
		sb.append(" FROM tt_sales_order s INNER JOIN tm_vs_product p  ON p.dealer_code = s.dealer_code  AND s.product_code = p.product_code WHERE S.sheet_CREATE_date > ' "+startDate+" '  ");
		sb.append(" AND  S.sheet_CREATE_date <= ' "+ endDate +" '  and s.DEALER_CODE='"+entityCode+"' AND s.BUSINESS_TYPE = 13001001  ");
		sb.append(" AND (s.so_status = 13011010 OR s.so_status = 13011015 OR s.so_status = 13011020 OR s.so_status = 13011025 OR s.so_status = 13011030 OR s.so_status = 13011035 OR s.so_status = 13011075) GROUP BY p.series_code  ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT p.series_code series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,COUNT(1) AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER  ");
		sb.append(" FROM tt_sales_order s INNER JOIN tm_vs_product p ON p.dealer_code = s.dealer_code  AND s.product_code = p.product_code WHERE S.PERMUTED_DATE > ' "+startDate+" '  AND S.PERMUTED_DATE <= ' "+ endDate +" '   and s.DEALER_CODE='"+entityCode+"' AND s.BUSINESS_TYPE = 13001001  ");
		sb.append(" AND (s.so_status = 13011010 OR s.so_status = 13011015 OR s.so_status = 13011020 OR s.so_status = 13011025 OR s.so_status = 13011030 OR s.so_status = 13011035 OR s.so_status = 13011075 )  ");
		sb.append(" AND S.PERMUTED_VIN IS NOT NULL GROUP BY p.series_code  ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT p.series_code series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,COUNT(1) AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,0 AS LCR_ORDER FROM tt_sales_order s  INNER JOIN tm_vs_product p  ON p.dealer_code = s.dealer_code  AND s.product_code = p.product_code  ");
		sb.append(" WHERE S.PERMUTED_DATE > ' "+startDate+" ' AND S.PERMUTED_DATE <= ' "+ endDate +" '  and s.DEALER_CODE='"+entityCode+"' AND s.BUSINESS_TYPE = 13001001  ");
		sb.append(" AND (s.so_status = 13011010 OR s.so_status = 13011015 OR s.so_status = 13011020 OR s.so_status = 13011025 OR s.so_status = 13011030 OR s.so_status = 13011035  OR s.so_status = 13011075)  ");
		sb.append(" AND S.PERMUTED_VIN IS NOT NULL AND s.FILE_OLD_A IS NOT NULL AND S.FILE_URLOLD_A IS NOT NULL GROUP BY p.series_code UNION ALL  ");
		sb.append(" SELECT d.intent_series series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,COUNT(DISTINCT p.customer_no) AS test_drive,0 AS scan_num,0 AS LCR_ORDER  ");
		sb.append(" FROM TT_SALES_PROMOTION_PLAN p INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code = p.dealer_code AND p.customer_no = i.customer_no AND i.intent_id = p.intent_id  INNER JOIN TT_CUSTOMER_INTENT_DETAIL d  ON d.dealer_code = p.dealer_code  ");
		sb.append(" AND d.INTENT_ID = i.INTENT_ID AND d.IS_MAIN_MODEL = 12781001 WHERE p.ACTION_DATE > ' "+startDate+" ' AND p.ACTION_DATE <= ' "+ endDate +" ' AND p.REAL_VISIT_ACTION LIKE '%试乘试驾%'  and p.DEALER_CODE='"+entityCode+"'  AND NOT EXISTS  ");
		sb.append(" (SELECT 1 FROM TT_SALES_PROMOTION_PLAN pt  WHERE pt.customer_no = p.customer_no AND pt.dealer_code = p.dealer_code  AND pt.REAL_VISIT_ACTION LIKE '%试乘试驾%'  AND pt.ACTION_DATE < ' "+startDate+" ' )  GROUP BY d.intent_series  ");
		sb.append(" UNION ALL  ");
		sb.append(" SELECT  tv.SERIES AS series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,COUNT(1) AS scan_num,0 AS LCR_ORDER  FROM TT_SO_INVOICE ts INNER JOIN tm_vehicle tv  ");
		sb.append(" ON ts.dealer_code = tv.dealer_code AND ts.vin = tv.vin WHERE ts.DEALER_CODE='"+entityCode+"' AND  ts.RECORD_DATE >= ' "+startDate+" ' AND ts.RECORD_DATE <= ' "+ endDate +" ' GROUP BY tv.SERIES  ");
		sb.append(" UNION ALL ");
		sb.append(" SELECT tv.SERIES_CODE AS series_code,0 AS walk_Found,0 AS hot_sales_leads,0 AS dcc_of_hot,0 AS HSL_REPLACE,0 AS HSL_SUMREPLACE,0 AS sales_orders,0 AS SALES_REPLACE,0 AS SALES_SUMREPLACE,0 AS conversion_ratio,0 AS test_drive,0 AS scan_num,COUNT(1) AS LCR_ORDER FROM tt_sales_order ts  ");
		sb.append(" INNER JOIN tm_vs_product tv ON ts.dealer_code = tv.dealer_code AND ts.product_code = tv.product_code WHERE  ts.DEALER_CODE='"+entityCode+"' AND");
		sb.append("  ts.CREATED_AT <=  ' "+ endDate +" '  AND (ts.so_status = 13011010 OR ts.SO_STATUS = 13011015 OR ts.SO_STATUS = 13011020 OR ts.SO_STATUS = 13011025) GROUP BY tv.SERIES_CODE)  a ");
		sb.append(" WHERE series_code IN (SELECT series_code FROM tm_series WHERE oem_tag = 12781001 AND is_valid = 12781001 ) GROUP BY series_code  ");
		List<Map> list = Base.findAll(sb.toString());
		System.out.println("*************************");
		System.out.println(sb.toString());
		System.out.println("*************************");
		return list;
	}
	
	public  String getDefaultValuePre(String itemCode) {
		String str = "";
		StringBuffer sb = new StringBuffer("SELECT * FROM TM_DEFAULT_PARA WHERE ITEM_CODE = '"+itemCode+"'");
 		List<Map> list3 = Base.findAll(sb.toString());
 		str = (String) list3.get(0).get("DEFAULT_VALUE");
		return str;
	}
	
}


