package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SADCS074DTO {
	
	private String dealerCode;
	private String vin; //底盘号
	private Date invoiceDate; //开票日期
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	
}
