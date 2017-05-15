package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TmOldpartStorDTO {
	private Long storId;
	private Date updateDate;
	private String remark;//备注
	private Long createBy;
	private Integer status;//状态
	private Integer isDel;
	private String storCode;//仓库代码
	private Long updateBy;
	private String storName;//仓库名称
	private String storAddress;//地址
	private Integer ver;
	private Date createDate;
	private Integer isArc;
	private Long oemCompanyId;


	public Long getOemCompanyId() {
		return oemCompanyId;
	}

	public void setOemCompanyId(Long oemCompanyId) {
		this.oemCompanyId = oemCompanyId;
	}

	public void setStorId(Long storId){
		this.storId=storId;
	}

	public Long getStorId(){
		return this.storId;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
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

	public void setStorCode(String storCode){
		this.storCode=storCode;
	}

	public String getStorCode(){
		return this.storCode;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setStorName(String storName){
		this.storName=storName;
	}

	public String getStorName(){
		return this.storName;
	}

	public void setStorAddress(String storAddress){
		this.storAddress=storAddress;
	}

	public String getStorAddress(){
		return this.storAddress;
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
