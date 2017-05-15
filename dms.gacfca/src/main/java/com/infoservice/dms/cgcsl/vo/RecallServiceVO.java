package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class RecallServiceVO extends BaseVO  {
	
	private static final long serialVersionUID = 1L;
	private String recallNo;//召回编号
	private String recallName;//召回名称
	private Integer recallTheme;//召回类别 70411001 RT快速反应、70411002 Recall召回
	private Integer recallType;//召回方式 70421001 必选、70421002 非必选
	private Date recallStartDate;//召回开始时间
	private Date recallEndDate;//召回结束时间
	private Integer isFixedCost;//是否固定费用10041001是 1001002否
	private Double manHourCost;//工时费用
	private Double partCost;//配件费用
	private Double otherCost;//其他费用
	private String recallExplain;//召回活动说明
	private String claimApplyGuidance;//索赔申请指导
	private Integer releaseTag;//发布状态
	private Date releaseDate;//发布时间
	
	private LinkedList<RecallServiceLabourVO> labourListVO;//工时信息
	private LinkedList<RecallServicePartVO> partListVO;//配件信息
	private LinkedList<RecallServiceVehicleVO> vehicleListVO;//车辆信息
	
	
	public Integer getReleaseTag() {
		return releaseTag;
	}
	public void setReleaseTag(Integer releaseTag) {
		this.releaseTag = releaseTag;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}
	public String getRecallName() {
		return recallName;
	}
	public void setRecallName(String recallName) {
		this.recallName = recallName;
	}
	public Integer getRecallTheme() {
		return recallTheme;
	}
	public void setRecallTheme(Integer recallTheme) {
		this.recallTheme = recallTheme;
	}
	public Integer getRecallType() {
		return recallType;
	}
	public void setRecallType(Integer recallType) {
		this.recallType = recallType;
	}
	public Date getRecallStartDate() {
		return recallStartDate;
	}
	public void setRecallStartDate(Date recallStartDate) {
		this.recallStartDate = recallStartDate;
	}
	public Date getRecallEndDate() {
		return recallEndDate;
	}
	public void setRecallEndDate(Date recallEndDate) {
		this.recallEndDate = recallEndDate;
	}
	public Integer getIsFixedCost() {
		return isFixedCost;
	}
	public void setIsFixedCost(Integer isFixedCost) {
		this.isFixedCost = isFixedCost;
	}
	public Double getManHourCost() {
		return manHourCost;
	}
	public void setManHourCost(Double manHourCost) {
		this.manHourCost = manHourCost;
	}
	public Double getPartCost() {
		return partCost;
	}
	public void setPartCost(Double partCost) {
		this.partCost = partCost;
	}
	public Double getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}
	public String getRecallExplain() {
		return recallExplain;
	}
	public void setRecallExplain(String recallExplain) {
		this.recallExplain = recallExplain;
	}
	public String getClaimApplyGuidance() {
		return claimApplyGuidance;
	}
	public void setClaimApplyGuidance(String claimApplyGuidance) {
		this.claimApplyGuidance = claimApplyGuidance;
	}
	public LinkedList<RecallServiceLabourVO> getLabourListVO() {
		return labourListVO;
	}
	public void setLabourListVO(LinkedList<RecallServiceLabourVO> labourListVO) {
		this.labourListVO = labourListVO;
	}
	public LinkedList<RecallServicePartVO> getPartListVO() {
		return partListVO;
	}
	public void setPartListVO(LinkedList<RecallServicePartVO> partListVO) {
		this.partListVO = partListVO;
	}
	public LinkedList<RecallServiceVehicleVO> getVehicleListVO() {
		return vehicleListVO;
	}
	public void setVehicleListVO(LinkedList<RecallServiceVehicleVO> vehicleListVO) {
		this.vehicleListVO = vehicleListVO;
	}
	
}
