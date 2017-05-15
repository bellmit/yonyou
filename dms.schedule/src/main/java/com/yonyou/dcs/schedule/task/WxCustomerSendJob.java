package com.yonyou.dcs.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.gacfca.SADCS020Cloud;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class WxCustomerSendJob extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(WxCustomerSendJob.class);
	@Autowired
	SADCS020Cloud sadcs020cloud;

	@Override
	public void execute() throws Exception {
		try {
			logger.info("====微信车主信息下发WxCustomerSendJob开始====");
			sadcs020cloud.execute();
			logger.info("====微信车主信息下发WxCustomerSendJob结束====");
		} catch (Exception e) {
			logger.error("微信车主信息下发WxCustomerSendJob下发失败", e);
		}
	}

}
