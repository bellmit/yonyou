package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.MaterialInPO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.dealerManager.TiDealerInfoPO;

@Repository
public class SI10Dao extends OemBaseDAO {
	public static Logger logger = LoggerFactory.getLogger(SI10Dao.class);

	/**
	 * 功能说明:物料主数据上传接口
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-22
	 * @return
	 */
	public List<MaterialInPO> getSI10Info() {
		List<MaterialInPO> list = MaterialInPO.find(" IS_SCAN = '0' or IS_SCAN is null ").limit(1000);
		return list;
	}
	
	/**
	 * 功能说明:销售线索反馈上传接口
	 * 创建人: zhangRM 
	 * 创建日期: 2013-06-07
	 * @return
	 * @throws ParseException 
	 */
	public List<TiSalesLeadsFeedbackDcsPO> getSI11Info() throws ParseException {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tslf.SEQUENCE_ID,tslc.ID,tslf.CUSTOMER_CODE, \n");
		sql.append("         tslc.DMS_CUSTOMER_ID, \n");
		sql.append("         tmc.COMPANY_CODE, \n");
		sql.append("         (select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = tslf.BRAND_CODE and GROUP_LEVEL=1) BRAND_CODE, \n");
		sql.append("         (select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = tslf.SERIAS_CODE and GROUP_LEVEL=2) SERIAS_CODE, \n");
		sql.append("         (select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = tslf.MODEL_CODE and GROUP_LEVEL=3) MODEL_CODE, \n");
		sql.append("         (select tree_code from TM_VHCL_MATERIAL_GROUP where group_code = tslf.CONFIGER_CODE and GROUP_LEVEL=4) CONFIGER_CODE, \n");
		sql.append("         tslf.SLEEP_REASON, \n");
		sql.append("         DATE_FORMAT(tslf.SLEEP_TIME,'%Y-%m-%d') SLEEP_TIME, \n");
		sql.append("         tslf.STATUS, \n");
		sql.append("         DATE_FORMAT(tslf.PURCHASE_TIME,'%Y-%m-%d') PURCHASE_TIME, \n");
		sql.append("         tslf.SALES_CONSULTANT, \n");
		sql.append("         tslf.OPPORTUNITY_LEVEL_ID, \n");
		//2015-1-12
		sql.append("         tslf.DEALER_CODE, \n");
		sql.append("         tslf.NAME, \n");
		sql.append("         tslf.GENDER, \n");
		sql.append("         tslf.PHONE, \n");
		sql.append("         tslf.TELEPHONE, \n");
		sql.append("         tslf.PROVINCE_ID ,\n");
		sql.append("         tslf.CITY_ID, \n");
		sql.append("         tslf.CONSIDERATION_ID ,\n");
		sql.append("         DATE_FORMAT(tslf.REGISTER_DATE,'%Y-%m-%d') REGISTER_DATE \n");
		//2015-1-19
		sql.append("    FROM TI_SALES_LEADS_FEEDBACK_dcs tslf LEFT JOIN TI_SALES_LEADS_CUSTOMER_dcs tslc ON  tslf.NID = tslc.NID \n");
		sql.append("    INNER JOIN TM_DEALER tmd ON tslf.DEALER_CODE = tmd.DEALER_CODE \n");
        sql.append("    INNER JOIN  TM_COMPANY tmc ON tmd.COMPANY_ID = tmc.COMPANY_ID \n");
		sql.append("    WHERE (tslf.IS_SCAN = '0' or tslf.IS_SCAN is null)  limit 0,5000 \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
				
		List<TiSalesLeadsFeedbackDcsPO> POlist = new ArrayList<TiSalesLeadsFeedbackDcsPO>();
		TiSalesLeadsFeedbackDcsPO po = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Map map : list) {
			po = new TiSalesLeadsFeedbackDcsPO();
			po.setLong("SEQUENCE_ID", new Long(CommonUtils.checkNull(map.get("SEQUENCE_ID"))));
			po.setLong("NID", new Long(CommonUtils.checkNull(map.get("ID"))==""?"0":CommonUtils.checkNull(map.get("ID"))));
			po.setString("CUSTOMER_CODE", CommonUtils.checkNull(map.get("CUSTOMER_CODE")));
			po.setString("DEALER_CODE", CommonUtils.checkNull(map.get("COMPANY_CODE")));
			po.setString("BRAND_CODE", CommonUtils.checkNull(map.get("BRAND_CODE")));
			po.setString("SERIAS_CODE", CommonUtils.checkNull(map.get("SERIAS_CODE")));
			po.setString("MODEL_CODE", CommonUtils.checkNull(map.get("MODEL_CODE")));
			po.setString("CONFIGER_CODE", CommonUtils.checkNull(map.get("CONFIGER_CODE")));
			po.setString("SLEEP_REASON", CommonUtils.checkNull(map.get("SLEEP_REASON")));
			if(!StringUtils.isNullOrEmpty(map.get("SLEEP_TIME"))){
				po.setTimestamp("SLEEP_TIME", format.parse(CommonUtils.checkNull(map.get("SLEEP_TIME"))));
			}
			po.setString("STATUS", CommonUtils.checkNull(map.get("STATUS")));
			if(!StringUtils.isNullOrEmpty(map.get("PURCHASE_TIME"))){
				po.setTimestamp("PURCHASE_TIME", format.parse(CommonUtils.checkNull(map.get("PURCHASE_TIME"))));
			}
			po.setString("SALES_CONSULTANT", CommonUtils.checkNull(map.get("SALES_CONSULTANT")));
			po.setString("OPPORTUNITY_LEVEL_ID", CommonUtils.checkNull(map.get("OPPORTUNITY_LEVEL_ID")));
			po.setString("DEALER_CODE", CommonUtils.checkNull(map.get("DEALER_CODE")));
			po.setString("NAME", CommonUtils.checkNull(map.get("NAME")));
			po.setInteger("GENDER", Integer.parseInt(CommonUtils.checkNull(map.get("GENDER"))==""?"0":CommonUtils.checkNull(map.get("GENDER"))));
			po.setString("PHONE", CommonUtils.checkNull(map.get("PHONE")));
			po.setString("TELEPHONE", CommonUtils.checkNull(map.get("TELEPHONE")));
			po.setLong("PROVINCE_ID", new Long(CommonUtils.checkNull(map.get("PROVINCE_ID"))==""?"0":CommonUtils.checkNull(map.get("PROVINCE_ID"))));
			po.setLong("CITY_ID", new Long(CommonUtils.checkNull(map.get("CITY_ID"))==""?"0":CommonUtils.checkNull(map.get("CITY_ID"))));
			po.setString("CONSIDERATION_ID", CommonUtils.checkNull(map.get("CONSIDERATION_ID")));
			if(!StringUtils.isNullOrEmpty(map.get("REGISTER_DATE"))){
				po.setTimestamp("REGISTER_DATE", format.parse(CommonUtils.checkNull(map.get("REGISTER_DATE"))));
			}
			POlist.add(po);
		}
		return POlist;
	}
	
