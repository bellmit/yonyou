
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairOrderDTO.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.commonAS.domains.DTO.order;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.License;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
 * 工单信息
 * @author ZhengHe
 * @date 2016年8月10日
 */

public class RepairOrderDTO {
    @Required 
    private Long roId;

    private Integer salesPartId;

    private String dealerCode;
    
    @Required 
    private String roNo;

    private String primaryRoNo;

    @Required
    private Integer roType;

    @Required
    private Integer roStatus;

    @Required
    private String serviceAdvisorAss;

    @Required
    private String repairTypeCode;
    //新增维修类型名称
    private String repairTypeName;

    @Required
    @Digits(integer=8,fraction=2)
    private Double labourPrice;
    
    //新增工时单价code
    private String labourPriceCode;

    private String discountModeCode;

    private String customerDesc;

    private String roTroubleDesc;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    @Required
    private Date roCreateDate;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date estimateBeginTime;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    @Required
    private Date endTimeSupposed;

    private Integer isWash;

    private Integer isSeasonCheck;

    private Integer isTakePartOld;

    private Integer isChangeOdograph;

    @Digits(integer=10,fraction=2)
    private Double changeMileage;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date lastMaintenanceDate;

    @Digits(integer=10,fraction=2)
    private Double lastMaintenanceMileage;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date nextMaintenanceDate;

    @Digits(integer=10,fraction=2)
    private Double nextMaintenanceMileage;

    private Integer insuranceId;

    private String occurInsuranceNo;

    private String insurationNo;

    @Required
    private Long ownerId;

    private String ownerName;

    private Integer ownerProperty;

    private String deliverer;

    private Integer delivererGender;

    private String delivererPhone;

    private String delivererMobile;

    @Required
    private Long vehicleId;

    @License
    private String license;

    @VIN
    private String vin;

    private String engineNo;

    private String brandCode;

    private String seriesCode;

    private String modelCode;

    private Date salesDate;

    private String serviceAdvisor;

    @Required
    @Digits(integer=10,fraction=2)
    private Double inMileage;

    @Digits(integer=10,fraction=2)
    private Double outMileage;

    @Digits(integer=10,fraction=2)
    private Double totalMileage;

    private String oilLevel;

    private Integer waitInfoTag;

    private Integer waitPartTag;

    private String testDriver;

    private String finishUser;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date completeTime;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date forBalanceTime;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date firstBalanceDate;

    @Digits(integer=12,fraction=2)
    private Double labourAmount;

    @Digits(integer=12,fraction=2)
    private Double repairPartAmount;

    @Digits(integer=12,fraction=2)
    private Double salesPartAmount;

    @Digits(integer=12,fraction=2)
    private Double addItemAmount;

    @Digits(integer=10,fraction=2)
    private Double overItemAmount;

    @Digits(integer=12,fraction=2)
    private Double repairAmount;

    @Digits(integer=12,fraction=2)
    private Double amount;

    @Digits(integer=12,fraction=2)
    private Double disLabourAmount;

    @Digits(integer=12,fraction=2)
    private Double disRepairPartAmount;

    @Digits(integer=12,fraction=2)
    private Double disSalesPartAmount;

    @Digits(integer=12,fraction=2)
    private Double disAddItemAmount;

    @Digits(integer=10,fraction=2)
    private Double disOverItemAmount;
    
    @Digits(integer=12,fraction=2)
    private Double disRepairAmount;

    @Digits(integer=12,fraction=2)
    private Double disAmount;

    @Digits(integer=12,fraction=2)
    private Double balanceAmount;

    @Digits(integer=12,fraction=2)
    private Double receiveAmount;

    @Digits(integer=12,fraction=2)
    private Double subObbAmount;

    @Digits(integer=12,fraction=2)
    private Double derateAmount;

    private Integer deliveryTag;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date deliveryDate;

    private String deliveryUser;

    private String remarkMain;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date printRoTime;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date printCarTime;

    private Integer activityTraceTag;

    private Integer isActivity;

    private Integer isFirstMaintain;

    private Integer isMaintain;

    @Digits(integer=12,fraction=2)
    private Double firstEstimateAmount;

    private Integer traceTag;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date printRpTime;

    private Long orderStatus; //出库状态

    private Date orderDateFrom;  //开单日期（起始日期）

    private Date orderDateTo;  //开单日期（结束日期）
    
    
    @Size(min=1)
    private List<RoPartDTO> roPartDtos;
    
    private String receiver;   //领料人

    private List<RoAddItemDTO> addRoItemList;
    
    @Size(min=1)
    private List<RoRepairProDTO> labourList;
    
    private String delRopartIdArr; //删除的维修配件的id
    private String delRepairProArr; //删除的维修项目id
    
    
    private Long boId;   //预约单id
    
    private Integer dKey;//
    
    
    
    
    
