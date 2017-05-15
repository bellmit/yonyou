
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TtUsedCarReplacementIntentionDetailDTO {
	private Double usedCarAssessAmount;
	private Long usedCarMileage;
	private String intentionModelCode;
	private String usedCarLicense;
	private String customerProvince;
	private Date updateDate;
	private String dealerCode;
	private Long createBy;
	private String usedCarSeriesCode;
	private String usedCarBrandCode;
	private String usedCarDescribe;
	private Integer isDel;
	private String customerCity;
	private String intentionBrandCode;
	private Long updateBy;
	private String intentionSeriesCode;
	private String usedCarModelCode;
	private Date usedCarLicenseDate;
	private Long id;
	private String usedCarVin;
	private Date createDate;
	private Integer customerType;
	private Long itemId;//单条数据标识ID
	private Date intentionDate;//置换意向时间
	private String customerNo;//客户编号

	public void setUsedCarAssessAmount(Double usedCarAssessAmount){
		this.usedCarAssessAmount=usedCarAssessAmount;
	}

	public Double getUsedCarAssessAmount(){
		return this.usedCarAssessAmount;
	}

	public void setUsedCarMileage(Long usedCarMileage){
		this.usedCarMileage=usedCarMileage;
	}

	public Long getUsedCarMileage(){
		return this.usedCarMileage;
	}

	public void setIntentionModelCode(String intentionModelCode){
		this.intentionModelCode=intentionModelCode;
	}

	public String getIntentionModelCode(){
		return this.intentionModelCode;
	}

	public void setUsedCarLicense(String usedCarLicense){
		this.usedCarLicense=usedCarLicense;
	}

	public String getUsedCarLicense(){
		return this.usedCarLicense;
	}

	public void setCustomerProvince(String customerProvince){
		this.customerProvince=customerProvince;
	}

	public String getCustomerProvince(){
		return this.customerProvince;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setUsedCarSeriesCode(String usedCarSeriesCode){
		this.usedCarSeriesCode=usedCarSeriesCode;
	}

	public String getUsedCarSeriesCode(){
		return this.usedCarSeriesCode;
	}

	public void setUsedCarBrandCode(String usedCarBrandCode){
		this.usedCarBrandCode=usedCarBrandCode;
	}

	public String getUsedCarBrandCode(){
		return this.usedCarBrandCode;
	}

	public void setUsedCarDescribe(String usedCarDescribe){
		this.usedCarDescribe=usedCarDescribe;
	}

	public String getUsedCarDescribe(){
		return this.usedCarDescribe;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setCustomerCity(String customerCity){
		this.customerCity=customerCity;
	}

	public String getCustomerCity(){
		return this.customerCity;
	}

	public void setIntentionBrandCode(String intentionBrandCode){
		this.intentionBrandCode=intentionBrandCode;
	}

	public String getIntentionBrandCode(){
		return this.intentionBrandCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setIntentionSeriesCode(String intentionSeriesCode){
		this.intentionSeriesCode=intentionSeriesCode;
	}

	public String getIntentionSeriesCode(){
		return this.intentionSeriesCode;
	}

	public void setUsedCarModelCode(String usedCarModelCode){
		this.usedCarModelCode=usedCarModelCode;
	}

	public String getUsedCarModelCode(){
		return this.usedCarModelCode;
	}

	public void setUsedCarLicenseDate(Date usedCarLicenseDate){
		this.usedCarLicenseDate=usedCarLicenseDate;
	}

	public Date getUsedCarLicenseDate(){
		return this.usedCarLicenseDate;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setUsedCarVin(String usedCarVin){
		this.usedCarVin=usedCarVin;
	}

	public String getUsedCarVin(){
		return this.usedCarVin;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public Integer getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
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

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
}