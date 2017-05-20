/**
 * 
 */
package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TmMarketActivityDto {
	
	private Double marketFee;
	private String seriesName;		//车系名称
	private String marketName;		//市场活动名称
	private Date endDate;			//结束日期
	private String modelCode;		//车型代码
	private String modelName;		//车型名称
	private Integer isDown;			
	private Integer isDel;			//删除标志
	private String marketNo;		//活动编号
	private Date startDate;			//开始日期
	private Integer ver;			//版本控制
	private String seriesCode;		//车系代码
	private Integer isArc;			//归档标志
	private String dealerCode;
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Double getMarketFee() {
		return marketFee;
	}
	public void setMarketFee(Double marketFee) {
		this.marketFee = marketFee;
	}
	public String getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}
	public String getMarketName() {
		return marketName;
	}
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getIsDown() {
		return isDown;
	}
	public void setIsDown(Integer isDown) {
		this.isDown = isDown;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getMarketNo() {
		return marketNo;
	}
	public void setMarketNo(String marketNo) {
		this.marketNo = marketNo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public Integer getIsArc() {
		return isArc;
	}
	public void setIsArc(Integer isArc) {
		this.isArc = isArc;
	}
	
	
}
