package com.yonyou.dms.manage.service.basedata.date;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

public interface YearMonthWeekServic {

	public List<Map> getYearList()throws ServiceBizException ;

	public List<Map> getMonthList()throws ServiceBizException ;

	public List<Map> getWeekList(Integer year,Integer month)throws ServiceBizException ;

}
