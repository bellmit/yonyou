package com.yonyou.dms.DTO.gacfca;

public class DCSTODMS001DTO {
	
	private String dealerCode;
	private String entityCodeName;
	private String tel;//手机号码
	private String soldBy;//销售顾问
	private String soldMobile;//销售顾问手机号
	private Integer isHitSingle;//是否撞单 是否撞单标识
//	12781001：是
//	12781002：否
	private String message;//错误信息
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEntityCodeName() {
		return entityCodeName;
	}
	public void setEntityCodeName(String entityCodeName) {
		this.entityCodeName = entityCodeName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public String getSoldMobile() {
		return soldMobile;
	}
	public void setSoldMobile(String soldMobile) {
		this.soldMobile = soldMobile;
	}
	public Integer getIsHitSingle() {
		return isHitSingle;
	}
	public void setIsHitSingle(Integer isHitSingle) {
		this.isHitSingle = isHitSingle;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
