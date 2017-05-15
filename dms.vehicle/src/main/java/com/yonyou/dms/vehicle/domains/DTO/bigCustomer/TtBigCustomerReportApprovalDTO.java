package com.yonyou.dms.vehicle.domains.DTO.bigCustomer;

public class TtBigCustomerReportApprovalDTO {

	private String customerCompanyCode;
	private Integer reportApprovalStatus;
	private String wsno;
	private String reportApprovalRemark;
	private String dealerCode;
	
	
	public String getCustomerCompanyCode() {
		return customerCompanyCode;
	}

	public void setCustomerCompanyCode(String customerCompanyCode) {
		this.customerCompanyCode = customerCompanyCode;
	}

	public Integer getReportApprovalStatus() {
		return reportApprovalStatus;
	}

	public void setReportApprovalStatus(Integer reportApprovalStatus) {
		this.reportApprovalStatus = reportApprovalStatus;
	}

	public String getWsno() {
		return wsno;
	}

	public void setWsno(String wsno) {
		this.wsno = wsno;
	}

	public String getReportApprovalRemark() {
		return reportApprovalRemark;
	}

	public void setReportApprovalRemark(String reportApprovalRemark) {
		this.reportApprovalRemark = reportApprovalRemark;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
}
