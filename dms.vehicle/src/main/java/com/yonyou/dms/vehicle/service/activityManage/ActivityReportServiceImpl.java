package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityReportDao;

/**
* @author liujiming
* @date 2017年4月5日
*/
@Service
public class ActivityReportServiceImpl implements ActivityReportService{
	
	
	@Autowired
	private ActivityReportDao arDao;
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto activityReportQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = arDao.getActivityReportQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 明细
	 */
	@Override
	public PageInfoDto activityReportDetailQuery(Map<String, String> queryParam, String activityCode)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = arDao.getActivityReportDetailQuery(queryParam, activityCode);
		return pageInfoDto;
	}

}
