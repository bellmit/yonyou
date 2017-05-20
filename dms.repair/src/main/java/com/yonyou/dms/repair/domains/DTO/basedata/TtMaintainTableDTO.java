
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : TtAdPartParaDTO.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

/**
 * 
* TODO description
* @author chenwei
* @date 2017年4月14日
 */

public class TtMaintainTableDTO {

    private Boolean isSelected;
    private String itemUpdateStatus;//新增修改或删除
    private Long itemId;
    private String partInfix;//配件中缀
    private String consignExterior;//是否委外
    private String manageSortCode;//收费类别代码
    private String needlessRepair;//是否不修;
    private Double oemLimitPrice;//OEM销售限价
    private String partBatchNo;//进货批号
    private Integer preCheck;//是否预检
    private Double priceRate;//价格系数
    private Integer priceType;//价格类型
    private Integer printBatchNo;//预捡单打印流水号
    private Date printRpTime;//预先捡料单打印时间
    private String isFinished;
    private String cardId;
    private String activityCode;
    private String storageName;
    private String storageCode;
    private String storagePositionCode;
    private String isMainPart;
    private String partNo;
    private String partName;
    private String unitCode;
    private Double partQuantity;
    private Double useableStock;
    private Double lockedQuantity;
    private String isDiscount;
    private Double partSalesPrice;
    private Double partSalesAmount;
    private String chargePartitionCode;
    private String sender;
    private String receiver;
    private String batchNo;
    private String outStockNo;
    private String labourName;
    private String labourCode;
    private String partModelGroupCodeSet;
    private Double discount;
    private Double partCostPrice;
    private Double partCostAmount;
    private Double addRate;
    private Integer nonOneOff;
    private String roNo;
    
    public String getItemUpdateStatus() {
        return itemUpdateStatus;
    }
    
    public void setItemUpdateStatus(String itemUpdateStatus) {
        this.itemUpdateStatus = itemUpdateStatus;
    }

    //数据库不存在字段
    private String recordId;
    
    public String getRecordId() {
        return recordId;
    }

    
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRoNo() {
        return roNo;
    }


    
    public void setRoNo(String roNo) {
        this.roNo = roNo;
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

    
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    
    public String getOutStockNo() {
        return outStockNo;
    }

    
    public void setOutStockNo(String outStockNo) {
        this.outStockNo = outStockNo;
    }

    public Integer getNonOneOff() {
        return nonOneOff;
    }
    
    public void setNonOneOff(Integer nonOneOff) {
        this.nonOneOff = nonOneOff;
    }

    public Boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }


    public Date getPrintRpTime() {
        return printRpTime;
    }

    
    public void setPrintRpTime(Date printRpTime) {
        this.printRpTime = printRpTime;
    }

    public Integer getPrintBatchNo() {
        return printBatchNo;
    }

    public void setPrintBatchNo(Integer printBatchNo) {
        this.printBatchNo = printBatchNo;
    }

    public Integer getPriceType() {
        return priceType;
    }
    
    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Double getPriceRate() {
        return priceRate;
    }
    
    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }



    public Integer getPreCheck() {
        return preCheck;
    }


    
    public void setPreCheck(Integer preCheck) {
        this.preCheck = preCheck;
    }


    public String getPartBatchNo() {
        return partBatchNo;
    }

    
    public void setPartBatchNo(String partBatchNo) {
        this.partBatchNo = partBatchNo;
    }

    public Double getOemLimitPrice() {
        return oemLimitPrice;
    }
    
    public void setOemLimitPrice(Double oemLimitPrice) {
        this.oemLimitPrice = oemLimitPrice;
    }



    public String getNeedlessRepair() {
        return needlessRepair;
    }


    
    public void setNeedlessRepair(String needlessRepair) {
        this.needlessRepair = needlessRepair;
    }


    public String getManageSortCode() {
        return manageSortCode;
    }

    
    public void setManageSortCode(String manageSortCode) {
        this.manageSortCode = manageSortCode;
    }

    public String getConsignExterior() {
        return consignExterior;
    }
    
    public void setConsignExterior(String consignExterior) {
        this.consignExterior = consignExterior;
    }

    public String getPartInfix() {
        return partInfix;
    }

    public void setPartInfix(String partInfix) {
        this.partInfix = partInfix;
    }

    
    public String getStorageCode() {
        return storageCode;
    }


    
    public void setStorageCode(String storageCode) {
        this.storageCode = storageCode;
    }


    public String getStorageName() {
        return storageName;
    }

    
    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getIsFinished() {
        return isFinished;
    }
    
    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }
    
    public String getCardId() {
        return cardId;
    }

    
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getActivityCode() {
        return activityCode;
    }
    
    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }
    
    public String getStoragePositionCode() {
        return storagePositionCode;
    }
    
    public void setStoragePositionCode(String storagePositionCode) {
        this.storagePositionCode = storagePositionCode;
    }
    
    public String getIsMainPart() {
        return isMainPart;
    }
    
    public void setIsMainPart(String isMainPart) {
        this.isMainPart = isMainPart;
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
    
    public String getUnitCode() {
        return unitCode;
    }


    
    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }


    public String getIsDiscount() {
        return isDiscount;
    }
    
    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }
    
    public String getChargePartitionCode() {
        return chargePartitionCode;
    }
    
    public void setChargePartitionCode(String chargePartitionCode) {
        this.chargePartitionCode = chargePartitionCode;
    }

    
    
    

    public String getBatchNo() {
        return batchNo;
    }
    
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
    
    
    public String getLabourName() {
        return labourName;
    }
    
    public void setLabourName(String labourName) {
        this.labourName = labourName;
    }
    
    public String getLabourCode() {
        return labourCode;
    }
    
    public void setLabourCode(String labourCode) {
        this.labourCode = labourCode;
    }
    
    public String getPartModelGroupCodeSet() {
        return partModelGroupCodeSet;
    }
    
    public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
        this.partModelGroupCodeSet = partModelGroupCodeSet;
    }

    
    public Double getPartQuantity() {
        return partQuantity;
    }

    
    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }

    
    public Double getUseableStock() {
        return useableStock;
    }

    
    public void setUseableStock(Double useableStock) {
        this.useableStock = useableStock;
    }

    
    public Double getLockedQuantity() {
        return lockedQuantity;
    }

    
    public void setLockedQuantity(Double lockedQuantity) {
        this.lockedQuantity = lockedQuantity;
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

    
    public Double getDiscount() {
        return discount;
    }

    
    public void setDiscount(Double discount) {
        this.discount = discount;
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

    
    public Double getAddRate() {
        return addRate;
    }

    
    public void setAddRate(Double addRate) {
        this.addRate = addRate;
    }


    
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

}
