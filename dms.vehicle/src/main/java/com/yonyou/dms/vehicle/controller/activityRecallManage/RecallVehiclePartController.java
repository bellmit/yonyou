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
import com.yonyou.dms.vehicle.service.activityRecallManage.RecallVehiclePartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月20日
*/


@Controller
@TxnConn
@RequestMapping("/recallVehiclePart")
public class RecallVehiclePartController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(RecallVehiclePartController.class);
	
	
	
	@Autowired
	private RecallVehiclePartService  rvpService;
	
	
	/**
	 * 召回车辆配件满足率  查询（主页面） 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallVehiclePartQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆配件满足率 查询05==============");
    	PageInfoDto pageInfoDto = rvpService.recallVehiclePartQuery(queryParam);   	
        return pageInfoDto;               
    }
	
	/**
	 *  下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void recallVehiclePartDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回车辆配件满足率 下载05===============");
    	rvpService.recallVehiclePartDownload(queryParam, request, response);
	}
	
	/**
	 *  明细下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/detailDownload", method = RequestMethod.GET)
    @ResponseBody
    public void recallVehiclePartDownloadDetail(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回车辆配件满足率 明细下载05===============");
    	rvpService.recallVehiclePartDownloadDetail(queryParam, request, response);
	}
	
	
	
	/**
	 *   查询（明细页面） 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/detailQuery/{RECALL_ID}/{DEALER_CODE}/{GROUP_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallDetailVehiclePartQuery(@PathVariable("RECALL_ID") Long recallId, 
    		@PathVariable("DEALER_CODE") String dealerCode, 
    		@PathVariable("GROUP_NO") String groupNo, 
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆配件满足率  明细查询05==============");
    	PageInfoDto pageInfoDto = rvpService.recallDetailVehiclePartQuery(dealerCode, recallId, groupNo);   	
        return pageInfoDto;               
    }
	/**
	 * 明细下载（表格中）
	 * @param recallId
	 * @param dealerCode
	 * @param request
	 * @param response
	 * @param uriCB
	 */
	@RequestMapping(value = "/download/{RECALL_ID}/{DEALER_CODE}/{GROUP_NO}", method = RequestMethod.GET)
	@ResponseBody
    public void recallFinishQueryDetailDownload(@PathVariable("RECALL_ID") Long recallId, 
    		@PathVariable("DEALER_CODE") String dealerCode, 
    		@PathVariable("GROUP_NO") String groupNo, 
    		HttpServletRequest request,HttpServletResponse response, 
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回车辆配件满足率 明细下载（召回ID、经销商限制)05===============");
    	rvpService.recallDetailVehiclePartDownload(dealerCode, recallId, groupNo, request, response);
    	
    }

}









