package com.yonyou.dms.vehicle.controller.activityManage;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivitySummaryDTO;
import com.yonyou.dms.vehicle.service.activityManage.ActivitySummaryDlrService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月6日
*/
@Controller
@TxnConn
@RequestMapping("/activitySummaryDlr")
public class ActivitySummaryDlrController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivitySummaryDlrController.class);
	
	@Autowired
	private ActivitySummaryDlrService asdService;
	
	
	/**
     *服务活动总结查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/summaryDlrQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activitySummaryDlrQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动总结Dlr 07==============");
    	PageInfoDto pageInfoDto = asdService.activitySummaryDlrQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
	
    /**
     *服务活动总结     明细查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/summaryDlrDeatilQuery/{ACTIVITY_ID}/{ACTIVITY_CODE}/{ACTIVITY_NAME}/{IN_AMOUNT}",method = RequestMethod.GET)
    @ResponseBody
    public Map activitySummaryDlrDetailQuery(@PathVariable(value = "ACTIVITY_CODE") String activityCode ,
    		@PathVariable(value = "ACTIVITY_NAME") String activityName ,
    		@PathVariable(value = "IN_AMOUNT") Integer inAmount ,
    		@PathVariable(value = "ACTIVITY_ID") Long activityId ,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动总结Dlr 维护查询 07==============");
    	Map map = asdService.activitySummaryDlrDetailQuery(activityCode, activityName, inAmount,activityId);   	
        return map;
        
        
    }
	
	
    @RequestMapping(value="/summaryUpdateSave",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ActivitySummaryDTO> activitySummaryUpdateSave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid ActivitySummaryDTO asDto,
    		UriComponentsBuilder uriCB) {
    	logger.info("============服务活动总结Dlr  上报07===============");
    	asdService.activitySummaryUpdateSave(asDto);
    	 MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/activitySummaryDlr/summaryUpdateSave").buildAndExpand().toUriString());
         return new ResponseEntity<ActivitySummaryDTO>(headers, HttpStatus.CREATED);    
    }
    
    
		
}











