package com.yonyou.dms.vehicle.service.claimApproveMgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrActivityVehicleCompleteDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.claimApproveMgr.RepariClaimCheckDao;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtWrClaimautoDetailDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtWrClaimcheckLogDcsPO;

/**
 * 保修索赔
 * @author ZhaoZ
 * @date 2017年4月25日
 */
@Service
public class RepariClaimCheckServiceImpl implements RepairClaimCheckService{

	@Autowired
	RepariClaimCheckDao  claimDao;
	
	/**
	 * 保修索赔申请审核查询
	 */
	@Override
	public PageInfoDto repairClaimQueryList(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId = loginUser.getUserId();
		TcUserPO userPO = TcUserPO.findById(userId);
		String authCode = "";
		if(userPO.getInteger("APPROVAL_LEVEL_CODE")!=null){
			authCode = userPO.getInteger("APPROVAL_LEVEL_CODE").toString();//授权级别代码
		}
		Long OemCompanyId = OemDAOUtil.getOemCompanyId(loginUser);
		return claimDao.repairClaimList(authCode,OemCompanyId,queryParams);
	}

	/**
	 * 查询companyId
	 * @param loginUser
	 * @return
	 */
	public Long getOemCompanyId(LoginInfoDto loginUser) {
		Long companyId=null;
		if(loginUser==null)
		{
			return null;	
		}
		companyId=loginUser.getCompanyId();
		String dutyType=loginUser.getDutyType();
		if(!"".equals(dutyType)&&String.valueOf(OemDictCodeConstants.DUTY_TYPE_DEALER).equals(dutyType))
		{
			if (null == loginUser.getOemCompanyId()) {
				throw new IllegalArgumentException("OemCompanyId 为空 " + loginUser.getUserName());
			}
			companyId=Long.valueOf(loginUser.getOemCompanyId());
		}
		return companyId;
	}

	/**
	 * 获得保修索赔申请审核信息
	 */
	@Override
	public Map<String, Object> claimInfo(Long claimId, Long oemCompanyId) throws ServiceBizException {
		return claimDao.claimInfoMap(claimId,oemCompanyId);
	}

	/**
	 * 查询索赔单下的其他项目列表
	 */
	@Override
	public PageInfoDto queryOtherItem(Long claimId) throws ServiceBizException {
		return claimDao.otherItems(claimId);
	}

	/**
	 * 获得索赔单的零部件信息列表
	 */
	@Override
	public PageInfoDto queryRepairPart(Long claimId) throws ServiceBizException {
		return claimDao.repairParts(claimId);
	}

	/**
	 * 查询索赔单与工时关系
	 */
	@Override
	public PageInfoDto queryClainLabour(Long claimId) throws ServiceBizException {
		return claimDao.clainLabours(claimId);
	}

