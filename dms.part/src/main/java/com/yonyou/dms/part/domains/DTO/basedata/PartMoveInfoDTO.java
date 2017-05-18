package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

/**
 * @description 配件移库单 表格用DTO
 * @author Administrator
 *
 */
public class PartMoveInfoDTO {

	private String dealerCode;
	private String transferNo;
	private String handler;
	private Date  transferDate;
	private Integer  isFinished;
	private Date  finishedDate;
	private String lockUser;
	private Integer  dKey;
	private Integer createBy;
	private Date createAt;
	private Integer updateBy;
	private Date updateAt;
	private Integer ver;
	public String getDealerCode() {
		return dealerCode;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public String getHandler() {
		return handler;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public Integer getIsFinished() {
		return isFinished;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public String getLockUser() {
		return lockUser;
	}
	public Integer getdKey() {
		return dKey;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public Integer getVer() {
		return ver;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public void setIsFinished(Integer isFinished) {
		this.isFinished = isFinished;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
}
