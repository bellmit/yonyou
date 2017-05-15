package com.yonyou.dms.DTO.gacfca;

public class LicenseChangeDTO {

	private String newLicense;
	
	private String dealerCode;
	
	private String oldLicense;
	
	private String vin;

	public String getNewLicense() {
		return newLicense;
	}

	public void setNewLicense(String newLicense) {
		this.newLicense = newLicense;
	}

	public String getOldLicense() {
		return oldLicense;
	}

	public void setOldLicense(String oldLicense) {
		this.oldLicense = oldLicense;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	
}
