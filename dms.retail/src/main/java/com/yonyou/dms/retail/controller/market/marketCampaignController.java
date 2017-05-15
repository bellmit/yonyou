package com.yonyou.dms.retail.controller.market;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

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
import com.yonyou.dms.retail.service.market.marketCampaignService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 活动计划/活动执行总结
 * 
 * @author Benzc/Zoukq
 */
@Controller
@TxnConn
@RequestMapping("/markting/marktingPlan")
public class marketCampaignController extends BaseController {
	@Autowired
	private marketCampaignService marketCampaignService;

	/**
	 * 市场活动查询
	 * 
	 * @author
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPayStatistics(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
//		if (queryParam.get("campaignName") == null && queryParam.get("yearMonth") == null
//				&& queryParam.get("performType") == null && queryParam.get("curAuditingStatus") == null
//				&& queryParam.get("startBegin") == null && queryParam.get("endBegin") == null) {
//			throw new ServiceBizException("至少有一个查询条件");
//			return null;
//		} else {
//			
//
//		}
		return marketCampaignService.queryCampaign(queryParam);
	}

	/**
	 * 查询申请人
	 * 
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/applicant", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAppli(@RequestParam Map<String, String> queryParam) throws ServiceBizException, SQLException {
		return marketCampaignService.queryApplicant(queryParam);

	}

	/**
	 * 活动目标查询
	 * 
	 * @author Benzc
	 * @date 2017年2月22日
	 */
	@RequestMapping(value = "/{id}/CampaignGoal", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCampaignGoal(@RequestParam Map<String, String> queryParam, @PathVariable("id") String id) {
		PageInfoDto goalList = marketCampaignService.QueryCampaignGoal(queryParam, id);
		return goalList;
	}

	/**
	 * 广告费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月22日
	 */
	@RequestMapping(value = "/{id}/CampaignFee", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCampaignFee(@RequestParam Map<String, String> queryParam, @PathVariable("id") String id) {
		PageInfoDto feeList = marketCampaignService.QueryCampaignFee(queryParam, id);
		return feeList;
	}

	/**
	 * 公关费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignPrFee", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignPrFee(@RequestParam Map<String, String> queryParam, @PathVariable("id") String id) {
		PageInfoDto prfeeList = marketCampaignService.QueryCampaignPrFee(queryParam, id);
		return prfeeList;
	}

	/**
	 * 现场布置费用及其他费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignPopFee", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignPopFee(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto popfeeList = marketCampaignService.QueryCampaignPopFee(queryParam, id);
		return popfeeList;
	}

	/**
	 * 促销费用查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignPsalesFee", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignPsalesFee(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto psalesfeeList = marketCampaignService.QueryCampaignPsalesFee(queryParam, id);
		return psalesfeeList;
	}

	/**
	 * 市场活动附件查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignPlanattached", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignPlanattached(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto planattachedList = marketCampaignService.QueryCampaignPlanattached(queryParam, id);
		return planattachedList;
	}

	/**
	 * 审核记录查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignApprovedMome", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignApprovedMome(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto memoList = marketCampaignService.QueryCampaignApprovedMome(queryParam, id);
		return memoList;
	}

	/**
	 * 活动车系查询
	 * 
	 * @author Benzc
	 * @date 2017年2月23日
	 */
	@RequestMapping(value = "/{id}/campaignSeries", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignSeries(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto seriesList = marketCampaignService.QueryCampaignSeries(queryParam, id);
		return seriesList;
	}

	/**
	 * 活动车系查询
	 * 
	 * @author
	 * @date
	 */
	@RequestMapping(value = "/campaignSeriesAign", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignSeries2(@RequestParam Map<String, String> queryParam) {
		PageInfoDto seriesList = marketCampaignService.QueryCampaignSeries2(queryParam);
		return seriesList;
	}

	/**
	 * 新增
	 * 
	 * @author Benzc
	 * @date 2016年12月22日
	 * @param uriCB
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MarketCampaignDTO> addDiscountMode(@RequestBody MarketCampaignDTO marketdto,
			UriComponentsBuilder uriCB) {
		marketCampaignService.addMarket(marketdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/markting/marktingPlan/{id}").buildAndExpand(marketdto.getCampaignCode()).toUriString());
		return new ResponseEntity<MarketCampaignDTO>(marketdto, headers, HttpStatus.CREATED);
	}

	/**
	 * 删除活动总结
	 * 
	 * @author Benzc
	 * @date 2017年2月27日
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMarketCampaign(@PathVariable("id") String id, UriComponentsBuilder uriCB) {
		marketCampaignService.deleteMarketCampaign(id);
	}

	/**
	 * 根据id查找
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value = "/{id}/planConclusion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findId(@PathVariable String id) throws ServiceBizException {
		TtCampaignGoalPO ggpo = marketCampaignService.findId(id);
		return ggpo.toMap();
	}

	/**
	 * 活动目标修改
	 * 
	 * @author Benzc
	 * @date 2017年2月27日
	 */

	@RequestMapping(value = "/{id}/planConclusion", method = RequestMethod.PUT)
	public ResponseEntity<CampaignGoalDTO> updateCampaignGoal(@PathVariable("id") String id,
			@RequestBody CampaignGoalDTO goaldto, UriComponentsBuilder uriCB) {
		marketCampaignService.updatePlanConclusion(id, goaldto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/markting/marktingPlan/{id}/planConclusion").buildAndExpand(id).toUriString());
		return new ResponseEntity<CampaignGoalDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 活动总结查询
	 * 
	 * @author Benzc
	 * @date 2017年2月28日
	 */
	@RequestMapping(value = "/{id}/conclusion", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryConclusion(@RequestParam Map<String, String> queryParam, @PathVariable("id") String id) {
		PageInfoDto conclusionList = marketCampaignService.QueryConclusion(queryParam, id);
		return conclusionList;
	}

	/**
	 * 根据id查找
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value = "/{id}/campaignConclusion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById2(@PathVariable String id) throws ServiceBizException {
		CampaignConclusionPO ggpo = marketCampaignService.findFromId(id);
		return ggpo.toMap();
	}

	/**
	 * 活动目标修改
	 * 
	 * @author Benzc
	 * @date 2017年2月28日
	 */

	@RequestMapping(value = "/{id}/campaignConclusion", method = RequestMethod.PUT)
	public ResponseEntity<MarketCampaignDTO> updateCampaignConclusion(@PathVariable("id") String id,
			@RequestBody MarketCampaignDTO conclusiondto, UriComponentsBuilder uriCB) {
		marketCampaignService.updateCampaignConclusion(id, conclusiondto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}/campaignConclusion")
				.buildAndExpand(conclusiondto.getCampaignCode()).toUriString());
		return new ResponseEntity<MarketCampaignDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 活动总结附件查询
	 * 
	 * @author Benzc
	 * @date 2017年3月1日
	 */
	@RequestMapping(value = "/{id}/campaignConclusionAttached", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querycampaignConclusionAttached(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto attachedList = marketCampaignService.QueryCampaignConclusionAttached(queryParam, id);
		return attachedList;
	}

	/**
	 * 新增活动总结附件新增
	 * 
	 * @author Benzc
	 * @date 2016年3月1日
	 * @return
	 */

	@RequestMapping(value = "/attached", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CampaignAttachedDTO> addConclAttached(@RequestBody CampaignAttachedDTO attacheddto,
			UriComponentsBuilder uriCB) {
		marketCampaignService.addAttached(attacheddto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/markting/marktingPlan/{id}").buildAndExpand(attacheddto.getCampaignCode()).toUriString());
		return new ResponseEntity<CampaignAttachedDTO>(attacheddto, headers, HttpStatus.CREATED);
	}

	/**
	 * 删除活动总结
	 * 
	 * @author Benzc
	 * @date 2017年3月1日
	 */

	@RequestMapping(value = "/{id}/attached", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAttached(@PathVariable("id") String id, UriComponentsBuilder uriCB) {
		marketCampaignService.deleteAttached(id);
	}

	/******************************************************************/

	/**
	 * 
	 * 新增市场活动
	 * 
	 * @date
	 * @param
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/plan", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtcampaignPlanDTO> addPlan(@RequestBody TtcampaignPlanDTO ttcampaignPlanDTO,
			UriComponentsBuilder uriCB) throws Exception {
		marketCampaignService.insertPlan(ttcampaignPlanDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttcampaignPlanDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtcampaignPlanDTO>(ttcampaignPlanDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据id查找
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable String id) throws ServiceBizException {
		System.err.println(id + "%%%%%%%");
		TtCampaignPlanPO wtpo = marketCampaignService.findById(id);
		return wtpo.toMap();
	}

	/**
	 * 根据id查找
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value = "/{id}/AttachedId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByAttachedId(@PathVariable String id) throws ServiceBizException {
		System.err.println(id + "%%%%%%%");
		TtCampaignPlanAttachedPO wtpo = marketCampaignService.findByAttachedId(id);
		return wtpo.toMap();
	}

	/**
	 * 
	 * 修改市场活动
	 * 
	 * @param id
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtcampaignPlanDTO> updateInsurance(@PathVariable String id,
			@RequestBody TtcampaignPlanDTO ttcampaignPlanDTO, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.updatePlan(id, ttcampaignPlanDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<TtcampaignPlanDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除市场活动
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/plan", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePlan(@PathVariable("id") String id, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.deletePlanById(id);
	}

	/**
	 * 
	 * 新增活动目标
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/gold", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtCampaignGoalDTO> addGold(@RequestBody TtCampaignGoalDTO ttCampaignGoalDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.insertPlanGold(ttCampaignGoalDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignGoalDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignGoalDTO>(ttCampaignGoalDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除活动目标
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/gold", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteGold(@PathVariable("id") String id, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.deleteGoldById(id);
	}

	/**
	 * 
	 * 新增广告费用
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/advertFee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtCampaignAdvertFeeDTO> addFee(@RequestBody TtCampaignAdvertFeeDTO ttCampaignAdvertFeeDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.insertFee(ttCampaignAdvertFeeDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignAdvertFeeDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignAdvertFeeDTO>(ttCampaignAdvertFeeDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除广告费用
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/advertFee", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteFeeById(@PathVariable("id") String id, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.deleteFeeById(id);
	}

	/**
	 * 
	 * 新增公关费用
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/prFee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtCampaignPrFeeDTO> addPrFee(@RequestBody TtCampaignPrFeeDTO ttCampaignPrFeeDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.insertPrFee(ttCampaignPrFeeDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignPrFeeDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignPrFeeDTO>(ttCampaignPrFeeDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除公关费用
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/prFee", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePrFeeById(@PathVariable("id") String id, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.deletePrFeeById(id);
	}

	/**
	 * 
	 * 新增现场布置及其他费用
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/popfee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtCampaignPopFeeDTO> addPpoFee(@RequestBody TtCampaignPopFeeDTO ttCampaignPopFeeDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.insertPpoFee(ttCampaignPopFeeDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignPopFeeDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignPopFeeDTO>(ttCampaignPopFeeDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除现场布置及其他费用
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/popfee", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePpoFeeById(@PathVariable("id") String id, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.deletePpoFeeById(id);
	}

	/**
	 * 
	 * 新增促销费用
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/PaslesFee", method = RequestMethod.POST)

	@ResponseBody
	public ResponseEntity<TtCampaignPsalesFeeDTO> addPaslesFee(
			@RequestBody TtCampaignPsalesFeeDTO ttCampaignPsalesFeeDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		marketCampaignService.insertPaslesFee(ttCampaignPsalesFeeDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignPsalesFeeDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignPsalesFeeDTO>(ttCampaignPsalesFeeDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除促销费用
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/PaslesFee", method = RequestMethod.DELETE)

	@ResponseBody
	public void deletePaslesFeeById(@PathVariable("id") String id, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		marketCampaignService.deletePaslesFeeById(id);
	}

	/**
	 * 
	 * 新增市场活动附件
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/planattached", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtCampaignPlanAttachedDTO> addAttached(
			@RequestBody TtCampaignPlanAttachedDTO ttCampaignPlanAttachedDTO, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		marketCampaignService.insertAttached(ttCampaignPlanAttachedDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/markting/marktingPlan/{id}")
				.buildAndExpand(ttCampaignPlanAttachedDTO.getCampaignCode()).toUriString());
		return new ResponseEntity<TtCampaignPlanAttachedDTO>(ttCampaignPlanAttachedDTO, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ID 删除活动附件
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/planattached", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteAttachedById(@PathVariable("id") String id, UriComponentsBuilder uriCB)
			throws ServiceBizException {
		marketCampaignService.deleteAttachedFeeById(id);
	}

	/**
	 * 
	 * 新增活动车型车系
	 * 
	 * @date
	 * @param
	 * @return
	 */

	@RequestMapping(value = "/add/servies/{campaignCode}/{seriesCodes}/{modelCode}", method = RequestMethod.POST)
	@ResponseBody
	public void addSeries(@PathVariable String campaignCode, @PathVariable String seriesCodes,
			@PathVariable String modelCode, UriComponentsBuilder uriCB) throws ServiceBizException {
		marketCampaignService.insertVehicle(campaignCode, seriesCodes, modelCode);
	}

	/**
	 * 根据ID 删除活动车型车系
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{id}/series", method = RequestMethod.DELETE)
	@ResponseBody
	public void deleteSeriesById(@PathVariable("id") String id) throws ServiceBizException {
		marketCampaignService.deleteVehicleById(id);
	}

}