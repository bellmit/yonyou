
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoServicesDTO.java
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
 * 销售订单服务项目明细
 * 
 * @author xukl
 * @date 2016年9月23日
 */
public class SoServicesDTO {

    private Integer soServicesId;

    private String  dealerCode;

    private String  serviceCode;

    private String  serviceName;

    private Integer serviceType;

    private Double  directivePrice;

    private Double  afterDiscountAmount;

    private Double  afterDiscountAmountServicesList;

    public Double getAfterDiscountAmountServicesList() {
        return afterDiscountAmountServicesList;
    }

    public void setAfterDiscountAmountServicesList(Double afterDiscountAmountServicesList) {
        this.afterDiscountAmountServicesList = afterDiscountAmountServicesList;
    }

    private String  remark;

    private Integer accountMode;

    public Integer getSoServicesId() {
        return soServicesId;
    }

    public void setSoServicesId(Integer soServicesId) {
        this.soServicesId = soServicesId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode == null ? null : serviceCode.trim();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public Double getDirectivePrice() {
        return directivePrice;
    }

    public void setDirectivePrice(Double directivePrice) {
        this.directivePrice = directivePrice;
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

    public Integer getAccountMode() {
        return accountMode;
    }

    public void setAccountMode(Integer accountMode) {
        this.accountMode = accountMode;
    }
}
