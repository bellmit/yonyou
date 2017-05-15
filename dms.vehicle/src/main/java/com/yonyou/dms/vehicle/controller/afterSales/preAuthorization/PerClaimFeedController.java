package com.yonyou.dms.vehicle.controller.afterSales.preAuthorization;

import java.util.Map;

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
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.PerClaimFeedService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/perClaimFeed")
public class PerClaimFeedController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PerClaimFeedService  perClaimFeedService;
	
	 // 索陪预授权反馈信息查询
	@RequestMapping(value="/perClaimFeedSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto perClaimFeedSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("============== 索陪预授权反馈信息查询=============");
        PageInfoDto pageInfoDto =perClaimFeedService.PerClaimFeedQuery(queryParam);
        return pageInfoDto;  
    }

}
