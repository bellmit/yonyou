package com.infoeai.eai.DTO;

public class Dcs2CtcaiDTO {
	
	private Long sequenceId;//    序列码
	private String actionCode;//	交易代码
	private String actionDate;//	交易日期
	private String actionTime;//	交易时间
	private String dealerCode;//	经销商代码
	private String rebateType;//	返利类型
	private String rebateCode;//	返利代码
	private String rebateAmount;//	返利金额
	private String operator;//	操作人
	private String remark;//	备注
	private String scanStatus;//   扫描状态
	
	public Long getSequenceId() {
		return sequenceId;
	}
	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionTime() {
		return actionTime;
	}
	public void setActionTime(String actionTime) {
		this.actionTime = actionTime;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	public String getRebateCode() {
		return rebateCode;
	}
	public void setRebateCode(String rebateCode) {
		this.rebateCode = rebateCode;
	}
	public String getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getScanStatus() {
		return scanStatus;
	}
	public void setScanStatus(String scanStatus) {
		this.scanStatus = scanStatus;
	}
	
	

}
