
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : PotentialCusDTO.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年9月1日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月1日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.Fax;
import com.yonyou.dms.function.utils.validate.define.Phone;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
 * 潜在客户DTO
 * 
 * @author zhanshiwei
 * @date 2016年9月1日
 */

public class PotentialCusDTO extends DataImportDto {

    private String       cusId;
    
    private String    noList;//隐藏字段 ，用于潜客再分配
    private String    applyList;//隐藏字段 ，用于客户休眠申请
    private String    applyintent;//隐藏字段 ，用于客户休眠申请
    private String    activeList;//隐藏字段 ，用于客户激活
    private String    activeIntentId;//隐藏字段 ，用于客户激活
    
    
    private String    mainCusList;//隐藏字段 ，用于客户合并保存所有合并的客户编号
    private String    mainCus;//隐藏字段 ，用于客户合并保存主要客户编号
    private long      isReceive;//显示字段，用于客户合并
    
    private String contactorName;
    
    
    
    
    /**
     * @return the contactorName
     */
    public String getContactorName() {
        return contactorName;
    }

    /**
     * @param contactorName the contactorName to set
     */
    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    public long getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(long isReceive) {
        this.isReceive = isReceive;
    }













    public String getMainCusList() {
        return mainCusList;
    }












    
    public void setMainCusList(String mainCusList) {
        this.mainCusList = mainCusList;
    }












    
    public String getMainCus() {
        return mainCus;
    }












    
    public void setMainCus(String mainCus) {
        this.mainCus = mainCus;
    }












    public String getActiveIntentId() {
        return activeIntentId;
    }











    
    public void setActiveIntentId(String activeIntentId) {
        this.activeIntentId = activeIntentId;
    }











    public String getActiveList() {
        return activeList;
    }










    
    public void setActiveList(String activeList) {
        this.activeList = activeList;
    }










    public String getApplyintent() {
        return applyintent;
    }









    
    public void setApplyintent(String applyintent) {
        this.applyintent = applyintent;
    }









    public String getApplyList() {
        return applyList;
    }








    
    public void setApplyList(String applyList) {
        this.applyList = applyList;
    }








    public String getNoList() {
        return noList;
    }







    
    public void setNoList(String noList) {
        this.noList = noList;
    }

    private Long         intentId;   // 展厅接待id

    private String       customerIds;

    private String       dealerCode;
    @Length(max=12)
    private String       customerNo;
    private String       sodCustomerId;
    @Length(max=120)
    @Required
    private String       customerName;
    private String    largeCustomerNo;
    
    private Integer      customerType;
    
    private Integer      customerStatus;

    private Integer      gender;
   
    private Date         birthday;
    @ZipCode
    private String       zipCode;
    private String       countryCode;
    @Required
    private String       province;
    @Required
    private String       city;
    @Required
    private String       district;
    @Required
    @Length(max=120)
    private String       address;
    @Length(max=30)
    private String       contactorPhone;
    @Phone
    private String       contactorMobile;
    @Email
    private String       eMail;
    @Fax
    private String       fax;
    private String       bestContactType;

    private Date         bestContactTime;
    private Integer      ctCode;
    
    private String       certificateNo;
    private long         educationLevel;
    private long         ownerMarriageE;
    private String       hobby;
   
    private long         industryFirst;
    private long         industrySecond;
    private String       organType;
    private long         organTypeCode;
    private String       positionName;
    private long         vocationType;
    private long         familyIncome;
    private long         ageStage;
    private long         isCrpvip;
    private long         isFirstBuy;
    private long         hasDriverLicense;
    private long         isPersonDriveCar;
    private long         buyPurpose;
    private long         choiceReason;
    private List<String> buyReason;
    private long         cusSource;
    private long         mediaType;
    private String       campaignCode;
    private String       campaignName;

    private Date         testDriveDate;
    
    
    /**
     * @return the testDriveDate
     */
    public Date getTestDriveDate() {
        return testDriveDate;
    }















    
    /**
     * @param testDriveDate the testDriveDate to set
     */
    public void setTestDriveDate(Date testDriveDate) {
        this.testDriveDate = testDriveDate;
    }















