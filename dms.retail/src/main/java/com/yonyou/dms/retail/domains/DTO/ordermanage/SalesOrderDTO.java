
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalesOrderDTO.java
*
* @Author : xukl
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.Date;
import java.util.List;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
* SalesOrderDTO
* @author xukl
* @date 2016年9月5日
*/

public class SalesOrderDTO {
    private List<SoDecrodateDTO> soDecrodateList;
    private List<SoDecrodatePartDTO> soDecrodatePartList;
    private List<SoServicesDTO> soServicesList;
    private Long soNoId;
//提交审核用字段
    private String auditSoNo;
    
    private String auditCustomerNo;
    
    private String auditByList;
    private String storageCode;
    private String storagePositionCode;
    
    private String ecOrderNo;
    
    
    
    
    /**
     * @return the ecOrderNo
     */
    public String getEcOrderNo() {
        return ecOrderNo;
    }






    
    /**
     * @param ecOrderNo the ecOrderNo to set
     */
    public void setEcOrderNo(String ecOrderNo) {
        this.ecOrderNo = ecOrderNo;
    }






    /**
     * @return the storagePositionCode
     */
    public String getStoragePositionCode() {
        return storagePositionCode;
    }





    
    /**
     * @param storagePositionCode the storagePositionCode to set
     */
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }





    /**
     * @return the storageCode
     */
    public String getStorageCode() {
        return storageCode;
    }




    
    /**
     * @param storageCode the storageCode to set
     */
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    private String functionCodeList;
    
    private String treatmentAdvice;
    
  //销售单信息
    private String dealerCode;

    private String soNo;
    
    @Required
    private String customerNo;
    
    private String wsNo;
    
    private String intentSoNo;
    
    private String customerName;
    
    private Integer customerType;
    
    private Integer cusSource;
    
    private Integer ctCode;
    
    private String certificateNumber;
    
    private String contactorName;
    
    private String mobile;
    
    private String address;
    
    @Required
    private Date sheetCreateDate;

    @Required
    private String sheetCreatedBy;
    
    private Date contractDate;
    
    private String soldBy;
    
    private String contractNo;

    private Double contractEarnest;
    
    private Date deliveringDate;
    
    private Integer deliveryModeElec;
    
    private Integer deliveryMode;
    
    private Integer payMode;
    
    private Integer invoiceMode;
    
    private Integer soStatus;
    
    private String province;
    
    private String city;
    
    private String district;
    //车辆信息
    private String vin;
    
    private String brand;
    
    private String series;
    
    private String model;
    
    private String config;
    
    private String color;
    
    private String productCode;
    
    private String productname;
    
    private Integer vehiclePurpose;
    
    private String engineNo;
    
    private String certificateNo;
    
    private Integer loanOrg;
    
    private Integer installmentNumber;
    private double assessedPrice;
    
    /**
     * @return the assessedPrice
     */
    public double getAssessedPrice() {
        return assessedPrice;
    }



    
    /**
     * @param assessedPrice the assessedPrice to set
     */
    public void setAssessedPrice(double assessedPrice) {
        this.assessedPrice = assessedPrice;
    }

    private double installmentAmount;
    
    private double firstPermentRatio;
    
    private double firstPermentRangeS;
    
    private double firstPermentRangeE;
    
    private double loanRate;
    
    private Integer isPermuted;
    //二手车信息
    private String permutedVin;
    
    private String oldBrandCode;
    
    private String oldSeriesCode;
    
    private String oldModelCode;
    
    private String assessedLicense;
    
    private double oldCarPurchase; 
    
    private String permutedDesc;
    
    private String fileUrloldA;
    
    private String fileOldA;
    
    //价格信息
    @Required
    private Double vehiclePrice;
    
    private String oldSoNo;

    private Integer businessType;

    private String consultant;

    

    private Date confirmedDate;

    

    private Integer vsStockId;

    private Integer vehicleId;

    private Integer productId;

  

    private Double upholsterSum;

    private Double presentSum;

    private Double serviceSum;

    private Double offsetAmount;

    private Double orderSum;

    private Double orderReceivableSum;

    private Double orderPayedAmount;

    private Double penaltyAmount;
    
    private Double otherAmount;

    private String returnReason;
    
    private String otherAmountObject;

    private String remark;

    
    private Date contractMaturity;
    
    
    
    /**
     * @return the otherAmount
     */
    public Double getOtherAmount() {
        return otherAmount;
    }






    
    /**
     * @param otherAmount the otherAmount to set
     */
    public void setOtherAmount(Double otherAmount) {
        this.otherAmount = otherAmount;
    }






    /**
     * @return the contractMaturity
     */
    public Date getContractMaturity() {
        return contractMaturity;
    }




    
    
    /**
     * @return the fileUrloldA
     */
    public String getFileUrloldA() {
        return fileUrloldA;
    }






    
    /**
     * @param fileUrloldA the fileUrloldA to set
     */
    public void setFileUrloldA(String fileUrloldA) {
        this.fileUrloldA = fileUrloldA;
    }






    
    /**
     * @return the fileOldA
     */
    public String getFileOldA() {
        return fileOldA;
    }






    
    /**
     * @param fileOldA the fileOldA to set
     */
    public void setFileOldA(String fileOldA) {
        this.fileOldA = fileOldA;
    }






    /**
     * @param contractMaturity the contractMaturity to set
     */
    public void setContractMaturity(Date contractMaturity) {
        this.contractMaturity = contractMaturity;
    }

   /* private Date dispatchedDate;*/

    private String dispatchedBy;

    private String confirmedBy;

    private String stockOutBy;

    private Date returnInDate;

    private String returnInBy;


    private Integer isDayAborting;

    private Integer isDayOrderReturn;

    private Integer isDayDeliveryReturn;

    private String auditedManager;

    private Date auditedByManagerDate;

    private String auditedManagerRemark;

    private String auditedFinance;

    private Date auditedByFinanceDate;

    private String auditedFinanceRemark;

    private Integer isDelivery;
    
    private String    noList;//隐藏字段 ，用于订单解锁 
    
   
    
    
    
    
    /**
     * @return the treatmentAdvice
     */
    public String getTreatmentAdvice() {
        return treatmentAdvice;
    }




    
    
    /**
     * @return the otherAmountObject
     */
    public String getOtherAmountObject() {
        return otherAmountObject;
    }




    
    /**
     * @param otherAmountObject the otherAmountObject to set
     */
    public void setOtherAmountObject(String otherAmountObject) {
        this.otherAmountObject = otherAmountObject;
    }




    /**
     * @param treatmentAdvice the treatmentAdvice to set
     */
    public void setTreatmentAdvice(String treatmentAdvice) {
        this.treatmentAdvice = treatmentAdvice;
    }




    /**
     * @return the auditSoNo
     */
    public String getAuditSoNo() {
        return auditSoNo;
    }




    
    /**
     * @param auditSoNo the auditSoNo to set
     */
    public void setAuditSoNo(String auditSoNo) {
        this.auditSoNo = auditSoNo;
    }




    
    /**
     * @return the auditCustomerNo
     */
    public String getAuditCustomerNo() {
        return auditCustomerNo;
    }




    
    /**
     * @param auditCustomerNo the auditCustomerNo to set
     */
    public void setAuditCustomerNo(String auditCustomerNo) {
        this.auditCustomerNo = auditCustomerNo;
    }




    
    /**
     * @return the auditByList
     */
    public String getAuditByList() {
        return auditByList;
    }




    
    /**
     * @param auditByList the auditByList to set
     */
    public void setAuditByList(String auditByList) {
        this.auditByList = auditByList;
    }




    
    /**
     * @return the functionCodeList
     */
    public String getFunctionCodeList() {
        return functionCodeList;
    }




    
    /**
     * @param functionCodeList the functionCodeList to set
     */
    public void setFunctionCodeList(String functionCodeList) {
        this.functionCodeList = functionCodeList;
    }




    public String getNoList() {
        return noList;
    }


    
    public void setNoList(String noList) {
        this.noList = noList;
    }


    /**
     * @return the roReceivableSum
     */
    public Double getRoReceivableSum() {
        return roReceivableSum;
    }

    
    /**
     * @param roReceivableSum the roReceivableSum to set
     */
    public void setRoReceivableSum(Double roReceivableSum) {
        this.roReceivableSum = roReceivableSum;
    }

    
    /**
     * @return the roPayedAmount
     */
    public Double getRoPayedAmount() {
        return roPayedAmount;
    }

    
    /**
     * @param roPayedAmount the roPayedAmount to set
     */
    public void setRoPayedAmount(Double roPayedAmount) {
        this.roPayedAmount = roPayedAmount;
    }

    private Double roReceivableSum;

    private Double roPayedAmount;
    
    public Long getSoNoId() {
        return soNoId;
    }

    public void setSoNoId(Long soNoId) {
        this.soNoId = soNoId;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode == null ? null : dealerCode.trim();
    }

    public String getSoNo() {
        return soNo;
    }

    public void setSoNo(String soNo) {
        this.soNo = soNo == null ? null : soNo.trim();
    }

    public String getOldSoNo() {
        return oldSoNo;
    }

    public void setOldSoNo(String oldSoNo) {
        this.oldSoNo = oldSoNo == null ? null : oldSoNo.trim();
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
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
        this.sheetCreatedBy = sheetCreatedBy == null ? null : sheetCreatedBy.trim();
    }

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public Double getContractEarnest() {
        return contractEarnest;
    }

    public void setContractEarnest(Double contractEarnest) {
        this.contractEarnest = contractEarnest;
    }

    public Integer getSoStatus() {
        return soStatus;
    }

    public void setSoStatus(Integer soStatus) {
        this.soStatus = soStatus;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant == null ? null : consultant.trim();
    }

    public Date getDeliveringDate() {
        return deliveringDate;
    }

    public void setDeliveringDate(Date deliveringDate) {
        this.deliveringDate = deliveringDate;
    }

    public Date getConfirmedDate() {
        return confirmedDate;
    }

    public void setConfirmedDate(Date confirmedDate) {
        this.confirmedDate = confirmedDate;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public Integer getVsStockId() {
        return vsStockId;
    }

    public void setVsStockId(Integer vsStockId) {
        this.vsStockId = vsStockId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getVehiclePrice() {
        return vehiclePrice;
    }

    public void setVehiclePrice(Double vehiclePrice) {
        this.vehiclePrice = vehiclePrice;
    }

    public Double getUpholsterSum() {
        return upholsterSum;
    }

    public void setUpholsterSum(Double upholsterSum) {
        this.upholsterSum = upholsterSum;
    }

    public Double getPresentSum() {
        return presentSum;
    }

    public void setPresentSum(Double presentSum) {
        this.presentSum = presentSum;
    }

    public Double getServiceSum() {
        return serviceSum;
    }

    public void setServiceSum(Double serviceSum) {
        this.serviceSum = serviceSum;
    }

    public Double getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(Double offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public Double getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(Double orderSum) {
        this.orderSum = orderSum;
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

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getVehiclePurpose() {
        return vehiclePurpose;
    }

    public void setVehiclePurpose(Integer vehiclePurpose) {
        this.vehiclePurpose = vehiclePurpose;
    }

/*    public Date getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(Date dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }*/

    public String getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(String dispatchedBy) {
        this.dispatchedBy = dispatchedBy == null ? null : dispatchedBy.trim();
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy == null ? null : confirmedBy.trim();
    }

    public String getStockOutBy() {
        return stockOutBy;
    }

    public void setStockOutBy(String stockOutBy) {
        this.stockOutBy = stockOutBy == null ? null : stockOutBy.trim();
    }

    public Date getReturnInDate() {
        return returnInDate;
    }

    public void setReturnInDate(Date returnInDate) {
        this.returnInDate = returnInDate;
    }

    public String getReturnInBy() {
        return returnInBy;
    }

    public void setReturnInBy(String returnInBy) {
        this.returnInBy = returnInBy == null ? null : returnInBy.trim();
    }

    public Integer getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(Integer invoiceMode) {
        this.invoiceMode = invoiceMode;
    }

    public Integer getIsDayAborting() {
        return isDayAborting;
    }

    public void setIsDayAborting(Integer isDayAborting) {
        this.isDayAborting = isDayAborting;
    }

    public Integer getIsDayOrderReturn() {
        return isDayOrderReturn;
    }

    public void setIsDayOrderReturn(Integer isDayOrderReturn) {
        this.isDayOrderReturn = isDayOrderReturn;
    }

    public Integer getIsDayDeliveryReturn() {
        return isDayDeliveryReturn;
    }

    public void setIsDayDeliveryReturn(Integer isDayDeliveryReturn) {
        this.isDayDeliveryReturn = isDayDeliveryReturn;
    }

    public String getAuditedManager() {
        return auditedManager;
    }

    public void setAuditedManager(String auditedManager) {
        this.auditedManager = auditedManager == null ? null : auditedManager.trim();
    }

    public Date getAuditedByManagerDate() {
        return auditedByManagerDate;
    }

    public void setAuditedByManagerDate(Date auditedByManagerDate) {
        this.auditedByManagerDate = auditedByManagerDate;
    }

    public String getAuditedManagerRemark() {
        return auditedManagerRemark;
    }

    public void setAuditedManagerRemark(String auditedManagerRemark) {
        this.auditedManagerRemark = auditedManagerRemark == null ? null : auditedManagerRemark.trim();
    }

    public String getAuditedFinance() {
        return auditedFinance;
    }

    public void setAuditedFinance(String auditedFinance) {
        this.auditedFinance = auditedFinance == null ? null : auditedFinance.trim();
    }

    public Date getAuditedByFinanceDate() {
        return auditedByFinanceDate;
    }

    public void setAuditedByFinanceDate(Date auditedByFinanceDate) {
        this.auditedByFinanceDate = auditedByFinanceDate;
    }

    public String getAuditedFinanceRemark() {
        return auditedFinanceRemark;
    }

    public void setAuditedFinanceRemark(String auditedFinanceRemark) {
        this.auditedFinanceRemark = auditedFinanceRemark == null ? null : auditedFinanceRemark.trim();
    }

    public Integer getIsDelivery() {
        return isDelivery;
    }

    public void setIsDelivery(Integer isDelivery) {
        this.isDelivery = isDelivery;
    }

    /**
     * @return the soDecrodateList
     */
    public List<SoDecrodateDTO> getSoDecrodateList() {
        return soDecrodateList;
    }

    /**
     * @param soDecrodateList the soDecrodateList to set
     */
    public void setSoDecrodateList(List<SoDecrodateDTO> soDecrodateList) {
        this.soDecrodateList = soDecrodateList;
    }

    /**
     * @return the soDecrodatePartList
     */
    public List<SoDecrodatePartDTO> getSoDecrodatePartList() {
        return soDecrodatePartList;
    }

    /**
     * @param soDecrodatePartList the soDecrodatePartList to set
     */
    public void setSoDecrodatePartList(List<SoDecrodatePartDTO> soDecrodatePartList) {
        this.soDecrodatePartList = soDecrodatePartList;
    }

    /**
     * @return the soServicesList
     */
    public List<SoServicesDTO> getSoServicesList() {
        return soServicesList;
    }

    /**
     * @param soServicesList the soServicesList to set
     */
    public void setSoServicesList(List<SoServicesDTO> soServicesList) {
        this.soServicesList = soServicesList;
    }



    
    /**
     * @return the wsNo
     */
    public String getWsNo() {
        return wsNo;
    }



    
    /**
     * @param wsNo the wsNo to set
     */
    public void setWsNo(String wsNo) {
        this.wsNo = wsNo;
    }



    
    /**
     * @return the intentSoNo
     */
    public String getIntentSoNo() {
        return intentSoNo;
    }



    
    /**
     * @param intentSoNo the intentSoNo to set
     */
    public void setIntentSoNo(String intentSoNo) {
        this.intentSoNo = intentSoNo;
    }



    
    /**
     * @return the cusSource
     */
    public Integer getCusSource() {
        return cusSource;
    }



    
    /**
     * @param cusSource the cusSource to set
     */
    public void setCusSource(Integer cusSource) {
        this.cusSource = cusSource;
    }



    
    /**
     * @return the ctCode
     */
    public Integer getCtCode() {
        return ctCode;
    }



    
    /**
     * @param ctCode the ctCode to set
     */
    public void setCtCode(Integer ctCode) {
        this.ctCode = ctCode;
    }



    
    /**
     * @return the certificateNumber
     */
    public String getCertificateNumber() {
        return certificateNumber;
    }



    
    /**
     * @param certificateNumber the certificateNumber to set
     */
    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }



    
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
     * @return the soldBy
     */
    public String getSoldBy() {
        return soldBy;
    }



    
    /**
     * @param soldBy the soldBy to set
     */
    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }



    
    /**
     * @return the deliveryModeElec
     */
    public Integer getDeliveryModeElec() {
        return deliveryModeElec;
    }



    
    /**
     * @param deliveryModeElec the deliveryModeElec to set
     */
    public void setDeliveryModeElec(Integer deliveryModeElec) {
        this.deliveryModeElec = deliveryModeElec;
    }



    
    /**
     * @return the deliveryMode
     */
    public Integer getDeliveryMode() {
        return deliveryMode;
    }



    
    /**
     * @param deliveryMode the deliveryMode to set
     */
    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }



    
    /**
     * @return the province
     */
    public String getProvince() {
        return province;
    }



    
    /**
     * @param province the province to set
     */
    public void setProvince(String province) {
        this.province = province;
    }



    
    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }



    
    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
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
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }



    
    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }



    
    /**
     * @return the series
     */
    public String getSeries() {
        return series;
    }



    
    /**
     * @param series the series to set
     */
    public void setSeries(String series) {
        this.series = series;
    }



    
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }



    
    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }



    
    /**
     * @return the config
     */
    public String getConfig() {
        return config;
    }



    
    /**
     * @param config the config to set
     */
    public void setConfig(String config) {
        this.config = config;
    }



    
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }



    
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }



    
    /**
     * @return the productCode
     */
    public String getProductCode() {
        return productCode;
    }



    
    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }



    
    /**
     * @return the productname
     */
    public String getProductname() {
        return productname;
    }



    
    /**
     * @param productname the productname to set
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }



    
    /**
     * @return the engineNo
     */
    public String getEngineNo() {
        return engineNo;
    }



    
    /**
     * @param engineNo the engineNo to set
     */
    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }



    
    /**
     * @return the certificateNo
     */
    public String getCertificateNo() {
        return certificateNo;
    }



    
    /**
     * @param certificateNo the certificateNo to set
     */
    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }



    
    /**
     * @return the loanOrg
     */
    public Integer getLoanOrg() {
        return loanOrg;
    }



    
    /**
     * @param loanOrg the loanOrg to set
     */
    public void setLoanOrg(Integer loanOrg) {
        this.loanOrg = loanOrg;
    }



    
    /**
     * @return the installmentNumber
     */
    public Integer getInstallmentNumber() {
        return installmentNumber;
    }



    
    /**
     * @param installmentNumber the installmentNumber to set
     */
    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }



    
    /**
     * @return the installmentAmount
     */
    public double getInstallmentAmount() {
        return installmentAmount;
    }



    
    /**
     * @param installmentAmount the installmentAmount to set
     */
    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }



    
    /**
     * @return the firstPermentRatio
     */
    public double getFirstPermentRatio() {
        return firstPermentRatio;
    }



    
    /**
     * @param firstPermentRatio the firstPermentRatio to set
     */
    public void setFirstPermentRatio(double firstPermentRatio) {
        this.firstPermentRatio = firstPermentRatio;
    }



    
    /**
     * @return the firstPermentRangeS
     */
    public double getFirstPermentRangeS() {
        return firstPermentRangeS;
    }



    
    /**
     * @param firstPermentRangeS the firstPermentRangeS to set
     */
    public void setFirstPermentRangeS(double firstPermentRangeS) {
        this.firstPermentRangeS = firstPermentRangeS;
    }



    
    /**
     * @return the firstPermentRangeE
     */
    public double getFirstPermentRangeE() {
        return firstPermentRangeE;
    }



    
    /**
     * @param firstPermentRangeE the firstPermentRangeE to set
     */
    public void setFirstPermentRangeE(double firstPermentRangeE) {
        this.firstPermentRangeE = firstPermentRangeE;
    }



    
    /**
     * @return the loanRate
     */
    public double getLoanRate() {
        return loanRate;
    }



    
    /**
     * @param loanRate the loanRate to set
     */
    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }



    
    /**
     * @return the isPermuted
     */
    public Integer getIsPermuted() {
        return isPermuted;
    }



    
    /**
     * @param isPermuted the isPermuted to set
     */
    public void setIsPermuted(Integer isPermuted) {
        this.isPermuted = isPermuted;
    }



    
    /**
     * @return the permutedVin
     */
    public String getPermutedVin() {
        return permutedVin;
    }



    
    /**
     * @param permutedVin the permutedVin to set
     */
    public void setPermutedVin(String permutedVin) {
        this.permutedVin = permutedVin;
    }



    
    /**
     * @return the oldBrandCode
     */
    public String getOldBrandCode() {
        return oldBrandCode;
    }



    
    /**
     * @param oldBrandCode the oldBrandCode to set
     */
    public void setOldBrandCode(String oldBrandCode) {
        this.oldBrandCode = oldBrandCode;
    }



    
    /**
     * @return the oldSeriesCode
     */
    public String getOldSeriesCode() {
        return oldSeriesCode;
    }



    
    /**
     * @param oldSeriesCode the oldSeriesCode to set
     */
    public void setOldSeriesCode(String oldSeriesCode) {
        this.oldSeriesCode = oldSeriesCode;
    }



    
    /**
     * @return the oldModelCode
     */
    public String getOldModelCode() {
        return oldModelCode;
    }



    
    /**
     * @param oldModelCode the oldModelCode to set
     */
    public void setOldModelCode(String oldModelCode) {
        this.oldModelCode = oldModelCode;
    }



    
    /**
     * @return the assessedLicense
     */
    public String getAssessedLicense() {
        return assessedLicense;
    }



    
    /**
     * @param assessedLicense the assessedLicense to set
     */
    public void setAssessedLicense(String assessedLicense) {
        this.assessedLicense = assessedLicense;
    }



    
    /**
     * @return the oldCarPurchase
     */
    public double getOldCarPurchase() {
        return oldCarPurchase;
    }



    
    /**
     * @param oldCarPurchase the oldCarPurchase to set
     */
    public void setOldCarPurchase(double oldCarPurchase) {
        this.oldCarPurchase = oldCarPurchase;
    }



    
    /**
     * @return the permutedDesc
     */
    public String getPermutedDesc() {
        return permutedDesc;
    }



    
    /**
     * @param permutedDesc the permutedDesc to set
     */
    public void setPermutedDesc(String permutedDesc) {
        this.permutedDesc = permutedDesc;
    }
    
    
}
