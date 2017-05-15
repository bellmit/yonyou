package com.yonyou.dms.vehicle.domains.DTO.insurancemanage;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtInsuranceCompanyMainDcsDTO extends DataImportDto{
	
	private Integer isDomn;
	private Integer oemTag;
	private Date updateDate;
	private String dealerCode;
	private String fileId;
	private String remark;
	private String insuranceCompanyName;
	private String insCompanyShortName;
	private Long createBy;
	private String fileUrl;
	private String dealerName;
	private Long insCompanyId;
	private String insuranceCompanyCode;
	private String fileName;
	private String execAuthor;
	private Long updateBy;
	private Date createDate;
	private Integer isCoInsuranceCompany;
	public Integer getIsDomn() {
		return isDomn;
	}
	public void setIsDomn(Integer isDomn) {
		this.isDomn = isDomn;
	}
	public Integer getOemTag() {
		return oemTag;
	}
	public void setOemTag(Integer oemTag) {
		this.oemTag = oemTag;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getInsCompanyShortName() {
		return insCompanyShortName;
	}
	public void setInsCompanyShortName(String insCompanyShortName) {
		this.insCompanyShortName = insCompanyShortName;
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
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public Long getInsCompanyId() {
		return insCompanyId;
	}
	public void setInsCompanyId(Long insCompanyId) {
		this.insCompanyId = insCompanyId;
	}
	public String getInsuranceCompanyCode() {
		return insuranceCompanyCode;
	}
	public void setInsuranceCompanyCode(String insuranceCompanyCode) {
		this.insuranceCompanyCode = insuranceCompanyCode;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getExecAuthor() {
		return execAuthor;
	}
	public void setExecAuthor(String execAuthor) {
		this.execAuthor = execAuthor;
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
	public Integer getIsCoInsuranceCompany() {
		return isCoInsuranceCompany;
	}
	public void setIsCoInsuranceCompany(Integer isCoInsuranceCompany) {
		this.isCoInsuranceCompany = isCoInsuranceCompany;
	}
	
}
