package com.infoservice.dms.cgcsl.vo;

import java.util.Date;


public class SA007VO extends BaseVO{//调拨
	private static final long serialVersionUID = 1L;
	private String inEntityCode;//调入方经销商代码
	private String outEntityCode;//调出方经销商代码
	private String productCode;//产品代码
    private String engineNo;//发动机号
    private String vin;
    private Date manufactureDate;//生产日期
    private String keyNumber;//钥匙编号    上端没有
    private Integer hasCertificate;//是否有合格证    上端没有
    private String certificateNumber;//合格证号    上端没有
    private Date factoryDate;//出厂日期
    private Double vehiclePrice;//车辆成本     上端没有

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public Date getFactoryDate() {
		return factoryDate;
	}

	public void setFactoryDate(Date factoryDate) {
		this.factoryDate = factoryDate;
	}

	public Integer getHasCertificate() {
		return hasCertificate;
	}

	public void setHasCertificate(Integer hasCertificate) {
		this.hasCertificate = hasCertificate;
	}

	public String getInEntityCode() {
		return inEntityCode;
	}

	public void setInEntityCode(String inEntityCode) {
		this.inEntityCode = inEntityCode;
	}

	public String getKeyNumber() {
		return keyNumber;
	}

	public void setKeyNumber(String keyNumber) {
		this.keyNumber = keyNumber;
	}

	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
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

	public Double getVehiclePrice() {
		return vehiclePrice;
	}

	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
