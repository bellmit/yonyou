package com.yonyou.dms.manage.domains.DTO.salesPlanManager;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
/**
 * 
* @ClassName: OemForecastAuditDTO 
* @Description: 生产订单导入DTO
* @author zhengzengliang
* @date 2017年2月10日 上午10:58:37 
*
 */
public class OemForecastAuditDTO extends DataImportDto{
	
	@ExcelColumnDefine(value=1)
	@Required
	private String bigArea;
	
	@ExcelColumnDefine(value=2)
	@Required
	private String samllArea;
	
	@ExcelColumnDefine(value=3)
	@Required
	private Long dealerCode;
	
	@ExcelColumnDefine(value=4)
	@Required
	private String dealerName;
	
	@ExcelColumnDefine(value=5)
	@Required
	private Long taskId;
	
	@ExcelColumnDefine(value=6)
	@Required
	private String seriesCode;
	
	@ExcelColumnDefine(value=7)
	@Required
	private String seriesName;
	
	@ExcelColumnDefine(value=8)
	@Required
	private String modelCode;
	
	@ExcelColumnDefine(value=9)
	@Required
	private String modelName;
	
	@ExcelColumnDefine(value=10)
	@Required
	private String groupCode;
	
	@ExcelColumnDefine(value=11)
	@Required
	private String groupName;
	
	@ExcelColumnDefine(value=12)
	@Required
	private String colorCode;
	
	@ExcelColumnDefine(value=13)
	@Required
	private String colorName;
	
	@ExcelColumnDefine(value=14)
	@Required
	private String trimCode;
	
	@ExcelColumnDefine(value=15)
	@Required
	private String trimName;
	
	@ExcelColumnDefine(value=16)
	@Required
	private Long detailId;
	
	@ExcelColumnDefine(value=17)
	@Required
	private Long materialId;
	
	@ExcelColumnDefine(value=18)
	@Required
	private Long forecastId;
	
	@ExcelColumnDefine(value=19)
	@Required
	private Long requireNum;
	
	@ExcelColumnDefine(value=20)
	@Required
	private Long confirmNum;
	
	//文件上传的ID
    private String dmsFileIds;
    
	public String getBigArea() {
		return bigArea;
	}

	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}

	public String getSamllArea() {
		return samllArea;
	}

	public void setSamllArea(String samllArea) {
		this.samllArea = samllArea;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getTrimCode() {
		return trimCode;
	}

	public void setTrimCode(String trimCode) {
		this.trimCode = trimCode;
	}

	public String getTrimName() {
		return trimName;
	}

	public void setTrimName(String trimName) {
		this.trimName = trimName;
	}

	public Long getRequireNum() {
		return requireNum;
	}

	public void setRequireNum(Long requireNum) {
		this.requireNum = requireNum;
	}

	public Long getConfirmNum() {
		return confirmNum;
	}

	public void setConfirmNum(Long confirmNum) {
		this.confirmNum = confirmNum;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getSeriesCode() {
		return seriesCode;
	}

	public void setSeriesCode(String seriesCode) {
		this.seriesCode = seriesCode;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public Long getForecastId() {
		return forecastId;
	}

	public void setForecastId(Long forecastId) {
		this.forecastId = forecastId;
	}

	public String getDmsFileIds() {
		return dmsFileIds;
	}

	public void setDmsFileIds(String dmsFileIds) {
		this.dmsFileIds = dmsFileIds;
	}

	public Long getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(Long dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}


	
	
}
