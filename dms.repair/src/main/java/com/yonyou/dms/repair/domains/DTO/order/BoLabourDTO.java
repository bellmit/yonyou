
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BoLabourPO.java
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



/**
* 预约单-维修项目
* @author jcsi
* @date 2016年10月14日
*/


public class BoLabourDTO{
    
    private Long boLabourId;

    private Long boId;  
    
    private String labourCode;  //维修项目代码
    
    private String labourName;  //维修项目名称
    
    private String localLabourCode; //行管项目代码
    
    private String localLabourName;  //行管项目名称
    
    private Double stdLabourHour;  //标准工时
    
    private Double assignLabourHour;  //派工工时
    
    private Double labourPrice;  //工时单价
    
    private Double labourAmount;  //工时费
    
    private String troubleDesc;   //故障描述

    
    private String modelLabourCode; //项目车型组；
    
    public Long getBoLabourId() {
        return boLabourId;
    }


    
    public void setBoLabourId(Long boLabourId) {
        this.boLabourId = boLabourId;
    }


    public Long getBoId() {
        return boId;
    }

    
    public void setBoId(Long boId) {
        this.boId = boId;
    }

    
    public String getLabourCode() {
        return labourCode;
    }

    
    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode;
    }

    
    public String getLabourName() {
        return labourName;
    }

    
    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }

    
    public String getLocalLabourCode() {
        return localLabourCode;
    }

    
    public void setLocalLabourCode(String localLabourCode) {
        this.localLabourCode = localLabourCode;
    }

    
    public String getLocalLabourName() {
        return localLabourName;
    }

    
    public void setLocalLabourName(String localLabourName) {
        this.localLabourName = localLabourName;
    }

    
    public Double getStdLabourHour() {
        return stdLabourHour;
    }

    
    public void setStdLabourHour(Double stdLabourHour) {
        this.stdLabourHour = stdLabourHour;
    }

    
    public Double getAssignLabourHour() {
        return assignLabourHour;
    }

    
    public void setAssignLabourHour(Double assignLabourHour) {
        this.assignLabourHour = assignLabourHour;
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

    
    public String getTroubleDesc() {
        return troubleDesc;
    }

    
    public void setTroubleDesc(String troubleDesc) {
        this.troubleDesc = troubleDesc;
    }



    
    public String getModelLabourCode() {
        return modelLabourCode;
    }



    
    public void setModelLabourCode(String modelLabourCode) {
        this.modelLabourCode = modelLabourCode;
    }
    
    
    
}
