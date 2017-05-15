
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtSalesPromotionPlanDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2016年12月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月22日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;

/**
* TODO description
* @author LiGaoqi
* @date 2016年12月22日
*/

public class TtSalesPromotionPlanDTO {
    private String    noList;//隐藏字段 ，用于再分配
    
    private String    auditList;//隐藏字段 ，用于审核
    
    public String getNoList() {
        return noList;
    }


    
    public void setNoList(String noList) {
        this.noList = noList;
    }
    

    
    public String getAuditList() {
        return auditList;
    }



    
    public void setAuditList(String auditList) {
        this.auditList = auditList;
    }


    private String dealerCode;
    private String customerNo;
    private String customerName;
    private String contactorName;
    private String phone;
    private String mobile;
    private String promContent;
    private String scene;
    private String auditingRemark;
    private String realContactorName;
    private String relatedActivity;
    private String realActivity;
    private List<String> visitAction;
    private List<String> realVisitAction;
    private String nextVisitAction;
    private String nextPromContent;
    private String lastScene;
    private String auditedBy;
    private String soldBy;
    
    private long itemId;
    private long intentId;
    private long taskId;
    private Integer priorGrade;
    private Integer nextGrade;
    private Integer promWay;
    private Integer createType;
    private Integer promResult;
    private Integer isAuditing;
  
    private Integer bookingCustomerType;
    private Integer realPromWay;
    private long drivePlanId;
    private long receptionId;
    private String ownedBy;
    private Integer dKey;
    private Integer ver;
    private Integer isSuccessBooking;
    private Integer buyCycleP;
    private Integer buyCycleS;
    private Integer buyCycleI;
    private Integer buyCycleX;
    private Integer buyCycleT;
    private Integer buyCycleA;
    private Integer buyCycleC;
    private Integer buyCycleL;
    private Integer buyCycleO;
    private Integer buyCycleD;
    private Integer isPadPlan;
    private Integer nextPadApproachPlan;
    private Integer isBigCustomerPlan;
    private Integer isSpadCreate;
    private long spadCustomerId;
    private Date scheduleDate;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date actionDate;
   /* @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)*/
    private Date nextPromDate;

    private Date bookingDate;

    private Date nextBookingDate;

    private Date ddcnUpdateDate;

    private Date spadUpdateDate;
    
    //试乘试驾页面新增字段
    private String isToShop;
	private Date timeToShop;
    private String isTestDrive;
    private String testDriveRemark;
    
    public String getIsToShop() {
		return isToShop;
	}



	public void setIsToShop(String isToShop) {
		this.isToShop = isToShop;
	}



	public Date getTimeToShop() {
		return timeToShop;
	}



	public void setTimeToShop(Date timeToShop) {
		this.timeToShop = timeToShop;
	}



	public String getIsTestDrive() {
		return isTestDrive;
	}



	public void setIsTestDrive(String isTestDrive) {
		this.isTestDrive = isTestDrive;
	}



	public String getTestDriveRemark() {
		return testDriveRemark;
	}



	public void setTestDriveRemark(String testDriveRemark) {
		this.testDriveRemark = testDriveRemark;
	}



    
    
    private List<TtBigCustomerVisitingIntentDTO> bigCustomerIntentList;//大客户意向
    
