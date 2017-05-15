package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;

/**
* @author liujm
* @date 2017年5月3日
*/
public interface SmallAreaClaimApprovalService {
	
	//查询（tab页数据）
	public PageInfoDto querySmallAreaClaimApproval(Map<String,String> queryParam, Integer type) throws ServiceBizException;
		
	//小区审核 审核历史
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException;
	
	//小区审核 
	public void smallAreaClaimApproval(ClaimManageDTO cmDto, Integer type) throws ServiceBizException;
			
}
