
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
* 活动附加项目
* @author jcsi
* @date 2016年12月23日
*/

public class ActivityAddDTO {
	
	private Long addId;
	
	
	
	private String activityCode;   //活动编号
	
	private Double discount;   //折扣率
	
	private String chargePartitionCode; //收费区分代码
	
	private String manageSortCode;  //收费类别代码
	
	private String addItemCode;  //附加项目代码
	
	private String addItemName;  //附加项目名称
	
	private Double addItemAmount;  //附加项目费
	
	private String remark;  //备注

	public Long getAddId() {
		return addId;
	}

	public void setAddId(Long addId) {
		this.addId = addId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getChargePartitionCode() {
		return chargePartitionCode;
	}

	public void setChargePartitionCode(String chargePartitionCode) {
		this.chargePartitionCode = chargePartitionCode;
	}

	public String getManageSortCode() {
		return manageSortCode;
	}

	public void setManageSortCode(String manageSortCode) {
		this.manageSortCode = manageSortCode;
	}

	public String getAddItemCode() {
		return addItemCode;
	}

	public void setAddItemCode(String addItemCode) {
		this.addItemCode = addItemCode;
	}

	public String getAddItemName() {
		return addItemName;
	}

	public void setAddItemName(String addItemName) {
		this.addItemName = addItemName;
	}

	public Double getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
