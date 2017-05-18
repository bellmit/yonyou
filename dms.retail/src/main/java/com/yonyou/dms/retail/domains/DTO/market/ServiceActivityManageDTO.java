
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceActivityManageDTO.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月16日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.market;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月16日
*/
@SuppressWarnings("rawtypes")
public class ServiceActivityManageDTO {
    private String activityCode;
    private String activityName;
    private Date beginDate;
    private Date endDate;
    private String brand;
    private String series;
    private String model;
    private String apAckAge;
    private Date salesDateBegin;
    private Date salesendDate;
    private Date mileageBegin;
    private Date mileageEnd;
    private String activityAmount;
    private String activityKind;
    private String vehiclePurpose;
    private String activityFirst;
    private String partsIsModify;
    private String isPartActivity;
    private String isRepeatAttend;
    private String memberLevelAllowed;
    private String businessType;
    private String creditTimes;
    private Date maintainBeginDay;
    private Date maintainEndDay;
    
    public Date getBeginDate() {
        return beginDate;
    }



    
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }



    
    public Date getEndDate() {
        return endDate;
    }



    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }



    
    public Date getSalesDateBegin() {
        return salesDateBegin;
    }



    
    public void setSalesDateBegin(Date salesDateBegin) {
        this.salesDateBegin = salesDateBegin;
    }



    
    public Date getSalesendDate() {
        return salesendDate;
    }



    
    public void setSalesendDate(Date salesendDate) {
        this.salesendDate = salesendDate;
    }



    
    public Date getMileageBegin() {
        return mileageBegin;
    }



    
    public void setMileageBegin(Date mileageBegin) {
        this.mileageBegin = mileageBegin;
    }



    
    public Date getMileageEnd() {
        return mileageEnd;
    }



    
    public void setMileageEnd(Date mileageEnd) {
        this.mileageEnd = mileageEnd;
    }



    
    public Date getMaintainBeginDay() {
        return maintainBeginDay;
    }



    
    public void setMaintainBeginDay(Date maintainBeginDay) {
        this.maintainBeginDay = maintainBeginDay;
    }



    
    public Date getMaintainEndDay() {
        return maintainEndDay;
    }



    
    public void setMaintainEndDay(Date maintainEndDay) {
        this.maintainEndDay = maintainEndDay;
    }

    private String activityProperty;
    private String modelYear;
    private String globalActivityCode;
    private String activityTitle;
    private List<Map> labourList;
    private List<Map> partList;
    private List<Map> subjoinList;
    private String fag;
    private String addItemAmount;
    
    
    public String getAddItemAmount() {
        return addItemAmount;
    }


    
    public void setAddItemAmount(String addItemAmount) {
        this.addItemAmount = addItemAmount;
    }


    public String getFag() {
        return fag;
    }

    
    public void setFag(String fag) {
        this.fag = fag;
    }

    public String getActivityCode() {
        return activityCode;
    }
    
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    
    public String getActivityName() {
        return activityName;
    }
    
    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
    
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getSeries() {
        return series;
    }
    
    public void setSeries(String series) {
        this.series = series;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getApAckAge() {
        return apAckAge;
    }
    
    public void setApAckAge(String apAckAge) {
        this.apAckAge = apAckAge;
    }
    
    
    public String getActivityAmount() {
        return activityAmount;
    }
    
    public void setActivityAmount(String activityAmount) {
        this.activityAmount = activityAmount;
    }
    
    public String getActivityKind() {
        return activityKind;
    }
    
    public void setActivityKind(String activityKind) {
        this.activityKind = activityKind;
    }
    
    public String getVehiclePurpose() {
        return vehiclePurpose;
    }
    
    public void setVehiclePurpose(String vehiclePurpose) {
        this.vehiclePurpose = vehiclePurpose;
    }
    
    public String getActivityFirst() {
        return activityFirst;
    }
    
    public void setActivityFirst(String activityFirst) {
        this.activityFirst = activityFirst;
    }
    
    public String getPartsIsModify() {
        return partsIsModify;
    }
    
    public void setPartsIsModify(String partsIsModify) {
        this.partsIsModify = partsIsModify;
    }
    
    public String getIsPartActivity() {
        return isPartActivity;
    }
    
    public void setIsPartActivity(String isPartActivity) {
        this.isPartActivity = isPartActivity;
    }
    
    public String getIsRepeatAttend() {
        return isRepeatAttend;
    }
    
    public void setIsRepeatAttend(String isRepeatAttend) {
        this.isRepeatAttend = isRepeatAttend;
    }
    
    public String getMemberLevelAllowed() {
        return memberLevelAllowed;
    }
    
    public void setMemberLevelAllowed(String memberLevelAllowed) {
        this.memberLevelAllowed = memberLevelAllowed;
    }
    
    public String getBusinessType() {
        return businessType;
    }
    
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
    
    public String getCreditTimes() {
        return creditTimes;
    }
    
    public void setCreditTimes(String creditTimes) {
        this.creditTimes = creditTimes;
    }
    
    
    public String getActivityProperty() {
        return activityProperty;
    }
    
    public void setActivityProperty(String activityProperty) {
        this.activityProperty = activityProperty;
    }
    
    public String getModelYear() {
        return modelYear;
    }
    
    public void setModelYear(String modelYear) {
        this.modelYear = modelYear;
    }
    
    public String getGlobalActivityCode() {
        return globalActivityCode;
    }
    
    public void setGlobalActivityCode(String globalActivityCode) {
        this.globalActivityCode = globalActivityCode;
    }
    
    public String getActivityTitle() {
        return activityTitle;
    }
    
    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }
    
    public List<Map> getLabourList() {
        return labourList;
    }
    
    public void setLabourList(List<Map> labourList) {
        this.labourList = labourList;
    }
    
    public List<Map> getPartList() {
        return partList;
    }
    
    public void setPartList(List<Map> partList) {
        this.partList = partList;
    }
    
    public List<Map> getSubjoinList() {
        return subjoinList;
    }
    
    public void setSubjoinList(List<Map> subjoinList) {
        this.subjoinList = subjoinList;
    }
}
