package com.yonyou.dms.web.domains.DTO.inter;

import java.util.Date;

public class DeMsgInfoDTO {
	private Integer msgType;
	private Integer process;
	private Integer tryTimes;
	private Integer msgPriority;
	private Date createDate;
	private String msgId;
	private String msgFrom;
	private String adapterName;
	private String msgTo;
	private String appName;
	private String msgFileId;
	private Date lastTryTime;
	private String bizType;

	public void setMsgType(Integer msgType){
		this.msgType=msgType;
	}

	public Integer getMsgType(){
		return this.msgType;
	}

	public void setProcess(Integer process){
		this.process=process;
	}

	public Integer getProcess(){
		return this.process;
	}

	public void setTryTimes(Integer tryTimes){
		this.tryTimes=tryTimes;
	}

	public Integer getTryTimes(){
		return this.tryTimes;
	}

	public void setMsgPriority(Integer msgPriority){
		this.msgPriority=msgPriority;
	}

	public Integer getMsgPriority(){
		return this.msgPriority;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setMsgId(String msgId){
		this.msgId=msgId;
	}

	public String getMsgId(){
		return this.msgId;
	}

	public void setMsgFrom(String msgFrom){
		this.msgFrom=msgFrom;
	}

	public String getMsgFrom(){
		return this.msgFrom;
	}

	public void setAdapterName(String adapterName){
		this.adapterName=adapterName;
	}

	public String getAdapterName(){
		return this.adapterName;
	}

	public void setMsgTo(String msgTo){
		this.msgTo=msgTo;
	}

	public String getMsgTo(){
		return this.msgTo;
	}

	public void setAppName(String appName){
		this.appName=appName;
	}

	public String getAppName(){
		return this.appName;
	}

	public void setMsgFileId(String msgFileId){
		this.msgFileId=msgFileId;
	}

	public String getMsgFileId(){
		return this.msgFileId;
	}

	public void setLastTryTime(Date lastTryTime){
		this.lastTryTime=lastTryTime;
	}

	public Date getLastTryTime(){
		return this.lastTryTime;
	}

	public void setBizType(String bizType){
		this.bizType=bizType;
	}

	public String getBizType(){
		return this.bizType;
	}

}
