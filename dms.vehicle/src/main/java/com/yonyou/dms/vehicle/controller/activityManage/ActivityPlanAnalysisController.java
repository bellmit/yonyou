package com.yonyou.dms.vehicle.controller.activityManage;

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
import com.yonyou.dms.vehicle.service.activityManage.ActivityPlanAnalysisService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月1日
*/
@Controller
@TxnConn
@RequestMapping("/activityPlanAnalysis")
public class ActivityPlanAnalysisController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityPlanAnalysisController.class);
	
	@Autowired
	private ActivityPlanAnalysisService apaService;
	
	/**
     *	服务活动计划分析
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/planAnalysisInit",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityPlanAnalysisInitQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动计划分析 04==============");
    	PageInfoDto pageInfoDto = apaService.getPlanAnalysisInitQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
    
    /**
     *	服务活动计划分析
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/planAnalysisDetail/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityPlanAnalysisDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动计划分析  明细04==============");
    	PageInfoDto pageInfoDto = apaService.getPlanAnalysisDetailQuery(queryParam, activityId);   	
        return pageInfoDto;
        
        
    }
	
    /**
     *	服务活动计划分析
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/planAnalysisDetailTwo/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityPlanAnalysisDetailQueryTwo(@PathVariable(value = "ACTIVITY_ID") Long activityId,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动计划分析  责任明细 04==============");
    	PageInfoDto pageInfoDto = apaService.getPlanAnalysisDetailQueryTwo(queryParam, activityId);   	
        return pageInfoDto;
        
        
    }
	
}














