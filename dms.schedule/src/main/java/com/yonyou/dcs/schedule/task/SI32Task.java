package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI32;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI32Task extends TenantSingletonTask {
	
	@Autowired
	SI32 si32;

	@Override
	public void execute() throws Exception {
		try {
			si32.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
