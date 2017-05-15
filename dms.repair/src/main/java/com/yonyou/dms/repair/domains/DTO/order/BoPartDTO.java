
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BoPartPO.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.order;



/**
* TT_BO_PART
* @author jcsi
* @date 2016年10月14日
*/

public class BoPartDTO{
    
    private Long boPartId;

    private Long boId;  
    
    private String storageCode;  //仓库代码
    
    private String storagePositionCode; //库位
    
    private String partNo;  //配件代码
    
    private String partName;  //配件名称
    
    private Double partQuantity;  //预约配件数量
    
    private Double  partSalesPrice;  //配件销售单价
    
    private Double partSalesAmount;  //配件销售金额
    
    private Long isObligated;  //是否预留

    
    public Long getBoId() {
        return boId;
    }

    
    public void setBoId(Long boId) {
        this.boId = boId;
    }

    
    
    public Long getBoPartId() {
        return boPartId;
    }


    
    public void setBoPartId(Long boPartId) {
        this.boPartId = boPartId;
    }


    public String getStorageCode() {
        return storageCode;
    }

    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }

    
    public String getStoragePositionCode() {
        return storagePositionCode;
    }

    
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
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

    
    public Double getPartQuantity() {
        return partQuantity;
    }

    
    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
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

    
    public Long getIsObligated() {
        return isObligated;
    }

    
    public void setIsObligated(Long isObligated) {
        this.isObligated = isObligated;
    }
    
    
}
