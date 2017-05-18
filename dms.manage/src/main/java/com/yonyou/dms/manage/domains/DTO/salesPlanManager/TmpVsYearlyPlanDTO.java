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
	private Long sumAmt;
	
	@ExcelColumnDefine(value=4)
	@Required
	private Long janAmt;
	
	@ExcelColumnDefine(value=5)
	@Required
	private Long febAmt;
	
	@ExcelColumnDefine(value=6)
	@Required
	private Long marAmt;
	
	@ExcelColumnDefine(value=7)
	@Required
	private Long aprAmt;
	
	@ExcelColumnDefine(value=8)
	@Required
	private Long mayAmount;
	
	@ExcelColumnDefine(value=9)
	@Required
	private Long junAmt;
	
	@ExcelColumnDefine(value=10)
	@Required
	private Long julAmt;
	
	@ExcelColumnDefine(value=11)
	@Required
	private Long augAmt;
	
	@ExcelColumnDefine(value=12)
	@Required
	private Long sepAmt;
	
	@ExcelColumnDefine(value=13)
	@Required
	private Long octAmt;
	
	@ExcelColumnDefine(value=14)
	@Required
	private Long novAmt;
	
	@ExcelColumnDefine(value=15)
	@Required
	private Long decAmt;
	
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

	public Long getSumAmt() {
		return sumAmt;
	}

	public void setSumAmt(Long sumAmt) {
		this.sumAmt = sumAmt;
	}

	public Long getJanAmt() {
		return janAmt;
	}

	public void setJanAmt(Long janAmt) {
		this.janAmt = janAmt;
	}

	public Long getFebAmt() {
		return febAmt;
	}

	public void setFebAmt(Long febAmt) {
		this.febAmt = febAmt;
	}

	public Long getMarAmt() {
		return marAmt;
	}

	public void setMarAmt(Long marAmt) {
		this.marAmt = marAmt;
	}

	public Long getAprAmt() {
		return aprAmt;
	}

	public void setAprAmt(Long aprAmt) {
		this.aprAmt = aprAmt;
	}

	public Long getMayAmount() {
		return mayAmount;
	}

	public void setMayAmount(Long mayAmount) {
		this.mayAmount = mayAmount;
	}

	public Long getJunAmt() {
		return junAmt;
	}

	public void setJunAmt(Long junAmt) {
		this.junAmt = junAmt;
	}

	public Long getJulAmt() {
		return julAmt;
	}

	public void setJulAmt(Long julAmt) {
		this.julAmt = julAmt;
	}

	public Long getAugAmt() {
		return augAmt;
	}

	public void setAugAmt(Long augAmt) {
		this.augAmt = augAmt;
	}

	public Long getSepAmt() {
		return sepAmt;
	}

	public void setSepAmt(Long sepAmt) {
		this.sepAmt = sepAmt;
	}

	public Long getOctAmt() {
		return octAmt;
	}

	public void setOctAmt(Long octAmt) {
		this.octAmt = octAmt;
	}

	public Long getNovAmt() {
		return novAmt;
	}

	public void setNovAmt(Long novAmt) {
		this.novAmt = novAmt;
	}

	public Long getDecAmt() {
		return decAmt;
	}

	public void setDecAmt(Long decAmt) {
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
