package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

import com.infoservice.dms.cgcsl.vo.BaseVO;

public class SEDCSP01VO extends BaseVO{
	private static final long serialVersionUID = 1L;
	//TB_IN
	private String iwerks;			//Plant (Own or External)
	private String izzcliente;		//特约店7位编码
	private String imarca;			//品牌
	private String idealerUsr;		//特约店用户
	private String Iauart;			//订单类型
	private String ivhvin;			//VIN
	private String ielcode;			//电子编码
	private String imecode;			//机械代码
	private String isubstitute;		//是否获取替代件
	private LinkedList<sedcsP01PartInfoVO> partBaseInfoList;//配件信息
	
	//TbOutput
	private String werks;//工厂
	private String zzcliente;//特约店7位编码
	private String marca;//品牌
	private String dealerUsr;//特约店用户
	private String auart;//订单类型
	private String vhvin;//VIN
	private String elcode;//电子编码
	private String mecode;//机械代码
	private String substitute;//获取替代件
	private String matnr;//Material Number
	private String maktx;//Material Description (Short Text)
	private String arktx;//零部件名称
	private String vormg;//最小订货批量
	private String qtyin;//传入参数中的需求量
	private String qtyreq;//按照最小订货批量折算后的计划量
	private String qtycom;//SAP中库存量
	private String isStock;//是否有库存
	private String hasChange;//是否有替换件
	private Date dtest;//预估日期
	private String price;//不含税终端销售价
	private String discount;//单个折扣
	private String opNetwr;//不含税单价
	private String opTotal;//不含税总额
	private String netwr;//含税单价
	private String total;//含税总额
	private String matnrDms;//替换前的物料代码
	private String matnrSap;//替换后的物料代码
	
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

	public String getIauart() {
		return Iauart;
	}

	public void setIauart(String iauart) {
		Iauart = iauart;
	}

	public String getIvhvin() {
		return ivhvin;
	}

	public void setIvhvin(String ivhvin) {
		this.ivhvin = ivhvin;
	}

	public String getIelcode() {
		return ielcode;
	}

	public void setIelcode(String ielcode) {
		this.ielcode = ielcode;
	}

	public String getImecode() {
		return imecode;
	}

	public void setImecode(String imecode) {
		this.imecode = imecode;
	}

	public String getIsubstitute() {
		return isubstitute;
	}

	public void setIsubstitute(String isubstitute) {
		this.isubstitute = isubstitute;
	}

	public LinkedList<sedcsP01PartInfoVO> getPartBaseInfoList() {
		return partBaseInfoList;
	}

	public void setPartBaseInfoList(LinkedList<sedcsP01PartInfoVO> partBaseInfoList) {
		this.partBaseInfoList = partBaseInfoList;
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

	public String getAuart() {
		return auart;
	}

	public void setAuart(String auart) {
		this.auart = auart;
	}

	public String getVhvin() {
		return vhvin;
	}

	public void setVhvin(String vhvin) {
		this.vhvin = vhvin;
	}

	public String getElcode() {
		return elcode;
	}

	public void setElcode(String elcode) {
		this.elcode = elcode;
	}

	public String getMecode() {
		return mecode;
	}

	public void setMecode(String mecode) {
		this.mecode = mecode;
	}

	public String getSubstitute() {
		return substitute;
	}

	public void setSubstitute(String substitute) {
		this.substitute = substitute;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getArktx() {
		return arktx;
	}

	public void setArktx(String arktx) {
		this.arktx = arktx;
	}

	public String getVormg() {
		return vormg;
	}

	public void setVormg(String vormg) {
		this.vormg = vormg;
	}

	public String getQtyin() {
		return qtyin;
	}

	public void setQtyin(String qtyin) {
		this.qtyin = qtyin;
	}

	public String getQtyreq() {
		return qtyreq;
	}

	public void setQtyreq(String qtyreq) {
		this.qtyreq = qtyreq;
	}

	public String getQtycom() {
		return qtycom;
	}

	public void setQtycom(String qtycom) {
		this.qtycom = qtycom;
	}

	public String getIsStock() {
		return isStock;
	}

	public void setIsStock(String isStock) {
		this.isStock = isStock;
	}

	public String getHasChange() {
		return hasChange;
	}

	public void setHasChange(String hasChange) {
		this.hasChange = hasChange;
	}

	public Date getDtest() {
		return dtest;
	}

	public void setDtest(Date dtest) {
		this.dtest = dtest;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getOpNetwr() {
		return opNetwr;
	}

	public void setOpNetwr(String opNetwr) {
		this.opNetwr = opNetwr;
	}

	public String getOpTotal() {
		return opTotal;
	}

	public void setOpTotal(String opTotal) {
		this.opTotal = opTotal;
	}

	public String getNetwr() {
		return netwr;
	}

	public void setNetwr(String netwr) {
		this.netwr = netwr;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMatnrDms() {
		return matnrDms;
	}

	public void setMatnrDms(String matnrDms) {
		this.matnrDms = matnrDms;
	}

	public String getMatnrSap() {
		return matnrSap;
	}

	public void setMatnrSap(String matnrSap) {
		this.matnrSap = matnrSap;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

}
