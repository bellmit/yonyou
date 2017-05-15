package com.infoeai.eai.vo;

import java.math.BigDecimal;

public class SEDCSP18VO {

	String matnr;	// 物料号
	String werks;	// 工厂
	String lgort;	// 库存地点
	String lgobe;	// 库存地点描述
	BigDecimal qtycom;	// SAP中可用库存数量
	String menis;	// 单位
	
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public String getWerks() {
		return werks;
	}
	public void setWerks(String werks) {
		this.werks = werks;
	}
	public String getLgort() {
		return lgort;
	}
	public void setLgort(String lgort) {
		this.lgort = lgort;
	}
	public String getLgobe() {
		return lgobe;
	}
	public void setLgobe(String lgobe) {
		this.lgobe = lgobe;
	}
	public BigDecimal getQtycom() {
		return qtycom;
	}
	public void setQtycom(BigDecimal qtycom) {
		this.qtycom = qtycom;
	}
	public String getMenis() {
		return menis;
	}
	public void setMenis(String menis) {
		this.menis = menis;
	}
	
}
