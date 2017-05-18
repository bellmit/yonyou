/**
 * 
 */
package com.yonyou.dms.customer.domains.DTO.customerManage;

/**
 * 销售回访结果查询追加表头
 * @author Administrator
 *
* @date 2017年3月21日
 */
public class TableHeaderDTO {

	private String title;
	private String valign;
	private String align;
	private Integer colspan;
	private Integer rowspan;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValign() {
		return valign;
	}
	public void setValign(String valign) {
		this.valign = valign;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public Integer getColspan() {
		return colspan;
	}
	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}
	public Integer getRowspan() {
		return rowspan;
	}
	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}
	
	
	
	
}
