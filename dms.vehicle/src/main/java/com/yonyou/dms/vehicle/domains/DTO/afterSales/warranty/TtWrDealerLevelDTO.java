package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrDealerLevelDTO {
	
	private Integer derLevel;
	private Long oemCompanyId;
	private Long dealerId;
	private Long updateBy;
	private Date updateDate;
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer status;
	private Integer isArc;
	private Integer isDel;
	public String getFiatWebdacCode() {
		return fiatWebdacCode;
	}

	public void setFiatWebdacCode(String fiatWebdacCode) {
		this.fiatWebdacCode = fiatWebdacCode;
	}

	private String fiatWebdacCode;
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

	public void setDealerId(Long dealerId){
		this.dealerId=dealerId;
	}

	public Long getDealerId(){
		return this.dealerId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
