package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.Date;

public class GathringMaintainDTO {

	private String calcUsableAmount;// 账户可用余额

	public String getCalcUsableAmount() {
		return calcUsableAmount;
	}

	public void setCalcUsableAmount(String calcUsableAmount) {
		this.calcUsableAmount = calcUsableAmount;
	}

	private Double orderPayedAmount;

	private Double conReceivableSum;

	private Double conPayedAmount;

	private Double conArrearageAmount;

	private Integer payOff;

	private Double commissionbalance;

	private Double arragementSum;

	/**
	 * @return the arragementSum
	 */
	public Double getArragementSum() {
		return arragementSum;
	}

	/**
	 * @param arragementSum
	 *            the arragementSum to set
	 */
	public void setArragementSum(Double arragementSum) {
		this.arragementSum = arragementSum;
	}

	private Integer lossesPayOff;

	private Double orderArrearageAmount;

	private Double orderPayedSum;

	private Double conPayedSum;

	private Double usableAmount;

	private Double gatherdSum;

	private Double unWriteoffSum;

	private Integer ver;

	public Double getCommissionbalance() {
		return commissionbalance;
	}

	public void setCommissionbalance(Double commissionbalance) {
		this.commissionbalance = commissionbalance;
	}

	public Double getOrderPayedAmount() {
		return orderPayedAmount;
	}

	public void setOrderPayedAmount(Double orderPayedAmount) {
		this.orderPayedAmount = orderPayedAmount;
	}

	public Double getConReceivableSum() {
		return conReceivableSum;
	}

	public void setConReceivableSum(Double conReceivableSum) {
		this.conReceivableSum = conReceivableSum;
	}

	public Double getConPayedAmount() {
		return conPayedAmount;
	}

	public void setConPayedAmount(Double conPayedAmount) {
		this.conPayedAmount = conPayedAmount;
	}

	public Double getConArrearageAmount() {
		return conArrearageAmount;
	}

	public void setConArrearageAmount(Double conArrearageAmount) {
		this.conArrearageAmount = conArrearageAmount;
	}

	public Integer getPayOff() {
		return payOff;
	}

	public void setPayOff(Integer payOff) {
		this.payOff = payOff;
	}

	public Integer getLossesPayOff() {
		return lossesPayOff;
	}

	public void setLossesPayOff(Integer lossesPayOff) {
		this.lossesPayOff = lossesPayOff;
	}

	public Double getOrderArrearageAmount() {
		return orderArrearageAmount;
	}

	public void setOrderArrearageAmount(Double orderArrearageAmount) {
		this.orderArrearageAmount = orderArrearageAmount;
	}

	public Double getOrderPayedSum() {
		return orderPayedSum;
	}

	public void setOrderPayedSum(Double orderPayedSum) {
		this.orderPayedSum = orderPayedSum;
	}

	public Double getConPayedSum() {
		return conPayedSum;
	}

	public void setConPayedSum(Double conPayedSum) {
		this.conPayedSum = conPayedSum;
	}

	public Double getUsableAmount() {
		return usableAmount;
	}

	public void setUsableAmount(Double usableAmount) {
		this.usableAmount = usableAmount;
	}

	public Double getGatherdSum() {
		return gatherdSum;
	}

	public void setGatherdSum(Double gatherdSum) {
		this.gatherdSum = gatherdSum;
	}

	public Double getUnWriteoffSum() {
		return unWriteoffSum;
	}

	public void setUnWriteoffSum(Double unWriteoffSum) {
		this.unWriteoffSum = unWriteoffSum;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	private String soNo1;

	/**
	 * @return the soNo1
	 */
	public String getSoNo1() {
		return soNo1;
	}

	/**
	 * @param soNo1
	 *            the soNo1 to set
	 */
	public void setSoNo1(String soNo1) {
		this.soNo1 = soNo1;
	}

	private String customerNo;

	private String soNo;

	private String receiveNo;

	private String payTypeCode;

	private Double receiveAmount;

	private String billNo;

	private Integer gatheringType;

	private Date receiveDate;

	private String transactor;

	private Double orderReceivableSumt;

	private Double useStore;

	// private Long recorder;

	private Integer writeoffTag;

	private Long writeoffBy;

	private Date writeoffDate;

	private String remark;

	private String fbiaoz;

	private Double fdorderReturnPrice;

	private Double fdweiyuePrice;

	private Integer isblance;

	public Integer getIsblance() {
		return isblance;
	}

	public void setIsblance(Integer isblance) {
		this.isblance = isblance;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public String getReceiveNo() {
		return receiveNo;
	}

	public void setReceiveNo(String receiveNo) {
		this.receiveNo = receiveNo;
	}

	public String getPayTypeCode() {
		return payTypeCode;
	}

	public void setPayTypeCode(String payTypeCode) {
		this.payTypeCode = payTypeCode;
	}

	public Double getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Double receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public Integer getGatheringType() {
		return gatheringType;
	}

	public void setGatheringType(Integer gatheringType) {
		this.gatheringType = gatheringType;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getTransactor() {
		return transactor;
	}

	public void setTransactor(String transactor) {
		this.transactor = transactor;
	}

	public Double getOrderReceivableSumt() {
		return orderReceivableSumt;
	}

	public void setOrderReceivableSumt(Double orderReceivableSumt) {
		this.orderReceivableSumt = orderReceivableSumt;
	}

	public Double getUseStore() {
		return useStore;
	}

	public void setUseStore(Double useStore) {
		this.useStore = useStore;
	}

	// public Long getRecorder() {
	// return recorder;
	// }
	//
	//
	// public void setRecorder(Long recorder) {
	// this.recorder = recorder;
	// }

	public Integer getWriteoffTag() {
		return writeoffTag;
	}

	public void setWriteoffTag(Integer writeoffTag) {
		this.writeoffTag = writeoffTag;
	}

	public Long getWriteoffBy() {
		return writeoffBy;
	}

	public void setWriteoffBy(Long writeoffBy) {
		this.writeoffBy = writeoffBy;
	}

	public Date getWriteoffDate() {
		return writeoffDate;
	}

	public void setWriteoffDate(Date writeoffDate) {
		this.writeoffDate = writeoffDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFbiaoz() {
		return fbiaoz;
	}

	public void setFbiaoz(String fbiaoz) {
		this.fbiaoz = fbiaoz;
	}

	public Double getFdorderReturnPrice() {
		return fdorderReturnPrice;
	}

	public void setFdorderReturnPrice(Double fdorderReturnPrice) {
		this.fdorderReturnPrice = fdorderReturnPrice;
	}

	public Double getFdweiyuePrice() {
		return fdweiyuePrice;
	}

	public void setFdweiyuePrice(Double fdweiyuePrice) {
		this.fdweiyuePrice = fdweiyuePrice;
	}

}
