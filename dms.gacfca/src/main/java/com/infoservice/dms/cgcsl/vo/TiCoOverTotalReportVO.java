package com.infoservice.dms.cgcsl.vo;


public class TiCoOverTotalReportVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	
	private Integer overCustomer;// 超期未维护客户
	private Integer overOrder;// 超期未维护订单
	private Integer validCustomerNum;//有效客户数
	private Integer validOrderNum;//有效订单数
	private String seriesCode;//车系代码
	
	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	/**
	 * @return the overCustomer
	 */
	public Integer getOverCustomer() {
		return overCustomer;
	}

	/**
	 * @param overCustomer
	 *            the overCustomer to set
	 */
	public void setOverCustomer(Integer overCustomer) {
		this.overCustomer = overCustomer;
	}

	/**
	 * @return the overOrder
	 */
	public Integer getOverOrder() {
		return overOrder;
	}

	/**
	 * @param overOrder
	 *            the overOrder to set
	 */
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

}