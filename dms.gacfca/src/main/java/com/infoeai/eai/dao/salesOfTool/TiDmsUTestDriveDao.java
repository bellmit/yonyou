package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 更新客户信息（试乘试驾）（dms更新）DCS->EAI
 * @author luoyang
 *
 */
@Repository
public class TiDmsUTestDriveDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_U_TEST_DRIVE_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT UNIQUENESS_ID as UniquenessID,cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, IFNULL(DATE_FORMAT(TEST_DRIVE_TIME,'%Y-%m-%d %H:%i:%s'),'') as TestDriveTime,");
		sql.append("(select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDUD.TEST_BRAND_ID limit 1) as TestBrandId,");
		sql.append("(select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDUD.TEST_MODEL_ID limit 1) as TestModelID, ");
		sql.append("(select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDUD.TEST_CAR_STYLE_ID limit 1) as TestCarStyleID, ");
		sql.append(" IFNULL(DRIVE_ROAD_DIC_ID,'') as DriveRoadDicID, IDENTIFICATION_NO as IdentificationNO,Dealer_User_ID as DealerUserID,TC.LMS_CODE as DealerCode, ");
		//sql.append("IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CREATE_DATE, ");
		sql.append("IFNULL(DATE_FORMAT(TDUD.UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UpdateDate ");
		sql.append("  FROM TI_DMS_U_TEST_DRIVE_DCS TDUD LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDUD.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
