
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CarownerDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月5日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.Date;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.IDNumber;
import com.yonyou.dms.function.utils.validate.define.Phone;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
 * 车主信息DTO
 * 
 * @author zhanshiwei
 * @date 2016年8月5日
 */

public class CarownerDTO {

    private Long ownerId;
    @Length(max=11) 
    private String  ownerNo;        // 车主编号
    @Required
    private Integer ownerProperty;  // 车主性质
    @Required
    @Length(max=120)
    private String  ownerName;      // 车主

    private String  ownerSpell;     // 车主拼音

    private Integer gender;         // 性别

    private Integer ctCode;         // 证件类型代码
    @IDNumber
    private String  certificateNo;  // 证件号码
    @Length(max=30) 
    private String  taxNo;          // 税号
    @Required
    private String  province;       // 省份
    @Required
    private String  city;           // 城市
    @Required
    private String  district;       // 区县
    @Required
    @Length(max=120) 
    private String  address;        // 地址
    @Required
    @ZipCode
    private String  zipCode;        // 邮编
   
    private String  phone;          // 电话
    @Phone
    private String  mobile;         // 手机

    private Date    birthday;       // 出生日期
    @Email
    private String  eMail;          // E_MAIL

    private Integer familyIncome;   // 月收入

    private Integer ownerMarriage;  // 婚姻状况

    private Integer eduLevel;       // 学历

    private String  hobby;          // 爱好

    private String  remark;         // 备注

    private String  ownerNoOld;     // 原车主编号
    @Digits(integer=10,fraction=2)
    private Double  prePay;         // 预收款
    @Digits(integer=10,fraction=2)
    private Double  arrearageAmount;// 欠款金额
    private VehicleDTO velDto;
    
    public VehicleDTO getVelDto() {
        return velDto;
    }

    
    public void setVelDto(VehicleDTO velDto) {
        this.velDto = velDto;
    }

    private Integer recordVersion;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    foundDate;

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerNo() {
        return ownerNo;
    }

    public void setOwnerNo(String ownerNo) {
        this.ownerNo = ownerNo == null ? null : ownerNo.trim();
    }

    public Integer getOwnerProperty() {
        return ownerProperty;
    }

    public void setOwnerProperty(Integer ownerProperty) {
        this.ownerProperty = ownerProperty;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOwnerSpell() {
        return ownerSpell;
    }

    public void setOwnerSpell(String ownerSpell) {
        this.ownerSpell = ownerSpell == null ? null : ownerSpell.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getCtCode() {
        return ctCode;
    }

    public void setCtCode(Integer ctCode) {
        this.ctCode = ctCode;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo == null ? null : certificateNo.trim();
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo == null ? null : taxNo.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail == null ? null : eMail.trim();
    }

    public Integer getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(Integer familyIncome) {
        this.familyIncome = familyIncome;
    }

    public Integer getOwnerMarriage() {
        return ownerMarriage;
    }

    public void setOwnerMarriage(Integer ownerMarriage) {
        this.ownerMarriage = ownerMarriage;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOwnerNoOld() {
        return ownerNoOld;
    }

    public void setOwnerNoOld(String ownerNoOld) {
        this.ownerNoOld = ownerNoOld == null ? null : ownerNoOld.trim();
    }

    public Double getPrePay() {
        return prePay;
    }

    public void setPrePay(Double prePay) {
        this.prePay = prePay;
    }

    public Double getArrearageAmount() {
        return arrearageAmount;
    }

    public void setArrearageAmount(Double arrearageAmount) {
        this.arrearageAmount = arrearageAmount;
    }

    public Integer getRecordVersion() {
        return recordVersion;
    }

    public void setRecordVersion(Integer recordVersion) {
        this.recordVersion = recordVersion;
    }
}
