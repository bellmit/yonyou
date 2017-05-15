/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2013-05-28 13:47:04
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.checkManagement;

import java.util.Date;

public class TtVsInspectionDetailDTO {

	private Long inspectionId;
	private Long updateBy;
	private Date updateDate;
	private String damageDesc;
	private Integer ver;
	private Long createBy;
	private Long detailId;
	private Date createDate;
	private String damagePart;
	private Integer isArc;
	private Integer isDel;

	public void setInspectionId(Long inspectionId){
		this.inspectionId=inspectionId;
	}

	public Long getInspectionId(){
		return this.inspectionId;
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

	public void setDamageDesc(String damageDesc){
		this.damageDesc=damageDesc;
	}

	public String getDamageDesc(){
		return this.damageDesc;
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

	public void setDetailId(Long detailId){
		this.detailId=detailId;
	}

	public Long getDetailId(){
		return this.detailId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setDamagePart(String damagePart){
		this.damagePart=damagePart;
	}

	public String getDamagePart(){
		return this.damagePart;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

}