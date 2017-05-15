package com.yonyou.dms.retail.domains.DTO.rebate;

import java.util.Date;

public class RebateDetailDTO {
	private String vin;
	private Date updateDate;
	private String endMonth;
	private Long createBy;
	private Integer isDel;
	private Long oemCompanyId;
	private Long count;
	private Date releaseDate;
	private Long updateBy;
	private Long rebateId;
	private Double backBonusesDown;
	private Double newIncentives;
	private Double backBonusesEst;
	private String dealerCode;
	private String startMonth;
	private String modelName;
	private Double nomalBonus;
	private String dealerName;
	private Long logId;
	private Double specialBonus;
	private String businessPolicyName;
	private String applicableTime;
	private Integer ver;
	private Date createDate;
	private Integer isArc;

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEndMonth(String endMonth){
		this.endMonth=endMonth;
	}

	public String getEndMonth(){
		return this.endMonth;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setCount(Long count){
		this.count=count;
	}

	public Long getCount(){
		return this.count;
	}

	public void setReleaseDate(Date releaseDate){
		this.releaseDate=releaseDate;
	}

	public Date getReleaseDate(){
		return this.releaseDate;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setRebateId(Long rebateId){
		this.rebateId=rebateId;
	}

	public Long getRebateId(){
		return this.rebateId;
	}

	public void setBackBonusesDown(Double backBonusesDown){
		this.backBonusesDown=backBonusesDown;
	}

	public Double getBackBonusesDown(){
		return this.backBonusesDown;
	}

	public void setNewIncentives(Double newIncentives){
		this.newIncentives=newIncentives;
	}

	public Double getNewIncentives(){
		return this.newIncentives;
	}

	public void setBackBonusesEst(Double backBonusesEst){
		this.backBonusesEst=backBonusesEst;
	}

	public Double getBackBonusesEst(){
		return this.backBonusesEst;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setStartMonth(String startMonth){
		this.startMonth=startMonth;
	}

	public String getStartMonth(){
		return this.startMonth;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}

	public String getModelName(){
		return this.modelName;
	}

	public void setNomalBonus(Double nomalBonus){
		this.nomalBonus=nomalBonus;
	}

	public Double getNomalBonus(){
		return this.nomalBonus;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setLogId(Long logId){
		this.logId=logId;
	}

	public Long getLogId(){
		return this.logId;
	}

	public void setSpecialBonus(Double specialBonus){
		this.specialBonus=specialBonus;
	}

	public Double getSpecialBonus(){
		return this.specialBonus;
	}

	public void setBusinessPolicyName(String businessPolicyName){
		this.businessPolicyName=businessPolicyName;
	}

	public String getBusinessPolicyName(){
		return this.businessPolicyName;
	}

	public void setApplicableTime(String applicableTime){
		this.applicableTime=applicableTime;
	}

	public String getApplicableTime(){
		return this.applicableTime;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}
}
