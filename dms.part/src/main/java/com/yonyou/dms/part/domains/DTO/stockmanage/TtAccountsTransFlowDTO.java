package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

/**
 * 财务凭证事务
* TODO description
* @author yujiangheng
* @date 2017年5月9日
 */
public class TtAccountsTransFlowDTO {
  
//    private Long updateBy;
//    private Date updateDate;
//    private Date createDate;
//    private Long createBy;
    private String orgCode;
    private String dealerCode;
    private String subBusinessNo;
    private String businessNo;
    private String bankAccount;
    private String employee;
    private String provider;
    private String customer;
    private String project;
    
    private Integer ver;
    private Integer isValid;
    private Integer execNum;
    private Integer transType;
    private Integer execStatus;
    
    private Long transId;
    private Long voucherNo;
    private Date transDate;

    private Double netAmount;
    private Double taxAmount;
    
    
    @Override
    public String toString() {
        return "TtAccountsTransFlowDTO [orgCode=" + orgCode + ", dealerCode=" + dealerCode + ", subBusinessNo="
               + subBusinessNo + ", businessNo=" + businessNo + ", bankAccount=" + bankAccount + ", employee="
               + employee + ", provider=" + provider + ", customer=" + customer + ", project=" + project + ", ver="
               + ver + ", isValid=" + isValid + ", execNum=" + execNum + ", transType=" + transType + ", execStatus="
               + execStatus + ", transId=" + transId + ", voucherNo=" + voucherNo + ", transDate=" + transDate
               + ", netAmount=" + netAmount + ", taxAmount=" + taxAmount + "]";
    }

    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getSubBusinessNo() {
        return subBusinessNo;
    }
    
    public void setSubBusinessNo(String subBusinessNo) {
        this.subBusinessNo = subBusinessNo;
    }
    
    public String getBusinessNo() {
        return businessNo;
    }
    
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    public String getBankAccount() {
        return bankAccount;
    }
    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
    
    public String getEmployee() {
        return employee;
    }
    
    public void setEmployee(String employee) {
        this.employee = employee;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public String getCustomer() {
        return customer;
    }
    
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    
    public String getProject() {
        return project;
    }
    
    public void setProject(String project) {
        this.project = project;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Integer getIsValid() {
        return isValid;
    }
    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    
    public Integer getExecNum() {
        return execNum;
    }
    
    public void setExecNum(Integer execNum) {
        this.execNum = execNum;
    }
    
    public Integer getTransType() {
        return transType;
    }
    
    public void setTransType(Integer transType) {
        this.transType = transType;
    }
    
    public Integer getExecStatus() {
        return execStatus;
    }
    
    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }
    
    public Long getTransId() {
        return transId;
    }
    
    public void setTransId(Long transId) {
        this.transId = transId;
    }
    
    public Long getVoucherNo() {
        return voucherNo;
    }
    
    public void setVoucherNo(Long voucherNo) {
        this.voucherNo = voucherNo;
    }
    
    public Date getTransDate() {
        return transDate;
    }
    
    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }
    
    public Double getNetAmount() {
        return netAmount;
    }
    
    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }
    
    public Double getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

  

   

   
    
    
}
