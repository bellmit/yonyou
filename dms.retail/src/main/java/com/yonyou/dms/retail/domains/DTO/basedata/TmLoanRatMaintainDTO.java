package com.yonyou.dms.retail.domains.DTO.basedata;

import java.util.Date;


import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TmLoanRatMaintainDTO extends DataImportDto{
	
	@ExcelColumnDefine(value=4)
	private String seriesGroupcode;
	
	@ExcelColumnDefine(value=3)
	private String seriesGroupname;
	
	@ExcelColumnDefine(value=11)
	private Double rate;

	private String isScan;
	
	@ExcelColumnDefine(value=6)
	private String styleGroupcode;
	
	@ExcelColumnDefine(value=5)
	private String styleGroupname;
	
	@ExcelColumnDefine(value=10)
	private Double dpmE;
	
	@ExcelColumnDefine(value=12)
	private Date effectiveDateS;
	
	private Date updateDate;

	private Long createBy;
	
	@ExcelColumnDefine(value=7)
	private String codeId;

	private Integer isValid;
	
	@ExcelColumnDefine(value=8)
	private Integer installmentNumber;
	
	@ExcelColumnDefine(value=13)
	private Date effectiveDateE;

	private Long updateBy;
	
	@ExcelColumnDefine(value=9)
	private Double dpmS;

	private Long id;

	private Date createDate;
	
	@ExcelColumnDefine(value=2)
	private String brandGroupcode;
	
	@ExcelColumnDefine(value=1)
	private String brandGroupname;

	private Long sendBy;

	private Date sendDate;
	
	private String ids;
	
	private String flag;
	
	public Long getSendBy() {
		return sendBy;
	}

	public void setSendBy(Long sendBy) {
		this.sendBy = sendBy;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public void setRate(Double rate){
		this.rate=rate;
	}

	public Double getRate(){
		return this.rate;
	}

	public void setIsScan(String isScan){
		this.isScan=isScan;
	}

	public String getIsScan(){
		return this.isScan;
	}

	public void setDpmE(Double dpmE){
		this.dpmE=dpmE;
	}

	public Double getDpmE(){
		return this.dpmE;
	}

	public void setEffectiveDateS(Date effectiveDateS){
		this.effectiveDateS=effectiveDateS;
	}

	public Date getEffectiveDateS(){
		return this.effectiveDateS;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setCodeId(String codeId){
		this.codeId=codeId;
	}

	public String getCodeId(){
		return this.codeId;
	}

	public void setIsValid(Integer isValid){
		this.isValid=isValid;
	}

	public Integer getIsValid(){
		return this.isValid;
	}

	public void setInstallmentNumber(Integer installmentNumber){
		this.installmentNumber=installmentNumber;
	}

	public Integer getInstallmentNumber(){
		return this.installmentNumber;
	}

	public void setEffectiveDateE(Date effectiveDateE){
		this.effectiveDateE=effectiveDateE;
	}

	public Date getEffectiveDateE(){
		return this.effectiveDateE;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setDpmS(Double dpmS){
		this.dpmS=dpmS;
	}

	public Double getDpmS(){
		return this.dpmS;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public String getSeriesGroupcode() {
		return seriesGroupcode;
	}

	public void setSeriesGroupcode(String seriesGroupcode) {
		this.seriesGroupcode = seriesGroupcode;
	}

	public String getSeriesGroupname() {
		return seriesGroupname;
	}

	public void setSeriesGroupname(String seriesGroupname) {
		this.seriesGroupname = seriesGroupname;
	}

	public String getStyleGroupcode() {
		return styleGroupcode;
	}

	public void setStyleGroupcode(String styleGroupcode) {
		this.styleGroupcode = styleGroupcode;
	}

	public String getStyleGroupname() {
		return styleGroupname;
	}

	public void setStyleGroupname(String styleGroupname) {
		this.styleGroupname = styleGroupname;
	}

	public String getBrandGroupcode() {
		return brandGroupcode;
	}

	public void setBrandGroupcode(String brandGroupcode) {
		this.brandGroupcode = brandGroupcode;
	}

	public String getBrandGroupname() {
		return brandGroupname;
	}

	public void setBrandGroupname(String brandGroupname) {
		this.brandGroupname = brandGroupname;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


}
