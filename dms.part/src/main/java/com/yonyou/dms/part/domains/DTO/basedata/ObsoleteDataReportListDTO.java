
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ObsoleteDataReportListDTO.java
*
* @Author : Administrator
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年4月18日
 */

public class ObsoleteDataReportListDTO {

    private String  dealerCode;
    private Long    itemId;
    private String  storageCode;
    private String  storagePositionCode;
    private String  partNo;
    private String  partName;
    private String  unitCode;
    private Integer reportedNumber;
    private Double  reportedPrice;
    private Double  reportedTotal;
    private Double  costPrice;
    private Double  salesPrice;
    private String  reportPerson;
    private Float   realTimeNumber;
    private Date    reportedDate;
    private Date    expirationDate;
    private Date    cancelDate;
    private Date    shelfDate;
    private Integer dKey;
    private Integer ver;
    
    /**
     * @return the dealerCode
     */
    public String getDealerCode() {
        return dealerCode;
    }
    
    /**
     * @param dealerCode the dealerCode to set
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    /**
     * @return the itemId
     */
    public Long getItemId() {
        return itemId;
    }
    
    /**
     * @param itemId the itemId to set
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    /**
     * @return the storageCode
     */
    public String getStorageCode() {
        return storageCode;
    }
    
    /**
     * @param storageCode the storageCode to set
     */
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
    
    /**
     * @return the storagePositionCode
     */
    public String getStoragePositionCode() {
        return storagePositionCode;
    }
    
    /**
     * @param storagePositionCode the storagePositionCode to set
     */
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }
    
    /**
     * @return the partNo
     */
    public String getPartNo() {
        return partNo;
    }
    
    /**
     * @param partNo the partNo to set
     */
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    /**
     * @return the partName
     */
    public String getPartName() {
        return partName;
    }
    
    /**
     * @param partName the partName to set
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    /**
     * @return the unitCode
     */
    public String getUnitCode() {
        return unitCode;
    }
    
    /**
     * @param unitCode the unitCode to set
     */
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    
    /**
     * @return the reportedNumber
     */
    public Integer getReportedNumber() {
        return reportedNumber;
    }
    
    /**
     * @param reportedNumber the reportedNumber to set
     */
    public void setReportedNumber(Integer reportedNumber) {
        this.reportedNumber = reportedNumber;
    }
    
    /**
     * @return the reportedPrice
     */
    public Double getReportedPrice() {
        return reportedPrice;
    }
    
    /**
     * @param reportedPrice the reportedPrice to set
     */
    public void setReportedPrice(Double reportedPrice) {
        this.reportedPrice = reportedPrice;
    }
    
    /**
     * @return the reportedTotal
     */
    public Double getReportedTotal() {
        return reportedTotal;
    }
    
    /**
     * @param reportedTotal the reportedTotal to set
     */
    public void setReportedTotal(Double reportedTotal) {
        this.reportedTotal = reportedTotal;
    }
    
    /**
     * @return the reportedDate
     */
    public Date getReportedDate() {
        return reportedDate;
    }
    
    /**
     * @param reportedDate the reportedDate to set
     */
    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }
    
    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }
    
    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    
    /**
     * @return the reportPerson
     */
    public String getReportPerson() {
        return reportPerson;
    }
    
    /**
     * @param reportPerson the reportPerson to set
     */
    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson;
    }
    
    /**
     * @return the realTimeNumber
     */
    public Float getRealTimeNumber() {
        return realTimeNumber;
    }
    
    /**
     * @param realTimeNumber the realTimeNumber to set
     */
    public void setRealTimeNumber(Float realTimeNumber) {
        this.realTimeNumber = realTimeNumber;
    }
    
    /**
     * @return the cancelDate
     */
    public Date getCancelDate() {
        return cancelDate;
    }
    
    /**
     * @param cancelDate the cancelDate to set
     */
    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }
    
    /**
     * @return the shelfDate
     */
    public Date getShelfDate() {
        return shelfDate;
    }
    
    /**
     * @param shelfDate the shelfDate to set
     */
    public void setShelfDate(Date shelfDate) {
        this.shelfDate = shelfDate;
    }
    
    /**
     * @return the dKey
     */
    public Integer getdKey() {
        return dKey;
    }
    
    /**
     * @param dKey the dKey to set
     */
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }
    
    /**
     * @return the ver
     */
    public Integer getVer() {
        return ver;
    }
    
    /**
     * @param ver the ver to set
     */
    public void setVer(Integer ver) {
        this.ver = ver;
    }

    
    /**
     * @return the costPrice
     */
    public Double getCostPrice() {
        return costPrice;
    }

    
    /**
     * @param costPrice the costPrice to set
     */
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    
    /**
     * @return the salesPrice
     */
    public Double getSalesPrice() {
        return salesPrice;
    }

    
    /**
     * @param salesPrice the salesPrice to set
     */
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    
    /**
    * @author Administrator
    * @date 2017年4月19日
    * @return
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
    	
    @Override
    public String toString() {
        return "ObsoleteDataReportListDTO [dealerCode=" + dealerCode + ", itemId=" + itemId + ", storageCode="
               + storageCode + ", storagePositionCode=" + storagePositionCode + ", partNo=" + partNo + ", partName="
               + partName + ", unitCode=" + unitCode + ", reportedNumber=" + reportedNumber + ", reportedPrice="
               + reportedPrice + ", reportedTotal=" + reportedTotal + ", costPrice=" + costPrice + ", salesPrice="
               + salesPrice + ", reportPerson=" + reportPerson + ", realTimeNumber=" + realTimeNumber
               + ", reportedDate=" + reportedDate + ", expirationDate=" + expirationDate + ", cancelDate=" + cancelDate
               + ", shelfDate=" + shelfDate + ", dKey=" + dKey + ", ver=" + ver + "]";
    }

    
    
    
}
