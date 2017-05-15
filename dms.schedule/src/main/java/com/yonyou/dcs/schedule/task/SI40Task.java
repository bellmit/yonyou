package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI40;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI40Task extends TenantSingletonTask {
	
	@Autowired
	SI40 si40;

	@Override
	public void execute() throws Exception {
		try {
			si40.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
