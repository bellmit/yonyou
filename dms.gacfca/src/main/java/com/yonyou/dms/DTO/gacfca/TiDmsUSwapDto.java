package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 更新客户信息（置换需求）DMS更新
 */
public class TiDmsUSwapDto {


	private String uniquenessID;// DMS客户唯一ID
	private Integer FCAID;// 售中APP的客户ID
	private String ownBrandID;
	private String ownModelID;// 现有车型
	private String ownCarStyleID;// 现有车款
	private String VINCode;// 现有的车辆VIN
	private Date licenceIssueDate;// 上牌时间
	private Integer travlledDistance;// 里程数
	private Integer isEstimated;// 是否评估
	private Integer estimatedPrice;// 评估金额
	private String estimatedOne;// 评估报告1
	private String estimatedTwo;// 评估报告2
	private String driveLicense;// 旧车行驶证
	private String dealerUserID;
	private String ownCarColor;
	private Date UpdateDate;// 更新时间
	private String entityCode;
	private String dealerCode;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getUniquenessID() {
		return uniquenessID;
	}

	public void setUniquenessID(String uniquenessID) {
		this.uniquenessID = uniquenessID;
	}

	public Integer getFCAID() {
		return FCAID;
	}

	public void setFCAID(Integer fCAID) {
		FCAID = fCAID;
	}

	public String getOwnModelID() {
		return ownModelID;
	}

	public void setOwnModelID(String ownModelID) {
		this.ownModelID = ownModelID;
	}

	public String getOwnCarStyleID() {
		return ownCarStyleID;
	}

	public void setOwnCarStyleID(String ownCarStyleID) {
		this.ownCarStyleID = ownCarStyleID;
	}

	public String getVINCode() {
		return VINCode;
	}

	public void setVINCode(String vINCode) {
		VINCode = vINCode;
	}

	public Date getLicenceIssueDate() {
		return licenceIssueDate;
	}

	public void setLicenceIssueDate(Date licenceIssueDate) {
		this.licenceIssueDate = licenceIssueDate;
	}

	public Integer getTravlledDistance() {
		return travlledDistance;
	}

	public void setTravlledDistance(Integer travlledDistance) {
		this.travlledDistance = travlledDistance;
	}

	public Integer getIsEstimated() {
		return isEstimated;
	}

	public void setIsEstimated(Integer isEstimated) {
		this.isEstimated = isEstimated;
	}

	public Integer getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Integer estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public String getEstimatedOne() {
		return estimatedOne;
	}

	public void setEstimatedOne(String estimatedOne) {
		this.estimatedOne = estimatedOne;
	}

	public String getEstimatedTwo() {
		return estimatedTwo;
	}

	public void setEstimatedTwo(String estimatedTwo) {
		this.estimatedTwo = estimatedTwo;
	}

	public String getDriveLicense() {
		return driveLicense;
	}

	public void setDriveLicense(String driveLicense) {
		this.driveLicense = driveLicense;
	}

	public Date getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(Date updateDate) {
		UpdateDate = updateDate;
	}

	// public String toXMLString() {
	// return VOUtil.vo2Xml(this);
	// }
	public String getDealerUserID() {
		return dealerUserID;
	}

	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
	}

	public String getOwnBrandID() {
		return ownBrandID;
	}

	public void setOwnBrandID(String ownBrandID) {
		this.ownBrandID = ownBrandID;
	}

	public String getOwnCarColor() {
		return ownCarColor;
	}

	public void setOwnCarColor(String ownCarColor) {
		this.ownCarColor = ownCarColor;
	}

}
