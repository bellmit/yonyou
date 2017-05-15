package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SADCS025Dto {

	private String dealerCode;// 经销商代码
	private Integer isValid;// 是否有效区分
	private Date downTimestamp;// 接口交互时间
	private Integer padArchivedCustomers;// PAD建档量
	private Integer padHslArchivedCustomers;// PAD建档客户HSL
	private Integer hslArchivedCustomers;// 建档客户HSL
	private Double padArchivedRatio;// PAD建档率
	private Date uploadTimestamp;// 上报时间
	private Integer isEndOfMonth;// 是否月末(12781001:是;12781002:否)
	private String entityCode; // 下端：经销商代码 CHAR(8) 上端：

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
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

	public Integer getPadArchivedCustomers() {
		return padArchivedCustomers;
	}

	public void setPadArchivedCustomers(Integer padArchivedCustomers) {
		this.padArchivedCustomers = padArchivedCustomers;
	}

	public Integer getPadHslArchivedCustomers() {
		return padHslArchivedCustomers;
	}

	public void setPadHslArchivedCustomers(Integer padHslArchivedCustomers) {
		this.padHslArchivedCustomers = padHslArchivedCustomers;
	}

	public Integer getHslArchivedCustomers() {
		return hslArchivedCustomers;
	}

	public void setHslArchivedCustomers(Integer hslArchivedCustomers) {
		this.hslArchivedCustomers = hslArchivedCustomers;
	}

	public Double getPadArchivedRatio() {
		return padArchivedRatio;
	}

	public void setPadArchivedRatio(Double padArchivedRatio) {
		this.padArchivedRatio = padArchivedRatio;
	}

	public Date getUploadTimestamp() {
		return uploadTimestamp;
	}

	public void setUploadTimestamp(Date uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

	public Integer getIsEndOfMonth() {
		return isEndOfMonth;
	}

	public void setIsEndOfMonth(Integer isEndOfMonth) {
		this.isEndOfMonth = isEndOfMonth;
	}

}
