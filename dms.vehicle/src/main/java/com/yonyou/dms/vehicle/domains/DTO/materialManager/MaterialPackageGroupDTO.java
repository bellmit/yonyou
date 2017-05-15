/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2015-04-01 11:41:33
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.materialManager;

/**
 * 车款组代码
 * @author 夏威
 */
public class MaterialPackageGroupDTO {
	
	private String ids;
	private String packageGroupName;
	private String packageGroupCode;
	private Long packageGroupId;
	private Integer status;

	
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setPackageGroupName(String packageGroupName){
		this.packageGroupName=packageGroupName;
	}

	public String getPackageGroupName(){
		return this.packageGroupName;
	}

	public void setPackageGroupCode(String packageGroupCode){
		this.packageGroupCode=packageGroupCode;
	}

	public String getPackageGroupCode(){
		return this.packageGroupCode;
	}

	public void setPackageGroupId(Long packageGroupId){
		this.packageGroupId=packageGroupId;
	}

	public Long getPackageGroupId(){
		return this.packageGroupId;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

}