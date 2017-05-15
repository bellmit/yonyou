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
public class InventoryCheckDTO {
	private List<Map> dms_search; // 仓库表格数据
	private String inventoryNo; // 盘点单号
	private String sortBy; // 排序方式
	private String amount; // 库存情况
	private String group; // 配件车型组
	private String stop; // 停用
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date inBegin; // 入库开始
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date inEnd; // 入库结束
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date outBegin; // 出库开始
	@JsonDeserialize(using = JsonSimpleDateDeserializer.class)
	private Date outEnd; // 出库结束
	private String count; // 每张盘点单配件个数
	private String remark; // 备注

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Map> getDms_search() {
		return dms_search;
	}

	public void setDms_search(List<Map> dms_search) {
		this.dms_search = dms_search;
	}

	public String getInventoryNo() {
		return inventoryNo;
	}

	public void setInventoryNo(String inventoryNo) {
		this.inventoryNo = inventoryNo;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public Date getInBegin() {
		return inBegin;
	}

	public void setInBegin(Date inBegin) {
		this.inBegin = inBegin;
	}

	public Date getInEnd() {
		return inEnd;
	}

	public void setInEnd(Date inEnd) {
		this.inEnd = inEnd;
	}

	public Date getOutBegin() {
		return outBegin;
	}

	public void setOutBegin(Date outBegin) {
		this.outBegin = outBegin;
	}

	public Date getOutEnd() {
		return outEnd;
	}

	public void setOutEnd(Date outEnd) {
		this.outEnd = outEnd;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
}
