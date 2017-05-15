package com.infoservice.dms.cgcsl.vo;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class RecallServiceLabourVO extends BaseVO  {
	private static final long serialVersionUID = 1L;
	private String labourCode; //工时代码  
	private String labourName; //工时名称
	private Double stdLabourHour; //工时数
	private Double discount;//折扣
	private Integer goupNo;//组合编号
	
	public Integer getGoupNo() {
		return goupNo;
	}
	public void setGoupNo(Integer goupNo) {
		this.goupNo = goupNo;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getLabourCode() {
		return labourCode;
	}
	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}
	public String getLabourName() {
		return labourName;
	}
	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}
	public Double getStdLabourHour() {
		return stdLabourHour;
	}
	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
}
