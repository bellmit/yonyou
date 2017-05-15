package com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmpWeekDTO  extends  DataImportDto{
	private String id;
	@ExcelColumnDefine(value = 1)
	@Required
	private String year_code;
	private String month_code;
	@ExcelColumnDefine(value = 2)
	@Required
	private String week_code;
	private String start_date;
	private String end_date;
	private String create_date;
	private String create_by;
	private String update_date;
	private String update_by;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getYear_code() {
		return year_code;
	}
	public void setYear_code(String year_code) {
		this.year_code = year_code;
	}
	public String getMonth_code() {
		return month_code;
	}
	public void setMonth_code(String month_code) {
		this.month_code = month_code;
	}
	public String getWeek_code() {
		return week_code;
	}
	public void setWeek_code(String week_code) {
		this.week_code = week_code;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getCreate_by() {
		return create_by;
	}
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
}
