package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
@Repository
public class WX03Dao extends OemBaseDAO {

	public static Logger logger = LoggerFactory.getLogger(WX03Dao.class);
	
	/**
	 * 功能说明:车辆信息接口 
	 * 
	 * @return
	 */
	public List<Map> getWX03Info() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select tv.ENGINE_NO,wxve.VEHICLE_ID,wxve.IS_UPDATE,wxve.VIN,TD.DEALER_CODE,TT1.TREE_CODE as BRAND_ID,TT2.TREE_CODE as SERIES_ID,TT4.TREE_CODE as MODEL_ID,T1.COLOR_CODE as COLOR_ID  \n ");
		sql.append(" from tm_vehicle_dec tv,tm_dealer td,TM_VHCL_MATERIAL t1,TM_VHCL_MATERIAL_GROUP_R t2,TM_VHCL_MATERIAL_GROUP tt4,  \n ");
		sql.append(" TM_VHCL_MATERIAL_GROUP tt3,TM_VHCL_MATERIAL_GROUP tt2,TM_VHCL_MATERIAL_GROUP tt1 ,TI_WX_VEHICLE wxve,("+OemBaseDAO.getVwMaterialSql()+") vm  \n ");
		sql.append(" where tv.DEALER_ID = td.DEALER_ID  \n ");
		sql.append(" and tv.MATERIAL_ID = t1.MATERIAL_ID  \n ");
		sql.append(" and t1.MATERIAL_ID = t2.MATERIAL_ID       \n ");
		sql.append(" and tv.MATERIAL_ID = vm.MATERIAL_ID       \n ");
		sql.append(" and t2.GROUP_ID = tt4.GROUP_ID      \n ");
		sql.append(" AND TD.STATUS = '10011001'     \n ");
		sql.append(" and tt3.GROUP_ID = tt4.PARENT_GROUP_ID      \n ");
		sql.append(" and tt2.GROUP_ID = tt3.PARENT_GROUP_ID      \n ");
		sql.append(" and tt1.GROUP_ID = tt2.PARENT_GROUP_ID     \n ");
		sql.append(" and tv.vin=wxve.vin     \n ");
		sql.append(" and (wxve.IS_SCAN = '0' or wxve.IS_SCAN is null)      \n ");
		sql.append(" and (wxve.IS_UPDATE = '0' or wxve.IS_UPDATE is null)     \n ");
		sql.append(" and (not exists (SELECT tc.BRAND_CODE FROM TI_BRAND_CODE_dcs tc WHERE tc.IS_DEL ='0' and tc.BRAND_CODE=vm.BRAND_CODE )) \n ");//过滤FIAT品牌
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	
	
	/**
	 * 功能说明:车辆信息接口 
	 * 
	 * @return
	 */
	public List<Map> getWX03uInfo() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select tv.ENGINE_NO,wxve.VEHICLE_ID,wxve.IS_UPDATE,wxve.VIN,TD.DEALER_CODE,TT1.TREE_CODE as BRAND_ID,TT2.TREE_CODE as SERIES_ID,TT4.TREE_CODE as MODEL_ID,T1.COLOR_CODE as COLOR_ID from tm_vehicle_dec tv,tm_dealer td,TM_VHCL_MATERIAL t1,TM_VHCL_MATERIAL_GROUP_R t2,TM_VHCL_MATERIAL_GROUP tt4, \n ");
		sql.append(" TM_VHCL_MATERIAL_GROUP tt3,TM_VHCL_MATERIAL_GROUP tt2,TM_VHCL_MATERIAL_GROUP tt1 ,TI_WX_VEHICLE wxve, ("+OemBaseDAO.getVwMaterialSql()+") vm  \n ");
		sql.append(" where tv.DEALER_ID = td.DEALER_ID  \n ");
		sql.append(" and tv.MATERIAL_ID = t1.MATERIAL_ID  \n ");
		sql.append(" and t1.MATERIAL_ID = t2.MATERIAL_ID       \n ");
		sql.append(" and tv.MATERIAL_ID = vm.MATERIAL_ID       \n ");
		sql.append(" and t2.GROUP_ID = tt4.GROUP_ID      \n ");
		sql.append(" AND TD.STATUS = '10011001'     \n ");
		sql.append(" and tt3.GROUP_ID = tt4.PARENT_GROUP_ID      \n ");
		sql.append(" and tt2.GROUP_ID = tt3.PARENT_GROUP_ID      \n ");
		sql.append(" and tt1.GROUP_ID = tt2.PARENT_GROUP_ID     \n ");
		sql.append(" and tv.vin=wxve.vin     \n ");
		sql.append(" and (wxve.IS_SCAN = '0' or wxve.IS_SCAN is null)      \n ");
		sql.append(" and wxve.IS_UPDATE = '1'      \n ");
		sql.append(" and (not exists (SELECT tc.BRAND_CODE FROM TI_BRAND_CODE_dcs tc WHERE tc.IS_DEL ='0' and tc.BRAND_CODE=vm.BRAND_CODE ))  \n ");//过滤FIAT品牌
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null); 
		return list;
	}
}
