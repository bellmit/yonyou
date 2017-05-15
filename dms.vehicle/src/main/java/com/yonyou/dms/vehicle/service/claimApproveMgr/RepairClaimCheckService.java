package com.yonyou.dms.vehicle.service.claimApproveMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 保修索赔
 * @author ZhaoZ
 * @date 2017年4月25日
 */
public interface RepairClaimCheckService {

	//保修索赔申请审核查询
	public PageInfoDto repairClaimQueryList(Map<String, String> queryParams)throws ServiceBizException;

	//获得保修索赔申请审核信息
	public Map<String, Object> claimInfo(Long claimId, Long oemCompanyId)throws ServiceBizException;
	//查询索赔单下的其他项目列表
	public PageInfoDto queryOtherItem(Long claimId)throws ServiceBizException;
	//获得索赔单的零部件信息列表
	public PageInfoDto queryRepairPart(Long claimId)throws ServiceBizException;
	//查询索赔单与工时关系
	public PageInfoDto queryClainLabour(Long claimId)throws ServiceBizException;
	//保修索赔单申请审核
	public void approve(TtWrClaimDcsDTO dto, Long claimId)throws ServiceBizException, Exception;
	//查询索赔单审批历史信息
	public PageInfoDto claimHistoryQuery(Long claim)throws ServiceBizException;
	//查询附件
	public PageInfoDto doSearchFujian(Long claim)throws ServiceBizException;
	//索赔单状态跟踪查询
	public PageInfoDto orderInfoList(Map<String, String> queryParams)throws ServiceBizException;
	//索赔单状态跟踪查询
	public List<Map> queryClaimOrderList(Map<String, String> queryParams)throws ServiceBizException;
	//查询索赔单的索赔情形
	public PageInfoDto queryClainCase(Long claimId)throws ServiceBizException;
	//进入保修申请审核
	public Map claimInfo(Long oemCompanyId, String claimNo, String dealerCode)throws ServiceBizException;
	//保修申请审核
	public PageInfoDto repairClaimList(Long oemCompanyId, Map<String, String> queryParams)throws ServiceBizException;
	//进入保修申请审核
	public Map claimInfos(Long oemCompanyId, Map<String, String> queryParams)throws ServiceBizException;

}
