/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2015-08-12 15:14:03
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage;

import java.util.Date;

public class TtVsOrderTransDTO {

	private String sapTransNo;
	private String vin;
	private Date updateDate;
	private Long createBy;
	private String remark;
	private String transAgentCode;
	private Integer status;
	private String expectedDate;
	private String transAgentName;
	private Integer transType;
	private Date transDate;
	private String driverName;
	private String transNo;
	private Long receivesDealerId;
	private Date departureDate;
	private String driverTel;
	private Long updateBy;
	private Long transId;
	private Date createDate;
	private String transVehicleNo;

	public void setSapTransNo(String sapTransNo){
		this.sapTransNo=sapTransNo;
	}

	public String getSapTransNo(){
		return this.sapTransNo;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
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

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setTransAgentCode(String transAgentCode){
		this.transAgentCode=transAgentCode;
	}

	public String getTransAgentCode(){
		return this.transAgentCode;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setExpectedDate(String expectedDate){
		this.expectedDate=expectedDate;
	}

	public String getExpectedDate(){
		return this.expectedDate;
	}

	public void setTransAgentName(String transAgentName){
		this.transAgentName=transAgentName;
	}

	public String getTransAgentName(){
		return this.transAgentName;
	}

	public void setTransType(Integer transType){
		this.transType=transType;
	}

	public Integer getTransType(){
		return this.transType;
	}

	public void setTransDate(Date transDate){
		this.transDate=transDate;
	}

	public Date getTransDate(){
		return this.transDate;
	}

	public void setDriverName(String driverName){
		this.driverName=driverName;
	}

	public String getDriverName(){
		return this.driverName;
	}

	public void setTransNo(String transNo){
		this.transNo=transNo;
	}

	public String getTransNo(){
		return this.transNo;
	}

	public void setReceivesDealerId(Long receivesDealerId){
		this.receivesDealerId=receivesDealerId;
	}

	public Long getReceivesDealerId(){
		return this.receivesDealerId;
	}

	public void setDepartureDate(Date departureDate){
		this.departureDate=departureDate;
	}

	public Date getDepartureDate(){
		return this.departureDate;
	}

	public void setDriverTel(String driverTel){
		this.driverTel=driverTel;
	}

	public String getDriverTel(){
		return this.driverTel;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setTransId(Long transId){
		this.transId=transId;
	}

	public Long getTransId(){
		return this.transId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setTransVehicleNo(String transVehicleNo){
		this.transVehicleNo=transVehicleNo;
	}

	public String getTransVehicleNo(){
		return this.transVehicleNo;
	}

}