package com.yonyou.dms.vehicle.domains.DTO.k4Order;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;

/**
 * @ClassName: TmpVsRebateTypeDTO
 * @Description:经销商返利上传
 * @author liujiming
 * @date 2017年3月21日
 */
public class TmpVsRebateImpDTO extends DataImportDto {

	@ExcelColumnDefine(value = 1)
	@Required
	private String dealerCode;

	@ExcelColumnDefine(value = 2)
	@Required
	@VIN
	private String vin;

	@ExcelColumnDefine(value = 3)
	@Required
	private String rebateAmount;

	@ExcelColumnDefine(value = 4)

	private String remark;

	private Long rebateType; // 返利类型

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getRebateAmount() {
		return rebateAmount;
	}

	public void setRebateAmount(String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRebateType() {
		return rebateType;
	}

	public void setRebateType(Long rebateType) {
		this.rebateType = rebateType;
	}

}
