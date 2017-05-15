package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

public class OpRepairOrderVO extends BaseVO{

	private static final long serialVersionUID = 1L;

	private String soNo;
	private String customerDesc;
	private Integer traceTag;
	private Integer isTimeoutSubmit;
	private Integer isUpdateEndTimeSupposed;
	private Integer roType;
	private Integer seriousSafetyStatus;
	private Integer roClaimStatus;
	private String remark;
	private Integer activityTraceTag;
	private Integer isTakePartOld;
	private Double lastMaintenanceMileage;
	private Date completeTime;
	private String primaryRoNo;
	private Double labourAmount;
	private Integer isPrint;
	private Integer isOldPart;
	private String delivererPhone;
	private Double balanceAmount;
	private String serviceAdvisorAss;
	private Integer isChangeOdograph;
	private String recommendCustomerName;
	private Integer checkedEnd;
	private String salesPartNo;
	private String recommendEmpName;
	private Integer vehicleTopDesc;
	private Double outMileage;
	private String occurInsuranceNo;
	private Integer isTripleGuaranteeBef;
	private Integer isLargessMaintain;
	private Integer isPurchaseMaintenance;
	private Integer isCustomerInAsc;
	private String deliveryUser;
	private Integer isSeriousTrouble;
	private Date ddcnUpdateDate;
	private Integer insuranceOver;
	private Integer oilRemain;
	private String memberNo;
	private Date printSendTime;
	private String sequenceNo;
	private Integer isActivity;
	private Integer inReason;
	private Double salesPartAmount;
	private Integer waitPartTag;
	private String chiefTechnician;
	private Double receiveAmount;
	private Integer isTripleGuarantee;
	private Integer repairStatus;//工单状态
	private Date forBalanceTime;
	private Double totalMileage;
	private Date troubleOccurDate;
	private Integer isWash;
	private Integer notIntegral;
	private String deliverBillMan;
	private String deliverer;
	private String ownerNo;
	private Date roCreateDate;
	private Double firstEstimateAmount;
	private Integer isQs;
	private String delivererMobile;
	private String testDriver;
	private Integer isAbandonActivity;
	private String lockUser;
	private Double memActiTotalAmount;
	private String eworkshopRemark;
	private Integer schemeStatus;
	private String insurationNo;
	private Double overItemAmount;
	private Integer traceTime;
	private Integer modifyNum;
	private Double dsFactBalance;
	private Date caseClosedDate;
	private Date oldpartTreatDate;
	private String license;
	private Integer roStatus;
	private String noTraceReason;
	private Integer roChargeType;
	private String estimateNo;
	private String ownerName;
	private Integer inBindingStatus;
	private String bookingOrderNo;
	private Integer delivererGender;
	private String engineNo;
	private Date deliverBillDate;
	private Integer settlementStatus;
	private Date printCarTime;
	private String roTroubleDesc;
	private Integer completeTag;
	private Integer deliveryTag;
	private String remark1;
	private String remark2;
	private Date lastMaintenanceDate;
	private String settlementRemark;
	private Double derateAmount;
	private Integer customerPreCheck;
	private Integer isSystemQuote;
	private Double changeMileage;
	private Double totalChangeMileage;
	private Integer ownerProperty;
	private String insurationCode;
	private String model;
	private String roNo;
	private Integer needRoadTest;
	private Float labourPrice;
	private String labourPositionCode;
	private Date printRoTime;
	private String receptionNo;
	private String oldpartRemark;
	private Integer notEnterWorkshop;
	private Date estimateBeginTime;
	private Integer quoteEndAccurate;
	private Double addItemAmount;
	private Date cancelledDate;
	private String vin;
	private Double repairAmount;
	private String series;
	private Double repairPartAmount;
	private Date deliveryDate;
	private String otherRepairType;
	private Double subObbAmount;
	private Date settleColDate;
	private Integer isCloseRo;
	private Double settlementAmount;
	private Integer oldpartTreatMode;
	private Integer waitInfoTag;
	private Date endTimeSupposed;
	private Integer isSeasonCheck;
	private String finishUser;
	private Integer isMaintain;
	private Integer isTrace;
	private Integer roSplitStatus;
	private Integer explainedBalanceAccounts;
	private String serviceAdvisor;
	private Integer outBindingStatus;
	private Double dsAmount;
	private String repairTypeCode;
	private Integer workshopStatus;
	private Double estimateAmount;
	private Integer payment;
	private Double inMileage;
	private String brand;
	private String configCode;
	private Date printRpTime;
	private Integer isStatus;//作废状态(1新建，0作废)
	private Date deleteDate;//作废时间
	private LinkedList<RoRepairPartDetailVO> roRepairPartVoList;//工单维修配件明细
	private LinkedList<RoAddItemDetailVO> roAddItemVoList;//工单附加项目明细
	private LinkedList<RoLabourDetailVO> roLabourVoList;//工单维修项目明细
	private LinkedList<SalesPartDetailVO> salesPartVoList;//配件销售单
	private LinkedList<SalesPartItemDetailVO> salesPartItemVoList;//配件销售单明细
	
