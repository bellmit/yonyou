package com.yonyou.dms.common.domains.DTO.basedata;

/**
* 索赔申请
* @author ZhaoZ
* @date 2017年4月26日
*/
public class TtWrClaimDcsDTO {

	private String approveOper;
	private String checkDesc;
	private String checkCode;
	private String codeDescHidden;
	private String roNo;//维修工单号
	private Long dealerId;//经销商ID
	private String deductFee;
	private String deductRemark;
	
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public String getApproveOper() {
		return approveOper;
	}
	public void setApproveOper(String approveOper) {
		this.approveOper = approveOper;
	}
	public String getCheckDesc() {
		return checkDesc;
	}
	public void setCheckDesc(String checkDesc) {
		this.checkDesc = checkDesc;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	public String getCodeDescHidden() {
		return codeDescHidden;
	}
	public void setCodeDescHidden(String codeDescHidden) {
		this.codeDescHidden = codeDescHidden;
	}
	public String getDeductFee() {
		return deductFee;
	}
	public void setDeductFee(String deductFee) {
		this.deductFee = deductFee;
	}
	public String getDeductRemark() {
		return deductRemark;
	}
	public void setDeductRemark(String deductRemark) {
		this.deductRemark = deductRemark;
	}
	
}
