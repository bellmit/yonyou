package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TtOpGcsImpDTO {
	private Double price;
	private Date paymentDate;
	private String vin;
	private Date updateDate;
	private String dealerCode;
	private String partName;
	private Long createBy;
	private String partCode;
	private Integer isDel;
	private String dealerName;
	private Long oemCompanyId;
	private Double subtotal;
	private Long updateBy;
	private Long partCount;
	private Integer ver;
	private Long gcsId;
	private String repairNo;
	private Date createDate;
	private Integer isArc;
	private Integer isMapping;

	public void setPrice(Double price){
		this.price=price;
	}

	public Double getPrice(){
		return this.price;
	}

	public void setPaymentDate(Date paymentDate){
		this.paymentDate=paymentDate;
	}

	public Date getPaymentDate(){
		return this.paymentDate;
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

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setPartName(String partName){
		this.partName=partName;
	}

	public String getPartName(){
		return this.partName;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setPartCode(String partCode){
		this.partCode=partCode;
	}

	public String getPartCode(){
		return this.partCode;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setSubtotal(Double subtotal){
		this.subtotal=subtotal;
	}

	public Double getSubtotal(){
		return this.subtotal;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setPartCount(Long partCount){
		this.partCount=partCount;
	}

	public Long getPartCount(){
		return this.partCount;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setGcsId(Long gcsId){
		this.gcsId=gcsId;
	}

	public Long getGcsId(){
		return this.gcsId;
	}

	public void setRepairNo(String repairNo){
		this.repairNo=repairNo;
	}

	public String getRepairNo(){
		return this.repairNo;
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

	public void setIsMapping(Integer isMapping){
		this.isMapping=isMapping;
	}

	public Integer getIsMapping(){
		return this.isMapping;
	}

}
