package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class BigCustomerAuthorityApprovalDto {
    private String applyNo ;// 申请单号 varchar(30)
    private String applyRemark ;//申请理由 VARCHAR(300)
    private String userCode;//申请用户(操作人) 
    private String entityCode;//经销商编号 VARCHAR(20)
    private String empCode;//申请员工代码(为哪个员工申请这个权限) 
    private String userName;//申请用户(操作人) VARCHAR(60)
    private Date authorityApplyDate;//权限申请日期 date
    
    public String getApplyNo() {
        return applyNo;
    }
    
    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }
    
    public String getApplyRemark() {
        return applyRemark;
    }
    
    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark;
    }
    
    public String getUserCode() {
        return userCode;
    }
    
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    
    public String getEntityCode() {
        return entityCode;
    }
    
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    
    public String getEmpCode() {
        return empCode;
    }
    
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Date getAuthorityApplyDate() {
        return authorityApplyDate;
    }
    
    public void setAuthorityApplyDate(Date authorityApplyDate) {
        this.authorityApplyDate = authorityApplyDate;
    }
    
    public Integer getAuthorityApprovalStatus() {
        return authorityApprovalStatus;
    }
    
    public void setAuthorityApprovalStatus(Integer authorityApprovalStatus) {
        this.authorityApprovalStatus = authorityApprovalStatus;
    }
    
    public String getOriginalStation() {
        return originalStation;
    }
    
    public void setOriginalStation(String originalStation) {
        this.originalStation = originalStation;
    }
    
    public String getParttimeStation() {
        return parttimeStation;
    }
    
    public void setParttimeStation(String parttimeStation) {
        this.parttimeStation = parttimeStation;
    }
    
    public String getContactorMobile() {
        return contactorMobile;
    }
    
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }
    
    public Integer getIsParttime() {
        return isParttime;
    }
    
    public void setIsParttime(Integer isParttime) {
        this.isParttime = isParttime;
    }
    
    public String getSelfEvalution() {
        return selfEvalution;
    }
    
    public void setSelfEvalution(String selfEvalution) {
        this.selfEvalution = selfEvalution;
    }
    
    public Double getBrandYear() {
        return brandYear;
    }
    
    public void setBrandYear(Double brandYear) {
        this.brandYear = brandYear;
    }
    private Integer authorityApprovalStatus;//审批状态 DECIMAL(8)
    private String originalStation;//原岗位 VARCHAR(30)@
    private String parttimeStation;//兼职岗位 VARCHAR(30)@
    private String contactorMobile;//电话 VARCHAR(30)@
    private Integer isParttime;//是否兼职 DECIMAL(8)@
    private String selfEvalution;//自我评价VARCHAR(600)@
    private Double brandYear;//年限 DECIMAL(4,1)

}
