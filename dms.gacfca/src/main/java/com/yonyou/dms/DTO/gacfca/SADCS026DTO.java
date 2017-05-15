package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SADCS026DTO {

	private String entityCode;// 经销商代码
	private Double deliverPartQuantity;// 货运单入库配件数量
	private Double deliverPartAmount;// 货运单入库配件金额
	private Double provisPartQuantity;// 临时入库已核销配件数量
	private Double provisPartAmount;// 临时入库已核销配件金额
	private Double verPartQuantity;// 临时入库未核销核销配件数量
	private Double verPartAmount;// 临时入库未核销配件金额
	private Double handlePartQuantity;// 手工入库配件数量
	private Double handlePartAmount;// 手工入库配件金额

	private Double allocatePartQuantity;// 调拨入库配件数量
	private Double allocatePartAmount;// 调拨入库配件金额
	private Double profitPartQuantity;// 报溢入库配件数量
	private Double profitPartAmount;// 报溢入库配件金额
	private Double sumPartQuantity;// 汇总配件数量
	private Double sumPartAmount;// 汇总配件金额
	private Date uploadTimestamp;// 上报时间

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Double getDeliverPartQuantity() {
		return deliverPartQuantity;
	}

	public void setDeliverPartQuantity(Double deliverPartQuantity) {
		this.deliverPartQuantity = deliverPartQuantity;
	}

	public Double getDeliverPartAmount() {
		return deliverPartAmount;
	}

	public void setDeliverPartAmount(Double deliverPartAmount) {
		this.deliverPartAmount = deliverPartAmount;
	}

	public Double getProvisPartQuantity() {
		return provisPartQuantity;
	}

	public void setProvisPartQuantity(Double provisPartQuantity) {
		this.provisPartQuantity = provisPartQuantity;
	}

	public Double getProvisPartAmount() {
		return provisPartAmount;
	}

	public void setProvisPartAmount(Double provisPartAmount) {
		this.provisPartAmount = provisPartAmount;
	}

	public Double getVerPartQuantity() {
		return verPartQuantity;
	}

	public void setVerPartQuantity(Double verPartQuantity) {
		this.verPartQuantity = verPartQuantity;
	}

	public Double getVerPartAmount() {
		return verPartAmount;
	}

	public void setVerPartAmount(Double verPartAmount) {
		this.verPartAmount = verPartAmount;
	}

	public Double getHandlePartQuantity() {
		return handlePartQuantity;
	}

	public void setHandlePartQuantity(Double handlePartQuantity) {
		this.handlePartQuantity = handlePartQuantity;
	}

	public Double getHandlePartAmount() {
		return handlePartAmount;
	}

	public void setHandlePartAmount(Double handlePartAmount) {
		this.handlePartAmount = handlePartAmount;
	}

	public Double getAllocatePartQuantity() {
		return allocatePartQuantity;
	}

	public void setAllocatePartQuantity(Double allocatePartQuantity) {
		this.allocatePartQuantity = allocatePartQuantity;
	}

	public Double getAllocatePartAmount() {
		return allocatePartAmount;
	}

	public void setAllocatePartAmount(Double allocatePartAmount) {
		this.allocatePartAmount = allocatePartAmount;
	}

	public Double getProfitPartQuantity() {
		return profitPartQuantity;
	}

	public void setProfitPartQuantity(Double profitPartQuantity) {
		this.profitPartQuantity = profitPartQuantity;
	}

	public Double getProfitPartAmount() {
		return profitPartAmount;
	}

	public void setProfitPartAmount(Double profitPartAmount) {
		this.profitPartAmount = profitPartAmount;
	}

	public Double getSumPartQuantity() {
		return sumPartQuantity;
	}

	public void setSumPartQuantity(Double sumPartQuantity) {
		this.sumPartQuantity = sumPartQuantity;
	}

	public Double getSumPartAmount() {
		return sumPartAmount;
	}

	public void setSumPartAmount(Double sumPartAmount) {
		this.sumPartAmount = sumPartAmount;
	}

	public Date getUploadTimestamp() {
		return uploadTimestamp;
	}

	public void setUploadTimestamp(Date uploadTimestamp) {
		this.uploadTimestamp = uploadTimestamp;
	}

}
