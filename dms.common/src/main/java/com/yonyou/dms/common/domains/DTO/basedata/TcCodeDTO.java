/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2009-12-23 11:50:51
* CreateBy   : Andy
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class TcCodeDTO {

	private String type;
	private Date updateDate;
	private Integer status;
	private String codeId;
	private Long updateBy;
	private Long createBy;
	private Integer isDown;
	private String codeDesc;
	private Date createDate;
	private String typeName;
	private Integer canModify;
	private Integer num;

	public void setType(String type){
		this.type=type;
	}

	public String getType(){
		return this.type;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setCodeId(String codeId){
		this.codeId=codeId;
	}

	public String getCodeId(){
		return this.codeId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setCodeDesc(String codeDesc){
		this.codeDesc=codeDesc;
	}

	public String getCodeDesc(){
		return this.codeDesc;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setTypeName(String typeName){
		this.typeName=typeName;
	}

	public String getTypeName(){
		return this.typeName;
	}

	public void setCanModify(Integer canModify){
		this.canModify=canModify;
	}

	public Integer getCanModify(){
		return this.canModify;
	}

	public void setNum(Integer num){
		this.num=num;
	}

	public Integer getNum(){
		return this.num;
	}

}