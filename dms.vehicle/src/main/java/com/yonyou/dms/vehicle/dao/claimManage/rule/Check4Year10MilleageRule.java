package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrWarrantyPO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

public class Check4Year10MilleageRule extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(Check4Year10MilleageRule.class);

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
			String claimType=!"".equals(CommonUtils.checkNull(dto.getClaimType()))?
					CommonUtils.checkNull(dto.getClaimType())
					:CommonUtils.checkNull(params.get("claimType"));//获取索赔类型
			//零件索赔不验证索赔质保期
			if(OemDictCodeConstants.CLAIM_TYPE_G_M.equals(claimType) || OemDictCodeConstants.CLAIM_TYPE_G_S.equals(claimType)){
				return retMap;
			}
			Double qualityMileage =0.0;
			int qualityTime = 0;
			String vin="".equals(CommonUtils.checkNull(dto.getVin()))?params.get("VIN").toString():CommonUtils.checkNull(dto.getVin());//里程数
			TtWrSpecialVehwarrantyPO twsvPO = new TtWrSpecialVehwarrantyPO();
			List<TtWrSpecialVehwarrantyPO> twsvList = TtWrSpecialVehwarrantyPO.find(" IS_SEL = 0 AND VIN = ? ", vin);
			if(null!=twsvList && twsvList.size()>0){
				TtWrSpecialVehwarrantyPO po = twsvList.get(0);
				qualityMileage = Double.parseDouble(po.get("QUALITY_MILEAGE").toString());
				qualityTime = Integer.parseInt(po.get("QUALITY_TIME").toString()) ;
			}else{
				TmVhclMaterialGroupPO tvmg = new TmVhclMaterialGroupPO();
				String groupCode = CommonUtils.checkNull(dto.getModel());
				
				List<TmVhclMaterialGroupPO> list = TmVhclMaterialGroupPO.find(" GROUP_CODE=? AND GROUP_LEVEL=3 ", groupCode);
				TmVhclMaterialGroupPO tvmgPO = null;
				if(null!=list && list.size()>0){
					tvmgPO = list.get(0);
				}else{
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE","索赔失败，车型信息不存在，请重新创建索赔！");
					retMap.put("ERROR_CODE","ERROR_RULE_21");
					logger.debug("-------------------检查步骤：检查索赔单车辆保修期结束-----------------------");
					return retMap;
				}
				TtWrWarrantyPO tww = new TtWrWarrantyPO();
				List<TtWrWarrantyPO> twwList = TtWrWarrantyPO.find(" CROUP_ID=? ", tvmgPO.get("GROUP_ID"));
				TtWrWarrantyPO twwPO = null;
				if(null!=twwList && twwList.size()>0){
					twwPO = twwList.get(0);
				}else{
					retMap.put("RET_FLAG", "SUCCESS");
					retMap.put("MESSAGE", "");
					retMap.put("ERROR_CODE", "");
					return retMap;
				}
				qualityMileage = Double.parseDouble(twwPO.get("QUALITY_MILEAGE").toString());
				qualityTime = Integer.parseInt(twwPO.get("QUALITY_TIME").toString()) ;
			}
			
			
			String vehicleMilleage="".equals(CommonUtils.checkNull(dto.getMilleage()))?params.get("MILLEAGE").toString():CommonUtils.checkNull(dto.getMilleage());//里程数
			String purchaseDate=CommonUtils.checkNull(dto.getPurchaseDate());
			
			if("".equals(purchaseDate))return retMap;
			
			/**
			 * 检查步骤：检查车辆保修期不能超过4年
			 */
			if(!checkDate(purchaseDate,+qualityTime)){
				retMap.put("RET_FLAG", "ERROR");
				retMap.put("MESSAGE","索赔失败，车辆保修期不能超过"+qualityTime+"月！");
				retMap.put("ERROR_CODE","ERROR_RULE_21");
				logger.debug("-------------------检查步骤：检查索赔单车辆保修期结束-----------------------");
				return retMap;
			}
			
			/**
			 * 检查步骤：检查车辆里程数不能超过
			 */
				if(null!=qualityMileage && 0.0!=qualityMileage && qualityMileage<Double.parseDouble(vehicleMilleage)){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE","索赔失败，车辆里程数不能超过"+qualityMileage+"公里！");
					retMap.put("ERROR_CODE","ERROR_RULE_23");
				}
		}
		logger.debug("-------------------检查步骤：检查索赔单车辆里程结束-----------------------");
		return retMap;
	}
	
	/**
	 * 保修时间与当前时间比较  true 报修内  false 报修外
	 * @param purchaseDate 购车日期
	 * @param month        保修月份
	 * @return
	 */
	private boolean checkDate(String purchaseDate,int month){
		int result = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("", Locale.CHINESE);
	        sdf.applyPattern("yyyy-MM-dd");
	        Date date = sdf.parse(purchaseDate);
	        Calendar cal= Calendar.getInstance();
	        Calendar c2=Calendar.getInstance();  
	        cal.setTime(date);
	        c2.setTime(sdf.parse(sdf.format(new Date())));
	        cal.add(Calendar.MONTH, month);
	        result=cal.compareTo(c2); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result>=0){
        	return true;
        }else{
        	return false;
        }
	}
}
