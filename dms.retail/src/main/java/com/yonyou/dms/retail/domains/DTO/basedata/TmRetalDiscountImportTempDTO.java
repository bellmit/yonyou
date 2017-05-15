package com.yonyou.dms.retail.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmRetalDiscountImportTempDTO extends DataImportDto{
	@ExcelColumnDefine(value=15)
	private String financingPname;
	@ExcelColumnDefine(value=10)
	@Required
	private String salesType;
	private String rYear;
	@ExcelColumnDefine(value=6)
	@Required
	private String vin;
	@ExcelColumnDefine(value=19)
	@Required
	private String installMentNum;
	private Date updateDate;
	private String merchantFees;
	@ExcelColumnDefine(value=26)
	@Required
	private String remark;
	private Long createBy;
	private String rMonth;
	@ExcelColumnDefine(value=11)
	@Required
	private String applyDate;
	@ExcelColumnDefine(value=16)
	@Required
	private String firstPerment;
	@ExcelColumnDefine(value=22)
	@Required
	private String policyRate;
	@ExcelColumnDefine(value=1)
	@Required
	private String bank;
	@ExcelColumnDefine(value=25)
	@Required
	private String allowanceBankSumTax;
	private String dealerBackSumTax;
	private Long updateBy;
	@ExcelColumnDefine(value=4)
	private String dealerCode2;
	private Long id;
	private String allowanceUp;
	@ExcelColumnDefine(value=17)
	@Required
	private String dealAmount;
	@ExcelColumnDefine(value=13)
	@Required
	private String msrp;
	@ExcelColumnDefine(value=23)
	@Required
	private String merchantFeesRate;
	@ExcelColumnDefine(value=5)
	@Required
	private String customer;
	@ExcelColumnDefine(value=7)
	@Required
	private String seriesName;
	@ExcelColumnDefine(value=3)
	@Required
	private String dealerCode;
	@ExcelColumnDefine(value=8)
	@Required
	private String modelName;
	@ExcelColumnDefine(value=14)
	@Required
	private String netPrice;
	@ExcelColumnDefine(value=18)
	@Required
	private String firstPermentRatio;
	@ExcelColumnDefine(value=2)
	@Required
	private String dealerName;
	@ExcelColumnDefine(value=12)
	@Required
	private String dealDate;
	@ExcelColumnDefine(value=21)
	@Required
	private String interestRate;
	@ExcelColumnDefine(value=24)
	@Required
	private String allowancedSumTax;
	@ExcelColumnDefine(value=9)
	@Required
	private String modelYear;
	@ExcelColumnDefine(value=20)
	@Required
	private String totalInterest;
	private Date createDate;

	public void setFinancingPname(String financingPname){
		this.financingPname=financingPname;
	}

	public String getFinancingPname(){
		return this.financingPname;
	}

	public void setSalesType(String salesType){
		this.salesType=salesType;
	}

	public String getSalesType(){
		return this.salesType;
	}

	public void setRYear(String rYear){
		this.rYear=rYear;
	}

	public String getRYear(){
		return this.rYear;
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

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
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

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRMonth(String rMonth){
		this.rMonth=rMonth;
	}

	public String getRMonth(){
		return this.rMonth;
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

	public void setBank(String bank){
		this.bank=bank;
	}

	public String getBank(){
		return this.bank;
	}

	public void setAllowanceBankSumTax(String allowanceBankSumTax){
		this.allowanceBankSumTax=allowanceBankSumTax;
	}

	public String getAllowanceBankSumTax(){
		return this.allowanceBankSumTax;
	}

	public void setDealerBackSumTax(String dealerBackSumTax){
		this.dealerBackSumTax=dealerBackSumTax;
	}

	public String getDealerBackSumTax(){
		return this.dealerBackSumTax;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setDealerCode2(String dealerCode2){
		this.dealerCode2=dealerCode2;
	}

	public String getDealerCode2(){
		return this.dealerCode2;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setAllowanceUp(String allowanceUp){
		this.allowanceUp=allowanceUp;
	}

	public String getAllowanceUp(){
		return this.allowanceUp;
	}

	public void setDealAmount(String dealAmount){
		this.dealAmount=dealAmount;
	}

	public String getDealAmount(){
		return this.dealAmount;
	}

	public void setMsrp(String msrp){
		this.msrp=msrp;
	}

	public String getMsrp(){
		return this.msrp;
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

	public void setSeriesName(String seriesName){
		this.seriesName=seriesName;
	}

	public String getSeriesName(){
		return this.seriesName;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}

	public String getModelName(){
		return this.modelName;
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

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
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

	public void setModelYear(String modelYear){
		this.modelYear=modelYear;
	}

	public String getModelYear(){
		return this.modelYear;
	}

	public void setTotalInterest(String totalInterest){
		this.totalInterest=totalInterest;
	}

	public String getTotalInterest(){
		return this.totalInterest;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public String getFileParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
