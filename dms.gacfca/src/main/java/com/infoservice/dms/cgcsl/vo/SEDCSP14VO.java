package com.infoservice.dms.cgcsl.vo;

import java.util.Date;

public class SEDCSP14VO extends BaseVO{
	private static final long serialVersionUID = 1L;

	//TB_IN					
	private String imandt;
	private String ivbeln;//交货单号
	
	private Integer iposnr;//交货项目	
	private String iztype;//类型		
	private String isernr;//序列号
	private String ikunnr;//送达方
	
	private String imatnr;//物料号	
	private String imaktx;//物料描述（短文本）
	private Double ilfimg;//实际已交货量（按销售单位）
	private Date ilfdat;//交货日期
	private String ivbelnva;//销售凭证SAP订单号	
	private String iuname;//用户名
	private Date idatum;//日期	
	private String iuzeit;//时间
	private Double ipodqty;//数量
	private String iremark;//备注		
	
	
	
	private String resultInfo;//下端上报，上端返回结果信息
	
	private Integer dealerCredentials;//经销商评价
	private String badRemark;//差评说明
	
	public String getBadRemark() {
		return badRemark;
	}
	public void setBadRemark(String badRemark) {
		this.badRemark = badRemark;
	}
	public Integer getDealerCredentials() {
		return dealerCredentials;
	}
	public void setDealerCredentials(Integer dealerCredentials) {
		this.dealerCredentials = dealerCredentials;
	}
	
	public String getIvbeln() {
		return ivbeln;
	}
	public void setIvbeln(String ivbeln) {
		this.ivbeln = ivbeln;
	}
	public Integer getIposnr() {
		return iposnr;
	}
	public void setIposnr(Integer iposnr) {
		this.iposnr = iposnr;
	}
	public String getIztype() {
		return iztype;
	}
	public void setIztype(String iztype) {
		this.iztype = iztype;
	}
	public String getIsernr() {
		return isernr;
	}
	public void setIsernr(String isernr) {
		this.isernr = isernr;
	}
	public String getIkunnr() {
		return ikunnr;
	}
	public void setIkunnr(String ikunnr) {
		this.ikunnr = ikunnr;
	}
	public String getImatnr() {
		return imatnr;
	}
	public void setImatnr(String imatnr) {
		this.imatnr = imatnr;
	}
	public String getImaktx() {
		return imaktx;
	}
	public void setImaktx(String imaktx) {
		this.imaktx = imaktx;
	}
	public Double getIlfimg() {
		return ilfimg;
	}
	public void setIlfimg(Double ilfimg) {
		this.ilfimg = ilfimg;
	}
	public Date getIlfdat() {
		return ilfdat;
	}
	public void setIlfdat(Date ilfdat) {
		this.ilfdat = ilfdat;
	}
	public String getIvbelnva() {
		return ivbelnva;
	}
	public void setIvbelnva(String ivbelnva) {
		this.ivbelnva = ivbelnva;
	}
	public String getIuname() {
		return iuname;
	}
	public void setIuname(String iuname) {
		this.iuname = iuname;
	}
	public Date getIdatum() {
		return idatum;
	}
	public void setIdatum(Date idatum) {
		this.idatum = idatum;
	}
	public String getIuzeit() {
		return iuzeit;
	}
	public void setIuzeit(String iuzeit) {
		this.iuzeit = iuzeit;
	}
	public Double getIpodqty() {
		return ipodqty;
	}
	public void setIpodqty(Double ipodqty) {
		this.ipodqty = ipodqty;
	}
	public String getIremark() {
		return iremark;
	}
	public void setIremark(String iremark) {
		this.iremark = iremark;
	}
	public String getImandt() {
		return imandt;
	}
	public void setImandt(String imandt) {
		this.imandt = imandt;
	}
	public String getResultInfo() {
		return resultInfo;
	}
	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}
}
