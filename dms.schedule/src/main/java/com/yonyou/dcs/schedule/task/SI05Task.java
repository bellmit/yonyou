package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI05;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI05Task extends TenantSingletonTask {
	
	@Autowired
	SI05 si05;

	@Override
	public void execute() throws Exception {
		try {
			si05.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
