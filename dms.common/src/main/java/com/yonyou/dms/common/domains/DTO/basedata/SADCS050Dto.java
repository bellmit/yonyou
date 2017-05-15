package com.yonyou.dms.common.domains.DTO.basedata;

import java.util.Date;

public class SADCS050Dto {
	private static final long serialVersionUID = 1L;
	private String DealerCode; //下端：经销商代码  CHAR(8)  上端：
	private String seriesCode;//车系代码
	private Integer hslHabod;//HSL_HABOD
	private Integer potentialCustomersNum;//置换潜客数
	private Integer intentionNum;//置换意向数
	private Integer saleNum;//零售实销数
	private Integer dealNum;//置换成交数
	private Double intentionRatio;//置换意向占比
	private Double dealRatio;//置换成交率
	private Double conversionRatio;//置换转化率
	private Integer reportType;//报表类型（月报：1，周报：2）
	private Date reportDate;//上报日期 yyyy-MM-dd
	public String getDealerCode() {
		return DealerCode;
	}
	public void setDealerCode(String dealerCode) {
		DealerCode = dealerCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public Integer getHslHabod() {
		return hslHabod;
	}
	public void setHslHabod(Integer hslHabod) {
		this.hslHabod = hslHabod;
	}
	public Integer getPotentialCustomersNum() {
		return potentialCustomersNum;
	}
	public void setPotentialCustomersNum(Integer potentialCustomersNum) {
		this.potentialCustomersNum = potentialCustomersNum;
	}
	public Integer getIntentionNum() {
		return intentionNum;
	}
	public void setIntentionNum(Integer intentionNum) {
		this.intentionNum = intentionNum;
	}
	public Integer getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}
	public Integer getDealNum() {
		return dealNum;
	}
	public void setDealNum(Integer dealNum) {
		this.dealNum = dealNum;
	}
	public Double getIntentionRatio() {
		return intentionRatio;
	}
	public void setIntentionRatio(Double intentionRatio) {
		this.intentionRatio = intentionRatio;
	}
	public Double getDealRatio() {
		return dealRatio;
	}
	public void setDealRatio(Double dealRatio) {
		this.dealRatio = dealRatio;
	}
	public Double getConversionRatio() {
		return conversionRatio;
	}
	public void setConversionRatio(Double conversionRatio) {
		this.conversionRatio = conversionRatio;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
