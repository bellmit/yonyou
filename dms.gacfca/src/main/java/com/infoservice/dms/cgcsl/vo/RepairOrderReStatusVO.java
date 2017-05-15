package com.infoservice.dms.cgcsl.vo;

import com.infoservice.dms.cgcsl.vo.BaseVO;

@SuppressWarnings("serial")
public class RepairOrderReStatusVO extends BaseVO{
	private String roNo;
	private Integer roStatus;//下端："12551011 取消结算" 上端："40021001; //未结算"
	public String getRoNo() {
		return roNo;
	}
	public void setRoNo(String roNo) {
		this.roNo = roNo;
	}
	public Integer getRoStatus() {
		return roStatus;
	}
	public void setRoStatus(Integer roStatus) {
		this.roStatus = roStatus;
	}
}
