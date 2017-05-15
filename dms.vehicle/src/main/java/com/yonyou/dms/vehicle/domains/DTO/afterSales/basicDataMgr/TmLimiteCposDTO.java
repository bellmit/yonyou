package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;

public class TmLimiteCposDTO{
	private Double limitedRange;
	private Date updateDate;
	private Long limitedId;
	private String dealerCode;
	private String series;
	private Long createBy;
	private String repairDesc;
	private Integer isDel;
	private Long updateBy;
	private Integer descendStatus;
	private String comment;
	private Date descendDate;
	private List<Object> repairType;
	private List<Object> seriesCode;
	private Date createDate;
	private String limitedName;
	private List<Object> brand;

	
	
	
	public List<Object> getRepairType() {
		return repairType;
	}

	public void setRepairType(List<Object> repairType) {
		this.repairType = repairType;
	}

	public List<Object> getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(List<Object> seriesCode) {
		this.seriesCode = seriesCode;
	}

	public List<Object> getBrand() {
		return brand;
	}

	public void setBrand(List<Object> brand) {
		this.brand = brand;
	}

	public void setLimitedRange(Double limitedRange){
		this.limitedRange=limitedRange;
	}

	public Double getLimitedRange(){
		return this.limitedRange;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setLimitedId(Long limitedId){
		this.limitedId=limitedId;
	}

	public Long getLimitedId(){
		return this.limitedId;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setSeries(String series){
		this.series=series;
	}

	public String getSeries(){
		return this.series;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRepairDesc(String repairDesc){
		this.repairDesc=repairDesc;
	}

	public String getRepairDesc(){
		return this.repairDesc;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setDescendStatus(Integer descendStatus){
		this.descendStatus=descendStatus;
	}

	public Integer getDescendStatus(){
		return this.descendStatus;
	}

	public void setComment(String comment){
		this.comment=comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setDescendDate(Date descendDate){
		this.descendDate=descendDate;
	}

	public Date getDescendDate(){
		return this.descendDate;
	}

/*	public void setRepairType(String repairType){
		this.repairType=repairType;
	}

	public String getRepairType(){
		return this.repairType;
	}

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}*/

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setLimitedName(String limitedName){
		this.limitedName=limitedName;
	}

	public String getLimitedName(){
		return this.limitedName;
	}

/*	public void setBrand(String brand){
		this.brand=brand;
	}

	public String getBrand(){
		return this.brand;
	}*/

}
