/*
 * Copyright (c) 2010 Yonyou Auto, Inc. All  Rights Reserved.
 * This software is published under the terms of the Yonyou Auto Information Technology (Shanghai) Co., Ltd
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * 
 * CreateDate  ： 2016年5月26日 下午5:22:10
 * CreateBy    ： MjieQiu
 * ProjectName ：schedule
 * PackageName ：com.yonyou.schedule.task
 * File_name   ：TaskGenetor.java
 * Type_name   ：TaskGenetor
 * Comment     ：			    
 */
package com.yonyou.dms.schedule.test.f4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yonyou.dms.function.exception.ApplicationException;
import com.yonyou.f4.common.database.JdbcTemplate;
import com.yonyou.f4.common.database.impl.DBServiceImpl;
import com.yonyou.f4.schedule.SchConstant;
import com.yonyou.f4.schedule.db.ParameterPreparedStatementSetter;
import com.yonyou.f4.schedule.db.SchJdbc;
import com.yonyou.f4.schedule.exception.SchDaoException;
import com.yonyou.f4.schedule.impl.ScheduleServiceImpl;
import com.yonyou.f4.schedule.task.QuartzService;
import com.yonyou.f4.schedule.task.vos.TaskInfo;
import com.yonyou.f4.schedule.task.vos.TriggerInfo;
import com.yonyou.f4.schedule.utils.LogUtil;
import com.yonyou.f4.schedule.utils.ScheduleUtil;

/**
 * @author MjieQiu
 *
 */
public class TaskGenetor {
	
	private final static Logger log = LogUtil.getLogger("TaskGenetor");	

	private DBServiceImpl dbService;
	private SchConstant schConstant;
	
	private QuartzService quartzService;
	private ScheduleServiceImpl schService;
	
	public TaskGenetor(ApplicationContext appCtx){
		this.dbService = (DBServiceImpl)appCtx.getBean("DBService");
		this.schConstant = (SchConstant)appCtx.getBean("schConstant");
		schService = (ScheduleServiceImpl)appCtx.getBean("scheduleService");
		this.quartzService = schService.getQuartzService();
		
	}
	public TriggerInfo init(int idx,Class jobClz,String cron,int type,String tenantId){
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setCreateBy("test");
		taskInfo.setCreateDate(new Date());
		taskInfo.setStatus(1);
		taskInfo.setTaskClass(jobClz.getCanonicalName());
		taskInfo.setTaskName(jobClz.getSimpleName()+"-"+idx);
		String tId = ScheduleUtil.getUUID();
		taskInfo.setTaskId(tId);
		
		TriggerInfo triggerInfo = new TriggerInfo();
		triggerInfo.setCreateBy("test");
		triggerInfo.setCreateDate(new Date());
		triggerInfo.setCronExpression(cron);
		triggerInfo.setDuration(1000l);
		triggerInfo.setIsParallel(1);
		triggerInfo.setStatus(1);
		triggerInfo.setTaskInfo(taskInfo);
		triggerInfo.setTenants(tenantId);
		triggerInfo.setTaskPlanDesc("say hello task test" + idx);
		triggerInfo.setTaskPlanName("first test trigger" + idx);
		triggerInfo.setTaskType(type);//0-一次性；1-长期执行
		if(triggerInfo.getTaskType()==0){
			triggerInfo.setStartDate(new Date(System.currentTimeMillis() - 1*60*1000));
			triggerInfo.setIsRun(0);
		}
		String tpId = ScheduleUtil.getUUID();
		triggerInfo.setTaskPlanId(tpId);
		try{
			dbService.beginTxnById(dbService.defTxn, schConstant.getTxnTimeout());
			Connection conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			saveTask(conn,taskInfo);
			saveTaskPlan(conn, triggerInfo);
			dbService.endTxn(true);
		}catch(Exception e){
			try {
				dbService.endTxn(false);
			} catch (Exception e1) {
				log.error(e1.getMessage(),e1);
			}
			log.error(e.getMessage(),e);
		}finally{
			dbService.clean();
		}
		return triggerInfo;
	}
	
