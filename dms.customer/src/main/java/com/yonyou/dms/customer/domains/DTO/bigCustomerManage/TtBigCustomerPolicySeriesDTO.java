package com.yonyou.dms.customer.domains.DTO.bigCustomerManage;

import java.util.Date;

/**
 * 政策车系定义
 * @author Administrator
 *
 */
public class TtBigCustomerPolicySeriesDTO {
	private Integer isScan;
	private Long bigCustomerPolicyId;
	private Long updateBy;
	private Date updateDate;
	private Integer psType;
	private Long createBy;
	private Integer isDelete;
	private Date createDate;
	private String seriesCode;
	private String brandCode;
	private Integer status;

	public void setIsScan(Integer isScan){
		this.isScan=isScan;
	}

	public Integer getIsScan(){
		return this.isScan;
	}

	public void setBigCustomerPolicyId(Long bigCustomerPolicyId){
		this.bigCustomerPolicyId=bigCustomerPolicyId;
	}

	public Long getBigCustomerPolicyId(){
		return this.bigCustomerPolicyId;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setPsType(Integer psType){
		this.psType=psType;
	}

	public Integer getPsType(){
		return this.psType;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete=isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}

	public void setBrandCode(String brandCode){
		this.brandCode=brandCode;
	}

	public String getBrandCode(){
		return this.brandCode;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}
}
