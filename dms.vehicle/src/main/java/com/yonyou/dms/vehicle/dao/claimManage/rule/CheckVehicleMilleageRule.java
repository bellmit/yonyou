package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckVehicleMilleageRule extends OemBaseDAO implements CheckRuleInfo{
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(CheckVehicleMilleageRule.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查索赔单车辆里程开始-----------------------");
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
			
			String vin="".equals(CommonUtils.checkNull(dto.getVin()))?params.get("VIN").toString():CommonUtils.checkNull(dto.getVin());//VIN
			String milleage="".equals(CommonUtils.checkNull(dto.getMilleage()))?params.get("MILLEAGE").toString():CommonUtils.checkNull(dto.getMilleage());//里程数			
			if("".equals(vin)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE","索赔失败，请检查是否填写VIN信息！");
				retMap.put("ERROR_CODE","ERROR_RULE_21");
				logger.debug("-------------------检查步骤：检查索赔单车辆里程结束-----------------------");
				return retMap;
			}
			if("".equals(milleage)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE","索赔失败，请检查是否填写里程数信息！");
				retMap.put("ERROR_CODE","ERROR_RULE_22");
				logger.debug("-------------------检查步骤：检查索赔单车辆里程结束-----------------------");
				return retMap;
			}
			/**
			 * 检查步骤：检查车辆的里程数是否越跑越小
			 */
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("SELECT COALESCE(TV.MILLEAGE,0) MILLEAGE FROM TM_VEHICLE TV WHERE TV.VIN='"+vin+"'");

			@SuppressWarnings("unchecked")			
			List<Map> retList=OemDAOUtil.findAll(sqlStr.toString(), new ArrayList());
			if(retList!=null&&retList.size()>0){
				Map<String,Object> vehicleMap=retList.get(0);
				double vehicleMilleage=Double.parseDouble(vehicleMap.get("MILLEAGE").toString());
				if(Double.parseDouble(milleage)<vehicleMilleage){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE","索赔失败，车辆里程数不能越跑越小，如需索赔请选择HMCI特批！");
					retMap.put("ERROR_CODE","ERROR_RULE_23");
				}
			}else{
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE","索赔失败，无法查询到该车辆信息！");
				retMap.put("ERROR_CODE","ERROR_RULE_24");
				
			}
			/**
			 * 检查步骤：检查本次索赔单里程数是否小于上次索赔单
			 */
		}
		logger.debug("-------------------检查步骤：检查索赔单车辆里程结束-----------------------");
		return retMap;
	}

	

}
