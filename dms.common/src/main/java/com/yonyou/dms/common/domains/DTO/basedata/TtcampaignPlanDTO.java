package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;
import java.util.LinkedList;

public class TtcampaignPlanDTO {

	private String applicant;
	private String refWebLink;
	private String distributerPhone;
	private Date updateDate;
	private Date endDate;
	private Long createBy;
	private Date applyDate;
	private String shortName;
	private String distributerName;
	private String entityCode;
	private String principalEmail;
	private String memo;
	private String principalPhone;
	private Long updateBy;
	private String curAuditor;
	private Date downTimestamp;
	private String distributerEmail;
	private String campaignCode;
	private Double campaignBudget;
	private Integer dKey;
	private String dealerCode;
	private Date beginDate;
	private String campaignName;
	private Integer dealerTag;
	private Integer downTag;
	private String targetCustomer;
	private Integer curAuditingStatus;
	private Integer ver;
	private Date createDate;
	private String seriesCode;
	private Integer campaignPerformType;
	private String principalName;
	private String spot;
	
	private String targetTraffic;
	private String targetNewCustomers;
	private String targetNewOrders;
	private String targetNewVehicles;
	
	
	public String getTargetTraffic() {
		return targetTraffic;
	}

	public void setTargetTraffic(String targetTraffic) {
		this.targetTraffic = targetTraffic;
	}

	public String getTargetNewCustomers() {
		return targetNewCustomers;
	}

	public void setTargetNewCustomers(String targetNewCustomers) {
		this.targetNewCustomers = targetNewCustomers;
	}

	public String getTargetNewOrders() {
		return targetNewOrders;
	}

	public void setTargetNewOrders(String targetNewOrders) {
		this.targetNewOrders = targetNewOrders;
	}

	public String getTargetNewVehicles() {
		return targetNewVehicles;
	}

	public void setTargetNewVehicles(String targetNewVehicles) {
		this.targetNewVehicles = targetNewVehicles;
	}

	

	private LinkedList<TtCampaignGoalDTO> GList;
	private LinkedList<TtCampaignPlanAttachedDTO> PlanAttachedList;
	private LinkedList<TtCampaignPopFeeDTO> PopFeeList;
	private LinkedList<TtCampaignPrFeeDTO> PrFeeList;
	private LinkedList<TtCampaignAdvertFeeDTO> AdvertFeeList;
	private LinkedList<TtCampaignPsalesFeeDTO> PsalesFeeList;
	private LinkedList<TtCampaignDealerDTO> dealerList;

	public String toXMLString() {

		return null;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Double getCampaignBudget() {
		return campaignBudget;
	}

	public void setCampaignBudget(Double campaignBudget) {
		this.campaignBudget = campaignBudget;
	}

	public String getCampaignCode() {
		return campaignCode;
	}

	public void setCampaignCode(String campaignCode) {
		this.campaignCode = campaignCode;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Integer getCampaignPerformType() {
		return campaignPerformType;
	}

	public void setCampaignPerformType(Integer campaignPerformType) {
		this.campaignPerformType = campaignPerformType;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCurAuditingStatus() {
		return curAuditingStatus;
	}

	public void setCurAuditingStatus(Integer curAuditingStatus) {
		this.curAuditingStatus = curAuditingStatus;
	}

	public String getCurAuditor() {
		return curAuditor;
	}

	public void setCurAuditor(String curAuditor) {
		this.curAuditor = curAuditor;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getDealerTag() {
		return dealerTag;
	}

	public void setDealerTag(Integer dealerTag) {
		this.dealerTag = dealerTag;
	}

	public String getDistributerEmail() {
		return distributerEmail;
	}

	public void setDistributerEmail(String distributerEmail) {
		this.distributerEmail = distributerEmail;
	}

	public String getDistributerName() {
		return distributerName;
	}

	public void setDistributerName(String distributerName) {
		this.distributerName = distributerName;
	}

	public String getDistributerPhone() {
		return distributerPhone;
	}

	public void setDistributerPhone(String distributerPhone) {
		this.distributerPhone = distributerPhone;
	}

	public Integer getDKey() {
		return dKey;
	}

	public void setDKey(Integer key) {
		dKey = key;
	}

	public Integer getDownTag() {
		return downTag;
	}

	public void setDownTag(Integer downTag) {
		this.downTag = downTag;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPrincipalEmail() {
		return principalEmail;
	}

	public void setPrincipalEmail(String principalEmail) {
		this.principalEmail = principalEmail;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalPhone() {
		return principalPhone;
	}

	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}

	public String getRefWebLink() {
		return refWebLink;
	}

	public void setRefWebLink(String refWebLink) {
		this.refWebLink = refWebLink;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSpot() {
		return spot;
	}

	public void setSpot(String spot) {
		this.spot = spot;
	}

	public String getTargetCustomer() {
		return targetCustomer;
	}

	public void setTargetCustomer(String targetCustomer) {
		this.targetCustomer = targetCustomer;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public LinkedList<TtCampaignAdvertFeeDTO> getAdvertFeeList() {
		return AdvertFeeList;
	}

	public void setAdvertFeeList(LinkedList<TtCampaignAdvertFeeDTO> advertFeeList) {
		AdvertFeeList = advertFeeList;
	}

	public LinkedList<TtCampaignDealerDTO> getDealerList() {
		return dealerList;
	}

	public void setDealerList(LinkedList<TtCampaignDealerDTO> dealerList) {
		this.dealerList = dealerList;
	}

	public LinkedList<TtCampaignGoalDTO> getGList() {
		return GList;
	}

	public void setGList(LinkedList<TtCampaignGoalDTO> list) {
		GList = list;
	}

	public LinkedList<TtCampaignPlanAttachedDTO> getPlanAttachedList() {
		return PlanAttachedList;
	}

	public void setPlanAttachedList(LinkedList<TtCampaignPlanAttachedDTO> planAttachedList) {
		PlanAttachedList = planAttachedList;
	}

	public LinkedList<TtCampaignPopFeeDTO> getPopFeeList() {
		return PopFeeList;
	}

	public void setPopFeeList(LinkedList<TtCampaignPopFeeDTO> popFeeList) {
		PopFeeList = popFeeList;
	}

	public LinkedList<TtCampaignPrFeeDTO> getPrFeeList() {
		return PrFeeList;
	}

	public void setPrFeeList(LinkedList<TtCampaignPrFeeDTO> prFeeList) {
		PrFeeList = prFeeList;
	}

	public LinkedList<TtCampaignPsalesFeeDTO> getPsalesFeeList() {
		return PsalesFeeList;
	}

	public void setPsalesFeeList(LinkedList<TtCampaignPsalesFeeDTO> psalesFeeList) {
		PsalesFeeList = psalesFeeList;
	}

}
