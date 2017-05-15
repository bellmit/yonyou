
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectPdiDto.java
*
* @Author : yll
*
* @Date : 2016年9月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月22日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.Date;

/**
* PDI检查
* @author yll
* @date 2016年9月22日
*/

public class InspectPdiDto {
	private String  inspectNo;//序号
	private Integer inspectType;//检查类型
    private String vin;
    private String  inspectCateGory;//检查分类
    private String  inspectItem;//检查项
    private Integer isProblem;//是否有问题
    private String  inspectDesCribe ;//检查问题描述
    private String  inspectPerson ;//检查人
    private Date inspectDate;//检查时间
	public String getInspectNo() {
		return inspectNo;
	}
	public void setInspectNo(String inspectNo) {
		this.inspectNo = inspectNo;
	}
	public Integer getInspectType() {
		return inspectType;
	}
	public void setInspectType(Integer inspectType) {
		this.inspectType = inspectType;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getInspectCateGory() {
		return inspectCateGory;
	}
	public void setInspectCateGory(String inspectCateGory) {
		this.inspectCateGory = inspectCateGory;
	}
	public String getInspectItem() {
		return inspectItem;
	}
	public void setInspectItem(String inspectItem) {
		this.inspectItem = inspectItem;
	}
	public Integer getIsProblem() {
		return isProblem;
	}
	public void setIsProblem(Integer isProblem) {
		this.isProblem = isProblem;
	}
	public String getInspectDesCribe() {
		return inspectDesCribe;
	}
	public void setInspectDesCribe(String inspectDesCribe) {
		this.inspectDesCribe = inspectDesCribe;
	}
	public String getInspectPerson() {
		return inspectPerson;
	}
	public void setInspectPerson(String inspectPerson) {
		this.inspectPerson = inspectPerson;
	}
	public Date getInspectDate() {
		return inspectDate;
	}
	public void setInspectDate(Date inspectDate) {
		this.inspectDate = inspectDate;
	}
    

}
