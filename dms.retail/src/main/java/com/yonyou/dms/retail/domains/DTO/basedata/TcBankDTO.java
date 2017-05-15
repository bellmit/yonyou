package com.yonyou.dms.retail.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;
import java.util.Date;

public class TcBankDTO {
	private Integer btcCode;
	private Long updateBy;
	private Date updateDate;
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer status;
	private Integer isArc;
	@Required
	private String bankName;
	private Integer isDel;
	private Integer isSend;
	private Date sendDate;
	private Long sendBy;
	private Integer updateStatus;

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Long getSendBy() {
		return sendBy;
	}

	public void setSendBy(Long sendBy) {
		this.sendBy = sendBy;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

	public void setBtcCode(Integer btcCode) {
		this.btcCode = btcCode;
	}

	public Integer getBtcCode() {
		return this.btcCode;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Integer getVer() {
		return this.ver;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getCreateBy() {
		return this.createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setIsArc(Integer isArc) {
		this.isArc = isArc;
	}

	public Integer getIsArc() {
		return this.isArc;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankName() {
		return this.bankName;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getIsDel() {
		return this.isDel;
	}

}
