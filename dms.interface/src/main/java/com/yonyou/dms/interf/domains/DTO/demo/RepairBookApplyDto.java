/*
* Copyright 2016 Yonyou Auto Information Technology（Shanghai） Co., Ltd. All Rights Reserved.
*
* This software is published under the terms of the YONYOU Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : marketing-service-interface
*
* @File name : RepairBookingApplyDto.java
*
* @Author : sangdeliang
*
* @Date : 2016年12月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月5日    sangdeliang    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.interf.domains.DTO.demo;

import java.io.Serializable;

/**
*
* @author zhangxc
* @ description 养修预约申请DTO
* @date 2016年12月5日
*/

public class RepairBookApplyDto  implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 8026678747703639486L;
    //预约日期(例:2016-10-01)
    private String bookDate;
    //预约时间(例:8:00)
    private String bookTime;
    //车牌
    private String carLicence;
    //姓名
    private String customerName;
    //手机
    private String customerMobile;
    //预约类型
    private String bookType; 
    //系统标志
    private String systemId;
    //系统来源
    private String sourceType;
    //经销商编号
    private String dealerCode;
    //预约单号(微信端生成)
    private String customerBookNo;
    //品牌名称(非必填)
    private String brandName;
    //车系名称(非必填)
    private String seriesName;
    //车型名称(非必填)
    private String modelName;
    //车名称(非必填)
    private String packageCode;
    
    /**
     * @return the bookDate
     */
    public String getBookDate() {
        return bookDate;
    }
    
    /**
     * @param bookDate the bookDate to set
     */
    public void setBookDate(String bookDate) {
        this.bookDate = bookDate;
    }
    
    /**
     * @return the bookTime
     */
    public String getBookTime() {
        return bookTime;
    }
    
    /**
     * @param bookTime the bookTime to set
     */
    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }
    
    /**
     * @return the carLicence
     */
    public String getCarLicence() {
        return carLicence;
    }
    
    /**
     * @param carLicence the carLicence to set
     */
    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }
    
    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /**
     * @return the customerMobile
     */
    public String getCustomerMobile() {
        return customerMobile;
    }
    
    /**
     * @param customerMobile the customerMobile to set
     */
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
    
    /**
     * @return the bookType
     */
    public String getBookType() {
        return bookType;
    }
    
    /**
     * @param bookType the bookType to set
     */
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }
    
    /**
     * @return the systemId
     */
    public String getSystemId() {
        return systemId;
    }
    
    /**
     * @param systemId the systemId to set
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    
    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }
    
    /**
     * @param sourceType the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    
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
     * @return the customerBookNo
     */
    public String getCustomerBookNo() {
        return customerBookNo;
    }
    
    /**
     * @param customerBookNo the customerBookNo to set
     */
    public void setCustomerBookNo(String customerBookNo) {
        this.customerBookNo = customerBookNo;
    }
    
    /**
     * @return the brandName
     */
    public String getBrandName() {
        return brandName;
    }
    
    /**
     * @param brandName the brandName to set
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    /**
     * @return the seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }
    
    /**
     * @param seriesName the seriesName to set
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
    
    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }
    
    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    /**
     * @return the packageCode
     */
    public String getPackageCode() {
        return packageCode;
    }
    
    /**
     * @param packageCode the packageCode to set
     */
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    
}
