/**
 * 
 */
package com.infoeai.eai.wsServer.bsuv.lms;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class DMSTODCS003VO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Date depositDate;//下定日期
	private String customerName;//客户姓名
	private Integer orderStatus;//订单类型
	private String retailFinance;//零售金融
	private String modelCode;//车型代码 
	private String modelYear;//年款
	private String entityCode;//DMS端经销商代码
	private Float depositAmount;//定金金额
	private String tel;//客户手机
	private String trimCode;//内饰代码
	private Date deliverDate;//交车日期
	private Date submitDate;//提交日期
	private Date revokeDate;//取消时间
	private String colorCode;//颜色代码
	private Date saleDate;//实销日期
	private String dealerCode;//DCS端经销商代码
	private String ecOrderNo;//官网订单号
	private String idCrad;//身份证
	private String seriesCode;//车系代码
	private String brandCode;//品牌代码
	private Integer escComfirmType;//官网订单类型
	private String groupCode;//车款代码
	public Date getDepositDate() {
		return depositDate;
	}
	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getRetailFinance() {
		return retailFinance;
	}
	public void setRetailFinance(String retailFinance) {
		this.retailFinance = retailFinance;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelYear() {
		return modelYear;
	}
	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public Float getDepositAmount() {
		return depositAmount;
	}
	public void setDepositAmount(Float depositAmount) {
		this.depositAmount = depositAmount;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getTrimCode() {
		return trimCode;
	}
	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public Date getRevokeDate() {
		return revokeDate;
	}
	public void setRevokeDate(Date revokeDate) {
		this.revokeDate = revokeDate;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public Date getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getIdCrad() {
		return idCrad;
	}
	public void setIdCrad(String idCrad) {
		this.idCrad = idCrad;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Integer getEscComfirmType() {
		return escComfirmType;
	}
	public void setEscComfirmType(Integer escComfirmType) {
		this.escComfirmType = escComfirmType;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
