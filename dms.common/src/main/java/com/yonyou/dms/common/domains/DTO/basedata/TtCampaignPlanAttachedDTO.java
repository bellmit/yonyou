package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TtCampaignPlanAttachedDTO {

	private String fileName;
	private String campaignCode;
	private Long attached;
	private Long updateBy;
	private Integer dKey;
	private Date updateDate;
	private Long fileId;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private String entityCode;

	public String toXMLString() {
		
		return null;
	}

	public Long getAttached() {
		return attached;
	}

	public void setAttached(Long attached) {
		this.attached = attached;
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

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
