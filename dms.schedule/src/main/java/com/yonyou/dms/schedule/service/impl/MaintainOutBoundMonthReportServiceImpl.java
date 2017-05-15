package com.yonyou.dms.schedule.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.TmOutBoundMonthReportDTO;
import com.yonyou.dms.schedule.domains.PO.TmOutBoundEntityMonthReportPO;
import com.yonyou.dms.schedule.domains.PO.TmOutBoundMonthReportPO;

@Service
public class MaintainOutBoundMonthReportServiceImpl implements  MaintainOutBoundMonthReportService{
	private int range=13;//超过的时间范围
	private int overDate=7;//二次上报有效时间
	private int monthRange=6;//最近几个月的绩效
	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public int performExecute() throws ServiceBizException {
		Calendar cd = Calendar.getInstance();
		int nowDay =cd.get(Calendar.DAY_OF_MONTH);//获取当前时间 是几号
		int nowHour = cd.get(Calendar.HOUR_OF_DAY);//几点
		int nowMinute = cd.get(Calendar.MINUTE);
		try{
			String defaultPara =getDefaultValuePre("5432");
			String[] planData = defaultPara.split(",");
			int planDay = Integer.parseInt(planData[0]);
			int planHour = Integer.parseInt(planData[1]);
			int planMinute = Integer.parseInt(planData[2]);
			//获取TM_DEFAULT_PARA 表中 设置的 该 计划任务 执行时间
			if(planDay==nowDay){	  
				List<Map> poList = null;
				poList =this.getOutBoundReturn(range, overDate);
				if(poList!=null&&poList.size()>0){
					  for(int i=0;i<poList.size();i++){
						  TmOutBoundMonthReportDTO dto = new TmOutBoundMonthReportDTO();
						  TmOutBoundMonthReportPO po = new TmOutBoundMonthReportPO();
						  po.setLong("ITEM_ID",(Long)poList.get(i).get("ITEM_ID"));
						  po.setInteger("_KEY",CommonConstants.D_KEY);
						  po.setDate("UPDATE_AT",new Date());
						  po.setLong("USER_ID",(Long)poList.get(i).get("USER_ID"));
						  po.setString("USER_NAME",(String)poList.get(i).get("USER_NAME"));
						  po.setInteger("CONFIR_NUM",(Integer)poList.get(i).get("CONFIRM_NUM"));
						  po.setInteger("SUCCESS_Num",(Integer)poList.get(i).get("SUCCESS_Num"));
						  po.setInteger("fail_num",(Integer)poList.get(i).get("fail_num"));
						  po.setInteger("NEED_SECOND_UPLOAD",(Integer)poList.get(i).get("NEED_SECOND_UPLOAD"));
						  po.setInteger("OVER_TIME_NUM",(Integer)poList.get(i).get("OVER_TIME_NUM"));
						  po.setDouble("PASS_RATE",(Double)poList.get(i).get("PASS_RATE"));
						  if((Double)poList.get(i).get("PASS_RATE")>=90){//如果通过率大于等于90则 绩效为通过
							  po.setInteger("PAMS",Integer.parseInt(DictCodeConstants.DICT_IS_YES));				  
						  }else{
							  po.setInteger("PAMS",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
						  }
						  if((Double)poList.get(i).get("PASS_RATE")>=90){//若通过 则最近6个月未 通过数 不变 否则+1
							  po.setInteger("SIX_MONTH_FAIL_NUM",this.querySixMonthFailNum( (Long)poList.get(i).get("USER_ID"))); 
						  }else{
							  po.setInteger("SIX_MONTH_FAIL_NUM",this.querySixMonthFailNum((Long)poList.get(i).get("USER_ID"))+1);
						  }
						  po.setInteger("WX_BINDING_NUM",(Integer)poList.get(i).get("WX_BINDING_NUM"));
						  po.setDouble("BINDING_RATE",(Double)poList.get(i).get("BINDING_RATE"));
						  if((Double)poList.get(i).get("BINDING_RATE")>=50){//如果微信绑定率大于等于50 则 考核通过
							  po.setInteger("IS_PASS",Utility.getInt(DictCodeConstants.DICT_IS_YES));
						  }else{
							  po.setInteger("IS_PASS",Utility.getInt(DictCodeConstants.DICT_IS_NO));
						  }
						  po.setDate("CREATED_AT",new Date());
						  po.saveIt();
					  }
				  }
				List<Map> peList = null;
				peList =this.getOutBoundEntityReturn(range, overDate);;
				if(peList!=null&&peList.size()>0){
					for( int i=0;i<peList.size();i++){
						TmOutBoundEntityMonthReportPO pe = new TmOutBoundEntityMonthReportPO();
						  pe.setLong("ITEM_ID",(Long)peList.get(i).get("ITEM_ID"));
						  pe.setInteger("_KEY",CommonConstants.D_KEY);
						  pe.setInteger("E_CONFIR_NUM",(Integer)peList.get(i).get("E_CONFIRM_NUM"));
						  pe.setInteger("E_SUCCESS_Num",(Integer)peList.get(i).get("E_SUCCESS_Num"));
						  pe.setInteger("E_fail_num",(Integer)peList.get(i).get("E_fail_num"));
						  pe.setInteger("E_NEED_SECOND_UPLOAD",(Integer)peList.get(i).get("E_NEED_SECOND_UPLOAD"));
						  pe.setInteger("E_OVER_TIME_NUM",(Integer)peList.get(i).get("E_OVER_TIME_NUM"));
						  pe.setDouble("E_PASS_RATE",(Double)peList.get(i).get("E_PASS_RATE"));
						  if((Double)peList.get(i).get("E_PASS_RATE")>=90){//如果通过率大于等于90则 绩效为通过
							  pe.setInteger("E_PAMS",Integer.parseInt(DictCodeConstants.DICT_IS_YES));				  
						  }else{
							  pe.setInteger("E_PAMS",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
						  }
						  if((Double)peList.get(i).get("E_PASS_RATE")>=90){//若通过 则最近6个月未 通过数 不变 否则+1
							  pe.setInteger("E_SIX_MONTH_FAIL_NUM",this.queryEntitySixMonthFailNum()); 
						  }else{
							  pe.setInteger("E_SIX_MONTH_FAIL_NUM",this.queryEntitySixMonthFailNum()+1);
						  }
						  pe.setInteger("E_WX_BINDING_NUM",(Integer)peList.get(i).get("E_WX_BINDING_NUM"));
						  pe.setDouble("E_BINDING_RATE",(Double)peList.get(i).get("E_BINDING_RATE"));
						  if((Double)peList.get(i).get("E_BINDING_RATE")>=50){//如果微信绑定率大于等于50 则 考核通过
							  pe.setInteger("E_IS_PASS",Utility.getInt(DictCodeConstants.DICT_IS_YES));
						  }else{
							  pe.setInteger("E_IS_PASS",Utility.getInt(DictCodeConstants.DICT_IS_NO));
						  }
						  pe.setDate("CREATED_AT",new Date());
						  pe.saveIt();

					}
				}
				return 1;
			}else{
				return 1;
			}
		}catch(Exception e){
			return 0;
		}
	
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> getOutBoundReturn(int range, int overDate) throws Exception {
		StringBuffer sql = new StringBuffer("");
			sql.append(" select A.SOLD_BY, A.CONFIRM_NUM,B.SUCCESS_NUM,C.FAIL_NUM,D.NEED_SECOND_UOLOAD,(ROUND(CAST(coalesce(B.SUCCESS_NUM,0) AS DOUBLE)/A.CONFIRM_NUM,4)*100) as PASS_RATE,'' as PAMS, ");
			sql.append("  N.OVER_TIME,W.WX_BINDING_NUM ,(ROUND(CAST(coalesce(W.WX_BINDING_NUM,0) AS DOUBLE)/A.CONFIRM_NUM,4)*100) as BINDING_RATE,E.EMPLOYEE_NAME ,E.MOBILE,E.PHONE from (select max(tp.entity_code) as entity_code, tt.sold_by,count(*) as CONFIRM_NUM from TM_POTENTIAL_CUSTOMER tp ");
			sql.append(" inner join tt_sales_order tt on  tp.entity_code = tt.entity_code and  tp.customer_no = tt.customer_no ");
			sql.append(" where tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) ");
			sql.append(" and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075)  group by tt.sold_by) A left join  ");
			
			sql.append(" (select tt.sold_by, count(*) AS SUCCESS_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt ");
			sql.append(" on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.entity_code=tv.entity_code and tt.vin=tv.vin where tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
			sql.append(" and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) and tv.IS_OVERTIME=12781002  ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_SUCCESS+"  group by tt.sold_by ) B on A.sold_by=B.sold_by left join ( ");
			
			sql.append(" select tt.sold_by, count(*) AS FAIL_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no where  ");
			sql.append(" tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_FAILED+"  group by tt.sold_by ");
			sql.append(" )C on  A.sold_by=C.sold_by  left join ( ");
			//需要补录的
			sql.append(" select tt.sold_by, count(*) AS NEED_SECOND_UOLOAD from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code  ");
			sql.append(" and tp.customer_no=tt.customer_no where  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') ");
			sql.append(" and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.is_second_time="+DictCodeConstants.DICT_IS_NO+" ");
			sql.append(" and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_FAILED+"  group by tt.sold_by )D  on A.sold_by = D.sold_by left join( ");
			//超期的
			sql.append("  select tt.sold_by, count(*) AS OVER_TIME from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.entity_code=tv.entity_code and tt.vin=tv.vin where  ");
			sql.append(" tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append("  and tv.IS_OVERTIME=12781001 ");
			sql.append("  group by tt.sold_by )N on A.sold_by=N.sold_by ");
			
			sql.append("  left join ( select  tt.sold_by,count(*) as WX_BINDING_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on  tp.entity_code = tt.entity_code and  tp.customer_no = tt.customer_no ");
			sql.append(" where tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=Year(current date-1 MONTH) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.IS_BINDING="+DictCodeConstants.DICT_IS_YES+" group by tt.sold_by) W on A.sold_by = W.sold_by ");
			sql.append(" inner join TM_USER U on  U.entity_code=A.entity_code AND U.USER_ID=A.SOLD_BY INNER JOIN TM_EMPLOYEE E ON E.entity_code=A.entity_code ");
			sql.append(" AND U.EMPLOYEE_NO=E.EMPLOYEE_NO  ");
//			if(Utility.testString(soldBy)){
//				sql.append(" where A.SOLD_BY='"+soldBy+"' ");				
//			}
			 List<Map> rsList = Base.findAll(sql.toString());
		return rsList;
		
	}
	/**
	 * 获得销售顾问 最近六个月绩效通过数
	 * @throws Exception 
	 * */
	@Override
	public int querySixMonthFailNum(Long soldBy) throws Exception {
		StringBuffer sql = new StringBuffer("");
			sql.append(" select count(*) from tm_out_bound_month_report where  month(current date)-month(create_date)>0  ");
			sql.append("  and  month(current date)-month(create_date)<"+monthRange+"  and  PAMS="+DictCodeConstants.DICT_IS_NO+"  and  user_id="+soldBy+" ");
			int num = Base.exec(sql.toString());
			
		return num;
	}
	/**
	 * 获取经销商为维度的核实结果月报统计
	 * */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getOutBoundEntityReturn(int range, int overDate) throws Exception {
		StringBuffer sql = new StringBuffer("");
			sql.append(" select A.ENTITY_CODE,A.CONFIRM_NUM AS E_CONFIRM_NUM,B.REPORT_DATE,B.SUCCESS_NUM AS E_SUCCESS_NUM,C.FAIL_NUM AS E_FAIL_NUM,D.NEED_SECOND_UOLOAD AS E_NEED_SECOND_UOLOAD,(ROUND(CAST(coalesce(B.SUCCESS_NUM,0) AS DOUBLE)/A.CONFIRM_NUM,4)*100) as E_PASS_RATE,'' as E_PAMS, ");
			sql.append("  N.OVER_TIME AS E_OVER_TIME,W.E_WX_BINDING_NUM,(ROUND(CAST(coalesce(W.E_WX_BINDING_NUM,0) AS DOUBLE)/A.CONFIRM_NUM,4)*100) as E_BINDING_RATE  from (select tp.entity_code,count(*) as CONFIRM_NUM from TM_POTENTIAL_CUSTOMER tp ");
			sql.append(" inner join tt_sales_order tt on  tp.entity_code = tt.entity_code and  tp.customer_no = tt.customer_no ");
			sql.append(" where  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) ");
			sql.append(" and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH)) and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075)  group by tp.entity_code) A left join  ");
			//成功的
			sql.append(" (select  tp.ENTITY_CODE,(left(cast((max(tt.CONFIRMED_DATE)) as char(10)),7)) AS REPORT_DATE,count(*) AS SUCCESS_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt ");
			sql.append(" on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.entity_code=tv.entity_code and tt.vin=tv.vin  where  tt.CONFIRMED_DATE is not null ");
			sql.append(" and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH)) and tv.IS_OVERTIME=12781002 ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_SUCCESS+"  group by tp.entity_code ) B on A.ENTITY_CODE=B.ENTITY_CODE left join ( ");
			//失败的
			sql.append(" select tt.ENTITY_CODE, count(*) AS FAIL_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no where  ");
			sql.append("  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH)) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append("  and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_FAILED+"  group by tt.entity_code ");
			sql.append(" )C on  A.ENTITY_CODE=C.ENTITY_CODE  left join ( ");
			//需要二次补录的
			sql.append(" select tt.ENTITY_CODE, count(*) AS NEED_SECOND_UOLOAD from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code  ");
			sql.append(" and tp.customer_no=tt.customer_no where  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5')  ");
			sql.append(" and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH)) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.is_second_time="+DictCodeConstants.DICT_IS_NO+"  ");
			sql.append(" and tp.OB_IS_SUCCESS="+DictCodeConstants.DICT_OUTBOUND_RETURN_STATUS_FAILED+"  group by tt.entity_code )D  on A.ENTITY_CODE = D.ENTITY_CODE left join( ");
			//超时的
			sql.append("  select tt.ENTITY_CODE, count(*) AS OVER_TIME from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on tp.entity_code=tt.entity_code and tp.customer_no=tt.customer_no left join tm_vehicle tv on tt.entity_code=tv.entity_code and tt.vin=tv.vin where  ");
			sql.append("  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>=date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH)) ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tv.IS_OVERTIME=12781001  ");
			sql.append(" group by tt.entity_code )N on A.ENTITY_CODE=N.ENTITY_CODE ");
			//微信
			sql.append("  left join ( select  tt.ENTITY_CODE,count(*) as E_WX_BINDING_NUM from TM_POTENTIAL_CUSTOMER tp inner join tt_sales_order tt on  tp.entity_code = tt.entity_code and  tp.customer_no = tt.customer_no ");
			sql.append(" where  tt.CONFIRMED_DATE is not null and tt.CONFIRMED_DATE>date('2014-12-5') and Month(tt.CONFIRMED_DATE)=(Month(current date-1 MONTH)) and Year(tt.CONFIRMED_DATE)=(Year(current date-1 MONTH))  ");
			sql.append(" and  tt.BUSINESS_TYPE=13001001 and  (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
			sql.append(" and tp.IS_BINDING="+DictCodeConstants.DICT_IS_YES+" group by tt.ENTITY_CODE ) W on A.ENTITY_CODE = W.ENTITY_CODE ");
			List<Map> rsList = Base.findAll(sql.toString());
		return rsList;
	}
	/**
	 * 获取经销商六个月未 达标数
	 * @author chen eg
	 * */
	@Override
	public int queryEntitySixMonthFailNum() throws Exception {
		StringBuffer sql = new StringBuffer("");
		sql.append(" select count(*) from tm_out_bound_entity_month_report where month(current date)-month(create_date)>0  ");
		sql.append("  and  month(current date)-month(create_date)<"+monthRange+"  and  E_PAMS="+DictCodeConstants.DICT_IS_NO+" ");
		int num = Base.exec(sql.toString());
		
		return num;
	}
	@SuppressWarnings("rawtypes")
	public  String getDefaultValuePre(String itemCode) {
		String str = "";
		StringBuffer sb = new StringBuffer("SELECT * FROM TM_DEFAULT_PARA WHERE ITEM_CODE = '"+itemCode+"'");
 		List<Map> list3 = Base.findAll(sb.toString());
 		str = (String) list3.get(0).get("DEFAULT_VALUE");
		return str;
	}
	

}
