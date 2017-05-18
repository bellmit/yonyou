package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SA007Dto {//调拨
    
	private String dealerCode;
	private String inEntityCode;//调入方经销商代码
	private String outEntityCode;//调出方经销商代码
	private String productCode;//产品代码
    private String engineNo;//发动机号
    private String vin;
    private Date manufactureDate;//生产日期
    private String keyNumber;//钥匙编号
    private Integer hasCertificate;//是否有合格证
    private String certificateNumber;//合格证号
    private Date factoryDate;//出厂日期
    private Double vehiclePrice;//
    
    
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getInEntityCode() {
		return inEntityCode;
	}
	public void setInEntityCode(String inEntityCode) {
		this.inEntityCode = inEntityCode;
	}
	public String getOutEntityCode() {
		return outEntityCode;
	}
	public void setOutEntityCode(String outEntityCode) {
		this.outEntityCode = outEntityCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	public String getKeyNumber() {
		return keyNumber;
	}
	public void setKeyNumber(String keyNumber) {
		this.keyNumber = keyNumber;
	}
	public Integer getHasCertificate() {
		return hasCertificate;
	}
	public void setHasCertificate(Integer hasCertificate) {
		this.hasCertificate = hasCertificate;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public Date getFactoryDate() {
		return factoryDate;
	}
	public void setFactoryDate(Date factoryDate) {
		this.factoryDate = factoryDate;
	}
	public Double getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	
}
