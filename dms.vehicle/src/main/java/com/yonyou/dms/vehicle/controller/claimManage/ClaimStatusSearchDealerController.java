package com.yonyou.dms.vehicle.controller.claimManage;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yonyou.dms.vehicle.service.claimManage.ClaimStatusSearchDealerService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月28日
*/


@Controller
@TxnConn
@RequestMapping("/claimStatusSearch")
public class ClaimStatusSearchDealerController {
	
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ClaimStatusSearchDealerController.class);
	
	@Autowired
	private ClaimStatusSearchDealerService cssdService;
	
	
	/**
	 * 索赔申请单状态跟踪 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimStatusSearchQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔申请单状态跟踪 查询04==============");
    	PageInfoDto pageInfoDto = cssdService.queryClaimStatusSearchDealer(queryParam);   	
        return pageInfoDto;               
    }
	
	/**
	 * 索赔申请单状态跟踪 下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void claimStatusSearchDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============索赔申请单状态跟踪 下载05===============");
    	cssdService.downloadClaimStatusSearchDealer(queryParam, request, response);
	}
	
	/**
	 * 索赔申请单状态跟踪明细 审核历史
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimAuditQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimAuditQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔申请单状态跟踪  审核历史 查询04==============");
    	PageInfoDto pageInfoDto = cssdService.claimAuditQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	/**
	 * 
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/accessory/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto accessoryClaimQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔申请单状态跟踪  审核历史 查询04==============");
    	PageInfoDto pageInfoDto = cssdService.accessoryQueryList(claimId);   	
        return pageInfoDto;               
    }
	
	/**
	 * 获取索赔类型列表下拉选
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimType/{TYPE}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryClaimTypeList(@PathVariable("TYPE") Integer type,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔申请单状态跟踪  审核历史 查询04==============");
    	List<Map> resultMap = cssdService.queryClaimTypeList(type);   	
        return resultMap;               
    }
	
	
	
}










