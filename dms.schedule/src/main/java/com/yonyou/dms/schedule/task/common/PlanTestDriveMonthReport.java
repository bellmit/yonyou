package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.PlanTestDriveMonthDTO;
import com.yonyou.dms.schedule.service.impl.PlanTestDriveMonthReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class PlanTestDriveMonthReport extends TenantSingletonTask {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PlanTestDriveDetail.class);
	
	 @Autowired
		private PlanTestDriveMonthReportService ptwMonthService;

	   @Override
	   public void execute() throws Exception {
		   LinkedList<PlanTestDriveMonthDTO> dtoList = ptwMonthService.getTestDriveMonthReport();
		   if(dtoList!=null && dtoList.size()>0){
			   for(int i = 0; i <dtoList.size(); i++){
				   PlanTestDriveMonthDTO dto = dtoList.get(i);
				   System.err.println("试乘试驾统计计划任务====================================");
	               System.err.println("条数"+dtoList.size());	   
			   }
		   }
		   
	    }
}
