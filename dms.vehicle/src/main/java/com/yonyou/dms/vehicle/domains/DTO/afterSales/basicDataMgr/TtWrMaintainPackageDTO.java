package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TtWrMaintainPackageDTO {
	private Date maintainEnddate;
	private Float maintainStartday;
	private Double maintainEndmileage;
	private Double maintainStartmileage;
	private Date updateDate;
	private Long createBy;
	private Integer isDown;
	private Integer isDel;
	private String packageCode;
	private Long oemCompanyId;
	private Long modelId;
	private Long updateBy;
	private Double totalAmount;
	private Long seriesId;
	private Integer ver;
	private Long packageId;
	private Float maintainEndday;
	private Date createDate;
	private Date maintainStartdate;
	private String packageName;
	
	private String groupIds;
	private String groupIds2;
	
	private List<Map> one_table;
	private List<Map> two_table;
	
	public List<Map> getOne_table() {
		return one_table;
	}

	public void setOne_table(List<Map> one_table) {
		this.one_table = one_table;
	}

	public List<Map> getTwo_table() {
		return two_table;
	}

	public void setTwo_table(List<Map> two_table) {
		this.two_table = two_table;
	}

	public String getGroupIds2() {
		return groupIds2;
	}

	public void setGroupIds2(String groupIds2) {
		this.groupIds2 = groupIds2;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public void setMaintainEnddate(Date maintainEnddate){
		this.maintainEnddate=maintainEnddate;
	}

	public Date getMaintainEnddate(){
		return this.maintainEnddate;
	}

	public void setMaintainStartday(Float maintainStartday){
		this.maintainStartday=maintainStartday;
	}

	public Float getMaintainStartday(){
		return this.maintainStartday;
	}

	public void setMaintainEndmileage(Double maintainEndmileage){
		this.maintainEndmileage=maintainEndmileage;
	}

	public Double getMaintainEndmileage(){
		return this.maintainEndmileage;
	}

	public void setMaintainStartmileage(Double maintainStartmileage){
		this.maintainStartmileage=maintainStartmileage;
	}

	public Double getMaintainStartmileage(){
		return this.maintainStartmileage;
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

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setPackageCode(String packageCode){
		this.packageCode=packageCode;
	}

	public String getPackageCode(){
		return this.packageCode;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setModelId(Long modelId){
		this.modelId=modelId;
	}

	public Long getModelId(){
		return this.modelId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setTotalAmount(Double totalAmount){
		this.totalAmount=totalAmount;
	}

	public Double getTotalAmount(){
		return this.totalAmount;
	}

	public void setSeriesId(Long seriesId){
		this.seriesId=seriesId;
	}

	public Long getSeriesId(){
		return this.seriesId;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setPackageId(Long packageId){
		this.packageId=packageId;
	}

	public Long getPackageId(){
		return this.packageId;
	}

	public void setMaintainEndday(Float maintainEndday){
		this.maintainEndday=maintainEndday;
	}

	public Float getMaintainEndday(){
		return this.maintainEndday;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setMaintainStartdate(Date maintainStartdate){
		this.maintainStartdate=maintainStartdate;
	}

	public Date getMaintainStartdate(){
		return this.maintainStartdate;
	}

	public void setPackageName(String packageName){
		this.packageName=packageName;
	}

	public String getPackageName(){
		return this.packageName;
	}
}
