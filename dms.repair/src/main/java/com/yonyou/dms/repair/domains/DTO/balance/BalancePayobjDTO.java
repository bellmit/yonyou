
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalancePayobjDTO.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.balance;

import javax.validation.constraints.Digits;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 结算单收费对象列表DTO
* @author ZhengHe
* @date 2016年9月26日
*/

public class BalancePayobjDTO {
    @Required
    private Long balaPayobjId;

    @Required
    private Long balanceId;

    private Integer paymentObjectType;

    private String paymentObjectCode;

    private String paymentObjectName;

    @Digits(integer=12,fraction=2)
    private Double disAmount;

    @Digits(integer=12,fraction=2)
    private Double subObbAmount;

    @Digits(integer=12,fraction=2)
    private Double receivableAmount;

    @Digits(integer=12,fraction=2)
    private Double receivedAmount;

    @Digits(integer=12,fraction=2)
    private Double notReceivedAmount;

    @Digits(integer=10,fraction=2)
    private Double derateAmount;
    
    @Digits(integer=3,fraction=2)
    private Double tax;

    @Digits(integer=12,fraction=2)
    private Double taxAmount;

    private Integer payOff;

    private Integer invoiceTag;

    private Integer isRed;

    public Long getBalaPayobjId() {
        return balaPayobjId;
    }

    public void setBalaPayobjId(Long balaPayobjId) {
        this.balaPayobjId = balaPayobjId;
    }

    public Long getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(Long balanceId) {
        this.balanceId = balanceId;
    }

    public Integer getPaymentObjectType() {
        return paymentObjectType;
    }

    public void setPaymentObjectType(Integer paymentObjectType) {
        this.paymentObjectType = paymentObjectType;
    }

    public String getPaymentObjectCode() {
        return paymentObjectCode;
    }

    public void setPaymentObjectCode(String paymentObjectCode) {
        this.paymentObjectCode = paymentObjectCode;
    }

    public String getPaymentObjectName() {
        return paymentObjectName;
    }

    public void setPaymentObjectName(String paymentObjectName) {
        this.paymentObjectName = paymentObjectName;
    }

    public Double getDisAmount() {
        return disAmount;
    }

    public void setDisAmount(Double disAmount) {
        this.disAmount = disAmount;
    }

    public Double getSubObbAmount() {
        return subObbAmount;
    }

    public void setSubObbAmount(Double subObbAmount) {
        this.subObbAmount = subObbAmount;
    }

    public Double getReceivableAmount() {
        return receivableAmount;
    }

    public void setReceivableAmount(Double receivableAmount) {
        this.receivableAmount = receivableAmount;
    }

    public Double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Double getNotReceivedAmount() {
        return notReceivedAmount;
    }

    public void setNotReceivedAmount(Double notReceivedAmount) {
        this.notReceivedAmount = notReceivedAmount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Integer getPayOff() {
        return payOff;
    }

    public void setPayOff(Integer payOff) {
        this.payOff = payOff;
    }

    public Integer getInvoiceTag() {
        return invoiceTag;
    }

    public void setInvoiceTag(Integer invoiceTag) {
        this.invoiceTag = invoiceTag;
    }

    public Integer getIsRed() {
        return isRed;
    }

    public void setIsRed(Integer isRed) {
        this.isRed = isRed;
    }

    public Double getDerateAmount() {
        return derateAmount;
    }

    public void setDerateAmount(Double derateAmount) {
        this.derateAmount = derateAmount;
    }
}
