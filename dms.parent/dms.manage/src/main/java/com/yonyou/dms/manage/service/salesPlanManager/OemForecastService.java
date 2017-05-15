package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.OemForecastAuditDTO;

/**
 * 生产订单审核业务层
 * zhengzengliang
 */
@SuppressWarnings("rawtypes")
public interface OemForecastService {

	
	public List<Map> getYearList() throws ServiceBizException;
	
	public List<Map> getTaskCode(Map<String, String> queryParam) throws ServiceBizException;
	
	public PageInfoDto getOTDForecastQueryList(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> queryforecastAuditDataExport(Map<String, String> queryParam) throws ServiceBizException;
	
	public void insertTmpVsProImpAudit(OemForecastAuditDTO forecast) throws ServiceBizException;
	
	public List<Map> forecastQueryInit() throws ServiceBizException;
	
	public List<Map> getMonthPlanTaskNoList2() throws ServiceBizException;
	
	public PageInfoDto forecastQueryTotal(Map<String, String> queryParam) throws ServiceBizException;
	
	public PageInfoDto getOemForecastQueryTotalList2(Map<String, String> queryParam) throws ServiceBizException;
	
	public PageInfoDto getOemForecastQueryDetailList(Map<String, String> queryParam) throws ServiceBizException;
	
	public PageInfoDto findNoHandInDelears2(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> forecastTotalDownload(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> forecastTotalDownload2(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> forecastDetailDownload(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> noHandInDealerDownload(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> getMonthForecastYearList() throws ServiceBizException;
	
	public List<Map> getReportTaskNoList(Map<String, String> queryParam) throws ServiceBizException;
	
	public PageInfoDto OTDfindBySerialNumber(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> getOemForecastQueryQueryListOtd(Map<String, String> queryParam) throws ServiceBizException;

	public ImportResultDto<OemForecastAuditDTO> checkData(String taskId) throws ServiceBizException;

	public List<Map> getTmVsProImpAudit() throws ServiceBizException;
	
	
}
