
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesPromotionDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月11日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateSerializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 潜客跟进DTO
 * 
 * @author zhanshiwei
 * @date 2016年9月11日
 */

public class SalesPromotionDTO {

    private Integer promotionId;
    private String  promotionIds;
    private String  customerIds;

    public String getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    public String getPromotionIds() {
        return promotionIds;
    }

    public void setPromotionIds(String promotionIds) {
        this.promotionIds = promotionIds;
    }

    private String  dealerCode;

    private Long    potentialCustomerId;

    private Integer priorGrade;

    private Integer nextGrade;
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    private Date    scheduleDate;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    nextscheduleDate;

    public Date getNextscheduleDate() {
        return nextscheduleDate;
    }

    public void setNextscheduleDate(Date nextscheduleDate) {
        this.nextscheduleDate = nextscheduleDate;
    }

    private Integer isSuccessBooking;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    bookingDate;

    private Integer bookingIsArrive;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    actionDate;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    nextArriveDate;

    private String  consultant;

    private Integer promWay;
    @Length(max = 30)
    private String  taskName;
    @Required
    private Integer promResult;
    @Required
    @Length(max = 120)
    private String  scene;
    @Length(max = 120)
    private String  promContent;
    private String  lastScene;

    public String getLastScene() {
        return lastScene;
    }

    public void setLastScene(String lastScene) {
        this.lastScene = lastScene;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public Long getPotentialCustomerId() {
        return potentialCustomerId;
    }

    public void setPotentialCustomerId(Long potentialCustomerId) {
        this.potentialCustomerId = potentialCustomerId;
    }

    public Integer getPriorGrade() {
        return priorGrade;
    }

    public void setPriorGrade(Integer priorGrade) {
        this.priorGrade = priorGrade;
    }

    public Integer getNextGrade() {
        return nextGrade;
    }

    public void setNextGrade(Integer nextGrade) {
        this.nextGrade = nextGrade;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Integer getIsSuccessBooking() {
        return isSuccessBooking;
    }

    public void setIsSuccessBooking(Integer isSuccessBooking) {
        this.isSuccessBooking = isSuccessBooking;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Integer getBookingIsArrive() {
        return bookingIsArrive;
    }

    public void setBookingIsArrive(Integer bookingIsArrive) {
        this.bookingIsArrive = bookingIsArrive;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Date getNextArriveDate() {
        return nextArriveDate;
    }

    public void setNextArriveDate(Date nextArriveDate) {
        this.nextArriveDate = nextArriveDate;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    public Integer getPromWay() {
        return promWay;
    }

    public void setPromWay(Integer promWay) {
        this.promWay = promWay;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public Integer getPromResult() {
        return promResult;
    }

    public void setPromResult(Integer promResult) {
        this.promResult = promResult;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    public String getPromContent() {
        return promContent;
    }

    public void setPromContent(String promContent) {
        this.promContent = promContent == null ? null : promContent.trim();
    }
}
