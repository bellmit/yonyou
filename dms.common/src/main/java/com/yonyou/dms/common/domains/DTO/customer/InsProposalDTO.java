package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class InsProposalDTO {

	private String proposalCode;// 投保单号

	private String vin;

	private Integer proposalType;// 投保类型
	private String PROPOSAL_NAME;// 投保人姓名
	private String soNo;// 订单编号
	private String license;// 车牌号
	private String engineNo;// 发动机编号
	private Integer ownerProperty;// 客户类型
	private String phone;// 电话
	private String mobile;// 手机
	private Integer zipCode;// 邮编
	private String address;// 地址
	private String INSURED_NAME;// 被保险人
	private Integer ctCode;// 证件类型
	private String certificateNo;// 证件号码
	private Integer vehicleRelation;// 与机动车关系
	private Integer isLocal;// 是否本地投保
	private Integer isCredit;// 是否信贷投保
	private Integer insChannl;// 投保渠道
	private String remark;// 备注
	private Integer approvedCustomerCount;// 核定载客
	private Integer approvedCustomerQuality;// 核定载客质量
	private Date firstRegisterDate;// 初次登记日期
	private Integer userYear;// 已使用年限
	private Integer vehicleType;// 机动车种类
	private Integer vehicleUseNature;// 机动车使用性质
	private String insBroker;// 保险顾问
	private Double totalDiscountAmount;// 优惠金额
	private String total_traffic_amount;
	private String total_business_amount;
	private Double travelTaxAmount;// 车船税
	private Double otherAmount;// 其他业务
	private Double totalAmount;// 总金额实际
	private String totalPreferential;
	private String insCompany;// 保险公司
	private Integer isCollection;// 是否代收
	private Integer salesModel;// 销售模式
	private String ownerNo;// 车主编号
	private Double comInsuranceRebateAmt;// 商业险返利金额
	private String busiAmt;
	private Integer isPayoffRebate;// 是否返利讨乞
	private Double receivableComAmount;// 交强险应收金额
	private Double applyAmount;// 应收金额
	private Double receivableBusiAmount;// 商业险应收金额
	private Date insSalesDate;// 保险销售日期
	private String comInsCode;// 交强险保单号
	private String busiInsCode;// 商业险保单号
	private Integer isAddProposal;// 是否新增续保
	private String isSelfCompanyInsurance;// 是否本公司投保
	private String FILE_B_INS_ID;// 商业险扫描件ID
	private String FILE_B_INS_URL;// 商业险扫描件URL
	private String FILE_A_INS_ID;// 交强险扫描件ID
	private String FILE_A_INS_URL;// 交强险扫描件ID
	private String INSURANCE_TYPE_CODE;// 险种代码
	private String INSURANCE_TYPE_NAME;// 险种名称
	private Integer INSURANCE_TYPE_LEVEL;// 险种类别
	private Double PROPOSAL_AMOUNT;// 保额
	private Double RECEIVABLE_AMOUNT;// 应收金额
	private Date BEGIN_DATE;// 开始日期
	private Date END_DATE;// 结束日期
	private Integer IS_PRESENTED;// 是否赠送

	public Double getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getProposalType() {
		return proposalType;
	}

	public void setProposalType(Integer proposalType) {
		this.proposalType = proposalType;
	}

	public String getPROPOSAL_NAME() {
		return PROPOSAL_NAME;
	}

	public void setPROPOSAL_NAME(String pROPOSAL_NAME) {
		PROPOSAL_NAME = pROPOSAL_NAME;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public Integer getOwnerProperty() {
		return ownerProperty;
	}

	public void setOwnerProperty(Integer ownerProperty) {
		this.ownerProperty = ownerProperty;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getINSURED_NAME() {
		return INSURED_NAME;
	}

	public void setINSURED_NAME(String iNSURED_NAME) {
		INSURED_NAME = iNSURED_NAME;
	}

	public Integer getCtCode() {
		return ctCode;
	}

	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public Integer getVehicleRelation() {
		return vehicleRelation;
	}

	public void setVehicleRelation(Integer vehicleRelation) {
		this.vehicleRelation = vehicleRelation;
	}

	public Integer getIsLocal() {
		return isLocal;
	}

	public void setIsLocal(Integer isLocal) {
		this.isLocal = isLocal;
	}

	public Integer getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(Integer isCredit) {
		this.isCredit = isCredit;
	}

	public Integer getInsChannl() {
		return insChannl;
	}

	public void setInsChannl(Integer insChannl) {
		this.insChannl = insChannl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getApprovedCustomerCount() {
		return approvedCustomerCount;
	}

	public void setApprovedCustomerCount(Integer approvedCustomerCount) {
		this.approvedCustomerCount = approvedCustomerCount;
	}

	public Integer getApprovedCustomerQuality() {
		return approvedCustomerQuality;
	}

	public void setApprovedCustomerQuality(Integer approvedCustomerQuality) {
		this.approvedCustomerQuality = approvedCustomerQuality;
	}

	public Date getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(Date firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}

	public Integer getUserYear() {
		return userYear;
	}

	public void setUserYear(Integer userYear) {
		this.userYear = userYear;
	}

	public Integer getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Integer vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Integer getVehicleUseNature() {
		return vehicleUseNature;
	}

	public void setVehicleUseNature(Integer vehicleUseNature) {
		this.vehicleUseNature = vehicleUseNature;
	}

	public String getInsBroker() {
		return insBroker;
	}

	public void setInsBroker(String insBroker) {
		this.insBroker = insBroker;
	}

	public Double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(Double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public String getTotal_traffic_amount() {
		return total_traffic_amount;
	}

	public void setTotal_traffic_amount(String total_traffic_amount) {
		this.total_traffic_amount = total_traffic_amount;
	}

	public String getTotal_business_amount() {
		return total_business_amount;
	}

	public void setTotal_business_amount(String total_business_amount) {
		this.total_business_amount = total_business_amount;
	}

	public Double getTravelTaxAmount() {
		return travelTaxAmount;
	}

	public void setTravelTaxAmount(Double travelTaxAmount) {
		this.travelTaxAmount = travelTaxAmount;
	}

	public Double getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalPreferential() {
		return totalPreferential;
	}

	public void setTotalPreferential(String totalPreferential) {
		this.totalPreferential = totalPreferential;
	}

	public String getInsCompany() {
		return insCompany;
	}

	public void setInsCompany(String insCompany) {
		this.insCompany = insCompany;
	}

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}

	public Integer getSalesModel() {
		return salesModel;
	}

	public void setSalesModel(Integer salesModel) {
		this.salesModel = salesModel;
	}

	public String getOwnerNo() {
		return ownerNo;
	}

	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}

	public Double getComInsuranceRebateAmt() {
		return comInsuranceRebateAmt;
	}

	public void setComInsuranceRebateAmt(Double comInsuranceRebateAmt) {
		this.comInsuranceRebateAmt = comInsuranceRebateAmt;
	}

	public String getBusiAmt() {
		return busiAmt;
	}

	public void setBusiAmt(String busiAmt) {
		this.busiAmt = busiAmt;
	}

	public Integer getIsPayoffRebate() {
		return isPayoffRebate;
	}

	public void setIsPayoffRebate(Integer isPayoffRebate) {
		this.isPayoffRebate = isPayoffRebate;
	}

	public Double getReceivableComAmount() {
		return receivableComAmount;
	}

	public void setReceivableComAmount(Double receivableComAmount) {
		this.receivableComAmount = receivableComAmount;
	}

	public Double getReceivableBusiAmount() {
		return receivableBusiAmount;
	}

	public void setReceivableBusiAmount(Double receivableBusiAmount) {
		this.receivableBusiAmount = receivableBusiAmount;
	}

	public Date getInsSalesDate() {
		return insSalesDate;
	}

	public void setInsSalesDate(Date insSalesDate) {
		this.insSalesDate = insSalesDate;
	}

	public String getComInsCode() {
		return comInsCode;
	}

	public void setComInsCode(String comInsCode) {
		this.comInsCode = comInsCode;
	}

	public String getBusiInsCode() {
		return busiInsCode;
	}

	public void setBusiInsCode(String busiInsCode) {
		this.busiInsCode = busiInsCode;
	}

	public Integer getIsAddProposal() {
		return isAddProposal;
	}

	public void setIsAddProposal(Integer isAddProposal) {
		this.isAddProposal = isAddProposal;
	}

	public String getIsSelfCompanyInsurance() {
		return isSelfCompanyInsurance;
	}

	public void setIsSelfCompanyInsurance(String isSelfCompanyInsurance) {
		this.isSelfCompanyInsurance = isSelfCompanyInsurance;
	}

	public String getFILE_B_INS_ID() {
		return FILE_B_INS_ID;
	}

	public void setFILE_B_INS_ID(String fILE_B_INS_ID) {
		FILE_B_INS_ID = fILE_B_INS_ID;
	}

	public String getFILE_B_INS_URL() {
		return FILE_B_INS_URL;
	}

	public void setFILE_B_INS_URL(String fILE_B_INS_URL) {
		FILE_B_INS_URL = fILE_B_INS_URL;
	}

	public String getFILE_A_INS_ID() {
		return FILE_A_INS_ID;
	}

	public void setFILE_A_INS_ID(String fILE_A_INS_ID) {
		FILE_A_INS_ID = fILE_A_INS_ID;
	}

	public String getFILE_A_INS_URL() {
		return FILE_A_INS_URL;
	}

	public void setFILE_A_INS_URL(String fILE_A_INS_URL) {
		FILE_A_INS_URL = fILE_A_INS_URL;
	}

	public String getINSURANCE_TYPE_CODE() {
		return INSURANCE_TYPE_CODE;
	}

	public void setINSURANCE_TYPE_CODE(String iNSURANCE_TYPE_CODE) {
		INSURANCE_TYPE_CODE = iNSURANCE_TYPE_CODE;
	}

	public String getINSURANCE_TYPE_NAME() {
		return INSURANCE_TYPE_NAME;
	}

	public void setINSURANCE_TYPE_NAME(String iNSURANCE_TYPE_NAME) {
		INSURANCE_TYPE_NAME = iNSURANCE_TYPE_NAME;
	}

	public Integer getINSURANCE_TYPE_LEVEL() {
		return INSURANCE_TYPE_LEVEL;
	}

	public void setINSURANCE_TYPE_LEVEL(Integer iNSURANCE_TYPE_LEVEL) {
		INSURANCE_TYPE_LEVEL = iNSURANCE_TYPE_LEVEL;
	}

	public Double getPROPOSAL_AMOUNT() {
		return PROPOSAL_AMOUNT;
	}

	public void setPROPOSAL_AMOUNT(Double pROPOSAL_AMOUNT) {
		PROPOSAL_AMOUNT = pROPOSAL_AMOUNT;
	}

	public Double getRECEIVABLE_AMOUNT() {
		return RECEIVABLE_AMOUNT;
	}

	public void setRECEIVABLE_AMOUNT(Double rECEIVABLE_AMOUNT) {
		RECEIVABLE_AMOUNT = rECEIVABLE_AMOUNT;
	}

	public Date getBEGIN_DATE() {
		return BEGIN_DATE;
	}

	public void setBEGIN_DATE(Date bEGIN_DATE) {
		BEGIN_DATE = bEGIN_DATE;
	}

	public Date getEND_DATE() {
		return END_DATE;
	}

	public void setEND_DATE(Date eND_DATE) {
		END_DATE = eND_DATE;
	}

	public Integer getIS_PRESENTED() {
		return IS_PRESENTED;
	}

	public void setIS_PRESENTED(Integer iS_PRESENTED) {
		IS_PRESENTED = iS_PRESENTED;
	}

}
