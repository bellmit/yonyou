
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : CustomerTrackingDTO.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月22日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月22日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.commonAS.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.ChineseLength;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 客户跟进DTO
 * 
 * @author zhanshiwei
 * @date 2016年8月22日
 */

public class CustomerTrackingDTO {

    private Integer      trackingId;
    private String       trackingIds;
    private String  customerIds;
    
    
    public String getCustomerIds() {
        return customerIds;
    }


    
    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }

    private Integer      customerId;

    private Integer      vehicleId;

    private Date         scheduleDate;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date         actionDate;

    private Integer      trackingWay;

    private String       trackingDescripton;
    @Required
    private Integer      trackingResult;

    private String       consultant;
    @Required
    @ChineseLength(max=120)
    private String       trackingProcess;
    @ChineseLength(max=120)
    private String       trackingContent;
    private List<Object> customerList;

    public String getTrackingIds() {
        return trackingIds;
    }

    public void setTrackingIds(String trackingIds) {
        this.trackingIds = trackingIds == null ? null : trackingIds.trim();
    }

    public Integer getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(Integer trackingId) {
        this.trackingId = trackingId;
    }

    public List<Object> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Object> customerList) {
        this.customerList = customerList;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
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

    public Integer getTrackingWay() {
        return trackingWay;
    }

    public void setTrackingWay(Integer trackingWay) {
        this.trackingWay = trackingWay;
    }

    public String getTrackingDescripton() {
        return trackingDescripton;
    }

    public void setTrackingDescripton(String trackingDescripton) {
        this.trackingDescripton = trackingDescripton == null ? null : trackingDescripton.trim();
    }

    public Integer getTrackingResult() {
        return trackingResult;
    }

    public void setTrackingResult(Integer trackingResult) {
        this.trackingResult = trackingResult;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    public String getTrackingProcess() {
        return trackingProcess;
    }

    public void setTrackingProcess(String trackingProcess) {
        this.trackingProcess = trackingProcess == null ? null : trackingProcess.trim();
    }

    public String getTrackingContent() {
        return trackingContent;
    }

    public void setTrackingContent(String trackingContent) {
        this.trackingContent = trackingContent == null ? null : trackingContent.trim();
    }

}
