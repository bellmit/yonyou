package com.yonyou.dms.vehicle.service.claimApproveMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 索赔申请单结算
 * @author ZhaoZ
 * @date 2017年4月28日
 */
public interface ClaimBillOemQueryService {

	//索赔申请单结算查询
	public PageInfoDto threePackItemList(Map<String, String> queryParams)throws ServiceBizException;

	//索赔申请单结算新增
	public PageInfoDto claimBillOemAddList(String claimNos)throws ServiceBizException;
	//索赔结算单查询
	public PageInfoDto claimBillQueryList(Map<String, String> queryParams)throws ServiceBizException;
	//索赔结算单明细查询
	public PageInfoDto claimBillOemDetail(Long balanceId)throws ServiceBizException;
	//索赔结算单dlr查询
	public PageInfoDto claimBillQueryDLRList(Map<String, String> queryParams)throws ServiceBizException;

}
