package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI27;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI27Task extends TenantSingletonTask {
	
	@Autowired
	SI27 si27;

	@Override
	public void execute() throws Exception {
		try {
			si27.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
