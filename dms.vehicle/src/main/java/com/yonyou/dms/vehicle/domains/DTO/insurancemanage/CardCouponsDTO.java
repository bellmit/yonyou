package com.yonyou.dms.vehicle.domains.DTO.insurancemanage;

import java.util.List;
import java.util.Map;

public class CardCouponsDTO{
	private String actCode;	//活动编号
	private String actName;  //活动名称
	private String insuranceCompanyCode; //保险公司代码
	private String insCompanyShortName; //保险公司名称
	private String actIssueNumber;     //活动发放卡券数据量
	private String actStartDate;  //有效日期start
	private String actEndDate;   //有效日期end
	private List<Map> cardCouponsTable;
	public List<Map> getCardCouponsTable() {
		return cardCouponsTable;
	}
	public void setCardCouponsTable(List<Map> cardCouponsTable) {
		this.cardCouponsTable = cardCouponsTable;
	}
	public String getActCode() {
		return actCode;
	}
	public void setActCode(String actCode) {
		this.actCode = actCode;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getInsuranceCompanyCode() {
		return insuranceCompanyCode;
	}
	public void setInsuranceCompanyCode(String insuranceCompanyCode) {
		this.insuranceCompanyCode = insuranceCompanyCode;
	}
	public String getInsCompanyShortName() {
		return insCompanyShortName;
	}
	public void setInsCompanyShortName(String insCompanyShortName) {
		this.insCompanyShortName = insCompanyShortName;
	}
	public String getActIssueNumber() {
		return actIssueNumber;
	}
	public void setActIssueNumber(String actIssueNumber) {
		this.actIssueNumber = actIssueNumber;
	}
	public String getActStartDate() {
		return actStartDate;
	}
	public void setActStartDate(String actStartDate) {
		this.actStartDate = actStartDate;
	}
	public String getActEndDate() {
		return actEndDate;
	}
	public void setActEndDate(String actEndDate) {
		this.actEndDate = actEndDate;
	}
	
	
}