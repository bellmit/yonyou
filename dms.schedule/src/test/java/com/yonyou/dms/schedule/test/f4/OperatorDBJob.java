/*
 * Copyright (c) 2010 Yonyou Auto, Inc. All  Rights Reserved.
 * This software is published under the terms of the Yonyou Auto Information Technology (Shanghai) Co., Ltd
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate  ： 2016年6月2日 上午11:22:55
 * CreateBy    ： MjieQiu
 * ProjectName ：f4.schedule
 * PackageName ：com.yonyou.f4.schedule.test
 * File_name   ：OperatorDBJob.java
 * Type_name   ：OperatorDBJob
 * Comment     ：			    
 */
package com.yonyou.dms.schedule.test.f4;

import java.sql.Connection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.function.exception.ApplicationException;
import com.yonyou.f4.common.database.JdbcTemplate;
import com.yonyou.f4.common.database.impl.DBServiceImpl;
import com.yonyou.f4.schedule.SchConstant;
import com.yonyou.f4.schedule.task.Task;
import com.yonyou.f4.schedule.utils.ScheduleUtil;

/**
 * @author MjieQiu
 *
 */
public class OperatorDBJob extends Task {
	
	@Autowired
	private SchConstant schConstant;
	@Autowired
	private DBServiceImpl dbService;

	/* (non-Javadoc)
	 * @see com.yonyou.f4.schedule.task.Task#execute()
	 */
	
	public void execute() throws Exception{
		String tenantId = null;
		Connection conn = null;
		try{
			tenantId = getTriggerInfo().getTenants();
			if(dbService.isTenantMode()){
				if(tenantId.equals(SchConstant.SCH_GS_TASK_SCHEMA_NAME)){
					dbService.beginTxnById(dbService.defTxn, -1);
					conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
				}else{
					dbService.beginTxn(tenantId);
					conn = dbService.openConn(tenantId);
				}
			}else{
				dbService.beginTxnById(dbService.defTxn, -1);
				conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			}
			Date date = new Date(System.currentTimeMillis());
			List<Object> params = new LinkedList<>();
			params.add(ScheduleUtil.getUUID());
			params.add("test"+System.currentTimeMillis());
			params.add(1);
			params.add(333);
			params.add("job_title"+System.currentTimeMillis());
			params.add(3.44);
			params.add(date);
			params.add(date);
			JdbcTemplate jp = new JdbcTemplate(conn);
			jp.execute("insert into TT_USER(id,name,sex,age,job_title,salary,created_at,updated_at) values(?,?,?,?,?,?,?,?)",params.toArray());
			dbService.endTxn(true);
			try{
				Thread.currentThread().sleep(30000);
			}catch(InterruptedException e){
				log.error(e.getMessage());
				throw e;
			}
		}catch(Exception t){
			dbService.endTxn(false);
			throw new ApplicationException(t.getMessage(), t);
		}finally{
			dbService.clean();
		}
	}

}
