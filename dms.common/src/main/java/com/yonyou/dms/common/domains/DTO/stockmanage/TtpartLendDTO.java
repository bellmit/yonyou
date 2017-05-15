package com.yonyou.dms.common.domains.DTO.stockmanage;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class TtpartLendDTO {
	
	//借出登记单以及明细
	private String lendNo;
	private String customerCode;
	private String customerName;
	private Double costAmount;
	private Double OUT_AMOUNT;

	private String itemId;

	private String handler;
	private Date sheetCreateDate;
	private Date lendDate;
	private Double isFinished;
	private Date finishedDate;
	private Integer payOff;
	private Integer borrowerTag;
	private String lockUser;
	private Date dxpDate;
	private String soNo;
	private String storageCode;
	private String storagePositionCode;
	private String partBatchNo;
	private String partNo;
	private String unitCode;
	private Double outQuantity;
	private Double writeOffQuantity;
	private Double costPrice;
	private Double partItemId;
	private Double otherPartCostPrice;
	private Double otherPartCostAmount;
	private List<Map> dms_checkout;// 表格数据
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Double getRepaiePartId() {
		return repaiePartId;
	}
	public void setRepaiePartId(Double repaiePartId) {
		this.repaiePartId = repaiePartId;
	}
	public Double getCostAmountAfter_A() {
		return costAmountAfter_A;
	}
	public void setCostAmountAfter_A(Double costAmountAfter_A) {
		this.costAmountAfter_A = costAmountAfter_A;
	}
	public Double getCostAmountAfter_B() {
		return costAmountAfter_B;
	}
	public void setCostAmountAfter_B(Double costAmountAfter_B) {
		this.costAmountAfter_B = costAmountAfter_B;
	}
	public Double getCostAmountBeffore_A() {
		return costAmountBeffore_A;
	}
	public void setCostAmountBeffore_A(Double costAmountBeffore_A) {
		this.costAmountBeffore_A = costAmountBeffore_A;
	}
	public Double getCostAmountBeffore_B() {
		return costAmountBeffore_B;
	}
	public void setCostAmountBeffore_B(Double costAmountBeffore_B) {
		this.costAmountBeffore_B = costAmountBeffore_B;
	}
	private Double repaiePartId;
	private Double costAmountAfter_A;
	private Double costAmountAfter_B;
	private Double costAmountBeffore_A;
	private Double costAmountBeffore_B;
	
	
	
	
	public Double getOUT_AMOUNT() {
		return OUT_AMOUNT;
	}
	public void setOUT_AMOUNT(Double oUT_AMOUNT) {
		OUT_AMOUNT = oUT_AMOUNT;
	}
	public List<Map> getDms_checkout() {
		return dms_checkout;
	}
	public void setDms_checkout(List<Map> dms_checkout) {
		this.dms_checkout = dms_checkout;
	}
	private String dealerCode;
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	private String partName;
	
	private Double outPrice;
	
	public String getLendNo() {
		return lendNo;
	}
	public void setLendNo(String lendNo) {
		this.lendNo = lendNo;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public Date getSheetCreateDate() {
		return sheetCreateDate;
	}
	public void setSheetCreateDate(Date sheetCreateDate) {
		this.sheetCreateDate = sheetCreateDate;
	}
	public Date getLendDate() {
		return lendDate;
	}
	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}
	public Double getIsFinished() {
		return isFinished;
	}
	public void setIsFinished(Double isFinished) {
		this.isFinished = isFinished;
	}
	public Date getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}
	public Integer getPayOff() {
		return payOff;
	}
	public void setPayOff(Integer payOff) {
		this.payOff = payOff;
	}
	public Integer getBorrowerTag() {
		return borrowerTag;
	}
	public void setBorrowerTag(Integer borrowerTag) {
		this.borrowerTag = borrowerTag;
	}
	public String getLockUser() {
		return lockUser;
	}
	public void setLockUser(String lockUser) {
		this.lockUser = lockUser;
	}
	public Date getDxpDate() {
		return dxpDate;
	}
	public void setDxpDate(Date dxpDate) {
		this.dxpDate = dxpDate;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public Double getOutPrice() {
		return outPrice;
	}
	public void setOutPrice(Double outPrice) {
		this.outPrice = outPrice;
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
