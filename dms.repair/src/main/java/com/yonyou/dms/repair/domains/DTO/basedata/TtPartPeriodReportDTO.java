package com.yonyou.dms.repair.domains.DTO.basedata;

/**
 * TODO description
 * 
 * @author chenwei
 * @date 2017年4月24日
 */
public class TtPartPeriodReportDTO {

    private String dealerCode;
    private String reportMonth;
    private String reportYear;
    private String storageCode;
    private String partBatchNo;
    private String partNo;
    private String partName;
    private Double openQuantity;
    private Double openPrice;
    private Double openAmount;
    private Double inQuantity;
    private Double stockInAmount;
    private Double buyInCount;
    private Double buyInAmount;
    private Double allocateInCount;
    private Double allocateInAmount;
    private Double transferInCount;
    private Double transferInAmount;
    private Double otherInCount;
    private Double otherInAmount;
    private Double profitInCount;
    private Double profitInAmount;
    private Double outQuantity;
    private Double stockOutCostAmount;
    private Double outAmount;
    private Double repairOutCount;
    private Double repairOutCostAmount;
    private Double repairOutSaleAmount;
    private Double saleOutCount;
    private Double saleOutCostAmount;
    private Double saleOutSaleAmount;
    private Double innerOutCount;
    private Double innerOutCostAmount;
    private Double innerOutSaleAmount;
    private Double allocateOutCount;
    private Double allocateOutCostAmount;
    private Double allocateOutSaleAmount;
    private Double transferOutCount;
    private Double transferOutCostAmount;
    private Double otherOutCount;
    private Double otherOutCostAmount;
    private Double otherOutSaleAmount;
    private Double lossOutCount;
    private Double lossOutAmount;
    private Double closeQuantity;
    private Double closePrice;
    private Double closeAmount;
    private Double upholsterOutCount;
    private Double upholsterOutCostAmount;
    private Double upholsterOutSaleAmount;
    
    @Override
    public String toString() {
        return "TtPartPeriodReportDTO [dealerCode=" + dealerCode + ", reportMonth=" + reportMonth + ", reportYear="
               + reportYear + ", storageCode=" + storageCode + ", partBatchNo=" + partBatchNo + ", partNo=" + partNo
               + ", partName=" + partName + ", openQuantity=" + openQuantity + ", openPrice=" + openPrice
               + ", openAmount=" + openAmount + ", inQuantity=" + inQuantity + ", stockInAmount=" + stockInAmount
               + ", buyInCount=" + buyInCount + ", buyInAmount=" + buyInAmount + ", allocateInCount=" + allocateInCount
               + ", allocateInAmount=" + allocateInAmount + ", transferInCount=" + transferInCount
               + ", transferInAmount=" + transferInAmount + ", otherInCount=" + otherInCount + ", otherInAmount="
               + otherInAmount + ", profitInCount=" + profitInCount + ", profitInAmount=" + profitInAmount
               + ", outQuantity=" + outQuantity + ", stockOutCostAmount=" + stockOutCostAmount + ", outAmount="
               + outAmount + ", repairOutCount=" + repairOutCount + ", repairOutCostAmount=" + repairOutCostAmount
               + ", repairOutSaleAmount=" + repairOutSaleAmount + ", saleOutCount=" + saleOutCount
               + ", saleOutCostAmount=" + saleOutCostAmount + ", saleOutSaleAmount=" + saleOutSaleAmount
               + ", innerOutCount=" + innerOutCount + ", innerOutCostAmount=" + innerOutCostAmount
               + ", innerOutSaleAmount=" + innerOutSaleAmount + ", allocateOutCount=" + allocateOutCount
               + ", allocateOutCostAmount=" + allocateOutCostAmount + ", allocateOutSaleAmount=" + allocateOutSaleAmount
               + ", transferOutCount=" + transferOutCount + ", transferOutCostAmount=" + transferOutCostAmount
               + ", otherOutCount=" + otherOutCount + ", otherOutCostAmount=" + otherOutCostAmount
               + ", otherOutSaleAmount=" + otherOutSaleAmount + ", lossOutCount=" + lossOutCount + ", lossOutAmount="
               + lossOutAmount + ", closeQuantity=" + closeQuantity + ", closePrice=" + closePrice + ", closeAmount="
               + closeAmount + ", upholsterOutCount=" + upholsterOutCount + ", upholsterOutCostAmount="
               + upholsterOutCostAmount + ", upholsterOutSaleAmount=" + upholsterOutSaleAmount + "]";
    }

