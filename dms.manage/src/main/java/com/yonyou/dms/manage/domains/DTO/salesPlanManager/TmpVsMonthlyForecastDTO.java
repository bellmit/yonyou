package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

import java.util.Date;

import com.yonyou.dms.function.domains.DTO.DataImportDto;
/**
 * 
* @ClassName: TmpVsMonthlyForecastDTO 
* @Description: 生产订单任务下发（新增） 
* @author zhengzengliang
* @date 2017年2月15日 上午10:52:45 
*
 */
public class TmpVsMonthlyForecastDTO extends DataImportDto{
	
	private Long materialId;
	private Integer forecastMonth;
	private Integer forecastYear;
	private Long updateBy;
	private Date updateDate;
	private Long id;
	private Integer ver;
	private Long createBy;
	private Date createDate;
	private Integer status;
	private Integer isArc;
	private Integer isDel;
	
	private Integer yearName; //任务下发：年
	private Integer monthName; //任务下发：月
	private String lastStockInDateFrom; //任务下发： 上报日期
	private String lastStockInDateTo; //任务下发：上报日期
	
	
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Integer getForecastMonth() {
		return forecastMonth;
	}
	public void setForecastMonth(Integer forecastMonth) {
		this.forecastMonth = forecastMonth;
	}
	public Integer getForecastYear() {
		return forecastYear;
	}
	public void setForecastYear(Integer forecastYear) {
		this.forecastYear = forecastYear;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsArc() {
		return isArc;
	}
	public void setIsArc(Integer isArc) {
		this.isArc = isArc;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getYearName() {
		return yearName;
	}
	public void setYearName(Integer yearName) {
		this.yearName = yearName;
	}
	public Integer getMonthName() {
		return monthName;
	}
	public void setMonthName(Integer monthName) {
		this.monthName = monthName;
	}
	public String getLastStockInDateFrom() {
		return lastStockInDateFrom;
	}
	public void setLastStockInDateFrom(String lastStockInDateFrom) {
		this.lastStockInDateFrom = lastStockInDateFrom;
	}
	public String getLastStockInDateTo() {
		return lastStockInDateTo;
	}
	public void setLastStockInDateTo(String lastStockInDateTo) {
		this.lastStockInDateTo = lastStockInDateTo;
	}


	
	
}
