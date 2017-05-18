
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerComplaintDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月28日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * 客户投诉主表DTO
 * 
 * @author zhanshiwei
 * @date 2016年7月28日
 */

public class CustomerComplaintDTO {

    private String  dealerCode;

    private String  complaintNo;
    @Required
    private String  complaintName;
    @Required
    private Integer complaintGender;
    @Required
    private String  complaintMobile;

    private String  complaintPhone;
    @Required
    private Integer complaintOrigin;
    @Required
    private Integer complaintMainType;
    @Required
    private Integer complaintType;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    @Required
    private Date    complaintDate;

    private String  receptionist;

    private String  beComplaintEmp;
    
    private String  department;
    @Required
    private Integer priority;
    @Required
    private Integer complaintSerious;

    private Integer dealStatus;
    @Required
    private String  complaintSummary;
    @Required
    private String  complaintReason;

    private String  resolvent;

    private String  roNo;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date    roCreateDate;

    private String  serviceAdvisor;

    private String  deliverer;

    private String  delivererPhone;

    private String  delivererMobile;

    private String  vin;

    private String  license;

    private String  ownerName;

    private String  linkAddress;

    private Double  mileage;

    private Date    buyCarDate;

    private String  soNo;

    private Date    salesDate;

    private String  consultant;

    private String  potentialCustomerName;

    private String  potentialCustomerMobile;

    private String  potentialCustomerPhone;

    private Date    closeDate;

    private Integer opeType;

    public Integer getOpeType() {
        return opeType;
    }

    public void setOpeType(Integer opeType) {
        this.opeType = opeType;
    }

    public List<CustomerComplaintDetailDTO> getComplaintDetai() {
        return complaintDetai;
    }

    public void setComplaintDetai(List<CustomerComplaintDetailDTO> complaintDetai) {
        this.complaintDetai = complaintDetai;
    }

    private List<CustomerComplaintDetailDTO> complaintDetai;

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getComplaintNo() {
        return complaintNo;
    }

    public void setComplaintNo(String complaintNo) {
        this.complaintNo = complaintNo == null ? null : complaintNo.trim();
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName == null ? null : complaintName.trim();
    }

    public Integer getComplaintGender() {
        return complaintGender;
    }

    public void setComplaintGender(Integer complaintGender) {
        this.complaintGender = complaintGender;
    }

    public String getComplaintMobile() {
        return complaintMobile;
    }

    public void setComplaintMobile(String complaintMobile) {
        this.complaintMobile = complaintMobile == null ? null : complaintMobile.trim();
    }

    public String getComplaintPhone() {
        return complaintPhone;
    }

    public void setComplaintPhone(String complaintPhone) {
        this.complaintPhone = complaintPhone == null ? null : complaintPhone.trim();
    }

    public Integer getComplaintOrigin() {
        return complaintOrigin;
    }

    public void setComplaintOrigin(Integer complaintOrigin) {
        this.complaintOrigin = complaintOrigin;
    }

    public Integer getComplaintMainType() {
        return complaintMainType;
    }

    public void setComplaintMainType(Integer complaintMainType) {
        this.complaintMainType = complaintMainType;
    }

    public Integer getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Integer complaintType) {
        this.complaintType = complaintType;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(String receptionist) {
        this.receptionist = receptionist == null ? null : receptionist.trim();
    }

    public String getBeComplaintEmp() {
        return beComplaintEmp;
    }

    public void setBeComplaintEmp(String beComplaintEmp) {
        this.beComplaintEmp = beComplaintEmp == null ? null : beComplaintEmp.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getComplaintSerious() {
        return complaintSerious;
    }

    public void setComplaintSerious(Integer complaintSerious) {
        this.complaintSerious = complaintSerious;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getComplaintSummary() {
        return complaintSummary;
    }

    public void setComplaintSummary(String complaintSummary) {
        this.complaintSummary = complaintSummary == null ? null : complaintSummary.trim();
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason == null ? null : complaintReason.trim();
    }

    public String getResolvent() {
        return resolvent;
    }

    public void setResolvent(String resolvent) {
        this.resolvent = resolvent == null ? null : resolvent.trim();
    }

    public String getRoNo() {
        return roNo;
    }

    public void setRoNo(String roNo) {
        this.roNo = roNo == null ? null : roNo.trim();
    }

    public Date getRoCreateDate() {
        return roCreateDate;
    }

    public void setRoCreateDate(Date roCreateDate) {
        this.roCreateDate = roCreateDate;
    }

    public String getServiceAdvisor() {
        return serviceAdvisor;
    }

    public void setServiceAdvisor(String serviceAdvisor) {
        this.serviceAdvisor = serviceAdvisor == null ? null : serviceAdvisor.trim();
    }

    public String getDeliverer() {
        return deliverer;
    }

    public void setDeliverer(String deliverer) {
        this.deliverer = deliverer == null ? null : deliverer.trim();
    }

    public String getDelivererPhone() {
        return delivererPhone;
    }

    public void setDelivererPhone(String delivererPhone) {
        this.delivererPhone = delivererPhone == null ? null : delivererPhone.trim();
    }

    public String getDelivererMobile() {
        return delivererMobile;
    }

    public void setDelivererMobile(String delivererMobile) {
        this.delivererMobile = delivererMobile == null ? null : delivererMobile.trim();
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin == null ? null : vin.trim();
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress == null ? null : linkAddress.trim();
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Date getBuyCarDate() {
        return buyCarDate;
    }

    public void setBuyCarDate(Date buyCarDate) {
        this.buyCarDate = buyCarDate;
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo == null ? null : soNo.trim();
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    public String getPotentialCustomerName() {
        return potentialCustomerName;
    }

    public void setPotentialCustomerName(String potentialCustomerName) {
        this.potentialCustomerName = potentialCustomerName == null ? null : potentialCustomerName.trim();
    }

    public String getPotentialCustomerMobile() {
        return potentialCustomerMobile;
    }

    public void setPotentialCustomerMobile(String potentialCustomerMobile) {
        this.potentialCustomerMobile = potentialCustomerMobile == null ? null : potentialCustomerMobile.trim();
    }

    public String getPotentialCustomerPhone() {
        return potentialCustomerPhone;
    }

    public void setPotentialCustomerPhone(String potentialCustomerPhone) {
        this.potentialCustomerPhone = potentialCustomerPhone == null ? null : potentialCustomerPhone.trim();
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
