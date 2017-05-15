package com.yonyou.dms.vehicle.controller.vehicleDirectOrderManage;

import java.util.List;
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
import com.yonyou.dms.vehicle.controller.vehicleAllot.DealerAllotApplyController;
import com.yonyou.dms.vehicle.service.vehicleDirectOrderManage.RealOrderQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 直销车实销查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/realOrderQuery")
public class RealOrderQueryController {
	@Autowired
	RealOrderQueryService  realOrderQueryService ;
	
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);

	   @RequestMapping(value="/realOrderSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryRealOrder(@RequestParam Map<String, String> queryParam) {
	    	logger.info("==============直销车实销查询=============");
	        PageInfoDto pageInfoDto =realOrderQueryService.realOrderQuery(queryParam);
	        return pageInfoDto;  
	    }
	   /**
	    * 下载
	    */
		@RequestMapping(value = "/download", method = RequestMethod.GET)
		public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) {
			logger.info("============资源备注下载下载===============");
			realOrderQueryService.download(queryParam, response, request);
		}
		
		/**
		 * 通过id进行明细查询
		 */
	    @TxnConn
	    @RequestMapping(value = "/xiangXi/{orderId}", method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> ChexiQuery(@PathVariable String orderId,@RequestParam Map<String, String> queryParams){
	    	logger.info("=====通过id进行明细查询=====");
	    	List<Map> xilist = realOrderQueryService.getXiangxi(orderId);
	    	return xilist;
	    }
		
		
	
}
