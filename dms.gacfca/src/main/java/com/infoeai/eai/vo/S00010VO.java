package com.infoeai.eai.vo;

@SuppressWarnings("serial")
public class S00010VO extends BaseVO{
	
	private String selectDate;
	private String selectTime;
	private String dealerCode;
	private String cashBalance;
	private String currencyType;
	private String rowId;

	
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getSelectTime() {
		return selectTime;
	}
	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getCashBalance() {
		return cashBalance;
	}
	public void setCashBalance(String cashBalance) {
		this.cashBalance = cashBalance;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

}
