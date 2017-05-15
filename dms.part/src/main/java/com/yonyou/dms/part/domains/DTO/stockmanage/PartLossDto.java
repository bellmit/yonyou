
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartLossDto.java
*
* @Author : jcsi
*
* @Date : 2016年8月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月13日    jcsi    1.0
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
* 配件报损
* @author jcsi
* @date 2016年8月13日
*/

public class PartLossDto {

    private String lossNo;//报损单号
    
    private String inventoryNo;  //盘点单号
    
    private Double costAmount;  //成本金额
    
    private Double outAmount;  //出库金额
    
    private String handler;   //经手人
    
    @Required
    private Date orderDate;  //开单日期
    
    private Long orderStatus;  //单据状态

    private Date orderDateFrom;
    
    private Date orderDateTo; 
    
    private List<PartLossItemDto> partLossItemList;
    
    public String getLossNo() {
        return lossNo;
    }

    
    public void setLossNo(String lossNo) {
        this.lossNo = lossNo;
    }

    
    public String getInventoryNo() {
        return inventoryNo;
    }

    
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    
    public Double getCostAmount() {
        return costAmount;
    }

    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }

    
    public Double getOutAmount() {
        return outAmount;
    }

    
    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }

    
    public String getHandler() {
        return handler;
    }

    
    public void setHandler(String handler) {
        this.handler = handler;
    }

    
    public Date getOrderDate() {
        return orderDate;
    }

    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    
    public Long getOrderStatus() {
        return orderStatus;
    }

    
    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }


    
    public Date getOrderDateFrom() {
        return orderDateFrom;
    }


    
    public void setOrderDateFrom(Date orderDateFrom) {
        this.orderDateFrom = orderDateFrom;
    }


    
    public Date getOrderDateTo() {
        return orderDateTo;
    }


    
    public void setOrderDateTo(Date orderDateTo) {
        this.orderDateTo = orderDateTo;
    }


    
    public List<PartLossItemDto> getPartLossItemList() {
        return partLossItemList;
    }


    
    public void setPartLossItemList(List<PartLossItemDto> partLossItemList) {
        this.partLossItemList = partLossItemList;
    }
  
    
    
}
