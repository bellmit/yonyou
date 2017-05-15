package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class PtDlyInfoDto {
	
	private String deliverynote;//发运单编号
	private Date invoiccreationdate;//发票创建日期
	private String  elinkorderno;//Elink订单号 
	private HashMap<Integer, PtDlyInfoDetailDto> ptDlyInfoDetailList;
	private String dealerCode;
	private Date downTimestamp;
	
	private String  caseId;//Elink箱号
	private Double  quantity;//Elink箱内配件数量
	private LinkedList<PtDlyInfoDetailDto> PtDlyInfoDetailDtos;
	
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public LinkedList<PtDlyInfoDetailDto> getPtDlyInfoDetailDtos() {
		return PtDlyInfoDetailDtos;
	}
	public void setPtDlyInfoDetailDtos(LinkedList<PtDlyInfoDetailDto> ptDlyInfoDetailDtos) {
		PtDlyInfoDetailDtos = ptDlyInfoDetailDtos;
	}
	public String getDeliverynote() {
		return deliverynote;
	}
	public void setDeliverynote(String deliverynote) {
		this.deliverynote = deliverynote;
	}
	public Date getInvoiccreationdate() {
		return invoiccreationdate;
	}
	public void setInvoiccreationdate(Date invoiccreationdate) {
		this.invoiccreationdate = invoiccreationdate;
	}
	public String getElinkorderno() {
		return elinkorderno;
	}
	public void setElinkorderno(String elinkorderno) {
		this.elinkorderno = elinkorderno;
	}
	public HashMap<Integer, PtDlyInfoDetailDto> getPtDlyInfoDetailList() {
		return ptDlyInfoDetailList;
	}
	public void setPtDlyInfoDetailList(HashMap<Integer, PtDlyInfoDetailDto> ptDlyInfoDetailList) {
		this.ptDlyInfoDetailList = ptDlyInfoDetailList;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	
	
}
