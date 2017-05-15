/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2015-08-13 16:32:00
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TtVsOrderCheckEDTO {

	private Long checkId;
	private String checkOpinion;
	private Long orderId;
	private Integer checkStatus;
	private Long updateBy;
	private Integer checkType;
	private Date updateDate;
	private Long createBy;
	private Date checkDate;
	private Date createDate;

	public void setCheckId(Long checkId){
		this.checkId=checkId;
	}

	public Long getCheckId(){
		return this.checkId;
	}

	public void setCheckOpinion(String checkOpinion){
		this.checkOpinion=checkOpinion;
	}

	public String getCheckOpinion(){
		return this.checkOpinion;
	}

	public void setOrderId(Long orderId){
		this.orderId=orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setCheckStatus(Integer checkStatus){
		this.checkStatus=checkStatus;
	}

	public Integer getCheckStatus(){
		return this.checkStatus;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setCheckType(Integer checkType){
		this.checkType=checkType;
	}

	public Integer getCheckType(){
		return this.checkType;
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

	public void setCheckDate(Date checkDate){
		this.checkDate=checkDate;
	}

	public Date getCheckDate(){
		return this.checkDate;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}