
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : TtRepairOrderPO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月10日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.partOrder;

import java.util.Date;
import java.util.List;

/**
 * 发料价格调整
 * 
 * @author zhanshiwei
 * @date 2017年5月10日
 */

public class TtRepairOrderDTO {

    private Double                       addItemAmount;
    private String                       insurationNo;
    private String                       license;
    private String                       testDriver;
    private String                       bookingOrderNo;
    private String                       ownerName;
    private Integer                      roChargeType;
    private String                       entityCode;
    private Integer                      ver;
    private Integer                      schemeStatus;            // 方案状态
    private String                       roNo;
    private String                       ownerNo;
    private Date                         createDate;
    private String                       insurationCode;
    private Date                         printRoTime;
    private String                       finishUser;
    private String                       noTraceReason;
    private Integer                      vehicleTopDesc;
    private Double                       labourAmount;
    private String                       chiefTechnician;
    private String                       engineNo;
    private Integer                      isMaintain;
    private String                       vin;
    private Double                       overItemAmount;
    private Integer                      oilRemain;
    private Double                       totalMileage;
    private Double                       firstEstimateAmount;
    private String                       recommendCustomerName;
    private Date                         printRpTime;
    private Integer                      completeTag;
    private Integer                      modifyNum;
    private String                       brand;
    private Integer                      needRoadTest;
    private String                       memberNo;
    private Integer                      deliveryTag;
    private Integer                      waitPartTag;
    private String                       recommendEmpName;
    private String                       delivererMobile;
    private Integer                      roStatus;
    private Double                       inMileage;
    private Long                         updateBy;
    private String                       deliveryUser;
    private Date                         roCreateDate;
    private String                       series;
    private String                       configCode;
    private Double                       balanceAmount;
    private Integer                      isChangeOdograph;
    private Integer                      roType;
    private Double                       changeMileage;
    private String                       serviceAdvisorAss;
    private Integer                      dKey;
    private Double                       receiveAmount;
    private String                       primaryRoNo;
    private Integer                      delivererGender;
    private Long                         createBy;
    private Date                         forBalanceTime;
    private Integer                      waitInfoTag;
    private String                       lockUser;
    private Integer                      isCloseRo;
    private String                       sequenceNo;

    private String                       delivererPhone;

    private Integer                      ownerProperty;

    private Double                       salesPartAmount;

    private Integer                      isTrace;

    private Float                        labourPrice;

    private String                       model;

    private Integer                      traceTag;

    private Integer                      traceTime;

    private Double                       totalChangeMileage;

    private Integer                      isCustomerInAsc;

    private String                       soNo;

    private Double                       repairPartAmount;

    private String                       salesPartNo;

    private Integer                      inReason;

    private Date                         completeTime;

    private Date                         updateDate;

    private Double                       derateAmount;

    private Date                         deliveryDate;

    private String                       estimateNo;

    private Date                         endTimeSupposed;

    private Double                       repairAmount;

    private String                       customerDesc;

    private Double                       outMileage;

    private Integer                      roSplitStatus;

    private String                       deliverer;

    private Integer                      isSeasonCheck;

    private String                       repairTypeCode;

    private String                       otherRepairType;

    private Double                       estimateAmount;

    private Integer                      isActivity;

    private Double                       subObbAmount;

    private Integer                      isWash;

    private String                       remark;

    private String                       remark1;

    private String                       remark2;

    private String                       serviceAdvisor;

    private Integer                      quoteEndAccurate;

    private Integer                      customerPreCheck;

    private Integer                      checkedEnd;

    private Integer                      explainedBalanceAccounts;

    private Date                         estimateBeginTime;

    private Integer                      notEnterWorkshop;

    private Integer                      isTakePartOld;

    private String                       roTroubleDesc;

    private Integer                      isLargessMaintain;

    private Integer                      isUpdateEndTimeSupposed;

    private Integer                      isSystemQuote;

    private Integer                      notIntegral;

    private String                       occurInsuranceNo;

    private String                       deliverBillMan;

    private Date                         deliverBillDate;

    private Integer                      payment;

    private Date                         caseClosedDate;

    private Date                         settleColDate;

    private Double                       settlementAmount;

    private Double                       dsAmount;

    private Double                       dsFactBalance;

    private Integer                      settlementStatus;

    private String                       settlementRemark;

