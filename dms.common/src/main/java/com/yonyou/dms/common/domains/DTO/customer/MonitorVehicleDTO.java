package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

/**
 * 监控车主车辆
 * 
 * @author Administrator
 *
 */
public class MonitorVehicleDTO {

	private Double monitirId;
	private String vin;
	private String license;
	private String engineNo;
	private String ownerNo;
	private String ownerName;
	private String message;
	private Integer repairOrderTag;
	private Integer balanceTag;
	private String operator;
	private Date beginDate;
	private Date endDate;
	private Date foundDate;
	private Date salesDateBegin;
	private Date salesDateEnd;
	private Integer isValid;
	private Double ownerConsumeAmoutMin;
	private Double ownerConsumeAmountMax;
	private String vinBeginRange;
	private String vinEndRange;
	private Integer bookingTag;

	public Double getMonitirId() {
		return monitirId;
	}

	public void setMonitirId(Double monitirId) {
		this.monitirId = monitirId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getRepairOrderTag() {
		return repairOrderTag;
	}

	public void setRepairOrderTag(Integer repairOrderTag) {
		this.repairOrderTag = repairOrderTag;
	}

	public Integer getBalanceTag() {
		return balanceTag;
	}

	public void setBalanceTag(Integer balanceTag) {
		this.balanceTag = balanceTag;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Date getSalesDateBegin() {
		return salesDateBegin;
	}

	public void setSalesDateBegin(Date salesDateBegin) {
		this.salesDateBegin = salesDateBegin;
	}

	public Date getSalesDateEnd() {
		return salesDateEnd;
	}

	public void setSalesDateEnd(Date salesDateEnd) {
		this.salesDateEnd = salesDateEnd;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Double getOwnerConsumeAmoutMin() {
		return ownerConsumeAmoutMin;
	}

	public void setOwnerConsumeAmoutMin(Double ownerConsumeAmoutMin) {
		this.ownerConsumeAmoutMin = ownerConsumeAmoutMin;
	}

	public Double getOwnerConsumeAmountMax() {
		return ownerConsumeAmountMax;
	}

	public void setOwnerConsumeAmountMax(Double ownerConsumeAmountMax) {
		this.ownerConsumeAmountMax = ownerConsumeAmountMax;
	}

	public String getVinBeginRange() {
		return vinBeginRange;
	}

	public void setVinBeginRange(String vinBeginRange) {
		this.vinBeginRange = vinBeginRange;
	}

	public String getVinEndRange() {
		return vinEndRange;
	}

	public void setVinEndRange(String vinEndRange) {
		this.vinEndRange = vinEndRange;
	}

	public Integer getBookingTag() {
		return bookingTag;
	}

	public void setBookingTag(Integer bookingTag) {
		this.bookingTag = bookingTag;
	}

}
