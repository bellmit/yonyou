package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.MonthPlanDealerMaintainDao;
/**
 * 
* @ClassName: MonthPlanDealerMaintainServiceImpl 
* @Description: 目标管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:26:01 
*
 */
@Service
@SuppressWarnings("rawtypes")
public class MonthPlanDealerMaintainServiceImpl implements MonthPlanDealerMaintainService{

	@Autowired
	private MonthPlanDealerMaintainDao monthPlanDealerMaintainDao ;
	
	@Override
	public List<Map> getDealerMonthPlanYearList() throws ServiceBizException {
		List<Map> list = monthPlanDealerMaintainDao.getDealerMonthPlanYearList();
		return list;
	}

	@Override
	public PageInfoDto dealearQueryMonthPlanDealerInfoList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = monthPlanDealerMaintainDao.dealearQueryMonthPlanDealerInfoList(queryParam);
		return pageInfoDto;
	}

	@Override
	public List<Map> monthPlanDealerDownLoad(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = monthPlanDealerMaintainDao.monthPlanDealerDownLoad(queryParam);
		return list;
	}

}