    public String getDealerCode() {
        return dealerCode;
    }
    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }
    
    public String getReportMonth() {
        return reportMonth;
    }
    
    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
    }
    
    public String getReportYear() {
        return reportYear;
    }
    
    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }
    
    public String getStorageCode() {
        return storageCode;
    }
    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }
    
    public String getPartBatchNo() {
        return partBatchNo;
    }
    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }
    
    public String getPartNo() {
        return partNo;
    }
    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }
    
    public String getPartName() {
        return partName;
    }
    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public Double getOpenQuantity() {
        return openQuantity;
    }
    
    public void setOpenQuantity(Double openQuantity) {
        this.openQuantity = openQuantity;
    }
    
    public Double getOpenPrice() {
        return openPrice;
    }
    
    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }
    
    public Double getOpenAmount() {
        return openAmount;
    }
    
    public void setOpenAmount(Double openAmount) {
        this.openAmount = openAmount;
    }
    
    public Double getInQuantity() {
        return inQuantity;
    }
    
    public void setInQuantity(Double inQuantity) {
        this.inQuantity = inQuantity;
    }
    
    public Double getStockInAmount() {
        return stockInAmount;
    }
    
    public void setStockInAmount(Double stockInAmount) {
        this.stockInAmount = stockInAmount;
    }
    
    public Double getBuyInCount() {
        return buyInCount;
    }
    
    public void setBuyInCount(Double buyInCount) {
        this.buyInCount = buyInCount;
    }
    
    public Double getBuyInAmount() {
        return buyInAmount;
    }
    
    public void setBuyInAmount(Double buyInAmount) {
        this.buyInAmount = buyInAmount;
    }
    
    public Double getAllocateInCount() {
        return allocateInCount;
    }
    
    public void setAllocateInCount(Double allocateInCount) {
        this.allocateInCount = allocateInCount;
    }
    
    public Double getAllocateInAmount() {
        return allocateInAmount;
    }
    
    public void setAllocateInAmount(Double allocateInAmount) {
        this.allocateInAmount = allocateInAmount;
    }
    
    public Double getTransferInCount() {
        return transferInCount;
    }
    
    public void setTransferInCount(Double transferInCount) {
        this.transferInCount = transferInCount;
    }
    
    public Double getTransferInAmount() {
        return transferInAmount;
    }
    
    public void setTransferInAmount(Double transferInAmount) {
        this.transferInAmount = transferInAmount;
    }
    
    public Double getOtherInCount() {
        return otherInCount;
    }
    
    public void setOtherInCount(Double otherInCount) {
        this.otherInCount = otherInCount;
    }
    
    public Double getOtherInAmount() {
        return otherInAmount;
    }
    
    public void setOtherInAmount(Double otherInAmount) {
        this.otherInAmount = otherInAmount;
    }
    
    public Double getProfitInCount() {
        return profitInCount;
    }
    
    public void setProfitInCount(Double profitInCount) {
        this.profitInCount = profitInCount;
    }
    
    public Double getProfitInAmount() {
        return profitInAmount;
    }
    
    public void setProfitInAmount(Double profitInAmount) {
        this.profitInAmount = profitInAmount;
    }
    
    public Double getOutQuantity() {
        return outQuantity;
    }
    
    public void setOutQuantity(Double outQuantity) {
        this.outQuantity = outQuantity;
    }
    
    public Double getStockOutCostAmount() {
        return stockOutCostAmount;
    }
    
    public void setStockOutCostAmount(Double stockOutCostAmount) {
        this.stockOutCostAmount = stockOutCostAmount;
    }
    
    public Double getOutAmount() {
        return outAmount;
    }
    
    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }
    
    public Double getRepairOutCount() {
        return repairOutCount;
    }
    
    public void setRepairOutCount(Double repairOutCount) {
        this.repairOutCount = repairOutCount;
    }
    
    public Double getRepairOutCostAmount() {
        return repairOutCostAmount;
    }
    
    public void setRepairOutCostAmount(Double repairOutCostAmount) {
        this.repairOutCostAmount = repairOutCostAmount;
    }
    
    public Double getRepairOutSaleAmount() {
        return repairOutSaleAmount;
    }
    
    public void setRepairOutSaleAmount(Double repairOutSaleAmount) {
        this.repairOutSaleAmount = repairOutSaleAmount;
    }
    
    public Double getSaleOutCount() {
        return saleOutCount;
    }
    
    public void setSaleOutCount(Double saleOutCount) {
        this.saleOutCount = saleOutCount;
    }
    
    public Double getSaleOutCostAmount() {
        return saleOutCostAmount;
    }
    
    public void setSaleOutCostAmount(Double saleOutCostAmount) {
        this.saleOutCostAmount = saleOutCostAmount;
    }
    
    public Double getSaleOutSaleAmount() {
        return saleOutSaleAmount;
    }
    
    public void setSaleOutSaleAmount(Double saleOutSaleAmount) {
        this.saleOutSaleAmount = saleOutSaleAmount;
    }
    
    public Double getInnerOutCount() {
        return innerOutCount;
    }
    
    public void setInnerOutCount(Double innerOutCount) {
        this.innerOutCount = innerOutCount;
    }
    
    public Double getInnerOutCostAmount() {
        return innerOutCostAmount;
    }
    
    public void setInnerOutCostAmount(Double innerOutCostAmount) {
        this.innerOutCostAmount = innerOutCostAmount;
    }
    
    public Double getInnerOutSaleAmount() {
        return innerOutSaleAmount;
    }
    
    public void setInnerOutSaleAmount(Double innerOutSaleAmount) {
        this.innerOutSaleAmount = innerOutSaleAmount;
    }
    
    public Double getAllocateOutCount() {
        return allocateOutCount;
    }
    
    public void setAllocateOutCount(Double allocateOutCount) {
        this.allocateOutCount = allocateOutCount;
    }
    
    public Double getAllocateOutCostAmount() {
        return allocateOutCostAmount;
    }
    
    public void setAllocateOutCostAmount(Double allocateOutCostAmount) {
        this.allocateOutCostAmount = allocateOutCostAmount;
    }
    
    public Double getAllocateOutSaleAmount() {
        return allocateOutSaleAmount;
    }
    
    public void setAllocateOutSaleAmount(Double allocateOutSaleAmount) {
        this.allocateOutSaleAmount = allocateOutSaleAmount;
    }
    
    public Double getTransferOutCount() {
        return transferOutCount;
    }
    
    public void setTransferOutCount(Double transferOutCount) {
        this.transferOutCount = transferOutCount;
    }
    
    public Double getTransferOutCostAmount() {
        return transferOutCostAmount;
    }
    
    public void setTransferOutCostAmount(Double transferOutCostAmount) {
        this.transferOutCostAmount = transferOutCostAmount;
    }
    
    public Double getOtherOutCount() {
        return otherOutCount;
    }
    
    public void setOtherOutCount(Double otherOutCount) {
        this.otherOutCount = otherOutCount;
    }
    
    public Double getOtherOutCostAmount() {
        return otherOutCostAmount;
    }
    
    public void setOtherOutCostAmount(Double otherOutCostAmount) {
        this.otherOutCostAmount = otherOutCostAmount;
    }
    
    public Double getOtherOutSaleAmount() {
        return otherOutSaleAmount;
    }
    
    public void setOtherOutSaleAmount(Double otherOutSaleAmount) {
        this.otherOutSaleAmount = otherOutSaleAmount;
    }
    
    public Double getLossOutCount() {
        return lossOutCount;
    }
    
    public void setLossOutCount(Double lossOutCount) {
        this.lossOutCount = lossOutCount;
    }
    
    public Double getLossOutAmount() {
        return lossOutAmount;
    }
    
    public void setLossOutAmount(Double lossOutAmount) {
        this.lossOutAmount = lossOutAmount;
    }
    
    public Double getCloseQuantity() {
        return closeQuantity;
    }
    
    public void setCloseQuantity(Double closeQuantity) {
        this.closeQuantity = closeQuantity;
    }
    
    public Double getClosePrice() {
        return closePrice;
    }
    
    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }
    
    public Double getCloseAmount() {
        return closeAmount;
    }
    
    public void setCloseAmount(Double closeAmount) {
        this.closeAmount = closeAmount;
    }
    
    public Double getUpholsterOutCount() {
        return upholsterOutCount;
    }
    
    public void setUpholsterOutCount(Double upholsterOutCount) {
        this.upholsterOutCount = upholsterOutCount;
    }
    
    public Double getUpholsterOutCostAmount() {
        return upholsterOutCostAmount;
    }
    
    public void setUpholsterOutCostAmount(Double upholsterOutCostAmount) {
        this.upholsterOutCostAmount = upholsterOutCostAmount;
    }
    
    public Double getUpholsterOutSaleAmount() {
        return upholsterOutSaleAmount;
    }
    
    public void setUpholsterOutSaleAmount(Double upholsterOutSaleAmount) {
        this.upholsterOutSaleAmount = upholsterOutSaleAmount;
    }

}
