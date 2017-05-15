package com.infoeai.eai.action;

import java.sql.Connection;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.common.domains.PO.basedata.MsgIdPO;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.common.database.DBService;

public class BaseService {

	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
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
	
	public void beginDbService(){
			try {
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
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceBizException("开启事务异常");
			}
	}
	public void dbService(){
		try {
			if(dbService.isTenantMode()) {
				dbService.beginTxn(getTenantId());
			} else {
				dbService.beginTxn();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("开启事务异常");
		}
	}
	
	/**
	 * 获取Msg_id
	 */
	public Long getMsgId(){
		MsgIdPO po = new MsgIdPO();
		po.saveIt();
		return po.getLongId();
	}
	
}
