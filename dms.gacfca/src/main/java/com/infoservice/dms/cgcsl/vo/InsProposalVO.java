package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;
/**
 * 
 * Title:InsProposalVO
 * Description: 投保单主表上报vo
 * @author DC
 * @date 2017年4月7日 下午2:00:58
 */
public class InsProposalVO extends  BaseVO{
	
	private static final long serialVersionUID = 1L;
	private String proposalName;
	private Double totalAmount;
	private String entityCode;
	private Integer ver;
	private Float receivableBusiAmount;
	private Date payOffDate;
	private Integer isTransfer;
	private Date createDate;
	private Integer vehicleRelation;
	private String insurationCode;
	private Float receivableComAmount;
	private String orgCusNo;
	private String proposalCode;
	private String vin;
	private Date firstRegisterDate;
	private Integer isCollection;
	private String insBroker;
	private Integer isInsCredit;
	private String mobile;
	private String zipCode;
	private Date completedDate;
	private Long updateBy;
	private String insuredName;
	private Integer isInsLocal;
	private Long createBy;
	private String certificateNo;
	private String phone;
	private Float busiInsuranceRebateAmt;
	private Float busiInsuranceRePayAmt;
	private Integer ctCode;
	private Double totalTrafficAmount;
	private Double totalBusinessAmount;
	private Float approvedCustomerQuality;
	private Float otherAmount;
	private Integer insChannels;
	private Integer vehicleType;
	private Integer proposalType;
	private Date updateDate;
	private String transferOwnerNo;
	private Integer isPayoffRebate;
	private Integer isAddProposal;
	private Integer salesModel;
	private Float approvedCustomerCount;
	private Double totalDiscountAmount;
	private Float comInsuranceRebateAmt;
	private Float comInsuranceRePayAmt;
	private Float usedYear;
	private Float travelTaxAmount;
	private Integer formStatus;
	private String remark;
	private Integer vehicleUseNature;
	private String address;
	private Integer payOff;
	private Integer lossesPayOff;
	private Double gatheredSum;
	private Date insSalesDate;
	private String comInsCode;
	private String busiInsCode;
	private Integer isSelfCompanyInsurance;
	private String fileBInsId;
	private String fileBInsUrl;
	private String fileAInsId;
	private String fileAInsUrl;
	private String soNo;
	//add by lsy 2015-6-23
	private Double totalPreferential;//
	private String license;//车牌号
	private String engineNo;//发动机号
	private Integer ownerProperty;//客户类型
	//end 
	private String brand;//品牌
	private String series;//车系
	private String model;//车型
	private String configCode;//车款
	private String modelYear;//年款
	private Integer cardNumber;//卡券数量
	
	private LinkedList<InsProposalListVO> insProposalList;
	
	private LinkedList<InsProposalCardListVO> insProposalCardList;
	
	public LinkedList<InsProposalCardListVO> getInsProposalCardList() {
		return insProposalCardList;
	}

