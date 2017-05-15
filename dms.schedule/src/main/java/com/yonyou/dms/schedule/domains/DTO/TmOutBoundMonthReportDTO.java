/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TmOutBoundMonthReportDTO {
	private String dealerCode;
	private Long itemId;
	private Long userId;
	private String userName;
	private Integer confirmNum;
	private Integer successNum;
	private Integer failNum;
	private Integer needSecondUpload;
	private Double passRate;
	private Integer pams;
	private Integer sixMonthFailNum;
	private Integer overTimeNum;
	private Date   createdAt;
	private Long	createdBy;
	private Date  updatedAt;
	private Long  updatedBy;
	private Integer dKey;
	private Integer ver;
	private Integer wxBindingNum;
	private Double bindingRate;
	private Integer isPass;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getConfirmNum() {
		return confirmNum;
	}
	public void setConfirmNum(Integer confirmNum) {
		this.confirmNum = confirmNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public Integer getNeedSecondUpload() {
		return needSecondUpload;
	}
	public void setNeedSecondUpload(Integer needSecondUpload) {
		this.needSecondUpload = needSecondUpload;
	}
	public Double getPassRate() {
		return passRate;
	}
	public void setPassRate(Double passRate) {
		this.passRate = passRate;
	}
	public Integer getPams() {
		return pams;
	}
	public void setPams(Integer pams) {
		this.pams = pams;
	}
	public Integer getSixMonthFailNum() {
		return sixMonthFailNum;
	}
	public void setSixMonthFailNum(Integer sixMonthFailNum) {
		this.sixMonthFailNum = sixMonthFailNum;
	}
	public Integer getOverTimeNum() {
		return overTimeNum;
	}
	public void setOverTimeNum(Integer overTimeNum) {
		this.overTimeNum = overTimeNum;
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
	public Integer getWxBindingNum() {
		return wxBindingNum;
	}
	public void setWxBindingNum(Integer wxBindingNum) {
		this.wxBindingNum = wxBindingNum;
	}
	public Double getBindingRate() {
		return bindingRate;
	}
	public void setBindingRate(Double bindingRate) {
		this.bindingRate = bindingRate;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	
	
}
