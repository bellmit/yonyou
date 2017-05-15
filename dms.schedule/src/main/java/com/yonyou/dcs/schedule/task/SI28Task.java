package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI28;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI28Task extends TenantSingletonTask {
	
	@Autowired
	SI28 si28;

	@Override
	public void execute() throws Exception {
		try {
			si28.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
