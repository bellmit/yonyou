package com.yonyou.dms.schedule.task.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.SADMS034Dto;
import com.yonyou.dms.schedule.service.impl.SADMS034Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class SADMS034 extends TenantSingletonTask{
	private static final Logger logger = LoggerFactory.getLogger(SADMS034.class);
	@Autowired
	SADMS034Service testService;

	@Override
	public void execute() throws Exception {
		System.out.println("SADMS034================开始");
		List<SADMS034Dto> list= testService.getSADMS034();
		System.out.println("SADMS034================结束");
	}
	
}
