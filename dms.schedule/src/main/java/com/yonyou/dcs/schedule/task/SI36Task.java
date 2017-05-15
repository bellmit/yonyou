package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI36;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI36Task extends TenantSingletonTask {
	
	@Autowired
	SI36 si36;

	@Override
	public void execute() throws Exception {
		try {
			si36.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
