package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.LinkedList;

public class RepairOrderDTO {
	private String roNo; 
	private Integer isClaim;
    private String dealerCode;
	private Integer roType;
	private String repairTypeCode;

	private String repairTypeName;
	private String serviceAdvisor;
	private String serviceAdvisorAss;
	private Integer roStatus;

	private Date roCreateDate;
	private String vin;
	private Double inMileage; 
	private String deliverer;
	private String delivererPhone;
	private Date forBalanceTime;
	private Float labourPrice;
	private Double labourAmount; 
	private Double repairPartAmount;
	private Double additemAmount;
	private String remark;

	private LinkedList labourVoList; 
	private LinkedList repairPartVoList; 
	private LinkedList addItemVoList; 
	private LinkedList<RoPartSalesDTO> partSalesVoList;
	private LinkedList<BalanceCouponDTO> wachatList;

	private  String customerAddress; 
	private  Date faultDate;
	private  Integer betweenDays; 
	private  Date repairDate; 
	private  String packageCode;
	private  String packageName;
	private Date finishDate;
	private String bookingOrderNo;;

	private Double outMileage;
	
	//start add by wujinbiao
	private String roTroubleDesc;
	private String customerDesc;
	//end
	//start add by wujinbiao
	private Integer isOrder;
	//end
	
