package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：客流HSL趋势分析
 * @author Benzc
 * @date 2017年2月6日
 */
public interface HslTrendAnalysisService {
	
	//分页查询信息
	public PageInfoDto queryHslTrendAnalysis(Map<String, String> queryParam) throws ServiceBizException;
	
	//导出
	@SuppressWarnings("rawtypes")
	public List<Map> queryHslTrendAnalysisforExport(Map<String, String> queryParam) throws ServiceBizException;
	
}
