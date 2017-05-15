
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADCS049Dto.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月19日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
* TODO description
* @author LiGaoqi
* @date 2017年4月19日
*/

public class SADCS049Dto {
    
    private String dealerCode;//经销商代码
    private String customerNo;
    private String customerProvince;//客户所在地省份
    private String customerCity;//客户所在地城市
    private Integer customerType;//客户状态
    private String intentionBrandCode;//意向品牌
    private String intentionSeriesCode;//意向车系
    private String intentionModelCode;//意向车型
    private String usedCarBrandCode;//二手车品牌
    private String usedCarSeriesCode;//二手车车系
    private String usedCarModelCode;//二手车车型
    private String usedCarLicense;//二手车车牌
    private String usedCarVin;//二手车VIN
    private Double usedCarAssessAmount;//二手车评估金额
    private Date usedCarLicenseDate;//二手车上次上牌日期（YYYY-MM-DD）
    private Long usedCarMileage;//二手车里程数
    private String usedCarDescribe;//二手车描述
    private Integer reportType;//数据状态1(新增)2（修改）3（删除）;
    private Long itemId;//ID
    private Date intentionDate;//意向时间（YYYY-MM-DD）
    
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
     * @return the customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }
    
    /**
     * @param customerNo the customerNo to set
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    /**
     * @return the customerProvince
     */
    public String getCustomerProvince() {
        return customerProvince;
    }
    
    /**
     * @param customerProvince the customerProvince to set
     */
    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }
    
    /**
     * @return the customerCity
     */
    public String getCustomerCity() {
        return customerCity;
    }
    
    /**
     * @param customerCity the customerCity to set
     */
    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }
    
    /**
     * @return the customerType
     */
    public Integer getCustomerType() {
        return customerType;
    }
    
    /**
     * @param customerType the customerType to set
     */
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    /**
     * @return the intentionBrandCode
     */
    public String getIntentionBrandCode() {
        return intentionBrandCode;
    }
    
    /**
     * @param intentionBrandCode the intentionBrandCode to set
     */
    public void setIntentionBrandCode(String intentionBrandCode) {
        this.intentionBrandCode = intentionBrandCode;
    }
    
    /**
     * @return the intentionSeriesCode
     */
    public String getIntentionSeriesCode() {
        return intentionSeriesCode;
    }
    
    /**
     * @param intentionSeriesCode the intentionSeriesCode to set
     */
    public void setIntentionSeriesCode(String intentionSeriesCode) {
        this.intentionSeriesCode = intentionSeriesCode;
    }
    
    /**
     * @return the intentionModelCode
     */
    public String getIntentionModelCode() {
        return intentionModelCode;
    }
    
    /**
     * @param intentionModelCode the intentionModelCode to set
     */
    public void setIntentionModelCode(String intentionModelCode) {
        this.intentionModelCode = intentionModelCode;
    }
    
    /**
     * @return the usedCarBrandCode
     */
    public String getUsedCarBrandCode() {
        return usedCarBrandCode;
    }
    
    /**
     * @param usedCarBrandCode the usedCarBrandCode to set
     */
    public void setUsedCarBrandCode(String usedCarBrandCode) {
        this.usedCarBrandCode = usedCarBrandCode;
    }
    
    /**
     * @return the usedCarSeriesCode
     */
    public String getUsedCarSeriesCode() {
        return usedCarSeriesCode;
    }
    
    /**
     * @param usedCarSeriesCode the usedCarSeriesCode to set
     */
    public void setUsedCarSeriesCode(String usedCarSeriesCode) {
        this.usedCarSeriesCode = usedCarSeriesCode;
    }
    
    /**
     * @return the usedCarModelCode
     */
    public String getUsedCarModelCode() {
        return usedCarModelCode;
    }
    
    /**
     * @param usedCarModelCode the usedCarModelCode to set
     */
    public void setUsedCarModelCode(String usedCarModelCode) {
        this.usedCarModelCode = usedCarModelCode;
    }
    
    /**
     * @return the usedCarLicense
     */
    public String getUsedCarLicense() {
        return usedCarLicense;
    }
    
    /**
     * @param usedCarLicense the usedCarLicense to set
     */
    public void setUsedCarLicense(String usedCarLicense) {
        this.usedCarLicense = usedCarLicense;
    }
    
    /**
     * @return the usedCarVin
     */
    public String getUsedCarVin() {
        return usedCarVin;
    }
    
    /**
     * @param usedCarVin the usedCarVin to set
     */
    public void setUsedCarVin(String usedCarVin) {
        this.usedCarVin = usedCarVin;
    }
    
    /**
     * @return the usedCarAssessAmount
     */
    public Double getUsedCarAssessAmount() {
        return usedCarAssessAmount;
    }
    
    /**
     * @param usedCarAssessAmount the usedCarAssessAmount to set
     */
    public void setUsedCarAssessAmount(Double usedCarAssessAmount) {
        this.usedCarAssessAmount = usedCarAssessAmount;
    }
    
    /**
     * @return the usedCarLicenseDate
     */
    public Date getUsedCarLicenseDate() {
        return usedCarLicenseDate;
    }
    
    /**
     * @param usedCarLicenseDate the usedCarLicenseDate to set
     */
    public void setUsedCarLicenseDate(Date usedCarLicenseDate) {
        this.usedCarLicenseDate = usedCarLicenseDate;
    }
    
    /**
     * @return the usedCarMileage
     */
    public Long getUsedCarMileage() {
        return usedCarMileage;
    }
    
    /**
     * @param usedCarMileage the usedCarMileage to set
     */
    public void setUsedCarMileage(Long usedCarMileage) {
        this.usedCarMileage = usedCarMileage;
    }
    
    /**
     * @return the usedCarDescribe
     */
    public String getUsedCarDescribe() {
        return usedCarDescribe;
    }
    
    /**
     * @param usedCarDescribe the usedCarDescribe to set
     */
    public void setUsedCarDescribe(String usedCarDescribe) {
        this.usedCarDescribe = usedCarDescribe;
    }
    
    /**
     * @return the reportType
     */
    public Integer getReportType() {
        return reportType;
    }
    
    /**
     * @param reportType the reportType to set
     */
    public void setReportType(Integer reportType) {
        this.reportType = reportType;
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
     * @return the intentionDate
     */
    public Date getIntentionDate() {
        return intentionDate;
    }
    
    /**
     * @param intentionDate the intentionDate to set
     */
    public void setIntentionDate(Date intentionDate) {
        this.intentionDate = intentionDate;
    }
    
    

}
