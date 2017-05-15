package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class DiscountCouponVO extends BaseVO  {
	private static final long serialVersionUID = 1L;
	private String cardNo;//卡券编号
	private String cardName;//卡券名称
	private Integer cardType;//卡券类型
	private String subType;//卡券子类
	private Double cardValue;//卡券面值
	private Double buyAmount;//卡券购买金额
	private Date issueDate; //卡券发放时间
	private Date startDate;//卡券起始日期
	private Date endDate;//卡券结束日期
	private String useDealer;//指定使用经销商
	private String useProject;//指定使用项目
	private Integer useStatus;//卡券使用状态
	private Integer sameOverlapCount;//同项叠加次数
	private Integer isDifferentOverlap;//非同项是否叠加
	private String entityCode;
	private String vin;//车架号
	
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public double getCardValue() {
		return cardValue;
	}
	public void setCardValue(double cardValue) {
		this.cardValue = cardValue;
	}
	public double getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}
	public Date getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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
	public String getUseDealer() {
		return useDealer;
	}
	public void setUseDealer(String useDealer) {
		this.useDealer = useDealer;
	}
	public String getUseProject() {
		return useProject;
	}
	public void setUseProject(String useProject) {
		this.useProject = useProject;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	public Integer getSameOverlapCount() {
		return sameOverlapCount;
	}
	public void setSameOverlapCount(Integer sameOverlapCount) {
		this.sameOverlapCount = sameOverlapCount;
	}
	public Integer getIsDifferentOverlap() {
		return isDifferentOverlap;
	}
	public void setIsDifferentOverlap(Integer isDifferentOverlap) {
		this.isDifferentOverlap = isDifferentOverlap;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
}
