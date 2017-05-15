package com.yonyou.dms.vehicle.service.claimManage;

import java.sql.Date;
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
import com.yonyou.dms.vehicle.dao.claimManage.BigAreaClaimApprovalDao;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimApprovalHisDcsPO;

/**
* @author liujm
* @date 2017年5月2日
*/

@Service
public class BigAreaClaimApprovalServiceImpl implements BigAreaClaimApprovalService{
	
	@Autowired
	private BigAreaClaimApprovalDao bacaDao;
	
	/**
	 * 未审核查询
	 */
	@Override
	public PageInfoDto queryBigAreaClaimApproval(Map<String, String> queryParam, Integer type)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = bacaDao.getBigAreaClaimApprovalQuery(queryParam, type);
		return pageInfoDto;
	}
	/**
	 * 大区 审核
	 */
	@Override
	public void bigAreaClaimApproval(ClaimManageDTO cmDto) throws ServiceBizException {
		//校验
		if(cmDto.getClaimId() == null || "".equals(cmDto.getClaimId().toString())){
			throw new ServiceBizException("索赔申请单号为空,请检查！");
		}
		if(cmDto.getAduitInfo() == null || "".equals(cmDto.getAduitInfo().toString())){
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}
		
		
		//校验 防止同时审批
		List<TtWrClaimPO>  calimList = TtWrClaimPO.find("   CLAIM_ID = ? AND BIG_AREA_APPROVAL_STATUS = ? AND DEALER_CODE =  ? AND IS_DEL = ? ", 
											cmDto.getClaimId(),OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_01,cmDto.getDealerCode(),OemDictCodeConstants.IS_DEL_00);
		if( calimList == null || calimList.size() <= 0 ){
			throw new ServiceBizException("没有找到需要审批数据,请确认是否已被审批过!");
		}
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//更新索赔单大区审批表
		TtWrClaimPO twcPo = new TtWrClaimPO();
		twcPo = TtWrClaimPO.findFirst(" CLAIM_ID = ?  ", cmDto.getClaimId());
		twcPo.set("BIG_AREA_APPROVAL_STATUS", cmDto.getAduitInfo());
		twcPo.setTimestamp("BIG_AREA_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		twcPo.set("BIG_AREA_APPROVAL_USER", loginInfo.getUserId().toString());
		twcPo.set("UPDATE_BY", loginInfo.getUserId());
		twcPo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
		twcPo.saveIt();
		
		//审批记录写入索赔单大区审批历史表
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
	
	/**
	 * 大区 审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = bacaDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}

}







