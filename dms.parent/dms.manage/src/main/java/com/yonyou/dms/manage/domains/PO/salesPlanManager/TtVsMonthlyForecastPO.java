package com.yonyou.dms.manage.domains.PO.salesPlanManager;


import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 
* @ClassName: TtVsMonthlyForecastPO 
* @Description: 生产订单任务录入 
* @author zhengzengliang
* @date 2017年2月17日 上午10:58:39 
*
 */
@Table("tt_vs_monthly_forecast")
@IdName("FORECAST_ID")
public class TtVsMonthlyForecastPO extends BaseModel{

	private Long updated_by;

	public Long getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(Long updated_by) {
		this.updated_by = updated_by;
	}
	
}
