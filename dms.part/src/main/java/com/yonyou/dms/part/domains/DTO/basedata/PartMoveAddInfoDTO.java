package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

/**
 * @description 配件移库信息DTO新增用
 * @author sip
 *
 */
public class PartMoveAddInfoDTO {
	private String dealerCode;
	private Integer itemId;
	private String entityCode2;
	private String transferNo;
	private Double costPrice;
	private String partBatchNo;
	private String partNo;
	private String partName;
	private String unitCode;
	private String oldStorageCode;
	private String newStorageCode;
	private String oldStoragePosition;
	private String newStoragePosition;
	private Double costAmount;
	private Double transferQuantity;
	private Integer DKey;
	private String createdBy;
	private Date createdAt;
	private String updatedBy;
	private Date updatedAt;
	private Integer ver;
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getEntityCode2() {
		return entityCode2;
	}
	public void setEntityCode2(String entityCode2) {
		this.entityCode2 = entityCode2;
	}
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
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
	public String getOldStorageCode() {
		return oldStorageCode;
	}
	public void setOldStorageCode(String oldStorageCode) {
		this.oldStorageCode = oldStorageCode;
	}
	public String getNewStorageCode() {
		return newStorageCode;
	}
	public void setNewStorageCode(String newStorageCode) {
		this.newStorageCode = newStorageCode;
	}
	public String getOldStoragePosition() {
		return oldStoragePosition;
	}
	public void setOldStoragePosition(String oldStoragePosition) {
		this.oldStoragePosition = oldStoragePosition;
	}
	public String getNewStoragePosition() {
		return newStoragePosition;
	}
	public void setNewStoragePosition(String newStoragePosition) {
		this.newStoragePosition = newStoragePosition;
	}
	public Double getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}
	public Double getTransferQuantity() {
		return transferQuantity;
	}
	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
	public Integer getDKey() {
		return DKey;
	}
	public void setDKey(Integer dKey) {
		DKey = dKey;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
}
