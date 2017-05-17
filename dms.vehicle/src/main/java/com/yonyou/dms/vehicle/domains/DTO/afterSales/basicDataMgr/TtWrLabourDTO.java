package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtWrLabourDTO  extends  DataImportDto{
	private Long groupId;
	@ExcelColumnDefine(value = 4)
	@Required
	private String skillCategory;
	@ExcelColumnDefine(value = 3)
	@Required
	private String labourName;
	private Date updateDate;
	private Integer daulCode;
	private Long createBy;
	@ExcelColumnDefine(value = 7)
	@Required
	private String labourNum;
	private Integer isDown;
	@ExcelColumnDefine(value = 2)
	@Required
	private String labourCode;
	private Integer isDel;
	private Long updateBy;
	private Long id;
	private String labourType;
	private Integer ver;
	
	@ExcelColumnDefine(value = 5)
	@Required
	private String rawSubsidy;//原料补贴
	private Date createDate;
	@ExcelColumnDefine(value = 1)
	@Required
	private String groupCode1;
	private List<String> groupCode;
	@ExcelColumnDefine(value = 6)
	@Required
	private Integer type;
	
	

	public String getLabourNum() {
		return labourNum;
	}

	public void setLabourNum(String labourNum) {
		this.labourNum = labourNum;
	}

	public String getRawSubsidy() {
		return rawSubsidy;
	}

	public void setRawSubsidy(String rawSubsidy) {
		this.rawSubsidy = rawSubsidy;
	}

	public String getGroupCode1() {
		return groupCode1;
	}

	public void setGroupCode1(String groupCode1) {
		this.groupCode1 = groupCode1;
	}

	public List<String> getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(List<String> groupCode) {
		this.groupCode = groupCode;
	}

	public void setGroupId(Long groupId){
		this.groupId=groupId;
	}

	public Long getGroupId(){
		return this.groupId;
	}

	public void setSkillCategory(String skillCategory){
		this.skillCategory=skillCategory;
	}

	public String getSkillCategory(){
		return this.skillCategory;
	}

	public void setLabourName(String labourName){
		this.labourName=labourName;
	}

	public String getLabourName(){
		return this.labourName;
	}

	public void setUpdateDate(Date updateDate){
		this.updateDate=updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public void setDaulCode(Integer daulCode){
		this.daulCode=daulCode;
	}

	public Integer getDaulCode(){
		return this.daulCode;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

/*	public void setLabourNum(Float labourNum){
		this.labourNum=labourNum;
	}

	public Float getLabourNum(){
		return this.labourNum;
	}*/

	public void setIsDown(Integer isDown){
		this.isDown=isDown;
	}

	public Integer getIsDown(){
		return this.isDown;
	}

	public void setLabourCode(String labourCode){
		this.labourCode=labourCode;
	}

	public String getLabourCode(){
		return this.labourCode;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setLabourType(String labourType){
		this.labourType=labourType;
	}

	public String getLabourType(){
		return this.labourType;
	}

	public void setVer(Integer ver){
		this.ver=ver;
	}

	public Integer getVer(){
		return this.ver;
	}

/*	public void setRawSubsidy(Float rawSubsidy){
		this.rawSubsidy=rawSubsidy;
	}

	public Float getRawSubsidy(){
		return this.rawSubsidy;
	}
*/
	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}



	public void setType(Integer type){
		this.type=type;
	}

	public Integer getType(){
		return this.type;
	}

}
