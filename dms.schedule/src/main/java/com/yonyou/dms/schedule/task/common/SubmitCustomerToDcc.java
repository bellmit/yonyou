/**
 * 
 */
package com.yonyou.dms.schedule.task.common;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.schedule.service.impl.SubmitCustomerToDccService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 业务描述：6个月未生成订单转为休眠客户
 * @author Administrator
 *
 */

@TxnConn
@Component
public class SubmitCustomerToDcc extends TenantSingletonTask{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SubmitCustomerToDcc.class);
    @Autowired
	private SubmitCustomerToDccService scDccService;
	   @Override
	   public void execute() throws Exception {
		   System.err.println("[6个月未生成订单转为休眠客户]===============计划任务开始");
	        // 获取是否修改
		   List<SameToDccDto> dtoList = scDccService.performExecute();
	        if(dtoList != null && dtoList.size()>0 ){
	        	 for(int i=0;i<dtoList.size();i++){
	        		 SameToDccDto dto=dtoList.get(i);
	                 System.out.println("SubmitCustomerToDcc====================================");
	                 System.out.println("条数"+dtoList.size());
	                 System.out.println("编号:"+dto.getCustomerNo()+"/n"+"名字:"+ dto.getName()+"/n"+"原因:"+dto.getSleepReason()
	                 +"/n"+"性别:"+dto.getGender());
	                         
	             }
	           
	        }
	        System.out.println("**************************************结束");
	    }

	
}
