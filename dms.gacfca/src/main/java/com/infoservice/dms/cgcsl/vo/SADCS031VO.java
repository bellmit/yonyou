/**
 * 
 */
package com.infoservice.dms.cgcsl.vo;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SADCS031VO extends BaseVO{
	private Long bigCustomerPolicyId;
	private String seriesCode;
	private String brandCode;
	private Integer status;
	private Integer isDelete;
	private Integer psType;
	public Long getBigCustomerPolicyId() {
		return bigCustomerPolicyId;
	}
	public void setBigCustomerPolicyId(Long bigCustomerPolicyId) {
		this.bigCustomerPolicyId = bigCustomerPolicyId;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Integer getPsType() {
		return psType;
	}
	public void setPsType(Integer psType) {
		this.psType = psType;
	}
	
}
