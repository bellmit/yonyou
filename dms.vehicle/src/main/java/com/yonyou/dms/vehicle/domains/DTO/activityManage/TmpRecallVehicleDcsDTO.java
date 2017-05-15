package com.yonyou.dms.vehicle.domains.DTO.activityManage;

import java.sql.Date;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
* @author liujm
* @date 2017年4月17日
*/
public class TmpRecallVehicleDcsDTO extends DataImportDto{
	
	@ExcelColumnDefine(value = 1)
	@Required
	private String recallNo;
	private Integer inportType;
	
	@ExcelColumnDefine(value = 2)
	@Required
	private String vin;
	private String dutyDealer;
	private Long createBy;
	private Integer inportClass;
	public String getRecallNo() {
		return recallNo;
	}
	public void setRecallNo(String recallNo) {
		this.recallNo = recallNo;
	}

	public Integer getInportType() {
		return inportType;
	}
	public void setInportType(Integer inportType) {
		this.inportType = inportType;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}
	public String getDutyDealer() {
		return dutyDealer;
	}
	public void setDutyDealer(String dutyDealer) {
		this.dutyDealer = dutyDealer;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Integer getInportClass() {
		return inportClass;
	}
	public void setInportClass(Integer inportClass) {
		this.inportClass = inportClass;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getTmpId() {
		return tmpId;
	}
	public void setTmpId(Long tmpId) {
		this.tmpId = tmpId;
	}
	private Date createDate;
	private Long tmpId;
	

}
