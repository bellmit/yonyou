package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;

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
	
	//查询工单列表
	public PageInfoDto queryRepairOrderList(Map<String,String> queryParam) throws ServiceBizException;
	
	//查询预授权单信息
	public PageInfoDto queryForeApprovalInfo(Map<String,String> queryParam) throws ServiceBizException;
	
	//查询工单下的维修工时列表
	public PageInfoDto queryRepairOrderClaimLabour(Map<String, String> queryParam) throws ServiceBizException;
	
	//查询工单下的维修零部件列表
	public PageInfoDto queryRepairOrderClaimPart(Map<String, String> queryParam) throws ServiceBizException;
	
	//查询工单下的其他项目列表
	public PageInfoDto queryRepairOrderClaimOther(Map<String, String> queryParam) throws ServiceBizException;
	
	//查询当前登录经销商
	public Map queryDealerCodeAndName(Map<String, String> queryParam) throws ServiceBizException;
		
	//善意索赔申请  新增保存
	public Long saveClaimOrderInfo(ClaimApplyDTO applyDto) throws ServiceBizException;
	//善意索赔申请 新增提报 
	public void submitClaimOrderInfo(ClaimApplyDTO applyDto,Long claimId) throws ServiceBizException;
	
	//修改页面回显信息
	public Map queryEditMessage(Long claimId,Map<String, String> queryParam) throws ServiceBizException;
	
	//删除申请信息
	public void deleteClaimInfo(Long claimId) throws ServiceBizException;
	
	//修改页面   报存
	public void updateClaimOrderInfo(Long claimId,ClaimApplyDTO applyDto) throws ServiceBizException;
		
}



