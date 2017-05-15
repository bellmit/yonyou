/**
 * 
 */
package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public class ReportPayOffDTO {

	private String handler;// 经手人

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date orderDate;// 报溢日期

	private List<Map> partProfitItemList;// 表格数据

	private String inventoryNo;// 盘点单号

	private String profitNo;// 报溢单号

	public String getProfitNo() {
		return profitNo;
	}

	public void setProfitNo(String profitNo) {
		this.profitNo = profitNo;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<Map> getPartProfitItemList() {
		return partProfitItemList;
	}

	public void setPartProfitItemList(List<Map> partProfitItemList) {
		this.partProfitItemList = partProfitItemList;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}
}
