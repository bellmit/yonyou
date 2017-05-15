package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class RepairOrderSchemeDTO {
	private String dealerCode;  //经销商代码
	
	private String entityName; //经销商名称

	private String roNo;  //工单号

	private String serviceAdvisor; //开单人
	
	private String serviceAdvisorTel;  //开单人电话
	
	private String vin;
	
	private String license;  //车牌号

	private Date salesDate; //购车日期
	
	private String ownerName;  //车主名称
	
	private String model; //车型
	
	private Double inMileage;  //行驶里程
	
	private Date roCreateDate;//进店日期
	
	private String roTroubleDesc; //维修故障描述
	private String dealerOpinoin; //经销商处理意见	DEALER_OPINOIN
	private String aduitRemark;   //审核结果描述	AUDIT_REMARK

	private Integer schemeStatus; //三包状态
	
	private LinkedList<RoLabourSchemeDTO> labourVoList;

	private LinkedList<RoRepairPartSchemeDTO> repairPartVoList;
	
	public String getDealerOpinoin() {
		return dealerOpinoin;
	}

	public void setDealerOpinoin(String dealerOpinoin) {
		this.dealerOpinoin = dealerOpinoin;
	}

	public String getAduitRemark() {
		return aduitRemark;
	}

	public void setAduitRemark(String aduitRemark) {
		this.aduitRemark = aduitRemark;
	}
	
	public Integer getSchemeStatus() {
		return schemeStatus;
	}

	public void setSchemeStatus(Integer schemeStatus) {
		this.schemeStatus = schemeStatus;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Double getInMileage() {
		return inMileage;
	}

	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}

	public Date getRoCreateDate() {
		return roCreateDate;
	}

	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}

	public String getRoNo() {
		return roNo;
	}

	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}


	public LinkedList<RoLabourSchemeDTO> getLabourVoList() {
		return labourVoList;
	}

	public void setLabourVoList(LinkedList<RoLabourSchemeDTO> labourVoList) {
		this.labourVoList = labourVoList;
	}

	public LinkedList<RoRepairPartSchemeDTO> getRepairPartVoList() {
		return repairPartVoList;
	}

	public void setRepairPartVoList(LinkedList<RoRepairPartSchemeDTO> repairPartVoList) {
		this.repairPartVoList = repairPartVoList;
	}

	public String getServiceAdvisor() {
		return serviceAdvisor;
	}

	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getServiceAdvisorTel() {
		return serviceAdvisorTel;
	}

	public void setServiceAdvisorTel(String serviceAdvisorTel) {
		this.serviceAdvisorTel = serviceAdvisorTel;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}

	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}
}
