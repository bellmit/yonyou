package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.OemYearPlanQueryDao;
/**
 * 
* @ClassName: OemYearPlanQueryServiceImpl 
* @Description: 目标任务管理 
* @author zhengzengliang
* @date 2017年3月1日 上午11:05:31 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class OemYearPlanQueryServiceImpl implements OemYearPlanQueryService{
	
	@Autowired
	private OemYearPlanQueryDao oemYearPlanQueryDao ;
	
	@Override
	public List<Map> getYearPlanYearList() throws ServiceBizException {
		List<Map> yearList = oemYearPlanQueryDao.getYearPlanYearList();
		return yearList;
	}

	@Override
	public List<Map> findPlanVerByYear() throws ServiceBizException {
		List<Map> planVerList = oemYearPlanQueryDao.findPlanVerByYear();
		return planVerList;
	}

	@Override
	public List<Map> getMaxPlanVer() throws ServiceBizException {
		List<Map> nowPlanVerList = oemYearPlanQueryDao.getMaxPlanVer();
		return nowPlanVerList;
	}

	@Override
	public PageInfoDto yearPlanDetailQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = oemYearPlanQueryDao.yearPlanDetailQuery(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> getOemYearPlanDetailQueryList(Map<String, String> queryParam) throws ServiceBizException {
		List<Map>  list = oemYearPlanQueryDao.getOemYearPlanDetailQueryList(queryParam);
		return list;
	}

}
