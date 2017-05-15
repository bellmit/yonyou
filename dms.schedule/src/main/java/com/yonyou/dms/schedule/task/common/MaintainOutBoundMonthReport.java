/**
 * 
 */
package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.SADCS033DTO;
import com.yonyou.dms.schedule.service.impl.MaintainOutBoundMonthReportService;
import com.yonyou.dms.schedule.service.impl.SADMS033Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * @author Administrator
 *计划任务 将外呼核实结果报表 固化 
 *新增固话之后上报以经销商为维度的汇总信息给总部 
 */
@TxnConn
@Component
public class MaintainOutBoundMonthReport extends TenantSingletonTask{
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CheckUploadWetherTimeout.class);
	@Autowired
	private MaintainOutBoundMonthReportService executeCrcTaskService;
	@Autowired
	private SADMS033Service sADMS033Service;
	@Override
	public void execute() throws Exception {
		Integer num=executeCrcTaskService.performExecute();
		if(num!=null&&num!=0){
		LinkedList<SADCS033DTO> list=sADMS033Service.getSADCS033();
			
		}
		
	}
}
