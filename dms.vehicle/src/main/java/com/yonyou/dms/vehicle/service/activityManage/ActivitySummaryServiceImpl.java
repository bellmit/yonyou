package com.yonyou.dms.vehicle.service.activityManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivitySummaryDao;

/**
* @author liujiming
* @date 2017年4月5日
*/
@Service
public class ActivitySummaryServiceImpl implements ActivitySummaryService{
	
	
	@Autowired
	private ActivitySummaryDao asDao;
	
	
	
	
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto activitySummaryQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = asDao.getActivitySummaryQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 明细查询
	 */
	@Override
	public Map activitySummaryDeatilQuery(Map<String, String> queryParam, Long id) throws ServiceBizException {
		Map resultMap = new HashMap();
		List<Map>  list = asDao.getActivitySummaryDetailQuery(queryParam, id);
		for(int i=0; i<list.size(); i++){
			resultMap = list.get(0); 
		}
		return resultMap;
	}

}
