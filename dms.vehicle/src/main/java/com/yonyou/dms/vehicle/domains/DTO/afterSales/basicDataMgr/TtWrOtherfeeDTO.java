package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtWrOtherfeeDTO {
	private Long otherFeeId;
	private Double otherFeeAmount;
	private Long oemCompanyId;
	private Long updateBy;
	private Date updateDate;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private String otherFeeCode;
	private Integer isDown;
	private String otherFeeName;
	private Integer isDel;

	public void setOtherFeeId(Long otherFeeId){
		this.otherFeeId=otherFeeId;
	}

	public Long getOtherFeeId(){
		return this.otherFeeId;
	}

	public void setOtherFeeAmount(Double otherFeeAmount){
		this.otherFeeAmount=otherFeeAmount;
	}

	public Double getOtherFeeAmount(){
		return this.otherFeeAmount;
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

	public void setOtherFeeCode(String otherFeeCode){
		this.otherFeeCode=otherFeeCode;
	}

	public String getOtherFeeCode(){
		return this.otherFeeCode;
	}

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setOtherFeeName(String otherFeeName){
		this.otherFeeName=otherFeeName;
	}

	public String getOtherFeeName(){
		return this.otherFeeName;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

}
