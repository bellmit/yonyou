package com.yonyou.dms.vehicle.domains.DTO.allot;

public class ResourceAllotPortDto {
	private String vpcPort;
	private String portLevel;
	private String tmAllotPortId;

	public String getVpcPort() {
		return vpcPort;
	}

	public void setVpcPort(String vpcPort) {
		this.vpcPort = vpcPort;
	}

	public String getPortLevel() {
		return portLevel;
	}

	public void setPortLevel(String portLevel) {
		this.portLevel = portLevel;
	}

	public String getTmAllotPortId() {
		return tmAllotPortId;
	}

	public void setTmAllotPortId(String tmAllotPortId) {
		this.tmAllotPortId = tmAllotPortId;
	}
}
