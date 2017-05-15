package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

public class MaintainWorkTypeDTO {

	 private String dealerCode;//经销商代码
	 
	 private String labourPositionCode;//维修工位代码
	 
	 private String labourPositionName;//维修工位名称
	 
	 private String labourPositionType;//维修工位类别
     
	 private String workGroupCode;//班组代码
	 
	 private Integer isManyPosition;//是否多车工位
	 
	 private Integer repairCapability;//工位容量

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getLabourPositionCode() {
		return labourPositionCode;
	}

	public void setLabourPositionCode(String labourPositionCode) {
		this.labourPositionCode = labourPositionCode;
	}

	public String getLabourPositionName() {
		return labourPositionName;
	}

	public void setLabourPositionName(String labourPositionName) {
		this.labourPositionName = labourPositionName;
	}

	public String getLabourPositionType() {
		return labourPositionType;
	}

	public void setLabourPositionType(String labourPositionType) {
		this.labourPositionType = labourPositionType;
	}

	public String getWorkGroupCode() {
		return workGroupCode;
	}

	public void setWorkGroupCode(String workGroupCode) {
		this.workGroupCode = workGroupCode;
	}

	public Integer getIsManyPosition() {
		return isManyPosition;
	}

	public void setIsManyPosition(Integer isManyPosition) {
		this.isManyPosition = isManyPosition;
	}

	public Integer getRepairCapability() {
		return repairCapability;
	}

	public void setRepairCapability(Integer repairCapability) {
		this.repairCapability = repairCapability;
	}
	 
}
