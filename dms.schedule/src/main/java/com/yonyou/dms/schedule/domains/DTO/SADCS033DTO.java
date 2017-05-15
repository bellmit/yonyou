/**
 * 
 */
package com.yonyou.dms.schedule.domains.DTO;

import java.util.Date;

/**
 * 上报经销商维度的外乎核实结果固话数据
 * @author Administrator
 *2017-04-05
 */
public class SADCS033DTO {
	
	private static final long serialVersionUID = 1L;
	private String dealerCode;		   //经销商code
	private Integer monthDmsPayCarNum; //当月DMS交车数
	private Integer verifySuccessNum;  //核实成功数
	private Integer verifyFailureNum;  //核实失败数
	private Double verifyPassRate;     //核实通过率 
	private String isStandards;		   //是否达标
	private String notOwner;		   //非机主
	private String notCarOwner;		   //非车主
	private String nullNum;		  	   //空号/错号
	private String busy;		  	   //占线/停机
	private Integer monthBindingNum;    //当月绑定数
	private Double bindingRate;    	   //绑定率
	private String isBindingStandards; //是否达标
	private Date parcarDate;//上报时间
	private String bizYear;			   //业务年
	private String bizMonth;		   //业务月
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Integer getMonthDmsPayCarNum() {
		return monthDmsPayCarNum;
	}
	public void setMonthDmsPayCarNum(Integer monthDmsPayCarNum) {
		this.monthDmsPayCarNum = monthDmsPayCarNum;
	}
	public Integer getVerifySuccessNum() {
		return verifySuccessNum;
	}
	public void setVerifySuccessNum(Integer verifySuccessNum) {
		this.verifySuccessNum = verifySuccessNum;
	}
	public Integer getVerifyFailureNum() {
		return verifyFailureNum;
	}
	public void setVerifyFailureNum(Integer verifyFailureNum) {
		this.verifyFailureNum = verifyFailureNum;
	}
	public Double getVerifyPassRate() {
		return verifyPassRate;
	}
	public void setVerifyPassRate(Double verifyPassRate) {
		this.verifyPassRate = verifyPassRate;
	}
	public String getIsStandards() {
		return isStandards;
	}
	public void setIsStandards(String isStandards) {
		this.isStandards = isStandards;
	}
	public String getNotOwner() {
		return notOwner;
	}
	public void setNotOwner(String notOwner) {
		this.notOwner = notOwner;
	}
	public String getNotCarOwner() {
		return notCarOwner;
	}
	public void setNotCarOwner(String notCarOwner) {
		this.notCarOwner = notCarOwner;
	}
	public String getNullNum() {
		return nullNum;
	}
	public void setNullNum(String nullNum) {
		this.nullNum = nullNum;
	}
	public String getBusy() {
		return busy;
	}
	public void setBusy(String busy) {
		this.busy = busy;
	}
	public Integer getMonthBindingNum() {
		return monthBindingNum;
	}
	public void setMonthBindingNum(Integer monthBindingNum) {
		this.monthBindingNum = monthBindingNum;
	}
	public Double getBindingRate() {
		return bindingRate;
	}
	public void setBindingRate(Double bindingRate) {
		this.bindingRate = bindingRate;
	}
	public String getIsBindingStandards() {
		return isBindingStandards;
	}
	public void setIsBindingStandards(String isBindingStandards) {
		this.isBindingStandards = isBindingStandards;
	}
	public Date getParcarDate() {
		return parcarDate;
	}
	public void setParcarDate(Date parcarDate) {
		this.parcarDate = parcarDate;
	}
	public String getBizYear() {
		return bizYear;
	}
	public void setBizYear(String bizYear) {
		this.bizYear = bizYear;
	}
	public String getBizMonth() {
		return bizMonth;
	}
	public void setBizMonth(String bizMonth) {
		this.bizMonth = bizMonth;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
