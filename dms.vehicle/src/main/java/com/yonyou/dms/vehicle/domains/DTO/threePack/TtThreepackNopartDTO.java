package com.yonyou.dms.vehicle.domains.DTO.threePack;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtThreepackNopartDTO extends DataImportDto{
	@ExcelColumnDefine(value = 1)
	@Required
	private String partType;
	private Long updateBy;
	private Date updateDate;
	private String partName;
	private Long id;
	private Long ver;
	private Long createBy;
	@ExcelColumnDefine(value = 3)
	private String remark;
	private Date createDate;
	@ExcelColumnDefine(value = 2)
	@Required
	private String partCode;
	private Integer isArc;
	private Integer isDel;

	public void setPartType(String partType){
		this.partType=partType;
	}

	public String getPartType(){
		return this.partType;
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

	public void setPartName(String partName){
		this.partName=partName;
	}

	public String getPartName(){
		return this.partName;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getId(){
		return this.id;
	}

	public void setVer(Long ver){
		this.ver=ver;
	}

	public Long getVer(){
		return this.ver;
	}

	public void setCreateBy(Long createBy){
		this.createBy=createBy;
	}

	public Long getCreateBy(){
		return this.createBy;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setPartCode(String partCode){
		this.partCode=partCode;
	}

	public String getPartCode(){
		return this.partCode;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}

}
