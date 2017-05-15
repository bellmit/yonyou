package com.yonyou.dms.vehicle.domains.DTO.bigCustomer;

public class TtBigCustomerAuthorityApprovalDTO {

	private  String authorityApprovalStatus;
	
	private String dealerCode;
	
	private String approvalRemark;
	
	public String getAuthorityApprovalStatus() {
		return authorityApprovalStatus;
	}

	public void setAuthorityApprovalStatus(String authorityApprovalStatus) {
		this.authorityApprovalStatus = authorityApprovalStatus;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	@Override
	public String toString() {
		return "TtBigCustomerAuthorityApprovalDTO [authorityApprovalStatus=" + authorityApprovalStatus + ", dealerCode="
				+ dealerCode + ", approvalRemark=" + approvalRemark + "]";
	}
	
}
