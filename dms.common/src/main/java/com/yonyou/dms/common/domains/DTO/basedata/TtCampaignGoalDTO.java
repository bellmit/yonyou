package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TtCampaignGoalDTO {

	private String memo;
	private String campaignCode;
	private Long updateBy;
	private Integer dKey;
	private Integer itemIndex;
	private Date updateDate;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private String goalItem;
	private Long itemId;
	private String entityCode;
	
	public String toXMLString() {
		
		return null;
	}
	public String getCampaignCode() {
		return campaignCode;
	}
	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getDKey() {
		return dKey;
	}
	public void setDKey(Integer key) {
		dKey = key;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getGoalItem() {
		return goalItem;
	}
	public void setGoalItem(String goalItem) {
		this.goalItem = goalItem;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Integer getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}


}
