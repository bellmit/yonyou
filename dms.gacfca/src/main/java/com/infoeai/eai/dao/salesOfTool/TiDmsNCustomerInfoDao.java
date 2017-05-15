package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 *客户接待信息/需求分析(DMS新增)
 *@author luoyang
 *
 */
@Repository
public class TiDmsNCustomerInfoDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_CUSTOMER_INFO_DCS WHERE 1=1 and is_send='0'");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT UNIQUENESS_ID as UniquenessID, IFNULL(CLIENT_TYPE,'10181001') as ClientType, NAME as Name, GENDER as Gender, PHOME as Phone, IFNULL(TELEPHONE,'') as Telephone, ");
		sql.append(" (select REGION_ID from TM_REGION_DCS where region_code=TNCI.PROVINCE_ID limit 1) as ProvinceID, ");
		sql.append(" (select REGION_ID from TM_REGION_DCS where region_code=TNCI.CITY_ID limit 1) as CityID, ");
		sql.append(" IFNULL(DATE_FORMAT(BIRTHDAY,'%Y-%m-%d'),'') as Birthday, OPP_LEVEL_ID as OppLevelID, SOURCE_TYPE as SourceType,");
		sql.append(" TC.LMS_CODE as DealerCode, DEALER_USER_ID as DealerUserID,IFNULL(BUY_CAR_BUGGET,'') as BuyCarBudget,");
		sql.append(" IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.BRAND_ID limit 1),'') as BrandID, ");
		sql.append(" IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.MODEL_ID limit 1),'') as ModelID, ");
		sql.append(" IFNULL((select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = TNCI.CAR_STYLE_ID limit 1),'') as CarStyleID,");
		sql.append(" IFNULL(INTENT_CAR_COLOR,'') as IntentCarColor, IFNULL(BUY_CARCONDITION,0) as BuyCarcondition,IFNULL(SECOND_SOURCE_TYPE,'') as SecondSourceType,");
		/* add 新增是否到店与到店日期 by xiawei 2016-06-20 start */
		sql.append(" IFNULL(TNCI.IS_TO_SHOP,'12781002') as IsToShop,IFNULL(DATE_FORMAT(TNCI.TIME_TO_SHOP,'%Y-%m-%d %H:%i:%s'),'') as TimeToShop, ");
		/* add 新增是否到店与到店日期 by xiawei 2016-06-20 end */
		sql.append(" IFNULL(DATE_FORMAT(TNCI.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate ");
		//sql.append("IFNULL(DATE_FORMAT(UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as UpdateDate ");
		sql.append("  FROM TI_DMS_N_CUSTOMER_INFO_DCS TNCI left join TI_DEALER_RELATION DR on DR.dms_code=TNCI.dealer_code left join TM_COMPANY TC on TC.company_code=DR.dcs_code where 1=1 and is_send='0'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
