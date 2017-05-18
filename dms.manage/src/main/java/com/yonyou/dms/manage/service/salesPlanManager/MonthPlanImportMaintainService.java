package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyPlanDTO;

/**
 * 
* @ClassName: MonthPlanImportMaintainService 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月14日 上午10:06:34 
*
 */
@SuppressWarnings("rawtypes")
public interface MonthPlanImportMaintainService {
	
	public List<Map> getSeriesCode() throws ServiceBizException;

	public List<Map> allMessageQuery(int i, LoginInfoDto loginInfo) throws ServiceBizException;

	public List<Map> selectTtVsMonthlyPlan(TmpVsMonthlyPlanDTO rowDto) throws ServiceBizException;

	public List<Map<String, String>> parsingSeriesJson(Map<String, Object> tmp) throws ServiceBizException;

	public String getPlayMonth(String planMonth) throws ServiceBizException;

	public ArrayList<TmpVsMonthlyPlanDTO> monthPlanInfoImport(ImportResultDto<TmpVsMonthlyPlanDTO> importResult, Map<String, String> queryParam);

	TmpVsMonthlyPlanDTO ImportDataRecord(TmpVsMonthlyPlanDTO rowDto, List<Map> seriesCodeList,
			ArrayList<TmpVsMonthlyPlanDTO> dataList) throws ServiceBizException;


}
