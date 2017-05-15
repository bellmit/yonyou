package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class TmVehicleDTO {

	private String vin;// vin

	private String vins;//前台传过来的值

	private String dealercode;

	private String ownerNo;// 车主编号

	private String customerNo;// 客户编号

	private String ownerNoOld;// 原车主编号

	private String license;// 车牌号

	private String engineNo;// 发动机号

	private String gearBox;// 变速箱箱号

	private String innerColor;// 内饰颜色

	private String brand;// 品牌

	private String series;// 车系

	private String model;// 车型

	private String apAckAge;// 配置

	private String modelYear;// 车型年

	private String exhaustQuantity;// 排气量

	private Date productDate;// 制造日期

	private String handbookNo;// 保修手册号码

	private String shifeType;// 排挡类别

	private Integer fuelType;// 燃料类别

	private Integer vehiclePurpose;// 车辆用途

	public Integer getVehiclePurpose() {
		return vehiclePurpose;
	}

	public void setVehiclePurpose(Integer vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}

	public Integer getBusinessKind() {
		return businessKind;
	}

	public void setBusinessKind(Integer businessKind) {
		this.businessKind = businessKind;
	}

	public Integer getIsSelfCompany() {
		return isSelfCompany;
	}

	public void setIsSelfCompany(Integer isSelfCompany) {
		this.isSelfCompany = isSelfCompany;
	}

	public Integer getDelivererHobbyContact() {
		return delivererHobbyContact;
	}

	public void setDelivererHobbyContact(Integer delivererHobbyContact) {
		this.delivererHobbyContact = delivererHobbyContact;
	}

	public Integer getSubmitStatus() {
		return submitStatus;
	}

	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}

	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	public Integer getIsUploadGroup() {
		return isUploadGroup;
	}

	public void setIsUploadGroup(Integer isUploadGroup) {
		this.isUploadGroup = isUploadGroup;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getNoValidReason() {
		return noValidReason;
	}

	public void setNoValidReason(String noValidReason) {
		this.noValidReason = noValidReason;
	}

	public Integer getIsDcrcAdvisor() {
		return isDcrcAdvisor;
	}

	public void setIsDcrcAdvisor(Integer isDcrcAdvisor) {
		this.isDcrcAdvisor = isDcrcAdvisor;
	}

	public Integer getDischargeStandrd() {
		return dischargeStandrd;
	}

	public void setDischargeStandrd(Integer dischargeStandrd) {
		this.dischargeStandrd = dischargeStandrd;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getWaysToBuy() {
		return waysToBuy;
	}

	public void setWaysToBuy(Integer waysToBuy) {
		this.waysToBuy = waysToBuy;
	}

	public Integer getIstripleGuarantee() {
		return istripleGuarantee;
	}

	public void setIstripleGuarantee(Integer istripleGuarantee) {
		this.istripleGuarantee = istripleGuarantee;
	}

	public Integer getWarMonths() {
		return warMonths;
	}

	public void setWarMonths(Integer warMonths) {
		this.warMonths = warMonths;
	}

	public Integer getRemindWay() {
		return remindWay;
	}

	public void setRemindWay(Integer remindWay) {
		this.remindWay = remindWay;
	}

	private Integer businessKind;// 营运性质

	private Date businessDate;// 营运证日期

	private String engineNoOld;// 旧发动机号

	private String salesAgentName;// 经销商

	private String consultant;// 销售顾问

	private Integer isSelfCompany;// 是否本公司购车

	private Date salesDate;// 销售日期
	
	private String salesMileage;//销售里程

	/**
	 * @return the salesMileage
	 */
	public String getSalesMileage() {
		return salesMileage;
	}

	/**
	 * @param salesMileage the salesMileage to set
	 */
	public void setSalesMileage(String salesMileage) {
		this.salesMileage = salesMileage;
	}

	private Double vehiclePrice;// 车辆价格

	private Date wrtBeginDate;// 保修起始日期

	private Date wrtEndDate;// 保修结束日期

	private Double wrtBeginMileage;// 保修起始里程

	private Double wrtEndMileage;// 保修结束里程

	private Date licenseDate;// 上牌日期

	private Double mileage;// 行驶里程

	private Double isChangeOdograph;// 是否换表

	private Date changeDate;// 上次换表日期

	private String addequipment;// 加装说明

	private Date firstInDate;// 第一次进厂日期

	private Date nextMaintainDate;// 预计下次保养日期

	private Double nextMaintainMileage;// 下次保养里程

	private Double dailyAverageMileage;// 日平均行驶里程

	private Date lastInspectDate;// 上次验车日期

	private Date nextInspectDate;// 下次验车日期

	private String deliverer;// 送修人

	private String delivererGender;// 送修人性别

	private String delivererPhone;// PHONE 送修人电话

	private String delivererMobile;// 送修人手机

	private Integer delivererHobbyContact;// 送修人喜欢联络方式

	private String delivererRelationToOwner;// 送修人与车主关系

	private String delivererCompany;// 送修人工作单位

	private String delivererCredit;// 送修人身份证

	private String delivererAddress;// ADDRESS 送修人地址

	private String zipCode;// 邮编

	private String chieftechnician;// 指定技师

	private String serviceAdvisor;// 服务专员

	private String insuranceAdvisor;// 续保专员

	private String maintainAdvisor;// 定保专员

	private Date LastMaintainDate;// 上次维修日期

	private Double LastMaintainMileage;// 上次维修里程

	private Date LastMaintenanceDate;// 上次保养日期

	private Double LastMaintenanceMileage;// 上次保养里程

	private Double prePay;// 预收款

	private Double ArrearageAmount;// 欠款金额

	private String prepareJob;// 待办事项

	private String contractNo;// 合约编号

	private String attentionInfo;// 注意事项

	private String vipNo;// VIP编号

	private Date contractDate;// 签约日期

	private Date discountexpireDate;// 优惠截止日期

	private String discountModeCode;// 优惠模式代码

	private String capitalAssertsManageNo;// 固定资产编号

	private String insurationCode;// 保险公司代码

	private String insuranceBillNo;// 保单号

	private String insuranceType;// 投保险种

	private Double insuranceAmount;// 投保金额

	private Date insuranceBuyDate;// 投保日期

	private Date insuranceBeginDate;// 保险开始日期

	private Date insuranceEndDate;// 保险结束日期

	private String insuranceIntroMan;// 保险介绍人

	private String keynumber;// 钥匙编号

	private String brandModel;// 厂牌型号

	private Double isSelfCompanyInsuance;// 是否本公司投保

	private Double istrace;// 是否三日电访

	private Date traceTime;// 三日电访时间

	private String adjustr;// 调整人

	private Date adjustrDate;// 调整日期

	private Date downTimeStamp;// 下发时序

	private Date foundDate;// 建档日期

	private String remark;// 备注

	private Date submitTime;// 上报日期

	private Integer submitStatus;// 上报状态

	private String exceptionCause;// 异常原因

	private Integer isUpload;// 是否上报

	private Integer isUploadGroup;// 是否上报集团

	private String rfid;// RFID

	private String memberNo;// 会员编号

	private Integer isValid;// 是否有效

	private String gearType;// 变速箱型式

	private Date offlineDate;// 下线日期

	private String yearModel;// 年款

	private Double newVehicleMileage;// 新车关怀日里程数

	private Date nerVehicleDat;// 新车关怀日期

	private String productCode;// 产品代码

	private String noValidReason;// 不激活原因

	private String productingArea;// 产地

	private Date roCreateDate;// 工单开单日期

	private Integer isDcrcAdvisor;// 是否DCRC专员

	private String dcrcAdvisor;// DCRC专员

	private String vsn;// 车辆配置代码

	private Integer dischargeStandrd;// 排放标准

	private String systemRemark;// 系统日志

	private Date systemLastMaintenceDate;// 修改前上次保养日期

	private Date systemUpdateDate;// 修改时间

	private String avchored;// 运营车挂靠

	private String unitAttachcode;// 挂靠单位代码

	private String createdby;// CREATE_BY

	private Date createdat;// CREATE_DATE

	private String updatedby;// UPDATE_BY

	private Date updatedat;// UPDATE_DATE

	private Integer ver;// VER

	private Integer district;// 区县

	private Integer city;// 城市

	private Integer province;// 省份

	private Double currentMIleage;// 当前行驶里程

	private Date currentMileageDate;// 当前里程日期

	private String warrantyNumber;// 索赔号

	private Integer waysToBuy;// 购买方式

	private String replaceIntentModel;// 置换意向车型

	private Date replaceDate;// 置换日期

	private String rebuyIntentModel;// 重购意向车型

	private Date rebuyDate;// 重购日期

	private Date ddcnUpdateDate;// DDC最新日期

	private Integer istripleGuarantee;// 是否三包

	private Double warmilage;// 三包里程

	private Integer warMonths;// 三包月数

	private Date verTime;// 最新时间戳

	private String invoiceNo;// 发票编号

	private Date warEndDate;// 三包结束日期

	private Date reportTime;// 上报时间

	private Double isbinding;//

	private Date bindingDate;

	private Double isEntity;

	private Double isOvertime;// 是否超时

	private Date wxunbundlingDate;

	private String paiqiQuantity;// 排气量_新

	private String oilType;// 燃油类型

	private Date reportBIDatetime;

	private String remindContent;// 提醒内容

	private String customerFeedback;// 客户反馈

	private Integer remindWay;// 提醒方式

	private String reminder;// 提醒人
	
	private String memoInfo;//车辆备注
	
	private Double remindStatus;// 提醒状态

	private Double remindFailReason;// 提醒失败原因
	
//	private String remindId;
	
	public String getMemoInfo() {
		return memoInfo;
	}

	public void setMemoInfo(String memoInfo) {
		this.memoInfo = memoInfo;
	}

	/**
	 * @return the remindId
	 */
//	public String getRemindId() {
//		return remindId;
//	}
//
//	/**
//	 * @param remindId the remindId to set
//	 */
//	public void setRemindId(String remindId) {
//		this.remindId = remindId;
//	}

	

	/**
	 * @return the vins
	 */
	public String getVins() {
		return vins;
	}

	/**
	 * @param vins
	 *            the vins to set
	 */
	public void setVins(String vins) {
		this.vins = vins;
	}

	/**
	 * @return the isbinding
	 */
	public Double getIsbinding() {
		return isbinding;
	}

	/**
	 * @param isbinding
	 *            the isbinding to set
	 */
	public void setIsbinding(Double isbinding) {
		this.isbinding = isbinding;
	}

	/**
	 * @return the bindingDate
	 */
	public Date getBindingDate() {
		return bindingDate;
	}

	/**
	 * @param bindingDate
	 *            the bindingDate to set
	 */
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}

	/**
	 * @return the isEntity
	 */
	public Double getIsEntity() {
		return isEntity;
	}

	/**
	 * @param isEntity
	 *            the isEntity to set
	 */
	public void setIsEntity(Double isEntity) {
		this.isEntity = isEntity;
	}

	/**
	 * @return the wxunbundlingDate
	 */
	public Date getWxunbundlingDate() {
		return wxunbundlingDate;
	}

	/**
	 * @param wxunbundlingDate
	 *            the wxunbundlingDate to set
	 */
	public void setWxunbundlingDate(Date wxunbundlingDate) {
		this.wxunbundlingDate = wxunbundlingDate;
	}

	/**
	 * @return the reportBIDatetime
	 */
	public Date getReportBIDatetime() {
		return reportBIDatetime;
	}

	/**
	 * @param reportBIDatetime
	 *            the reportBIDatetime to set
	 */
	public void setReportBIDatetime(Date reportBIDatetime) {
		this.reportBIDatetime = reportBIDatetime;
	}

	/**
	 * @return the remindContent
	 */
	public String getRemindContent() {
		return remindContent;
	}

	/**
	 * @param remindContent
	 *            the remindContent to set
	 */
	public void setRemindContent(String remindContent) {
		this.remindContent = remindContent;
	}

	/**
	 * @return the customerFeedback
	 */
	public String getCustomerFeedback() {
		return customerFeedback;
	}

	/**
	 * @param customerFeedback
	 *            the customerFeedback to set
	 */
	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}


	/**
	 * @return the reminder
	 */
	public String getReminder() {
		return reminder;
	}

	/**
	 * @param reminder
	 *            the reminder to set
	 */
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}

	/**
	 * @return the remindStatus
	 */
	public Double getRemindStatus() {
		return remindStatus;
	}

	/**
	 * @param remindStatus
	 *            the remindStatus to set
	 */
	public void setRemindStatus(Double remindStatus) {
		this.remindStatus = remindStatus;
	}

	/**
	 * @return the remindFailReason
	 */
	public Double getRemindFailReason() {
		return remindFailReason;
	}

	/**
	 * @param remindFailReason
	 *            the remindFailReason to set
	 */
	public void setRemindFailReason(Double remindFailReason) {
		this.remindFailReason = remindFailReason;
	}

	

	/**
	 * @return the vin
	 */
	public String getVin() {
		return vin;
	}

	/**
	 * @param vin
	 *            the vin to set
	 */
	public void setVin(String vin) {
		this.vin = vin;
	}

	/**
	 * @return the dealercode
	 */
	public String getDealercode() {
		return dealercode;
	}

	/**
	 * @param dealercode
	 *            the dealercode to set
	 */
	public void setDealercode(String dealercode) {
		this.dealercode = dealercode;
	}

	/**
	 * @return the ownerNo
	 */
	public String getOwnerNo() {
		return ownerNo;
	}

	/**
	 * @param ownerNo
	 *            the ownerNo to set
	 */
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	/**
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * @param customerNo
	 *            the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * @return the ownerNoOld
	 */
	public String getOwnerNoOld() {
		return ownerNoOld;
	}

	/**
	 * @param ownerNoOld
	 *            the ownerNoOld to set
	 */
	public void setOwnerNoOld(String ownerNoOld) {
		this.ownerNoOld = ownerNoOld;
	}

	/**
	 * @return the license
	 */
	public String getLicense() {
		return license;
	}

	/**
	 * @param license
	 *            the license to set
	 */
	public void setLicense(String license) {
		this.license = license;
	}

	/**
	 * @return the engineNo
	 */
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * @param engineNo
	 *            the engineNo to set
	 */
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	/**
	 * @return the gearBox
	 */
	public String getGearBox() {
		return gearBox;
	}

	/**
	 * @param gearBox
	 *            the gearBox to set
	 */
	public void setGearBox(String gearBox) {
		this.gearBox = gearBox;
	}

	/**
	 * @return the innerColor
	 */
	public String getInnerColor() {
		return innerColor;
	}

	/**
	 * @param innerColor
	 *            the innerColor to set
	 */
	public void setInnerColor(String innerColor) {
		this.innerColor = innerColor;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the series
	 */
	public String getSeries() {
		return series;
	}

	/**
	 * @param series
	 *            the series to set
	 */
	public void setSeries(String series) {
		this.series = series;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the apAckAge
	 */
	public String getApAckAge() {
		return apAckAge;
	}

	/**
	 * @param apAckAge
	 *            the apAckAge to set
	 */
	public void setApAckAge(String apAckAge) {
		this.apAckAge = apAckAge;
	}

	/**
	 * @return the modelYear
	 */
	public String getModelYear() {
		return modelYear;
	}

	/**
	 * @param modelYear
	 *            the modelYear to set
	 */
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	/**
	 * @return the exhaustQuantity
	 */
	public String getExhaustQuantity() {
		return exhaustQuantity;
	}

	/**
	 * @param exhaustQuantity
	 *            the exhaustQuantity to set
	 */
	public void setExhaustQuantity(String exhaustQuantity) {
		this.exhaustQuantity = exhaustQuantity;
	}

	/**
	 * @return the productDate
	 */
	public Date getProductDate() {
		return productDate;
	}

	/**
	 * @param productDate
	 *            the productDate to set
	 */
	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	/**
	 * @return the handbookNo
	 */
	public String getHandbookNo() {
		return handbookNo;
	}

	/**
	 * @param handbookNo
	 *            the handbookNo to set
	 */
	public void setHandbookNo(String handbookNo) {
		this.handbookNo = handbookNo;
	}

	/**
	 * @return the shifeType
	 */
	public String getShifeType() {
		return shifeType;
	}

	/**
	 * @param shifeType
	 *            the shifeType to set
	 */
	public void setShifeType(String shifeType) {
		this.shifeType = shifeType;
	}

	/**
	 * @return the fuelType
	 */
	public Integer getFuelType() {
		return fuelType;
	}

	/**
	 * @param fuelType
	 *            the fuelType to set
	 */
	public void setFuelType(Integer fuelType) {
		this.fuelType = fuelType;
	}


	/**
	 * @return the businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate
	 *            the businessDate to set
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}

	/**
	 * @return the engineNoOld
	 */
	public String getEngineNoOld() {
		return engineNoOld;
	}

	/**
	 * @param engineNoOld
	 *            the engineNoOld to set
	 */
	public void setEngineNoOld(String engineNoOld) {
		this.engineNoOld = engineNoOld;
	}

	/**
	 * @return the salesAgentName
	 */
	public String getSalesAgentName() {
		return salesAgentName;
	}

	/**
	 * @param salesAgentName
	 *            the salesAgentName to set
	 */
	public void setSalesAgentName(String salesAgentName) {
		this.salesAgentName = salesAgentName;
	}

	/**
	 * @return the consultant
	 */
	public String getConsultant() {
		return consultant;
	}

	/**
	 * @param consultant
	 *            the consultant to set
	 */
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}


	/**
	 * @return the salesDate
	 */
	public Date getSalesDate() {
		return salesDate;
	}

	/**
	 * @param salesDate
	 *            the salesDate to set
	 */
	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	/**
	 * @return the vehiclePrice
	 */
	public Double getVehiclePrice() {
		return vehiclePrice;
	}

	/**
	 * @param vehiclePrice
	 *            the vehiclePrice to set
	 */
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}

	/**
	 * @return the wrtBeginDate
	 */
	public Date getWrtBeginDate() {
		return wrtBeginDate;
	}

	/**
	 * @param wrtBeginDate
	 *            the wrtBeginDate to set
	 */
	public void setWrtBeginDate(Date wrtBeginDate) {
		this.wrtBeginDate = wrtBeginDate;
	}

	/**
	 * @return the wrtEndDate
	 */
	public Date getWrtEndDate() {
		return wrtEndDate;
	}

	/**
	 * @param wrtEndDate
	 *            the wrtEndDate to set
	 */
	public void setWrtEndDate(Date wrtEndDate) {
		this.wrtEndDate = wrtEndDate;
	}

	/**
	 * @return the wrtBeginMileage
	 */
	public Double getWrtBeginMileage() {
		return wrtBeginMileage;
	}

	/**
	 * @param wrtBeginMileage
	 *            the wrtBeginMileage to set
	 */
	public void setWrtBeginMileage(Double wrtBeginMileage) {
		this.wrtBeginMileage = wrtBeginMileage;
	}

	/**
	 * @return the wrtEndMileage
	 */
	public Double getWrtEndMileage() {
		return wrtEndMileage;
	}

	/**
	 * @param wrtEndMileage
	 *            the wrtEndMileage to set
	 */
	public void setWrtEndMileage(Double wrtEndMileage) {
		this.wrtEndMileage = wrtEndMileage;
	}

	/**
	 * @return the licenseDate
	 */
	public Date getLicenseDate() {
		return licenseDate;
	}

	/**
	 * @param licenseDate
	 *            the licenseDate to set
	 */
	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}

	/**
	 * @return the mileage
	 */
	public Double getMileage() {
		return mileage;
	}

	/**
	 * @param mileage
	 *            the mileage to set
	 */
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	/**
	 * @return the isChangeOdograph
	 */
	public Double getIsChangeOdograph() {
		return isChangeOdograph;
	}

	/**
	 * @param isChangeOdograph
	 *            the isChangeOdograph to set
	 */
	public void setIsChangeOdograph(Double isChangeOdograph) {
		this.isChangeOdograph = isChangeOdograph;
	}

	/**
	 * @return the changeDate
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * @param changeDate
	 *            the changeDate to set
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * @return the addequipment
	 */
	public String getAddequipment() {
		return addequipment;
	}

	/**
	 * @param addequipment
	 *            the addequipment to set
	 */
	public void setAddequipment(String addequipment) {
		this.addequipment = addequipment;
	}

	/**
	 * @return the firstInDate
	 */
	public Date getFirstInDate() {
		return firstInDate;
	}

	/**
	 * @param firstInDate
	 *            the firstInDate to set
	 */
	public void setFirstInDate(Date firstInDate) {
		this.firstInDate = firstInDate;
	}

	/**
	 * @return the nextMaintainDate
	 */
	public Date getNextMaintainDate() {
		return nextMaintainDate;
	}

	/**
	 * @param nextMaintainDate
	 *            the nextMaintainDate to set
	 */
	public void setNextMaintainDate(Date nextMaintainDate) {
		this.nextMaintainDate = nextMaintainDate;
	}

	/**
	 * @return the nextMaintainMileage
	 */
	public Double getNextMaintainMileage() {
		return nextMaintainMileage;
	}

	/**
	 * @param nextMaintainMileage
	 *            the nextMaintainMileage to set
	 */
	public void setNextMaintainMileage(Double nextMaintainMileage) {
		this.nextMaintainMileage = nextMaintainMileage;
	}

	/**
	 * @return the dailyAverageMileage
	 */
	public Double getDailyAverageMileage() {
		return dailyAverageMileage;
	}

	/**
	 * @param dailyAverageMileage
	 *            the dailyAverageMileage to set
	 */
	public void setDailyAverageMileage(Double dailyAverageMileage) {
		this.dailyAverageMileage = dailyAverageMileage;
	}

	/**
	 * @return the lastInspectDate
	 */
	public Date getLastInspectDate() {
		return lastInspectDate;
	}

	/**
	 * @param lastInspectDate
	 *            the lastInspectDate to set
	 */
	public void setLastInspectDate(Date lastInspectDate) {
		this.lastInspectDate = lastInspectDate;
	}

	/**
	 * @return the nextInspectDate
	 */
	public Date getNextInspectDate() {
		return nextInspectDate;
	}

	/**
	 * @param nextInspectDate
	 *            the nextInspectDate to set
	 */
	public void setNextInspectDate(Date nextInspectDate) {
		this.nextInspectDate = nextInspectDate;
	}

	/**
	 * @return the deliverer
	 */
	public String getDeliverer() {
		return deliverer;
	}

	/**
	 * @param deliverer
	 *            the deliverer to set
	 */
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}

	/**
	 * @return the delivererGender
	 */
	public String getDelivererGender() {
		return delivererGender;
	}

	/**
	 * @param delivererGender
	 *            the delivererGender to set
	 */
	public void setDelivererGender(String delivererGender) {
		this.delivererGender = delivererGender;
	}

	/**
	 * @return the delivererPhone
	 */
	public String getDelivererPhone() {
		return delivererPhone;
	}

	/**
	 * @param delivererPhone
	 *            the delivererPhone to set
	 */
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}

	/**
	 * @return the delivererMobile
	 */
	public String getDelivererMobile() {
		return delivererMobile;
	}

	/**
	 * @param delivererMobile
	 *            the delivererMobile to set
	 */
	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}


	/**
	 * @return the delivererRelationToOwner
	 */
	public String getDelivererRelationToOwner() {
		return delivererRelationToOwner;
	}

	/**
	 * @param delivererRelationToOwner
	 *            the delivererRelationToOwner to set
	 */
	public void setDelivererRelationToOwner(String delivererRelationToOwner) {
		this.delivererRelationToOwner = delivererRelationToOwner;
	}

	/**
	 * @return the delivererCompany
	 */
	public String getDelivererCompany() {
		return delivererCompany;
	}

	/**
	 * @param delivererCompany
	 *            the delivererCompany to set
	 */
	public void setDelivererCompany(String delivererCompany) {
		this.delivererCompany = delivererCompany;
	}

	/**
	 * @return the delivererCredit
	 */
	public String getDelivererCredit() {
		return delivererCredit;
	}

	/**
	 * @param delivererCredit
	 *            the delivererCredit to set
	 */
	public void setDelivererCredit(String delivererCredit) {
		this.delivererCredit = delivererCredit;
	}

	/**
	 * @return the delivererAddress
	 */
	public String getDelivererAddress() {
		return delivererAddress;
	}

	/**
	 * @param delivererAddress
	 *            the delivererAddress to set
	 */
	public void setDelivererAddress(String delivererAddress) {
		this.delivererAddress = delivererAddress;
	}

	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return the chieftechnician
	 */
	public String getChieftechnician() {
		return chieftechnician;
	}

	/**
	 * @param chieftechnician
	 *            the chieftechnician to set
	 */
	public void setChieftechnician(String chieftechnician) {
		this.chieftechnician = chieftechnician;
	}

	/**
	 * @return the serviceAdvisor
	 */
	public String getServiceAdvisor() {
		return serviceAdvisor;
	}

	/**
	 * @param serviceAdvisor
	 *            the serviceAdvisor to set
	 */
	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	/**
	 * @return the insuranceAdvisor
	 */
	public String getInsuranceAdvisor() {
		return insuranceAdvisor;
	}

	/**
	 * @param insuranceAdvisor
	 *            the insuranceAdvisor to set
	 */
	public void setInsuranceAdvisor(String insuranceAdvisor) {
		this.insuranceAdvisor = insuranceAdvisor;
	}

	/**
	 * @return the maintainAdvisor
	 */
	public String getMaintainAdvisor() {
		return maintainAdvisor;
	}

	/**
	 * @param maintainAdvisor
	 *            the maintainAdvisor to set
	 */
	public void setMaintainAdvisor(String maintainAdvisor) {
		this.maintainAdvisor = maintainAdvisor;
	}

	/**
	 * @return the lastMaintainDate
	 */
	public Date getLastMaintainDate() {
		return LastMaintainDate;
	}

	/**
	 * @param lastMaintainDate
	 *            the lastMaintainDate to set
	 */
	public void setLastMaintainDate(Date lastMaintainDate) {
		LastMaintainDate = lastMaintainDate;
	}

	/**
	 * @return the lastMaintainMileage
	 */
	public Double getLastMaintainMileage() {
		return LastMaintainMileage;
	}

	/**
	 * @param lastMaintainMileage
	 *            the lastMaintainMileage to set
	 */
	public void setLastMaintainMileage(Double lastMaintainMileage) {
		LastMaintainMileage = lastMaintainMileage;
	}

	/**
	 * @return the lastMaintenanceDate
	 */
	public Date getLastMaintenanceDate() {
		return LastMaintenanceDate;
	}

	/**
	 * @param lastMaintenanceDate
	 *            the lastMaintenanceDate to set
	 */
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		LastMaintenanceDate = lastMaintenanceDate;
	}

	/**
	 * @return the lastMaintenanceMileage
	 */
	public Double getLastMaintenanceMileage() {
		return LastMaintenanceMileage;
	}

	/**
	 * @param lastMaintenanceMileage
	 *            the lastMaintenanceMileage to set
	 */
	public void setLastMaintenanceMileage(Double lastMaintenanceMileage) {
		LastMaintenanceMileage = lastMaintenanceMileage;
	}

	/**
	 * @return the prePay
	 */
	public Double getPrePay() {
		return prePay;
	}

	/**
	 * @param prePay
	 *            the prePay to set
	 */
	public void setPrePay(Double prePay) {
		this.prePay = prePay;
	}

	/**
	 * @return the arrearageAmount
	 */
	public Double getArrearageAmount() {
		return ArrearageAmount;
	}

	/**
	 * @param arrearageAmount
	 *            the arrearageAmount to set
	 */
	public void setArrearageAmount(Double arrearageAmount) {
		ArrearageAmount = arrearageAmount;
	}

	/**
	 * @return the prepareJob
	 */
	public String getPrepareJob() {
		return prepareJob;
	}

	/**
	 * @param prepareJob
	 *            the prepareJob to set
	 */
	public void setPrepareJob(String prepareJob) {
		this.prepareJob = prepareJob;
	}

	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * @param contractNo
	 *            the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * @return the attentionInfo
	 */
	public String getAttentionInfo() {
		return attentionInfo;
	}

	/**
	 * @param attentionInfo
	 *            the attentionInfo to set
	 */
	public void setAttentionInfo(String attentionInfo) {
		this.attentionInfo = attentionInfo;
	}

	/**
	 * @return the vipNo
	 */
	public String getVipNo() {
		return vipNo;
	}

	/**
	 * @param vipNo
	 *            the vipNo to set
	 */
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}

	/**
	 * @return the contractDate
	 */
	public Date getContractDate() {
		return contractDate;
	}

	/**
	 * @param contractDate
	 *            the contractDate to set
	 */
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * @return the discountexpireDate
	 */
	public Date getDiscountexpireDate() {
		return discountexpireDate;
	}

	/**
	 * @param discountexpireDate
	 *            the discountexpireDate to set
	 */
	public void setDiscountexpireDate(Date discountexpireDate) {
		this.discountexpireDate = discountexpireDate;
	}

	/**
	 * @return the discountModeCode
	 */
	public String getDiscountModeCode() {
		return discountModeCode;
	}

	/**
	 * @param discountModeCode
	 *            the discountModeCode to set
	 */
	public void setDiscountModeCode(String discountModeCode) {
		this.discountModeCode = discountModeCode;
	}

	/**
	 * @return the capitalAssertsManageNo
	 */
	public String getCapitalAssertsManageNo() {
		return capitalAssertsManageNo;
	}

	/**
	 * @param capitalAssertsManageNo
	 *            the capitalAssertsManageNo to set
	 */
	public void setCapitalAssertsManageNo(String capitalAssertsManageNo) {
		this.capitalAssertsManageNo = capitalAssertsManageNo;
	}

	/**
	 * @return the insurationCode
	 */
	public String getInsurationCode() {
		return insurationCode;
	}

	/**
	 * @param insurationCode
	 *            the insurationCode to set
	 */
	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}

	/**
	 * @return the insuranceBillNo
	 */
	public String getInsuranceBillNo() {
		return insuranceBillNo;
	}

	/**
	 * @param insuranceBillNo
	 *            the insuranceBillNo to set
	 */
	public void setInsuranceBillNo(String insuranceBillNo) {
		this.insuranceBillNo = insuranceBillNo;
	}

	/**
	 * @return the insuranceType
	 */
	public String getInsuranceType() {
		return insuranceType;
	}

	/**
	 * @param insuranceType
	 *            the insuranceType to set
	 */
	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	/**
	 * @return the insuranceAmount
	 */
	public Double getInsuranceAmount() {
		return insuranceAmount;
	}

	/**
	 * @param insuranceAmount
	 *            the insuranceAmount to set
	 */
	public void setInsuranceAmount(Double insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	/**
	 * @return the insuranceBuyDate
	 */
	public Date getInsuranceBuyDate() {
		return insuranceBuyDate;
	}

	/**
	 * @param insuranceBuyDate
	 *            the insuranceBuyDate to set
	 */
	public void setInsuranceBuyDate(Date insuranceBuyDate) {
		this.insuranceBuyDate = insuranceBuyDate;
	}

	/**
	 * @return the insuranceBeginDate
	 */
	public Date getInsuranceBeginDate() {
		return insuranceBeginDate;
	}

	/**
	 * @param insuranceBeginDate
	 *            the insuranceBeginDate to set
	 */
	public void setInsuranceBeginDate(Date insuranceBeginDate) {
		this.insuranceBeginDate = insuranceBeginDate;
	}

	/**
	 * @return the insuranceEndDate
	 */
	public Date getInsuranceEndDate() {
		return insuranceEndDate;
	}

	/**
	 * @param insuranceEndDate
	 *            the insuranceEndDate to set
	 */
	public void setInsuranceEndDate(Date insuranceEndDate) {
		this.insuranceEndDate = insuranceEndDate;
	}

	/**
	 * @return the insuranceIntroMan
	 */
	public String getInsuranceIntroMan() {
		return insuranceIntroMan;
	}

	/**
	 * @param insuranceIntroMan
	 *            the insuranceIntroMan to set
	 */
	public void setInsuranceIntroMan(String insuranceIntroMan) {
		this.insuranceIntroMan = insuranceIntroMan;
	}

	/**
	 * @return the keynumber
	 */
	public String getKeynumber() {
		return keynumber;
	}

	/**
	 * @param keynumber
	 *            the keynumber to set
	 */
	public void setKeynumber(String keynumber) {
		this.keynumber = keynumber;
	}

	/**
	 * @return the brandModel
	 */
	public String getBrandModel() {
		return brandModel;
	}

	/**
	 * @param brandModel
	 *            the brandModel to set
	 */
	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	/**
	 * @return the isSelfCompanyInsuance
	 */
	public Double getIsSelfCompanyInsuance() {
		return isSelfCompanyInsuance;
	}

	/**
	 * @param isSelfCompanyInsuance
	 *            the isSelfCompanyInsuance to set
	 */
	public void setIsSelfCompanyInsuance(Double isSelfCompanyInsuance) {
		this.isSelfCompanyInsuance = isSelfCompanyInsuance;
	}

	/**
	 * @return the istrace
	 */
	public Double getIstrace() {
		return istrace;
	}

	/**
	 * @param istrace
	 *            the istrace to set
	 */
	public void setIstrace(Double istrace) {
		this.istrace = istrace;
	}

	/**
	 * @return the traceTime
	 */
	public Date getTraceTime() {
		return traceTime;
	}

	/**
	 * @param traceTime
	 *            the traceTime to set
	 */
	public void setTraceTime(Date traceTime) {
		this.traceTime = traceTime;
	}

	/**
	 * @return the adjustr
	 */
	public String getAdjustr() {
		return adjustr;
	}

	/**
	 * @param adjustr
	 *            the adjustr to set
	 */
	public void setAdjustr(String adjustr) {
		this.adjustr = adjustr;
	}

	/**
	 * @return the adjustrDate
	 */
	public Date getAdjustrDate() {
		return adjustrDate;
	}

	/**
	 * @param adjustrDate
	 *            the adjustrDate to set
	 */
	public void setAdjustrDate(Date adjustrDate) {
		this.adjustrDate = adjustrDate;
	}

	/**
	 * @return the downTimeStamp
	 */
	public Date getDownTimeStamp() {
		return downTimeStamp;
	}

	/**
	 * @param downTimeStamp
	 *            the downTimeStamp to set
	 */
	public void setDownTimeStamp(Date downTimeStamp) {
		this.downTimeStamp = downTimeStamp;
	}

	/**
	 * @return the foundDate
	 */
	public Date getFoundDate() {
		return foundDate;
	}

	/**
	 * @param foundDate
	 *            the foundDate to set
	 */
	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the submitTime
	 */
	public Date getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param submitTime
	 *            the submitTime to set
	 */
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}


	/**
	 * @return the exceptionCause
	 */
	public String getExceptionCause() {
		return exceptionCause;
	}

	/**
	 * @param exceptionCause
	 *            the exceptionCause to set
	 */
	public void setExceptionCause(String exceptionCause) {
		this.exceptionCause = exceptionCause;
	}


	/**
	 * @return the rfid
	 */
	public String getRfid() {
		return rfid;
	}

	/**
	 * @param rfid
	 *            the rfid to set
	 */
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	/**
	 * @return the memberNo
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * @param memberNo
	 *            the memberNo to set
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}


	/**
	 * @return the gearType
	 */
	public String getGearType() {
		return gearType;
	}

	/**
	 * @param gearType
	 *            the gearType to set
	 */
	public void setGearType(String gearType) {
		this.gearType = gearType;
	}

	/**
	 * @return the offlineDate
	 */
	public Date getOfflineDate() {
		return offlineDate;
	}

	/**
	 * @param offlineDate
	 *            the offlineDate to set
	 */
	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	/**
	 * @return the yearModel
	 */
	public String getYearModel() {
		return yearModel;
	}

	/**
	 * @param yearModel
	 *            the yearModel to set
	 */
	public void setYearModel(String yearModel) {
		this.yearModel = yearModel;
	}

	/**
	 * @return the newVehicleMileage
	 */
	public Double getNewVehicleMileage() {
		return newVehicleMileage;
	}

	/**
	 * @param newVehicleMileage
	 *            the newVehicleMileage to set
	 */
	public void setNewVehicleMileage(Double newVehicleMileage) {
		this.newVehicleMileage = newVehicleMileage;
	}

	/**
	 * @return the nerVehicleDat
	 */
	public Date getNerVehicleDat() {
		return nerVehicleDat;
	}

	/**
	 * @param nerVehicleDat
	 *            the nerVehicleDat to set
	 */
	public void setNerVehicleDat(Date nerVehicleDat) {
		this.nerVehicleDat = nerVehicleDat;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode
	 *            the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	/**
	 * @return the productingArea
	 */
	public String getProductingArea() {
		return productingArea;
	}

	/**
	 * @param productingArea
	 *            the productingArea to set
	 */
	public void setProductingArea(String productingArea) {
		this.productingArea = productingArea;
	}

	/**
	 * @return the roCreateDate
	 */
	public Date getRoCreateDate() {
		return roCreateDate;
	}

	/**
	 * @param roCreateDate
	 *            the roCreateDate to set
	 */
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}


	/**
	 * @return the dcrcAdvisor
	 */
	public String getDcrcAdvisor() {
		return dcrcAdvisor;
	}

	/**
	 * @param dcrcAdvisor
	 *            the dcrcAdvisor to set
	 */
	public void setDcrcAdvisor(String dcrcAdvisor) {
		this.dcrcAdvisor = dcrcAdvisor;
	}

	/**
	 * @return the vsn
	 */
	public String getVsn() {
		return vsn;
	}

	/**
	 * @param vsn
	 *            the vsn to set
	 */
	public void setVsn(String vsn) {
		this.vsn = vsn;
	}


	/**
	 * @return the systemRemark
	 */
	public String getSystemRemark() {
		return systemRemark;
	}

	/**
	 * @param systemRemark
	 *            the systemRemark to set
	 */
	public void setSystemRemark(String systemRemark) {
		this.systemRemark = systemRemark;
	}

	/**
	 * @return the systemLastMaintenceDate
	 */
	public Date getSystemLastMaintenceDate() {
		return systemLastMaintenceDate;
	}

	/**
	 * @param systemLastMaintenceDate
	 *            the systemLastMaintenceDate to set
	 */
	public void setSystemLastMaintenceDate(Date systemLastMaintenceDate) {
		this.systemLastMaintenceDate = systemLastMaintenceDate;
	}

	/**
	 * @return the systemUpdateDate
	 */
	public Date getSystemUpdateDate() {
		return systemUpdateDate;
	}

	/**
	 * @param systemUpdateDate
	 *            the systemUpdateDate to set
	 */
	public void setSystemUpdateDate(Date systemUpdateDate) {
		this.systemUpdateDate = systemUpdateDate;
	}

	/**
	 * @return the avchored
	 */
	public String getAvchored() {
		return avchored;
	}

	/**
	 * @param avchored
	 *            the avchored to set
	 */
	public void setAvchored(String avchored) {
		this.avchored = avchored;
	}

	/**
	 * @return the unitAttachcode
	 */
	public String getUnitAttachcode() {
		return unitAttachcode;
	}

	/**
	 * @param unitAttachcode
	 *            the unitAttachcode to set
	 */
	public void setUnitAttachcode(String unitAttachcode) {
		this.unitAttachcode = unitAttachcode;
	}

	/**
	 * @return the createdby
	 */
	public String getCreatedby() {
		return createdby;
	}

	/**
	 * @param createdby
	 *            the createdby to set
	 */
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	/**
	 * @return the createdat
	 */
	public Date getCreatedat() {
		return createdat;
	}

	/**
	 * @param createdat
	 *            the createdat to set
	 */
	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	/**
	 * @return the updatedby
	 */
	public String getUpdatedby() {
		return updatedby;
	}

	/**
	 * @param updatedby
	 *            the updatedby to set
	 */
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	/**
	 * @return the updatedat
	 */
	public Date getUpdatedat() {
		return updatedat;
	}

	/**
	 * @param updatedat
	 *            the updatedat to set
	 */
	public void setUpdatedat(Date updatedat) {
		this.updatedat = updatedat;
	}

	/**
	 * @return the currentMIleage
	 */
	public Double getCurrentMIleage() {
		return currentMIleage;
	}

	/**
	 * @param currentMIleage
	 *            the currentMIleage to set
	 */
	public void setCurrentMIleage(Double currentMIleage) {
		this.currentMIleage = currentMIleage;
	}

	/**
	 * @return the currentMileageDate
	 */
	public Date getCurrentMileageDate() {
		return currentMileageDate;
	}

	/**
	 * @param currentMileageDate
	 *            the currentMileageDate to set
	 */
	public void setCurrentMileageDate(Date currentMileageDate) {
		this.currentMileageDate = currentMileageDate;
	}

	/**
	 * @return the warrantyNumber
	 */
	public String getWarrantyNumber() {
		return warrantyNumber;
	}

	/**
	 * @param warrantyNumber
	 *            the warrantyNumber to set
	 */
	public void setWarrantyNumber(String warrantyNumber) {
		this.warrantyNumber = warrantyNumber;
	}


	/**
	 * @return the replaceIntentModel
	 */
	public String getReplaceIntentModel() {
		return replaceIntentModel;
	}

	/**
	 * @param replaceIntentModel
	 *            the replaceIntentModel to set
	 */
	public void setReplaceIntentModel(String replaceIntentModel) {
		this.replaceIntentModel = replaceIntentModel;
	}

	/**
	 * @return the replaceDate
	 */
	public Date getReplaceDate() {
		return replaceDate;
	}

	/**
	 * @param replaceDate
	 *            the replaceDate to set
	 */
	public void setReplaceDate(Date replaceDate) {
		this.replaceDate = replaceDate;
	}

	/**
	 * @return the rebuyIntentModel
	 */
	public String getRebuyIntentModel() {
		return rebuyIntentModel;
	}

	/**
	 * @param rebuyIntentModel
	 *            the rebuyIntentModel to set
	 */
	public void setRebuyIntentModel(String rebuyIntentModel) {
		this.rebuyIntentModel = rebuyIntentModel;
	}

	/**
	 * @return the rebuyDate
	 */
	public Date getRebuyDate() {
		return rebuyDate;
	}

	/**
	 * @param rebuyDate
	 *            the rebuyDate to set
	 */
	public void setRebuyDate(Date rebuyDate) {
		this.rebuyDate = rebuyDate;
	}

	/**
	 * @return the ddcnUpdateDate
	 */
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}

	/**
	 * @param ddcnUpdateDate
	 *            the ddcnUpdateDate to set
	 */
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}


	/**
	 * @return the warmilage
	 */
	public Double getWarmilage() {
		return warmilage;
	}

	/**
	 * @param warmilage
	 *            the warmilage to set
	 */
	public void setWarmilage(Double warmilage) {
		this.warmilage = warmilage;
	}


	/**
	 * @return the verTime
	 */
	public Date getVerTime() {
		return verTime;
	}

	/**
	 * @param verTime
	 *            the verTime to set
	 */
	public void setVerTime(Date verTime) {
		this.verTime = verTime;
	}

	/**
	 * @return the invoiceNo
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}

	/**
	 * @param invoiceNo
	 *            the invoiceNo to set
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	/**
	 * @return the warEndDate
	 */
	public Date getWarEndDate() {
		return warEndDate;
	}

	/**
	 * @param warEndDate
	 *            the warEndDate to set
	 */
	public void setWarEndDate(Date warEndDate) {
		this.warEndDate = warEndDate;
	}

	/**
	 * @return the reportTime
	 */
	public Date getReportTime() {
		return reportTime;
	}

	/**
	 * @param reportTime
	 *            the reportTime to set
	 */
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	/**
	 * @return the isOvertime
	 */
	public Double getIsOvertime() {
		return isOvertime;
	}

	/**
	 * @param isOvertime
	 *            the isOvertime to set
	 */
	public void setIsOvertime(Double isOvertime) {
		this.isOvertime = isOvertime;
	}

	/**
	 * @return the paiqiQuantity
	 */
	public String getPaiqiQuantity() {
		return paiqiQuantity;
	}

	/**
	 * @param paiqiQuantity
	 *            the paiqiQuantity to set
	 */
	public void setPaiqiQuantity(String paiqiQuantity) {
		this.paiqiQuantity = paiqiQuantity;
	}

	/**
	 * @return the oilType
	 */
	public String getOilType() {
		return oilType;
	}

	/**
	 * @param oilType
	 *            the oilType to set
	 */
	public void setOilType(String oilType) {
		this.oilType = oilType;
	}

}
