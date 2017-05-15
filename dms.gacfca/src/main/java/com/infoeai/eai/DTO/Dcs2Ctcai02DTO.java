package com.infoeai.eai.DTO;

/**
 * 销售订单导入-PO
 * @author luoyang
 *
 */
public class Dcs2Ctcai02DTO {
	
	private Long sequenceId;            //序列号
	private String vin;                 //VIN号
	private String actionCode;          //交易代码
	private String actionDate;          //交易时间
	private String actionTime;          //交易时间
	private String dealerCode;          //经销商代码
	private String dealerName;          //经销商简称
	private String shippingAddress;     //收货地址
	private String contactMethod;       //联系方式
	private String paymentType;         //付款方式
	private String finalNetAmount;      //最终应付车价
	private String model;               //车型
	private String modelYear;           //车型年
	private String colourCode;          //颜色
	private String trimCode;            //内饰
	private String standardOptions;     //标准配置
	private String factoryOptions;      //其他配置
	private String localOptions;     	 //本地配置
	private String vehicleUsage;      	//车辆用途
	
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public String getContactMethod() {
		return contactMethod;
	}
	public void setContactMethod(String contactMethod) {
		this.contactMethod = contactMethod;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getFinalNetAmount() {
		return finalNetAmount;
	}
	public void setFinalNetAmount(String finalNetAmount) {
		this.finalNetAmount = finalNetAmount;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getColourCode() {
		return colourCode;
	}
	public void setColourCode(String colourCode) {
		this.colourCode = colourCode;
	}
	public String getTrimCode() {
		return trimCode;
	}
	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}
	public String getStandardOptions() {
		return standardOptions;
	}
	public void setStandardOptions(String standardOptions) {
		this.standardOptions = standardOptions;
	}
	public String getFactoryOptions() {
		return factoryOptions;
	}
	public void setFactoryOptions(String factoryOptions) {
		this.factoryOptions = factoryOptions;
	}
	public String getLocalOptions() {
		return localOptions;
	}
	public void setLocalOptions(String localOptions) {
		this.localOptions = localOptions;
	}
	public String getVehicleUsage() {
		return vehicleUsage;
	}
	public void setVehicleUsage(String vehicleUsage) {
		this.vehicleUsage = vehicleUsage;
	}
	
	

}
