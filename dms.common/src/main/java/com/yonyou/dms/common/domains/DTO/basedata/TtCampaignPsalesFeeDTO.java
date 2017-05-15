package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TtCampaignPsalesFeeDTO {

	private String campaignCode;
	private String items;
	private Long updateBy;
	private Integer dKey;
	private Double itemsBudget;
	private Date updateDate;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Long itemId;
	private String itemsDesc;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public Double getItemsBudget() {
		return itemsBudget;
	}

	public void setItemsBudget(Double itemsBudget) {
		this.itemsBudget = itemsBudget;
	}

	public String getItemsDesc() {
		return itemsDesc;
	}

	public void setItemsDesc(String itemsDesc) {
		this.itemsDesc = itemsDesc;
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
