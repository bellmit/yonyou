
	
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SADCS049VO extends BaseVO{
	private static final long serialVersionUID = 1L;
	private String entityCode;//经销商代码
	private String customerNo;//客户编号
	private String customerProvince;//客户所在地省份
	private String customerCity;//客户所在地城市
	private Integer customerType;//客户类型
	private String intentionBrandCode;//意向品牌
	private String intentionSeriesCode;//意向车系
	private String intentionModelCode;//意向车型
	private String usedCarBrandCode;//二手车品牌
	private String usedCarSeriesCode;//二手车车系
	private String usedCarModelCode;//二手车车型
	private String usedCarLicense;//二手车车牌
	private String usedCarVin;//二手车VIN
	private Double usedCarAssessAmount;//二手车评估金额
	private Date usedCarLicenseDate;//二手车上次上牌日期（YYYY-MM-DD）
	private Long usedCarMileage;//二手车里程数
	private String usedCarDescribe;//二手车描述
	private Integer reportType;//上报类型:1：新增、2：更新、3：删除
	private Long itemId;//单条数据标识ID
	private Date intentionDate;//置换意向时间
	
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getCustomerProvince() {
		return customerProvince;
	}
	public void setCustomerProvince(String customerProvince) {
		this.customerProvince = customerProvince;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getIntentionBrandCode() {
		return intentionBrandCode;
	}
	public void setIntentionBrandCode(String intentionBrandCode) {
		this.intentionBrandCode = intentionBrandCode;
	}
	public String getIntentionSeriesCode() {
		return intentionSeriesCode;
	}
	public void setIntentionSeriesCode(String intentionSeriesCode) {
		this.intentionSeriesCode = intentionSeriesCode;
	}
	public String getIntentionModelCode() {
		return intentionModelCode;
	}
	public void setIntentionModelCode(String intentionModelCode) {
		this.intentionModelCode = intentionModelCode;
	}
	public String getUsedCarBrandCode() {
		return usedCarBrandCode;
	}
	public void setUsedCarBrandCode(String usedCarBrandCode) {
		this.usedCarBrandCode = usedCarBrandCode;
	}
	public String getUsedCarSeriesCode() {
		return usedCarSeriesCode;
	}
	public void setUsedCarSeriesCode(String usedCarSeriesCode) {
		this.usedCarSeriesCode = usedCarSeriesCode;
	}
	public String getUsedCarModelCode() {
		return usedCarModelCode;
	}
	public void setUsedCarModelCode(String usedCarModelCode) {
		this.usedCarModelCode = usedCarModelCode;
	}
	public String getUsedCarLicense() {
		return usedCarLicense;
	}
	public void setUsedCarLicense(String usedCarLicense) {
		this.usedCarLicense = usedCarLicense;
	}
	public String getUsedCarVin() {
		return usedCarVin;
	}
	public void setUsedCarVin(String usedCarVin) {
		this.usedCarVin = usedCarVin;
	}
	public Double getUsedCarAssessAmount() {
		return usedCarAssessAmount;
	}
	public void setUsedCarAssessAmount(Double usedCarAssessAmount) {
		this.usedCarAssessAmount = usedCarAssessAmount;
	}
	public Date getUsedCarLicenseDate() {
		return usedCarLicenseDate;
	}
	public void setUsedCarLicenseDate(Date usedCarLicenseDate) {
		this.usedCarLicenseDate = usedCarLicenseDate;
	}
	public Long getUsedCarMileage() {
		return usedCarMileage;
	}
	public void setUsedCarMileage(Long usedCarMileage) {
		this.usedCarMileage = usedCarMileage;
	}
	public String getUsedCarDescribe() {
		return usedCarDescribe;
	}
	public void setUsedCarDescribe(String usedCarDescribe) {
		this.usedCarDescribe = usedCarDescribe;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Date getIntentionDate() {
		return intentionDate;
	}
	public void setIntentionDate(Date intentionDate) {
		this.intentionDate = intentionDate;
	}

}
