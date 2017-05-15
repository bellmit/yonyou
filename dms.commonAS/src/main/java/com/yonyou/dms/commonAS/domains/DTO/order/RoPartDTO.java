
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RoPartDTO.java
*
* @Author : jcsi
*
* @Date : 2016年8月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月17日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.commonAS.domains.DTO.order;

import java.util.Date;

/**
* 工单维修配件明细
* @author jcsi
* @date 2016年8月17日
*/

public class RoPartDTO{
    
    private Long roPartId;
    
    private Long roLabourId;  //维修项目id
    
    private Long roId;   //工单id

    private String outStockNo;  //发料单号
    
    private String storageCode;  //仓库代码
    
    private String storagePositionCode; //库位
    
    private String partNo;  //配件代码
    
    private String partName;  //配件名称
    
    private String chargePartitionCode;  //收费区分
    
    private String unit;  //计量单位
    
    private Long isMainPart; //主要配件
    
    private Double partQuantity; //配件数量
    
    private Double priceRate;  //价格系数
    
    private Double partCostPrice; //配件成本单价
    
    private Double partCostAmount; //配件成本金额
    
    private Double partSalesPrice; //配件销售单价
    
    private Double partSalesAmount;  //配件销售金额
    
    private Double discount;  //折扣率
    
    private Double afterDiscountAmount; //折后总金额
    
    private String sender;//发料人
    
    private String receiver;//领料人
    
    private Integer batchNo; //流水号
    
    private Long isFinished; //是否入账
    
    private Date sendTime; //发料时间
    
    private String activityCode; //活动编号

    private String labourCode;  //维修项目Code
   
    private Long isDiscount;   //主要配件
    
    
    private Double returnQuantity;  //可退货数量
    
    
    
    public Double getReturnQuantity() {
		return returnQuantity;
	}






	public void setReturnQuantity(Double returnQuantity) {
		this.returnQuantity = returnQuantity;
	}






	public Long getRoPartId() {
        return roPartId;
    }





    
    public void setRoPartId(Long roPartId) {
        this.roPartId = roPartId;
    }





    public Long getIsDiscount() {
        return isDiscount;
    }




    
    public void setIsDiscount(Long isDiscount) {
        this.isDiscount = isDiscount;
    }




    public String getLabourCode() {
        return labourCode;
    }



    
    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode;
    }



    public String getOutStockNo() {
        return outStockNo;
    }


    
    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
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

    
    public String getChargePartitionCode() {
        return chargePartitionCode;
    }

    
    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    
    public String getUnit() {
        return unit;
    }

    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    public Long getIsMainPart() {
        return isMainPart;
    }

    
    public void setIsMainPart(Long isMainPart) {
        this.isMainPart = isMainPart;
    }

    
    public Double getPartQuantity() {
        return partQuantity;
    }

    
    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }

    
    public Double getPriceRate() {
        return priceRate;
    }

    
    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    
    public Double getPartCostPrice() {
        return partCostPrice;
    }

    
    public void setPartCostPrice(Double partCostPrice) {
        this.partCostPrice = partCostPrice;
    }

    
    public Double getPartCostAmount() {
        return partCostAmount;
    }

    
    public void setPartCostAmount(Double partCostAmount) {
        this.partCostAmount = partCostAmount;
    }

    
    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    
    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    
    public Double getDiscount() {
        return discount;
    }

    
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    
    public Double getAfterDiscountAmount() {
        return afterDiscountAmount;
    }

    
    public void setAfterDiscountAmount(Double afterDiscountAmount) {
        this.afterDiscountAmount = afterDiscountAmount;
    }

    
    public String getSender() {
        return sender;
    }

    
    public void setSender(String sender) {
        this.sender = sender;
    }

    
    public String getReceiver() {
        return receiver;
    }

    
    
    public Double getPartSalesAmount() {
        return partSalesAmount;
    }



    
    public void setPartSalesAmount(Double partSalesAmount) {
        this.partSalesAmount = partSalesAmount;
    }



    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    
    public Integer getBatchNo() {
        return batchNo;
    }

    
    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    
    public Long getIsFinished() {
        return isFinished;
    }

    
    public void setIsFinished(Long isFinished) {
        this.isFinished = isFinished;
    }

    
    public Date getSendTime() {
        return sendTime;
    }

    
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    
    public String getActivityCode() {
        return activityCode;
    }

    
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }


    
    public Long getRoId() {
        return roId;
    }


    
    public void setRoId(Long roId) {
        this.roId = roId;
    }



    
    public Long getRoLabourId() {
        return roLabourId;
    }



    
    public void setRoLabourId(Long roLabourId) {
        this.roLabourId = roLabourId;
    }



    
}
