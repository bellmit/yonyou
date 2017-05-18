package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SEDCSP16DTO {

	private String entityCode;//经销商代码
	private String claimNo;//索赔单号
	private Integer claimStatus;//索赔单状态
	private Integer dutyBy;//责任供应商
	private Integer auditOptions;//处理意见	
	private String auditBy;//审核人
	private Date auditDate;//审核日期	YYYY-MM-DD
	private String auditRemark;//审核说明	
	private Integer isAduit;//业务区分值（审核结果下发:10041001  补发运单:10041002）
	private String reissueTransNo;//补发运单号
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public Integer getClaimStatus() {
		return claimStatus;
	}
	public void setClaimStatus(Integer claimStatus) {
		this.claimStatus = claimStatus;
	}
	public Integer getDutyBy() {
		return dutyBy;
	}
	public void setDutyBy(Integer dutyBy) {
		this.dutyBy = dutyBy;
	}
	public Integer getAuditOptions() {
		return auditOptions;
	}
	public void setAuditOptions(Integer auditOptions) {
		this.auditOptions = auditOptions;
	}
	public String getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public Integer getIsAduit() {
		return isAduit;
	}
	public void setIsAduit(Integer isAduit) {
		this.isAduit = isAduit;
	}
	public String getReissueTransNo() {
		return reissueTransNo;
	}
	public void setReissueTransNo(String reissueTransNo) {
		this.reissueTransNo = reissueTransNo;
	}

}
