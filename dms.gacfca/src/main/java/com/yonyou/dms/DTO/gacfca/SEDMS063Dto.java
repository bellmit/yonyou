package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SEDMS063Dto {
	
	private String dealerCode;
	private Integer backloggedDate;//积压期（月）
	private Integer validityDate;//发布有效期
	private String slowMovingName;//呆滞品定义名称
	private Date updateTime;//下发时间
	private Long itemId;//唯一键
	private String remark;//备注
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getBackloggedDate() {
		return backloggedDate;
	}
	public void setBackloggedDate(Integer backloggedDate) {
		this.backloggedDate = backloggedDate;
	}
	public Integer getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(Integer validityDate) {
		this.validityDate = validityDate;
	}
	public String getSlowMovingName() {
		return slowMovingName;
	}
	public void setSlowMovingName(String slowMovingName) {
		this.slowMovingName = slowMovingName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
