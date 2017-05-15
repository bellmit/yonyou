package com.yonyou.dms.vehicle.domains.DTO.basedata;

/**
* 客户投诉
* @author ZhaoZ
* @date 2017年4月20日
*/
public class TtCrComplaintsDcsDTO {

	private String alloDate;
	private String dealUser;
	private String dealPlan;
	private String customFeedback;
	private String noReturnMark;
	private String isR;
	//审核意见
	private String noReturnBackRemark;
	
	public String getAlloDate() {
		return alloDate;
	}
	public void setAlloDate(String alloDate) {
		this.alloDate = alloDate;
	}
	public String getDealUser() {
		return dealUser;
	}
	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}
	public String getDealPlan() {
		return dealPlan;
	}
	public void setDealPlan(String dealPlan) {
		this.dealPlan = dealPlan;
	}
	public String getCustomFeedback() {
		return customFeedback;
	}
	public void setCustomFeedback(String customFeedback) {
		this.customFeedback = customFeedback;
	}
	public String getNoReturnMark() {
		return noReturnMark;
	}
	public void setNoReturnMark(String noReturnMark) {
		this.noReturnMark = noReturnMark;
	}
	public String getIsR() {
		return isR;
	}
	public void setIsR(String isR) {
		this.isR = isR;
	}
	public String getNoReturnBackRemark() {
		return noReturnBackRemark;
	}
	public void setNoReturnBackRemark(String noReturnBackRemark) {
		this.noReturnBackRemark = noReturnBackRemark;
	}
	
	
	
	
	
	
}
