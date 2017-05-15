package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TtActivityResultDto {
	private String activityCode;// 活动编号 下端：ACTIVITY_CODE VARCHAR(15)
								// 上端：ACTIVITY_CODE VARCHAR(15)
	private String vin;// 参加活动车架号 下端：VIN VARCHAR(17) 上端：VIN VARCHAR(20)
	private String campaignDate;// 参加活动时间 下端：CAMPAIGN_DATE TIMESTAMP
								// 上端：EXECUTE_DATE TIMESTAMP
	private Date downTimestamp;// 下发时间 下端:DOWN_TIMESTAMP TIMESTAMP 上端
	private String activityName; // 下端：活动名称 ACTIVITY_NAME varchar(60)
									// 上端：ACTIVITY_NAME VARCHAR (200)
	private String dealerName; // 经销商名称
	private String entityCode; // 下端：经销商代码 CHAR(8) 上端：

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getCampaignDate() {
		return campaignDate;
	}

	public void setCampaignDate(String campaignDate) {
		this.campaignDate = campaignDate;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
}
