package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDetailColorDTO;

/**
 * 生产订单管理
 * zhengzengliang
 */
@SuppressWarnings("rawtypes")
public interface DlrForecastQueryService {
	
	public List<Map> getDealerMonthPlanYearList(Map<String, String> queryParam) throws ServiceBizException;

	public PageInfoDto getDlrForecastQueryList2(Map<String, String> queryParam) throws ServiceBizException;

	public void modifyVsMonthlyForecastDetailColor(TtVsMonthlyForecastDetailColorDTO ttVsMonthlyForecastDetailColorDTO);

	public PageInfoDto DlrfindBySerialNumber(Map<String, String> queryParam) throws ServiceBizException;
	
	
}
