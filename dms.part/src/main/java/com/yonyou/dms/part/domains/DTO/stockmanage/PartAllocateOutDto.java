
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateOutDto.java
*
* @Author : jcsi
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    Administrator    1.0
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
*调拨出库
* @author jcsi
* @date 2016年7月26日
*/

public class PartAllocateOutDto {
    
    private String allocateOutNo;  //调拨出库单号
    
    @Required
    private String customerCode;  //往来客户代码
    
    @Required
    private String customerName;  //客户名称
    
    private Long orderStatus;  //单据状态
    
    private Date orderDateFrom;  //开单日期（开始）
    
    private Date orderDateTo;  //开始日期（结束）
    
    @Required
    private Date orderDate;  //开单日期
    
    private Double outAmount;  //出库金额

    private Double costAmount;  //成本金额 
    
    private String remark;  //备注
    
    private int isBalance;  //是否结算
    
    
    
    public int getIsBalance() {
        return isBalance;
    }


    
    public void setIsBalance(int isBalance) {
        this.isBalance = isBalance;
    }



    private List<PartAllocateOutItemDto> partAllocateOutItemList;
    
    public String getAllocateOutNo() {
        return allocateOutNo;
    }

    
    public void setAllocateOutNo(String allocateOutNo) {
        this.allocateOutNo = allocateOutNo;
    }

    
    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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


    
    public Date getOrderDate() {
        return orderDate;
    }


    
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    
    public Double getOutAmount() {
        return outAmount;
    }


    
    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }


    
    public Double getCostAmount() {
        return costAmount;
    }


    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }


    
    public String getRemark() {
        return remark;
    }


    
    public void setRemark(String remark) {
        this.remark = remark;
    }


    
    public List<PartAllocateOutItemDto> getPartAllocateOutItemList() {
        return partAllocateOutItemList;
    }


    
    public void setPartAllocateOutItemList(List<PartAllocateOutItemDto> partAllocateOutItemList) {
        this.partAllocateOutItemList = partAllocateOutItemList;
    }


    
  


    
   
    
  

    
  
    
    

}
