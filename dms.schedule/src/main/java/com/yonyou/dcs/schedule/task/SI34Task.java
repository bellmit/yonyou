package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI34;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI34Task extends TenantSingletonTask {
	
	@Autowired
	SI34 si34;

	@Override
	public void execute() throws Exception {
		try {
			si34.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
