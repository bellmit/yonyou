package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

import java.util.Date;

import com.yonyou.dms.function.domains.DTO.DataImportDto;
/**
 * 
* @ClassName: TtVsMonthlyForecastDTO 
* @Description: 生产订单任务录入（提交） 
* @author zhengzengliang
* @date 2017年2月17日 上午10:55:12 
*
 */
public class TtVsMonthlyForecastDTO extends DataImportDto{

	private Long dealerId;
	private Integer forecastYear;
	private Long companyId;
	private Integer orgType;
	private Date updateDate;
	private Long createBy;
	private Long taskId;
	private Integer status;
	private Long orgId;
	private Long updateBy;
	private Integer bussType;
	private Integer forecastMonth;
	private Date createDate;
	private Long forecastId;
	
	public Long getDealerId() {
		return dealerId;
	}
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}
	public Integer getForecastYear() {
		return forecastYear;
	}
	public void setForecastYear(Integer forecastYear) {
		this.forecastYear = forecastYear;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Integer getOrgType() {
		return orgType;
	}
	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Integer getBussType() {
		return bussType;
	}
	public void setBussType(Integer bussType) {
		this.bussType = bussType;
	}
	public Integer getForecastMonth() {
		return forecastMonth;
	}
	public void setForecastMonth(Integer forecastMonth) {
		this.forecastMonth = forecastMonth;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getForecastId() {
		return forecastId;
	}
	public void setForecastId(Long forecastId) {
		this.forecastId = forecastId;
	}
	
	
	
}
