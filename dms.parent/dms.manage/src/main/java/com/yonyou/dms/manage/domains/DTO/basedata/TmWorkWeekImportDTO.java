package com.yonyou.dms.manage.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmWorkWeekImportDTO extends DataImportDto {
	
	@ExcelColumnDefine(value=1)
	@Required
	private Integer workYear;
	
	@ExcelColumnDefine(value=2)
	@Required
	private Integer workMonth;
	
	@ExcelColumnDefine(value=3)
	@Required
	private Integer workWeek;
	
	@ExcelColumnDefine(value=4)
	@Required
	private Date startDate;
	
	@ExcelColumnDefine(value=5)
	@Required
	private Date endDate;
	

	public Integer getWorkYear() {
		return workYear;
	}

	public void setWorkYear(Integer workYear) {
		this.workYear = workYear;
	}

	public Integer getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(Integer workMonth) {
		this.workMonth = workMonth;
	}

	public Integer getWorkWeek() {
		return workWeek;
	}

	public void setWorkWeek(Integer workWeek) {
		this.workWeek = workWeek;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "TmWorkWeekImportDTO [rowNo="+getRowNO()+", workYear=" + workYear + ", workMonth=" + workMonth + ", workWeek=" + workWeek
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
	

}
