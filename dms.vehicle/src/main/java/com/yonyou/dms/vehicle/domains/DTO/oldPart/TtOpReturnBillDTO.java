package com.yonyou.dms.vehicle.domains.DTO.oldPart;

import java.util.Date;

public class TtOpReturnBillDTO {
	private Date updateDate;
	private String dealerCode;
	private String freightNo;
	private Long createBy;
	private String remark;
	private Date billDate;
	private Long repulseTotal;
	private Long receptionTotal;
	private Integer createType;
	private Integer isDel;
	private String dealerName;
	private Long oemCompanyId;
	private Integer returnBillStatus;
	private Long updateBy;
	private Date despatchDate;
	private String carriage;
	private Integer ver;
	private Long returnBillId;
	private Date createDate;
	private String returnBillNo;
	private Long oldpartTotal;
	private Long returnaddrId;
	private Integer isArc;
	private Double transportCosts;

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setDealerCode(String dealerCode){
		this.dealerCode=dealerCode;
	}

	public String getDealerCode(){
		return this.dealerCode;
	}

	public void setFreightNo(String freightNo){
		this.freightNo=freightNo;
	}

	public String getFreightNo(){
		return this.freightNo;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setBillDate(Date billDate){
		this.billDate=billDate;
	}

	public Date getBillDate(){
		return this.billDate;
	}

	public void setRepulseTotal(Long repulseTotal){
		this.repulseTotal=repulseTotal;
	}

	public Long getRepulseTotal(){
		return this.repulseTotal;
	}

	public void setReceptionTotal(Long receptionTotal){
		this.receptionTotal=receptionTotal;
	}

	public Long getReceptionTotal(){
		return this.receptionTotal;
	}

	public void setCreateType(Integer createType){
		this.createType=createType;
	}

	public Integer getCreateType(){
		return this.createType;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setDealerName(String dealerName){
		this.dealerName=dealerName;
	}

	public String getDealerName(){
		return this.dealerName;
	}

	public void setOemCompanyId(Long oemCompanyId){
		this.oemCompanyId=oemCompanyId;
	}

	public Long getOemCompanyId(){
		return this.oemCompanyId;
	}

	public void setReturnBillStatus(Integer returnBillStatus){
		this.returnBillStatus=returnBillStatus;
	}

	public Integer getReturnBillStatus(){
		return this.returnBillStatus;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setDespatchDate(Date despatchDate){
		this.despatchDate=despatchDate;
	}

	public Date getDespatchDate(){
		return this.despatchDate;
	}

	public void setCarriage(String carriage){
		this.carriage=carriage;
	}

	public String getCarriage(){
		return this.carriage;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setReturnBillId(Long returnBillId){
		this.returnBillId=returnBillId;
	}

	public Long getReturnBillId(){
		return this.returnBillId;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setReturnBillNo(String returnBillNo){
		this.returnBillNo=returnBillNo;
	}

	public String getReturnBillNo(){
		return this.returnBillNo;
	}

	public void setOldpartTotal(Long oldpartTotal){
		this.oldpartTotal=oldpartTotal;
	}

	public Long getOldpartTotal(){
		return this.oldpartTotal;
	}

	public void setReturnaddrId(Long returnaddrId){
		this.returnaddrId=returnaddrId;
	}

	public Long getReturnaddrId(){
		return this.returnaddrId;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setTransportCosts(Double transportCosts){
		this.transportCosts=transportCosts;
	}

	public Double getTransportCosts(){
		return this.transportCosts;
	}
}
