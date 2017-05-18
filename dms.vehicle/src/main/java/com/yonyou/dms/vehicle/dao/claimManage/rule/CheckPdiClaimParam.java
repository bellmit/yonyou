package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;
@Repository
public class CheckPdiClaimParam extends OemBaseDAO implements CheckRuleInfo{
	
	private static final Logger logger = LoggerFactory.getLogger(CheckPdiClaimParam.class);
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）开始-----------------------");
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
			logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
			return retMap;
		}else{
			retMap.put("RET_FLAG", "SUCCESS");
			retMap.put("MESSAGE", "");
			retMap.put("ERROR_CODE", "");
			
			String dealerId=logonUser.getDealerId().toString();
			if("".equals(dealerId)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，无法获得登陆账户信息！");
				retMap.put("ERROR_CODE", "ERROR_RULE_51");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
			//检索特约店系统参数设置
			List<Object> sqlParams=new ArrayList<Object>();//参数
			StringBuffer sqlStr= new StringBuffer();
			sqlStr.append("SELECT TWBP.PARA_ID,TWBP.TRADE_TERM,COALESCE(TWBP.PART_COST,0) PART_COST,\n" );
			sqlStr.append("COALESCE(TWBP.PLCA,0) PLCA,COALESCE(TWBP.LAB_PAY,0) LAB_PAY,COALESCE(TWBP.PARTFEE_LIMIT,0) PARTFEE_LIMIT,\n" );
			sqlStr.append("COALESCE(TWBP.OTHERFEE_LIMIT,0) OTHERFEE_LIMIT,COALESCE(TWBP.CLAIMFEE_LIMT,0) CLAIMFEE_LIMT,\n" );
			sqlStr.append("COALESCE(TWBP.FRT_LIMTSUM,0) FRT_LIMTSUM,COALESCE(TWBP.CLAIMAPPROVE_DATE,0) CLAIMAPPROVE_DATE\n" );
			sqlStr.append("FROM TT_WR_BASIC_PARA TWBP\n" );
			sqlStr.append("WHERE TWBP.VALID_DATE<=VARCHAR_FORMAT(?,'YYYY-MM-DD')\n" );
			sqlStr.append("AND TWBP.IS_DEL=0 AND TWBP.DEALER_ID=? AND TWBP.OEM_COMPANY_ID=? \n");
			try {
				sqlParams.add(DateTimeUtil.parseDateToDate(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			sqlParams.add(logonUser.getDealerId());
			sqlParams.add(logonUser.getOemCompanyId());
			Map<String,Object> dealerParamMap=OemDAOUtil.findFirst(sqlStr.toString(), sqlParams);
			if(dealerParamMap==null){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，无法找到特约店的参数设定，请联系管理员！");
				retMap.put("ERROR_CODE", "ERROR_RULE_52");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
            //如果参数不为空，检查页面费用是否超出
			//double postCost=Double.parseDouble(dealerParamMap.get("PART_COST").toString());//零部件成本
			//double labPay=Double.parseDouble(dealerParamMap.get("LAB_PAY").toString());//工时费率
			double partFeeLimit=Double.parseDouble(dealerParamMap.get("PARTFEE_LIMIT").toString());//零部件限制金额
			double otherLimit=Double.parseDouble(dealerParamMap.get("OTHERFEE_LIMIT").toString());//其他费用限制金额
			double claimLimit=Double.parseDouble(dealerParamMap.get("CLAIMFEE_LIMT").toString());//索赔总费用限制金额
			double frtLimit=Double.parseDouble(dealerParamMap.get("FRT_LIMTSUM").toString());//FRT限制工时总计
			//double approveDaysLimit=Double.parseDouble(dealerParamMap.get("CLAIMAPPROVE_DATE").toString());//索赔批准天数
			
			//取出页面
			double partFee=!"".equals(CommonUtils.checkNull(params.get("partFee")))?
					Double.parseDouble(CommonUtils.checkNull(params.get("partFee"), "0"))
					:Double.parseDouble(params.get("partFee").toString());//零部件总费用
			double otherFee=!"".equals(CommonUtils.checkNull(dto.getOtherFee()))?
					Double.parseDouble(CommonUtils.checkNull(dto.getOtherFee(), "0"))
					:Double.parseDouble(params.get("totalFee").toString());//其他项目总费用
			double totalFee=!"".equals(CommonUtils.checkNull(dto.getTotalFee()))?
					Double.parseDouble(CommonUtils.checkNull(dto.getTotalFee(), "0"))
					:Double.parseDouble(params.get("totalFee").toString());//索赔总费用
			double frtAmount=!"".equals(CommonUtils.checkNull(dto.getLabourFee()))?
					Double.parseDouble(CommonUtils.checkNull(dto.getLabourFee(), "0"))
					:Double.parseDouble(params.get("labourFee").toString());//FRT总计
			
			if(partFee>partFeeLimit){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，索赔单的零部件总金额超过HMCI规定的零部件总金额！");
				retMap.put("ERROR_CODE", "ERROR_RULE_53");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
			if(otherFee>otherLimit){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，索赔单的其他费用金额超过HMCI规定的其他费用总金额！");
				retMap.put("ERROR_CODE", "ERROR_RULE_54");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
			if(frtAmount>frtLimit){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，索赔单的工时合计超过HMCI规定的工时合计数！");
				retMap.put("ERROR_CODE", "ERROR_RULE_55");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
			if(totalFee>claimLimit){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔失败，索赔单的合计金额超过HMCI规定的合计金额！");
				retMap.put("ERROR_CODE", "ERROR_RULE_56");
				logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
				return retMap;
			}
		}
		logger.debug("-------------------检查规则：检查特约店索赔参数（PDI）结束-----------------------");
		return retMap;
	}

}
