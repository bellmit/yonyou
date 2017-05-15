package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.jec.SI18;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI18Task extends TenantSingletonTask {
	
	@Autowired
	SI18 si18;

	@Override
	public void execute() throws Exception {
		try {
			si18.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
