package com.yonyou.dms.DTO.gacfca;


public class SEDCSP02DTO {
	//TB_IN
	private String iwerks;			   //Plant (Own or External)  3210
	private String izzcliente;         //特约店7位编码 
	private String imarca;			   //品牌 99
	private String idealerUsr;	       //特约店用户
	private String icreditConArea;	   //信用控制范围 GF01
	
	//TbOutput
	private String werks; 	           //工厂
	private String zzcliente;	       //Customer code A26
	private String marca;	           //品牌
	private String dealerUsr;          //特约店用户
	private String creditLimit;
	private String receivable;
	private String salesValue;
	private String creditExposure;     //特约店信用余额
	
	private String resultInfo;//下端上报，上端返回结果信息

	public String getIwerks() {
		return iwerks;
	}

	public void setIwerks(String iwerks) {
		this.iwerks = iwerks;
	}

	public String getIzzcliente() {
		return izzcliente;
	}

	public void setIzzcliente(String izzcliente) {
		this.izzcliente = izzcliente;
	}

	public String getImarca() {
		return imarca;
	}

	public void setImarca(String imarca) {
		this.imarca = imarca;
	}

	public String getIdealerUsr() {
		return idealerUsr;
	}

	public void setIdealerUsr(String idealerUsr) {
		this.idealerUsr = idealerUsr;
	}

	public String getIcreditConArea() {
		return icreditConArea;
	}

	public void setIcreditConArea(String icreditConArea) {
		this.icreditConArea = icreditConArea;
	}

	public String getWerks() {
		return werks;
	}

	public void setWerks(String werks) {
		this.werks = werks;
	}

	public String getZzcliente() {
		return zzcliente;
	}

	public void setZzcliente(String zzcliente) {
		this.zzcliente = zzcliente;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getDealerUsr() {
		return dealerUsr;
	}

	public void setDealerUsr(String dealerUsr) {
		this.dealerUsr = dealerUsr;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getReceivable() {
		return receivable;
	}

	public void setReceivable(String receivable) {
		this.receivable = receivable;
	}

	public String getSalesValue() {
		return salesValue;
	}

	public void setSalesValue(String salesValue) {
		this.salesValue = salesValue;
	}

	public String getCreditExposure() {
		return creditExposure;
	}

	public void setCreditExposure(String creditExposure) {
		this.creditExposure = creditExposure;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}


}
