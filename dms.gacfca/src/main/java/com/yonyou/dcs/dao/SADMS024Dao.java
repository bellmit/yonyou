package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADMS024Dao extends OemBaseDAO {
	
	/**查询下发数据
	 * @return
	 * 
	 */
	public List<Map> querySendDmsInfoById(String limitId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT LIMITED_ID, LIMITED_NAME, LIMITED_RANGE, LM.DEALER_CODE, DESCEND_STATUS, DESCEND_DATE, \n");
		sql.append(" BRAND, SERIES,LM.SERIES_CODE, REPAIR_TYPE, COMMENT, LM.CREATE_DATE,TM.DEALER_SHORTNAME,TR.DMS_CODE as ENTITY_CODE \n");
		sql.append("  FROM TM_LIMITE_CPOS LM  \n");
		sql.append("  INNER JOIN TM_DEALER TM  on  TM.DEALER_CODE =LM.DEALER_CODE \n");
		sql.append("  INNER JOIN TI_DEALER_RELATION  TR on TR.DCS_CODE=replace(LM.DEALER_CODE,'A','')");
		sql.append("  WHERE 1=1  \n");
		sql.append("  AND LM.LIMITED_ID=" + limitId);
		List<Map> listMap=OemDAOUtil.findAll(sql.toString(), null);
		return listMap;
	}
	
	/**
	 * 根据车系code查找品牌code
	 * @param seriesCode
	 * @return
	 */
	public List<String> findBrandBySeriesCode(String seriesCode){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T1.GROUP_ID,T1.GROUP_NAME,T1.GROUP_CODE  \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP T1,TM_VHCL_MATERIAL_GROUP T2 WHERE T1.GROUP_ID = T2.PARENT_GROUP_ID AND T2.GROUP_LEVEL = 2 \n");
		sql.append("  AND  T2.STATUS = 10011001 \n");
		sql.append("  AND  T2.GROUP_CODE in ('" + seriesCode + "') ");
		List<Map> listMap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> blist=new ArrayList<>();
		if(null!=listMap&&listMap.size()>0){
			for(Map map:listMap){
				blist.add(CommonUtils.checkNull(map.get("GROUP_CODE")));
			}
		}
		return blist;
	}
}
