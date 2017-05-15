
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ActivityLabourDTO.java
*
* @Author : Administrator
*
* @Date : 2016年12月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月23日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.activity;


/**
* 活动维修项目
* @author jcsi
* @date 2016年12月23日
*/

public class ActivityLabourDTO {
	
	private Long labourId;
	
	private Long activityLabourId;
	

	private String activityCode;  //活动编号
	
	private String labourCode;  //维修项目代码
	
	private String labourName;  //维修项目名称
	
	private String chargePartitionCode; //收费区分代码
	
	private String modelLabourCode;  //维修车型分组代码
	
	private Double stdLabourHour;  //标准工时
	
	private Double assignLabourHour;  //派工工时
	
	private Double labourPrice;  //工时单价
	
	private Double labourAmount;  //工时费
	
	private Double discount;  //折扣率
	
	private Long repairTypeCode;  //维修类型代码
	
	private Long roLabourType;  //维修项目类型
	
	private String orderNum;   //项目编号

	
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public Long getActivityLabourId() {
		return activityLabourId;
	}

	public void setActivityLabourId(Long activityLabourId) {
		this.activityLabourId = activityLabourId;
	}

	public Long getLabourId() {
		return labourId;
	}

	public void setLabourId(Long labourId) {
		this.labourId = labourId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}

	public String getLabourName() {
		return labourName;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public String getChargePartitionCode() {
		return chargePartitionCode;
	}

	public void setChargePartitionCode(String chargePartitionCode) {
		this.chargePartitionCode = chargePartitionCode;
	}

	public String getModelLabourCode() {
		return modelLabourCode;
	}

	public void setModelLabourCode(String modelLabourCode) {
		this.modelLabourCode = modelLabourCode;
	}

	public Double getStdLabourHour() {
		return stdLabourHour;
	}

	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}

	public Double getAssignLabourHour() {
		return assignLabourHour;
	}

	public void setAssignLabourHour(Double assignLabourHour) {
		this.assignLabourHour = assignLabourHour;
	}

	public Double getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(Double labourPrice) {
		this.labourPrice = labourPrice;
	}

	public Double getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Long getRepairTypeCode() {
		return repairTypeCode;
	}

	public void setRepairTypeCode(Long repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}

	public Long getRoLabourType() {
		return roLabourType;
	}

	public void setRoLabourType(Long roLabourType) {
		this.roLabourType = roLabourType;
	}
	
	
}
