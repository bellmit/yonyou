package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.BalanceDTO;

@SuppressWarnings("rawtypes")
public interface FreeSettlementService {

	public PageInfoDto searchRepairOrder(Map<String, String> queryParam) throws ServiceBizException;// 查询

	public List<Map> queryRoLabourByRoNO(String id) throws ServiceBizException;

	public List<Map> queryRoRepairPartByRoNO(String id) throws ServiceBizException;

	public List<Map> queryRoAddItemByRoNO(String id) throws ServiceBizException;

	public PageInfoDto queryForSettlement(Map<String, String> queryParam) throws ServiceBizException;// 查询
	
	public List<Map> querySalesPartList(String id) throws ServiceBizException;
	
	public PageInfoDto queryOwnerAndCustomer(Map<String, String> queryParam) throws ServiceBizException;// 查询
	
	public List<Map> queryOtherCost() throws ServiceBizException;// 查询
	
	void  addBalanceAccounts(BalanceDTO balanceDTO) throws ServiceBizException;
}
