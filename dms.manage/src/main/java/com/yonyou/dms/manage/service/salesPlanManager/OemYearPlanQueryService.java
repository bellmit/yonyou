package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: OemYearPlanQueryService 
* @Description: 目标任务管理 
* @author zhengzengliang
* @date 2017年3月1日 上午11:04:58 
*
 */
@SuppressWarnings("rawtypes")
public interface OemYearPlanQueryService {

	
	public List<Map> getYearPlanYearList() throws ServiceBizException;

	public List<Map> findPlanVerByYear() throws ServiceBizException;

	public List<Map> getMaxPlanVer() throws ServiceBizException;

	public PageInfoDto yearPlanDetailQuery(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> getOemYearPlanDetailQueryList(Map<String, String> queryParam) throws ServiceBizException;

}
