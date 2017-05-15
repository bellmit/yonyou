package com.yonyou.dms.vehicle.controller.claimManage;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;
import com.yonyou.dms.vehicle.service.claimManage.ClaimGroupApprovalService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年5月4日
*/


@Controller
@TxnConn
@RequestMapping("/claimGroupApproval")
public class ClaimGroupApprovalController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ClaimGroupApprovalController.class);
		
	
	@Autowired
	private ClaimGroupApprovalService claimService;
	
	
	/**
	 * 索赔单审批(索赔组) 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/{TYPE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryClaimGroupApproval(@PathVariable("TYPE") Integer type ,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔单审批(索赔组)  查询03==============");
    	PageInfoDto pageInfoDto =  claimService.queryClaimGroupApproval(queryParam, type);   	
        return pageInfoDto;               
    }
	
	/**
	 * 申请信息下载
	 * @param claimId
	 * @param response
	 * @param uriCB
	 */
	@RequestMapping(value = "/download/{CLAIM_ID}", method = RequestMethod.GET)
	@ResponseBody
    public void downloadClaimGroupApproval(@PathVariable("CLAIM_ID") Long claimId, 
			HttpServletResponse response, 
    		UriComponentsBuilder uriCB) {
    	logger.info("============索赔单审批(索赔组) 明细下载03===============");
    	
    	
    }
	
	/**
	 * 索赔组审核 审核历史
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimAuditQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimAuditQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔单审批(索赔组) 审核历史 查询03==============");
    	PageInfoDto pageInfoDto = claimService.claimAuditQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	

	/**
	 * 索赔组审核
	 * @param response
	 * @param uriCB
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
   public ResponseEntity<String> bigAreaClaimApproval(@RequestBody @Valid ClaimManageDTO cmDto , 
   		@RequestParam Map<String, String> queryParam,UriComponentsBuilder uriCB) {
		logger.info("============索赔单审批(索赔组) 审核03===============");
   		claimService.claimGroupApproval(cmDto);
   	
   	    MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/claimGroupApproval/approval").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
   	
   }
	
}