    public Integer getdKey() {
        return dKey;
    }



    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }



    public Long getBoId() {
        return boId;
    }


    
    public void setBoId(Long boId) {
        this.boId = boId;
    }


    public String getDelRopartIdArr() {
        return delRopartIdArr;
    }

    
    public void setDelRopartIdArr(String delRopartIdArr) {
        this.delRopartIdArr = delRopartIdArr;
    }

    public Long getRoId() {
        return roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
    }

    public Integer getSalesPartId() {
        return salesPartId;
    }

    public void setSalesPartId(Integer salesPartId) {
        this.salesPartId = salesPartId;
    }

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

    public String getPrimaryRoNo() {
        return primaryRoNo;
    }

    public void setPrimaryRoNo(String primaryRoNo) {
        this.primaryRoNo = primaryRoNo;
    }

    public Integer getRoType() {
        return roType;
    }

    public void setRoType(Integer roType) {
        this.roType = roType;
    }

    public Integer getRoStatus() {
        return roStatus;
    }

    public void setRoStatus(Integer roStatus) {
        this.roStatus = roStatus;
    }

    public String getServiceAdvisorAss() {
        return serviceAdvisorAss;
    }

    public void setServiceAdvisorAss(String serviceAdvisorAss) {
        this.serviceAdvisorAss = serviceAdvisorAss;
    }

    public String getRepairTypeCode() {
        return repairTypeCode;
    }

    public void setRepairTypeCode(String repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
    }

    public Double getLabourPrice() {
        return labourPrice;
    }

    public void setLabourPrice(Double labourPrice) {
        this.labourPrice = labourPrice;
    }

    public String getDiscountModeCode() {
        return discountModeCode;
    }

    public void setDiscountModeCode(String discountModeCode) {
        this.discountModeCode = discountModeCode;
    }

    public String getCustomerDesc() {
        return customerDesc;
    }

    public void setCustomerDesc(String customerDesc) {
        this.customerDesc = customerDesc;
    }

    public String getRoTroubleDesc() {
        return roTroubleDesc;
    }

    public void setRoTroubleDesc(String roTroubleDesc) {
        this.roTroubleDesc = roTroubleDesc;
    }

    public Date getRoCreateDate() {
        return roCreateDate;
    }

    public void setRoCreateDate(Date roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    public Date getEstimateBeginTime() {
        return estimateBeginTime;
    }

    public void setEstimateBeginTime(Date estimateBeginTime) {
        this.estimateBeginTime = estimateBeginTime;
    }

    public Date getEndTimeSupposed() {
        return endTimeSupposed;
    }

    public void setEndTimeSupposed(Date endTimeSupposed) {
        this.endTimeSupposed = endTimeSupposed;
    }

    public Integer getIsWash() {
        return isWash;
    }

    public void setIsWash(Integer isWash) {
        this.isWash = isWash;
    }

    public Integer getIsSeasonCheck() {
        return isSeasonCheck;
    }

    public void setIsSeasonCheck(Integer isSeasonCheck) {
        this.isSeasonCheck = isSeasonCheck;
    }

    public Integer getIsTakePartOld() {
        return isTakePartOld;
    }

    public void setIsTakePartOld(Integer isTakePartOld) {
        this.isTakePartOld = isTakePartOld;
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

    public Date getNextMaintenanceDate() {
        return nextMaintenanceDate;
    }

    public void setNextMaintenanceDate(Date nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Double getNextMaintenanceMileage() {
        return nextMaintenanceMileage;
    }

    public void setNextMaintenanceMileage(Double nextMaintenanceMileage) {
        this.nextMaintenanceMileage = nextMaintenanceMileage;
    }

    public Integer getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(Integer insuranceId) {
        this.insuranceId = insuranceId;
    }

    public String getOccurInsuranceNo() {
        return occurInsuranceNo;
    }

    public void setOccurInsuranceNo(String occurInsuranceNo) {
        this.occurInsuranceNo = occurInsuranceNo;
    }

    public String getInsurationNo() {
        return insurationNo;
    }

    public void setInsurationNo(String insurationNo) {
        this.insurationNo = insurationNo;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getServiceAdvisor() {
        return serviceAdvisor;
    }

    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor;
    }

    public Double getInMileage() {
        return inMileage;
    }

    public void setInMileage(Double inMileage) {
        this.inMileage = inMileage;
    }

    public Double getOutMileage() {
        return outMileage;
    }

    public void setOutMileage(Double outMileage) {
        this.outMileage = outMileage;
    }

    public Double getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Double totalMileage) {
        this.totalMileage = totalMileage;
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

    public String getTestDriver() {
        return testDriver;
    }

    public void setTestDriver(String testDriver) {
        this.testDriver = testDriver;
    }

    public String getFinishUser() {
        return finishUser;
    }

    public void setFinishUser(String finishUser) {
        this.finishUser = finishUser;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getForBalanceTime() {
        return forBalanceTime;
    }

    public void setForBalanceTime(Date forBalanceTime) {
        this.forBalanceTime = forBalanceTime;
    }

    public Date getFirstBalanceDate() {
        return firstBalanceDate;
    }

    public void setFirstBalanceDate(Date firstBalanceDate) {
        this.firstBalanceDate = firstBalanceDate;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDisLabourAmount() {
        return disLabourAmount;
    }

    public void setDisLabourAmount(Double disLabourAmount) {
        this.disLabourAmount = disLabourAmount;
    }

    public Double getDisRepairPartAmount() {
        return disRepairPartAmount;
    }

    public void setDisRepairPartAmount(Double disRepairPartAmount) {
        this.disRepairPartAmount = disRepairPartAmount;
    }

    public Double getDisSalesPartAmount() {
        return disSalesPartAmount;
    }

    public void setDisSalesPartAmount(Double disSalesPartAmount) {
        this.disSalesPartAmount = disSalesPartAmount;
    }

    public Double getDisAddItemAmount() {
        return disAddItemAmount;
    }

    public void setDisAddItemAmount(Double disAddItemAmount) {
        this.disAddItemAmount = disAddItemAmount;
    }

    public Double getDisOverItemAmount() {
        return disOverItemAmount;
    }

    public void setDisOverItemAmount(Double disOverItemAmount) {
        this.disOverItemAmount = disOverItemAmount;
    }

    public Double getDisRepairAmount() {
        return disRepairAmount;
    }

    public void setDisRepairAmount(Double disRepairAmount) {
        this.disRepairAmount = disRepairAmount;
    }

    public Double getDisAmount() {
        return disAmount;
    }

    public void setDisAmount(Double disAmount) {
        this.disAmount = disAmount;
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

    public String getDeliveryUser() {
        return deliveryUser;
    }

    public void setDeliveryUser(String deliveryUser) {
        this.deliveryUser = deliveryUser;
    }

    public Date getPrintRoTime() {
        return printRoTime;
    }

    public void setPrintRoTime(Date printRoTime) {
        this.printRoTime = printRoTime;
    }

    public Date getPrintCarTime() {
        return printCarTime;
    }

    public void setPrintCarTime(Date printCarTime) {
        this.printCarTime = printCarTime;
    }

    public Integer getActivityTraceTag() {
        return activityTraceTag;
    }

    public void setActivityTraceTag(Integer activityTraceTag) {
        this.activityTraceTag = activityTraceTag;
    }

    public Integer getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(Integer isActivity) {
        this.isActivity = isActivity;
    }

    public Integer getIsFirstMaintain() {
        return isFirstMaintain;
    }

    public void setIsFirstMaintain(Integer isFirstMaintain) {
        this.isFirstMaintain = isFirstMaintain;
    }

    public Integer getIsMaintain() {
        return isMaintain;
    }

    public void setIsMaintain(Integer isMaintain) {
        this.isMaintain = isMaintain;
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

    public Date getPrintRpTime() {
        return printRpTime;
    }

    public void setPrintRpTime(Date printRpTime) {
        this.printRpTime = printRpTime;
    }

    public Date getOrderDateFrom() {
        return orderDateFrom;
    }


    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }


    public Date getOrderDateTo() {
        return orderDateTo;
    }


    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }


    public Long getOrderStatus() {
        return orderStatus;
    }


    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }

    
    public List<RoPartDTO> getRoPartDtos() {
        return roPartDtos;
    }

    
    public void setRoPartDtos(List<RoPartDTO> roPartDtos) {
        this.roPartDtos = roPartDtos;
    }

    
    public String getReceiver() {
        return receiver;
    }

    
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    
    public List<RoAddItemDTO> getAddRoItemList() {
        return addRoItemList;
    }

    
    public void setAddRoItemList(List<RoAddItemDTO> addRoItemList) {
        this.addRoItemList = addRoItemList;
    }

    public String getOilLevel() {
        return oilLevel;
    }

    public void setOilLevel(String oilLevel) {
        this.oilLevel = oilLevel;
    }

    public String getLabourPriceCode() {
        return labourPriceCode;
    }

    public void setLabourPriceCode(String labourPriceCode) {
        this.labourPriceCode = labourPriceCode;
    }

    public String getRepairTypeName() {
        return repairTypeName;
    }

    public void setRepairTypeName(String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }

    
    public List<RoRepairProDTO> getLabourList() {
        return labourList;
    }

    
    public void setLabourList(List<RoRepairProDTO> labourList) {
        this.labourList = labourList;
    }


    public String getRemarkMain() {
        return remarkMain;
    }


    public void setRemarkMain(String remarkMain) {
        this.remarkMain = remarkMain;
    }


    
    public String getDelRepairProArr() {
        return delRepairProArr;
    }


    
    public void setDelRepairProArr(String delRepairProArr) {
        this.delRepairProArr = delRepairProArr;
    }


}