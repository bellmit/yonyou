package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

public class CheckVehicleIsMaintain extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckVehicleIsMaintain.class);
	
	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查车辆保养是否索赔开始-----------------------");
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
			 * 检查步骤：获得当前车辆VIN，保养套餐信息
			 */
			String vin=!"".equals(CommonUtils.checkNull(dto.getVin()))?
					CommonUtils.checkNull(dto.getVin())
					:CommonUtils.checkNull(params.get("VIN"));
			String packageCode=!"".equals(CommonUtils.checkNull(dto.getPackageCode()))?
					CommonUtils.checkNull(dto.getPackageCode())
					:CommonUtils.checkNull(params.get("packageCode"));
			if("".equals(vin)||"".equals(packageCode)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "系统检查车辆是否申请索赔时无法获得保养套餐代码或车辆VIN");
				retMap.put("ERROR_CODE", "ERROR_RULE_101");
				return retMap;
			}
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("SELECT TWC.CLAIM_ID\n" );
			sqlStr.append("FROM TT_WR_CLAIM TWC WHERE TWC.VIN='"+vin+"'\n" );
			sqlStr.append("AND TWC.ACTIVITY_CODE='"+packageCode+"' AND TWC.IS_DEL=0\n" );
			sqlStr.append("AND TWC.STATUS<>"+OemDictCodeConstants.CLAIM_STATUS_05);
			List<Object> sqlPara=new ArrayList<Object>();
			
			@SuppressWarnings("unchecked")
			List<Map> resultList=OemDAOUtil.findAll(sqlStr.toString(), new ArrayList());
			if(resultList!=null&&resultList.size()>0){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，该车辆已经申请过该保养索赔，不能重复索赔！");
				retMap.put("ERROR_CODE", "ERROR_RULE_102");
			}else{
				retMap.put("RET_FLAG", "SUCCESS");
				retMap.put("MESSAGE","");
				retMap.put("ERROR_CODE","");
			}
			//HMCI特批后可不对过保进行判断
			String isApprove= CommonUtils.checkNull(dto.getIsApprove(),"0");
			if("0".equals(isApprove)){
				double maintainDays=Double.parseDouble(!"".equals(CommonUtils.checkNull(dto.getBetweenDays()))?
						CommonUtils.checkNull(dto.getBetweenDays(),"0")
						:CommonUtils.checkNull(params.get("betweenDays"),"0"));//取出工单中的保养天数
				if(maintainDays>OemDictCodeConstants.MAINTAIN_MAX_DAYS){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，该车辆已超过4年免费保养期！");
					retMap.put("ERROR_CODE", "ERROR_RULE_114");
					return retMap;
				}
			}
			/**
			 * 检查制动液保养活动开始
			 * 原DTS是手工维护EXCEL对车辆进行管理，DMS将这部分数据放入单独表中，
			 * 所以在校验制动液保养时，需对以前历史也做一次校验才能保证车辆是否能进行该索赔（必要）
			 */
			if(packageCode.indexOf("GHZDY")>=0){
				sqlStr.delete(0, sqlStr.length());
				sqlPara.clear();
				sqlStr.append("SELECT COUNT(1) RET_COUNT FROM TT_WR_ZDY_VIN_HIS WHERE VIN=  '+vin+' \n" );
				sqlPara.add(vin);
				@SuppressWarnings("unchecked")
				Map<String,Object> resultMap=OemDAOUtil.findFirst(sqlStr.toString(), sqlPara);
				if(resultMap!=null&&resultMap.size()>0){
					int count=Integer.parseInt(resultMap.get("RET_COUNT").toString());
					if(count>0){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，该车辆已经申请过该保养索赔，不能重复索赔！");
						retMap.put("ERROR_CODE", "ERROR_RULE_102");
					}else{
						retMap.put("RET_FLAG", "SUCCESS");
						retMap.put("MESSAGE","");
						retMap.put("ERROR_CODE","");
					}
				}else{
					retMap.put("RET_FLAG", "SUCCESS");
					retMap.put("MESSAGE","");
					retMap.put("ERROR_CODE","");
				}
			}
			/**
			 * 检查制动液保养活动结束
			 */
		}
		logger.debug("-------------------检查规则：检查车辆保养是否索赔结束-----------------------");
		return retMap;
	}


}