    private Integer                      isOldPart;

    private Integer                      oldpartTreatMode;

    private Date                         oldpartTreatDate;

    private String                       oldpartRemark;

    private Integer                      insuranceOver;

    private Date                         printSendTime;

    private String                       labourPositionCode;

    private String                       receptionNo;

    private Double                       memActiTotalAmount;

    private Date                         troubleOccurDate;

    private Integer                      roClaimStatus;

    private Integer                      isPurchaseMaintenance;

    private Integer                      isQs;

    private String                       eworkshopRemark;

    private Integer                      isTripleGuarantee;

    private Integer                      isTripleGuaranteeBef;

    private Integer                      isPrint;

    private Integer                      workshopStatus;

    private Date                         printCarTime;            // 交车服务单打印时间

    private Date                         lastMaintenanceDate;     // 上次保养日期

    private Double                       lastMaintenanceMileage;  // 上次保养里程

    private Integer                      isAbandonActivity;       // 是否放弃活动

    private Integer                      seriousSafetyStatus;     // 严重安全故障状态

    private Integer                      isTimeoutSubmit;         // 是否超时上报

    private Integer                      inBindingStatus;         // 进厂是微信绑定状况

    private Integer                      outBindingStatus;        // 出厂时微信绑定状况
    private Date                         reportBIDatetime;        // bi接口上报时间
    private String customerCode;
    private String customerName;

    
    
    public String getCustomerName() {
        return customerName;
    }


    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    private List<ListPtDmsOrderPriceDTO> partSendPrice;

    public Date getReportBIDatetime() {
        return reportBIDatetime;
    }

    public List<ListPtDmsOrderPriceDTO> getPartSendPrice() {
        return partSendPrice;
    }

    public void setPartSendPrice(List<ListPtDmsOrderPriceDTO> partSendPrice) {
        this.partSendPrice = partSendPrice;
    }

    public void setReportBIDatetime(Date reportBIDatetime) {
        this.reportBIDatetime = reportBIDatetime;
    }

    public Integer getIsTimeoutSubmit() {
        return isTimeoutSubmit;
    }

    public void setIsTimeoutSubmit(Integer isTimeoutSubmit) {
        this.isTimeoutSubmit = isTimeoutSubmit;
    }

    public Integer getSeriousSafetyStatus() {
        return seriousSafetyStatus;
    }

    public void setSeriousSafetyStatus(Integer seriousSafetyStatus) {
        this.seriousSafetyStatus = seriousSafetyStatus;
    }

    public Integer getSchemeStatus() {
        return schemeStatus;
    }

    public void setSchemeStatus(Integer schemeStatus) {
        this.schemeStatus = schemeStatus;
    }

    public Integer getIsAbandonActivity() {
        return isAbandonActivity;
    }

