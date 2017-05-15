package com.yonyou.dms.vehicle.domains.DTO.k4Order;

/**
 * 审核通过、驳回返回Id
 * 
 * @author liujimng
 *
 */

public class K4OrderDTO {

	private String ids;
	private String orderIds;
	private String vins;
	private String isEc;
	private String dealerCode;
	private String zdrrVins;

	public String getZdrrVins() {
		return zdrrVins;
	}

	public void setZdrrVins(String zdrrVins) {
		this.zdrrVins = zdrrVins;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getVins() {
		return vins;
	}

	public void setVins(String vins) {
		this.vins = vins;
	}

	public String getIsEc() {
		return isEc;
	}

	public void setIsEc(String isEc) {
		this.isEc = isEc;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

}
