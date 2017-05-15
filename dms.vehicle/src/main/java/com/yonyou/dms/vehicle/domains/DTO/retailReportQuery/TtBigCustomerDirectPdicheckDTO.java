/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2016-09-18 17:42:42
* CreateBy   : Administrator
* Comment    : generate by com.infoservice.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.retailReportQuery;

import java.util.Date;

public class TtBigCustomerDirectPdicheckDTO{

	private String fileName;
	private Long checkId;
	private Long orderId;
	private Integer checkResult;
	private Long updateBy;
	private Double mileage;
	private Date updateDate;
	private String technicalReportNo;
	private Long createBy;
	private String fileUrl;
	private Date checkDate;
	private Date createDate;

	public void setFileName(String fileName){
		this.fileName=fileName;
	}

	public String getFileName(){
		return this.fileName;
	}

	public void setCheckId(Long checkId){
		this.checkId=checkId;
	}

	public Long getCheckId(){
		return this.checkId;
	}

	public void setOrderId(Long orderId){
		this.orderId=orderId;
	}

	public Long getOrderId(){
		return this.orderId;
	}

	public void setCheckResult(Integer checkResult){
		this.checkResult=checkResult;
	}

	public Integer getCheckResult(){
		return this.checkResult;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setMileage(Double mileage){
		this.mileage=mileage;
	}

	public Double getMileage(){
		return this.mileage;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setTechnicalReportNo(String technicalReportNo){
		this.technicalReportNo=technicalReportNo;
	}

	public String getTechnicalReportNo(){
		return this.technicalReportNo;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setFileUrl(String fileUrl){
		this.fileUrl=fileUrl;
	}

	public String getFileUrl(){
		return this.fileUrl;
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