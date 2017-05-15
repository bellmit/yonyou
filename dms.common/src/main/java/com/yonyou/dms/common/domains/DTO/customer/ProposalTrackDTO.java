package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class ProposalTrackDTO {
	/**
	 * 跟踪信息表
	 * 
	 * @author sqh
	 *
	 */
	
	private String proposalCode;
	
	private String trackContent;
	
	private Integer trackType;
	
	private String failingReason;
	
	private Date trackNextDate;
	
	private String tracer;
	
	private String customerFeedback;
	
	private String remark;
	
	private Date inputDate;

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public String getTrackContent() {
		return trackContent;
	}

	public void setTrackContent(String trackContent) {
		this.trackContent = trackContent;
	}

	public Integer getTrackType() {
		return trackType;
	}

	public void setTrackType(Integer trackType) {
		this.trackType = trackType;
	}

	public String getFailingReason() {
		return failingReason;
	}

	public void setFailingReason(String failingReason) {
		this.failingReason = failingReason;
	}

	public Date getTrackNextDate() {
		return trackNextDate;
	}

	public void setTrackNextDate(Date trackNextDate) {
		this.trackNextDate = trackNextDate;
	}

	public String getTracer() {
		return tracer;
	}

	public void setTracer(String tracer) {
		this.tracer = tracer;
	}

	public String getCustomerFeedback() {
		return customerFeedback;
	}

	public void setCustomerFeedback(String customerFeedback) {
		this.customerFeedback = customerFeedback;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	
	
}
