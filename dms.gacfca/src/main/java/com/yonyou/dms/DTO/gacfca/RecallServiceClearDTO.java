package com.yonyou.dms.DTO.gacfca;

public class RecallServiceClearDTO {
	
	private String recallNo;//召回编号
	private String recallName;//召回名称
	private String recallStatus;//召回状态
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}
	public String getRecallName() {
		return recallName;
	}
	public void setRecallName(String recallName) {
		this.recallName = recallName;
	}
	public String getRecallStatus() {
		return recallStatus;
	}
	public void setRecallStatus(String recallStatus) {
		this.recallStatus = recallStatus;
	}
}
