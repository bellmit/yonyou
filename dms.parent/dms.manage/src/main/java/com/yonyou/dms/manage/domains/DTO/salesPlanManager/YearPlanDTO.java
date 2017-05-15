package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

/**
 * 
* @ClassName: YearPlanDTO 
* @Description: 年度目标上传
* @author zhengzengliang
* @date 2017年2月28日 下午8:41:28 
*
 */
public class YearPlanDTO {
	
	private String year;  //目标年份
	private String planType; //目标类型
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	
	

}
