
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : FinanceShutTableDTO.java
*
* @Author : Administrator
*
* @Date : 2017年2月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.Date;

/**
* TODO description
* @author Administrator
* @date 2017年2月16日
*/

public class FinanceShutTableDTO {
    
              private String soNo;
              private String soNos;
              
              private String applyNo;
              
              private Integer soStatus;
              
              private Integer businessType;
              
              private Integer isSpeediness;
              
              private Date sheetCreateDate;
              
              private Long soldBy;
              
              private Long obligatedOperator;
              
              
            public String getSoNos() {
                return soNos;
            }


            
            public void setSoNos(String soNos) {
                this.soNos = soNos;
            }


            private Date balanceCloseTime;
              
              private String customerNo;
              
              private String customerName;
              
              private String vin;
              
              private Integer ver;
              
              private String modelCode;
              
              private String configCode;
              
              private String colorCode;
              
              private Integer customerType;
              
              private String contactorName;
              
              private String phone;
              
              private String contractNo;
              
              private Double orderReceivableSum;
              
              private Double orderPayedAmount;
              
              private String otherAmountObject;
              
              private Double otherAmount;
              
              private Integer otherPayOff;
              
              private String remark;
              
              private Integer deliveryModeElec;
              
              private Integer escOrderStatus;
              
              private Integer escType;
              
              private String ecOrderNo;

            
            public String getSoNo() {
                return soNo;
            }

            
            public void setSoNo(String soNo) {
                this.soNo = soNo;
            }

            
            public String getApplyNo() {
                return applyNo;
            }

            
            public void setApplyNo(String applyNo) {
                this.applyNo = applyNo;
            }

            
            public Integer getSoStatus() {
                return soStatus;
            }

            
            public void setSoStatus(Integer soStatus) {
                this.soStatus = soStatus;
            }

            
            public Integer getBusinessType() {
                return businessType;
            }

            
            public void setBusinessType(Integer businessType) {
                this.businessType = businessType;
            }

            
            public Integer getIsSpeediness() {
                return isSpeediness;
            }

            
            public void setIsSpeediness(Integer isSpeediness) {
                this.isSpeediness = isSpeediness;
            }

            
            public Date getSheetCreateDate() {
                return sheetCreateDate;
            }

            
            public void setSheetCreateDate(Date sheetCreateDate) {
                this.sheetCreateDate = sheetCreateDate;
            }

            
            public Long getSoldBy() {
                return soldBy;
            }

            
            public void setSoldBy(Long soldBy) {
                this.soldBy = soldBy;
            }

            
            public Long getObligatedOperator() {
                return obligatedOperator;
            }

            
            public void setObligatedOperator(Long obligatedOperator) {
                this.obligatedOperator = obligatedOperator;
            }

            
            public Date getBalanceCloseTime() {
                return balanceCloseTime;
            }

            
            public void setBalanceCloseTime(Date balanceCloseTime) {
                this.balanceCloseTime = balanceCloseTime;
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

            
            public String getVin() {
                return vin;
            }

            
            public void setVin(String vin) {
                this.vin = vin;
            }

            
            public Integer getVer() {
                return ver;
            }

            
            public void setVer(Integer ver) {
                this.ver = ver;
            }

            
            public String getModelCode() {
                return modelCode;
            }

            
            public void setModelCode(String modelCode) {
                this.modelCode = modelCode;
            }

            
            public String getConfigCode() {
                return configCode;
            }

            
            public void setConfigCode(String configCode) {
                this.configCode = configCode;
            }

            
            public String getColorCode() {
                return colorCode;
            }

            
            public void setColorCode(String colorCode) {
                this.colorCode = colorCode;
            }

            
            public Integer getCustomerType() {
                return customerType;
            }

            
            public void setCustomerType(Integer customerType) {
                this.customerType = customerType;
            }

            
            public String getContactorName() {
                return contactorName;
            }

            
            public void setContactorName(String contactorName) {
                this.contactorName = contactorName;
            }

            
            public String getPhone() {
                return phone;
            }

            
            public void setPhone(String phone) {
                this.phone = phone;
            }

            
            public String getContractNo() {
                return contractNo;
            }

            
            public void setContractNo(String contractNo) {
                this.contractNo = contractNo;
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

            
            public String getOtherAmountObject() {
                return otherAmountObject;
            }

            
            public void setOtherAmountObject(String otherAmountObject) {
                this.otherAmountObject = otherAmountObject;
            }

            
            public Double getOtherAmount() {
                return otherAmount;
            }

            
            public void setOtherAmount(Double otherAmount) {
                this.otherAmount = otherAmount;
            }

            
            public Integer getOtherPayOff() {
                return otherPayOff;
            }

            
            public void setOtherPayOff(Integer otherPayOff) {
                this.otherPayOff = otherPayOff;
            }

            
            public String getRemark() {
                return remark;
            }

            
            public void setRemark(String remark) {
                this.remark = remark;
            }

            
            public Integer getDeliveryModeElec() {
                return deliveryModeElec;
            }

            
            public void setDeliveryModeElec(Integer deliveryModeElec) {
                this.deliveryModeElec = deliveryModeElec;
            }

            
            public Integer getEscOrderStatus() {
                return escOrderStatus;
            }

            
            public void setEscOrderStatus(Integer escOrderStatus) {
                this.escOrderStatus = escOrderStatus;
            }

            
            public Integer getEscType() {
                return escType;
            }

            
            public void setEscType(Integer escType) {
                this.escType = escType;
            }

            
            public String getEcOrderNo() {
                return ecOrderNo;
            }

            
            public void setEcOrderNo(String ecOrderNo) {
                this.ecOrderNo = ecOrderNo;
            }
              
              
}
