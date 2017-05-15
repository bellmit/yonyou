package com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;


public class SalesOdditionalOrderDTO {
	
	//服务订单信息
	private String soNo;
	private String salesNo;
	private String serviceNo;
	private Integer soStatus;
	private String customerName;
	private Date sheetCreateDate;
	private String customerNo;
	private Integer customerType;
	private Integer sheetCreatedBy;
	private String phone;
	private String contactorName;
	private String address;
	private Integer ctCode;
	private String contractNo;
	private Date contractDate;
	private Date contractMaturity;
	private String labourPrice;
	private String certificateNo;
	private String loanOrg;
	private String soldBy;
	private String preInvoiceAmount;
	private Integer payMode;
	private Integer invoiceMode;
	private Integer orderSort;
	private String remark;
	//车辆信息
	private String productCode;
	private String productname;
	private String vin;
	private String license;
	private String cardCode;
	private String brand;
	private String series;
	private String model;
	private String config;
	private String color;
	private Integer cardTypeCode;
	private Integer vehiclePurpose;
	private String vehicleDesc;
	private Integer disCountModeCode;
	//财务信息
	private Double upholsterSum;
	private Double presentSum;
	private Double otherServiceSum;
	private Double commissionBalance;
	private Double insuranceSum;
	private Double plateSum;
	private Double loadSum;
	private Double taxSum;
	private Double consignedSum;
	private Double conReceivableSum;
	private Double orderSum;
	private Double orderReceivableSum;
	
	//装潢项目
	@SuppressWarnings("rawtypes")
	private List<Map> soDecrodateList;
	//精品材料
	@SuppressWarnings("rawtypes")
	private List<Map> soDecrodatePartList; 
	//服务项目
	@SuppressWarnings("rawtypes")
	private List<Map> soServicesList;
	
	@SuppressWarnings("rawtypes")
	public List<Map> getSoDecrodateList() {
		return soDecrodateList;
	}
	public void setSoDecrodateList(@SuppressWarnings("rawtypes") List<Map> soDecrodateList) {
		this.soDecrodateList = soDecrodateList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getSoDecrodatePartList() {
		return soDecrodatePartList;
	}
	@SuppressWarnings("rawtypes")
	public void setSoDecrodatePartList(List<Map> soDecrodatePartList) {
		this.soDecrodatePartList = soDecrodatePartList;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getSoServicesList() {
		return soServicesList;
	}
	@SuppressWarnings("rawtypes")
	public void setSoServicesList(List<Map> soServicesList) {
		this.soServicesList = soServicesList;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getSalesNo() {
		return salesNo;
	}
	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getSheetCreateDate() {
		return sheetCreateDate;
	}
	public void setSheetCreateDate(Date sheetCreateDate) {
		this.sheetCreateDate = sheetCreateDate;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public Integer getSheetCreatedBy() {
		return sheetCreatedBy;
	}
	public void setSheetCreatedBy(Integer sheetCreatedBy) {
		this.sheetCreatedBy = sheetCreatedBy;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getCtCode() {
		return ctCode;
	}
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Date getContractMaturity() {
		return contractMaturity;
	}
	public void setContractMaturity(Date contractMaturity) {
		this.contractMaturity = contractMaturity;
	}
	public String getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(String labourPrice) {
		this.labourPrice = labourPrice;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getLoanOrg() {
		return loanOrg;
	}
	public void setLoanOrg(String loanOrg) {
		this.loanOrg = loanOrg;
	}
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public String getPreInvoiceAmount() {
		return preInvoiceAmount;
	}
	public void setPreInvoiceAmount(String preInvoiceAmount) {
		this.preInvoiceAmount = preInvoiceAmount;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public Integer getInvoiceMode() {
		return invoiceMode;
	}
	public void setInvoiceMode(Integer invoiceMode) {
		this.invoiceMode = invoiceMode;
	}
	public Integer getOrderSort() {
		return orderSort;
	}
	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getCardTypeCode() {
		return cardTypeCode;
	}
	public void setCardTypeCode(Integer cardTypeCode) {
		this.cardTypeCode = cardTypeCode;
	}
	public Integer getVehiclePurpose() {
		return vehiclePurpose;
	}
	public void setVehiclePurpose(Integer vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}
	public String getVehicleDesc() {
		return vehicleDesc;
	}
	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}
	public Integer getDisCountModeCode() {
		return disCountModeCode;
	}
	public void setDisCountModeCode(Integer disCountModeCode) {
		this.disCountModeCode = disCountModeCode;
	}
	public Double getUpholsterSum() {
		return upholsterSum;
	}
	public void setUpholsterSum(Double upholsterSum) {
		this.upholsterSum = upholsterSum;
	}
	public Double getPresentSum() {
		return presentSum;
	}
	public void setPresentSum(Double presentSum) {
		this.presentSum = presentSum;
	}
	public Double getOtherServiceSum() {
		return otherServiceSum;
	}
	public void setOtherServiceSum(Double otherServiceSum) {
		this.otherServiceSum = otherServiceSum;
	}
	public Double getCommissionBalance() {
		return commissionBalance;
	}
	public void setCommissionBalance(Double commissionBalance) {
		this.commissionBalance = commissionBalance;
	}
	public Double getInsuranceSum() {
		return insuranceSum;
	}
	public void setInsuranceSum(Double insuranceSum) {
		this.insuranceSum = insuranceSum;
	}
	public Double getPlateSum() {
		return plateSum;
	}
	public void setPlateSum(Double plateSum) {
		this.plateSum = plateSum;
	}
	public Double getLoadSum() {
		return loadSum;
	}
	public void setLoadSum(Double loadSum) {
		this.loadSum = loadSum;
	}
	public Double getTaxSum() {
		return taxSum;
	}
	public void setTaxSum(Double taxSum) {
		this.taxSum = taxSum;
	}
	public Double getConsignedSum() {
		return consignedSum;
	}
	public void setConsignedSum(Double consignedSum) {
		this.consignedSum = consignedSum;
	}
	public Double getConReceivableSum() {
		return conReceivableSum;
	}
	public void setConReceivableSum(Double conReceivableSum) {
		this.conReceivableSum = conReceivableSum;
	}
	public Double getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(Double orderSum) {
		this.orderSum = orderSum;
	}
	public Double getOrderReceivableSum() {
		return orderReceivableSum;
	}
	public void setOrderReceivableSum(Double orderReceivableSum) {
		this.orderReceivableSum = orderReceivableSum;
	}
}