    public String getCampaignName() {
        return campaignName;
    }






    
    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    private long         initLevel;
    private long         intentLevel;
    private long         failConsultant;
    private long         delayConsultant;
    private Date         consultantTime;
    private Long         soldBy;
    private String       dcrcService;
    private long         isWholeSaler;
    private long         isDirect;
    private long         isUpload;
    private String       recommendEmpName;
    private double       gatheredSum;
    private Double       orderPayedSum;
    private double       conPayedSum;
    private double       usableAmount;
    private double       unWriteoffSum;
    private String       modifyReason;
    private long         isReported;
    private String       reportRemark;
    private Date         reportDatetime;
    private long         reportStatus;
    private String       reportAuditingRemark;
    private String       reportAbortReason;
    private Date         downTimestamp;
    private Date         submitTime;
    private Date         foundDate;
    @Length(max=1000)
    private String       remark;
    private long         ownerBy;
    private long         isTheShop;
    private long         firstIsArrived;
    private Date         arriveTime;
    private String       mediaDetail;
    private long         dKey;
    private long         isSecondTehShop;
    private Date secondArriveTime;
    private String mergerCusNo;
    private String oemCustomerNo;
    private long customerImportantLevel;
    private long cusImportantRating;
    private String cusRatingDesc;
    private String im;
    private long cusOrientSort;
    private long betterContactTime;
    private long betterContactPeriod;
    private String detailDesc;
    private String recommendEmpPhone;
    private long familyStructure;
    private String brandNow;
    private String modelNow;
    private String licenseNow;
    private long childrenNumber;
    private long isSmoke;
    private long favoriteDrink;
    private double buildingPrice;
    private String doingThings;
    private String social;
    private String personality;
    private String brandChooseReason;
    private long kaType;
    private String lastSoldBy;
    private long failIntentLevel;
    private Date ddcnUpdateDate; 
    private Date lastArriveTime;
    private String mergeCustomerNo;
    private String mergeOemCustomerNo;
    private String auditView;
    private List<String> keepApplyReasion;
    private String familyMember;
    private long  auditStatus;
    private long  auditBy;
    private long  isSameDcc;
    private Date validityBeginDate;
    private Date auditDate;
    private Date dccDate;
    private String memberMobile;
    private String memberPhone;
    private String companyName;
    private String ownYear;
    private String seriesNow;
    private String testDriveDccompany;
    private long  ownMileage;
    private long  sleepType;
    private String sleepSeries;
    private String sleepSeries1;
    
    /**
     * @return the sleepSeries1
     */
    public String getSleepSeries1() {
        return sleepSeries1;
    }

    
    /**
     * @param sleepSeries1 the sleepSeries1 to set
     */
    public void setSleepSeries1(String sleepSeries1) {
        this.sleepSeries1 = sleepSeries1;
    }

    private String keepApplyRemark;
    private String lmsRemark;
    private long  rebuyCustomerType;
    private long  isPadCreate;
    private long  isVerifyAddress;
    private long  isOutbound;
    private long  obIsSuccess;
    private long  reasons;
    private String outboundRemark;
    private Date spadUpdateDate;
    private Date bindingDate;
    private Date replaceDate;
    private Date outboundReturnTime;
    private Date outboundUploadTime;
    private Date outboundTime;
    private long  isUpdate;
    private long  isOwner;
    private long  isBinding;
    private long  isSecondTime;
    private long  isBigCustomer;
    private String orgCode;
    private String ecOrderNo;
    private String oldCustomerVin;
    private String oldCustomerName;

    private long  isSpadCreate;
    private long  spadCustomerId;
    private long  expectTimesRange;
    private long  escOrderStatus;
    private long  isHitFollowUpload;
    private long  escType;
    private long  isToShop;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date expectDate;
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date timeToshop;
    private Date hitOrderArrive;
    private List<TtCustomerIntentDetailDTO> intentList;
    private List<TtCustomerVehicleListDTO> keepCarList;
    private List<TtSalesPromotionPlanDTO> followList;
    private List<TtPoCusLinkmanDTO> linkManList;
    
    
    
    public List<TtPoCusLinkmanDTO> getLinkManList() {
        return linkManList;
    }





    
    public void setLinkmanList(List<TtPoCusLinkmanDTO> linkManList) {
        this.linkManList = linkManList;
    }





