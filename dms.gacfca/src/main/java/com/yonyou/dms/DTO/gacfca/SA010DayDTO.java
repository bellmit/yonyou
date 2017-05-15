package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 
* @ClassName: SA010DayDTO 
* @Description: 展厅预测报告(每天)
* @author zhengzengliang 
* @date 2017年4月13日 下午3:19:36 
*
 */
public class SA010DayDTO {
	
	private String dealerCode;//经销商代码
	private String seriesCode;// 车系代码
	private Integer callIn;//展厅来电车系数量
	private Integer walkIn;//展厅来访车系数量	 //日流量中不用传
	private Integer comeIn;//进店客流车系数量
	private Integer hotSalesLeads;//意向客户车系数量
	private Integer salesOrders;//销售订单车系数量
	private Double conversionRatio;//销售转换比率  为 销售订单车系数量 除以 意向客户车系数量	 //日流量中不用传
	private Integer testDrive;//试驾
	private Integer noOfSc;//销售顾问人数
	private Date currentDate;//当前时间
	
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
	public Integer getComeIn() {
		return comeIn;
	}
	public void setComeIn(Integer comeIn) {
		this.comeIn = comeIn;
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
	
	
	
	

}
