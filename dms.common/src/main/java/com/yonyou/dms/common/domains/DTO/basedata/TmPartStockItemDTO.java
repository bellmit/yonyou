package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件库存批次信息 TODO description
 * 
 * @author chenwei
 * @date 2017年4月8日
 */
public class TmPartStockItemDTO {

    private String  dealerCode;
    private String  partNo;             // 配件代码
    private String  storageCode;        // 仓库代码
    private String  partBatchNo;        // 进货批号
    private String  storagePositionCode;// 库位代码
    private String  partName;           // 配件名称
    private String  spellCode;          // 拼音代码
    private Integer partGroupCode;      // 配件类别
    private String  unitCode;           // 计量单位代码
    private Double  stockQuantity;      // 库存数量
    private Double  salesPrice;         // 销售价
    private Double  claimPrice;         // 索赔价
    private Double  limitPrice;         // 销售限价
    private Double  latestPrice;        // 最新进货价
    private Double  insurancePrice;     // 保险价
    private Double  costPrice;          // 成本单价
    private Double  costAmount;         // 成本金额
    private Double  borrowQuantity;     // 借进数量
    private Integer partStatus;         // 是否停用
    private Double  lendQuantity;       // 借出数量
    private Date    lastStockIn;        // 最新入库日期
    private Date    lastStockOut;       // 最新出库日期
    private Date    foundDate;          // 建档日期
    private String  remark;             // 备注
    private Integer dKey;               // distribution key 用来数据库分区，可根据业务分为 交易数据，历史数据，归档数据等，分别用 0 ，1，2表示 。默认为0，表示当前交易数据
    private Double  nodePrice;          // 网点价

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
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

    public String getPartBatchNo() {
        return partBatchNo;
    }

    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }

    public String getStoragePositionCode() {
        return storagePositionCode;
    }

    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getSpellCode() {
        return spellCode;
    }

    public void setSpellCode(String spellCode) {
        this.spellCode = spellCode;
    }

    public Integer getPartGroupCode() {
        return partGroupCode;
    }

    public void setPartGroupCode(Integer partGroupCode) {
        this.partGroupCode = partGroupCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Double getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Double stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public Double getClaimPrice() {
        return claimPrice;
    }

    public void setClaimPrice(Double claimPrice) {
        this.claimPrice = claimPrice;
    }

    public Double getLimitPrice() {
        return limitPrice;
    }

    public void setLimitPrice(Double limitPrice) {
        this.limitPrice = limitPrice;
    }

    public Double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(Double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public Double getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(Double insurancePrice) {
        this.insurancePrice = insurancePrice;
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

    public Double getBorrowQuantity() {
        return borrowQuantity;
    }

    public void setBorrowQuantity(Double borrowQuantity) {
        this.borrowQuantity = borrowQuantity;
    }

    public Integer getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(Integer partStatus) {
        this.partStatus = partStatus;
    }

    public Double getLendQuantity() {
        return lendQuantity;
    }

    public void setLendQuantity(Double lendQuantity) {
        this.lendQuantity = lendQuantity;
    }

    public Date getLastStockIn() {
        return lastStockIn;
    }

    public void setLastStockIn(Date lastStockIn) {
        this.lastStockIn = lastStockIn;
    }

    public Date getLastStockOut() {
        return lastStockOut;
    }

    public void setLastStockOut(Date lastStockOut) {
        this.lastStockOut = lastStockOut;
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

    public Integer getdKey() {
        return dKey;
    }

    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }

    public Double getNodePrice() {
        return nodePrice;
    }

    public void setNodePrice(Double nodePrice) {
        this.nodePrice = nodePrice;
    }

}
