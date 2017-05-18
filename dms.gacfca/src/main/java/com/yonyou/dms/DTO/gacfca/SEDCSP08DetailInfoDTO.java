package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class SEDCSP08DetailInfoDTO {
	private String opWerks;//工厂						
	private String opZzcliente;//Customer code A26					
	private String opMarca;//品牌				
	private String opDealerUsr;//特约店用户					
	private String opVbeln;//销售凭证			
	private Double OpDelCharge;//运费			是	否	输出	输出明细字段“运费”
	private String OpInvvbeln;//Billing Document		是	否	输出	输出明细字段“发票号”
	private String OpInvdate;//Billing Date			是	否	输出	输出明细字段“开票日期”
	private String OpDelvbeln;//Delivery Document			是	否	输出	输出明细字段“运单号”
	private String OpDeldate;//Date on Which Record Was Created			是	否	输出	输出明细字段“运单日期”
	private String OpTxdeldate;//Ship-to Party's Purchase Order NO.						
	private String OpKeytype;//Form Type						
	private String OpOrdstatus;//Order Status					
	private String OpMabnr;//Material Number				是	否	输出	输出明细字段“配件”
	private String OpArktx;//Short text for sales order item		是	否	输出	输出明细字段“描述”
	private Double OpFklmg;//Billing quantity in stockkeeping unit			是	否	输出	输出明细字段“需要数量”
	private Double OpFklmgFga;//Billing quantity in stockkeeping unit				
	private Double OpRetailprice;//Subtotal 1 from pricing procedure			是	否	输出	输出明细字段“订单总额”
	private Double OpDiscount;//Subtotal 2 from pricing procedure			是	否	输出	输出明细字段“折扣”
	private String Posnr;//sales Document item	销售订单行项目号
	private String Posex;//Item Number of the Underlying Purchase Order 是	否	输出	输出明细字段“描述”
	
	private String opInvitem;//发票的行数
	private String opDelitem;//交货单的行数
	public String getOpWerks() {
		return opWerks;
	}
	public void setOpWerks(String opWerks) {
		this.opWerks = opWerks;
	}
	public String getOpZzcliente() {
		return opZzcliente;
	}
	public void setOpZzcliente(String opZzcliente) {
		this.opZzcliente = opZzcliente;
	}
	public String getOpMarca() {
		return opMarca;
	}
	public void setOpMarca(String opMarca) {
		this.opMarca = opMarca;
	}
	public String getOpDealerUsr() {
		return opDealerUsr;
	}
	public void setOpDealerUsr(String opDealerUsr) {
		this.opDealerUsr = opDealerUsr;
	}
	public String getOpVbeln() {
		return opVbeln;
	}
	public void setOpVbeln(String opVbeln) {
		this.opVbeln = opVbeln;
	}
	public Double getOpDelCharge() {
		return OpDelCharge;
	}
	public void setOpDelCharge(Double opDelCharge) {
		OpDelCharge = opDelCharge;
	}
	public String getOpInvvbeln() {
		return OpInvvbeln;
	}
	public void setOpInvvbeln(String opInvvbeln) {
		OpInvvbeln = opInvvbeln;
	}
	public String getOpInvdate() {
		return OpInvdate;
	}
	public void setOpInvdate(String opInvdate) {
		OpInvdate = opInvdate;
	}
	public String getOpDelvbeln() {
		return OpDelvbeln;
	}
	public void setOpDelvbeln(String opDelvbeln) {
		OpDelvbeln = opDelvbeln;
	}
	public String getOpDeldate() {
		return OpDeldate;
	}
	public void setOpDeldate(String opDeldate) {
		OpDeldate = opDeldate;
	}
	public String getOpTxdeldate() {
		return OpTxdeldate;
	}
	public void setOpTxdeldate(String opTxdeldate) {
		OpTxdeldate = opTxdeldate;
	}
	public String getOpKeytype() {
		return OpKeytype;
	}
	public void setOpKeytype(String opKeytype) {
		OpKeytype = opKeytype;
	}
	public String getOpOrdstatus() {
		return OpOrdstatus;
	}
	public void setOpOrdstatus(String opOrdstatus) {
		OpOrdstatus = opOrdstatus;
	}
	public String getOpMabnr() {
		return OpMabnr;
	}
	public void setOpMabnr(String opMabnr) {
		OpMabnr = opMabnr;
	}
	public String getOpArktx() {
		return OpArktx;
	}
	public void setOpArktx(String opArktx) {
		OpArktx = opArktx;
	}
	public Double getOpFklmg() {
		return OpFklmg;
	}
	public void setOpFklmg(Double opFklmg) {
		OpFklmg = opFklmg;
	}
	public Double getOpFklmgFga() {
		return OpFklmgFga;
	}
	public void setOpFklmgFga(Double opFklmgFga) {
		OpFklmgFga = opFklmgFga;
	}
	public Double getOpRetailprice() {
		return OpRetailprice;
	}
	public void setOpRetailprice(Double opRetailprice) {
		OpRetailprice = opRetailprice;
	}
	public Double getOpDiscount() {
		return OpDiscount;
	}
	public void setOpDiscount(Double opDiscount) {
		OpDiscount = opDiscount;
	}
	public String getPosnr() {
		return Posnr;
	}
	public void setPosnr(String posnr) {
		Posnr = posnr;
	}
	public String getPosex() {
		return Posex;
	}
	public void setPosex(String posex) {
		Posex = posex;
	}
	public String getOpInvitem() {
		return opInvitem;
	}
	public void setOpInvitem(String opInvitem) {
		this.opInvitem = opInvitem;
	}
	public String getOpDelitem() {
		return opDelitem;
	}
	public void setOpDelitem(String opDelitem) {
		this.opDelitem = opDelitem;
	}
	

}
