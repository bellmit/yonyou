/**
 * @Title: WX02Dao
 * @Description:
 * @Copyright: Copyright (c) 2013
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-3-17
 * @author baojie
 * @version 1.0
 * @remark
 */
package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TiWxCustomerManagerPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;


@Repository
public class WX02Dao extends OemBaseDAO {
	public static Logger logger = LoggerFactory.getLogger(WX02Dao.class);

	/**
	 * 功能说明:新增1对1客户经理
	 * 
	 * @return
	 */
	public List<Map> getWX02Info() {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT CM.MANAGER_ID,CM.VIN,CM.DEALER_CODE,CM.SERVICE_ADVISOR,CM.EMPLOYEE_NAME,CM.MOBILE,CM.IS_UPDATE, \n");
		sql.append("CM.DMS_OWNER_ID, CM.CLIENT_TYPE, CM.NAME,CM.CELLPHONE,CM.GENDER, CM.PROVINCE_ID, CM.CITY_ID, \n");
		sql.append("CM.DISTRICT, CM.ADDRESS, CM.POST_CODE, CM.ID_OR_COMP_CODE, CM.EMAIL, CM.BUY_TIME, CM.WX_BIND_TIME,  \n");
		sql.append("TMC.LMS_CODE,CM.SEND_TIMES FROM TI_WX_CUSTOMER_MANAGER CM,TM_DEALER TMD,TM_COMPANY TMC \n");
		sql.append("WHERE  CM.DEALER_CODE = TMD.DEALER_CODE AND TMD.COMPANY_ID = TMC.COMPANY_ID");
		sql.append(" and  CM.IS_UPDATE='0' and (CM.RESULT_VALUE  <> '001' or CM.RESULT_VALUE  is null)  ");
		sql.append("   AND (CM.SEND_TIMES<=2 OR CM.SEND_TIMES IS NULL)	\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	
	/**
	 * 功能说明:更新1对1客户经理
	 * 
	 * @return
	 */
	public List<Map> getWX02uInfo() {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT CM.MANAGER_ID,CM.VIN,CM.DEALER_CODE,CM.SERVICE_ADVISOR,CM.EMPLOYEE_NAME,CM.MOBILE,CM.IS_UPDATE, \n");
		sql.append("CM.DMS_OWNER_ID,CM.NAME,CM.CELLPHONE,CM.DISPATCH_TIME,TMC.LMS_CODE,CM.SEND_TIMES  \n");
		sql.append("FROM TI_WX_CUSTOMER_MANAGER CM,TM_DEALER TMD,TM_COMPANY TMC WHERE  CM.DEALER_CODE = TMD.DEALER_CODE AND TMD.COMPANY_ID = TMC.COMPANY_ID");
		sql.append(" and CM.IS_UPDATE='1' and (CM.RESULT_VALUE  <> '001' or CM.RESULT_VALUE  is null)  ");
		sql.append("   AND (CM.SEND_TIMES<=2 OR CM.SEND_TIMES IS NULL)	\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
}
