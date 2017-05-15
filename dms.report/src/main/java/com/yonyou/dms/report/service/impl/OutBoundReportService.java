package com.yonyou.dms.report.service.impl;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.report.domains.DTO.OutBoundReportDTO;

public interface OutBoundReportService {
	
	 public PageInfoDto searchOutBoundReport(Map<String, String> param)throws ServiceBizException;
	 
	 public PageInfoDto queryEntityNum(Map<String, String> param)throws ServiceBizException;
	 
	 public PageInfoDto queryOutBundDetail(Map<String, String> param,String soldBy)throws ServiceBizException;
	 
	 public Map<String, Object> queryOutBundDetailbyVin(String vin) throws ServiceBizException;
	 
	 public void updateCusInfoById(OutBoundReportDTO outBoundReportDTO)throws ServiceBizException;
	 
	 List<Map> queryOutBundDetailExport(Map<String, String> param) throws ServiceBizException;//使用excel导出
}
