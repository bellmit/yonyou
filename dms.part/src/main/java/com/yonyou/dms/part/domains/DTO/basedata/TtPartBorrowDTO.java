package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

public class TtPartBorrowDTO {
//    private Date createDate;
//    private Date updateDate;
//    private Long createBy;
//    private Long updateBy;
    
    private String dealerCode;
    private String borrowNo;
    private String customerName;
    private String customerCode;
    private Date borrowDate;
    private String handler;
    private Double borrowTotalAmount;
   
    private String lockUser;
    
    private Integer payOff;
    private Integer ver;
    private Integer dKey;
    private Integer isFinished;
    

    private Date finishedDate;
    
    private Date DxpDate;// comg2011-07-26  为二次录入服务
    
    
    
    
    @Override
    public String toString() {
        return "TtPartBorrowDTO [dealerCode=" + dealerCode + ", borrowNo=" + borrowNo + ", customerName=" + customerName
               + ", customerCode=" + customerCode + ", borrowDate=" + borrowDate + ", handler=" + handler
               + ", borrowTotalAmount=" + borrowTotalAmount + ", lockUser=" + lockUser + ", payOff=" + payOff + ", ver="
               + ver + ", dKey=" + dKey + ", isFinished=" + isFinished + ", finishedDate=" + finishedDate + ", DxpDate="
               + DxpDate + "]";
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getLockUser() {
        return lockUser;
    }
    
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getBorrowNo() {
        return borrowNo;
    }
    
    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }
    
    public String getHandler() {
        return handler;
    }
    
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    public Integer getPayOff() {
        return payOff;
    }
    
    public void setPayOff(Integer payOff) {
        this.payOff = payOff;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Integer getdKey() {
        return dKey;
    }
    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }
    
    public Integer getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
    
    public Double getBorrowTotalAmount() {
        return borrowTotalAmount;
    }
    
    public void setBorrowTotalAmount(Double borrowTotalAmount) {
        this.borrowTotalAmount = borrowTotalAmount;
    }
    
    public Date getFinishedDate() {
        return finishedDate;
    }
    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
    public Date getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    
    public Date getDxpDate() {
        return DxpDate;
    }
    
    public void setDxpDate(Date dxpDate) {
        DxpDate = dxpDate;
    }
    
    
    
    
}
