package com.yonyou.dms.vehicle.dao.claimManage;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
* @author liujm
* @date 2017年5月11日
*/


@Repository
public class DBLockDao extends OemBaseDAO{
	
	
	
	/*
	 * 创建锁
	 * @param lockId
	 * @param businessType 
	 */
	@SuppressWarnings("unchecked")
	public void createLock(String lockId,String businessType){
		
		StringBuilder sql= new StringBuilder();
		sql.append("INSERT INTO BUSINESSLOCK_DCS (LOCK_ID,BUSINESS_TYPE,CREATEDATE)\n" );
		sql.append("SELECT '"+lockId+"','"+businessType+"',CURRENT_TIMESTAMP\n" );
		sql.append("FROM sysibm.sysdummy1\n" );
		sql.append("WHERE 1=1\n" );
		sql.append("AND NOT EXISTS (SELECT B.LOCK_ID,B.BUSINESS_TYPE FROM BUSINESSLOCK_DCS B\n" );
		sql.append("WHERE B.LOCK_ID = '"+lockId+"'\n" );
		sql.append("AND B.BUSINESS_TYPE = '"+businessType+"')");
		
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList());
		
	}
	
	/**
	 * 取得锁
	 * @param lockId
	 * @param businessType
	 */
	@SuppressWarnings("unchecked")
	public boolean getLock(String lockId,String businessType){
		boolean flag = false;
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT B.LOCK_ID,B.BUSINESS_TYPE FROM BUSINESSLOCK_DCS B\n" );
		sql.append("WHERE B.LOCK_ID = '"+lockId+"'\n" );
		sql.append("AND B.BUSINESS_TYPE = '"+businessType+"'\n");
		sql.append("FOR UPDATE ");

		//this.pageQuery(sql.toString(), null, this.getFunName());
		List<Object> params = new ArrayList<Object>();
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(list != null && list.size() > 0){
			flag = true;
		}
		
		return flag;
	}
	
	/**
	 * 释放锁
	 * @param lockId
	 * @param businessType
	 */
	@SuppressWarnings("unchecked")
	public void freeLock(String lockId,String businessType){
		StringBuilder sql= new StringBuilder();
		sql.append("DELETE FROM BUSINESSLOCK_DCS B\n" );
		sql.append("WHERE B.LOCK_ID = '"+lockId+"'\n" );
		sql.append("AND B.BUSINESS_TYPE = '"+businessType+"'\n");		
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList());
		
	}
}
