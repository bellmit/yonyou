package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class RoAddItemDetailVO  extends BaseVO{

	private static final long serialVersionUID = 1L;

	private Double addItemAmount;
	private String remark;
	private Long itemId;
	private String entityCode;
	private String addItemName;
	private String addItemCode;
	private String activityCode;
	private String manageSortCode;
	private String roNo;
	private String chargePartitionCode;
	private Date ddcnUpdateDate;
	private Double discount;
	public Double getAddItemAmount() {
		return addItemAmount;
	}
	public String getRemark() {
		return remark;
	}
	public Long getItemId() {
		return itemId;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public String getAddItemName() {
		return addItemName;
	}
	public String getAddItemCode() {
		return addItemCode;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public String getManageSortCode() {
		return manageSortCode;
	}
	public String getRoNo() {
		return roNo;
	}
	public String getChargePartitionCode() {
		return chargePartitionCode;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public void setAddItemName(String addItemName) {
		this.addItemName = addItemName;
	}
	public void setAddItemCode(String addItemCode) {
		this.addItemCode = addItemCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public void setManageSortCode(String manageSortCode) {
		this.manageSortCode = manageSortCode;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public void setChargePartitionCode(String chargePartitionCode) {
		this.chargePartitionCode = chargePartitionCode;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}

}
