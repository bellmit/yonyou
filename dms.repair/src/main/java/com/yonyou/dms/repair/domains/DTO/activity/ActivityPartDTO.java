
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ActivityAddDTO.java
*
* @Author : jcsi
*
* @Date : 2016年12月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月23日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.activity;


/**
* 活动配件
* @author jcsi
* @date 2016年12月23日
*/

public class ActivityPartDTO {
	
	private Long partId;
	
	private Long activityPartId;
	
	private String activityCode;  //活动编号
	
	private String storageCode;  //仓库代码
	
	private String partNo;  //配件代码
	
	private String partName;  //配件名称
	
	private Double partSalesAmount;  //配件销售金额
	
	private Double partCostAmount;  //配件成本金额
	
	private Double partSalesPrice; //配件销售单价
	
	private Double partCostPrice;  //配件成本单价
	
	private Double partQuantity; //配件数量
	
	private String chargePartitionCode; //收费区分低吗
	
	private String unitName;  //计量单位
	
	private Double discount;   //折扣率
	
	private Long isMainPart;  //主要配件
	
	private String labourCode;  //维修项目代码
	
	private String orderNum;  //项目编号
	
	

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getActivityPartId() {
		return activityPartId;
	}

	public void setActivityPartId(Long activityPartId) {
		this.activityPartId = activityPartId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getStorageCode() {
		return storageCode;
	}

	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
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

	public Double getPartSalesAmount() {
		return partSalesAmount;
	}

	public void setPartSalesAmount(Double partSalesAmount) {
		this.partSalesAmount = partSalesAmount;
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

	

	public Double getPartCostPrice() {
		return partCostPrice;
	}

	public void setPartCostPrice(Double partCostPrice) {
		this.partCostPrice = partCostPrice;
	}

	public Double getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Double partQuantity) {
		this.partQuantity = partQuantity;
	}

	public String getChargePartitionCode() {
		return chargePartitionCode;
	}

	public void setChargePartitionCode(String chargePartitionCode) {
		this.chargePartitionCode = chargePartitionCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getIsMainPart() {
		return isMainPart;
	}

	public void setIsMainPart(Long isMainPart) {
		this.isMainPart = isMainPart;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	
	

}
