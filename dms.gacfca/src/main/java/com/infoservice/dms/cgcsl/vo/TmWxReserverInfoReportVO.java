/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TmWxReserverInfoReportVO extends BaseVO{
	private String reserveId;
	private String vin;
	private Date updateDate;
	private Date reserverConfirmTime;
	private String remark;
	// private Long createBy;
	private Integer status;
	private String licenceNum;
	// private Long updateBy;
	private String miles;
	private String dealerCode;
	private String reserveTimeId;
	private String telephone;
	private Date reserveDate;
	private Date createDate;
	private String linkmanName;
	private Date serviceFinishDate; // reserve_finish_Date timestamp --服务完成日期
	private Date reserveExpireDate; // reserve_expire_Date timestamp --超期日期
	private Date reserveCancelDate; // reserve_Cancel__Date timestamp --取消日期
	private String entityCode; // 下端：经销商代码 CHAR(8) 上端：
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getReserverConfirmTime() {
		return reserverConfirmTime;
	}
	public void setReserverConfirmTime(Date reserverConfirmTime) {
		this.reserverConfirmTime = reserverConfirmTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLicenceNum() {
		return licenceNum;
	}
	public void setLicenceNum(String licenceNum) {
		this.licenceNum = licenceNum;
	}
	public String getMiles() {
		return miles;
	}
	public void setMiles(String miles) {
		this.miles = miles;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getReserveTimeId() {
		return reserveTimeId;
	}
	public void setReserveTimeId(String reserveTimeId) {
		this.reserveTimeId = reserveTimeId;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getReserveDate() {
		return reserveDate;
	}
	public void setReserveDate(Date reserveDate) {
		this.reserveDate = reserveDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public Date getServiceFinishDate() {
		return serviceFinishDate;
	}
	public void setServiceFinishDate(Date serviceFinishDate) {
		this.serviceFinishDate = serviceFinishDate;
	}
	public Date getReserveExpireDate() {
		return reserveExpireDate;
	}
	public void setReserveExpireDate(Date reserveExpireDate) {
		this.reserveExpireDate = reserveExpireDate;
	}
	public Date getReserveCancelDate() {
		return reserveCancelDate;
	}
	public void setReserveCancelDate(Date reserveCancelDate) {
		this.reserveCancelDate = reserveCancelDate;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	

}
