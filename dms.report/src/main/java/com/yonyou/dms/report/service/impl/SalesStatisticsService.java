package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SalesStatisticsService {

	public PageInfoDto querySalesStatisticsChecked(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> querySalesStaticExport(Map<String, String> queryParam) throws ServiceBizException;

}
