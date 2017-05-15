package com.infoservice.dms.cgcsl.vo;

public class SEDCSP03VOPartInfoVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	private String partCode;//配件代码
	private String partName;//配件名称
	private Long packageNum;//最小包装量
	private Long orderNum;//计划量
	private Double noTaxPrice;//不含税单价
	private Double noTaxAmount;//不含税总额
	private String unit;//单位
	private Double discount;//单个折扣
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
	public Long getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(Long packageNum) {
		this.packageNum = packageNum;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	public Double getNoTaxPrice() {
		return noTaxPrice;
	}
	public void setNoTaxPrice(Double noTaxPrice) {
		this.noTaxPrice = noTaxPrice;
	}
	public Double getNoTaxAmount() {
		return noTaxAmount;
	}
	public void setNoTaxAmount(Double noTaxAmount) {
		this.noTaxAmount = noTaxAmount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
}
