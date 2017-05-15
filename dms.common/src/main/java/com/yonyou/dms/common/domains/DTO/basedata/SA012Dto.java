package com.yonyou.dms.common.domains.DTO.basedata;

public class SA012Dto {
	private static final long serialVersionUID = 1L;
	
	private String seriesCode;// 车系代码
	
	private Integer hotSalesLeads;//意向客户车系数量（HSL）
	
	private Integer salesOrders;//销售订单车系数量（销售订单）
	
	private Double conversionRatio;//销售转换比率  为 销售订单车系数量 除以 意向客户车系数量
	
	private Integer testDrive;//试驾
	
	private Integer walkFound;	//treffic有效客流，要求建档，分车系统计
	
	private Integer hslReplace;//HSL中 置换的数量 --新增置换意向客户数
	
	private Integer salesReplace;//置换的销售订单数量--置换成交数
	
	private Integer dccOfHot;//HSL 中DCC转入的潜客数量（DCC HSL）
	
	private Integer hslSumreplace;  //置换意向客户数
	
	private Integer salesSumreplace; //【置换成交数】

	private Integer invoiceScanNum;//发票扫描数
	
	private Integer lcreplaceOrders;//留存订单数

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public Integer getHotSalesLeads() {
		return hotSalesLeads;
	}

	public void setHotSalesLeads(Integer hotSalesLeads) {
		this.hotSalesLeads = hotSalesLeads;
	}

	public Integer getSalesOrders() {
		return salesOrders;
	}

	public void setSalesOrders(Integer salesOrders) {
		this.salesOrders = salesOrders;
	}

	public Double getConversionRatio() {
		return conversionRatio;
	}

	public void setConversionRatio(Double conversionRatio) {
		this.conversionRatio = conversionRatio;
	}

	public Integer getTestDrive() {
		return testDrive;
	}

	public void setTestDrive(Integer testDrive) {
		this.testDrive = testDrive;
	}

	public Integer getWalkFound() {
		return walkFound;
	}

	public void setWalkFound(Integer walkFound) {
		this.walkFound = walkFound;
	}

	public Integer getHslReplace() {
		return hslReplace;
	}

	public void setHslReplace(Integer hslReplace) {
		this.hslReplace = hslReplace;
	}

	public Integer getSalesReplace() {
		return salesReplace;
	}

	public void setSalesReplace(Integer salesReplace) {
		this.salesReplace = salesReplace;
	}

	public Integer getDccOfHot() {
		return dccOfHot;
	}

	public void setDccOfHot(Integer dccOfHot) {
		this.dccOfHot = dccOfHot;
	}

	public Integer getHslSumreplace() {
		return hslSumreplace;
	}

	public void setHslSumreplace(Integer hslSumreplace) {
		this.hslSumreplace = hslSumreplace;
	}

	public Integer getSalesSumreplace() {
		return salesSumreplace;
	}

	public void setSalesSumreplace(Integer salesSumreplace) {
		this.salesSumreplace = salesSumreplace;
	}

	public Integer getInvoiceScanNum() {
		return invoiceScanNum;
	}

	public void setInvoiceScanNum(Integer invoiceScanNum) {
		this.invoiceScanNum = invoiceScanNum;
	}

	public Integer getLcreplaceOrders() {
		return lcreplaceOrders;
	}

	public void setLcreplaceOrders(Integer lcreplaceOrders) {
		this.lcreplaceOrders = lcreplaceOrders;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
