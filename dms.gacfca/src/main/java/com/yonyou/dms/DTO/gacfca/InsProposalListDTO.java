package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class InsProposalListDTO {
	
	private Date beginDate;
	private Date updateDate;
	private Double realReceiveAmount;//实收金额
	private String entityCode;
	private Long createBy;
	private Integer ver;
	private Date createDate;
	private Double receivableAmount;//应收帐款
	private Double proposalAmount;//保额
	private Integer insuranceTypeLevel;//险种类别
	private String insuranceTypeCode;//险种代码
	private Integer isPresented;//是否赠送
	private Long updateBy;
	private String proposalCode;//投保单号
	private Double discountAmount;//折让金额
	private Long itemId;
	private Date endDate;
	private String remark;//备注
	//add by lsy 2015-6-26 
	private String insuranceTypeName;//险种名称
	//end
	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getInsuranceTypeName() {
		return insuranceTypeName;
	}
	public void setInsuranceTypeName(String insuranceTypeName) {
		this.insuranceTypeName = insuranceTypeName;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getInsuranceTypeCode() {
		return insuranceTypeCode;
	}
	public void setInsuranceTypeCode(String insuranceTypeCode) {
		this.insuranceTypeCode = insuranceTypeCode;
	}
	public Integer getInsuranceTypeLevel() {
		return insuranceTypeLevel;
	}
	public void setInsuranceTypeLevel(Integer insuranceTypeLevel) {
		this.insuranceTypeLevel = insuranceTypeLevel;
	}
	public Integer getIsPresented() {
		return isPresented;
	}
	public void setIsPresented(Integer isPresented) {
		this.isPresented = isPresented;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Double getProposalAmount() {
		return proposalAmount;
	}
	public void setProposalAmount(Double proposalAmount) {
		this.proposalAmount = proposalAmount;
	}
	public String getProposalCode() {
		return proposalCode;
	}
	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}
	public Double getRealReceiveAmount() {
		return realReceiveAmount;
	}
	public void setRealReceiveAmount(Double realReceiveAmount) {
		this.realReceiveAmount = realReceiveAmount;
	}
	public Double getReceivableAmount() {
		return receivableAmount;
	}
	public void setReceivableAmount(Double receivableAmount) {
		this.receivableAmount = receivableAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
}
