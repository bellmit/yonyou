package com.yonyou.dms.vehicle.domains.DTO.orderManager;

public class FurtherOrderRangeSettingDTO {
	private Integer nodeStatus;
	private String[] codeId;

	public String[] getCodeId() {
		return codeId;
	}

	public void setCodeId(String[] codeId) {
		this.codeId = codeId;
	}

	public Integer getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(Integer nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
}
