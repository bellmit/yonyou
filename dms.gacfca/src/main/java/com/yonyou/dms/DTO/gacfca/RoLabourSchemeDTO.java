package com.yonyou.dms.DTO.gacfca;

public class RoLabourSchemeDTO {
	private String labourCode; //工时代码

	private String labourName;  //工时名称

	private Double stdLabourHour;  //维修工时

	private String remark;  //备注
	
	public String getLabourCode() {
		return labourCode;
	}

	public void setLabourCode(String labourCode) {
		this.labourCode = labourCode;
	}

	public String getLabourName() {
		return labourName;
	}

	public void setLabourName(String labourName) {
		this.labourName = labourName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getStdLabourHour() {
		return stdLabourHour;
	}

	public void setStdLabourHour(Double stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
}
