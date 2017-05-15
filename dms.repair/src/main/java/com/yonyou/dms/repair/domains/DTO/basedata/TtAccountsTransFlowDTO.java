package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

public class TtAccountsTransFlowDTO {

    private Integer isValid;

    private Date updateDate;

    private String orgCode;

    private Double netAmount;

    private Long createBy;

    private String dealerCode;

    private Integer ver;

    private Long transId;

    private Date createDate;

    private String subBusinessNo;

    private Long updateBy;

    private Date transDate;

    private Integer transType;

    private Double taxAmount;

    private Integer execNum;

    private String businessNo;

    private String bankAccount;

    private String employee;

    private String provider;

    private String customer;

    private String project;

    private Long voucherNo;
    
    private Integer execStatus;


    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getNetAmount() {
        return this.netAmount;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getCreateBy() {
        return this.createBy;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setTransId(Long transId) {
        this.transId = transId;
    }

    public Long getTransId() {
        return this.transId;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setSubBusinessNo(String subBusinessNo) {
        this.subBusinessNo = subBusinessNo;
    }

    public String getSubBusinessNo() {
        return this.subBusinessNo;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateBy() {
        return this.updateBy;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public Date getTransDate() {
        return this.transDate;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getTransType() {
        return this.transType;
    }


    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public void setExecNum(Integer execNum) {
        this.execNum = execNum;
    }

    public Integer getExecNum() {
        return this.execNum;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBusinessNo() {
        return this.businessNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public Long getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Long voucherNo) {
        this.voucherNo = voucherNo;
    }
    

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    @Override
    public String toString() {
        return "TtAccountsTransFlowDTO [isValid=" + isValid + ", updateDate=" + updateDate + ", orgCode=" + orgCode
               + ", netAmount=" + netAmount + ", createBy=" + createBy + ", dealerCode=" + dealerCode + ", ver=" + ver
               + ", transId=" + transId + ", createDate=" + createDate + ", subBusinessNo=" + subBusinessNo
               + ", updateBy=" + updateBy + ", transDate=" + transDate + ", transType=" + transType + ", taxAmount="
               + taxAmount + ", execNum=" + execNum + ", businessNo=" + businessNo + ", bankAccount=" + bankAccount
               + ", employee=" + employee + ", provider=" + provider + ", customer=" + customer + ", project=" + project
               + ", voucherNo=" + voucherNo + ", execStatus=" + execStatus + "]";
    }
    
	 
}
