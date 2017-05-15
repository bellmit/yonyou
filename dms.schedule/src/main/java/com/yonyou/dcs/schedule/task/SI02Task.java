package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI02;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI02Task extends TenantSingletonTask {
	
	@Autowired
	SI02 si02;

	@Override
	public void execute() throws Exception {
		try {
			si02.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
