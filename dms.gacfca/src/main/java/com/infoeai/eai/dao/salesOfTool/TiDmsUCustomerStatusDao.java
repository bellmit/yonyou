package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 休眠，订单，交车客户状态数据传递(DMS更新)
 * @author luoyang
 *
 */
@Repository
public class TiDmsUCustomerStatusDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_U_CUSTOMER_STATUS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT  UNIQUENESS_ID as UniquenessID, cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, OPP_LEVEL_ID as OppLevelID, IFNULL(GIVE_UP_TYPE,'') as GiveUpType, IFNULL(COMPARE_CAR,'') as CompareCar, IFNULL(GIVE_UP_REASON,'') as GiveUpReason, ");
		sql.append("IFNULL(DATE_FORMAT(GIVE_UP_DATE,'%Y-%m-%d %H:%i:%s'),'') as GiveUpDate, IFNULL(DATE_FORMAT(ORDER_DATE,'%Y-%m-%d %H:%i:%s'),'') as OrderDate,");
		sql.append("IFNULL(DATE_FORMAT(BUY_CAR_DATE,'%Y-%m-%d %H:%i:%s'),'') as BuyCarDate,Dealer_User_ID as DealerUserID,TC.LMS_CODE as DealerCode ");
		//sql.append("IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CREATE_DATE,");
		//sql.append("IFNULL(DATE_FORMAT(UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UPDATE_DATE ");
		sql.append("  FROM TI_DMS_U_CUSTOMER_STATUS  TDUC LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDUC.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
