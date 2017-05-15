package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrForecheckLogDTO {
	private Long logId;
	private Long updateBy;
	private Date updateDate;
	private Long createBy;
	private String checkDesc;
	private Date checkDate;
	private Long checkUser;
	private Date createDate;
	private Integer status;
	private String authLevel;
	private Long foreId;
	private String levelName;

	public void setLogId(Long logId){
		this.logId=logId;
	}

	public Long getLogId(){
		return this.logId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCheckDesc(String checkDesc){
		this.checkDesc=checkDesc;
	}

	public String getCheckDesc(){
		return this.checkDesc;
	}

	public void setCheckDate(Date checkDate){
		this.checkDate=checkDate;
	}

	public Date getCheckDate(){
		return this.checkDate;
	}

	public void setCheckUser(Long checkUser){
		this.checkUser=checkUser;
	}

	public Long getCheckUser(){
		return this.checkUser;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setAuthLevel(String authLevel){
		this.authLevel=authLevel;
	}

	public String getAuthLevel(){
		return this.authLevel;
	}

	public void setForeId(Long foreId){
		this.foreId=foreId;
	}

	public Long getForeId(){
		return this.foreId;
	}

	public void setLevelName(String levelName){
		this.levelName=levelName;
	}

	public String getLevelName(){
		return this.levelName;
	}
}
