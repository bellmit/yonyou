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
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.PreclaimPreQueryOemService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔预授权状态查询
 * @author Administrator
 */
@Controller
@TxnConn
@RequestMapping("/preclaimPreQueryOem")
public class PreclaimPreQueryOemController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PreclaimPreQueryOemService  preclaimPreQueryOemService;
	
	/**
	 * 索赔预授权状态查询
	 */
	@RequestMapping(value="/preclaimPreQuery",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto PreclaimPreQueryOemSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 索赔预授权状态查询=============");
       PageInfoDto pageInfoDto =preclaimPreQueryOemService.PreclaimPreQueryOemQuery(queryParam);
       return pageInfoDto;  
   }
	
}
