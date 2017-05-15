package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SA057VO extends BaseVO{
	//add by : lsy  2015-4-13 
	private static final long serialVersionUID = 1L;
	private String entityCode;//经销商代码
	private Date currentDate;//当前时间
	private Long poCustomer; //潜客人数
	private Long testDriverFeedback;//试驾反馈人数
	private Long testDriverOrder;//试驾成交数
	private Long testDriver; //试驾人数
	//add by lsy 2015-5-7
	private String seriesCode;// 车系代码
	//end
	public String getEntityCode() {
		return entityCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	public Long getPoCustomer() {
		return poCustomer;
	}
	public void setPoCustomer(Long poCustomer) {
		this.poCustomer = poCustomer;
	}
	public Long getTestDriverFeedback() {
		return testDriverFeedback;
	}
	public void setTestDriverFeedback(Long testDriverFeedback) {
		this.testDriverFeedback = testDriverFeedback;
	}
	public Long getTestDriverOrder() {
		return testDriverOrder;
	}
	public void setTestDriverOrder(Long testDriverOrder) {
		this.testDriverOrder = testDriverOrder;
	}
	public Long getTestDriver() {
		return testDriver;
	}
	public void setTestDriver(Long testDriver) {
		this.testDriver = testDriver;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
