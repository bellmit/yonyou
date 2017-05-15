package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class OpRepairOrderDTO {

	private String entityCode;
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
	private Integer repairStatus;
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
	private Integer isStatus;
	private Date deleteDate;
	private LinkedList<RoRepairPartDetailDTO> roRepairPartVoList;
	private LinkedList<RoAddItemDetailDTO> roAddItemVoList;
	private LinkedList<RoLabourDetailDTO> roLabourVoList;
	private LinkedList<SalesPartDetailDTO> salesPartVoList;
	private LinkedList<SalesPartItemDetailDTO> salesPartItemVoList;
	private Double cardAmount;//卡券折扣金额
	private String cardNo;//卡券唯一标识
	private String standardPackageCode;//保养套餐代码 
	
	public String getEntityCode() {
		return entityCode;
	}
	public String getSoNo() {
		return soNo;
	}
	public String getCustomerDesc() {
		return customerDesc;
	}
	public Integer getTraceTag() {
		return traceTag;
	}
	public Integer getIsTimeoutSubmit() {
		return isTimeoutSubmit;
	}
	public Integer getIsUpdateEndTimeSupposed() {
		return isUpdateEndTimeSupposed;
	}
	public Integer getRoType() {
		return roType;
	}
	public Integer getSeriousSafetyStatus() {
		return seriousSafetyStatus;
	}
	public Integer getRoClaimStatus() {
		return roClaimStatus;
	}
	public String getRemark() {
		return remark;
	}
	public Integer getActivityTraceTag() {
		return activityTraceTag;
	}
	public Integer getIsTakePartOld() {
		return isTakePartOld;
	}
	public Double getLastMaintenanceMileage() {
		return lastMaintenanceMileage;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public String getPrimaryRoNo() {
		return primaryRoNo;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public Integer getIsPrint() {
		return isPrint;
	}
	public Integer getIsOldPart() {
		return isOldPart;
	}
	public String getDelivererPhone() {
		return delivererPhone;
	}
	public Double getBalanceAmount() {
		return balanceAmount;
	}
	public String getServiceAdvisorAss() {
		return serviceAdvisorAss;
	}
	public Integer getIsChangeOdograph() {
		return isChangeOdograph;
	}
	public String getRecommendCustomerName() {
		return recommendCustomerName;
	}
	public Integer getCheckedEnd() {
		return checkedEnd;
	}
	public String getSalesPartNo() {
		return salesPartNo;
	}
	public String getRecommendEmpName() {
		return recommendEmpName;
	}
	public Integer getVehicleTopDesc() {
		return vehicleTopDesc;
	}
	public Double getOutMileage() {
		return outMileage;
	}
	public String getOccurInsuranceNo() {
		return occurInsuranceNo;
	}
	public Integer getIsTripleGuaranteeBef() {
		return isTripleGuaranteeBef;
	}
	public Integer getIsLargessMaintain() {
		return isLargessMaintain;
	}
	public Integer getIsPurchaseMaintenance() {
		return isPurchaseMaintenance;
	}
	public Integer getIsCustomerInAsc() {
		return isCustomerInAsc;
	}
	public String getDeliveryUser() {
		return deliveryUser;
	}
	public Integer getIsSeriousTrouble() {
		return isSeriousTrouble;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public Integer getInsuranceOver() {
		return insuranceOver;
	}
	public Integer getOilRemain() {
		return oilRemain;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public Date getPrintSendTime() {
		return printSendTime;
	}
	public String getSequenceNo() {
		return sequenceNo;
	}
	public Integer getIsActivity() {
		return isActivity;
	}
	public Integer getInReason() {
		return inReason;
	}
	public Double getSalesPartAmount() {
		return salesPartAmount;
	}
	public Integer getWaitPartTag() {
		return waitPartTag;
	}
	public String getChiefTechnician() {
		return chiefTechnician;
	}
	public Double getReceiveAmount() {
		return receiveAmount;
	}
	public Integer getIsTripleGuarantee() {
		return isTripleGuarantee;
	}
	public Integer getRepairStatus() {
		return repairStatus;
	}
	public Date getForBalanceTime() {
		return forBalanceTime;
	}
	public Double getTotalMileage() {
		return totalMileage;
	}
	public Date getTroubleOccurDate() {
		return troubleOccurDate;
	}
	public Integer getIsWash() {
		return isWash;
	}
	public Integer getNotIntegral() {
		return notIntegral;
	}
	public String getDeliverBillMan() {
		return deliverBillMan;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public Date getRoCreateDate() {
		return roCreateDate;
	}
	public Double getFirstEstimateAmount() {
		return firstEstimateAmount;
	}
	public Integer getIsQs() {
		return isQs;
	}
	public String getDelivererMobile() {
		return delivererMobile;
	}
	public String getTestDriver() {
		return testDriver;
	}
	public Integer getIsAbandonActivity() {
		return isAbandonActivity;
	}
	public String getLockUser() {
		return lockUser;
	}
	public Double getMemActiTotalAmount() {
		return memActiTotalAmount;
	}
	public String getEworkshopRemark() {
		return eworkshopRemark;
	}
	public Integer getSchemeStatus() {
		return schemeStatus;
	}
	public String getInsurationNo() {
		return insurationNo;
	}
	public Double getOverItemAmount() {
		return overItemAmount;
	}
	public Integer getTraceTime() {
		return traceTime;
	}
	public Integer getModifyNum() {
		return modifyNum;
	}
	public Double getDsFactBalance() {
		return dsFactBalance;
	}
	public Date getCaseClosedDate() {
		return caseClosedDate;
	}
	public Date getOldpartTreatDate() {
		return oldpartTreatDate;
	}
	public String getLicense() {
		return license;
	}
	public Integer getRoStatus() {
		return roStatus;
	}
	public String getNoTraceReason() {
		return noTraceReason;
	}
	public Integer getRoChargeType() {
		return roChargeType;
	}
	public String getEstimateNo() {
		return estimateNo;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public Integer getInBindingStatus() {
		return inBindingStatus;
	}
	public String getBookingOrderNo() {
		return bookingOrderNo;
	}
	public Integer getDelivererGender() {
		return delivererGender;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public Date getDeliverBillDate() {
		return deliverBillDate;
	}
	public Integer getSettlementStatus() {
		return settlementStatus;
	}
	public Date getPrintCarTime() {
		return printCarTime;
	}
	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}
	public Integer getCompleteTag() {
		return completeTag;
	}
	public Integer getDeliveryTag() {
		return deliveryTag;
	}
	public String getRemark1() {
		return remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public Date getLastMaintenanceDate() {
		return lastMaintenanceDate;
	}
	public String getSettlementRemark() {
		return settlementRemark;
	}
	public Double getDerateAmount() {
		return derateAmount;
	}
	public Integer getCustomerPreCheck() {
		return customerPreCheck;
	}
	public Integer getIsSystemQuote() {
		return isSystemQuote;
	}
	public Double getChangeMileage() {
		return changeMileage;
	}
	public Double getTotalChangeMileage() {
		return totalChangeMileage;
	}
	public Integer getOwnerProperty() {
		return ownerProperty;
	}
	public String getInsurationCode() {
		return insurationCode;
	}
	public String getModel() {
		return model;
	}
	public String getRoNo() {
		return roNo;
	}
	public Integer getNeedRoadTest() {
		return needRoadTest;
	}
	public Float getLabourPrice() {
		return labourPrice;
	}
	public String getLabourPositionCode() {
		return labourPositionCode;
	}
	public Date getPrintRoTime() {
		return printRoTime;
	}
	public String getReceptionNo() {
		return receptionNo;
	}
	public String getOldpartRemark() {
		return oldpartRemark;
	}
	public Integer getNotEnterWorkshop() {
		return notEnterWorkshop;
	}
	public Date getEstimateBeginTime() {
		return estimateBeginTime;
	}
	public Integer getQuoteEndAccurate() {
		return quoteEndAccurate;
	}
	public Double getAddItemAmount() {
		return addItemAmount;
	}
	public Date getCancelledDate() {
		return cancelledDate;
	}
	public String getVin() {
		return vin;
	}
	public Double getRepairAmount() {
		return repairAmount;
	}
	public String getSeries() {
		return series;
	}
	public Double getRepairPartAmount() {
		return repairPartAmount;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public String getOtherRepairType() {
		return otherRepairType;
	}
	public Double getSubObbAmount() {
		return subObbAmount;
	}
	public Date getSettleColDate() {
		return settleColDate;
	}
	public Integer getIsCloseRo() {
		return isCloseRo;
	}
	public Double getSettlementAmount() {
		return settlementAmount;
	}
	public Integer getOldpartTreatMode() {
		return oldpartTreatMode;
	}
	public Integer getWaitInfoTag() {
		return waitInfoTag;
	}
	public Date getEndTimeSupposed() {
		return endTimeSupposed;
	}
	public Integer getIsSeasonCheck() {
		return isSeasonCheck;
	}
	public String getFinishUser() {
		return finishUser;
	}
	public Integer getIsMaintain() {
		return isMaintain;
	}
	public Integer getIsTrace() {
		return isTrace;
	}
	public Integer getRoSplitStatus() {
		return roSplitStatus;
	}
	public Integer getExplainedBalanceAccounts() {
		return explainedBalanceAccounts;
	}
	public String getServiceAdvisor() {
		return serviceAdvisor;
	}
	public Integer getOutBindingStatus() {
		return outBindingStatus;
	}
	public Double getDsAmount() {
		return dsAmount;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public Integer getWorkshopStatus() {
		return workshopStatus;
	}
	public Double getEstimateAmount() {
		return estimateAmount;
	}
	public Integer getPayment() {
		return payment;
	}
	public Double getInMileage() {
		return inMileage;
	}
	public String getBrand() {
		return brand;
	}
	public String getConfigCode() {
		return configCode;
	}
	public Date getPrintRpTime() {
		return printRpTime;
	}
	public Integer getIsStatus() {
		return isStatus;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public LinkedList<RoRepairPartDetailDTO> getRoRepairPartVoList() {
		return roRepairPartVoList;
	}
	public LinkedList<RoAddItemDetailDTO> getRoAddItemVoList() {
		return roAddItemVoList;
	}
	public LinkedList<RoLabourDetailDTO> getRoLabourVoList() {
		return roLabourVoList;
	}
	public LinkedList<SalesPartDetailDTO> getSalesPartVoList() {
		return salesPartVoList;
	}
	public LinkedList<SalesPartItemDetailDTO> getSalesPartItemVoList() {
		return salesPartItemVoList;
	}
	public Double getCardAmount() {
		return cardAmount;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}
	public void setTraceTag(Integer traceTag) {
		this.traceTag = traceTag;
	}
	public void setIsTimeoutSubmit(Integer isTimeoutSubmit) {
		this.isTimeoutSubmit = isTimeoutSubmit;
	}
	public void setIsUpdateEndTimeSupposed(Integer isUpdateEndTimeSupposed) {
		this.isUpdateEndTimeSupposed = isUpdateEndTimeSupposed;
	}
	public void setRoType(Integer roType) {
		this.roType = roType;
	}
	public void setSeriousSafetyStatus(Integer seriousSafetyStatus) {
		this.seriousSafetyStatus = seriousSafetyStatus;
	}
	public void setRoClaimStatus(Integer roClaimStatus) {
		this.roClaimStatus = roClaimStatus;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setActivityTraceTag(Integer activityTraceTag) {
		this.activityTraceTag = activityTraceTag;
	}
	public void setIsTakePartOld(Integer isTakePartOld) {
		this.isTakePartOld = isTakePartOld;
	}
	public void setLastMaintenanceMileage(Double lastMaintenanceMileage) {
		this.lastMaintenanceMileage = lastMaintenanceMileage;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public void setPrimaryRoNo(String primaryRoNo) {
		this.primaryRoNo = primaryRoNo;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}
	public void setIsOldPart(Integer isOldPart) {
		this.isOldPart = isOldPart;
	}
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}
	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
	public void setServiceAdvisorAss(String serviceAdvisorAss) {
		this.serviceAdvisorAss = serviceAdvisorAss;
	}
	public void setIsChangeOdograph(Integer isChangeOdograph) {
		this.isChangeOdograph = isChangeOdograph;
	}
	public void setRecommendCustomerName(String recommendCustomerName) {
		this.recommendCustomerName = recommendCustomerName;
	}
	public void setCheckedEnd(Integer checkedEnd) {
		this.checkedEnd = checkedEnd;
	}
	public void setSalesPartNo(String salesPartNo) {
		this.salesPartNo = salesPartNo;
	}
	public void setRecommendEmpName(String recommendEmpName) {
		this.recommendEmpName = recommendEmpName;
	}
	public void setVehicleTopDesc(Integer vehicleTopDesc) {
		this.vehicleTopDesc = vehicleTopDesc;
	}
	public void setOutMileage(Double outMileage) {
		this.outMileage = outMileage;
	}
	public void setOccurInsuranceNo(String occurInsuranceNo) {
		this.occurInsuranceNo = occurInsuranceNo;
	}
	public void setIsTripleGuaranteeBef(Integer isTripleGuaranteeBef) {
		this.isTripleGuaranteeBef = isTripleGuaranteeBef;
	}
	public void setIsLargessMaintain(Integer isLargessMaintain) {
		this.isLargessMaintain = isLargessMaintain;
	}
	public void setIsPurchaseMaintenance(Integer isPurchaseMaintenance) {
		this.isPurchaseMaintenance = isPurchaseMaintenance;
	}
	public void setIsCustomerInAsc(Integer isCustomerInAsc) {
		this.isCustomerInAsc = isCustomerInAsc;
	}
	public void setDeliveryUser(String deliveryUser) {
		this.deliveryUser = deliveryUser;
	}
	public void setIsSeriousTrouble(Integer isSeriousTrouble) {
		this.isSeriousTrouble = isSeriousTrouble;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public void setInsuranceOver(Integer insuranceOver) {
		this.insuranceOver = insuranceOver;
	}
	public void setOilRemain(Integer oilRemain) {
		this.oilRemain = oilRemain;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public void setPrintSendTime(Date printSendTime) {
		this.printSendTime = printSendTime;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public void setIsActivity(Integer isActivity) {
		this.isActivity = isActivity;
	}
	public void setInReason(Integer inReason) {
		this.inReason = inReason;
	}
	public void setSalesPartAmount(Double salesPartAmount) {
		this.salesPartAmount = salesPartAmount;
	}
	public void setWaitPartTag(Integer waitPartTag) {
		this.waitPartTag = waitPartTag;
	}
	public void setChiefTechnician(String chiefTechnician) {
		this.chiefTechnician = chiefTechnician;
	}
	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}
	public void setIsTripleGuarantee(Integer isTripleGuarantee) {
		this.isTripleGuarantee = isTripleGuarantee;
	}
	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
	}
	public void setForBalanceTime(Date forBalanceTime) {
		this.forBalanceTime = forBalanceTime;
	}
	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}
	public void setTroubleOccurDate(Date troubleOccurDate) {
		this.troubleOccurDate = troubleOccurDate;
	}
	public void setIsWash(Integer isWash) {
		this.isWash = isWash;
	}
	public void setNotIntegral(Integer notIntegral) {
		this.notIntegral = notIntegral;
	}
	public void setDeliverBillMan(String deliverBillMan) {
		this.deliverBillMan = deliverBillMan;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}
	public void setFirstEstimateAmount(Double firstEstimateAmount) {
		this.firstEstimateAmount = firstEstimateAmount;
	}
	public void setIsQs(Integer isQs) {
		this.isQs = isQs;
	}
	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}
	public void setTestDriver(String testDriver) {
		this.testDriver = testDriver;
	}
	public void setIsAbandonActivity(Integer isAbandonActivity) {
		this.isAbandonActivity = isAbandonActivity;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public void setMemActiTotalAmount(Double memActiTotalAmount) {
		this.memActiTotalAmount = memActiTotalAmount;
	}
	public void setEworkshopRemark(String eworkshopRemark) {
		this.eworkshopRemark = eworkshopRemark;
	}
	public void setSchemeStatus(Integer schemeStatus) {
		this.schemeStatus = schemeStatus;
	}
	public void setInsurationNo(String insurationNo) {
		this.insurationNo = insurationNo;
	}
	public void setOverItemAmount(Double overItemAmount) {
		this.overItemAmount = overItemAmount;
	}
	public void setTraceTime(Integer traceTime) {
		this.traceTime = traceTime;
	}
	public void setModifyNum(Integer modifyNum) {
		this.modifyNum = modifyNum;
	}
	public void setDsFactBalance(Double dsFactBalance) {
		this.dsFactBalance = dsFactBalance;
	}
	public void setCaseClosedDate(Date caseClosedDate) {
		this.caseClosedDate = caseClosedDate;
	}
	public void setOldpartTreatDate(Date oldpartTreatDate) {
		this.oldpartTreatDate = oldpartTreatDate;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}
	public void setNoTraceReason(String noTraceReason) {
		this.noTraceReason = noTraceReason;
	}
	public void setRoChargeType(Integer roChargeType) {
		this.roChargeType = roChargeType;
	}
	public void setEstimateNo(String estimateNo) {
		this.estimateNo = estimateNo;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setInBindingStatus(Integer inBindingStatus) {
		this.inBindingStatus = inBindingStatus;
	}
	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}
	public void setDelivererGender(Integer delivererGender) {
		this.delivererGender = delivererGender;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public void setDeliverBillDate(Date deliverBillDate) {
		this.deliverBillDate = deliverBillDate;
	}
	public void setSettlementStatus(Integer settlementStatus) {
		this.settlementStatus = settlementStatus;
	}
	public void setPrintCarTime(Date printCarTime) {
		this.printCarTime = printCarTime;
	}
	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}
	public void setCompleteTag(Integer completeTag) {
		this.completeTag = completeTag;
	}
	public void setDeliveryTag(Integer deliveryTag) {
		this.deliveryTag = deliveryTag;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public void setLastMaintenanceDate(Date lastMaintenanceDate) {
		this.lastMaintenanceDate = lastMaintenanceDate;
	}
	public void setSettlementRemark(String settlementRemark) {
		this.settlementRemark = settlementRemark;
	}
	public void setDerateAmount(Double derateAmount) {
		this.derateAmount = derateAmount;
	}
	public void setCustomerPreCheck(Integer customerPreCheck) {
		this.customerPreCheck = customerPreCheck;
	}
	public void setIsSystemQuote(Integer isSystemQuote) {
		this.isSystemQuote = isSystemQuote;
	}
	public void setChangeMileage(Double changeMileage) {
		this.changeMileage = changeMileage;
	}
	public void setTotalChangeMileage(Double totalChangeMileage) {
		this.totalChangeMileage = totalChangeMileage;
	}
	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}
	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public void setNeedRoadTest(Integer needRoadTest) {
		this.needRoadTest = needRoadTest;
	}
	public void setLabourPrice(Float labourPrice) {
		this.labourPrice = labourPrice;
	}
	public void setLabourPositionCode(String labourPositionCode) {
		this.labourPositionCode = labourPositionCode;
	}
	public void setPrintRoTime(Date printRoTime) {
		this.printRoTime = printRoTime;
	}
	public void setReceptionNo(String receptionNo) {
		this.receptionNo = receptionNo;
	}
	public void setOldpartRemark(String oldpartRemark) {
		this.oldpartRemark = oldpartRemark;
	}
	public void setNotEnterWorkshop(Integer notEnterWorkshop) {
		this.notEnterWorkshop = notEnterWorkshop;
	}
	public void setEstimateBeginTime(Date estimateBeginTime) {
		this.estimateBeginTime = estimateBeginTime;
	}
	public void setQuoteEndAccurate(Integer quoteEndAccurate) {
		this.quoteEndAccurate = quoteEndAccurate;
	}
	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}
	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public void setRepairAmount(Double repairAmount) {
		this.repairAmount = repairAmount;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public void setOtherRepairType(String otherRepairType) {
		this.otherRepairType = otherRepairType;
	}
	public void setSubObbAmount(Double subObbAmount) {
		this.subObbAmount = subObbAmount;
	}
	public void setSettleColDate(Date settleColDate) {
		this.settleColDate = settleColDate;
	}
	public void setIsCloseRo(Integer isCloseRo) {
		this.isCloseRo = isCloseRo;
	}
	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public void setOldpartTreatMode(Integer oldpartTreatMode) {
		this.oldpartTreatMode = oldpartTreatMode;
	}
	public void setWaitInfoTag(Integer waitInfoTag) {
		this.waitInfoTag = waitInfoTag;
	}
	public void setEndTimeSupposed(Date endTimeSupposed) {
		this.endTimeSupposed = endTimeSupposed;
	}
	public void setIsSeasonCheck(Integer isSeasonCheck) {
		this.isSeasonCheck = isSeasonCheck;
	}
	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}
	public void setIsMaintain(Integer isMaintain) {
		this.isMaintain = isMaintain;
	}
	public void setIsTrace(Integer isTrace) {
		this.isTrace = isTrace;
	}
	public void setRoSplitStatus(Integer roSplitStatus) {
		this.roSplitStatus = roSplitStatus;
	}
	public void setExplainedBalanceAccounts(Integer explainedBalanceAccounts) {
		this.explainedBalanceAccounts = explainedBalanceAccounts;
	}
	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}
	public void setOutBindingStatus(Integer outBindingStatus) {
		this.outBindingStatus = outBindingStatus;
	}
	public void setDsAmount(Double dsAmount) {
		this.dsAmount = dsAmount;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public void setWorkshopStatus(Integer workshopStatus) {
		this.workshopStatus = workshopStatus;
	}
	public void setEstimateAmount(Double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public void setPrintRpTime(Date printRpTime) {
		this.printRpTime = printRpTime;
	}
	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public void setRoRepairPartVoList(LinkedList<RoRepairPartDetailDTO> roRepairPartVoList) {
		this.roRepairPartVoList = roRepairPartVoList;
	}
	public void setRoAddItemVoList(LinkedList<RoAddItemDetailDTO> roAddItemVoList) {
		this.roAddItemVoList = roAddItemVoList;
	}
	public void setRoLabourVoList(LinkedList<RoLabourDetailDTO> roLabourVoList) {
		this.roLabourVoList = roLabourVoList;
	}
	public void setSalesPartVoList(LinkedList<SalesPartDetailDTO> salesPartVoList) {
		this.salesPartVoList = salesPartVoList;
	}
	public void setSalesPartItemVoList(LinkedList<SalesPartItemDetailDTO> salesPartItemVoList) {
		this.salesPartItemVoList = salesPartItemVoList;
	}
	public void setCardAmount(Double cardAmount) {
		this.cardAmount = cardAmount;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getStandardPackageCode() {
		return standardPackageCode;
	}
	public void setStandardPackageCode(String standardPackageCode) {
		this.standardPackageCode = standardPackageCode;
	}
	
}
