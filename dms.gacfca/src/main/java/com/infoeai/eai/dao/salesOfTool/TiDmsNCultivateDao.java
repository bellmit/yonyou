package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 客户培育(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNCultivateDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_CULTIVATE_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT UNIQUENESS_ID as UniquenessID,cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, IFNULL(DATE_FORMAT(COMM_DATE,'%Y-%m-%d %H:%i:%s'),'') as CommData, COMM_TYPE as CommType, IFNULL(COMM_CONTENT,'') as CommContent, FOLLOW_OPP_LEVEL_ID as FollowOppLevelID,IFNULL(DATE_FORMAT(NEXT_COMM_DATE,'%Y-%m-%d %H:%i:%s'),'') as NextCommDate, ");
		sql.append("IFNULL(NEXT_COMM_CONTENT,'') as NextCommContent,");
		//sql.append("IFNULL(DOMANT_TYPE,'') as DormantType,");
		//sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNC.COMPARE_BRAND_ID),'') as CompareBrandID,");
		//sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNC.COMPARE_MODEL_ID),'') as CompareModelID,");
		//sql.append("IFNULL(DATE_FORMAT(GIVE_UP_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as GiveUpDate, GIVE_UP_REASON as GiveUpReason,");
		sql.append("Dealer_User_ID as DealerUserID, TC.LMS_CODE as DealerCode, ");
		sql.append("IFNULL(DATE_FORMAT(TDNC.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate ");
		//sql.append("IFNULL(DATE_FORMAT(UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as UPDATE_DATE ");
		sql.append("  FROM TI_DMS_N_CULTIVATE_DCS TDNC LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDNC.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
