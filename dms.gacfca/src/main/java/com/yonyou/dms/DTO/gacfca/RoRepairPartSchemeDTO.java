package com.yonyou.dms.DTO.gacfca;

public class RoRepairPartSchemeDTO {

	private String partNo; //配件编号

	private String partName; //配件名称

	private float partQuantity; //数量
	
	private Integer ptMum;  //累计更换次数
	
	private Integer warmTimes; // 预警项目次数
	
	private String warnItemName; //项目名称
	
	public Integer getPtMum() {
		return ptMum;
	}

	public void setPtMum(Integer ptMum) {
		this.ptMum = ptMum;
	}

	public Integer getWarmTimes() {
		return warmTimes;
	}

	public void setWarmTimes(Integer warmTimes) {
		this.warmTimes = warmTimes;
	}

	public String getWarnItemName() {
		return warnItemName;
	}

	public void setWarnItemName(String warnItemName) {
		this.warnItemName = warnItemName;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public Float getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Float partQuantity) {
		this.partQuantity = partQuantity;
	}
}
