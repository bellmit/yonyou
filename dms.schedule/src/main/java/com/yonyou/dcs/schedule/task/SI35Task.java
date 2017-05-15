package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI35;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI35Task extends TenantSingletonTask {
	
	@Autowired
	SI35 si35;

	@Override
	public void execute() throws Exception {
		try {
			si35.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
