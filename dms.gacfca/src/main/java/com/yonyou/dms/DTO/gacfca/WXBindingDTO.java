package com.yonyou.dms.DTO.gacfca;

public class WXBindingDTO {
	private Integer nowYear;// 当前年份
	private Integer nowMonth;// 当前月份
	private Integer inNotBindingNum;// 当月进站客户进站时未绑定的总数
	private Integer outBindingNum;// 当月进站未绑定客户截止月底的绑定总数
	private String bindingRate;// 微信绑定率
	private Integer inNum;// 进厂台次
	private Integer inCustomerNum;// 进厂客户数
	private String entityCode; // 下端：经销商代码 CHAR(8) 上端：
	private String dealerCode;
	

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public Integer getNowYear() {
		return nowYear;
	}

	public void setNowYear(Integer nowYear) {
		this.nowYear = nowYear;
	}

	public Integer getNowMonth() {
		return nowMonth;
	}

	public void setNowMonth(Integer nowMonth) {
		this.nowMonth = nowMonth;
	}

	public Integer getInNotBindingNum() {
		return inNotBindingNum;
	}

	public void setInNotBindingNum(Integer inNotBindingNum) {
		this.inNotBindingNum = inNotBindingNum;
	}

	public Integer getOutBindingNum() {
		return outBindingNum;
	}

	public void setOutBindingNum(Integer outBindingNum) {
		this.outBindingNum = outBindingNum;
	}

	public String getBindingRate() {
		return bindingRate;
	}

	public void setBindingRate(String bindingRate) {
		this.bindingRate = bindingRate;
	}

	public Integer getInNum() {
		return inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}

	public Integer getInCustomerNum() {
		return inCustomerNum;
	}

	public void setInCustomerNum(Integer inCustomerNum) {
		this.inCustomerNum = inCustomerNum;
	}
}
