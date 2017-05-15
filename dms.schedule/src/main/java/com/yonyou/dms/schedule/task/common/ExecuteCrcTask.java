package com.yonyou.dms.schedule.task.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.service.impl.ExecuteCrcTaskService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class ExecuteCrcTask extends TenantSingletonTask{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ExecuteCrcTask.class);
	@Autowired
	private ExecuteCrcTaskService executeCrcTaskService;
	
	@Override
	public void execute() throws Exception {
		 System.out.println("ExecuteCrcTask 计划任务开始");
		 Integer dtoList = executeCrcTaskService.performExecute();
		 if(dtoList != null && dtoList!=0 ){
                
                 System.out.println("ExecuteCrcTask====================================");
                 System.out.println("条数"+dtoList);
                
        }
        System.out.println("ExecuteCrcTask**************************************结束");
		 
		 
		
	}
	
}
