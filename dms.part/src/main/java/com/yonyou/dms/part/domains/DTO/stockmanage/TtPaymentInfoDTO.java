package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

public class TtPaymentInfoDTO {
//    private Date updateDate;
//    private Date createDate;
//    private Long createBy;
//    private Long updateBy;
    private Long itemId;
    
    private String dealerCode;
    private String customerNo;
    private String businessNo;
    private String payMoneyNo;
    private Date payOffDate;
    
    private Integer ver;
    private Integer isPayoff;
    private Integer businessType;
    
    private Double payedAmount;
    private Double paymentAmount;
    
    public Long getItemId() {
        return itemId;
    }
    
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getBusinessNo() {
        return businessNo;
    }
    
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    public String getPayMoneyNo() {
        return payMoneyNo;
    }
    
    public void setPayMoneyNo(String payMoneyNo) {
        this.payMoneyNo = payMoneyNo;
    }
    
    public Date getPayOffDate() {
        return payOffDate;
    }
    
    public void setPayOffDate(Date payOffDate) {
        this.payOffDate = payOffDate;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Integer getIsPayoff() {
        return isPayoff;
    }
    
    public void setIsPayoff(Integer isPayoff) {
        this.isPayoff = isPayoff;
    }
    
    public Integer getBusinessType() {
        return businessType;
    }
    
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
    
    public Double getPayedAmount() {
        return payedAmount;
    }
    
    public void setPayedAmount(Double payedAmount) {
        this.payedAmount = payedAmount;
    }
    
    public Double getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
   
    
    
    
    
}
