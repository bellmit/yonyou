package com.yonyou.dms.part.domains.DTO.basedata;

/**
 * @description 配件移库查询DTO
 * @author Administrator
 *
 */
public class PartItemDTO {

	private String storageCode;	//仓库
	private String brand;	//品牌
	private String partNo;		//配件号
	private String spellCode;	//拼音
	private String partName; 	//配件名称
	private Integer partGroupCode;	//类别
	private String storagePositionCode;  //库位
	private String partModelGroupCodeSet; //配件车型组
	private String stockZero;			//库存是否大于0
	private String priceZero;			//售价是否大于0
	private String description;			//备注
	public String getStorageCode() {
		return storageCode;
	}
	public String getPartNo() {
		return partNo;
	}
	public String getSpellCode() {
		return spellCode;
	}
	public String getPartName() {
		return partName;
	}
	public Integer getPartGroupCode() {
		return partGroupCode;
	}
	public String getStoragePositionCode() {
		return storagePositionCode;
	}
	public String getPartModelGroupCodeSet() {
		return partModelGroupCodeSet;
	}
	public String getStockZero() {
		return stockZero;
	}
	public String getPriceZero() {
		return priceZero;
	}
	public void setStorageCode(String storageCode) {
		this.storageCode = storageCode;
	}
	public String getBrand() {
		return brand;
	}
	public String getDescription() {
		return description;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public void setPartGroupCode(Integer partGroupCode) {
		this.partGroupCode = partGroupCode;
	}
	public void setStoragePositionCode(String storagePositionCode) {
		this.storagePositionCode = storagePositionCode;
	}
	public void setPartModelGroupCodeSet(String partModelGroupCodeSet) {
		this.partModelGroupCodeSet = partModelGroupCodeSet;
	}
	public void setStockZero(String stockZero) {
		this.stockZero = stockZero;
	}
	public void setPriceZero(String priceZero) {
		this.priceZero = priceZero;
	}

}
