package com.yonyou.dms.vehicle.service.claimManage;

import java.sql.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.SmallAreaClaimApprovalDao;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimApprovalHisDcsPO;

/**
* @author liujm
* @date 2017年5月3日
*/

@Service
public class SmallAreaClaimApprovalServiceImpl implements SmallAreaClaimApprovalService {
	
	@Autowired
	private SmallAreaClaimApprovalDao  sacaDao;
	
	/**
	 * 查询（tab页数据）
	 */
	@Override
	public PageInfoDto querySmallAreaClaimApproval(Map<String, String> queryParam, Integer type)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = sacaDao.getSmallAreaClaimApprovalQuery(queryParam, type);
		return pageInfoDto;
	}
	
	/**
	 * 小区 审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = sacaDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}
	/**
	 * 小区审核
	 */
	@Override
	public void smallAreaClaimApproval(ClaimManageDTO cmDto, Integer type) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		
		//检查数据
		if(cmDto.getClaimId() == null || "".equals(cmDto.getClaimId().toString())){
			throw new ServiceBizException("索赔申请单号为空,请检查！");
		}		
		if(cmDto.getAduitInfo() == null || "".equals(cmDto.getAduitInfo().toString())){
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}
		if(cmDto.getSelectGategory() == null || "".equals(cmDto.getSelectGategory().toString())){
			throw new ServiceBizException("分类为空,请检查！");
		}
		if(cmDto.getSelectModel() == null || "".equals(cmDto.getSelectModel().toString())){
			throw new ServiceBizException("方式为空,请联系管理员!");
		}
		

		
      //校验----------- 防止同时审批----------
	  boolean flag = sacaDao.checkData(cmDto, type);
      if( !flag ){
      		throw new ServiceBizException("没有找到需要审批数据,请确认是否已被审批过!");
      }
		
      // 更新索赔单小区审批表 
		
      TtWrClaimPO claimPO = new TtWrClaimPO();
      claimPO = TtWrClaimPO.findFirst(" CLAIM_ID = ? ", cmDto.getClaimId());
      claimPO.set("SMALL_AREA_APPROVAL_STATUS", cmDto.getAduitInfo());
      claimPO.setTimestamp("SMALL_AREA_APPROVAL_DATE", new Date(System.currentTimeMillis()));
      claimPO.set("SMALL_AREA_APPROVAL_USER", loginInfo.getUserId().toString());
      claimPO.set("SELECT_CATEGORY", cmDto.getSelectGategory());
      claimPO.set("SELECT_MODE", cmDto.getSelectModel());
      claimPO.set("UPDATE_BY", loginInfo.getUserId());
      claimPO.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
      claimPO.saveIt();
      
      //审核记录写入索赔单小区审批历史表
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
