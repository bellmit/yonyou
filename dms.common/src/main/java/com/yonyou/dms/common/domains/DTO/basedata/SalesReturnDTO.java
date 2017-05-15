
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : SalesReturnDTO.java
*
* @Author : Administrator
*
* @Date : 2017年2月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月4日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateSerializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateTimeSerializer;

/**
* TODO description
* @author Administrator
* @date 2017年2月4日
*/

public class SalesReturnDTO {
          private String soNo;
          private String customerNo;
          private String customerName;
          private String soStatus;
          private String address;
          private String mobile;
          private String ctCode;
          private String certificateNo;
          private String customerType;
          private String contactorName;
          private Long auditedBy;
          private Long reAuditedBy; 
          @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
          @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
          private Date sheetCreateDate;
          private String sheetCreatedBy;
          private String soldBy;
          private String abortingReason;
          private String remark;
          private String vin;
          private String brand;
          private String series;
          private String model;
          private String config;
          private String color;
          private String dispatchedBy;
          @JsonDeserialize(using = JsonSimpleDateTimeDeserializer.class)
          @JsonSerialize(using = JsonSimpleDateTimeSerializer.class)
          private Date dispatchedDate;
          @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
          @JsonSerialize(using = JsonSimpleDateSerializer.class)
          private Date stockOutDate;
          private String directivePrice;         
          private String oldSoNo;
          private Double vehiclePrice;
          private Double garnitureSum;
          private Double upholsterSum;
          private Double insuranceSum;
          private Double plateSum;
          private Double taxSum;
          private Double loanSum;
          private Double otherServiceSum;
          private Double orderSum;
          private Double offsetAmount;
          private Double orderReceivableSum;
          private Double orderPayedAmount;
          private Double penaltyAmount;
          private Double orderArrearageAmount;
          
          
          
          private String OwnerNo;
          private String OwnerName;
          private String businessType;
        private Integer Ver;
        
        
        
        
        public Long getAuditedBy() {
            return auditedBy;
        }




        
        public void setAuditedBy(Long auditedBy) {
            this.auditedBy = auditedBy;
        }




        
        public Long getReAuditedBy() {
            return reAuditedBy;
        }




        
        public void setReAuditedBy(Long reAuditedBy) {
            this.reAuditedBy = reAuditedBy;
        }




        public Integer getVer() {
            return Ver;
        }



        
        public void setVer(Integer ver) {
            Ver = ver;
        }



