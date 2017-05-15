package com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty;

import java.util.Date;

public class TtWrOperateDTO {
	
	private Integer isSp;
	private Date updateDate;
	private Long createBy;
	private Integer status;
	private String optCode;
	private String optNameEn;
	private Integer isDel;
	private String optNameCn;
	private Long oemCompanyId;
	private Long updateBy;
	private String invoiceCode;
	private Long id;
	private Integer ver;
	private Date createDate;
	private Integer isArc;

	public void setIsSp(Integer isSp){
		this.isSp=isSp;
	}

	public Integer getIsSp(){
		return this.isSp;
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

	public void setOptNameEn(String optNameEn){
		this.optNameEn=optNameEn;
	}

	public String getOptNameEn(){
		return this.optNameEn;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setOptNameCn(String optNameCn){
		this.optNameCn=optNameCn;
	}

	public String getOptNameCn(){
		return this.optNameCn;
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

	public void setInvoiceCode(String invoiceCode){
		this.invoiceCode=invoiceCode;
	}

	public String getInvoiceCode(){
		return this.invoiceCode;
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
