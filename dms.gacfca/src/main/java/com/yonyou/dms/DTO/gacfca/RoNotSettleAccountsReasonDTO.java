package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * @description 工单未结算原因
 * @author Administrator
 *
 */
public class RoNotSettleAccountsReasonDTO {
	private String roNo; //工单号   
	private String operator; //填写人
	private String waitWorkReason; //待工原因
	private String waitWorkRemark; //待工备注（待料备注）
	private String waitMaterialPartName ; //待料名称
	private String waitMaterialPartCode; //待料代码
	private String waitMaterialOrderNo; //待料订单号
	private Date waitMaterialOrderDate; //待料订单日期
	private String waitMaterialRemark; //待料备注
	private Float waitMaterialPartQuantity; //待料数量
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getWaitWorkReason() {
		return waitWorkReason;
	}
	public void setWaitWorkReason(String waitWorkReason) {
		this.waitWorkReason = waitWorkReason;
	}
	public String getWaitWorkRemark() {
		return waitWorkRemark;
	}
	public void setWaitWorkRemark(String waitWorkRemark) {
		this.waitWorkRemark = waitWorkRemark;
	}
	public String getWaitMaterialPartName() {
		return waitMaterialPartName;
	}
	public void setWaitMaterialPartName(String waitMaterialPartName) {
		this.waitMaterialPartName = waitMaterialPartName;
	}
	public String getWaitMaterialPartCode() {
		return waitMaterialPartCode;
	}
	public void setWaitMaterialPartCode(String waitMaterialPartCode) {
		this.waitMaterialPartCode = waitMaterialPartCode;
	}
	public String getWaitMaterialOrderNo() {
		return waitMaterialOrderNo;
	}
	public void setWaitMaterialOrderNo(String waitMaterialOrderNo) {
		this.waitMaterialOrderNo = waitMaterialOrderNo;
	}
	public Date getWaitMaterialOrderDate() {
		return waitMaterialOrderDate;
	}
	public void setWaitMaterialOrderDate(Date waitMaterialOrderDate) {
		this.waitMaterialOrderDate = waitMaterialOrderDate;
	}
	public String getWaitMaterialRemark() {
		return waitMaterialRemark;
	}
	public void setWaitMaterialRemark(String waitMaterialRemark) {
		this.waitMaterialRemark = waitMaterialRemark;
	}
	public Float getWaitMaterialPartQuantity() {
		return waitMaterialPartQuantity;
	}
	public void setWaitMaterialPartQuantity(Float waitMaterialPartQuantity) {
		this.waitMaterialPartQuantity = waitMaterialPartQuantity;
	}
}
