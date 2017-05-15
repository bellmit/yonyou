package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class TermlyMaintainLRemindDTO {


	private Date updateDate;
	private Date remindDateF;
	private Long updateBy;
	private String entityCode;
	private Long createBy;
	private String ownerNo;
	private Date createDate;
	private Long itemId;
	private Date remindDateS;
	private Date remindDateT;
	private String vin;
	
	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setRemindDateF(Date remindDateF){
		this.remindDateF=remindDateF;
	}

	public Date getRemindDateF(){
		return this.remindDateF;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setEntityCode(String entityCode){
		this.entityCode=entityCode;
	}

	public String getEntityCode(){
		return this.entityCode;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOwnerNo(String ownerNo){
		this.ownerNo=ownerNo;
	}

	public String getOwnerNo(){
		return this.ownerNo;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setItemId(Long itemId){
		this.itemId=itemId;
	}

	public Long getItemId(){
		return this.itemId;
	}

	public void setRemindDateS(Date remindDateS){
		this.remindDateS=remindDateS;
	}

	public Date getRemindDateS(){
		return this.remindDateS;
	}

	public void setRemindDateT(Date remindDateT){
		this.remindDateT=remindDateT;
	}

	public Date getRemindDateT(){
		return this.remindDateT;
	}

//	public String toXMLString(){
//		return POFactoryUtil.beanToXmlString(this);
//	}

}
