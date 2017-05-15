package com.yonyou.dms.vehicle.controller.claimManage;

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
import com.yonyou.dms.vehicle.service.claimManage.ClaimNoticeQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月27日
*/


@Controller
@TxnConn
@RequestMapping("/claimNotice")
public class ClaimNoticeQueryController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ClaimNoticeQueryController.class);
	
	@Autowired
	private ClaimNoticeQueryService cnqService;
	
	
	
	/**
	 * 索赔结算单查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimNoticeQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔结算单 查询05==============");
    	PageInfoDto pageInfoDto = cnqService.queryClaimNotice(queryParam);   	
        return pageInfoDto;               
    }
	
	/**
	 * 索赔结算单  明细
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/queryDetail/{CLAIMS_BILLING_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto claimNoticeQueryDetail(@PathVariable("CLAIMS_BILLING_ID") Long claimBillingId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔结算单明细  详细信息 05==============");
    	PageInfoDto pageInfoDto = cnqService.queryClaimNoticeDetail(claimBillingId);   	
        return pageInfoDto;               
    }
	
	/**
	 * 索赔结算单  明细
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/queryMap/{CLAIMS_BILLING_ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map claimNoticeQueryDetailMap(@PathVariable("CLAIMS_BILLING_ID") Long claimBillingId,@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔结算单明细  查询05==============");
    	Map map = cnqService.queryClaimNoticeDetailMap(claimBillingId);   	
        return map;               
    }
	/**
	 * 开票
	 * @param claimBillingId
	 * @param queryParam
	 */
	@RequestMapping(value="/update/{CLAIMS_BILLING_ID}/{IS_INVOICE}",method = RequestMethod.POST)
    @ResponseBody
    public void claimNoticeUpdate(@PathVariable("CLAIMS_BILLING_ID") Long claimBillingId,
    		@PathVariable("IS_INVOICE") Integer isInvoice,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============索赔结算单明细  开票  查询05==============");
    	cnqService.updateClaimNotice(claimBillingId, isInvoice);          
    }
	/**
	 * 下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadclaimNotice(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============索赔结算单下载05===============");
    	
	}

}
