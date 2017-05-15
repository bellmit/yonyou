package com.yonyou.dms.retail.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmRetailDiscountBankImportTempDTO extends DataImportDto{
	@ExcelColumnDefine(value=14)
	@Required
	private String endPermentRatio;
	@ExcelColumnDefine(value=9)
	@Required
	private String financingPname;
	@ExcelColumnDefine(value=4)
	@Required
	private String vin;
	@ExcelColumnDefine(value=15)
	@Required
	private String installMentNum;
	@ExcelColumnDefine(value=12)
	@Required
	private String endPerment;
	private String merchantFees;
	@ExcelColumnDefine(value=21)
	@Required
	private String remark;
	@ExcelColumnDefine(value=5)
	@Required
	private String appState;
	@ExcelColumnDefine(value=6)
	@Required
	private String applyDate;
	@ExcelColumnDefine(value=10)
	@Required
	private String firstPerment;
	@ExcelColumnDefine(value=18)
	@Required
	private String policyRate;
	private Long bank;
	private Long id;
	@ExcelColumnDefine(value=11)
	@Required
	private String dealAmount;
	@ExcelColumnDefine(value=19)
	@Required
	private String merchantFeesRate;
	@ExcelColumnDefine(value=3)
	@Required
	private String customer;
	@ExcelColumnDefine(value=1)
	@Required
	private String dealerShortname;
	@ExcelColumnDefine(value=2)
	@Required
	private String dealerCode;
	@ExcelColumnDefine(value=8)
	@Required
	private String netPrice;
	@ExcelColumnDefine(value=13)
	@Required
	private String firstPermentRatio;
	@ExcelColumnDefine(value=7)
	@Required
	private String dealDate;
	@ExcelColumnDefine(value=17)
	@Required
	private String interestRate;
	@ExcelColumnDefine(value=20)
	@Required
	private String allowancedSumTax;
	@ExcelColumnDefine(value=16)
	@Required
	private String totalInterest;

	public void setEndPermentRatio(String endPermentRatio){
		this.endPermentRatio=endPermentRatio;
	}

	public String getEndPermentRatio(){
		return this.endPermentRatio;
	}

	public void setFinancingPname(String financingPname){
		this.financingPname=financingPname;
	}

	public String getFinancingPname(){
		return this.financingPname;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setInstallMentNum(String installMentNum){
		this.installMentNum=installMentNum;
	}

	public String getInstallMentNum(){
		return this.installMentNum;
	}

	public void setEndPerment(String endPerment){
		this.endPerment=endPerment;
	}

	public String getEndPerment(){
		return this.endPerment;
	}

	public void setMerchantFees(String merchantFees){
		this.merchantFees=merchantFees;
	}

	public String getMerchantFees(){
		return this.merchantFees;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setAppState(String appState){
		this.appState=appState;
	}

	public String getAppState(){
		return this.appState;
	}

	public void setApplyDate(String applyDate){
		this.applyDate=applyDate;
	}

	public String getApplyDate(){
		return this.applyDate;
	}

	public void setFirstPerment(String firstPerment){
		this.firstPerment=firstPerment;
	}

	public String getFirstPerment(){
		return this.firstPerment;
	}

	public void setPolicyRate(String policyRate){
		this.policyRate=policyRate;
	}

	public String getPolicyRate(){
		return this.policyRate;
	}

	public void setBank(Long bank){
		this.bank=bank;
	}

	public Long getBank(){
		return this.bank;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setDealAmount(String dealAmount){
		this.dealAmount=dealAmount;
	}

	public String getDealAmount(){
		return this.dealAmount;
	}

	public void setMerchantFeesRate(String merchantFeesRate){
		this.merchantFeesRate=merchantFeesRate;
	}

	public String getMerchantFeesRate(){
		return this.merchantFeesRate;
	}

	public void setCustomer(String customer){
		this.customer=customer;
	}

	public String getCustomer(){
		return this.customer;
	}

	public void setDealerShortname(String dealerShortname){
		this.dealerShortname=dealerShortname;
	}

	public String getDealerShortname(){
		return this.dealerShortname;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setNetPrice(String netPrice){
		this.netPrice=netPrice;
	}

	public String getNetPrice(){
		return this.netPrice;
	}

	public void setFirstPermentRatio(String firstPermentRatio){
		this.firstPermentRatio=firstPermentRatio;
	}

	public String getFirstPermentRatio(){
		return this.firstPermentRatio;
	}

	public void setDealDate(String dealDate){
		this.dealDate=dealDate;
	}

	public String getDealDate(){
		return this.dealDate;
	}

	public void setInterestRate(String interestRate){
		this.interestRate=interestRate;
	}

	public String getInterestRate(){
		return this.interestRate;
	}

	public void setAllowancedSumTax(String allowancedSumTax){
		this.allowancedSumTax=allowancedSumTax;
	}

	public String getAllowancedSumTax(){
		return this.allowancedSumTax;
	}

	public void setTotalInterest(String totalInterest){
		this.totalInterest=totalInterest;
	}

	public String getTotalInterest(){
		return this.totalInterest;
	}

	public String getDmsFileIds() {
		// TODO Auto-generated method stub
		return null;
	}

}
