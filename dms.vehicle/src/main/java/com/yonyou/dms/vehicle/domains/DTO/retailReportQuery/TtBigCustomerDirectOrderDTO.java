/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2016-08-15 16:12:27
* CreateBy   : 翟鸿淼
* Comment    : generate by com.infoservice.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.retailReportQuery;

import java.util.Date;

public class TtBigCustomerDirectOrderDTO {

	private Long orderId;
	private Long directId;
	private Long updateBy;
	private Date updateDate;
	private Long createBy;
	private Long bigId;
	private Date createDate;

	public void setOrderId(Long orderId){
		this.orderId=orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setDirectId(Long directId){
		this.directId=directId;
	}

	public Long getDirectId(){
		return this.directId;
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

	public void setBigId(Long bigId){
		this.bigId=bigId;
	}

	public Long getBigId(){
		return this.bigId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

}