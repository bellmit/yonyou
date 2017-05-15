package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件追溯 TODO description
 * 
 * @author chenwei
 * @date 2017年5月1日
 */
public class TmPartBackDTO {

    private String  dealerCode;

    private Long    itemId;

    private String  backCode;

    private Integer isStockOut;

    private String  partNo;

    private String  storageCode;

    private Long    itemIdPart;

    private Integer dKey;

    private Long    createdBy;

    private Date    createdDate;

    private Long    updatedBy;

    private Date    updatedDate;

    private Integer ver;

    // 非数据库字段
    private String recordId;

    
    public String getRecordId() {
        return recordId;
    }

    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getBackCode() {
        return backCode;
    }

    public void setBackCode(String backCode) {
        this.backCode = backCode;
    }

    public Integer getDKey() {
        return dKey;
    }

    public void setDKey(Integer key) {
        dKey = key;
    }

    public Integer getIsStockOut() {
        return isStockOut;
    }

    public void setIsStockOut(Integer isStockOut) {
        this.isStockOut = isStockOut;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Long getItemIdPart() {
        return itemIdPart;
    }

    public void setItemIdPart(Long itemIdPart) {
        this.itemIdPart = itemIdPart;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "TmPartBackDTO [dealerCode=" + dealerCode + ", itemId=" + itemId + ", backCode=" + backCode
               + ", isStockOut=" + isStockOut + ", partNo=" + partNo + ", storageCode=" + storageCode + ", itemIdPart="
               + itemIdPart + ", dKey=" + dKey + ", createdBy=" + createdBy + ", createdDate=" + createdDate
               + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", ver=" + ver + "]";
    }

}
