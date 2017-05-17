package com.yonyou.dms.DTO.gacfca;


public class SEDCS016DTO {
	
	private String entityCode;//经销商代码
	private String dealerShortname;//经销商简称
	private String isRestrict;//是否行管经销商
	private String isOther;//它牌车维修控制
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getDealerShortname() {
		return dealerShortname;
	}
	public void setDealerShortname(String dealerShortname) {
		this.dealerShortname = dealerShortname;
	}
	public String getIsRestrict() {
		return isRestrict;
	}
	public void setIsRestrict(String isRestrict) {
		this.isRestrict = isRestrict;
	}
	public String getIsOther() {
		return isOther;
	}
	public void setIsOther(String isOther) {
		this.isOther = isOther;
	}
}
