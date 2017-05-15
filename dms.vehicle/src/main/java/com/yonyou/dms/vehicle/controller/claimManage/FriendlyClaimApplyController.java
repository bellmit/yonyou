package com.yonyou.dms.vehicle.controller.claimManage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
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
	
}














