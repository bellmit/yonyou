package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

public class CheckActivityPartInfoRule extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckActivityPartInfoRule.class);
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------索赔单检查规则：检查服务活动零部件开始-----------------------");
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

			String activityCode=!"".equals(CommonUtils.checkNull(dto.getActivityCode()))?
					CommonUtils.checkNull(dto.getActivityCode())
					:CommonUtils.checkNull(params.get("activityCode"));
			/**
			 * 根据服务活动代码查询：支持查询替换件的父件和所有子替换件
			 */
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("WITH DD (LEVEL_SEQ, PART_ID, PART_SHOW_CODE,NEW_PART_CODE)\n" );
			sqlStr.append("AS (SELECT 0, PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("    FROM TT_PT_PART_BASE\n" );
			sqlStr.append("    WHERE NEW_PART_CODE IN(\n" );
			sqlStr.append("    SELECT REPLACE(TWAP.PART_CODE,'-','') FROM TT_WR_ACTIVITY TWA,TT_WR_ACTIVITY_PART TWAP\n" );
			sqlStr.append("    WHERE TWA.ACTIVITY_ID=TWAP.ACTIVITY_ID AND TWA.IS_DEL=0\n" );
			sqlStr.append("    AND TWAP.IS_DEL=0 AND TWA.ACTIVITY_CODE=?\n" );
			sqlStr.append("    )\n" );
			sqlStr.append("    UNION ALL\n" );
			sqlStr.append("    SELECT 0, PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("    FROM TT_PT_PART_BASE\n" );
			sqlStr.append("    WHERE PART_CODE IN(\n" );
			sqlStr.append("    SELECT REPLACE(TWAP.PART_CODE,'-','') FROM TT_WR_ACTIVITY TWA,TT_WR_ACTIVITY_PART TWAP\n" );
			sqlStr.append("    WHERE TWA.ACTIVITY_ID=TWAP.ACTIVITY_ID AND TWA.IS_DEL=0\n" );
			sqlStr.append("    AND TWAP.IS_DEL=0 AND TWA.ACTIVITY_CODE=?\n" );
			sqlStr.append("    )\n" );
			sqlStr.append("    UNION ALL\n" );
			sqlStr.append("    SELECT LEVEL_SEQ + 1, B.PART_ID, B.PART_SHOW_CODE,B.NEW_PART_CODE\n" );
			sqlStr.append("    FROM DD A, TT_PT_PART_BASE B\n" );
			sqlStr.append("    WHERE REPLACE(A.NEW_PART_CODE,'-','') = B.PART_CODE)\n" );
			sqlStr.append("SELECT PART_ID,PART_SHOW_CODE,NEW_PART_CODE\n" );
			sqlStr.append("FROM DD");

			List<Object> paraList=new ArrayList<Object>();
			paraList.add(activityCode);
			paraList.add(activityCode);
			
			List<Map<String, Object>> packPartList = pageQuery(sqlStr.toString(), paraList, getFunName());
			Map<String,String> correctPartMap=new HashMap<String, String>();//服务活动中的零部件（包含替换件）
			for(Map<String, Object> partMap:packPartList){
				correctPartMap.put(partMap.get("PART_SHOW_CODE").toString().trim(), partMap.get("PART_SHOW_CODE").toString().trim());
			}
			/**获得当前待验证的索赔单保养零部件信息*/
			String claimId=CommonUtils.checkNull(dto.getClaimId()==null?params.get("claimId"):dto.getClaimId());//获得索赔单ID
			if("".equals(claimId)){
				//从页面获得服务获得零部件信息
				List<Map> partTotal=dto.getClaimPartsTable();//获得零部件种类数
				for(int count=1;count<=partTotal.size();count++){
					String claimPartCode=CommonUtils.checkNull(partTotal.get(count).get("PART_CODE"));
					if(!correctPartMap.containsKey(claimPartCode)){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，零部件【"+claimPartCode+"】并不是有效的服务活动更换零部件，请修正工单信息！");
						retMap.put("ERROR_CODE", "ERROR_RULE_131");
						return retMap;
					}
				}
			}else{
				//从索赔零部件明细表中获得零部件信息
				sqlStr.delete(0, sqlStr.length());
				paraList.clear();
				sqlStr.append("SELECT PART_CODE FROM TT_WR_CLAIMPART_REL\n" );
				sqlStr.append("WHERE CLAIM_ID=?");
				paraList.add(claimId);
				List<Map<String, Object>> claimPartList = pageQuery(sqlStr.toString(), paraList, getFunName());
				for(Map<String, Object> tempMap:claimPartList){
					String claimPartCode=CommonUtils.checkNull(tempMap.get("PART_CODE").toString());
					if(!correctPartMap.containsKey(claimPartCode)){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔失败，零部件【"+claimPartCode+"】并不是有效的服务活动更换零部件，请修正工单信息！");
						retMap.put("ERROR_CODE", "ERROR_RULE_131");
						return retMap;
					}
				}
			}
			
		}
		return retMap;
	}

}
