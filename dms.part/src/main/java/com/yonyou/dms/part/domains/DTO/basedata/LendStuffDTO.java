
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : LendStuffDTO.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月12日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月12日
*/

public class LendStuffDTO {
    
    private String iteId;

    private String roNo;
    
    private String license;
    
    private String roType;
    
    private String model;
    
    private String serviceAdvisor;
    
    private String roCreateDate;
    
    private String ownerName;
    
    private String chiefTechnician;

    private List<Map> tables;

    public String getIteId() {
        return iteId;
    }

    
    public void setIteId(String iteId) {
        this.iteId = iteId;
    }
    
    public String getRoNo() {
        return roNo;
    }

    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }

    
    public String getLicense() {
        return license;
    }

    
    public void setLicense(String license) {
        this.license = license;
    }

    
    public String getRoType() {
        return roType;
    }

    
    public void setRoType(String roType) {
        this.roType = roType;
    }

    
    public String getModel() {
        return model;
    }

    
    public void setModel(String model) {
        this.model = model;
    }

    
    public String getServiceAdvisor() {
        return serviceAdvisor;
    }

    
    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor;
    }

    
    public String getRoCreateDate() {
        return roCreateDate;
    }

    
    public void setRoCreateDate(String roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    
    public String getOwnerName() {
        return ownerName;
    }

    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    
    public String getChiefTechnician() {
        return chiefTechnician;
    }

    
    public void setChiefTechnician(String chiefTechnician) {
        this.chiefTechnician = chiefTechnician;
    }

    
    public List<Map> getTables() {
        return tables;
    }

    
    public void setTables(List<Map> tables) {
        this.tables = tables;
    }

}
