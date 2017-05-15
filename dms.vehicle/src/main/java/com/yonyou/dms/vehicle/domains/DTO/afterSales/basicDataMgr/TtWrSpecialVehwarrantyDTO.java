package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrSpecialVehwarrantyDTO {
	private Long updateBy;
	private Double qualityMileage;
	private String vin;
	private Date updateDate;
	private Integer qualityTime;
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer isDel;

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setQualityMileage(Double qualityMileage){
		this.qualityMileage=qualityMileage;
	}

	public Double getQualityMileage(){
		return this.qualityMileage;
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

	public void setQualityTime(Integer qualityTime){
		this.qualityTime=qualityTime;
	}

	public Integer getQualityTime(){
		return this.qualityTime;
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

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
