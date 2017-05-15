package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 出库信息or作废信息上报接口
 * @author luoyang
 *
 */
public class SEDMS067Dto {
	
	private String dealerCode;
	private String entityCode;//经销商代码
	private String allocateOutNo;//调拨出库单号
	private String customerName;//客户名称
	private Integer allocateState;//调拨单状态 （1 出库  2 作废）
	private Date stockOutDate;//出库时间
	private String handler;//经手人
	private Date makeUnableDate;//作废时间
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getAllocateOutNo() {
		return allocateOutNo;
	}
	public void setAllocateOutNo(String allocateOutNo) {
		this.allocateOutNo = allocateOutNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getAllocateState() {
		return allocateState;
	}
	public void setAllocateState(Integer allocateState) {
		this.allocateState = allocateState;
	}
	public Date getStockOutDate() {
		return stockOutDate;
	}
	public void setStockOutDate(Date stockOutDate) {
		this.stockOutDate = stockOutDate;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public Date getMakeUnableDate() {
		return makeUnableDate;
	}
	public void setMakeUnableDate(Date makeUnableDate) {
		this.makeUnableDate = makeUnableDate;
	}
	
	

}
