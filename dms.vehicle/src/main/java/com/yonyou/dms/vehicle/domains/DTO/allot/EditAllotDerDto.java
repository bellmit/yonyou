package com.yonyou.dms.vehicle.domains.DTO.allot;

public class EditAllotDerDto {
	private String portLevelTJ;
	private String portLevelSH;
	private String dealerShortNmae;
	private String dealerId;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPortLevelTJ() {
		return portLevelTJ;
	}

	public void setPortLevelTJ(String portLevelTJ) {
		this.portLevelTJ = portLevelTJ;
	}

	public String getPortLevelSH() {
		return portLevelSH;
	}

	public void setPortLevelSH(String portLevelSH) {
		this.portLevelSH = portLevelSH;
	}

	public String getDealerShortNmae() {
		return dealerShortNmae;
	}

	public void setDealerShortNmae(String dealerShortNmae) {
		this.dealerShortNmae = dealerShortNmae;
	}
}
