package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;

/**
* @author liujm
* @date 2017年5月2日
*/
public interface BigAreaClaimApprovalService {
	
	
	//查询（大区待审核）
	public PageInfoDto queryBigAreaClaimApproval(Map<String,String> queryParam, Integer type) throws ServiceBizException;
	
	//大区审核
	public void bigAreaClaimApproval(ClaimManageDTO cmDto)throws ServiceBizException;
	
	//大区审核 审核历史
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException;
		
	
}
