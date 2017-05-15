package com.yonyou.dms.retail.domains.DTO.rebate;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
/**
 * 
* @ClassName: TtRebateCalculateTmpDTO 
* @Description: 返利核算管理
* @author zhengzengliang 
* @date 2017年3月29日 下午3:07:24 
*
 */
public class TtRebateCalculateTmpDTO  extends DataImportDto{
	
	@ExcelColumnDefine(value=1)
	@Required
	private String businessPolicyName;
	
	@ExcelColumnDefine(value=2)
	@Required
	private String applicableTime;
	
	@ExcelColumnDefine(value=3)
	@Required
	private Date releaseDate;
	
	@ExcelColumnDefine(value=4)
	@Required
	private Date startMonth;
	
	@ExcelColumnDefine(value=5)
	@Required
	private Date endMonth;
	
	@ExcelColumnDefine(value=6)
	@Required
	private String dealerCode;
	
	@ExcelColumnDefine(value=7)
	@Required
	private String dealerName;
	
	@ExcelColumnDefine(value=8)
	@Required
	private String vin;
	
	@ExcelColumnDefine(value=9)
	@Required
	private String modelName;
	
	@ExcelColumnDefine(value=10)
	@Required
	private String count;
	
	@ExcelColumnDefine(value=11)
	@Required
	private String nomalBonus;
	
	@ExcelColumnDefine(value=12)
	@Required
	private String specialBonus;
	
	@ExcelColumnDefine(value=13)
	@Required
	private String backBonusesEst;
	
	@ExcelColumnDefine(value=14)
	@Required
	private String backBonusesDown;
	
	@ExcelColumnDefine(value=15)
	@Required
	private String newIncentives;
	
	

	

	public String getBusinessPolicyName() {
		return businessPolicyName;
	}

	public void setBusinessPolicyName(String businessPolicyName) {
		this.businessPolicyName = businessPolicyName;
	}

	public String getApplicableTime() {
		return applicableTime;
	}

	public void setApplicableTime(String applicableTime) {
		this.applicableTime = applicableTime;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Date getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}

	public Date getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getNomalBonus() {
		return nomalBonus;
	}

	public void setNomalBonus(String nomalBonus) {
		this.nomalBonus = nomalBonus;
	}

	public String getSpecialBonus() {
		return specialBonus;
	}

	public void setSpecialBonus(String specialBonus) {
		this.specialBonus = specialBonus;
	}

	public String getBackBonusesEst() {
		return backBonusesEst;
	}

	public void setBackBonusesEst(String backBonusesEst) {
		this.backBonusesEst = backBonusesEst;
	}

	public String getBackBonusesDown() {
		return backBonusesDown;
	}

	public void setBackBonusesDown(String backBonusesDown) {
		this.backBonusesDown = backBonusesDown;
	}

	public String getNewIncentives() {
		return newIncentives;
	}

	public void setNewIncentives(String newIncentives) {
		this.newIncentives = newIncentives;
	}

	
	

}
