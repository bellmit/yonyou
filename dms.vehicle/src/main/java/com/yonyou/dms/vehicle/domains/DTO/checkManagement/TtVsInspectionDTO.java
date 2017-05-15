/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2013-05-28 13:15:09
* CreateBy   : Administrator
* Comment    : generate by com.sgm.po.POGen
*/

package com.yonyou.dms.vehicle.domains.DTO.checkManagement;

import java.util.Date;

public class TtVsInspectionDTO {

	private String inspectionPerson;
	private Date updateDate;
	private String remark;
	private Long createBy;
	private Integer isDel;
	private Long inspectionId;
	private Long updateBy;
	private Date arriveDate;
	private Integer ver;
	private Date createDate;
	private Integer isArc;
	private Long vehicleId;
	private String arriveTime;
	private String inspectionNo;
	private Integer damageFlag;
	private String vin;
	private Date actualDate;
	private String trimName;
	private String colorName;
	private String modelYear;
	private String groupName;
	private String modelCode;
	private String seriesName;
	private String brandName;
	private String engineNo;
	private String damageDesc;
	private String damagePart;
	private String damageDatas;
	
	//新增两个字段 by wangJian 2016-07-05
	private Integer inspectionResult;// 入库验收结果 13351001：验收未通过 13351002：验收已通过
	private Integer pdiResult;//PDI检查结果   70161001：通过 70161002：不通过

	public Integer getInspectionResult() {
		return inspectionResult;
	}

	public void setInspectionResult(Integer inspectionResult) {
		this.inspectionResult = inspectionResult;
	}

	public Integer getPdiResult() {
		return pdiResult;
	}

	public void setPdiResult(Integer pdiResult) {
		this.pdiResult = pdiResult;
	}

	public void setInspectionPerson(String inspectionPerson){
		this.inspectionPerson=inspectionPerson;
	}

	public String getInspectionPerson(){
		return this.inspectionPerson;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

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

	public void setArriveDate(Date arriveDate){
		this.arriveDate=arriveDate;
	}

	public Date getArriveDate(){
		return this.arriveDate;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
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

	public void setVehicleId(Long vehicleId){
		this.vehicleId=vehicleId;
	}

	public Long getVehicleId(){
		return this.vehicleId;
	}

	public void setArriveTime(String arriveTime){
		this.arriveTime=arriveTime;
	}

	public String getArriveTime(){
		return this.arriveTime;
	}

	public void setInspectionNo(String inspectionNo){
		this.inspectionNo=inspectionNo;
	}

	public String getInspectionNo(){
		return this.inspectionNo;
	}

	public Integer getDamageFlag() {
		return damageFlag;
	}

	public void setDamageFlag(Integer damageFlag) {
		this.damageFlag = damageFlag;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Date getActualDate() {
		return actualDate;
	}

	public void setActualDate(Date actualDate) {
		this.actualDate = actualDate;
	}

	public String getTrimName() {
		return trimName;
	}

	public void setTrimName(String trimName) {
		this.trimName = trimName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getDamageDesc() {
		return damageDesc;
	}

	public void setDamageDesc(String damageDesc) {
		this.damageDesc = damageDesc;
	}

	public String getDamagePart() {
		return damagePart;
	}

	public void setDamagePart(String damagePart) {
		this.damagePart = damagePart;
	}

	public String getDamageDatas() {
		return damageDatas;
	}

	public void setDamageDatas(String damageDatas) {
		this.damageDatas = damageDatas;
	}

}