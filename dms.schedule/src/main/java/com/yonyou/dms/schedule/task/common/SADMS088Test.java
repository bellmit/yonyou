/**
 * 
 */
package com.yonyou.dms.schedule.task.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.gacfca.SADMS088Coud;
import com.yonyou.dms.schedule.service.impl.SADMS034Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * @author Administrator
 *每天上报产品信息
 */
@TxnConn
@Component
public class SADMS088Test extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(SADMS088Test.class);
	@Autowired
	SADMS088Coud sadms088;
	@Override
	public void execute() throws Exception {
		System.out.println("SADMS088================开始");
		int num=sadms088.getSADMS088();
		if(num==1){
			System.out.println("SADMS088===========执行成功");
		}else{
			System.out.println("SADMS088===========失败");
		}
		
		
	}

}
