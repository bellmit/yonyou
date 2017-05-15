
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoDTO.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.basedata;

import java.util.Date;

/**
 * 业务往来客户资料DTO
 * 
* @Author : chenwei
*
* @Date : 2017年3月28日
 */

public class BusinessCustomerDTO {

	
	private String  dealerCode;//经销商代码

    private String  customerCode;      // 客户代码

    private String  customerTypeCode;      // 客户类别代码
    
    private String customerName;//客户名称
    
    private String customerSpell;//客户拼音
    
    private String customerShortName;//客户简称
    
    private String address;//地址
    
    private String zipCode;//邮编
    
    private String contactorName;//联系人
    
    private String contactorPhone;//联系人电话
    
    private String contactorMobile;//联系人手机
    
    private String fax;//传真
    
    private String contractNo;//合约编号
    
    private Date agreementBeginDate;//签约开始日期
    
    private Date agreementEndDate;//签约结束日期
    
    private double priceAddRate;//加价率
    
    private double priceRate;//价格系数
    
    private double salesBasePriceType;//销售基价
    
    private double creditAmount;//信用(元)
    
    private double totalArrearageAmount;//累计欠款(元)
    
    private double accountTerm;//帐期(天)
    
    private double totalArrearageTerm;//累计欠款(天)
    
    private double prePay;//预收款
    
    private double arrearageAmount;//欠款金额
    
    private double accountAge;//帐龄
    
    private Integer oemTag;//OEM标志
    
    private double cusReceiveSort;//客户收款类别
    
    private char balObjCode;//结算对象代码
    
    private String balObjName;//结算对象名称
    
    private double leadTime;//订货周期
    
    private String dealer2Code;//维修站代码
    
    private Integer isValid;//是否有效

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    
    public String getCustomerCode() {
        return customerCode;
    }

    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    
    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    
    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
    public String getCustomerSpell() {
        return customerSpell;
    }

    
    public void setCustomerSpell(String customerSpell) {
        this.customerSpell = customerSpell;
    }

    
    public String getAddress() {
        return address;
    }

    
    public void setAddress(String address) {
        this.address = address;
    }

    
    public String getZipCode() {
        return zipCode;
    }

    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    
    public String getContactorName() {
        return contactorName;
    }

    
    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    
    public String getContactorPhone() {
        return contactorPhone;
    }

    
    public void setContactorPhone(String contactorPhone) {
        this.contactorPhone = contactorPhone;
    }

    
    public String getContactorMobile() {
        return contactorMobile;
    }

    
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }

    
    public String getFax() {
        return fax;
    }

    
    public void setFax(String fax) {
        this.fax = fax;
    }

    
    public String getContractNo() {
        return contractNo;
    }

    
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    
    public Date getAgreementBeginDate() {
        return agreementBeginDate;
    }

    
    public void setAgreementBeginDate(Date agreementBeginDate) {
        this.agreementBeginDate = agreementBeginDate;
    }

    
    public Date getAgreementEndDate() {
        return agreementEndDate;
    }

    
    public void setAgreementEndDate(Date agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    
    public double getPriceAddRate() {
        return priceAddRate;
    }

    
    public void setPriceAddRate(double priceAddRate) {
        this.priceAddRate = priceAddRate;
    }

    
    public double getPriceRate() {
        return priceRate;
    }

    
    public void setPriceRate(double priceRate) {
        this.priceRate = priceRate;
    }

    
    public double getSalesBasePriceType() {
        return salesBasePriceType;
    }

    
    public void setSalesBasePriceType(double salesBasePriceType) {
        this.salesBasePriceType = salesBasePriceType;
    }

    
    public double getCreditAmount() {
        return creditAmount;
    }

    
    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }

    
    public double getTotalArrearageAmount() {
        return totalArrearageAmount;
    }

    
    public void setTotalArrearageAmount(double totalArrearageAmount) {
        this.totalArrearageAmount = totalArrearageAmount;
    }

    
    public double getAccountTerm() {
        return accountTerm;
    }

    
    public void setAccountTerm(double accountTerm) {
        this.accountTerm = accountTerm;
    }

    
    public double getTotalArrearageTerm() {
        return totalArrearageTerm;
    }

    
    public void setTotalArrearageTerm(double totalArrearageTerm) {
        this.totalArrearageTerm = totalArrearageTerm;
    }

    
    public double getPrePay() {
        return prePay;
    }

    
    public void setPrePay(double prePay) {
        this.prePay = prePay;
    }

    
    public double getArrearageAmount() {
        return arrearageAmount;
    }

    
    public void setArrearageAmount(double arrearageAmount) {
        this.arrearageAmount = arrearageAmount;
    }

    
    public double getAccountAge() {
        return accountAge;
    }

    
    public void setAccountAge(double accountAge) {
        this.accountAge = accountAge;
    }

    
    public double getCusReceiveSort() {
        return cusReceiveSort;
    }

    
    public void setCusReceiveSort(double cusReceiveSort) {
        this.cusReceiveSort = cusReceiveSort;
    }

    
    public char getBalObjCode() {
        return balObjCode;
    }

    
    public void setBalObjCode(char balObjCode) {
        this.balObjCode = balObjCode;
    }

    
    public String getBalObjName() {
        return balObjName;
    }

    
    public void setBalObjName(String balObjName) {
        this.balObjName = balObjName;
    }

    
    public double getLeadTime() {
        return leadTime;
    }

    
    public void setLeadTime(double leadTime) {
        this.leadTime = leadTime;
    }

    
    public String getDealer2Code() {
        return dealer2Code;
    }

    
    public void setDealer2Code(String dealer2Code) {
        this.dealer2Code = dealer2Code;
    }


    
    public Integer getOemTag() {
        return oemTag;
    }


    
    public void setOemTag(Integer oemTag) {
        this.oemTag = oemTag;
    }


    
    public Integer getIsValid() {
        return isValid;
    }


    
    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }


    
    public String getCustomerShortName() {
        return customerShortName;
    }


    
    public void setCustomerShortName(String customerShortName) {
        this.customerShortName = customerShortName;
    }

}
