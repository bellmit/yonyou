package com.yonyou.dms.manage.domains.DTO.basedata.organization;

/**
 * 用于组织管理  经销商公司维护  新增修改
 * @author Administrator
 *
 */
public class DealerInfoDTO {
	private Long companyId;
	private String dlrCode;				//公司代码
	private String dlrName;				//公司名称
	private String dlrNameForShort;		//公司简称
	private String companyEn;			//英文名称
	private Integer companyType;		//公司类型
	private Long province;				//省份
	private Long city;					//城市
	private String contTel;				//联系电话
	private String zipCode;				//邮编
	private String fax;					//传真
	private String detailAddr;			//详细地址
	private Integer status;				//状态
	private String ctciCode;			//CTCI经销商代码
	private String swtCode;				//SWT经销商代码
	private String elinkCode;			//E-Link经销商代码
	private String dcCode;				//Dealer-Connect经销商代码
	private String lmsCode;				//LMS经销商代码
	private String jecCode;				//JEC经销商代码
	private String fcaCode;				//FCA经销商代码
	
	
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getDlrCode() {
		return dlrCode;
	}
	public void setDlrCode(String dlrCode) {
		this.dlrCode = dlrCode;
	}
	public String getDlrName() {
		return dlrName;
	}
	public void setDlrName(String dlrName) {
		this.dlrName = dlrName;
	}
	public String getDlrNameForShort() {
		return dlrNameForShort;
	}
	public void setDlrNameForShort(String dlrNameForShort) {
		this.dlrNameForShort = dlrNameForShort;
	}
	public String getCompanyEn() {
		return companyEn;
	}
	public void setCompanyEn(String companyEn) {
		this.companyEn = companyEn;
	}
	public Integer getCompanyType() {
		return companyType;
	}
	public void setCompanyType(Integer companyType) {
		this.companyType = companyType;
	}
	public Long getProvince() {
		return province;
	}
	public void setProvince(Long province) {
		this.province = province;
	}
	public Long getCity() {
		return city;
	}
	public void setCity(Long city) {
		this.city = city;
	}
	public String getContTel() {
		return contTel;
	}
	public void setContTel(String contTel) {
		this.contTel = contTel;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getDetailAddr() {
		return detailAddr;
	}
	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCtciCode() {
		return ctciCode;
	}
	public void setCtciCode(String ctciCode) {
		this.ctciCode = ctciCode;
	}
	public String getSwtCode() {
		return swtCode;
	}
	public void setSwtCode(String swtCode) {
		this.swtCode = swtCode;
	}
	public String getElinkCode() {
		return elinkCode;
	}
	public void setElinkCode(String elinkCode) {
		this.elinkCode = elinkCode;
	}
	public String getDcCode() {
		return dcCode;
	}
	public void setDcCode(String dcCode) {
		this.dcCode = dcCode;
	}
	public String getLmsCode() {
		return lmsCode;
	}
	public void setLmsCode(String lmsCode) {
		this.lmsCode = lmsCode;
	}
	public String getJecCode() {
		return jecCode;
	}
	public void setJecCode(String jecCode) {
		this.jecCode = jecCode;
	}
	public String getFcaCode() {
		return fcaCode;
	}
	public void setFcaCode(String fcaCode) {
		this.fcaCode = fcaCode;
	}
	
	
}
