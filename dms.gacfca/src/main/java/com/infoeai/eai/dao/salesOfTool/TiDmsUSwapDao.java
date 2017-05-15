package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 置换需求(DMS更新)
 * @author luoyang
 *
 */
@Repository
public class TiDmsUSwapDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_U_SWAP_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("select UNIQUENESS_ID as UniquenessID, cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID, ");
		sql.append("IFNULL(TDUS.OWN_BRAND_ID,'') as OwnBrandID, ");
		sql.append("IFNULL(TDUS.OWN_MODEL_ID,'') as OwnModelID, ");
		sql.append("IFNULL(TDUS.OWN_CAR_STYLE_ID,'') as OwnCarStyleID,IFNULL(Own_Car_Color,'') as OwnCarColor, ");
		sql.append("VIN_CODE as VINCode, IFNULL(DATE_FORMAT(LICENCELSSUE_DATE,'%Y-%m-%d %H:%i:%s'),'') as LicenceIssueDate, ");
		sql.append("TRAVLLED_DISTANCE as TravlledDistance, IS_ESTIMATED as IsEstimated, IFNULL(ESTIMATED_PRICE,0) as EstimatedPrice, IFNULL(ESTIMATED_ONE,'') as EstimatedOne,");
		sql.append("IFNULL(ESTIMATED_TWO,'') as EstimatedTwo, IFNULL(DRIVE_LICENSE,'') as DriveLicense,Dealer_User_ID as DealerUserID, TC.LMS_CODE as DealerCode,");
		//sql.append("IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CREATE_DATE, ");
		sql.append("IFNULL(DATE_FORMAT(TDUS.UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UpdateDate ");
		sql.append("  FROM TI_DMS_U_SWAP_DCS TDUS LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDUS.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
