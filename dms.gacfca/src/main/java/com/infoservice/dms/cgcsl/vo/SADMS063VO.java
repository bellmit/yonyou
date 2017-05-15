package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SADMS063VO extends BaseVO {
	private static final long serialVersionUID = 1L;
	private String seriesCode;// 车系代码
	private String brandCode;//品牌代码
	private Integer soStatus;//订单状态 
							/*13011010 ：未提交
							13011015 ：经理审核数
							13011020 ：财务审核中
							13011025：交交车确认*/
	private Integer salesLcreplace;//留存数量
	private Date submitTime;//上报时间
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public Integer getSalesLcreplace() {
		return salesLcreplace;
	}
	public void setSalesLcreplace(Integer salesLcreplace) {
		this.salesLcreplace = salesLcreplace;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
   
}
