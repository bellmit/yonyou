package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackCreateDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SI30Dao {
	private static Logger logger = LoggerFactory.getLogger(SI30Dao.class);

	
	
	/**
	 * 功能说明:销售线索反馈上传接口
	 * 创建人: zhangRM 
	 * 创建日期: 2013-06-07
	 * @return
	 */
	public List<TiSalesLeadsFeedbackCreateDcsPO> getSI30Info() {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tslcc.ID,\n");
		sql.append("         tslfc.DMS_CUSTOMER_NO, \n");
		sql.append("         tslfc.CONFLICTED_TYPE, \n");
		sql.append("         tslfc.SALES_CONSULTANT, \n");
		sql.append("         tslfc.OPPORTUNITY_LEVEL_ID, \n");
		sql.append("         tslfc.SEQUENCE_ID \n");
		sql.append("    FROM TI_SALES_LEADS_FEEDBACK_CREATE_dcs tslfc, TI_SALES_LEADS_CUSTOMER_CREATE_dcs tslcc,TM_DEALER tmd,TM_COMPANY tmc \n");
		sql.append("   WHERE tslfc.NID = tslcc.NID \n");
		sql.append("     and tslfc.DEALER_CODE = tmd.DEALER_CODE \n");
		sql.append("     and tmd.COMPANY_ID = tmc.COMPANY_ID \n");
		sql.append("     and (tslfc.IS_SCAN = '0' or tslfc.IS_SCAN is null) limit 0,1000 \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
		List<TiSalesLeadsFeedbackCreateDcsPO> poList = new ArrayList<TiSalesLeadsFeedbackCreateDcsPO>(); 
		TiSalesLeadsFeedbackCreateDcsPO po = null;
		for (Map map : list) {
			po = new TiSalesLeadsFeedbackCreateDcsPO();
			po.setLong("NID", new Long(CommonUtils.checkNull(map.get("ID"))==""?"0":CommonUtils.checkNull(map.get("ID"))));
			po.setInteger("CONFLICTED_TYPE", Integer.parseInt(CommonUtils.checkNull(map.get("CONFLICTED_TYPE"))==""?"0":CommonUtils.checkNull(map.get("CONFLICTED_TYPE"))));
			po.setString("DMS_CUSTOMER_NO", CommonUtils.checkNull(map.get("DMS_CUSTOMER_NO")));
			po.setString("OPPORTUNITY_LEVEL_ID", CommonUtils.checkNull(map.get("OPPORTUNITY_LEVEL_ID")));
			po.setString("SALES_CONSULTANT", CommonUtils.checkNull(map.get("SALES_CONSULTANT")));
			po.setLong("SEQUENCE_ID", new Long(CommonUtils.checkNull(map.get("SEQUENCE_ID"))));
			
			poList.add(po);
		}
		
		return poList;
	}
	
}
