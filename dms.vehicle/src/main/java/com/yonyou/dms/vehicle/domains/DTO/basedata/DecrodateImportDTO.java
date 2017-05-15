package com.yonyou.dms.vehicle.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class DecrodateImportDTO extends DataImportDto {
	 // 文件上传的ID
    private String dmsFileIds;
    @ExcelColumnDefine(value = 1)
    private String modelLabourCode;//装潢车型分组代码
    @ExcelColumnDefine(value = 2)
    private String labourCode;//装潢项目代码
    @ExcelColumnDefine(value = 3)
    private String labourName;//装潢项目名称
    @ExcelColumnDefine(value = 4)
    private String repairTypeCode;//装潢类型
    @ExcelColumnDefine(value = 5)
    private String localLabourCode;//行管项目代码
    @ExcelColumnDefine(value = 6)
    private String localLabourName;//行管项目名称
    @ExcelColumnDefine(value = 7)
    private String stdLabourHour;//标准工时
    @ExcelColumnDefine(value = 8)
    private String assignLabourHour;//派工工时
    @ExcelColumnDefine(value = 9)
    private String workerTypeCode;//工种
    @ExcelColumnDefine(value = 10)
    private String operationCode;//索赔项目代码
    @ExcelColumnDefine(value = 11)
    private String claimLabour;//索赔工时
    @ExcelColumnDefine(value = 12)
    private String spellCode;//拼音代码
    @ExcelColumnDefine(value = 13)
    private String repairGroupCode;//装潢项目分组代码
    @ExcelColumnDefine(value = 14)
    private String oemLabourHour;//OEM标准工时
    @ExcelColumnDefine(value = 15, dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer downTag;//是否下发
    @ExcelColumnDefine(value = 16, dataType = ExcelDataType.Dict, dataCode = 1278)
    private Integer replaceStatus;//更新状态
	public Integer getDownTag() {
		return downTag;
	}
	public void setDownTag(Integer downTag) {
		this.downTag = downTag;
	}
	public Integer getReplaceStatus() {
		return replaceStatus;
	}
	public void setReplaceStatus(Integer replaceStatus) {
		this.replaceStatus = replaceStatus;
	}
	public String getDmsFileIds() {
		return dmsFileIds;
	}
	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}
	public String getModelLabourCode() {
		return modelLabourCode;
	}
	public void setModelLabourCode(String modelLabourCode) {
		this.modelLabourCode = modelLabourCode;
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
	public String getRepairTypeCode() {
		return repairTypeCode;
	}
	public void setRepairTypeCode(String repairTypeCode) {
		this.repairTypeCode = repairTypeCode;
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
	public String getClaimLabour() {
		return claimLabour;
	}
	public void setClaimLabour(String claimLabour) {
		this.claimLabour = claimLabour;
	}
	public String getSpellCode() {
		return spellCode;
	}
	public void setSpellCode(String spellCode) {
		this.spellCode = spellCode;
	}
	public String getRepairGroupCode() {
		return repairGroupCode;
	}
	public void setRepairGroupCode(String repairGroupCode) {
		this.repairGroupCode = repairGroupCode;
	}
	public String getOemLabourHour() {
		return oemLabourHour;
	}
	public void setOemLabourHour(String oemLabourHour) {
		this.oemLabourHour = oemLabourHour;
	}


}
