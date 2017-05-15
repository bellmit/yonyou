package com.yonyou.dms.retail.domains.DTO.rebate;


/**
 * 
* @ClassName: TtRebateCalculateTmpDTO2 
* @Description: 返利核算管理
* @author zhengzengliang 
* @date 2017年3月29日 下午3:07:24 
*
 */
public class TtRebateCalculateTmpDTO2 {
	
	private String businessPolicyName;
	private String rYear;
	private String rMonth;
	private String eYear;
	private String eMonth;
	private String uploadWayName;
	private String businessPolicyTypeName;
	public String getBusinessPolicyName() {
		return businessPolicyName;
	}
	public void setBusinessPolicyName(String businessPolicyName) {
		this.businessPolicyName = businessPolicyName;
	}
	public String getrYear() {
		return rYear;
	}
	public void setrYear(String rYear) {
		this.rYear = rYear;
	}
	public String getrMonth() {
		return rMonth;
	}
	public void setrMonth(String rMonth) {
		this.rMonth = rMonth;
	}
	public String geteYear() {
		return eYear;
	}
	public void seteYear(String eYear) {
		this.eYear = eYear;
	}
	public String geteMonth() {
		return eMonth;
	}
	public void seteMonth(String eMonth) {
		this.eMonth = eMonth;
	}
	public String getUploadWayName() {
		return uploadWayName;
	}
	public void setUploadWayName(String uploadWayName) {
		this.uploadWayName = uploadWayName;
	}
	public String getBusinessPolicyTypeName() {
		return businessPolicyTypeName;
	}
	public void setBusinessPolicyTypeName(String businessPolicyTypeName) {
		this.businessPolicyTypeName = businessPolicyTypeName;
	}


}
