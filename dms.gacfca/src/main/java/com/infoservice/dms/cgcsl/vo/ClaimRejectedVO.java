package com.infoservice.dms.cgcsl.vo;


/**
 * 索赔单驳回和删除VO
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ClaimRejectedVO extends BaseVO {
	private String roNo; //工单号 CHAR(12)
	private String roStatus; //工单状态 NUMERIC(8) ：：未结算40021001 REPAIR_ORD_BALANCE_TYPE_01
	private Integer isDel;// 是否删除，0未删除   1已删除
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public String getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(String roStatus) {
		this.roStatus = roStatus;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	
	
	
	
	


}
