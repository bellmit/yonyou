package com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TmWxMaintainPackageDcsDTO {
	private Integer mType;
	private Date maintainEnddate;
	private Double maintainEndmileage;
	private Double maintainStartmileage;
	private Integer isWxSend;
	private Date updateDate;
	private String dealerCode;
	private Long createBy;
	private Integer oileType;
	private Integer isDel;
	private String engineDesc;
	private String packageCode;
	private Integer pType;
	private Long oemCompanyId;
	private Long updateBy;
	private Double totalAmount;
	private Integer ver;
	private Long packageId;
	private String modelYear;
	private String seriesCode;
	private Integer isDmsSend;
	private Date createDate;
	private Date maintainStartdate;
	private String packageName;
	
	private String groupIds;
	private String groupIds2;
	
	private List<Map> one_table;
	private List<Map> two_table;
	
	

	public Integer getmType() {
		return mType;
	}

	public void setmType(Integer mType) {
		this.mType = mType;
	}

	public Integer getpType() {
		return pType;
	}

	public void setpType(Integer pType) {
		this.pType = pType;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getGroupIds2() {
		return groupIds2;
	}

	public void setGroupIds2(String groupIds2) {
		this.groupIds2 = groupIds2;
	}

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

	public void setMType(Integer mType){
		this.mType=mType;
	}

	public Integer getMType(){
		return this.mType;
	}

	public void setMaintainEnddate(Date maintainEnddate){
		this.maintainEnddate=maintainEnddate;
	}

	public Date getMaintainEnddate(){
		return this.maintainEnddate;
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

	public void setIsWxSend(Integer isWxSend){
		this.isWxSend=isWxSend;
	}

	public Integer getIsWxSend(){
		return this.isWxSend;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOileType(Integer oileType){
		this.oileType=oileType;
	}

	public Integer getOileType(){
		return this.oileType;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setEngineDesc(String engineDesc){
		this.engineDesc=engineDesc;
	}

	public String getEngineDesc(){
		return this.engineDesc;
	}

	public void setPackageCode(String packageCode){
		this.packageCode=packageCode;
	}

	public String getPackageCode(){
		return this.packageCode;
	}

	public void setPType(Integer pType){
		this.pType=pType;
	}

	public Integer getPType(){
		return this.pType;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
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

	public void setModelYear(String modelYear){
		this.modelYear=modelYear;
	}

	public String getModelYear(){
		return this.modelYear;
	}

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}

	public void setIsDmsSend(Integer isDmsSend){
		this.isDmsSend=isDmsSend;
	}

	public Integer getIsDmsSend(){
		return this.isDmsSend;
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
