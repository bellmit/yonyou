package com.yonyou.dms.DTO.gacfca;
/**
 * 合作银行下发VO
 * @author wangJian by 2016-4-11
 *
 */
public class PayingBankDTO{
	
	private String bankName;//银行名称
	private Integer status;//有效、无效
	private Integer updateStatus;//是否更新
	private Integer bankCode;//合作银行
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getUpdateStatus() {
		return updateStatus;
	}
	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}
	public Integer getBankCode() {
		return bankCode;
	}
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

}
