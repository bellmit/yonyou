package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.jec.SI19;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI19Task extends TenantSingletonTask {
	
	@Autowired
	SI19 si19;

	@Override
	public void execute() throws Exception {
		try {
			si19.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
