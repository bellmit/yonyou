package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class RoNotSettleAccountsRepairDelDTO {

	private String roNo;
	private String entityCode;
	private Integer isValid;
	private Date downTimestamp;

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
}
