package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrWtActivityDTO {
	
	private Date updateDate;
	private Long wtId;
	private Long createBy;
	private Integer isDel;
	private Integer isOptLock;
	private Integer isPtLock;
	private Long oemCompanyId;
	private String actName;
	private Date beginTime;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Date endTime;
	private Date createDate;
	private String actCode;
	private Integer isArc;

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setWtId(Long wtId){
		this.wtId=wtId;
	}

	public Long getWtId(){
		return this.wtId;
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

	public void setIsOptLock(Integer isOptLock){
		this.isOptLock=isOptLock;
	}

	public Integer getIsOptLock(){
		return this.isOptLock;
	}

	public void setIsPtLock(Integer isPtLock){
		this.isPtLock=isPtLock;
	}

	public Integer getIsPtLock(){
		return this.isPtLock;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setActName(String actName){
		this.actName=actName;
	}

	public String getActName(){
		return this.actName;
	}

	public void setBeginTime(Date beginTime){
		this.beginTime=beginTime;
	}

	public Date getBeginTime(){
		return this.beginTime;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setEndTime(Date endTime){
		this.endTime=endTime;
	}

	public Date getEndTime(){
		return this.endTime;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setActCode(String actCode){
		this.actCode=actCode;
	}

	public String getActCode(){
		return this.actCode;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}
}
