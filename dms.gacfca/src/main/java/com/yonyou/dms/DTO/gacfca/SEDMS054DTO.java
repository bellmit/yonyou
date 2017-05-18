package com.yonyou.dms.DTO.gacfca;

public class SEDMS054DTO {
	private String dealerCode;
	private String entityCode;//下端经销code
	private String dealerName;//经销商简称
	private String execAuthor;//执行人员
	private String phone;//留店电话
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getExecAuthor() {
		return execAuthor;
	}
	public void setExecAuthor(String execAuthor) {
		this.execAuthor = execAuthor;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
