package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

public class CheckVehicleMaintenceIsApprove extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckVehicleMaintenceIsApprove.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查车辆保养是否需要HMCI特批开始-----------------------");
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
			/**
			 * 检查步骤：获得当前车辆VIN
			 */
			String vin=!"".equals(CommonUtils.checkNull(dto.getVin()))?
					CommonUtils.checkNull(dto.getVin())
					:CommonUtils.checkNull(params.get("VIN"));
			String packageCode=!"".equals(CommonUtils.checkNull(dto.getPackageCode()))?
					CommonUtils.checkNull(dto.getPackageCode())
					:CommonUtils.checkNull(params.get("packageCode"));
			double maintainDays=Double.parseDouble(!"".equals(CommonUtils.checkNull(dto.getBetweenDays()))?
					CommonUtils.checkNull(dto.getBetweenDays(),"0")
					:CommonUtils.checkNull(params.get("betweenDays"),"0"));//取出工单中的保养天数
			double milleage=Double.parseDouble(!"".equals(CommonUtils.checkNull(dto.getMilleage()))?
					CommonUtils.checkNull(dto.getMilleage(),"0")
					:CommonUtils.checkNull(params.get("MILLEAGE"),"0"));//取出工单中的里程数
			if("".equals(vin)||"".equals(packageCode)){ 
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "系统无法获得车辆VIN或保养信息");
				retMap.put("ERROR_CODE", "ERROR_RULE_111");
				return retMap;
			}
			params.put("VIN", vin);
			//获得车型组ID
			Map<String, Object> vinMap=null;
			try {
				vinMap = quertVehicleDetailInfo(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			String groupId=CommonUtils.checkNull(vinMap.get("GROUP_ID"));
			String groupName=CommonUtils.checkNull(vinMap.get("MATERIAL_CODE"));
			if("".equals(groupId)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "系统无法获得车辆的车型组信息");
				retMap.put("ERROR_CODE", "ERROR_RULE_112");
				return retMap;
			}
			List<Object> sqlPara=new ArrayList<Object>();
			List<Object> sqlPara2=new ArrayList<Object>();
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("SELECT COALESCE(B.MAINTAIN_STARTDAY,0) MAINTAIN_STARTDAY,COALESCE(B.MAINTAIN_ENDDAY,0) MAINTAIN_ENDDAY,COALESCE(B.MAINTAIN_STARTMILEAGE,0) MAINTAIN_STARTMILEAGE,COALESCE(B.MAINTAIN_ENDMILEAGE,0) MAINTAIN_ENDMILEAGE FROM TT_WR_MAINTAIN_PACKAGE B\n" );
			sqlStr.append("WHERE B.MODEL_ID=? AND B.PACKAGE_CODE=? AND IS_DEL=0");
			StringBuffer sqlStrModel= new StringBuffer();
			sqlStrModel.append("select distinct vw.GROUP_name as MODEL_NAME From VW_MATERIAL vw where vw.GROUP_ID in (SELECT b.MODEL_ID FROM TT_WR_MAINTAIN_PACKAGE B\n");
			sqlStrModel.append("WHERE  B.PACKAGE_CODE=? AND IS_DEL=0)");			
			sqlPara.add(groupId);
			sqlPara.add(packageCode);
			sqlPara2.add(packageCode);
			List<Map<String,Object>> dayList=pageQuery(sqlStr.toString(), sqlPara, getFunName());
			List<Map<String,Object>> modelList=pageQuery(sqlStrModel.toString(), sqlPara2, getFunName());
			if(dayList!=null&&dayList.size()>0){
				double startMilleage=Double.parseDouble(dayList.get(0).get("MAINTAIN_STARTMILEAGE").toString());
				double endMilleage=Double.parseDouble(dayList.get(0).get("MAINTAIN_ENDMILEAGE").toString());
				double startDay=Double.parseDouble(dayList.get(0).get("MAINTAIN_STARTDAY").toString());
				double endDay=Double.parseDouble(dayList.get(0).get("MAINTAIN_ENDDAY").toString());
				if(milleage<startMilleage||milleage>endMilleage){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，索赔车辆行驶里程不在本次保养规定的历程范围，如需索赔请选择HMCI特批！");
					retMap.put("ERROR_CODE", "ERROR_RULE_113");
				}else{
					if("ERROR".equals(retMap.get("RET_FLAG").toString())){
						return retMap;
					}
					/*if(maintainDays<startDay||maintainDays>endDay){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，该车辆已超过4年免费保养期，如需索赔请选择HMCI特批！");
						retMap.put("ERROR_CODE", "ERROR_RULE_114");
						return retMap;
					}*/
					retMap.put("RET_FLAG", "SUCCESS");
					retMap.put("MESSAGE","");
					retMap.put("ERROR_CODE","");
				}
			}else{
				if(modelList!=null&&modelList.size()>0){
					String modelName=modelList.get(0).get("MODEL_NAME").toString();
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "该车的车型为:"+groupName+"；但此套餐是"+modelName+"的套餐，无法索赔！");
					retMap.put("ERROR_CODE", "ERROR_RULE_116");
				}else{
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "保养工单信息不正确，无法索赔！");
				retMap.put("ERROR_CODE", "ERROR_RULE_116");
				}
			}
		}
		logger.debug("-------------------检查规则：检查车辆保养是否需要HMCI特批结束-----------------------");
		return retMap;
	}
	
	
	/**
	 * 根据VIN获得车辆详细信息
	 * @param params
	 * @return
	 * @throws BizException 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> quertVehicleDetailInfo(Map<String,Object> params) {
		String vin=CommonUtils.checkNull(params==null?"":params.get("VIN"));//获得车辆VIN
		String condition=CommonUtils.checkNull(params==null?"":params.get("CONDITION"));//获得车辆查询其他条件
		List<Object> sqlPara=new ArrayList<Object>();//参数
		if("".equals(vin)){
			return null;
		}
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT TV.VEHICLE_ID,TV.VIN,COALESCE(TV.MILEAGE,0) MILLEAGE,\n" );
		sqlStr.append("TT.MATERIAL_ID,TT.GROUP_ID,COALESCE(TT.GROUP_NAME,'') MATERIAL_CODE,\n" );
		sqlStr.append("TT.MODEL_CODE,\n" );
		sqlStr.append("TV.MODEL_YEAR,\n" );
		sqlStr.append("TT.COLOR_CODE,TT.COLOR_NAME,\n" );
		sqlStr.append("TV.ENGINE_NO,\n" );
		sqlStr.append("TV.LICENSE_NO,TV.MILEAGE,\n" );
		//sqlStr.append("TV.KEY_NO,TV.MANUAL_NO,\n");//增加‘钥匙号’和‘手册号’
		sqlStr.append("VARCHAR_FORMAT(TV.PURCHASE_DATE,'YYYY-MM-DD') PURCHASE_DATE\n" );
		sqlStr.append("FROM TM_VEHICLE TV LEFT JOIN\n" );
		sqlStr.append("VW_MATERIAL TT\n" );
		sqlStr.append("ON TV.MATERIAL_ID=TT.MATERIAL_ID WHERE VIN=? \n");
		sqlPara.add(vin);
		if(!"".equals(condition)){
			sqlStr.append(condition+" \n");
		}		
		Map<String,Object> retMap=OemDAOUtil.findFirst(sqlStr.toString(), sqlPara);
		return retMap;
		
	}
	

}
