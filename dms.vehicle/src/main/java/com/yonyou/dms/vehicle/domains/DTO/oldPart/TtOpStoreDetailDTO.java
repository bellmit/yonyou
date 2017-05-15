package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TtOpStoreDetailDTO {
	private Integer storeType;
	private Long updateBy;
	private Date updateDate;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Date operationDate;
	private Long storeId;
	private Integer isArc;
	private Integer isDel;
	private Long storAddressId;
	private Long storeDetailId;

	public void setStoreType(Integer storeType){
		this.storeType=storeType;
	}

	public Integer getStoreType(){
		return this.storeType;
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

	public void setOperationDate(Date operationDate){
		this.operationDate=operationDate;
	}

	public Date getOperationDate(){
		return this.operationDate;
	}

	public void setStoreId(Long storeId){
		this.storeId=storeId;
	}

	public Long getStoreId(){
		return this.storeId;
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

	public void setStorAddressId(Long storAddressId){
		this.storAddressId=storAddressId;
	}

	public Long getStorAddressId(){
		return this.storAddressId;
	}

	public void setStoreDetailId(Long storeDetailId){
		this.storeDetailId=storeDetailId;
	}

	public Long getStoreDetailId(){
		return this.storeDetailId;
	}

}