	private String saveTaskPlan(Connection conn,final TriggerInfo triggerInfo){
		String insertSql = "insert into F4_TRIGGER_TASK (task_plan_id,create_by,create_date,cron_expression,duration,is_parallel,"
				+ "status,task_id,task_plan_desc,task_plan_name,task_type";
		Object[] objs = new Object[]{triggerInfo.getTaskPlanId(),triggerInfo.getCreateBy(),new Timestamp(triggerInfo.getCreateDate().getTime()),triggerInfo.getCronExpression()
				,triggerInfo.getDuration(),triggerInfo.getIsParallel(),triggerInfo.getStatus(),triggerInfo.getTaskInfo().getTaskId()
				,triggerInfo.getTaskPlanDesc(),triggerInfo.getTaskPlanName(),triggerInfo.getTaskType()};
		List<Object> params = new LinkedList<>();
		params.addAll(Arrays.asList(objs));
		
		if(triggerInfo.getTaskType()==0){
			insertSql +=",start_date,is_run";
		}
		if(triggerInfo.getTenants()!=null&&!triggerInfo.getTenants().trim().equals("")){
			insertSql +=",tenants";
		}
		insertSql += ") values(?,?,?,?,?,?,?,?,?,?";
		if(triggerInfo.getTaskType()==0){
			insertSql += ",?,?";
			params.add(triggerInfo.getStartDate());
			params.add(triggerInfo.getIsRun());
		}
		if(triggerInfo.getTenants()!=null&&!triggerInfo.getTenants().trim().equals("")){
			insertSql +=",?";
			params.add(triggerInfo.getTenants());
		}
		insertSql += ",?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(conn);
		try{
		    jdbcTemplate.execute(insertSql,params.toArray());
		}catch(Exception e){
		    throw new ApplicationException(e.getMessage(),e);
		}
		
		return triggerInfo.getTaskPlanId();
	}

	public String saveTask(Connection conn,final TaskInfo taskInfo){
		final String insertSql = "insert into f4_task_info (task_id,task_name,task_class,create_date,create_by,status) values(?,?,?,?,?,?)";
		Object[] params = new Object[]{taskInfo.getTaskId(),taskInfo.getTaskName(),taskInfo.getTaskClass(),new Timestamp(taskInfo.getCreateDate().getTime())
				,taskInfo.getCreateBy(),taskInfo.getStatus()};
		JdbcTemplate jdbcTemplate = new JdbcTemplate(conn);
		try{
		    jdbcTemplate.execute(insertSql,params);
        }catch(Exception e){
            throw new ApplicationException(e.getMessage(),e);
        }
		return taskInfo.getTaskId();
	}

	public void pauseTrigger(String tenantId){
		quartzService.pauseTenant(tenantId);
	}
	public void resumeTrigger(String tenantId){
		quartzService.resumeTenant(tenantId);
	}
	public void resumeTrigger(TriggerInfo triggerInfo){
		quartzService.resumeTrigger(triggerInfo);
	}
	public void delTrigger(TriggerInfo triggerInfo){
		quartzService.delTrigger(triggerInfo);
	}
	public void delTrigger(String tenantId){
		quartzService.delTenant(tenantId);
	}
	
