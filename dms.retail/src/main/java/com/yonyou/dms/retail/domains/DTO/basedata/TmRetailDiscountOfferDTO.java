/*
* Copyright (c) 2005 Infoservice, Inc. All  Rights Reserved.
* This software is published under the terms of the Infoservice Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* CreateDate : 2016-04-08 11:18:29
* CreateBy   : Administrator
* Comment    : generate by com.infoservice.po.POGen
*/

package com.yonyou.dms.retail.domains.DTO.basedata;

import java.util.Date;

public class TmRetailDiscountOfferDTO {

	private Double merchantFeesRate;
	private String ctmName;
	private Integer installMentNum;
	private Double merchantFees;
	private Long createBy;
	private Date applyDate;
	private Double netPrice;
	private Double firstPermentRatio;
	private Long bank;
	private Date dealDate;
	private Integer isBilling;
	private Long id;
	private String discountDealerCode;
	private Integer isEdit;
	private Date createDate;
	private Double dealAmount;
	private Long reportId;

	public void setMerchantFeesRate(Double merchantFeesRate){
		this.merchantFeesRate=merchantFeesRate;
	}

	public Double getMerchantFeesRate(){
		return this.merchantFeesRate;
	}

	public void setCtmName(String ctmName){
		this.ctmName=ctmName;
	}

	public String getCtmName(){
		return this.ctmName;
	}

	public void setInstallMentNum(Integer installMentNum){
		this.installMentNum=installMentNum;
	}

	public Integer getInstallMentNum(){
		return this.installMentNum;
	}

	public void setMerchantFees(Double merchantFees){
		this.merchantFees=merchantFees;
	}

	public Double getMerchantFees(){
		return this.merchantFees;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setApplyDate(Date applyDate){
		this.applyDate=applyDate;
	}

	public Date getApplyDate(){
		return this.applyDate;
	}

	public void setNetPrice(Double netPrice){
		this.netPrice=netPrice;
	}

	public Double getNetPrice(){
		return this.netPrice;
	}

	public void setFirstPermentRatio(Double firstPermentRatio){
		this.firstPermentRatio=firstPermentRatio;
	}

	public Double getFirstPermentRatio(){
		return this.firstPermentRatio;
	}

	public void setBank(Long bank){
		this.bank=bank;
	}

	public Long getBank(){
		return this.bank;
	}

	public void setDealDate(Date dealDate){
		this.dealDate=dealDate;
	}

	public Date getDealDate(){
		return this.dealDate;
	}

	public void setIsBilling(Integer isBilling){
		this.isBilling=isBilling;
	}

	public Integer getIsBilling(){
		return this.isBilling;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setDiscountDealerCode(String discountDealerCode){
		this.discountDealerCode=discountDealerCode;
	}

	public String getDiscountDealerCode(){
		return this.discountDealerCode;
	}

	public void setIsEdit(Integer isEdit){
		this.isEdit=isEdit;
	}

	public Integer getIsEdit(){
		return this.isEdit;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setDealAmount(Double dealAmount){
		this.dealAmount=dealAmount;
	}

	public Double getDealAmount(){
		return this.dealAmount;
	}

	public void setReportId(Long reportId){
		this.reportId=reportId;
	}

	public Long getReportId(){
		return this.reportId;
	}

}