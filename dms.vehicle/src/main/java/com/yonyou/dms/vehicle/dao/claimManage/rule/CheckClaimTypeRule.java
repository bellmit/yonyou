package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckClaimTypeRule extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckClaimTypeRule.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查保修索赔类型合法性开始-----------------------");
		ClaimApplyDTO dto =(ClaimApplyDTO) params.get("DTO");
		//获取当前用户
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String,Object> retMap=new HashMap<String, Object>();
		String RET_FLAG=message!=null&&message.get("RET_FLAG")!=null?message.get("RET_FLAG").toString():"";
		String ERROR_CODE=message!=null&&message.get("ERROR_CODE")!=null?message.get("ERROR_CODE").toString():"";
		
		if("ERROR".equals(RET_FLAG)||!"".equals(ERROR_CODE)){
			retMap.put("RET_FLAG", "ERROR");
			retMap.put("MESSAGE", message.get("MESSAGE"));
			retMap.put("ERROR_CODE", ERROR_CODE);
			logger.debug("-------------------检查规则：检查保修索赔类型合法性结束-----------------------");
			return retMap;
		}else{
			retMap.put("RET_FLAG", "SUCCESS");
			retMap.put("MESSAGE", "");
			retMap.put("ERROR_CODE", "");
			
			String dealerId=logonUser.getDealerId().toString();
			if("".equals(dealerId)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，无法获得登陆账户信息！");
				retMap.put("ERROR_CODE", "ERROR_RULE_41");
				logger.debug("-------------------检查规则：检查保修索赔类型合法性结束-----------------------");
				return retMap;
			}
			String repair_type="".equals(CommonUtils.checkNull(dto.getRepairType()))?
					params.get("repairType").toString():CommonUtils.checkNull(dto.getRepairType().toString());//维修类型
			String claim_type="".equals(CommonUtils.checkNull(dto.getClaimType()))?
					params.get("claimType").toString():CommonUtils.checkNull(dto.getClaimType());//索赔类型
			if(OemDictCodeConstants.REPAIR_FIXED_TYPE_16.toString().equals(repair_type)){
				//检查维修类型是服务活动的，是否和索赔类型（召回/PUD/PIC）一致
				if(OemDictCodeConstants.CLAIM_TYPE_01.toString().equals(claim_type)
						||OemDictCodeConstants.CLAIM_TYPE_02.toString().equals(claim_type)
						||OemDictCodeConstants.CLAIM_TYPE_03.toString().equals(claim_type)
						||OemDictCodeConstants.CLAIM_TYPE_05.toString().equals(claim_type)){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，当前维修类型是服务活动，索赔类型【召回/PUD/PIC】！");
					retMap.put("ERROR_CODE", "ERROR_RULE_42");
					logger.debug("-------------------检查规则：检查保修索赔类型合法性结束-----------------------");
					return retMap;
				}
			}else{
				//检查维修类型是服务活动的，是否和索赔类型（服务活动、召回/PUD/PIC）一致
				if(OemDictCodeConstants.CLAIM_TYPE_03.toString().equals(claim_type)
						||OemDictCodeConstants.CLAIM_TYPE_04.toString().equals(claim_type)){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，当前维修类型非服务活动，索赔类型不能是【服务活动】或【召回/PUD/PIC】！");
					retMap.put("ERROR_CODE", "ERROR_RULE_43");
					logger.debug("-------------------检查规则：检查保修索赔类型合法性结束-----------------------");
					return retMap;
				}
			}
		}
		return retMap;
	}


}
