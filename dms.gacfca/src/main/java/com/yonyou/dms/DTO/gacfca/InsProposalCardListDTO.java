package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class InsProposalCardListDTO {
	
	private String cardID;//卡券ID
	private String cardType;//卡券类型
	private Integer useStatus;//状态
	private Integer voucherStandard;//发券标准
	private String repairType;//维修类型
	private Date issueDate;//卡券发放日期
	private String activityCode;//营销活动ID
	
	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public Integer getVoucherStandard() {
		return voucherStandard;
	}

	public void setVoucherStandard(Integer voucherStandard) {
		this.voucherStandard = voucherStandard;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}
}
