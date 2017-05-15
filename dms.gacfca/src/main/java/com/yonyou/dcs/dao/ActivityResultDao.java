package com.yonyou.dcs.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class ActivityResultDao extends OemBaseDAO {

	/**
	 * 根据配件代码返回ITEM_NO三包项目
	 * @param partNo
	 * @return
	 */
	public List<Map> queryActivityIds(String activityCodes){
		if(activityCodes==null||"".equals(activityCodes)) return null;

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.ACTIVITY_ID\n" );
		sql.append("  FROM TT_WR_ACTIVITY A\n" );
		sql.append(" WHERE A.ACTIVITY_CODE IN ("+activityCodes+")\n" );

		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);;
		return list;
	}
	/**
	 * 根据配件代码返回ITEM_NO三包项目
	 * @param partNo
	 * @return
	 */
	public List<Map> findActVehComp(String vin,String actCode){

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT * FROM tt_wr_activity_vehicle_complete_dcs\n" );
		sql.append("WHERE VIN='"+vin+"'   \n" );
		sql.append("AND ACTIVITY_CODE = '"+actCode+"'\n" );

		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);;
		return list;
	}
	
	
}