    public long getPurchaseType() {
        return purchaseType;
    }




    
    public void setPurchaseType(long purchaseType) {
        this.purchaseType = purchaseType;
    }




    
    public long getBillType() {
        return billType;
    }




    
    public void setBillType(long billType) {
        this.billType = billType;
    }




    
    public String getDecisionMaker() {
        return decisionMaker;
    }




    
    public void setDecisionMaker(String decisionMaker) {
        this.decisionMaker = decisionMaker;
    }




    
    public double getBudgetAmount() {
        return budgetAmount;
    }




    
    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }




    
    public long getIsBudgetEnough() {
        return isBudgetEnough;
    }




    
    public void setIsBudgetEnough(long isBudgetEnough) {
        this.isBudgetEnough = isBudgetEnough;
    }

    private long purchaseType;  //tt_customer_intent
    private long billType;
    private String decisionMaker;
    private double budgetAmount;
    private long isBudgetEnough;
    private long isTestDrive;
 
    
    public long getIsTestDrive() {
        return isTestDrive;
    }






    
    public void setIsTestDrive(long isTestDrive) {
        this.isTestDrive = isTestDrive;
    }






    public List<TtCustomerIntentDetailDTO> getIntentList() {
        return intentList;
    }



    
    public void setIntentList(List<TtCustomerIntentDetailDTO> intentList) {
        this.intentList = intentList;
    }

  
    
    
    
    public List<TtSalesPromotionPlanDTO> getFollowList() {
        return followList;
    }


    
    public void setFollowList(List<TtSalesPromotionPlanDTO> followList) {
        this.followList = followList;
    }


    public List<TtCustomerVehicleListDTO> getKeepCarList() {
        return keepCarList;
    }

    
    public void setKeepCarList(List<TtCustomerVehicleListDTO> keepCarList) {
        this.keepCarList = keepCarList;
    }

    public String getCusId() {
        return cusId;
    }
    
    public void setCusId(String cusId) {
        this.cusId = cusId;
    }
    
    public Long getIntentId() {
        return intentId;
    }
    
    public void setIntentId(Long intentId) {
        this.intentId = intentId;
    }
    
    public String getCustomerIds() {
        return customerIds;
    }
    
    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getCustomerNo() {
        return customerNo;
    }
    
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }
    
    public String getSodCustomerId() {
        return sodCustomerId;
    }
    
    public void setSodCustomerId(String sodCustomerId) {
        this.sodCustomerId = sodCustomerId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getLargeCustomerNo() {
        return largeCustomerNo;
    }
    
    public void setLargeCustomerNo(String largeCustomerNo) {
        this.largeCustomerNo = largeCustomerNo;
    }
    
    public Integer getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }
    
    public Integer getCustomerStatus() {
        return customerStatus;
    }
    
    public void setCustomerStatus(Integer customerStatus) {
        this.customerStatus = customerStatus;
    }
    
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getProvince() {
        return province;
    }
    
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
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
    
    public String geteMail() {
        return eMail;
    }
    
    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getBestContactType() {
        return bestContactType;
    }
    
    public void setBestContactType(String bestContactType) {
        this.bestContactType = bestContactType;
    }
    
    public Date getBestContactTime() {
        return bestContactTime;
    }
    
    public void setBestContactTime(Date bestContactTime) {
        this.bestContactTime = bestContactTime;
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
        this.certificateNo = certificateNo;
    }
    
    public long getEducationLevel() {
        return educationLevel;
    }
    
    public void setEducationLevel(long educationLevel) {
        this.educationLevel = educationLevel;
    }
    
    public long getOwnerMarriageE() {
        return ownerMarriageE;
    }
    
    public void setOwnerMarriageE(long ownerMarriageE) {
        this.ownerMarriageE = ownerMarriageE;
    }
    
    public String getHobby() {
        return hobby;
    }
    
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
    
    public long getIndustryFirst() {
        return industryFirst;
    }
    
    public void setIndustryFirst(long industryFirst) {
        this.industryFirst = industryFirst;
    }
    
    public long getIndustrySecond() {
        return industrySecond;
    }
    
    public void setIndustrySecond(long industrySecond) {
        this.industrySecond = industrySecond;
    }
    
    public String getOrganType() {
        return organType;
    }
    
    public void setOrganType(String organType) {
        this.organType = organType;
    }
    
    public long getOrganTypeCode() {
        return organTypeCode;
    }
    
    public void setOrganTypeCode(long organTypeCode) {
        this.organTypeCode = organTypeCode;
    }
    
    public String getPositionName() {
        return positionName;
    }
    
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    
    public long getVocationType() {
        return vocationType;
    }
    
    public void setVocationType(long vocationType) {
        this.vocationType = vocationType;
    }
    
    public long getFamilyIncome() {
        return familyIncome;
    }
    
    public void setFamilyIncome(long familyIncome) {
        this.familyIncome = familyIncome;
    }
    
    public long getAgeStage() {
        return ageStage;
    }
    
    public void setAgeStage(long ageStage) {
        this.ageStage = ageStage;
    }
    
    public long getIsCrpvip() {
        return isCrpvip;
    }
    
    public void setIsCrpvip(long isCrpvip) {
        this.isCrpvip = isCrpvip;
    }
    
    public long getIsFirstBuy() {
        return isFirstBuy;
    }
    
    public void setIsFirstBuy(long isFirstBuy) {
        this.isFirstBuy = isFirstBuy;
    }
    
    public long getHasDriverLicense() {
        return hasDriverLicense;
    }
    
    public void setHasDriverLicense(long hasDriverLicense) {
        this.hasDriverLicense = hasDriverLicense;
    }
    
    public long getIsPersonDriveCar() {
        return isPersonDriveCar;
    }
    
    public void setIsPersonDriveCar(long isPersonDriveCar) {
        this.isPersonDriveCar = isPersonDriveCar;
    }
    
    public long getBuyPurpose() {
        return buyPurpose;
    }
    
    public void setBuyPurpose(long buyPurpose) {
        this.buyPurpose = buyPurpose;
    }
    
    public long getChoiceReason() {
        return choiceReason;
    }
    
    public void setChoiceReason(long choiceReason) {
        this.choiceReason = choiceReason;
    }
    

    
    
    public List<String> getBuyReason() {
        return buyReason;
    }






    
    public void setBuyReason(List<String> buyReason) {
        this.buyReason = buyReason;
    }






    
    public void setLinkManList(List<TtPoCusLinkmanDTO> linkManList) {
        this.linkManList = linkManList;
    }






    public long getCusSource() {
        return cusSource;
    }
    
    public void setCusSource(long cusSource) {
        this.cusSource = cusSource;
    }
    
    public long getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(long mediaType) {
        this.mediaType = mediaType;
    }
    
    public String getCampaignCode() {
        return campaignCode;
    }
    
    public void setCampaignCode(String campaignCode) {
        this.campaignCode = campaignCode;
    }
    
    public long getInitLevel() {
        return initLevel;
    }
    
    public void setInitLevel(long initLevel) {
        this.initLevel = initLevel;
    }
    
    public long getIntentLevel() {
        return intentLevel;
    }
    
    public void setIntentLevel(long intentLevel) {
        this.intentLevel = intentLevel;
    }
    
    public long getFailConsultant() {
        return failConsultant;
    }
    
    public void setFailConsultant(long failConsultant) {
        this.failConsultant = failConsultant;
    }
    
    public long getDelayConsultant() {
        return delayConsultant;
    }
    
    public void setDelayConsultant(long delayConsultant) {
        this.delayConsultant = delayConsultant;
    }
    
    public Date getConsultantTime() {
        return consultantTime;
    }
    
    public void setConsultantTime(Date consultantTime) {
        this.consultantTime = consultantTime;
    }
    
    
    public Long getSoldBy() {
        return soldBy;
    }














    
    public void setSoldBy(Long soldBy) {
        this.soldBy = soldBy;
    }














    public String getDcrcService() {
        return dcrcService;
    }
    
    public void setDcrcService(String dcrcService) {
        this.dcrcService = dcrcService;
    }
    
    public long getIsWholeSaler() {
        return isWholeSaler;
    }
    
    public void setIsWholeSaler(long isWholeSaler) {
        this.isWholeSaler = isWholeSaler;
    }
    
    public long getIsDirect() {
        return isDirect;
    }
    
    public void setIsDirect(long isDirect) {
        this.isDirect = isDirect;
    }
    
    public long getIsUpload() {
        return isUpload;
    }
    
    public void setIsUpload(long isUpload) {
        this.isUpload = isUpload;
    }
    
    public String getRecommendEmpName() {
        return recommendEmpName;
    }
    
    public void setRecommendEmpName(String recommendEmpName) {
        this.recommendEmpName = recommendEmpName;
    }
    
    public double getGatheredSum() {
        return gatheredSum;
    }
    
    public void setGatheredSum(double gatheredSum) {
        this.gatheredSum = gatheredSum;
    }
    
    public Double getOrderPayedSum() {
        return orderPayedSum;
    }
    
    public void setOrderPayedSum(Double orderPayedSum) {
        this.orderPayedSum = orderPayedSum;
    }
    
    public double getConPayedSum() {
        return conPayedSum;
    }
    
    public void setConPayedSum(double conPayedSum) {
        this.conPayedSum = conPayedSum;
    }
    
    public double getUsableAmount() {
        return usableAmount;
    }
    
    public void setUsableAmount(double usableAmount) {
        this.usableAmount = usableAmount;
    }
    
    public double getUnWriteoffSum() {
        return unWriteoffSum;
    }
    
    public void setUnWriteoffSum(double unWriteoffSum) {
        this.unWriteoffSum = unWriteoffSum;
    }
    
    public String getModifyReason() {
        return modifyReason;
    }
    
    public void setModifyReason(String modifyReason) {
        this.modifyReason = modifyReason;
    }
    
    public long getIsReported() {
        return isReported;
    }
    
    public void setIsReported(long isReported) {
        this.isReported = isReported;
    }
    
    public String getReportRemark() {
        return reportRemark;
    }
    
    public void setReportRemark(String reportRemark) {
        this.reportRemark = reportRemark;
    }
    
    public Date getReportDatetime() {
        return reportDatetime;
    }
    
    public void setReportDatetime(Date reportDatetime) {
        this.reportDatetime = reportDatetime;
    }
    
    public long getReportStatus() {
        return reportStatus;
    }
    
    public void setReportStatus(long reportStatus) {
        this.reportStatus = reportStatus;
    }
    
    public String getReportAuditingRemark() {
        return reportAuditingRemark;
    }
    
    public void setReportAuditingRemark(String reportAuditingRemark) {
        this.reportAuditingRemark = reportAuditingRemark;
    }
    
    public String getReportAbortReason() {
        return reportAbortReason;
    }
    
    public void setReportAbortReason(String reportAbortReason) {
        this.reportAbortReason = reportAbortReason;
    }
    
    public Date getDownTimestamp() {
        return downTimestamp;
    }
    
    public void setDownTimestamp(Date downTimestamp) {
        this.downTimestamp = downTimestamp;
    }
    
    public Date getSubmitTime() {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    
    public Date getFoundDate() {
        return foundDate;
    }
    
    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public long getOwnerBy() {
        return ownerBy;
    }
    
    public void setOwnerBy(long ownerBy) {
        this.ownerBy = ownerBy;
    }
    
    public long getIsTheShop() {
        return isTheShop;
    }
    
    public void setIsTheShop(long isTheShop) {
        this.isTheShop = isTheShop;
    }
    
    public long getFirstIsArrived() {
        return firstIsArrived;
    }
    
    public void setFirstIsArrived(long firstIsArrived) {
        this.firstIsArrived = firstIsArrived;
    }
    
    public Date getArriveTime() {
        return arriveTime;
    }
    
    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }
    
    public String getMediaDetail() {
        return mediaDetail;
    }
    
    public void setMediaDetail(String mediaDetail) {
        this.mediaDetail = mediaDetail;
    }
    
    public long getdKey() {
        return dKey;
    }
    
    public void setdKey(long dKey) {
        this.dKey = dKey;
    }
    
    public long getIsSecondTehShop() {
        return isSecondTehShop;
    }
    
    public void setIsSecondTehShop(long isSecondTehShop) {
        this.isSecondTehShop = isSecondTehShop;
    }
    
    public Date getSecondArriveTime() {
        return secondArriveTime;
    }
    
    public void setSecondArriveTime(Date secondArriveTime) {
        this.secondArriveTime = secondArriveTime;
    }
    
    public String getMergerCusNo() {
        return mergerCusNo;
    }
    
    public void setMergerCusNo(String mergerCusNo) {
        this.mergerCusNo = mergerCusNo;
    }
    
    public String getOemCustomerNo() {
        return oemCustomerNo;
    }
    
    public void setOemCustomerNo(String oemCustomerNo) {
        this.oemCustomerNo = oemCustomerNo;
    }
    
    public long getCustomerImportantLevel() {
        return customerImportantLevel;
    }
    
    public void setCustomerImportantLevel(long customerImportantLevel) {
        this.customerImportantLevel = customerImportantLevel;
    }
    
    public long getCusImportantRating() {
        return cusImportantRating;
    }
    
    public void setCusImportantRating(long cusImportantRating) {
        this.cusImportantRating = cusImportantRating;
    }
    
    public String getCusRatingDesc() {
        return cusRatingDesc;
    }
    
    public void setCusRatingDesc(String cusRatingDesc) {
        this.cusRatingDesc = cusRatingDesc;
    }
    
    public String getIm() {
        return im;
    }
    
    public void setIm(String im) {
        this.im = im;
    }
    
    public long getCusOrientSort() {
        return cusOrientSort;
    }
    
    public void setCusOrientSort(long cusOrientSort) {
        this.cusOrientSort = cusOrientSort;
    }
    
    public long getBetterContactTime() {
        return betterContactTime;
    }
    
    public void setBetterContactTime(long betterContactTime) {
        this.betterContactTime = betterContactTime;
    }
    
    public long getBetterContactPeriod() {
        return betterContactPeriod;
    }
    
    public void setBetterContactPeriod(long betterContactPeriod) {
        this.betterContactPeriod = betterContactPeriod;
    }
    
    public String getDetailDesc() {
        return detailDesc;
    }
    
    public void setDetailDesc(String detailDesc) {
        this.detailDesc = detailDesc;
    }
    
    public String getRecommendEmpPhone() {
        return recommendEmpPhone;
    }
    
    public void setRecommendEmpPhone(String recommendEmpPhone) {
        this.recommendEmpPhone = recommendEmpPhone;
    }
    
    public long getFamilyStructure() {
        return familyStructure;
    }
    
    public void setFamilyStructure(long familyStructure) {
        this.familyStructure = familyStructure;
    }
    
    public String getBrandNow() {
        return brandNow;
    }
    
    public void setBrandNow(String brandNow) {
        this.brandNow = brandNow;
    }
    
    public String getModelNow() {
        return modelNow;
    }
    
    public void setModelNow(String modelNow) {
        this.modelNow = modelNow;
    }
    
    public String getLicenseNow() {
        return licenseNow;
    }
    
    public void setLicenseNow(String licenseNow) {
        this.licenseNow = licenseNow;
    }
    
    public long getChildrenNumber() {
        return childrenNumber;
    }
    
    public void setChildrenNumber(long childrenNumber) {
        this.childrenNumber = childrenNumber;
    }
    
    public long getIsSmoke() {
        return isSmoke;
    }
    
    public void setIsSmoke(long isSmoke) {
        this.isSmoke = isSmoke;
    }
    
    public long getFavoriteDrink() {
        return favoriteDrink;
    }
    
    public void setFavoriteDrink(long favoriteDrink) {
        this.favoriteDrink = favoriteDrink;
    }
    
    public double getBuildingPrice() {
        return buildingPrice;
    }
    
    public void setBuildingPrice(double buildingPrice) {
        this.buildingPrice = buildingPrice;
    }
    
    public String getDoingThings() {
        return doingThings;
    }
    
    public void setDoingThings(String doingThings) {
        this.doingThings = doingThings;
    }
    
    public String getSocial() {
        return social;
    }
    
    public void setSocial(String social) {
        this.social = social;
    }
    
    public String getPersonality() {
        return personality;
    }
    
    public void setPersonality(String personality) {
        this.personality = personality;
    }
    
    public String getBrandChooseReason() {
        return brandChooseReason;
    }
    
    public void setBrandChooseReason(String brandChooseReason) {
        this.brandChooseReason = brandChooseReason;
    }
    
    public long getKaType() {
        return kaType;
    }
    
    public void setKaType(long kaType) {
        this.kaType = kaType;
    }
    
    public String getLastSoldBy() {
        return lastSoldBy;
    }
    
    public void setLastSoldBy(String lastSoldBy) {
        this.lastSoldBy = lastSoldBy;
    }
    
    public long getFailIntentLevel() {
        return failIntentLevel;
    }
    
    public void setFailIntentLevel(long failIntentLevel) {
        this.failIntentLevel = failIntentLevel;
    }
    
    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }
    
    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
    public Date getLastArriveTime() {
        return lastArriveTime;
    }
    
    public void setLastArriveTime(Date lastArriveTime) {
        this.lastArriveTime = lastArriveTime;
    }
    
    public String getMergeCustomerNo() {
        return mergeCustomerNo;
    }
    
    public void setMergeCustomerNo(String mergeCustomerNo) {
        this.mergeCustomerNo = mergeCustomerNo;
    }
    
    public String getMergeOemCustomerNo() {
        return mergeOemCustomerNo;
    }
    
    public void setMergeOemCustomerNo(String mergeOemCustomerNo) {
        this.mergeOemCustomerNo = mergeOemCustomerNo;
    }
    
    public String getAuditView() {
        return auditView;
    }
    
    public void setAuditView(String auditView) {
        this.auditView = auditView;
    }
    

    
    
    public List<String> getKeepApplyReasion() {
        return keepApplyReasion;
    }








    
    public void setKeepApplyReasion(List<String> keepApplyReasion) {
        this.keepApplyReasion = keepApplyReasion;
    }








    public String getFamilyMember() {
        return familyMember;
    }
    
    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }
    
    public long getAuditStatus() {
        return auditStatus;
    }
    
    public void setAuditStatus(long auditStatus) {
        this.auditStatus = auditStatus;
    }
    
    public long getAuditBy() {
        return auditBy;
    }
    
    public void setAuditBy(long auditBy) {
        this.auditBy = auditBy;
    }
    
    public long getIsSameDcc() {
        return isSameDcc;
    }
    
    public void setIsSameDcc(long isSameDcc) {
        this.isSameDcc = isSameDcc;
    }
    
    public Date getValidityBeginDate() {
        return validityBeginDate;
    }
    
    public void setValidityBeginDate(Date validityBeginDate) {
        this.validityBeginDate = validityBeginDate;
    }
    
    public Date getAuditDate() {
        return auditDate;
    }
    
    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }
    
    public Date getDccDate() {
        return dccDate;
    }
    
    public void setDccDate(Date dccDate) {
        this.dccDate = dccDate;
    }
    
    public String getMemberMobile() {
        return memberMobile;
    }
    
    public void setMemberMobile(String memberMobile) {
        this.memberMobile = memberMobile;
    }
    
    public String getMemberPhone() {
        return memberPhone;
    }
    
    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public String getOwnYear() {
        return ownYear;
    }
    
    public void setOwnYear(String ownYear) {
        this.ownYear = ownYear;
    }
    
    public String getSeriesNow() {
        return seriesNow;
    }
    
    public void setSeriesNow(String seriesNow) {
        this.seriesNow = seriesNow;
    }
    
    public String getTestDriveDccompany() {
        return testDriveDccompany;
    }
    
    public void setTestDriveDccompany(String testDriveDccompany) {
        this.testDriveDccompany = testDriveDccompany;
    }
    
    public long getOwnMileage() {
        return ownMileage;
    }
    
    public void setOwnMileage(long ownMileage) {
        this.ownMileage = ownMileage;
    }
    
    public long getSleepType() {
        return sleepType;
    }
    
    public void setSleepType(long sleepType) {
        this.sleepType = sleepType;
    }
    
    public String getSleepSeries() {
        return sleepSeries;
    }
    
    public void setSleepSeries(String sleepSeries) {
        this.sleepSeries = sleepSeries;
    }
    
    public String getKeepApplyRemark() {
        return keepApplyRemark;
    }
    
    public void setKeepApplyRemark(String keepApplyRemark) {
        this.keepApplyRemark = keepApplyRemark;
    }
    
    public String getLmsRemark() {
        return lmsRemark;
    }
    
    public void setLmsRemark(String lmsRemark) {
        this.lmsRemark = lmsRemark;
    }
    
    public long getRebuyCustomerType() {
        return rebuyCustomerType;
    }
    
    public void setRebuyCustomerType(long rebuyCustomerType) {
        this.rebuyCustomerType = rebuyCustomerType;
    }
    
    public long getIsPadCreate() {
        return isPadCreate;
    }
    
    public void setIsPadCreate(long isPadCreate) {
        this.isPadCreate = isPadCreate;
    }
    
    public long getIsVerifyAddress() {
        return isVerifyAddress;
    }
    
    public void setIsVerifyAddress(long isVerifyAddress) {
        this.isVerifyAddress = isVerifyAddress;
    }
    
    public long getIsOutbound() {
        return isOutbound;
    }
    
    public void setIsOutbound(long isOutbound) {
        this.isOutbound = isOutbound;
    }
    
    public long getObIsSuccess() {
        return obIsSuccess;
    }
    
    public void setObIsSuccess(long obIsSuccess) {
        this.obIsSuccess = obIsSuccess;
    }
    
    public long getReasons() {
        return reasons;
    }
    
    public void setReasons(long reasons) {
        this.reasons = reasons;
    }
    
    public String getOutboundRemark() {
        return outboundRemark;
    }
    
    public void setOutboundRemark(String outboundRemark) {
        this.outboundRemark = outboundRemark;
    }
    
    public Date getSpadUpdateDate() {
        return spadUpdateDate;
    }
    
    public void setSpadUpdateDate(Date spadUpdateDate) {
        this.spadUpdateDate = spadUpdateDate;
    }
    
    public Date getBindingDate() {
        return bindingDate;
    }
    
    public void setBindingDate(Date bindingDate) {
        this.bindingDate = bindingDate;
    }
    
    public Date getReplaceDate() {
        return replaceDate;
    }
    
    public void setReplaceDate(Date replaceDate) {
        this.replaceDate = replaceDate;
    }
    
    public Date getOutboundReturnTime() {
        return outboundReturnTime;
    }
    
    public void setOutboundReturnTime(Date outboundReturnTime) {
        this.outboundReturnTime = outboundReturnTime;
    }
    
    public Date getOutboundUploadTime() {
        return outboundUploadTime;
    }
    
    public void setOutboundUploadTime(Date outboundUploadTime) {
        this.outboundUploadTime = outboundUploadTime;
    }
    
    public Date getOutboundTime() {
        return outboundTime;
    }
    
    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
    }
    
    public long getIsUpdate() {
        return isUpdate;
    }
    
    public void setIsUpdate(long isUpdate) {
        this.isUpdate = isUpdate;
    }
    
    public long getIsOwner() {
        return isOwner;
    }
    
    public void setIsOwner(long isOwner) {
        this.isOwner = isOwner;
    }
    
    public long getIsBinding() {
        return isBinding;
    }
    
    public void setIsBinding(long isBinding) {
        this.isBinding = isBinding;
    }
    
    public long getIsSecondTime() {
        return isSecondTime;
    }
    
    public void setIsSecondTime(long isSecondTime) {
        this.isSecondTime = isSecondTime;
    }
    
    public long getIsBigCustomer() {
        return isBigCustomer;
    }
    
    public void setIsBigCustomer(long isBigCustomer) {
        this.isBigCustomer = isBigCustomer;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getEcOrderNo() {
        return ecOrderNo;
    }
    
    public void setEcOrderNo(String ecOrderNo) {
        this.ecOrderNo = ecOrderNo;
    }
    
    public String getOldCustomerVin() {
        return oldCustomerVin;
    }
    
    public void setOldCustomerVin(String oldCustomerVin) {
        this.oldCustomerVin = oldCustomerVin;
    }
    
    public String getOldCustomerName() {
        return oldCustomerName;
    }
    
    public void setOldCustomerName(String oldCustomerName) {
        this.oldCustomerName = oldCustomerName;
    }
    
    public long getIsSpadCreate() {
        return isSpadCreate;
    }
    
    public void setIsSpadCreate(long isSpadCreate) {
        this.isSpadCreate = isSpadCreate;
    }
    
    public long getSpadCustomerId() {
        return spadCustomerId;
    }
    
    public void setSpadCustomerId(long spadCustomerId) {
        this.spadCustomerId = spadCustomerId;
    }
    
    public long getExpectTimesRange() {
        return expectTimesRange;
    }
    
    public void setExpectTimesRange(long expectTimesRange) {
        this.expectTimesRange = expectTimesRange;
    }
    
    public long getEscOrderStatus() {
        return escOrderStatus;
    }
    
    public void setEscOrderStatus(long escOrderStatus) {
        this.escOrderStatus = escOrderStatus;
    }
    
    public long getIsHitFollowUpload() {
        return isHitFollowUpload;
    }
    
    public void setIsHitFollowUpload(long isHitFollowUpload) {
        this.isHitFollowUpload = isHitFollowUpload;
    }
    
    public long getEscType() {
        return escType;
    }
    
    public void setEscType(long escType) {
        this.escType = escType;
    }
    
    public long getIsToShop() {
        return isToShop;
    }
    
    public void setIsToShop(long isToShop) {
        this.isToShop = isToShop;
    }
    
    public Date getExpectDate() {
        return expectDate;
    }
    
    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }
    
    public Date getTimeToshop() {
        return timeToshop;
    }
    
    public void setTimeToshop(Date timeToshop) {
        this.timeToshop = timeToshop;
    }
    
    public Date getHitOrderArrive() {
        return hitOrderArrive;
    }
    
    public void setHitOrderArrive(Date hitOrderArrive) {
        this.hitOrderArrive = hitOrderArrive;
    }
    
 

    
    

    
    
   
    



    




    

   

   

    
    

}
