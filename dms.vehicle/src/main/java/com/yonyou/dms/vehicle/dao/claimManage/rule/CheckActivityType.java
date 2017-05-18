package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckActivityType extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckActivityType.class);

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
			String actCode = "";
			List<Map> partTotal= dto.getClaimPartsTable();//获得零部件种类数
			for(int count=1;count<=partTotal.size();count++){
				actCode+=","+CommonUtils.checkNull(partTotal.get(count).get("ACTIVITY_CODE"));
			}
			
			List<Map> labourTotal= dto.getClaimLabourTable();//获得索赔维修种类数
			for (int count = 1; count <= labourTotal.size(); count++) {
				actCode+=","+CommonUtils.checkNull(labourTotal.get(count).get("ACTIVITY_CODE"));
			}
			
			List<Map> itemTotal=dto.getClaimOtherTable();//获得其他项目种类数
			for(int count=1;count<=itemTotal.size();count++){
				actCode+=","+CommonUtils.checkNull(itemTotal.get(count).get("ACTIVITY_CODE"));
			}
			actCode = "".equals(actCode)?"":actCode.substring(1);
			actCode = actCode.replaceAll(",", "','");
			StringBuffer sql = new StringBuffer();
			sql.append("select IS_FEE from TT_WR_ACTIVITY \n");
			sql.append("WHERE ACTIVITY_CODE IN ('");
			sql.append(actCode).append("')");
			List<Map<String, Object>> retList=pageQuery(sql.toString(), null, getFunName());
			Map rs = new HashMap();
			if(null!=retList && retList.size()>0){
				for (int i = 0; i < retList.size(); i++) {
					Map map = retList.get(i);
					String isFee = CommonUtils.checkNull(map.get("IS_FEE"));
					if(null==rs.get(isFee)){
						rs.put(isFee, 1);
					}else{
						rs.put(isFee, Integer.parseInt(CommonUtils.checkNull(rs.get(isFee),"0"))+1);
					}
				}
			}
			System.out.println("=============================================:"+rs.toString());
			if(rs.size()>1){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "固定费用服务活动与非固定费用服务活动不能在同一张索赔单上");
				retMap.put("ERROR_CODE", ERROR_CODE);
			}
		}
		return retMap;
	}


}
