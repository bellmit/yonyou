package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

@SuppressWarnings("serial")
public class SEDMS022VO extends BaseVO{
	private String vin;//下端车辆vin
	private String fileId;//下端发票id
	private String fileUrl;//下端发票url
	private String dealerCode;
	private Integer isValid;
	private Date downTimestamp;
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	
}
