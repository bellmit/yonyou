
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestPartDTO.java
*
* @Author : jcsi
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;

import java.util.Date;

/**
*
* @author jcsi
* @date 2016年9月5日
*/

public class SuggestPartDTO {
    
    private String suggestMaintainPartId;
    
    private String vin;
    
    
    private String partNo;  //配件代码
    
    private String partName; //配件名称
    
    private Date suggestDate;  //建议日期
    
    private Double salesPrice;  //销售价

    private Double quantity;   //数量
    
    private Double amount;  //金额

    private Long reason;  //原因
    
    private String  remark;  //备注

   
    
    
    public String getSuggestMaintainPartId() {
        return suggestMaintainPartId;
    }


    
    public void setSuggestMaintainPartId(String suggestMaintainPartId) {
        this.suggestMaintainPartId = suggestMaintainPartId;
    }


    public String getVin() {
        return vin;
    }

    
    public void setVin(String vin) {
        this.vin = vin;
    }

    
  

    

    public String getPartNo() {
        return partNo;
    }

    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    
    public String getPartName() {
        return partName;
    }

    
    public void setPartName(String partName) {
        this.partName = partName;
    }

    
    public Date getSuggestDate() {
        return suggestDate;
    }

    
    public void setSuggestDate(Date suggestDate) {
        this.suggestDate = suggestDate;
    }

    
    public Double getSalesPrice() {
        return salesPrice;
    }

    
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    
    public Double getQuantity() {
        return quantity;
    }

    
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    
    public Double getAmount() {
        return amount;
    }

    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    
    public Long getReason() {
        return reason;
    }

    
    public void setReason(Long reason) {
        this.reason = reason;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
    
}
