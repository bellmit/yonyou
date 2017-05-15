
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoDecrodateDTO.java
*
* @Author : xukl
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;



/**
* 销售订单装潢项目明细
* @author xukl
* @date 2016年9月23日
*/

public class SoDecrodateDTO {
    private Integer soDecrodateId;

    private Integer soNoId;

    private String dealerCode;

    private String labourCode;

    private String labourName;

    private Double stdLabourHour;

    private Double labourPrice;

    private Double discount;

    private Double afterDiscountAmount;

    private String remark;

    public Integer getSoDecrodateId() {
        return soDecrodateId;
    }

    public void setSoDecrodateId(Integer soDecrodateId) {
        this.soDecrodateId = soDecrodateId;
    }

    public Integer getSoNoId() {
        return soNoId;
    }

    public void setSoNoId(Integer soNoId) {
        this.soNoId = soNoId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getLabourCode() {
        return labourCode;
    }

    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode == null ? null : labourCode.trim();
    }

    public String getLabourName() {
        return labourName;
    }

    public void setLabourName(String labourName) {
        this.labourName = labourName == null ? null : labourName.trim();
    }

    public Double getStdLabourHour() {
        return stdLabourHour;
    }

    public void setStdLabourHour(Double stdLabourHour) {
        this.stdLabourHour = stdLabourHour;
    }

    public Double getLabourPrice() {
        return labourPrice;
    }

    public void setLabourPrice(Double labourPrice) {
        this.labourPrice = labourPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAfterDiscountAmount() {
        return afterDiscountAmount;
    }

    public void setAfterDiscountAmount(Double afterDiscountAmount) {
        this.afterDiscountAmount = afterDiscountAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
