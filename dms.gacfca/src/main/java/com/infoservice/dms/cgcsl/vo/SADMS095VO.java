package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SADMS095VO {
	private String entityCode; //下端：经销商代码  CHAR(8)  上端： 
    private String customerNo;//客户编号
    private String customerName;//人名
    private String contactorMobile;//电话
    private Integer customerType;//客户类型
    private Integer ctCode;//证件类型
    private String certificateNo;//证件编号
    private Integer gender;//性别
    private String address;//地址
    private String zipCode;//邮编
    private String soNo;// 订单编号
    private String vin;// VIN
    private String invoiceTypeCode;// 发票类型
    private String invoiceNo;// 发票编号
    private Integer invoiceChargeType;// 费用类型
    private Double invoiceAmount;// 发票金额
    private String invoiceCustomer;// 开票客户
    private String invoiceWriter ;// 开票人员
    private Date invoiceDate;// 开票日期
    private String transactor;// 经办人
    private String remark;// 备注
    private Double assessedPrice ;// 评估金额
    private Double oldCarPurchase ;// 收购金额
    private String oldBrandCode;// 品牌
    private String oldSeriesCode;// 车系
    private String oldModelCode ;// 车型
    private Integer isPermuted;// 是否置换
    private String permutedVin ;// 置换车辆
    private String permutedDesc ;// 二手车描述
    private String assessedLicense;// 车牌号
    private String fileOldA;// 收购协议ID
    private String fileUrloldA ;// 收购协议URL
    private Integer province;//省
    private Integer city;//市
    private Integer district;//区
    private String email;//邮编
    private Date birthday;//生日
    private Integer ownerMarriage;//是否结婚
    private Integer educationLevel;//教育程度
    private Integer familyIncome;//家庭月收
    private String bestContactTime;//最佳联系时间
    private String hobby;//爱好
    private String industryFirst;//所在行业大类
    private String industrySecond;//所在行业二类
    private String vocationType;//职位
    private String positionName;//职务
    private String soldBy;//销售顾问
    private Integer isModify;//是否更改
    private Date confirmedDate;//销售日期
    private Double salesMileage;//车辆交付公里数
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getContactorMobile() {
        return contactorMobile;
    }
    
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }
    
    public Integer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    public Integer getCtCode() {
        return ctCode;
    }
    
    public void setCtCode(Integer ctCode) {
        this.ctCode = ctCode;
    }
    
    public String getCertificateNo() {
        return certificateNo;
    }
    
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getSoNo() {
        return soNo;
    }
    
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }
    
    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getInvoiceTypeCode() {
        return invoiceTypeCode;
    }
    
    public void setInvoiceTypeCode(String invoiceTypeCode) {
        this.invoiceTypeCode = invoiceTypeCode;
    }
    
    public String getInvoiceNo() {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
    
    public Integer getInvoiceChargeType() {
        return invoiceChargeType;
    }
    
    public void setInvoiceChargeType(Integer invoiceChargeType) {
        this.invoiceChargeType = invoiceChargeType;
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
    
    public Date getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    public String getTransactor() {
        return transactor;
    }
    
    public void setTransactor(String transactor) {
        this.transactor = transactor;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Double getAssessedPrice() {
        return assessedPrice;
    }
    
    public void setAssessedPrice(Double assessedPrice) {
        this.assessedPrice = assessedPrice;
    }
    
    public Double getOldCarPurchase() {
        return oldCarPurchase;
    }
    
    public void setOldCarPurchase(Double oldCarPurchase) {
        this.oldCarPurchase = oldCarPurchase;
    }
    
    public String getOldBrandCode() {
        return oldBrandCode;
    }
    
    public void setOldBrandCode(String oldBrandCode) {
        this.oldBrandCode = oldBrandCode;
    }
    
    public String getOldSeriesCode() {
        return oldSeriesCode;
    }
    
    public void setOldSeriesCode(String oldSeriesCode) {
        this.oldSeriesCode = oldSeriesCode;
    }
    
    public String getOldModelCode() {
        return oldModelCode;
    }
    
    public void setOldModelCode(String oldModelCode) {
        this.oldModelCode = oldModelCode;
    }
    
    public Integer getIsPermuted() {
        return isPermuted;
    }
    
    public void setIsPermuted(Integer isPermuted) {
        this.isPermuted = isPermuted;
    }
    
    public String getPermutedVin() {
        return permutedVin;
    }
    
    public void setPermutedVin(String permutedVin) {
        this.permutedVin = permutedVin;
    }
    
    public String getPermutedDesc() {
        return permutedDesc;
    }
    
    public void setPermutedDesc(String permutedDesc) {
        this.permutedDesc = permutedDesc;
    }
    
    public String getAssessedLicense() {
        return assessedLicense;
    }
    
    public void setAssessedLicense(String assessedLicense) {
        this.assessedLicense = assessedLicense;
    }
    
    public String getFileOldA() {
        return fileOldA;
    }
    
    public void setFileOldA(String fileOldA) {
        this.fileOldA = fileOldA;
    }
    
    public String getFileUrloldA() {
        return fileUrloldA;
    }
    
    public void setFileUrloldA(String fileUrloldA) {
        this.fileUrloldA = fileUrloldA;
    }
    
    public Integer getProvince() {
        return province;
    }
    
    public void setProvince(Integer province) {
        this.province = province;
    }
    
    public Integer getCity() {
        return city;
    }
    
    public void setCity(Integer city) {
        this.city = city;
    }
    
    public Integer getDistrict() {
        return district;
    }
    
    public void setDistrict(Integer district) {
        this.district = district;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Integer getOwnerMarriage() {
        return ownerMarriage;
    }
    
    public void setOwnerMarriage(Integer ownerMarriage) {
        this.ownerMarriage = ownerMarriage;
    }
    
    public Integer getEducationLevel() {
        return educationLevel;
    }
    
    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }
    
    public Integer getFamilyIncome() {
        return familyIncome;
    }
    
    public void setFamilyIncome(Integer familyIncome) {
        this.familyIncome = familyIncome;
    }
    
    public String getBestContactTime() {
        return bestContactTime;
    }
    
    public void setBestContactTime(String bestContactTime) {
        this.bestContactTime = bestContactTime;
    }
    
    public String getHobby() {
        return hobby;
    }
    
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    public String getIndustryFirst() {
        return industryFirst;
    }
    
    public void setIndustryFirst(String industryFirst) {
        this.industryFirst = industryFirst;
    }
    
    public String getIndustrySecond() {
        return industrySecond;
    }
    
    public void setIndustrySecond(String industrySecond) {
        this.industrySecond = industrySecond;
    }
    
    public String getVocationType() {
        return vocationType;
    }
    
    public void setVocationType(String vocationType) {
        this.vocationType = vocationType;
    }
    
    public String getPositionName() {
        return positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
    public String getSoldBy() {
        return soldBy;
    }
    
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
    
    public Integer getIsModify() {
        return isModify;
    }
    
    public void setIsModify(Integer isModify) {
        this.isModify = isModify;
    }
    
    public Date getConfirmedDate() {
        return confirmedDate;
    }
    
    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }
    
    public Double getSalesMileage() {
        return salesMileage;
    }
    
    public void setSalesMileage(Double salesMileage) {
        this.salesMileage = salesMileage;
    }

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
  
}
