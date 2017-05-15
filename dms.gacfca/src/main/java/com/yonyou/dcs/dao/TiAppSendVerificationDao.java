package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TiAppSendVerificationPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class TiAppSendVerificationDao extends OemBaseDAO {

	public List<Map> queryAppSendVerification() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TASV.SEND_VERIFICATION, \n" );
		sql.append("       TASV.FILE_TYPE, \n" );
		sql.append("       TASV.UNIQUENESS_ID, \n" );
		sql.append("       TASV.FAILURE_CODE, \n" );
		sql.append("       TASV.FAIL_REASON, \n" );
		sql.append("       DATE_FORMAT(TASV.EXECUTE_DATE,'%Y-%m-%d %H:%i:%S') AS EXECUTE_DATE, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );// 下端经销商CODE
		sql.append("       DATE_FORMAT(TASV.CREATE_DATE,'%Y-%m-%d %H:%i:%S') AS CREATE_DATE \n" );
		sql.append("   FROM TI_APP_SEND_VERIFICATION TASV \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON TASV.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE TASV.IS_SEND = '0' OR TASV.IS_SEND IS NULL \n" );
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
