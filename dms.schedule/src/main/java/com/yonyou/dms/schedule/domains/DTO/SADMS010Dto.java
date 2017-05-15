/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class SADMS010Dto {
	private static final Long serialVersionUID = 1L;
	private String dealerCode;//经销商代码
	private String seriesCode;// 车系代码
	private Integer callIn;//展厅来电车系数量
	private Integer walkIn;//展厅来访车系数量 不要求建档
	private Integer walkFound;//展厅来访车系数量 建档
	private Integer hotSalesLeads;//意向客户车系数量
	private Integer salesOrders;//销售订单车系数量
	private Double conversionRatio;//销售转换比率
	private Integer testDrive;//试驾
	private Integer noOfSc;//销售顾问人数
	private Date currentDate;//当前时间
	private Integer hslReplace;//HSL中 置换的数量
	private Integer salesReplace;//置换的销售订单数量
	private Integer dccOfHot;//HSL 中DCC转入的潜客数量
	private Integer hslSumreplace;  
	private Integer salesSumreplace;
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public Integer getCallIn() {
		return callIn;
	}
	public void setCallIn(Integer callIn) {
		this.callIn = callIn;
	}
	public Integer getWalkIn() {
		return walkIn;
	}
	public void setWalkIn(Integer walkIn) {
		this.walkIn = walkIn;
	}
	public Integer getWalkFound() {
		return walkFound;
	}
	public void setWalkFound(Integer walkFound) {
		this.walkFound = walkFound;
	}
	public Integer getHotSalesLeads() {
		return hotSalesLeads;
	}
	public void setHotSalesLeads(Integer hotSalesLeads) {
		this.hotSalesLeads = hotSalesLeads;
	}
	public Integer getSalesOrders() {
		return salesOrders;
	}
	public void setSalesOrders(Integer salesOrders) {
		this.salesOrders = salesOrders;
	}
	public Double getConversionRatio() {
		return conversionRatio;
	}
	public void setConversionRatio(Double conversionRatio) {
		this.conversionRatio = conversionRatio;
	}
	public Integer getTestDrive() {
		return testDrive;
	}
	public void setTestDrive(Integer testDrive) {
		this.testDrive = testDrive;
	}
	public Integer getNoOfSc() {
		return noOfSc;
	}
	public void setNoOfSc(Integer noOfSc) {
		this.noOfSc = noOfSc;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public Integer getHslReplace() {
		return hslReplace;
	}
	public void setHslReplace(Integer hslReplace) {
		this.hslReplace = hslReplace;
	}
	public Integer getSalesReplace() {
		return salesReplace;
	}
	public void setSalesReplace(Integer salesReplace) {
		this.salesReplace = salesReplace;
	}
	public Integer getDccOfHot() {
		return dccOfHot;
	}
	public void setDccOfHot(Integer dccOfHot) {
		this.dccOfHot = dccOfHot;
	}
	public Integer getHslSumreplace() {
		return hslSumreplace;
	}
	public void setHslSumreplace(Integer hslSumreplace) {
		this.hslSumreplace = hslSumreplace;
	}
	public Integer getSalesSumreplace() {
		return salesSumreplace;
	}
	public void setSalesSumreplace(Integer salesSumreplace) {
		this.salesSumreplace = salesSumreplace;
	}
	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
