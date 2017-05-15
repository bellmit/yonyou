package com.yonyou.dms.DTO.gacfca;

/**
 * 调拨入库单下发接口
 * @author luoyang
 *
 */
public class SEDMS068Dto {
	
	private String dealerCode;
	private String entityCode;//入库经销商代码
	private String allocateInNo;//调拨入库单号
	private String providerName;//供应商名称（默认:购买经销商名称(全称)）
	private String partNo;//配件代码
	private String partName;//配件名称
	private String unitCode;//单位
	private String inQuantity;//入库数量
	private String inPrice;//入库不含税单价（上报单价）
	private String inAmount;//入库不含税金额
	private String outWarehousNo;
	
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
	public String getAllocateInNo() {
		return allocateInNo;
	}
	public void setAllocateInNo(String allocateInNo) {
		this.allocateInNo = allocateInNo;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
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
	public String getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(String inQuantity) {
		this.inQuantity = inQuantity;
	}
	public String getInPrice() {
		return inPrice;
	}
	public void setInPrice(String inPrice) {
		this.inPrice = inPrice;
	}
	public String getInAmount() {
		return inAmount;
	}
	public void setInAmount(String inAmount) {
		this.inAmount = inAmount;
	}
	public String getOutWarehousNo() {
		return outWarehousNo;
	}
	public void setOutWarehousNo(String outWarehousNo) {
		this.outWarehousNo = outWarehousNo;
	}
	
	

}
