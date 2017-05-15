/**
 * 
 */
package com.yonyou.dms.schedule.task.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.service.impl.CheckUploadWetherTimeoutService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 每天将400逾期未进行二次补录的数据标为逾期
 * @author xhy
 * @date 2017年2月23号
 */
@TxnConn
@Component
public class CheckUploadWetherTimeout extends TenantSingletonTask {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CheckUploadWetherTimeout.class);
	@Autowired
	private CheckUploadWetherTimeoutService executeCrcTaskService;
	@Override
	public void execute() throws Exception {
		 System.out.println("CheckUploadWetherTimeout 计划任务开始");
		 Integer dtoList = executeCrcTaskService.performExecute();
		 System.out.println("条数"+dtoList);
		 if(dtoList != null && dtoList!=0 ){
                
                 System.out.println("CheckUploadWetherTimeout====================================");
                 System.out.println("条数"+dtoList);
                
        }
        System.out.println("CheckUploadWetherTimeout**************************************结束");
		
	}

}
