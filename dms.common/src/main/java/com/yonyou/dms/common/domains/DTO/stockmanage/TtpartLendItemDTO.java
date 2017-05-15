package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.List;
import java.util.Map;

public class TtpartLendItemDTO {
	private Double itemId;
	private String lendNo;
	private String storageCode;
	private String storagePositionCode;
	private String partBatchNo;
	private String partNo;
	private String partName;
	private String unitCode;
	private Double outQuantity;
	private Double writeOffQuantity;
	private Double costPrice;
	private Double costAmount;
	private Double outPrice;
	private Double outAmount;
	private Double partItemId;
	private Double otherPartCostPrice;
	private Double otherPartCostAmount;
	private List<Map> partProfitItemList;// 表格数据

	public List<Map> getPartProfitItemList() {
		return partProfitItemList;
	}

	public void setPartProfitItemList(List<Map> partProfitItemList) {
		this.partProfitItemList = partProfitItemList;
	}

	public Double getItemId() {
		return itemId;
	}

	public void setItemId(Double itemId) {
		this.itemId = itemId;
	}

	public String getLendNo() {
		return lendNo;
	}

	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
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

	public String getPartBatchNo() {
		return partBatchNo;
	}

	public void setPartBatchNo(String partBatchNo) {
		this.partBatchNo = partBatchNo;
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

	public Double getOutQuantity() {
		return outQuantity;
	}

	public void setOutQuantity(Double outQuantity) {
		this.outQuantity = outQuantity;
	}

	public Double getWriteOffQuantity() {
		return writeOffQuantity;
	}

	public void setWriteOffQuantity(Double writeOffQuantity) {
		this.writeOffQuantity = writeOffQuantity;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	public Double getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
	}

	public Double getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(Double outAmount) {
		this.outAmount = outAmount;
	}

	public Double getPartItemId() {
		return partItemId;
	}

	public void setPartItemId(Double partItemId) {
		this.partItemId = partItemId;
	}

	public Double getOtherPartCostPrice() {
		return otherPartCostPrice;
	}

	public void setOtherPartCostPrice(Double otherPartCostPrice) {
		this.otherPartCostPrice = otherPartCostPrice;
	}

	public Double getOtherPartCostAmount() {
		return otherPartCostAmount;
	}

	public void setOtherPartCostAmount(Double otherPartCostAmount) {
		this.otherPartCostAmount = otherPartCostAmount;
	}
}
