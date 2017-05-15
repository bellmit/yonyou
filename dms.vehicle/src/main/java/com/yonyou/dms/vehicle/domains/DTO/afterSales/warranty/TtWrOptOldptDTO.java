package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrOptOldptDTO {
	
	private Date updateDate;
	private Long createBy;
	private Integer status;
	private String optCode;
	private Integer isDel;
	private Long oldptId;
	private String oldptCode;
	private Long oemCompanyId;
	private String ptTag;
	private Long updateBy;
	private Long id;
	private Integer ver;
	private Long optId;
	private Date createDate;
	private Integer isArc;

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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setOptCode(String optCode){
		this.optCode=optCode;
	}

	public String getOptCode(){
		return this.optCode;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOldptId(Long oldptId){
		this.oldptId=oldptId;
	}

	public Long getOldptId(){
		return this.oldptId;
	}

	public void setOldptCode(String oldptCode){
		this.oldptCode=oldptCode;
	}

	public String getOldptCode(){
		return this.oldptCode;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setPtTag(String ptTag){
		this.ptTag=ptTag;
	}

	public String getPtTag(){
		return this.ptTag;
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

	public void setOptId(Long optId){
		this.optId=optId;
	}

	public Long getOptId(){
		return this.optId;
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
}
