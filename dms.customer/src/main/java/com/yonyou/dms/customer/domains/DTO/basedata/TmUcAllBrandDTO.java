package com.yonyou.dms.customer.domains.DTO.basedata;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TmUcAllBrandDTO extends DataImportDto {

	private String dataStatus;
	private Date updateDate;
	private Long createBy;
	private String modelCode;
	private String brandName;
	private String vhclTrans;
	private Long updateBy;
	@ExcelColumnDefine(value = 1)
	private String launch;
	@ExcelColumnDefine(value = 2)
	private Date launchDate;
	private String excelFileId;
	private String excelFileName;
	private String vhclLiter;
	private String seriesName;
	private String validatorMsg;
	private String modelName;
	private Date importDate;
	private String makeCode;
	@ExcelColumnDefine(value = 3)
	private Date dropDate;
	private String makeName;
	private String vhclMsrp;
	private Integer modelYear;
	private String brandCode;
	private String seriesCode;
	private Date createDate;
	private Long tmpId;
	private String firstLetter;
	private Integer successFlag;

	public Integer getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(Integer successFlag) {
		this.successFlag = successFlag;
	}

	public void setDataStatus(String dataStatus){
		this.dataStatus=dataStatus;
	}

	public String getDataStatus(){
		return this.dataStatus;
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

	public void setModelCode(String modelCode){
		this.modelCode=modelCode;
	}

	public String getModelCode(){
		return this.modelCode;
	}

	public void setBrandName(String brandName){
		this.brandName=brandName;
	}

	public String getBrandName(){
		return this.brandName;
	}

	public void setVhclTrans(String vhclTrans){
		this.vhclTrans=vhclTrans;
	}

	public String getVhclTrans(){
		return this.vhclTrans;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setLaunchDate(Date launchDate){
		this.launchDate=launchDate;
	}

	public Date getLaunchDate(){
		return this.launchDate;
	}

	public void setExcelFileId(String excelFileId){
		this.excelFileId=excelFileId;
	}

	public String getExcelFileId(){
		return this.excelFileId;
	}

	public void setExcelFileName(String excelFileName){
		this.excelFileName=excelFileName;
	}

	public String getExcelFileName(){
		return this.excelFileName;
	}

	public void setVhclLiter(String vhclLiter){
		this.vhclLiter=vhclLiter;
	}

	public String getVhclLiter(){
		return this.vhclLiter;
	}

	public void setSeriesName(String seriesName){
		this.seriesName=seriesName;
	}

	public String getSeriesName(){
		return this.seriesName;
	}

	public void setValidatorMsg(String validatorMsg){
		this.validatorMsg=validatorMsg;
	}

	public String getValidatorMsg(){
		return this.validatorMsg;
	}

	public void setModelName(String modelName){
		this.modelName=modelName;
	}

	public String getModelName(){
		return this.modelName;
	}

	public void setImportDate(Date importDate){
		this.importDate=importDate;
	}

	public Date getImportDate(){
		return this.importDate;
	}

	public void setMakeCode(String makeCode){
		this.makeCode=makeCode;
	}

	public String getMakeCode(){
		return this.makeCode;
	}

	public void setDropDate(Date dropDate){
		this.dropDate=dropDate;
	}

	public Date getDropDate(){
		return this.dropDate;
	}

	public void setMakeName(String makeName){
		this.makeName=makeName;
	}

	public String getMakeName(){
		return this.makeName;
	}

	public void setVhclMsrp(String vhclMsrp){
		this.vhclMsrp=vhclMsrp;
	}

	public String getVhclMsrp(){
		return this.vhclMsrp;
	}

	public void setModelYear(Integer modelYear){
		this.modelYear=modelYear;
	}

	public Integer getModelYear(){
		return this.modelYear;
	}

	public void setBrandCode(String brandCode){
		this.brandCode=brandCode;
	}

	public String getBrandCode(){
		return this.brandCode;
	}

	public void setSeriesCode(String seriesCode){
		this.seriesCode=seriesCode;
	}

	public String getSeriesCode(){
		return this.seriesCode;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setTmpId(Long tmpId){
		this.tmpId=tmpId;
	}

	public Long getTmpId(){
		return this.tmpId;
	}

	public void setFirstLetter(String firstLetter){
		this.firstLetter=firstLetter;
	}

	public String getFirstLetter(){
		return this.firstLetter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((brandCode == null) ? 0 : brandCode.hashCode());
		result = prime * result
				+ ((makeCode == null) ? 0 : makeCode.hashCode());
		result = prime * result
				+ ((modelCode == null) ? 0 : modelCode.hashCode());
		result = prime * result
				+ ((modelYear == null) ? 0 : modelYear.hashCode());
		result = prime * result
				+ ((seriesCode == null) ? 0 : seriesCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final TmUcAllBrandDTO other = (TmUcAllBrandDTO) obj;
		if (brandCode == null) {
			if (other.brandCode != null)
				return false;
		} else if (!brandCode.equals(other.brandCode))
			return false;
		if (makeCode == null) {
			if (other.makeCode != null)
				return false;
		} else if (!makeCode.equals(other.makeCode))
			return false;
		if (modelCode == null) {
			if (other.modelCode != null)
				return false;
		} else if (!modelCode.equals(other.modelCode))
			return false;
		if (modelYear == null) {
			if (other.modelYear != null)
				return false;
		} else if (!modelYear.equals(other.modelYear))
			return false;
		if (seriesCode == null) {
			if (other.seriesCode != null)
				return false;
		} else if (!seriesCode.equals(other.seriesCode))
			return false;
		return true;
	}
}
