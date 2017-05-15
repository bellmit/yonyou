package com.yonyou.dms.DTO.gacfca;
/**
 * 
* @ClassName: SADMS003ForeReturnDTO 
* @Description: DCC建档客户信息反馈接收
* @author zhengzengliang 
* @date 2017年4月13日 上午9:44:27 
*
 */
public class SADMS003ForeReturnDTO {
	
	private String dmsCustomerNo;
	private String conflictedType;
	private String salesConsultant;
	private Integer opportunityLevelID;//下端字典 dcs是一致的
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
