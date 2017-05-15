package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI33;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI33Task extends TenantSingletonTask {
	
	@Autowired
	SI33 si33;

	@Override
	public void execute() throws Exception {
		try {
			si33.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
