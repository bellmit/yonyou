package com.yonyou.dms.DTO.gacfca;

/**
 * @description　配件基本信息Dto
 * @author Administrator
 *
 */
public class CLDMS005Dto {
	private String partCode;
	private String partName;
	private String partEnglishName;
	private String oldPartCode;//替换件代码
	private Integer packageNum;//最小包装数
	private String partNuit;//单位
	private Double partPrice;//建议销售价格
	private Integer partType;//配件大类
	private Integer partStatus;//配件状态
	private	Double	dnpPrice;//dnp价格  成本价
	//新增
	private Integer partProperty;//配件属性
	private Integer isPurchase;//是否可采购
	private Integer isSales;//是否销售
	private Integer isMOP;//是否M
	private Integer isSJV;//是否S
	private Integer reportType;//提报方式
	private Integer isNormal;//是否常用件
	private String modelCodes;//所属车型
	private Integer partModel;//配件类别
	private String remark;//备注
	public String getPartCode() {
		return partCode;
	}
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getPartEnglishName() {
		return partEnglishName;
	}
	public void setPartEnglishName(String partEnglishName) {
		this.partEnglishName = partEnglishName;
	}
	public String getOldPartCode() {
		return oldPartCode;
	}
	public void setOldPartCode(String oldPartCode) {
		this.oldPartCode = oldPartCode;
	}
	public Integer getPackageNum() {
		return packageNum;
	}
	public void setPackageNum(Integer packageNum) {
		this.packageNum = packageNum;
	}
	public String getPartNuit() {
		return partNuit;
	}
	public void setPartNuit(String partNuit) {
		this.partNuit = partNuit;
	}
	public Double getPartPrice() {
		return partPrice;
	}
	public void setPartPrice(Double partPrice) {
		this.partPrice = partPrice;
	}
	public Integer getPartType() {
		return partType;
	}
	public void setPartType(Integer partType) {
		this.partType = partType;
	}
	public Integer getPartStatus() {
		return partStatus;
	}
	public void setPartStatus(Integer partStatus) {
		this.partStatus = partStatus;
	}
	public Double getDnpPrice() {
		return dnpPrice;
	}
	public void setDnpPrice(Double dnpPrice) {
		this.dnpPrice = dnpPrice;
	}
	public Integer getPartProperty() {
		return partProperty;
	}
	public void setPartProperty(Integer partProperty) {
		this.partProperty = partProperty;
	}
	public Integer getIsPurchase() {
		return isPurchase;
	}
	public void setIsPurchase(Integer isPurchase) {
		this.isPurchase = isPurchase;
	}
	public Integer getIsSales() {
		return isSales;
	}
	public void setIsSales(Integer isSales) {
		this.isSales = isSales;
	}
	public Integer getIsMOP() {
		return isMOP;
	}
	public void setIsMOP(Integer isMOP) {
		this.isMOP = isMOP;
	}
	public Integer getIsSJV() {
		return isSJV;
	}
	public void setIsSJV(Integer isSJV) {
		this.isSJV = isSJV;
	}
	public Integer getReportType() {
		return reportType;
	}
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}
	public Integer getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(Integer isNormal) {
		this.isNormal = isNormal;
	}
	public String getModelCodes() {
		return modelCodes;
	}
	public void setModelCodes(String modelCodes) {
		this.modelCodes = modelCodes;
	}
	public Integer getPartModel() {
		return partModel;
	}
	public void setPartModel(Integer partModel) {
		this.partModel = partModel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
