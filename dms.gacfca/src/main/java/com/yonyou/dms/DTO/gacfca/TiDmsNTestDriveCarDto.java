package com.yonyou.dms.DTO.gacfca;

import java.util.Date;

import com.infoeai.eai.vo.BaseVO;

@SuppressWarnings("serial")
public class TiDmsNTestDriveCarDto extends BaseVO{


	private String vinCode;// 车辆识别码
	private String modelId;// DMS车型ID
	private String modelName;
	private String licenseCode;// 车牌号
	private String dealerCode;// 经销商代码//经销商ID
	private String carStaturs;
	private Date createDate;// 创建时间//创建时间
	private String dealerUserId;// 销售人员的ID
	private String entityCode;
	private Date updateDate;

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getEntityCode() {
		return entityCode;
	}

	public void setEntityCode(String entityCode) {
		this.entityCode = entityCode;
	}

	public String getCarStaturs() {
		return carStaturs;
	}

	public void setCarStaturs(String carStaturs) {
		this.carStaturs = carStaturs;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerUserId() {
		return dealerUserId;
	}

	public void setDealerUserId(String dealerUserId) {
		this.dealerUserId = dealerUserId;
	}

	public String getLicenseCode() {
		return licenseCode;
	}

	public void setLicenseCode(String licenseCode) {
		this.licenseCode = licenseCode;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getVinCode() {
		return vinCode;
	}

	public void setVinCode(String vinCode) {
		this.vinCode = vinCode;
	}

	// public String toXMLString() {
	// return VOUtil.vo2Xml(this);
	// }

}
