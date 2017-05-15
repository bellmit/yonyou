package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerFilingBaseInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcLinkmanPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 
* @ClassName: SADCS012CloudDao 
* @Description: 【异步】大客户报备数据上报
* @author zhengzengliang 
* @date 2017年4月13日 下午4:42:06 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SADCS012CloudDao extends OemBaseDAO{

	//更新大客户信息
	public int updateTtUcCustomer(TtUcCustomerPO keyUcCustomerPo, TtUcCustomerPO ucCustomerPo) {
		List<Object> params = new ArrayList<Object>();
		params.add(ucCustomerPo.get("DEALER_CODE"));
		params.add(ucCustomerPo.get("CUSTOMER_TYPE"));
		params.add(ucCustomerPo.get("CUSTOMER_BUSINESS_TYPE"));
		params.add(ucCustomerPo.get("CUSTOMER_NAME"));
		params.add(ucCustomerPo.get("CUSTOMER_PHONE"));
		params.add(ucCustomerPo.get("CUSTOMER_COMPANY_NAME"));
		params.add(ucCustomerPo.get("CUSTOMER_COMPANY_NATURE"));
		params.add(ucCustomerPo.get("CUSTOMER_COMPANY_TYPE"));
		params.add(ucCustomerPo.get("COMPANY_NAME"));
		params.add(ucCustomerPo.get("CUSTOMER_COMPANY_APPLY_SOURCE"));
		params.add(ucCustomerPo.get("UPDATE_BY"));
		params.add(ucCustomerPo.get("UPDATE_DATE"));
		params.add(keyUcCustomerPo.get("CUSTOMER_ID"));
		params.add(keyUcCustomerPo.get("ENABLE"));
		params.add(keyUcCustomerPo.get("DEALER_CODE"));
		int updateCustomerCount = TtUcCustomerPO.update(" DEALER_CODE=? , CUSTOMER_TYPE=? , "
				+ "CUSTOMER_BUSINESS_TYPE=? , CUSTOMER_NAME=? , CUSTOMER_PHONE=? , CUSTOMER_COMPANY_NAME=? , "
				+ "CUSTOMER_COMPANY_NATURE=? , CUSTOMER_COMPANY_TYPE=? , COMPANY_NAME=? , CUSTOMER_COMPANY_APPLY_SOURCE=? , "
				+ "UPDATE_BY=? , UPDATE_DATE=? ", " CUSTOMER_ID=? AND ENABLE=? AND DEALER_CODE=? ", params.toArray());
		
		return updateCustomerCount;
	}

	//更新联系人信息
	public int updateTtUcLinkman(TtUcLinkmanPO keyUcLinkManPo, TtUcLinkmanPO ucLinkManPo) {
		List<Object> params = new ArrayList<Object>();
		params.add(ucLinkManPo.get("DEALER_CODE"));
		params.add(ucLinkManPo.get("LM_TYPE"));
		params.add(ucLinkManPo.get("LM_NAME"));
		params.add(ucLinkManPo.get("LM_CARD_TYPE"));
		params.add(ucLinkManPo.get("LM_CARD_NO"));
		params.add(ucLinkManPo.get("LM_BIRTH_DAY"));
		params.add(ucLinkManPo.get("LM_SEX"));
		params.add(ucLinkManPo.get("LM_CELLPHONE"));
		params.add(ucLinkManPo.get("LM_TELEPHONE"));
		params.add(ucLinkManPo.get("LM_EMAIL"));
		params.add(ucLinkManPo.get("LM_FAX"));
		params.add(ucLinkManPo.get("LM_POST"));
		params.add(ucLinkManPo.get("UPDATE_BY"));
		params.add(ucLinkManPo.get("UPDATE_DATE"));
		params.add(keyUcLinkManPo.get("CUSTOMER_ID"));
		params.add(keyUcLinkManPo.get("ENABLE"));
		params.add(keyUcLinkManPo.get("DEALER_CODE"));
		int updateUcLinkManCount = TtUcLinkmanPO.update(" DEALER_CODE=? , LM_TYPE=? , LM_NAME=? , LM_CARD_TYPE=? , LM_CARD_NO=? , "
				+ "LM_BIRTH_DAY=? , LM_SEX=? , LM_CELLPHONE=? , LM_TELEPHONE=? , LM_EMAIL=? , LM_FAX=? , LM_POST=? , UPDATE_BY=? , "
				+ "UPDATE_DATE=? ", " CUSTOMER_ID=? AND ENABLE=? AND DEALER_CODE=?", params.toArray());
		return updateUcLinkManCount;
	}

	//更新报备基础信息
	public int updateTtBigCustomerFilingBaseInfo(TtBigCustomerFilingBaseInfoPO keyFilingBasePO,
			TtBigCustomerFilingBaseInfoPO filingBasePO) {
		List<Object> params = new ArrayList<Object>();
		params.add(filingBasePO.get("DEALER_CODE"));
		params.add(filingBasePO.get("WS_NO"));
		params.add(filingBasePO.get("DLR_HEAD"));
		params.add(filingBasePO.get("DLR_HEAD_PHONE"));
		params.add(filingBasePO.get("PS_TYPE"));
		params.add(filingBasePO.get("REPORT_DATE"));
		params.add(filingBasePO.get("REAMRK"));
		params.add(filingBasePO.get("APPLY_PIC"));
		params.add(filingBasePO.get("APPLY_PIC1"));
		params.add(filingBasePO.get("SALE_CONTRACT_PIC"));
		params.add(filingBasePO.get("SALE_CONTRACT_PIC1"));
		params.add(filingBasePO.get("SALE_CONTRACT_PIC2"));
		params.add(filingBasePO.get("CUSTOMER_CARD_PIC"));
		params.add(filingBasePO.get("ESTIMATE_APPLY_TIME"));
		params.add(filingBasePO.get("EMPLOYEE_TYPE"));
		params.add(filingBasePO.get("CUSTOMER_SUB_TYPE"));
		params.add(filingBasePO.get("UPDATE_BY"));
		params.add(filingBasePO.get("UPDATE_DATE"));
		
		params.add(keyFilingBasePO.get("WS_NO"));
		params.add(keyFilingBasePO.get("DEALER_CODE"));
		params.add(keyFilingBasePO.get("ENABLE"));
		
		int updateFilingCount = TtBigCustomerFilingBaseInfoPO.update("DEALER_CODE=? , WS_NO=? , DLR_HEAD=? , DLR_HEAD_PHONE=? , PS_TYPE=? , "
				+ "REPORT_DATE=? , REAMRK=? , APPLY_PIC=? , APPLY_PIC1=? , SALE_CONTRACT_PIC=? , SALE_CONTRACT_PIC1=? , SALE_CONTRACT_PIC2=? , "
				+ "CUSTOMER_CARD_PIC=? , ESTIMATE_APPLY_TIME=? , EMPLOYEE_TYPE=? , CUSTOMER_SUB_TYPE=? , UPDATE_BY=? ,"
				+ " UPDATE_DATE=? ", "WS_NO= ? AND DEALER_CODE= ? AND ENABLE= ? ", params.toArray());
		return updateFilingCount;
	}

	/**
	 * 
	* @Title: updateTtBigCustomerReportApproval 
	* @Description: //更新审批信息
	* @param @param keyReportApprovalPo
	* @param @param reportApprovalPo
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int updateTtBigCustomerReportApproval(TtBigCustomerReportApprovalPO keyReportApprovalPo,
			TtBigCustomerReportApprovalPO reportApprovalPo) {
		List<Object> params = new ArrayList<Object>();
		params.add(reportApprovalPo.get("DEALER_CODE"));
		params.add(reportApprovalPo.get("REPORT_DATE"));
		params.add(reportApprovalPo.get("REPORT_APPROVAL_STATUS"));
		params.add(reportApprovalPo.get("UPDATE_BY"));
		params.add(reportApprovalPo.get("UPDATE_DATE"));
		params.add(reportApprovalPo.get("PS_SOURCE_APPLY_NUM_COUNT"));
		
		params.add(keyReportApprovalPo.get("WS_NO"));
		params.add(keyReportApprovalPo.get("ENABLE"));
		params.add(keyReportApprovalPo.get("DEALER_CODE"));
		
		int upApprovalCount = TtBigCustomerReportApprovalPO.update(" DEALER_CODE=? , REPORT_DATE=? , REPORT_APPROVAL_STATUS=? , UPDATE_BY=? , UPDATE_DATE=? ,PS_SOURCE_APPLY_NUM_COUNT = ?", " WS_NO=? AND ENABLE=? AND DEALER_CODE=? ", params.toArray());
		return upApprovalCount;
	}

	/**
	 * 
	* @Title: getCurrentTablePK 
	* @Description: 
	* @param @param pk
	* @param @param tableName
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public long getCurrentTableMaxPK(String pk, String tableName) {
		Map m = OemDAOUtil.findFirst("SELECT max( "+pk+" )AS "+pk+" FROM "+tableName, null);
		Long  customerId = Long.valueOf(m.get(pk).toString());
		return customerId;
	}


}
