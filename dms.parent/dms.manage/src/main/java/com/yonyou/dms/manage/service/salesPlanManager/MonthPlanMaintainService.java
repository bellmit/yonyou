package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: MonthPlanMaintainService 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:24:50 
*
 */
@SuppressWarnings("rawtypes")
public interface MonthPlanMaintainService {
	
	public List<Map> getMonthPlanYearList() throws ServiceBizException;

	public PageInfoDto oemQueryMonthPlanDetialInfoList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> monthPlanDetialInfoDownLoad(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> getNowSeasonList() throws ServiceBizException;

}