	/**
	 * 功能说明:经销商主数据上传接口
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-22
	 * @return
	 */
	public List<TiDealerInfoPO> getDealersInfo() {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT TDI.SEQUENCE_ID, \n");
		sql.append("         TDI.DEALER_CODE, \n");
		sql.append("         TDI.DEALER_NAME, \n");
		sql.append("         TDI.DEALERAB_CN, \n");
		sql.append("         TDI.DEALERAB_EN, \n");
		sql.append("         (SELECT LMS_ID FROM TM_REGION_dcs WHERE REGION_CODE = TDI.CITY_ID limit 0,1) CITY_ID, \n");
		sql.append("         (SELECT LMS_ID FROM TM_REGION_dcs WHERE REGION_CODE = TDI.REALLY_CITY_ID limit 0,1) REALLY_CITY_ID, \n");
		sql.append("         TDI.ADDRESS, \n");
		sql.append("         TDI.SERVICE_TEL, \n");
		sql.append("         TDI.STATUS \n");
		sql.append("    FROM TI_DEALER_INFO TDI \n");
		sql.append("   WHERE (TDI.IS_SCAN = '0' OR TDI.IS_SCAN IS NULL) limit 0,1000 \n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), new ArrayList<Object>());
		List<TiDealerInfoPO> beanList = new ArrayList<>();
		TiDealerInfoPO po = null;
		for (Map map : list) {
			po = new TiDealerInfoPO();
			po.setLong("SEQUENCE_ID",new Long(CommonUtils.checkNull(map.get("SEQUENCE_ID"))));
			po.setInteger("DEALER_CODE",Integer.parseInt(CommonUtils.checkNull(map.get("DEALER_CODE"))==""?"0":CommonUtils.checkNull(map.get("DEALER_CODE"))));
			po.setString("DEALER_NAME",CommonUtils.checkNull(map.get("DEALER_NAME")));
			po.setString("DEALERAB_CN",CommonUtils.checkNull(map.get("DEALERAB_CN")));
			po.setString("DEALERAB_EN",CommonUtils.checkNull(map.get("DEALERAB_EN")));
			po.setInteger("CITY_ID",Integer.parseInt(CommonUtils.checkNull(map.get("CITY_ID"))==""?"0":CommonUtils.checkNull(map.get("CITY_ID"))));
			po.setInteger("REALLY_CITY_ID",Integer.parseInt(CommonUtils.checkNull(map.get("REALLY_CITY_ID"))==""?"0":CommonUtils.checkNull(map.get("REALLY_CITY_ID"))));
			po.setString("ADDRESS",CommonUtils.checkNull(map.get("ADDRESS")));
			po.setString("SERVICE_TEL",CommonUtils.checkNull(map.get("SERVICE_TEL")));
			po.setString("STATUS",CommonUtils.checkNull(map.get("STATUS")));
			beanList.add(po);
		}
		return beanList;
	}
}
