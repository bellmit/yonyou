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
import com.yonyou.dms.vehicle.service.claimManage.rule.ClaimOrderSubmitPreCheck;

@Repository
public class CheckOrderStatusRule  extends OemBaseDAO implements CheckRuleInfo{
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ClaimOrderSubmitPreCheck.class);

	@Override
	public Map<String,Object> executeCheckStep(Map<String,Object> params,Map<String,Object> message) {
		logger.debug("-------------------检查规则：检查索赔单提报状态开始-----------------------");
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
			String claimId=!"".equals(CommonUtils.checkNull(dto.getClaimId().toString()))?
					CommonUtils.checkNull(dto.getClaimId().toString())
					:CommonUtils.checkNull(params.get("claimId"));
			if("".equals(claimId)){
				claimId=params==null?"":CommonUtils.checkNull(params.get("CLAIM_ID"));
				if("".equals(claimId)) return retMap;
			}
			/**
			 * 检查步骤：检查索赔单的当前索赔状态，不是未上报，则不能保存当前索赔单
			 */

			@SuppressWarnings("unchecked")
			List<TtWrClaimPO> retList=TtWrClaimPO.find(" CLAIM_ID = ? ", Long.parseLong(claimId));
			if(retList!=null&&retList.size()>0){
				TtWrClaimPO claimInfo=retList.get(0);
				if(OemDictCodeConstants.CLAIM_STATUS_02.equals(claimInfo.get("STATUS"))||OemDictCodeConstants.CLAIM_STATUS_03.equals(claimInfo.get("STATUS"))
						||OemDictCodeConstants.CLAIM_STATUS_05.equals(claimInfo.get("STATUS"))||OemDictCodeConstants.CLAIM_STATUS_06.equals(claimInfo.get("STATUS"))
						||OemDictCodeConstants.CLAIM_STATUS_07.equals(claimInfo.get("STATUS"))||OemDictCodeConstants.CLAIM_STATUS_08.equals(claimInfo.get("STATUS"))){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，索赔单状态已经改变，暂时不能操作保存或提报功能！");
					retMap.put("ERROR_CODE", "ERROR_RULE_11");
					
				}
			}else{
				retMap.put("RET_FLAG", "SUCCESS");
				retMap.put("MESSAGE","");
				retMap.put("ERROR_CODE","");
			}
		}
		logger.debug("-------------------检查规则：检查索赔单提报状态结束-----------------------");
		return retMap;
	}	
	
}
