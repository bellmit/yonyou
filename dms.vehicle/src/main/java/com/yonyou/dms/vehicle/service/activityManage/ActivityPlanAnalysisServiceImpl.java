package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityPlanAnalysisDao;

/**
* @author liujiming
* @date 2017年4月1日
*/
@Service
public class ActivityPlanAnalysisServiceImpl  implements ActivityPlanAnalysisService{
	
	@Autowired
	private ActivityPlanAnalysisDao apaDao;
	
	
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto getPlanAnalysisInitQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = apaDao.getPlanAnalysisInitQueryList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 明细查询
	 */
	@Override
	public PageInfoDto getPlanAnalysisDetailQuery(Map<String, String> queryParam, Long activityId)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = apaDao.getPlanAnalysisDetailQueryList(queryParam, activityId);
		return pageInfoDto;
	}
	/**
	 *  责任明细查询
	 */
	@Override
	public PageInfoDto getPlanAnalysisDetailQueryTwo(Map<String, String> queryParam, Long activityId)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = apaDao.getPlanAnalysisDetailQueryListTwo(queryParam, activityId);
		return pageInfoDto;
	}

}
