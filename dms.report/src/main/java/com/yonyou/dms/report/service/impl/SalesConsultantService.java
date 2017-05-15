package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 销售顾问业绩统计接口
 * @author wangliang
 * @date 2017年2月06日
 */
public interface SalesConsultantService {

	public PageInfoDto querySalesConsultant(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> querySalesConsultantExport(Map<String, String> queryParam) throws ServiceBizException;
	
}
