package com.yonyou.dms.vehicle.service.claimManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月28日
*/

public interface ClaimStatusSearchDealerService {
	
	//查询（主页面）
	public PageInfoDto queryClaimStatusSearchDealer(Map<String,String> queryParam) throws ServiceBizException;
		
	//下载
	public void downloadClaimStatusSearchDealer(Map<String,String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;

	//索赔申请单状态跟踪明细 审核历史
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException;
	//索赔申请单状态跟踪明细  附件列表
	public PageInfoDto accessoryQueryList(Long claimId) throws ServiceBizException;
	
	//获取索赔类型列表
	public List<Map> queryClaimTypeList() throws ServiceBizException;
	
			
		
}








