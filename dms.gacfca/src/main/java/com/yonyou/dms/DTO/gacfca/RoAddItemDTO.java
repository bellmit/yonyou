package com.yonyou.dms.DTO.gacfca;

public class RoAddItemDTO {

	private String addItemCode;

	private String addItemName;

	private Double addItemAmount;

	private String remark;
	
	private String activityCode;

	private Integer oemTag;
	
	public Integer getOemTag() {
		return oemTag;
	}

	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public Double getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
