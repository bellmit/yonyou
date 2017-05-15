package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 客户培育(DMS更新)
 * @author luoyang
 *
 */
@Repository
public class TiDmsUCultivateDao {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_U_CULTIVATE_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT UNIQUENESS_ID as UniquenessID,cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, IFNULL(DATE_FORMAT(COMM_DATE,'%Y-%m-%d %H:%i:%s'),'') as CommData, COMM_TYPE as CommType, IFNULL(COMM_CONTENT,'') as CommContent, FOLLOW_OPP_LEVEL_ID as FollowOppLevelID,IFNULL(DATE_FORMAT(NEXT_COMM_DATE,'%Y-%m-%d %H:%i:%s'),'') as NextCommDate,  ");
		sql.append(" IFNULL(NEXT_COMM_CONTENT,'') as NextCommContent,Dealer_User_ID as DealerUserID, TC.LMS_CODE as DealerCode, ");
		//sql.append("IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CREATE_DATE, ");
		sql.append("IFNULL(DATE_FORMAT(TDUC.UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UpdateDate ");
		sql.append("  FROM TI_DMS_U_CULTIVATE_DCS TDUC LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDUC.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
