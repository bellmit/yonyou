package com.yonyou.dms.vehicle.domains.DTO.threePack;

import java.util.Date;

public class TtWrRepairNotSettleReasonDTO {
	private String waitWorkRemark;
	private String waitMaterialPartName;
	private String vin;
	private Date updateDate;
	private String dealerCode;
	private Double waitMaterialPartQuantity;
	private Long createBy;
	private String operator;
	private Date waitMaterialOrderDate;
	private Long repairId;
	private String waitMaterialOrderNo;
	private String waitMaterialRemark;
	private String roNo;
	private Long updateBy;
	private String waitMaterialPartCode;
	private String idDel;
	private String waitWorkReason;
	private Date createDate;
	private Long reasonId;

	public void setWaitWorkRemark(String waitWorkRemark){
		this.waitWorkRemark=waitWorkRemark;
	}

	public String getWaitWorkRemark(){
		return this.waitWorkRemark;
	}

	public void setWaitMaterialPartName(String waitMaterialPartName){
		this.waitMaterialPartName=waitMaterialPartName;
	}

	public String getWaitMaterialPartName(){
		return this.waitMaterialPartName;
	}

	public void setVin(String vin){
		this.vin=vin;
	}

	public String getVin(){
		return this.vin;
	}

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

	public void setWaitMaterialPartQuantity(Double waitMaterialPartQuantity){
		this.waitMaterialPartQuantity=waitMaterialPartQuantity;
	}

	public Double getWaitMaterialPartQuantity(){
		return this.waitMaterialPartQuantity;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setOperator(String operator){
		this.operator=operator;
	}

	public String getOperator(){
		return this.operator;
	}

	public void setWaitMaterialOrderDate(Date waitMaterialOrderDate){
		this.waitMaterialOrderDate=waitMaterialOrderDate;
	}

	public Date getWaitMaterialOrderDate(){
		return this.waitMaterialOrderDate;
	}

	public void setRepairId(Long repairId){
		this.repairId=repairId;
	}

	public Long getRepairId(){
		return this.repairId;
	}

	public void setWaitMaterialOrderNo(String waitMaterialOrderNo){
		this.waitMaterialOrderNo=waitMaterialOrderNo;
	}

	public String getWaitMaterialOrderNo(){
		return this.waitMaterialOrderNo;
	}

	public void setWaitMaterialRemark(String waitMaterialRemark){
		this.waitMaterialRemark=waitMaterialRemark;
	}

	public String getWaitMaterialRemark(){
		return this.waitMaterialRemark;
	}

	public void setRoNo(String roNo){
		this.roNo=roNo;
	}

	public String getRoNo(){
		return this.roNo;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setWaitMaterialPartCode(String waitMaterialPartCode){
		this.waitMaterialPartCode=waitMaterialPartCode;
	}

	public String getWaitMaterialPartCode(){
		return this.waitMaterialPartCode;
	}

	public void setIdDel(String idDel){
		this.idDel=idDel;
	}

	public String getIdDel(){
		return this.idDel;
	}

	public void setWaitWorkReason(String waitWorkReason){
		this.waitWorkReason=waitWorkReason;
	}

	public String getWaitWorkReason(){
		return this.waitWorkReason;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setReasonId(Long reasonId){
		this.reasonId=reasonId;
	}

	public Long getReasonId(){
		return this.reasonId;
	}
}
