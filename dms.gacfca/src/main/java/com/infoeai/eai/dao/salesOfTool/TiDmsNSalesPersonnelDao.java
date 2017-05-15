package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.f4.common.database.DBService;

@Repository
public class TiDmsNSalesPersonnelDao extends OemBaseDAO {
	
	@Autowired
	DBService dbService;
	
	private static final Logger log = LoggerFactory.getLogger(TiDmsNSalesPersonnelDao.class);

	public void updateIsNotSend() throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update TI_DMS_N_SALES_PERSONNEL_DCS set is_send='0' where is_send='9'");
		log.info("==========updateIsNotSend=======start========");
		try {
			//开启事物
			OemDAOUtil.execBatchPreparement(sql.toString(), null);
			log.info("==========updateIsNotSend=======finish========");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("==========updateIsNotSend=======exception========");
		}
		
	}

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_SALES_PERSONNEL_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT USER_ID as UserID, TC.LMS_CODE as DealerCode, USER_NAME as UserName, ROLE_ID as RoleID, EMAIL as Email, MOBILE as Mobile, ROLE_NAME as RoleName , IS_DCC_VIEW as IsDCCView, USER_STATUS as UserStatus ");
		sql.append("  FROM TI_DMS_N_SALES_PERSONNEL_DCS DNSP  left join TI_DEALER_RELATION DR on DR.dms_code=DNSP.dealer_code left join TM_COMPANY TC on TC.company_code=DR.dcs_code where 1=1 and is_send='0'");
		List<Map > list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public void updateSended() throws Exception {
		StringBuffer sql=new StringBuffer();
		sql.append("update TI_DMS_N_SALES_PERSONNEL_DCS set is_send='1',update_date=sysdate,update_by="+DEConstant.DE_UPDATE_BY+" where is_send='0'");
		//开启事物
			log.info("==========updateIsSended=======start========");
			try {
				OemDAOUtil.execBatchPreparement(sql.toString(), null);
				log.info("==========updateIsSended=======finish========");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("==========updateIsSended=======exception========");
			}
	}

}
