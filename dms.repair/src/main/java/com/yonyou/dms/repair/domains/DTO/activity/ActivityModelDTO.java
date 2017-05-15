
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ActivityModelDTO.java
*
* @Author : jcsi
*
* @Date : 2016年12月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月23日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.activity;

import java.util.Date;

/**
* 活动车型
* @author jcsi
* @date 2016年12月23日
*/

public class ActivityModelDTO {
	
	private Long activityModelId;
	
	private Long packageId;
	
	private String activityCode;  //活动编号
	
	private String modelCode;  //车型代码
	
	private String modelName;  //车型名称
	
	private Date manufactureDateGegin;  //生产开始日期
	
	private Date manufactureDateEnd;  //生产结束日期
	
	private String beginVin;  //VIN后六位开始i
	
	private String endVin;  //Vin后六位结束
	
	private String seriesCode;  //车系
	
	private String configCode;  //配置

	

	public Long getActivityModelId() {
		return activityModelId;
	}

	public void setActivityModelId(Long activityModelId) {
		this.activityModelId = activityModelId;
	}

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Date getManufactureDateGegin() {
		return manufactureDateGegin;
	}

	public void setManufactureDateGegin(Date manufactureDateGegin) {
		this.manufactureDateGegin = manufactureDateGegin;
	}

	public Date getManufactureDateEnd() {
		return manufactureDateEnd;
	}

	public void setManufactureDateEnd(Date manufactureDateEnd) {
		this.manufactureDateEnd = manufactureDateEnd;
	}

	public String getBeginVin() {
		return beginVin;
	}

	public void setBeginVin(String beginVin) {
		this.beginVin = beginVin;
	}

	public String getEndVin() {
		return endVin;
	}

	public void setEndVin(String endVin) {
		this.endVin = endVin;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	
	
}
