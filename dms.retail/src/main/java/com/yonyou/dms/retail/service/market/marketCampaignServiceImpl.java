package com.yonyou.dms.retail.service.market;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignAdvertFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignGoalDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPlanAttachedDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPopFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPrFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPsalesFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignAdvertFeePO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignGoalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanAttachedPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPopFeePO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPrFeePO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPsalesFeePO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignSeriesPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.market.CampaignAttachedDTO;
import com.yonyou.dms.retail.domains.DTO.market.CampaignGoalDTO;
import com.yonyou.dms.retail.domains.DTO.market.MarketCampaignDTO;
import com.yonyou.dms.retail.domains.PO.market.CampConclAttachedPO;
import com.yonyou.dms.retail.domains.PO.market.CampaignConclusionPO;

@Service
public class marketCampaignServiceImpl implements marketCampaignService {

	@Autowired
	private CommonNoService commonNoService;

	@Autowired
	FileStoreService fileStoreService;

	/**
	 * 查询
	 */
	@Override
	public PageInfoDto queryCampaign(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		PageInfoDto pageInfoDto = new PageInfoDto();
		List<Object> queryList = new ArrayList<Object>();
		sql.append(
				" SELECT 12781002 AS IS_SELECTED,A.REF_WEB_LINK,A.PRINCIPAL_NAME,A.PRINCIPAL_PHONE,A.PRINCIPAL_EMAIL,A.DISTRIBUTER_NAME,A.DISTRIBUTER_PHONE,");
		sql.append(
				"A.DISTRIBUTER_EMAIL,A.CAMPAIGN_CODE,A.CAMPAIGN_NAME,A.SPOT,CASE WHEN (A.CAMPAIGN_BUDGET IS NULL) THEN 0 ELSE A.CAMPAIGN_BUDGET END CAMPAIGN_BUDGET,A.BEGIN_DATE,A.END_DATE,A.TARGET_CUSTOMER,A.CAMPAIGN_PERFORM_TYPE,");
		sql.append(
				"A.APPLY_DATE,A.APPLICANT,MEMO,A.CUR_AUDITOR,A.CUR_AUDITING_STATUS,A.COM_AUDITING_STATUS,A.SERIES_CODE,A.OWNED_BY,A.TARGET_TRAFFIC,A.TARGET_NEW_CUSTOMERS,A.TARGET_NEW_ORDERS,");
		sql.append("A.TARGET_NEW_VEHICLES,A.OEM_TAG,A.DEALER_CODE,tu.USER_NAME FROM TT_CAMPAIGN_PLAN A");
		sql.append(" left join tm_user tu on A.DEALER_CODE=tu.DEALER_CODE and A.APPLICANT=tu.USER_ID");
		sql.append(" WHERE A.DEALER_CODE = '");
		sql.append(FrameworkUtil.getLoginInfo().getDealerCode() + "'  " + "AND A.D_KEY = ");
		sql.append(CommonConstants.D_KEY);
		sql.append(" and 1=1 ");
		this.setWhere(sql, queryParam, queryList);
		pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 查询条件
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		String yearMonth = queryParam.get("yearMonth");
		if (!StringUtils.isNullOrEmpty(yearMonth)) {
			int index = yearMonth.indexOf("-");
			String year = yearMonth.substring(0, index);
			String month = yearMonth.substring(index + 1, yearMonth.length());
			String dd2 = "'" + year + "-" + month + "-" + "01'";
			sql.append(" and (( " + Utility.getYearStr("", "BEGIN_DATE") + " = " + year + " and ");
			sql.append(Utility.getMonthStr("", "BEGIN_DATE") + " = " + month + ") OR (( (");
			sql.append(Utility.getDateDiff("", "END_DATE", "", dd2) + ")>=0)" + " AND ( (");
			sql.append(Utility.getDateDiff("", "BEGIN_DATE", "", dd2) + ")<=0)))");
		} else if (StringUtils.isNullOrEmpty(yearMonth)) {
			sql.append(" and 1=1");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("campaignName"))) {
			sql.append(" AND A.CAMPAIGN_NAME like ? ");
			queryList.add("%" + queryParam.get("campaignName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("performType"))) {
			sql.append(" AND A.CAMPAIGN_PERFORM_TYPE like ? ");
			queryList.add("%" + queryParam.get("performType") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("curAuditingStatus"))) {
			sql.append(" AND A.CUR_AUDITING_STATUS like ? ");
			queryList.add("%" + queryParam.get("curAuditingStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startBegin"))) {
			sql.append(" AND A.BEGIN_DATE >= ? ");
			queryList.add(queryParam.get("startBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startTo"))) {
			sql.append(" AND A.BEGIN_DATE <= ? ");
			queryList.add(queryParam.get("startTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endBegin"))) {
			sql.append(" AND A.END_DATE >= ? ");
			queryList.add(queryParam.get("endBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endTo"))) {
			sql.append(" AND A.END_DATE <= ? ");
			queryList.add(queryParam.get("endTo"));
		}

	}

	/**
	 * 查询申请人
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryApplicant(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
				" SELECT CA.CAMPAIGN_CODE,CA.CAMPAIGN_NAME,CA.DEALER_CODE,CA.APPLICANT FROM TT_CAMPAIGN_PLAN CA WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}

	/**
	 * 活动目标查询
	 * 
	 * @author Benzc
	 * @date 2017年2月22日
	 */
	@Override
	public PageInfoDto QueryCampaignGoal(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_GOAL WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey  );
		sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1" );
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 广告费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月22日
	 */
	@Override
	public PageInfoDto QueryCampaignFee(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_ADVERT_FEE WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey  );
		sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 公关费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@Override
	public PageInfoDto QueryCampaignPrFee(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_PR_FEE WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey  );
				sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1" );
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 现场布置费用及其他费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@Override
	public PageInfoDto QueryCampaignPopFee(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_POP_FEE WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey );
		sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 促销费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@Override
	public PageInfoDto QueryCampaignPsalesFee(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_PSALES_FEE WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey );
				sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1" );
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 市场活动附件查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@Override
	public PageInfoDto QueryCampaignPlanattached(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMPAIGN_PLAN_ATTACHED WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey );
		sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 审核记录查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@Override
	public PageInfoDto QueryCampaignApprovedMome(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAM_PLAN_AUDITING WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey );
				sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 活动车系查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@SuppressWarnings("unused")
	@Override
	public PageInfoDto QueryCampaignSeries(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
//		sb.append("SELECT * FROM TT_CAMPAIGN_SERIES WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey
//				+ "' AND CAMPAIGN_CODE='" + id + "' AND 1=1");
		sb.append(" SELECT TS.ITEM_ID,TS.CAMPAIGN_CODE,TS.DEALER_CODE,TS.SERIES_CODE,TS.MODEL_CODE,S.SERIES_NAME,M.MODEL_NAME,TS.D_KEY FROM " );
		sb.append( " TT_CAMPAIGN_SERIES TS LEFT JOIN TM_SERIES S ON TS.DEALER_CODE = S.DEALER_CODE AND TS.SERIES_CODE = S.SERIES_CODE " );
		sb.append( " LEFT JOIN TM_MODEL M ON TS.DEALER_CODE=M.DEALER_CODE AND TS.MODEL_CODE=M.MODEL_CODE ");
		
		sb.append(" LEFT JOIN TT_CAMPAIGN_PLAN CP ON CP.CAMPAIGN_CODE = TS.CAMPAIGN_CODE AND CP.DEALER_CODE = TS.DEALER_CODE ");
		sb.append(" WHERE CP.CAMPAIGN_CODE = '"+id+"' ");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 活动执行总结新增
	 * 
	 * @author Benzc
	 * @date 2017年2月24日
	 */
	@Override
	public void addMarket(MarketCampaignDTO marketdto) throws ServiceBizException {
		CampaignConclusionPO conclusionPo = new CampaignConclusionPO();
		// 设置对象属性
		setCampaignConclusionPO(conclusionPo, marketdto);
		conclusionPo.saveIt();
	}

	/**
	 * 设置对象属性
	 * 
	 * @author Benzc
	 * @date 2017年2月24日
	 */
	private void setCampaignConclusionPO(CampaignConclusionPO conPO, MarketCampaignDTO marDTO) {
		/*
		 * String campaignCode =
		 * commonNoService.getSystemOrderNo(CommonConstants.SRV_HDBH);
		 * System.out.println(commonNoService.getSystemOrderNo(CommonConstants.
		 * SRV_HDBH));
		 */
		System.err.println(marDTO.getCampaignCode());
		conPO.setString("CAMPAIGN_CODE", marDTO.getCampaignCode());
		conPO.setDouble("APPLY_PRICE", marDTO.getApplyPrice());
		conPO.setString("APPLY_MEMO", marDTO.getApplyMemo());
		conPO.setString("CAM_EFFECT", marDTO.getCamEffect());
		conPO.setString("CAM_RIVAL", marDTO.getCamRival());
		conPO.setString("CAM_GAIN", marDTO.getCamGain());
		conPO.setString("CAM_LACK", marDTO.getCamLack());
		conPO.setInteger("ACTIVE_LEVEL_CODE", marDTO.getActiveLevelCode());
		conPO.setDate("EVALUATE_DATE", marDTO.getEvaluateDate());
		conPO.setString("EVALUATER", marDTO.getEvaluater());
		conPO.setDouble("OEM_CONFIRM_PRICE", marDTO.getOemConfirmPrice());
		conPO.setDate("METERIAL_RECEIVE_DATE", marDTO.getMeterialReceiveDate());
		conPO.setInteger("CUR_AUDITING_STATUS", marDTO.getCurAuditingStatus());
		conPO.setString("OEM_CONFIRM_MEMO", marDTO.getOemConfirmMemo());
	}

	/**
	 * 删除活动总结
	 */
	@Override
	public void deleteMarketCampaign(String id) throws ServiceBizException {
		CampaignConclusionPO campaignConclusion = CampaignConclusionPO.findByCompositeKeys(id,
				FrameworkUtil.getLoginInfo().getDealerCode());
		campaignConclusion.delete();
	}

	/**
	 * 活动目标编辑
	 */
	@Override
	public void updatePlanConclusion(String id, CampaignGoalDTO goaldto) throws ServiceBizException {
		TtCampaignGoalPO goalpo = TtCampaignGoalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
				id);
		setPlan(goalpo, goaldto);
		goalpo.saveIt();
	}

	/**
	 * 活动目标条件设置
	 * 
	 * @param goal
	 * @param goaldto
	 * @throws ServiceBizException
	 */
	private void setPlan(TtCampaignGoalPO goal, CampaignGoalDTO goaldto) throws ServiceBizException {
		goal.setString("GOAL_ITEM", goaldto.getGoalItem());
		goal.setInteger("ITEM_INDEX", goaldto.getItemIndex());
		goal.setString("MEMO", goaldto.getMemo());
	}

	/**
	 * 根据ID查找
	 */
	@Override
	public TtCampaignGoalPO findId(String id) throws ServiceBizException {
		TtCampaignGoalPO ggpo = TtCampaignGoalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), id);
		return ggpo;
	}

	/**
	 * 活动总结查询
	 * 
	 * @author Benzc
	 * @date 2017年2月28日
	 */
	@Override
	public PageInfoDto QueryConclusion(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.CAMPAIGN_CODE,A.DEALER_CODE,A.APPLY_PRICE,A.APPLY_MEMO,A.OEM_CONFIRM_PRICE,A.OEM_CONFIRM_MEMO,A.METERIAL_RECEIVE_DATE,");
		sb.append(" A.CAM_EFFECT,A.CAM_RIVAL,A.CAM_GAIN,A.CAM_LACK,A.CUR_AUDITING_STATUS,A.COM_AUDITING_STATUS,A.CUR_AUDITOR,");
		sb.append(" A.ACTIVE_LEVEL_CODE,A.EVALUATE_DATE,A.EVALUATER AS EV,U.USER_NAME AS EVALUATER FROM TT_CAMPAIGN_CONCLUSION A");
		sb.append(" LEFT JOIN TM_USER U ON U.USER_ID=A.EVALUATER AND U.DEALER_CODE=A.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE='" + dealerCode + "' AND A.D_KEY='" + dKey + "' AND A.CAMPAIGN_CODE='" + id);
		sb.append("' AND 1=1");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	@Override
	public CampaignConclusionPO findFromId(String id) throws ServiceBizException {
		CampaignConclusionPO conclusionPo = CampaignConclusionPO.findByCompositeKeys(id,
				FrameworkUtil.getLoginInfo().getDealerCode());
		return conclusionPo;
	}

	@Override
	public void updateCampaignConclusion(String id, MarketCampaignDTO conclusiondto) throws ServiceBizException {
		CampaignConclusionPO conclusionPo = CampaignConclusionPO.findByCompositeKeys(id,
				FrameworkUtil.getLoginInfo().getDealerCode());
		// 设置对象属性
		setCampaignConclusionPO(conclusionPo, conclusiondto);
		conclusionPo.saveIt();

	}

	/**
	 * 活动总结附件查询
	 */
	@Override
	public PageInfoDto QueryCampaignConclusionAttached(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		int dKey = CommonConstants.D_KEY;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_CAMP_CONCL_ATTACHED WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY='" + dKey );
				sb.append( "' AND CAMPAIGN_CODE='" + id + "' AND 1=1" );
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 活动总结附件新增
	 */
	@Override
	public void addAttached(CampaignAttachedDTO attacheddto) throws ServiceBizException {
		CampConclAttachedPO attachedPo = new CampConclAttachedPO();
		attachedPo.setString("CAMPAIGN_CODE", attacheddto.getCampaignCode());
		attachedPo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		attachedPo.setString("FILE_NAME", attacheddto.getFileName());
		attachedPo.setDate("DDCN_UPDATE_DATE", new Date(System.currentTimeMillis()));
		attachedPo.saveIt();
		// 插入附件信息
		fileStoreService.addFileUploadInfo(attacheddto.getFileName(), attachedPo.getLongId().toString(),
				DictCodeConstants.FILE_TYPE_USER_INFO);

	}

	/**
	 * 删除活动总结附件
	 */
	@Override
	public void deleteAttached(String id) throws ServiceBizException {
		CampConclAttachedPO findById = CampConclAttachedPO.findById(id);
		findById.delete();
	}

	/*************************************************************/

	/**
	 * 新增活动目标
	 */

	@Override
	public void insertPlanGold(TtCampaignGoalDTO ttCampaignGoalDTO) throws ServiceBizException {
		TtCampaignGoalPO ttCampaignGoalPO = new TtCampaignGoalPO();
		// String campaignCode =
		// CommonNoService.getSystemOrderNo(CommonConstants.SRV_HDBH);
		ttCampaignGoalPO.setString("CAMPAIGN_CODE", ttCampaignGoalDTO.getCampaignCode());
		ttCampaignGoalPO.setString("GOAL_ITEM", ttCampaignGoalDTO.getGoalItem());
		ttCampaignGoalPO.setInteger("ITEM_INDEX", ttCampaignGoalDTO.getItemIndex());
		ttCampaignGoalPO.setString("MEMO", ttCampaignGoalDTO.getMemo());
		ttCampaignGoalPO.saveIt();
	}

	/**
	 * 删除活动目标
	 */

	@Override
	public void deleteGoldById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignGoalPO ttCampaignGoalPO = TtCampaignGoalPO.findByCompositeKeys(dealerCode, id);
		ttCampaignGoalPO.delete();
	}

	/**
	 * 新增广告费用
	 */

	@Override
	public void insertFee(TtCampaignAdvertFeeDTO ttCampaignAdvertFeeDTO) throws ServiceBizException {
		TtCampaignAdvertFeePO ttCampaignAdvertFeePO = new TtCampaignAdvertFeePO();
		ttCampaignAdvertFeePO.setString("CAMPAIGN_CODE", ttCampaignAdvertFeeDTO.getCampaignCode());
		ttCampaignAdvertFeePO.setString("ITEMS", ttCampaignAdvertFeeDTO.getItems());
		ttCampaignAdvertFeePO.setDouble("ITEMS_BUDGET", ttCampaignAdvertFeeDTO.getItemsBudget());
		ttCampaignAdvertFeePO.setString("ITEMS_DESC", ttCampaignAdvertFeeDTO.getItemsDesc());
		ttCampaignAdvertFeePO.saveIt();
	}

	/**
	 * 删除广告费用
	 */
	@Override
	public void deleteFeeById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignAdvertFeePO ttCampaignAdvertFeePO = TtCampaignAdvertFeePO.findByCompositeKeys(dealerCode, id);
		ttCampaignAdvertFeePO.delete();
	}

	/**
	 * 新增公关费用
	 */

	@Override
	public void insertPrFee(TtCampaignPrFeeDTO ttCampaignPrFeeDTO) throws ServiceBizException {
		TtCampaignPrFeePO ttCampaignPrFeePO = new TtCampaignPrFeePO();
		ttCampaignPrFeePO.setString("CAMPAIGN_CODE", ttCampaignPrFeeDTO.getCampaignCode());
		ttCampaignPrFeePO.setString("ITEMS", ttCampaignPrFeeDTO.getItems());
		ttCampaignPrFeePO.setDouble("ITEMS_BUDGET", ttCampaignPrFeeDTO.getItemsBudget());
		ttCampaignPrFeePO.setString("ITEMS_DESC", ttCampaignPrFeeDTO.getItemsDesc());
		ttCampaignPrFeePO.saveIt();
	}

	/**
	 * 删除公关费用
	 */

	@Override
	public void deletePrFeeById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPrFeePO ttCampaignPrFeePO = TtCampaignPrFeePO.findByCompositeKeys(dealerCode, id);
		ttCampaignPrFeePO.delete();
	}

	/**
	 * 新增现场布置及其他费用
	 */

	@Override
	public void insertPpoFee(TtCampaignPopFeeDTO ttCampaignPopFeeDTO) throws ServiceBizException {
		TtCampaignPopFeePO ttCampaignPopFeePO = new TtCampaignPopFeePO();
		ttCampaignPopFeePO.setString("CAMPAIGN_CODE", ttCampaignPopFeeDTO.getCampaignCode());
		ttCampaignPopFeePO.setString("ITEMS", ttCampaignPopFeeDTO.getItems());
		ttCampaignPopFeePO.setDouble("ITEMS_BUDGET", ttCampaignPopFeeDTO.getItemsBudget());
		ttCampaignPopFeePO.setString("ITEMS_DESC", ttCampaignPopFeeDTO.getItemsDesc());
		ttCampaignPopFeePO.saveIt();
	}

	/**
	 * 删除现场布置及其他费用
	 */

	@Override
	public void deletePpoFeeById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPopFeePO ttCampaignPopFeePO = TtCampaignPopFeePO.findByCompositeKeys(dealerCode, id);
		ttCampaignPopFeePO.delete();
	}

	/**
	 * 新增促销费用
	 */

	@Override
	public void insertPaslesFee(TtCampaignPsalesFeeDTO ttCampaignPsalesFeeDTO) throws ServiceBizException {
		TtCampaignPsalesFeePO ttCampaignPsalesFeePO = new TtCampaignPsalesFeePO();
		ttCampaignPsalesFeePO.setString("CAMPAIGN_CODE", ttCampaignPsalesFeeDTO.getCampaignCode());
		ttCampaignPsalesFeePO.setString("ITEMS", ttCampaignPsalesFeeDTO.getItems());
		ttCampaignPsalesFeePO.setDouble("ITEMS_BUDGET", ttCampaignPsalesFeeDTO.getItemsBudget());
		ttCampaignPsalesFeePO.setString("ITEMS_DESC", ttCampaignPsalesFeeDTO.getItemsDesc());
		ttCampaignPsalesFeePO.saveIt();
	}

	/**
	 * 删除促销费用
	 */

	@Override
	public void deletePaslesFeeById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPsalesFeePO ttCampaignPsalesFeePO = TtCampaignPsalesFeePO.findByCompositeKeys(dealerCode, id);
		ttCampaignPsalesFeePO.delete();
	}

	/**
	 * 根据ID 查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException(non-Javadoc)
	 */

	@Override
	public TtCampaignPlanAttachedPO findByAttachedId(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPlanAttachedPO ttCampaignPlanAttachedPO = TtCampaignPlanAttachedPO.findByCompositeKeys(dealerCode,
				id);
		return ttCampaignPlanAttachedPO;
	}

	/**
	 * 新增市场活动附件
	 */
	@Override
	public void insertAttached(TtCampaignPlanAttachedDTO ttCampaignPlanAttachedDTO) throws ServiceBizException {
		TtCampaignPlanAttachedPO ttCampaignPlanAttachedPO = new TtCampaignPlanAttachedPO();
		ttCampaignPlanAttachedPO.setString("CAMPAIGN_CODE", ttCampaignPlanAttachedDTO.getCampaignCode());
		ttCampaignPlanAttachedPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		ttCampaignPlanAttachedPO.setString("FILE_NAME", ttCampaignPlanAttachedDTO.getFileName());
		ttCampaignPlanAttachedPO.setDate("DDCN_UPDATE_DATE", new Date(System.currentTimeMillis()));
		ttCampaignPlanAttachedPO.saveIt();
		// 插入附件信息
		fileStoreService.addFileUploadInfo(ttCampaignPlanAttachedDTO.getFileName(),
				ttCampaignPlanAttachedPO.getLongId().toString(), DictCodeConstants.FILE_TYPE_USER_INFO);

	}

	/**
	 * 删除市场活动附件
	 */
	@Override
	public void deleteAttachedFeeById(String id) throws ServiceBizException {
		// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPlanAttachedPO ttCampaignPlanAttachedPO = TtCampaignPlanAttachedPO.findById(id);
		ttCampaignPlanAttachedPO.delete();
	}

	/**
	 * 新增活动车型车系
	 * 
	 * @param ttCampaignPsalesFeeDTO
	 * @throws ServiceBizException
	 */
	@Override
	public void insertVehicle(String CampaignCode,String SeriesCodes,String ModelCode)
			throws ServiceBizException {
		TtCampaignSeriesPO ttCampaignSeriesPO = new TtCampaignSeriesPO();
		ttCampaignSeriesPO.setString("CAMPAIGN_CODE", CampaignCode);
		ttCampaignSeriesPO.setString("SERIES_CODE", SeriesCodes);
		ttCampaignSeriesPO.setString("MODEL_CODE", ModelCode);
		ttCampaignSeriesPO.saveIt();
	}

	/**
	 * 删除活动车型车系
	 */
	@Override
	public void deleteVehicleById(String id) throws ServiceBizException {
		TtCampaignSeriesPO ttCampaignSeriesPO = TtCampaignSeriesPO.findById(id);
		ttCampaignSeriesPO.delete();
	}

	/**
	 * 查询增加时的车系车型
	 */
	@Override
	public PageInfoDto QueryCampaignSeries2(Map<String, String> queryParam) {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT M.MODEL_CODE,M.MODEL_NAME,M.SERIES_CODE,S.SERIES_NAME,M.DEALER_CODE,S.DEALER_CODE D FROM TM_MODEL M ");
		sb.append(" LEFT JOIN TM_SERIES S ON M.DEALER_CODE=S.DEALER_CODE AND M.SERIES_CODE=S.SERIES_CODE ");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;

	}

	/**
	 * 根据ID 查找
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException(non-Javadoc)
	 */

	@Override
	public TtCampaignPlanPO findById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPlanPO wtpo = TtCampaignPlanPO.findByCompositeKeys(dealerCode, id);
		return wtpo;
	}

	/**
	 * 增加市场活动
	 * 
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public void insertPlan(TtcampaignPlanDTO ttcampaignPlanDTO) throws ServiceBizException, Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		@SuppressWarnings("deprecation")
		Date dateFormat1 = df.parse(ttcampaignPlanDTO.getBeginDate().toLocaleString());// 开始时间
		@SuppressWarnings("deprecation")
		Date dateFormat2 = df.parse(ttcampaignPlanDTO.getApplyDate().toLocaleString()); // 申请时间
		@SuppressWarnings("deprecation")
		Date dateFormat3 = df.parse(ttcampaignPlanDTO.getEndDate().toLocaleString());// 结束时间

		System.err.println(dateFormat1 + "%%%%%%%" + "  ");
		if (dateFormat1.getTime() < dateFormat2.getTime()) {
			throw new ServiceBizException("开始日期不能小于申请时间");
		}
		if (dateFormat3.getTime() < dateFormat2.getTime()) {
			throw new ServiceBizException("结束时间不能小于开始时间");
		}

		TtCampaignPlanPO ttCampaignPlanPO = new TtCampaignPlanPO();
		setttcampaignPo(ttCampaignPlanPO, ttcampaignPlanDTO, true);
		System.out.println("*********" + ttcampaignPlanDTO.getCampaignName());
		ttCampaignPlanPO.saveIt();

	}

	/**
	 * 修改活动市场
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updatePlan(String id, TtcampaignPlanDTO ttcampaignPlanDTO) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPlanPO ttCampaignPlanPO = TtCampaignPlanPO.findByCompositeKeys(dealerCode, id);
		StringBuffer sb = new StringBuffer(
				" SELECT DEALER_CODE,CAMPAIGN_CODE,CAMPAIGN_NAME,CAMPAIGN_PERFORM_TYPE,BEGIN_DATE,END_DATE,D_KEY FROM TT_CAMPAIGN_PLAN A WHERE 1=1 AND CAMPAIGN_NAME=? AND A.D_KEY = " + CommonConstants.D_KEY);
//						+ " AND A.D_KEY = " + CommonConstants.D_KEY);
		// System.err.println(sb+"%%%%%%%%%%%%%5");
		List<Object> list = new ArrayList<Object>();
		list.add(ttcampaignPlanDTO.getCampaignName());
		List<Map> map = DAOUtil.findAll(sb.toString(), list);
		String s = ttCampaignPlanPO.getString("CAMPAIGN_NAME");

		StringBuffer sb2 = new StringBuffer(
				" SELECT DEALER_CODE,CAMPAIGN_CODE,CAMPAIGN_NAME,CAMPAIGN_PERFORM_TYPE,BEGIN_DATE,END_DATE,D_KEY FROM TT_CAMPAIGN_PLAN A WHERE 1=1 AND CAMPAIGN_CODE=? AND A.D_KEY = " + CommonConstants.D_KEY);
//						+ " AND A.D_KEY = " + CommonConstants.D_KEY);
		List<Object> list2 = new ArrayList<Object>();
		list2.add(ttcampaignPlanDTO.getCampaignCode());
		List<Map> map2 = DAOUtil.findAll(sb2.toString(), list2);
		String s2 = ttCampaignPlanPO.getString("CAMPAIGN_CODE");

		if (map.size() <= 0 || map2.size() <= 0 || s.equals(ttcampaignPlanDTO.getCampaignName()) && map.size() == 1
				&& s2.equals(ttcampaignPlanDTO.getCampaignCode()) && map2.size() == 1) {
			setttcampaignPo(ttCampaignPlanPO, ttcampaignPlanDTO, false);
			ttCampaignPlanPO.saveIt();
		}
	}

	/**
	 * 删除市场活动
	 */
	@Override
	public void deletePlanById(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		TtCampaignPlanPO ttCampaignPlanPO = TtCampaignPlanPO.findByCompositeKeys(dealerCode, id);
		ttCampaignPlanPO.delete();
	}

	/**
	 * 设置属性
	 * 
	 * @param queryParam
	 */
	public void setttcampaignPo(TtCampaignPlanPO ttCampaignPlanPO, TtcampaignPlanDTO ttcampaignPlanDTO, Boolean flag) {
		if (flag) {
			String campaignCode = commonNoService.getSystemOrderNo(CommonConstants.SRV_HDBH);
			ttCampaignPlanPO.setString("CAMPAIGN_CODE", campaignCode);
		} else {
			ttCampaignPlanPO.setString("CAMPAIGN_CODE", ttcampaignPlanDTO.getCampaignCode());
		}
		ttCampaignPlanPO.setString("CAMPAIGN_NAME", ttcampaignPlanDTO.getCampaignName());
		ttCampaignPlanPO.setDate("APPLY_DATE", ttcampaignPlanDTO.getApplyDate());
		ttCampaignPlanPO.setString("APPLICANT", ttcampaignPlanDTO.getApplicant());
		ttCampaignPlanPO.setInteger("CAMPAIGN_PERFORM_TYPE", ttcampaignPlanDTO.getCampaignPerformType());
		ttCampaignPlanPO.setString("SPOT", ttcampaignPlanDTO.getSpot());
		ttCampaignPlanPO.setString("TARGET_CUSTOMER", ttcampaignPlanDTO.getTargetCustomer());
		ttCampaignPlanPO.setDouble("CAMPAIGN_BUDGET", ttcampaignPlanDTO.getCampaignBudget());
		ttCampaignPlanPO.setDate("BEGIN_DATE", ttcampaignPlanDTO.getBeginDate());
		ttCampaignPlanPO.setDate("END_DATE", ttcampaignPlanDTO.getEndDate());
		ttCampaignPlanPO.setString("CUR_AUDITOR", ttcampaignPlanDTO.getCurAuditor());
		ttCampaignPlanPO.setInteger("CUR_AUDITING_STATUS", ttcampaignPlanDTO.getCurAuditingStatus());
		ttCampaignPlanPO.setString("MEMO", ttcampaignPlanDTO.getMemo());
		ttCampaignPlanPO.setString("REF_WEB_LINK", ttcampaignPlanDTO.getRefWebLink());
		ttCampaignPlanPO.setString("PRINCIPAL_NAME", ttcampaignPlanDTO.getPrincipalName());
		ttCampaignPlanPO.setString("PRINCIPAL_PHONE", ttcampaignPlanDTO.getPrincipalPhone());
		ttCampaignPlanPO.setString("PRINCIPAL_EMAIL", ttcampaignPlanDTO.getPrincipalEmail());
		ttCampaignPlanPO.setString("DISTRIBUTER_NAME", ttcampaignPlanDTO.getDistributerName());
		ttCampaignPlanPO.setString("DISTRIBUTER_PHONE", ttcampaignPlanDTO.getDistributerPhone());
		ttCampaignPlanPO.setString("DISTRIBUTER_EMAIL", ttcampaignPlanDTO.getDistributerEmail());
		ttCampaignPlanPO.setString("TARGET_TRAFFIC", ttcampaignPlanDTO.getTargetTraffic());
		ttCampaignPlanPO.setString("TARGET_NEW_CUSTOMERS", ttcampaignPlanDTO.getTargetNewCustomers());
		ttCampaignPlanPO.setString("TARGET_NEW_ORDERS", ttcampaignPlanDTO.getTargetNewOrders());
		ttCampaignPlanPO.setString("TARGET_NEW_VEHICLES", ttcampaignPlanDTO.getTargetNewVehicles());

	}

}
