package com.yonyou.dcs.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.gacfca.SADCS055Cloud;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class ResultActivityJob extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(ResultActivityJob.class);
	@Autowired
	SADCS055Cloud sADCS055;

	@Override
	public void execute() throws Exception {
		logger.info("====SADCS055下发开始====");
		try {
			sADCS055.execute();
			logger.info("====SADCS055下发结束====");
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			logger.info("SADCS055服务活动下发失败", e);
		}

	}

}
