
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : SalesPartDto.java
*
* @Author : jcsi
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    Administrator    1.0
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
* 销售出库
* @author jcsi
* @date 2016年8月4日
*/

public class PartSalesDto {
    
    private String salesPartNo;   //配件销售单号
    
    private String roNo;  //工单号
    
    private Long roId;
    
    @Required
    private String customerCode;  //客户代码

    private Double outAmount;   //出库金额

    private Double costAmount;  //成本金额
    
    private Long customerType;   //客户类型
    
    @Required
    private Date orderDate;  //开单日期
    
    private Long orderStatus;  //单据状态
    
    private String remark; //备注
    
    private Date orderDateFrom;   //开单日期（开始日期）
    
    private Date orderDateTo;   //开单日期（结束日期）
    
    @Required
    private String customerName;  //客户名称
    
    private List<PartSalesItemDto>  salesPartItemDtos;

    
    public String getSalesPartNo() {
        return salesPartNo;
    }

    
    public void setSalesPartNo(String salesPartNo) {
        this.salesPartNo = salesPartNo;
    }

    
    public String getRoNo() {
        return roNo;
    }

    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    
    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
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

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
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


    
    public List<PartSalesItemDto> getSalesPartItemDtos() {
        return salesPartItemDtos;
    }


    
    public void setSalesPartItemDtos(List<PartSalesItemDto> salesPartItemDtos) {
        this.salesPartItemDtos = salesPartItemDtos;
    }


    
    public Long getCustomerType() {
        return customerType;
    }


    
    public void setCustomerType(Long customerType) {
        this.customerType = customerType;
    }


    
    public String getCustomerName() {
        return customerName;
    }


    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


	public Long getRoId() {
		return roId;
	}


	public void setRoId(Long roId) {
		this.roId = roId;
	}
    
    
}
