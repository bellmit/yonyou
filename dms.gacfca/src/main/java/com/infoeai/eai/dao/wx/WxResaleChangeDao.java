package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class WxResaleChangeDao extends OemBaseDAO {

	public static Logger logger = LoggerFactory.getLogger(WxResaleChangeDao.class);
	
	/**
	 * 功能说明:零售信息变更推送
	 * 
	 * @return
	 */
	public List<Map> getWxRcInfo() {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT TVSR.REPORT_ID,TMD.DEALER_ID,TMD.DEALER_CODE,TMV.VEHICLE_ID,TMV.VIN,TVSR.CTM_ID,TVSR.CONSIGNATION_DATE,TVSR.MILES,TVSR.IS_MODIFY, \n");
		sql.append(" TVC.CTM_NO,TVC.CTM_TYPE,TVC.CTM_NAME,TVC.SEX,TVC.CARD_TYPE,TVC.CARD_NUM,TVC.MAIN_PHONE,TVC.EMAIL,TVC.POST_CODE,TVC.BIRTHDAY, \n");
		sql.append(" TVC.INCOME,TVC.EDUCATION,TVC.IS_MARRIED,TVC.PROVINCE,TVC.CITY,TVC.TOWN,TVC.ADDRESS,TVC.SALES_ADVISER,TVC.BEST_CONTACT_TYPE, \n");
		sql.append(" TVC.HOBBY,TVC.INDUSTRY_FIRST,TVC.INDUSTRY_SECOND,TVC.VOCATION_TYPE,TVC.POSITION_NAME, \n");
		sql.append(" TVSR.CREATE_BY,TVSR.CREATE_DATE,TVSR.UPDATE_BY,TVSR.UPDATE_DATE,TVSR.IS_SCAN,TVSR.RESULT_VALUE \n");
		sql.append(" FROM TT_VS_SALES_REPORT TVSR,TT_VS_CUSTOMER TVC,TM_DEALER TMD,TM_VEHICLE_dec TMV \n");
		sql.append(" WHERE  TVSR.DEALER_ID = TMD.DEALER_ID AND TVSR.VEHICLE_ID = TMV.VEHICLE_ID AND TVSR.CTM_ID = TVC.CTM_ID");
		sql.append(" AND (TVSR.RESULT_VALUE IS NULL OR  TVSR.RESULT_VALUE != '001') AND (TVSR.IS_SCAN  = 0 or TVSR.IS_SCAN  = 1) ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
}
