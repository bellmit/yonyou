package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
/**
 * 
* @ClassName: TmpVsYearlyPlanDTO 
* @Description: 年度目标上传
* @author zhengzengliang
* @date 2017年2月10日 上午10:58:37 
*
 */
public class TmpVsYearlyPlanDTO extends DataImportDto{
	
	@ExcelColumnDefine(value=1)
	@Required
	private String dealerCode;
	
	@ExcelColumnDefine(value=2)
	@Required
	private String groupCode;
	
	@ExcelColumnDefine(value=3)
	@Required
	private String sumAmt;
	
	@ExcelColumnDefine(value=4)
	@Required
	private String janAmt;
	
	@ExcelColumnDefine(value=5)
	@Required
	private String febAmt;
	
	@ExcelColumnDefine(value=6)
	@Required
	private String marAmt;
	
	@ExcelColumnDefine(value=7)
	@Required
	private String aprAmt;
	
	@ExcelColumnDefine(value=8)
	@Required
	private String mayAmount;
	
	@ExcelColumnDefine(value=9)
	@Required
	private String junAmt;
	
	@ExcelColumnDefine(value=10)
	@Required
	private String julAmt;
	
	@ExcelColumnDefine(value=11)
	@Required
	private String augAmt;
	
	@ExcelColumnDefine(value=12)
	@Required
	private String sepAmt;
	
	@ExcelColumnDefine(value=13)
	@Required
	private String octAmt;
	
	@ExcelColumnDefine(value=14)
	@Required
	private String novAmt;
	
	@ExcelColumnDefine(value=15)
	@Required
	private String decAmt;
	
	private String year;  //目标年份
	private String planType; //目标类型

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(String sumAmt) {
		this.sumAmt = sumAmt;
	}

	public String getJanAmt() {
		return janAmt;
	}

	public void setJanAmt(String janAmt) {
		this.janAmt = janAmt;
	}

	public String getFebAmt() {
		return febAmt;
	}

	public void setFebAmt(String febAmt) {
		this.febAmt = febAmt;
	}

	public String getMarAmt() {
		return marAmt;
	}

	public void setMarAmt(String marAmt) {
		this.marAmt = marAmt;
	}

	public String getAprAmt() {
		return aprAmt;
	}

	public void setAprAmt(String aprAmt) {
		this.aprAmt = aprAmt;
	}

	public String getMayAmount() {
		return mayAmount;
	}

	public void setMayAmount(String mayAmount) {
		this.mayAmount = mayAmount;
	}

	public String getJunAmt() {
		return junAmt;
	}

	public void setJunAmt(String junAmt) {
		this.junAmt = junAmt;
	}

	public String getJulAmt() {
		return julAmt;
	}

	public void setJulAmt(String julAmt) {
		this.julAmt = julAmt;
	}

	public String getAugAmt() {
		return augAmt;
	}

	public void setAugAmt(String augAmt) {
		this.augAmt = augAmt;
	}

	public String getSepAmt() {
		return sepAmt;
	}

	public void setSepAmt(String sepAmt) {
		this.sepAmt = sepAmt;
	}

	public String getOctAmt() {
		return octAmt;
	}

	public void setOctAmt(String octAmt) {
		this.octAmt = octAmt;
	}

	public String getNovAmt() {
		return novAmt;
	}

	public void setNovAmt(String novAmt) {
		this.novAmt = novAmt;
	}

	public String getDecAmt() {
		return decAmt;
	}

	public void setDecAmt(String decAmt) {
		this.decAmt = decAmt;
	}

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