	/**
	 * 保修索赔单申请审核
	 * @throws Exception 
	 */
	@Override
	public void approve(TtWrClaimDcsDTO dto, Long claimId) throws Exception {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId = loginUser.getUserId();//登陆用户ID
		//String credit=request.getParamValue("credit");   //信誉 信誉为1表示HMCI已经同意不向日方索赔
		String approveOper=dto.getApproveOper();   //审核操作：通过，驳回，拒绝：
		String checkCode=CommonUtils.checkNull(dto.getCheckCode());   //驳回（拒绝）代码：
		String codeDescHidden=CommonUtils.checkNull(dto.getCodeDescHidden());   //驳回（拒绝）描述：
		String checkDesc=CommonUtils.checkNull(dto.getCheckDesc());   //意见：

		if(this.isAuditing(claimId, userId)){//不可以再次审核，该索赔单已经审核过
			 throw new ServiceBizException("审批失败,请联系管理员该索赔单之前已经审核过，不能再次审核!");
		}else{
			//4、记录审核授权状态过程
			this.recordAuthProcess(approveOper,userId,claimId,checkDesc,checkCode,codeDescHidden);
			//5、根据索赔申请单中当前审核步骤，确定下一审核步骤
			String nextCode = this.getNextAuthCode(claimId);
			//6、更新索赔申请单状态、下一步需要审核的级别
			TtWrClaimPO conditionPO =TtWrClaimPO.findFirst("CLAIM_ID = ?", claimId);
			conditionPO.setLong("UPDATE_BY",userId);
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				conditionPO.setTimestamp("UPDATE_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			conditionPO.setString("AUTH_CODE",nextCode);
			if(OemDictCodeConstants.CLAIM_STATUS_05.toString().equals(approveOper)){//审核拒绝 则 不在进行其他审核
				conditionPO.setInteger("STATUS",Utility.getInt(approveOper));//索赔单审核后的状态；
				//跟新活动客户登记表IS_CLAIM=yes
				updateVehicleByVinForUnPass(claimId);
			}else if(OemDictCodeConstants.CLAIM_STATUS_04.toString().equals(approveOper)){//审核退回 则不在进行其他审核 工单进行更改
				conditionPO.setInteger("STATUS",Utility.getInt(approveOper));//索赔单审核后的状态；
				updateVehicleByVinForUnPass(claimId);
				/** 非PDI 索赔 驳回到索赔单，走工单反结算流程 **/
				//克莱斯勒不涉及工单反结算
				//updateClaimForRejected(claimId,userId);
			}else{//审核通过
				if(nextCode==null || "".equals(nextCode)){//不存在其他需要审核的级别
					conditionPO.setInteger("STATUS",Utility.getInt(approveOper));//索赔单审核后的状态；
					//claimPO.setNwsStatus(Constant.NWS_STATUS_01);//修改NWS审核状态为待审核 add by Zhaold 2012-07-23
					//终审通过后，
					try {
						Date date = sdf.parse(sdf.format(new Date(time)));
						java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
						conditionPO.setTimestamp("PASS_DATE",st);
					} catch (ParseException e) {
						e.printStackTrace();
					} 
					updateVehicleByVinForPass(claimId);
				}
			}
			
			conditionPO.saveIt();
		}
	
	}

	/**
	 * 取的下一审核需要的授权级别
	 * @param claimId
	 * @return
	 */
	private String getNextAuthCode(Long claimId) {
		String nextAuthCode = "";
		
		//索赔申请单信息
		TtWrClaimPO claimPO =TtWrClaimPO.findFirst("CLAIM_ID = ?", claimId);
		String authCode = claimPO.getString("AUTH_CODE");
		if(authCode==null)
			return nextAuthCode;
		//索赔审核需要授权信息
		TtWrClaimautoDetailDcsPO authPO = queryAuthReason(claimId);
		String levelCode = "";
		
		if(authPO!=null)
			levelCode = authPO.getString("LEVEL_CODE");
		
		//取得下一级需要审核的代码
		if(levelCode!=null && !"".equals(levelCode)){
			int index = levelCode.indexOf(authCode);
			
			if(index>-1)
				index = index + authCode.length();
			
			levelCode = levelCode.substring(index);
			
			if(levelCode!=null && !"".equals(levelCode)){
				while(levelCode.startsWith(",")){
					levelCode = levelCode.replaceFirst(",", "");
				}
				if(levelCode.indexOf(",")>-1){
					nextAuthCode = levelCode.split(",")[0];
				}else{
					nextAuthCode = levelCode;
				}
			}
		}
		return nextAuthCode;
	}

	/**
	 * 查询对应索赔申请单需要审核的原因
	 * @param claimId
	 * @return
	 */
	private TtWrClaimautoDetailDcsPO queryAuthReason(Long claimId) {
		String sql = "SELECT * FROM TT_WR_CLAIMAUTO_DETAIL_dcs WHERE CLAIM_ID = ? ";
		TtWrClaimautoDetailDcsPO result =
				TtWrClaimautoDetailDcsPO.findFirst("CLAIM_ID = ?", claimId);
		if (result != null) {
			return result;
		}
		return null;
	}

	/**
	 * 记录审核记录
	 * @param approveOper
	 * @param userId
	 * @param claimId
	 * @param checkDesc
	 * @param checkCode
	 * @param codeDescHidden
	 */
	private void recordAuthProcess(String approveOper, Long userId, Long claimId, String checkDesc, String checkCode,
			String codeDesc) {

		TcUserPO userPO = TcUserPO.findById(userId);
		String authCode = "";
		authCode = userPO.getInteger("APPROVAL_LEVEL_CODE").toString();//结算授权级别代码
		TtWrClaimcheckLogDcsPO appAuthPO = new TtWrClaimcheckLogDcsPO();
		appAuthPO.setLong("CLAIM_ID",claimId);
		appAuthPO.setLong("CHECK_USER",userId);//审核人员
//		appAuthPO.setUserName(userPO.getName());
		appAuthPO.setInteger("CHECK_TYPE",OemDictCodeConstants.CHECK_TYPE_02);//人工审核
		appAuthPO.setString("AUTH_LEVEL",authCode);//审核角色
		appAuthPO.setDate("CHECK_DATE",new Date());
		appAuthPO.setInteger("STATUS",Integer.parseInt(approveOper));//审核结果状态
		if(approveOper.equals(OemDictCodeConstants.CLAIM_STATUS_04.toString()) || approveOper.equals(OemDictCodeConstants.CLAIM_STATUS_05.toString())){
			appAuthPO.setString("CHECK_CODE",checkCode);//审核代码
			appAuthPO.setString("CODE_DESC",codeDesc);//代码描述
			appAuthPO.setString("CHECK_DESC",checkDesc);//审核意见
		}
		appAuthPO.setLong("CREATE_BY",userId);
		appAuthPO.setDate("CREATE_DATE",new Date());
		appAuthPO.insert();
	}

	/**
	 * 检测该用户是否可以审核该索赔单或该索赔单是否已经审核过
	 * @param claimId
	 * @param userId
	 * @return
	 */
	private boolean isAuditing(Long claimId, Long userId) {
		boolean result = false;
		TcUserPO userPO = TcUserPO.findById(userId);
		
		TtWrClaimPO claimPO =TtWrClaimPO.findFirst("CLAIM_ID = ?", claimId);
		
		if(userPO==null || claimPO==null){
			result = true;
		}else{
		
			String userCode = "";
			if(userPO.getInteger("APPROVAL_LEVEL_CODE")!=null){
				userCode = userPO.getInteger("APPROVAL_LEVEL_CODE").toString();//结算授权级别代码
			}
			String needCode = claimPO.getString("AUTH_CODE");//该索赔单需要审核的级别
			if(userCode==null || "".equals(userCode) ||!userCode.equals(needCode)){
				//该用户的审核级别同索赔单需要的审核级别不同时，不允许审核
				result = true;
			}
		}
		
		return result;
	}

	@SuppressWarnings("static-access")
	private void updateVehicleByVinForPass(Long claimId) {

		TtWrClaimPO claimPO =TtWrClaimPO.findFirst("CLAIM_ID = ?", claimId);
		List<TtWrClaimPO> list = claimPO.findAll();
		if (list != null && list.size() > 0) {
			claimPO = list.get(0);
		}
		TmVehiclePO updatePo = TmVehiclePO.findFirst("VIN = ? ", claimPO.getString("VIN"));
		
		updatePo.setDouble("MILEAGE",Double.valueOf(claimPO.getInteger("MILLEAGE").toString()));
		updatePo.saveIt();
		String claimType = claimPO.getString("CLAIM_TYPE");
		//如果是服务活动，则修改该车的服务活动登记表的索赔状态
		if(OemDictCodeConstants.CLAIM_TYPE_03.toString().equals(claimType) || OemDictCodeConstants.CLAIM_TYPE_04.equals(claimType)){
			TtWrActivityVehicleCompleteDcsPO updatePo1 =
					TtWrActivityVehicleCompleteDcsPO
					.findFirst("VIN = ? AND  ACTIVITY_CODE = ?", claimPO.getString("VIN"),claimPO.getString("ACTIVITY_CODE"));
			updatePo1.setInteger("IS_CLAIM",OemDictCodeConstants.IF_TYPE_YES);//拒绝或退回后，车辆索赔状态至为否
			updatePo1.saveIt();
		}		
		
	}

	private void updateVehicleByVinForUnPass(Long claimId) {
		//根据索赔单ID获取，更新所需数据
				TtWrClaimPO claimPO =TtWrClaimPO.findFirst("CLAIM_ID = ?", claimId);
				List<TtWrClaimPO> list = TtWrClaimPO.findAll();
				if (list != null && list.size() > 0) {
					claimPO = list.get(0);
				}
				String claimType = claimPO.getString("CLAIM_TYPE");
				//如果是服务活动，则修改该车的服务活动登记表的索赔状态
				if(OemDictCodeConstants.CLAIM_TYPE_03.toString().equals(claimType)){
					TtWrActivityVehicleCompleteDcsPO updatePo1 =
							TtWrActivityVehicleCompleteDcsPO
							.findFirst("VIN = ? AND  ACTIVITY_CODE = ?", claimPO.getString("VIN"),claimPO.getString("ACTIVITY_CODE"));
					updatePo1.setInteger("IS_CLAIM",OemDictCodeConstants.IF_TYPE_NO);//拒绝或退回后，车辆索赔状态至为否
					updatePo1.saveIt();
			}
	}

	/**
	 * 查询索赔单审批历史信息
	 */
	@Override
	public PageInfoDto claimHistoryQuery(Long claim) throws ServiceBizException {
		return claimDao.historyList(claim);
	}

	/**
	 * 查询附件
	 */
	@Override
	public PageInfoDto doSearchFujian(Long claim) throws ServiceBizException {
		return claimDao.fujianList(claim);
	}

	/**
	 * 索赔单状态跟踪--查询操作
	 */
	@Override
	public PageInfoDto orderInfoList(Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.getOrderInfoList(queryParams);
	}

	/**
	 * 索赔单状态跟踪--下载查询操作
	 */
	@Override
	public List<Map> queryClaimOrderList(Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.getClaimOrderInfoList(queryParams);
	}

	/**
	 * 查询索赔单的索赔情形
	 */
	@Override
	public PageInfoDto queryClainCase(Long claimId) throws ServiceBizException {
		return claimDao.clainCaseList(claimId);
	}

	/**
	 * 进入保修申请审核页面初始化
	 */
	@Override
	public Map claimInfo(Long oemCompanyId, String claimNo, String dealerCode) throws ServiceBizException {
		return claimDao.claimMap(oemCompanyId,claimNo,dealerCode);
	}

	/**
	 * 
	 */
	@Override
	public PageInfoDto repairClaimList(Long oemCompanyId, Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.claimList(oemCompanyId,queryParams);
	}

	/**
	 *  进入保修申请审核
	 */
	@Override
	public Map claimInfos(Long oemCompanyId, Map<String, String> queryParams) throws ServiceBizException {
		return claimDao.claimMap(oemCompanyId,queryParams);
	}
	
	

}