package com.yonyou.dms.vehicle.domains.DTO.claimManage;

public class ClaimManageDTO {
	
	private Integer aduitInfo;	  	//审核状态	
									
	private String approvalRemark;	//审核意见
	
	private Long claimId;
	
	private String claimNo;
	
	private String dealerCode;
	
	private Integer selectGategory;  //选择分类
	
	private Integer selectModel; 	 //选择方式

	public Integer getAduitInfo() {
		return aduitInfo;
	}

	public void setAduitInfo(Integer aduitInfo) {
		this.aduitInfo = aduitInfo;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getSelectGategory() {
		return selectGategory;
	}

	public void setSelectGategory(Integer selectGategory) {
		this.selectGategory = selectGategory;
	}

	public Integer getSelectModel() {
		return selectModel;
	}

	public void setSelectModel(Integer selectModel) {
		this.selectModel = selectModel;
	}
	
	
	

}
