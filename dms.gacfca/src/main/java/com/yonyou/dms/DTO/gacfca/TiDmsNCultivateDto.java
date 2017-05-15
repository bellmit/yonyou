package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiDmsNCultivateDto {


	
	private String entityCode;
	private String uniquenessId;//DMS客户唯一ID
	private Long FCAID;//售中工具id 有就上报
	private Date commDate;//沟通日期
	private String commContent;//沟通内容
	private String dealerCode;//经销商代码
	private String nextCommContent;//下次沟通内容
	private Date nextCommDate;//下次沟通日期
	private String commType;//沟通方式
	private Date createDate;//创建时间
	private String dealerUserId;
	private String followOppLevelId;//跟进后客户级别


	public void setUniquenessId(String uniquenessId){
		this.uniquenessId=uniquenessId;
	}

	public String getUniquenessId(){
		return this.uniquenessId;
	}

	public void setCommDate(Date commDate){
		this.commDate=commDate;
	}

	public Date getCommDate(){
		return this.commDate;
	}

	public void setCommContent(String commContent){
		this.commContent=commContent;
	}

	public String getCommContent(){
		return this.commContent;
	}


	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}


	public void setNextCommContent(String nextCommContent){
		this.nextCommContent=nextCommContent;
	}

	public String getNextCommContent(){
		return this.nextCommContent;
	}

	public void setNextCommDate(Date nextCommDate){
		this.nextCommDate=nextCommDate;
	}

	public Date getNextCommDate(){
		return this.nextCommDate;
	}


	public void setCommType(String commType){
		this.commType=commType;
	}

	public String getCommType(){
		return this.commType;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setFollowOppLevelId(String followOppLevelId){
		this.followOppLevelId=followOppLevelId;
	}

	public String getFollowOppLevelId(){
		return this.followOppLevelId;
	}

//	public String toXMLString() {
//		return VOUtil.vo2Xml(this);
//	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Long getFCAID() {
		return FCAID;
	}

	public void setFCAID(Long fcaid) {
		FCAID = fcaid;
	}


}
