package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.List;
import java.util.Map;

public class OrderRegisterDTO {

	private String opAuart;
	
	private String opVbeln;
	
	private List<Map> dms_table;

	public String getOpAuart() {
		return opAuart;
	}

	public void setOpAuart(String opAuart) {
		this.opAuart = opAuart;
	}

	public String getOpVbeln() {
		return opVbeln;
	}

	public void setOpVbeln(String opVbeln) {
		this.opVbeln = opVbeln;
	}

	public List<Map> getDms_table() {
		return dms_table;
	}

	public void setDms_table(List<Map> dms_table) {
		this.dms_table = dms_table;
	}
	
}
