package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TmpGcsImpDTO  extends DataImportDto{
	@ExcelColumnDefine(value=2)
	private String dealerName;
	@ExcelColumnDefine(value=8)
	private String price;
	@ExcelColumnDefine(value=5)
	@Required
	private String paymentDate;
	@ExcelColumnDefine(value=10)
	private String subtotal;
	@ExcelColumnDefine(value=4)
	private String vin;
	@ExcelColumnDefine(value=7)
	private String partName;
	@ExcelColumnDefine(value=1)
	private String dealerCode;
	@ExcelColumnDefine(value=9)
	private String partCount;
	@ExcelColumnDefine(value=3)
	private String repairNo;
	@ExcelColumnDefine(value=6)
	private String partCode;

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setPrice(String price){
		this.price=price;
	}

	public String getPrice(){
		return this.price;
	}

	public void setPaymentDate(String paymentDate){
		this.paymentDate=paymentDate;
	}

	public String getPaymentDate(){
		return this.paymentDate;
	}

	public void setSubtotal(String subtotal){
		this.subtotal=subtotal;
	}

	public String getSubtotal(){
		return this.subtotal;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

	public void setPartName(String partName){
		this.partName=partName;
	}

	public String getPartName(){
		return this.partName;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setPartCount(String partCount){
		this.partCount=partCount;
	}

	public String getPartCount(){
		return this.partCount;
	}

	public void setRepairNo(String repairNo){
		this.repairNo=repairNo;
	}

	public String getRepairNo(){
		return this.repairNo;
	}

	public void setPartCode(String partCode){
		this.partCode=partCode;
	}

	public String getPartCode(){
		return this.partCode;
	}

}
