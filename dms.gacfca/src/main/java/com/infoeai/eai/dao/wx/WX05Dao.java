package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.action.wx.WX05Impl;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class WX05Dao extends OemBaseDAO {
	
	public static Logger logger = LoggerFactory.getLogger(WX05Impl.class);
	
	/**
	 * 功能说明:保养信息  
	 * 
	 * @return
	 */
	public List<Map> getWX05Info() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select MT.MAINTAIN_ID,TMC.COMPANY_CODE,RE.VIN,RE.REPAIR_NO,RE.REPORT_MAN,RE.MAKE_DATE,RE.OUT_MILEAGE,RE.ORDER_TYPE,RE.REPAIR_TYPE from TI_WX_MAINTAIN_dcs MT,TT_WR_REPAIR_dcs  RE,TM_DEALER TMD,TM_COMPANY TMC   \n ");
		sql.append(" WHERE  RE.DEALER_ID = TMD.DEALER_ID AND TMD.COMPANY_ID = TMC.COMPANY_ID \n ");
		sql.append(" AND  MT.REPAIR_ID=RE.REPAIR_ID and (MT.IS_SCAN = '0' or MT.IS_SCAN is null) \n ");
		//sql.append(" AND  RE.REPAIR_TYPE IN ('40011001','40011002','40011003','40011004','40011005','40011007','40011008','40011009','40011010','40011011','40011012','40011013','40011014','40011016','40011017','40011018') \n ");
		sql.append(" AND  RE.REPAIR_TYPE IN ("+OemDictCodeConstants.REPAIR_FIXED_TYPE_01.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_02.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_03.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_04.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_05.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_07.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_08.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_09.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_10.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_11.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_12.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_13.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_14.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_16.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_17.toString()+","+OemDictCodeConstants.REPAIR_FIXED_TYPE_18.toString()+") \n ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
}
