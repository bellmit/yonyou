package com.yonyou.dms.manage.domains.DTO.basedata;

import java.math.BigDecimal;
import java.util.Date;

import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
 * 经销商详细信息DTO
 * @author ZhaoZ
 *
 */
public class DealerDetailedinfoDTO {

	 private Integer acuraGhasType;
	
	 private String companyShortname;
	
	 private Long dealerId;

	 private String dealerCode_;
	 
	 private String dealerCodeA_;
	 
	 private String dealerShortname;

	 private String dealerName;
	 
	 private String dealerType;
	 
	 private String orgName;
	 
	 private Long companyId;

	 private String marketing;
	 
	 private Long province;

	 private Long city;
	 
	 private Integer status;
	 	 
	 private Date foundDate;// 经销商成立日期
	 
	 private String phone;

	 private String linkMan;
	 
	 private Long tmGroupId;

	 private String groupShotname;
	 
	 private String linkManMobile;
	 
	 private String faxNo;
	 
	 private String taxesNo;
	 
	 private String erpCode;
	 
	 private String beginBank;
	 
	 private String bankCode;
	 
	 private String address;
	 
	 private String remark;
	 
	 private String threePackType;
	 
	 private String discount;
	 
	 private Integer range;
	 
	 private String ccCode;
	 
	 private String companyLevel;
	 
	 private String[] licensedBrands;
	 
	 private String brands;
	 
	 private Date endDate;
	 
	 private String lineNumber;
	 
	 private String integratedSocial;
	 
	 private String isEc;
	 
	 private String isK4;
	 
	 private String investorsEmial;
	 
	 private String isFiat;
	 
	 private String isCjd;

	 private String openTime;

	 private String companyPhone;
	
	 private String openRange;
	 
	 private String groupIds;
	 
	 private Long orgId;
	 
	 private Long orgIdA;
	
	 @Required
	 @Email
	 private String email;

	 @Required
	 @ZipCode
	 private String zipCode;
	 private Integer maintainability;
	 private String legalRepresentative;
	 private String companyNo;
	 private String buildStatus;
	 private Date completionDate;
	 private Date startDate;
	 private Integer showCars;
	 private Integer serviceNum;
	 private Integer machineRepair;
	 private Integer sheetSpray;
	 private Integer preflightNum;
	 private Integer reservedNum;
	 private Integer stopCar;
	 private String land;
	 private String landNature;
	 private Double partsArea;
	 private String rate;
	 private Integer registeredCapital;
	 private String companyInvestors;
	 private String investorsTel;
	
	 private BigDecimal groupId;
	 
	 /*
	  * 上传附件
	  */
	 //附件
	 private String dmsFileIds;
	 //附件种类  1 经销店面照片  2  营业执照复印件 3  组织机构代码证复印件  4  公司章程复印件  5  税务登记证复印件  6  财务报表-年报  7  授权合同
	 private Integer uploadType;
	 
	 
	 
	 /*
	  * 经销商展厅
	  */
	 private Long dealerRoomId;
	 private String rxPhone;	 
	 private String officePhone;
	 private String fax;
	 private String buninessTime;
	 private String[] brand;
	 private Integer showcarNum;
	 private Integer stoppingNum;
	 private String landPro;
	 private String landBuyOfRent;
	 private Integer weixiuNum;
	 private Integer jixiuNum;
	 private Integer banpenNum;
	 private Integer yujianNum;
	 private Integer yuliuNum;
	 private Double aftersaleArea;
	 private Double dangerArea;
	 private Integer showroomArea;
	 private Double showroomWidth;
	 private String linkPhone;
	 
	 
	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerShortname() {
		return dealerShortname;
	}

