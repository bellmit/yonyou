package com.infoservice.dms.cgcsl.vo;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class OwnerEntityVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	private String entityCode;
	private String vin;
	private Integer isOwner;//12781001 是 12781002否

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getIsOwner() {
		return isOwner;
	}

	public void setIsOwner(Integer isOwner) {
		this.isOwner = isOwner;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}
}
