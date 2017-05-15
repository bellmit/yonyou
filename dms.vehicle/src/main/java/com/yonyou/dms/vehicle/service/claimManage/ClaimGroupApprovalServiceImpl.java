package com.yonyou.dms.vehicle.service.claimManage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.ClaimGroupApprovalDao;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimApprovalHisDcsPO;

/**
* @author liujm
* @date 2017年5月3日
*/

@Service
public class ClaimGroupApprovalServiceImpl implements ClaimGroupApprovalService{
	
	
	@Autowired
	private ClaimGroupApprovalDao claimDao;
	
	
	/**
	 *查询  索赔组
	 */
	@Override
	public PageInfoDto queryClaimGroupApproval(Map<String, String> queryParam, Integer type)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = claimDao.getClaimGroupApprovalQuery(queryParam, type);
		return pageInfoDto;
	}
	
	/**
	 * 索赔组 审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = claimDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}
	/**
	 * 索赔组审核
	 */
	@Override
	public void claimGroupApproval(ClaimManageDTO cmDto) throws ServiceBizException {
		//校验
		if(cmDto.getClaimId() == null || "".equals(cmDto.getClaimId().toString())){
			throw new ServiceBizException("索赔申请单号为空,请检查！");
		}
		if(cmDto.getAduitInfo() == null || "".equals(cmDto.getAduitInfo().toString())){
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}
		
		//------防止同时审批
		
		List<TtWrClaimPO>  calimList = TtWrClaimPO.find(" DEALER_CODE =  ? AND CLAIM_ID = ? AND CLAIM_GROUP_APPROVAL_STATUS = ? AND IS_DEL = ? ", 
				cmDto.getDealerCode(),cmDto.getClaimId(), OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01, OemDictCodeConstants.IS_DEL_00);
		if( calimList == null || calimList.size() <= 0 ){
				throw new ServiceBizException("没有找到需要审批数据,请确认是否已被审批过!");
		}
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
							
		//更新索赔单审批表(索赔组)
		TtWrClaimPO twcPo = new TtWrClaimPO();
		twcPo = TtWrClaimPO.findFirst(" CLAIM_ID = ?  ", cmDto.getClaimId());
		twcPo.set("CLAIM_GROUP_APPROVAL_STATUS", cmDto.getAduitInfo());
		twcPo.setTimestamp("CLAIM_GROUP_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		twcPo.set("CLAIM_GROUP_APPROVAL_USER", loginInfo.getUserId().toString());
		twcPo.set("UPDATE_BY", loginInfo.getUserId());
		twcPo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
		//twcPo.saveIt();
		//成功保存索赔单审批信息历史表(索赔组)
		TtWrClaimApprovalHisDcsPO twcahPo = new TtWrClaimApprovalHisDcsPO();		
		twcahPo.set("CLAIM_ID", cmDto.getClaimId());
		twcahPo.set("CLAIM_NO", cmDto.getClaimNo());
		twcahPo.set("APPROVAL_STATUS", cmDto.getAduitInfo());
		twcahPo.set("APPROVAL_REMARK", cmDto.getApprovalRemark());
		twcahPo.set("APPROVAL_USER", loginInfo.getUserId());
		twcahPo.setTimestamp("APPROVAL_DATE", new Date(System.currentTimeMillis()));
		twcahPo.set("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
		twcahPo.saveIt();
		
		
	}
	
	
	
}




