/**
 * 
 */
package com.yonyou.dms.schedule.task.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.WarrantyRegistDTO;
import com.yonyou.dms.schedule.service.impl.CheckOutBoundUploadService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 监控交车确认时保修登记信息上报情况(未成功上报的重新上报) 
 * @author Administrator
 * @date 2017年2月23日
 *
 */
@TxnConn
@Component
public class CheckOutBoundUpload extends TenantSingletonTask {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SubmitCustomerToDcc.class);
    @Autowired
	 CheckOutBoundUploadService scDccService;
	   @Override
	   public void execute() throws Exception {
		   System.out.println("CheckOutBoundUpload计划任务开始");
	        // 获取是否修改
		   int dtoList = scDccService.performExecute();
	        if(dtoList != 0  ){
	                 System.out.println("CheckOutBoundUpload====================================");          
	        }
	           
	        
	        System.out.println("CheckOutBoundUpload**************************************结束");
	    }
}
