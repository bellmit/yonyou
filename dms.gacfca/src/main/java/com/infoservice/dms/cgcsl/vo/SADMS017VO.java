/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SADMS017VO extends BaseVO{
	private String soNo;//销售订单号
	private String dealerCode;
	private String auditBy;//审核人
	private Integer soStatus;//下端字典 dcs是一致的 1595打头
	private String auditRemark;//审核意见
	private Date auditDate ;//审核日期
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
}
