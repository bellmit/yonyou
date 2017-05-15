package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.report.domains.DTO.OutBoundReportDTO;

@Service
@SuppressWarnings("rawtypes")
public class OutBoundReportServiceImpl implements OutBoundReportService{
    @Autowired
    private OperateLogService operateLogService;
    
    @Override
    public PageInfoDto searchOutBoundReport(Map<String, String> param)throws ServiceBizException{
    	String soldBy = param.get("soldBy");
    	String yearMonth = param.get("yearMonth");
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgcode = FrameworkUtil.getLoginInfo().getOrgCode();
		String functionCode= "55604000";//客流HSL趋势分析
    	
    	int index = yearMonth.indexOf("-");
    	String year = yearMonth.substring(0,index);
    	String month = yearMonth.substring(index+1, yearMonth.length());
		Calendar date = Calendar.getInstance();
		int dateDay = date.get(Calendar.DAY_OF_MONTH);
		int nYear = Integer.parseInt(year);
		int nMonth = Integer.parseInt(month);
		 StringBuilder sql=new StringBuilder();
		 List<Map> cusList = this.isHave(nYear,nMonth);
			if(cusList.size()>0){
		          //不是查询当前月的 则 从固化的 表中 查询数据
                sql.append(" select tb.DEALER_CODE,tb.USER_ID as SOLD_BY ,tb.USER_NAME as EMPLOYEE_NAME , tb.CONFIRM_NUM ,tb.SUCCESS_NUM ,tb.FAIL_NUM,tb.WX_BINDING_NUM,tb.BINDING_RATE,tb.IS_PASS, ");
                sql.append(" tb.NEED_SECOND_UPLOAD as NEED_SECOND_UOLOAD,tb.PASS_RATE , tb.PAMS,tb.SIX_MONTH_FAIL_NUM , tb.OVER_TIME_NUM as OVER_TIME, te.MOBILE,te.PHONE  ");
                sql.append(" from tm_out_bound_month_report tb left join tm_user tu on tb.DEALER_CODE=tu.DEALER_CODE ");
                sql.append(" and tb.user_id=tu.user_id left join  tm_employee te on tu.DEALER_CODE=te.DEALER_CODE and ");
                sql.append(" tu.employee_no=te.employee_no  where tb.DEALER_CODE ='"+dealerCode+"'  and YEAR(DATE_SUB(tb.created_at, INTERVAL  1 MONTH ))="+nYear+" and MONTH(DATE_SUB(tb.created_at, INTERVAL  1 MONTH ))="+nMonth+"   ");
                if(!StringUtils.isNullOrEmpty(soldBy)){
                    sql.append(" and tb.user_id="+soldBy+" ");              
                }else{
                    if(userid != null){
                        sql.append(DAOUtilGF.getFunRangeByStr("tb", "user_id", userid, orgcode, functionCode, dealerCode));
                    }
                }
			}else{
			  //是当月 还没有 固化的 数据
                sql.append(" select A.DEALER_CODE,A.SOLD_BY, A.CONFIRM_NUM,B.SUCCESS_NUM,C.FAIL_NUM,D.NEED_SECOND_UOLOAD,(ROUND(CAST(coalesce(B.SUCCESS_NUM,0) AS DECIMAL(10,2))/A.CONFIRM_NUM,4)*100) as PASS_RATE,'' as PAMS, ");
                sql.append("  N.OVER_TIME ,E.EMPLOYEE_NAME,W.WX_BINDING_NUM,(ROUND(CAST(coalesce(W.WX_BINDING_NUM,0) AS DECIMAL(10,2))/A.CONFIRM_NUM,4)*100) as BINDING_RATE, '' as IS_PASS,R.SIX_MONTH_FAIL_NUM ,E.MOBILE,E.PHONE from (select max(tp.DEALER_CODE) as DEALER_CODE, tt.sold_by,count(*) as CONFIRM_NUM from TM_POTENTIAL_CUSTOMER tp ");
                sql.append(" inner join tt_sales_order tt on  tp.DEALER_CODE = tt.DEALER_CODE and  tp.customer_no = tt.customer_no ");
                sql.append(" where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" ");//and tt.CONFIRMED_DATE>date('2014-12-5')
                sql.append(" and Year(tt.CONFIRMED_DATE)="+nYear+" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) group by tt.sold_by) A left join  ");
                //成功的
                sql.append(" (select tt.sold_by, count(*) AS SUCCESS_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt ");
                sql.append(" on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.DEALER_CODE=tv.DEALER_CODE and tt.vin=tv.vin where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null ");
                sql.append(" and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+"  and tv.IS_OVERTIME=12781002 ");//现在是通过是否与其标识
                sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
                sql.append(" and tp.OB_IS_SUCCESS=70031001  group by tt.sold_by ) B on A.sold_by=B.sold_by left join ( ");
                //失败
                sql.append(" select tt.sold_by, count(*) AS FAIL_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no where  ");
                sql.append(" tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
                sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
                sql.append("  and tp.OB_IS_SUCCESS=70031002  group by tt.sold_by ");//and Days(tp.OUTBOUND_RETURN_TIME)-Days(tt.CONFIRMED_DATE)<="+range+"
                sql.append(" )C on  A.sold_by=C.sold_by  left join ( ");
                //需要二次上报的
                sql.append(" select tt.sold_by, count(*) AS NEED_SECOND_UOLOAD from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE  ");
                sql.append(" and tp.customer_no=tt.customer_no where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
                sql.append(" and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+"  ");//and Days(tp.OUTBOUND_RETURN_TIME)-Days(tt.CONFIRMED_DATE)<="+range+"
                sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
                sql.append(" and tp.is_second_time=12781002 ");//and Days(current DATE)-Days(tp.OUTBOUND_RETURN_TIME)<="+overDate+" 
                sql.append(" and tp.OB_IS_SUCCESS=70031002  group by tt.sold_by )D  on A.sold_by = D.sold_by left join( ");
                //超时的
                sql.append("  select tt.sold_by, count(*) AS OVER_TIME from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.DEALER_CODE=tv.DEALER_CODE and tt.vin=tv.vin where  ");
                sql.append(" tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
                sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
                sql.append(" and  tv.IS_OVERTIME=12781001   ");
                sql.append("  group by tt.sold_by )N on A.sold_by=N.sold_by ");
                
                //微信绑定数
                sql.append("  left join ( select  tt.sold_by,count(*) as WX_BINDING_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on  tp.DEALER_CODE = tt.DEALER_CODE and  tp.customer_no = tt.customer_no ");
                sql.append(" where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
                sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
                sql.append(" and tp.IS_BINDING=12781001 group by tt.sold_by) W on A.sold_by = W.sold_by ");
                if(dateDay>13){
                    sql.append("  left join (select * from tm_out_bound_month_report where Year(created_at)=Year(CURRENT_DATE) and ");
                    sql.append(" Month(created_at)=Month(CURRENT_DATE)) R on A.DEALER_CODE=R.DEALER_CODE AND A.sold_by=R.user_id ");
                }else{
                    sql.append("  left join (select * from tm_out_bound_month_report where Year(created_at)=YEAR(DATE_SUB(CURRENT_DATE, INTERVAL  1 MONTH )) and ");
                    sql.append(" Month(created_at)=MONTH(DATE_SUB(CURRENT_DATE, INTERVAL  1 MONTH ))) R on A.DEALER_CODE=R.DEALER_CODE AND A.sold_by=R.user_id ");
                }
                sql.append(" inner join TM_USER U on  U.DEALER_CODE=A.DEALER_CODE AND U.USER_ID=A.SOLD_BY INNER JOIN TM_EMPLOYEE E ON E.DEALER_CODE=A.DEALER_CODE ");
                sql.append(" AND U.EMPLOYEE_NO=E.EMPLOYEE_NO  ");
                if(!StringUtils.isNullOrEmpty(soldBy)){
                    sql.append(" where A.SOLD_BY='"+soldBy+"' ");               
                }else{
                    if(userid != null){
                        sql.append(DAOUtilGF.getFunRangeByStr("A", "SOLD_BY", userid, orgcode, functionCode, dealerCode));
                    }
                }
                
  
			}

		System.err.println("table2 -----------------------"+sql.toString());

		 List<Object> queryParam=new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), queryParam);
        
    }
    
    @Override
    public PageInfoDto queryEntityNum(Map<String, String> param)throws ServiceBizException{
    	String yearMonth = param.get("yearMonth");
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	
    	int index = yearMonth.indexOf("-");
    	String year = yearMonth.substring(0,index);
    	String month = yearMonth.substring(index+1, yearMonth.length());
		Calendar date = Calendar.getInstance();
		int dateDay = date.get(Calendar.DAY_OF_MONTH);
		int nYear = Integer.parseInt(year);
		int nMonth = Integer.parseInt(month);
    	 StringBuilder sql=new StringBuilder();
		 List<Map> cusList = this.isHave(nYear,nMonth);
		 System.out.println("11111111111111111");
		 System.out.println(cusList);
			if(cusList.size()>0){
		        //不是查询当前月的 则 从固化的 表中 查询数据
	            sql.append("SELECT AAA.*,BBB.DEALER_SHORTNAME FROM( "); 
	            sql.append(" select tb.DEALER_CODE ,tb.E_CONFIRM_NUM ,tb.E_SUCCESS_NUM ,tb.E_FAIL_NUM,(left(cast(DATE_SUB(tb.created_at ,INTERVAL  1 MONTH ) as char(10)),7)) AS REPORT_DATE ,tb.E_WX_BINDING_NUM,tb.E_BINDING_RATE,tb.E_IS_PASS, ");
	            sql.append(" tb.E_NEED_SECOND_UPLOAD AS E_NEED_SECOND_UOLOAD, (ROUND(CAST(coalesce(tb.E_SUCCESS_NUM,0) AS DECIMAL(10,2))/coalesce(tb.E_CONFIRM_NUM,0),4)*100) AS E_PASS_RATE , tb.E_PAMS  ,tb.E_SIX_MONTH_FAIL_NUM ,tb.E_OVER_TIME_NUM AS E_OVER_TIME ");
	            sql.append(" from tm_out_bound_entity_month_report tb LEFT JOIN  TM_ASC_BASICINFO AC on tb.DEALER_CODE = AC.DEALER_CODE");
	            sql.append(" where tb.DEALER_CODE ='"+dealerCode+"'  and YEAR(DATE_SUB(tb.created_at, INTERVAL  1 MONTH ))="+nYear+" and MONTH(DATE_SUB(tb.created_at, INTERVAL  1 MONTH ))="+nMonth+"   ");
	            sql.append(" ) AAA LEFT JOIN TM_DEALER_BASICINFO BBB ON AAA.DEALER_CODE = BBB.DEALER_CODE");
			}else{
			  //是当月 还没有 固化的 数据
                
	            sql.append("SELECT AAA.*,BBB.DEALER_SHORTNAME FROM( "); 
	            sql.append(" select A.DEALER_CODE  ,A.CONFIRM_NUM AS E_CONFIRM_NUM,B.REPORT_DATE,B.SUCCESS_NUM AS E_SUCCESS_NUM,C.FAIL_NUM AS E_FAIL_NUM,D.NEED_SECOND_UOLOAD AS E_NEED_SECOND_UOLOAD,(ROUND(CAST(coalesce(B.SUCCESS_NUM,0) AS DECIMAL(10,2))/A.CONFIRM_NUM,4)*100) as E_PASS_RATE,'' as E_PAMS, ");
	            sql.append("  N.OVER_TIME AS E_OVER_TIME,W.E_WX_BINDING_NUM,(ROUND(CAST(coalesce(W.E_WX_BINDING_NUM,0) AS DECIMAL(10,2))/A.CONFIRM_NUM,4)*100) AS E_BINDING_RATE,'' AS E_IS_PASS,R.E_SIX_MONTH_FAIL_NUM from (select tp.DEALER_CODE,count(*) as CONFIRM_NUM from TM_POTENTIAL_CUSTOMER tp ");
	            sql.append(" inner join tt_sales_order tt on  tp.DEALER_CODE = tt.DEALER_CODE and  tp.customer_no = tt.customer_no ");
	            sql.append(" where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" ");
	            sql.append(" and Year(tt.CONFIRMED_DATE)="+nYear+"  and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075)  group by tp.DEALER_CODE) A left join  ");
	            //成功数 sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append(" (select  tp.DEALER_CODE,(left(cast((max(tt.CONFIRMED_DATE)) as char(10)),7)) AS REPORT_DATE,count(*) AS SUCCESS_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt ");
	            sql.append(" on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.DEALER_CODE=tv.DEALER_CODE and tt.vin=tv.vin  where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null ");
	            sql.append(" and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" and tv.IS_OVERTIME=12781002 ");
	             sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append(" and tp.OB_IS_SUCCESS=70031001  group by tp.DEALER_CODE ) B on A.DEALER_CODE=B.DEALER_CODE left join ( ");
	            //失败的
	            sql.append(" select tt.DEALER_CODE, count(*) AS FAIL_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no where  ");
	            sql.append(" tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
	            sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append("  and tp.OB_IS_SUCCESS=70031002  group by tt.DEALER_CODE ");
	            sql.append(" )C on  A.DEALER_CODE=C.DEALER_CODE  left join ( ");
	            //需要二次补录的
	            sql.append(" select tt.DEALER_CODE, count(*) AS NEED_SECOND_UOLOAD from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE  ");
	            sql.append(" and tp.customer_no=tt.customer_no where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
	            sql.append(" and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
	            sql.append(" and tp.is_second_time=12781002  ");
	             sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append(" and tp.OB_IS_SUCCESS=70031002  group by tt.DEALER_CODE )D  on A.DEALER_CODE = D.DEALER_CODE left join( ");
	            //超时的
	            sql.append("  select tt.DEALER_CODE, count(*) AS OVER_TIME from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.DEALER_CODE=tt.DEALER_CODE and tp.customer_no=tt.customer_no  left join tm_vehicle tv on tt.DEALER_CODE=tv.DEALER_CODE and tt.vin=tv.vin     where  ");
	            sql.append(" tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+" ");
	             sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append(" and tv.IS_OVERTIME=12781001   ");
	            sql.append("  group by tt.DEALER_CODE )N on A.DEALER_CODE=N.DEALER_CODE LEFT JOIN TM_ASC_BASICINFO AC");
	            sql.append(" on A.DEALER_CODE = AC.DEALER_CODE ");
	            //微信绑定
	            sql.append("  left join ( select  tt.DEALER_CODE,count(*) as E_WX_BINDING_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on  tp.DEALER_CODE = tt.DEALER_CODE and  tp.customer_no = tt.customer_no ");
	            sql.append(" where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>date('2014-12-5') and Month(tt.CONFIRMED_DATE)="+nMonth+" and Year(tt.CONFIRMED_DATE)="+nYear+"  ");
	             sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
	            sql.append(" and tp.IS_BINDING=12781001 group by tt.DEALER_CODE ) W on A.DEALER_CODE = W.DEALER_CODE ");
	            if(dateDay>13){
	                sql.append("  left join (select DEALER_CODE,E_SIX_MONTH_FAIL_NUM  from tm_out_bound_entity_month_report where Year(created_at)=Year(CURRENT_DATE) and ");
	                sql.append(" Month(created_at)=Month(CURRENT_DATE) ) R on A.DEALER_CODE=R.DEALER_CODE  ");
	            }else{
	                sql.append("  left join (select DEALER_CODE,E_SIX_MONTH_FAIL_NUM from tm_out_bound_entity_month_report where Year(created_at)=Year(CURRENT_DATE-1 ) and ");
	                sql.append(" Month(created_at)=Month(CURRENT_DATE-1 ) ) R on A.DEALER_CODE=R.DEALER_CODE   ");
	            }
	            sql.append(" ) AAA LEFT JOIN TM_DEALER_BASICINFO BBB ON AAA.DEALER_CODE = BBB.DEALER_CODE");
	
		}	
			
			System.err.println("table 1 ----------------------"+sql.toString());
		 List<Object> queryParam=new ArrayList<Object>();
	        return DAOUtil.pageQuery(sql.toString(), queryParam);
    }
    @Override
    public PageInfoDto queryOutBundDetail(Map<String, String> param,String soldBy)throws ServiceBizException{
    	String yearMonth = param.get("yearMonth");
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgcode = FrameworkUtil.getLoginInfo().getOrgCode();
		String functionCode= "55604000";//客流HSL趋势分析
    	int index = yearMonth.indexOf("-");
    	String year = yearMonth.substring(0,index);
    	String month = yearMonth.substring(index+1, yearMonth.length());
    	StringBuffer sql= new StringBuffer("");
    	sql.append("SELECT AAA.*,BBB.MODEL_NAME,CCC.COLOR_NAME,DDD.EMPLOYEE_NAME FROM ( ");
		sql.append(" select tp.DEALER_CODE,tp.CUSTOMER_NAME,tp.CONTACTOR_MOBILE,tt.SOLD_BY,tp.OB_IS_SUCCESS,tp.REASONS,tp.OUTBOUND_UPLOAD_TIME as OUTBOUND_UPLOAD_TIME,te.IS_OVERTIME,tp.IS_SECOND_TIME,te.IS_BINDING,te.BINDING_DATE as BINDING_DATE,tp.IS_UPDATE,tt.VIN,tt.SO_NO, ");//(values date(tp.BINDING_DATE))
		sql.append(" tt.CONFIRMED_DATE as CONFIRMED_DATE,tv.MODEL_CODE as MODEL,tt.COLOR_CODE as COLOR,tp.OUTBOUND_RETURN_TIME as OUTBOUND_RETURN_TIME, ");
		sql.append(" ((TO_DAYS(tt.CONFIRMED_DATE)+10)-TO_DAYS(CURRENT_DATE)) as REMAIN_TIME from TM_POTENTIAL_CUSTOMER tp inner join TT_SALES_ORDER tt ");
		sql.append(" on tp.DEALER_CODE = tt.DEALER_CODE and tp.customer_no = tt.customer_no ");
		sql.append(" left join TM_VS_PRODUCT tv on tt.DEALER_CODE = tv.DEALER_CODE and tt.product_code = tv.product_code LEFT JOIN TM_VEHICLE te ON tt.DEALER_CODE=te.DEALER_CODE and tt.vin=te.vin where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and year(tt.CONFIRMED_DATE)="+year+"  ");
		sql.append(" and month(tt.CONFIRMED_DATE)="+month+" and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
		//过滤掉销售退回的 
		sql.append(" and tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
		if(!StringUtils.isNullOrEmpty(soldBy)){
			sql.append(" and tt.sold_by='"+soldBy+"' ");
		}else{
        	if(userid != null){
        		sql.append(DAOUtilGF.getFunRangeByStr("tt", "SOLD_BY", userid, orgcode, functionCode, dealerCode));
        	}
        }
		sql.append(" ) AAA LEFT JOIN TM_MODEL BBB ON AAA.MODEL = BBB.MODEL_CODE LEFT JOIN TM_COLOR CCC ON AAA.COLOR = CCC.COLOR_CODE "
				+ " LEFT JOIN TM_EMPLOYEE DDD ON AAA.SOLD_BY = DDD.EMPLOYEE_NO");
		System.err.println("tabler3------------------"+sql.toString());
		 List<Object> queryParam=new ArrayList<Object>();
	        return DAOUtil.pageQuery(sql.toString(), queryParam);
    }
    
    @Override
    public Map<String, Object> queryOutBundDetailbyVin(String vin) throws ServiceBizException {
    	StringBuilder sb = new StringBuilder();
    	sb.append("select aa.* from ( ");
    	sb.append("SELECT AAA.*,BBB.MODEL_NAME,CCC.COLOR_NAME,DDD.EMPLOYEE_NAME,EEE.BRAND_NAME,FFF.SERIES_NAME,GGG.CONFIG_NAME FROM ( ");
    	sb.append(" select tv.BRAND_CODE,TV.SERIES_CODE,TV.CONFIG_CODE,tp.ADDRESS,TP.E_MAIL,tp.CUSTOMER_TYPE,tt.CONTACTOR_NAME,tp.GENDER,tp.ZIP_CODE,tp.CT_CODE,tp.CERTIFICATE_NO,tp.DEALER_CODE,tp.CUSTOMER_NAME,tp.CONTACTOR_MOBILE,tt.SOLD_BY,tp.OB_IS_SUCCESS,tp.REASONS,tp.OUTBOUND_UPLOAD_TIME as OUTBOUND_UPLOAD_TIME,te.IS_OVERTIME,tp.IS_SECOND_TIME,te.IS_BINDING,te.BINDING_DATE as BINDING_DATE,tp.IS_UPDATE,tt.VIN,tt.SO_NO, ");//(values date(tp.BINDING_DATE))
    	sb.append(" tt.CONFIRMED_DATE as CONFIRMED_DATE,tv.MODEL_CODE as MODEL,tt.COLOR_CODE as COLOR,tp.OUTBOUND_RETURN_TIME as OUTBOUND_RETURN_TIME, ");
    	sb.append(" ((TO_DAYS(tt.CONFIRMED_DATE)+10)-TO_DAYS(CURRENT_DATE)) as REMAIN_TIME from TM_POTENTIAL_CUSTOMER tp inner join TT_SALES_ORDER tt ");
    	sb.append(" on tp.DEALER_CODE = tt.DEALER_CODE and tp.customer_no = tt.customer_no ");
    	sb.append(" left join TM_VS_PRODUCT tv on tt.DEALER_CODE = tv.DEALER_CODE and tt.product_code = tv.product_code LEFT JOIN TM_VEHICLE te ON tt.DEALER_CODE=te.DEALER_CODE and tt.vin=te.vin  ");
    	sb.append(" ) AAA LEFT JOIN TM_MODEL BBB ON AAA.MODEL = BBB.MODEL_CODE LEFT JOIN TM_COLOR CCC ON AAA.COLOR = CCC.COLOR_CODE "
				+ " LEFT JOIN TM_EMPLOYEE DDD ON AAA.SOLD_BY = DDD.EMPLOYEE_NO LEFT JOIN TM_BRAND EEE ON AAA.BRAND_CODE = EEE.BRAND_CODE LEFT JOIN TM_SERIES FFF ON AAA.SERIES_CODE = FFF.SERIES_CODE"
				+ " LEFT JOIN TM_CONFIGURATION GGG ON AAA.CONFIG_CODE = GGG.CONFIG_CODE  ");
    	sb.append(" )aa where aa.vin = '"+vin+"' ");
    	System.err.println("--------------------------------二次补录查询:    "+sb.toString());
        Map map = DAOUtil.findAll(sb.toString(), null).get(0);
        return map;
    }
    
    @Override 
    public void updateCusInfoById(OutBoundReportDTO outBoundReportDTO) throws ServiceBizException {
	    System.err.println(outBoundReportDTO);
	    String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
	    Integer dKey = CommonConstants.D_KEY;
	    SalesOrderPO tt1 = SalesOrderPO.findByCompositeKeys(dealerCode,outBoundReportDTO.getSoNo());
		String linkCusNo = tt1.getString("CUSTOMER_NO");
		String businessType =tt1.getString("BUSINESS_TYPE");
		String vin = outBoundReportDTO.getVin();
		Map map = this.queryOutBundDetailbyVin(vin);
		String oldLinkedName = null;
		String oldCusName = null;
		String oldCusType = null;
		String oldLinkedMobile = null; 
		String oldCtNo = null;
		String oldColor = null;
		if(!StringUtils.isNullOrEmpty(map.get("CONTACTOR_NAME"))){
			 oldLinkedName = map.get("CONTACTOR_NAME").toString();
		}
		if(!StringUtils.isNullOrEmpty(map.get("CUSTOMER_NAME"))){
			 oldCusName = map.get("CUSTOMER_NAME").toString();
		}
		if(!StringUtils.isNullOrEmpty(map.get("CUSTOMER_TYPE"))){
			 oldCusType = map.get("CUSTOMER_TYPE").toString();
		}
		if(!StringUtils.isNullOrEmpty(map.get("CONTACTOR_MOBILE"))){
			 oldLinkedMobile = map.get("CONTACTOR_MOBILE").toString(); 
		}
		if(!StringUtils.isNullOrEmpty(map.get("CERTIFICATE_NO"))){
			 oldCtNo = map.get("CERTIFICATE_NO").toString();
		}
		if(!StringUtils.isNullOrEmpty(map.get("COLOR"))){
			 oldColor = map.get("COLOR").toString();
		}

		Boolean isColorChange = false;
		if(!StringUtils.isNullOrEmpty(oldColor) && !StringUtils.isNullOrEmpty(outBoundReportDTO.getColorCode())){
			if(!oldColor.equals(outBoundReportDTO.getColorCode())){
				isColorChange = true;
			}
		}
		if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getContactorMobile())){
			StringBuilder sql = new StringBuilder();
			sql.append("select * from Tm_Potential_Customer where ( Contactor_Mobile='"+outBoundReportDTO.getContactorMobile()+"' "
					+ "or Contactor_Phone='"+outBoundReportDTO.getContactorMobile()+"')"				
					+" and DEALER_CODE='"+dealerCode+"' AND D_KEY="+dKey+"  ");
			List list = DAOUtil.findAll(sql.toString(), null);
			if(list != null && list.size()>0){
				throw new ServiceBizException("手机号与客户"+linkCusNo+"相同,无法修改");
			}
		}

		Long userid = FrameworkUtil.getLoginInfo().getUserId();
	    if(!StringUtils.isNullOrEmpty(businessType) && !"13001004".equals(businessType)){
	    	if(!StringUtils.isNullOrEmpty(oldLinkedName)){
	    		 List<TtPoCusLinkmanPO> regionList = TtPoCusLinkmanPO.find("DEALER_CODE = ? AND CUSTOMER_NO = ? AND IS_DEFAULT_CONTACTOR = ? AND "
	    				+ "CONTACTOR_NAME = ? ", dealerCode,linkCusNo,12781001,oldLinkedName);
	    		 for (int i = 0; i < regionList.size(); i++) {
	    			 TtPoCusLinkmanPO po1 = regionList.get(i);
	    			 po1.setString("CONTACTOR_NAME", outBoundReportDTO.getCustomerName());
	    			 po1.saveIt();
	    		}
	    	}
	    }else{
	    	TtPoCusLinkmanPO linkmaninfo = new TtPoCusLinkmanPO();
	    	linkmaninfo.setString("DEALER_CODE", dealerCode);
	    	linkmaninfo.setString("CUSTOMER_NO", linkCusNo);
	    	linkmaninfo.setString("CONTACTOR_NAME", outBoundReportDTO.getCustomerName());
	    	linkmaninfo.setString("MOBILE", outBoundReportDTO.getContactorMobile());
	    	linkmaninfo.setString("IS_DEFAULT_CONTACTOR", 12781001);
	    	linkmaninfo.setString("OWNED_BY", userid);
	    	linkmaninfo.saveIt();
	    }
	
		String customerNo = this.checkCustomerInfo(vin, dealerCode);
		if(!StringUtils.isNullOrEmpty(customerNo) && !StringUtils.isNullOrEmpty(businessType) && !"13001004".equals(businessType)){
			List<PotentialCusPO> regionList = PotentialCusPO.find("DEALER_CODE = ? AND CUSTOMER_NAME = ? AND CUSTOMER_TYPE = ? "
					+ " AND CONTACTOR_MOBILE = ? AND CERTIFICATE_NO = ? ", dealerCode,oldCusName,oldCusType,oldLinkedMobile,oldCtNo);
			System.err.println(dealerCode+" "+oldCusName+" "+oldCusType+" "+oldLinkedMobile+" "+oldCtNo);
			System.err.println(regionList);
			for (int i = 0; i < regionList.size(); i++) {
				PotentialCusPO pPO = regionList.get(i);
				pPO.setString("CUSTOMER_NAME", outBoundReportDTO.getCustomerName());		
				pPO.setString("CUSTOMER_TYPE", outBoundReportDTO.getCustomerType());
				pPO.setString("CONTACTOR_MOBILE", outBoundReportDTO.getContactorMobile());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getGender())){
					pPO.setString("GENDER", outBoundReportDTO.getGender());
				}else{
					pPO.setString("GENDER", "");
				}
				pPO.setString("ADDRESS", outBoundReportDTO.getAddress());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCtCode())){
					pPO.setString("CT_CODE", outBoundReportDTO.getCtCode());
				}else{
					pPO.setString("CT_CODE", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCertificateNo())){
					pPO.setString("CERTIFICATE_NO", outBoundReportDTO.getCertificateNo());
				}else{
					pPO.setString("CERTIFICATE_NO", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getZipCode())){
					pPO.setString("ZIP_CODE", outBoundReportDTO.getZipCode());
				}else{
					pPO.setString("ZIP_CODE", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.geteMail())){
					pPO.setString("E_MAIL", outBoundReportDTO.geteMail());
				}else{
					pPO.setString("E_MAIL", "");
				}
				pPO.saveIt();
			}
			List<CustomerPO> cusList = CustomerPO.find("DEALER_CODE = ? AND CUSTOMER_NAME = ? AND CUSTOMER_TYPE = ? "
					+ " AND CONTACTOR_MOBILE = ? AND CERTIFICATE_NO = ? ", dealerCode,oldLinkedName,oldCusType,oldLinkedMobile,oldCtNo);
			for (int i = 0; i < cusList.size(); i++) {
				CustomerPO cPO = cusList.get(i);
				cPO.setString("CUSTOMER_NAME", outBoundReportDTO.getCustomerName());		
				cPO.setString("CUSTOMER_TYPE", outBoundReportDTO.getCustomerType());
				cPO.setString("CONTACTOR_MOBILE", outBoundReportDTO.getContactorMobile());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getGender())){
					cPO.setString("GENDER", outBoundReportDTO.getGender());
				}else{
					cPO.setString("GENDER", "");
				}
				cPO.setString("ADDRESS", outBoundReportDTO.getAddress());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCtCode())){
					cPO.setString("CT_CODE", outBoundReportDTO.getCtCode());
				}else{
					cPO.setString("CT_CODE", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCertificateNo())){
					cPO.setString("CERTIFICATE_NO", outBoundReportDTO.getCertificateNo());
				}else{
					cPO.setString("CERTIFICATE_NO", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getZipCode())){
					cPO.setString("ZIP_CODE", outBoundReportDTO.getZipCode());
				}else{
					cPO.setString("ZIP_CODE", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.geteMail())){
					cPO.setString("E_MAIL", outBoundReportDTO.geteMail());
				}else{
					cPO.setString("E_MAIL", "");
				}
				cPO.saveIt();		
			}	
		 }else{
			 if(!StringUtils.isNullOrEmpty(businessType) && !"13001004".equals(businessType)){
					List<PotentialCusPO> regionList = PotentialCusPO.find("DEALER_CODE = ? AND CUSTOMER_NAME = ? AND CUSTOMER_TYPE = ? "
							+ " AND CONTACTOR_MOBILE = ? AND CERTIFICATE_NO = ? ", dealerCode,oldLinkedName,oldCusType,oldLinkedMobile,oldCtNo);
					for (int i = 0; i < regionList.size(); i++) {
						PotentialCusPO pPO = regionList.get(i);
						pPO.setString("CUSTOMER_NAME", outBoundReportDTO.getCustomerName());		
						pPO.setString("CUSTOMER_TYPE", outBoundReportDTO.getCustomerType());
						pPO.setString("CONTACTOR_MOBILE", outBoundReportDTO.getContactorMobile());
						if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getGender())){
							pPO.setString("GENDER", outBoundReportDTO.getGender());
						}else{
							pPO.setString("GENDER", "");
						}
						pPO.setString("ADDRESS", outBoundReportDTO.getAddress());
						if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCtCode())){
							pPO.setString("CT_CODE", outBoundReportDTO.getCtCode());
						}else{
							pPO.setString("CT_CODE", "");
						}
						if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCertificateNo())){
							pPO.setString("CERTIFICATE_NO", outBoundReportDTO.getCertificateNo());
						}else{
							pPO.setString("CERTIFICATE_NO", "");
						}
						if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getZipCode())){
							pPO.setString("ZIP_CODE", outBoundReportDTO.getZipCode());
						}else{
							pPO.setString("ZIP_CODE", "");
						}
						if(!StringUtils.isNullOrEmpty(outBoundReportDTO.geteMail())){
							pPO.setString("E_MAIL", outBoundReportDTO.geteMail());
						}else{
							pPO.setString("E_MAIL", "");
						}
						pPO.saveIt();
					}
			 }
		 }
		List<SalesOrderPO> soList = SalesOrderPO.find("DEALER_CODE = ? AND SO_NO = ? ", dealerCode,outBoundReportDTO.getSoNo());
		for (int i = 0; i < soList.size(); i++) {
			SalesOrderPO soPO = soList.get(i);
			System.err.println(soPO);
			soPO.setString("CUSTOMER_TYPE", outBoundReportDTO.getCustomerType());
			soPO.setString("CUSTOMER_NAME", outBoundReportDTO.getCustomerName());		
			soPO.setString("ADDRESS", outBoundReportDTO.getAddress());
			if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCtCode())){
				soPO.setString("CT_CODE", outBoundReportDTO.getCtCode());
			}else{
				soPO.setString("CT_CODE", "");
			}
			soPO.setString("CERTIFICATE_NO", outBoundReportDTO.getCertificateNo());
			soPO.saveIt();
		}
		String ownerNo = this.queryOwner(dealerCode, vin);
		if(!StringUtils.isNullOrEmpty(ownerNo) && !StringUtils.isNullOrEmpty(businessType) && !"13001004".equals(businessType)){
			List<CarownerPO> owList = CarownerPO.find("OWNER_NO = ? ", ownerNo);
			for (int i = 0; i < owList.size(); i++) {
				CarownerPO ownerPO = owList.get(i);
				ownerPO.setString("OWNER_NAME", outBoundReportDTO.getCustomerName());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getGender())){
					ownerPO.setString("GENDER", outBoundReportDTO.getGender());
				}else{
					ownerPO.setString("GENDER", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCtCode())){
					ownerPO.setString("CT_CODE", outBoundReportDTO.getCtCode());
				}else{
					ownerPO.setString("CT_CODE", "");
				}
				ownerPO.setString("ADDRESS", outBoundReportDTO.getAddress());
				ownerPO.setString("MOBILE", outBoundReportDTO.getContactorMobile());
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getCertificateNo())){
					ownerPO.setString("CERTIFICATE_NO", outBoundReportDTO.getCertificateNo());
				}else{
					ownerPO.setString("CERTIFICATE_NO", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.getZipCode())){
					ownerPO.setString("ZIP_CODE", outBoundReportDTO.getZipCode());
				}else{
					ownerPO.setString("ZIP_CODE", "");
				}
				if(!StringUtils.isNullOrEmpty(outBoundReportDTO.geteMail())){
					ownerPO.setString("E_MAIL", outBoundReportDTO.geteMail());
				}else{
					ownerPO.setString("E_MAIL", "");
				}
				ownerPO.saveIt();
			}
		}
		if(isColorChange = true && !StringUtils.isNullOrEmpty(businessType) && !"13001004".equals(businessType)){
			List<VehiclePO> veList = VehiclePO.find("VIN = ?",outBoundReportDTO.getVin());
			for (int i = 0; i < veList.size(); i++) {
				VehiclePO vePO = veList.get(i);
				vePO.setString("COLOR", outBoundReportDTO.getColorCode());
				vePO.saveIt();
			}
			List<SalesOrderPO> voList = SalesOrderPO.find("VIN = ? AND SO_NO = ? ",outBoundReportDTO.getVin(),outBoundReportDTO.getSoNo());
			for (int i = 0; i < voList.size(); i++) {
				SalesOrderPO soPO = voList.get(i);
				System.err.println(soPO);
				System.err.println(outBoundReportDTO.getColorCode());
				soPO.setString("COLOR_CODE", outBoundReportDTO.getColorCode());
				soPO.saveIt();
			}
		}
    }
    
    public List<Map> queryOutBundDetailExport(Map<String, String> param) throws ServiceBizException {
    	String yearMonth = param.get("yearMonth");
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	String soldBy = param.get("soldBy");
    	int index = yearMonth.indexOf("-");
    	String year = yearMonth.substring(0,index);
    	String month = yearMonth.substring(index+1, yearMonth.length());
    	StringBuffer sql= new StringBuffer("");
    	sql.append("SELECT AAA.*,BBB.MODEL_NAME,CCC.COLOR_NAME,DDD.EMPLOYEE_NAME FROM ( ");
		sql.append(" select tp.DEALER_CODE,tp.CUSTOMER_NAME,tp.CONTACTOR_MOBILE,tt.SOLD_BY,tp.OB_IS_SUCCESS,tp.REASONS,tp.OUTBOUND_UPLOAD_TIME as OUTBOUND_UPLOAD_TIME,te.IS_OVERTIME,tp.IS_SECOND_TIME,te.IS_BINDING,te.BINDING_DATE as BINDING_DATE,tp.IS_UPDATE,tt.VIN,tt.SO_NO, ");//(values date(tp.BINDING_DATE))
		sql.append(" tt.CONFIRMED_DATE as CONFIRMED_DATE,tv.MODEL_CODE as MODEL,tt.COLOR_CODE as COLOR,tp.OUTBOUND_RETURN_TIME as OUTBOUND_RETURN_TIME, ");
		sql.append(" ((TO_DAYS(tt.CONFIRMED_DATE)+10)-TO_DAYS(CURRENT_DATE)) as REMAIN_TIME from TM_POTENTIAL_CUSTOMER tp inner join TT_SALES_ORDER tt ");
		sql.append(" on tp.DEALER_CODE = tt.DEALER_CODE and tp.customer_no = tt.customer_no ");
		sql.append(" left join TM_VS_PRODUCT tv on tt.DEALER_CODE = tv.DEALER_CODE and tt.product_code = tv.product_code LEFT JOIN TM_VEHICLE te ON tt.DEALER_CODE=te.DEALER_CODE and tt.vin=te.vin where tp.DEALER_CODE='"+dealerCode+"' and tt.CONFIRMED_DATE is not null and year(tt.CONFIRMED_DATE)="+year+"  ");
		sql.append(" and month(tt.CONFIRMED_DATE)="+month+" and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
		//过滤掉销售退回的 
		sql.append(" and tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
		if(!StringUtils.isNullOrEmpty(soldBy)){
			sql.append(" and tt.sold_by='"+soldBy+"' ");
		}
		sql.append(" ) AAA LEFT JOIN TM_MODEL BBB ON AAA.MODEL = BBB.MODEL_CODE LEFT JOIN TM_COLOR CCC ON AAA.COLOR = CCC.COLOR_CODE "
				+ " LEFT JOIN TM_EMPLOYEE DDD ON AAA.SOLD_BY = DDD.EMPLOYEE_NO");
        List<Object> queryList = new ArrayList<Object>(); 
        List<Map> resultList = DAOUtil.findAll(sql.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("新交车客户400核实月报导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }
    

	private List<Map> isHave(int nYear,int nMonth) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from  tm_out_bound_month_report where dealer_code='"+dealerCode+"' ");
        sb.append(" and Year(created_at-1 )="+nYear+" and Month(created_at -1 )="+nMonth+" ");
        System.err.println(sb.toString());
        List<Object> queryList = new ArrayList<Object>();
        return Base.findAll(sb.toString(), queryList.toArray());
    }
	
	/**
	 * 校验是否存在保佑客户信息
	 * @throws ServiceBizException 
	 */
	 public String checkCustomerInfo(String vin, String dealerCode)
			throws ServiceBizException {
		//根据VIN查TM_VEHICLE,查CUSTOMER_NO有没信息,有：已经有保佑客户信息,否:没有保佑客户信息
		 String customerNo = null;
		 StringBuffer sql = new StringBuffer("");
		 try{
			 sql.append(" select * from tm_vehicle where DEALER_CODE='"+dealerCode+"' and vin='"+vin+"' ");
			 Map map= DAOUtil.findAll(sql.toString(),null).get(0);
			 if(map != null && map.size()>0){
				 customerNo = map.get("CUSTOMER_NO").toString(); 
			 }
		}catch(Exception e){

				System.err.println(sql.toString());
				throw e;
		}
		return customerNo;
	 }
	 
	 /**
	  * 是否存在车主信息
	 * @throws ServiceBizException 
	  * */
	 public String queryOwner(String dealerCode , String vin) throws ServiceBizException{
		String owNo="%888888888888%";
		String ownerNo = null;
		StringBuffer sql = new StringBuffer("");
		try{
			sql.append(" select * from tm_vehicle where DEALER_CODE='"+dealerCode+"' and vin='"+vin+"' ");
			sql.append(" and OWNER_NO not like '"+owNo+"' ");
			 Map map= DAOUtil.findAll(sql.toString(),null).get(0);
			 if(map != null && map.size()>0){
				 ownerNo = map.get("OWNER_NO").toString(); 
			 }
		}catch(Exception e){
			System.err.println(sql.toString());
			throw e;
		}

		return ownerNo; 
	 }
	
}
