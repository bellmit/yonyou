package com.yonyou.dms.DTO.gacfca;

/**
 * 调拨出库单下发接口
 * @author luoyang
 *
 */
public class SEDMS066Dto {
	
	private String dealerCode;
	private String entityCode;//发布经销商代码
	private String allocateOutNo;//调拨出库单号
	private String customerName;//客户名称（默认:购买经销商名称(全称)）
	private String storageCode;//仓库
	private String storagePositionCode;//库位代码
	private String partNo;//配件代码
	private String partName;//配件名称
	private String unitCode;//单位
	private String outgoingQuantity;//出库数量
	private String outgoingPrice;//出库单价
	private String outgoingTotal;//出库金额
	private String reportedCostPrice;//上报时成本单价
	private String reportedCostTotal;//上报时成本金额
	
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
	public String getAllocateOutNo() {
		return allocateOutNo;
	}
	public void setAllocateOutNo(String allocateOutNo) {
		this.allocateOutNo = allocateOutNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getStoragePositionCode() {
		return storagePositionCode;
	}
	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getOutgoingQuantity() {
		return outgoingQuantity;
	}
	public void setOutgoingQuantity(String outgoingQuantity) {
		this.outgoingQuantity = outgoingQuantity;
	}
	public String getOutgoingPrice() {
		return outgoingPrice;
	}
	public void setOutgoingPrice(String outgoingPrice) {
		this.outgoingPrice = outgoingPrice;
	}
	public String getOutgoingTotal() {
		return outgoingTotal;
	}
	public void setOutgoingTotal(String outgoingTotal) {
		this.outgoingTotal = outgoingTotal;
	}
	public String getReportedCostPrice() {
		return reportedCostPrice;
	}
	public void setReportedCostPrice(String reportedCostPrice) {
		this.reportedCostPrice = reportedCostPrice;
	}
	public String getReportedCostTotal() {
		return reportedCostTotal;
	}
	public void setReportedCostTotal(String reportedCostTotal) {
		this.reportedCostTotal = reportedCostTotal;
	}
	
	

}
