
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ListTtAdPartParaDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
* TODO description
* @author chenwei
* @date 2017年4月14日
 */

public class ListAdMaintainDTO {

    private List<TtMaintainTableDTO> maintainPickingTbl;
    private String[] oldPartPrice;
    private String roNo;
    private String license;
    private String roType;
    private String repairTypeCode;
    private String model;
    private String serviceAdvisor;
    private String vin;
    private String ownerName;
    private char ownerNo;
    private Date roCreateDate;
    private String chiefTechnician;
    private Double edtMoney;
    private Double cxAddRate;
    private Double edtFactMoney;
    private Double edtCount;
    private Double cxCurrencyEditDiscount;
    private String sendTime;

    
    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo;
    }


    public String[] getOldPartPrice() {
        return oldPartPrice;
    }

    
    public void setOldPartPrice(String[] oldPartPrice) {
        this.oldPartPrice = oldPartPrice;
    }

    public char getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(char ownerNo) {
        this.ownerNo = ownerNo;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }



    public List<TtMaintainTableDTO> getMaintainPickingTbl() {
        return maintainPickingTbl;
    }


    
    public void setMaintainPickingTbl(List<TtMaintainTableDTO> maintainPickingTbl) {
        this.maintainPickingTbl = maintainPickingTbl;
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

    
    public String getRepairTypeCode() {
        return repairTypeCode;
    }

    
    public void setRepairTypeCode(String repairTypeCode) {
        this.repairTypeCode = repairTypeCode;
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

    
    public String getVin() {
        return vin;
    }

    
    public void setVin(String vin) {
        this.vin = vin;
    }

    
    public String getOwnerName() {
        return ownerName;
    }

    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    
    public Date getRoCreateDate() {
        return roCreateDate;
    }

    
    public void setRoCreateDate(Date roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    
    public String getChiefTechnician() {
        return chiefTechnician;
    }

    
    public void setChiefTechnician(String chiefTechnician) {
        this.chiefTechnician = chiefTechnician;
    }

    
    public Double getEdtMoney() {
        return edtMoney;
    }

    
    public void setEdtMoney(Double edtMoney) {
        this.edtMoney = edtMoney;
    }

    
    public Double getCxAddRate() {
        return cxAddRate;
    }

    
    public void setCxAddRate(Double cxAddRate) {
        this.cxAddRate = cxAddRate;
    }

    
    public Double getEdtFactMoney() {
        return edtFactMoney;
    }

    
    public void setEdtFactMoney(Double edtFactMoney) {
        this.edtFactMoney = edtFactMoney;
    }

    
    public Double getEdtCount() {
        return edtCount;
    }

    
    public void setEdtCount(Double edtCount) {
        this.edtCount = edtCount;
    }

    
    public Double getCxCurrencyEditDiscount() {
        return cxCurrencyEditDiscount;
    }

    
    public void setCxCurrencyEditDiscount(Double cxCurrencyEditDiscount) {
        this.cxCurrencyEditDiscount = cxCurrencyEditDiscount;
    }

}
