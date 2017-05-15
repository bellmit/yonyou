package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.customer.domains.DTO.customerManage.SalesGatheringDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SalesGatheringService {

	PageInfoDto queryCusInfo(Map<String, String> queryParam) throws ServiceBizException;
	
	public List<Map> salesGatheringbyId(String id) throws ServiceBizException;
	
	public List<Map> salesAmountbyId(String id) throws ServiceBizException;
	
	public Map salesEditGatheringbyId(String id) throws ServiceBizException;
	
	public void addSalesGathering(SalesGatheringDTO salesGatheringDto) throws ServiceBizException;
	
    public Map<String, Object> editSalesGathering(String id, SalesGatheringDTO salesGatheringDto,
            String customerNo) throws ServiceBizException;
	
	public List<Map> qryPayTypeCode(String orgCode) throws ServiceBizException;
	
}