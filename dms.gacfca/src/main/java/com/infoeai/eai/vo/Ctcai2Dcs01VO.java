package com.infoeai.eai.vo;


/**
 * 车辆返利使用结果回传--PO
 * @author dengweili 
 * add by 20130521
 */
@SuppressWarnings("serial")
public class Ctcai2Dcs01VO  extends BaseVO{
	
	private String vin;//	车架号
	private String actionCode;//	交易代码
	private String actionDate;//	交易日期
	private String actionTime;//	交易时间
	private String dealerCode;//	经销商代码
	private String rebateType;//	返利类型_不存在改字段
	private String rebateCode;//	使用返利代码
	private String rebateAmount;//	使用返利金额
	private String operator;//	操作人_不存在改字段
	private String remark;//	备注
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
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

}
