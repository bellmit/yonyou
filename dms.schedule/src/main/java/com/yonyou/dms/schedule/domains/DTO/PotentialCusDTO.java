/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class PotentialCusDTO {
	private Integer cusSource;
	private String oldCustomerVin;
	private String oldCustomerName;
	private Date submitTime;
	private String reportAuditingRemark;
	private String eMail;
	private String dealerCode;
	private Integer ver;
	private String contactorPhone;
	private Date createDate;
	private Integer customerStatus;
	private Double unWriteoffSum;
	private Integer isUpload;
	private Double orderPayedSum;
	private Integer isDirect;
	private Integer countryCode;
	private Date consultantTime;
	private Integer intentLevel;
	private Integer ownerMarriage;
	private Integer buyPurpose;
	private String customerName;
	private Date reportDatetime;
	private String reportAbortReason;
	private Integer hasDriverLicense;
	private Integer reportStatus;
	private String zipCode;
	private String recommendEmpName;
	private Integer initLevel;
	private Long soldBy;
	private Integer mediaType;
	private String modifyReason;
	private Long updateBy;
	private Integer isWholesaler;
	private Long delayConsultant;
	private Integer city;
	private Integer isReported;
	private Integer organTypeCode;
	private String reportRemark;
	private Double usableAmount;
	private Integer dKey;
	private Integer educationLevel;
	private String sodCustomerId;
	private Integer industrySecond;
	private Long createBy;
	private Double conPayedSum;
	private Long failConsultant;
	private Integer province;
	private String certificateNo;
	private String fax;
	private Integer district;
	private String buyReason;
	private Integer customerType;
	private Integer vocationType;
	private Integer ageStage;
	private String dcrcService;
	private Integer ctCode;
	private String customerNo;
	private Date downTimestamp;
	private Long intentId;
	private Double gatheredSum;
	private Integer isPersonDriveCar;
	private Integer industryFirst;
	private Integer familyIncome;
	private String campaignCode;
	private Integer isFirstBuy;
	private String organType;
	private Date birthday;
	private Date updateDate;
	private String hobby;
	private Long ownedBy;
	private Date bestContactTime;
	private String positionName;
	private String contactorMobile;
	private Date foundDate;
	private Date replaceDate;
	private String largeCustomerNo;
	private Integer bestContactType;
	private Integer gender;
	private Integer isCrpvip;
	private String remark;
	private String address;
	private Integer choiceReason;
	private Integer firstIsArrived;
	private Date arriveTime;
	private String mediaDetail;
	private Integer isTehShop;
	private Integer isSecondTehShop;
	private Date secondArriveTime;
	private String mergerCusNo;
	private String oemCustomerNo;
	private Integer customerImportantLevel;
	private String cusRatingDesc;
	private String contactorMobile2;
	private String im;
	private Integer cusOrientSort;
	private Integer betterContactTime;
	private Integer betterContactPeriod;
	private String detailDesc;
	private String recommendEmpPhone;
	private Integer familyStructure;
	private String brandNow;
	private String modelNow;
	private String licenseNow;
	private Integer childrenNumber;
	private Integer isSmoke;
	private Integer kaType; //DECIMAL(8) 
	private String mergeCustomerNo;
	private String mergeOemCustomerNo;
	private Date LastArriveTime;
	private Long lastSoldBy;
	private Integer failIntentLevel;
	private Date validityBeginDate;
	private Integer auditStatus;
	private Long auditBy;
	private String auditView;
	private Date auditDate;
	private Date dccDate;
	private Integer isSameDcc;
	private String keepApplyReasion;
	private String familyMember;   
	private String memberMobile;
	private String memberPhone;
	private String companyName;
	private String ownYear;
	private Double ownMileage;
	private String seriesNow;
	private String testDriveAccompany;
	private Integer sleepType;
	private String sleepSeries;
	private String keepApplyRemark;
	private Integer rebuyCustomerType;
	private Date ddcnUpdateDate;//DDCN_UPDATE_DATE 克莱斯勒用来记录级别变动的时间
	private Integer isPadCreate;
	private Integer isBigCustomer;
	private Integer isVerifyAddress;//是否核实地址(1、是 0、否)
	private Integer isOutbound;//数据是否外呼(1.否 2、是)
	private Integer obIsSuccess;//外呼是否成功（1、是 0 、否）
	private Integer reasons; //失败原因 (obIsSuccess=0) 1 非机主 2、非 车主 3、空号/错号 4、占线/无人接听/停机
							//外呼成功原因(obIsSuccess=1) 5、需再联系 6 成功核实 7、信息未核实
	
	private Integer isUpdate;//客户信息是否有更新（1、是 0、否） 外乎失败(obIsSuccess=0) 则isUpdate=0;成功 isUpdate=1; 
	private String outboundRemark;//外呼备注
	private Integer isOwner;//是否车主本人 1、是本人 2 不是本人
	private Date outboundTime;//外呼时间
	private Integer isBinding;//是否进行微信认证
	private Integer isSecondTime;//是否是二次上报
	private Date outboundUploadTime;//外呼上报时间
	private Date outboundReturnTime;//核实反馈日期
	private Date bindingDate;//微信绑定时间

	
	private Date spadUpdateDate;//售中工具更新日期
	private Integer isSpadCreate;//是否售中工具创建
	private Long spadCustomerId;//售中工具客户ID 
	
	private Integer expectTimesRange;
	private Date expectDate;
	private String ecOrderNo;//电商订单编号
	private Integer escOrderStatus;//电商订单状态
	private Integer escType;//电商订单类型
	private Integer isHitFollowUpload;//撞单潜客第一次跟进是否上报
	private Date hitOrderArrive;//接收潜客信息的时间
	
	private Integer isToShop;//是否到店
	private Date timeToShop;//到店时间
	public Integer getCusSource() {
		return cusSource;
	}
	public void setCusSource(Integer cusSource) {
		this.cusSource = cusSource;
	}
	public String getOldCustomerVin() {
		return oldCustomerVin;
	}
	public void setOldCustomerVin(String oldCustomerVin) {
		this.oldCustomerVin = oldCustomerVin;
	}
	public String getOldCustomerName() {
		return oldCustomerName;
	}
	public void setOldCustomerName(String oldCustomerName) {
		this.oldCustomerName = oldCustomerName;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public String getReportAuditingRemark() {
		return reportAuditingRemark;
	}
	public void setReportAuditingRemark(String reportAuditingRemark) {
		this.reportAuditingRemark = reportAuditingRemark;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getContactorPhone() {
		return contactorPhone;
	}
	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(Integer customerStatus) {
		this.customerStatus = customerStatus;
	}
	public Double getUnWriteoffSum() {
		return unWriteoffSum;
	}
	public void setUnWriteoffSum(Double unWriteoffSum) {
		this.unWriteoffSum = unWriteoffSum;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	public Double getOrderPayedSum() {
		return orderPayedSum;
	}
	public void setOrderPayedSum(Double orderPayedSum) {
		this.orderPayedSum = orderPayedSum;
	}
	public Integer getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(Integer isDirect) {
		this.isDirect = isDirect;
	}
	public Integer getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}
	public Date getConsultantTime() {
		return consultantTime;
	}
	public void setConsultantTime(Date consultantTime) {
		this.consultantTime = consultantTime;
	}
	public Integer getIntentLevel() {
		return intentLevel;
	}
	public void setIntentLevel(Integer intentLevel) {
		this.intentLevel = intentLevel;
	}
	public Integer getOwnerMarriage() {
		return ownerMarriage;
	}
	public void setOwnerMarriage(Integer ownerMarriage) {
		this.ownerMarriage = ownerMarriage;
	}
	public Integer getBuyPurpose() {
		return buyPurpose;
	}
	public void setBuyPurpose(Integer buyPurpose) {
		this.buyPurpose = buyPurpose;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getReportDatetime() {
		return reportDatetime;
	}
	public void setReportDatetime(Date reportDatetime) {
		this.reportDatetime = reportDatetime;
	}
	public String getReportAbortReason() {
		return reportAbortReason;
	}
	public void setReportAbortReason(String reportAbortReason) {
		this.reportAbortReason = reportAbortReason;
	}
	public Integer getHasDriverLicense() {
		return hasDriverLicense;
	}
	public void setHasDriverLicense(Integer hasDriverLicense) {
		this.hasDriverLicense = hasDriverLicense;
	}
	public Integer getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(Integer reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getRecommendEmpName() {
		return recommendEmpName;
	}
	public void setRecommendEmpName(String recommendEmpName) {
		this.recommendEmpName = recommendEmpName;
	}
	public Integer getInitLevel() {
		return initLevel;
	}
	public void setInitLevel(Integer initLevel) {
		this.initLevel = initLevel;
	}
	public Long getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(Long soldBy) {
		this.soldBy = soldBy;
	}
	public Integer getMediaType() {
		return mediaType;
	}
	public void setMediaType(Integer mediaType) {
		this.mediaType = mediaType;
	}
	public String getModifyReason() {
		return modifyReason;
	}
	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Integer getIsWholesaler() {
		return isWholesaler;
	}
	public void setIsWholesaler(Integer isWholesaler) {
		this.isWholesaler = isWholesaler;
	}
	public Long getDelayConsultant() {
		return delayConsultant;
	}
	public void setDelayConsultant(Long delayConsultant) {
		this.delayConsultant = delayConsultant;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getIsReported() {
		return isReported;
	}
	public void setIsReported(Integer isReported) {
		this.isReported = isReported;
	}
	public Integer getOrganTypeCode() {
		return organTypeCode;
	}
	public void setOrganTypeCode(Integer organTypeCode) {
		this.organTypeCode = organTypeCode;
	}
	public String getReportRemark() {
		return reportRemark;
	}
	public void setReportRemark(String reportRemark) {
		this.reportRemark = reportRemark;
	}
	public Double getUsableAmount() {
		return usableAmount;
	}
	public void setUsableAmount(Double usableAmount) {
		this.usableAmount = usableAmount;
	}
	public Integer getdKey() {
		return dKey;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public Integer getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(Integer educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getSodCustomerId() {
		return sodCustomerId;
	}
	public void setSodCustomerId(String sodCustomerId) {
		this.sodCustomerId = sodCustomerId;
	}
	public Integer getIndustrySecond() {
		return industrySecond;
	}
	public void setIndustrySecond(Integer industrySecond) {
		this.industrySecond = industrySecond;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Double getConPayedSum() {
		return conPayedSum;
	}
	public void setConPayedSum(Double conPayedSum) {
		this.conPayedSum = conPayedSum;
	}
	public Long getFailConsultant() {
		return failConsultant;
	}
	public void setFailConsultant(Long failConsultant) {
		this.failConsultant = failConsultant;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public String getBuyReason() {
		return buyReason;
	}
	public void setBuyReason(String buyReason) {
		this.buyReason = buyReason;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public Integer getVocationType() {
		return vocationType;
	}
	public void setVocationType(Integer vocationType) {
		this.vocationType = vocationType;
	}
	public Integer getAgeStage() {
		return ageStage;
	}
	public void setAgeStage(Integer ageStage) {
		this.ageStage = ageStage;
	}
	public String getDcrcService() {
		return dcrcService;
	}
	public void setDcrcService(String dcrcService) {
		this.dcrcService = dcrcService;
	}
	public Integer getCtCode() {
		return ctCode;
	}
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	public Long getIntentId() {
		return intentId;
	}
	public void setIntentId(Long intentId) {
		this.intentId = intentId;
	}
	public Double getGatheredSum() {
		return gatheredSum;
	}
	public void setGatheredSum(Double gatheredSum) {
		this.gatheredSum = gatheredSum;
	}
	public Integer getIsPersonDriveCar() {
		return isPersonDriveCar;
	}
	public void setIsPersonDriveCar(Integer isPersonDriveCar) {
		this.isPersonDriveCar = isPersonDriveCar;
	}
	public Integer getIndustryFirst() {
		return industryFirst;
	}
	public void setIndustryFirst(Integer industryFirst) {
		this.industryFirst = industryFirst;
	}
	public Integer getFamilyIncome() {
		return familyIncome;
	}
	public void setFamilyIncome(Integer familyIncome) {
		this.familyIncome = familyIncome;
	}
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public Integer getIsFirstBuy() {
		return isFirstBuy;
	}
	public void setIsFirstBuy(Integer isFirstBuy) {
		this.isFirstBuy = isFirstBuy;
	}
	public String getOrganType() {
		return organType;
	}
	public void setOrganType(String organType) {
		this.organType = organType;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public Long getOwnedBy() {
		return ownedBy;
	}
	public void setOwnedBy(Long ownedBy) {
		this.ownedBy = ownedBy;
	}
	public Date getBestContactTime() {
		return bestContactTime;
	}
	public void setBestContactTime(Date bestContactTime) {
		this.bestContactTime = bestContactTime;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getContactorMobile() {
		return contactorMobile;
	}
	public void setContactorMobile(String contactorMobile) {
		this.contactorMobile = contactorMobile;
	}
	public Date getFoundDate() {
		return foundDate;
	}
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}
	public Date getReplaceDate() {
		return replaceDate;
	}
	public void setReplaceDate(Date replaceDate) {
		this.replaceDate = replaceDate;
	}
	public String getLargeCustomerNo() {
		return largeCustomerNo;
	}
	public void setLargeCustomerNo(String largeCustomerNo) {
		this.largeCustomerNo = largeCustomerNo;
	}
	public Integer getBestContactType() {
		return bestContactType;
	}
	public void setBestContactType(Integer bestContactType) {
		this.bestContactType = bestContactType;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getIsCrpvip() {
		return isCrpvip;
	}
	public void setIsCrpvip(Integer isCrpvip) {
		this.isCrpvip = isCrpvip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getChoiceReason() {
		return choiceReason;
	}
	public void setChoiceReason(Integer choiceReason) {
		this.choiceReason = choiceReason;
	}
	public Integer getFirstIsArrived() {
		return firstIsArrived;
	}
	public void setFirstIsArrived(Integer firstIsArrived) {
		this.firstIsArrived = firstIsArrived;
	}
	public Date getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}
	public String getMediaDetail() {
		return mediaDetail;
	}
	public void setMediaDetail(String mediaDetail) {
		this.mediaDetail = mediaDetail;
	}
	public Integer getIsTehShop() {
		return isTehShop;
	}
	public void setIsTehShop(Integer isTehShop) {
		this.isTehShop = isTehShop;
	}
	public Integer getIsSecondTehShop() {
		return isSecondTehShop;
	}
	public void setIsSecondTehShop(Integer isSecondTehShop) {
		this.isSecondTehShop = isSecondTehShop;
	}
	public Date getSecondArriveTime() {
		return secondArriveTime;
	}
	public void setSecondArriveTime(Date secondArriveTime) {
		this.secondArriveTime = secondArriveTime;
	}
	public String getMergerCusNo() {
		return mergerCusNo;
	}
	public void setMergerCusNo(String mergerCusNo) {
		this.mergerCusNo = mergerCusNo;
	}
	public String getOemCustomerNo() {
		return oemCustomerNo;
	}
	public void setOemCustomerNo(String oemCustomerNo) {
		this.oemCustomerNo = oemCustomerNo;
	}
	public Integer getCustomerImportantLevel() {
		return customerImportantLevel;
	}
	public void setCustomerImportantLevel(Integer customerImportantLevel) {
		this.customerImportantLevel = customerImportantLevel;
	}
	public String getCusRatingDesc() {
		return cusRatingDesc;
	}
	public void setCusRatingDesc(String cusRatingDesc) {
		this.cusRatingDesc = cusRatingDesc;
	}
	public String getContactorMobile2() {
		return contactorMobile2;
	}
	public void setContactorMobile2(String contactorMobile2) {
		this.contactorMobile2 = contactorMobile2;
	}
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public Integer getCusOrientSort() {
		return cusOrientSort;
	}
	public void setCusOrientSort(Integer cusOrientSort) {
		this.cusOrientSort = cusOrientSort;
	}
	public Integer getBetterContactTime() {
		return betterContactTime;
	}
	public void setBetterContactTime(Integer betterContactTime) {
		this.betterContactTime = betterContactTime;
	}
	public Integer getBetterContactPeriod() {
		return betterContactPeriod;
	}
	public void setBetterContactPeriod(Integer betterContactPeriod) {
		this.betterContactPeriod = betterContactPeriod;
	}
	public String getDetailDesc() {
		return detailDesc;
	}
	public void setDetailDesc(String detailDesc) {
		this.detailDesc = detailDesc;
	}
	public String getRecommendEmpPhone() {
		return recommendEmpPhone;
	}
	public void setRecommendEmpPhone(String recommendEmpPhone) {
		this.recommendEmpPhone = recommendEmpPhone;
	}
	public Integer getFamilyStructure() {
		return familyStructure;
	}
	public void setFamilyStructure(Integer familyStructure) {
		this.familyStructure = familyStructure;
	}
	public String getBrandNow() {
		return brandNow;
	}
	public void setBrandNow(String brandNow) {
		this.brandNow = brandNow;
	}
	public String getModelNow() {
		return modelNow;
	}
	public void setModelNow(String modelNow) {
		this.modelNow = modelNow;
	}
	public String getLicenseNow() {
		return licenseNow;
	}
	public void setLicenseNow(String licenseNow) {
		this.licenseNow = licenseNow;
	}
	public Integer getChildrenNumber() {
		return childrenNumber;
	}
	public void setChildrenNumber(Integer childrenNumber) {
		this.childrenNumber = childrenNumber;
	}
	public Integer getIsSmoke() {
		return isSmoke;
	}
	public void setIsSmoke(Integer isSmoke) {
		this.isSmoke = isSmoke;
	}
	public Integer getKaType() {
		return kaType;
	}
	public void setKaType(Integer kaType) {
		this.kaType = kaType;
	}
	public String getMergeCustomerNo() {
		return mergeCustomerNo;
	}
	public void setMergeCustomerNo(String mergeCustomerNo) {
		this.mergeCustomerNo = mergeCustomerNo;
	}
	public String getMergeOemCustomerNo() {
		return mergeOemCustomerNo;
	}
	public void setMergeOemCustomerNo(String mergeOemCustomerNo) {
		this.mergeOemCustomerNo = mergeOemCustomerNo;
	}
	public Date getLastArriveTime() {
		return LastArriveTime;
	}
	public void setLastArriveTime(Date lastArriveTime) {
		LastArriveTime = lastArriveTime;
	}
	public Long getLastSoldBy() {
		return lastSoldBy;
	}
	public void setLastSoldBy(Long lastSoldBy) {
		this.lastSoldBy = lastSoldBy;
	}
	public Integer getFailIntentLevel() {
		return failIntentLevel;
	}
	public void setFailIntentLevel(Integer failIntentLevel) {
		this.failIntentLevel = failIntentLevel;
	}
	public Date getValidityBeginDate() {
		return validityBeginDate;
	}
	public void setValidityBeginDate(Date validityBeginDate) {
		this.validityBeginDate = validityBeginDate;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Long getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(Long auditBy) {
		this.auditBy = auditBy;
	}
	public String getAuditView() {
		return auditView;
	}
	public void setAuditView(String auditView) {
		this.auditView = auditView;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public Date getDccDate() {
		return dccDate;
	}
	public void setDccDate(Date dccDate) {
		this.dccDate = dccDate;
	}
	public Integer getIsSameDcc() {
		return isSameDcc;
	}
	public void setIsSameDcc(Integer isSameDcc) {
		this.isSameDcc = isSameDcc;
	}
	public String getKeepApplyReasion() {
		return keepApplyReasion;
	}
	public void setKeepApplyReasion(String keepApplyReasion) {
		this.keepApplyReasion = keepApplyReasion;
	}
	public String getFamilyMember() {
		return familyMember;
	}
	public void setFamilyMember(String familyMember) {
		this.familyMember = familyMember;
	}
	public String getMemberMobile() {
		return memberMobile;
	}
	public void setMemberMobile(String memberMobile) {
		this.memberMobile = memberMobile;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOwnYear() {
		return ownYear;
	}
	public void setOwnYear(String ownYear) {
		this.ownYear = ownYear;
	}
	public Double getOwnMileage() {
		return ownMileage;
	}
	public void setOwnMileage(Double ownMileage) {
		this.ownMileage = ownMileage;
	}
	public String getSeriesNow() {
		return seriesNow;
	}
	public void setSeriesNow(String seriesNow) {
		this.seriesNow = seriesNow;
	}
	public String getTestDriveAccompany() {
		return testDriveAccompany;
	}
	public void setTestDriveAccompany(String testDriveAccompany) {
		this.testDriveAccompany = testDriveAccompany;
	}
	public Integer getSleepType() {
		return sleepType;
	}
	public void setSleepType(Integer sleepType) {
		this.sleepType = sleepType;
	}
	public String getSleepSeries() {
		return sleepSeries;
	}
	public void setSleepSeries(String sleepSeries) {
		this.sleepSeries = sleepSeries;
	}
	public String getKeepApplyRemark() {
		return keepApplyRemark;
	}
	public void setKeepApplyRemark(String keepApplyRemark) {
		this.keepApplyRemark = keepApplyRemark;
	}
	public Integer getRebuyCustomerType() {
		return rebuyCustomerType;
	}
	public void setRebuyCustomerType(Integer rebuyCustomerType) {
		this.rebuyCustomerType = rebuyCustomerType;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public Integer getIsPadCreate() {
		return isPadCreate;
	}
	public void setIsPadCreate(Integer isPadCreate) {
		this.isPadCreate = isPadCreate;
	}
	public Integer getIsBigCustomer() {
		return isBigCustomer;
	}
	public void setIsBigCustomer(Integer isBigCustomer) {
		this.isBigCustomer = isBigCustomer;
	}
	public Integer getIsVerifyAddress() {
		return isVerifyAddress;
	}
	public void setIsVerifyAddress(Integer isVerifyAddress) {
		this.isVerifyAddress = isVerifyAddress;
	}
	public Integer getIsOutbound() {
		return isOutbound;
	}
	public void setIsOutbound(Integer isOutbound) {
		this.isOutbound = isOutbound;
	}
	public Integer getObIsSuccess() {
		return obIsSuccess;
	}
	public void setObIsSuccess(Integer obIsSuccess) {
		this.obIsSuccess = obIsSuccess;
	}
	public Integer getReasons() {
		return reasons;
	}
	public void setReasons(Integer reasons) {
		this.reasons = reasons;
	}
	public Integer getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getOutboundRemark() {
		return outboundRemark;
	}
	public void setOutboundRemark(String outboundRemark) {
		this.outboundRemark = outboundRemark;
	}
	public Integer getIsOwner() {
		return isOwner;
	}
	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}
	public Date getOutboundTime() {
		return outboundTime;
	}
	public void setOutboundTime(Date outboundTime) {
		this.outboundTime = outboundTime;
	}
	public Integer getIsBinding() {
		return isBinding;
	}
	public void setIsBinding(Integer isBinding) {
		this.isBinding = isBinding;
	}
	public Integer getIsSecondTime() {
		return isSecondTime;
	}
	public void setIsSecondTime(Integer isSecondTime) {
		this.isSecondTime = isSecondTime;
	}
	public Date getOutboundUploadTime() {
		return outboundUploadTime;
	}
	public void setOutboundUploadTime(Date outboundUploadTime) {
		this.outboundUploadTime = outboundUploadTime;
	}
	public Date getOutboundReturnTime() {
		return outboundReturnTime;
	}
	public void setOutboundReturnTime(Date outboundReturnTime) {
		this.outboundReturnTime = outboundReturnTime;
	}
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public Date getSpadUpdateDate() {
		return spadUpdateDate;
	}
	public void setSpadUpdateDate(Date spadUpdateDate) {
		this.spadUpdateDate = spadUpdateDate;
	}
	public Integer getIsSpadCreate() {
		return isSpadCreate;
	}
	public void setIsSpadCreate(Integer isSpadCreate) {
		this.isSpadCreate = isSpadCreate;
	}
	public Long getSpadCustomerId() {
		return spadCustomerId;
	}
	public void setSpadCustomerId(Long spadCustomerId) {
		this.spadCustomerId = spadCustomerId;
	}
	public Integer getExpectTimesRange() {
		return expectTimesRange;
	}
	public void setExpectTimesRange(Integer expectTimesRange) {
		this.expectTimesRange = expectTimesRange;
	}
	public Date getExpectDate() {
		return expectDate;
	}
	public void setExpectDate(Date expectDate) {
		this.expectDate = expectDate;
	}
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public Integer getEscOrderStatus() {
		return escOrderStatus;
	}
	public void setEscOrderStatus(Integer escOrderStatus) {
		this.escOrderStatus = escOrderStatus;
	}
	public Integer getEscType() {
		return escType;
	}
	public void setEscType(Integer escType) {
		this.escType = escType;
	}
	public Integer getIsHitFollowUpload() {
		return isHitFollowUpload;
	}
	public void setIsHitFollowUpload(Integer isHitFollowUpload) {
		this.isHitFollowUpload = isHitFollowUpload;
	}
	public Date getHitOrderArrive() {
		return hitOrderArrive;
	}
	public void setHitOrderArrive(Date hitOrderArrive) {
		this.hitOrderArrive = hitOrderArrive;
	}
	public Integer getIsToShop() {
		return isToShop;
	}
	public void setIsToShop(Integer isToShop) {
		this.isToShop = isToShop;
	}
	public Date getTimeToShop() {
		return timeToShop;
	}
	public void setTimeToShop(Date timeToShop) {
		this.timeToShop = timeToShop;
	}
	
	
	
	
}
