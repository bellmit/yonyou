package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* @author liujm
* @date 2017年4月25日
*/
public interface FriendlyClaimApplyService {
	//查询（主页面）
	public PageInfoDto queryFriendlyClaimApply(Map<String,String> queryParam) throws ServiceBizException;
	
	//工单明细 零部件清单
	public PageInfoDto repairQueryPartMsgDetail(String repairNo) throws ServiceBizException;
		
	//工单明细 维修工时清单
	public PageInfoDto repairQueryItemMsgDetail(String repairNo) throws ServiceBizException;
	
	//工单明细 其他项目清单
	public PageInfoDto repairQueryOtherItemMsgDetail(String repairNo) throws ServiceBizException;
	
	//工单明细 车辆信息
	public Map repairQueryVehicleMsgDetail(String vin) throws ServiceBizException;
	
	//工单明细 基本信息
	public Map repairQueryMsgDetail(String repairNo) throws ServiceBizException;
		
	//索赔明细  申请信息 
	public Map claimQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔明细  情形
	public PageInfoDto claimCaseQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔明细  工时
	public PageInfoDto claimLabourQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔明细 零部件
	public PageInfoDto claimPartQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔明细 其他项目
	public PageInfoDto claimOtherQueryDetail(Long claimId) throws ServiceBizException;
	
	//索赔明细 审核历史
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException;
	
	
}



