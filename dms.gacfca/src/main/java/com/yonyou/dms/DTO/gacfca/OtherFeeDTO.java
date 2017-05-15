package com.yonyou.dms.DTO.gacfca;

public class OtherFeeDTO {

	private String entityCode;

	private String addItemCode;

	private String addItemName;

	private Double addItemPrice;

	private Integer isValid;

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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

	public Double getAddItemPrice() {
		return addItemPrice;
	}

	public void setAddItemPrice(Double addItemPrice) {
		this.addItemPrice = addItemPrice;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
}
