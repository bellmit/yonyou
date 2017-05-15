
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInnerDto.java
*
* @Author : jcsi
*
* @Date : 2016年8月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月14日    Administrator    1.0
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
*
* @author jcsi
* @date 2016年8月14日
*/

public class PartInnerDto {
    
    private String receiptNo;  //领用单号
    
    private Double costAmount; //成本金额
    
    private Double outAmount; //出库金额
    
    @Required
    private String receiptor;  //领用人
    
    @Required
    private Date receiptDate;  //领用日期
    
    private Long orderStatus;  //单据状态
    
    private String remark;  //备注
    
    private Date orderDateFrom;
    
    private Date orderDateTo;
    
    private List<PartInnerItemDto> partInnerItemDtos;
    
    public String getReceiptNo() {
        return receiptNo;
    }

    
    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
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

    
    public String getReceiptor() {
        return receiptor;
    }

    
    public void setReceiptor(String receiptor) {
        this.receiptor = receiptor;
    }

    
    public Date getReceiptDate() {
        return receiptDate;
    }

    
    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
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


    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }


    
    public List<PartInnerItemDto> getPartInnerItemDtos() {
        return partInnerItemDtos;
    }


    
    public void setPartInnerItemDtos(List<PartInnerItemDto> partInnerItemDtos) {
        this.partInnerItemDtos = partInnerItemDtos;
    }

}
