package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.MonthPlanMaintainDao;
/**
 * 
* @ClassName: MonthPlanDealerMaintainServiceImpl 
* @Description: 目标管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:26:01 
*
 */
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class MonthPlanMaintainServiceImpl implements MonthPlanMaintainService{

	@Autowired
	private MonthPlanMaintainDao monthPlanDealerMaintainDao ;

	@Override
	public List<Map> getMonthPlanYearList() throws ServiceBizException {
		List<Map>  yearList = monthPlanDealerMaintainDao.getMonthPlanYearList();
		return yearList;
	}

	@Override
	public PageInfoDto oemQueryMonthPlanDetialInfoList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = monthPlanDealerMaintainDao.oemQueryMonthPlanDetialInfoList(queryParam); 
		return pageInfoDto;
	}

	@Override
	public List<Map> monthPlanDetialInfoDownLoad(Map<String, String> queryParam) throws ServiceBizException {
		List<Map>  list = monthPlanDealerMaintainDao.monthPlanDetialInfoDownLoad(queryParam);
		return list;
	}

	@Override
	public List<Map> getNowSeasonList() throws ServiceBizException {
		List<Map>  list = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH) + 1;
		Map m = new HashMap();
		switch (month) {
		case 1:
		case 2:
		case 3:
			m.put("SEASON", "第一季度");
			list.add(m);
			return list;
		case 4:
		case 5:
		case 6:
			m.put("SEASON", "第二季度");
			list.add(m);
			return list;
		case 7:
		case 8:
		case 9:
			m.put("SEASON", "第三季度");
			list.add(m);
			return list;
		case 10:
		case 11:
		case 12:
			m.put("SEASON", "第四季度");
			list.add(m);
			return list;
		}
		
		return null;
	}
	
	

}
