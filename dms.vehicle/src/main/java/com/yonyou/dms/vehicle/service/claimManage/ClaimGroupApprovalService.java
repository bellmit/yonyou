package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;

/**
* @author liujm
* @date 2017年5月4日
*/

public interface ClaimGroupApprovalService {
	
	//查询（索赔组审核）
	public PageInfoDto queryClaimGroupApproval(Map<String,String> queryParam, Integer type) throws ServiceBizException;
		
	//索赔组审核 审核历史
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔组审核
	public void claimGroupApproval(ClaimManageDTO cmDto)throws ServiceBizException;
		
		
}
