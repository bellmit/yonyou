package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class WXUnbundingDTO {
	private String dealerCode;
	private String Vin;
	private Date wxUnbundlingDate;
	private String OwnerNo;
	private String isBundingWx;
	public String getDealerCode() {
		return dealerCode;
	}
	public String getVin() {
		return Vin;
	}
	public Date getWxUnbundlingDate() {
		return wxUnbundlingDate;
	}
	public String getOwnerNo() {
		return OwnerNo;
	}
	public String getIsBundingWx() {
		return isBundingWx;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public void setVin(String vin) {
		Vin = vin;
	}
	public void setWxUnbundlingDate(Date wxUnbundlingDate) {
		this.wxUnbundlingDate = wxUnbundlingDate;
	}
	public void setOwnerNo(String ownerNo) {
		OwnerNo = ownerNo;
	}
	public void setIsBundingWx(String isBundingWx) {
		this.isBundingWx = isBundingWx;
	}
}
