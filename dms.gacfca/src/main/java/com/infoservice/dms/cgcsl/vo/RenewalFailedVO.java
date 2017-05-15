package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class RenewalFailedVO {
	private String entityCode;
	private Long remindId;//保险提醒记录ID
	private String ownerNo;//车主编号
	private String vin;
	private Date remindDate;//提醒日期
	private Integer renewalfailedReason;//续保失败原因 13931002 出单服务差 13931003 出险服务差 13931004 维修质量差（含时间长） 13931005 其它（请在备注栏说明） 13931001 价格高
	private String renewalRemark;//续保失败备注
	private String seriesCode;//车系
	
	
	
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public Long getRemindId() {
		return remindId;
	}
	public void setRemindId(Long remindId) {
		this.remindId = remindId;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}
	public Integer getRenewalfailedReason() {
		return renewalfailedReason;
	}
	public void setRenewalfailedReason(Integer renewalfailedReason) {
		this.renewalfailedReason = renewalfailedReason;
	}
	public String getRenewalRemark() {
		return renewalRemark;
	}
	public void setRenewalRemark(String renewalRemark) {
		this.renewalRemark = renewalRemark;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
}
