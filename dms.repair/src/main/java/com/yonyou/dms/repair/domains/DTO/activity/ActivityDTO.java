
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ActivityDTO.java
*
* @Author : jcsi
*
* @Date : 2016年12月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月22日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.activity;

import java.util.Date;
import java.util.List;

/**
* 服务活动（主单）
* @author jcsi
* @date 2016年12月22日
*/

public class ActivityDTO {
	
	private String activityCode;   //活动编号
	
	private String activityName;   //活动名称
	
	private Long activityType;   //活动类型
	
	private Long activityKind;   //活动类别
	
	private Date beginDate;   //开始日期
	
	private Date endDate;   //结束日期
	
	private Date releaseDate;   //发布日期
	
	private Long releaseTag;   //发布状态
	
	private Double labourAmount;   //活动维修项目费（工时费）
	
	private Double addItemAmount;   //附加项目费
	
	private Double repairPartAmount;   //维修材料费
	
	private Double activityAmount;   //活动总金额
	
	private String fromEntity;   //数据来源
	
	private String claimGuide;   //索赔申请填写指导
	
	private String solution;   //解决方案说明
	
	private Long isClaim;   //是否索赔
	
	private Double worktimeFee;   //工时费用
	
	private Double partFee;   //配件费用
	
	private Long isFree;   //是否免费
	
	private Long downTag;   //下发标志
	
	private Date downTimestamp;   //下发时序
	
	private Long isValid;   //是否有效
	
	private Long isPartActivity;   //是否配件活动
	
	private Long activityFirst;   //是否活动优先
	
	private String launchEntity;   //发起机构
	
	private String launchEntityName;   //发起机构名称
	
	private Date salesDateBegin;   //销售开始日期
	
	private Date salesDateEnd;   //销售结束日期
	
	private Double mileageBegin;   //行程开始里程
	
	private Double mileageEnd;   //行程结束里程
	
	private Long isRepeatAttend;   //本活动可以多次参加
	
	private Long vehiclePurpose;   //车辆用途
	
	private int activitySumCount;   //活动总台次
	
	private int activityCount;   //单台能参加活动总次数
	
	private Date productDateBegin;   //制造起始日期
	
	private Date productDateEnd;   //制造结束日期
	
	private Long businessType;   //销售业务类型
	
	private String shareEntityCode;   //共享ENTITY_CODE
	
	private Date maintainBeginDate;   //保养开始日期
	
	private Long shareActivityCode;   //共享活动代码
	
	private Date maintainEndDate;   //保养结束日期
	
	private Double maintainEndDay;  //保养结束天数
	
	private Double maintainBeginDay;  //保养开始天数
	
	private Double maintainBeginMileage;  //保养开始里程
	
	private Double maintainEndMileage;  //保养结束里程
	
	private Long isRecallActivity; //是否召回活动
	
	private String remark;  //备注
	
	private Long partsIsModify;  //配件书否可编辑
	
	private Long activityProperty;  //活动性质
	
	private String globalActivityCode; //全球活动编号
	
	private String activityTitle;  //全球活动主题
	
	private List<ActivityLabourDTO> labourList;
	
	private List<ActivityPartDTO> partList;
	
	private List<ActivityAddDTO> subjoinList;
	
	private List<ActivityModelDTO> modelList;
	
    private String delLabours;    
    
    private String delParts;
    
    private String delSubjoins;
    
    private String delModels;


    
    
	public String getDelLabours() {
		return delLabours;
	}

	public void setDelLabours(String delLabours) {
		this.delLabours = delLabours;
	}

	public String getDelParts() {
		return delParts;
	}

	public void setDelParts(String delParts) {
		this.delParts = delParts;
	}

	public String getDelSubjoins() {
		return delSubjoins;
	}

	public void setDelSubjoins(String delSubjoins) {
		this.delSubjoins = delSubjoins;
	}

	public String getDelModels() {
		return delModels;
	}

	public void setDelModels(String delModels) {
		this.delModels = delModels;
	}

	public List<ActivityLabourDTO> getLabourList() {
		return labourList;
	}

	public void setLabourList(List<ActivityLabourDTO> labourList) {
		this.labourList = labourList;
	}

	public List<ActivityPartDTO> getPartList() {
		return partList;
	}

	public void setPartList(List<ActivityPartDTO> partList) {
		this.partList = partList;
	}

	public List<ActivityAddDTO> getSubjoinList() {
		return subjoinList;
	}

	public void setSubjoinList(List<ActivityAddDTO> subjoinList) {
		this.subjoinList = subjoinList;
	}

	public List<ActivityModelDTO> getModelList() {
		return modelList;
	}

	public void setModelList(List<ActivityModelDTO> modelList) {
		this.modelList = modelList;
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

	public Long getActivityType() {
		return activityType;
	}

	public void setActivityType(Long activityType) {
		this.activityType = activityType;
	}

	public Long getActivityKind() {
		return activityKind;
	}

	public void setActivityKind(Long activityKind) {
		this.activityKind = activityKind;
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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long getReleaseTag() {
		return releaseTag;
	}

	public void setReleaseTag(Long releaseTag) {
		this.releaseTag = releaseTag;
	}

	public Double getLabourAmount() {
		return labourAmount;
	}

	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}

	public Double getAddItemAmount() {
		return addItemAmount;
	}

	public void setAddItemAmount(Double addItemAmount) {
		this.addItemAmount = addItemAmount;
	}

	public Double getRepairPartAmount() {
		return repairPartAmount;
	}

	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}

