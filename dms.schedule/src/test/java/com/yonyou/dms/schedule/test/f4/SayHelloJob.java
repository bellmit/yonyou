/*
 * Copyright (c) 2010 Yonyou Auto, Inc. All  Rights Reserved.
 * This software is published under the terms of the Yonyou Auto Information Technology (Shanghai) Co., Ltd
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate  ： 2016年5月26日 下午5:40:38
 * CreateBy    ： MjieQiu
 * ProjectName ：schedule
 * PackageName ：com.yonyou.schedule.test
 * File_name   ：SayHelloJob.java
 * Type_name   ：SayHelloJob
 * Comment     ：			    
 */
package com.yonyou.dms.schedule.test.f4;

import com.yonyou.f4.schedule.task.Task;

/**
 * @author MjieQiu
 *
 */
public class SayHelloJob extends Task {

	/* (non-Javadoc)
	 * @see com.yonyou.schedule.task.Task#execute()
	 */
	public void execute() {
		log.info("say hello f4-schedule."+schConstant.getErrorStackLen());
//		try{
////			Thread.currentThread().sleep(1000);
//		}catch(InterruptedException e){
//			log.error(e.getMessage());
//		}
	}

}
