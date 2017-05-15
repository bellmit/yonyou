/**
 * @Title: SalesorderShoppingVO.java
 * @Date: 2015-5-5
 * @author xuqinqin
 * @version 1.0
 * @remark
 */
package com.yonyou.dms.common.domains.DTO.common;

import java.util.Date;

/**
 * @author Administrator
 */
public class SalesorderShoppingDTO {
	
	private String consigneeCode;
	private String vin;
	private Integer dealStatus;
	private String remark;
	private String productCode;
	private Date downTimestamp; //下发的时间
	private String entityCode; //下端：经销商代码  CHAR(8)  上端： 
	
	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	
	public Date getDownTimestamp() {
		return downTimestamp;
	}

	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConsigneeCode() {
		return consigneeCode;
	}

	public void setConsigneeCode(String consigneeCode) {
		this.consigneeCode = consigneeCode;
	}

	public Integer getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(Integer dealStatus) {
		this.dealStatus = dealStatus;
	}
}
