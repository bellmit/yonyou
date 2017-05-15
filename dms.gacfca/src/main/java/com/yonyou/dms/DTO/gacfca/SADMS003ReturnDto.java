package com.yonyou.dms.DTO.gacfca;

public class SADMS003ReturnDto {
	
	private String dmsCustomerNo;
	private String conflictedType;
	private String salesConsultant;
	private Integer opportunityLevelID;
	private String dealerCode;
	private Long nid ;//上端主键
	public String getDmsCustomerNo() {
		return dmsCustomerNo;
	}
	public void setDmsCustomerNo(String dmsCustomerNo) {
		this.dmsCustomerNo = dmsCustomerNo;
	}
	public String getConflictedType() {
		return conflictedType;
	}
	public void setConflictedType(String conflictedType) {
		this.conflictedType = conflictedType;
	}
	public String getSalesConsultant() {
		return salesConsultant;
	}
	public void setSalesConsultant(String salesConsultant) {
		this.salesConsultant = salesConsultant;
	}
	public Integer getOpportunityLevelID() {
		return opportunityLevelID;
	}
	public void setOpportunityLevelID(Integer opportunityLevelID) {
		this.opportunityLevelID = opportunityLevelID;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getNid() {
		return nid;
	}
	public void setNid(Long nid) {
		this.nid = nid;
	}

}
