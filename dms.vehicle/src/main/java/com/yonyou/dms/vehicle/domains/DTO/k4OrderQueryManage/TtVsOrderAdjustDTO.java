/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2015-08-03 15:29:59
* CreateBy   : luoyg
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TtVsOrderAdjustDTO {

	private Long materialId;
	private Long orderId;
	private Long adjustId;
	private Long dealerId;
	private Long updateBy;
	private Date updateDate;
	private Long createBy;
	private Date createDate;
	private Integer status;
	private Long oldMaterialId;
	private Long resultMaterialId;

	public void setMaterialId(Long materialId){
		this.materialId=materialId;
	}

	public Long getMaterialId(){
		return this.materialId;
	}

	public void setOrderId(Long orderId){
		this.orderId=orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setAdjustId(Long adjustId){
		this.adjustId=adjustId;
	}

	public Long getAdjustId(){
		return this.adjustId;
	}

	public void setDealerId(Long dealerId){
		this.dealerId=dealerId;
	}

	public Long getDealerId(){
		return this.dealerId;
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

	public void setOldMaterialId(Long oldMaterialId){
		this.oldMaterialId=oldMaterialId;
	}

	public Long getOldMaterialId(){
		return this.oldMaterialId;
	}

	public void setResultMaterialId(Long resultMaterialId){
		this.resultMaterialId=resultMaterialId;
	}

	public Long getResultMaterialId(){
		return this.resultMaterialId;
	}

}