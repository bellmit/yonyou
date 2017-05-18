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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;
@Repository
public class CheckVehicleIsActivity extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckVehicleIsActivity.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查车辆参加服务活动情况开始-----------------------");
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
			//如果索赔单存在则不检查服务活动参加情况，该规则只检查没生成索赔单的车辆服务活动
			String claimId=!"".equals(CommonUtils.checkNull(dto.getClaimId()))?
					CommonUtils.checkNull(dto.getClaimId())
					:CommonUtils.checkNull(params.get("claimId"));
			if(!"".equals(claimId)){
				return retMap;
			}
			/**
			 * 检查步骤：获得当前车辆VIN，登陆特约店信息和服务活动信息
			 */
			String vin=!"".equals(CommonUtils.checkNull(dto.getVin()))?
					CommonUtils.checkNull(dto.getVin())
					:CommonUtils.checkNull(params.get("VIN"));
			String activityCode=!"".equals(CommonUtils.checkNull(dto.getActivityCode()))?
					CommonUtils.checkNull(dto.getActivityCode())
					:CommonUtils.checkNull(params.get("activityCode"));
			if("".equals(vin)||"".equals(activityCode)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "系统检查车辆参加服务活动情况时无法获得足够的参数");
				retMap.put("ERROR_CODE", "ERROR_RULE_61");
				return retMap;
			}
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("SELECT TWC.CLAIM_ID\n" );
			sqlStr.append("FROM TT_WR_CLAIM_DCS TWC WHERE TWC.VIN=?\n" );
			sqlStr.append("AND TWC.ACTIVITY_CODE=? AND TWC.IS_DEL=0\n" );
			sqlStr.append("AND TWC.STATUS<>?");
			List<Object> sqlPara=new ArrayList<Object>();
			sqlPara.add(vin);
			sqlPara.add(activityCode);
			sqlPara.add(OemDictCodeConstants.CLAIM_STATUS_05);
			
			@SuppressWarnings("unchecked")
			List<Map> resultList=OemDAOUtil.findAll(sqlStr.toString(), sqlPara);
			if(resultList!=null&&resultList.size()>0){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，这台车已经做过本次活动索赔，不能重复索赔！");
				retMap.put("ERROR_CODE", "ERROR_RULE_62");
			}else{
				retMap.put("RET_FLAG", "SUCCESS");
				retMap.put("MESSAGE","");
				retMap.put("ERROR_CODE","");
			}
		}
		logger.debug("-------------------检查规则：检查车辆参加服务活动情况结束-----------------------");
		return retMap;
	}
}
