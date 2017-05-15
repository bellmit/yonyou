package com.yonyou.dms.DTO.gacfca;

public class SADMS023Dto {
	private static final long serialVersionUID = 1L;
	
	private String dealerCode; //经销商代码
	
	private String vin; //下端车辆vin
	
	private String soNo; //下端销售订单号
	
	private Integer IdentifyStatus; //下端字典识别状态 17181001：未知  17181002：待识别  17181003：识别成功  17181004：识别失败

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getSoNo() {
		return soNo;
	}

	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}

	public Integer getIdentifyStatus() {
		return IdentifyStatus;
	}

	public void setIdentifyStatus(Integer identifyStatus) {
		IdentifyStatus = identifyStatus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
