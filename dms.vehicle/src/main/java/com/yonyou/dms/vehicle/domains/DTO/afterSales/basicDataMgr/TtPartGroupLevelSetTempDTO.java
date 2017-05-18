package com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr;

import java.util.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;

public class TtPartGroupLevelSetTempDTO   extends  DataImportDto {
	private String dataStatus;
	private Long updateBy;
	@ExcelColumnDefine(value = 2)
	@Required
	private String famigliaFamigliaDesc;
	private Date updateDate;
	private Long id;
	private Long createBy;
	@ExcelColumnDefine(value = 4)
	@Required
	private Date createDate;
	@ExcelColumnDefine(value = 3)
	@Required
	private String itemNo;
	@ExcelColumnDefine(value = 1)
	@Required
	private String famigliaFamiglia;

	public void setDataStatus(String dataStatus){
		this.dataStatus=dataStatus;
	}

	public String getDataStatus(){
		return this.dataStatus;
	}

	public void setUpdateBy(Long updateBy){
		this.updateBy=updateBy;
	}

	public Long getUpdateBy(){
		return this.updateBy;
	}

	public void setFamigliaFamigliaDesc(String famigliaFamigliaDesc){
		this.famigliaFamigliaDesc=famigliaFamigliaDesc;
	}

	public String getFamigliaFamigliaDesc(){
		return this.famigliaFamigliaDesc;
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

/*	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}*/

	public String getItemNo(){
		return this.itemNo;
	}

	public void setFamigliaFamiglia(String famigliaFamiglia){
		this.famigliaFamiglia=famigliaFamiglia;
	}

	public String getFamigliaFamiglia(){
		return this.famigliaFamiglia;
	}
}
