
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TtCustomerLinkmanInfoDTO.java
*
* @Author : Administrator
*
* @Date : 2017年1月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月1日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

/**
* TODO description
* @author Administrator
* @date 2017年1月1日
*/

public class TtCustomerLinkmanInfoDTO {

    private String customerNo;
    private String dealerCode;
    private String company;
    private String contactorDepartment;
    private String positionName;
    private String contactorName;
    private String phone;
    private String mobile;
    private String fax;
    private String eMail;
    private String remark;
    
    private Date ddcnUpdateDate;

    
    private long itemId;
    private long isDefaultContactor;
    private long bestContactType;
    private long bestContactTime;
    private long gender;
    private long contactorType;
    private long ownedBy;
    private long dKey;
    private long ver;
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getCompany() {
        return company;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public String getContactorDepartment() {
        return contactorDepartment;
    }
    
    public void setContactorDepartment(String contactorDepartment) {
        this.contactorDepartment = contactorDepartment;
    }
    
    public String getPositionName() {
        return positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
    public String getContactorName() {
        return contactorName;
    }
    
    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String geteMail() {
        return eMail;
    }
    
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }
    
    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
    public long getItemId() {
        return itemId;
    }
    
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    
    public long getIsDefaultContactor() {
        return isDefaultContactor;
    }
    
    public void setIsDefaultContactor(long isDefaultContactor) {
        this.isDefaultContactor = isDefaultContactor;
    }
    
    public long getBestContactType() {
        return bestContactType;
    }
    
    public void setBestContactType(long bestContactType) {
        this.bestContactType = bestContactType;
    }
    
    public long getBestContactTime() {
        return bestContactTime;
    }
    
    public void setBestContactTime(long bestContactTime) {
        this.bestContactTime = bestContactTime;
    }
    
    public long getGender() {
        return gender;
    }
    
    public void setGender(long gender) {
        this.gender = gender;
    }
    
    public long getContactorType() {
        return contactorType;
    }
    
    public void setContactorType(long contactorType) {
        this.contactorType = contactorType;
    }
    
    public long getOwnedBy() {
        return ownedBy;
    }
    
    public void setOwnedBy(long ownedBy) {
        this.ownedBy = ownedBy;
    }
    
    public long getdKey() {
        return dKey;
    }
    
    public void setdKey(long dKey) {
        this.dKey = dKey;
    }
    
    public long getVer() {
        return ver;
    }
    
    public void setVer(long ver) {
        this.ver = ver;
    }

}