	//add by huyu start
	private String license;
	private Date deliveryDate;
	private String series;
	private String model;//新增车型代码  
	private Double mileage;//行驶里程
	private String refund;
	private LinkedList<BalancePayobjDTO> balancePayobjVoList;
	private LinkedList<BalanceAccountsDTO> balanceAccountVoList;
	private LinkedList<InsProposalCardListDTO> insProposalCardListVOList;
	private String newOrderType;
	private String newRepairType;
	//end
	private String reservationId;//微信预约单
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public Integer getIsClaim() {
		return isClaim;
	}
	public void setIsClaim(Integer isClaim) {
		this.isClaim = isClaim;
	}
	public Integer getRoType() {
		return roType;
	}
	public void setRoType(Integer roType) {
		this.roType = roType;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public String getRepairTypeName() {
		return repairTypeName;
	}
	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}
	public String getServiceAdvisor() {
		return serviceAdvisor;
	}
	public void setServiceAdvisor(String serviceAdvisor) {
		this.serviceAdvisor = serviceAdvisor;
	}
	public String getServiceAdvisorAss() {
		return serviceAdvisorAss;
	}
	public void setServiceAdvisorAss(String serviceAdvisorAss) {
		this.serviceAdvisorAss = serviceAdvisorAss;
	}
	public Integer getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}
	public Date getRoCreateDate() {
		return roCreateDate;
	}
	public void setRoCreateDate(Date roCreateDate) {
		this.roCreateDate = roCreateDate;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public Double getInMileage() {
		return inMileage;
	}
	public void setInMileage(Double inMileage) {
		this.inMileage = inMileage;
	}
	public String getDeliverer() {
		return deliverer;
	}
	public void setDeliverer(String deliverer) {
		this.deliverer = deliverer;
	}
	public String getDelivererPhone() {
		return delivererPhone;
	}
	public void setDelivererPhone(String delivererPhone) {
		this.delivererPhone = delivererPhone;
	}
	public Date getForBalanceTime() {
		return forBalanceTime;
	}
	public void setForBalanceTime(Date forBalanceTime) {
		this.forBalanceTime = forBalanceTime;
	}
	public Float getLabourPrice() {
		return labourPrice;
	}
	public void setLabourPrice(Float labourPrice) {
		this.labourPrice = labourPrice;
	}
	public Double getLabourAmount() {
		return labourAmount;
	}
	public void setLabourAmount(Double labourAmount) {
		this.labourAmount = labourAmount;
	}
	public Double getRepairPartAmount() {
		return repairPartAmount;
	}
	public void setRepairPartAmount(Double repairPartAmount) {
		this.repairPartAmount = repairPartAmount;
	}
	public Double getAdditemAmount() {
		return additemAmount;
	}
	public void setAdditemAmount(Double additemAmount) {
		this.additemAmount = additemAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public LinkedList getLabourVoList() {
		return labourVoList;
	}
	public void setLabourVoList(LinkedList labourVoList) {
		this.labourVoList = labourVoList;
	}
	public LinkedList getRepairPartVoList() {
		return repairPartVoList;
	}
	public void setRepairPartVoList(LinkedList repairPartVoList) {
		this.repairPartVoList = repairPartVoList;
	}
	public LinkedList getAddItemVoList() {
		return addItemVoList;
	}
	public void setAddItemVoList(LinkedList addItemVoList) {
		this.addItemVoList = addItemVoList;
	}
	public LinkedList<RoPartSalesDTO> getPartSalesVoList() {
		return partSalesVoList;
	}
	public void setPartSalesVoList(LinkedList<RoPartSalesDTO> partSalesVoList) {
		this.partSalesVoList = partSalesVoList;
	}
	public LinkedList<BalanceCouponDTO> getWachatList() {
		return wachatList;
	}
	public void setWachatList(LinkedList<BalanceCouponDTO> wachatList) {
		this.wachatList = wachatList;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public Date getFaultDate() {
		return faultDate;
	}
	public void setFaultDate(Date faultDate) {
		this.faultDate = faultDate;
	}
	public Integer getBetweenDays() {
		return betweenDays;
	}
	public void setBetweenDays(Integer betweenDays) {
		this.betweenDays = betweenDays;
	}
	public Date getRepairDate() {
		return repairDate;
	}
	public void setRepairDate(Date repairDate) {
		this.repairDate = repairDate;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getBookingOrderNo() {
		return bookingOrderNo;
	}
	public void setBookingOrderNo(String bookingOrderNo) {
		this.bookingOrderNo = bookingOrderNo;
	}
	public Double getOutMileage() {
		return outMileage;
	}
	public void setOutMileage(Double outMileage) {
		this.outMileage = outMileage;
	}
	public String getRoTroubleDesc() {
		return roTroubleDesc;
	}
	public void setRoTroubleDesc(String roTroubleDesc) {
		this.roTroubleDesc = roTroubleDesc;
	}
	public String getCustomerDesc() {
		return customerDesc;
	}
	public void setCustomerDesc(String customerDesc) {
		this.customerDesc = customerDesc;
	}
	public Integer getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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
	public Double getMileage() {
		return mileage;
	}
	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
	public String getRefund() {
		return refund;
	}
	public void setRefund(String refund) {
		this.refund = refund;
	}
	public LinkedList<BalancePayobjDTO> getBalancePayobjVoList() {
		return balancePayobjVoList;
	}
	public void setBalancePayobjVoList(LinkedList<BalancePayobjDTO> balancePayobjVoList) {
		this.balancePayobjVoList = balancePayobjVoList;
	}
	public LinkedList<BalanceAccountsDTO> getBalanceAccountVoList() {
		return balanceAccountVoList;
	}
	public void setBalanceAccountVoList(LinkedList<BalanceAccountsDTO> balanceAccountVoList) {
		this.balanceAccountVoList = balanceAccountVoList;
	}
	public LinkedList<InsProposalCardListDTO> getInsProposalCardListVOList() {
		return insProposalCardListVOList;
	}
	public void setInsProposalCardListVOList(LinkedList<InsProposalCardListDTO> insProposalCardListVOList) {
		this.insProposalCardListVOList = insProposalCardListVOList;
	}
	public String getNewOrderType() {
		return newOrderType;
	}
	public void setNewOrderType(String newOrderType) {
		this.newOrderType = newOrderType;
	}
	public String getNewRepairType() {
		return newRepairType;
	}
	public void setNewRepairType(String newRepairType) {
		this.newRepairType = newRepairType;
	}
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

}
