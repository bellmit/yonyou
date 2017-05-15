
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartBuyDTO.java
*
* @Author : xukl
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;
import java.util.List;

import com.yonyou.dms.function.utils.validate.define.Required;


/**
* PartBuyDTO
* @author xukl
* @date 2016年7月26日
*/

public class PartBuyDTO {

    private String stockInNo;

    private String dealerCode;

    @Required
    private String customerCode;
    
    private String customerName;
    
    private String deliveryNo;
    
    @Required
    private Date orderDate;

    private Integer orderStatus;

    private Double beforeTaxAmount;

    private Double totalAmount;

    private String remark;

    private List<PartBuyItemDTO> partBuyItemList;
    
    public List<PartBuyItemDTO> getPartBuyItemList() {
        return partBuyItemList;
    }

    public void setPartBuyItemList(List<PartBuyItemDTO> partBuyItemList) {
        this.partBuyItemList = partBuyItemList;
    }


    public String getStockInNo() {
        return stockInNo;
    }

    public void setStockInNo(String stockInNo) {
        this.stockInNo = stockInNo == null ? null : stockInNo.trim();
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getDeliveryNo() {
        return deliveryNo;
    }

    public void setDeliveryNo(String delvieryNo) {
        this.deliveryNo = delvieryNo == null ? null : delvieryNo.trim();
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getBeforeTaxAmount() {
        return beforeTaxAmount;
    }

    public void setBeforeTaxAmount(Double beforeTaxAmount) {
        this.beforeTaxAmount = beforeTaxAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