    public List<TtBigCustomerVisitingIntentDTO> getBigCustomerIntentList() {
        return bigCustomerIntentList;
    }

    
    public void setBigCustomerIntentList(List<TtBigCustomerVisitingIntentDTO> bigCustomerIntentList) {
        this.bigCustomerIntentList = bigCustomerIntentList;
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
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
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getPromContent() {
        return promContent;
    }
    
    public void setPromContent(String promContent) {
        this.promContent = promContent;
    }
    
    public String getScene() {
        return scene;
    }
    
    public void setScene(String scene) {
        this.scene = scene;
    }
    
    public String getAuditingRemark() {
        return auditingRemark;
    }
    
    public void setAuditingRemark(String auditingRemark) {
        this.auditingRemark = auditingRemark;
    }
    
    public String getRealContactorName() {
        return realContactorName;
    }
    
    public void setRealContactorName(String realContactorName) {
        this.realContactorName = realContactorName;
    }
    
    public String getRelatedActivity() {
        return relatedActivity;
    }
    
    public void setRelatedActivity(String relatedActivity) {
        this.relatedActivity = relatedActivity;
    }
    
    public String getRealActivity() {
        return realActivity;
    }
    
    public void setRealActivity(String realActivity) {
        this.realActivity = realActivity;
    }
    

    
    
    public List<String> getVisitAction() {
        return visitAction;
    }


    
    public void setVisitAction(List<String> visitAction) {
        this.visitAction = visitAction;
    }


    
    public List<String> getRealVisitAction() {
        return realVisitAction;
    }


    
    public void setRealVisitAction(List<String> realVisitAction) {
        this.realVisitAction = realVisitAction;
    }


    public String getNextVisitAction() {
        return nextVisitAction;
    }
    
    public void setNextVisitAction(String nextVisitAction) {
        this.nextVisitAction = nextVisitAction;
    }
    
    public String getNextPromContent() {
        return nextPromContent;
    }
    
    public void setNextPromContent(String nextPromContent) {
        this.nextPromContent = nextPromContent;
    }
    
    public String getLastScene() {
        return lastScene;
    }
    
    public void setLastScene(String lastScene) {
        this.lastScene = lastScene;
    }
    
    public long getItemId() {
        return itemId;
    }
    
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    
    public long getIntentId() {
        return intentId;
    }
    
    public void setIntentId(long intentId) {
        this.intentId = intentId;
    }
    
    public long getTaskId() {
        return taskId;
    }
    
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    
 
    
    
    
    
    
    public Integer getPriorGrade() {
        return priorGrade;
    }


    public String getAuditedBy() {
        return auditedBy;
    }


    
    public void setAuditedBy(String auditedBy) {
        this.auditedBy = auditedBy;
    }


    
    public String getSoldBy() {
        return soldBy;
    }


    
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }


    
    public Integer getNextGrade() {
        return nextGrade;
    }


    
    public void setNextGrade(Integer nextGrade) {
        this.nextGrade = nextGrade;
    }


    
    public Integer getPromWay() {
        return promWay;
    }


    
    public void setPromWay(Integer promWay) {
        this.promWay = promWay;
    }


    
    public Integer getCreateType() {
        return createType;
    }


    
    public void setCreateType(Integer createType) {
        this.createType = createType;
    }


    
    public Integer getPromResult() {
        return promResult;
    }


    
    public void setPromResult(Integer promResult) {
        this.promResult = promResult;
    }


    
    public Integer getIsAuditing() {
        return isAuditing;
    }


    
    public void setIsAuditing(Integer isAuditing) {
        this.isAuditing = isAuditing;
    }


    
    public Integer getBookingCustomerType() {
        return bookingCustomerType;
    }


    
    public void setBookingCustomerType(Integer bookingCustomerType) {
        this.bookingCustomerType = bookingCustomerType;
    }


    
    public Integer getRealPromWay() {
        return realPromWay;
    }


    
    public void setRealPromWay(Integer realPromWay) {
        this.realPromWay = realPromWay;
    }


    
    public long getDrivePlanId() {
        return drivePlanId;
    }


    
    public void setDrivePlanId(long drivePlanId) {
        this.drivePlanId = drivePlanId;
    }


    
    public long getReceptionId() {
        return receptionId;
    }


    
    public void setReceptionId(long receptionId) {
        this.receptionId = receptionId;
    }


    
    public String getOwnedBy() {
        return ownedBy;
    }


    
    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
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


    
    public Integer getIsSuccessBooking() {
        return isSuccessBooking;
    }


    
    public void setIsSuccessBooking(Integer isSuccessBooking) {
        this.isSuccessBooking = isSuccessBooking;
    }


    
    public Integer getBuyCycleP() {
        return buyCycleP;
    }


    
    public void setBuyCycleP(Integer buyCycleP) {
        this.buyCycleP = buyCycleP;
    }


    
    public Integer getBuyCycleS() {
        return buyCycleS;
    }


    
    public void setBuyCycleS(Integer buyCycleS) {
        this.buyCycleS = buyCycleS;
    }


    
    public Integer getBuyCycleI() {
        return buyCycleI;
    }


    
    public void setBuyCycleI(Integer buyCycleI) {
        this.buyCycleI = buyCycleI;
    }


    
    public Integer getBuyCycleX() {
        return buyCycleX;
    }


    
    public void setBuyCycleX(Integer buyCycleX) {
        this.buyCycleX = buyCycleX;
    }


    
    public Integer getBuyCycleT() {
        return buyCycleT;
    }


    
    public void setBuyCycleT(Integer buyCycleT) {
        this.buyCycleT = buyCycleT;
    }


    
    public Integer getBuyCycleA() {
        return buyCycleA;
    }


    
    public void setBuyCycleA(Integer buyCycleA) {
        this.buyCycleA = buyCycleA;
    }


    
    public Integer getBuyCycleC() {
        return buyCycleC;
    }


    
    public void setBuyCycleC(Integer buyCycleC) {
        this.buyCycleC = buyCycleC;
    }


    
    public Integer getBuyCycleL() {
        return buyCycleL;
    }


    
    public void setBuyCycleL(Integer buyCycleL) {
        this.buyCycleL = buyCycleL;
    }


    
    public Integer getBuyCycleO() {
        return buyCycleO;
    }


    
    public void setBuyCycleO(Integer buyCycleO) {
        this.buyCycleO = buyCycleO;
    }


    
    public Integer getBuyCycleD() {
        return buyCycleD;
    }


    
    public void setBuyCycleD(Integer buyCycleD) {
        this.buyCycleD = buyCycleD;
    }


    
    public Integer getIsPadPlan() {
        return isPadPlan;
    }


    
    public void setIsPadPlan(Integer isPadPlan) {
        this.isPadPlan = isPadPlan;
    }


    
    public Integer getNextPadApproachPlan() {
        return nextPadApproachPlan;
    }


    
    public void setNextPadApproachPlan(Integer nextPadApproachPlan) {
        this.nextPadApproachPlan = nextPadApproachPlan;
    }


    
    public Integer getIsBigCustomerPlan() {
        return isBigCustomerPlan;
    }


    
    public void setIsBigCustomerPlan(Integer isBigCustomerPlan) {
        this.isBigCustomerPlan = isBigCustomerPlan;
    }


    
    public Integer getIsSpadCreate() {
        return isSpadCreate;
    }


    
    public void setIsSpadCreate(Integer isSpadCreate) {
        this.isSpadCreate = isSpadCreate;
    }


    
    public void setPriorGrade(Integer priorGrade) {
        this.priorGrade = priorGrade;
    }


    public long getSpadCustomerId() {
        return spadCustomerId;
    }
    
    public void setSpadCustomerId(long spadCustomerId) {
        this.spadCustomerId = spadCustomerId;
    }
    
    public Date getScheduleDate() {
        return scheduleDate;
    }
    
    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }
    
    public Date getActionDate() {
        return actionDate;
    }
    
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    public Date getNextPromDate() {
        return nextPromDate;
    }
    
    public void setNextPromDate(Date nextPromDate) {
        this.nextPromDate = nextPromDate;
    }
    
    public Date getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public Date getNextBookingDate() {
        return nextBookingDate;
    }
    
    public void setNextBookingDate(Date nextBookingDate) {
        this.nextBookingDate = nextBookingDate;
    }
    
    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }
    
    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
    public Date getSpadUpdateDate() {
        return spadUpdateDate;
    }
    
    public void setSpadUpdateDate(Date spadUpdateDate) {
        this.spadUpdateDate = spadUpdateDate;
    }
    
    

}
