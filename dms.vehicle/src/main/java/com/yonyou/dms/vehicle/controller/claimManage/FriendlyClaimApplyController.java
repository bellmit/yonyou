package com.yonyou.dms.vehicle.controller.claimManage;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimApplyDTO;
import com.yonyou.dms.vehicle.service.claimManage.FriendlyClaimApplyService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月25日
*/


@Controller
@TxnConn
@RequestMapping("/friendlyClaimApply")
public class FriendlyClaimApplyController {
	
	
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FriendlyClaimApplyController.class);
	
	@Autowired
	private FriendlyClaimApplyService fcaService;
	
	
	/**
	 * 善意索赔申请 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto friendlyClaimApplyQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============善意索赔申请 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.queryFriendlyClaimApply(queryParam);   	
        return pageInfoDto;               
    }
	/**
	 * 工单明细 零部件清单
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/repairQueryPart/{REPAIR_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto repairQueryPartMsgDetail(@PathVariable("REPAIR_NO") String repairNo,@RequestParam Map<String, String> queryParam) {
    	logger.info("============工单明细 零部件清单 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.repairQueryPartMsgDetail(repairNo);   	
        return pageInfoDto;               
    }
	/**
	 * 工单明细 维修工时清单
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/repairQueryItem/{REPAIR_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto repairQueryItemMsgDetail(@PathVariable("REPAIR_NO") String repairNo,@RequestParam Map<String, String> queryParam) {
    	logger.info("============工单明细 维修工时清单 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.repairQueryItemMsgDetail(repairNo);   	
        return pageInfoDto;               
    }
	/**
	 * 工单明细 其他项目清单
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/repairQueryOther/{REPAIR_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto repairQueryOtherItemMsgDetail(@PathVariable("REPAIR_NO") String repairNo,@RequestParam Map<String, String> queryParam) {
    	logger.info("============工单明细 其他项目清单 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.repairQueryOtherItemMsgDetail(repairNo);   	
        return pageInfoDto;               
    }
	/**
	 * 工单明细 车辆信息
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/repairQueryVehicle/{VIN}",method = RequestMethod.GET)
    @ResponseBody
    public Map repairQueryVehicleMsgDetail(@PathVariable("VIN") String vin,@RequestParam Map<String, String> queryParam) {
    	logger.info("============工单明细 车辆信息 查询04==============");
    	Map map = fcaService.repairQueryVehicleMsgDetail(vin);   	
        return map;               
    }
	/**
	 * 工单明细 基本信息
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/repairQuery/{REPAIR_NO}",method = RequestMethod.GET)
    @ResponseBody
    public Map repairQueryMsgDetail(@PathVariable("REPAIR_NO") String repairNo,@RequestParam Map<String, String> queryParam) {
    	logger.info("============工单明细 基本信息 查询04==============");
    	Map map = fcaService.repairQueryMsgDetail(repairNo);   	
        return map;               
    }
	
	
	/**
	 * 查询 索赔申请 维修方案 索赔费用
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map claimQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细 申请信息 查询04==============");
    	Map map = fcaService.claimQueryDetail(claimId);   	
        return map;               
    }
	/**
	 * 索赔明细 情形
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimCaseQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimCaseQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细  情形 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.claimCaseQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	/**
	 * 索赔明细 工时
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimLabourQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimLabourQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细 工时清单 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.claimLabourQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	/**
	 * 索赔明细 配件
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimPartQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimLPartQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细 零部件清单 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.claimPartQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	/**
	 * 索赔明细 其他项目
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimOtherQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimLOtherQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细 其他项目 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.claimOtherQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	/**
	 * 索赔明细 审核历史
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimAuditQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimAuditQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔明细 审核历史 查询04==============");
    	PageInfoDto pageInfoDto = fcaService.claimAuditQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	
	/**
	 * 维修工单列表 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/repairOrder",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderInfo(@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询维修工单列表   04==============");
    	PageInfoDto pageInfoDto =  fcaService.queryRepairOrderList(queryParam);	
        return pageInfoDto;               
    }
	
	/**
	 * 查询预授权单信息 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/foreOrder",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryForeApprovalInfo(@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询预授权单信息   04==============");
    	PageInfoDto pageInfoDto =  fcaService.queryForeApprovalInfo(queryParam);	
        return pageInfoDto;               
    }
	/**
	 * 查询工单下的维修工时列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/repairLabour/{REPAIR_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderClaimLabour(@PathVariable("REPAIR_ID") Long repairId, @RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询工单下的维修工时列表   04==============");
    	PageInfoDto pageInfoDto =  fcaService.queryRepairOrderClaimLabour(queryParam);	
        return pageInfoDto;               
    }
	/**
	 * 查询工单下的维修零部件列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/repairPart/{REPAIR_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderClaimPart(@PathVariable("REPAIR_ID") Long repairId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询工单下的维修零部件列表  04==============");
    	PageInfoDto pageInfoDto =  fcaService.queryRepairOrderClaimPart(queryParam);	
        return pageInfoDto;               
    }
	/**
	 * 查询工单下的其他项目列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/repairOther/{REPAIR_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderClaimOther(@PathVariable("REPAIR_ID") Long repairId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询工单下的其他项目列表   04==============");
    	PageInfoDto pageInfoDto = fcaService.queryRepairOrderClaimOther(queryParam);	
        return pageInfoDto;               
    }
	
	/**
	 * 善意索赔申请  新增保存
	 */
	@RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> saveClaimOrderInfo(@RequestBody @Valid ClaimApplyDTO applyDto,
    		@RequestParam Map<String, String> queryParam,
    		UriComponentsBuilder uriCB) {
    	logger.info("============善意索赔申请单 新增保存  04===============");
    	fcaService.saveClaimOrderInfo(applyDto);    
    	
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/friendlyClaimApply/saveOrder").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    	
    }
	/**
	 * 善意索赔申请 新增提报
	 */
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> submitClaimOrderInfo(@RequestBody @Valid ClaimApplyDTO applyDto,
    		@RequestParam Map<String, String> queryParam,
    		UriComponentsBuilder uriCB) {
    	logger.info("============善意索赔申请单 新增提报  04===============");
    	    	
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/friendlyClaimApply/submitOrder").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    	
    }
	/**
	 * 查询经销商CODE和NAME
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/queryDealer",method = RequestMethod.GET)
    @ResponseBody
    public Map queryDealerCodeAndName(@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询当前登录经销商  04==============");
    	Map map = fcaService.queryDealerCodeAndName(queryParam);
        return map;               
    }
	/**
	 * 查询修改页面回显信息
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/queryEditMap/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map queryEditMessage(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 查询当前登录经销商  04==============");
    	Map map = fcaService.queryEditMessage(claimId, queryParam);
        return map;               
    }
	
	/**
	 * 善意索赔申请  修改保存
	 * @param applyDto
	 * @param queryParam
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateOrder/{CLAIM_ID}", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> updateClaimOrderInfo(@PathVariable("CLAIM_ID") Long claimId,
    		@RequestBody @Valid ClaimApplyDTO applyDto,
    		@RequestParam Map<String, String> queryParam,
    		UriComponentsBuilder uriCB) {
    	logger.info("============善意索赔申请单 修改页面保存  04===============");
    	fcaService.updateClaimOrderInfo(claimId, applyDto);
    	
    	
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/friendlyClaimApply/updateOrder").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    	
    }
	/**
	 * 善意索赔申请 修改和按钮提报
	 */
	@RequestMapping(value = "/submitOrderUpdate/{CLAIM_ID}", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> submitUpdateClaimOrderInfo(@PathVariable("CLAIM_ID") Long claimId,
    		@RequestBody @Valid ClaimApplyDTO applyDto,
    		@RequestParam Map<String, String> queryParam,
    		UriComponentsBuilder uriCB) {
    	logger.info("============善意索赔申请单 修改页面提报  04===============");
    	fcaService.submitClaimOrderInfo(applyDto, claimId);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    	
    }
	
	/**
	 * 删除 索赔申请
	 * @param claimId
	 * @param uriCB
	 */
	@RequestMapping(value = "/deleteClaim/{CLAIM_ID}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteClaimInfo(@PathVariable("CLAIM_ID") Long claimId, UriComponentsBuilder uriCB) {
	fcaService.deleteClaimInfo(claimId);      
	} 
	
}














