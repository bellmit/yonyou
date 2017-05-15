package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 置换需求(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNSwapDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_SWAP_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("select IFNULL(UNIQUENESS_ID,'') as UniquenessID,cast(IFNULL(FCA_ID,0) as DECIMAL(30,0)) as FCAID,");
		sql.append("IFNULL(TDNS.OWN_BRAND_ID,'') as OwnBrandID,");
		sql.append("IFNULL(TDNS.OWN_MODEL_ID,'') as OwnModelID,");
		sql.append("IFNULL(TDNS.OWN_CAR_STYLE_ID,'') as OwnCarStyleID,IFNULL(OWN_CAR_COLOR,'') as OwnCarColor,");
		sql.append("VIN_CODE as VINCode, IFNULL(DATE_FORMAT(LICENCELSSUE_DATE,'%Y-%m-%d %H:%i:%s'),'') as LicenceIssueDate, ");
		sql.append("cast(IFNULL(TRAVLLED_DISTANCE,0) as DECIMAL(12)) as TravlledDistance, cast(IFNULL(IS_ESTIMATED,0) as DECIMAL(12)) as IsEstimated, IFNULL(ESTIMATED_PRICE,0) as EstimatedPrice, ");
		sql.append("IFNULL(ESTIMATED_ONE,'') as EstimatedOne, IFNULL(ESTIMATED_TWO,'') as EstimatedTwo, IFNULL(DRIVE_LICENSE,'') as DriveLicense,Dealer_User_ID as DealerUserID, TC.LMS_CODE as DealerCode,");
		sql.append("IFNULL(DATE_FORMAT(TDNS.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate ");
		sql.append("  FROM TI_DMS_N_SWAP_DCS TDNS LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDNS.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
