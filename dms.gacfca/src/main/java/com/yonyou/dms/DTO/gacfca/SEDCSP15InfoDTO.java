package com.yonyou.dms.DTO.gacfca;

public class SEDCSP15InfoDTO {
	private String entityCode;//经销商代码
	private String claimNo;//索赔单号
	private String attpath;//附件路径
	private Integer attType;//附件类型
	private String attName;//附件名称
	public String getClaimNo() {
		return claimNo;
	}
	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}
	public String getAttpath() {
		return attpath;
	}
	public void setAttpath(String attpath) {
		this.attpath = attpath;
	}
	public Integer getAttType() {
		return attType;
	}
	public void setAttType(Integer attType) {
		this.attType = attType;
	}
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	
}
