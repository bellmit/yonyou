package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.SalesTraceTaskDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SalesTraceTaskService {
	
	 public PageInfoDto querySalesTraceTask(Map<String, String> queryParam) throws ServiceBizException;
	 
	 public void modifySoldBy(SalesTraceTaskDTO salesTraceTaskDTO) throws ServiceBizException;
	 
	 List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;//使用excel导出
}