	public void setInsProposalCardList(
			LinkedList<InsProposalCardListVO> insProposalCardList) {
		this.insProposalCardList = insProposalCardList;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public Integer getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Integer cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Double getTotalPreferential() {
		return totalPreferential;
	}

	public void setTotalPreferential(Double totalPreferential) {
		this.totalPreferential = totalPreferential;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getApprovedCustomerCount() {
		return approvedCustomerCount;
	}

	public void setApprovedCustomerCount(Float approvedCustomerCount) {
		this.approvedCustomerCount = approvedCustomerCount;
	}

	public Float getApprovedCustomerQuality() {
		return approvedCustomerQuality;
	}

	public void setApprovedCustomerQuality(Float approvedCustomerQuality) {
		this.approvedCustomerQuality = approvedCustomerQuality;
	}

	public String getBusiInsCode() {
		return busiInsCode;
	}

	public void setBusiInsCode(String busiInsCode) {
		this.busiInsCode = busiInsCode;
	}

	public Float getBusiInsuranceRebateAmt() {
		return busiInsuranceRebateAmt;
	}

	public void setBusiInsuranceRebateAmt(Float busiInsuranceRebateAmt) {
		this.busiInsuranceRebateAmt = busiInsuranceRebateAmt;
	}

	public Float getBusiInsuranceRePayAmt() {
		return busiInsuranceRePayAmt;
	}

	public void setBusiInsuranceRePayAmt(Float busiInsuranceRePayAmt) {
		this.busiInsuranceRePayAmt = busiInsuranceRePayAmt;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public String getComInsCode() {
		return comInsCode;
	}

	public void setComInsCode(String comInsCode) {
		this.comInsCode = comInsCode;
	}

	public Float getComInsuranceRebateAmt() {
		return comInsuranceRebateAmt;
	}

	public void setComInsuranceRebateAmt(Float comInsuranceRebateAmt) {
		this.comInsuranceRebateAmt = comInsuranceRebateAmt;
	}

	public Float getComInsuranceRePayAmt() {
		return comInsuranceRePayAmt;
	}

	public void setComInsuranceRePayAmt(Float comInsuranceRePayAmt) {
		this.comInsuranceRePayAmt = comInsuranceRePayAmt;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
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

	public Integer getCtCode() {
		return ctCode;
	}

	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getFileAInsId() {
		return fileAInsId;
	}

	public void setFileAInsId(String fileAInsId) {
		this.fileAInsId = fileAInsId;
	}

	public String getFileAInsUrl() {
		return fileAInsUrl;
	}

	public void setFileAInsUrl(String fileAInsUrl) {
		this.fileAInsUrl = fileAInsUrl;
	}

	public String getFileBInsId() {
		return fileBInsId;
	}

	public void setFileBInsId(String fileBInsId) {
		this.fileBInsId = fileBInsId;
	}

	public String getFileBInsUrl() {
		return fileBInsUrl;
	}

	public void setFileBInsUrl(String fileBInsUrl) {
		this.fileBInsUrl = fileBInsUrl;
	}

	public Date getFirstRegisterDate() {
		return firstRegisterDate;
	}

	public void setFirstRegisterDate(Date firstRegisterDate) {
		this.firstRegisterDate = firstRegisterDate;
	}

	public Integer getFormStatus() {
		return formStatus;
	}

	public void setFormStatus(Integer formStatus) {
		this.formStatus = formStatus;
	}

	public Double getGatheredSum() {
		return gatheredSum;
	}

	public void setGatheredSum(Double gatheredSum) {
		this.gatheredSum = gatheredSum;
	}

	public String getInsBroker() {
		return insBroker;
	}

	public void setInsBroker(String insBroker) {
		this.insBroker = insBroker;
	}

	public Integer getInsChannels() {
		return insChannels;
	}

	public void setInsChannels(Integer insChannels) {
		this.insChannels = insChannels;
	}

	public LinkedList<InsProposalListVO> getInsProposalList() {
		return insProposalList;
	}

	public void setInsProposalList(LinkedList<InsProposalListVO> insProposalList) {
		this.insProposalList = insProposalList;
	}

	public Date getInsSalesDate() {
		return insSalesDate;
	}

	public void setInsSalesDate(Date insSalesDate) {
		this.insSalesDate = insSalesDate;
	}

	public String getInsurationCode() {
		return insurationCode;
	}

	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public Integer getIsAddProposal() {
		return isAddProposal;
	}

	public void setIsAddProposal(Integer isAddProposal) {
		this.isAddProposal = isAddProposal;
	}

	public Integer getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Integer isCollection) {
		this.isCollection = isCollection;
	}

	public Integer getIsInsCredit() {
		return isInsCredit;
	}

	public void setIsInsCredit(Integer isInsCredit) {
		this.isInsCredit = isInsCredit;
	}

	public Integer getIsInsLocal() {
		return isInsLocal;
	}

	public void setIsInsLocal(Integer isInsLocal) {
		this.isInsLocal = isInsLocal;
	}

	public Integer getIsPayoffRebate() {
		return isPayoffRebate;
	}

	public void setIsPayoffRebate(Integer isPayoffRebate) {
		this.isPayoffRebate = isPayoffRebate;
	}

	public Integer getIsSelfCompanyInsurance() {
		return isSelfCompanyInsurance;
	}

	public void setIsSelfCompanyInsurance(Integer isSelfCompanyInsurance) {
		this.isSelfCompanyInsurance = isSelfCompanyInsurance;
	}

	public Integer getIsTransfer() {
		return isTransfer;
	}

	public void setIsTransfer(Integer isTransfer) {
		this.isTransfer = isTransfer;
	}

	public Integer getLossesPayOff() {
		return lossesPayOff;
	}

	public void setLossesPayOff(Integer lossesPayOff) {
		this.lossesPayOff = lossesPayOff;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrgCusNo() {
		return orgCusNo;
	}

	public void setOrgCusNo(String orgCusNo) {
		this.orgCusNo = orgCusNo;
	}

	public Float getOtherAmount() {
		return otherAmount;
	}

	public void setOtherAmount(Float otherAmount) {
		this.otherAmount = otherAmount;
	}

	public Integer getPayOff() {
		return payOff;
	}

	public void setPayOff(Integer payOff) {
		this.payOff = payOff;
	}

	public Date getPayOffDate() {
		return payOffDate;
	}

	public void setPayOffDate(Date payOffDate) {
		this.payOffDate = payOffDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProposalCode() {
		return proposalCode;
	}

	public void setProposalCode(String proposalCode) {
		this.proposalCode = proposalCode;
	}

	public String getProposalName() {
		return proposalName;
	}

	public void setProposalName(String proposalName) {
		this.proposalName = proposalName;
	}

	public Integer getProposalType() {
		return proposalType;
	}

	public void setProposalType(Integer proposalType) {
		this.proposalType = proposalType;
	}

	public Float getReceivableBusiAmount() {
		return receivableBusiAmount;
	}

	public void setReceivableBusiAmount(Float receivableBusiAmount) {
		this.receivableBusiAmount = receivableBusiAmount;
	}

	public Float getReceivableComAmount() {
		return receivableComAmount;
	}

	public void setReceivableComAmount(Float receivableComAmount) {
		this.receivableComAmount = receivableComAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSalesModel() {
		return salesModel;
	}

	public void setSalesModel(Integer salesModel) {
		this.salesModel = salesModel;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getTotalBusinessAmount() {
		return totalBusinessAmount;
	}

	public void setTotalBusinessAmount(Double totalBusinessAmount) {
		this.totalBusinessAmount = totalBusinessAmount;
	}

	public Double getTotalDiscountAmount() {
		return totalDiscountAmount;
	}

	public void setTotalDiscountAmount(Double totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	public Double getTotalTrafficAmount() {
		return totalTrafficAmount;
	}

	public void setTotalTrafficAmount(Double totalTrafficAmount) {
		this.totalTrafficAmount = totalTrafficAmount;
	}

	public String getTransferOwnerNo() {
		return transferOwnerNo;
	}

	public void setTransferOwnerNo(String transferOwnerNo) {
		this.transferOwnerNo = transferOwnerNo;
	}

	public Float getTravelTaxAmount() {
		return travelTaxAmount;
	}

	public void setTravelTaxAmount(Float travelTaxAmount) {
		this.travelTaxAmount = travelTaxAmount;
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

	public Float getUsedYear() {
		return usedYear;
	}

	public void setUsedYear(Float usedYear) {
		this.usedYear = usedYear;
	}

	public Integer getVehicleRelation() {
		return vehicleRelation;
	}

	public void setVehicleRelation(Integer vehicleRelation) {
		this.vehicleRelation = vehicleRelation;
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

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toXMLString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}