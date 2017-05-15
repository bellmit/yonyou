package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TtCampaignDealerDTO {

	private String campaignCode;
	private Integer dKey;
	private String dealerCode;
	private Date updateDate;
	private Long createBy;
	private Long itemId;
	private String shortName;
	private Integer downTag;
	private String entityCode;
	private Long updateBy;
	private Integer ver;
	private Date createDate;
	private Date downTimestamp;

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

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getDKey() {
		return dKey;
	}

	public void setDKey(Integer key) {
		dKey = key;
	}

	public Integer getDownTag() {
		return downTag;
	}

	public void setDownTag(Integer downTag) {
		this.downTag = downTag;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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
