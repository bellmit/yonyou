package com.yonyou.dcs.schedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dcs.gacfca.SADCS021Cloud;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

@TxnConn
@Component
public class WxCustomerManagerSendJob extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(WxCustomerManagerSendJob.class);
	@Autowired
	SADCS021Cloud sadcs021cloud;

	@Override
	public void execute() throws Exception {
		try {
			logger.info("====一对一客户经理绑定修改下发WxCustomerManagerSendJob开始====");
			sadcs021cloud.handleExecute();
			logger.info("====一对一客户经理绑定修改下发WxCustomerManagerSendJob结束====");
		} catch (Exception e) {
			logger.error("一对一客户经理绑定修改下发WxCustomerManagerSendJob下发失败", e);
		}

	}

}
