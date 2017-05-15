package com.yonyou.dms.vehicle.domains.DTO.threePack;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtThreepackItemMinclassDTO extends DataImportDto{
	private Long updateBy;
	private Date updateDate;
	private Long id;
	private Long ver;
	private Long createBy;
	@ExcelColumnDefine(value = 2)
	@Required
	private String minclassNo;
	private Date createDate;
	@ExcelColumnDefine(value = 1)
	@Required
	private String itemNo;
	private Long itemId;
	@ExcelColumnDefine(value = 3)
	@Required
	private String minclassName;
	private Integer isArc;
	private Integer isDel;

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

	public void setMinclassNo(String minclassNo){
		this.minclassNo=minclassNo;
	}

	public String getMinclassNo(){
		return this.minclassNo;
	}

	public void setCreateDate(Date createDate){
		this.createDate=createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setItemId(Long itemId){
		this.itemId=itemId;
	}

	public Long getItemId(){
		return this.itemId;
	}

	public void setMinclassName(String minclassName){
		this.minclassName=minclassName;
	}

	public String getMinclassName(){
		return this.minclassName;
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
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

}
