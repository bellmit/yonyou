package com.infoeai.eai.dao.wx;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class WXHiringTaxiesDao extends OemBaseDAO {

	public static Logger logger = LoggerFactory.getLogger(WXHiringTaxiesDao.class);
	
	/**
	 * 功能说明:订车推送
	 * 
	 * @return
	 */
	public List<Map> getWxHtInfo() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tht.HIRING_TAXIES_ID,tht.DEALER_CODE,tht.SO_NO,tht.CUSTOMER_NAME,tht.CUSTOMER_MOBILE,tht.SOLD_BY, \n");
		sql.append("tht.PHONE, tht.CARD_TYPE, tht.CARD_NO,tht.BRAND,tht.CAR_MOBILE, tht.FIRST_COLOR, tht.DELIVER_MODE, \n");
		sql.append("tht.APP_DELIVER_DATE, tht.STOCK_DATE, tht.DEAL_DATE, tht.VIN , tht.IS_UPDATE,tht.IS_SCAN, tht.RESULT_VALUE, \n");
		sql.append(" tht.CREATE_DATE,tht.UPDATE_DATE \n");
		sql.append(" FROM TT_HIRING_TAXIES tht,TM_DEALER TMD \n");
		sql.append("WHERE  tht.DEALER_CODE = TMD.DEALER_CODE \n");
		sql.append(" and (tht.RESULT_VALUE = '001' or tht.RESULT_VALUE  is null)  \n");
		sql.append(" AND DATE_FORMAT(tht.CREATE_DATE, '%Y-%m-%d') =  DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY),'%Y-%m-%d')  ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
}
