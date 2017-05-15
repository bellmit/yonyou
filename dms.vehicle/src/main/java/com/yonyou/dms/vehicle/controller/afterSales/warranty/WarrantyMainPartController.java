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
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyMainPartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 主因件维护
 * @author xuqinqin
 */
@Controller
@TxnConn
@RequestMapping("/warrantyMainPart")
public class WarrantyMainPartController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	WarrantyMainPartService  warrantyMainPartService;
	
	/**
	 * 主因件维护查询
	 */
	@RequestMapping(value = "/warrantyMainPartSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto warrantyMainPartSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==============主因件维护查询=============");
		PageInfoDto pageInfoDto = warrantyMainPartService.mainPartQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 主因件下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/downloadMainPart", method = RequestMethod.GET)
	public void MainPartDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============主因件维护  主页面下载  ===============");
    	warrantyMainPartService.mainPartDownload(queryParam, request, response);
	}
}
