
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADCS060Dto.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.DTO.gacfca;



/**
* TODO description
* @author LiGaoqi
* @date 2017年4月18日
*/

public class SADCS060Dto {
    private String entityCode;
    private String clientType;
    private String name;
    private String gender;
    private String cellphone;
    private String provinceId;
    private String cityId;
    private String district;
    private String address;
    private String postCode;
    private String idOrCompCode;
    private String email;
    private String dmsOwnerId;
    private String dealerCode;
    private String buyTime;
    private String vin;
    private String serviceAdviser;//专属经理id
    private String employeeName;//专属经理姓名
    private String mobile;
    private String wxBindTime;//专属经理绑定时间
    
    
    /**
     * @return the entityCode
     */
    public String getEntityCode() {
        return entityCode;
    }

    
    /**
     * @param entityCode the entityCode to set
     */
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    /**
     * @return the clientType
     */
    public String getClientType() {
        return clientType;
    }
    
    /**
     * @param clientType the clientType to set
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * @return the cellphone
     */
    public String getCellphone() {
        return cellphone;
    }
    
    /**
     * @param cellphone the cellphone to set
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    
    /**
     * @return the provinceId
     */
    public String getProvinceId() {
        return provinceId;
    }
    
    /**
     * @param provinceId the provinceId to set
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
    
    /**
     * @return the cityId
     */
    public String getCityId() {
        return cityId;
    }
    
    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
    
    /**
     * @return the district
     */
    public String getDistrict() {
        return district;
    }
    
    /**
     * @param district the district to set
     */
    public void setDistrict(String district) {
        this.district = district;
    }
    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return the postCode
     */
    public String getPostCode() {
        return postCode;
    }
    
    /**
     * @param postCode the postCode to set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
    
    /**
     * @return the idOrCompCode
     */
    public String getIdOrCompCode() {
        return idOrCompCode;
    }
    
    /**
     * @param idOrCompCode the idOrCompCode to set
     */
    public void setIdOrCompCode(String idOrCompCode) {
        this.idOrCompCode = idOrCompCode;
    }
    
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the dmsOwnerId
     */
    public String getDmsOwnerId() {
        return dmsOwnerId;
    }
    
    /**
     * @param dmsOwnerId the dmsOwnerId to set
     */
    public void setDmsOwnerId(String dmsOwnerId) {
        this.dmsOwnerId = dmsOwnerId;
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
     * @return the buyTime
     */
    public String getBuyTime() {
        return buyTime;
    }
    
    /**
     * @param buyTime the buyTime to set
     */
    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }
    
    /**
     * @return the vin
     */
    public String getVin() {
        return vin;
    }
    
    /**
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }
    
    /**
     * @return the serviceAdviser
     */
    public String getServiceAdviser() {
        return serviceAdviser;
    }
    
    /**
     * @param serviceAdviser the serviceAdviser to set
     */
    public void setServiceAdviser(String serviceAdviser) {
        this.serviceAdviser = serviceAdviser;
    }
    
    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }
    
    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }
    
    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    /**
     * @return the wxBindTime
     */
    public String getWxBindTime() {
        return wxBindTime;
    }
    
    /**
     * @param wxBindTime the wxBindTime to set
     */
    public void setWxBindTime(String wxBindTime) {
        this.wxBindTime = wxBindTime;
    }
    


}
