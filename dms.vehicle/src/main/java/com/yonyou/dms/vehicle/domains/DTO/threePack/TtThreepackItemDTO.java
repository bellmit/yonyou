package com.yonyou.dms.vehicle.domains.DTO.threePack;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

public class TtThreepackItemDTO extends DataImportDto{
	private Long updateBy;
	private Date updateDate;
	private String itemName;//项目名称
	private Long id;
	private Long ver;
	private Long createBy;
	private Date createDate;
	private String itemNo;//项目编号
	private Integer isArc;//归档标志
	private String itemRemark;//项目描述
	private Integer isDel;
	private String  minclassNo;//三包小类代码
	private String  minclassName;//三包小类名称

	public String getMinclassNo() {
		return minclassNo;
	}

	public void setMinclassNo(String minclassNo) {
		this.minclassNo = minclassNo;
	}

	public String getMinclassName() {
		return minclassName;
	}

	public void setMinclassName(String minclassName) {
		this.minclassName = minclassName;
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

	public void setItemName(String itemName){
		this.itemName=itemName;
	}

	public String getItemName(){
		return this.itemName;
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

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setItemNo(String itemNo){
		this.itemNo=itemNo;
	}

	public String getItemNo(){
		return this.itemNo;
	}

	public void setIsArc(Integer isArc){
		this.isArc=isArc;
	}

	public Integer getIsArc(){
		return this.isArc;
	}

	public void setItemRemark(String itemRemark){
		this.itemRemark=itemRemark;
	}

	public String getItemRemark(){
		return this.itemRemark;
	}

	public void setIsDel(Integer isDel){
		this.isDel=isDel;
	}

	public Integer getIsDel(){
		return this.isDel;
	}
}
