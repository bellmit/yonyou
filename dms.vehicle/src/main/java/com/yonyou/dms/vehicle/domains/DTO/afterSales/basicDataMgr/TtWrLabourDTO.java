package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;

public class TtWrLabourDTO {
	private Long groupId;
	private String skillCategory;
	private String labourName;
	private Date updateDate;
	private Integer daulCode;
	private Long createBy;
	private Float labourNum;
	private Integer isDown;
	private String labourCode;
	private Integer isDel;
	private Long updateBy;
	private Long id;
	private String labourType;
	private Integer ver;
	private Float rawSubsidy;
	private Date createDate;
	private String groupCode1;
	private List<String> groupCode;
	private Integer type;
	
	

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

	public void setLabourNum(Float labourNum){
		this.labourNum=labourNum;
	}

	public Float getLabourNum(){
		return this.labourNum;
	}

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

	public void setRawSubsidy(Float rawSubsidy){
		this.rawSubsidy=rawSubsidy;
	}

	public Float getRawSubsidy(){
		return this.rawSubsidy;
	}

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
