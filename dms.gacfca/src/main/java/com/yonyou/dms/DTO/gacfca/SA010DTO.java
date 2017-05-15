package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 
* @ClassName: SA010DTO 
* @Description: 展厅预测报告
* @author zhengzengliang 
* @date 2017年4月13日 下午2:40:45 
*
 */
public class SA010DTO {
	
	private String dealerCode;//经销商代码
	private String seriesCode;// 车系代码
	private Integer callIn;//展厅来电车系数量
	private Integer walkIn;//展厅来访车系数量
	private Integer hotSalesLeads;//意向客户车系数量
	private Integer salesOrders;//销售订单车系数量
	private Double conversionRatio;//销售转换比率  为 销售订单车系数量 除以 意向客户车系数量
	private Integer testDrive;//试驾
	private Integer noOfSc;//销售顾问人数
	private Date currentDate;//当前时间
	private Integer walkFound;	//treffic有效客流，要求建档，分车系统计
	private Integer hslReplace;//HSL中 置换的数量 --新增置换意向客户数
	private Integer salesReplace;//置换的销售订单数量--置换成交数
	//2015-1-15wjs
	private Integer dccOfHot;//HSL 中DCC转入的潜客数量
	//wjb  2015-03-09
	private Integer hslSumreplace;  //置换意向客户数
	private Integer salesSumreplace; //【置换成交数】
	//add by : lsy  2015-4-9 
	private Integer poCustomer; //潜客人数
	private Integer testDriverFeedback;// 试 驾反馈人数
	private Integer testDriverOrder;//试驾成交数
	private Integer testDriver; //试驾人数
	
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
	public Integer getWalkFound() {
		return walkFound;
	}
	public void setWalkFound(Integer walkFound) {
		this.walkFound = walkFound;
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
	public Integer getPoCustomer() {
		return poCustomer;
	}
	public void setPoCustomer(Integer poCustomer) {
		this.poCustomer = poCustomer;
	}
	public Integer getTestDriverFeedback() {
		return testDriverFeedback;
	}
	public void setTestDriverFeedback(Integer testDriverFeedback) {
		this.testDriverFeedback = testDriverFeedback;
	}
	public Integer getTestDriverOrder() {
		return testDriverOrder;
	}
	public void setTestDriverOrder(Integer testDriverOrder) {
		this.testDriverOrder = testDriverOrder;
	}
	public Integer getTestDriver() {
		return testDriver;
	}
	public void setTestDriver(Integer testDriver) {
		this.testDriver = testDriver;
	}
	
	

}
