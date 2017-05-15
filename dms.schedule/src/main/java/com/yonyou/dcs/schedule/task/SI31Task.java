package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI31;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI31Task extends TenantSingletonTask {
	
	@Autowired
	SI31 si31;

	@Override
	public void execute() throws Exception {
		try {
			si31.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
