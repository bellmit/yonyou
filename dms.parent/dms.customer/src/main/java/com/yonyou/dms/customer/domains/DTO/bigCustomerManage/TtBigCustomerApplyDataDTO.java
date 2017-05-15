package com.yonyou.dms.customer.domains.DTO.bigCustomerManage;

import java.util.Date;


/**
 * 政策申请数据定义
 * @author Administrator
 *
 */
public class TtBigCustomerApplyDataDTO {
	private Integer isScan;
	private Long updated_by;
	private Date updateDate;
	private Integer psType;
	private Long bigCustomerApplyId;
	private Long createBy;
	private Integer employeeType;
	private Integer isDelete;
	private Date createDate;
	private Integer status;
	private Integer number;
	

	public Integer getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Integer employeeType) {
		this.employeeType = employeeType;
	}

	public void setIsScan(Integer isScan){
		this.isScan=isScan;
	}

	public Integer getIsScan(){
		return this.isScan;
	}

/*	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}*/

	public Long getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Long updated_by) {
		this.updated_by = updated_by;
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

	public void setBigCustomerApplyId(Long bigCustomerApplyId){
		this.bigCustomerApplyId=bigCustomerApplyId;
	}

	public Long getBigCustomerApplyId(){
		return this.bigCustomerApplyId;
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

	public void setStatus(Integer status){
		this.status=status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setNumber(Integer number){
		this.number=number;
	}

	public Integer getNumber(){
		return this.number;
	}

}
