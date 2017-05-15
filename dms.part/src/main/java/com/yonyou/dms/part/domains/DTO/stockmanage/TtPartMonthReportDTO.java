package com.yonyou.dms.part.domains.DTO.stockmanage;

public class TtPartMonthReportDTO {

    private String reportYear;       // 报表年份
    private String reportMonth;      // 报表月份
    private String   storageCode;      // 仓库代码
    private String partBatchNo;     // 进货批号
    private String partNo;           // 配件代码
    private String partName;         // 配件名称
    private Float openQuantity;     // 期初数量
    private Double openPrice;        // 期初单价
    private Double openAmount;       // 期初金额
    private Float inQuantity;       // 入库数量
    private Double stockInAmount;   // 入库金额
    private Float outQuantity;      // 出库数量
    private Double outAmount;        // 出库金额
    private Float inventoryQuantity;// 本期盘调量
    private Double inventoryAmount;  // 本期盘调金额
    private Float closeQuantity;    // 期末数量
    private Double closePrice;       // 期末单价
    private Double closeAmount;      // 期末金额
    
    private String dealerCode;
    
    
    
    
    
    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getReportYear() {
        return reportYear;
    }
    
    public void setReportYear(String reportYear) {
        this.reportYear = reportYear;
    }
    
    public String getReportMonth() {
        return reportMonth;
    }
    
    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth;
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
    

    
    public Float getOpenQuantity() {
        return openQuantity;
    }

    
    public void setOpenQuantity(Float openQuantity) {
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
    
 
    
    public Double getStockInAmount() {
        return stockInAmount;
    }
    
    public void setStockInAmount(Double stockInAmount) {
        this.stockInAmount = stockInAmount;
    }
    

    
    public Double getOutAmount() {
        return outAmount;
    }
    
    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }
    
    public Float getInQuantity() {
        return inQuantity;
    }


    
    public void setInQuantity(Float inQuantity) {
        this.inQuantity = inQuantity;
    }


    
    public Float getOutQuantity() {
        return outQuantity;
    }


    
    public void setOutQuantity(Float outQuantity) {
        this.outQuantity = outQuantity;
    }


    
    public Float getInventoryQuantity() {
        return inventoryQuantity;
    }


    
    public void setInventoryQuantity(Float inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }


    
    public void setCloseQuantity(Float closeQuantity) {
        this.closeQuantity = closeQuantity;
    }


    public Double getInventoryAmount() {
        return inventoryAmount;
    }
    
    public void setInventoryAmount(Double inventoryAmount) {
        this.inventoryAmount = inventoryAmount;
    }
    
    public Float getCloseQuantity() {
        return closeQuantity;
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

    @Override
    public String toString() {
        return "PartMonthReportDTO [reportYear=" + reportYear + ", reportMonth=" + reportMonth + ", storageCode="
               + storageCode + ", partBatchNo=" + partBatchNo + ", partNo=" + partNo + ", partName=" + partName
               + ", openQuantity=" + openQuantity + ", openPrice=" + openPrice + ", openAmount=" + openAmount
               + ", inQuantity=" + inQuantity + ", stockInAmount=" + stockInAmount + ", outQuantity=" + outQuantity
               + ", outAmount=" + outAmount + ", inventoryQuantity=" + inventoryQuantity + ", inventoryAmount="
               + inventoryAmount + ", closeQuantity=" + closeQuantity + ", closePrice=" + closePrice + ", closeAmount="
               + closeAmount + "]";
    }

    

}
