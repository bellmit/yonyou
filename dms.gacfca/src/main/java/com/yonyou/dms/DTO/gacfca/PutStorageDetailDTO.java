package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class PutStorageDetailDTO {

	private String entityCode;// 经销商代码
	private Integer putStorageType;// 入库类型 1：采购类型；2：调拨入库；3：盘点入库
	private Date putStorageDate;// 入库日期
	private String putStorageNum;// 入库单号
	private String supplierName;// 供应商名称
	private String storageCode;// 仓库代码
	private String storageName;// 仓库名称
	private String partCode;// 配件代码
	private String partName;// 配件名称
	private Double dnpPrice;// DNP价格
	private Float numbers;// 数量
	private Double price;// 行价

	// new by wangJian 2016-09-05
	private String ecOrderNo;// 电商订单号
	private String deliverNo;// 交货单号

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getPutStorageType() {
		return putStorageType;
	}

	public void setPutStorageType(Integer putStorageType) {
		this.putStorageType = putStorageType;
	}

	public Date getPutStorageDate() {
		return putStorageDate;
	}

	public void setPutStorageDate(Date putStorageDate) {
		this.putStorageDate = putStorageDate;
	}

	public String getPutStorageNum() {
		return putStorageNum;
	}

	public void setPutStorageNum(String putStorageNum) {
		this.putStorageNum = putStorageNum;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getStorageCode() {
		return storageCode;
	}

	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Double getDnpPrice() {
		return dnpPrice;
	}

	public void setDnpPrice(Double dnpPrice) {
		this.dnpPrice = dnpPrice;
	}

	public Float getNumbers() {
		return numbers;
	}

	public void setNumbers(Float numbers) {
		this.numbers = numbers;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getEcOrderNo() {
		return ecOrderNo;
	}

	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}

	public String getDeliverNo() {
		return deliverNo;
	}

	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
}