	//start wangJian 2016-11-02
	private Double cardAmount;//卡券折扣金额
	private String cardNo;//卡券唯一标识
	//end
	private String standardPackageCode;//保养套餐代码 zhm
	

	public Double getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(Double cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getCustomerDesc() {
		return customerDesc;
	}
	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}
	public Integer getTraceTag() {
		return traceTag;
	}
	public void setTraceTag(Integer traceTag) {
		this.traceTag = traceTag;
	}
	public Integer getIsTimeoutSubmit() {
		return isTimeoutSubmit;
	}
	public void setIsTimeoutSubmit(Integer isTimeoutSubmit) {
		this.isTimeoutSubmit = isTimeoutSubmit;
	}
	public Integer getIsUpdateEndTimeSupposed() {
		return isUpdateEndTimeSupposed;
	}
	public void setIsUpdateEndTimeSupposed(Integer isUpdateEndTimeSupposed) {
		this.isUpdateEndTimeSupposed = isUpdateEndTimeSupposed;
	}
	public Integer getRoType() {
		return roType;
	}
	public void setRoType(Integer roType) {
		this.roType = roType;
	}
	public Integer getSeriousSafetyStatus() {
		return seriousSafetyStatus;
	}
	public void setSeriousSafetyStatus(Integer seriousSafetyStatus) {
		this.seriousSafetyStatus = seriousSafetyStatus;
	}
	public Integer getRoClaimStatus() {
		return roClaimStatus;
	}
	public void setRoClaimStatus(Integer roClaimStatus) {
		this.roClaimStatus = roClaimStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getActivityTraceTag() {
		return activityTraceTag;
	}
	public void setActivityTraceTag(Integer activityTraceTag) {
		this.activityTraceTag = activityTraceTag;
	}
	public Integer getIsTakePartOld() {
		return isTakePartOld;
	}
	public void setIsTakePartOld(Integer isTakePartOld) {
		this.isTakePartOld = isTakePartOld;
	}
	public Double getLastMaintenanceMileage() {
		return lastMaintenanceMileage;
	}
	public void setLastMaintenanceMileage(Double lastMaintenanceMileage) {
		this.lastMaintenanceMileage = lastMaintenanceMileage;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public String getPrimaryRoNo() {
		return primaryRoNo;
	}
	public void setPrimaryRoNo(String primaryRoNo) {
		this.primaryRoNo = primaryRoNo;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}
	public Integer getIsOldPart() {
		return isOldPart;
	}
	public void setIsOldPart(Integer isOldPart) {
		this.isOldPart = isOldPart;
	}
	public String getDelivererPhone() {
		return delivererPhone;
	}
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public String getServiceAdvisorAss() {
		return serviceAdvisorAss;
	}
	public void setServiceAdvisorAss(String serviceAdvisorAss) {
		this.serviceAdvisorAss = serviceAdvisorAss;
	}
	public Integer getIsChangeOdograph() {
		return isChangeOdograph;
	}
	public void setIsChangeOdograph(Integer isChangeOdograph) {
		this.isChangeOdograph = isChangeOdograph;
	}
	public String getRecommendCustomerName() {
		return recommendCustomerName;
	}
	public void setRecommendCustomerName(String recommendCustomerName) {
		this.recommendCustomerName = recommendCustomerName;
	}
	public Integer getCheckedEnd() {
		return checkedEnd;
	}
	public void setCheckedEnd(Integer checkedEnd) {
		this.checkedEnd = checkedEnd;
	}
	public String getSalesPartNo() {
		return salesPartNo;
	}
	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}
	public String getRecommendEmpName() {
		return recommendEmpName;
	}
	public void setRecommendEmpName(String recommendEmpName) {
		this.recommendEmpName = recommendEmpName;
	}
	public Integer getVehicleTopDesc() {
		return vehicleTopDesc;
	}
	public void setVehicleTopDesc(Integer vehicleTopDesc) {
		this.vehicleTopDesc = vehicleTopDesc;
	}
	public Double getOutMileage() {
		return outMileage;
	}
	public void setOutMileage(Double outMileage) {
		this.outMileage = outMileage;
	}
	public String getOccurInsuranceNo() {
		return occurInsuranceNo;
	}
	public void setOccurInsuranceNo(String occurInsuranceNo) {
		this.occurInsuranceNo = occurInsuranceNo;
	}
	public Integer getIsTripleGuaranteeBef() {
		return isTripleGuaranteeBef;
	}
	public void setIsTripleGuaranteeBef(Integer isTripleGuaranteeBef) {
		this.isTripleGuaranteeBef = isTripleGuaranteeBef;
	}
	public Integer getIsLargessMaintain() {
		return isLargessMaintain;
	}
	public void setIsLargessMaintain(Integer isLargessMaintain) {
		this.isLargessMaintain = isLargessMaintain;
	}
	public Integer getIsPurchaseMaintenance() {
		return isPurchaseMaintenance;
	}
	public void setIsPurchaseMaintenance(Integer isPurchaseMaintenance) {
		this.isPurchaseMaintenance = isPurchaseMaintenance;
	}
	public Integer getIsCustomerInAsc() {
		return isCustomerInAsc;
	}
	public void setIsCustomerInAsc(Integer isCustomerInAsc) {
		this.isCustomerInAsc = isCustomerInAsc;
	}
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public Integer getIsSeriousTrouble() {
		return isSeriousTrouble;
	}
	public void setIsSeriousTrouble(Integer isSeriousTrouble) {
		this.isSeriousTrouble = isSeriousTrouble;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public Integer getInsuranceOver() {
		return insuranceOver;
	}
	public void setInsuranceOver(Integer insuranceOver) {
		this.insuranceOver = insuranceOver;
	}
	public Integer getOilRemain() {
		return oilRemain;
	}
	public void setOilRemain(Integer oilRemain) {
		this.oilRemain = oilRemain;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public Date getPrintSendTime() {
		return printSendTime;
	}
	public void setPrintSendTime(Date printSendTime) {
		this.printSendTime = printSendTime;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public Integer getIsActivity() {
		return isActivity;
	}
	public void setIsActivity(Integer isActivity) {
		this.isActivity = isActivity;
	}
	public Integer getInReason() {
		return inReason;
	}
	public void setInReason(Integer inReason) {
		this.inReason = inReason;
	}
	public Double getSalesPartAmount() {
		return salesPartAmount;
	}
	public void setSalesPartAmount(Double salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}
	public Integer getWaitPartTag() {
		return waitPartTag;
	}
	public void setWaitPartTag(Integer waitPartTag) {
		this.waitPartTag = waitPartTag;
	}
	public String getChiefTechnician() {
		return chiefTechnician;
	}
	public void setChiefTechnician(String chiefTechnician) {
		this.chiefTechnician = chiefTechnician;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public Integer getIsTripleGuarantee() {
		return isTripleGuarantee;
	}
	public void setIsTripleGuarantee(Integer isTripleGuarantee) {
		this.isTripleGuarantee = isTripleGuarantee;
	}
	public Integer getRepairStatus() {
		return repairStatus;
	}
	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}
	public Date getForBalanceTime() {
		return forBalanceTime;
	}
	public void setForBalanceTime(Date forBalanceTime) {
		this.forBalanceTime = forBalanceTime;
	}
	public Double getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}
	public Date getTroubleOccurDate() {
		return troubleOccurDate;
	}
	public void setTroubleOccurDate(Date troubleOccurDate) {
		this.troubleOccurDate = troubleOccurDate;
	}
	public Integer getIsWash() {
		return isWash;
	}
	public void setIsWash(Integer isWash) {
		this.isWash = isWash;
	}
	public Integer getNotIntegral() {
		return notIntegral;
	}
	public void setNotIntegral(Integer notIntegral) {
		this.notIntegral = notIntegral;
	}
	public String getDeliverBillMan() {
		return deliverBillMan;
	}
	public void setDeliverBillMan(String deliverBillMan) {
		this.deliverBillMan = deliverBillMan;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public Date getRoCreateDate() {
		return roCreateDate;
	}
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}
	public Double getFirstEstimateAmount() {
		return firstEstimateAmount;
	}
	public void setFirstEstimateAmount(Double firstEstimateAmount) {
		this.firstEstimateAmount = firstEstimateAmount;
	}
	public Integer getIsQs() {
		return isQs;
	}
	public void setIsQs(Integer isQs) {
		this.isQs = isQs;
	}
	public String getDelivererMobile() {
		return delivererMobile;
	}
	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}
	public String getTestDriver() {
		return testDriver;
	}
	public void setTestDriver(String testDriver) {
		this.testDriver = testDriver;
	}
	public Integer getIsAbandonActivity() {
		return isAbandonActivity;
	}
	public void setIsAbandonActivity(Integer isAbandonActivity) {
		this.isAbandonActivity = isAbandonActivity;
	}
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public Double getMemActiTotalAmount() {
		return memActiTotalAmount;
	}
	public void setMemActiTotalAmount(Double memActiTotalAmount) {
		this.memActiTotalAmount = memActiTotalAmount;
	}
	public String getEworkshopRemark() {
		return eworkshopRemark;
	}
	public void setEworkshopRemark(String eworkshopRemark) {
		this.eworkshopRemark = eworkshopRemark;
	}
	public Integer getSchemeStatus() {
		return schemeStatus;
	}
	public void setSchemeStatus(Integer schemeStatus) {
		this.schemeStatus = schemeStatus;
	}
	public String getInsurationNo() {
		return insurationNo;
	}
	public void setInsurationNo(String insurationNo) {
		this.insurationNo = insurationNo;
	}
	public Double getOverItemAmount() {
		return overItemAmount;
	}
	public void setOverItemAmount(Double overItemAmount) {
		this.overItemAmount = overItemAmount;
	}
	public Integer getTraceTime() {
		return traceTime;
	}
	public void setTraceTime(Integer traceTime) {
		this.traceTime = traceTime;
	}
	public Integer getModifyNum() {
		return modifyNum;
	}
	public void setModifyNum(Integer modifyNum) {
		this.modifyNum = modifyNum;
	}
	public Double getDsFactBalance() {
		return dsFactBalance;
	}
	public void setDsFactBalance(Double dsFactBalance) {
		this.dsFactBalance = dsFactBalance;
	}
	public Date getCaseClosedDate() {
		return caseClosedDate;
	}
	public void setCaseClosedDate(Date caseClosedDate) {
		this.caseClosedDate = caseClosedDate;
	}
	public Date getOldpartTreatDate() {
		return oldpartTreatDate;
	}
	public void setOldpartTreatDate(Date oldpartTreatDate) {
		this.oldpartTreatDate = oldpartTreatDate;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Integer getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}
	public String getNoTraceReason() {
		return noTraceReason;
	}
	public void setNoTraceReason(String noTraceReason) {
		this.noTraceReason = noTraceReason;
	}
	public Integer getRoChargeType() {
		return roChargeType;
	}
	public void setRoChargeType(Integer roChargeType) {
		this.roChargeType = roChargeType;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getInBindingStatus() {
		return inBindingStatus;
	}
	public void setInBindingStatus(Integer inBindingStatus) {
		this.inBindingStatus = inBindingStatus;
	}
	public String getBookingOrderNo() {
		return bookingOrderNo;
	}
	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}
	public Integer getDelivererGender() {
		return delivererGender;
	}
	public void setDelivererGender(Integer delivererGender) {
		this.delivererGender = delivererGender;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public Date getDeliverBillDate() {
		return deliverBillDate;
	}
	public void setDeliverBillDate(Date deliverBillDate) {
		this.deliverBillDate = deliverBillDate;
	}
	public Integer getSettlementStatus() {
		return settlementStatus;
	}
	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public Date getPrintCarTime() {
		return printCarTime;
	}
	public void setPrintCarTime(Date printCarTime) {
		this.printCarTime = printCarTime;
	}
	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}
	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}
	public Integer getCompleteTag() {
		return completeTag;
	}
	public void setCompleteTag(Integer completeTag) {
		this.completeTag = completeTag;
	}
	public Integer getDeliveryTag() {
		return deliveryTag;
	}
	public void setDeliveryTag(Integer deliveryTag) {
		this.deliveryTag = deliveryTag;
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
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	public String getSettlementRemark() {
		return settlementRemark;
	}
	public void setSettlementRemark(String settlementRemark) {
		this.settlementRemark = settlementRemark;
	}
	public Double getDerateAmount() {
		return derateAmount;
	}
	public void setDerateAmount(Double derateAmount) {
		this.derateAmount = derateAmount;
	}
	public Integer getCustomerPreCheck() {
		return customerPreCheck;
	}
	public void setCustomerPreCheck(Integer customerPreCheck) {
		this.customerPreCheck = customerPreCheck;
	}
	public Integer getIsSystemQuote() {
		return isSystemQuote;
	}
	public void setIsSystemQuote(Integer isSystemQuote) {
		this.isSystemQuote = isSystemQuote;
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
	public Integer getOwnerProperty() {
		return ownerProperty;
	}
	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}
	public String getInsurationCode() {
		return insurationCode;
	}
	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public Integer getNeedRoadTest() {
		return needRoadTest;
	}
	public void setNeedRoadTest(Integer needRoadTest) {
		this.needRoadTest = needRoadTest;
	}
	public Float getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(Float labourPrice) {
		this.labourPrice = labourPrice;
	}
	public String getLabourPositionCode() {
		return labourPositionCode;
	}
	public void setLabourPositionCode(String labourPositionCode) {
		this.labourPositionCode = labourPositionCode;
	}
	public Date getPrintRoTime() {
		return printRoTime;
	}
	public void setPrintRoTime(Date printRoTime) {
		this.printRoTime = printRoTime;
	}
	public String getReceptionNo() {
		return receptionNo;
	}
	public void setReceptionNo(String receptionNo) {
		this.receptionNo = receptionNo;
	}
	public String getOldpartRemark() {
		return oldpartRemark;
	}
	public void setOldpartRemark(String oldpartRemark) {
		this.oldpartRemark = oldpartRemark;
	}
	public Integer getNotEnterWorkshop() {
		return notEnterWorkshop;
	}
	public void setNotEnterWorkshop(Integer notEnterWorkshop) {
		this.notEnterWorkshop = notEnterWorkshop;
	}
	public Date getEstimateBeginTime() {
		return estimateBeginTime;
	}
	public void setEstimateBeginTime(Date estimateBeginTime) {
		this.estimateBeginTime = estimateBeginTime;
	}
	public Integer getQuoteEndAccurate() {
		return quoteEndAccurate;
	}
	public void setQuoteEndAccurate(Integer quoteEndAccurate) {
		this.quoteEndAccurate = quoteEndAccurate;
	}
	public Double getAddItemAmount() {
		return addItemAmount;
	}
	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}
	public Date getCancelledDate() {
		return cancelledDate;
	}
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Double getRepairAmount() {
		return repairAmount;
	}
	public void setRepairAmount(Double repairAmount) {
		this.repairAmount = repairAmount;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public Double getRepairPartAmount() {
		return repairPartAmount;
	}
	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getOtherRepairType() {
		return otherRepairType;
	}
	public void setOtherRepairType(String otherRepairType) {
		this.otherRepairType = otherRepairType;
	}
	public Double getSubObbAmount() {
		return subObbAmount;
	}
	public void setSubObbAmount(Double subObbAmount) {
		this.subObbAmount = subObbAmount;
	}
	public Date getSettleColDate() {
		return settleColDate;
	}
	public void setSettleColDate(Date settleColDate) {
		this.settleColDate = settleColDate;
	}
	public Integer getIsCloseRo() {
		return isCloseRo;
	}
	public void setIsCloseRo(Integer isCloseRo) {
		this.isCloseRo = isCloseRo;
	}
	public Double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public Integer getOldpartTreatMode() {
		return oldpartTreatMode;
	}
	public void setOldpartTreatMode(Integer oldpartTreatMode) {
		this.oldpartTreatMode = oldpartTreatMode;
	}
	public Integer getWaitInfoTag() {
		return waitInfoTag;
	}
	public void setWaitInfoTag(Integer waitInfoTag) {
		this.waitInfoTag = waitInfoTag;
	}
	public Date getEndTimeSupposed() {
		return endTimeSupposed;
	}
	public void setEndTimeSupposed(Date endTimeSupposed) {
		this.endTimeSupposed = endTimeSupposed;
	}
	public Integer getIsSeasonCheck() {
		return isSeasonCheck;
	}
	public void setIsSeasonCheck(Integer isSeasonCheck) {
		this.isSeasonCheck = isSeasonCheck;
	}
	public String getFinishUser() {
		return finishUser;
	}
	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}
	public Integer getIsMaintain() {
		return isMaintain;
	}
	public void setIsMaintain(Integer isMaintain) {
		this.isMaintain = isMaintain;
	}
	public Integer getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public Integer getRoSplitStatus() {
		return roSplitStatus;
	}
	public void setRoSplitStatus(Integer roSplitStatus) {
		this.roSplitStatus = roSplitStatus;
	}
	public Integer getExplainedBalanceAccounts() {
		return explainedBalanceAccounts;
	}
	public void setExplainedBalanceAccounts(Integer explainedBalanceAccounts) {
		this.explainedBalanceAccounts = explainedBalanceAccounts;
	}
	public String getServiceAdvisor() {
		return serviceAdvisor;
	}
	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}
	public Integer getOutBindingStatus() {
		return outBindingStatus;
	}
	public void setOutBindingStatus(Integer outBindingStatus) {
		this.outBindingStatus = outBindingStatus;
	}
	public Double getDsAmount() {
		return dsAmount;
	}
	public void setDsAmount(Double dsAmount) {
		this.dsAmount = dsAmount;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public Integer getWorkshopStatus() {
		return workshopStatus;
	}
	public void setWorkshopStatus(Integer workshopStatus) {
		this.workshopStatus = workshopStatus;
	}
	public Double getEstimateAmount() {
		return estimateAmount;
	}
	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public Double getInMileage() {
		return inMileage;
	}
	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public Date getPrintRpTime() {
		return printRpTime;
	}
	public void setPrintRpTime(Date printRpTime) {
		this.printRpTime = printRpTime;
	}
	public Integer getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public LinkedList<RoRepairPartDetailVO> getRoRepairPartVoList() {
		return roRepairPartVoList;
	}
	public void setRoRepairPartVoList(
			LinkedList<RoRepairPartDetailVO> roRepairPartVoList) {
		this.roRepairPartVoList = roRepairPartVoList;
	}
	public LinkedList<RoAddItemDetailVO> getRoAddItemVoList() {
		return roAddItemVoList;
	}
	public void setRoAddItemVoList(LinkedList<RoAddItemDetailVO> roAddItemVoList) {
		this.roAddItemVoList = roAddItemVoList;
	}
	public LinkedList<RoLabourDetailVO> getRoLabourVoList() {
		return roLabourVoList;
	}
	public void setRoLabourVoList(LinkedList<RoLabourDetailVO> roLabourVoList) {
		this.roLabourVoList = roLabourVoList;
	}
	public LinkedList<SalesPartDetailVO> getSalesPartVoList() {
		return salesPartVoList;
	}
	public void setSalesPartVoList(LinkedList<SalesPartDetailVO> salesPartVoList) {
		this.salesPartVoList = salesPartVoList;
	}
	public LinkedList<SalesPartItemDetailVO> getSalesPartItemVoList() {
		return salesPartItemVoList;
	}
	public void setSalesPartItemVoList(
			LinkedList<SalesPartItemDetailVO> salesPartItemVoList) {
		this.salesPartItemVoList = salesPartItemVoList;
	}
	public String getStandardPackageCode() {
		return standardPackageCode;
	}
	public void setStandardPackageCode(String standardPackageCode) {
		this.standardPackageCode = standardPackageCode;
	}
	
}
