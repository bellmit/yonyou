package com.yonyou.dms.vehicle.service.realitySales.scanInvoiceResults;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface ScanInvoiceResultsService {

	public PageInfoDto scanInvoiceResultsQuery(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException;

	public List<Map> scanInvoiceResultsDownLoadList(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	public Map<String, Object> queryDealerDetail(Long id, LoginInfoDto loginInfo)throws ServiceBizException;

}
