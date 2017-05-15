package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;
/**
 * 配件流水账DTO
* TODO description
* @author yujiangheng
* @date 2017年5月6日
 */
public class TtPartFlowDTO {
   // private Long createBy;
   // private Integer ver;
  //private Date createDate;
  //private Long updateBy;
  //private Date updateDate;
  //private Integer dKey;
    private String customerCode;
    private String customerName;
    private String partNo;
    private String storageCode;
    private String sheetNo;
    private String partName;
    private String license;
    private String dealerCode;
    private String operator;
    private String partBatchNo;
    
    private Long repairPartId;
    private Long flowId;
    
    private Integer inOutTag;
    private Integer inOutType;
    
    private Date operateDate;
    private Date reportBIDatetime;//bi接口上报时间
    
    private Float stockInQuantity;
    private Float stockQuantity;
    private Float stockOutQuantity;
    
    private Double inOutNetAmount;
    private Double inOutTaxedAmount;
    private Double inOutTaxedPrice;
    private Double costPrice;
    private Double costAmount;
    private Double inOutNetPrice;
    private Double costAmountBeforeA;
    private Double costAmountBeforeB;
    private Double costAmountAfterA;
    private Double costAmountAfterB;
    private Double otherPartCostPrice;
    private Double otherPartCostAmount;
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getPartNo() {
        return partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public String getStorageCode() {
        return storageCode;
    }
    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
    
    public String getSheetNo() {
        return sheetNo;
    }
    
    public void setSheetNo(String sheetNo) {
        this.sheetNo = sheetNo;
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
    
    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    public Long getRepairPartId() {
        return repairPartId;
    }
    
    public void setRepairPartId(Long repairPartId) {
        this.repairPartId = repairPartId;
    }
    
    public Long getFlowId() {
        return flowId;
    }
    
    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }
    
    public Integer getInOutTag() {
        return inOutTag;
    }
    
    public void setInOutTag(Integer inOutTag) {
        this.inOutTag = inOutTag;
    }
    
    public Integer getInOutType() {
        return inOutType;
    }
    
    public void setInOutType(Integer inOutType) {
        this.inOutType = inOutType;
    }
    
    public Date getOperateDate() {
        return operateDate;
    }
    
    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }
    
    public Date getReportBIDatetime() {
        return reportBIDatetime;
    }
    
    public void setReportBIDatetime(Date reportBIDatetime) {
        this.reportBIDatetime = reportBIDatetime;
    }
    
    public Float getStockInQuantity() {
        return stockInQuantity;
    }
    
    public void setStockInQuantity(Float stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }
    
    public Float getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Float stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Float getStockOutQuantity() {
        return stockOutQuantity;
    }
    
    public void setStockOutQuantity(Float stockOutQuantity) {
        this.stockOutQuantity = stockOutQuantity;
    }
    
    public Double getInOutNetAmount() {
        return inOutNetAmount;
    }
    
    public void setInOutNetAmount(Double inOutNetAmount) {
        this.inOutNetAmount = inOutNetAmount;
    }
    
    public Double getInOutTaxedAmount() {
        return inOutTaxedAmount;
    }
    
    public void setInOutTaxedAmount(Double inOutTaxedAmount) {
        this.inOutTaxedAmount = inOutTaxedAmount;
    }
    
    public Double getInOutTaxedPrice() {
        return inOutTaxedPrice;
    }
    
    public void setInOutTaxedPrice(Double inOutTaxedPrice) {
        this.inOutTaxedPrice = inOutTaxedPrice;
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
    
    public Double getInOutNetPrice() {
        return inOutNetPrice;
    }
    
    public void setInOutNetPrice(Double inOutNetPrice) {
        this.inOutNetPrice = inOutNetPrice;
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
    
    
    
}
