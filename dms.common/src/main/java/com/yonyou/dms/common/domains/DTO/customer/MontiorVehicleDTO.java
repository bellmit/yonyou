package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;
import java.util.List;
import java.util.Map;
//监控车主车辆信息
public class MontiorVehicleDTO {
	private Double monitorID;
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
	private Date saledDateEnd;
	private Integer idValid;
	private Double ownerConsumeAmountMin;
	private Double ownerConsumeAmountMax;
	private String vinBeginRange;
	private String vinEndRange;
	private Integer bookingTag;
	@SuppressWarnings("rawtypes")
	private List<Map> dms_monitor;
	
	@SuppressWarnings("rawtypes")
	public List<Map> getDms_monitor() {
		return dms_monitor;
	}
	public void setDms_monitor(@SuppressWarnings("rawtypes") List<Map> dms_monitor) {
		this.dms_monitor = dms_monitor;
	}
	public Double getMonitorID() {
		return monitorID;
	}
	public void setMonitorID(Double monitorID) {
		this.monitorID = monitorID;
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
	public void setMessagee(String message) {
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
	public Date getSaledDateEnd() {
		return saledDateEnd;
	}
	public void setSaledDateEnd(Date saledDateEnd) {
		this.saledDateEnd = saledDateEnd;
	}
	public Integer getIdValid() {
		return idValid;
	}
	public void setIdValid(Integer idValid) {
		this.idValid = idValid;
	}
	public Double getOwnerConsumeAmountMin() {
		return ownerConsumeAmountMin;
	}
	public void setOwnerConsumeAmountMin(Double ownerConsumeAmountMin) {
		this.ownerConsumeAmountMin = ownerConsumeAmountMin;
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
