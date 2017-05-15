package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

public class TtSalesQuoteDTO {

    private String  customerName;
    private Date    salesQuoteDate;
    private Integer dKey;
    private Date    updateDate;
    private Double  outAmount;
    private Long    createBy;
    private String  entityCode;
    private Integer ver;
    private Date    createDate;
    private String  lockUser;
    private String  customerCode;
    private Double  costAmount;
    private Long    updateBy;
    private Date    finishedDate;
    private String  salesQuoteNo;
    private Integer isFinished;//是否入帐 12781001 入帐   ; 12781002 没有入帐
    private String  handler;
    private String  popedomOrderNo;//辖区订单号
    
    private String updateStatus;
    
    private List<TtSalesQuoteItemDTO> itemList;
    
    
    public String getUpdateStatus() {
        return updateStatus;
    }

    
    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<TtSalesQuoteItemDTO> getItemList() {
        return itemList;
    }
    
    public void setItemList(List<TtSalesQuoteItemDTO> itemList) {
        this.itemList = itemList;
    }

    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public Date getSalesQuoteDate() {
        return salesQuoteDate;
    }
    
    public void setSalesQuoteDate(Date salesQuoteDate) {
        this.salesQuoteDate = salesQuoteDate;
    }
    
    public Integer getdKey() {
        return dKey;
    }
    
    public void setdKey(Integer dKey) {
        this.dKey = dKey;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public Double getOutAmount() {
        return outAmount;
    }
    
    public void setOutAmount(Double outAmount) {
        this.outAmount = outAmount;
    }
    
    public Long getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    
    public String getEntityCode() {
        return entityCode;
    }
    
    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }
    
    public Integer getVer() {
        return ver;
    }
    
    public void setVer(Integer ver) {
        this.ver = ver;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getLockUser() {
        return lockUser;
    }
    
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public Double getCostAmount() {
        return costAmount;
    }
    
    public void setCostAmount(Double costAmount) {
        this.costAmount = costAmount;
    }
    
    public Long getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    
    public Date getFinishedDate() {
        return finishedDate;
    }
    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }
    
    public String getSalesQuoteNo() {
        return salesQuoteNo;
    }
    
    public void setSalesQuoteNo(String salesQuoteNo) {
        this.salesQuoteNo = salesQuoteNo;
    }
    
    public Integer getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }
    
    public String getHandler() {
        return handler;
    }
    
    public void setHandler(String handler) {
        this.handler = handler;
    }
    
    public String getPopedomOrderNo() {
        return popedomOrderNo;
    }
    
    public void setPopedomOrderNo(String popedomOrderNo) {
        this.popedomOrderNo = popedomOrderNo;
    }

    @Override
    public String toString() {
        return "TtSalesQuoteDTO [customerName=" + customerName + ", salesQuoteDate=" + salesQuoteDate + ", dKey=" + dKey
               + ", updateDate=" + updateDate + ", outAmount=" + outAmount + ", createBy=" + createBy + ", entityCode="
               + entityCode + ", ver=" + ver + ", createDate=" + createDate + ", lockUser=" + lockUser
               + ", customerCode=" + customerCode + ", costAmount=" + costAmount + ", updateBy=" + updateBy
               + ", finishedDate=" + finishedDate + ", salesQuoteNo=" + salesQuoteNo + ", isFinished=" + isFinished
               + ", handler=" + handler + ", popedomOrderNo=" + popedomOrderNo + "]";
    }
    
}
