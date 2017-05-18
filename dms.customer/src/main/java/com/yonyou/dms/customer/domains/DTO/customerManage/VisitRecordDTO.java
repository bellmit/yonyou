package com.yonyou.dms.customer.domains.DTO.customerManage;

public class VisitRecordDTO {
	
	private String soldBy2;//再分配销售顾问下拉框
	
	private String noList;// 隐藏字段 ，用于再分配

	public String getSoldBy2() {
		return soldBy2;
	}

	public void setSoldBy2(String soldBy2) {
		this.soldBy2 = soldBy2;
	}

	public String getNoList() {
		return noList;
	}

	public void setNoList(String noList) {
		this.noList = noList;
	}

}
