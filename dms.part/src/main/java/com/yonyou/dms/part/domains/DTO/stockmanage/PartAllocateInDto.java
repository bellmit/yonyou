
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateInDto.java
*
* @Author : xukl
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月10日    xukl    1.0
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
*PartAllocateInDto
* @author xukl
* @date 2016年8月10日
*/
public class PartAllocateInDto {
/**
 * DEALER_CODE, ALLOCATE_IN_NO, CUSTOMER_CODE,CUSTOMER_NAME ,  STOCK_IN_DATE
STOCK_IN_VOUCHER,HANDLER,LOCK_USER,IS_IDLE_ALLOCATION,IS_NET_TRANSFER 
 */
    private String dealerCode;
    private String allocateInNo;//调拨入库单号
    private String customerCode;//供应商代码
    private String customerName;//供应商名称
    private String stockInDate;//入库日期
    private String stockInVoucher;
    private String handler;
    private String lockUser;
    private String isIdleAllocation;
    private String isNetTransfer;
    
    private String remark;
    //private String allocateOutNo;
    private Integer isFinished;
    private String befoeTaxAmount;
    private Date finishedDate;
    private String isPayoff;
    private String crednce;
    private Double totalAmount;
    
    
    @Override
    public String toString() {
        return "PartAllocateInDto [dealerCode=" + dealerCode + ", allocateInNo=" + allocateInNo + ", customerCode="
               + customerCode + ", customerName=" + customerName + ", stockInDate=" + stockInDate + ", stockInVoucher="
               + stockInVoucher + ", handler=" + handler + ", lockUser=" + lockUser + ", isIdleAllocation="
               + isIdleAllocation + ", isNetTransfer=" + isNetTransfer + ", remark=" + remark + ", isFinished="
               + isFinished + ", befoeTaxAmount=" + befoeTaxAmount + ", finishedDate=" + finishedDate + ", isPayoff="
               + isPayoff + ", crednce=" + crednce + ", totalAmount=" + totalAmount + "]";
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getAllocateInNo() {
        return allocateInNo;
    }
    
    public void setAllocateInNo(String allocateInNo) {
        this.allocateInNo = allocateInNo;
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
   
    

    
    public String getStockInDate() {
        return stockInDate;
    }

    
    public void setStockInDate(String stockInDate) {
        this.stockInDate = stockInDate;
    }

    public String getStockInVoucher() {
        return stockInVoucher;
    }
    
    public void setStockInVoucher(String stockInVoucher) {
        this.stockInVoucher = stockInVoucher;
    }
    
    public String getHandler() {
        return handler;
    }
    
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    public String getLockUser() {
        return lockUser;
    }
    
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }
    
    public String getIsIdleAllocation() {
        return isIdleAllocation;
    }
    
    public void setIsIdleAllocation(String isIdleAllocation) {
        this.isIdleAllocation = isIdleAllocation;
    }
    
    public String getIsNetTransfer() {
        return isNetTransfer;
    }
    
    public void setIsNetTransfer(String isNetTransfer) {
        this.isNetTransfer = isNetTransfer;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
  
    
    public Integer getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
    
    public String getBefoeTaxAmount() {
        return befoeTaxAmount;
    }
    
    public void setBefoeTaxAmount(String befoeTaxAmount) {
        this.befoeTaxAmount = befoeTaxAmount;
    }
    
    public Date getFinishedDate() {
        return finishedDate;
    }
    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
    public String getIsPayoff() {
        return isPayoff;
    }
    
    public void setIsPayoff(String isPayoff) {
        this.isPayoff = isPayoff;
    }
    
  
    
    public String getCrednce() {
        return crednce;
    }

    
    public void setCrednce(String crednce) {
        this.crednce = crednce;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    
   
}