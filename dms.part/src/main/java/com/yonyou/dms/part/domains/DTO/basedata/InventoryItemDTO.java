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
public class InventoryItemDTO {

	private String inventoryNo;

	private String handler;

	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date inventoryDate;

	private String remark;

	private List<Map> dms_details;

	public List<Map> getDms_details() {
		return dms_details;
	}

	public void setDms_details(List<Map> dms_details) {
		this.dms_details = dms_details;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public Date getInventoryDate() {
		return inventoryDate;
	}

	public void setInventoryDate(Date inventoryDate) {
		this.inventoryDate = inventoryDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
