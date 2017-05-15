package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * *转化率报表Service实现类
 * @author Benzc
 * @date 2017年1月18日
 */
@Service
public class ConversionRateServiceImpl implements ConversionRateService{
    
	/**
	 * 分页查询
	 * @author Benzc
	 * @date 2017年1月18日
	 */
	@Override
	public PageInfoDto queryConversionRate(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String yearMonth = queryParam.get("yearMonth");
    	int index = yearMonth.indexOf("-");
    	String qyear = yearMonth.substring(0,index);
    	String qmonth = yearMonth.substring(index+1, yearMonth.length());
    	
    	//将前台取到年月转化为int类型
		int year = Integer.parseInt(qyear);
		int month = Integer.parseInt(qmonth);
		
		int year1;
		int year2;
		int year3;
		
		int month1;
		int month2;
		int month3;
		int lastDay2;
		int lastDay;
		int lastDay1;
		if (month==1){
			 year1=year-1;
			 month1=12;
			 year2=year1;
			 year3=year1;
			 month2=11;
			 month3=10;
			 lastDay=this.getLastOfMonth(year, month);
			 lastDay2 = this.getLastOfMonth(year2, month2);
			 lastDay1= this.getLastOfMonth(year1, month1);
		}else{
			year1=year;
		    month1=month-1;
		    lastDay=this.getLastOfMonth(year, month);
		    lastDay1= this.getLastOfMonth(year1, month1);
			if (month1==1){
				year2=year1-1;
				month2=12;
				year3=year2;
				month3=11;
				lastDay2 = this.getLastOfMonth(year2, month2);
			} else {
				year2=year1;
				month2=month1-1;
				lastDay2 = this.getLastOfMonth(year2, month2);
				if (month2==1){
					year3=year2-1;
					month3=12;
				}else {
					year3=year2;
					month3=month2-1;
				}
			}
		}
		sb.append(" select * from ( ");
		sb.append(" select 5 as id, "+dealerCode+" as dealer_code, "+" '展厅成交率' AS TYPE,'展厅交车率=(当月展厅交车数/当月展厅留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("  from ( ");
		sb.append("  select count(*) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("  and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year+" and month(CONFIRMED_DATE)="+month);
		sb.append( "   and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("   and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append("  union all select 0 real1, count(distinct a.customer_no) goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append( "    and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("    union all ");
		sb.append("     select 0 real1,0 goal1,count(*) real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("    and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year1+" and month(CONFIRMED_DATE)="+month1);
		sb.append("    and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("    and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append( "     union all select 0 real1,0 goal1,0 real2, count(distinct a.customer_no) goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("    where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("   and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("    union all ");
		sb.append("   select 0 real1,0 goal1,0 real2,0 goal2,count(*) real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("     and business_type=13001001  and dealer_code='"+dealerCode+"' and " );
		sb.append(" ((year(CONFIRMED_DATE)="+year2+" and month(CONFIRMED_DATE)="+month2+") or (year(CONFIRMED_DATE)="+year3+" and month(CONFIRMED_DATE)="+month3+") )");
		sb.append( "   and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("  and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append("   union all select 0 real1, 0 goal1,0 real2,0 goal2,0 real3, count(distinct a.customer_no) goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append("  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+")or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))" );
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("   )as g ");
		sb.append("   union all ");
		sb.append(" select 4 as id, "+dealerCode+" as dealer_code, "+"'试乘试驾率' AS TYPE,'试乘试驾率=(当月展厅试驾数/当月展厅留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("   from ( ");
		sb.append("  select count(distinct a.customer_no) real1,0 as goal1,0 real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a, ");
		sb.append("  TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append("   where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append(" and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append("  and  year(action_date)="+year+" and month(action_date)="+month+" and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append(" and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1, count(distinct a.customer_no) goal1,0 real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append(" and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all ");
		sb.append("  select 0 real1,0 as goal1,count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a, ");
		sb.append(" TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append(" where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append(" and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append(" and  year(action_date)="+year1+" and month(action_date)="+month1+" and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append("  and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1, 0 goal1,0 real2,count(distinct a.customer_no) goal2,0 real3,0 goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("  and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all ");
		sb.append( "   select 0 real1,0 as goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3  from tm_potential_customer a, ");
		sb.append(" TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append("  where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
				 //"  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))"+
		sb.append("and date(b.created_at) between date('"+year3+"-"+month3+"-01') and date('"+year2+"-"+month2+"-"+lastDay2+"')");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append(" and  year(action_date)=year(b.created_at) and month(action_date)=month(b.created_at) and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append(" and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1,0 goal1,0 real2,0 goal2,0 real3,count(distinct a.customer_no) goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at) ");
//				 "  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))"+
		sb.append(" and date(b.created_at) between date('"+year3+"-"+month3+"-01') and date('"+year2+"-"+month2+"-"+lastDay2+"') ");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("   )as f  ");
		sb.append("  union all ");
		sb.append(" select 3 as id, "+dealerCode+" as dealer_code, "+"'二次进店邀回率' AS TYPE,'二次进店邀回率=(当月二次进店数/当月首次留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("  from (  ");
		sb.append(" select count(distinct a.customer_no) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001 and date(action_date)<=date('"+year+"-"+month+"-"+lastDay+"') ");//and year(action_date)="+year+" and month(action_date)="+month+
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append("  where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year+"-"+month+"-01') ");
		sb.append(" and dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,count(*) as goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer ");
		sb.append(" where dealer_code='"+dealerCode+"' and year(created_at)="+year+" and month(created_at)="+month);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 goal1, count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001 and date(action_date)<=date('"+year1+"-"+month1+"-"+lastDay1+"')");//and year(action_date)="+year1+" and month(action_date)="+month1+
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append(" where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year1+"-"+month1+"-01') ");
		sb.append("  and dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,count(*) as goal2,0 real3,0 goal3 from tm_potential_customer ");
		sb.append(" where dealer_code='"+dealerCode+"' and year(created_at)="+year1+" and month(created_at)="+month1);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001" );
		sb.append(" and(( year(action_date)="+year2+" and month(action_date)="+month2+") or (year(action_date)="+year3+" and month(action_date)="+month3+"))");
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append(" where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year3+"-"+month3+"-01') ");
		sb.append("  and dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,count(*) as goal3 from tm_potential_customer ");
		sb.append("  where dealer_code='"+dealerCode+"'" );
		sb.append(" and(( year(created_at)="+year2+" and month(created_at)="+month2+") or (year(created_at)="+year3+" and month(created_at)="+month3+"))");
		sb.append("  )as e ");
		sb.append("  union all ");
		sb.append(" select 2 as id, "+dealerCode+" as dealer_code, "+"'到店留档率' AS TYPE,'到店留档率=(当月展厅留档数/当月首次进店数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append(" from ( ");
		sb.append(" select count(distinct a.customer_no) real1,0 as goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("  where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append("  and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,(count(*)-count(customer_no)+count(distinct customer_no))as goal1,0 real2,0 goal2,0 real3,0 goal3  from TT_VISITING_RECORD ");
		sb.append("  where dealer_code='"+dealerCode+"' and year(created_at)="+year+" and month(created_at)="+month);
		sb.append(" union all ");
		sb.append(" select 0 real1,0 as goal1,count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("  and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,(count(*)-count(customer_no)+count(distinct customer_no))as goal2,0 real3,0 goal3  from TT_VISITING_RECORD ");
		sb.append("  where dealer_code='"+dealerCode+"' and year(created_at)="+year1+" and month(created_at)="+month1);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 as goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append(" and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))  ");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,(count(*)-count(customer_no)+count(distinct customer_no))as goal3  from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='"+dealerCode+"' and" );
		sb.append(" ((year(created_at)="+year2+" and month(created_at)="+month2+") or (year(created_at)="+year3+" and month(created_at)="+month3+"))  ");
		sb.append("  )as d ");
		sb.append(" union all  ");
		sb.append(" select 1 as id, "+dealerCode+" as dealer_code, "+" '销售目标完成率' AS TYPE,'销售目标完成率=(当月交车数/当月零售目标)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append(" from ( ");
		sb.append(" select count(*) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year+" and month(CONFIRMED_DATE)="+month);
		sb.append(" union all select 0 real1 ,goal1,0 real2,0 goal2,0 real3,0 goal3 from (select sum( DELIVERY_COUNT) goal1 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year+"-"+month+"')or (MONTH='"+year+"-0"+month+"') ))as c ");
		sb.append(" union all  ");
		sb.append(" select 0 real1,0 goal1,count(*) real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year1+" and month(CONFIRMED_DATE)="+month1);
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,goal2,0 real3,0 goal3 from (select sum( DELIVERY_COUNT) goal2 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year1+"-"+month1+"')or (MONTH='"+year1+"-0"+month1+"') ))as a ");
		sb.append(" union all  ");
		sb.append(" select 0 real1,0 goal1,0 real2,0 goal2,count(*) real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and " );
		sb.append(" ((year(CONFIRMED_DATE)="+year2+" and month(CONFIRMED_DATE)="+month2+") or (year(CONFIRMED_DATE)="+year3+" and month(CONFIRMED_DATE)="+month3+"))  ");
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,goal3 from (select sum( DELIVERY_COUNT) goal3 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year2+"-"+month2+"')or (MONTH='"+year2+"-0"+month2+"')or (MONTH='"+year3+"-"+month3+"')or (MONTH='"+year3+"-0"+month3+"') ))as b ");
		sb.append(" )as i ");
		sb.append(" )as h order by id ");
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        return result;
	}
	
	public int getLastOfMonth(int year, int month){
		int lastDay = 0;
		int day = 1;
		Calendar cal = Calendar.getInstance();
		cal.set(year,month-1 ,day);
		lastDay = cal.getActualMaximum(Calendar.DATE);
		return lastDay;
	}
    
	/**
	 * 转化率报表
	 * @author Benzc
	 * @date 2017年2月4日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryConversionRecordforExport(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String yearMonth = queryParam.get("yearMonth");
    	int index = yearMonth.indexOf("-");
    	String qyear = yearMonth.substring(0,index);
    	String qmonth = yearMonth.substring(index+1, yearMonth.length());
    	
    	//将前台取到年月转化为int类型
		int year = Integer.parseInt(qyear);
		int month = Integer.parseInt(qmonth);
		
		int year1;
		int year2;
		int year3;
		
		int month1;
		int month2;
		int month3;
		int lastDay2;
		int lastDay;
		int lastDay1;
		if (month==1){
			 year1=year-1;
			 month1=12;
			 year2=year1;
			 year3=year1;
			 month2=11;
			 month3=10;
			 lastDay=this.getLastOfMonth(year, month);
			 lastDay2 = this.getLastOfMonth(year2, month2);
			 lastDay1= this.getLastOfMonth(year1, month1);
		}else{
			year1=year;
		    month1=month-1;
		    lastDay=this.getLastOfMonth(year, month);
		    lastDay1= this.getLastOfMonth(year1, month1);
			if (month1==1){
				year2=year1-1;
				month2=12;
				year3=year2;
				month3=11;
				lastDay2 = this.getLastOfMonth(year2, month2);
			} else {
				year2=year1;
				month2=month1-1;
				lastDay2 = this.getLastOfMonth(year2, month2);
				if (month2==1){
					year3=year2-1;
					month3=12;
				}else {
					year3=year2;
					month3=month2-1;
				}
			}
		}
		sb.append(" select * from ( ");
		sb.append(" select 5 as id, "+dealerCode+" as dealer_code, "+" '展厅成交率' AS TYPE,'展厅交车率=(当月展厅交车数/当月展厅留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("  from ( ");
		sb.append("  select count(*) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("  and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year+" and month(CONFIRMED_DATE)="+month);
		sb.append( "   and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("   and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append("  union all select 0 real1, count(distinct a.customer_no) goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append( "    and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("    union all ");
		sb.append("     select 0 real1,0 goal1,count(*) real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("    and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year1+" and month(CONFIRMED_DATE)="+month1);
		sb.append("    and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("    and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append( "     union all select 0 real1,0 goal1,0 real2, count(distinct a.customer_no) goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("    where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("   and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("    union all ");
		sb.append("   select 0 real1,0 goal1,0 real2,0 goal2,count(*) real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append("     and business_type=13001001  and dealer_code='"+dealerCode+"' and " );
		sb.append(" ((year(CONFIRMED_DATE)="+year2+" and month(CONFIRMED_DATE)="+month2+") or (year(CONFIRMED_DATE)="+year3+" and month(CONFIRMED_DATE)="+month3+") )");
		sb.append( "   and exists (select a.customer_no from tm_potential_customer a,TT_VISITING_RECORD b where a.customer_no=b.customer_no ");
		sb.append("  and a.dealer_code=b.dealer_code and a.customer_no=tt_sales_order.customer_no ) ");
		sb.append("   union all select 0 real1, 0 goal1,0 real2,0 goal2,0 real3, count(distinct a.customer_no) goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append("  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+")or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))" );
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("   )as g ");
		sb.append("   union all ");
		sb.append(" select 4 as id, "+dealerCode+" as dealer_code, "+"'试乘试驾率' AS TYPE,'试乘试驾率=(当月展厅试驾数/当月展厅留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("   from ( ");
		sb.append("  select count(distinct a.customer_no) real1,0 as goal1,0 real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a, ");
		sb.append("  TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append("   where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append(" and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append("  and  year(action_date)="+year+" and month(action_date)="+month+" and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append(" and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1, count(distinct a.customer_no) goal1,0 real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)="+year+" and month(a.created_at)="+month);
		sb.append(" and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all ");
		sb.append("  select 0 real1,0 as goal1,count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3  from tm_potential_customer a, ");
		sb.append(" TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append(" where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append(" and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append(" and  year(action_date)="+year1+" and month(action_date)="+month1+" and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append("  and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1, 0 goal1,0 real2,count(distinct a.customer_no) goal2,0 real3,0 goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("  and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all ");
		sb.append( "   select 0 real1,0 as goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3  from tm_potential_customer a, ");
		sb.append(" TT_VISITING_RECORD b,TT_SALES_PROMOTION_PLAN c  ");
		sb.append("  where a.customer_no=b.customer_no and a.customer_no=c.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
				 //"  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))"+
		sb.append("and date(b.created_at) between date('"+year3+"-"+month3+"-01') and date('"+year2+"-"+month2+"-"+lastDay2+"')");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code=c.dealer_code ");
		sb.append(" and  year(action_date)=year(b.created_at) and month(action_date)=month(b.created_at) and c.REAL_VISIT_ACTION like '%试乘试驾%' ");
		sb.append(" and a.dealer_code='"+dealerCode+"'  ");
		sb.append("  union all select 0 real1,0 goal1,0 real2,0 goal2,0 real3,count(distinct a.customer_no) goal3  from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at) ");
//				 "  and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))"+
		sb.append(" and date(b.created_at) between date('"+year3+"-"+month3+"-01') and date('"+year2+"-"+month2+"-"+lastDay2+"') ");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"'  ");
		sb.append("   )as f  ");
		sb.append("  union all ");
		sb.append(" select 3 as id, "+dealerCode+" as dealer_code, "+"'二次进店邀回率' AS TYPE,'二次进店邀回率=(当月二次进店数/当月首次留档数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append("  from (  ");
		sb.append(" select count(distinct a.customer_no) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001 and date(action_date)<=date('"+year+"-"+month+"-"+lastDay+"') ");//and year(action_date)="+year+" and month(action_date)="+month+
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append("  where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year+"-"+month+"-01') ");
		sb.append(" and dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,count(*) as goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer ");
		sb.append(" where dealer_code='"+dealerCode+"' and year(created_at)="+year+" and month(created_at)="+month);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 goal1, count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001 and date(action_date)<=date('"+year1+"-"+month1+"-"+lastDay1+"')");//and year(action_date)="+year1+" and month(action_date)="+month1+
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append(" where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year1+"-"+month1+"-01') ");
		sb.append("  and dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,count(*) as goal2,0 real3,0 goal3 from tm_potential_customer ");
		sb.append(" where dealer_code='"+dealerCode+"' and year(created_at)="+year1+" and month(created_at)="+month1);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3 from   TT_SALES_PROMOTION_PLAN a  ");
		sb.append(" where a.IS_SUCCESS_BOOKING=12781001" );
		sb.append(" and(( year(action_date)="+year2+" and month(action_date)="+month2+") or (year(action_date)="+year3+" and month(action_date)="+month3+"))");
		sb.append(" and not exists (select *  from TT_SALES_PROMOTION_PLAN b  ");
		sb.append(" where b.customer_no=a.customer_no and b.dealer_code=a.dealer_code and b.action_date<'"+year3+"-"+month3+"-01') ");
		sb.append("  and dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,count(*) as goal3 from tm_potential_customer ");
		sb.append("  where dealer_code='"+dealerCode+"'" );
		sb.append(" and(( year(created_at)="+year2+" and month(created_at)="+month2+") or (year(created_at)="+year3+" and month(created_at)="+month3+"))");
		sb.append("  )as e ");
		sb.append("  union all ");
		sb.append(" select 2 as id, "+dealerCode+" as dealer_code, "+"'到店留档率' AS TYPE,'到店留档率=(当月展厅留档数/当月首次进店数)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append(" from ( ");
		sb.append(" select count(distinct a.customer_no) real1,0 as goal1,0 real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("  where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append("  and year(b.created_at)="+year+" and month(b.created_at)="+month+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append(" union all select 0 real1 ,(count(*)-count(customer_no)+count(distinct customer_no))as goal1,0 real2,0 goal2,0 real3,0 goal3  from TT_VISITING_RECORD ");
		sb.append("  where dealer_code='"+dealerCode+"' and year(created_at)="+year+" and month(created_at)="+month);
		sb.append(" union all ");
		sb.append(" select 0 real1,0 as goal1,count(distinct a.customer_no) real2,0 goal2,0 real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append(" where a.customer_no=b.customer_no and year(a.created_at)="+year1+" and month(a.created_at)="+month1);
		sb.append("  and year(b.created_at)="+year1+" and month(b.created_at)="+month1+" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,(count(*)-count(customer_no)+count(distinct customer_no))as goal2,0 real3,0 goal3  from TT_VISITING_RECORD ");
		sb.append("  where dealer_code='"+dealerCode+"' and year(created_at)="+year1+" and month(created_at)="+month1);
		sb.append("  union all ");
		sb.append("  select 0 real1,0 as goal1,0 real2,0 goal2,count(distinct a.customer_no) real3,0 goal3 from tm_potential_customer a,TT_VISITING_RECORD b ");
		sb.append("   where a.customer_no=b.customer_no and year(a.created_at)=year(b.created_at) and month(a.created_at)=month(b.created_at)");
		sb.append(" and ((year(b.created_at)="+year2+" and month(b.created_at)="+month2+") or (year(b.created_at)="+year3+" and month(b.created_at)="+month3+"))  ");
		sb.append(" and a.dealer_code=b.dealer_code and a.dealer_code='"+dealerCode+"' ");
		sb.append("  union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,(count(*)-count(customer_no)+count(distinct customer_no))as goal3  from TT_VISITING_RECORD ");
		sb.append(" where dealer_code='"+dealerCode+"' and" );
		sb.append(" ((year(created_at)="+year2+" and month(created_at)="+month2+") or (year(created_at)="+year3+" and month(created_at)="+month3+"))  ");
		sb.append("  )as d ");
		sb.append(" union all  ");
		sb.append(" select 1 as id, "+dealerCode+" as dealer_code, "+" '销售目标完成率' AS TYPE,'销售目标完成率=(当月交车数/当月零售目标)' AS REMARK, ");
		sb.append(" CONCAT(CAST(SUM(real1) * 100.00 / (CASE WHEN SUM(goal1) = 0 THEN 1 ELSE SUM(goal1) END) AS DECIMAL (10, 2)),'%') NOW, ");
		sb.append(" CONCAT(CAST(SUM(real2) * 100.00 / (CASE WHEN SUM(goal2) = 0 THEN 1 ELSE SUM(goal2) END) AS DECIMAL (10, 2)),'%') PAST, ");
		sb.append(" CONCAT(CAST(SUM(real3 + real2) * 100.00 / (CASE WHEN SUM(goal3 + goal2) = 0 THEN 1 ELSE SUM(goal3 + goal2) END) AS DECIMAL (10, 2)),'%') EVER ");
		sb.append(" from ( ");
		sb.append(" select count(*) real1,0 goal1,0 real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year+" and month(CONFIRMED_DATE)="+month);
		sb.append(" union all select 0 real1 ,goal1,0 real2,0 goal2,0 real3,0 goal3 from (select sum( DELIVERY_COUNT) goal1 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year+"-"+month+"')or (MONTH='"+year+"-0"+month+"') ))as c ");
		sb.append(" union all  ");
		sb.append(" select 0 real1,0 goal1,count(*) real2,0 goal2,0 real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and  year(CONFIRMED_DATE)="+year1+" and month(CONFIRMED_DATE)="+month1);
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,goal2,0 real3,0 goal3 from (select sum( DELIVERY_COUNT) goal2 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year1+"-"+month1+"')or (MONTH='"+year1+"-0"+month1+"') ))as a ");
		sb.append(" union all  ");
		sb.append(" select 0 real1,0 goal1,0 real2,0 goal2,count(*) real3,0 goal3 from tt_sales_order where so_status not in (13011055,13011060,13011065,13011070) ");
		sb.append(" and business_type=13001001  and dealer_code='"+dealerCode+"' and " );
		sb.append(" ((year(CONFIRMED_DATE)="+year2+" and month(CONFIRMED_DATE)="+month2+") or (year(CONFIRMED_DATE)="+year3+" and month(CONFIRMED_DATE)="+month3+"))  ");
		sb.append(" union all select 0 real1 ,0 goal1,0 real2,0 goal2,0 real3,goal3 from (select sum( DELIVERY_COUNT) goal3 from  TT_PLAN_DEALER where dealer_code='"+dealerCode+"'" );
		sb.append(" and ((MONTH='"+year2+"-"+month2+"')or (MONTH='"+year2+"-0"+month2+"')or (MONTH='"+year3+"-"+month3+"')or (MONTH='"+year3+"-0"+month3+"') ))as b ");
		sb.append(" )as i ");
		sb.append(" )as h order by id ");
		List<Object> queryList = new ArrayList<Object>();
        List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
        return list;
	}

}
