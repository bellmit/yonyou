package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

public class SADMS034Dto {
	
	private static final long serialVersionUID = 1L;
	
	private String dealerCode;//dealerCode
	
	private Integer overCustomer;// 超期未维护客户
	
	private Integer overOrder;// 超期未维护订单
	
	private Integer validCustomerNum ;// 有效客户数
	
	private Integer validOrderNum;// 有效订单数
	
	private String seriesCode;//车系代码
	
	private Date downTimestamp; //下发的时间
	
	private Integer isValid; //是否有效
	
	private String errorMsg; //错误消息

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public Integer getOverCustomer() {
		return overCustomer;
	}

	public void setOverCustomer(Integer overCustomer) {
		this.overCustomer = overCustomer;
	}

	public Integer getOverOrder() {
		return overOrder;
	}

	public void setOverOrder(Integer overOrder) {
		this.overOrder = overOrder;
	}

	public Integer getValidCustomerNum() {
		return validCustomerNum;
	}

	public void setValidCustomerNum(Integer validCustomerNum) {
		this.validCustomerNum = validCustomerNum;
	}

	public Integer getValidOrderNum() {
		return validOrderNum;
	}

	public void setValidOrderNum(Integer validOrderNum) {
		this.validOrderNum = validOrderNum;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
