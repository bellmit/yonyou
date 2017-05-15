package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class DMSTODCS004Dao extends OemBaseDAO {

	public long getMaterialId(Logger logger, String ecOrderNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT M.MATERIAL_ID -- 物料ID \n");
		sql.append("  FROM TI_EC_POTENTIAL_CUSTOMER_DCS PC, \n");
		sql.append("       ("+getVwMaterialSql()+") M \n");
		sql.append(" WHERE PC.BRAND_CODE = M.BRAND_CODE \n");
		sql.append("   AND PC.SERIES_CODE = M.SERIES_CODE \n");
		sql.append("   AND PC.MODEL_CODE = M.MODEL_CODE \n");
		sql.append("   AND PC.GROUP_CODE = M.GROUP_CODE \n");
//		sql.append("   AND PC.MODEL_YEAR = M.MODEL_YEAR \n");
		sql.append("   AND PC.COLOR_CODE = M.COLOR_CODE \n");
		sql.append("   AND PC.TRIM_CODE = M.TRIM_CODE \n");
		sql.append("   AND PC.EC_ORDER_NO = '" + ecOrderNo + "' \n");
		
		logger.info(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null != list && list.size() > 0) {
			return Long.parseLong(CommonUtils.checkNull(list.get(0).get("MATERIAL_ID")));
		} else {
			return 0l;
		}
	}

}
