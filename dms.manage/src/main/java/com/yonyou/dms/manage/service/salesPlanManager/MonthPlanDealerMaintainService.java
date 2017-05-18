package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: MonthPlanDealerMaintainService 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:24:50 
*
 */
@SuppressWarnings("rawtypes")
public interface MonthPlanDealerMaintainService {
	
	public List<Map> getDealerMonthPlanYearList() throws ServiceBizException;

	public PageInfoDto dealearQueryMonthPlanDealerInfoList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> monthPlanDealerDownLoad(Map<String, String> queryParam) throws ServiceBizException;

}
