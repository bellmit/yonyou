package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TmOldpartReturnaddrDTO {
	private String returnaddrName;
	private Long cityId;
	private Date updateDate;
	private String address;
	private Long createBy;
	private String remark;
	private Integer status;
	private String linkMan;
	private Long provinceId;
	private Integer isDel;
	private String linkTel;
	private Long oemCompanyId;
	private String returnaddrCode;
	private Long updateBy;
	private Long countyId;
	private String postCode;
	private Integer ver;
	private Date createDate;
	private Integer isArc;
	private Long returnaddrId;

	public void setReturnaddrName(String returnaddrName){
		this.returnaddrName=returnaddrName;
	}

	public String getReturnaddrName(){
		return this.returnaddrName;
	}

	public void setCityId(Long cityId){
		this.cityId=cityId;
	}

	public Long getCityId(){
		return this.cityId;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setLinkMan(String linkMan){
		this.linkMan=linkMan;
	}

	public String getLinkMan(){
		return this.linkMan;
	}

	public void setProvinceId(Long provinceId){
		this.provinceId=provinceId;
	}

	public Long getProvinceId(){
		return this.provinceId;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setLinkTel(String linkTel){
		this.linkTel=linkTel;
	}

	public String getLinkTel(){
		return this.linkTel;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setReturnaddrCode(String returnaddrCode){
		this.returnaddrCode=returnaddrCode;
	}

	public String getReturnaddrCode(){
		return this.returnaddrCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setCountyId(Long countyId){
		this.countyId=countyId;
	}

	public Long getCountyId(){
		return this.countyId;
	}

	public void setPostCode(String postCode){
		this.postCode=postCode;
	}

	public String getPostCode(){
		return this.postCode;
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

	public void setReturnaddrId(Long returnaddrId){
		this.returnaddrId=returnaddrId;
	}

	public Long getReturnaddrId(){
		return this.returnaddrId;
	}

}
