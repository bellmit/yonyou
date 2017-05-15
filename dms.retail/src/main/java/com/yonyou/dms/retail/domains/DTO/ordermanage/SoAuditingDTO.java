
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SoAuditingDTO.java
*
* @Author : xukl
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

/**
* 销售订单-审批记录vo
* @author xukl
* @date 2016年9月28日
*/
public class SoAuditingDTO {
    private Integer auditingResult;
    private String  auditingPostil; 
    private String  auditList;
    private String  businessTypeList;
    private String  isSpeedinessList;
    private String  soStatusList;
    private String  verList;
    private String  vinList;
    private String  treatmentAdvice;
    private String  auditByList;
    
    
    
    
    public String getAuditByList() {
        return auditByList;
    }



    
    public void setAuditByList(String auditByList) {
        this.auditByList = auditByList;
    }



    public String getTreatmentAdvice() {
        return treatmentAdvice;
    }


    
    public void setTreatmentAdvice(String treatmentAdvice) {
        this.treatmentAdvice = treatmentAdvice;
    }


    public String getVinList() {
        return vinList;
    }

    
    public void setVinList(String vinList) {
        this.vinList = vinList;
    }

    public Integer getAuditingResult() {
        return auditingResult;
    }
    
    public void setAuditingResult(Integer auditingResult) {
        this.auditingResult = auditingResult;
    }
    
    public String getAuditingPostil() {
        return auditingPostil;
    }
    
    public void setAuditingPostil(String auditingPostil) {
        this.auditingPostil = auditingPostil;
    }
    
    public String getAuditList() {
        return auditList;
    }
    
    public void setAuditList(String auditList) {
        this.auditList = auditList;
    }
    
    public String getBusinessTypeList() {
        return businessTypeList;
    }
    
    public void setBusinessTypeList(String businessTypeList) {
        this.businessTypeList = businessTypeList;
    }
    
    public String getIsSpeedinessList() {
        return isSpeedinessList;
    }
    
    public void setIsSpeedinessList(String isSpeedinessList) {
        this.isSpeedinessList = isSpeedinessList;
    }
    
    public String getSoStatusList() {
        return soStatusList;
    }
    
    public void setSoStatusList(String soStatusList) {
        this.soStatusList = soStatusList;
    }
    
    public String getVerList() {
        return verList;
    }
    
    public void setVerList(String verList) {
        this.verList = verList;
    }
    
}
