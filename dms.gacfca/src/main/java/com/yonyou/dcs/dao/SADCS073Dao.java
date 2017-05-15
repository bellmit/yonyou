package com.yonyou.dcs.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS073Dao extends OemBaseDAO {

	/**
	 * 根据vin查询CTM_ID
	 * @param vin
	 * @return
	 */
	public List<Map<String, Object>> searchCtmIdByVin(String vin){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT T2.CTM_ID \n");
		sql.append("  FROM TT_VS_SALES_REPORT T1 \n");
		sql.append("  INNER JOIN TT_VS_CUSTOMER T2 ON T1.CTM_ID = T2.CTM_ID \n");
		sql.append("  INNER JOIN TM_VEHICLE_DEC T3 ON T1.VEHICLE_ID = T3.VEHICLE_ID \n");
		sql.append("  WHERE T3.VIN = '"+vin+"' \n");
		List list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	/**
	 * 根据DMScode查询经销商简称
	 * @param entytyCode
	 * @return
	 */
	public List<Map<String, Object>> searchDealerName(String entytyCode){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT T2.DEALER_SHORTNAME \n");
		sql.append(" FROM TI_DEALER_RELATION T1 \n");
		sql.append(" INNER JOIN TM_DEALER T2 ON T1.DCS_CODE = T2.DEALER_CODE \n");
		sql.append(" WHERE T1.DMS_CODE = '"+entytyCode+"' \n");
		String db = "";
		List list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	/**
	 * 根据下端上报的VIN查询查询车辆是否存在，存在则添加,不存在则不添加 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getVehiceVin(String vin) throws Exception {
		if("".equals(CommonUtils.checkNull(vin))){
			return null;
		}
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT V.VIN\n" );
		sql.append("  FROM TM_VEHICLE_DEC V\n" );
		sql.append(" WHERE V.VIN = '"+vin+"'\n" );
		sql.append("   AND V.IS_DEL = 0");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return map;
	}
	
	
}
