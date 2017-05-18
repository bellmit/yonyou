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
public class CheckPackageInfoRule extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckPackageInfoRule.class);
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------索赔单检查规则：检查保养套餐零部件开始-----------------------");
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
			String vin=!"".equals(CommonUtils.checkNull(dto.getVin()))?
					CommonUtils.checkNull(dto.getVin())
					:CommonUtils.checkNull(params.get("VIN"));
			params.put("VIN", vin);//查询车辆详细信息时用
			params.put("vin", vin);//查询保养套餐时用
			String packageCode=!"".equals(CommonUtils.checkNull(dto.getPackageCode()))?
					CommonUtils.checkNull(dto.getPackageCode())
					:CommonUtils.checkNull(params.get("packageCode"));
			String milleage=!"".equals(CommonUtils.checkNull(dto.getMilleage()))?
					CommonUtils.checkNull(dto.getMilleage())
					:CommonUtils.checkNull(params.get("MILLEAGE"));
			String betweenDays=!"".equals(CommonUtils.checkNull(dto.getBetweenDays()))?
					CommonUtils.checkNull(dto.getBetweenDays())
					:CommonUtils.checkNull(params.get("betweenDays"));
			String groupId="";
			try {
				Map<String,Object> vehicleInfoMap=quertVehicleDetailInfo(params);
				if(vehicleInfoMap!=null&&vehicleInfoMap.size()>0){
					groupId=CommonUtils.checkNull(vehicleInfoMap.get("GROUP_ID"));
				}
			} catch (Exception e) {
			}
			if("".equals(groupId)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，无法获得VIN："+vin+"的车型信息！");
				retMap.put("ERROR_CODE", "ERROR_RULE_121");
				return retMap;
			}
			
			/**
			 * 根据车辆VIN和里程数查找对应的保养套餐代码，如果和上报的保养套餐不一致则报错
			 */
			params.put("groupId", groupId);
			params.put("milleage", milleage);
			params.put("maintenceDays", betweenDays);
			params.put("packageCode", packageCode);
			List<Map> packageInfoList=queryPackageInfo(params);
			if(packageInfoList!=null&&packageInfoList.size()>0){
				Map<String,Object> packageMap=packageInfoList.get(0);
				String dcsPackageCode=CommonUtils.checkNull(packageMap.get("PACKAGE_CODE"));//上端系统对应的保养套餐代码
				if(!dcsPackageCode.equals(packageCode)){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "索赔失败，保养套餐代码【"+packageCode+"】在HMCI中不存在！");
					retMap.put("ERROR_CODE", "ERROR_RULE_121");
					return retMap;
				}
			}else{
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，该车没有对应的保养信息，请联系管理员！");
				retMap.put("ERROR_CODE", "ERROR_RULE_122");
				return retMap;
			}
			/**
			 * 根据保养套餐代码查询：支持查询替换件的父件和所有子替换件
			 */
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("WITH DD (LEVEL_SEQ, PART_ID, PART_SHOW_CODE,NEW_PART_CODE)\n" );
			sqlStr.append("AS (SELECT -1, PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("    FROM TT_PT_PART_BASE\n" );
			sqlStr.append("    WHERE NEW_PART_CODE IN(\n" );
			sqlStr.append("    SELECT TMP.PART_CODE\n" );
			sqlStr.append("    FROM  TT_WR_MAINTAIN_PACKAGE TM, TT_WR_MAINTAIN_PART TMP\n" );
			sqlStr.append("    WHERE TM.PACKAGE_ID=TMP.PACKAGE_ID AND TM.MODEL_ID="+groupId+"\n" );
			sqlStr.append("    AND TM.PACKAGE_CODE='"+packageCode+"' AND TM.IS_DEL=0\n" );
			sqlStr.append("    )\n" );
			sqlStr.append("    UNION ALL\n" );
			sqlStr.append("    SELECT 0, PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("    FROM TT_PT_PART_BASE\n" );
			sqlStr.append("    WHERE PART_CODE IN(\n" );
			sqlStr.append("    SELECT REPLACE(TMP.PART_CODE,'-','') PART_CODE\n" );
			sqlStr.append("    FROM  TT_WR_MAINTAIN_PACKAGE TM, TT_WR_MAINTAIN_PART TMP\n" );
			sqlStr.append("    WHERE TM.PACKAGE_ID=TMP.PACKAGE_ID AND TM.MODEL_ID="+groupId+"\n" );
			sqlStr.append("    AND TM.PACKAGE_CODE='"+packageCode+"' AND TM.IS_DEL=0\n" );
			sqlStr.append("    )\n" );
			sqlStr.append("    UNION ALL\n" );
			sqlStr.append("    SELECT LEVEL_SEQ + 1, B.PART_ID, B.PART_SHOW_CODE,B.NEW_PART_CODE\n" );
			sqlStr.append("    FROM DD A, TT_PT_PART_BASE B\n" );
			sqlStr.append("    WHERE REPLACE(A.NEW_PART_CODE,'-','') = B.PART_CODE)\n" );
			sqlStr.append("SELECT PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("FROM DD");

			List<Map> packPartList = OemDAOUtil.findAll(sqlStr.toString(), null);
			Map<String,String> correctPartMap=new HashMap<String, String>();//保养套餐中的零部件（包含替换件）
			for(Map<String, Object> partMap:packPartList){
				correctPartMap.put(partMap.get("PART_SHOW_CODE").toString().trim(), partMap.get("PART_SHOW_CODE").toString().trim());
			}
			/**获得当前待验证的索赔单保养零部件信息*/
			String claimId=CommonUtils.checkNull(dto.getClaimId()==null?params.get("claimId"):dto.getClaimId());//获得索赔单ID
			if("".equals(claimId)){
				//插入索赔零部件表
				List<Map> partTotal=dto.getClaimPartsTable();//获得零部件种类数
				for(int count=1;count<=partTotal.size();count++){
					String claimPartCode=CommonUtils.checkNull(partTotal.get(count).get("PART_CODE"));
					
					if(!correctPartMap.containsKey(claimPartCode)){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，零部件【"+claimPartCode+"】并不是有效的保养套餐更换零部件，请修正工单信息！");
						retMap.put("ERROR_CODE", "ERROR_RULE_123");
						return retMap;
					}
				}
			}else{
				sqlStr.delete(0, sqlStr.length());
				sqlStr.append("SELECT PART_CODE FROM TT_WR_CLAIMPART_REL\n" );
				sqlStr.append("WHERE CLAIM_ID="+claimId);
				List<Map<String, Object>> claimPartList = pageQuery(sqlStr.toString(), null, getFunName());
				for(Map<String, Object> tempMap:claimPartList){
					String claimPartCode=CommonUtils.checkNull(tempMap.get("PART_CODE").toString());
					if(!correctPartMap.containsKey(claimPartCode)){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，零部件【"+claimPartCode+"】并不是有效的保养套餐更换零部件，请修正工单信息！");
						retMap.put("ERROR_CODE", "ERROR_RULE_124");
						return retMap;
					}
				}
			}
			
		}
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
	/**
	 * 根据车辆的车型组、里程数、保养天数获得保养套餐信息
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> queryPackageInfo(Map<String,Object> params){
		String vin=CommonUtils.checkNull(params.get("vin"));//获得车型组VIN
		String groupId=CommonUtils.checkNull(params.get("groupId"));//获得车型组ID
		String milleage=CommonUtils.checkNull(params.get("milleage"));//获得里程数
		String maintenceDays=CommonUtils.checkNull(params.get("maintenceDays"));//获得保养天数
		String packageCode=CommonUtils.checkNull(params.get("packageCode"));
		
		if("".equals(vin)&&"".equals(groupId)&&"".equals(milleage)&&"".equals(maintenceDays)){
			return null;
		}
		List<Object> sqlParams=new ArrayList<Object>();//参数
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT TT.PACKAGE_ID, TT.PACKAGE_CODE, TT.PACKAGE_NAME,COALESCE(TWR.REPAIR_ID,999) REPAIR_ID\n" );
		sqlStr.append("FROM(SELECT TWMP.PACKAGE_ID,TWMP.PACKAGE_CODE,TWMP.PACKAGE_NAME\n" );
		sqlStr.append("FROM (SELECT * FROM TT_WR_MAINTAIN_PACKAGE WHERE MODEL_ID=? AND IS_DEL=0\n" );
		if(!"".equals(packageCode)){
			sqlStr.append("AND PACKAGE_CODE='"+packageCode.trim()+"'\n" );
		}
		sqlStr.append(") TWMP WHERE 1=1\n" );
		sqlStr.append("AND (TWMP.MAINTAIN_STARTMILEAGE<=?\n" );
		sqlStr.append("AND TWMP.MAINTAIN_ENDMILEAGE>=?)\n" );
		sqlStr.append("OR (TWMP.MAINTAIN_STARTDAY<=?\n" );
		sqlStr.append("AND TWMP.MAINTAIN_ENDDAY>=?)\n" );
		sqlStr.append(") TT LEFT JOIN (SELECT * FROM TT_WR_REPAIR WHERE IS_DEL=0) TWR\n" );
		sqlStr.append("ON TWR.VIN=? AND TT.PACKAGE_CODE=TWR.PACKAGE_CODE");

		sqlParams.add(groupId);
		sqlParams.add(milleage);
		sqlParams.add(milleage);
		sqlParams.add(maintenceDays);
		sqlParams.add(maintenceDays);
		sqlParams.add(vin);

		List<Map> retList=OemDAOUtil.findAll(sqlStr.toString(), sqlParams);
        
        return retList;
	}


}
