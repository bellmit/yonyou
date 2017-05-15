package com.yonyou.dms.manage.domains.DTO.personInfoManager;

import java.util.Date;
public class TcUserOnlineDTO {

	private String userIp;
	private Date updateDate;
	private Long userOnlineId;
	private Long updateBy;
	private Integer userOnlineStatus;
	private Long createBy;
	private Long orgId;
	private Long userId;
	private Date createDate;
	private Date loginDate;

	public void setUserIp(String userIp){
		this.userIp=userIp;
	}

	public String getUserIp(){
		return this.userIp;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setUserOnlineId(Long userOnlineId){
		this.userOnlineId=userOnlineId;
	}

	public Long getUserOnlineId(){
		return this.userOnlineId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUserOnlineStatus(Integer userOnlineStatus){
		this.userOnlineStatus=userOnlineStatus;
	}

	public Integer getUserOnlineStatus(){
		return this.userOnlineStatus;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOrgId(Long orgId){
		this.orgId=orgId;
	}

	public Long getOrgId(){
		return this.orgId;
	}

	public void setUserId(Long userId){
		this.userId=userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setLoginDate(Date loginDate){
		this.loginDate=loginDate;
	}

	public Date getLoginDate(){
		return this.loginDate;
	}
	
	
	
}