	public Double getActivityAmount() {
		return activityAmount;
	}

	public void setActivityAmount(Double activityAmount) {
		this.activityAmount = activityAmount;
	}

	public String getFromEntity() {
		return fromEntity;
	}

	public void setFromEntity(String fromEntity) {
		this.fromEntity = fromEntity;
	}

	public String getClaimGuide() {
		return claimGuide;
	}

	public void setClaimGuide(String claimGuide) {
		this.claimGuide = claimGuide;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Long getIsClaim() {
		return isClaim;
	}

	public void setIsClaim(Long isClaim) {
		this.isClaim = isClaim;
	}

	public Double getWorktimeFee() {
		return worktimeFee;
	}

	public void setWorktimeFee(Double worktimeFee) {
		this.worktimeFee = worktimeFee;
	}

	public Double getPartFee() {
		return partFee;
	}

	public void setPartFee(Double partFee) {
		this.partFee = partFee;
	}

	public Long getIsFree() {
		return isFree;
	}

	public void setIsFree(Long isFree) {
		this.isFree = isFree;
	}

	public Long getDownTag() {
		return downTag;
	}

	public void setDownTag(Long downTag) {
		this.downTag = downTag;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public Long getIsValid() {
		return isValid;
	}

	public void setIsValid(Long isValid) {
		this.isValid = isValid;
	}

	public Long getIsPartActivity() {
		return isPartActivity;
	}

	public void setIsPartActivity(Long isPartActivity) {
		this.isPartActivity = isPartActivity;
	}

	public Long getActivityFirst() {
		return activityFirst;
	}

	public void setActivityFirst(Long activityFirst) {
		this.activityFirst = activityFirst;
	}

	public String getLaunchEntity() {
		return launchEntity;
	}

	public void setLaunchEntity(String launchEntity) {
		this.launchEntity = launchEntity;
	}

	public String getLaunchEntityName() {
		return launchEntityName;
	}

	public void setLaunchEntityName(String launchEntityName) {
		this.launchEntityName = launchEntityName;
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

	public Double getMileageBegin() {
		return mileageBegin;
	}

	public void setMileageBegin(Double mileageBegin) {
		this.mileageBegin = mileageBegin;
	}

	public Double getMileageEnd() {
		return mileageEnd;
	}

	public void setMileageEnd(Double mileageEnd) {
		this.mileageEnd = mileageEnd;
	}

	public Long getIsRepeatAttend() {
		return isRepeatAttend;
	}

	public void setIsRepeatAttend(Long isRepeatAttend) {
		this.isRepeatAttend = isRepeatAttend;
	}

	public Long getVehiclePurpose() {
		return vehiclePurpose;
	}

	public void setVehiclePurpose(Long vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}

	public int getActivitySumCount() {
		return activitySumCount;
	}

	public void setActivitySumCount(int activitySumCount) {
		this.activitySumCount = activitySumCount;
	}

	public int getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(int activityCount) {
		this.activityCount = activityCount;
	}

	public Date getProductDateBegin() {
		return productDateBegin;
	}

	public void setProductDateBegin(Date productDateBegin) {
		this.productDateBegin = productDateBegin;
	}

	public Date getProductDateEnd() {
		return productDateEnd;
	}

	public void setProductDateEnd(Date productDateEnd) {
		this.productDateEnd = productDateEnd;
	}

	public Long getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Long businessType) {
		this.businessType = businessType;
	}

	public String getShareEntityCode() {
		return shareEntityCode;
	}

	public void setShareEntityCode(String shareEntityCode) {
		this.shareEntityCode = shareEntityCode;
	}

	public Date getMaintainBeginDate() {
		return maintainBeginDate;
	}

	public void setMaintainBeginDate(Date maintainBeginDate) {
		this.maintainBeginDate = maintainBeginDate;
	}

	public Long getShareActivityCode() {
		return shareActivityCode;
	}

	public void setShareActivityCode(Long shareActivityCode) {
		this.shareActivityCode = shareActivityCode;
	}

	public Date getMaintainEndDate() {
		return maintainEndDate;
	}

	public void setMaintainEndDate(Date maintainEndDate) {
		this.maintainEndDate = maintainEndDate;
	}

	public Double getMaintainEndDay() {
		return maintainEndDay;
	}

	public void setMaintainEndDay(Double maintainEndDay) {
		this.maintainEndDay = maintainEndDay;
	}

	public Double getMaintainBeginDay() {
		return maintainBeginDay;
	}

	public void setMaintainBeginDay(Double maintainBeginDay) {
		this.maintainBeginDay = maintainBeginDay;
	}

	public Double getMaintainBeginMileage() {
		return maintainBeginMileage;
	}

	public void setMaintainBeginMileage(Double maintainBeginMileage) {
		this.maintainBeginMileage = maintainBeginMileage;
	}

	public Double getMaintainEndMileage() {
		return maintainEndMileage;
	}

	public void setMaintainEndMileage(Double maintainEndMileage) {
		this.maintainEndMileage = maintainEndMileage;
	}

	public Long getIsRecallActivity() {
		return isRecallActivity;
	}

	public void setIsRecallActivity(Long isRecallActivity) {
		this.isRecallActivity = isRecallActivity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPartsIsModify() {
		return partsIsModify;
	}

	public void setPartsIsModify(Long partsIsModify) {
		this.partsIsModify = partsIsModify;
	}

	public Long getActivityProperty() {
		return activityProperty;
	}

	public void setActivityProperty(Long activityProperty) {
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
	
	
	
	
	

}
