/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2016-01-18 14:08:57
* CreateBy   : Administrator
* Comment    : generate by com.infoservice.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TmOrderfreezereasonDTO {

	private Long freezeId;
	private Long updateBy;
	private String freezeCode;
	private Date updateDate;
	private Long createBy;
	private Date createDate;
	private String freezeReason;
	private Integer status;

	public void setFreezeId(Long freezeId){
		this.freezeId=freezeId;
	}

	public Long getFreezeId(){
		return this.freezeId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setFreezeCode(String freezeCode){
		this.freezeCode=freezeCode;
	}

	public String getFreezeCode(){
		return this.freezeCode;
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

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setFreezeReason(String freezeReason){
		this.freezeReason=freezeReason;
	}

	public String getFreezeReason(){
		return this.freezeReason;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

}