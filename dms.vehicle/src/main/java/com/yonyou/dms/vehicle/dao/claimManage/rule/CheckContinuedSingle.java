package com.yonyou.dms.vehicle.dao.claimManage.rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.rule.CheckRuleInfo;

@Repository
public class CheckContinuedSingle extends OemBaseDAO implements CheckRuleInfo{

	private static final Logger logger = LoggerFactory.getLogger(CheckContinuedSingle.class);

	public Map<String, Object> executeCheckStep(Map<String, Object> params,
			Map<String, Object> message) {
		logger.debug("-------------------检查规则：续单-----------------------");
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
			String claimNo = CommonUtils.checkNull(dto.getClaimNo());//获得索赔单号
			String repairNo = CommonUtils.checkNull(dto.getRepairNo() );//维修工单号
			int continueFlag = Integer.parseInt(CommonUtils.checkNull(dto.getClaimCintinueFlag(),"0"));//延续
			String preRepairNo = CommonUtils.checkNull(dto.getPreClaimNo() );//先前索赔单号 PRE_CLAIM_NO
			String claimType = CommonUtils.checkNull(dto.getClaimType());//索赔类型 CLAIM_TYPE
			String update = CommonUtils.checkNull(dto.getUpd() );//验证是否修改操作  1为修改 upd
			StringBuffer sql = new StringBuffer();
			sql.append("select clm.CLAIM_ID,clm.CLAIM_TYPE,clm.CLAIM_NO,clm.PRE_CLAIM_NO,clm.CONTINUE_FLAG from TT_WR_CLAIM clm \n");
			sql.append("where 0=0 and clm.is_del=0 and clm.RO_NO = '").append(repairNo).append("'");
			List<Map<String,Object>> list = pageQuery(sql.toString(), null, getFunName());
			if(null!=list && list.size()>0){
				Map map = null;
				//得到父类索赔单
				for (int i = 0; i < list.size(); i++) {
					map = list.get(i);
					if(Integer.parseInt(CommonUtils.checkNull(map.get("CONTINUE_FLAG"),"0"))==OemDictCodeConstants.CLAIM_CONTINUE_FLAG_N){
						break;
					}
				}
				//页面得到的索赔单号 与 父类索赔单号不相等 并  延续表示为N
				if(!claimNo.equals(CommonUtils.checkNull(map.get("CLAIM_NO"))) && continueFlag==OemDictCodeConstants.CLAIM_CONTINUE_FLAG_N){
					retMap.put("RET_FLAG", "ERROR");
					retMap.put("MESSAGE", "该工单已经提报了索赔单,请重新选择工单！");
					retMap.put("ERROR_CODE", ERROR_CODE);
					return retMap;
				}else{
					//不为修改操作  且目前库中维修工单号已存在3次
					if(!"1".equals(update) && list.size()>=3){
						retMap.put("RET_FLAG", "ERROR");
						retMap.put("MESSAGE", "索赔单延续不能超过2次");
						retMap.put("ERROR_CODE", ERROR_CODE);
						return retMap;
					}else{
						//页面得到的索赔单号 与 父类索赔单号不相等 并
						if(!claimNo.equals(CommonUtils.checkNull(map.get("CLAIM_NO"))) && !preRepairNo.equals(CommonUtils.checkNull(map.get("CLAIM_NO")))){
							retMap.put("RET_FLAG", "ERROR");
							retMap.put("MESSAGE", "先前索赔单号不匹配");
							retMap.put("ERROR_CODE", ERROR_CODE);
							return retMap;
						}
						//页面得到的索赔单号 与 父类索赔单号不相等 并 索赔类型与父类索赔类型不相等
						if(!claimNo.equals(CommonUtils.checkNull(map.get("CLAIM_NO"))) && !claimType.equals(CommonUtils.checkNull(map.get("CLAIM_TYPE")))){
							retMap.put("RET_FLAG", "ERROR");
							retMap.put("MESSAGE", "子单与父单的索赔类型需一致");
							retMap.put("ERROR_CODE", ERROR_CODE);
							return retMap;
						}
						for (int j = 0; j < list.size(); j++) {
							Map m = list.get(j);
							String jg = "子";
							if(Integer.parseInt(CommonUtils.checkNull(m.get("CONTINUE_FLAG"),"0"))==OemDictCodeConstants.CLAIM_CONTINUE_FLAG_N){
								jg = "父";
							}
							if(!claimNo.equals(CommonUtils.checkNull(m.get("CLAIM_NO")))){
								//得到索赔零部件表
								List<Map> partTotal=dto.getClaimPartsTable();//获得零部件种类数
								String partCode = "";
								for(int count=0;count<=partTotal.size();count++){
									partCode+=","+CommonUtils.checkNull( partTotal.get(count).get("PART_CODE"));
								}
								if(!"".equals(partCode)){
									//验证子类索赔零件信息是否在父类中存在
									partCode = partCode.substring(1);
									partCode = partCode.replaceAll(",", "','");
									StringBuffer partSql = new StringBuffer();
									partSql.append("select part_code from tt_wr_claimpart_rel where claim_id='").append(CommonUtils.checkNull(m.get("CLAIM_ID"))).append("' \n");
									partSql.append("and PART_CODE in ('").append(partCode).append("') \n");
									List<Map<String,Object>> partList = pageQuery(partSql.toString(), null, getFunName());
									if(null!=partList && partList.size()!=0){
										String code = "";
										for (int i = 0, size = partList.size(); i < size; i++) {
											Map partMap = partList.get(i);
											code+=","+partMap.get("PART_CODE");
										}
										retMap.put("RET_FLAG", "ERROR");
										retMap.put("MESSAGE", "索赔配件 "+code.substring(1)+"在"+jg+"单中已经存在");
										retMap.put("ERROR_CODE", ERROR_CODE);
										return retMap;
									}
								}
								
								//插入索赔维修项目表
								List<Map> labourTotal=dto.getClaimLabourTable();//获得索赔维修种类数
								String labourCode = "";
								for (int count = 0; count <= labourTotal.size(); count++) {
									labourCode+=","+CommonUtils.checkNull(labourTotal.get(count).get("LABOUR_CODE"));
								}
								if(!"".equals(labourCode)){
									labourCode = labourCode.substring(1);
									labourCode = labourCode.replaceAll(",", "','");
									StringBuffer labourSql = new StringBuffer();
									labourSql.append("select labour_code from Tt_Wr_Claimlabour_Rel where claim_id='").append(CommonUtils.checkNull(m.get("CLAIM_ID"))).append("' \n");
									labourSql.append("and labour_CODE in ('").append(labourCode).append("') \n");
									List<Map<String,Object>> labourList = pageQuery(labourSql.toString(), null, getFunName());
									if(null!=labourList && labourList.size()!=0){
										String code = "";
										for (int i = 0, size = labourList.size(); i < size; i++) {
											Map labourMap = labourList.get(i);
											code+=","+labourMap.get("LABOUR_CODE");
										}
										retMap.put("RET_FLAG", "ERROR");
										retMap.put("MESSAGE", "索赔维修项目 "+code.substring(1)+"在"+jg+"单中已经存在");
										retMap.put("ERROR_CODE", ERROR_CODE);
										return retMap;
									}
								}
								
								//插入索赔其他项目表
								List<Map> itemTotal=dto.getClaimOtherTable();//获得其他项目种类数
								String itemCode = "";
								for(int count=0;count<=itemTotal.size();count++){
									itemCode+=","+CommonUtils.checkNull(itemTotal.get(count).get("OTHER_CODE") );//其他项目代码
								}
								if(!"".equals(itemCode)){
									itemCode = itemCode.substring(1);
									itemCode = itemCode.replaceAll(",", "','");
									StringBuffer itemSql = new StringBuffer();
									itemSql.append("select other_fee_code from Tt_Wr_Claimother_Rel where claim_id='").append(CommonUtils.checkNull(m.get("CLAIM_ID"))).append("' \n");
									itemSql.append("and other_fee_code in ('").append(itemCode).append("') \n");
									List<Map<String,Object>> itemList = pageQuery(itemSql.toString(), null, getFunName());
									if(null!=itemList && itemList.size()!=0){
										String code = "";
										for (int i = 0, size = itemList.size(); i < size; i++) {
											Map itemMap = itemList.get(i);
											code+=","+itemMap.get("OTHER_FEE_CODE");
										}
										retMap.put("RET_FLAG", "ERROR");
										retMap.put("MESSAGE", "特殊服务 "+code.substring(1)+"在"+jg+"单中已经存在");
										retMap.put("ERROR_CODE", ERROR_CODE);
										return retMap;
									}
								}
							}
						}
					}
				}
			}
		}
		return retMap;
	}

}
