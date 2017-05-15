package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiAppSendVerificationDto {
	
	private Long sendVerification;
	private String fileType;
	private String uniquenessId;
	private String dealerCode;
	private String failureCode;
	private String failReason;
	private Date executeDate;
	
	
	public Long getSendVerification() {
		return sendVerification;
	}
	public void setSendVerification(Long sendVerification) {
		this.sendVerification = sendVerification;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getUniquenessId() {
		return uniquenessId;
	}
	public void setUniquenessId(String uniquenessId) {
		this.uniquenessId = uniquenessId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getFailureCode() {
		return failureCode;
	}
	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public Date getExecuteDate() {
		return executeDate;
	}
	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}
	
	

}
