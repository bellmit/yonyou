/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2016-02-16 14:44:34
* CreateBy   : Administrator
* Comment    : generate by com.infoservice.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TmOrderCancelRemarksDTO {

	private String cancelReasonText;
	private String cancelRemarksNo;
	private Long updateBy;
	private Date updateDate;
	private Integer cancelRemarksStatus;
	private Long createBy;
	private Long cancelRemarksId;
	private Date createDate;
	private Integer updateType;

	public void setCancelReasonText(String cancelReasonText){
		this.cancelReasonText=cancelReasonText;
	}

	public String getCancelReasonText(){
		return this.cancelReasonText;
	}

	public void setCancelRemarksNo(String cancelRemarksNo){
		this.cancelRemarksNo=cancelRemarksNo;
	}

	public String getCancelRemarksNo(){
		return this.cancelRemarksNo;
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

	public void setCancelRemarksStatus(Integer cancelRemarksStatus){
		this.cancelRemarksStatus=cancelRemarksStatus;
	}

	public Integer getCancelRemarksStatus(){
		return this.cancelRemarksStatus;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCancelRemarksId(Long cancelRemarksId){
		this.cancelRemarksId=cancelRemarksId;
	}

	public Long getCancelRemarksId(){
		return this.cancelRemarksId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateType(Integer updateType){
		this.updateType=updateType;
	}

	public Integer getUpdateType(){
		return this.updateType;
	}

}