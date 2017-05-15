package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: DlrYearPlanQuerySercvice 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午6:36:15 
*
 */
@SuppressWarnings("rawtypes")
public interface DlrYearPlanQuerySercvice {
	
	public List<Map> getDealerPlanYearList() throws ServiceBizException;

	public PageInfoDto getDlrYearPlanQueryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> yearPlanDownload(Map<String, String> queryParam) throws ServiceBizException;

}
