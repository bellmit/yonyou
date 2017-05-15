/*
 * Copyright (c) 2010 Yonyou Auto, Inc. All  Rights Reserved.
 * This software is published under the terms of the Yonyou Auto Information Technology (Shanghai) Co., Ltd
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate  ： 2016年6月7日 上午11:55:16
 * CreateBy    ： MjieQiu
 * ProjectName ：f4.schedule
 * PackageName ：com.yonyou.f4.schedule.test
 * File_name   ：TSTask.java
 * Type_name   ：TSTask
 * Comment     ：			    
 */
package com.yonyou.dms.schedule.test.f4;

import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * @author MjieQiu
 *
 */
public class TSTask extends TenantSingletonTask {

	/* (non-Javadoc)
	 * @see com.yonyou.f4.schedule.task.Task#execute()
	 */
	public void execute() throws Exception {
		log.info("hello TenantSingletonTask.");
		try{
			Thread.currentThread().sleep(30000);
		}catch(InterruptedException e){
			log.error(e.getMessage());
			throw e;
		}
	}

}
