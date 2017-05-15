/**
 * 
 */
package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.SADMS010Dto;
import com.yonyou.dms.schedule.service.impl.SADMS010Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 每周上报展厅报表（展厅周报）
 * @author Administrator
 *@date 2017年2月28日
 */
@TxnConn
@Component
public class SADMS010 extends TenantSingletonTask{
	 @SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SADMS010.class);
	 @Autowired
	  SADMS010Service planTestService;
	@Override
	public void execute() throws Exception {
		   LinkedList<SADMS010Dto> dtoList = planTestService.getSADMS010();
	        if(dtoList!=null && dtoList.size()>0){
	            for(int i=0;i<dtoList.size();i++){
	            	SADMS010Dto dto=dtoList.get(i);
	                System.out.println("SADMS010====================================");
	                System.out.println("条数"+dtoList.size());
	                System.out.println(dto.getDealerCode()+"/n"+ dto.getSeriesCode()+"/n"+dto.getSalesOrders()
	                +"/n"+dto.getCurrentDate());
	                        
	            }
	        }
		
	}
	
	
	
	
	
	
	
	

}
