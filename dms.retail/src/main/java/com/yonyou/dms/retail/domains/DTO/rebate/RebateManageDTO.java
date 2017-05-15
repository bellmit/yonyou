package com.yonyou.dms.retail.domains.DTO.rebate;

import java.util.Date;
/**
 * 经销商返利核算管理
 * @author zhoushijie
 *
 */
public class RebateManageDTO{
	private Integer businessPolicyType;
	private String startMonth;
	private Date updateDate;
	private String endMonth;
	private Long createBy;
	private Integer isDel;
	private Long logId;
	private Long updateBy;
	private String businessPolicyName;
	private Integer uploadWay;
	private Integer ver;
	private Date createDate;
	private Integer isArc;

	public void setBusinessPolicyType(Integer businessPolicyType){
		this.businessPolicyType=businessPolicyType;
	}

	public Integer getBusinessPolicyType(){
		return this.businessPolicyType;
	}

	public void setStartMonth(String startMonth){
		this.startMonth=startMonth;
	}

	public String getStartMonth(){
		return this.startMonth;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setEndMonth(String endMonth){
		this.endMonth=endMonth;
	}

	public String getEndMonth(){
		return this.endMonth;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setLogId(Long logId){
		this.logId=logId;
	}

	public Long getLogId(){
		return this.logId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setBusinessPolicyName(String businessPolicyName){
		this.businessPolicyName=businessPolicyName;
	}

	public String getBusinessPolicyName(){
		return this.businessPolicyName;
	}

	public void setUploadWay(Integer uploadWay){
		this.uploadWay=uploadWay;
	}

	public Integer getUploadWay(){
		return this.uploadWay;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

}
