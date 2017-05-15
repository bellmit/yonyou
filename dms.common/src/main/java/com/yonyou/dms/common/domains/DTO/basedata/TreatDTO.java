
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : TreatDTO.java
*
* @Author : Administrator
*
* @Date : 2017年1月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月10日    Administrator    1.0
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
* @date 2017年1月10日
*/

public class TreatDTO {
    private String    vin;
    private String    customerName;
    private Integer    createType;
    private String    crLinker;
    
    private String    linkPhone;
    private String    linkMobile;
    private Date    cheduleDate;
    private Date    actionDate;
    
    private Integer    crType;
    private String    crName;
    private Integer    crResult;
    private String    soldBy;
    
    private String    crScene;
    private String    crContext;
    
    private String  customerNo;
    
    
    public String getCustomerNo() {
        return customerNo;
    }

    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getVin() {
        return vin;
    }
    
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Integer getCreateType() {
        return createType;
    }
    
    public void setCreateType(Integer createType) {
        this.createType = createType;
    }
    
    public String getCrLinker() {
        return crLinker;
    }
    
    public void setCrLinker(String crLinker) {
        this.crLinker = crLinker;
    }
    
    public String getLinkPhone() {
        return linkPhone;
    }
    
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
    
    public String getLinkMobile() {
        return linkMobile;
    }
    
    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile;
    }
    
    public Date getCheduleDate() {
        return cheduleDate;
    }
    
    public void setCheduleDate(Date cheduleDate) {
        this.cheduleDate = cheduleDate;
    }
    
    public Date getActionDate() {
        return actionDate;
    }
    
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
    
    public Integer getCrType() {
        return crType;
    }
    
    public void setCrType(Integer crType) {
        this.crType = crType;
    }
    
    public String getCrName() {
        return crName;
    }
    
    public void setCrName(String crName) {
        this.crName = crName;
    }
    
    public Integer getCrResult() {
        return crResult;
    }
    
    public void setCrResult(Integer crResult) {
        this.crResult = crResult;
    }
    
    public String getSoldBy() {
        return soldBy;
    }
    
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
    
    public String getCrScene() {
        return crScene;
    }
    
    public void setCrScene(String crScene) {
        this.crScene = crScene;
    }
    
    public String getCrContext() {
        return crContext;
    }
    
    public void setCrContext(String crContext) {
        this.crContext = crContext;
    }

}
