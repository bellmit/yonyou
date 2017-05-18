package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;

/**
 * @description 移库单打印详细
 * @author Administrator
 *
 */
public class PartStockInfoDTO {
	private String transferNo;
	private Date transferDate;
	private String partNo;
	private String partName;
	private String oldStorageCode;
	private String oldStorageName;
	private String oldStoragepositionCode;
	private String newStorageCode;
	private String newStorageName;
	private String newStoragepositionCode;
	private Double transferQuantity;
	public String getTransferNo() {
		return transferNo;
	}
	public Date getTransferDate() {
		return transferDate;
	}
	public String getPartNo() {
		return partNo;
	}
	public String getPartName() {
		return partName;
	}
	public String getOldStorageCode() {
		return oldStorageCode;
	}
	public String getOldStorageName() {
		return oldStorageName;
	}
	public String getOldStoragepositionCode() {
		return oldStoragepositionCode;
	}
	public String getNewStorageCode() {
		return newStorageCode;
	}
	public String getNewStorageName() {
		return newStorageName;
	}
	public String getNewStoragepositionCode() {
		return newStoragepositionCode;
	}
	public Double getTransferQuantity() {
		return transferQuantity;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setOldStorageCode(String oldStorageCode) {
		this.oldStorageCode = oldStorageCode;
	}
	public void setOldStorageName(String oldStorageName) {
		this.oldStorageName = oldStorageName;
	}
	public void setOldStoragepositionCode(String oldStoragepositionCode) {
		this.oldStoragepositionCode = oldStoragepositionCode;
	}
	public void setNewStorageCode(String newStorageCode) {
		this.newStorageCode = newStorageCode;
	}
	public void setNewStorageName(String newStorageName) {
		this.newStorageName = newStorageName;
	}
	public void setNewStoragepositionCode(String newStoragepositionCode) {
		this.newStoragepositionCode = newStoragepositionCode;
	}
	public void setTransferQuantity(Double transferQuantity) {
		this.transferQuantity = transferQuantity;
	}
}
