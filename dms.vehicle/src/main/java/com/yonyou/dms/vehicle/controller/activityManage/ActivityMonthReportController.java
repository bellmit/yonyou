package com.yonyou.dms.vehicle.controller.activityManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.activityManage.ActivityMonthReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月7日
*/
@Controller
@TxnConn
@RequestMapping("/activityMonthReport")
public class ActivityMonthReportController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityMonthReportController.class);
	
	 @Autowired
	 private ActivityMonthReportService amrService;
	
	
	/**
	 * 服务月活动报表  查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/monthReportQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityMonthReportQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务月活动报表  查询 10==============");
    	PageInfoDto pageInfoDto = amrService.getMonthReportQuery(queryParam);   	
        return pageInfoDto;

    }
	
	/**
	 * 服务月活动报表 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/monthReportDownload", method = RequestMethod.GET)
	public void activityMonthReportQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============服务月活动报表  下载  10===============");
    	amrService.getMonthReportDownload(queryParam, request, response);
	}
	
	

}
