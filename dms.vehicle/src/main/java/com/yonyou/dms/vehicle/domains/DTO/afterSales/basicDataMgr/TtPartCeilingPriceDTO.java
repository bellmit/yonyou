package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

public class TtPartCeilingPriceDTO {
	private Date sendDate;
	private Integer ceilingPriceScope;
	private Integer isErrNum;
	private String ceilingPriceCode;
	private Date updateDate;
	private String remark;
	private Long createBy;
	private Integer isDown;
	private Integer isSucNum;
	private Long updateBy;
	private Long id;
	private Date createDate;
	private String ceilingPriceName;
	private String partFlag;

	public void setSendDate(Date sendDate){
		this.sendDate=sendDate;
	}

	public Date getSendDate(){
		return this.sendDate;
	}

	public void setCeilingPriceScope(Integer ceilingPriceScope){
		this.ceilingPriceScope=ceilingPriceScope;
	}

	public Integer getCeilingPriceScope(){
		return this.ceilingPriceScope;
	}

	public void setIsErrNum(Integer isErrNum){
		this.isErrNum=isErrNum;
	}

	public Integer getIsErrNum(){
		return this.isErrNum;
	}

	public void setCeilingPriceCode(String ceilingPriceCode){
		this.ceilingPriceCode=ceilingPriceCode;
	}

	public String getCeilingPriceCode(){
		return this.ceilingPriceCode;
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

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setIsSucNum(Integer isSucNum){
		this.isSucNum=isSucNum;
	}

	public Integer getIsSucNum(){
		return this.isSucNum;
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

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCeilingPriceName(String ceilingPriceName){
		this.ceilingPriceName=ceilingPriceName;
	}

	public String getCeilingPriceName(){
		return this.ceilingPriceName;
	}

	public String getPartFlag() {
		return partFlag;
	}

	public void setPartFlag(String partFlag) {
		this.partFlag = partFlag;
	}

}
