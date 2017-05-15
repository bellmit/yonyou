package com.yonyou.dms.DTO.gacfca;

import com.infoservice.dms.cgcsl.vo.BaseVO;

@SuppressWarnings("serial")
public class SADCS076DTO extends BaseVO{

	private String recallNo;//召回编号
	private String recallName;//召回名称
	private String vin;//车架号
	private String dealerName;//责任经销商
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}
	public String getRecallName() {
		return recallName;
	}
	public void setRecallName(String recallName) {
		this.recallName = recallName;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	
}
