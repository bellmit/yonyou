package com.yonyou.dms.vehicle.domains.DTO.activityManage;

import java.io.File;
import java.sql.Date;

/**
* @author liujiming
* @date 2017年3月23日
*/
public class TtWrActivityDTO {	
	private Long activityId;		//活动ID
	private String activityCode;	//活动系统编号
	private String activityName;	//活动名称
	private String globalActivityCode;	//活动编号
	private String activityTitle;	//活动主题	
	private Integer activityType;  // 活动类别
	private Integer claimType;		//索赔类型
	private Date startDate; 	//开始日期
	private Date endDate; 	//结束日期	
	private Date summClosedate; //上传活动总结日期
	private Integer isFee;			//是否固定费用
	private Integer isMulti;		//是否可多次
	private Double labourFee;		//工时费用
	private Double partFee;			//配件费用
	private Double otherFee;		//其他费用
	private Integer chooseWay;		//活动选择方式
	private File isFile;			//附件
	private String fileUrl;			//附件路径
	private String explans;			//解决方案说明
	private String guide;			//索赔申请指导
	
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
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
	public Integer getActivityType() {
		return activityType;
	}
	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	public Integer getClaimType() {
		return claimType;
	}
	public void setClaimType(Integer claimType) {
		this.claimType = claimType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getSummClosedate() {
		return summClosedate;
	}
	public void setSummClosedate(Date summClosedate) {
		this.summClosedate = summClosedate;
	}
	public Integer getIsFee() {
		return isFee;
	}
	public void setIsFee(Integer isFee) {
		this.isFee = isFee;
	}
	public Integer getIsMulti() {
		return isMulti;
	}
	public void setIsMulti(Integer isMulti) {
		this.isMulti = isMulti;
	}
	public Double getLabourFee() {
		return labourFee;
	}
	public void setLabourFee(Double labourFee) {
		this.labourFee = labourFee;
	}
	public Double getPartFee() {
		return partFee;
	}
	public void setPartFee(Double partFee) {
		this.partFee = partFee;
	}
	public Double getOtherFee() {
		return otherFee;
	}
	public void setOtherFee(Double otherFee) {
		this.otherFee = otherFee;
	}
	public Integer getChooseWay() {
		return chooseWay;
	}
	public void setChooseWay(Integer chooseWay) {
		this.chooseWay = chooseWay;
	}
	public File getIsFile() {
		return isFile;
	}
	public void setIsFile(File isFile) {
		this.isFile = isFile;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getExplans() {
		return explans;
	}
	public void setExplans(String explans) {
		this.explans = explans;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}

	
}
