/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.schedule.domains.DTO.SADMS010Dto;

/**
 * 每周上报展厅报表（展厅周报）
 * @author Administrator
 *@date 2017年2月28日
 */

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SADMS010ServiceImpl implements SADMS010Service {
	
	
	@Override
	public LinkedList<SADMS010Dto> getSADMS010() {
		
		Date date = new Date();
	    Calendar calm = Calendar.getInstance();
		calm.setTime(date);
		String endDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + " 23:59:59";
		calm.add(Calendar.DATE,-7);
		String beginDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+ " 23:59:59";
	    String seriesCode=null;// 车系代码
	    int callIn=0;
		int walkIn=0;
		int noOfSc=0;
		int walkFound=0;
		int hotSalesLeads=0;
		int salesOrders=0;
		Double conversionRatio=0.00;
		int testDrive=0;
		int hslReplace=0;
		int salesReplace=0;
		int dccOfHot=0;
		int hslSumreplace=0;
		int salesSumreplace=0;
		LinkedList<SADMS010Dto> resultList = new LinkedList<SADMS010Dto>();
		List<Map> listNoseries = new ArrayList();// 不分车系的三个值 callIn walkIn noOfSc
		listNoseries = this.querySubmitReportNoseries(beginDate,endDate);
		System.out.println("-----------------010----------------");
		if (listNoseries!=null && listNoseries.size()>=2){
			  Map bean = listNoseries.get(0);
			  Number num = (Number) bean.get( "SUM"); 
			  callIn=num.intValue();
			  bean =  listNoseries.get(1);
			  Number num1 = (Number) bean.get( "SUM"); 
			  walkIn=num1.intValue();
			  bean =  listNoseries.get(2);
			  Number num2 = (Number) bean.get( "SUM");
			  noOfSc=num2.intValue();
			}
		List<Map> listSeries = new ArrayList();
		listSeries = this.querySubmitReport(beginDate,endDate);
		
		if (listSeries!=null && listSeries.size()>0){
			for (int i = 0; i < listSeries.size(); i++){
				  Map  bean =  listSeries.get(i);
				  seriesCode = (String)bean.get("SERIES_CODE");
				  walkFound=Integer.parseInt(bean.get( "WALK_FOUND").toString());
				  hotSalesLeads=Integer.parseInt(bean.get( "HOT_SALES_LEADS").toString());
				  salesOrders=Integer.parseInt(bean.get( "SALES_ORDERS").toString());
				  conversionRatio=Double.parseDouble(bean.get( "CONVERSION_RATIO").toString());
				  testDrive=Integer.parseInt(bean.get( "TEST_DRIVE").toString());
				  hslReplace=Integer.parseInt(bean.get( "HSL_REPLACE").toString());
				  salesReplace=Integer.parseInt(bean.get( "SALES_REPLACE").toString());
				  dccOfHot=Integer.parseInt(bean.get( "DCC_OF_HOT").toString());
				  hslSumreplace=Integer.parseInt(bean.get("HSL_SUMREPLACE").toString());
				  salesSumreplace=Integer.parseInt(bean.get("SALES_SUMREPLACE").toString());
				 
				  SADMS010Dto vo= new SADMS010Dto();
				  vo.setCallIn(callIn);
				  vo.setWalkIn(walkIn);
				  vo.setNoOfSc(noOfSc);
				  
				  vo.setSeriesCode(seriesCode);
				  vo.setWalkFound(walkFound);//有效客流
				  vo.setHotSalesLeads(hotSalesLeads);//hsl
				  vo.setSalesOrders(salesOrders);//订单数量
				  vo.setConversionRatio(conversionRatio);
				  vo.setTestDrive(testDrive);//试乘试驾数
				  vo.setHslSumreplace(hslSumreplace);//置换意向客户数
				  vo.setSalesSumreplace(salesSumreplace);//置换成交数
				  vo.setHslReplace(hslReplace);
				  vo.setSalesReplace(salesReplace);
				  vo.setCurrentDate(date);//当前的时间也传上去
				  vo.setDccOfHot(dccOfHot);//HSL DCC转入的潜客数量
				  resultList.add(vo);
			}
		}else {
			SADMS010Dto vo= new SADMS010Dto();
			  vo.setCallIn(callIn);
			  vo.setWalkIn(walkIn);
			  vo.setNoOfSc(noOfSc);
			  
			  vo.setSeriesCode(seriesCode);
			  vo.setWalkFound(walkFound);
			  vo.setHotSalesLeads(hotSalesLeads);
			  vo.setSalesOrders(salesOrders);
			  vo.setConversionRatio(conversionRatio);
			  vo.setTestDrive(testDrive);
			  vo.setHslReplace(hslReplace);
			  vo.setSalesReplace(salesReplace);
			  vo.setHslSumreplace(hslSumreplace);//置换意向客户数
			  vo.setSalesSumreplace(salesSumreplace);//置换成交数
			  vo.setCurrentDate(date);//当前的时间也传上去
			  vo.setDccOfHot(dccOfHot);//HSL DCC转入的潜客数量
			  resultList.add(vo);
		}
		
		return resultList;
	}

	// lim  查询DMS数据 每周上报到上端
	
	@SuppressWarnings("unused")
	public List<Map> querySubmitReportNoseries(String beginDate, String endDate) {
		StringBuffer sql = new StringBuffer("");
		   sql.append( " select count(*) as SUM  from  TT_VISITING_RECORD where  IS_VALID=12781001" );
			sql.append(" and VISIT_TYPE="+DictCodeConstants.DICT_VISIT_TYPE_BY_CALL+" and VISIT_TIME>'"+beginDate+"' and VISIT_TIME<='"+endDate+"'" );
			sql.append(" union all " );
			sql.append(" select count(*) as SUM  from  TT_VISITING_RECORD  where    IS_VALID=12781001" );
			sql.append(" and (VISIT_TYPE!="+DictCodeConstants.DICT_VISIT_TYPE_BY_CALL+" OR VISIT_TYPE IS NULL ) and " );
			sql.append(" VISIT_TIME>'"+beginDate+"' and VISIT_TIME<='"+endDate+"'" );
			sql.append(" union all " );
			sql.append(" select count(*)  as SUM  from tm_user where  is_consultant="+DictCodeConstants.DICT_IS_YES );
			sql.append(" and user_status="+DictCodeConstants.DICT_IN_USE_START );
		 	List<Object> queryList = new  ArrayList<Object>();
	        List<Map> result = Base.findAll(sql.toString());
	        return result;
	}

	
	
	public List<Map> querySubmitReport(String beginDate, String endDate) {
		StringBuffer sql = new StringBuffer("");
		sql.append( "select series_code,(sum(walk_Found))  walk_found,sum(hot_sales_leads) hot_sales_leads,sum(dcc_of_hot) dcc_of_hot,sum(HSL_REPLACE) HSL_REPLACE,sum(HSL_SUMREPLACE) HSL_SUMREPLACE,sum(sales_orders) sales_orders,sum(SALES_REPLACE) SALES_REPLACE,sum(SALES_SUMREPLACE) SALES_SUMREPLACE," );
		sql.append(" (sum(sales_orders)*1.00/(case when sum(hot_sales_leads)=0 then 1 else sum(hot_sales_leads) end))as conversion_ratio,sum(test_drive)" );
		sql.append(" test_drive" );
		sql.append(" from (" );
		sql.append(" select  d.intent_series series_code, count(p.customer_no) walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio," );
		sql.append(" 0 as test_drive from  tm_potential_customer P " );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001" );
		sql.append(" inner join  (" );
		sql.append(" select v2.dealer_code,v2.customer_no,v2.IS_VALID,v2.VISIT_TYPE,v2.VISIT_TIME from" );
		sql.append(" (SELECT  IF(@pdept = CONCAT(CONCAT(b.dealer_code, b.customer_no )),@rank := @rank + 1,@rank := 1) AS snid," );
		sql.append(" b.*,@pdept := CONCAT(b.dealer_code, b.customer_no )," );
		sql.append(" @rownum := @rownum + 1" );
		sql.append(" FROM" );
		sql.append(" (SELECT  v1.dealer_code, v1.customer_no, v1.IS_VALID, v1.VISIT_TYPE, v1.VISIT_TIME" );
		sql.append(" FROM TT_VISITING_RECORD v1 " );
		sql.append(" INNER JOIN tm_potential_customer v11 " );
		sql.append(" ON v1.dealer_code = v11.dealer_code " );
		sql.append(" AND v1.customer_no = v11.customer_no " );
		sql.append(" AND v1.IS_VALID = 12781001 " );
		sql.append(" AND (" );
		sql.append("  v1.VISIT_TYPE != 13091001 " );
		sql.append(" OR v1.VISIT_TYPE IS NULL" );
		sql.append(" ) " );
		sql.append(" AND v1.VISIT_TIME > '"+beginDate+"' " );
		sql.append(" AND v1.VISIT_TIME <= '"+endDate+"' ORDER BY CONCAT(v1.dealer_code," );
		sql.append(" v1.customer_no ) ASC, v1.VISIT_TIME DESC) b, " );
		sql.append(" (SELECT @rownum := 0,@pdept := NULL,@rank := 0) a ) v2 where v2.SNID=1" );
		sql.append(" ) v on v.dealer_code =p.dealer_code  and v.customer_no=p.customer_no and " );
		sql.append(" v.IS_VALID=12781001 and (v.VISIT_TYPE!=13091001 or v.VISIT_TYPE is null )" );
		sql.append(" and v.VISIT_TIME>'"+beginDate+"' and v.VISIT_TIME<='"+endDate+"'" );
		sql.append("  group by d.intent_series" );
		sql.append(" union all " );
		sql.append(" select  d.intent_series series_code,0 as walk_Found ,count(1) as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio," );
		sql.append(" 0 as test_drive from  tm_potential_customer P " );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		sql.append(" where  (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 ");
		sql.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009)" );
		sql.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"'" );
		sql.append(" group by d.intent_series " );
		sql.append(" union all " );
		sql.append(" select  d.intent_series series_code,0 as walk_Found ,0 as hot_sales_leads,count(1) as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio,");
		sql.append(" 0 as test_drive from  tm_potential_customer P " );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		sql.append(" where  (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
		sql.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.CUS_SOURCE = "+ DictCodeConstants.DICT_CUS_SOURCE_BY_DCC +" " );
		sql.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"'" );
		sql.append(" group by d.intent_series " );
		sql.append(" union all " );//下面是新增加的 置换的潜客数
		sql.append(" select  d.intent_series series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,count(1) as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio,");
		sql.append(" 0 as test_drive from  tm_potential_customer P " );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.dealer_code=p.dealer_code and e.customer_no=p.customer_no  AND  not (e.File_Message_A is not null and e.File_Message_B is not null and e.File_Message_C is not null)" );
		sql.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		sql.append(" where  (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
		sql.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
		sql.append(" and p.REPLACE_DATE>'"+beginDate+"' and p.REPLACE_DATE<='"+endDate+"'" );
		sql.append(" group by d.intent_series" );
		sql.append(" union all " );//下面是 置换意向客户数
		sql.append(" select  d.intent_series series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,count(1) as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio," );
		sql.append(" 0 as test_drive from  tm_potential_customer P " );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(" inner join  TT_CUSTOMER_VEHICLE_LIST e on e.dealer_code=p.dealer_code and e.customer_no=p.customer_no  and e.FILE_MESSAGE_B is not null and e.FILE_URLMESSAGE_B is not null" );
		sql.append(" and  e.FILE_URLMESSAGE_A is not null and e.FILE_URLMESSAGE_C is not null and e.FILE_MESSAGE_A is not null and e.FILE_MESSAGE_C is not null" );
		sql.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001 " );
		sql.append(" where (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003 " );
		sql.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009) and p.REBUY_CUSTOMER_TYPE=10541002 " );
		sql.append(" and p.REPLACE_DATE>'"+beginDate+"' and p.REPLACE_DATE<='"+endDate+"' " ); 	
		sql.append(" group by d.intent_series");
		sql.append(" union all " );
		sql.append(" select  p.series_code series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,count(1) as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0" );
		sql.append(" as conversion_ratio,0 as test_drive " );
		sql.append(" from  tt_sales_order s" );
		sql.append(" inner join tm_vs_product p on p.dealer_code=s.dealer_code and s.product_code=p.product_code " );
		sql.append(" WHERE S.sheet_CREATE_DATE>'"+beginDate+"' and S.sheet_CREATE_DATE<='"+endDate+"'" );
		sql.append(" and  s.BUSINESS_TYPE=13001001 and " );
		sql.append(" (s.so_status=13011010 or s.so_status=13011015 or s.so_status=13011020 or s.so_status=13011025 or " );
		sql.append(" s.so_status=13011030 or s.so_status=13011035 or s.so_status=13011075 )" );
		sql.append(" group by p.series_code" );
		sql.append(" union all " );// 下面新增 置换订单数
		sql.append(" select  p.series_code series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,count(1) as SALES_REPLACE,0 as SALES_SUMREPLACE,0" );
		sql.append(" as conversion_ratio,0 as test_drive " );
		sql.append(" from  tt_sales_order s" );
		sql.append(" inner join tm_vs_product p on p.dealer_code=s.dealer_code and s.product_code=p.product_code " );
		sql.append(" WHERE S.PERMUTED_DATE>'"+beginDate+"' and S.PERMUTED_DATE<='"+endDate+"'" );
		sql.append(" and  s.BUSINESS_TYPE=13001001  " );
		//" and EXISTS (SELECT 1 FROM TT_SO_INVOICE D WHERE s.dealer_code=D.dealer_code AND s.SO_NO=D.SO_NO and d.INVOICE_CHARGE_TYPE="+DictDataConstant.DICT_INVOICE_FEE_VEHICLE+")" +
		sql.append(" and (s.so_status=13011010 or s.so_status=13011015 or s.so_status=13011020 or s.so_status=13011025 or " );
		sql.append(" s.so_status=13011030 or s.so_status=13011035 or s.so_status=13011075 )  AND S.PERMUTED_VIN IS NOT NULL " );
		sql.append(" group by p.series_code" );
		sql.append(" union all " );//下面是置换成交数
		sql.append(	" select  p.series_code series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,count(1) as SALES_SUMREPLACE,0" );
		sql.append(	" as conversion_ratio,0 as test_drive " );
		sql.append(	" from  tt_sales_order s" );
		sql.append(" inner join tm_vs_product p on p.dealer_code=s.dealer_code and s.product_code=p.product_code ");
		sql.append(	" WHERE S.PERMUTED_DATE>'"+beginDate+"' and S.PERMUTED_DATE<='"+endDate+"'" );
		sql.append(	" and  s.BUSINESS_TYPE=13001001  " );
		//" and EXISTS (SELECT 1 FROM TT_SO_INVOICE D WHERE s.dealer_code=D.dealer_code AND s.SO_NO=D.SO_NO and d.INVOICE_CHARGE_TYPE="+DictDataConstant.DICT_INVOICE_FEE_VEHICLE+")" +
		sql.append(" and (s.so_status=13011010 or s.so_status=13011015 or s.so_status=13011020 or s.so_status=13011025 or " );
		sql.append(	" s.so_status=13011030 or s.so_status=13011035 or s.so_status=13011075 )  AND S.PERMUTED_VIN IS NOT NULL and s.FILE_OLD_A IS NOT NULL AND S.FILE_URLOLD_A IS NOT NULL " );
		sql.append(	" group by p.series_code" );
		sql.append(" union all " );
		sql.append(	" select d.intent_series series_code,0 as walk_Found ,0 as hot_sales_leads,0 as dcc_of_hot,0 as HSL_REPLACE,0 as HSL_SUMREPLACE,0 as sales_orders,0 as SALES_REPLACE,0 as SALES_SUMREPLACE,0 as conversion_ratio,");
		sql.append(	" count(distinct p.customer_no) as test_drive from TT_SALES_PROMOTION_PLAN p" );
		sql.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id" );
		sql.append(	" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001");
		sql.append(" where p.ACTION_DATE>'"+beginDate+"' and p.ACTION_DATE<='"+endDate+"' and p.REAL_VISIT_ACTION like '%试乘试驾%'" );
		sql.append(" and  not exists (select 1 from TT_SALES_PROMOTION_PLAN pt where pt.customer_no=p.customer_no" );
		sql.append(	" and pt.dealer_code=p.dealer_code and pt.REAL_VISIT_ACTION like '%试乘试驾%'" );
		sql.append(" and pt.ACTION_DATE<'"+beginDate+"')" );
		sql.append(" group by d.intent_series" );
		sql.append(" ) ow where series_code in (select series_code from tm_series where oem_tag=12781001 and is_valid=12781001 ");
		sql.append(" ) group by series_code");
	        List<Map> result = Base.findAll(sql.toString());
	        return result;
	}

	
	
	

}
