package com.yonyou.dms.vehicle.service.claimManage.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.dao.claimManage.rule.Check4Year10MilleageRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckActivityPartInfoRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckActivityType;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckClaimNwsStatusRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckClaimTypeRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckClaimerValid;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckContinuedSingle;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckDealerMaintainClaimParam;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckDealerWarrantyClaimParam;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckOrderStatusRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckPackageInfoRule;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckPartsClaims;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckPdiClaimParam;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckVehicleIsActivity;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckVehicleIsMaintain;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckVehicleMaintenceIsApprove;
import com.yonyou.dms.vehicle.dao.claimManage.rule.CheckVehicleMilleageRule;

public class ClaimOrderSubmitPreCheck implements Callable<Map<String,Object>>{
	
	//private Logger logger = Logger.getLogger(ClaimOrderSubmitPreCheck.class);
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ClaimOrderSubmitPreCheck.class);
			
	
	private Map<String,Object> params=new HashMap<String, Object>();
	private String[] ruleArr=null;
	private Map<String,Object> message=new HashMap<String, Object>();
	
	public Map<String,Object> ClaimOrderSubmitPreCheck(Map<String, Object> params,String[] ruleArr) {
		this.params = params;
		this.ruleArr = ruleArr;
		check();
		return message;
	}
	public void check(){
//		RequestWrapper request=params==null?null:(RequestWrapper)params.get("request");
//		ActionContext act = ActionContext.getContext();
//		AclUserBean logonUser= (AclUserBean)act.getSession().get(Constant.LOGON_USER);
		
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		logger.debug("*************************索赔单提交检查开始");
		
		message.put("RET_FLAG", "SUCCESS");
		message.put("MESSAGE", "");
		message.put("ERROR_CODE", "");
		if(loginInfo==null){
			message.put("RET_FLAG", "ERROR");
			message.put("MESSAGE", "后台获取信息失败！");
			message.put("ERROR_CODE", "INFO_FAIL_01");
		}else{
			if(ruleArr!=null){
				for(int count=0;count<ruleArr.length;count++){
					if("ERROR".equals(message.get("RET_FLAG"))){
						break;
					}else{
						if("CheckOrderStatusRule".equals(ruleArr[count])){
							//检查索赔单提报状态   CLAIM_ID
							CheckRuleInfo check=new CheckOrderStatusRule();
							message=check.executeCheckStep(params, message);
						}else if("CheckVehicleMilleageRule".equals(ruleArr[count])){
							//检查规则：检查特约店索赔参数   VIN MILLEAGE
							CheckRuleInfo check=new CheckVehicleMilleageRule();
							message=check.executeCheckStep(params, message);
						}else if("CheckDealerMaintainClaimParam".equals(ruleArr[count])){
							//检查规则：检查登陆特约店系统参数（保养专用）
							CheckRuleInfo check=new CheckDealerMaintainClaimParam();
							message=check.executeCheckStep(params, message);
						}else if("CheckClaimTypeRule".equals(ruleArr[count])){
							//检查规则：检查索赔单中的索赔类型
							CheckRuleInfo check=new CheckClaimTypeRule();
							message=check.executeCheckStep(params, message);
						}else if("CheckDealerWarrantyClaimParam".equals(ruleArr[count])){
							//检查规则：检查登陆特约店系统参数（保修专用）
							CheckRuleInfo check=new CheckDealerWarrantyClaimParam();
							message=check.executeCheckStep(params, message);
						}else if("CheckPdiClaimParam".equals(ruleArr[count])){
							//检查规则：检查登陆特约店系统参数（PDI专用）
							CheckRuleInfo check=new CheckPdiClaimParam();
							message=check.executeCheckStep(params, message);
						}else if("CheckVehicleIsActivity".equals(ruleArr[count])){
							//检查规则：检查车辆参加服务活动参数
							CheckRuleInfo check=new CheckVehicleIsActivity();
							message=check.executeCheckStep(params, message);
						}else if("CheckClaimNwsStatusRule".equals(ruleArr[count])){
							//检查规则：检查索赔单NWS状态
							CheckRuleInfo check=new CheckClaimNwsStatusRule();
							message=check.executeCheckStep(params, message);
						}else if("CheckVehicleIsMaintain".equals(ruleArr[count])){
							//检查规则：检查车辆保养索赔情况
							CheckRuleInfo check=new CheckVehicleIsMaintain();
							message=check.executeCheckStep(params, message);
						}else if("CheckVehicleMaintenceIsApprove".equals(ruleArr[count])){
							//检查规则：检查车辆是否需要HMCI特批情况
							CheckRuleInfo check=new CheckVehicleMaintenceIsApprove();
							message=check.executeCheckStep(params, message);
						}else if("CheckPackageInfoRule".equals(ruleArr[count])){
							//检查规则：检查索赔保养套餐差异零部件
							CheckRuleInfo check=new CheckPackageInfoRule();
							message=check.executeCheckStep(params, message);
						}else if("CheckActivityPartInfoRule".equals(ruleArr[count])){
							//检查规则：检查索赔服务活动差异零部件
							CheckRuleInfo check=new CheckActivityPartInfoRule();
							message=check.executeCheckStep(params, message);
						}else if("Check4Year10MilleageRule".equals(ruleArr[count])){
							//检查规则：检查索赔年限与里程
							CheckRuleInfo check=new Check4Year10MilleageRule();
							message=check.executeCheckStep(params, message);
//						}else if("CheckIfPassForeApprRule".equals(ruleArr[count])){
//							//检查规则：校验索赔单是否包含监控项目，且已做过预授权申请操作
//							//add by dengweili 20130626
//							CheckRuleInf check=new CheckIfPassForeApprRule();
//							message=check.executeCheckStep(params, message);
						}else if("CheckClaimerValid".equals(ruleArr[count])){
							//检查规则：校验是否超过索赔有效期
							CheckRuleInfo check=new CheckClaimerValid();
							message=check.executeCheckStep(params, message);
						}else if("CheckPartsClaims".equals(ruleArr[count])){
							//检查规则：零件索赔是否超过索赔期
							CheckRuleInfo check=new CheckPartsClaims();
							message=check.executeCheckStep(params, message);
						}else if("CheckContinuedSingle".equals(ruleArr[count])){
							//检查规则：续单校验
							CheckRuleInfo check=new CheckContinuedSingle();
							message=check.executeCheckStep(params, message);
						}else if("CheckActivityType".equals(ruleArr[count])){
							//检查规则：服务活动验证
							CheckRuleInfo check=new CheckActivityType();
							message=check.executeCheckStep(params, message);
						}
					}
				}
			}
		}
	}
	public Map<String, Object> call() throws Exception {
		return message;
	}
}
