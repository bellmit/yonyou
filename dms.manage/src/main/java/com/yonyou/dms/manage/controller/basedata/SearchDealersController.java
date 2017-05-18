package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.service.basedata.dealer.SearchDealersService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 经销商弹出多选页面
 * 
 * @author xiawei
 * @date 2016年7月15日
 */

@Controller
@TxnConn
@RequestMapping("/basedataDealers")
public class SearchDealersController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SearchDealersController.class);

	@Autowired
	SearchDealersService service;

	/**
	 * 弹出经销商多选界面
	 * 
	 * @author xiawei
	 * @date 2017年2月20日
	 * @return
	 */
	@RequestMapping(value = "/SearchDealers", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllDealers(@RequestParam Map<String, String> queryParams) {
		logger.info("=====弹出经销商多选界面=====");
		List<Map> tenantMapping = service.getDealerList(queryParams);
		return tenantMapping;
	}

	/**
	 * 弹出经销商单选界面
	 * 
	 * @author xiawei
	 * @date 2017年2月20日
	 * @return
	 */
	@RequestMapping(value = "/SearchDealer", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllDealer(@RequestParam Map<String, String> queryParams) {
		logger.info("=====弹出经销商单选界面=====");
		List<Map> tenantMapping = service.getDealerList(queryParams);
		return tenantMapping;
	}

	/**
	 * 弹出经销商单选界面
	 * 
	 * @author xiawei
	 * @date 2017年2月20日
	 * @return
	 */
	@RequestMapping(value = "/SearchCheckDealer", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllCheckDealer(@RequestParam Map<String, String> queryParams) {
		logger.info("=====弹出经销商单选界面=====");
		List<Map> tenantMapping = service.searchCheckDealer(queryParams);
		return tenantMapping;
	}
	
	/**
	 * 查询经销商售后
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/SearchAfterDealer",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto SearchAfterDealer(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====查询经销商售后=====");
		
		 return service.getDealerShouHouList(queryParams);
		 
	}

}
