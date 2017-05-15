package com.yonyou.dms.retail.service.rebate;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: RebateSumService 
* @Description: 返利核算汇总查询（OEM）
* @author zhengzengliang 
* @date 2017年4月10日 下午3:00:01 
*
 */
@SuppressWarnings("rawtypes")
public interface RebateSumService {
	
	PageInfoDto getRebateSum(Map<String, String> queryParam);

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;

	List<Map> queryRebateSumMX(Map<String, String> queryParam)throws Exception;

	List<Map> getRebateSumMX(Map<String, String> queryParam);

	List<Map> queryDetailDownSt(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) throws ServiceBizException;

	List<Map> queryDetailDownDy(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) throws ServiceBizException;

	void addExcelExportColumn(List<Map> sList, List<Map> dList, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException;

	PageInfoDto pageQueryDetailDownSt(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) throws ServiceBizException;
}
