package com.yonyou.dms.vehicle.service.claimManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.FriendlyClaimApplyDao;

/**
* @author liujm
* @date 2017年4月25日
*/

@Service
public class FriendlyClaimApplyServiceImpl implements FriendlyClaimApplyService {
	
	@Autowired
	private FriendlyClaimApplyDao fcaDao;
	
	
	
	/**
	 * 查询（主页面）
	 */
	@Override
	public PageInfoDto queryFriendlyClaimApply(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getFriendlyClaimApplyQuery(queryParam);
		return pageInfoDto;
	}


	/**
	 * 工单明细 零部件清单
	 */
	@Override
	public PageInfoDto repairQueryPartMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryPartMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 维修工时清单
	 */
	@Override
	public PageInfoDto repairQueryItemMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryItemMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 其他项目清单
	 */
	@Override
	public PageInfoDto repairQueryOtherItemMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryOtherItemMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 车辆信息
	 */
	@Override
	public Map repairQueryVehicleMsgDetail(String vin) throws ServiceBizException {
		Map map = fcaDao.getRepairQueryVehicleMsgDetail(vin);
		return map;
	}


	/**
	 * 工单明细 基本信息
	 */
	@Override
	public Map repairQueryMsgDetail(String repairNo) throws ServiceBizException {
		Map map = fcaDao.getRepairQueryMsgDetail(repairNo);
		return map;
	}

	/**
	 * 索赔明细  申请信息
	 */
	@Override
	public Map claimQueryDetail(Long claimId) throws ServiceBizException {
		Map map = fcaDao.getClaimQueryDetail(claimId);
		return map;
	}

	/**
	 * 索赔明细  情形
	 */
	@Override
	public PageInfoDto claimCaseQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimCaseQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细  工时
	 */
	@Override
	public PageInfoDto claimLabourQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimLabourQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 零部件
	 */
	@Override
	public PageInfoDto claimPartQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimPartQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 其他项目
	 */
	@Override
	public PageInfoDto claimOtherQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimOtherQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}

}
