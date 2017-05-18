package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.DlrYearPlanQueryDao;
/**
 * 
* @ClassName: DlrYearPlanQuerySercviceImpl 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午6:37:17 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class DlrYearPlanQuerySercviceImpl implements DlrYearPlanQuerySercvice{

	@Autowired
	private DlrYearPlanQueryDao dlrYearPlanQueryDao ;
	
	
	@Override
	public List<Map> getDealerPlanYearList() throws ServiceBizException {
		List<Map> list = dlrYearPlanQueryDao.getDealerPlanYearList();
		return list;
	}


	@Override
	public PageInfoDto getDlrYearPlanQueryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = dlrYearPlanQueryDao.getDlrYearPlanQueryList(queryParam);
		return pageInfoDto;
	}


	@Override
	public List<Map> yearPlanDownload(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = dlrYearPlanQueryDao.yearPlanDownload(queryParam);
		return list;
	}

}
