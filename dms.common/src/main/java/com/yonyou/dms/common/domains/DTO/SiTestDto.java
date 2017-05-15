package com.yonyou.dms.common.domains.DTO;

public class SiTestDto {
	
	//SADMS065
	private String vinList;
	
	//SEDCS063 呆滞品定义下发接口
	private String id;
	
	//SEDMS066
	private String outWarehousNos;
	
	//SEDMS068
	private String allocateOutNo;
	
	//SI25
	private String vin;
	
	//SI37
	private String in;
	
	private String dduPath;
	

	public String getDduPath() {
		return dduPath;
	}

	public void setDduPath(String dduPath) {
		this.dduPath = dduPath;
	}

	public String getAllocateOutNo() {
		return allocateOutNo;
	}

	public void setAllocateOutNo(String allocateOutNo) {
		this.allocateOutNo = allocateOutNo;
	}

	public String getOutWarehousNos() {
		return outWarehousNos;
	}

	public void setOutWarehousNos(String outWarehousNos) {
		this.outWarehousNos = outWarehousNos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVinList() {
		return vinList;
	}

	public void setVinList(String vinList) {
		this.vinList = vinList;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}
	
	

}
