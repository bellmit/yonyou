/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : OrganizationDto.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.Date;

public class OrganizationDto {
    private Integer organizationId;
    private String dealerCode;
    private String parentOrgCode;
    private String orgCode;
    private String orgName;
    private String orgShortName;
    private String orgDesc;
    private String orgType;
//    private Integer recordVersion;
    private String createdBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
    private Integer isValid;
    private Integer ver;
    
    public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getOrganizationId() {
        return organizationId;
    }
    
    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getParentOrgCode() {
        return parentOrgCode;
    }
    
    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getorgShortName() {
        return orgShortName;
    }
    
    public void setorgShortName(String orgShortName) {
        this.orgShortName = orgShortName;
    }
    
    public String getOrgDesc() {
        return orgDesc;
    }
    
    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
    }
    
    public String getOrgType() {
        return orgType;
    }
    
    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
    
//    public Integer getRecordVersion() {
//        return recordVersion;
//    }
//    
//    public void setRecordVersion(Integer recordVersion) {
//        this.recordVersion = recordVersion;
//    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Date getUpdateAt() {
        return updateAt;
    }
    
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    
    public Integer getIsValid() {
        return isValid;
    }

    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    
    
}
