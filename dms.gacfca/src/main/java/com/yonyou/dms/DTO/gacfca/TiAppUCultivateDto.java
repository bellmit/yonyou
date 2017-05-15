package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiAppUCultivateDto {
	
	private Long cultivateId;
	private String uniquenessID;
	private Integer FCAID;
	private Date commData;
	private String commType;
	private String commContent;
	private String followOppLevelID;
	private Date nextCommDate;
	private String nextCommContent;
	private String dealerUserID;
	private String dealerCode;
	private Date updateDate;
	
	public Long getCultivateId() {
		return cultivateId;
	}
	public void setCultivateId(Long cultivateId) {
		this.cultivateId = cultivateId;
	}
	public String getUniquenessID() {
		return uniquenessID;
	}
	public void setUniquenessID(String uniquenessID) {
		this.uniquenessID = uniquenessID;
	}
	public Integer getFCAID() {
		return FCAID;
	}
	public void setFCAID(Integer fCAID) {
		FCAID = fCAID;
	}
	public Date getCommData() {
		return commData;
	}
	public void setCommData(Date commData) {
		this.commData = commData;
	}
	public String getCommType() {
		return commType;
	}
	public void setCommType(String commType) {
		this.commType = commType;
	}
	public String getCommContent() {
		return commContent;
	}
	public void setCommContent(String commContent) {
		this.commContent = commContent;
	}
	public String getFollowOppLevelID() {
		return followOppLevelID;
	}
	public void setFollowOppLevelID(String followOppLevelID) {
		this.followOppLevelID = followOppLevelID;
	}
	public Date getNextCommDate() {
		return nextCommDate;
	}
	public void setNextCommDate(Date nextCommDate) {
		this.nextCommDate = nextCommDate;
	}
	public String getNextCommContent() {
		return nextCommContent;
	}
	public void setNextCommContent(String nextCommContent) {
		this.nextCommContent = nextCommContent;
	}
	public String getDealerUserID() {
		return dealerUserID;
	}
	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	

}
