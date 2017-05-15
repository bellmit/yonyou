
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : SalesPartItemDto.java
*
* @Author : jcsi
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.domains.DTO.stockmanage;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

/**
* 销售出库明细
* @author jcsi
* @date 2016年8月4日
*/

public class PartSalesItemDto extends DataImportDto{
    
    @ExcelColumnDefine(value=3)
    @Required
    private String storageCode; //仓库代码
    
    @ExcelColumnDefine(value=4)
    private String storagePositionCode;  //库位代码
    
    @ExcelColumnDefine(value=1)
    @Required
    private String partNo;  //配件代码
    
    @ExcelColumnDefine(value=2)
    @Required
    private String partName; //配件名称
    
    @ExcelColumnDefine(value=6)
    @Required
    private Double partQuantity; //配件数量
    
    @ExcelColumnDefine(value=5)
    private String unit;  //计量单位
    
    @ExcelColumnDefine(value=9)
    private Double discount; //折扣率
    
    private Long priceType;  //价格类型
 
    private Double priceRate;  //价格系数
    
    @ExcelColumnDefine(value=7)
    @Required
    private Double partSalesPrice;  //配件销售单价
    
    private Double partCostAmount;  //配件成本金额
    
    @ExcelColumnDefine(value=8)
    @Required
    private Double partSalesAmount; //配件销售金额

    @ExcelColumnDefine(value=10)
    private Double salesAmount;  //折后销售金额
    
    private Long isFinished;  //是否入账
    
    private Date finishedDate;  //入账日期
    
    private Double partCostPrice; //配件销售单价

    private Double returnQuantity;  //可退货数量
    
    
    
    public Double getReturnQuantity() {
		return returnQuantity;
	}


	public void setReturnQuantity(Double returnQuantity) {
		this.returnQuantity = returnQuantity;
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

    
    public String getUnit() {
        return unit;
    }

    
    public void setUnit(String unit) {
        this.unit = unit;
    }

    
    public Double getDiscount() {
        return discount;
    }

    
    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    
    public Long getPriceType() {
        return priceType;
    }

    
    public void setPriceType(Long priceType) {
        this.priceType = priceType;
    }

    
    public Double getPriceRate() {
        return priceRate;
    }

    
    public void setPriceRate(Double priceRate) {
        this.priceRate = priceRate;
    }

    
    public Double getPartSalesPrice() {
        return partSalesPrice;
    }

    
    public void setPartSalesPrice(Double partSalesPrice) {
        this.partSalesPrice = partSalesPrice;
    }

    
    public Double getPartCostAmount() {
        return partCostAmount;
    }

    
    public void setPartCostAmount(Double partCostAmount) {
        this.partCostAmount = partCostAmount;
    }

    
    public Double getPartSalesAmount() {
        return partSalesAmount;
    }

    
    public void setPartSalesAmount(Double partSalesAmount) {
        this.partSalesAmount = partSalesAmount;
    }

    
    public Double getSalesAmount() {
        return salesAmount;
    }

    
    public void setSalesAmount(Double salesAmount) {
        this.salesAmount = salesAmount;
    }

    
    public Long getIsFinished() {
        return isFinished;
    }

    
    public void setIsFinished(Long isFinished) {
        this.isFinished = isFinished;
    }

    
    public Date getFinishedDate() {
        return finishedDate;
    }

    
    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }


    
    public Double getPartCostPrice() {
        return partCostPrice;
    }


    
    public void setPartCostPrice(Double partCostPrice) {
        this.partCostPrice = partCostPrice;
    }
    
    

}
