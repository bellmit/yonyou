
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : PotentialCustomerImportDTO.java
*
* @Author : LiGaoqi
*
* @Date : 2017年2月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月27日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;


/**
* TODO description
* @author LiGaoqi
* @date 2017年2月27日
*/

public class PotentialCustomerImportDTO extends DataImportDto {
 // 文件上传的ID
    private String dmsFileIds;
    @ExcelColumnDefine(value = 1)
    private String customerName;
    @ExcelColumnDefine(value = 2, dataType = ExcelDataType.Dict, dataCode = 1006)
    private Integer gender;
    @ExcelColumnDefine(value = 3, dataType = ExcelDataType.Dict, dataCode = 1018)
    private Integer customerType;
    @ExcelColumnDefine(value = 4)
    private String  contactorMobile;
    @ExcelColumnDefine(value = 5, dataType = ExcelDataType.Dict, dataCode = 1311)
    private Integer cusSource;
    @ExcelColumnDefine(value = 6, dataType = ExcelDataType.Dict, dataCode = 1310)
    private Integer intentLevel;
    @ExcelColumnDefine(value = 7)
    private String  soldBy;
    @ExcelColumnDefine(value = 8, dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer isToShop;
    @ExcelColumnDefine(value = 9, dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer isTestDrive;
    @ExcelColumnDefine(value = 10)
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)  
    private Date testDriveDate;
    @ExcelColumnDefine(value = 11)
    private String intentModel;
    
    /**
     * @return the dmsFileIds
     */
    public String getDmsFileIds() {
        return dmsFileIds;
    }
    
    /**
     * @param dmsFileIds the dmsFileIds to set
     */
    public void setDmsFileIds(String dmsFileIds) {
        this.dmsFileIds = dmsFileIds;
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
     * @return the gender
     */
    public Integer getGender() {
        return gender;
    }
    
    /**
     * @param gender the gender to set
     */
    public void setGender(Integer gender) {
        this.gender = gender;
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
     * @return the contactorMobile
     */
    public String getContactorMobile() {
        return contactorMobile;
    }
    
    /**
     * @param contactorMobile the contactorMobile to set
     */
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }
    
    /**
     * @return the cusSource
     */
    public Integer getCusSource() {
        return cusSource;
    }
    
    /**
     * @param cusSource the cusSource to set
     */
    public void setCusSource(Integer cusSource) {
        this.cusSource = cusSource;
    }
    
    /**
     * @return the intentLevel
     */
    public Integer getIntentLevel() {
        return intentLevel;
    }
    
    /**
     * @param intentLevel the intentLevel to set
     */
    public void setIntentLevel(Integer intentLevel) {
        this.intentLevel = intentLevel;
    }
    
    
    /**
     * @return the soldBy
     */
    public String getSoldBy() {
        return soldBy;
    }
    
    /**
     * @param soldBy the soldBy to set
     */
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
    
    /**
     * @return the isToShop
     */
    public Integer getIsToShop() {
        return isToShop;
    }
    
    /**
     * @param isToShop the isToShop to set
     */
    public void setIsToShop(Integer isToShop) {
        this.isToShop = isToShop;
    }
    
    /**
     * @return the isTestDrive
     */
    public Integer getIsTestDrive() {
        return isTestDrive;
    }
    
    /**
     * @param isTestDrive the isTestDrive to set
     */
    public void setIsTestDrive(Integer isTestDrive) {
        this.isTestDrive = isTestDrive;
    }
    
    /**
     * @return the testDriveDat
     */
    public Date getTestDriveDate() {
        return testDriveDate;
    }
    
    /**
     * @param testDriveDat the testDriveDat to set
     */
    public void setTestDriveDate(Date testDriveDate) {
        this.testDriveDate = testDriveDate;
    }
    
    /**
     * @return the intentModel
     */
    public String getIntentModel() {
        return intentModel;
    }
    
    /**
     * @param intentModel the intentModel to set
     */
    public void setIntentModel(String intentModel) {
        this.intentModel = intentModel;
    }
    
    
    
}
