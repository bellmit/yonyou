package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 二手车（现有车辆）品牌信息(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNUsedCarBrandDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TM_UC_BRAND WHERE 1=1 and IS_SEND=9");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT MAKE_CODE as ManufacturerID,MAKE_NAME as ManufacturerName,");
		sql.append("IFNULL(UC.BRAND_CODE,'') as BrandCode,BRAND_NAME as BrandName,");
		sql.append("IFNULL(UC.SERIES_CODE,'') as SeriesCode,SERIES_NAME as SeriesName,");
		sql.append("IFNULL(UC.MODEL_CODE,'') as ModelCode ,MODEL_NAME as ModelName,ENABLE as IsValid, ");
		sql.append("IFNULL(DATE_FORMAT(UC.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate,");
		sql.append("IFNULL(DATE_FORMAT(UC.UPDATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as UpdateDate  ");
		sql.append("  FROM TM_UC_BRAND  UC WHERE IS_SEND=9");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
