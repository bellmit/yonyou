package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class TmWxMaintainLabourDTO {
	private Integer labourDealType;// 维修工单处理方式)
	private Double labourPrice;// 工时单价
	private String labourName;// 项目名称
	private Float frt;// 工时
	private String labourCode;// 项目代码
	private Double labourFee;// 费用
	private Date labourCreateDate;// 工时创建时间

	public Float getFrt() {
		return frt;
	}

	public void setFrt(Float frt) {
		this.frt = frt;
	}

	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}

	public Date getLabourCreateDate() {
		return labourCreateDate;
	}

	public void setLabourCreateDate(Date labourCreateDate) {
		this.labourCreateDate = labourCreateDate;
	}

	public Integer getLabourDealType() {
		return labourDealType;
	}

	public void setLabourDealType(Integer labourDealType) {
		this.labourDealType = labourDealType;
	}

	public Double getLabourFee() {
		return labourFee;
	}

	public void setLabourFee(Double labourFee) {
		this.labourFee = labourFee;
	}

	public String getLabourName() {
		return labourName;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public Double getLabourPrice() {
		return labourPrice;
	}

	public void setLabourPrice(Double labourPrice) {
		this.labourPrice = labourPrice;
	}
}
