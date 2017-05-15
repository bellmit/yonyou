package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.action.wx.WX04Impl;
import com.yonyou.dms.common.domains.PO.customer.TiWxCustomerManagerBindingPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
@Repository
public class WX04Dao extends OemBaseDAO {

	public static Logger logger = LoggerFactory.getLogger(WX04Impl.class);
	
	/**
	 * 功能说明:新增经销商专属客户经理列表
	 * 
	 * @return
	 */
	public List<Map> getWX04Info() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select MANAGER_ID,DEALER_CODE,SERVICE_ADVISOR,EMPLOYEE_NAME,MOBILE,IS_UPDATE,IS_VALID,IS_SERVICE_ADVISOR,IS_DEFAULT_WX_SA,SEND_TIMES from TI_WX_CUSTOMER_MANAGER_BINDING_dcs \n ");
		sql.append("  where IS_UPDATE='0' and (RESULT_VALUE  <> '001' or RESULT_VALUE  is null) \n ");
		sql.append("   AND (SEND_TIMES<=2 OR SEND_TIMES IS NULL)	\n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null); 
		return list;
	}
	
	
	/**
	 * 功能说明:更新经销商专属客户经理列表
	 * 
	 * @return
	 */
	public List<Map> getWX04uInfo() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select MANAGER_ID,DEALER_CODE,SERVICE_ADVISOR,EMPLOYEE_NAME,MOBILE,IS_UPDATE,IS_VALID,IS_SERVICE_ADVISOR,IS_DEFAULT_WX_SA,SEND_TIMES from TI_WX_CUSTOMER_MANAGER_BINDING_dcs \n ");
		sql.append("  where   IS_UPDATE='1' and (RESULT_VALUE  <> '001' or RESULT_VALUE  is null)  \n ");
		sql.append("   AND (SEND_TIMES<=2 OR SEND_TIMES IS NULL)	\n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null); 
		return list;
	}
}
