package com.yonyou.dms.vehicle.domains.DTO.basedata;

import java.util.List;

public class DecrodateProjectManageDTO {
	private List<String>  modelLabourCode;
	private String  labourCode;// 维修项目代码
	private String  labourName;//维修项目名称
	private String  localLabourCode;// 行管项目代码;
	private String  localLabourName;// 行管项目名称
	private String  repairGroupCode;//  维修项目类别
	private String  stdLabourHour;//标准工时
	private String  assignLabourHour; //派工工时
	private String  workerTypeCode;//工种
	private String  operationCode;//索赔代码
	private String  spellCode;//拼音代码
	private String  repairTypeCode;//維修類型
	private String  claimLabour;//索賠工時
	private String  isMember;//是否会员专用
	private String mainGroupCode;//主分类名称
	private String subGroupCode;//二级分类名称
	private String codeFrom;
	
	public String getCodeFrom() {
		return codeFrom;
	}
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}
	public List<String> getModelLabourCode() {
		return modelLabourCode;
	}
	public void setModelLabourCode(List<String> modelLabourCode) {
		this.modelLabourCode = modelLabourCode;
	}
	public String getMainGroupCode() {
		return mainGroupCode;
	}
	public void setMainGroupCode(String mainGroupCode) {
		this.mainGroupCode = mainGroupCode;
	}
	public String getSubGroupCode() {
		return subGroupCode;
	}
	public void setSubGroupCode(String subGroupCode) {
		this.subGroupCode = subGroupCode;
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
	public String getLocalLabourCode() {
		return localLabourCode;
	}
	public void setLocalLabourCode(String localLabourCode) {
		this.localLabourCode = localLabourCode;
	}
	public String getLocalLabourName() {
		return localLabourName;
	}
	public void setLocalLabourName(String localLabourName) {
		this.localLabourName = localLabourName;
	}
	public String getRepairGroupCode() {
		return repairGroupCode;
	}
	public void setRepairGroupCode(String repairGroupCode) {
		this.repairGroupCode = repairGroupCode;
	}
	public String getStdLabourHour() {
		return stdLabourHour;
	}
	public void setStdLabourHour(String stdLabourHour) {
		this.stdLabourHour = stdLabourHour;
	}
	public String getAssignLabourHour() {
		return assignLabourHour;
	}
	public void setAssignLabourHour(String assignLabourHour) {
		this.assignLabourHour = assignLabourHour;
	}
	public String getWorkerTypeCode() {
		return workerTypeCode;
	}
	public void setWorkerTypeCode(String workerTypeCode) {
		this.workerTypeCode = workerTypeCode;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getSpellCode() {
		return spellCode;
	}
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
	}
	public String getClaimLabour() {
		return claimLabour;
	}
	public void setClaimLabour(String claimLabour) {
		this.claimLabour = claimLabour;
	}
	public String getIsMember() {
		return isMember;
	}
	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}
	
}
