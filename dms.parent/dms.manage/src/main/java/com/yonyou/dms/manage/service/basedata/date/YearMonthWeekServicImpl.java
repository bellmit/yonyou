package com.yonyou.dms.manage.service.basedata.date;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.date.YearMonthWeekDao;

@Service
public class YearMonthWeekServicImpl implements YearMonthWeekServic {
	
	@Autowired
	YearMonthWeekDao dao;

	@Override
	public List<Map> getYearList() throws ServiceBizException {
		return dao.getYearList();
	}

	@Override
	public List<Map> getMonthList() throws ServiceBizException {
		return dao.getMonthList();
	}

	@Override
	public List<Map> getWeekList(Integer year,Integer month) throws ServiceBizException {
		return dao.getWeekList(year,month);
	}

	

}
