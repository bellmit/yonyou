package com.infoeai.eai.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class S0010VO extends BaseVO{
	
	private String selectTime;
	private String dealerCode;
	private Date updateDate;
	private Long createBy;
	private String selectDate;
	private Integer isDel;
	private Long ifId;
	private Long updateBy;
	private String paymentType;
	private String rowId;
	private String isResult;
	private String isMessage;
	private Date createDate;
	private String cashBalance;
	private String currencyType;

	public void setSelectTime(String selectTime){
		this.selectTime=selectTime;
	}

	public String getSelectTime(){
		return this.selectTime;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
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

	public void setSelectDate(String selectDate){
		this.selectDate=selectDate;
	}

	public String getSelectDate(){
		return this.selectDate;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setIfId(Long ifId){
		this.ifId=ifId;
	}

	public Long getIfId(){
		return this.ifId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setPaymentType(String paymentType){
		this.paymentType=paymentType;
	}

	public String getPaymentType(){
		return this.paymentType;
	}

	public void setRowId(String rowId){
		this.rowId=rowId;
	}

	public String getRowId(){
		return this.rowId;
	}

	public void setIsResult(String isResult){
		this.isResult=isResult;
	}

	public String getIsResult(){
		return this.isResult;
	}

	public void setIsMessage(String isMessage){
		this.isMessage=isMessage;
	}

	public String getIsMessage(){
		return this.isMessage;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setCashBalance(String cashBalance){
		this.cashBalance=cashBalance;
	}

	public String getCashBalance(){
		return this.cashBalance;
	}

	public void setCurrencyType(String currencyType){
		this.currencyType=currencyType;
	}

	public String getCurrencyType(){
		return this.currencyType;
	}

}