    public void setIsAbandonActivity(Integer isAbandonActivity) {
        this.isAbandonActivity = isAbandonActivity;
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

    public Date getPrintCarTime() {
        return printCarTime;
    }

    public void setPrintCarTime(Date printCarTime) {
        this.printCarTime = printCarTime;
    }

    public Integer getWorkshopStatus() {
        return workshopStatus;
    }

    public void setWorkshopStatus(Integer workshopStatus) {
        this.workshopStatus = workshopStatus;
    }

    public Integer getIsTripleGuarantee() {
        return isTripleGuarantee;
    }

    public void setIsTripleGuarantee(Integer isTripleGuarantee) {
        this.isTripleGuarantee = isTripleGuarantee;
    }

    public Integer getIsTripleGuaranteeBef() {
        return isTripleGuaranteeBef;
    }

    public void setIsTripleGuaranteeBef(Integer isTripleGuaranteeBef) {
        this.isTripleGuaranteeBef = isTripleGuaranteeBef;
    }

    public String getEworkshopRemark() {
        return eworkshopRemark;
    }

    public void setEworkshopRemark(String eworkshopRemark) {
        this.eworkshopRemark = eworkshopRemark;
    }

    public Integer getIsQs() {
        return isQs;
    }

    public void setIsQs(Integer isQs) {
        this.isQs = isQs;
    }

    public Integer getIsPurchaseMaintenance() {
        return isPurchaseMaintenance;
    }

    public void setIsPurchaseMaintenance(Integer isPurchaseMaintenance) {
        this.isPurchaseMaintenance = isPurchaseMaintenance;
    }

    public Integer getRoClaimStatus() {
        return roClaimStatus;
    }

    public void setRoClaimStatus(Integer roClaimStatus) {
        this.roClaimStatus = roClaimStatus;
    }

    public Date getTroubleOccurDate() {
        return troubleOccurDate;
    }

    public void setTroubleOccurDate(Date troubleOccurDate) {
        this.troubleOccurDate = troubleOccurDate;
    }

    public Double getMemActiTotalAmount() {
        return memActiTotalAmount;
    }

    public void setMemActiTotalAmount(Double memActiTotalAmount) {
        this.memActiTotalAmount = memActiTotalAmount;
    }

    public String getLabourPositionCode() {
        return labourPositionCode;
    }

    public void setLabourPositionCode(String labourPositionCode) {
        this.labourPositionCode = labourPositionCode;
    }

    public String getReceptionNo() {
        return receptionNo;
    }

    public void setReceptionNo(String receptionNo) {
        this.receptionNo = receptionNo;
    }

    public Date getPrintSendTime() {
        return printSendTime;
    }

    public void setPrintSendTime(Date printSendTime) {
        this.printSendTime = printSendTime;
    }

    public String getOccurInsuranceNo() {
        return occurInsuranceNo;
    }

    public void setOccurInsuranceNo(String occurInsuranceNo) {
        this.occurInsuranceNo = occurInsuranceNo;
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

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Date getCaseClosedDate() {
        return caseClosedDate;
    }

    public void setCaseClosedDate(Date caseClosedDate) {
        this.caseClosedDate = caseClosedDate;
    }

    public Date getSettleColDate() {
        return settleColDate;
    }

    public void setSettleColDate(Date settleColDate) {
        this.settleColDate = settleColDate;
    }

    public Double getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(Double settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public Double getDsAmount() {
        return dsAmount;
    }

    public void setDsAmount(Double dsAmount) {
        this.dsAmount = dsAmount;
    }

    public Double getDsFactBalance() {
        return dsFactBalance;
    }

    public void setDsFactBalance(Double dsFactBalance) {
        this.dsFactBalance = dsFactBalance;
    }

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getSettlementRemark() {
        return settlementRemark;
    }

    public void setSettlementRemark(String settlementRemark) {
        this.settlementRemark = settlementRemark;
    }

    public Integer getIsOldPart() {
        return isOldPart;
    }

    public void setIsOldPart(Integer isOldPart) {
        this.isOldPart = isOldPart;
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

    public Integer getInsuranceOver() {
        return insuranceOver;
    }

    public void setInsuranceOver(Integer insuranceOver) {
        this.insuranceOver = insuranceOver;
    }

    public Integer getNotIntegral() {
        return notIntegral;
    }

    public void setNotIntegral(Integer notIntegral) {
        this.notIntegral = notIntegral;
    }

    public Integer getIsSystemQuote() {
        return isSystemQuote;
    }

    public void setIsSystemQuote(Integer isSystemQuote) {
        this.isSystemQuote = isSystemQuote;
    }

    public String getRoTroubleDesc() {
        return roTroubleDesc;
    }

    public void setRoTroubleDesc(String roTroubleDesc) {
        this.roTroubleDesc = roTroubleDesc;
    }

    public Integer getNotEnterWorkshop() {
        return notEnterWorkshop;
    }

    public void setNotEnterWorkshop(Integer notEnterWorkshop) {
        this.notEnterWorkshop = notEnterWorkshop;
    }

    public Integer getCheckedEnd() {
        return checkedEnd;
    }

    public void setCheckedEnd(Integer checkedEnd) {
        this.checkedEnd = checkedEnd;
    }

    public Integer getCustomerPreCheck() {
        return customerPreCheck;
    }

    public void setCustomerPreCheck(Integer customerPreCheck) {
        this.customerPreCheck = customerPreCheck;
    }

    public Integer getExplainedBalanceAccounts() {
        return explainedBalanceAccounts;
    }

    public void setExplainedBalanceAccounts(Integer explainedBalanceAccounts) {
        this.explainedBalanceAccounts = explainedBalanceAccounts;
    }

    public Integer getQuoteEndAccurate() {
        return quoteEndAccurate;
    }

    public void setQuoteEndAccurate(Integer quoteEndAccurate) {
        this.quoteEndAccurate = quoteEndAccurate;
    }

    public void setAddItemAmount(Double addItemAmount) {
        this.addItemAmount = addItemAmount;
    }

    public Double getAddItemAmount() {
        return this.addItemAmount;
    }

    public void setInsurationNo(String insurationNo) {
        this.insurationNo = insurationNo;
    }

    public String getInsurationNo() {
        return this.insurationNo;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicense() {
        return this.license;
    }

    public void setTestDriver(String testDriver) {
        this.testDriver = testDriver;
    }

    public String getTestDriver() {
        return this.testDriver;
    }

    public void setBookingOrderNo(String bookingOrderNo) {
        this.bookingOrderNo = bookingOrderNo;
    }

    public String getBookingOrderNo() {
        return this.bookingOrderNo;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public void setRoChargeType(Integer roChargeType) {
        this.roChargeType = roChargeType;
    }

    public Integer getRoChargeType() {
        return this.roChargeType;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    public String getRoNo() {
        return this.roNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo;
    }

    public String getOwnerNo() {
        return this.ownerNo;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setInsurationCode(String insurationCode) {
        this.insurationCode = insurationCode;
    }

    public String getInsurationCode() {
        return this.insurationCode;
    }

    public void setPrintRoTime(Date printRoTime) {
        this.printRoTime = printRoTime;
    }

    public Date getPrintRoTime() {
        return this.printRoTime;
    }

    public void setFinishUser(String finishUser) {
        this.finishUser = finishUser;
    }

    public String getFinishUser() {
        return this.finishUser;
    }

    public void setNoTraceReason(String noTraceReason) {
        this.noTraceReason = noTraceReason;
    }

    public String getNoTraceReason() {
        return this.noTraceReason;
    }

    public void setVehicleTopDesc(Integer vehicleTopDesc) {
        this.vehicleTopDesc = vehicleTopDesc;
    }

    public Integer getVehicleTopDesc() {
        return this.vehicleTopDesc;
    }

    public void setLabourAmount(Double labourAmount) {
        this.labourAmount = labourAmount;
    }

    public Double getLabourAmount() {
        return this.labourAmount;
    }

    public void setChiefTechnician(String chiefTechnician) {
        this.chiefTechnician = chiefTechnician;
    }

    public String getChiefTechnician() {
        return this.chiefTechnician;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getEngineNo() {
        return this.engineNo;
    }

    public void setIsMaintain(Integer isMaintain) {
        this.isMaintain = isMaintain;
    }

    public Integer getIsMaintain() {
        return this.isMaintain;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return this.vin;
    }

    public void setOverItemAmount(Double overItemAmount) {
        this.overItemAmount = overItemAmount;
    }

    public Double getOverItemAmount() {
        return this.overItemAmount;
    }

    public void setOilRemain(Integer oilRemain) {
        this.oilRemain = oilRemain;
    }

    public Integer getOilRemain() {
        return this.oilRemain;
    }

    public void setTotalMileage(Double totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Double getTotalMileage() {
        return this.totalMileage;
    }

    public void setFirstEstimateAmount(Double firstEstimateAmount) {
        this.firstEstimateAmount = firstEstimateAmount;
    }

    public Double getFirstEstimateAmount() {
        return this.firstEstimateAmount;
    }

    public void setRecommendCustomerName(String recommendCustomerName) {
        this.recommendCustomerName = recommendCustomerName;
    }

    public String getRecommendCustomerName() {
        return this.recommendCustomerName;
    }

    public void setPrintRpTime(Date printRpTime) {
        this.printRpTime = printRpTime;
    }

    public Date getPrintRpTime() {
        return this.printRpTime;
    }

    public void setCompleteTag(Integer completeTag) {
        this.completeTag = completeTag;
    }

    public Integer getCompleteTag() {
        return this.completeTag;
    }

    public void setModifyNum(Integer modifyNum) {
        this.modifyNum = modifyNum;
    }

    public Integer getModifyNum() {
        return this.modifyNum;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setNeedRoadTest(Integer needRoadTest) {
        this.needRoadTest = needRoadTest;
    }

    public Integer getNeedRoadTest() {
        return this.needRoadTest;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberNo() {
        return this.memberNo;
    }

    public void setDeliveryTag(Integer deliveryTag) {
        this.deliveryTag = deliveryTag;
    }

    public Integer getDeliveryTag() {
        return this.deliveryTag;
    }

    public void setWaitPartTag(Integer waitPartTag) {
        this.waitPartTag = waitPartTag;
    }

    public Integer getWaitPartTag() {
        return this.waitPartTag;
    }

    public void setRecommendEmpName(String recommendEmpName) {
        this.recommendEmpName = recommendEmpName;
    }

    public String getRecommendEmpName() {
        return this.recommendEmpName;
    }

    public void setDelivererMobile(String delivererMobile) {
        this.delivererMobile = delivererMobile;
    }

    public String getDelivererMobile() {
        return this.delivererMobile;
    }

    public void setRoStatus(Integer roStatus) {
        this.roStatus = roStatus;
    }

    public Integer getRoStatus() {
        return this.roStatus;
    }

    public void setInMileage(Double inMileage) {
        this.inMileage = inMileage;
    }

    public Double getInMileage() {
        return this.inMileage;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setDeliveryUser(String deliveryUser) {
        this.deliveryUser = deliveryUser;
    }

    public String getDeliveryUser() {
        return this.deliveryUser;
    }

    public void setRoCreateDate(Date roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    public Date getRoCreateDate() {
        return this.roCreateDate;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeries() {
        return this.series;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Double getBalanceAmount() {
        return this.balanceAmount;
    }

    public void setIsChangeOdograph(Integer isChangeOdograph) {
        this.isChangeOdograph = isChangeOdograph;
    }

    public Integer getIsChangeOdograph() {
        return this.isChangeOdograph;
    }

    public void setRoType(Integer roType) {
        this.roType = roType;
    }

    public Integer getRoType() {
        return this.roType;
    }

    public void setChangeMileage(Double changeMileage) {
        this.changeMileage = changeMileage;
    }

    public Double getChangeMileage() {
        return this.changeMileage;
    }

    public void setServiceAdvisorAss(String serviceAdvisorAss) {
        this.serviceAdvisorAss = serviceAdvisorAss;
    }

    public String getServiceAdvisorAss() {
        return this.serviceAdvisorAss;
    }

    public void setDKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Integer getDKey() {
        return this.dKey;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getReceiveAmount() {
        return this.receiveAmount;
    }

    public void setPrimaryRoNo(String primaryRoNo) {
        this.primaryRoNo = primaryRoNo;
    }

    public String getPrimaryRoNo() {
        return this.primaryRoNo;
    }

    public void setDelivererGender(Integer delivererGender) {
        this.delivererGender = delivererGender;
    }

    public Integer getDelivererGender() {
        return this.delivererGender;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public void setForBalanceTime(Date forBalanceTime) {
        this.forBalanceTime = forBalanceTime;
    }

    public Date getForBalanceTime() {
        return this.forBalanceTime;
    }

    public void setWaitInfoTag(Integer waitInfoTag) {
        this.waitInfoTag = waitInfoTag;
    }

    public Integer getWaitInfoTag() {
        return this.waitInfoTag;
    }

    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    public String getLockUser() {
        return this.lockUser;
    }

    public void setIsCloseRo(Integer isCloseRo) {
        this.isCloseRo = isCloseRo;
    }

    public Integer getIsCloseRo() {
        return this.isCloseRo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSequenceNo() {
        return this.sequenceNo;
    }

    public void setDelivererPhone(String delivererPhone) {
        this.delivererPhone = delivererPhone;
    }

    public String getDelivererPhone() {
        return this.delivererPhone;
    }

    public void setOwnerProperty(Integer ownerProperty) {
        this.ownerProperty = ownerProperty;
    }

    public Integer getOwnerProperty() {
        return this.ownerProperty;
    }

    public void setSalesPartAmount(Double salesPartAmount) {
        this.salesPartAmount = salesPartAmount;
    }

    public Double getSalesPartAmount() {
        return this.salesPartAmount;
    }

    public void setIsTrace(Integer isTrace) {
        this.isTrace = isTrace;
    }

    public Integer getIsTrace() {
        return this.isTrace;
    }

    public void setLabourPrice(Float labourPrice) {
        this.labourPrice = labourPrice;
    }

    public Float getLabourPrice() {
        return this.labourPrice;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    public void setTraceTag(Integer traceTag) {
        this.traceTag = traceTag;
    }

    public Integer getTraceTag() {
        return this.traceTag;
    }

    public void setTraceTime(Integer traceTime) {
        this.traceTime = traceTime;
    }

    public Integer getTraceTime() {
        return this.traceTime;
    }

    public void setTotalChangeMileage(Double totalChangeMileage) {
        this.totalChangeMileage = totalChangeMileage;
    }

    public Double getTotalChangeMileage() {
        return this.totalChangeMileage;
    }

    public void setIsCustomerInAsc(Integer isCustomerInAsc) {
        this.isCustomerInAsc = isCustomerInAsc;
    }

    public Integer getIsCustomerInAsc() {
        return this.isCustomerInAsc;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }

    public String getSoNo() {
        return this.soNo;
    }

    public void setRepairPartAmount(Double repairPartAmount) {
        this.repairPartAmount = repairPartAmount;
    }

    public Double getRepairPartAmount() {
        return this.repairPartAmount;
    }

    public void setSalesPartNo(String salesPartNo) {
        this.salesPartNo = salesPartNo;
    }

    public String getSalesPartNo() {
        return this.salesPartNo;
    }

    public void setInReason(Integer inReason) {
        this.inReason = inReason;
    }

    public Integer getInReason() {
        return this.inReason;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getCompleteTime() {
        return this.completeTime;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setDerateAmount(Double derateAmount) {
        this.derateAmount = derateAmount;
    }

    public Double getDerateAmount() {
        return this.derateAmount;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return this.deliveryDate;
    }

    public void setEstimateNo(String estimateNo) {
        this.estimateNo = estimateNo;
    }

    public String getEstimateNo() {
        return this.estimateNo;
    }

    public void setEndTimeSupposed(Date endTimeSupposed) {
        this.endTimeSupposed = endTimeSupposed;
    }

    public Date getEndTimeSupposed() {
        return this.endTimeSupposed;
    }

    public void setRepairAmount(Double repairAmount) {
        this.repairAmount = repairAmount;
    }

    public Double getRepairAmount() {
        return this.repairAmount;
    }

    public void setCustomerDesc(String customerDesc) {
        this.customerDesc = customerDesc;
    }

    public String getCustomerDesc() {
        return this.customerDesc;
    }

    public void setOutMileage(Double outMileage) {
        this.outMileage = outMileage;
    }

    public Double getOutMileage() {
        return this.outMileage;
    }

    public void setRoSplitStatus(Integer roSplitStatus) {
        this.roSplitStatus = roSplitStatus;
    }

    public Integer getRoSplitStatus() {
        return this.roSplitStatus;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    public String getDeliverer() {
        return this.deliverer;
    }

    public void setIsSeasonCheck(Integer isSeasonCheck) {
        this.isSeasonCheck = isSeasonCheck;
    }

    public Integer getIsSeasonCheck() {
        return this.isSeasonCheck;
    }

    public void setRepairTypeCode(String repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }

    public String getRepairTypeCode() {
        return this.repairTypeCode;
    }

    public void setOtherRepairType(String otherRepairType) {
        this.otherRepairType = otherRepairType;
    }

    public String getOtherRepairType() {
        return this.otherRepairType;
    }

    public void setEstimateAmount(Double estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    public Double getEstimateAmount() {
        return this.estimateAmount;
    }

    public void setIsActivity(Integer isActivity) {
        this.isActivity = isActivity;
    }

    public Integer getIsActivity() {
        return this.isActivity;
    }

    public void setSubObbAmount(Double subObbAmount) {
        this.subObbAmount = subObbAmount;
    }

    public Double getSubObbAmount() {
        return this.subObbAmount;
    }

    public void setIsWash(Integer isWash) {
        this.isWash = isWash;
    }

    public Integer getIsWash() {
        return this.isWash;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark1() {
        return this.remark1;
    }

    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor;
    }

    public String getServiceAdvisor() {
        return this.serviceAdvisor;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public void setEstimateBeginTime(Date estimateBeginTime) {
        this.estimateBeginTime = estimateBeginTime;
    }

    public Date getEstimateBeginTime() {
        return this.estimateBeginTime;
    }

    public void setIsTakePartOld(Integer isTakePartOld) {
        this.isTakePartOld = isTakePartOld;
    }

    public Integer getIsTakePartOld() {
        return this.isTakePartOld;
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

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public Integer getIsPrint() {
        return isPrint;
    }

    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
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

}
