package com.yonyou.dms.vehicle.controller.activityRecallManage;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.activityRecallManage.RecallFinishTotalService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月20日
*/


@Controller
@TxnConn
@RequestMapping("/recallFinishTotal")
public class RecallFinishTotalController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(RecallFinishTotalController.class);

	
	
	@Autowired
	private RecallFinishTotalService rftService;
	
	
	/**
	 * 召回完成统计 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/totalQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallFinishTotalQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回完成统计 查询03==============");
    	PageInfoDto pageInfoDto = rftService.recallFinishTotalQuery(queryParam);   	
        return pageInfoDto;               
    }
	/**
	 * 召回完成统计 主页面下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/totalDownload", method = RequestMethod.GET)
    @ResponseBody
    public void recallFinishTotalDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回完成统计 下载03===============");
    	rftService.recallFinishTotalDownload(queryParam, request, response);
	}
	/**
	 * 召回完成统计 主页面   明细下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/totalDownload/detail", method = RequestMethod.GET)
    @ResponseBody
    public void recallFinishTotalDownloadDetail(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回完成统计  明细下载03===============");
    	rftService.recallFinishTotalDownloadDetail(queryParam, request, response);
	}
	/**
	 * 明细页面查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/detailQuery/{RECALL_ID}/{DEALER_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallFinishQueryDetail(@RequestParam Map<String, String> queryParam,
    		@PathVariable("RECALL_ID") Long recallId,@PathVariable("DEALER_CODE") String dealerCode) {
    	logger.info("============召回完成统计 查询03==============");
    	PageInfoDto pageInfoDto = rftService.recallFinishQueryDetail(recallId, dealerCode);   	
        return pageInfoDto;               
    }
	/**
	 * 明细下载 表格中
	 * @param recallId
	 * @param dealerCode
	 * @param uriCB
	 */
	@RequestMapping(value = "/download/{RECALL_ID}/{DEALER_CODE}", method = RequestMethod.GET)
    //@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
    public void recallFinishQueryDetailDownload(@PathVariable("RECALL_ID") Long recallId, 
    		@PathVariable("DEALER_CODE") String dealerCode, HttpServletRequest request,
			HttpServletResponse response, 
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回完成统计 明细下载(活动、经销商限制)03===============");
    	rftService.recallFinishQueryDetailDownload(recallId, dealerCode, request, response);
    	
    }
	
	
}










