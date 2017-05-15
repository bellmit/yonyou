package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TtTestDriverStatDTO {

	private Long testDriverOrder;
	private String dealerCode;
	private Long id;
	private Long testDriverFeedback;
	private Long poCustomer;
	private Integer currentWeek;
	private Date currentDate;
	private Long testDriver;
	private String seriesCode;
	private Date createDate;
	private String entityCode;//经销商代码

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public void setTestDriverOrder(Long testDriverOrder){
		this.testDriverOrder=testDriverOrder;
	}

	public Long getTestDriverOrder(){
		return this.testDriverOrder;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setTestDriverFeedback(Long testDriverFeedback){
		this.testDriverFeedback=testDriverFeedback;
	}

	public Long getTestDriverFeedback(){
		return this.testDriverFeedback;
	}

	public void setPoCustomer(Long poCustomer){
		this.poCustomer=poCustomer;
	}

	public Long getPoCustomer(){
		return this.poCustomer;
	}

	public void setCurrentWeek(Integer currentWeek){
		this.currentWeek=currentWeek;
	}

	public Integer getCurrentWeek(){
		return this.currentWeek;
	}

	public void setCurrentDate(Date currentDate){
		this.currentDate=currentDate;
	}

	public Date getCurrentDate(){
		return this.currentDate;
	}

	public void setTestDriver(Long testDriver){
		this.testDriver=testDriver;
	}

	public Long getTestDriver(){
		return this.testDriver;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

}