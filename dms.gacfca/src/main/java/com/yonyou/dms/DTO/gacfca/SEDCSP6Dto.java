package com.yonyou.dms.DTO.gacfca;

import java.util.Date;
import java.util.HashMap;

/**
 * 经销商交货单发送接口
 * @author luoyang
 *
 */
public class SEDCSP6Dto {
	
	private String dealerCode;
	private String entityCode; //下端：经销商代码  CHAR(8)  上端： 
	private String deliverNo;//交货单号
	private String sapOrderNo;//SAP订单编号
	private String dmsOrderNo;//DMS订单编号
	private Double amount;//总价
	private Double netPrice;//净价
	private Double taxAmount;//税额  
	private Double transAmount;//运费
	private String transNo;//运单号
	private String transCompany;//运输公司
	private Date transDate;//运单日期
	private Date arrivedDate;//预计到店日期
	private Date deliverDate;//交货单创建日期
	private HashMap<Integer, SEDCSP6DetailDto> waybillList; //货运单明细
	private String ecOrderNo;//配件订单号 
	private String transType;//运输方式
	
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getEntityCode() {
		return entityCode;
	}
	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}
	public String getDeliverNo() {
		return deliverNo;
	}
	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}
	public String getSapOrderNo() {
		return sapOrderNo;
	}
	public void setSapOrderNo(String sapOrderNo) {
		this.sapOrderNo = sapOrderNo;
	}
	public String getDmsOrderNo() {
		return dmsOrderNo;
	}
	public void setDmsOrderNo(String dmsOrderNo) {
		this.dmsOrderNo = dmsOrderNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getNetPrice() {
		return netPrice;
	}
	public void setNetPrice(Double netPrice) {
		this.netPrice = netPrice;
	}
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public Double getTransAmount() {
		return transAmount;
	}
	public void setTransAmount(Double transAmount) {
		this.transAmount = transAmount;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getTransCompany() {
		return transCompany;
	}
	public void setTransCompany(String transCompany) {
		this.transCompany = transCompany;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public Date getArrivedDate() {
		return arrivedDate;
	}
	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public HashMap<Integer, SEDCSP6DetailDto> getWaybillList() {
		return waybillList;
	}
	public void setWaybillList(HashMap<Integer, SEDCSP6DetailDto> waybillList) {
		this.waybillList = waybillList;
	}
	public String getEcOrderNo() {
		return ecOrderNo;
	}
	public void setEcOrderNo(String ecOrderNo) {
		this.ecOrderNo = ecOrderNo;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	

}
