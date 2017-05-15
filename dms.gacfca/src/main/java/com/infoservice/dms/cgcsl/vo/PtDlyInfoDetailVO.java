package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class PtDlyInfoDetailVO extends BaseVO {
	private static final long serialVersionUID = 1L;
    private Integer deliverynoteItem;//货运单序号
	private Double sapinvoicequantity;//配件数量
	private Double netprice;//销售单价（不含税）下端订货价
	private Double valueofgoods;//销售总价
	private String partno;//配件代码
	private String  partname;//配件名称
	private Date downTimestamp;
	private String dealerCode;
	
	
	public Integer getDeliverynoteItem() {
		return deliverynoteItem;
	}
	public Date getDownTimestamp() {
		return downTimestamp;
	}
	public void setDownTimestamp(Date downTimestamp) {
		this.downTimestamp = downTimestamp;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public void setDeliverynoteItem(Integer deliverynoteItem) {
		this.deliverynoteItem = deliverynoteItem;
	}
	public Double getSapinvoicequantity() {
		return sapinvoicequantity;
	}
	public void setSapinvoicequantity(Double sapinvoicequantity) {
		this.sapinvoicequantity = sapinvoicequantity;
	}
	public Double getNetprice() {
		return netprice;
	}
	public void setNetprice(Double netprice) {
		this.netprice = netprice;
	}
	public Double getValueofgoods() {
		return valueofgoods;
	}
	public void setValueofgoods(Double valueofgoods) {
		this.valueofgoods = valueofgoods;
	}
	public String getPartno() {
		return partno;
	}
	public void setPartno(String partno) {
		this.partno = partno;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	
	
}
