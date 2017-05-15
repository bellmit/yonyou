package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class ActivityResultVO  extends BaseVO{
	private static final long serialVersionUID = 1L;
	private String activityCode; //下端：活动编号 ACTIVITY_CODE varchar(15)  上端：ACTIVITY_CODE VARCHAR (60) 
	private String activityName; //下端：活动名称 ACTIVITY_NAME varchar(60)  上端：ACTIVITY_NAME VARCHAR (200) 
	private String realEntityCode; //下端：实际维修站代码 REAL_ENTITY CHAR(8)  上端：DEALER_CODE VARCHAR (60) 
	private String realEntityName; //下端：实际维修站名称  VARCHAR(150)  上端：DEALER_NAME VARCHAR (200) 
	private String vin; //下端：VIN VIN varchar(17)  上端：VIN VARCHAR (60) 
	private Date CampaignDate; //下端：参加活动时间 REPAIR_DATE TIMESTAMP  上端：EXECUTE_DATE TIMESTAMP 
	private Date downTimestamp; //下端：下发时序 DOWN_TIMESTAMP TIMESTAMP  上端：  
	private Integer isValid; //下端：有效状态 IS_VALID NUMERIC(8) 下端传以下值 10011001 有效 上端：  "
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getRealEntityCode() {
		return realEntityCode;
	}
	public void setRealEntityCode(String realEntityCode) {
		this.realEntityCode = realEntityCode;
	}
	public String getRealEntityName() {
		return realEntityName;
	}
	public void setRealEntityName(String realEntityName) {
		this.realEntityName = realEntityName;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getCampaignDate() {
		return CampaignDate;
	}
	public void setCampaignDate(Date campaignDate) {
		CampaignDate = campaignDate;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
}
