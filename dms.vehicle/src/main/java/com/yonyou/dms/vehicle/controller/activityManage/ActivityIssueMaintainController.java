package com.yonyou.dms.vehicle.controller.activityManage;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.activityManage.ActivityIssueMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月31日
*/
@Controller
@TxnConn
@RequestMapping("/ActivityIssueMaintain")
public class ActivityIssueMaintainController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityIssueMaintainController.class);
	
	@Autowired
	private ActivityIssueMaintainService aimService;
	
	
	
	
	
	
	
	
	
	
	
	/**
     *服务活动发布 查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/activityIssueQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityIssueQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 服务活动发布  03==============");
    	PageInfoDto pageInfoDto = aimService.getActivityIssueQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
    /**
     *服务活动发布 查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/activityIssue/dealerQuery/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityIssueQueryDealer(@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 服务活动发布   经销商  03==============");
    	PageInfoDto pageInfoDto = aimService.getActivityIssueQueryDealer(queryParam, activityId);   	
        return pageInfoDto;
        
        
    }
    
    
    /**
     * 服务活动发布   发布 
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "activityIssue/{ACTIVITY_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityIssue(@PathVariable("ACTIVITY_ID") Long activityId, UriComponentsBuilder uriCB) {
    	logger.info("============服务活动发布   发布操作03==============");
    	
    }
    
    
    /**
     * 服务活动发布   发布 
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/activityCancleIssue/{ACTIVITY_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityCancleIssue(@PathVariable("ACTIVITY_ID") Long activityId, UriComponentsBuilder uriCB) {
    	logger.info("============服务活动发布   取消发布操作03==============");
    	
    }
    
	
	
}









