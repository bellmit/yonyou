package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TiAppNSwapDto {
	private static final long serialVersionUID = 1L;
	
	private String dealerCode;
	
	private Long swapId;
	
	private Long fcaId;
	
	private Date licencelssueDate;
	
	private String estimatedTwo; //评估报告2
	
	private String estimatedOne; //评估报告1
	
	private String driverLicense; //旧车行驶证
		
	private String ownBrandId; //现有品牌
	
	private String ownModelId; //现有车型
	
	private String ownCarStyleId; //现有车款
	
	private Long isEstimated; //是否评估
	
	private String vinCode; //现有车辆VIN
	
	private Long travlledDistance; //里程数
	
	private Long estimeedPrice; //评估金额
	
	private Date createDate; //创建时间
	
	private String ownCarColor; //现有车色
	
	private String dealerUserId; //销售人员ID
	
	private String uniquenessID;
	

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Long getSwapId() {
		return swapId;
	}

	public void setSwapId(Long swapId) {
		this.swapId = swapId;
	}

	public Long getFcaId() {
		return fcaId;
	}

	public void setFcaId(Long fcaId) {
		this.fcaId = fcaId;
	}

	public Date getLicencelssueDate() {
		return licencelssueDate;
	}

	public void setLicencelssueDate(Date licencelssueDate) {
		this.licencelssueDate = licencelssueDate;
	}

	public String getEstimatedTwo() {
		return estimatedTwo;
	}

	public void setEstimatedTwo(String estimatedTwo) {
		this.estimatedTwo = estimatedTwo;
	}

	public String getEstimatedOne() {
		return estimatedOne;
	}

	public void setEstimatedOne(String estimatedOne) {
		this.estimatedOne = estimatedOne;
	}

	public String getDriverLicense() {
		return driverLicense;
	}

	public void setDriverLicense(String driverLicense) {
		this.driverLicense = driverLicense;
	}

	public String getOwnBrandId() {
		return ownBrandId;
	}

	public void setOwnBrandId(String ownBrandId) {
		this.ownBrandId = ownBrandId;
	}

	public String getOwnModelId() {
		return ownModelId;
	}

	public void setOwnModelId(String ownModelId) {
		this.ownModelId = ownModelId;
	}

	public String getOwnCarStyleId() {
		return ownCarStyleId;
	}

	public void setOwnCarStyleId(String ownCarStyleId) {
		this.ownCarStyleId = ownCarStyleId;
	}

	public Long getIsEstimated() {
		return isEstimated;
	}

	public void setIsEstimated(Long isEstimated) {
		this.isEstimated = isEstimated;
	}

	public String getVinCode() {
		return vinCode;
	}

	public void setVinCode(String vinCode) {
		this.vinCode = vinCode;
	}

	public Long getTravlledDistance() {
		return travlledDistance;
	}

	public void setTravlledDistance(Long travlledDistance) {
		this.travlledDistance = travlledDistance;
	}

	public Long getEstimeedPrice() {
		return estimeedPrice;
	}

	public void setEstimeedPrice(Long estimeedPrice) {
		this.estimeedPrice = estimeedPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOwnCarColor() {
		return ownCarColor;
	}

	public void setOwnCarColor(String ownCarColor) {
		this.ownCarColor = ownCarColor;
	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getUniquenessID() {
		return uniquenessID;
	}

	public void setUniquenessID(String uniquenessID) {
		this.uniquenessID = uniquenessID;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
