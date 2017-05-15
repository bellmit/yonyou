/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TmOutBoundEntityMonthReportDTO {
	private String dealerCode;
	private Long itemId;
	private Integer eConfirmNum;
	private Integer eSuccessNum;
	private Integer eFailNum;
	private Integer eNeedSecondUpload;
	private Double ePassRate;
	private Integer ePams;
	private Integer eSixMonthFailNum;
	private Integer eOverTimeNum;
	private Date   createdAt;
	private Long	createdBy;
	private Date  updatedAt;
	private Long  updatedBy;
	private Integer dKey;
	private Integer ver;
	private Integer eWxBindingNum;
	private Double eBindingRate;
	private Integer eIsPass;
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Integer geteConfirmNum() {
		return eConfirmNum;
	}
	public void seteConfirmNum(Integer eConfirmNum) {
		this.eConfirmNum = eConfirmNum;
	}
	public Integer geteSuccessNum() {
		return eSuccessNum;
	}
	public void seteSuccessNum(Integer eSuccessNum) {
		this.eSuccessNum = eSuccessNum;
	}
	public Integer geteFailNum() {
		return eFailNum;
	}
	public void seteFailNum(Integer eFailNum) {
		this.eFailNum = eFailNum;
	}
	public Integer geteNeedSecondUpload() {
		return eNeedSecondUpload;
	}
	public void seteNeedSecondUpload(Integer eNeedSecondUpload) {
		this.eNeedSecondUpload = eNeedSecondUpload;
	}
	public Double getePassRate() {
		return ePassRate;
	}
	public void setePassRate(Double ePassRate) {
		this.ePassRate = ePassRate;
	}
	public Integer getePams() {
		return ePams;
	}
	public void setePams(Integer ePams) {
		this.ePams = ePams;
	}
	public Integer geteSixMonthFailNum() {
		return eSixMonthFailNum;
	}
	public void seteSixMonthFailNum(Integer eSixMonthFailNum) {
		this.eSixMonthFailNum = eSixMonthFailNum;
	}
	public Integer geteOverTimeNum() {
		return eOverTimeNum;
	}
	public void seteOverTimeNum(Integer eOverTimeNum) {
		this.eOverTimeNum = eOverTimeNum;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Integer getdKey() {
		return dKey;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Integer geteWxBindingNum() {
		return eWxBindingNum;
	}
	public void seteWxBindingNum(Integer eWxBindingNum) {
		this.eWxBindingNum = eWxBindingNum;
	}
	public Double geteBindingRate() {
		return eBindingRate;
	}
	public void seteBindingRate(Double eBindingRate) {
		this.eBindingRate = eBindingRate;
	}
	public Integer geteIsPass() {
		return eIsPass;
	}
	public void seteIsPass(Integer eIsPass) {
		this.eIsPass = eIsPass;
	}
	
	
}
