package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 试乘试驾(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNTestDriveDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_TEST_DRIVE_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT UNIQUENESS_ID as UniquenessID,cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, IFNULL(DATE_FORMAT(TEST_DRIVE_TIME,'%Y-%m-%d %H:%i:%s'),'') as TestDriveTime, ");
		sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNT.TEST_BRAND_ID limit 1),'') as TestBrandId, ");
		sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNT.TEST_MODEL_ID limit 1),'') as TestModelID,");
		sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNT.TEST_CAR_STYLE_ID limit 1),'') as TestCarStyleID,");
		sql.append("IFNULL(DRIVE_ROAD_DIC_ID,'') as DriveRoadDicID, IFNULL(IDENTIFICATION_NO,'') as IdentificationNO, DEALER_USER_ID as DealerUserID ,TC.LMS_CODE as DealerCode, ");
		sql.append("IFNULL(DATE_FORMAT(TDNT.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate ");
		sql.append("  FROM TI_DMS_N_TEST_DRIVE_DCS TDNT left join TI_DEALER_RELATION DR on DR.dms_code=TDNT.dealer_code left join TM_COMPANY TC on TC.company_code=DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
