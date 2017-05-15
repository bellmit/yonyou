package com.yonyou.dcs.schedule.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infoeai.eai.action.ctcai.SI03;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@Component
public class SI03Task extends TenantSingletonTask {
	
	@Autowired
	SI03 si03;

	@Override
	public void execute() throws Exception {
		try {
			si03.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
