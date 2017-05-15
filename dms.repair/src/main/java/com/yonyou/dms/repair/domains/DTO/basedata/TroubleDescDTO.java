package com.yonyou.dms.repair.domains.DTO.basedata;

import java.util.Date;

public class TroubleDescDTO {

	 private String dealerCode;//经销商代码
	 
	 private String troubleCode;//故障代码
	 
	 private String troubleGroup;//故障分类
	 
	 private String troubleSpell;//故障拼音
     
	 private String troubleDesc;//故障描述
	 
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getTroubleCode() {
		return troubleCode;
	}

	public void setTroubleCode(String troubleCode) {
		this.troubleCode = troubleCode;
	}

	public String getTroubleGroup() {
		return troubleGroup;
	}

	public void setTroubleGroup(String troubleGroup) {
		this.troubleGroup = troubleGroup;
	}

	public String getTroubleSpell() {
		return troubleSpell;
	}

	public void setTroubleSpell(String troubleSpell) {
		this.troubleSpell = troubleSpell;
	}

	public String getTroubleDesc() {
		return troubleDesc;
	}

	public void setTroubleDesc(String troubleDesc) {
		this.troubleDesc = troubleDesc;
	}

}
