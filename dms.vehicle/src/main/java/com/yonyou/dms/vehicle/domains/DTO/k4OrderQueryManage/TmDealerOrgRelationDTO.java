/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2012-05-14 16:09:23
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TmDealerOrgRelationDTO {

	private Integer relationType;
	private Long dealerId;
	private Long updateBy;
	private Date updateDate;
	private Integer ver;
	private Long createBy;
	private Integer businessType;
	private Date createDate;
	private Integer isArc;
	private Long relationId;
	private Integer isDel;
	private Long orgId;

	public void setRelationType(Integer relationType){
		this.relationType=relationType;
	}

	public Integer getRelationType(){
		return this.relationType;
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

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setBusinessType(Integer businessType){
		this.businessType=businessType;
	}

	public Integer getBusinessType(){
		return this.businessType;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setRelationId(Long relationId){
		this.relationId=relationId;
	}

	public Long getRelationId(){
		return this.relationId;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOrgId(Long orgId){
		this.orgId=orgId;
	}

	public Long getOrgId(){
		return this.orgId;
	}

}