package com.yonyou.dms.retail.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class RetailBankDTO extends DataImportDto{
	@ExcelColumnDefine(value=1)
	@Required
	private Double endPermentRatio;
	@ExcelColumnDefine(value=2)
	@Required
	private String financingPname;
	@ExcelColumnDefine(value=3)
	@Required
	private String vin;
	@ExcelColumnDefine(value=4)
	@Required
	private Integer installMentNum;
	@ExcelColumnDefine(value=5)
	@Required
	private Date updateDate;
	@ExcelColumnDefine(value=6)
	@Required
	private Double endPerment;
	@ExcelColumnDefine(value=7)
	@Required
	private Double merchantFees;
	@ExcelColumnDefine(value=8)
	@Required
	private String appState;
	@ExcelColumnDefine(value=9)
	@Required
	private Long createBy;
	@ExcelColumnDefine(value=10)
	@Required
	private String remark;
	@ExcelColumnDefine(value=11)
	@Required
	private Date applyDate;
	@ExcelColumnDefine(value=12)
	@Required
	private Double firstPerment;
	@ExcelColumnDefine(value=13)
	@Required
	private Double policyRate;
	@ExcelColumnDefine(value=14)
	@Required
	private Long bank;
	@ExcelColumnDefine(value=15)
	@Required
	private Long updateBy;
	@ExcelColumnDefine(value=16)
	@Required
	private Long id;
	@ExcelColumnDefine(value=17)
	@Required
	private Double dealAmount;
	@ExcelColumnDefine(value=18)
	@Required
	private Double merchantFeesRate;
	@ExcelColumnDefine(value=19)
	@Required
	private String customer;
	@ExcelColumnDefine(value=20)
	@Required
	private String dealerShortname;
	@ExcelColumnDefine(value=21)
	@Required
	private String dealerCode;
	@ExcelColumnDefine(value=22)
	@Required
	private Double netPrice;
	@ExcelColumnDefine(value=23)
	@Required
	private Double firstPermentRatio;
	@ExcelColumnDefine(value=24)
	@Required
	private Date dealDate;
	@ExcelColumnDefine(value=25)
	@Required
	private Double allowancedSumTax;
	@ExcelColumnDefine(value=26)
	@Required
	private Double interestRate;
	@ExcelColumnDefine(value=27)
	@Required
	private Date createDate;
	@ExcelColumnDefine(value=28)
	@Required
	private Double totalInterest;
	//文件上传的ID
    private String dmsFileIds;

	public void setEndPermentRatio(Double endPermentRatio){
		this.endPermentRatio=endPermentRatio;
	}

	public Double getEndPermentRatio(){
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

	public void setInstallMentNum(Integer installMentNum){
		this.installMentNum=installMentNum;
	}

	public Integer getInstallMentNum(){
		return this.installMentNum;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEndPerment(Double endPerment){
		this.endPerment=endPerment;
	}

	public Double getEndPerment(){
		return this.endPerment;
	}

	public void setMerchantFees(Double merchantFees){
		this.merchantFees=merchantFees;
	}

	public Double getMerchantFees(){
		return this.merchantFees;
	}

	public void setAppState(String appState){
		this.appState=appState;
	}

	public String getAppState(){
		return this.appState;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setApplyDate(Date applyDate){
		this.applyDate=applyDate;
	}

	public Date getApplyDate(){
		return this.applyDate;
	}

	public void setFirstPerment(Double firstPerment){
		this.firstPerment=firstPerment;
	}

	public Double getFirstPerment(){
		return this.firstPerment;
	}

	public void setPolicyRate(Double policyRate){
		this.policyRate=policyRate;
	}

	public Double getPolicyRate(){
		return this.policyRate;
	}

	public void setBank(Long bank){
		this.bank=bank;
	}

	public Long getBank(){
		return this.bank;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setDealAmount(Double dealAmount){
		this.dealAmount=dealAmount;
	}

	public Double getDealAmount(){
		return this.dealAmount;
	}

	public void setMerchantFeesRate(Double merchantFeesRate){
		this.merchantFeesRate=merchantFeesRate;
	}

	public Double getMerchantFeesRate(){
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

	public void setNetPrice(Double netPrice){
		this.netPrice=netPrice;
	}

	public Double getNetPrice(){
		return this.netPrice;
	}

	public void setFirstPermentRatio(Double firstPermentRatio){
		this.firstPermentRatio=firstPermentRatio;
	}

	public Double getFirstPermentRatio(){
		return this.firstPermentRatio;
	}

	public void setDealDate(Date dealDate){
		this.dealDate=dealDate;
	}

	public Date getDealDate(){
		return this.dealDate;
	}

	public void setAllowancedSumTax(Double allowancedSumTax){
		this.allowancedSumTax=allowancedSumTax;
	}

	public Double getAllowancedSumTax(){
		return this.allowancedSumTax;
	}

	public void setInterestRate(Double interestRate){
		this.interestRate=interestRate;
	}

	public Double getInterestRate(){
		return this.interestRate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setTotalInterest(Double totalInterest){
		this.totalInterest=totalInterest;
	}

	public Double getTotalInterest(){
		return this.totalInterest;
	}

	public String getDmsFileIds() {
		return dmsFileIds;
	}

	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}
}
