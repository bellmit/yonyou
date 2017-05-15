package com.yonyou.dms.retail.dao.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetalDiscountImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetalDiscountImportTempPO;

@Repository
public class RetailDiscountDao {

	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TmRetalDiscountImportTempDTO tvypDTO) {
		TmRetalDiscountImportTempPO tvypPO = new TmRetalDiscountImportTempPO();
		//设置对象属性
		setTmRetalDiscountImportTempPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}
	
	public void setTmRetalDiscountImportTempPO(TmRetalDiscountImportTempPO retailBank,TmRetalDiscountImportTempDTO retailBankDTO){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setString("BANK", retailBankDTO.getBank());
		retailBank.setString("DEALER_NAME", retailBankDTO.getDealerName());
		retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
		retailBank.setString("DEALER_CODE2", retailBankDTO.getDealerCode2());
		retailBank.setString("CUSTOMER", retailBankDTO.getCustomer());
		retailBank.setString("VIN", retailBankDTO.getVin());
		retailBank.setString("SERIES_NAME", retailBankDTO.getSeriesName());
		retailBank.setString("MODEL_NAME", retailBankDTO.getModelName());
		retailBank.setString("MODEL_YEAR", retailBankDTO.getModelYear());
		retailBank.setString("SALES_TYPE", retailBankDTO.getSalesType());
		retailBank.setString("APPLY_DATE", retailBankDTO.getApplyDate());
		retailBank.setString("DEAL_DATE", retailBankDTO.getDealDate());
		retailBank.setString("MSRP", retailBankDTO.getMsrp());
		retailBank.setString("NET_PRICE", retailBankDTO.getNetPrice());
		retailBank.setString("FINANCING_PNAME", retailBankDTO.getFinancingPname());
		retailBank.setString("FIRST_PERMENT", retailBankDTO.getFirstPerment());
		retailBank.setString("DEAL_AMOUNT", retailBankDTO.getDealAmount());
		retailBank.setString("FIRST_PERMENT_RATIO", retailBankDTO.getFirstPermentRatio());
		retailBank.setString("INSTALL_MENT_NUM", retailBankDTO.getInstallMentNum());
		retailBank.setString("TOTAL_INTEREST", retailBankDTO.getTotalInterest());
		retailBank.setString("INTEREST_RATE", retailBankDTO.getInterestRate());
		retailBank.setString("POLICY_RATE", retailBankDTO.getPolicyRate());
		retailBank.setString("MERCHANT_FEES_RATE", retailBankDTO.getMerchantFeesRate());
		retailBank.setString("ALLOWANCED_SUM_TAX", retailBankDTO.getAllowancedSumTax());
		retailBank.setString("ALLOWANCE_BANK_SUM_TAX", retailBankDTO.getAllowancedSumTax());
		retailBank.setString("R_MONTH", retailBankDTO.getRMonth());
		retailBank.setString("R_YEAR", retailBankDTO.getRYear());
		retailBank.setString("REMARK", retailBankDTO.getRemark());
		retailBank.setLong("ROW_NO",retailBankDTO.getRowNO());
		retailBank.setLong("CREATE_BY",loginInfo.getUserId());
		retailBank.setTimestamp("CREATE_DATE",new Date());
       		
	}
	public boolean save() {
		  List<Object> params = new ArrayList<Object>();
		try {
			//List<TmRetailDiscountImportTempPO> list = dao.select(new TmRetailDiscountImportTempPO());
			StringBuffer sql = new StringBuffer("");
			sql.append("  insert into TM_RETAIL_DISCOUNT_IMPORT(\n");
			sql.append(" BANK,\n");
			sql.append(" DEALER_NAME, DEALER_CODE, DEALER_CODE2, CUSTOMER, VIN,\n");
			sql.append("  SERIES_NAME, MODEL_NAME, MODEL_YEAR, SALES_TYPE,\n");
			sql.append(" APPLY_DATE,DEAL_DATE,MSRP, NET_PRICE,\n");
			sql.append(" FINANCING_PNAME, \n");
			sql.append(" FIRST_PERMENT, DEAL_AMOUNT, FIRST_PERMENT_RATIO,INSTALL_MENT_NUM,TOTAL_INTEREST, INTEREST_RATE, \n");
			sql.append(" MERCHANT_FEES_RATE , POLICY_RATE, ALLOWANCED_SUM_TAX, ALLOWANCE_BANK_SUM_TAX,\n");
			sql.append(" REMARK, R_YEAR, R_MONTH,CREATE_DATE,CREATE_BY \n");
			sql.append(" )");
			sql.append(" select  \n");
			sql.append(" 	BANK,DEALER_NAME, DEALER_CODE, DEALER_CODE2, CUSTOMER, VIN, \n");
			sql.append(" SERIES_NAME, MODEL_NAME, MODEL_YEAR,SALES_TYPE,\n");
			sql.append("	case APPLY_DATE when '' THEN NULL ELSE APPLY_DATE END AS APPLY_DATE,\n");
			sql.append("	case DEAL_DATE when '' THEN NULL ELSE DEAL_DATE END AS DEAL_DATE,\n");
			sql.append("  case MSRP when '' THEN NULL ELSE MSRP END AS MSRP,case NET_PRICE when '' THEN NULL ELSE NET_PRICE END AS  NET_PRICE,\n");
			sql.append(" 	FINANCING_PNAME,\n");
			sql.append(" 	case FIRST_PERMENT when '' THEN NULL ELSE FIRST_PERMENT END AS FIRST_PERMENT, \n");
			sql.append(" 	case DEAL_AMOUNT when '' THEN NULL ELSE DEAL_AMOUNT END AS DEAL_AMOUNT,\n");
			sql.append(" 	CASE replace(FIRST_PERMENT_RATIO,'%','') WHEN '' THEN NULL ELSE replace(FIRST_PERMENT_RATIO,'%','') END AS FIRST_PERMENT_RATIO,\n");
			
			sql.append("  case INSTALL_MENT_NUM when '' then NULL else INSTALL_MENT_NUM end as INSTALL_MENT_NUM,\n");
			sql.append(" case TOTAL_INTEREST when '' then NULL else TOTAL_INTEREST end as  TOTAL_INTEREST, \n");
			sql.append(" 	CASE replace(INTEREST_RATE,'%','') WHEN '' THEN NULL ELSE replace(INTEREST_RATE,'%','') END AS INTEREST_RATE,\n");
			sql.append(" 	CASE replace(MERCHANT_FEES_RATE,'%','') WHEN ''THEN NULL ELSE replace(MERCHANT_FEES_RATE,'%','') END AS MERCHANT_FEES_RATE , \n");
			sql.append("  CASE replace(POLICY_RATE,'%','') WHEN '' THEN NULL ELSE replace(POLICY_RATE,'%','')END AS POLICY_RATE,\n");
			sql.append(" case ALLOWANCED_SUM_TAX when '' THEN NULL ELSE ALLOWANCED_SUM_TAX END AS ALLOWANCED_SUM_TAX,\n");
			sql.append(" case ALLOWANCE_BANK_SUM_TAX when '' THEN NULL ELSE ALLOWANCE_BANK_SUM_TAX END AS  ALLOWANCE_BANK_SUM_TAX,\n");
			sql.append(" REMARK, R_YEAR, R_MONTH,CREATE_DATE,CREATE_BY \n");
			sql.append(" FROM TM_RETAIL_DISCOUNT_IMPORT_TEMP \n");
			OemDAOUtil.execBatchPreparement(sql.toString(), params);
			return true;
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
