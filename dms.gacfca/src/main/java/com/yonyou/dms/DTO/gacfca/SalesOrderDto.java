package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class SalesOrderDto {
	
	private String dealerCode;
	private String customerName;
	private String contactorName;
	private String phone;
	private String address;
	private String vin;
	private String ownerNo;
	private String productCode;
	private Date submitTime;
	private String license;
	private String soNo;
	private Date invoiceDate;
	private String invoiceNo;
	private String insurationName;
	private Date insuranceBuyDate;
	private Date salesDate;
	private Double salesMileage;
	private Integer vehicleType;
	private String soldBy;
	private Date wrtBeginDate;
	private String handbookNo;
	private Integer gender;
	private Integer ctCode;
	private String certificateNo;
	private String email;
	private String zipCode;
	private Date birthday;
	private Integer ownerMarriage;
	private Integer familyIncome;
	private Integer educationLevel;
	private Integer province;
	private Integer city;
	private Integer district;
	private String remark;
	private Double vehiclePrice;
	private Double priceAll;
	private Integer payMode;
	private Integer loanOrg;//付款银行 字典 金融模块
	private Double installmentAmount;//分期金额
	private Integer installmentNumber;//分期期数 字典
	private Double firstPermentRatio;
	private Double loanRate;
	private LinkedList<LinkManListDto> linkManList;
//	 新增二手车VO
	private LinkedList<SecondCarListDto> secondCarList; //二手车VO  
	// 新增的字段
	private String weddingDay;//结婚纪念日
	private String reference;//推荐人
	private String referenceTel;//推荐人电话
	private String salesAdviser;//销售顾问//原来是有soldBy
	private Integer ctmType;//客户类型
	private String fax;//传真
	private String bestContactType;//最佳联系方式
	private String bestContactTime;//最佳联系时间
	private String hobby;//爱好
	private String industryFirst;//所在行业大类
	private String industrySecond;//所在行业二类
	private String vocationType;//职业类别
	private String positionName;//职务名称
	private String isFirstBuy;//是否首次购车
	private String isPersonDriveCar;//是否亲自驾车
	private String buyPurpose;//购车目的
	private String choiceReason;//选车理由
	private String buyReason;//购车因素
	private String ctmForm;//客户来源  改成了string
	private String mediaType;//媒体类型
	private String mediaDetail;//渠道细分
	private String familyMember;//家庭成员
	private String im;//IM(QQ/MSN/微博)
	private String companyPhone;//联系电话
	private String companyName;//公司名称
	private String existBrand;//现有品牌
	private String existSeries;//现有车系
	private String existModel;//现有车型
	private String purchaseYear;//年份
	private Double mileage;//里程
	private String purchaseDiffer;//购买区分
	private String editor;//决策者
	private Double budgetAmount;//预算金额
	private String intentionBrand;//品牌
	private String intentionSeries;//车系
	private String intentionModel;//车型
	private String intentionColor;//颜色
	private String intentionRemark;//适合车型原因
	private String otherNeed;//其他需求（建议车型）\
	private String fileId;//附件id
	private String fileUrl;//
	private Integer isDccOffer;//客户来源 是否DCC邀约
	private Date recordDate;//开票登记日期
	private String oldCustomerVin;//保客推荐人VIN号
	
	private String useProvince;//使用地省份
	private String useCity;//使用地城市
	private Integer customerType;//客户类型（DCS端代码）:20291001：个人、20291002：公司
	private String newCarBrandCode;//新车品牌
	private String newCarSeriesCode;//新车车系
	private String newCarModelCode;//新车车型
	private String newCarVin;//新车车架号
	private Date replacementDealDate;//置换成交时间（YYYY-MM-DD）
	private String usedCarBrandCode;//二手车品牌
	private String usedCarSeriesCode;//二手车车系
	private String usedCarModelCode;//二手车车型
	private String usedCarLicense;//二手车车牌
	private String usedCarVin;//二手车车架号
	private Double usedCarBuyAmount;//二手车收购金额
	private Date usedCarLicenseDate;//二手车上次上牌日期（YYYY-MM-DD）
	private Long usedCarMileage;//二手车里程数
	private String usedCarDescribe;//二手车描述

	/**
	 * 电商订单车辆实销（接收DMS接口）
	 * 电商零售订单实销后由DMS上报给DCS
	 * add by huyu
	 * 2016-5-9
	 * @return
	 */
	private String ecOrderNo;//电商订单号	(必传)
	private String brandCode;//品牌代码	(必传)
	private String seriesCode;//车系代码	(必传)
	private String modelCode;//车型代码	(必传)
	private String groupCode;//车款代码	(必传)
	private String colorCode;//颜色代码
//	private String customerName;//客户名称	(必传)
	private String modelYear;// 年款
	private String trimCode;// 内饰
	private String tel;//客户联系电话	(必传)
	private String idCrad;//身份证	(必传)
	private Date depositDate;//下定日期	(必传)
	private String retailFinance;//零售金融
	private Float depositAmount;//定金金额
	private Integer orderStatus;//订单类型	(必传)
								/* <提交的状态>
								 *	13011015：经理审核中
								 *	13011020：财务审核中
								 *	13011025：交车确认中
								 *	<取消的状态>
								 *	13011055：已取消
								 *	<交车确认>
								 *	13011030：已交车确认
								 *	<实销完成>
								 *	13011035：已完成
								 */
	private Integer escComfirmType;//电商订单类型(16001001：现车;16001002：CALL车)	(必传)
	private String soldById;//销售顾问ID
	private String soldMobile;//销售顾问手机号
	private String soldEmail;//销售顾问邮箱
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInsurationName() {
		return insurationName;
	}
	public void setInsurationName(String insurationName) {
		this.insurationName = insurationName;
	}
	public Date getInsuranceBuyDate() {
		return insuranceBuyDate;
	}
	public void setInsuranceBuyDate(Date insuranceBuyDate) {
		this.insuranceBuyDate = insuranceBuyDate;
	}
	public Date getSalesDate() {
		return salesDate;
	}
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}
	public Double getSalesMileage() {
		return salesMileage;
	}
	public void setSalesMileage(Double salesMileage) {
		this.salesMileage = salesMileage;
	}
	public Integer getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public Date getWrtBeginDate() {
		return wrtBeginDate;
	}
	public void setWrtBeginDate(Date wrtBeginDate) {
		this.wrtBeginDate = wrtBeginDate;
	}
	public String getHandbookNo() {
		return handbookNo;
	}
	public void setHandbookNo(String handbookNo) {
		this.handbookNo = handbookNo;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	public Integer getFamilyIncome() {
		return familyIncome;
	}
	public void setFamilyIncome(Integer familyIncome) {
		this.familyIncome = familyIncome;
	}
	public Integer getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Double getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	public Double getPriceAll() {
		return priceAll;
	}
	public void setPriceAll(Double priceAll) {
		this.priceAll = priceAll;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public Integer getLoanOrg() {
		return loanOrg;
	}
	public void setLoanOrg(Integer loanOrg) {
		this.loanOrg = loanOrg;
	}
	public Double getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(Double installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}
	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}
	public Double getFirstPermentRatio() {
		return firstPermentRatio;
	}
	public void setFirstPermentRatio(Double firstPermentRatio) {
		this.firstPermentRatio = firstPermentRatio;
	}
	public Double getLoanRate() {
		return loanRate;
	}
	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}
	public LinkedList<LinkManListDto> getLinkManList() {
		return linkManList;
	}
	public void setLinkManList(LinkedList<LinkManListDto> linkManList) {
		this.linkManList = linkManList;
	}
	public LinkedList<SecondCarListDto> getSecondCarList() {
		return secondCarList;
	}
	public void setSecondCarList(LinkedList<SecondCarListDto> secondCarList) {
		this.secondCarList = secondCarList;
	}
	public String getWeddingDay() {
		return weddingDay;
	}
	public void setWeddingDay(String weddingDay) {
		this.weddingDay = weddingDay;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getReferenceTel() {
		return referenceTel;
	}
	public void setReferenceTel(String referenceTel) {
		this.referenceTel = referenceTel;
	}
	public String getSalesAdviser() {
		return salesAdviser;
	}
	public void setSalesAdviser(String salesAdviser) {
		this.salesAdviser = salesAdviser;
	}
	public Integer getCtmType() {
		return ctmType;
	}
	public void setCtmType(Integer ctmType) {
		this.ctmType = ctmType;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getBestContactType() {
		return bestContactType;
	}
	public void setBestContactType(String bestContactType) {
		this.bestContactType = bestContactType;
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
	public String getIsFirstBuy() {
		return isFirstBuy;
	}
	public void setIsFirstBuy(String isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}
	public String getIsPersonDriveCar() {
		return isPersonDriveCar;
	}
	public void setIsPersonDriveCar(String isPersonDriveCar) {
		this.isPersonDriveCar = isPersonDriveCar;
	}
	public String getBuyPurpose() {
		return buyPurpose;
	}
	public void setBuyPurpose(String buyPurpose) {
		this.buyPurpose = buyPurpose;
	}
	public String getChoiceReason() {
		return choiceReason;
	}
	public void setChoiceReason(String choiceReason) {
		this.choiceReason = choiceReason;
	}
	public String getBuyReason() {
		return buyReason;
	}
	public void setBuyReason(String buyReason) {
		this.buyReason = buyReason;
	}
	public String getCtmForm() {
		return ctmForm;
	}
	public void setCtmForm(String ctmForm) {
		this.ctmForm = ctmForm;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaDetail() {
		return mediaDetail;
	}
	public void setMediaDetail(String mediaDetail) {
		this.mediaDetail = mediaDetail;
	}
	public String getFamilyMember() {
		return familyMember;
	}
	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getCompanyPhone() {
		return companyPhone;
	}
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getExistBrand() {
		return existBrand;
	}
	public void setExistBrand(String existBrand) {
		this.existBrand = existBrand;
	}
	public String getExistSeries() {
		return existSeries;
	}
	public void setExistSeries(String existSeries) {
		this.existSeries = existSeries;
	}
	public String getExistModel() {
		return existModel;
	}
	public void setExistModel(String existModel) {
		this.existModel = existModel;
	}
	public String getPurchaseYear() {
		return purchaseYear;
	}
	public void setPurchaseYear(String purchaseYear) {
		this.purchaseYear = purchaseYear;
	}
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public String getPurchaseDiffer() {
		return purchaseDiffer;
	}
	public void setPurchaseDiffer(String purchaseDiffer) {
		this.purchaseDiffer = purchaseDiffer;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public Double getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getIntentionBrand() {
		return intentionBrand;
	}
	public void setIntentionBrand(String intentionBrand) {
		this.intentionBrand = intentionBrand;
	}
	public String getIntentionSeries() {
		return intentionSeries;
	}
	public void setIntentionSeries(String intentionSeries) {
		this.intentionSeries = intentionSeries;
	}
	public String getIntentionModel() {
		return intentionModel;
	}
	public void setIntentionModel(String intentionModel) {
		this.intentionModel = intentionModel;
	}
	public String getIntentionColor() {
		return intentionColor;
	}
	public void setIntentionColor(String intentionColor) {
		this.intentionColor = intentionColor;
	}
	public String getIntentionRemark() {
		return intentionRemark;
	}
	public void setIntentionRemark(String intentionRemark) {
		this.intentionRemark = intentionRemark;
	}
	public String getOtherNeed() {
		return otherNeed;
	}
	public void setOtherNeed(String otherNeed) {
		this.otherNeed = otherNeed;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Integer getIsDccOffer() {
		return isDccOffer;
	}
	public void setIsDccOffer(Integer isDccOffer) {
		this.isDccOffer = isDccOffer;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getOldCustomerVin() {
		return oldCustomerVin;
	}
	public void setOldCustomerVin(String oldCustomerVin) {
		this.oldCustomerVin = oldCustomerVin;
	}
	public String getUseProvince() {
		return useProvince;
	}
	public void setUseProvince(String useProvince) {
		this.useProvince = useProvince;
	}
	public String getUseCity() {
		return useCity;
	}
	public void setUseCity(String useCity) {
		this.useCity = useCity;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public String getNewCarBrandCode() {
		return newCarBrandCode;
	}
	public void setNewCarBrandCode(String newCarBrandCode) {
		this.newCarBrandCode = newCarBrandCode;
	}
	public String getNewCarSeriesCode() {
		return newCarSeriesCode;
	}
	public void setNewCarSeriesCode(String newCarSeriesCode) {
		this.newCarSeriesCode = newCarSeriesCode;
	}
	public String getNewCarModelCode() {
		return newCarModelCode;
	}
	public void setNewCarModelCode(String newCarModelCode) {
		this.newCarModelCode = newCarModelCode;
	}
	public String getNewCarVin() {
		return newCarVin;
	}
	public void setNewCarVin(String newCarVin) {
		this.newCarVin = newCarVin;
	}
	public Date getReplacementDealDate() {
		return replacementDealDate;
	}
	public void setReplacementDealDate(Date replacementDealDate) {
		this.replacementDealDate = replacementDealDate;
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
	public Double getUsedCarBuyAmount() {
		return usedCarBuyAmount;
	}
	public void setUsedCarBuyAmount(Double usedCarBuyAmount) {
		this.usedCarBuyAmount = usedCarBuyAmount;
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
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getTrimCode() {
		return trimCode;
	}
	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdCrad() {
		return idCrad;
	}
	public void setIdCrad(String idCrad) {
		this.idCrad = idCrad;
	}
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	public String getRetailFinance() {
		return retailFinance;
	}
	public void setRetailFinance(String retailFinance) {
		this.retailFinance = retailFinance;
	}
	public Float getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Float depositAmount) {
		this.depositAmount = depositAmount;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getEscComfirmType() {
		return escComfirmType;
	}
	public void setEscComfirmType(Integer escComfirmType) {
		this.escComfirmType = escComfirmType;
	}
	public String getSoldById() {
		return soldById;
	}
	public void setSoldById(String soldById) {
		this.soldById = soldById;
	}
	public String getSoldMobile() {
		return soldMobile;
	}
	public void setSoldMobile(String soldMobile) {
		this.soldMobile = soldMobile;
	}
	public String getSoldEmail() {
		return soldEmail;
	}
	public void setSoldEmail(String soldEmail) {
		this.soldEmail = soldEmail;
	}
	

}
