package com.yonyou.dms.DTO.gacfca;

import java.util.LinkedList;

public class SEDCSP08DTO {

	//TB_IN					
	private String iwerks;//Plant (Own or External)
	private String izzcliente;//特约店7位编码
	private String imarca;//品牌
	private String idealerUsr;//特约店用户	
	private LinkedList<SEDCSP08NoteInfoDTO> tbVbeln;//运单号		
	
	//TbOutputHeader			
	private String opWerks;//工厂
	private String opZzcliente;//Customer code A26
	private String opMarca;//品牌
	private String opDealerUsr;//特约店用户
	private String opVbeln;//销售凭证
	private String opAudat;//凭证日期 (接收/发送日期)
	private String opAuart;//销售凭证类型
	private String opOrdstatus;//订单状态
	private String opShipto;//Address
	private String opBillto;//Address
	private String opRespstaff;//负责人
	private String opNote;//备注
	private String opWarrDate;//保修日期
	private String opVinCode;//Vehicle Identification Number
	private String opElecCode;//Electric Code
	private String opMechCode;//Mechanical Code
	private String opPlate;//Description related to plate
	private String opLocDate;//Location date
	private String opDelivery;//Insurance type
	private String opRefNumber;//Customer purchase order number
	private LinkedList<SEDCSP08DetailInfoDTO> detailInfo;//运单号		
	
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

	public LinkedList<SEDCSP08NoteInfoDTO> getTbVbeln() {
		return tbVbeln;
	}

	public void setTbVbeln(LinkedList<SEDCSP08NoteInfoDTO> tbVbeln) {
		this.tbVbeln = tbVbeln;
	}

	public void setDetailInfo(LinkedList<SEDCSP08DetailInfoDTO> detailInfo) {
		this.detailInfo = detailInfo;
	}

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

	public String getOpAudat() {
		return opAudat;
	}

	public void setOpAudat(String opAudat) {
		this.opAudat = opAudat;
	}

	public String getOpAuart() {
		return opAuart;
	}

	public void setOpAuart(String opAuart) {
		this.opAuart = opAuart;
	}

	public String getOpOrdstatus() {
		return opOrdstatus;
	}

	public void setOpOrdstatus(String opOrdstatus) {
		this.opOrdstatus = opOrdstatus;
	}

	public String getOpShipto() {
		return opShipto;
	}

	public void setOpShipto(String opShipto) {
		this.opShipto = opShipto;
	}

	public String getOpBillto() {
		return opBillto;
	}

	public void setOpBillto(String opBillto) {
		this.opBillto = opBillto;
	}

	public String getOpRespstaff() {
		return opRespstaff;
	}

	public void setOpRespstaff(String opRespstaff) {
		this.opRespstaff = opRespstaff;
	}

	public String getOpNote() {
		return opNote;
	}

	public void setOpNote(String opNote) {
		this.opNote = opNote;
	}

	public String getOpWarrDate() {
		return opWarrDate;
	}

	public void setOpWarrDate(String opWarrDate) {
		this.opWarrDate = opWarrDate;
	}

	public String getOpVinCode() {
		return opVinCode;
	}

	public void setOpVinCode(String opVinCode) {
		this.opVinCode = opVinCode;
	}

	public String getOpElecCode() {
		return opElecCode;
	}

	public void setOpElecCode(String opElecCode) {
		this.opElecCode = opElecCode;
	}

	public String getOpMechCode() {
		return opMechCode;
	}

	public void setOpMechCode(String opMechCode) {
		this.opMechCode = opMechCode;
	}

	public String getOpPlate() {
		return opPlate;
	}

	public void setOpPlate(String opPlate) {
		this.opPlate = opPlate;
	}

	public String getOpLocDate() {
		return opLocDate;
	}

	public void setOpLocDate(String opLocDate) {
		this.opLocDate = opLocDate;
	}

	public String getOpDelivery() {
		return opDelivery;
	}

	public void setOpDelivery(String opDelivery) {
		this.opDelivery = opDelivery;
	}

	public String getOpRefNumber() {
		return opRefNumber;
	}

	public void setOpRefNumber(String opRefNumber) {
		this.opRefNumber = opRefNumber;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public LinkedList<SEDCSP08DetailInfoDTO> getDetailInfo() {
		return detailInfo;
	}
}
