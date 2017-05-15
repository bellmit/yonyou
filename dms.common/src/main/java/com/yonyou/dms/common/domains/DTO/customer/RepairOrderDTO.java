package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class RepairOrderDTO {
	private String dealerCode;
	private String roNo;// 工单号
	private String salesPartNo;// 配件销售单号
	private String bookingOrderNo;// 预约单号
	private String estimateNo;// 估价单号
	private String soNo;// 销售订单编号
	private Integer roType;// 工单类型
	private String repairTypeCode;// 维修类型代码
	private String otherRepairType;// 其他维修类型
	private Integer vehicleTopDesc;// 车顶号
	private String sequenceNo;// 车顶号序列码
	private String primaryRoNo;// 原工单号
	private String insurationNo;// 理赔单号
	private String insurationCode;// 保险公司代码
	private Integer isCustomerInAsc;// 客户是否在厂
	private Integer isSeasonCheck;// 是否质检
	private Integer oilRemain;// 进厂油料
	private Integer isWash;// 是否洗车
	private Integer isTrace;// 是否三日电访
	private Integer traceTime;// 三日电访时间
	private String noTraceReason;// 不回访原因
	private Integer needRoadest;// 是否路试
	private String recommendEmpName;// 推荐单位2
	private String recommendCustomerName;// 推荐人
	private Integer serviceAdvisor;// 服务专员
	private String serviceAdvisorAss;// 开单人员
	private Integer roStatus;// 工单状态
	private Date roCreateDate;// 工单开单日期
	private Date endTimeSupposed;// 预交车时间
	private String chiefTechnician;// 指定技师
	private String ownerNo;// 车主编号
	private String ownerName;// 车主
	private Integer ownerProperty;// 车主性质
	private String license;// 车牌号
	private String vin;// VIN
	private String engineNo;// 发动机号
	private String brand;// 品牌
	private String series;// 车系
	private String model;// 车型
	private Integer inMileage;// 进厂行驶里程
	private Integer outMileage;// 出厂行驶里程
	private Integer isChangeOdograph;// 是否换表
	private Double changeMileage;// 换表里程
	private Double totalChangeMileage;// 累计换表里程
	private Double totalMileage;// 行驶总里程
	private String deliverer;// 送修人
	private Integer delivererGender;// 送修人性别
	private String delivererPhone;// 送修人电话
	private String delivererMobile;// 送修人手机
	private String finishUser;// 完工验收人
	private Integer completeTag;// 是否竣工
	private Integer waitInfoTag;// 待信标志
	private Integer waitPartTag;// 待料标志
	private Date COMPLETE_TIME;// 竣工时间
	private Date forBalanceTime;// 提交结算时间
	private Integer deliveryTag;// 交车标识
	private Date deliveryDate;// 交车日期
	private Integer deliveryUser;// 交车人
	private Double labourPrice;// 工时单价
	private Double labourAmount;// 工时费
	private Double repairPartAmount;// 维修材料费
	private Double salesPartAmount;// 销售材料费
	private Double addItemAmount;// 附加项目费
	private Double overItemAmount;// 辅料管理费
	private Double repairAmount;// 维修金额
	private Double estimateAmount;// 预估金额
	private Double balanceAmount;// 结算金额
	private Double receiveAmount;// 收款金额
	private Double subObbAmount;// 去零金额
	private Double derateAmount;// 减免金额
	private Double firstEstimateAmount;// 首次预估金额
	private Integer traceTag;// 跟踪标识
	private String remark;// 备注
	private String remark1;// 备注1
	private String remark2;// 备注2
	private String testDriver;// 试车员
	private Date printRoTime;// 工单打印时间
	private Integer roChargeType;// 工单付费类型
	private Date printRpTime;// 预先捡料单打印时间
	private Date estimateBeginTime;// 预计开工时间
	private Integer isActivity;// 是否参加活动
	private Integer isCloseRo;// 是否仓库关单
	private Integer isMaintain;// 是否含保养
	private String customerDesc;// 顾客描述
	private Integer roSplitStatus;// 工单拆分状态
	private String memberNo;// 会员编号
	private Integer inReason;// 进厂原因
	private Integer modifyNum;// 修改次数
	private Integer quoteEndAccurate;// 最终报价准确
	private Integer customerPreCheck;// 陪客预检
	private Integer checkedEnd;// 终检完成
	private Integer explainedBalanceAccounts;// 解释结算单
	private Integer notEnterWorkshop;// 工单不进车间
	private Integer isTakePartOld;// 是否带走旧件
	private String lockUser;// 锁定人
	private Integer dKey;//
	private Integer ver;// ver
	private String roTroubleDesc;// 维修故障描述
	private Integer activityTraceTag;// 服务活动跟踪标识
	private Integer isLargessMaintain;// 是否赠送保养
	private Integer isUpdateEndTimeSupposed;// 是否修改预交车时间
	private Integer isSystemQuote;// 是否系统引用
	private Integer notIntegral;// 不参与积分
	private Integer payment;// 付款
	private String occurInsuranceNo;// 出险单号
	private Double dsFactBalance;// 定损与实际赔付差额
	private Double dsAmount;// 定损金额
	private Date caseClosedDate;// 收案日期
	private Integer oldpartTreatMode;// 旧件处理方式
	private Date oldpartTreatDate;// 旧件处理时间
	private String oldpartRemark;// 旧件备注
	private Integer isOldPart;// 是否旧件
	private String settlementRemark;// 理赔备注
	private Date settleColDate;// 赔付收款日期
	private Integer settlementStatus;// 赔付状态
	private Double settlementAmount;// 赔付金额
	private String deliverBillMan;// 送单人
	private Date deliverBillDate;// 送单理赔日期
	private Integer insuranceOver;// 出险完成
	private Date printSendTime;// 发料单首次打印时间
	private String receptionNo;// 接车单号
	private String labourPositionCode;// 工位代码
	private Double memActiTotalAmount;// 会员活动总金额
	private Date troubleOccurDate;// 故障发生日期
	private Integer roClaimStatus;// 索赔单提报状态
	private Integer isPurchaseMaintenance;// 是否购买维护
	private String configCode;// 配置代码
	private Integer isQs;// 是否QS工单
	private Integer isTripleGuarantee;// 是否三包
	private String eworkshopRemark;// 车间反馈结果
	private Date ddcnUpdateDate;// DDC最新日期
	private Integer isTripleGuaranteeBef;// 是否做过三包
	private Integer isPrint;// 结算是否打印
	private Integer workshopStatus;// 车间状态
	private Date printCarTime;// 打印交车服务单时间
	private Date lastMaintenanceDate;// 上次保养日期
	private Double lastMaintenanceMileage;// 上次保养里程
	private Integer isAbandonActivity;// 是否放弃活动
	private Integer schemeStatus;// SCHEME_STATUS
	private Integer seriousSafetyStatus;// 严重安全故障状态
	private Integer isTimeoutSubmit;// 是否超时上报
	private Integer inBindingStatus;// 进站绑定情况
	private Integer outBindingStatus;// 出站绑定情况
	private Date reportBIDatetime;//
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public String getSalesPartNo() {
		return salesPartNo;
	}
	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}
	public String getBookingOrderNo() {
		return bookingOrderNo;
	}
	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public Integer getRoType() {
		return roType;
	}
	public void setRoType(Integer roType) {
		this.roType = roType;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public String getOtherRepairType() {
		return otherRepairType;
	}
	public void setOtherRepairType(String otherRepairType) {
		this.otherRepairType = otherRepairType;
	}
	public Integer getVehicleTopDesc() {
		return vehicleTopDesc;
	}
	public void setVehicleTopDesc(Integer vehicleTopDesc) {
		this.vehicleTopDesc = vehicleTopDesc;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getPrimaryRoNo() {
		return primaryRoNo;
	}
	public void setPrimaryRoNo(String primaryRoNo) {
		this.primaryRoNo = primaryRoNo;
	}
	public String getInsurationNo() {
		return insurationNo;
	}
	public void setInsurationNo(String insurationNo) {
		this.insurationNo = insurationNo;
	}
	public String getInsurationCode() {
		return insurationCode;
	}
	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}
	public Integer getIsCustomerInAsc() {
		return isCustomerInAsc;
	}
	public void setIsCustomerInAsc(Integer isCustomerInAsc) {
		this.isCustomerInAsc = isCustomerInAsc;
	}
	public Integer getIsSeasonCheck() {
		return isSeasonCheck;
	}
	public void setIsSeasonCheck(Integer isSeasonCheck) {
		this.isSeasonCheck = isSeasonCheck;
	}
	public Integer getOilRemain() {
		return oilRemain;
	}
	public void setOilRemain(Integer oilRemain) {
		this.oilRemain = oilRemain;
	}
	public Integer getIsWash() {
		return isWash;
	}
	public void setIsWash(Integer isWash) {
		this.isWash = isWash;
	}
	public Integer getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getTraceTime() {
		return traceTime;
	}
	public void setTraceTime(Integer traceTime) {
		this.traceTime = traceTime;
	}
	public String getNoTraceReason() {
		return noTraceReason;
	}
	public void setNoTraceReason(String noTraceReason) {
		this.noTraceReason = noTraceReason;
	}
	public Integer getNeedRoadest() {
		return needRoadest;
	}
	public void setNeedRoadest(Integer needRoadest) {
		this.needRoadest = needRoadest;
	}
	public String getRecommendEmpName() {
		return recommendEmpName;
	}
	public void setRecommendEmpName(String recommendEmpName) {
		this.recommendEmpName = recommendEmpName;
	}
	public String getRecommendCustomerName() {
		return recommendCustomerName;
	}
	public void setRecommendCustomerName(String recommendCustomerName) {
		this.recommendCustomerName = recommendCustomerName;
	}
	public Integer getServiceAdvisor() {
		return serviceAdvisor;
	}
	public void setServiceAdvisor(Integer serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}
	public String getServiceAdvisorAss() {
		return serviceAdvisorAss;
	}
	public void setServiceAdvisorAss(String serviceAdvisorAss) {
		this.serviceAdvisorAss = serviceAdvisorAss;
	}
	public Integer getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}
	public Date getRoCreateDate() {
		return roCreateDate;
	}
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}
	public Date getEndTimeSupposed() {
		return endTimeSupposed;
	}
	public void setEndTimeSupposed(Date endTimeSupposed) {
		this.endTimeSupposed = endTimeSupposed;
	}
	public String getChiefTechnician() {
		return chiefTechnician;
	}
	public void setChiefTechnician(String chiefTechnician) {
		this.chiefTechnician = chiefTechnician;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getOwnerProperty() {
		return ownerProperty;
	}
	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Integer getInMileage() {
		return inMileage;
	}
	public void setInMileage(Integer inMileage) {
		this.inMileage = inMileage;
	}
	public Integer getOutMileage() {
		return outMileage;
	}
	public void setOutMileage(Integer outMileage) {
		this.outMileage = outMileage;
	}
	public Integer getIsChangeOdograph() {
		return isChangeOdograph;
	}
	public void setIsChangeOdograph(Integer isChangeOdograph) {
		this.isChangeOdograph = isChangeOdograph;
	}
	public Double getChangeMileage() {
		return changeMileage;
	}
	public void setChangeMileage(Double changeMileage) {
		this.changeMileage = changeMileage;
	}
	public Double getTotalChangeMileage() {
		return totalChangeMileage;
	}
	public void setTotalChangeMileage(Double totalChangeMileage) {
		this.totalChangeMileage = totalChangeMileage;
	}
	public Double getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}
	public Date getCOMPLETE_TIME() {
		return COMPLETE_TIME;
	}
	public void setCOMPLETE_TIME(Date cOMPLETE_TIME) {
		COMPLETE_TIME = cOMPLETE_TIME;
	}
	public Date getForBalanceTime() {
		return forBalanceTime;
	}
	public void setForBalanceTime(Date forBalanceTime) {
		this.forBalanceTime = forBalanceTime;
	}
	public Integer getDeliveryTag() {
		return deliveryTag;
	}
	public void setDeliveryTag(Integer deliveryTag) {
		this.deliveryTag = deliveryTag;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public Integer getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(Integer deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public Double getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(Double labourPrice) {
		this.labourPrice = labourPrice;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public Double getRepairPartAmount() {
		return repairPartAmount;
	}
	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}
	public Double getSalesPartAmount() {
		return salesPartAmount;
	}
	public void setSalesPartAmount(Double salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}
	public Double getAddItemAmount() {
		return addItemAmount;
	}
	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}
	public Double getOverItemAmount() {
		return overItemAmount;
	}
	public void setOverItemAmount(Double overItemAmount) {
		this.overItemAmount = overItemAmount;
	}
	public Double getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(Double repairAmount) {
		this.repairAmount = repairAmount;
	}
	public Double getEstimateAmount() {
		return estimateAmount;
	}
	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Double getSubObbAmount() {
		return subObbAmount;
	}
	public void setSubObbAmount(Double subObbAmount) {
		this.subObbAmount = subObbAmount;
	}
	public Double getDerateAmount() {
		return derateAmount;
	}
	public void setDerateAmount(Double derateAmount) {
		this.derateAmount = derateAmount;
	}
	public Double getFirstEstimateAmount() {
		return firstEstimateAmount;
	}
	public void setFirstEstimateAmount(Double firstEstimateAmount) {
		this.firstEstimateAmount = firstEstimateAmount;
	}
	public Integer getTraceTag() {
		return traceTag;
	}
	public void setTraceTag(Integer traceTag) {
		this.traceTag = traceTag;
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
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getTestDriver() {
		return testDriver;
	}
	public void setTestDriver(String testDriver) {
		this.testDriver = testDriver;
	}
	public Date getPrintRoTime() {
		return printRoTime;
	}
	public void setPrintRoTime(Date printRoTime) {
		this.printRoTime = printRoTime;
	}
	public Integer getRoChargeType() {
		return roChargeType;
	}
	public void setRoChargeType(Integer roChargeType) {
		this.roChargeType = roChargeType;
	}
	public Date getPrintRpTime() {
		return printRpTime;
	}
	public void setPrintRpTime(Date printRpTime) {
		this.printRpTime = printRpTime;
	}
	public Date getEstimateBeginTime() {
		return estimateBeginTime;
	}
	public void setEstimateBeginTime(Date estimateBeginTime) {
		this.estimateBeginTime = estimateBeginTime;
	}
	public Integer getIsActivity() {
		return isActivity;
	}
	public void setIsActivity(Integer isActivity) {
		this.isActivity = isActivity;
	}
	public Integer getIsCloseRo() {
		return isCloseRo;
	}
	public void setIsCloseRo(Integer isCloseRo) {
		this.isCloseRo = isCloseRo;
	}
	public Integer getIsMaintain() {
		return isMaintain;
	}
	public void setIsMaintain(Integer isMaintain) {
		this.isMaintain = isMaintain;
	}
	public String getCustomerDesc() {
		return customerDesc;
	}
	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}
	public Integer getRoSplitStatus() {
		return roSplitStatus;
	}
	public void setRoSplitStatus(Integer roSplitStatus) {
		this.roSplitStatus = roSplitStatus;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getInReason() {
		return inReason;
	}
	public void setInReason(Integer inReason) {
		this.inReason = inReason;
	}
	public Integer getModifyNum() {
		return modifyNum;
	}
	public void setModifyNum(Integer modifyNum) {
		this.modifyNum = modifyNum;
	}
	public Integer getQuoteEndAccurate() {
		return quoteEndAccurate;
	}
	public void setQuoteEndAccurate(Integer quoteEndAccurate) {
		this.quoteEndAccurate = quoteEndAccurate;
	}
	public Integer getCustomerPreCheck() {
		return customerPreCheck;
	}
	public void setCustomerPreCheck(Integer customerPreCheck) {
		this.customerPreCheck = customerPreCheck;
	}
	public Integer getCheckedEnd() {
		return checkedEnd;
	}
	public void setCheckedEnd(Integer checkedEnd) {
		this.checkedEnd = checkedEnd;
	}
	public Integer getExplainedBalanceAccounts() {
		return explainedBalanceAccounts;
	}
	public void setExplainedBalanceAccounts(Integer explainedBalanceAccounts) {
		this.explainedBalanceAccounts = explainedBalanceAccounts;
	}
	public Integer getNotEnterWorkshop() {
		return notEnterWorkshop;
	}
	public void setNotEnterWorkshop(Integer notEnterWorkshop) {
		this.notEnterWorkshop = notEnterWorkshop;
	}
	public Integer getIsTakePartOld() {
		return isTakePartOld;
	}
	public void setIsTakePartOld(Integer isTakePartOld) {
		this.isTakePartOld = isTakePartOld;
	}
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public Integer getdKey() {
		return dKey;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}
	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}
	public Integer getActivityTraceTag() {
		return activityTraceTag;
	}
	public void setActivityTraceTag(Integer activityTraceTag) {
		this.activityTraceTag = activityTraceTag;
	}
	public Integer getIsLargessMaintain() {
		return isLargessMaintain;
	}
	public void setIsLargessMaintain(Integer isLargessMaintain) {
		this.isLargessMaintain = isLargessMaintain;
	}
	public Integer getIsUpdateEndTimeSupposed() {
		return isUpdateEndTimeSupposed;
	}
	public void setIsUpdateEndTimeSupposed(Integer isUpdateEndTimeSupposed) {
		this.isUpdateEndTimeSupposed = isUpdateEndTimeSupposed;
	}
	public Integer getIsSystemQuote() {
		return isSystemQuote;
	}
	public void setIsSystemQuote(Integer isSystemQuote) {
		this.isSystemQuote = isSystemQuote;
	}
	public Integer getNotIntegral() {
		return notIntegral;
	}
	public void setNotIntegral(Integer notIntegral) {
		this.notIntegral = notIntegral;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public String getOccurInsuranceNo() {
		return occurInsuranceNo;
	}
	public void setOccurInsuranceNo(String occurInsuranceNo) {
		this.occurInsuranceNo = occurInsuranceNo;
	}
	public Double getDsFactBalance() {
		return dsFactBalance;
	}
	public void setDsFactBalance(Double dsFactBalance) {
		this.dsFactBalance = dsFactBalance;
	}
	public Double getDsAmount() {
		return dsAmount;
	}
	public void setDsAmount(Double dsAmount) {
		this.dsAmount = dsAmount;
	}
	public Date getCaseClosedDate() {
		return caseClosedDate;
	}
	public void setCaseClosedDate(Date caseClosedDate) {
		this.caseClosedDate = caseClosedDate;
	}
	public Integer getOldpartTreatMode() {
		return oldpartTreatMode;
	}
	public void setOldpartTreatMode(Integer oldpartTreatMode) {
		this.oldpartTreatMode = oldpartTreatMode;
	}
	public Date getOldpartTreatDate() {
		return oldpartTreatDate;
	}
	public void setOldpartTreatDate(Date oldpartTreatDate) {
		this.oldpartTreatDate = oldpartTreatDate;
	}
	public String getOldpartRemark() {
		return oldpartRemark;
	}
	public void setOldpartRemark(String oldpartRemark) {
		this.oldpartRemark = oldpartRemark;
	}
	public Integer getIsOldPart() {
		return isOldPart;
	}
	public void setIsOldPart(Integer isOldPart) {
		this.isOldPart = isOldPart;
	}
	public String getSettlementRemark() {
		return settlementRemark;
	}
	public void setSettlementRemark(String settlementRemark) {
		this.settlementRemark = settlementRemark;
	}
	public Date getSettleColDate() {
		return settleColDate;
	}
	public void setSettleColDate(Date settleColDate) {
		this.settleColDate = settleColDate;
	}
	public Integer getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public Double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getDeliverBillMan() {
		return deliverBillMan;
	}
	public void setDeliverBillMan(String deliverBillMan) {
		this.deliverBillMan = deliverBillMan;
	}
	public Date getDeliverBillDate() {
		return deliverBillDate;
	}
	public void setDeliverBillDate(Date deliverBillDate) {
		this.deliverBillDate = deliverBillDate;
	}
	public Integer getInsuranceOver() {
		return insuranceOver;
	}
	public void setInsuranceOver(Integer insuranceOver) {
		this.insuranceOver = insuranceOver;
	}
	public Date getPrintSendTime() {
		return printSendTime;
	}
	public void setPrintSendTime(Date printSendTime) {
		this.printSendTime = printSendTime;
	}
	public String getReceptionNo() {
		return receptionNo;
	}
	public void setReceptionNo(String receptionNo) {
		this.receptionNo = receptionNo;
	}
	public String getLabourPositionCode() {
		return labourPositionCode;
	}
	public void setLabourPositionCode(String labourPositionCode) {
		this.labourPositionCode = labourPositionCode;
	}
	public Double getMemActiTotalAmount() {
		return memActiTotalAmount;
	}
	public void setMemActiTotalAmount(Double memActiTotalAmount) {
		this.memActiTotalAmount = memActiTotalAmount;
	}
	public Date getTroubleOccurDate() {
		return troubleOccurDate;
	}
	public void setTroubleOccurDate(Date troubleOccurDate) {
		this.troubleOccurDate = troubleOccurDate;
	}
	public Integer getRoClaimStatus() {
		return roClaimStatus;
	}
	public void setRoClaimStatus(Integer roClaimStatus) {
		this.roClaimStatus = roClaimStatus;
	}
	public Integer getIsPurchaseMaintenance() {
		return isPurchaseMaintenance;
	}
	public void setIsPurchaseMaintenance(Integer isPurchaseMaintenance) {
		this.isPurchaseMaintenance = isPurchaseMaintenance;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public Integer getIsQs() {
		return isQs;
	}
	public void setIsQs(Integer isQs) {
		this.isQs = isQs;
	}
	public Integer getIsTripleGuarantee() {
		return isTripleGuarantee;
	}
	public void setIsTripleGuarantee(Integer isTripleGuarantee) {
		this.isTripleGuarantee = isTripleGuarantee;
	}
	public String getEworkshopRemark() {
		return eworkshopRemark;
	}
	public void setEworkshopRemark(String eworkshopRemark) {
		this.eworkshopRemark = eworkshopRemark;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public Integer getIsTripleGuaranteeBef() {
		return isTripleGuaranteeBef;
	}
	public void setIsTripleGuaranteeBef(Integer isTripleGuaranteeBef) {
		this.isTripleGuaranteeBef = isTripleGuaranteeBef;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getWorkshopStatus() {
		return workshopStatus;
	}
	public void setWorkshopStatus(Integer workshopStatus) {
		this.workshopStatus = workshopStatus;
	}
	public Date getPrintCarTime() {
		return printCarTime;
	}
	public void setPrintCarTime(Date printCarTime) {
		this.printCarTime = printCarTime;
	}
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	public Double getLastMaintenanceMileage() {
		return lastMaintenanceMileage;
	}
	public void setLastMaintenanceMileage(Double lastMaintenanceMileage) {
		this.lastMaintenanceMileage = lastMaintenanceMileage;
	}
	public Integer getIsAbandonActivity() {
		return isAbandonActivity;
	}
	public void setIsAbandonActivity(Integer isAbandonActivity) {
		this.isAbandonActivity = isAbandonActivity;
	}
	public Integer getSchemeStatus() {
		return schemeStatus;
	}
	public void setSchemeStatus(Integer schemeStatus) {
		this.schemeStatus = schemeStatus;
	}
	public Integer getSeriousSafetyStatus() {
		return seriousSafetyStatus;
	}
	public void setSeriousSafetyStatus(Integer seriousSafetyStatus) {
		this.seriousSafetyStatus = seriousSafetyStatus;
	}
	public Integer getIsTimeoutSubmit() {
		return isTimeoutSubmit;
	}
	public void setIsTimeoutSubmit(Integer isTimeoutSubmit) {
		this.isTimeoutSubmit = isTimeoutSubmit;
	}
	public Integer getInBindingStatus() {
		return inBindingStatus;
	}
	public void setInBindingStatus(Integer inBindingStatus) {
		this.inBindingStatus = inBindingStatus;
	}
	public Integer getOutBindingStatus() {
		return outBindingStatus;
	}
	public void setOutBindingStatus(Integer outBindingStatus) {
		this.outBindingStatus = outBindingStatus;
	}
	public Date getReportBIDatetime() {
		return reportBIDatetime;
	}
	public void setReportBIDatetime(Date reportBIDatetime) {
		this.reportBIDatetime = reportBIDatetime;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public Integer getDelivererGender() {
		return delivererGender;
	}
	public void setDelivererGender(Integer delivererGender) {
		this.delivererGender = delivererGender;
	}
	public String getDelivererPhone() {
		return delivererPhone;
	}
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}
	public String getDelivererMobile() {
		return delivererMobile;
	}
	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}
	public String getFinishUser() {
		return finishUser;
	}
	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}
	public Integer getCompleteTag() {
		return completeTag;
	}
	public void setCompleteTag(Integer completeTag) {
		this.completeTag = completeTag;
	}
	public Integer getWaitInfoTag() {
		return waitInfoTag;
	}
	public void setWaitInfoTag(Integer waitInfoTag) {
		this.waitInfoTag = waitInfoTag;
	}
	public Integer getWaitPartTag() {
		return waitPartTag;
	}
	public void setWaitPartTag(Integer waitPartTag) {
		this.waitPartTag = waitPartTag;
	}

}
