package com.yonyou.dms.vehicle.service.claimManage;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.FriendlyClaimApplyDao;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimCaseDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimlabourRelDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimotherRelDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimManage.TtWrClaimpartRelDcsPO;

/**
* @author liujm
* @date 2017年4月25日
*/
@SuppressWarnings("all")

@Service
public class FriendlyClaimApplyServiceImpl implements FriendlyClaimApplyService {
	
	@Autowired
	private FriendlyClaimApplyDao fcaDao;
	
	
	
	/**
	 * 查询（主页面）
	 */
	@Override
	public PageInfoDto queryFriendlyClaimApply(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getFriendlyClaimApplyQuery(queryParam);
		return pageInfoDto;
	}


	/**
	 * 工单明细 零部件清单
	 */
	@Override
	public PageInfoDto repairQueryPartMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryPartMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 维修工时清单
	 */
	@Override
	public PageInfoDto repairQueryItemMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryItemMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 其他项目清单
	 */
	@Override
	public PageInfoDto repairQueryOtherItemMsgDetail(String repairNo) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getRepairQueryOtherItemMsgDetail(repairNo);
		return pageInfoDto;
	}


	/**
	 * 工单明细 车辆信息
	 */
	@Override
	public Map repairQueryVehicleMsgDetail(String vin) throws ServiceBizException {
		Map map = fcaDao.getRepairQueryVehicleMsgDetail(vin);
		return map;
	}


	/**
	 * 工单明细 基本信息
	 */
	@Override
	public Map repairQueryMsgDetail(String repairNo) throws ServiceBizException {
		Map map = fcaDao.getRepairQueryMsgDetail(repairNo);
		return map;
	}

	/**
	 * 索赔明细  申请信息
	 */
	@Override
	public Map claimQueryDetail(Long claimId) throws ServiceBizException {
		Map map = fcaDao.getClaimQueryDetail(claimId);
		return map;
	}

	/**
	 * 索赔明细  情形
	 */
	@Override
	public PageInfoDto claimCaseQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimCaseQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细  工时
	 */
	@Override
	public PageInfoDto claimLabourQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimLabourQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 零部件
	 */
	@Override
	public PageInfoDto claimPartQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimPartQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 其他项目
	 */
	@Override
	public PageInfoDto claimOtherQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimOtherQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 索赔明细 审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}

	/**
	 * 查询工单列表
	 */
	@Override
	public PageInfoDto queryRepairOrderList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getQueryRepairOrderList(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询预授权单信息
	 */
	@Override
	public PageInfoDto queryForeApprovalInfo(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getQueryforeOrderList(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询工单下的维修工时列表
	 */
	@Override
	public PageInfoDto queryRepairOrderClaimLabour(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto =  fcaDao.getQueryRepairOrderClaimLabour(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询工单下的维修零部件列表
	 */
	@Override
	public PageInfoDto queryRepairOrderClaimPart(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = fcaDao.getQueryRepairOrderClaimPart(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询工单下的其他项目列表
	 */
	@Override
	public PageInfoDto queryRepairOrderClaimOther(Map<String, String> queryParam) throws ServiceBizException {
		
		PageInfoDto pageInfoDto = fcaDao.getQueryRepairOrderClaimOther(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询当前登录经销商 工时单价，税率
	 */
	@Override
	public Map queryDealerCodeAndName(Map<String, String> queryParam) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		Map map = fcaDao.getClaimBaseParamByDealer();
		return map;
	}

	/**
	 * 善意索赔申请  新增保存
	 */
	@Override
	public Long saveClaimOrderInfo(ClaimApplyDTO applyDto) throws ServiceBizException {
		
		//加入同步锁
		
		//本地索赔的索赔判断条件
		
		//保存操作
		TtWrClaimPO savePo = new TtWrClaimPO();
		
		//保存TT_WR_CLAIM
		saveAndUpdateClaimOrder(savePo, applyDto, 1); 
		savePo.saveIt();
	
		TtWrClaimPO findPo =  TtWrClaimPO.findFirst(" CLAIM_NO = ? AND IS_DEL = 0 ", applyDto.getClaimNo()) ;
		Long claimId = findPo.getLongId();
		//claimId = 2015120121727212L;
		//保存索赔单子项信息
		insertClaimItemInfo(claimId, applyDto,1);
		
		
		return claimId;
	}

	/**
	 * 善意索赔申请 提报 
	 */
	@Override
	public void submitClaimOrderInfo(ClaimApplyDTO applyDto,Long claimId) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);	
		TtWrClaimPO submitPo = TtWrClaimPO.findFirst(" CLAIM_ID=? ", claimId);
		submitPo.set("SUBMIT_COUNT", Integer.parseInt(submitPo.get("SUBMIT_COUNT").toString())+1);
		submitPo.set("STATUS", OemDictCodeConstants.CLAIM_STATUS_02);
		submitPo.set("APPLY_DATE", new Date(System.currentTimeMillis()));
		submitPo.set("UPDATE_BY", loginInfo.getUserId());
		submitPo.set("UPDATE_DATE", new Date(System.currentTimeMillis()));
		submitPo.set("SMALL_AREA_APPROVAL_STATUS", OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_01);
		submitPo.set("BIG_AREA_APPROVAL_STATUS", OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_01);
		submitPo.set("CLAIM_GROUP_APPROVAL_STATUS", OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01);
		submitPo.set("SMALL_AREA_APPROVAL_USER", null);
		submitPo.set("BIG_AREA_APPROVAL_USER", null);
		submitPo.set("CLAIM_GROUP_APPROVAL_USER", null);
		submitPo.set("SMALL_AREA_APPROVAL_DATE", null);
		submitPo.set("BIG_AREA_APPROVAL_DATE", null);
		submitPo.set("CLAIM_GROUP_APPROVAL_DATE", null);
		submitPo.saveIt();
		
		
		
		
	}
	/**
	 * 善意索赔申请  保存、修改 操作 set对象
	 * @param applyDto
	 * @param type
	 */
	private void saveAndUpdateClaimOrder(TtWrClaimPO twcPo, ClaimApplyDTO applyDto, Integer type){
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		twcPo.set("OEM_COMPANY_ID", loginInfo.getOemCompanyId());		//公司ID
		twcPo.set("CLAIM_NO", applyDto.getClaimNo());			//索赔单号
		twcPo.set("RO_NO", applyDto.getRepairNo());				//维修工单号
		twcPo.set("RO_STARTDATE", applyDto.getRoStratDate());		//工单开始时间
		twcPo.set("RO_ENDDATE", applyDto.getRoEndDate());			//工单结束时间
		twcPo.set("WARRANTY_DATE", applyDto.getPurchaseDate());		//购车时间
		
		twcPo.set("FOREAPPROVAL_NO", applyDto.getForeapprovalNo());  	//授权单号
		twcPo.set("CONTINUE_FLAG", applyDto.getClaimCintinueFlag());	//延续标识
//		twcPo.set("PRE_CLAIM_NO", null);	//先前索赔单号
//		twcPo.set("LASTRO_NO", null);		//上次维修单号
//		twcPo.set("LASTRO_DATE", null);		//上次维修日期
//		twcPo.set("LASTRO_MILLEAGE", null);	//上次行驶里程
//		twcPo.set("ARRIVAL_TIME", null);	//到港日期
//		twcPo.set("SHIP_NO", null);			//船号
		twcPo.set("DEALER_ID", loginInfo.getDealerId());		//经销商ID
		twcPo.set("DEALER_CODE", loginInfo.getDealerCode());		//经销商代码
		twcPo.set("CLAIM_CATEGORY", OemDictCodeConstants.CLAIM_TYPE_L.toString());	//索赔种类：全球索赔或本地索赔
		twcPo.set("CLAIM_TYPE", applyDto.getClaimType());		//索赔类型
		twcPo.set("APPLY_DATE", new Date(System.currentTimeMillis()));
		//维修方案信息
		twcPo.set("CUSTOMER_COMPLAIN", applyDto.getCustomerComplain());	//客户抱怨
		twcPo.set("CHECK_REPAIR_PROCEDURES", applyDto.getCheckRepairProcedures());//检测和维修步骤
		twcPo.set("DEAL_SCHEME", applyDto.getDealScheme());			//处理方案
		twcPo.set("APPLY_REASONS", applyDto.getApplyReasons());		//申请理由
		
		twcPo.set("STATUS", null);		//索赔状态
		twcPo.set("VIN", applyDto.getVin());
		twcPo.set("BRAND", applyDto.getBrand());		//品牌
		twcPo.set("SERIES", applyDto.getSeries());		//车系
		twcPo.set("MODEL", applyDto.getModel());		//车型
		twcPo.set("PLATE_NO", applyDto.getPlateNo());	//车牌号
		twcPo.set("ENGINE_NO", applyDto.getEngineNo());	//发动机号
		twcPo.set("SERVE_ADVISOR", applyDto.getServeAdvisor());	//服务顾问
		twcPo.set("MILLEAGE", applyDto.getMilleage());		//行驶里程
		//twcPo.set("PT_MAIN", null);			//主故障配件	
		if(type==1){
			twcPo.set("SUBMIT_COUNT", 0);			//提交次数
		}
		twcPo.set("STATUS", OemDictCodeConstants.CLAIM_STATUS_01);	//保存索赔单操作，设置状态为未上报
		
		
		//费用
		twcPo.set("LABOUR_FEE", applyDto.getLabourFee());		//工时总费用
		twcPo.set("PART_FEE", applyDto.getPartFee());		//配件总费用
		twcPo.set("OTHER_AMOUNT", applyDto.getOtherFee());	//其他项目总费用

		
		//各个分担费用
		twcPo.set("CUSTOMER_BEAR_FEE_LABOR", applyDto.getCustomerBearFeeLabour());	//客户分担费用
		twcPo.set("DEALER_BEAR_FEE_LABOR", applyDto.getDealerBearFeeLabour());	//经销商分担费用
		twcPo.set("OEM_BEAR_FEE_LABOR", applyDto.getOemBearFeeLabour());		//总部分担费用
		twcPo.set("CUSTOMER_BEAR_FEE_PART", applyDto.getCustomerBearFeePart());	//客户分担费用
		twcPo.set("DEALER_BEAR_FEE_PART", applyDto.getDealerBearFeePart());	//经销商分担费用
		twcPo.set("OEM_BEAR_FEE_PART", applyDto.getOemBearFeePart());		//总部分担费用
		twcPo.set("CUSTOMER_BEAR_FEE_OTHER", applyDto.getCustomerBearFeeOther());	//客户分担费用
		twcPo.set("DEALER_BEAR_FEE_OTHER", applyDto.getDealerBearFeeOther());	//经销商分担费用
		twcPo.set("OEM_BEAR_FEE_OTHER", applyDto.getOemBearFeeOther());		//总部分担费用
		twcPo.set("REMARK", applyDto.getClaimRemark());	//申请备注		
		
		if(type==1){
			twcPo.set("IS_DEL", OemDictCodeConstants.IS_DEL_00);	//逻辑删除标识
			twcPo.set("CREATE_BY", loginInfo.getUserId());		//创建用户
			twcPo.set("CREATE_DATE", new Date(System.currentTimeMillis()));	//创建日期
		}else{
			twcPo.set("UPDATE_BY", loginInfo.getUserId());		//创建用户
			twcPo.set("UPDATE_DATE", new Date(System.currentTimeMillis()));	//创建日期
		}
		
	}
	/**
	 * 保存索赔单申请子项信息
	 * @param claimId
	 * @param applyDto
	 */
	private void insertClaimItemInfo(Long claimId,ClaimApplyDTO applyDto,Integer type){
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		

		
		//插入索赔情形tt_wr_claim_case_dcs
		if(applyDto.getClaimCaseTable() != null && applyDto.getClaimCaseTable().size() >0){
			
			for(Map tableMap :applyDto.getClaimCaseTable()){
				TtWrClaimCaseDcsPO savePo = new TtWrClaimCaseDcsPO();
				savePo.set("CLAIM_ID", claimId);
				savePo.set("CASE_CODE", tableMap.get("CASE_CODE"));
				savePo.set("TECHPERSON", tableMap.get("TECHPERSON"));
				savePo.set("FAULT_CAUSE", tableMap.get("FAULT_CAUSE"));
				savePo.set("APPLY_REMARK", tableMap.get("APPLY_REMARK"));
				savePo.set("CREATE_BY", loginInfo.getUserId());
				savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
				savePo.saveIt();
				
			}

		}
		
		//插入索赔零部件表tt_wr_claimpart_rel_dcs
		if(applyDto.getClaimPartsTable() != null && applyDto.getClaimPartsTable().size() >0){
			for(Map tableMap :applyDto.getClaimPartsTable()){
				TtWrClaimpartRelDcsPO savePo = new TtWrClaimpartRelDcsPO();
				savePo.set("CLAIM_ID", claimId);
				savePo.set("ACTIVITY_CODE", tableMap.get("CASE_CODE"));
				savePo.set("CASE_ID", tableMap.get("CASE_ID"));
				savePo.set("IS_MAIN", tableMap.get("IS_MAIN"));
				savePo.set("PART_CODE", tableMap.get("PART_CODE"));
				savePo.set("PART_NAME", tableMap.get("PART_NAME"));
				savePo.set("PRICE", tableMap.get("PRICE"));
				savePo.set("QUANTITY", tableMap.get("QUANTITY"));
				savePo.set("AMOUNT", Double.parseDouble(tableMap.get("QUANTITY").toString())*Double.parseDouble(tableMap.get("PRICE").toString()));
				savePo.set("CREATE_BY", loginInfo.getUserId());
				savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
				savePo.saveIt();
			}
			
			
		}
		
		//插入索赔维修项目表tt_wr_claimlabour_rel_dcs
		if(applyDto.getClaimLabourTable() != null && applyDto.getClaimLabourTable().size() >0){
			for(Map tableMap :applyDto.getClaimLabourTable()){
				TtWrClaimlabourRelDcsPO savePo = new TtWrClaimlabourRelDcsPO();
				savePo.set("CLAIM_ID", claimId);
				savePo.set("ACTIVITY_CODE", tableMap.get("ACTIVITY_CODE"));
				savePo.set("CASE_ID", tableMap.get("CASE_ID"));
				savePo.set("IS_MAIN", OemDictCodeConstants.IF_TYPE_NO.toString());
				savePo.set("LABOUR_CODE", tableMap.get("LABOUR_CODE"));
				savePo.set("LABOUR_NAME", tableMap.get("LABOUR_NAME"));
				savePo.set("LABOUR_NUM", tableMap.get("LABOUR_NUM"));
				savePo.set("PRICE", tableMap.get("LABOUR_PRICE"));
				savePo.set("FEE", Double.parseDouble(tableMap.get("LABOUR_NUM").toString())*Double.parseDouble(tableMap.get("LABOUR_PRICE").toString()));
				savePo.set("FAULT_CODE", tableMap.get("FAULT_CODE"));
				savePo.set("DAMAGE_AREA", null);
				savePo.set("DAMAGE_TYPE", null);
				savePo.set("DAMAGE_LEVEL", null);
				savePo.set("CREATE_BY", loginInfo.getUserId());
				savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
				savePo.saveIt();
			}
			

		}
		//插入索赔其他项目表tt_wr_claimother_rel_dcs
		if(applyDto.getClaimOtherTable() != null && applyDto.getClaimOtherTable().size() >0){
			for(Map tableMap :applyDto.getClaimOtherTable()){
				TtWrClaimotherRelDcsPO savePo = new TtWrClaimotherRelDcsPO();
				savePo.set("CLAIM_ID", claimId);
				savePo.set("ACTIVITY_CODE", tableMap.get("FAULT_CODE"));
				savePo.set("CASE_ID", null);
				savePo.set("OTHER_FEE_CODE", tableMap.get("OTHER_FEE_CODE"));
				savePo.set("OTHER_FEE_NAME", tableMap.get("OTHER_FEE_NAME"));
				savePo.set("AMOUNT", tableMap.get("ITEM_FEE"));
				savePo.set("LOP_NUMBER", tableMap.get("LOP"));
				savePo.set("DOC_NO", null);
				savePo.set("REMARK", tableMap.get("REMARK"));
				savePo.set("CREATE_BY", loginInfo.getUserId());
				savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
				savePo.saveIt();
			}
			
		}
	}

	/**
	 * 修改页面信息回显
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@Override
	public Map queryEditMessage(Long claimId,Map<String, String> queryParam) throws ServiceBizException {
		Map map  = fcaDao.getQueryEditMessage(claimId, queryParam);
		return map;
	}

	/**
	 * 删除申请信息
	 */
	@Override
	public void deleteClaimInfo(Long claimId) throws ServiceBizException {
		//校验
		TtWrClaimPO submitPo = TtWrClaimPO.findFirst(" CLAIM_ID=? ", claimId);
		if(submitPo.get("CONTINUE_FLAG")!=null && submitPo.get("CONTINUE_FLAG").toString().equals(OemDictCodeConstants.CLAIM_CONTINUE_FLAG_N.toString())){
			List<TtWrClaimPO>  list =  TtWrClaimPO.find(" IS_DEL = 0 and PRE_CLAIM_NO = ? ", submitPo.get("CLAIM_NO").toString());
			if(list != null && list.size() != 0){
				throw new ServiceBizException("需要先删除子单才能删除父单!");
			}
		}
		//删除索赔申请单
		TtWrClaimPO deletePo = TtWrClaimPO.findFirst(" CLAIM_ID=? ", claimId);
		deletePo.set("IS_DEL", OemDictCodeConstants.IS_DEL_01);
		deletePo.saveIt();
	}

	/**
	 * 修改页面数据保存
	 */
	@Override
	public void updateClaimOrderInfo(Long claimId,ClaimApplyDTO applyDto) throws ServiceBizException {
		//修改数据
		TtWrClaimPO updatePo = TtWrClaimPO.findFirst(" CLAIM_ID=? ", claimId);
		saveAndUpdateClaimOrder(updatePo, applyDto, 2);
		updatePo.saveIt();
		
		//删除索赔工时和索赔零部件表信息、其他项目表
		TtWrClaimCaseDcsPO.delete(" CLAIM_ID=?  ", claimId);
		TtWrClaimpartRelDcsPO.delete(" CLAIM_ID=?  ", claimId);
		TtWrClaimlabourRelDcsPO.delete(" CLAIM_ID=?  ", claimId);
		TtWrClaimotherRelDcsPO.delete(" CLAIM_ID=?  ", claimId);			
		//插入索赔单子项信息
		insertClaimItemInfo(claimId, applyDto, 2);

		
	}

	/**
	 * 查询车辆详细信息
	 */
	@Override
	public Map queryShowVehicleInfo(String vin) throws ServiceBizException {
		Map map = fcaDao.queryShowVehicleInfoMap(vin);
		return map;
	}
	

}