        public String getBusinessType() {
            return businessType;
        }


        
        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }


        public String getOwnerNo() {
            return OwnerNo;
        }

        
        public void setOwnerNo(String ownerNo) {
            OwnerNo = ownerNo;
        }

        
        public String getOwnerName() {
            return OwnerName;
        }

        
        public void setOwnerName(String ownerName) {
            OwnerName = ownerName;
        }

        
       

        public String getSoNo() {
            return soNo;
        }
        
        public void setSoNo(String soNo) {
            this.soNo = soNo;
        }
        
        public String getCustomerNo() {
            return customerNo;
        }
        
        public void setCustomerNo(String customerNo) {
            this.customerNo = customerNo;
        }
        
        public String getCustomerName() {
            return customerName;
        }
        
        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }
        
        public String getSoStatus() {
            return soStatus;
        }
        
        public void setSoStatus(String soStatus) {
            this.soStatus = soStatus;
        }
        
        public String getAddress() {
            return address;
        }
        
        public void setAddress(String address) {
            this.address = address;
        }
        
        public String getMobile() {
            return mobile;
        }
        
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
        
        public String getCtCode() {
            return ctCode;
        }
        
        public void setCtCode(String ctCode) {
            this.ctCode = ctCode;
        }
        
        public String getCertificateNo() {
            return certificateNo;
        }
        
        public void setCertificateNo(String certificateNo) {
            this.certificateNo = certificateNo;
        }
        
        public String getCustomerType() {
            return customerType;
        }
        
        public void setCustomerType(String customerType) {
            this.customerType = customerType;
        }
        
        public String getContactorName() {
            return contactorName;
        }
        
        public void setContactorName(String contactorName) {
            this.contactorName = contactorName;
        }
        
        public Date getSheetCreateDate() {
            return sheetCreateDate;
        }
        
        public void setSheetCreateDate(Date sheetCreateDate) {
            this.sheetCreateDate = sheetCreateDate;
        }
        
        public String getSheetCreatedBy() {
            return sheetCreatedBy;
        }
        
        public void setSheetCreatedBy(String sheetCreatedBy) {
            this.sheetCreatedBy = sheetCreatedBy;
        }
        
        public String getSoldBy() {
            return soldBy;
        }
        
        public void setSoldBy(String soldBy) {
            this.soldBy = soldBy;
        }
        
        public String getAbortingReason() {
            return abortingReason;
        }
        
        public void setAbortingReason(String abortingReason) {
            this.abortingReason = abortingReason;
        }
        
        public String getRemark() {
            return remark;
        }
        
        public void setRemark(String remark) {
            this.remark = remark;
        }
        
        public String getVin() {
            return vin;
        }
        
        public void setVin(String vin) {
            this.vin = vin;
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
        
        public String getConfig() {
            return config;
        }
        
        public void setConfig(String config) {
            this.config = config;
        }
        
        public String getColor() {
            return color;
        }
        
        public void setColor(String color) {
            this.color = color;
        }
        
        public String getDispatchedBy() {
            return dispatchedBy;
        }
        
        public void setDispatchedBy(String dispatchedBy) {
            this.dispatchedBy = dispatchedBy;
        }
        
        public Date getDispatchedDate() {
            return dispatchedDate;
        }
        
        public void setDispatchedDate(Date dispatchedDate) {
            this.dispatchedDate = dispatchedDate;
        }
        
        public Date getStockOutDate() {
            return stockOutDate;
        }
        
        public void setStockOutDate(Date stockOutDate) {
            this.stockOutDate = stockOutDate;
        }
        
        public String getDirectivePrice() {
            return directivePrice;
        }
        
        public void setDirectivePrice(String directivePrice) {
            this.directivePrice = directivePrice;
        }
        
        public String getOldSoNo() {
            return oldSoNo;
        }
        
        public void setOldSoNo(String oldSoNo) {
            this.oldSoNo = oldSoNo;
        }
        
        public Double getVehiclePrice() {
            return vehiclePrice;
        }
        
        public void setVehiclePrice(Double vehiclePrice) {
            this.vehiclePrice = vehiclePrice;
        }
        
        public Double getGarnitureSum() {
            return garnitureSum;
        }
        
        public void setGarnitureSum(Double garnitureSum) {
            this.garnitureSum = garnitureSum;
        }
        
        public Double getUpholsterSum() {
            return upholsterSum;
        }
        
        public void setUpholsterSum(Double upholsterSum) {
            this.upholsterSum = upholsterSum;
        }
        
        public Double getInsuranceSum() {
            return insuranceSum;
        }
        
        public void setInsuranceSum(Double insuranceSum) {
            this.insuranceSum = insuranceSum;
        }
        
        public Double getPlateSum() {
            return plateSum;
        }
        
        public void setPlateSum(Double plateSum) {
            this.plateSum = plateSum;
        }
        

        
        
        public Double getTaxSum() {
            return taxSum;
        }


        
        public void setTaxSum(Double taxSum) {
            this.taxSum = taxSum;
        }


        public Double getLoanSum() {
            return loanSum;
        }
        
        public void setLoanSum(Double loanSum) {
            this.loanSum = loanSum;
        }
        
        public Double getOtherServiceSum() {
            return otherServiceSum;
        }
        
        public void setOtherServiceSum(Double otherServiceSum) {
            this.otherServiceSum = otherServiceSum;
        }
        
        public Double getOrderSum() {
            return orderSum;
        }
        
        public void setOrderSum(Double orderSum) {
            this.orderSum = orderSum;
        }
        
        public Double getOffsetAmount() {
            return offsetAmount;
        }
        
        public void setOffsetAmount(Double offsetAmount) {
            this.offsetAmount = offsetAmount;
        }
        
        public Double getOrderReceivableSum() {
            return orderReceivableSum;
        }
        
        public void setOrderReceivableSum(Double orderReceivableSum) {
            this.orderReceivableSum = orderReceivableSum;
        }
        
        public Double getOrderPayedAmount() {
            return orderPayedAmount;
        }
        
        public void setOrderPayedAmount(Double orderPayedAmount) {
            this.orderPayedAmount = orderPayedAmount;
        }
        
        public Double getPenaltyAmount() {
            return penaltyAmount;
        }
        
        public void setPenaltyAmount(Double penaltyAmount) {
            this.penaltyAmount = penaltyAmount;
        }
        
        public Double getOrderArrearageAmount() {
            return orderArrearageAmount;
        }
        
        public void setOrderArrearageAmount(Double orderArrearageAmount) {
            this.orderArrearageAmount = orderArrearageAmount;
        }
          
          
}
