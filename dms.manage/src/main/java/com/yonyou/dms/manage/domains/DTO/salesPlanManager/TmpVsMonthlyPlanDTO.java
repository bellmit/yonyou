package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
/**
 * 
* @ClassName: TmpVsMonthlyPlanDTO 
* @Description: 月度任务上传
* @author zhengzengliang 
* @date 2017年3月14日 下午2:05:01 
*
 */
public class TmpVsMonthlyPlanDTO extends DataImportDto{
	
	@ExcelColumnDefine(value=1)
	@Required
	private String dealerCode;
	
	@ExcelColumnDefine(value=2)
	@Required
	private String series300C;
	
	@ExcelColumnDefine(value=3)
	@Required
	private String caliber;
	
	@ExcelColumnDefine(value=4)
	@Required
	private String cherokee;
	
	@ExcelColumnDefine(value=5)
	@Required
	private String compass20;
	
	@ExcelColumnDefine(value=6)
	@Required
	private String compass24;
	
	@ExcelColumnDefine(value=7)
	@Required
	private String grandCherokee;
	
	@ExcelColumnDefine(value=8)
	@Required
	private String grandCherokee30;
	
	@ExcelColumnDefine(value=9)
	@Required
	private String grandVoyager;
	
	@ExcelColumnDefine(value=10)
	@Required
	private String journey;
	
	@ExcelColumnDefine(value=11)
	@Required
	private String patriot;
	
	@ExcelColumnDefine(value=12)
	@Required
	private String wrangler;
	
	private String planYear;
	private String planMonth;
	private String planType;
	private String seriesNumJson;
	private String num ;  //目标任务
	private String series; //车系
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
	public String getSeries300C() {
		return series300C;
	}
	public void setSeries300C(String series300c) {
		series300C = series300c;
	}
	public String getCaliber() {
		return caliber;
	}
	public void setCaliber(String caliber) {
		this.caliber = caliber;
	}
	public String getCherokee() {
		return cherokee;
	}
	public void setCherokee(String cherokee) {
		this.cherokee = cherokee;
	}
	public String getCompass20() {
		return compass20;
	}
	public void setCompass20(String compass20) {
		this.compass20 = compass20;
	}
	public String getCompass24() {
		return compass24;
	}
	public void setCompass24(String compass24) {
		this.compass24 = compass24;
	}
	public String getGrandCherokee() {
		return grandCherokee;
	}
	public void setGrandCherokee(String grandCherokee) {
		this.grandCherokee = grandCherokee;
	}
	public String getGrandCherokee30() {
		return grandCherokee30;
	}
	public void setGrandCherokee30(String grandCherokee30) {
		this.grandCherokee30 = grandCherokee30;
	}
	public String getGrandVoyager() {
		return grandVoyager;
	}
	public void setGrandVoyager(String grandVoyager) {
		this.grandVoyager = grandVoyager;
	}
	public String getJourney() {
		return journey;
	}
	public void setJourney(String journey) {
		this.journey = journey;
	}
	public String getPatriot() {
		return patriot;
	}
	public void setPatriot(String patriot) {
		this.patriot = patriot;
	}
	public String getWrangler() {
		return wrangler;
	}
	public void setWrangler(String wrangler) {
		this.wrangler = wrangler;
	}
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public String getPlanMonth() {
		return planMonth;
	}
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getSeriesNumJson() {
		return seriesNumJson;
	}
	public void setSeriesNumJson(String seriesNumJson) {
		this.seriesNumJson = seriesNumJson;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	
	

}
