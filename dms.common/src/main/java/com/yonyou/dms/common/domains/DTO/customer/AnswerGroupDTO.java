package com.yonyou.dms.common.domains.DTO.customer;
import java.util.Date;
public class AnswerGroupDTO {
	private String  dealerCode;
	private String fromEntity;
	private Integer isValid;
	private Integer ver;
	private Integer downTag;
	private String answerGroupNo;
	private String answerGroupDesc;
	private Date downTimestamp;	
	private String answerGroupName;

	public void setFromEntity(String fromEntity){
		this.fromEntity=fromEntity;
	}

	public String getFromEntity(){
		return this.fromEntity;
	}

	public void setIsValid(Integer isValid){
		this.isValid=isValid;
	}

	public Integer getIsValid(){
		return this.isValid;
	}


	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setDownTag(Integer downTag){
		this.downTag=downTag;
	}

	public Integer getDownTag(){
		return this.downTag;
	}

	public void setAnswerGroupNo(String answerGroupNo){
		this.answerGroupNo=answerGroupNo;
	}

	public String getAnswerGroupNo(){
		return this.answerGroupNo;
	}

	public void setAnswerGroupDesc(String answerGroupDesc){
		this.answerGroupDesc=answerGroupDesc;
	}

	public String getAnswerGroupDesc(){
		return this.answerGroupDesc;
	}

	public void setDownTimestamp(Date downTimestamp){
		this.downTimestamp=downTimestamp;
	}

	public Date getDownTimestamp(){
		return this.downTimestamp;
	}


	public void setAnswerGroupName(String answerGroupName){
		this.answerGroupName=answerGroupName;
	}

	public String getAnswerGroupName(){
		return this.answerGroupName;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}


}
