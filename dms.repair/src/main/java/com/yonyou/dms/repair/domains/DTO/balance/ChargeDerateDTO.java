
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ChargeDerateDTO.java
*
* @Author : jcsi
*
* @Date : 2016年9月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月29日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
*
* @author jcsi
* @date 2016年9月29日
*/

public class ChargeDerateDTO {
    
    private Long balaPayobjId; //
    
    private String derateNo;
    
    @Required
    private Double derateAmount;
    
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date derateDate;
    
   
    private String handler;
    
    private String derateRatifier;
    
    private String remark;

    private Double notReceivedAmount;  //未收款金额
    
    public Long getBalaPayobjId() {
        return balaPayobjId;
    }

    
    public void setBalaPayobjId(Long balaPayobjId) {
        this.balaPayobjId = balaPayobjId;
    }

    
    public String getDerateNo() {
        return derateNo;
    }

    
    public void setDerateNo(String derateNo) {
        this.derateNo = derateNo;
    }

    
    public Double getDerateAmount() {
        return derateAmount;
    }

    
    
    public Double getNotReceivedAmount() {
        return notReceivedAmount;
    }


    
    public void setNotReceivedAmount(Double notReceivedAmount) {
        this.notReceivedAmount = notReceivedAmount;
    }


    public void setDerateAmount(Double derateAmount) {
        this.derateAmount = derateAmount;
    }

    
    public Date getDerateDate() {
        return derateDate;
    }

    
    public void setDerateDate(Date derateDate) {
        this.derateDate = derateDate;
    }

    
    public String getHandler() {
        return handler;
    }

    
    public void setHandler(String handler) {
        this.handler = handler;
    }

    
    public String getDerateRatifier() {
        return derateRatifier;
    }

    
    public void setDerateRatifier(String derateRatifier) {
        this.derateRatifier = derateRatifier;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
