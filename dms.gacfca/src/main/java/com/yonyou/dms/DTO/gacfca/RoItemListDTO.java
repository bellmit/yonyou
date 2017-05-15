package com.yonyou.dms.DTO.gacfca;

/**
 * @description　配件基本信息Dto
 * @author Administrator
 *
 */
public class RoItemListDTO {
	private String entityCode;//经销商代码
	private String labourCode;//工时代码
	private String labourName;//工时名称
    private Double labourNum;//索赔工时数
    private String modelLabourCode;//维修车型组代码
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
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
	public Double getLabourNum() {
		return labourNum;
	}
	public void setLabourNum(Double labourNum) {
		this.labourNum = labourNum;
	}
	public String getModelLabourCode() {
		return modelLabourCode;
	}
	public void setModelLabourCode(String modelLabourCode) {
		this.modelLabourCode = modelLabourCode;
	}
}
