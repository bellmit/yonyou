package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SEDCSP07DTO {
	// TB_IN
	private String iwerks;// Plant (Own or External)
	private String izzcliente;// 特约店7位编码
	private String imarca;// 品牌
	private String idealerUsr;// 特约店用户
	private String ivbeln;// SD Document Number
	private Date iaudat;// Current Date of Application Server
	private String sign;
	private String option;
	private Date low;
	private Date high;
	private String iauart;
	private String iordstatus;

	// TbOutput
	private String werks;// 工厂
	private String zzcliente;// 特约店7位编码
	private String marca;// 品牌
	private String dealerUsr;// 特约店用户
	// CHAR(10) DMS订单号
	private String vbeln;// 销售和分销凭证号
	private String audat;// 凭证日期 (接收/发送日期)
	private String auart;// 销售凭证类型
	private String orderStatus;// 订单状态
	// CHAR(2) 接货状态
	private String delvryAddr;// 住宅号及街道
	private String invoiceAddr;// 住宅号及街道
	private String respStaff;// Responsible Staff at Dealer
	private String note;// Dealer Note
	private Double delCharge;// Delivery charge
	private String allDate;// 日期

	private String resultInfo;// 下端上报，上端返回结果信息

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

	public String getIvbeln() {
		return ivbeln;
	}

	public void setIvbeln(String ivbeln) {
		this.ivbeln = ivbeln;
	}

	public Date getIaudat() {
		return iaudat;
	}

	public void setIaudat(Date iaudat) {
		this.iaudat = iaudat;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public Date getLow() {
		return low;
	}

	public void setLow(Date low) {
		this.low = low;
	}

	public Date getHigh() {
		return high;
	}

	public void setHigh(Date high) {
		this.high = high;
	}

	public String getIauart() {
		return iauart;
	}

	public void setIauart(String iauart) {
		this.iauart = iauart;
	}

	public String getIordstatus() {
		return iordstatus;
	}

	public void setIordstatus(String iordstatus) {
		this.iordstatus = iordstatus;
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

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getAudat() {
		return audat;
	}

	public void setAudat(String audat) {
		this.audat = audat;
	}

	public String getAuart() {
		return auart;
	}

	public void setAuart(String auart) {
		this.auart = auart;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDelvryAddr() {
		return delvryAddr;
	}

	public void setDelvryAddr(String delvryAddr) {
		this.delvryAddr = delvryAddr;
	}

	public String getInvoiceAddr() {
		return invoiceAddr;
	}

	public void setInvoiceAddr(String invoiceAddr) {
		this.invoiceAddr = invoiceAddr;
	}

	public String getRespStaff() {
		return respStaff;
	}

	public void setRespStaff(String respStaff) {
		this.respStaff = respStaff;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getDelCharge() {
		return delCharge;
	}

	public void setDelCharge(Double delCharge) {
		this.delCharge = delCharge;
	}

	public String getAllDate() {
		return allDate;
	}

	public void setAllDate(String allDate) {
		this.allDate = allDate;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

}
