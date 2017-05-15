package com.yonyou.dms.retail.domains.DTO.market;

/**
 * 活动目标
 * @author Benzc
 * @date 2017年2月27日
 */
public class CampaignGoalDTO {
	
	private String goalItem;
	
	private Integer itemIndex;
	
	private String memo;

	public String getGoalItem() {
		return goalItem;
	}

	public void setGoalItem(String goalItem) {
		this.goalItem = goalItem;
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
	
	

}
