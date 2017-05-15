
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingIntentDTO.java
*
* @Author : Administrator
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.DTO.customerManage;

/**
* 展厅客户意向报价
* @author zhanshiwei
* @date 2016年8月31日
*/

public class VisitingIntentDTO {
    private Long visitingIntentId;

    private Integer itemId;

    private String intentBrand;

    private String intentModel;

    private String intentSeries;

    private String intentConfig;

    private String intentColor;

    private Integer isMainModel;
    
    private Integer isMainSeries;    

	private Double quotedAmount;

    private String quotedRemark;


    public Long getVisitingIntentId() {
        return visitingIntentId;
    }

    public void setVisitingIntentId(Long visitingIntentId) {
        this.visitingIntentId = visitingIntentId;
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

    public Integer getIsMainSeries() {
		return isMainSeries;
	}

	public void setIsMainSeries(Integer isMainSeries) {
		this.isMainSeries = isMainSeries;
	}    
    
    public Integer getIsMainModel() {
        return isMainModel;
    }

    
    public void setIsMainModel(Integer isMainModel) {
        this.isMainModel = isMainModel;
    }

    public Integer getItemId() {
        return itemId;
    }

    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    
    public Double getQuotedAmount() {
        return quotedAmount;
    }

    
    public void setQuotedAmount(Double quotedAmount) {
        this.quotedAmount = quotedAmount;
    }

    
    public String getQuotedRemark() {
        return quotedRemark;
    }

    
    public void setQuotedRemark(String quotedRemark) {
        this.quotedRemark = quotedRemark;
    }
    
    

}
