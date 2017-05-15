package com.yonyou.dms.vehicle.domains.DTO.insurancemanage;

import java.util.Date;

import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TtInsuranceSortDcsDTO extends DataImportDto{
	private String insuranceSortName;
	private Integer oemTag;
	private Long sortId;
	private Date updateDate;
	private String dealerCode;
	private String fileId;
	private Long createBy;
	private String fileUrl;
	private Integer isDown;
	private Integer isComInsurance;
	private String dealerName;
	private String fileName;
	private String insuranceSortCode;
	private Long updateBy;
	private Date createDate;
	private Integer status;
	private Integer sortNum;
	public String getInsuranceSortName() {
		return insuranceSortName;
	}
	public void setInsuranceSortName(String insuranceSortName) {
		this.insuranceSortName = insuranceSortName;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
	}
	public Long getSortId() {
		return sortId;
	}
	public void setSortId(Long sortId) {
		this.sortId = sortId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Integer getIsDown() {
		return isDown;
	}
	public void setIsDown(Integer isDown) {
		this.isDown = isDown;
	}
	public Integer getIsComInsurance() {
		return isComInsurance;
	}
	public void setIsComInsurance(Integer isComInsurance) {
		this.isComInsurance = isComInsurance;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getInsuranceSortCode() {
		return insuranceSortCode;
	}
	public void setInsuranceSortCode(String insuranceSortCode) {
		this.insuranceSortCode = insuranceSortCode;
	}
	public Long getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	
}
