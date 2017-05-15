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
import com.yonyou.dms.vehicle.service.activityManage.ActivitySummaryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月5日
*/
@Controller
@TxnConn
@RequestMapping("/activitySummary")
public class ActivitySummaryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivitySummaryController.class);
	
	@Autowired
	private ActivitySummaryService asService;
	
	
	/**
     *服务活动总结查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/summaryQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activitySummaryQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动总结查询04==============");
    	PageInfoDto pageInfoDto = asService.activitySummaryQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
	
    /**
     *服务活动总结     明细查询
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/summaryDeatilQuery/{ID}",method = RequestMethod.GET)
    @ResponseBody
    public Map activitySummaryDetailQuery(@PathVariable(value = "ID") Long id,
    		@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动总结  明细查询04==============");
    	Map map = asService.activitySummaryDeatilQuery(queryParam, id);   	
        return map;
        
        
    }
	
	
		
}