	public void setDealerShortname(String dealerShortname) {
		this.dealerShortname = dealerShortname;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId( Long companyId) {
		this.companyId = companyId;
	}

	public String getMarketing() {
		return marketing;
	}

	public void setMarketing(String marketing) {
		this.marketing = marketing;
	}


	public Long getProvince() {
		return province;
	}

	public void setProvince(Long province) {
		this.province = province;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}


	public Long getTmGroupId() {
		return tmGroupId;
	}

	public void setTmGroupId(Long tmGroupId) {
		this.tmGroupId = tmGroupId;
	}

	public String getGroupShotname() {
		return groupShotname;
	}

	public void setGroupShotname(String groupShotname) {
		this.groupShotname = groupShotname;
	}

	public String getLinkManMobile() {
		return linkManMobile;
	}

	public void setLinkManMobile(String linkManMobile) {
		this.linkManMobile = linkManMobile;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getTaxesNo() {
		return taxesNo;
	}

	public void setTaxesNo(String taxesNo) {
		this.taxesNo = taxesNo;
	}

	public String getErpCode() {
		return erpCode;
	}

	public void setErpCode(String erpCode) {
		this.erpCode = erpCode;
	}

	public String getBeginBank() {
		return beginBank;
	}

	public void setBeginBank(String beginBank) {
		this.beginBank = beginBank;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getThreePackType() {
		return threePackType;
	}

	public void setThreePackType(String threePackType) {
		this.threePackType = threePackType;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public Integer getRange() {
		return range;
	}

	public void setRange(Integer range) {
		this.range = range;
	}

	public String getCcCode() {
		return ccCode;
	}

	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}

	
	public String[] getLicensedBrands() {
		return licensedBrands;
	}

	public void setLicensedBrands(String[] licensedBrands) {
		this.licensedBrands = licensedBrands;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getIntegratedSocial() {
		return integratedSocial;
	}

	public void setIntegratedSocial(String integratedSocial) {
		this.integratedSocial = integratedSocial;
	}

	public String getIsEc() {
		return isEc;
	}

	public void setIsEc(String isEc) {
		this.isEc = isEc;
	}

	public String getIsK4() {
		return isK4;
	}

	public void setIsK4(String isK4) {
		this.isK4 = isK4;
	}

	public String getIsFiat() {
		return isFiat;
	}

	public void setIsFiat(String isFiat) {
		this.isFiat = isFiat;
	}

	public String getIsCjd() {
		return isCjd;
	}

	public void setIsCjd(String isCjd) {
		this.isCjd = isCjd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCompanyShortname() {
		return companyShortname;
	}

	public void setCompanyShortname(String companyShortname) {
		this.companyShortname = companyShortname;
	}

	public Integer getAcuraGhasType() {
		return acuraGhasType;
	}

	public void setAcuraGhasType(Integer acuraGhasType) {
		this.acuraGhasType = acuraGhasType;
	}

	
	
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getBuildStatus() {
		return buildStatus;
	}

	public void setBuildStatus(String buildStatus) {
		this.buildStatus = buildStatus;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public Integer getShowroomArea() {
		return showroomArea;
	}

	public void setShowroomArea(Integer showroomArea) {
		this.showroomArea = showroomArea;
	}

	public Double getShowroomWidth() {
		return showroomWidth;
	}

	public void setShowroomWidth(Double showroomWidth) {
		this.showroomWidth = showroomWidth;
	}

	public Integer getShowCars() {
		return showCars;
	}

	public void setShowCars(Integer showCars) {
		this.showCars = showCars;
	}

	public Integer getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
	}

	public Integer getMachineRepair() {
		return machineRepair;
	}

	public void setMachineRepair(Integer machineRepair) {
		this.machineRepair = machineRepair;
	}

	public Integer getSheetSpray() {
		return sheetSpray;
	}

	public void setSheetSpray(Integer sheetSpray) {
		this.sheetSpray = sheetSpray;
	}

	public Integer getPreflightNum() {
		return preflightNum;
	}

	public void setPreflightNum(Integer preflightNum) {
		this.preflightNum = preflightNum;
	}

	public Integer getReservedNum() {
		return reservedNum;
	}

	public void setReservedNum(Integer reservedNum) {
		this.reservedNum = reservedNum;
	}

	public Integer getStopCar() {
		return stopCar;
	}

	public void setStopCar(Integer stopCar) {
		this.stopCar = stopCar;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public String getLandNature() {
		return landNature;
	}

	public void setLandNature(String landNature) {
		this.landNature = landNature;
	}

	public Double getPartsArea() {
		return partsArea;
	}

	public void setPartsArea(Double partsArea) {
		this.partsArea = partsArea;
	}

	public Double getDangerArea() {
		return dangerArea;
	}

	public void setDangerArea(Double dangerArea) {
		this.dangerArea = dangerArea;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Integer getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(Integer registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getCompanyInvestors() {
		return companyInvestors;
	}

	public void setCompanyInvestors(String companyInvestors) {
		this.companyInvestors = companyInvestors;
	}

	public String getInvestorsTel() {
		return investorsTel;
	}

	public void setInvestorsTel(String investorsTel) {
		this.investorsTel = investorsTel;
	}


	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getOpenRange() {
		return openRange;
	}

	public void setOpenRange(String openRange) {
		this.openRange = openRange;
	}

	public BigDecimal getGroupId() {
		return groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public String getBrands() {
		return brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getInvestorsEmial() {
		return investorsEmial;
	}

	public void setInvestorsEmial(String investorsEmial) {
		this.investorsEmial = investorsEmial;
	}

	public Integer getMaintainability() {
		return maintainability;
	}

	public void setMaintainability(Integer maintainability) {
		this.maintainability = maintainability;
	}

	public String getDealerCode_() {
		return dealerCode_;
	}

	public void setDealerCode_(String dealerCode_) {
		this.dealerCode_ = dealerCode_;
	}

	public String getDealerCodeA_() {
		return dealerCodeA_;
	}

	public void setDealerCodeA_(String dealerCodeA_) {
		this.dealerCodeA_ = dealerCodeA_;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getDmsFileIds() {
		return dmsFileIds;
	}

	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}

	public Integer getUploadType() {
		return uploadType;
	}

	public void setUploadType(Integer uploadType) {
		this.uploadType = uploadType;
	}
	
	public Long getDealerRoomId() {
		return dealerRoomId;
	}

	public void setDealerRoomId(Long dealerRoomId) {
		this.dealerRoomId = dealerRoomId;
	}

	public String getRxPhone() {
		return rxPhone;
	}

	public void setRxPhone(String rxPhone) {
		this.rxPhone = rxPhone;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getBuninessTime() {
		return buninessTime;
	}

	public void setBuninessTime(String buninessTime) {
		this.buninessTime = buninessTime;
	}

	public String[] getBrand() {
		return brand;
	}

	public void setBrand(String[] brand) {
		this.brand = brand;
	}

	public Integer getShowcarNum() {
		return showcarNum;
	}

	public void setShowcarNum(Integer showcarNum) {
		this.showcarNum = showcarNum;
	}

	public Integer getStoppingNum() {
		return stoppingNum;
	}

	public void setStoppingNum(Integer stoppingNum) {
		this.stoppingNum = stoppingNum;
	}

	public String getLandPro() {
		return landPro;
	}

	public void setLandPro(String landPro) {
		this.landPro = landPro;
	}

	public String getLandBuyOfRent() {
		return landBuyOfRent;
	}

	public void setLandBuyOfRent(String landBuyOfRent) {
		this.landBuyOfRent = landBuyOfRent;
	}

	public Integer getWeixiuNum() {
		return weixiuNum;
	}

	public void setWeixiuNum(Integer weixiuNum) {
		this.weixiuNum = weixiuNum;
	}

	public Integer getJixiuNum() {
		return jixiuNum;
	}

	public void setJixiuNum(Integer jixiuNum) {
		this.jixiuNum = jixiuNum;
	}

	public Integer getBanpenNum() {
		return banpenNum;
	}

	public void setBanpenNum(Integer banpenNum) {
		this.banpenNum = banpenNum;
	}

	public Integer getYujianNum() {
		return yujianNum;
	}

	public void setYujianNum(Integer yujianNum) {
		this.yujianNum = yujianNum;
	}

	public Integer getYuliuNum() {
		return yuliuNum;
	}

	public void setYuliuNum(Integer yuliuNum) {
		this.yuliuNum = yuliuNum;
	}

	public Double getAftersaleArea() {
		return aftersaleArea;
	}

	public void setAftersaleArea(Double aftersaleArea) {
		this.aftersaleArea = aftersaleArea;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public Long getOrgIdA() {
		return orgIdA;
	}

	public void setOrgIdA(Long orgIdA) {
		this.orgIdA = orgIdA;
	}
	
	 
	 
}
