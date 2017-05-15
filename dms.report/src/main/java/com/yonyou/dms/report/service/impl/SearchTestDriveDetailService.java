package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SearchTestDriveDetailService {
	public PageInfoDto queryTestDriveDetail(Map<String, String> queryParam) throws ServiceBizException;
	
	 List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出

}
