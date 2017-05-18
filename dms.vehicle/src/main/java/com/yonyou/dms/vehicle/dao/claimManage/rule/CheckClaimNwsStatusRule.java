package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckClaimNwsStatusRule extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckClaimNwsStatusRule.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查索赔单NWS状态开始-----------------------");
		ClaimApplyDTO dto =(ClaimApplyDTO) params.get("DTO");
		Map<String,Object> retMap=new HashMap<String, Object>();
		String RET_FLAG=message!=null&&message.get("RET_FLAG")!=null?message.get("RET_FLAG").toString():"";
		String ERROR_CODE=message!=null&&message.get("ERROR_CODE")!=null?message.get("ERROR_CODE").toString():"";
		
		if("ERROR".equals(RET_FLAG)||!"".equals(ERROR_CODE)){
			retMap.put("RET_FLAG", "ERROR");
			retMap.put("MESSAGE", message.get("MESSAGE"));
			retMap.put("ERROR_CODE", ERROR_CODE);
		}else{
			retMap.put("RET_FLAG", "SUCCESS");
			retMap.put("MESSAGE", "");
			retMap.put("ERROR_CODE", "");
			String claimId=!"".equals(CommonUtils.checkNull(dto.getClaimId()))?
					CommonUtils.checkNull(dto.getClaimId())
					:CommonUtils.checkNull(params.get("claimId"));
			if("".equals(claimId)){
				claimId=CommonUtils.checkNull(params.get("claimId"));
				if("".equals(claimId)){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，没有获得索赔单ID！");
					retMap.put("ERROR_CODE", "ERROR_RULE_91");
					return retMap;
				} 
			}
			/**
			 * 检查步骤：检查索赔单的当前NWS状态
			 */
			
			@SuppressWarnings("unchecked")
			List<TtWrClaimPO> retList=TtWrClaimPO.find(" CLAIM_ID = ? ",Long.parseLong(claimId));
			if(retList!=null&&retList.size()>0){
				TtWrClaimPO claimInfo=retList.get(0);
				if(!OemDictCodeConstants.NWS_STATUS_03.toString().equals("40091003")){
					
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，索赔NWS状态已被更改，不能进行当前操作！");
					retMap.put("ERROR_CODE", "ERROR_RULE_92");
				}
			}else{
				retMap.put("RET_FLAG", "SUCCESS");
				retMap.put("MESSAGE","");
				retMap.put("ERROR_CODE","");
			}
		}
		logger.debug("-------------------检查规则：检查索赔单NWS状态结束-----------------------");
		return retMap;
	}

}
