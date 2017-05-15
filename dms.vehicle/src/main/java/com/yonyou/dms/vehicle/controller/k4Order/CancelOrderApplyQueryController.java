package com.yonyou.dms.vehicle.controller.k4Order;


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
import com.yonyou.dms.vehicle.dao.k4Order.CancelOrderApplyQueryDao;
import com.yonyou.dms.vehicle.service.k4Order.CancelOrderApplyQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月9日
*/
@Controller
@TxnConn
@RequestMapping("/CancelOrderApplyQuery")
public class CancelOrderApplyQueryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(CancelOrderApplyQueryController.class);
	

	@Autowired
	private CancelOrderApplyQueryDao  cancleAppDao;
	
	@Autowired
	private CancelOrderApplyQueryService cancleAppService;
	
	/**
     *撤单查询(经销商)查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/cancleApply/orderQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto cancelOrderApplyQuery(@RequestParam Map<String, String> queryParam) {
    	//System.out.println("queryInitController");
    	logger.info("============撤单查询(经销商)查询12==============");
    	PageInfoDto pageInfoDto = cancleAppDao.getCancelOrderApplyQueryList(queryParam); 
    	

        return pageInfoDto;
        
        
    }
	
    /**
	 * 撤单查询(经销商)下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/cancleApply/queryDownload", method = RequestMethod.GET)
	public void cancelOrderApplyQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============撤单查询(经销商)下载12===============");
    	cancleAppService.findCancelOrderApplyQueryDownload(queryParam, request, response);
	} 
	
    

	
}
