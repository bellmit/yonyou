
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestLabourDTO.java
*
* @Author : jcsi
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;

import java.util.Date;

/**
*
* @author jcsi
* @date 2016年9月5日
*/

public class SuggestLabourDTO {

    private String suggestLabourId;
    
    private String vin;   
    
    private String labourCode;  //维修项目代码
    
    private String labourName;  //维修项目名称
    
    private String localLabourCode;  //行政项目代码
    
    private String localLabourName;  //行政项目名称
    
    private Double stdLabourHour;  //标准工时
    
    private Double assignLabourHour;  //派工工时
    
    private Double labourPrice;   //工时单价
    
    private Double labourAmount;  //工时费
    
    private Long reason;  //原因
    
    private Date suggestDate;
    
    private String remark;
    
    private String modeGroup;  //维修车型分组代码
    

    
    
    
    
    
    public String getModeGroup() {
        return modeGroup;
    }





    
    public void setModeGroup(String modeGroup) {
        this.modeGroup = modeGroup;
    }





    public String getSuggestLabourId() {
        return suggestLabourId;
    }




    
    public void setSuggestLabourId(String suggestLabourId) {
        this.suggestLabourId = suggestLabourId;
    }




    public Date getSuggestDate() {
        return suggestDate;
    }



    
    public void setSuggestDate(Date suggestDate) {
        this.suggestDate = suggestDate;
    }



    
    public String getRemark() {
        return remark;
    }



    
    public void setRemark(String remark) {
        this.remark = remark;
    }



    public String getVin() {
        return vin;
    }

    
    public void setVin(String vin) {
        this.vin = vin;
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


    public Long getReason() {
        return reason;
    }

    
    public void setReason(Long reason) {
        this.reason = reason;
    }
    
    
}
