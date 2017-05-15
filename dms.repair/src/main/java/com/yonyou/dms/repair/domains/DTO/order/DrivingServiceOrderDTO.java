
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : DrivingServiceOrderPO.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;

import java.util.Date;

/**
* 预约单-取送车服务单
* @author jcsi
* @date 2016年10月14日
*/


public class DrivingServiceOrderDTO{

    private Long boId;  //预约单id
    
    private String startAdd;  //起始地
    
    private String startContactorName; //起始地联系人
    
    private String startContactorMobile; //起始地联系人手机
    
    private String destAdd;  //目的地
    
    private String destContactorName; //目的地联系人
    
    private String destContactorMobile; //目的地联系人手机
    
    private Date startTime;  //取车时间
    
    private Double referKilometer; //预估里程 
    
    private Double referPrice;  //预估价格
    
    private String sign; //签名
    
    private Long serviceOrderStatus; //服务单状态
    
    private Long  idealersId;  //平台服务单号

    
    public Long getBoId() {
        return boId;
    }

    
    public void setBoId(Long boId) {
        this.boId = boId;
    }

    
    public String getStartAdd() {
        return startAdd;
    }

    
    public void setStartAdd(String startAdd) {
        this.startAdd = startAdd;
    }

    
    public String getStartContactorName() {
        return startContactorName;
    }

    
    public void setStartContactorName(String startContactorName) {
        this.startContactorName = startContactorName;
    }

    
    public String getStartContactorMobile() {
        return startContactorMobile;
    }

    
    public void setStartContactorMobile(String startContactorMobile) {
        this.startContactorMobile = startContactorMobile;
    }

    
    public String getDestAdd() {
        return destAdd;
    }

    
    public void setDestAdd(String destAdd) {
        this.destAdd = destAdd;
    }

    
    public String getDestContactorName() {
        return destContactorName;
    }

    
    public void setDestContactorName(String destContactorName) {
        this.destContactorName = destContactorName;
    }

    
    public String getDestContactorMobile() {
        return destContactorMobile;
    }

    
    public void setDestContactorMobile(String destContactorMobile) {
        this.destContactorMobile = destContactorMobile;
    }

    
    public Date getStartTime() {
        return startTime;
    }

    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    
    public Double getReferKilometer() {
        return referKilometer;
    }

    
    public void setReferKilometer(Double referKilometer) {
        this.referKilometer = referKilometer;
    }

    
    public Double getReferPrice() {
        return referPrice;
    }

    
    public void setReferPrice(Double referPrice) {
        this.referPrice = referPrice;
    }

    
    public String getSign() {
        return sign;
    }

    
    public void setSign(String sign) {
        this.sign = sign;
    }

    
    public Long getServiceOrderStatus() {
        return serviceOrderStatus;
    }

    
    public void setServiceOrderStatus(Long serviceOrderStatus) {
        this.serviceOrderStatus = serviceOrderStatus;
    }

    
    public Long getIdealersId() {
        return idealersId;
    }

    
    public void setIdealersId(Long idealersId) {
        this.idealersId = idealersId;
    }
    
    
    
}
