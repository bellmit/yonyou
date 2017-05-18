/**
 * 
 */
package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

/**
 * @author sqh
 *
 */
public class TtRoTimeoutDTO {

	private String items;
	
	private String roNo;
	
	private Integer ownedBy;
	
	private Integer workStatus;
	
	private String remark;
	
	private String PART_NO;
	
	private String PART_NAME;
	
	private Double PART_QUANTITY;
	
	private String PURCHASE_ORDER_NO;
	
	private Date ORDER_DATE;
	
	private String REMARK;

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}

	public Integer getOwnedBy() {
		return ownedBy;
	}

	public void setOwnedBy(Integer ownedBy) {
		this.ownedBy = ownedBy;
	}

	public Integer getWorkStatus() {
		return workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPART_NO() {
		return PART_NO;
	}

	public void setPART_NO(String pART_NO) {
		PART_NO = pART_NO;
	}

	public String getPART_NAME() {
		return PART_NAME;
	}

	public void setPART_NAME(String pART_NAME) {
		PART_NAME = pART_NAME;
	}

	public Double getPART_QUANTITY() {
		return PART_QUANTITY;
	}

	public void setPART_QUANTITY(Double pART_QUANTITY) {
		PART_QUANTITY = pART_QUANTITY;
	}

	public String getPURCHASE_ORDER_NO() {
		return PURCHASE_ORDER_NO;
	}

	public void setPURCHASE_ORDER_NO(String pURCHASE_ORDER_NO) {
		PURCHASE_ORDER_NO = pURCHASE_ORDER_NO;
	}

	public Date getORDER_DATE() {
		return ORDER_DATE;
	}

	public void setORDER_DATE(Date oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}

	public String getREMARK() {
		return REMARK;
	}

	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	
	
}
