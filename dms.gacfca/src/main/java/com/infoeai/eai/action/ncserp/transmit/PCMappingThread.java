/**
 * 
 */
package com.infoeai.eai.action.ncserp.transmit;

import java.sql.Connection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;

import com.infoeai.eai.action.ncserp.impl.PCMappingExecutorImpl;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.f4.common.database.DBService;

/**
 * @author Administrator
 *
 */
public class PCMappingThread extends Thread {
	
	private final static Logger logger = Logger.getLogger(PCMappingThread.class);
	
	protected DBService dbService ;
	
	@Autowired
	public void setDbService(DBService dbService) {
		this.dbService = dbService;
	}

	@Autowired
    private TenantDealerMappingService tenantDealerSerivce;
    
	/**
	 * 获取租户节点
	 * @param tenantId
	 */
	public String getTenantId() {
		Map<String,Map<String,String>> tenantDealerMaping = tenantDealerSerivce.getAll();
		return tenantDealerMaping.get(OemDictCodeConstants.OEM_LOGING_DEALERCODE).get("TENANT_ID");
	}
	
	public PCMappingThread(String name){
		super(name);
	}
	
	public void run() {
		logger.info("====================PCMappingThread 开始执行...===================");
		try{
			mapping();
		}catch(Throwable t){
			logger.error("=================>>PCMappingThread 执行报错", t );
		}
		logger.info("====================PCMappingThread 执行结束.===================");
	}
	
	public void mapping(){
		 //开始PC映射DCS
		 PCMappingExecutorImpl pcMappImpl = null;
		   try{
			   if (dbService.isTenantMode()) {
					logger.debug("dbService.beginTxn with TenantId");
				    dbService.beginTxn(getTenantId());
				    logger.debug("dbService openConn");
				    Connection conn = dbService.openConn(getTenantId());
				    Base.attach(conn);
				} else {
					logger.debug("dbService.beginTxn ");
					dbService.beginTxn();
					logger.debug("dbService openConn");
					Connection conn = dbService.openConn(getTenantId());
					Base.attach(conn);
				}
			   logger.info("====================映射DCS事物创建===================");
			   
			   pcMappImpl = new PCMappingExecutorImpl();
			   pcMappImpl.pcMappingDCS();
			   logger.info("====================映射DCS事物提交===================");
			   dbService.endTxn(true);
		   }catch (Throwable t) {
				logger.error("===========返回Servlet结果状态：=========映射失败 ",t );
				try {
					dbService.endTxn(false);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}finally{
				logger.info("====================映射DCS事物关闭===================");
				dbService.clean();
			}
	 }
}
