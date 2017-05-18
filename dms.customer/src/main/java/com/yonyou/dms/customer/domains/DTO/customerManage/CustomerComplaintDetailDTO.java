
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerComplaintDetailDTO.java
*
* @Author : Administrator
*
* @Date : 2016年7月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月28日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 客户投诉子表DTO
 * 
 * @author zhanshiwei
 * @date 2016年7月28日
 */

public class CustomerComplaintDetailDTO {

    private String  dealer;
    @Required
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    dealDate;
    @Required
    private String  dealResult;
    @Required
    private String  oemDealName;

    private String  remark;

    private Integer recordVersion;
    private Long    complaintFile;

    public Long getComplaintFile() {
        return complaintFile;
    }

    public void setComplaintFile(Long complaintFile) {
        this.complaintFile = complaintFile;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer == null ? null : dealer.trim();
    }

    public Date getDealDate() {
        return dealDate;
    }

    public void setDealDate(Date dealDate) {
        this.dealDate = dealDate;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult == null ? null : dealResult.trim();
    }

    public String getOemDealName() {
        return oemDealName;
    }

    public void setOemDealName(String oemDealName) {
        this.oemDealName = oemDealName == null ? null : oemDealName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}
