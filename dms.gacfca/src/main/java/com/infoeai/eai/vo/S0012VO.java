package com.infoeai.eai.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class S0012VO extends BaseVO {
	
	private String selectTime;
	private Date updateDate;
	private String dealerCode;
	private Long createBy;
	private String selectDate;
	private String financingAmount;
	private String advanceAmount;
	private Integer isDel;
	private String financingStatus;	// update_date 20151228 by maxiang new column
	private Long ifId;
	private Long updateBy;
	private String financeCompanyCode;
	private String usedAmount;
	private String rowId;
	private String isResult;
	private String isMessage;
	private String availabelAmount;
	private String financeBrandCode;
	private Date createDate;

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public String getSelectTime() {
		return this.selectTime;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerCode() {
		return this.dealerCode;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}

	public String getSelectDate() {
		return this.selectDate;
	}

	public void setFinancingAmount(String financingAmount) {
		this.financingAmount = financingAmount;
	}

	public String getFinancingAmount() {
		return this.financingAmount;
	}

	public void setAdvanceAmount(String advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getAdvanceAmount() {
		return this.advanceAmount;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}

	public void setFinancingStatus(String financingStatus) {
		this.financingStatus = financingStatus;
	}

	public String getFinancingStatus() {
		return this.financingStatus;
	}

	public void setIfId(Long ifId) {
		this.ifId = ifId;
	}

	public Long getIfId() {
		return this.ifId;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Long getUpdateBy() {
		return this.updateBy;
	}

	public void setFinanceCompanyCode(String financeCompanyCode) {
		this.financeCompanyCode = financeCompanyCode;
	}

	public String getFinanceCompanyCode() {
		return this.financeCompanyCode;
	}

	public void setUsedAmount(String usedAmount) {
		this.usedAmount = usedAmount;
	}

	public String getUsedAmount() {
		return this.usedAmount;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getRowId() {
		return this.rowId;
	}

	public void setIsResult(String isResult) {
		this.isResult = isResult;
	}

	public String getIsResult() {
		return this.isResult;
	}

	public void setIsMessage(String isMessage) {
		this.isMessage = isMessage;
	}

	public String getIsMessage() {
		return this.isMessage;
	}

	public void setAvailabelAmount(String availabelAmount) {
		this.availabelAmount = availabelAmount;
	}

	public String getAvailabelAmount() {
		return this.availabelAmount;
	}

	public void setFinanceBrandCode(String financeBrandCode) {
		this.financeBrandCode = financeBrandCode;
	}

	public String getFinanceBrandCode() {
		return this.financeBrandCode;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

}
