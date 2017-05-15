package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * 入库信息上报接口
 * @author luoyang
 *
 */
public class SEDMS070Dto {
	
	private String dealerCode;
	private String entityCode;//经销商代码
	private String allocateInNo;//调拨入库单号
	private String providerName;//供应商名称
	private Date stockInDate;//入库时间
	private String handler;//经手人
	
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
	public String getAllocateInNo() {
		return allocateInNo;
	}
	public void setAllocateInNo(String allocateInNo) {
		this.allocateInNo = allocateInNo;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Date getStockInDate() {
		return stockInDate;
	}
	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	
	

}
