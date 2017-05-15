
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartDTO.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月22日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月22日
*/

public class BookPartDTO {

    private String obligatedNo;
    
    private String roNo;
    
    private String applicant;
    
    private String ownerName;
    
    private String owenNo;
    
    private String biaoji;
    
    private String isObligated;
    
    private String itemId;
    
    
    public String getItemId() {
        return itemId;
    }




    
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }




    public String getBiaoji() {
        return biaoji;
    }



    
    public void setBiaoji(String biaoji) {
        this.biaoji = biaoji;
    }



    
    public String getIsObligated() {
        return isObligated;
    }



    
    public void setIsObligated(String isObligated) {
        this.isObligated = isObligated;
    }



    public String getOwenNo() {
        return owenNo;
    }


    
    public void setOwenNo(String owenNo) {
        this.owenNo = owenNo;
    }

    private String license;
    
    private String deliverer;
    
    private String delivererMobile;
    
    private String delivererPhone;
    
    private String serviceAdvisor;
    
    private Date obligatedCloseCate;
    
    private String remark;
    
    
    public String getObligatedNo() {
        return obligatedNo;
    }

    
    public void setObligatedNo(String obligatedNo) {
        this.obligatedNo = obligatedNo;
    }

    
    public String getRoNo() {
        return roNo;
    }

    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    
    public String getApplicant() {
        return applicant;
    }

    
    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    
    public String getOwnerName() {
        return ownerName;
    }

    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    
    public String getLicense() {
        return license;
    }

    
    public void setLicense(String license) {
        this.license = license;
    }

    
    public String getDeliverer() {
        return deliverer;
    }

    
    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer;
    }

    
    public String getDelivererMobile() {
        return delivererMobile;
    }

    
    public void setDelivererMobile(String delivererMobile) {
        this.delivererMobile = delivererMobile;
    }

    
    public String getDelivererPhone() {
        return delivererPhone;
    }

    
    public void setDelivererPhone(String delivererPhone) {
        this.delivererPhone = delivererPhone;
    }

    
    public String getServiceAdvisor() {
        return serviceAdvisor;
    }

    
    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor;
    }

    
    public Date getObligatedCloseCate() {
        return obligatedCloseCate;
    }

    
    public void setObligatedCloseCate(Date obligatedCloseCate) {
        this.obligatedCloseCate = obligatedCloseCate;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public List getTables() {
        return tables;
    }

    
    public void setTables(List tables) {
        this.tables = tables;
    }

    private List tables;

 

}
