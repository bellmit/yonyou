package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SADCS029DTO {

	private String entityCode; // 经销商代码
	private String partCode; // 配件代码
	private String partName; // 配件名称
	private Date adjustDate; // 调整时间
	private Double nowCeilingPrice; // 现调整销售限价
	private Double oldCeilingPrice; // 原销售限价
	private String dealerCode;
	

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Date getAdjustDate() {
		return adjustDate;
	}

	public void setAdjustDate(Date adjustDate) {
		this.adjustDate = adjustDate;
	}

	public Double getNowCeilingPrice() {
		return nowCeilingPrice;
	}

	public void setNowCeilingPrice(Double nowCeilingPrice) {
		this.nowCeilingPrice = nowCeilingPrice;
	}

	public Double getOldCeilingPrice() {
		return oldCeilingPrice;
	}

	public void setOldCeilingPrice(Double oldCeilingPrice) {
		this.oldCeilingPrice = oldCeilingPrice;
	}
}
