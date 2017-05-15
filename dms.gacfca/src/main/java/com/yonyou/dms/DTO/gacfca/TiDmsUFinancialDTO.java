package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 更新客户信息（金融报价）DMS更新
 * 
 * @author Administrator
 * @date 2015年6月10日上午10:28:48
 */
public class TiDmsUFinancialDTO {

	private static final long serialVersionUID = 1L;

	private String dealerCode;
	private String uniquenessID;
	private Integer FCAID;
	private Integer buyType;
	private Double carPrice;
	private Double firstPayment;
	private Double loanSum;
	private Integer loanYear;
	private Double loanRate;
	private Double repaymentMonth;
	private Double isPrint;
	private Double roadToll;
	private Double vehiclePurchaseTax;
	private Double vehicleVesselTax;
	private Double licensePlateCost;
	private Double eXWarehouseCost;
	private Integer boutique;
	private Double insuranceSum;
	private Double estimatedPrice;
	private String dealerUserID;
	private Date updateDate;

	public String getDealerUserID() {
		return dealerUserID;
	}

	public void setDealerUserID(String dealerUserID) {
		this.dealerUserID = dealerUserID;
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

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	public Double getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(Double carPrice) {
		this.carPrice = carPrice;
	}

	public Double getFirstPayment() {
		return firstPayment;
	}

	public void setFirstPayment(Double firstPayment) {
		this.firstPayment = firstPayment;
	}

	public Double getLoanSum() {
		return loanSum;
	}

	public void setLoanSum(Double loanSum) {
		this.loanSum = loanSum;
	}

	public Integer getLoanYear() {
		return loanYear;
	}

	public void setLoanYear(Integer loanYear) {
		this.loanYear = loanYear;
	}

	public Double getLoanRate() {
		return loanRate;
	}

	public void setLoanRate(Double loanRate) {
		this.loanRate = loanRate;
	}

	public Double getRepaymentMonth() {
		return repaymentMonth;
	}

	public void setRepaymentMonth(Double repaymentMonth) {
		this.repaymentMonth = repaymentMonth;
	}

	public Double getIsPrint() {
		return isPrint;
	}

	public void setIsPrint(Double isPrint) {
		this.isPrint = isPrint;
	}

	public Double getRoadToll() {
		return roadToll;
	}

	public void setRoadToll(Double roadToll) {
		this.roadToll = roadToll;
	}

	public Double getVehiclePurchaseTax() {
		return vehiclePurchaseTax;
	}

	public void setVehiclePurchaseTax(Double vehiclePurchaseTax) {
		this.vehiclePurchaseTax = vehiclePurchaseTax;
	}

	public Double getVehicleVesselTax() {
		return vehicleVesselTax;
	}

	public void setVehicleVesselTax(Double vehicleVesselTax) {
		this.vehicleVesselTax = vehicleVesselTax;
	}

	public Double getLicensePlateCost() {
		return licensePlateCost;
	}

	public void setLicensePlateCost(Double licensePlateCost) {
		this.licensePlateCost = licensePlateCost;
	}

	public Double geteXWarehouseCost() {
		return eXWarehouseCost;
	}

	public void seteXWarehouseCost(Double eXWarehouseCost) {
		this.eXWarehouseCost = eXWarehouseCost;
	}

	public Integer getBoutique() {
		return boutique;
	}

	public void setBoutique(Integer boutique) {
		this.boutique = boutique;
	}

	public Double getInsuranceSum() {
		return insuranceSum;
	}

	public void setInsuranceSum(Double insuranceSum) {
		this.insuranceSum = insuranceSum;
	}

	public Double getEstimatedPrice() {
		return estimatedPrice;
	}

	public void setEstimatedPrice(Double estimatedPrice) {
		this.estimatedPrice = estimatedPrice;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

}
