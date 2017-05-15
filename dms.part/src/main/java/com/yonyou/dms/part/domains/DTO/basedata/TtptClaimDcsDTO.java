package com.yonyou.dms.part.domains.DTO.basedata;

public class TtptClaimDcsDTO {

	private String dutyBy;

	private String auditOptions;
	
	private String checkOpinion;
	
	private String reissueTransNo;

	public String getReissueTransNo() {
		return reissueTransNo;
	}

	public void setReissueTransNo(String reissueTransNo) {
		this.reissueTransNo = reissueTransNo;
	}
	
	public String getDutyBy() {
		return dutyBy;
	}

	public void setDutyBy(String dutyBy) {
		this.dutyBy = dutyBy;
	}

	public String getAuditOptions() {
		return auditOptions;
	}

	public void setAuditOptions(String auditOptions) {
		this.auditOptions = auditOptions;
	}

	public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
}
