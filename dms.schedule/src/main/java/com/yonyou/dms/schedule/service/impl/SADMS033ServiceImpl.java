/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.SADCS033DTO;
import com.yonyou.dms.schedule.domains.PO.TmOutBoundEntityMonthReportPO;

/**
 * @author Administrator
 *
 */
@Service
public class SADMS033ServiceImpl implements SADMS033Service {

	@Override
	public LinkedList<SADCS033DTO> getSADCS033() throws ServiceBizException {
		LinkedList<SADCS033DTO> resultList = new LinkedList<SADCS033DTO>();
		Calendar cd = Calendar.getInstance();
		int nowDay =cd.get(Calendar.DAY_OF_MONTH);//获取当前时间 是几号
		try{
			String defaultPara =getDefaultValuePre("5432");
			String[] planData = defaultPara.split(",");
			int planDay = Integer.parseInt(planData[0]);
			int planHour = Integer.parseInt(planData[1]);
			int planMinute = Integer.parseInt(planData[2]);
			//获取TM_DEFAULT_PARA 表中 设置的 该 计划任务 执行时间
			if(planDay==nowDay){
				  SADCS033DTO vo = new SADCS033DTO();
				  //获取业务年月
					Calendar calendar = Calendar.getInstance(); 
					calendar.add(Calendar.MONTH, -1);    //得到前一个月 
					Integer bizYear = calendar.get(Calendar.YEAR); 
					Integer bizMonth = calendar.get(Calendar.MONTH)+1; 
					List<Map> po = null;
						po =QueryEntityReport();
						//globalTxnMgr.commit(ut);
					
				  if(!po.isEmpty()){
					  for(int j=0;j<po.size();j++){
						  vo.setMonthDmsPayCarNum((Integer)po.get(j).get("E_CONFIRM_NUM"));
						  vo.setVerifySuccessNum((Integer)po.get(j).get("E_SUCCESS_NUM"));
						  vo.setVerifyFailureNum((Integer) po.get(j).get("E_FAIL_NUM"));
						  vo.setVerifyPassRate((Double) po.get(j).get("E_PASS_RATE"));//核实通过率
						  if((Integer)po.get(j).get("E_PAMS")==12781001){
						    vo.setIsStandards("1");//达标
						  }else{
							vo.setIsStandards("2");
						  }
						  vo.setMonthBindingNum((Integer) po.get(j).get("E_WX_BINDING_NUM"));
						  vo.setBindingRate((Double) po.get(j).get("E_BINDING_RATE"));
						  if((Integer)po.get(j).get("E_IS_PASS")==12781001){//如果微信绑定率大于等于50 则 考核通过
							  vo.setIsBindingStandards("1");
						  }else{
							  vo.setIsBindingStandards("2");
						  } 
					  }
				  }else{
					  vo.setMonthDmsPayCarNum(0);
					  vo.setVerifySuccessNum(0);
					  vo.setVerifyFailureNum(0);
					  vo.setVerifyPassRate(0.0d);//核实通过率
					  vo.setIsStandards("2");
					  vo.setMonthBindingNum(0);
					  vo.setBindingRate(0.0d);
				      vo.setIsBindingStandards("2");
				  }
				  vo.setParcarDate(new Date());
				  vo.setBizYear(bizYear.toString());
				  vo.setBizMonth(bizMonth.toString());
				  Integer  busyNum;
				  Integer  notCarNum;
				  Integer  notOwnerNum;
				  Integer  nullNum;
				  busyNum = QueryFailReasonsNum(70011004);
				  notCarNum = QueryFailReasonsNum( 70011001);
				  notOwnerNum = QueryFailReasonsNum( 70011002);
				  nullNum =  QueryFailReasonsNum( 70011003);
						//globalTxnMgr.commit(ut);
					
				  
				  vo.setBusy(busyNum.toString());//失败原因是占线停机的有多少
				  vo.setNotCarOwner(notCarNum.toString());//非机主数
				  vo.setNotOwner(notOwnerNum.toString());//非车主数
				  vo.setNullNum(nullNum.toString());//错号空号
				resultList.add(vo);
//				发送消息
				
			}
			
			
		}catch(Exception e){
			
		}
		return resultList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> QueryEntityReport() throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append(" select * from Tm_Out_Bound_Entity_Month_Report where  ");
		sql.append("  Year(create_date)=Year(current date) and Month(create_date)=Month(current date) ");
		List<Map> list=Base.findAll(sql.toString());
		
		
		return list;
	}

	
	@Override
	public Integer QueryFailReasonsNum(int reasons) throws SQLException {
		return null;
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
