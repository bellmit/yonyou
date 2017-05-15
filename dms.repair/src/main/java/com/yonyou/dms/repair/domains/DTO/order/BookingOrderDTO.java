
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingOrderPO.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 预约单主单
* @author jcsi
* @date 2016年10月14日
*/


public class BookingOrderDTO{

    private String boNo;  //预约单号
    
    private Long vehicleId;  //车辆id
    
    private String brandCode;  //品牌代码
    
    private String seriesCode; //车系代码
    
    private String modelCode;  //车型代码
    
    @Required
    private String contactorName;   //联系人
    
    @Required
    private String contactorMobile;  //联系人手机
    
    @Required
    private String bookingTypeCode; //预约类别代码
    
    @Required
    private Long bookingSource;   //资料来源
    
    @Required
    @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
    private Date bookingComeTime;  //预约进厂时间
    
    private String serviceAdvisorAss; //服务顾问
    
    private String remark;  //顾问
    
    private Double estimateAmount;  //预估金额
    
    private Long bookingOrderstatus;  //预约单状态
    
    private Long isDrivingService;  //是否代价

    private List<BoLabourDTO> bolabourList;  //维修项目List
    
    private List<BoPartDTO> boPartList;  //维修配件List
    
    private List<BoAddItemDTO> boAddItemList;  //附加项目List
    
    private Date bookingComeTimeFrom;  
    
    private Date bookingComeTimeTo;
    
    private String license;
    
    private String delBolabourId;  //
    
    private String delBoPartId;  
    
    private String delBoAddItemId; 
    
    private Integer isComfirm;  
    
    
    public Integer getIsComfirm() {
        return isComfirm;
    }





    
    public void setIsComfirm(Integer isComfirm) {
        this.isComfirm = isComfirm;
    }





    public String getDelBolabourId() {
        return delBolabourId;
    }




    
    public void setDelBolabourId(String delBolabourId) {
        this.delBolabourId = delBolabourId;
    }




    
    public String getDelBoPartId() {
        return delBoPartId;
    }




    
    public void setDelBoPartId(String delBoPartId) {
        this.delBoPartId = delBoPartId;
    }




    
    public String getDelBoAddItemId() {
        return delBoAddItemId;
    }




    
    public void setDelBoAddItemId(String delBoAddItemId) {
        this.delBoAddItemId = delBoAddItemId;
    }




    public String getLicense() {
        return license;
    }



    
    public void setLicense(String license) {
        this.license = license;
    }



    public Date getBookingComeTimeFrom() {
        return bookingComeTimeFrom;
    }


    
    public void setBookingComeTimeFrom(Date bookingComeTimeFrom) {
        this.bookingComeTimeFrom = bookingComeTimeFrom;
    }


    
    public Date getBookingComeTimeTo() {
        return bookingComeTimeTo;
    }


    
    public void setBookingComeTimeTo(Date bookingComeTimeTo) {
        this.bookingComeTimeTo = bookingComeTimeTo;
    }


    public String getBoNo() {
        return boNo;
    }

    
    public void setBoNo(String boNo) {
        this.boNo = boNo;
    }

    
    public Long getVehicleId() {
        return vehicleId;
    }

    
    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    
    public String getBrandCode() {
        return brandCode;
    }

    
    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    
    public String getSeriesCode() {
        return seriesCode;
    }

    
    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    
    public String getModelCode() {
        return modelCode;
    }

    
    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    
    public String getContactorName() {
        return contactorName;
    }

    
    public void setContactorName(String contactorName) {
        this.contactorName = contactorName;
    }

    
    public String getContactorMobile() {
        return contactorMobile;
    }

    
    public void setContactorMobile(String contactorMobile) {
        this.contactorMobile = contactorMobile;
    }

    
    public String getBookingTypeCode() {
        return bookingTypeCode;
    }

    
    public void setBookingTypeCode(String bookingTypeCode) {
        this.bookingTypeCode = bookingTypeCode;
    }

    
    public Long getBookingSource() {
        return bookingSource;
    }

    
    public void setBookingSource(Long bookingSource) {
        this.bookingSource = bookingSource;
    }

    
    public Date getBookingComeTime() {
        return bookingComeTime;
    }

    
    public void setBookingComeTime(Date bookingComeTime) {
        this.bookingComeTime = bookingComeTime;
    }

    
    public String getServiceAdvisorAss() {
        return serviceAdvisorAss;
    }

    
    public void setServiceAdvisorAss(String serviceAdvisorAss) {
        this.serviceAdvisorAss = serviceAdvisorAss;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    
    public Double getEstimateAmount() {
        return estimateAmount;
    }

    
    public void setEstimateAmount(Double estimateAmount) {
        this.estimateAmount = estimateAmount;
    }

    
    public Long getBookingOrderstatus() {
        return bookingOrderstatus;
    }

    
    public void setBookingOrderstatus(Long bookingOrderstatus) {
        this.bookingOrderstatus = bookingOrderstatus;
    }

    
    public Long getIsDrivingService() {
        return isDrivingService;
    }

    
    public void setIsDrivingService(Long isDrivingService) {
        this.isDrivingService = isDrivingService;
    }


    
    public List<BoLabourDTO> getBolabourList() {
        return bolabourList;
    }


    
    public void setBolabourList(List<BoLabourDTO> bolabourList) {
        this.bolabourList = bolabourList;
    }


    
    public List<BoPartDTO> getBoPartList() {
        return boPartList;
    }


    
    public void setBoPartList(List<BoPartDTO> boPartList) {
        this.boPartList = boPartList;
    }


    
    public List<BoAddItemDTO> getBoAddItemList() {
        return boAddItemList;
    }


    
    public void setBoAddItemList(List<BoAddItemDTO> boAddItemList) {
        this.boAddItemList = boAddItemList;
    }
    
    
    
}
