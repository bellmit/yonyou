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
import com.yonyou.dms.vehicle.dao.k4Order.OrderLogQueryDao;
import com.yonyou.dms.vehicle.service.k4Order.OrderLogQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月1日
*/
@Controller
@TxnConn
@RequestMapping("/OrderCancelLogQuery")
public class OrderCancelLogQueryController {


	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoQueryController.class);
	
	@Autowired
	private OrderLogQueryDao  orderLogDao;
	
	@Autowired
	private OrderLogQueryService  orderLogService;
	
	
	/**
     *日志查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/orderCancel/logQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto orderCancelLogQuery(@RequestParam Map<String, String> queryParam) {
    	//System.out.println("queryInitController");
    	logger.info("============撤单日志查询09==============");
    	PageInfoDto pageInfoDto =orderLogDao.getOrderLogQueryList(queryParam);
    	
    	  return pageInfoDto;
    }
	
    /**
	 * 订单汇总查询 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/orderCancle/logDownload", method = RequestMethod.GET)
	public void orderCancelLogDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============撤单日志下载09===============");
    	orderLogService.findOrderLogQueryDownload(queryParam, request, response); 
    }
	
}






