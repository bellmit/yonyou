package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujiming
* @date 2017年4月7日
*/
public interface ActivityMonthReportService {
	
	//查询
	public PageInfoDto getMonthReportQuery(Map<String, String> queryParam) throws ServiceBizException;
	//下载
	public void getMonthReportDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

}
