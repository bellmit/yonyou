package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * *转化率报表Service
 * @author Benzc
 * @date 2017年1月18日
 */
public interface ConversionRateService {
	
	//分页查询信息
	public PageInfoDto queryConversionRate(Map<String, String> queryParam) throws ServiceBizException;
	
	//导出
	@SuppressWarnings("rawtypes")
	public List<Map> queryConversionRecordforExport(Map<String, String> queryParam) throws ServiceBizException;

}
