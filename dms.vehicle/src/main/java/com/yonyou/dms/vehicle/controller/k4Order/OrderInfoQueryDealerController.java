package com.yonyou.dms.vehicle.controller.k4Order;


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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.k4Order.OrderInfoQueryDealerDao;
import com.yonyou.dms.vehicle.service.k4Order.OrderInfoQueryDealerService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月7日
*/
@Controller
@TxnConn
@RequestMapping("/orderInfoQueryDealer")
public class OrderInfoQueryDealerController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoQueryDealerController.class);
	
	@Autowired
	private OrderInfoQueryDealerDao orderDealerDao;

	
	@Autowired
	private OrderInfoQueryDealerService orderDealerService;
	
	/**
     *整车订单查询（经销商）	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/orderInfo/dealerQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto orderInfoDealerQuery(@RequestParam Map<String, String> queryParam) {

    	logger.info("============整车订单查询（经销商）07==============");
    	PageInfoDto pageInfoDto =orderDealerDao.getOrderInfoQueryDealerList(queryParam);
    	
        return pageInfoDto;
        
        
    }
	
    /**
	 * 整车订单查询（经销商）下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/orderInfo/dealerDownload", method = RequestMethod.GET)
	public void orderInfoDealerQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("===========整车订单查询（经销商）下载07===============");
    	orderDealerService.findOrderInfoQueryDealerDownload(queryParam, request, response);
	} 
    
    
    @RequestMapping(value = "/{vin}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getOrderDealerByVIN(@PathVariable(value = "vin") String vin) {
    	logger.info("============整车订单(经销商)明细07===============");
    	Map<String, Object> map = orderDealerService.findOrderVehicleByVIN(vin);
        return map;
    }

}
