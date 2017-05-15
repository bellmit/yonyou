package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SADCS074VO extends BaseVO {
	private String vin; //底盘号
	private Date invoiceDate; //开票日期
	
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
