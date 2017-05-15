
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : CustomerTrackingDTO.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月22日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月22日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.commonAS.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件流水帐DTO TODO description
 * 
 * @author chenwei
 * @date 2017年4月17日
 */

public class PartFlowDTO {

    private Integer flowId;
    private String  dealerCode;
    private String    storageCode;
    private String  partNo;
    private String  partBatchNo;
    private String  partName;
    private String  license;
    private String    sheetNo;
    private Integer inOutType;
    private Integer inOutTag;
    private Float  stockInQuantity;
    private Float  stockOutQuantity;
    private Double  costPrice;
    private Double  costAmount;
    private Double  inOutTaxedPrice;
    private Double  inOutTaxedAmount;
    private Double  inOutNetPrice;
    private Double  inOutNetAmount;
    private Float  stockQuantity;
    private String  customerName;
    private char    customerCode;
    private String  operator;
    private Date    operateDate;
    private Long repairPartId;
    private Integer dKey;
    private String  createdBy;
    private Date    createdAt;
    private String  updatedBy;
    private Date    updatedAt;
    private Integer ver;
    private Double  otherPartCostPrice;
    private Double  otherPartCostAmount;
    private Double  costAmountAfterA;
    private Double  costAmountAfterB;
    private Double  costAmountBeforeA;
    private Double  costAmountBeforeB;
    private Date    ddcnUpdateDate;
    private Date    reportBIDateTime;
    
    public Integer getFlowId() {
        return flowId;
    }
    
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    
    public String getStorageCode() {
        return storageCode;
    }

    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public String getPartNo() {
        return partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    public String getPartName() {
        return partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public String getLicense() {
        return license;
    }
    
    public void setLicense(String license) {
        this.license = license;
    }
    
    public String getSheetNo() {
        return sheetNo;
    }
    
    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
    }
    
    public Integer getInOutType() {
        return inOutType;
    }
    
    public void setInOutType(Integer inOutType) {
        this.inOutType = inOutType;
    }
    
    public Integer getInOutTag() {
        return inOutTag;
    }
    
    public void setInOutTag(Integer inOutTag) {
        this.inOutTag = inOutTag;
    }
    
    
    
    public Double getCostPrice() {
        return costPrice;
    }
    
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }
    
    public Double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }
    
    public Double getInOutTaxedPrice() {
        return inOutTaxedPrice;
    }
    
    public void setInOutTaxedPrice(Double inOutTaxedPrice) {
        this.inOutTaxedPrice = inOutTaxedPrice;
    }
 
    
    
    public Float getStockInQuantity() {
        return stockInQuantity;
    }

    
    public void setStockInQuantity(Float stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    
    public Float getStockOutQuantity() {
        return stockOutQuantity;
    }

    
    public void setStockOutQuantity(Float stockOutQuantity) {
        this.stockOutQuantity = stockOutQuantity;
    }

    
    public Double getInOutTaxedAmount() {
        return inOutTaxedAmount;
    }

    
    public void setInOutTaxedAmount(Double inOutTaxedAmount) {
        this.inOutTaxedAmount = inOutTaxedAmount;
    }

    
    public Double getInOutNetAmount() {
        return inOutNetAmount;
    }

    
    public void setInOutNetAmount(Double inOutNetAmount) {
        this.inOutNetAmount = inOutNetAmount;
    }

    public Float getStockQuantity() {
        return stockQuantity;
    }

    
    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getInOutNetPrice() {
        return inOutNetPrice;
    }
    
    public void setInOutNetPrice(Double inOutNetPrice) {
        this.inOutNetPrice = inOutNetPrice;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public char getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(char customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public Date getOperateDate() {
        return operateDate;
    }
    
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    
    public Long getRepairPartId() {
        return repairPartId;
    }

    
    public void setRepairPartId(Long repairPartId) {
        this.repairPartId = repairPartId;
    }

    public Integer getdKey() {
        return dKey;
    }
    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Double getOtherPartCostPrice() {
        return otherPartCostPrice;
    }
    
    public void setOtherPartCostPrice(Double otherPartCostPrice) {
        this.otherPartCostPrice = otherPartCostPrice;
    }
    
    public Double getOtherPartCostAmount() {
        return otherPartCostAmount;
    }
    
    public void setOtherPartCostAmount(Double otherPartCostAmount) {
        this.otherPartCostAmount = otherPartCostAmount;
    }
    
    public Double getCostAmountAfterA() {
        return costAmountAfterA;
    }
    
    public void setCostAmountAfterA(Double costAmountAfterA) {
        this.costAmountAfterA = costAmountAfterA;
    }
    
    public Double getCostAmountAfterB() {
        return costAmountAfterB;
    }
    
    public void setCostAmountAfterB(Double costAmountAfterB) {
        this.costAmountAfterB = costAmountAfterB;
    }
    
    public Double getCostAmountBeforeA() {
        return costAmountBeforeA;
    }
    
    public void setCostAmountBeforeA(Double costAmountBeforeA) {
        this.costAmountBeforeA = costAmountBeforeA;
    }
    
    public Double getCostAmountBeforeB() {
        return costAmountBeforeB;
    }
    
    public void setCostAmountBeforeB(Double costAmountBeforeB) {
        this.costAmountBeforeB = costAmountBeforeB;
    }
    
    public Date getDdcnUpdateDate() {
        return ddcnUpdateDate;
    }
    
    public void setDdcnUpdateDate(Date ddcnUpdateDate) {
        this.ddcnUpdateDate = ddcnUpdateDate;
    }
    
    public Date getReportBIDateTime() {
        return reportBIDateTime;
    }
    
    public void setReportBIDateTime(Date reportBIDateTime) {
        this.reportBIDateTime = reportBIDateTime;
    }
    
    /*private Integer FLOW_ID;
    private String  DEALER_CODE;
    private char    STORAGE_CODE;
    private String  PART_NO;
    private String  PART_BATCH_NO;
    private String  PART_NAME;
    private String  LICENSE;
    private char    SHEET_NO;
    private Integer IN_OUT_TYPE;
    private Integer IN_OUT_TAG;
    private Double  STOCK_IN_QUANTITY;
    private Double  STOCK_OUT_QUANTITY;
    private Double  COST_PRICE;
    private Double  COST_AMOUNT;
    private Double  IN_OUT_TAXED_PRICE;
    private Double  IN_OUT_TAXED_AMOUNT;
    private Double  IN_OUT_NET_PRICE;
    private Double  IN_OUT_NET_AMOUNT;
    private Double  STOCK_QUANTITY;
    private String  CUSTOMER_NAME;
    private char    CUSTOMER_CODE;
    private String  OPERATOR;
    private Date    OPERATE_DATE;
    private Integer REPAIR_PART_ID;
    private Integer D_KEY;
    private String  CREATED_BY;
    private Date    CREATED_AT;
    private String  UPDATED_BY;
    private Date    UPDATED_AT;
    private Integer VER;
    private Double  OTHER_PART_COST_PRICE;
    private Double  OTHER_PART_COST_AMOUNT;
    private Double  COST_AMOUNT_AFTER_A;
    private Double  COST_AMOUNT_AFTER_B;
    private Double  COST_AMOUNT_BEFORE_A;
    private Double  COST_AMOUNT_BEFORE_B;
    private Date    DDCN_UPDATE_DATE;
    private Date    REPORT_B_I_DATETIME;*/

    

}
