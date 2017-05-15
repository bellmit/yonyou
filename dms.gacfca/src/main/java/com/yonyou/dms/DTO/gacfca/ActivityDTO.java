package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class ActivityDTO {
	private Integer isRepeatAttend;//是否可以多次参加
	private String entityCode;
	private String activityCode;
	private String activityName;
	private Integer activityType;
	private Date beginDate;
	private Date endDate;
	private Date salesDateBegin;
	private Date salesDateEnd;
	private Date releaseDate;
	private Integer releaseTag;
	private Double labourAmount;
	private Double repairPartAmount;
	private Double otherFee;
	private Double activityAmount;
	private Integer isClaim;
	private Integer activityProperty; //活动性质  added by liufeilu in 20130528
	private String globalActivityCode; //全球活动编号  added by lny in 20140811
	private String activityTitle; //活动主题  added by lny in 20140811
	private String attachmentUrl; //活动性质  added by lny in 20140811
	private Date downTimestamp;
	private Integer isValid;
	private LinkedList<ActivityLabourDTO> labourVoList;
	private LinkedList<ActivityPartDTO> partVoList;
	private LinkedList<ActivityVehicleDTO> vehicleVoList;
	private LinkedList<ActivityOtherFeeDTO> otherFeeVoList;
	private LinkedList<ActivityModelDTO> ModelVoList;
	
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
	public LinkedList<ActivityLabourDTO> getLabourVoList() {
		return labourVoList;
	}
	public void setLabourVoList(LinkedList<ActivityLabourDTO> labourVoList) {
		this.labourVoList = labourVoList;
	}
	public LinkedList<ActivityPartDTO> getPartVoList() {
		return partVoList;
	}
	public void setPartVoList(LinkedList<ActivityPartDTO> partVoList) {
		this.partVoList = partVoList;
	}
	public LinkedList<ActivityVehicleDTO> getVehicleVoList() {
		return vehicleVoList;
	}
	public void setVehicleVoList(LinkedList<ActivityVehicleDTO> vehicleVoList) {
		this.vehicleVoList = vehicleVoList;
	}
	public LinkedList<ActivityOtherFeeDTO> getOtherFeeVoList() {
		return otherFeeVoList;
	}
	public void setOtherFeeVoList(LinkedList<ActivityOtherFeeDTO> otherFeeVoList) {
		this.otherFeeVoList = otherFeeVoList;
	}
	public LinkedList<ActivityModelDTO> getModelVoList() {
		return ModelVoList;
	}
	public void setModelVoList(LinkedList<ActivityModelDTO> modelVoList) {
		ModelVoList = modelVoList;
	}
	public Integer getIsRepeatAttend() {
		return isRepeatAttend;
	}
	public void setIsRepeatAttend(Integer isRepeatAttend) {
		this.isRepeatAttend = isRepeatAttend;
	}
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
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getSalesDateBegin() {
		return salesDateBegin;
	}
	public void setSalesDateBegin(Date salesDateBegin) {
		this.salesDateBegin = salesDateBegin;
	}
	public Date getSalesDateEnd() {
		return salesDateEnd;
	}
	public void setSalesDateEnd(Date salesDateEnd) {
		this.salesDateEnd = salesDateEnd;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getReleaseTag() {
		return releaseTag;
	}
	public void setReleaseTag(Integer releaseTag) {
		this.releaseTag = releaseTag;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public Double getRepairPartAmount() {
		return repairPartAmount;
	}
	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Double getActivityAmount() {
		return activityAmount;
	}
	public void setActivityAmount(Double activityAmount) {
		this.activityAmount = activityAmount;
	}
	public Integer getIsClaim() {
		return isClaim;
	}
	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}
	public Integer getActivityProperty() {
		return activityProperty;
	}
	public void setActivityProperty(Integer activityProperty) {
		this.activityProperty = activityProperty;
	}
	public String getGlobalActivityCode() {
		return globalActivityCode;
	}
	public void setGlobalActivityCode(String globalActivityCode) {
		this.globalActivityCode = globalActivityCode;
	}
	public String getActivityTitle() {
		return activityTitle;
	}
	public void setActivityTitle(String activityTitle) {
		this.activityTitle = activityTitle;
	}
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
}
