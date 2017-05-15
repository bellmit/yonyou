
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartProfitDto.java
*
* @Author : xukl
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    xukl    1.0
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
* PartProfitDto
* @author xukl
* @date 2016年8月12日
*/

public class PartProfitDto {
    private String dealerCode;

    private String profitNo;

    private String inventoryNo;

    @Required
    private String handler;

    private Double totalAmount;

    @Required
    private Date orderDate;

    private Integer orderStatus;
    
    private List<PartProfitItemDto> partProfitItemList;
    
    /**
     * @return the dealerCode
     */
    public String getDealerCode() {
        return dealerCode;
    }

    
    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    /**
     * @return the profitNo
     */
    public String getProfitNo() {
        return profitNo;
    }

    
    /**
     * @param profitNo the profitNo to set
     */
    public void setProfitNo(String profitNo) {
        this.profitNo = profitNo;
    }

    
    /**
     * @return the inventoryNo
     */
    public String getInventoryNo() {
        return inventoryNo;
    }

    
    /**
     * @param inventoryNo the inventoryNo to set
     */
    public void setInventoryNo(String inventoryNo) {
        this.inventoryNo = inventoryNo;
    }

    
    /**
     * @return the handler
     */
    public String getHandler() {
        return handler;
    }

    
    /**
     * @param handler the handler to set
     */
    public void setHandler(String handler) {
        this.handler = handler;
    }

    
    /**
     * @return the totalAmount
     */
    public Double getTotalAmount() {
        return totalAmount;
    }

    
    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    /**
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    
    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    
    /**
     * @return the orderStatus
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    
    /**
     * @param orderStatus the orderStatus to set
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * @return the partProfitItemList
     */
    public List<PartProfitItemDto> getPartProfitItemList() {
        return partProfitItemList;
    }


    /**
     * @param partProfitItemList the partProfitItemList to set
     */
    public void setPartProfitItemList(List<PartProfitItemDto> partProfitItemList) {
        this.partProfitItemList = partProfitItemList;
    }
    
}
