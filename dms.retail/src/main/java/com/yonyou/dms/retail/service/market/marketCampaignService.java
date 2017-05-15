package com.yonyou.dms.retail.service.market;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignAdvertFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignGoalDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPlanAttachedDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPopFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPrFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCampaignPsalesFeeDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignGoalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanAttachedPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.market.CampaignAttachedDTO;
import com.yonyou.dms.retail.domains.DTO.market.CampaignGoalDTO;
import com.yonyou.dms.retail.domains.DTO.market.MarketCampaignDTO;
import com.yonyou.dms.retail.domains.PO.market.CampaignConclusionPO;

public interface marketCampaignService {
	/**
	 * 分页查询
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	public PageInfoDto queryCampaign(Map<String, String> queryParam) throws ServiceBizException;

	/**
	 * 查询审批人
	 * 
	 * @param queryParam
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryApplicant(Map<String, String> queryParam) throws ServiceBizException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public TtCampaignPlanPO findById(String id) throws ServiceBizException;

	/**
	 * 活动目标查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignGoal(Map<String, String> queryParam, String id);

	/**
	 * 广告费用查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignFee(Map<String, String> queryParam, String id);

	/**
	 * 公关费用查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignPrFee(Map<String, String> queryParam, String id);

	/**
	 * 现场布置费用及其他费用查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignPopFee(Map<String, String> queryParam, String id);

	/**
	 * 促销费用查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignPsalesFee(Map<String, String> queryParam, String id);

	/**
	 * 市场活动附件查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignPlanattached(Map<String, String> queryParam, String id);

	/**
	 * 审核记录查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignApprovedMome(Map<String, String> queryParam, String id);

	/**
	 * 活动车系查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignSeries(Map<String, String> queryParam, String id);

	/**
	 * 活动执行总结编辑
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public void addMarket(MarketCampaignDTO marketdto) throws ServiceBizException;

	/**
	 * 删除活动总结
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteMarketCampaign(String id) throws ServiceBizException;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public TtCampaignGoalPO findId(String id) throws ServiceBizException;

	/**
	 * 活动目标编辑
	 * 
	 * @param id
	 * @param goaldto
	 * @throws ServiceBizException
	 */
	public void updatePlanConclusion(String id, CampaignGoalDTO goaldto) throws ServiceBizException;

	/**
	 * 活动总结查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryConclusion(Map<String, String> queryParam, String id);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public CampaignConclusionPO findFromId(String id) throws ServiceBizException;

	/**
	 * 活动目标修改
	 * 
	 * @param id
	 * @param goaldto
	 * @throws ServiceBizException
	 */
	public void updateCampaignConclusion(String id, MarketCampaignDTO conclusiondto) throws ServiceBizException;

	/**
	 * 活动总结附件查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignConclusionAttached(Map<String, String> queryParam, String id);

	/**
	 * 活动总结附件新增
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public void addAttached(CampaignAttachedDTO attacheddto) throws ServiceBizException;

	/**
	 * 删除活动总结附件
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteAttached(String id) throws ServiceBizException;

	/****************************************************/

	/**
	 * 新增市场活动
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 * @throws Exception 
	 */
	public void insertPlan(TtcampaignPlanDTO ttcampaignPlanDTO) throws ServiceBizException, Exception;

	/**
	 * 修改市场活动
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void updatePlan(String id, TtcampaignPlanDTO ttcampaignPlanDTO);

	/**
	 * 删除市场活动
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deletePlanById(String id) throws ServiceBizException;

	/**
	 * 新增活动目标
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertPlanGold(TtCampaignGoalDTO ttCampaignGoalDTO) throws ServiceBizException;

	/**
	 * 删除活动目标
	 * 
	 * @param id
	 */
	public void deleteGoldById(String id) throws ServiceBizException;

	/**
	 * 新增广告费用
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertFee(TtCampaignAdvertFeeDTO ttCampaignAdvertFeeDTO) throws ServiceBizException;

	/**
	 * 删除广告费用
	 * 
	 * @param id
	 */
	public void deleteFeeById(String id) throws ServiceBizException;

	/**
	 * 新增公关费用
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertPrFee(TtCampaignPrFeeDTO ttCampaignPrFeeDTO) throws ServiceBizException;

	/**
	 * 删除公关费用
	 * 
	 * @param id
	 */
	public void deletePrFeeById(String id) throws ServiceBizException;

	/**
	 * 新增现场布置及其他费用
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertPpoFee(TtCampaignPopFeeDTO ttCampaignPopFeeDTO) throws ServiceBizException;

	/**
	 * 删除现场布置及其他费用
	 * 
	 * @param id
	 */
	public void deletePpoFeeById(String id) throws ServiceBizException;

	/**
	 * 新增促销费用
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertPaslesFee(TtCampaignPsalesFeeDTO ttCampaignPsalesFeeDTO) throws ServiceBizException;

	/**
	 * 删除促销费用
	 * 
	 * @param id
	 */
	public void deletePaslesFeeById(String id) throws ServiceBizException;

	/**
	 * 新增市场活动附件
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertAttached(TtCampaignPlanAttachedDTO ttCampaignPlanAttachedDTO) throws ServiceBizException;

	/**
	 * 删除市场活动附件
	 * 
	 * @param id
	 */
	public void deleteAttachedFeeById(String id) throws ServiceBizException;

	/**
	 * 新增活动车辆
	 * 
	 * @param ttcampaignPlanDTO
	 * @return
	 * @throws ServiceBizException
	 */
	public void insertVehicle(String CampaignCode,String SeriesCodes,String ModelCode) throws ServiceBizException;

	/**
	 * 删除活动车辆
	 * 
	 * @param id
	 */
	public void deleteVehicleById(String id) throws ServiceBizException;

	/**
	 * 活动车系查询
	 * 
	 * @param queryParam
	 * @param id
	 * @return
	 */
	public PageInfoDto QueryCampaignSeries2(Map<String, String> queryParam);

	/**
	 * 市场活动附件Id
	 * 
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 */
	public TtCampaignPlanAttachedPO findByAttachedId(String id) throws ServiceBizException;

}
