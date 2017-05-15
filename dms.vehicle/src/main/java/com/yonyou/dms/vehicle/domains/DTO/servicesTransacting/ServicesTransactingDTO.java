package com.yonyou.dms.vehicle.domains.DTO.servicesTransacting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务项目办理DTO
 * @author Benzc
 * @date 2017年4月15日
 */
public class ServicesTransactingDTO {
	//订单服务项目
	private Integer isCompleted;
	private Date completedDate;
	//保险办理结果
	private String insuranceTypeCode;
	private String insurationCode;
	private Date insuranceBeginDate;
	private Date insuranceEndDate;
	private String insuranceBillNo;
	private Integer reInsure;
	private Double actualExpending;
	private String completedBy;
	private Date completedDate2;
	private String insuranceIntroMan;
	private Date recordDate;
	private String recorder;
	private Integer isInsureRemind;
	private Date insuranceBuyDate;
	private Integer isSelfCompanyInsurance;
	private String remark;
	//税费办理结果
	private String taxSort;
	private Double actualExpending3;
	private String completedBy3;
	private Date completedDate3;
	private String recorder3;
	private Date recordDate3;
	private String remark3;
	//牌照办理结果
	private String license;
	private Date licenseDate;
	private Double actualExpending4;
	private String completedBy4;
	private Date completedDate4;
	private String recorder4;
	private Date recordDate4;
	private String remark4;
	
	//表明集合
	@SuppressWarnings("rawtypes")
	private List<Map> orderService;//订单服务项目
	@SuppressWarnings("rawtypes")
	private List<Map> insurancManage;//保险办理结果
	@SuppressWarnings("rawtypes")
	private List<Map> taxManage;//税费办理结果
	@SuppressWarnings("rawtypes")
	private List<Map> licenseManage;//牌照办理结果
	public Integer getIsCompleted() {
		return isCompleted;
	}
	public void setIsCompleted(Integer isCompleted) {
		this.isCompleted = isCompleted;
	}
	public Date getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}
	public String getInsuranceTypeCode() {
		return insuranceTypeCode;
	}
	public void setInsuranceTypeCode(String insuranceTypeCode) {
		this.insuranceTypeCode = insuranceTypeCode;
	}
	public String getInsurationCode() {
		return insurationCode;
	}
	public void setInsurationCode(String insurationCode) {
		this.insurationCode = insurationCode;
	}
	public Date getInsuranceBeginDate() {
		return insuranceBeginDate;
	}
	public void setInsuranceBeginDate(Date insuranceBeginDate) {
		this.insuranceBeginDate = insuranceBeginDate;
	}
	public Date getInsuranceEndDate() {
		return insuranceEndDate;
	}
	public void setInsuranceEndDate(Date insuranceEndDate) {
		this.insuranceEndDate = insuranceEndDate;
	}
	public String getInsuranceBillNo() {
		return insuranceBillNo;
	}
	public void setInsuranceBillNo(String insuranceBillNo) {
		this.insuranceBillNo = insuranceBillNo;
	}
	public Integer getReInsure() {
		return reInsure;
	}
	public void setReInsure(Integer reInsure) {
		this.reInsure = reInsure;
	}
	public Double getActualExpending() {
		return actualExpending;
	}
	public void setActualExpending(Double actualExpending) {
		this.actualExpending = actualExpending;
	}
	public String getCompletedBy() {
		return completedBy;
	}
	public void setCompletedBy(String completedBy) {
		this.completedBy = completedBy;
	}
	public Date getCompletedDate2() {
		return completedDate2;
	}
	public void setCompletedDate2(Date completedDate2) {
		this.completedDate2 = completedDate2;
	}
	public String getInsuranceIntroMan() {
		return insuranceIntroMan;
	}
	public void setInsuranceIntroMan(String insuranceIntroMan) {
		this.insuranceIntroMan = insuranceIntroMan;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public Integer getIsInsureRemind() {
		return isInsureRemind;
	}
	public void setIsInsureRemind(Integer isInsureRemind) {
		this.isInsureRemind = isInsureRemind;
	}
	public Date getInsuranceBuyDate() {
		return insuranceBuyDate;
	}
	public void setInsuranceBuyDate(Date insuranceBuyDate) {
		this.insuranceBuyDate = insuranceBuyDate;
	}
	public Integer getIsSelfCompanyInsurance() {
		return isSelfCompanyInsurance;
	}
	public void setIsSelfCompanyInsurance(Integer isSelfCompanyInsurance) {
		this.isSelfCompanyInsurance = isSelfCompanyInsurance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTaxSort() {
		return taxSort;
	}
	public void setTaxSort(String taxSort) {
		this.taxSort = taxSort;
	}
	public Double getActualExpending3() {
		return actualExpending3;
	}
	public void setActualExpending3(Double actualExpending3) {
		this.actualExpending3 = actualExpending3;
	}
	public String getCompletedBy3() {
		return completedBy3;
	}
	public void setCompletedBy3(String completedBy3) {
		this.completedBy3 = completedBy3;
	}
	public Date getCompletedDate3() {
		return completedDate3;
	}
	public void setCompletedDate3(Date completedDate3) {
		this.completedDate3 = completedDate3;
	}
	public String getRecorder3() {
		return recorder3;
	}
	public void setRecorder3(String recorder3) {
		this.recorder3 = recorder3;
	}
	public Date getRecordDate3() {
		return recordDate3;
	}
	public void setRecordDate3(Date recordDate3) {
		this.recordDate3 = recordDate3;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Date getLicenseDate() {
		return licenseDate;
	}
	public void setLicenseDate(Date licenseDate) {
		this.licenseDate = licenseDate;
	}
	public Double getActualExpending4() {
		return actualExpending4;
	}
	public void setActualExpending4(Double actualExpending4) {
		this.actualExpending4 = actualExpending4;
	}
	public String getCompletedBy4() {
		return completedBy4;
	}
	public void setCompletedBy4(String completedBy4) {
		this.completedBy4 = completedBy4;
	}
	public Date getCompletedDate4() {
		return completedDate4;
	}
	public void setCompletedDate4(Date completedDate4) {
		this.completedDate4 = completedDate4;
	}
	public String getRecorder4() {
		return recorder4;
	}
	public void setRecorder4(String recorder4) {
		this.recorder4 = recorder4;
	}
	public Date getRecordDate4() {
		return recordDate4;
	}
	public void setRecordDate4(Date recordDate4) {
		this.recordDate4 = recordDate4;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getOrderService() {
		return orderService;
	}
	@SuppressWarnings("rawtypes")
	public void setOrderService(List<Map> orderService) {
		this.orderService = orderService;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getInsurancManage() {
		return insurancManage;
	}
	@SuppressWarnings("rawtypes")
	public void setInsurancManage(List<Map> insurancManage) {
		this.insurancManage = insurancManage;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getTaxManage() {
		return taxManage;
	}
	@SuppressWarnings("rawtypes")
	public void setTaxManage(List<Map> taxManage) {
		this.taxManage = taxManage;
	}
	@SuppressWarnings("rawtypes")
	public List<Map> getLicenseManage() {
		return licenseManage;
	}
	@SuppressWarnings("rawtypes")
	public void setLicenseManage(List<Map> licenseManage) {
		this.licenseManage = licenseManage;
	}
	
	
	
}
