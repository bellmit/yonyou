package com.yonyou.dms.vehicle.controller.afterSales.warranty;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyMainPartAndLocService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 主因件与位置代码维护
 * @author zhanghongyi
 */
@Controller
@TxnConn
@RequestMapping("/warrantyMainPartAndLoc")
public class WarrantyMainPartAndLocController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	WarrantyMainPartAndLocService  warrantyMainPartAndLocService;
	
	/**
	 * 查询
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> queryParam) {
		logger.info("==============主因件与位置代码查询=============");
		PageInfoDto pageInfoDto = warrantyMainPartAndLocService.search(queryParam);
		return pageInfoDto;
	}

	/**
	 * 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============主因件与位置代码下载  ===============");
    	warrantyMainPartAndLocService.download(queryParam, request, response);
	}
}
