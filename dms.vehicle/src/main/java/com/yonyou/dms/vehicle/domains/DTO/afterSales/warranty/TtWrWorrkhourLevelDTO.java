package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrWorrkhourLevelDTO {
	
	private Date updateDate;
	private Long wtId;
	private String remark;
	private Long createBy;
	private Integer status;
	private Integer isDel;
	private Integer derLevel;
	private Long oemCompanyId;
	private Long updateBy;
	private Long id;
	private String wtCode;
	private Integer ver;
	private Double workPrice;
	private Date createDate;
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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setDerLevel(Integer derLevel){
		this.derLevel=derLevel;
	}

	public Integer getDerLevel(){
		return this.derLevel;
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

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setWtCode(String wtCode){
		this.wtCode=wtCode;
	}

	public String getWtCode(){
		return this.wtCode;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setWorkPrice(Double workPrice){
		this.workPrice=workPrice;
	}

	public Double getWorkPrice(){
		return this.workPrice;
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