	public List<Object[]> loadTenantInfos(){
		List<String> retList = null;
		try{
			dbService.beginTxnById(dbService.defTxn, schConstant.getTxnTimeout());
			Connection conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			JdbcTemplate jdbcTemplate = new JdbcTemplate(conn);
			retList = jdbcTemplate.query("SELECT TI.TENANT_ID FROM F4_TENANT_INFO ti WHERE TI.TENANT_STATUS = 1", 
					new JdbcTemplate.RowMapper<String>(){

						
						public String mapper(ResultSet rs) throws Exception {
							return rs.getString("TENANT_ID");
						}});
			dbService.endTxn(true);
		}catch(Exception t){
			try {
				dbService.endTxn(false);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			log.error(t.getMessage(),t);
		}finally{
			dbService.clean();
		}
		List<Object[]> objList = new LinkedList<>();
//		for(String str : retList){
//			objList.add(new Object[]{str});
//		}
		objList.add(new Object[]{SchConstant.SCH_GS_TASK_SCHEMA_NAME});
		objList.add(new Object[]{"T140"});
		objList.add(new Object[]{"T150"});
		return objList;
	}
	public List<Object[]> initTenantInfo(){
		List<Object[]> pobj = new LinkedList<>();
		for(int i=0;i<10;i++){
			pobj.add(new Object[]{"T"+i,"TENANT_T"+i,1,"dataSource","GLOBAL_SCHEMA"});
		}
		
		try{
			dbService.beginTxnById(dbService.defTxn, schConstant.getTxnTimeout());
			Connection conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			SchJdbc jdbcTemplate = new SchJdbc(conn);
			int[][] ret = jdbcTemplate.batchUpdate("insert into f4_tenant_info (TENANT_ID,TENANT_NAME,TENANT_STATUS,DATASOURCE_NAME,SCHEMA_NAME) VALUES (?,?,?,?,?)", 5, pobj, new ParameterPreparedStatementSetter<Object[]>(){
				
				
				public void setParam(PreparedStatement ps, Object[] obj) throws SchDaoException {
					try {
						ps.setString(1, obj[0].toString());
						ps.setString(2, obj[1].toString());				
						ps.setInt(3, Integer.parseInt(obj[2].toString()));				
						ps.setString(4, obj[3].toString());				
						ps.setString(5, obj[4].toString());				
					} catch (SQLException e) {
						throw new SchDaoException(e.getMessage(),e);
					}				
				}});
			dbService.endTxn(true);
		}catch(Exception t){
			try {
				dbService.endTxn(false);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			log.error(t.getMessage(),t);
		}finally {
			dbService.clean();
		}
		return pobj;
	}
	
	/**
	 * 多租户模式下任务初始化。
	 */
	public void initTaskForTeanant(){
		List<String> retList = null;
		try{
			dbService.beginTxnById(dbService.defTxn, schConstant.getTxnTimeout());
			Connection conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			SchJdbc jdbcTemplate = new SchJdbc(conn);
			retList = jdbcTemplate.query("SELECT TI.TENANT_ID FROM F4_TENANT_INFO ti WHERE TI.TENANT_STATUS = 1 ", //AND TI.TENANT_ID IN('T10','T1','T150','T140')
					new JdbcTemplate.RowMapper<String>(){

						
						public String mapper(ResultSet rs) throws Exception {
							return rs.getString("TENANT_ID");
						}});
			dbService.endTxn(true);
		}catch(Exception t){
			try {
				dbService.endTxn(true);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			log.error(t.getMessage(),t);
		}finally{
			dbService.clean();
		}
		if(retList!=null){
		    retList.add(SchConstant.SCH_GS_TASK_SCHEMA_NAME);
	        for(int j = 0; j < retList.size();j++ ){
	            for(int i = 0; i < 1;i++){
	                init(i,SayHelloJob.class,"8/30 * * * * ?",0,retList.get(j));//once task
	                init(i,OperatorDBJob.class,"5/10 * * * * ?",1,retList.get(j));
//	              init(i,OperatorDBJob.class,"5/10 * * * * ?",1,retList.get(j));
//	              init(i,GSTask.class,"15/20 * * * * ?",1,retList.get(j));
	                init(i,TSTask.class,"10/10 * * * * ?",1,retList.get(j));
	                if(retList.get(j).equals(SchConstant.SCH_GS_TASK_SCHEMA_NAME)){
	                    //初始化全局任务
	                    init(1,GSTask.class,"15/20 * * * * ?",1,SchConstant.SCH_GS_TASK_SCHEMA_NAME);
	                }
	            }
	        }
		}
	}

	public void delTriggerByTenants(List<Object[]> pobj){
		for(Object[] array : pobj){
			delTrigger(array[0].toString());
		}
	}
	public void pauseTriggerByTenants(List<Object[]> pobj){
		for(Object[] array : pobj){
			pauseTrigger(array[0].toString());
		}
	}
	
	public List<TriggerInfo> initTask(){
		List<TriggerInfo> retList = new LinkedList<>();
		for(int i = 0; i < 1;i++){
			retList.add(init(i,SayHelloJob.class,"15/5 * * * * ?",1,null));//once task
			retList.add(init(i,GSTask.class,"5/10 * * * * ?",1,null));//once task
			retList.add(init(i,GSTask.class,"5/15 * * * * ?",1,null));
			retList.add(init(i,AnnotationTestTask.class,"10/20 * * * * ?",1,null));
		}
		return retList;
	}
	
	@SuppressWarnings("static-access")
	public void tenantTest(){
//		List<Object[]> tenants = initTenantInfo();//初始化租户信息
		List<Object[]> tenants = loadTenantInfos();//初始化租户信息
		initTaskForTeanant();//初始化任务信息
//		try {
//			Thread.currentThread().sleep(5*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		pauseTriggerByTenants(tenants);//暂停任务
//		try {
//			Thread.currentThread().sleep(5*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		resumeTriggerByTenants(tenants);
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		delTriggerByTenants(tenants);
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		initTaskForTeanant();
//		try {
//			Thread.currentThread().sleep(5*60*1000);//休眠5分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		initTaskForTeanant();
	}
	private void resumeTriggerByTenants(List<Object[]> tenants) {
		for(Object[] array : tenants){
			resumeTrigger(array[0].toString());
		}		
	}

	/**
	 * 非多租户模式测试
	 */
	@SuppressWarnings("static-access")
	public void test(){
		List<TriggerInfo> retList = initTask();
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		log.warn("need pause triggers is {}.",retList.size());
//		pauseTrigger(retList);
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		log.warn("need resume triggers is {}.",retList.size());
//		resumeTrigger(retList);
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		log.warn("need del triggers is {}.",retList.size());
//		delTriggers(retList);
//		try {
//			Thread.currentThread().sleep(1*60*1000);//休眠1分钟
//		} catch (InterruptedException e) {
//			log.error(e.getMessage(),e);
//		}
//		retList = initTask();
	}
	
	private void delTriggers(List<TriggerInfo> retList) {
		quartzService.delTriggers(retList);
	}

	private void resumeTrigger(List<TriggerInfo> retList) {
		for(TriggerInfo ti : retList){
			log.info("resume ti is {}",ti);
//			String nodeId = ti.getNodeId();
//			ti.setNodeId(nodeId==null||nodeId.equals("")?ScheduleUtil.generateID():nodeId);
			quartzService.resumeTrigger(ti);
		}
	}

	private void pauseTrigger(List<TriggerInfo> retList) {
		for(TriggerInfo ti : retList){
			log.info("ti {} pause begin.",ti);
			//ti.setNodeId(nodeId==null||nodeId.equals("")?ScheduleUtil.generateID():nodeId);
			quartzService.pauseTrigger(ti);
		}
	}
	
	public void cleanDB(){
		try{
			dbService.beginTxnById(dbService.defTxn, schConstant.getTxnTimeout());
			Connection conn = dbService.openConnById(dbService.defDs, dbService.defSchema);
			JdbcTemplate jp = new JdbcTemplate(conn);
			int tlcnt = jp.execute("delete from F4_TASK_LOG");
			log.info("del f4_task_log {} records.",tlcnt);
			int tricnt = jp.execute("delete from F4_TRIGGER_TASK");
			log.info("del F4_TRIGGER_TASK {} records.",tricnt);
			int ticnt = jp.execute("delete from F4_TASK_INFO");
			log.info("del f4_task_info {} records.",ticnt);
//			int nicnt = schJdbc.update(conn, "delete from F4_NODE_INFO");
//			log.info("del f4_node_info {} records.",nicnt);
			int tslcnt = jp.execute("delete from F4_TASK_LOCK");
			log.info("del F4_TASK_LOCK {} records.",tslcnt);
//			int cnt = schJdbc.update(conn, "delete from F4_TENANT_INFO");
//			log.info("del f4_tenant_info {} records.",cnt);
			dbService.endTxn(true);
		}catch(Exception t){
			try {
				dbService.endTxn(false);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
			log.error(t.getMessage(),t);
		}finally{
			dbService.clean();
		}
	}

	/**
	 * @param args
	 */
	@Test
	public static void testGenerator() {
		try{
			ApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext_schedule.xml"});
			TaskGenetor tg = new TaskGenetor(appCtx);
			tg.cleanDB();
			if(tg.schConstant.isMultiTenant()){
				tg.tenantTest();
			}else{
				tg.test();
			}
			//tg.schService.destory();
			//log.warn("f4 schedule destory end.");
		}catch(Exception t){
			log.error(t.getMessage(),t);
		}
		
	}

}
