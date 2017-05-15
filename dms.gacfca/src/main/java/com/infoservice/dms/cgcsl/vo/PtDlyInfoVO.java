package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.HashMap;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class PtDlyInfoVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	
	private String deliverynote;//发运单编号
	private Date invoiccreationdate;//发票创建日期
	private String  elinkorderno;//Elink订单号 
	private HashMap<Integer, BaseVO> ptDlyInfoDetailList;
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
	public HashMap<Integer, BaseVO> getPtDlyInfoDetailList() {
		return ptDlyInfoDetailList;
	}
	public void setPtDlyInfoDetailList(HashMap<Integer, BaseVO> ptDlyInfoDetailList) {
		this.ptDlyInfoDetailList = ptDlyInfoDetailList;
	}

	
	
//	/** *发货单编号** */
//	private String deliveryOrderNo;
//	/** *发货日期** */
//	private Date deliveryTime;
//	/** *运输方式** */
//	private Integer shippingWay;
//	/** *备注** */
//	private String remark;
//	/** *配件列表** */
//	private HashMap<Integer, BaseVO> dlyInfoItemList;

}
