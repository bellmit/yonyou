package com.yonyou.dms.common.domains.DTO.customer;

import java.util.Date;

public class SuggestComplaintDTO {

	private String dealerCode;
	
	private String complaintNo;
	private String complaintName;//投诉人姓名
	private String complaintCorp;//COMPLAINT_CORP
	private String complaintPhone;//投诉人电话
	private String complaintType;//投诉类型
	private Date complaintDate;//投诉日期
	private Date complaintEndDate;
	private String complaintMobile;//投诉人手机
	private Integer complaintGender;//投诉人性别
	private Integer complaintMainType;//投诉大类
	private Integer complaintSubType;//投诉小类
	private String crcComplaintNo;
	private Integer isGcr;
	private Integer crcComplaintSource;//投诉来源
	private Date closeDate;
	private String consultant;
	private String complaintSummary;
	private String complaintReason;
	private String resolvent;
	private Integer isValid;
	private Integer dealStatus;//处理状态
	private Integer closeStatus;
	private Double complaintResult;
	private String principal;//接待人
	private Double complaintSerious;
	private String department;
	private String beComplaintEmp;
	private Double priority;
	private String roNo;
	private Date roCreateDate;
	private Double roType;
	private String techician;
	private String license;
	private String vin;
	private String ownerName;
	private String linkAddress;
	private String delivererPhone;
	private String delivererMobile;
	private String deliverer;
	private Double inMileage;
	private Double ownerProperty;
	private String serviceAdvisor;
	private Double repeatDealTimes;
	private Double isIntimeDeal;
	private String failingReason;
	private Double complaintOrigin;
	private String sendDocPerson;
	private String sendDocTel;
	private String sendDocFax;
	private Date sendDocDate;
	private Date ascReceiveDate;
	private String engineNo;
	private Date sendDate;
	private Date downTimeStamp;
	private Double isUpload;
	private Date butCarDate;
	private String saleShop;
	private String surveyContent;
	private String surreyMan;
	private Date surveyDate;
	private String recallContent;
	private String isaccept;
	private String recallMan;
	private Date recallDate;
	private String recordMan;
	private Date recordTime;
	private String disposeState;
	private String estavlishProject;
	private String checkSuggest;
	private Double complaintID;
	private Double complaintStatus;
	private String receptionst;

	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getComplaintNo() {
		return complaintNo;
	}
	public void setComplaintNo(String complaintNo) {
		this.complaintNo = complaintNo;
	}
	public String getComplaintName() {
		return complaintName;
	}
	public void setComplaintName(String complaintName) {
		this.complaintName = complaintName;
	}
	public String getComplaintCorp() {
		return complaintCorp;
	}
	public void setComplaintCorp(String complaintCorp) {
		this.complaintCorp = complaintCorp;
	}
	public String getComplaintPhone() {
		return complaintPhone;
	}
	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public Date getComplaintDate() {
		return complaintDate;
	}
	public void setComplaintDate(Date complaintDate) {
		this.complaintDate = complaintDate;
	}
	public Date getComplaintEndDate() {
		return complaintEndDate;
	}
	public void setComplaintEndDate(Date complaintEndDate) {
		this.complaintEndDate = complaintEndDate;
	}
	public String getComplaintMobile() {
		return complaintMobile;
	}
	public void setComplaintMobile(String complaintMobile) {
		this.complaintMobile = complaintMobile;
	}
	public Integer getComplaintGender() {
		return complaintGender;
	}
	public void setComplaintGender(Integer complaintGender) {
		this.complaintGender = complaintGender;
	}
	public Integer getComplaintMainType() {
		return complaintMainType;
	}
	public void setComplaintMainType(Integer complaintMainType) {
		this.complaintMainType = complaintMainType;
	}
	public Integer getComplaintSubType() {
		return complaintSubType;
	}
	public void setComplaintSubType(Integer complaintSubType) {
		this.complaintSubType = complaintSubType;
	}
	public String getCrcComplaintNo() {
		return crcComplaintNo;
	}
	public void setCrcComplaintNo(String crcComplaintNo) {
		this.crcComplaintNo = crcComplaintNo;
	}
	public Integer getIsGcr() {
		return isGcr;
	}
	public void setIsGcr(Integer isGcr) {
		this.isGcr = isGcr;
	}
	public Integer getCrcComplaintSource() {
		return crcComplaintSource;
	}
	public void setCrcComplaintSource(Integer crcComplaintSource) {
		this.crcComplaintSource = crcComplaintSource;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public String getConsultant() {
		return consultant;
	}
	public void setConsultant(String consultant) {
		this.consultant = consultant;
	}
	public String getComplaintSummary() {
		return complaintSummary;
	}
	public void setComplaintSummary(String complaintSummary) {
		this.complaintSummary = complaintSummary;
	}
	public String getComplaintReason() {
		return complaintReason;
	}
	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}
	public String getResolvent() {
		return resolvent;
	}
	public void setResolvent(String resolvent) {
		this.resolvent = resolvent;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	public Integer getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
	public Integer getCloseStatus() {
		return closeStatus;
	}
	public void setCloseStatus(Integer closeStatus) {
		this.closeStatus = closeStatus;
	}
	public Double getComplaintResult() {
		return complaintResult;
	}
	public void setComplaintResult(Double complaintResult) {
		this.complaintResult = complaintResult;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public Double getComplaintSerious() {
		return complaintSerious;
	}
	public void setComplaintSerious(Double complaintSerious) {
		this.complaintSerious = complaintSerious;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getBeComplaintEmp() {
		return beComplaintEmp;
	}
	public void setBeComplaintEmp(String beComplaintEmp) {
		this.beComplaintEmp = beComplaintEmp;
	}
	public Double getPriority() {
		return priority;
	}
	public void setPriority(Double priority) {
		this.priority = priority;
	}
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public Date getRoCreateDate() {
		return roCreateDate;
	}
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}
	public Double getRoType() {
		return roType;
	}
	public void setRoType(Double roType) {
		this.roType = roType;
	}
	public String getTechician() {
		return techician;
	}
	public void setTechician(String techician) {
		this.techician = techician;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getLinkAddress() {
		return linkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	public String getDelivererPhone() {
		return delivererPhone;
	}
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}
	public String getDelivererMobile() {
		return delivererMobile;
	}
	public void setDelivererMobile(String delivererMobile) {
		this.delivererMobile = delivererMobile;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public Double getInMileage() {
		return inMileage;
	}
	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}
	public Double getOwnerProperty() {
		return ownerProperty;
	}
	public void setOwnerProperty(Double ownerProperty) {
		this.ownerProperty = ownerProperty;
	}
	public String getServiceAdvisor() {
		return serviceAdvisor;
	}
	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}
	public Double getRepeatDealTimes() {
		return repeatDealTimes;
	}
	public void setRepeatDealTimes(Double repeatDealTimes) {
		this.repeatDealTimes = repeatDealTimes;
	}
	public Double getIsIntimeDeal() {
		return isIntimeDeal;
	}
	public void setIsIntimeDeal(Double isIntimeDeal) {
		this.isIntimeDeal = isIntimeDeal;
	}
	public String getFailingReason() {
		return failingReason;
	}
	public void setFailingReason(String failingReason) {
		this.failingReason = failingReason;
	}
	public Double getComplaintOrigin() {
		return complaintOrigin;
	}
	public void setComplaintOrigin(Double complaintOrigin) {
		this.complaintOrigin = complaintOrigin;
	}
	public String getSendDocPerson() {
		return sendDocPerson;
	}
	public void setSendDocPerson(String sendDocPerson) {
		this.sendDocPerson = sendDocPerson;
	}
	public String getSendDocTel() {
		return sendDocTel;
	}
	public void setSendDocTel(String sendDocTel) {
		this.sendDocTel = sendDocTel;
	}
	public String getSendDocFax() {
		return sendDocFax;
	}
	public void setSendDocFax(String sendDocFax) {
		this.sendDocFax = sendDocFax;
	}
	public Date getSendDocDate() {
		return sendDocDate;
	}
	public void setSendDocDate(Date sendDocDate) {
		this.sendDocDate = sendDocDate;
	}
	public Date getAscReceiveDate() {
		return ascReceiveDate;
	}
	public void setAscReceiveDate(Date ascReceiveDate) {
		this.ascReceiveDate = ascReceiveDate;
	}
	public String getEngineNo() {
		return engineNo;
	}
	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Date getDownTimeStamp() {
		return downTimeStamp;
	}
	public void setDownTimeStamp(Date downTimeStamp) {
		this.downTimeStamp = downTimeStamp;
	}
	public Double getIsUpload() {
		return isUpload;
	}
	public void setIsUpload(Double isUpload) {
		this.isUpload = isUpload;
	}
	public Date getButCarDate() {
		return butCarDate;
	}
	public void setButCarDate(Date butCarDate) {
		this.butCarDate = butCarDate;
	}
	public String getSaleShop() {
		return saleShop;
	}
	public void setSaleShop(String saleShop) {
		this.saleShop = saleShop;
	}
	public String getSurveyContent() {
		return surveyContent;
	}
	public void setSurveyContent(String surveyContent) {
		this.surveyContent = surveyContent;
	}
	public String getSurreyMan() {
		return surreyMan;
	}
	public void setSurreyMan(String surreyMan) {
		this.surreyMan = surreyMan;
	}
	public Date getSurveyDate() {
		return surveyDate;
	}
	public void setSurveyDate(Date surveyDate) {
		this.surveyDate = surveyDate;
	}
	public String getRecallContent() {
		return recallContent;
	}
	public void setRecallContent(String recallContent) {
		this.recallContent = recallContent;
	}
	public String getIsaccept() {
		return isaccept;
	}
	public void setIsaccept(String isaccept) {
		this.isaccept = isaccept;
	}
	public String getRecallMan() {
		return recallMan;
	}
	public void setRecallMan(String recallMan) {
		this.recallMan = recallMan;
	}
	public Date getRecallDate() {
		return recallDate;
	}
	public void setRecallDate(Date recallDate) {
		this.recallDate = recallDate;
	}
	public String getRecordMan() {
		return recordMan;
	}
	public void setRecordMan(String recordMan) {
		this.recordMan = recordMan;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public String getDisposeState() {
		return disposeState;
	}
	public void setDisposeState(String disposeState) {
		this.disposeState = disposeState;
	}
	public String getEstavlishProject() {
		return estavlishProject;
	}
	public void setEstavlishProject(String estavlishProject) {
		this.estavlishProject = estavlishProject;
	}
	public String getCheckSuggest() {
		return checkSuggest;
	}
	public void setCheckSuggest(String checkSuggest) {
		this.checkSuggest = checkSuggest;
	}
	public Double getComplaintID() {
		return complaintID;
	}
	public void setComplaintID(Double complaintID) {
		this.complaintID = complaintID;
	}
	public Double getComplaintStatus() {
		return complaintStatus;
	}
	public void setComplaintStatus(Double complaintStatus) {
		this.complaintStatus = complaintStatus;
	}
	public String getReceptionst() {
		return receptionst;
	}
	public void setReceptionst(String receptionst) {
		this.receptionst = receptionst;
	}
}
