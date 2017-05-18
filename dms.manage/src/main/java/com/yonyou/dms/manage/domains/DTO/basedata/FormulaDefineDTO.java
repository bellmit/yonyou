/**
 * 
 */
package com.yonyou.dms.manage.domains.DTO.basedata;

import com.yonyou.dms.function.utils.validate.define.Required;

/**
 * @author yangjie
 *
 */
public class FormulaDefineDTO {
	@Required
	private String orderPlanFormulasName; // 公式名称
	@Required
	private String formulasCode; // 公式
	private Integer isAvailable; // 是否可用
	@Required
	private Long exp; // 经验值

	public String getOrderPlanFormulasName() {
		return orderPlanFormulasName;
	}

	public void setOrderPlanFormulasName(String orderPlanFormulasName) {
		this.orderPlanFormulasName = orderPlanFormulasName;
	}

	public String getFormulasCode() {
		return formulasCode;
	}

	public void setFormulasCode(String formulasCode) {
		this.formulasCode = formulasCode;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}

}
