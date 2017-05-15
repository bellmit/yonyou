
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : DealerBasicinfoDTO1.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月13日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月13日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.ZipCode;
/**
 * 
 * 经销商信息DTO
 * @author ZhengHe
 * @date 2016年7月6日
 */
public class DealerBasicinfoDTO {
    private Long dealerId;

    private String dealerCode;

    private String dealerShortname;

    private String dealerName;

    private String province;

    private String city;

    private String county;

    private String property;

    @Required
    @Email
    private String eMail;

    @Required
    @ZipCode
    private String zipCode;

    @Required
    private String fax;

    private Date openDate;

    @Required
    private String hotLine;

    @Required
    private String salesLine;

    @Required
    private String serviceLine;

    private String address;

    private String businessHours;

    private Date createdDate;
    
    private Integer grade;

    private Integer dealerStatus;

    private String companyHomepage;

    private String remark;
    
    private Date belongDate;
    
    private String bank;
    
    private String bankAccount;
    
    private String taxNo;
    
    private Double fixedAssets;
    
    private Double circulatingFund;
    
    private Integer engineerNum;
    
    private Integer serviceworkerNum;
    
    private Integer employeeNum;
    
    private Double ascArea;
    
    private Double serviceBayNumber;
    
    private String bookingPhone;

    
    
    
    public Integer getGrade() {
        return grade;
    }


    
    public void setGrade(Integer grade) {
        this.grade = grade;
    }


    public Date getBelongDate() {
        return belongDate;
    }

    
    public void setBelongDate(Date belongDate) {
        this.belongDate = belongDate;
    }

    
    public String getBank() {
        return bank;
    }

    
    public void setBank(String bank) {
        this.bank = bank;
    }

    
    public String getBankAccount() {
        return bankAccount;
    }

    
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    
    public String getTaxNo() {
        return taxNo;
    }

    
    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    
    public Double getFixedAssets() {
        return fixedAssets;
    }

    
    public void setFixedAssets(Double fixedAssets) {
        this.fixedAssets = fixedAssets;
    }

    
    public Double getCirculatingFund() {
        return circulatingFund;
    }

    
    public void setCirculatingFund(Double circulatingFund) {
        this.circulatingFund = circulatingFund;
    }

    
    public Integer getEngineerNum() {
        return engineerNum;
    }

    
    public void setEngineerNum(Integer engineerNum) {
        this.engineerNum = engineerNum;
    }

    
    public Integer getServiceworkerNum() {
        return serviceworkerNum;
    }

    
    public void setServiceworkerNum(Integer serviceworkerNum) {
        this.serviceworkerNum = serviceworkerNum;
    }

    
    public Integer getEmployeeNum() {
        return employeeNum;
    }

    
    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    
    public Double getAscArea() {
        return ascArea;
    }

    
    public void setAscArea(Double ascArea) {
        this.ascArea = ascArea;
    }

    
    public Double getServiceBayNumber() {
        return serviceBayNumber;
    }

    
    public void setServiceBayNumber(Double serviceBayNumber) {
        this.serviceBayNumber = serviceBayNumber;
    }

    
    public String getBookingPhone() {
        return bookingPhone;
    }

    
    public void setBookingPhone(String bookingPhone) {
        this.bookingPhone = bookingPhone;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode ;
    }

    public String getDealerShortname() {
        return dealerShortname;
    }

    public void setDealerShortname(String dealerShortname) {
        this.dealerShortname = dealerShortname;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province ;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city ;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county ;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property ;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail ;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax ;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public String getHotLine() {
        return hotLine;
    }

    public void setHotLine(String hotLine) {
        this.hotLine = hotLine ;
    }

    public String getSalesLine() {
        return salesLine;
    }

    public void setSalesLine(String salesLine) {
        this.salesLine = salesLine;
    }

    public String getServiceLine() {
        return serviceLine;
    }

    public void setServiceLine(String serviceLine) {
        this.serviceLine = serviceLine ;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address ;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours ;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getDealerStatus() {
        return dealerStatus;
    }

    public void setDealerStatus(Integer dealerStatus) {
        this.dealerStatus = dealerStatus;
    }

    public String getCompanyHomepage() {
        return companyHomepage;
    }

    public void setCompanyHomepage(String companyHomepage) {
        this.companyHomepage = companyHomepage ;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark ;
    }
}
