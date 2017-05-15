package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class OrderCarVO {
	
	private String	entityCode;//经销商代码
	private String	soNo;//销售订单号
	private String	customerName;//车主姓名
	private String	customerMobile;//车主手机号
	private String	soldBy;//销售顾问
	private String	phone;//联系电话
	private Integer	cardType;//证件类型
	private String	cardNo;//证件号码
	private String	brand;//汽车品牌
	private String	carModel;//车型
	private String	firstColor;//车身颜色（首选）
	private Integer	deliverMode;//交车方式
	private Date appDeliverDate;//预约交车时间
	private Date stockDate;//车辆到库时间
	private Date dealDate;//订单时间
	private String vin;//车辆代码（Vin）
	
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getSoldBy() {
		return soldBy;
	}
	public void setSoldBy(String soldBy) {
		this.soldBy = soldBy;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCardType() {
		return cardType;
	}
	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCarModel() {
		return carModel;
	}
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	public String getFirstColor() {
		return firstColor;
	}
	public void setFirstColor(String firstColor) {
		this.firstColor = firstColor;
	}
	public Integer getDeliverMode() {
		return deliverMode;
	}
	public void setDeliverMode(Integer deliverMode) {
		this.deliverMode = deliverMode;
	}
	public Date getAppDeliverDate() {
		return appDeliverDate;
	}
	public void setAppDeliverDate(Date appDeliverDate) {
		this.appDeliverDate = appDeliverDate;
	}
	public Date getStockDate() {
		return stockDate;
	}
	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}

}
