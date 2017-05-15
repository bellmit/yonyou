
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 配件预留单明細 TODO description
 * 
 * @author chenwei
 * @date 2017年4月30日
 */

public class TtPartObligatedItemDTO {

    private String  dealerCode;        // 经销商code
    private String partNo;
    private Integer dKey;
    private Date updateDate;
    private Long createBy;
    private String entityCode;
    private Integer ver;
    private String storageCode;
    private String obligatedNo;
    private Date createDate;
    private String unitCode;
    private String storagePositionCode;
    private String partName;
    private Double costAmount;
    private Long updateBy;
    private Double quantity;
    private String partBatchNo;
    private Integer isObligated;
    private Long itemId;
    private Float usableStock;
    private Double costPrice;

    
    public String getDealerCode() {
        return dealerCode;
    }

    
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public void setPartNo(String partNo){
        this.partNo=partNo;
    }

    public String getPartNo(){
        return this.partNo;
    }

    public void setDKey(Integer dKey){
        this.dKey=dKey;
    }

    public Integer getDKey(){
        return this.dKey;
    }

    public void setUpdateDate(Date updateDate){
        this.updateDate=updateDate;
    }

    public Date getUpdateDate(){
        return this.updateDate;
    }

    public void setCreateBy(Long createBy){
        this.createBy=createBy;
    }

    public Long getCreateBy(){
        return this.createBy;
    }

    public void setEntityCode(String entityCode){
        this.entityCode=entityCode;
    }

    public String getEntityCode(){
        return this.entityCode;
    }

    public void setVer(Integer ver){
        this.ver=ver;
    }

    public Integer getVer(){
        return this.ver;
    }

    public void setStorageCode(String storageCode){
        this.storageCode=storageCode;
    }

    public String getStorageCode(){
        return this.storageCode;
    }

    public void setObligatedNo(String obligatedNo){
        this.obligatedNo=obligatedNo;
    }

    public String getObligatedNo(){
        return this.obligatedNo;
    }

    public void setCreateDate(Date createDate){
        this.createDate=createDate;
    }

    public Date getCreateDate(){
        return this.createDate;
    }

    public void setUnitCode(String unitCode){
        this.unitCode=unitCode;
    }

    public String getUnitCode(){
        return this.unitCode;
    }

    public void setStoragePositionCode(String storagePositionCode){
        this.storagePositionCode=storagePositionCode;
    }

    public String getStoragePositionCode(){
        return this.storagePositionCode;
    }

    public void setPartName(String partName){
        this.partName=partName;
    }

    public String getPartName(){
        return this.partName;
    }

    public void setCostAmount(Double costAmount){
        this.costAmount=costAmount;
    }

    public Double getCostAmount(){
        return this.costAmount;
    }

    public void setUpdateBy(Long updateBy){
        this.updateBy=updateBy;
    }

    public Long getUpdateBy(){
        return this.updateBy;
    }

    public void setQuantity(Double quantity){
        this.quantity=quantity;
    }

    public Double getQuantity(){
        return this.quantity;
    }

    public void setPartBatchNo(String partBatchNo){
        this.partBatchNo=partBatchNo;
    }

    public String getPartBatchNo(){
        return this.partBatchNo;
    }

    public void setIsObligated(Integer isObligated){
        this.isObligated=isObligated;
    }

    public Integer getIsObligated(){
        return this.isObligated;
    }

    public void setItemId(Long itemId){
        this.itemId=itemId;
    }

    public Long getItemId(){
        return this.itemId;
    }

    public void setUsableStock(Float usableStock){
        this.usableStock=usableStock;
    }

    public Float getUsableStock(){
        return this.usableStock;
    }

    public void setCostPrice(Double costPrice){
        this.costPrice=costPrice;
    }

    public Double getCostPrice(){
        return this.costPrice;
    }

    @Override
    public String toString() {
        return "TtPartObligatedItemDTO [dealerCode=" + dealerCode + ", partNo=" + partNo + ", dKey=" + dKey
               + ", updateDate=" + updateDate + ", createBy=" + createBy + ", entityCode=" + entityCode + ", ver=" + ver
               + ", storageCode=" + storageCode + ", obligatedNo=" + obligatedNo + ", createDate=" + createDate
               + ", unitCode=" + unitCode + ", storagePositionCode=" + storagePositionCode + ", partName=" + partName
               + ", costAmount=" + costAmount + ", updateBy=" + updateBy + ", quantity=" + quantity + ", partBatchNo="
               + partBatchNo + ", isObligated=" + isObligated + ", itemId=" + itemId + ", usableStock=" + usableStock
               + ", costPrice=" + costPrice + "]";
    }

}
