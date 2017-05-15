
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ListPtDmsOrderPriceDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月10日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.partOrder;

/**
 * @author
 * @date 2017年5月10日
 */

public class ListPtDmsOrderPriceDTO {

    private Double partSalesPrice;
    private Double partSalesAmount;
    private Double oldPrice;
    private String itemId;
    private String storageCode;
    private String partName;
    private Double costPrice;
    private double partQuantity  ;
    private String partBatchNo;
    private String limitPrice;
    
    
    
    
    
    public String getLimitPrice() {
        return limitPrice;
    }





    
    public void setLimitPrice(String limitPrice) {
        this.limitPrice = limitPrice;
    }





    public String getPartBatchNo() {
        return partBatchNo;
    }




    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }




    public double getPartQuantity() {
        return partQuantity;
    }



    
    public void setPartQuantity(double partQuantity) {
        this.partQuantity = partQuantity;
    }



    public Double getCostPrice() {
        return costPrice;
    }


    
    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }


    public String getPartName() {
        return partName;
    }

    
    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getItemId() {
        return itemId;
    }

    public String getStorageCode() {
        return storageCode;
    }

    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    public Double getPartSalesAmount() {
        return partSalesAmount;
    }

    public void setPartSalesAmount(Double partSalesAmount) {
        this.partSalesAmount = partSalesAmount;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    private String partNo;

}
