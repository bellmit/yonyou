package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI39;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI39Task extends TenantSingletonTask {
	
	@Autowired
	SI39 si39;

	@Override
	public void execute() throws Exception {
		try {
			si39.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
