package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsRetailTaskPO;

/**
 * 生产订单任务下发
 * zhengzengliang
 */
@SuppressWarnings("rawtypes")
public interface ForecastImportService {
	
	public PageInfoDto queryByMaterialid1(Map<String, String> queryParam) throws ServiceBizException;
	
	public Long addMaterialIds(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException;
	
	public Long retailforecastIssuedAdd(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException;
	
	public List<Map> getMaterialIdList(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException;
	
	public Long addMaterialId(Map materialMap, Long taskId) throws ServiceBizException;
	
	public void deleteTmpVsMonthlyForecast(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException;

	public PageInfoDto getRetailforecasList3(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> forecastImportOTDQuery(Map<String, String> queryParam) throws ServiceBizException;

	public void modifyforecastOTDSubmit(Long taskId, TtVsMonthlyForecastDTO userDto) throws ServiceBizException;

	public List<Map> selectTmVhclMaterialGroupUnique(TmVhclMaterialGroupPO tvmgPo) throws ServiceBizException;

	public List<Map> selectTtVsRetailTaskUnique(TtVsRetailTaskPO trtPO) throws ServiceBizException;

	public List<Map> getForecastPackageOTDFilterList(String groupCode, LoginInfoDto loginInfo,
			String taskId) throws ServiceBizException;

	public List<Map> getForecastCarOTDListTotal(String groupCode, LoginInfoDto loginInfo,
			String taskId) throws ServiceBizException;

	public List<Map> findModelYearList();

	public List<Map> findGroupByModelYear(String modelYear);

	public List<Map> findColorAndTrim(Map<String, String> params);

	public boolean saveColorAndTrim(Map<String, String> params);

	public boolean delColorAndTrim(Long id);

	public List<Map> getForecastColorOTDFilterList(String packageId, LoginInfoDto loginInfo, String taskId,
			String detailId);

	public void saveOTDForecast2(String taskId, String colorDetailId, String groupId, String forecastAmount,
			String[] numsColor, String[] materialIds, LoginInfoDto loginInfo);
	
	
	
}
