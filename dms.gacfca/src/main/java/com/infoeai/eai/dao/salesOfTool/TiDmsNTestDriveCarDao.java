package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 试驾车辆信息(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNTestDriveCarDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_TEST_DRIVE_CAR_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT TC.LMS_CODE as DealerCode,Dealer_User_ID as DealerUserID, VIN_CODE as VINCode,LICENSE_CODE as LicenseCode, CAR_STATURS as CarStatus,");
		sql.append("IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TDNTD.MODEL_ID),'') as ModelID,TDNTD.Model_Name as ModelName, ");
		sql.append("IFNULL(DATE_FORMAT(TDNTD.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate, ");
		sql.append("IFNULL(DATE_FORMAT(TDNTD.UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UpdateDate ");
		sql.append("  FROM TI_DMS_N_TEST_DRIVE_CAR_DCS TDNTD LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDNTD.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
