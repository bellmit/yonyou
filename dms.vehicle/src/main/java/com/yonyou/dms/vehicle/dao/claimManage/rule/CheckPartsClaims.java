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
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrPartwarrantyPO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckPartsClaims extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckPartsClaims.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		System.out.println("进入零件索赔。。。。。");
		logger.debug("-------------------检查规则：零件索赔验证开始-----------------------");
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
			
			String lastroDate = CommonUtils.checkNull( dto.getLastroDate());//上次工单时间
			double lastroMilleage = Double.parseDouble(CommonUtils.checkNull(dto.getLastroMilleage() ,"0"));//上次行驶里程
			double milleage = Double.parseDouble(CommonUtils.checkNull(dto.getMilleage(),"0"));//进场里程
			String claimType = CommonUtils.checkNull( dto.getClaimType());
			if(OemDictCodeConstants.CLAIM_TYPE_G_M.equals(claimType)){
				List<Map> partTotal=dto.getClaimPartsTable();//获得零部件种类数
				for(int count=1;count<=partTotal.size();count++){
					String partCode = CommonUtils.checkNull(partTotal.get(count).get("PART_CODE"));
					TtWrPartwarrantyPO twp = new TtWrPartwarrantyPO();
					
					List<TtWrPartwarrantyPO> list = TtWrPartwarrantyPO.find("PART_CODE=? ", partCode);
					if(null!=list && list.size()>0){
						TtWrPartwarrantyPO twpPO = list.get(0);
						if(!checkDate(lastroDate, +Integer.parseInt(twpPO.get("QUALITY_TIME").toString()))){
							retMap.put("RET_FLAG", "ERROR");
							retMap.put("MESSAGE", "配件："+twpPO.get("QUALITY_TIME")+"  超过保修期");
							retMap.put("ERROR_CODE", ERROR_CODE);
							return retMap;
						}
						if((lastroMilleage+twpPO.getDouble("QUALITY_MILEAGE"))<milleage){
							retMap.put("RET_FLAG", "ERROR");
							retMap.put("MESSAGE", "配件索赔里程数超出质保期");
							retMap.put("ERROR_CODE", ERROR_CODE);
							return retMap;
						}
					}
				}
			}
		}
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
