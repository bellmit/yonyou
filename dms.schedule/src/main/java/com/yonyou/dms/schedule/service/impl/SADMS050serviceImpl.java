package com.yonyou.dms.schedule.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.task.common.SubmitCustomerToDcc;
/**
 * 二手车置换率月报周报 接口实现类
 * @author wangliang
 * @date 2017年3月15日
 */
@Service
@SuppressWarnings({ "rawtypes" })
public class SADMS050serviceImpl extends BaseService  implements SADMS050Service{
    private static final Logger logger = LoggerFactory.getLogger(SADMS050serviceImpl.class);
	@Override
	public LinkedList<SADCS050Dto> getSADMS050(String entityCode) throws ServiceBizException, ParseException {
	    try {
	        /******************************开启事物*********************/
	        logger.info("=========================开启SADMS050Service"+entityCode);
            logger.info("=========================开启SADMS050Service1"+entityCode);
	        
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        Calendar calm = Calendar.getInstance();
	        calm.setTime(date);
	        
	        int uploadYear = calm.get(Calendar.YEAR); //当前年份
	        int uploadMonth = (calm.get(Calendar.MONTH))+1; //当前月份
	        if(uploadMonth==1){
	            uploadYear = uploadYear-1;
	            uploadMonth = 12;
	        }else{
	            uploadMonth = uploadMonth-1;
	        }
	        Calendar  calmLast = Calendar.getInstance();
	        calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMinimum(Calendar.DAY_OF_MONTH));
	        String strIsEndMonth = CommonConstants.DICT_IS_NO;
	        String strIsEndWeek = CommonConstants.DICT_IS_NO;
	        if(!format.format(calmLast.getTime()).equals(format.format(calm.getTime()))) {
	            if(calm.get(Calendar.DAY_OF_WEEK)==6) { //2是周一
	                strIsEndWeek = CommonConstants.DICT_IS_YES;
	            }
	        } else{
	            if(calm.get(Calendar.DAY_OF_WEEK)==6) {
	                strIsEndWeek = CommonConstants.DICT_IS_YES;
	            }
	            strIsEndMonth = CommonConstants.DICT_IS_YES;
	        }
	        calm.add(Calendar.DATE, -1);
	        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+"23:59:59";
	        
	        calm.add(Calendar.DATE, -7);
	        String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+" 00:00:00";
	        logger.info("=========================开启SADMS050Service2");
	        LinkedList<SADCS050Dto> resultList = new LinkedList<SADCS050Dto>();
	        if(strIsEndMonth != null && strIsEndMonth.equals(CommonConstants.DICT_IS_YES)) {
	            System.out.println("每个月第一天执行此计划任务 并上报总部");
	            List<Map> partStockList = QuerySeriesUsedCarMonthReport(uploadMonth,uploadYear,entityCode);
	           
	            SADCS050Dto dto = null;
	            if(partStockList != null && partStockList.size()> 0){
	                for(int i=0;i<=partStockList.size();i++) {
	                    Map map = partStockList.get(i-1);
	                    String seriesCode = map.get("SERIES_CODE").toString();
	                    String dealerCode = map.get("DEALER_CODE").toString();
	                    int hslHabod = Integer.parseInt(map.get("HSL_HABOD").toString());
	                    int potentialCustomersNum = Integer.parseInt(map.get("POTENTIAL_CUSTOMERS_NUM").toString());
	                    int intentionNum = Integer.parseInt(map.get("INTENTION_NUM").toString());
	                    int saleNum = Integer.parseInt(map.get("SALE_NUM").toString());
	                    int dealNum = Integer.parseInt(map.get("DEAL_NUM").toString());
	                    int reportType = 1;
	                    Date reportDate = format.parse(format.format(new Date()));
	                    double intentionRatio = Double.parseDouble(map.get("INTENTION_RATIO").toString());
	                    double dealRatio = Double.parseDouble(map.get("DEAL_RATIO").toString());
	                    double conversionRatio = Double.parseDouble(map.get("CONVERSION_RATIO").toString());
	                    
	                    dto = new SADCS050Dto();
	                    if(intentionNum == 0) {
	                        dto.setConversionRatio((double)0);
	                    }else{
	                        dto.setConversionRatio(conversionRatio);
	                    }
	                    
	                    if(saleNum == 0){
	                        dto.setDealRatio((double)0);
	                    }else{
	                        dto.setDealRatio(dealRatio);
	                    }
	                    if(hslHabod == 0){
	                        dto.setIntentionRatio((double)0);
	                    }else{
	                        dto.setIntentionRatio(intentionRatio);
	                    }
	                    dto.setDealNum(dealNum);
	                    //是否存DealerCode
	                    dto.setDealerCode(dealerCode);
	                    dto.setHslHabod(hslHabod);
	                    dto.setIntentionNum(intentionNum);
	                    
	                    dto.setPotentialCustomersNum(potentialCustomersNum);
	                    dto.setReportDate(reportDate);
	                    dto.setReportType(reportType);
	                    dto.setSeriesCode(seriesCode);
	                    
	                    resultList.add(dto);
	                }
	            }
	            //return resultList;
	        }
	        
	        //================计划任务执行当天 是否是周一 ================
	        if(strIsEndWeek != null && strIsEndWeek.equals(CommonConstants.DICT_IS_YES)) {
	            //每周一上报
	            System.out.println("每zhpu第一天执行此计划任务 并上报总部");
	            List<Map> partStockList = QuerySeriesUsedCarWeekReport(beginDate,endDate,entityCode);;
	           
	            SADCS050Dto dto = null;
	            if(partStockList != null && partStockList.size() > 0) {
	                for(int i= 1;i<= partStockList.size();i++) {
	                    Map map = partStockList.get(i);
	                    String seriesCode = map.get("SERIES_CODE").toString();
	                    String dealerCode = map.get("DEALER_CODE").toString();
	                    int hslHabod = Integer.parseInt(map.get("HSL_HABOD").toString());
	                    int potentialCustomersNum = Integer.parseInt(map.get("POTENTIAL_CUSTOMERS_NUM").toString());
	                    int intentionNum = Integer.parseInt(map.get("INTENTION_NUM").toString());
	                    int saleNum = Integer.parseInt(map.get("SALE_NUM").toString());
	                    int dealNum = Integer.parseInt(map.get("DEAL_NUM").toString());
	                    int reportType = 1;
	                    Date reportDate = format.parse(format.format(new Date()));
	                    double intentionRatio = Double.parseDouble(map.get("INTENTION_RATIO").toString());
	                    double dealRatio = Double.parseDouble(map.get("DEAL_RATIO").toString());
	                    double conversionRatio = Double.parseDouble(map.get("CONVERSION_RATIO").toString());
	                    dto = new SADCS050Dto();
	                    if(intentionNum == 0) {
	                        dto.setConversionRatio((double)0);
	                    }else{
	                        dto.setConversionRatio(conversionRatio);
	                    }
	                    if(saleNum == 0){
	                        dto.setDealRatio((double)0);
	                    }else{
	                        dto.setDealRatio(dealRatio);
	                    }
	                    if(hslHabod == 0){
	                        dto.setIntentionRatio((double)0);
	                    }else{
	                        dto.setIntentionRatio(intentionRatio);
	                    }
	                    dto.setDealNum(dealNum);
	                    //是否存DealerCode
	                    dto.setDealerCode(dealerCode);
	                    dto.setHslHabod(hslHabod);
	                    dto.setIntentionNum(intentionNum);
	                    
	                    dto.setPotentialCustomersNum(potentialCustomersNum);
	                    dto.setReportDate(reportDate);
	                    dto.setReportType(reportType);
	                    dto.setReportType(2);
	                    dto.setSeriesCode(seriesCode);
	                    resultList.add(dto);
	                }
	            }
	            //return resultList;
	        }
	        dbService.endTxn(true);
            /******************************结束事物*********************/
	       
	        return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e); 
            return null;
        }finally{
            Base.detach();
            dbService.clean();
            beginDbService();
        }
	   
	}

	private List<Map> QuerySeriesUsedCarWeekReport(String beginDate, String endDate,String entityCode) {
		         StringBuffer sb= new StringBuffer();
		         sb.append(" select dealer_code,series_code,sum(hsl_habod) hsl_habod,sum(potential_customers_num) potential_customers_num,sum(intention_num) intention_num,sum(deal_num) deal_num, " );
		    	 sb.append(" CAST((ROUND(COALESCE(CAST(sum(deal_num) AS UNSIGNED), 0)/(CASE WHEN sum(intention_num)=0 THEN 1 ELSE sum(intention_num) END),4)*100) AS DECIMAL(20,2)) as conversion_ratio, ");   
			     sb.append(" CAST((ROUND(COALESCE(CAST(sum(deal_num) AS UNSIGNED), 0)/(CASE WHEN sum(sale_num)=0 THEN 1 ELSE sum(sale_num) END),4)*100) AS DECIMAL(20,2)) as deal_ratio, ");    
			     sb.append(" CAST((ROUND(COALESCE(CAST(sum(intention_num) AS UNSIGNED), 0)/(CASE WHEN sum(hsl_habod)=0 THEN 1 ELSE sum(hsl_habod) END),4)*100) AS DECIMAL(20,2)) as intention_Ratio, ");     
		    	 sb.append(" sum(sale_num) as sale_num ");
		    	 sb.append(" from ( " );
		    	 sb.append(" select p.dealer_code ,d.intent_series series_code,count(1) as hsl_habod,0 as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );	
		    	 sb.append(" 0 as sale_num from  tm_potential_customer P " );
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id ");
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 ");
		    	 sb.append(" where  p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 ");
		    	 sb.append(" or p.intent_level=13101008 or p.intent_level=13101009) " );
		    	 sb.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"'  " );
		    	 sb.append(" group by d.intent_series " );
		    	 sb.append(" union all " );//下面是新增加的 置换的潜客数
		    	 sb.append(" select p.dealer_code, d.intent_series series_code,0 as hsl_habod,count(1) as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
		    	 sb.append(" 0 as sale_num from  tm_potential_customer P  " );
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id " );
		    	 sb.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.DEALER_CODE=p.DEALER_CODE and e.customer_no=p.customer_no AND not (e.File_Message_A is not null and e.File_Message_B is not null and e.File_Message_C is not null) " );
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		    	 sb.append(" where  p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p. intent_level=13101002 or p.intent_level=13101003 " );
		    	 sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
		    	 sb.append(" and p.REPLACE_DATE>'"+beginDate+"' and p.REPLACE_DATE<='"+endDate+"' " );
		    	 sb.append(" group by d.intent_series " );
		    	 sb.append(" union all " );//下面是 置换意向客户数
		    	 sb.append(" select  p.dealer_code,d.intent_series series_code,0 as hsl_habod,0 as potential_customers_num,count(1) as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
		    	 sb.append(" 0 as sale_num from  tm_potential_customer P " );
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id " );
		    	 sb.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.DEALER_CODE=p.DEALER_CODE and e.customer_no=p.customer_no  and e.FILE_MESSAGE_B is not null and e.FILE_URLMESSAGE_B is not null " );
		    	 sb.append(" and  e.FILE_URLMESSAGE_A is not null and e.FILE_URLMESSAGE_C is not null and e.FILE_MESSAGE_A is not null and e.FILE_MESSAGE_C is not null " );
		    	 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		    	 sb.append(" where   p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
		    	 sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
		    	 sb.append(" and REPLACE_DATE>'"+beginDate+"' and p.REPLACE_DATE<='"+endDate+"' " ); 	
		    	 sb.append(" group by d.intent_series " );
		    	 sb.append(" union all " );//下面是置换成交数
		    	 sb.append(" select  p.dealer_code, p.series_code series_code,0 as hsl_habod,0 as potential_customers_num,0 as intention_num,count(1) as deal_num,0 " );
		    	 sb.append(" as conversion_ratio,0 as deal_ratio,0 as intention_ratio,0 as sale_num " );
		    	 sb.append(" from  tt_sales_order s " );
		    	 sb.append(" inner join tm_vs_product p on p.DEALER_CODE=s.DEALER_CODE and s.product_code=p.product_code " );
		    	 sb.append(" WHERE S.PERMUTED_DATE>'"+beginDate+"' and S.PERMUTED_DATE<='"+endDate+"' " );
		    	 sb.append("  and s.dealer_code ='"+entityCode+"' and s.BUSINESS_TYPE=13001001  " );
		    	 sb.append(" AND S.PERMUTED_VIN IS NOT NULL and s.FILE_OLD_A IS NOT NULL AND S.FILE_URLOLD_A IS NOT NULL " );
		    	 sb.append(" group by p.series_code " );
		    	 sb.append(" union all " );//开票登记数
		    	 sb.append(" select ts.dealer_code,tv.SERIES as series_code,0 as hsl_habod,0 as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
		    	 sb.append(" count(1) as scan_num  from TT_SO_INVOICE ts inner join tm_vehicle tv on ts.DEALER_CODE=tv.DEALER_CODE and ts.vin=tv.vin  " );
		    	 sb.append(" where   ts.dealer_code ='"+entityCode+"'  and ts.RECORD_DATE>'"+beginDate+"' and ts.RECORD_DATE<='"+endDate+"' " );
		    	 sb.append(" group by tv.SERIES) as a ");
		    	 sb.append("  where series_code in (select series_code from tm_series where oem_tag=12781001 and is_valid=12781001 " );
		    	 sb.append("  ) group by series_code ");
		 		 List<Map> list2 = Base.findAll(sb.toString());
		 		 return list2;
	}

	private List<Map> QuerySeriesUsedCarMonthReport(int month, int year,String entityCode) {
		 	StringBuffer sb = new StringBuffer();
		 	 sb.append("  select dealer_code,series_code,sum(hsl_habod) hsl_habod,sum(potential_customers_num) potential_customers_num,sum(intention_num) intention_num,sum(deal_num) deal_num, " );
			 sb.append("  CAST((ROUND(COALESCE(CAST(sum(deal_num) AS UNSIGNED), 0)/(CASE WHEN sum(intention_num)=0 THEN 1 ELSE sum(intention_num) END),4)*100) AS DECIMAL(20,2)) as conversion_ratio, " );
			 sb.append("  CAST((ROUND(COALESCE(CAST(sum(deal_num) AS UNSIGNED), 0)/(CASE WHEN sum(sale_num)=0 THEN 1 ELSE sum(sale_num) END),4)*100) AS DECIMAL(20,2)) as deal_ratio, ");
			 sb.append("  CAST((ROUND(COALESCE(CAST(sum(intention_num) AS UNSIGNED), 0)/(CASE WHEN sum(hsl_habod)=0 THEN 1 ELSE sum(hsl_habod) END),4)*100) AS DECIMAL(20,2)) as intention_Ratio, ");
			 sb.append(" sum(sale_num) as sale_num " );
			 sb.append(" from ( " );
			 sb.append(" select  p.dealer_code,d.intent_series series_code,count(1) as hsl_habod,0 as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
			 sb.append(" 0 as sale_num from  tm_potential_customer P " );
			 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id " );
			 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
			 sb.append(" where   p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
			 sb.append(" or p.intent_level=13101008 or p.intent_level=13101009) " );
			 sb.append(" and month(p.FOUND_DATE)='"+month+"' and year(p.FOUND_DATE)='"+year+"'  " );
			 sb.append(" group by d.intent_series " );
			 sb.append(" union all " );//下面是新增加的 置换的潜客数
			 sb.append(" select  p.dealer_code,d.intent_series series_code,0 as hsl_habod,count(1) as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
			 sb.append(" 0 as sale_num from  tm_potential_customer P  " );
			 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id " );
			 sb.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.DEALER_CODE=p.DEALER_CODE and e.customer_no=p.customer_no AND not (e.File_Message_A is not null and e.File_Message_B is not null and e.File_Message_C is not null) " ); 
			 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
			 sb.append(" where   p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
			 sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
			 sb.append(" and month(p.REPLACE_DATE)='"+month+"' and year(p.REPLACE_DATE)='"+year+"' " );
			 sb.append(" group by d.intent_series " );
			 sb.append(" union all " );//下面是 置换意向客户数
			 sb.append(" select  p.dealer_code,d.intent_series series_code,0 as hsl_habod,0 as potential_customers_num,count(1) as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
			 sb.append(" 0 as sale_num from  tm_potential_customer P " );
			 sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.DEALER_CODE=p.DEALER_CODE and p.customer_no=i.customer_no and i.intent_id=p.intent_id " );
			 sb.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.DEALER_CODE=p.DEALER_CODE and e.customer_no=p.customer_no  and e.FILE_MESSAGE_B is not null and e.FILE_URLMESSAGE_B is not null " );
			 sb.append(" and  e.FILE_URLMESSAGE_A is not null and e.FILE_URLMESSAGE_C is not null and e.FILE_MESSAGE_A is not null and e.FILE_MESSAGE_C is not null " );
			 sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.DEALER_CODE=p.DEALER_CODE and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
			 sb.append(" where  p.dealer_code ='"+entityCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
			 sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
			 sb.append(" and month(REPLACE_DATE)='"+month+"' and year(p.REPLACE_DATE)='"+year+"' " ); 	
			 sb.append(" group by d.intent_series " );
			 sb.append(" union all " );//下面是置换成交数
			 sb.append(" select  p.dealer_code,p.series_code series_code,0 as hsl_habod,0 as potential_customers_num,0 as intention_num,count(1) as deal_num,0 " );
			 sb.append(" as conversion_ratio,0 as deal_ratio,0 as intention_ratio,0 as sale_num " );
			 sb.append(" from  tt_sales_order s " );
			 sb.append(" inner join tm_vs_product p on p.DEALER_CODE=s.DEALER_CODE and s.product_code=p.product_code " );
			 sb.append(" WHERE month(S.PERMUTED_DATE)='"+month+"' and year(S.PERMUTED_DATE)='"+year+"' " );
			 sb.append("  s.dealer_code ='"+entityCode+"'  and and s.BUSINESS_TYPE=13001001  " );
			 sb.append(" AND S.PERMUTED_VIN IS NOT NULL and s.FILE_OLD_A IS NOT NULL AND S.FILE_URLOLD_A IS NOT NULL " );
			 sb.append(" group by p.series_code " );
			 sb.append(" union all " );//开票登记数
			 sb.append(" select ts.dealer_code,tv.SERIES as series_code,0 as hsl_habod,0 as potential_customers_num,0 as intention_num,0 as deal_num,0 as conversion_ratio,0 as deal_ratio,0 as intention_ratio, " );
			 sb.append(" count(1) as scan_num  from TT_SO_INVOICE ts inner join tm_vehicle tv on ts.DEALER_CODE=tv.DEALER_CODE and ts.vin=tv.vin  " );
			 sb.append("  where    ts.dealer_code ='"+entityCode+"'  and month(ts.RECORD_DATE)='"+month+"' and year(ts.RECORD_DATE)='"+year+"' " );
			 sb.append("  group by tv.SERIES) AS a ");
			 sb.append("  where series_code in (select series_code from tm_series where oem_tag=12781001 and is_valid=12781001 " );
			 sb.append("  ) group by series_code ");
		 	List<Map> list = Base.findAll(sb.toString());
		return list;
	}
	
}
