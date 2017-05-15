/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2015-03-06 17:12:42
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.retailReportQuery;

import java.util.Date;

public class TtVsSalesReportDTO {

	private Double price;
	private Long dlrCompanyId;
	private Long dealerId;
	private Date insuranceDate;
	private Date consignationDate;
	private Date updateDate;
	private Long createBy;
	private Integer status;
	private String insuranceCompany;
	private Integer isDel;
	private Long ctmId;
	private String memo;
	private Long oemCompanyId;
	private Long updateBy;
	private Integer isOldCtm;
	private Float miles;
	private Long vehicleId;
	private String salesMan;
	private String invoiceNo;
	private Date salesDate;
	private Double loanRate;
	private String vehicleNo;
	private Integer loanOrg;
	private Double firstPermentRatio;
	private Date invoiceDate;
	private Integer installmentNumber;
	private Double installmentAmount;
	private Integer isOcr;
	private Integer ver;
	private Integer payment;
	private Integer vehicleType;
	private Date createDate;
	private Integer isArc;
	private String contractNo;
	private Long reportId;
	private Integer isModify;
	private Integer isScan;
	private String resultValue;
	//add by huyu
	private String invoiceTypeCode;//发票类型
	private Double invoiceAmount;//发票金额
	private String invoiceCustomer;//费用类型
	private String invoiceWriter;//开票客户
	private String transactor;//开票人员
	private Integer invoiceChargeType;//经办人
	//end
	
	//add by huyu	
	private Integer isBack;//是否退车[0:否][1:是]
	private Date backDate;//退车日期
	//end

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return this.price;
	}

	public void setDlrCompanyId(Long dlrCompanyId){
		this.dlrCompanyId=dlrCompanyId;
	}

	public Long getDlrCompanyId(){
		return this.dlrCompanyId;
	}

	public void setDealerId(Long dealerId){
		this.dealerId=dealerId;
	}

	public Long getDealerId(){
		return this.dealerId;
	}

	public void setInsuranceDate(Date insuranceDate){
		this.insuranceDate=insuranceDate;
	}

	public Date getInsuranceDate(){
		return this.insuranceDate;
	}

	public void setConsignationDate(Date consignationDate){
		this.consignationDate=consignationDate;
	}

	public Date getConsignationDate(){
		return this.consignationDate;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setInsuranceCompany(String insuranceCompany){
		this.insuranceCompany=insuranceCompany;
	}

	public String getInsuranceCompany(){
		return this.insuranceCompany;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setCtmId(Long ctmId){
		this.ctmId=ctmId;
	}

	public Long getCtmId(){
		return this.ctmId;
	}

	public void setMemo(String memo){
		this.memo=memo;
	}

	public String getMemo(){
		return this.memo;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setIsOldCtm(Integer isOldCtm){
		this.isOldCtm=isOldCtm;
	}

	public Integer getIsOldCtm(){
		return this.isOldCtm;
	}

	public void setMiles(Float miles){
		this.miles=miles;
	}

	public Float getMiles(){
		return this.miles;
	}

	public void setVehicleId(Long vehicleId){
		this.vehicleId=vehicleId;
	}

	public Long getVehicleId(){
		return this.vehicleId;
	}

	public void setSalesMan(String salesMan){
		this.salesMan=salesMan;
	}

	public String getSalesMan(){
		return this.salesMan;
	}

	public void setInvoiceNo(String invoiceNo){
		this.invoiceNo=invoiceNo;
	}

	public String getInvoiceNo(){
		return this.invoiceNo;
	}

	public void setSalesDate(Date salesDate){
		this.salesDate=salesDate;
	}

	public Date getSalesDate(){
		return this.salesDate;
	}

	public void setLoanRate(Double loanRate){
		this.loanRate=loanRate;
	}

	public Double getLoanRate(){
		return this.loanRate;
	}

	public void setVehicleNo(String vehicleNo){
		this.vehicleNo=vehicleNo;
	}

	public String getVehicleNo(){
		return this.vehicleNo;
	}

	public void setLoanOrg(Integer loanOrg){
		this.loanOrg=loanOrg;
	}

	public Integer getLoanOrg(){
		return this.loanOrg;
	}

	public void setFirstPermentRatio(Double firstPermentRatio){
		this.firstPermentRatio=firstPermentRatio;
	}

	public Double getFirstPermentRatio(){
		return this.firstPermentRatio;
	}

	public void setInvoiceDate(Date invoiceDate){
		this.invoiceDate=invoiceDate;
	}

	public Date getInvoiceDate(){
		return this.invoiceDate;
	}

	public void setInstallmentNumber(Integer installmentNumber){
		this.installmentNumber=installmentNumber;
	}

	public Integer getInstallmentNumber(){
		return this.installmentNumber;
	}

	public void setInstallmentAmount(Double installmentAmount){
		this.installmentAmount=installmentAmount;
	}

	public Double getInstallmentAmount(){
		return this.installmentAmount;
	}

	public void setIsOcr(Integer isOcr){
		this.isOcr=isOcr;
	}

	public Integer getIsOcr(){
		return this.isOcr;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setPayment(Integer payment){
		this.payment=payment;
	}

	public Integer getPayment(){
		return this.payment;
	}

	public void setVehicleType(Integer vehicleType){
		this.vehicleType=vehicleType;
	}

	public Integer getVehicleType(){
		return this.vehicleType;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setContractNo(String contractNo){
		this.contractNo=contractNo;
	}

	public String getContractNo(){
		return this.contractNo;
	}

	public void setReportId(Long reportId){
		this.reportId=reportId;
	}

	public Long getReportId(){
		return this.reportId;
	}

	public Integer getIsModify() {
		return isModify;
	}

	public void setIsModify(Integer isModify) {
		this.isModify = isModify;
	}

	public Integer getIsScan() {
		return isScan;
	}

	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getInvoiceTypeCode() {
		return invoiceTypeCode;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode) {
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public Double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceCustomer() {
		return invoiceCustomer;
	}

	public void setInvoiceCustomer(String invoiceCustomer) {
		this.invoiceCustomer = invoiceCustomer;
	}

	public String getInvoiceWriter() {
		return invoiceWriter;
	}

	public void setInvoiceWriter(String invoiceWriter) {
		this.invoiceWriter = invoiceWriter;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public Integer getInvoiceChargeType() {
		return invoiceChargeType;
	}

	public void setInvoiceChargeType(Integer invoiceChargeType) {
		this.invoiceChargeType = invoiceChargeType;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public Date getBackDate() {
		return backDate;
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

}