package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class VoucherVO extends BaseVO{

	private String activityCode;//活动编号
	private String activityName;//活动名称
	private Integer activityIssueNumber;//活动发放卡券数量
	private Date activityStartDate;//活动开始日期
	private Date activityEndDate;// 活动结束日期
	private Integer releaseStatus;//发布状态(92201001:未发布;92201002:已发布;92201003:已终止)
	
	private String voucherNo;//卡券类型ID
	private String voucherName;//卡券名称
	private Integer voucherType;//卡券分类
	private Float singleAmount;//单张抵扣金额
	private Integer issueNumberLimit;//发放数量上限
	private Integer voucherStandard;//发券标准
	private Integer useRange;//使用范围
	private String repairType;//维修类型代码
	private String repairTypeName;//维修类型名称
	private String insuranceCompanyCode;//保险公司代码
	private String insuranceCompanyName;//保险公司名称
	private Date startDate;//卡券有效开始日期
	private Date endDate;//卡券有效结束日期
	
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
	public Integer getActivityIssueNumber() {
		return activityIssueNumber;
	}
	public void setActivityIssueNumber(Integer activityIssueNumber) {
		this.activityIssueNumber = activityIssueNumber;
	}
	public Date getActivityStartDate() {
		return activityStartDate;
	}
	public void setActivityStartDate(Date activityStartDate) {
		this.activityStartDate = activityStartDate;
	}
	public Date getActivityEndDate() {
		return activityEndDate;
	}
	public void setActivityEndDate(Date activityEndDate) {
		this.activityEndDate = activityEndDate;
	}
	public Integer getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(Integer releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	public Integer getVoucherType() {
		return voucherType;
	}
	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}
	public Float getSingleAmount() {
		return singleAmount;
	}
	public void setSingleAmount(Float singleAmount) {
		this.singleAmount = singleAmount;
	}
	public Integer getIssueNumberLimit() {
		return issueNumberLimit;
	}
	public void setIssueNumberLimit(Integer issueNumberLimit) {
		this.issueNumberLimit = issueNumberLimit;
	}
	public Integer getVoucherStandard() {
		return voucherStandard;
	}
	public void setVoucherStandard(Integer voucherStandard) {
		this.voucherStandard = voucherStandard;
	}
	public Integer getUseRange() {
		return useRange;
	}
	public void setUseRange(Integer useRange) {
		this.useRange = useRange;
	}
	public String getRepairType() {
		return repairType;
	}
	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}
	public String getRepairTypeName() {
		return repairTypeName;
	}
	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}
	public String getInsuranceCompanyCode() {
		return insuranceCompanyCode;
	}
	public void setInsuranceCompanyCode(String insuranceCompanyCode) {
		this.insuranceCompanyCode = insuranceCompanyCode;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
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
	
}
