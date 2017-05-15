package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class SADMS063Dto {
    private String dealerCode;//经销商代码
    private String seriesCode;// 车系代码
    private String brandCode;//品牌代码
    private Integer soStatus;//订单状态
    private Integer salesLcreplace;//留存数量
    private Date submitTime;//上报时间
    
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
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
