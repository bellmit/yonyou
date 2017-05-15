package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.ClaimTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔类型维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/claimType")
public class ClaimTypeController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimTypeService   claimTypeService;
	
	/**
	 * 索赔维护类型查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/claimTypeSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto basicParamsCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔维护类型查询=============");
        PageInfoDto pageInfoDto =claimTypeService.ClaimTypeQuery(queryParam);
        return pageInfoDto;  
    }
	
}
