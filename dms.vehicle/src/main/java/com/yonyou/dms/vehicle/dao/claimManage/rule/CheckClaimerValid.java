package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.common.domains.PO.basedata.TtWrRepairPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrBasicParaPO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckClaimerValid extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckClaimerValid.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------索赔单检查规则：检查索赔有效期开始-----------------------");
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
			
			String repairNo = CommonUtils.checkNull(dto.getRepairNo());
			//获取当前用户
			LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			
			//查询索赔有效天数
			TtWrBasicParaPO twbp = new TtWrBasicParaPO();
			List<TtWrBasicParaPO> twbplist = TtWrBasicParaPO.find(" DEALER_ID =? ",Long.valueOf(logonUser.getDealerId()));
			TtWrBasicParaPO twbpPO = null;
			if(null!=twbplist && twbplist.size()>0){
				twbpPO = (TtWrBasicParaPO)twbplist.get(0);
			}
			if(null==twbpPO){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "索赔有效天数数据错误");
				retMap.put("ERROR_CODE", ERROR_CODE);
				return retMap;
			}
			int validDays = Integer.parseInt(twbpPO.get("VALID_DAYS").toString()) ;
			//查询工单结算日期
			TtWrRepairPO twr = new TtWrRepairPO();
			List<TtWrRepairPO> list = TtWrRepairPO.find("DEALER_ID =? AND REPAIR_NO=? ", Long.valueOf(logonUser.getDealerId()),repairNo);
			TtWrRepairPO twrPO = null;
			if(null!=list && list.size()>0){
				twrPO = (TtWrRepairPO)list.get(0);
			}
			if(null == twrPO){//数据不存在返回错误
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "数据错误");
				retMap.put("ERROR_CODE", ERROR_CODE);
				return retMap;
			}
			Date balanceDate = (Date) twrPO.get("BALANCE_DATE");//维修工单的结算日期
			Double day = 0.0;
			try {
				day = DateTimeUtil.dateBetween(DateTimeUtil.parseDateToDate(balanceDate),DateTimeUtil.parseDateToDate(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(day>validDays){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE", "超过索赔有效期");
				retMap.put("ERROR_CODE", ERROR_CODE);
				return retMap;
			}
			
		}
		
		return retMap;
	}


}
