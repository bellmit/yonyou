package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

public class WsConfigInfoRepayClryslerDto {
	
	private String dealerCode;//经销商代码 	CHAR(8) 
	private String configCode;//配置代码		VARCHAR(30)
	private String seriesCode;//车系代码	VARCHAR(30)
	private String brandCode;//品牌代码	VARCHAR(30)
	private String modelCode;//车型代码	VARCHAR(30)
	private Double vehiclePrice; //车辆价格
	private String soNo;//销售单号
	private String vin;//车架号
	private String fileInvoiceId;//发票ID
	private String fileCardId;//行驶证ID
	private String fileInvoiceUrl;//发票URL
	private String fileCardUrl;//行驶证URL
	private Date vhclDeliveryDate; //  交车日期	 待定
	private Date invoiceDate;   //开票日期  待定
	private String fileCustomerCardId;// 身份证明 (实销车辆的)
	private String fileCustomerCardUrl;// 身份证明(实销车辆的)
	private String fileSaleContractId;// 社保缴纳凭证(实销车辆的)1
	private String fileSaleContractUrl;//社保缴纳凭证(实销车辆的)1
	private String fileSaleContractId1;// 社保缴纳凭证(实销车辆的)2
	private String fileSaleContractUrl1;//社保缴纳凭证(实销车辆的)2
	
	
	private String fileUrlEmpGId; //员工在职证明ID 
	private String fileUrlEmpHId; //公务员专用销售合同原件扫描件ID
	private String fileUrlEmpIId; // 公务员工作证原件扫描件ID

	
	private String fileUrlEmpG;  //员工在职证明URL
	private String fileUrlEmpH;  //公务员专用销售合同原件扫描件url
	private String fileUrlEmpI;  //公务员工作证原件扫描件url
	private String fileUrlEmpH2Id; //公务员专用销售合同原件扫描件ID2
	private String fileUrlEmpH2;  //公务员专用销售合同原件扫描件url2
	private String fileUrlEmpH3Id; //公务员专用销售合同原件扫描件ID3
	private String fileUrlEmpH3;  //公务员专用销售合同原件扫描件url3
	private String fileUrlEmpH4Id; //公务员专用销售合同原件扫描件ID4
	private String fileUrlEmpH4;  //公务员专用销售合同原件扫描件url4
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getSeriesCode() {
		return seriesCode;
	}
	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}
	public String getBrandCode() {
		return brandCode;
	}
	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	public Double getVehiclePrice() {
		return vehiclePrice;
	}
	public void setVehiclePrice(Double vehiclePrice) {
		this.vehiclePrice = vehiclePrice;
	}
	public String getSoNo() {
		return soNo;
	}
	public void setSoNo(String soNo) {
		this.soNo = soNo;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getFileInvoiceId() {
		return fileInvoiceId;
	}
	public void setFileInvoiceId(String fileInvoiceId) {
		this.fileInvoiceId = fileInvoiceId;
	}
	public String getFileCardId() {
		return fileCardId;
	}
	public void setFileCardId(String fileCardId) {
		this.fileCardId = fileCardId;
	}
	public String getFileInvoiceUrl() {
		return fileInvoiceUrl;
	}
	public void setFileInvoiceUrl(String fileInvoiceUrl) {
		this.fileInvoiceUrl = fileInvoiceUrl;
	}
	public String getFileCardUrl() {
		return fileCardUrl;
	}
	public void setFileCardUrl(String fileCardUrl) {
		this.fileCardUrl = fileCardUrl;
	}
	public Date getVhclDeliveryDate() {
		return vhclDeliveryDate;
	}
	public void setVhclDeliveryDate(Date vhclDeliveryDate) {
		this.vhclDeliveryDate = vhclDeliveryDate;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getFileCustomerCardId() {
		return fileCustomerCardId;
	}
	public void setFileCustomerCardId(String fileCustomerCardId) {
		this.fileCustomerCardId = fileCustomerCardId;
	}
	public String getFileCustomerCardUrl() {
		return fileCustomerCardUrl;
	}
	public void setFileCustomerCardUrl(String fileCustomerCardUrl) {
		this.fileCustomerCardUrl = fileCustomerCardUrl;
	}
	public String getFileSaleContractId() {
		return fileSaleContractId;
	}
	public void setFileSaleContractId(String fileSaleContractId) {
		this.fileSaleContractId = fileSaleContractId;
	}
	public String getFileSaleContractUrl() {
		return fileSaleContractUrl;
	}
	public void setFileSaleContractUrl(String fileSaleContractUrl) {
		this.fileSaleContractUrl = fileSaleContractUrl;
	}
	public String getFileSaleContractId1() {
		return fileSaleContractId1;
	}
	public void setFileSaleContractId1(String fileSaleContractId1) {
		this.fileSaleContractId1 = fileSaleContractId1;
	}
	public String getFileSaleContractUrl1() {
		return fileSaleContractUrl1;
	}
	public void setFileSaleContractUrl1(String fileSaleContractUrl1) {
		this.fileSaleContractUrl1 = fileSaleContractUrl1;
	}
	public String getFileUrlEmpGId() {
		return fileUrlEmpGId;
	}
	public void setFileUrlEmpGId(String fileUrlEmpGId) {
		this.fileUrlEmpGId = fileUrlEmpGId;
	}
	public String getFileUrlEmpHId() {
		return fileUrlEmpHId;
	}
	public void setFileUrlEmpHId(String fileUrlEmpHId) {
		this.fileUrlEmpHId = fileUrlEmpHId;
	}
	public String getFileUrlEmpIId() {
		return fileUrlEmpIId;
	}
	public void setFileUrlEmpIId(String fileUrlEmpIId) {
		this.fileUrlEmpIId = fileUrlEmpIId;
	}
	public String getFileUrlEmpG() {
		return fileUrlEmpG;
	}
	public void setFileUrlEmpG(String fileUrlEmpG) {
		this.fileUrlEmpG = fileUrlEmpG;
	}
	public String getFileUrlEmpH() {
		return fileUrlEmpH;
	}
	public void setFileUrlEmpH(String fileUrlEmpH) {
		this.fileUrlEmpH = fileUrlEmpH;
	}
	public String getFileUrlEmpI() {
		return fileUrlEmpI;
	}
	public void setFileUrlEmpI(String fileUrlEmpI) {
		this.fileUrlEmpI = fileUrlEmpI;
	}
	public String getFileUrlEmpH2Id() {
		return fileUrlEmpH2Id;
	}
	public void setFileUrlEmpH2Id(String fileUrlEmpH2Id) {
		this.fileUrlEmpH2Id = fileUrlEmpH2Id;
	}
	public String getFileUrlEmpH2() {
		return fileUrlEmpH2;
	}
	public void setFileUrlEmpH2(String fileUrlEmpH2) {
		this.fileUrlEmpH2 = fileUrlEmpH2;
	}
	public String getFileUrlEmpH3Id() {
		return fileUrlEmpH3Id;
	}
	public void setFileUrlEmpH3Id(String fileUrlEmpH3Id) {
		this.fileUrlEmpH3Id = fileUrlEmpH3Id;
	}
	public String getFileUrlEmpH3() {
		return fileUrlEmpH3;
	}
	public void setFileUrlEmpH3(String fileUrlEmpH3) {
		this.fileUrlEmpH3 = fileUrlEmpH3;
	}
	public String getFileUrlEmpH4Id() {
		return fileUrlEmpH4Id;
	}
	public void setFileUrlEmpH4Id(String fileUrlEmpH4Id) {
		this.fileUrlEmpH4Id = fileUrlEmpH4Id;
	}
	public String getFileUrlEmpH4() {
		return fileUrlEmpH4;
	}
	public void setFileUrlEmpH4(String fileUrlEmpH4) {
		this.fileUrlEmpH4 = fileUrlEmpH4;
	}

}
