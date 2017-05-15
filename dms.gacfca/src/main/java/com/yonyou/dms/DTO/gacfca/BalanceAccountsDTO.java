package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class BalanceAccountsDTO {

	private Date balanceTime;//结算日期
	private String balanceNo;//结算单号
	private String InvoiceNo;//发票号码
	private String InvoiceTypeCode; //发票类型
	private String balanceHandler;
	////如果是索赔工单，结算的时候生成GSC系统保修单号
	private String gcsSysRepairNo;
	private String roNo;
	private String salesPartNo;
	private String balanceModeCode;
	private String discountModeCode;
	private Double labourAmount;
	private Double repairPartAmount;
	private Double salesPartAmount;
	private Double addItemAmount;
	private Double overItemAmount;
	private Double sumAmount;
	private Double totalAmount;
	private Float tax;
	private Double taxAmount;
	private Double netAmount;
	private Double receiveAmount;
	private Double subObbAmount;
	private Double derateAmount;
	private Integer isRed;
	private Double repairPartCost;
	private Double salesPartCost;
	

	public Double getRepairPartCost() {
		return repairPartCost;
	}

	public void setRepairPartCost(Double repairPartCost) {
		this.repairPartCost = repairPartCost;
	}

	public Double getSalesPartCost() {
		return salesPartCost;
	}

	public void setSalesPartCost(Double salesPartCost) {
		this.salesPartCost = salesPartCost;
	}

	public Double getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}

	public String getBalanceModeCode() {
		return balanceModeCode;
	}

	public void setBalanceModeCode(String balanceModeCode) {
		this.balanceModeCode = balanceModeCode;
	}

	public Double getDerateAmount() {
		return derateAmount;
	}

	public void setDerateAmount(Double derateAmount) {
		this.derateAmount = derateAmount;
	}

	public String getDiscountModeCode() {
		return discountModeCode;
	}

	public void setDiscountModeCode(String discountModeCode) {
		this.discountModeCode = discountModeCode;
	}

	public Integer getIsRed() {
		return isRed;
	}

	public void setIsRed(Integer isRed) {
		this.isRed = isRed;
	}

	public Double getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getOverItemAmount() {
		return overItemAmount;
	}

	public void setOverItemAmount(Double overItemAmount) {
		this.overItemAmount = overItemAmount;
	}

	public Double getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public Double getRepairPartAmount() {
		return repairPartAmount;
	}

	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

	public Double getSalesPartAmount() {
		return salesPartAmount;
	}

	public void setSalesPartAmount(Double salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}

	public String getSalesPartNo() {
		return salesPartNo;
	}

	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}

	public Double getSubObbAmount() {
		return subObbAmount;
	}

	public void setSubObbAmount(Double subObbAmount) {
		this.subObbAmount = subObbAmount;
	}

	public Double getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(Double sumAmount) {
		this.sumAmount = sumAmount;
	}

	public Float getTax() {
		return tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getGcsSysRepairNo() {
		return gcsSysRepairNo;
	}

	public void setGcsSysRepairNo(String gcsSysRepairNo) {
		this.gcsSysRepairNo = gcsSysRepairNo;
	}

	public Date getBalanceTime() {
		return balanceTime;
	}

	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}

	public String getInvoiceNo() {
		return InvoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		InvoiceNo = invoiceNo;
	}

	public String getInvoiceTypeCode() {
		return InvoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		InvoiceTypeCode = invoiceTypeCode;
	}

	public String getBalanceHandler() {
		return balanceHandler;
	}

	public void setBalanceHandler(String balanceHandler) {
		this.balanceHandler = balanceHandler;
	}

	public String getBalanceNo() {
		return balanceNo;
	}

	public void setBalanceNo(String balanceNo) {
		this.balanceNo = balanceNo;
	}


}
