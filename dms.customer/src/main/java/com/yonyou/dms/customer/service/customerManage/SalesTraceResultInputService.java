package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.QuestionnaireInputDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

@SuppressWarnings("rawtypes")
public interface SalesTraceResultInputService {

	public PageInfoDto querySalesTraceResultInput(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> queryQusetionnaire(String id1, String id2, String id3,String id4) throws ServiceBizException;

	public List<Map> queryBigCustomerHistoryIntent(String id) throws ServiceBizException;

	public PageInfoDto querysaleanddcrc(String id, Map<String, String> queryParam) throws ServiceBizException;

	List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException;// 使用excel导出

	List<Map> querySafeToExport1(String id) throws ServiceBizException;// 使用excel导出

	List<Map> querySafeToExport2(String id, Map<String, String> queryParam) throws ServiceBizException;// 使用excel导出

	public List<Map> qrySalesConsultant(String orgCode) throws ServiceBizException;

	Map<String, Object> findById(String id,String id1) throws ServiceBizException;

	public String updateTracingtaskSales(String id, QuestionnaireInputDTO questionnaireInputDTO)
			throws ServiceBizException;
	
	public List<Map> querySalesTraceTaskLog(String id) throws ServiceBizException;

}
