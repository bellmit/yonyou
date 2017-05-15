
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerIntentDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月2日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月2日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

/**
* 潜客客户意向
* @author zhanshiwei
* @date 2016年9月2日
*/

public class CustomerIntentDTO {
    private Long customerIntentId;

    private Long potentialCustomerId;

    private String dealerCode;

    private String intentBrand;

    private String intentModel;

    private String intentSeries;

    private String intentConfig;

    private String intentColor;

    private Integer isMainIntent;

    private Double quotedPrice;

    private String remark;

    public Long getCustomerIntentId() {
        return customerIntentId;
    }

    public void setCustomerIntentId(Long customerIntentId) {
        this.customerIntentId = customerIntentId;
    }

    public Long getPotentialCustomerId() {
        return potentialCustomerId;
    }

    public void setPotentialCustomerId(Long potentialCustomerId) {
        this.potentialCustomerId = potentialCustomerId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getIntentBrand() {
        return intentBrand;
    }

    public void setIntentBrand(String intentBrand) {
        this.intentBrand = intentBrand == null ? null : intentBrand.trim();
    }

    public String getIntentModel() {
        return intentModel;
    }

    public void setIntentModel(String intentModel) {
        this.intentModel = intentModel == null ? null : intentModel.trim();
    }

    public String getIntentSeries() {
        return intentSeries;
    }

    public void setIntentSeries(String intentSeries) {
        this.intentSeries = intentSeries == null ? null : intentSeries.trim();
    }

    public String getIntentConfig() {
        return intentConfig;
    }

    public void setIntentConfig(String intentConfig) {
        this.intentConfig = intentConfig == null ? null : intentConfig.trim();
    }

    public String getIntentColor() {
        return intentColor;
    }

    public void setIntentColor(String intentColor) {
        this.intentColor = intentColor == null ? null : intentColor.trim();
    }

    public Integer getIsMainIntent() {
        return isMainIntent;
    }

    public void setIsMainIntent(Integer isMainIntent) {
        this.isMainIntent = isMainIntent;
    }

    public Double getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(Double quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
