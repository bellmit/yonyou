package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 呆滞件发布上报接口
 * @author luoyang
 *
 */
public class SEDCS064Dto {
	
	private String dealerCode;
	private Long itemId;//下端发布流水号
	private String warehouse;//仓库
	private String storageCode;//库位代码
	private String partCode;//配件代码
	private String partName;//配件名称
	private String measureUnits;//计量单位
	private Integer releaseNumber;//发布数量（上端）/上报数量（下端）
	private Double salesPrice;//销售单价(上端)/上报单价（下端）
	private Date releaseDate;//发布日期（上端）/上报日期（下端）
	private Date endDate;//结束日期(上端)/呆滞品发布过期日期(下端)
	private Double costPrice;//成本单价
	private String linkmanName;//联系人名称
	private String linkmanTel;//联系人方式
	private String linkmanAddress;//联系人地址
	
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getWarehouse() {
		return warehouse;
	}
	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	public String getStorageCode() {
		return storageCode;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
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
	public String getMeasureUnits() {
		return measureUnits;
	}
	public void setMeasureUnits(String measureUnits) {
		this.measureUnits = measureUnits;
	}
	public Integer getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(Integer releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	public Double getSalesPrice() {
		return salesPrice;
	}
	public void setSalesPrice(Double salesPrice) {
		this.salesPrice = salesPrice;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Double getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}
	public String getLinkmanName() {
		return linkmanName;
	}
	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}
	public String getLinkmanTel() {
		return linkmanTel;
	}
	public void setLinkmanTel(String linkmanTel) {
		this.linkmanTel = linkmanTel;
	}
	public String getLinkmanAddress() {
		return linkmanAddress;
	}
	public void setLinkmanAddress(String linkmanAddress) {
		this.linkmanAddress = linkmanAddress;
	}
	
	

}
