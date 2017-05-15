package com.yonyou.dms.manage.service.salesPlanManager;




import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsYearlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsYearlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanPO;

/**
 * 年度目标上传
 * zhengzengliang
 */
@SuppressWarnings("rawtypes")
public interface YearPlanImportService {

	public void deleteTmpVsYearlyPlan() throws ServiceBizException;

	public void insertTmpVsYearlyPlan(TmpVsYearlyPlanDTO rowDto) throws ServiceBizException;

	public ImportResultDto<TmpVsYearlyPlanDTO> checkData(TmpVsYearlyPlanDTO rowDto) throws ServiceBizException;

	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) throws ServiceBizException;

	public int getTmpTtVsYearlyPlanTotal(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> checkImportData(String string) throws ServiceBizException;

	public List<TtVsYearlyPlanPO> selectTtVsYearlyPlan(String year) throws ServiceBizException;

	public void deleteTtVsYearlyPlanDetail(TtVsYearlyPlanDetailPO detailPo) throws ServiceBizException;

	public void clearUserYearlyPlan(TtVsYearlyPlanPO actPo) throws ServiceBizException;

	public List<TmpVsYearlyPlanPO> selectTmpVsYearlyPlan(String year) throws ServiceBizException;

	public List<Map> findMaxPlanVer(String year, String planType) throws ServiceBizException;

	public TtVsYearlyPlanPO getYearlyPlanPo(int plan, String planType, TmpVsYearlyPlanPO po, String year) throws ServiceBizException;

	public void insertDetailPo(String groupCode, Long planId, Integer month, Integer saleAmt, Long userId) throws ServiceBizException;
	
	
}
