package com.yonyou.dms.manage.domains.DTO.bulletin;

/**
 * 通告查看回复
 * @author Administrator
 *
 */
public class BulletinReplyDTO {
	
	private Long typeId;
	
	private Long bulletinId;
	
	private String dmsFileIds;   //回复附件
	
	private String reply;        //回复内容

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getBulletinId() {
		return bulletinId;
	}

	public void setBulletinId(Long bulletinId) {
		this.bulletinId = bulletinId;
	}

	public String getDmsFileIds() {
		return dmsFileIds;
	}

	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}
	
	

}
