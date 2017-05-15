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
import com.yonyou.dms.vehicle.service.activityManage.ActivityReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月5日
*/
@Controller
@TxnConn
@RequestMapping("/activityReport")
public class ActivityReportController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityReportController.class);
	
	@Autowired
	private ActivityReportService  arService;
	
	
	/**
     *服务活动统计 查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/reportQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityReportQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动统计  06==============");
    	PageInfoDto pageInfoDto = arService.activityReportQuery(queryParam);   	
        return pageInfoDto;                
    }
    
    
    /**
     * 服务活动统计 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/reportDetailQuery/{ACTIVITY_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto detailGTMMsgQuery(@PathVariable(value = "ACTIVITY_CODE") String activityCode,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============ 服务活动统计 明细  06==============");
    	PageInfoDto pageInfoDto = arService.activityReportDetailQuery(queryParam, activityCode);   	
        return pageInfoDto;   
    }
    

}












