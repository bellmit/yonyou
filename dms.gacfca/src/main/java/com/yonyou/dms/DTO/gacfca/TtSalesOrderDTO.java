package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 
* @ClassName: TtSalesOrderDTO 
* @Description: 销售订单数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午4:28:44 
*
 */
public class TtSalesOrderDTO {
	
	private String dealerCode; //char(8)
	private String soNo;
	private String customerName;
	private Integer otherPayOff;
	private String salesNo;
	private Integer vehiclePurpose;
	private String remark;
	private Integer deliveryMode;
	private Date confirmedDate;
	private Date completeTime;
	private Long updateBy;
	private Long dispatchedBy;
	private String otherAmountObject;
	private String assessedLicense;
	private Date submitTime;
	private Double directivePrice;
	private String customerNo;
	private String intentSoNo;
	private Long returnInBy;
	private String recommendCustomerName;
	private Date contractDate;
	private Integer isFirsetSummit;
	private Double plateSum;
	private Integer abortingFlag;
	private Integer dKey;
	private Long confirmedBy;
	private Long abortingBy;
	private String relatedOrderNo;
	private Date balanceCloseTime;
	private String serviceNo;
	private String storagePositionCode;
	private Integer submitStatus;
	private Double assessedPrice;
	private Integer invoiceMode;
	private Double orderArrearageAmount;
	private String permutedDesc;
	private Integer payMode;
	private Double orderDeratedSum;
	private Date ddcnUpdateDate;
	private Integer businessType;
	private String contactorName;
	private Date createDate;
	private String vehicleDesc;
	private Date abortedDate;
	private Date updateDate;
	private Double orderSum;
	private Long abortedBy;
	private Long createBy;
	private String deliveryAddress;
	private Double conArrearageAmount;
	private String consignerName;
	private Double insuranceSum;
	private String entityCode;
	private String phone;
	private Double upholsterSum;
	private Date balanceTime;
	private String consigneeName;
	private Integer soStatus;
	private String ownerNo;
	private Long sheetCreatedBy;
	private Long intentId;
	private Integer isPermuted;
	private Double loanSum;
	private Date invoiceDate;
	private String lockUser;
	private Double offsetAmount;
	private Double installmentAmount;
	private Integer province;
	private Date returnInDate;
	private Double presentSum;
	private Long auditedBy;
	private String certificateNo;
	private Double garnitureSum;
	private Integer isOppStockIn;
	private String permutedVin;
	private Double generalManagerPrice;
	private Double otherServiceSum;
	private Double orderReceivableSum;
	private Double conReceivableSum;
	private String license;
	private Long soldBy;
	private String ownerName;
	private Integer isLocalUse;
	private Date dispatchedDate;
	private Double otherAmount;
	private Integer completeTag;
	private String abortingReason;//订单取消原因
	private Integer oemDealStatus;
	private String discountModeCode;
	private Integer ctCode;
	private String roNo;
	private String obligatedOperator;
	private Long recommendCardId;
	private Integer ver;
	private Date lastSubmitAuditDate;
	private Integer city;
	private Double conPayedAmount;
	private String applyNo;
	private Integer oemTag;
	private Double invoiceAmount;
	private String vin;
	private Integer isUpload;
	private String productCode;
	private Double discountRate;
	private Date contractMaturity;
	private Integer customerType;
	private Long stockOutBy;
	private Double salesConsultantPrice;
	private Integer allocatingType;
	private Double commissionBalance;
	private String consignerCode;
	private Integer isSpeediness;
	private Integer isCloseRo;
	private Double penaltyAmount;
	private String storageCode;
	private String finishUser;
	private Double vehiclePrice;
	private Double managerPrice;
	private Integer district;
	private Date deliveringDate;
	private String colorCode;
	private Integer orderSort;
	private String address;
	private String oldSoNo;
	private Double orderPayedAmount;
	private Integer dealStatus;
	private Date abortingDate;//订单取消时间
	private Double contractEarnest;
	private String loanOrg;
	private Integer lossesPayOff;
	private Integer installmentNumber;
	private Integer invoiceTag;
	private Double taxSum;
	private Double preInvoiceAmount;
	private Integer isAssessed;
	private Long ownedBy;
	private Double consignedSum;
	private Date stockOutDate;
	private Date sheetCreateDate;
	private String consigneeCode;
	private String contractNo;
	private Long reAuditedBy;
	private Integer payOff;
	private Date permutedDate;//置换日期
	//add by huyu 2016-2-17
	private Date enteringDate;//保留订单日期
	//end
	
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getOtherPayOff() {
		return otherPayOff;
	}
	public void setOtherPayOff(Integer otherPayOff) {
		this.otherPayOff = otherPayOff;
	}
	public String getSalesNo() {
		return salesNo;
	}
	public void setSalesNo(String salesNo) {
		this.salesNo = salesNo;
	}
	public Integer getVehiclePurpose() {
		return vehiclePurpose;
	}
	public void setVehiclePurpose(Integer vehiclePurpose) {
		this.vehiclePurpose = vehiclePurpose;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDeliveryMode() {
		return deliveryMode;
	}
	public void setDeliveryMode(Integer deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	public Date getConfirmedDate() {
		return confirmedDate;
	}
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Long getDispatchedBy() {
		return dispatchedBy;
	}
	public void setDispatchedBy(Long dispatchedBy) {
		this.dispatchedBy = dispatchedBy;
	}
	public String getOtherAmountObject() {
		return otherAmountObject;
	}
	public void setOtherAmountObject(String otherAmountObject) {
		this.otherAmountObject = otherAmountObject;
	}
	public String getAssessedLicense() {
		return assessedLicense;
	}
	public void setAssessedLicense(String assessedLicense) {
		this.assessedLicense = assessedLicense;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Double getDirectivePrice() {
		return directivePrice;
	}
	public void setDirectivePrice(Double directivePrice) {
		this.directivePrice = directivePrice;
	}
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	public String getIntentSoNo() {
		return intentSoNo;
	}
	public void setIntentSoNo(String intentSoNo) {
		this.intentSoNo = intentSoNo;
	}
	public Long getReturnInBy() {
		return returnInBy;
	}
	public void setReturnInBy(Long returnInBy) {
		this.returnInBy = returnInBy;
	}
	public String getRecommendCustomerName() {
		return recommendCustomerName;
	}
	public void setRecommendCustomerName(String recommendCustomerName) {
		this.recommendCustomerName = recommendCustomerName;
	}
	public Date getContractDate() {
		return contractDate;
	}
	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}
	public Integer getIsFirsetSummit() {
		return isFirsetSummit;
	}
	public void setIsFirsetSummit(Integer isFirsetSummit) {
		this.isFirsetSummit = isFirsetSummit;
	}
	public Double getPlateSum() {
		return plateSum;
	}
	public void setPlateSum(Double plateSum) {
		this.plateSum = plateSum;
	}
	public Integer getAbortingFlag() {
		return abortingFlag;
	}
	public void setAbortingFlag(Integer abortingFlag) {
		this.abortingFlag = abortingFlag;
	}
	public Integer getdKey() {
		return dKey;
	}
	public void setdKey(Integer dKey) {
		this.dKey = dKey;
	}
	public Long getConfirmedBy() {
		return confirmedBy;
	}
	public void setConfirmedBy(Long confirmedBy) {
		this.confirmedBy = confirmedBy;
	}
	public Long getAbortingBy() {
		return abortingBy;
	}
	public void setAbortingBy(Long abortingBy) {
		this.abortingBy = abortingBy;
	}
	public String getRelatedOrderNo() {
		return relatedOrderNo;
	}
	public void setRelatedOrderNo(String relatedOrderNo) {
		this.relatedOrderNo = relatedOrderNo;
	}
	public Date getBalanceCloseTime() {
		return balanceCloseTime;
	}
	public void setBalanceCloseTime(Date balanceCloseTime) {
		this.balanceCloseTime = balanceCloseTime;
	}
	public String getServiceNo() {
		return serviceNo;
	}
	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}
	public String getStoragePositionCode() {
		return storagePositionCode;
	}
	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}
	public Integer getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(Integer submitStatus) {
		this.submitStatus = submitStatus;
	}
	public Double getAssessedPrice() {
		return assessedPrice;
	}
	public void setAssessedPrice(Double assessedPrice) {
		this.assessedPrice = assessedPrice;
	}
	public Integer getInvoiceMode() {
		return invoiceMode;
	}
	public void setInvoiceMode(Integer invoiceMode) {
		this.invoiceMode = invoiceMode;
	}
	public Double getOrderArrearageAmount() {
		return orderArrearageAmount;
	}
	public void setOrderArrearageAmount(Double orderArrearageAmount) {
		this.orderArrearageAmount = orderArrearageAmount;
	}
	public String getPermutedDesc() {
		return permutedDesc;
	}
	public void setPermutedDesc(String permutedDesc) {
		this.permutedDesc = permutedDesc;
	}
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public Double getOrderDeratedSum() {
		return orderDeratedSum;
	}
	public void setOrderDeratedSum(Double orderDeratedSum) {
		this.orderDeratedSum = orderDeratedSum;
	}
	public Date getDdcnUpdateDate() {
		return ddcnUpdateDate;
	}
	public void setDdcnUpdateDate(Date ddcnUpdateDate) {
		this.ddcnUpdateDate = ddcnUpdateDate;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	public String getContactorName() {
		return contactorName;
	}
	public void setContactorName(String contactorName) {
		this.contactorName = contactorName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getVehicleDesc() {
		return vehicleDesc;
	}
	public void setVehicleDesc(String vehicleDesc) {
		this.vehicleDesc = vehicleDesc;
	}
	public Date getAbortedDate() {
		return abortedDate;
	}
	public void setAbortedDate(Date abortedDate) {
		this.abortedDate = abortedDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Double getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(Double orderSum) {
		this.orderSum = orderSum;
	}
	public Long getAbortedBy() {
		return abortedBy;
	}
	public void setAbortedBy(Long abortedBy) {
		this.abortedBy = abortedBy;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public Double getConArrearageAmount() {
		return conArrearageAmount;
	}
	public void setConArrearageAmount(Double conArrearageAmount) {
		this.conArrearageAmount = conArrearageAmount;
	}
	public String getConsignerName() {
		return consignerName;
	}
	public void setConsignerName(String consignerName) {
		this.consignerName = consignerName;
	}
	public Double getInsuranceSum() {
		return insuranceSum;
	}
	public void setInsuranceSum(Double insuranceSum) {
		this.insuranceSum = insuranceSum;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getUpholsterSum() {
		return upholsterSum;
	}
	public void setUpholsterSum(Double upholsterSum) {
		this.upholsterSum = upholsterSum;
	}
	public Date getBalanceTime() {
		return balanceTime;
	}
	public void setBalanceTime(Date balanceTime) {
		this.balanceTime = balanceTime;
	}
	public String getConsigneeName() {
		return consigneeName;
	}
	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public String getOwnerNo() {
		return ownerNo;
	}
	public void setOwnerNo(String ownerNo) {
		this.ownerNo = ownerNo;
	}
	public Long getSheetCreatedBy() {
		return sheetCreatedBy;
	}
	public void setSheetCreatedBy(Long sheetCreatedBy) {
		this.sheetCreatedBy = sheetCreatedBy;
	}
	public Long getIntentId() {
		return intentId;
	}
	public void setIntentId(Long intentId) {
		this.intentId = intentId;
	}
	public Integer getIsPermuted() {
		return isPermuted;
	}
	public void setIsPermuted(Integer isPermuted) {
		this.isPermuted = isPermuted;
	}
	public Double getLoanSum() {
		return loanSum;
	}
	public void setLoanSum(Double loanSum) {
		this.loanSum = loanSum;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public Double getOffsetAmount() {
		return offsetAmount;
	}
	public void setOffsetAmount(Double offsetAmount) {
		this.offsetAmount = offsetAmount;
	}
	public Double getInstallmentAmount() {
		return installmentAmount;
	}
	public void setInstallmentAmount(Double installmentAmount) {
		this.installmentAmount = installmentAmount;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Date getReturnInDate() {
		return returnInDate;
	}
	public void setReturnInDate(Date returnInDate) {
		this.returnInDate = returnInDate;
	}
	public Double getPresentSum() {
		return presentSum;
	}
	public void setPresentSum(Double presentSum) {
		this.presentSum = presentSum;
	}
	public Long getAuditedBy() {
		return auditedBy;
	}
	public void setAuditedBy(Long auditedBy) {
		this.auditedBy = auditedBy;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public Double getGarnitureSum() {
		return garnitureSum;
	}
	public void setGarnitureSum(Double garnitureSum) {
		this.garnitureSum = garnitureSum;
	}
	public Integer getIsOppStockIn() {
		return isOppStockIn;
	}
	public void setIsOppStockIn(Integer isOppStockIn) {
		this.isOppStockIn = isOppStockIn;
	}
	public String getPermutedVin() {
		return permutedVin;
	}
	public void setPermutedVin(String permutedVin) {
		this.permutedVin = permutedVin;
	}
	public Double getGeneralManagerPrice() {
		return generalManagerPrice;
	}
	public void setGeneralManagerPrice(Double generalManagerPrice) {
		this.generalManagerPrice = generalManagerPrice;
	}
	public Double getOtherServiceSum() {
		return otherServiceSum;
	}
	public void setOtherServiceSum(Double otherServiceSum) {
		this.otherServiceSum = otherServiceSum;
	}
	public Double getOrderReceivableSum() {
		return orderReceivableSum;
	}
	public void setOrderReceivableSum(Double orderReceivableSum) {
		this.orderReceivableSum = orderReceivableSum;
	}
	public Double getConReceivableSum() {
		return conReceivableSum;
	}
	public void setConReceivableSum(Double conReceivableSum) {
		this.conReceivableSum = conReceivableSum;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Long getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(Long soldBy) {
		this.soldBy = soldBy;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Integer getIsLocalUse() {
		return isLocalUse;
	}
	public void setIsLocalUse(Integer isLocalUse) {
		this.isLocalUse = isLocalUse;
	}
	public Date getDispatchedDate() {
		return dispatchedDate;
	}
	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	public Double getOtherAmount() {
		return otherAmount;
	}
	public void setOtherAmount(Double otherAmount) {
		this.otherAmount = otherAmount;
	}
	public Integer getCompleteTag() {
		return completeTag;
	}
	public void setCompleteTag(Integer completeTag) {
		this.completeTag = completeTag;
	}
	public String getAbortingReason() {
		return abortingReason;
	}
	public void setAbortingReason(String abortingReason) {
		this.abortingReason = abortingReason;
	}
	public Integer getOemDealStatus() {
		return oemDealStatus;
	}
	public void setOemDealStatus(Integer oemDealStatus) {
		this.oemDealStatus = oemDealStatus;
	}
	public String getDiscountModeCode() {
		return discountModeCode;
	}
	public void setDiscountModeCode(String discountModeCode) {
		this.discountModeCode = discountModeCode;
	}
	public Integer getCtCode() {
		return ctCode;
	}
	public void setCtCode(Integer ctCode) {
		this.ctCode = ctCode;
	}
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public String getObligatedOperator() {
		return obligatedOperator;
	}
	public void setObligatedOperator(String obligatedOperator) {
		this.obligatedOperator = obligatedOperator;
	}
	public Long getRecommendCardId() {
		return recommendCardId;
	}
	public void setRecommendCardId(Long recommendCardId) {
		this.recommendCardId = recommendCardId;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Date getLastSubmitAuditDate() {
		return lastSubmitAuditDate;
	}
	public void setLastSubmitAuditDate(Date lastSubmitAuditDate) {
		this.lastSubmitAuditDate = lastSubmitAuditDate;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Double getConPayedAmount() {
		return conPayedAmount;
	}
	public void setConPayedAmount(Double conPayedAmount) {
		this.conPayedAmount = conPayedAmount;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Integer getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public Date getContractMaturity() {
		return contractMaturity;
	}
	public void setContractMaturity(Date contractMaturity) {
		this.contractMaturity = contractMaturity;
	}
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public Long getStockOutBy() {
		return stockOutBy;
	}
	public void setStockOutBy(Long stockOutBy) {
		this.stockOutBy = stockOutBy;
	}
	public Double getSalesConsultantPrice() {
		return salesConsultantPrice;
	}
	public void setSalesConsultantPrice(Double salesConsultantPrice) {
		this.salesConsultantPrice = salesConsultantPrice;
	}
	public Integer getAllocatingType() {
		return allocatingType;
	}
	public void setAllocatingType(Integer allocatingType) {
		this.allocatingType = allocatingType;
	}
	public Double getCommissionBalance() {
		return commissionBalance;
	}
	public void setCommissionBalance(Double commissionBalance) {
		this.commissionBalance = commissionBalance;
	}
	public String getConsignerCode() {
		return consignerCode;
	}
	public void setConsignerCode(String consignerCode) {
		this.consignerCode = consignerCode;
	}
	public Integer getIsSpeediness() {
		return isSpeediness;
	}
	public void setIsSpeediness(Integer isSpeediness) {
		this.isSpeediness = isSpeediness;
	}
	public Integer getIsCloseRo() {
		return isCloseRo;
	}
	public void setIsCloseRo(Integer isCloseRo) {
		this.isCloseRo = isCloseRo;
	}
	public Double getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(Double penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getFinishUser() {
		return finishUser;
	}
	public void setFinishUser(String finishUser) {
		this.finishUser = finishUser;
	}
	public Double getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	public Double getManagerPrice() {
		return managerPrice;
	}
	public void setManagerPrice(Double managerPrice) {
		this.managerPrice = managerPrice;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Date getDeliveringDate() {
		return deliveringDate;
	}
	public void setDeliveringDate(Date deliveringDate) {
		this.deliveringDate = deliveringDate;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public Integer getOrderSort() {
		return orderSort;
	}
	public void setOrderSort(Integer orderSort) {
		this.orderSort = orderSort;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOldSoNo() {
		return oldSoNo;
	}
	public void setOldSoNo(String oldSoNo) {
		this.oldSoNo = oldSoNo;
	}
	public Double getOrderPayedAmount() {
		return orderPayedAmount;
	}
	public void setOrderPayedAmount(Double orderPayedAmount) {
		this.orderPayedAmount = orderPayedAmount;
	}
	public Integer getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Date getAbortingDate() {
		return abortingDate;
	}
	public void setAbortingDate(Date abortingDate) {
		this.abortingDate = abortingDate;
	}
	public Double getContractEarnest() {
		return contractEarnest;
	}
	public void setContractEarnest(Double contractEarnest) {
		this.contractEarnest = contractEarnest;
	}
	public String getLoanOrg() {
		return loanOrg;
	}
	public void setLoanOrg(String loanOrg) {
		this.loanOrg = loanOrg;
	}
	public Integer getLossesPayOff() {
		return lossesPayOff;
	}
	public void setLossesPayOff(Integer lossesPayOff) {
		this.lossesPayOff = lossesPayOff;
	}
	public Integer getInstallmentNumber() {
		return installmentNumber;
	}
	public void setInstallmentNumber(Integer installmentNumber) {
		this.installmentNumber = installmentNumber;
	}
	public Integer getInvoiceTag() {
		return invoiceTag;
	}
	public void setInvoiceTag(Integer invoiceTag) {
		this.invoiceTag = invoiceTag;
	}
	public Double getTaxSum() {
		return taxSum;
	}
	public void setTaxSum(Double taxSum) {
		this.taxSum = taxSum;
	}
	public Double getPreInvoiceAmount() {
		return preInvoiceAmount;
	}
	public void setPreInvoiceAmount(Double preInvoiceAmount) {
		this.preInvoiceAmount = preInvoiceAmount;
	}
	public Integer getIsAssessed() {
		return isAssessed;
	}
	public void setIsAssessed(Integer isAssessed) {
		this.isAssessed = isAssessed;
	}
	public Long getOwnedBy() {
		return ownedBy;
	}
	public void setOwnedBy(Long ownedBy) {
		this.ownedBy = ownedBy;
	}
	public Double getConsignedSum() {
		return consignedSum;
	}
	public void setConsignedSum(Double consignedSum) {
		this.consignedSum = consignedSum;
	}
	public Date getStockOutDate() {
		return stockOutDate;
	}
	public void setStockOutDate(Date stockOutDate) {
		this.stockOutDate = stockOutDate;
	}
	public Date getSheetCreateDate() {
		return sheetCreateDate;
	}
	public void setSheetCreateDate(Date sheetCreateDate) {
		this.sheetCreateDate = sheetCreateDate;
	}
	public String getConsigneeCode() {
		return consigneeCode;
	}
	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Long getReAuditedBy() {
		return reAuditedBy;
	}
	public void setReAuditedBy(Long reAuditedBy) {
		this.reAuditedBy = reAuditedBy;
	}
	public Integer getPayOff() {
		return payOff;
	}
	public void setPayOff(Integer payOff) {
		this.payOff = payOff;
	}
	public Date getPermutedDate() {
		return permutedDate;
	}
	public void setPermutedDate(Date permutedDate) {
		this.permutedDate = permutedDate;
	}
	public Date getEnteringDate() {
		return enteringDate;
	}
	public void setEnteringDate(Date enteringDate) {
		this.enteringDate = enteringDate;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	
	

}
