package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI04;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI04Task extends TenantSingletonTask {
	
	@Autowired
	SI04 si04;

	@Override
	public void execute() throws Exception {
		try {
			si04.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
