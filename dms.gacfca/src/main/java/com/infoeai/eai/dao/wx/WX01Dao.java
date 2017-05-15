/**
 * @Title: WX01Dao
 * @Description:
 * @Copyright: Copyright (c) 2013
 * @Company: http://autosoft.ufida.com
 * @Date: 2014-3-17
 * @author baojie
 * @version 1.0
 * @remark
 */
package com.infoeai.eai.dao.wx;


import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;


@Repository
public class WX01Dao extends OemBaseDAO {
	public static Logger logger = LoggerFactory.getLogger(WX01Dao.class);

	/**
	 * 功能说明:车主信息接口 创建人: baojie 创建日期: 2014-03-17
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getWX01Info() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select  wx.CUSTOMER_ID,            \n ");
		sql.append("           cus.OWNER_NO,           \n ");
		sql.append("           cus.CTM_NAME,           \n ");
		sql.append("	        cus.MAIN_PHONE,          \n ");
		sql.append("	         (select LMS_ID from TM_REGION_dcs where region_code=cus.PROVINCE LIMIT 0,1) as PROVINCE,    \n ");
		sql.append("	         (select LMS_ID from TM_REGION_dcs where region_code= cus.CITY LIMIT 0,1) as CITY,        \n ");
		sql.append("	         (select LMS_ID from TM_REGION_dcs where region_code= cus.TOWN LIMIT 0,1) as TOWN,      \n ");
		sql.append("	         cus.ADDRESS,            \n ");
		sql.append("	         cus.SEX,                \n ");
		sql.append("	         cus.POST_CODE,          \n ");
		sql.append("	         cus.EMAIL,              \n ");
		sql.append("	         cus.BIRTHDAY,           \n ");
		sql.append("            tmc.LMS_CODE,          \n ");
		sql.append("            ve.VIN,                \n ");
		sql.append("            re.INVOICE_DATE,  \n ");
		sql.append("            re.MILES,              \n ");
		sql.append("	         cus.CTM_TYPE,           \n ");
		sql.append("	         cus.CARD_TYPE,          \n ");
		sql.append("	         cus.CARD_NUM,           \n ");
		sql.append("	         cus.IS_MARRIED,         \n ");
		sql.append("            cus.INCOME,            \n ");
		sql.append("            cus.EDUCATION,         \n ");
		sql.append("	         cus.BEST_CONTACT_TIME,  \n ");
		sql.append("	         cus.HOBBY,              \n ");
		sql.append("	         cus.INDUSTRY_FIRST,     \n ");
		sql.append("	         cus.INDUSTRY_SECOND,    \n ");
		sql.append("	         cus.VOCATION_TYPE,      \n ");
		sql.append("	         cus.POSITION_NAME,      \n ");
		sql.append("	         re.SALES_MAN ,      \n ");
		sql.append("             re.IS_MODIFY         ");
		sql.append("	    FROM TT_VS_CUSTOMER cus ,TI_WX_CUSTOMER wx,TT_VS_SALES_REPORT re ,TM_VEHICLE_dec ve ,("+OemBaseDAO.getVwMaterialSql()+") vm,TM_DEALER tmd,TM_COMPANY tmc \n ");
		sql.append("	    where RE.REPORT_ID=WX.SALES_REPORT_ID AND re.CTM_ID=cus.CTM_ID and re.VEHICLE_ID=ve.VEHICLE_ID AND ve.MATERIAL_ID=vm.MATERIAL_ID  \n ");
		sql.append("        and re.DEALER_ID=tmd.DEALER_ID and tmd.COMPANY_ID = tmc.COMPANY_ID                                          \n ");
		sql.append("        and (WX.IS_SCAN = '0' or WX.IS_SCAN is null)        \n ");
		sql.append("        and (not exists (SELECT tc.BRAND_CODE FROM TI_BRAND_CODE_dcs tc WHERE tc.IS_DEL ='0' and tc.BRAND_CODE=vm.BRAND_CODE)) \n ");//过滤FIAT品牌
		
		List <Map> list = OemDAOUtil.findAll(sql.toString(),null);
		return list;
	}
}
