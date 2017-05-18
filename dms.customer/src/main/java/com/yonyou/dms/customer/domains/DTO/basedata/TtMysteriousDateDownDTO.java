package com.yonyou.dms.customer.domains.DTO.basedata;

import java.util.Date;

public class TtMysteriousDateDownDTO {

	private Long downId;
	private Date updateDate;
	private String dealerCode;
	private Long createBy;
	private String inputName;
	private Date inputDate;
	private String dealerName;
	private String phone;
	private String execAuthor;
	private Long updateBy;
	private Long mysteriousId;
	private Date createDate;
	private Integer isInputDms;
	private String inputPhone;

	public void setDownId(Long downId){
		this.downId=downId;
	}

	public Long getDownId(){
		return this.downId;
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

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setInputName(String inputName){
		this.inputName=inputName;
	}

	public String getInputName(){
		return this.inputName;
	}

	public void setInputDate(Date inputDate){
		this.inputDate=inputDate;
	}

	public Date getInputDate(){
		return this.inputDate;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setExecAuthor(String execAuthor){
		this.execAuthor=execAuthor;
	}

	public String getExecAuthor(){
		return this.execAuthor;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setMysteriousId(Long mysteriousId){
		this.mysteriousId=mysteriousId;
	}

	public Long getMysteriousId(){
		return this.mysteriousId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsInputDms(Integer isInputDms){
		this.isInputDms=isInputDms;
	}

	public Integer getIsInputDms(){
		return this.isInputDms;
	}

	public void setInputPhone(String inputPhone){
		this.inputPhone=inputPhone;
	}

	public String getInputPhone(){
		return this.inputPhone;
	}
}
