package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SecondCarListVo extends BaseVO {
	private String emissions;//排气量
	private String gearForm;//排挡形式
	private Date scrapDate;//强制报废期
	private String vin;// 车辆
	private Date annualInspectionDate;//年检有效期
	private String remark;//手续项目特殊说明：(车辆手续、状态特殊情况具体说明)
	private String originCertificate;//来历凭证
	private String customerType;//客户类型
	private Date buyDate;//购买日期
	private String license;//车牌号
	private Double mileage;//里程数
	private String trafficInsureInfo;//交强险信息
	private String business;//商业险信息
	private String useType;//使用性质
	private String engineNum;//发动机号 
	private String vehicleAllocation;//车辆配置
	private String colorName;//颜色
	private Date productionDate;//出厂日期
	private String seriesName;//品牌
	private String fuelType;//燃料种类
	private String hbbj;//环保标准
	private String modelName;//车型
	private String vehicleAndVesselTax;//车船使用税
	private String registry;//登记证书
	private String drivingLicense;//机动车行驶证
	private Date trafficInsureData;//交强有效期
	private String purchaseTax;//购置税凭证
	private Integer isAssessed;//是否评估
	private Double assessedPrice;//评估金额


	public String getEmissions() {
		return emissions;
	}
	public void setEmissions(String emissions) {
		this.emissions = emissions;
	}
	public String getGearForm() {
		return gearForm;
	}
	public void setGearForm(String gearForm) {
		this.gearForm = gearForm;
	}
	public Date getScrapDate() {
		return scrapDate;
	}
	public void setScrapDate(Date scrapDate) {
		this.scrapDate = scrapDate;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getAnnualInspectionDate() {
		return annualInspectionDate;
	}
	public void setAnnualInspectionDate(Date annualInspectionDate) {
		this.annualInspectionDate = annualInspectionDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOriginCertificate() {
		return originCertificate;
	}
	public void setOriginCertificate(String originCertificate) {
		this.originCertificate = originCertificate;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public Date getBuyDate() {
		return buyDate;
	}
	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public String getTrafficInsureInfo() {
		return trafficInsureInfo;
	}
	public void setTrafficInsureInfo(String trafficInsureInfo) {
		this.trafficInsureInfo = trafficInsureInfo;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getEngineNum() {
		return engineNum;
	}
	public void setEngineNum(String engineNum) {
		this.engineNum = engineNum;
	}
	public String getVehicleAllocation() {
		return vehicleAllocation;
	}
	public void setVehicleAllocation(String vehicleAllocation) {
		this.vehicleAllocation = vehicleAllocation;
	}
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public Date getProductionDate() {
		return productionDate;
	}
	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getFuelType() {
		return fuelType;
	}
	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}
	public String getHbbj() {
		return hbbj;
	}
	public void setHbbj(String hbbj) {
		this.hbbj = hbbj;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getVehicleAndVesselTax() {
		return vehicleAndVesselTax;
	}
	public void setVehicleAndVesselTax(String vehicleAndVesselTax) {
		this.vehicleAndVesselTax = vehicleAndVesselTax;
	}
	public String getRegistry() {
		return registry;
	}
	public void setRegistry(String registry) {
		this.registry = registry;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public Date getTrafficInsureData() {
		return trafficInsureData;
	}
	public void setTrafficInsureData(Date trafficInsureData) {
		this.trafficInsureData = trafficInsureData;
	}
	public String getPurchaseTax() {
		return purchaseTax;
	}
	public void setPurchaseTax(String purchaseTax) {
		this.purchaseTax = purchaseTax;
	}
	public Integer getIsAssessed() {
		return isAssessed;
	}
	public void setIsAssessed(Integer isAssessed) {
		this.isAssessed = isAssessed;
	}
	public Double getAssessedPrice() {
		return assessedPrice;
	}
	public void setAssessedPrice(Double assessedPrice) {
		this.assessedPrice = assessedPrice;
	}
	
}
