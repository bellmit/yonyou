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
import com.yonyou.dms.vehicle.service.claimManage.BigAreaClaimApprovalService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年5月2日
*/


@Controller
@TxnConn
@RequestMapping("/bigAreaApproval")
public class BigAreaClaimApprovalController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(BigAreaClaimApprovalController.class);
	
	@Autowired
	private BigAreaClaimApprovalService bacaService;
	
	
	/**
	 * 索赔单审批(大区) 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query/{TYPE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBigAreaClaimApproval(@PathVariable("TYPE") Integer type ,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔单审批(大区)  查询02==============");
    	PageInfoDto pageInfoDto =  bacaService.queryBigAreaClaimApproval(queryParam, type);   	
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
    public void downloadBigAreaClaimApproval(@PathVariable("CLAIM_ID") Long claimId, 
			HttpServletResponse response, 
    		UriComponentsBuilder uriCB) {
    	logger.info("============索赔单审批(大区) 明细下载02===============");
    	
    	
    }
	/**
	 * 大区审核
	 * @param response
	 * @param uriCB
	 */
	@RequestMapping(value = "/approval", method = RequestMethod.POST)
	@ResponseBody
    public ResponseEntity<String> bigAreaClaimApproval(@RequestBody @Valid ClaimManageDTO cmDto , 
    		@RequestParam Map<String, String> queryParam,
    		UriComponentsBuilder uriCB) {
    	logger.info("============索赔单审批(大区) 审核02===============");
    	bacaService.bigAreaClaimApproval(cmDto);
    	
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/bigAreaApproval/approval").buildAndExpand().toUriString());
        return new ResponseEntity<String>( headers, HttpStatus.CREATED);
    	
    }
	/**
	 * 大区审核 审核历史
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimAuditQuery/{CLAIM_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimAuditQueryDetail(@PathVariable("CLAIM_ID") Long claimId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔单审批(大区) 审核历史 查询04==============");
    	PageInfoDto pageInfoDto = bacaService.claimAuditQueryDetail(claimId);   	
        return pageInfoDto;               
    }
	
}





