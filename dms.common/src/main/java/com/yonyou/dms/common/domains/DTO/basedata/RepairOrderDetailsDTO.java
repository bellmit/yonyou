/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;

/**
 * @author yangjie
 *
 */
public class RepairOrderDetailsDTO {

	private String roNo;// 工单号
	
	private String ver;
	
	private String completeTag;//是否竣工
	
	public String getCompleteTag() {
		return completeTag;
	}

	public void setCompleteTag(String completeTag) {
		this.completeTag = completeTag;
	}

	private String isUpdateEndTimeSupposed;//是否修改预交车时间

	public String getIsUpdateEndTimeSupposed() {
		return isUpdateEndTimeSupposed;
	}

	public void setIsUpdateEndTimeSupposed(String isUpdateEndTimeSupposed) {
		this.isUpdateEndTimeSupposed = isUpdateEndTimeSupposed;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	private String carTopType;// 车顶号

	private String sequenceNo;// 车顶号序列码
	
	private String customerDesc;//顾客描述
	
	private String isTripleGuarantee;//是否三包
	
	private String isTripleGuaranteeBef;//是否做过三包
	
	private String seriousSafetyStatus;//严重安全故障状态
	
	private String roStatus;//工单状态

	public String getRoStatus() {
		return roStatus;
	}

	public void setRoStatus(String roStatus) {
		this.roStatus = roStatus;
	}

	public String getIsTripleGuaranteeBef() {
		return isTripleGuaranteeBef;
	}

	public void setIsTripleGuaranteeBef(String isTripleGuaranteeBef) {
		this.isTripleGuaranteeBef = isTripleGuaranteeBef;
	}

	public String getIsTripleGuarantee() {
		return isTripleGuarantee;
	}

	public void setIsTripleGuarantee(String isTripleGuarantee) {
		this.isTripleGuarantee = isTripleGuarantee;
	}

	public String getCustomerDesc() {
		return customerDesc;
	}

	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	private String roType;// 工单类型

	private String priRoNo;// 原工单号

	private String repairType;// 维修类型

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date firstInDate;

	public Date getFirstInDate() {
		return firstInDate;
	}

	public void setFirstInDate(Date firstInDate) {
		this.firstInDate = firstInDate;
	}

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date createDate;// 开单日期

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date endTimeSupposed;// 预交车日期

	private String technician;// 指定技师

	private String insure;// 保险公司

	private String insuranceNO;// 出险单号

	private String insurationCode;// 理赔单号

	private String inReason;// 进厂原因

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date estimateBeginTime;// 预开工时间

	private String labourPrice;// 工时单价

	private String labourAmount;// 工时费

	private String repairPartAmount;// 维修材料费

	private String salePartAmount;// 销售材料费

	private String addItemAmount;// 附加项目费

	private String overItemAmount;// 辅料管理费

	private String totalAmount;// 维修金额

	private String estimateAmount;// 预估金额

	private String Remark2;

	private String traceTag;// 跟踪状态
	
	private String labourPositionCode;//工位代码
	
	public String getLabourPositionCode() {
		return labourPositionCode;
	}

	public void setLabourPositionCode(String labourPositionCode) {
		this.labourPositionCode = labourPositionCode;
	}

	public String getTraceTag() {
		return traceTag;
	}

	public void setTraceTag(String traceTag) {
		this.traceTag = traceTag;
	}

	public String getRemark2() {
		return Remark2;
	}

	public void setRemark2(String remark2) {
		Remark2 = remark2;
	}

	private String bookingOrderNo;// 预约单号

	private String serviceAdvisor;// 客户经理

	private String otherRepairType;// 其他维修类型

	public String getOtherRepairType() {
		return otherRepairType;
	}

	public void setOtherRepairType(String otherRepairType) {
		this.otherRepairType = otherRepairType;
	}

	private String license;// 车牌号

	private String ownerNo;// 车主编号

	private String inMileage;// 进厂行驶里程

	private String MemberNo;// 会员编号

	private String brand;// 品牌

	private String ownerProperty;// 车主性质

	private String outMileage;// 出厂行驶里程

	private String vin;// VIN

	private String ownerName;// 车主

	private String nextMaintainMileage;// 下次保养里程

	private String lastRepairMileage;// 上次维修里程

	public String getLastRepairMileage() {
		return lastRepairMileage;
	}

	public void setLastRepairMileage(String lastRepairMileage) {
		this.lastRepairMileage = lastRepairMileage;
	}

	private String engineNo;// 发动机号

	private String deliverer;// 送修人姓名

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date nextMaintainDate;// 预计下次保养日期

	private String series;// 车系

	private String delivererGender;// 送修人性别

	private String changeMileage;// 换表里程

	private String model;// 车型

	private String delivererPhone;// 送修人电话

	private String totalChangeMileage;// 累计换表里程

	private String config;// 配置

	private String delivererMobile;// 送修人手机

	private String changeDate;// 上次换表日期

	private String color;// 颜色

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date licenseDate;// 上牌日期

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date lastMaintainDate;// 上次维修日期

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date salesDate;// 开票日期

	private String reCommendEmp;// 推荐人

	private String reCommendCustomer;// 推荐单位

	private String oilRemain;// 进厂油料

	private String traceTime;// 三日电访时间请选择

	private String noTraceReason;// 不回访原因

	private String isUpdateOwner;// 是否更新车主信息

	private String isUpdateWarLevel;

	private String noWarning;

	private String noMileageWarning;

	private String noChargePartitionWarning;

	private String repairDays;

	private String isWar;

	private String warMileage;

	private String warMonth;

	public String getNoChargePartitionWarning() {
		return noChargePartitionWarning;
	}

	public void setNoChargePartitionWarning(String noChargePartitionWarning) {
		this.noChargePartitionWarning = noChargePartitionWarning;
	}

	public String getRepairDays() {
		return repairDays;
	}

	public void setRepairDays(String repairDays) {
		this.repairDays = repairDays;
	}

	public String getIsWar() {
		return isWar;
	}

	public void setIsWar(String isWar) {
		this.isWar = isWar;
	}

	public String getWarMileage() {
		return warMileage;
	}

	public void setWarMileage(String warMileage) {
		this.warMileage = warMileage;
	}

	public String getWarMonth() {
		return warMonth;
	}

	public void setWarMonth(String warMonth) {
		this.warMonth = warMonth;
	}

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date warEndDate;

	public Date getWarEndDate() {
		return warEndDate;
	}

	public void setWarEndDate(Date warEndDate) {
		this.warEndDate = warEndDate;
	}

	public String getNoMileageWarning() {
		return noMileageWarning;
	}

	public void setNoMileageWarning(String noMileageWarning) {
		this.noMileageWarning = noMileageWarning;
	}

	public String getNoWarning() {
		return noWarning;
	}

	public void setNoWarning(String noWarning) {
		this.noWarning = noWarning;
	}

	public String getIsUpdateWarLevel() {
		return isUpdateWarLevel;
	}

	public void setIsUpdateWarLevel(String isUpdateWarLevel) {
		this.isUpdateWarLevel = isUpdateWarLevel;
	}

	public String getIsUpdateOwner() {
		return isUpdateOwner;
	}

	public void setIsUpdateOwner(String isUpdateOwner) {
		this.isUpdateOwner = isUpdateOwner;
	}

	private String memCustomerDesc;// 送修问题

	private String productCode;// 产品代码

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date memOfMaturity;// 会员卡到期日

	private String troubleDesc;// 检查结果

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date troubleOccurDate;// 故障发生日
	
	private String schemeStatus;//方案状态
	
	private String modifyNum;//修改次数
	
	public String getModifyNum() {
		return modifyNum;
	}

	public void setModifyNum(String modifyNum) {
		this.modifyNum = modifyNum;
	}

	private String updateStatus;//U 修改;A 新增

	public String getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getSchemeStatus() {
		return schemeStatus;
	}

	public void setSchemeStatus(String schemeStatus) {
		this.schemeStatus = schemeStatus;
	}

	private String remark;// 备注

	private String remark1;// 备注1
	
	private String notIntegral;//不参与积分

	public String getNotIntegral() {
		return notIntegral;
	}

	public void setNotIntegral(String notIntegral) {
		this.notIntegral = notIntegral;
	}

	private List<Map<String, String>> dms_table;// 维修项目

	private List<Map<String, String>> dms_part;// 维修材料(维修配件)

	private List<Map<String, String>> dms_sales;// 销售材料

	private List<Map<String, String>> dms_subjoinItem;// 附加项目
	
	private String isCloseRo;//关单标志
	
	public String getIsCloseRo() {
		return isCloseRo;
	}

	public void setIsCloseRo(String isCloseRo) {
		this.isCloseRo = isCloseRo;
	}

	@SuppressWarnings("rawtypes")
	private List<Map> ttTripleInfo;// 预警信息

	@SuppressWarnings("rawtypes")
	public List<Map> getTtTripleInfo() {
		return ttTripleInfo;
	}

	public void setTtTripleInfo(@SuppressWarnings("rawtypes") List<Map> ttTripleInfo) {
		this.ttTripleInfo = ttTripleInfo;
	}

	private String checkIsActivity;// 是否参加活动

	private String isChangeOdograph;// 换表

	private String checkIsTrace;// 三日电访时间

	private String checkIsFactory;// 客户是否在厂

	private String checkIsQuality;// 是否质检
	
	private String roTroubleDesc;//维修故障描述

	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}

	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}

	private String checkIsTake;// 是否带走旧件
	
	private String isLargessMaintain;//是否赠送保养 

	public String getIsLargessMaintain() {
		return isLargessMaintain;
	}

	public void setIsLargessMaintain(String isLargessMaintain) {
		this.isLargessMaintain = isLargessMaintain;
	}

	private String checkIsGiveUp;// 是否放弃活动

	private String checkIsWash;// 是否洗车

	private String checkIsRoad;// 是否路试

	public String getCheckIsActivity() {
		return checkIsActivity;
	}

	public void setCheckIsActivity(String checkIsActivity) {
		this.checkIsActivity = checkIsActivity;
	}

	public String getIsChangeOdograph() {
		return isChangeOdograph;
	}

	public void setIsChangeOdograph(String isChangeOdograph) {
		this.isChangeOdograph = isChangeOdograph;
	}

	public String getCheckIsTrace() {
		return checkIsTrace;
	}

	public void setCheckIsTrace(String checkIsTrace) {
		this.checkIsTrace = checkIsTrace;
	}

	public String getCheckIsFactory() {
		return checkIsFactory;
	}

	public void setCheckIsFactory(String checkIsFactory) {
		this.checkIsFactory = checkIsFactory;
	}

	public String getCheckIsQuality() {
		return checkIsQuality;
	}

	public void setCheckIsQuality(String checkIsQuality) {
		this.checkIsQuality = checkIsQuality;
	}

	public String getCheckIsTake() {
		return checkIsTake;
	}

	public void setCheckIsTake(String checkIsTake) {
		this.checkIsTake = checkIsTake;
	}

	public String getCheckIsGiveUp() {
		return checkIsGiveUp;
	}

	public void setCheckIsGiveUp(String checkIsGiveUp) {
		this.checkIsGiveUp = checkIsGiveUp;
	}

	public String getCheckIsWash() {
		return checkIsWash;
	}

	public void setCheckIsWash(String checkIsWash) {
		this.checkIsWash = checkIsWash;
	}

	public String getCheckIsRoad() {
		return checkIsRoad;
	}

	public void setCheckIsRoad(String checkIsRoad) {
		this.checkIsRoad = checkIsRoad;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

	public String getCarTopType() {
		return carTopType;
	}

	public void setCarTopType(String carTopType) {
		this.carTopType = carTopType;
	}

	public String getRoType() {
		return roType;
	}

	public void setRoType(String roType) {
		this.roType = roType;
	}

	public String getPriRoNo() {
		return priRoNo;
	}

	public void setPriRoNo(String priRoNo) {
		this.priRoNo = priRoNo;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getSeriousSafetyStatus() {
		return seriousSafetyStatus;
	}

	public void setSeriousSafetyStatus(String seriousSafetyStatus) {
		this.seriousSafetyStatus = seriousSafetyStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndTimeSupposed() {
		return endTimeSupposed;
	}

	public void setEndTimeSupposed(Date endTimeSupposed) {
		this.endTimeSupposed = endTimeSupposed;
	}

	public String getTechnician() {
		return technician;
	}

	public void setTechnician(String technician) {
		this.technician = technician;
	}

	public String getInsure() {
		return insure;
	}

	public void setInsure(String insure) {
		this.insure = insure;
	}

	public String getInsuranceNO() {
		return insuranceNO;
	}

	public void setInsuranceNO(String insuranceNO) {
		this.insuranceNO = insuranceNO;
	}

	public String getInsurationCode() {
		return insurationCode;
	}

	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}

	public String getInReason() {
		return inReason;
	}

	public void setInReason(String inReason) {
		this.inReason = inReason;
	}

	public Date getEstimateBeginTime() {
		return estimateBeginTime;
	}

	public void setEstimateBeginTime(Date estimateBeginTime) {
		this.estimateBeginTime = estimateBeginTime;
	}

	public String getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(String labourPrice) {
		this.labourPrice = labourPrice;
	}

	public String getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(String labourAmount) {
		this.labourAmount = labourAmount;
	}

	public String getRepairPartAmount() {
		return repairPartAmount;
	}

	public void setRepairPartAmount(String repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}

	public String getSalePartAmount() {
		return salePartAmount;
	}

	public void setSalePartAmount(String salePartAmount) {
		this.salePartAmount = salePartAmount;
	}

	public String getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(String addItemAmount) {
		this.addItemAmount = addItemAmount;
	}

	public String getOverItemAmount() {
		return overItemAmount;
	}

	public void setOverItemAmount(String overItemAmount) {
		this.overItemAmount = overItemAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getEstimateAmount() {
		return estimateAmount;
	}

	public void setEstimateAmount(String estimateAmount) {
		this.estimateAmount = estimateAmount;
	}

	public String getBookingOrderNo() {
		return bookingOrderNo;
	}

	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}

	public String getServiceAdvisor() {
		return serviceAdvisor;
	}

	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getInMileage() {
		return inMileage;
	}

	public void setInMileage(String inMileage) {
		this.inMileage = inMileage;
	}

	public String getMemberNo() {
		return MemberNo;
	}

	public void setMemberNo(String memberNo) {
		MemberNo = memberNo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOwnerProperty() {
		return ownerProperty;
	}

	public void setOwnerProperty(String ownerProperty) {
		this.ownerProperty = ownerProperty;
	}

	public String getOutMileage() {
		return outMileage;
	}

	public void setOutMileage(String outMileage) {
		this.outMileage = outMileage;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getNextMaintainMileage() {
		return nextMaintainMileage;
	}

	public void setNextMaintainMileage(String nextMaintainMileage) {
		this.nextMaintainMileage = nextMaintainMileage;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getDeliverer() {
		return deliverer;
	}

	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}

	public Date getNextMaintainDate() {
		return nextMaintainDate;
	}

	public void setNextMaintainDate(Date nextMaintainDate) {
		this.nextMaintainDate = nextMaintainDate;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getDelivererGender() {
		return delivererGender;
	}

	public void setDelivererGender(String delivererGender) {
		this.delivererGender = delivererGender;
	}

	public String getChangeMileage() {
		return changeMileage;
	}

	public void setChangeMileage(String changeMileage) {
		this.changeMileage = changeMileage;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getDelivererPhone() {
		return delivererPhone;
	}

	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}

	public String getTotalChangeMileage() {
		return totalChangeMileage;
	}

	public void setTotalChangeMileage(String totalChangeMileage) {
		this.totalChangeMileage = totalChangeMileage;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getDelivererMobile() {
		return delivererMobile;
	}

	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getLicenseDate() {
		return licenseDate;
	}

	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}

	public Date getLastMaintainDate() {
		return lastMaintainDate;
	}

	public void setLastMaintainDate(Date lastMaintainDate) {
		this.lastMaintainDate = lastMaintainDate;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getReCommendEmp() {
		return reCommendEmp;
	}

	public void setReCommendEmp(String reCommendEmp) {
		this.reCommendEmp = reCommendEmp;
	}

	public String getReCommendCustomer() {
		return reCommendCustomer;
	}

	public void setReCommendCustomer(String reCommendCustomer) {
		this.reCommendCustomer = reCommendCustomer;
	}

	public String getOilRemain() {
		return oilRemain;
	}

	public void setOilRemain(String oilRemain) {
		this.oilRemain = oilRemain;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public String getNoTraceReason() {
		return noTraceReason;
	}

	public void setNoTraceReason(String noTraceReason) {
		this.noTraceReason = noTraceReason;
	}

	public String getMemCustomerDesc() {
		return memCustomerDesc;
	}

	public void setMemCustomerDesc(String memCustomerDesc) {
		this.memCustomerDesc = memCustomerDesc;
	}

	public Date getMemOfMaturity() {
		return memOfMaturity;
	}

	public void setMemOfMaturity(Date memOfMaturity) {
		this.memOfMaturity = memOfMaturity;
	}

	public String getTroubleDesc() {
		return troubleDesc;
	}

	public void setTroubleDesc(String troubleDesc) {
		this.troubleDesc = troubleDesc;
	}

	public Date getTroubleOccurDate() {
		return troubleOccurDate;
	}

	public void setTroubleOccurDate(Date troubleOccurDate) {
		this.troubleOccurDate = troubleOccurDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public List<Map<String, String>> getDms_table() {
		return dms_table;
	}

	public void setDms_table(List<Map<String, String>> dms_table) {
		this.dms_table = dms_table;
	}

	public List<Map<String, String>> getDms_part() {
		return dms_part;
	}

	public void setDms_part(List<Map<String, String>> dms_part) {
		this.dms_part = dms_part;
	}

	public List<Map<String, String>> getDms_sales() {
		return dms_sales;
	}

	public void setDms_sales(List<Map<String, String>> dms_sales) {
		this.dms_sales = dms_sales;
	}

	public List<Map<String, String>> getDms_subjoinItem() {
		return dms_subjoinItem;
	}

	public void setDms_subjoinItem(List<Map<String, String>> dms_subjoinItem) {
		this.dms_subjoinItem = dms_subjoinItem;
	}

}
