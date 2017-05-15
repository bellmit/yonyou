package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiDmsNSwapDto {


	private Date licencelssueDate;//上牌时间
	private String estimatedTwo;//评估报告2
	private String estimatedOne;//评估报告1
	private String uniquenessId;//DMS客户唯一ID
	private Long FCAID;//售中客户唯一标识
	private String driveLicense;//旧车行驶证
	private String dealerCode;//经销商代码
	private String ownBrandId;//现有品牌
	private String ownModelId;//现有车型
	private String ownCarStyleId;//现有车款
	private String ownCarColor;//现有车色
	private Long isEstimated;//是否评估
	private String vinCode;//现有的车辆VIN
	private Long travlledDistance;//里程数
	private Long estimatedPrice;//评估金额
	private Date createDate;//创建时间
	private String dealerUserId;//销售人员ID
	private String entityCode;
	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getDriveLicense() {
		return driveLicense;
	}

	public void setDriveLicense(String driveLicense) {
		this.driveLicense = driveLicense;
	}

	public String getEstimatedOne() {
		return estimatedOne;
	}

	public void setEstimatedOne(String estimatedOne) {
		this.estimatedOne = estimatedOne;
	}

	public Long getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Long estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public String getEstimatedTwo() {
		return estimatedTwo;
	}

	public void setEstimatedTwo(String estimatedTwo) {
		this.estimatedTwo = estimatedTwo;
	}

	public Long getIsEstimated() {
		return isEstimated;
	}

	public void setIsEstimated(Long isEstimated) {
		this.isEstimated = isEstimated;
	}

	public Date getLicencelssueDate() {
		return licencelssueDate;
	}

	public void setLicencelssueDate(Date licencelssueDate) {
		this.licencelssueDate = licencelssueDate;
	}

	public String getOwnBrandId() {
		return ownBrandId;
	}

	public void setOwnBrandId(String ownBrandId) {
		this.ownBrandId = ownBrandId;
	}

	public String getOwnCarColor() {
		return ownCarColor;
	}

	public void setOwnCarColor(String ownCarColor) {
		this.ownCarColor = ownCarColor;
	}

	public String getOwnCarStyleId() {
		return ownCarStyleId;
	}

	public void setOwnCarStyleId(String ownCarStyleId) {
		this.ownCarStyleId = ownCarStyleId;
	}

	public String getOwnModelId() {
		return ownModelId;
	}

	public void setOwnModelId(String ownModelId) {
		this.ownModelId = ownModelId;
	}

	public Long getTravlledDistance() {
		return travlledDistance;
	}

	public void setTravlledDistance(Long travlledDistance) {
		this.travlledDistance = travlledDistance;
	}

	public String getUniquenessId() {
		return uniquenessId;
	}

	public void setUniquenessId(String uniquenessId) {
		this.uniquenessId = uniquenessId;
	}

	public String getVinCode() {
		return vinCode;
	}

	public void setVinCode(String vinCode) {
		this.vinCode = vinCode;
	}

//	public String toXMLString() {
//		return VOUtil.vo2Xml(this);
//	}

	public Long getFCAID() {
		return FCAID;
	}

	public void setFCAID(Long fcaid) {
		FCAID = fcaid;
	}



}
