package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月27日
*/

public interface ClaimNoticeQueryService {

	//查询（主页面）
	public PageInfoDto queryClaimNotice(Map<String,String> queryParam) throws ServiceBizException;
		
	//查询（明细）
	public PageInfoDto queryClaimNoticeDetail(Long claimBillingId) throws ServiceBizException;
	
	//查询（明细）
	public Map queryClaimNoticeDetailMap(Long claimBillingId) throws ServiceBizException;
	
	//开票（明细）
	public void updateClaimNotice(Long claimBillingId,Integer isInvoice) throws ServiceBizException;
	
	//下载
	public void claimNoticeDownload(Map<String,String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
	
}
