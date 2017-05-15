package com.yonyou.dms.manage.controller.personInfoManager;

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
import com.yonyou.dms.manage.service.basedata.personInfoManager.DealerSwitchService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


@Controller
@TxnConn
@RequestMapping("/dealerSwitch")
@ResponseBody
/**
 * 经销商权限切换
 * @author Administrator
 *
 */
public class DealerSwitchController extends BaseController{
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DealerSwitchService   dealerSwitchService;

	
	
	
	@RequestMapping(value = "/dealerSwitchQuery", method = RequestMethod.GET)
	// 查询所有经销商
	public  PageInfoDto  dealerSwitchQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("==============查询所有经销商信息=============");
		PageInfoDto personInfoDto = dealerSwitchService.dealerSwitchQuery(queryParam);
		return personInfoDto;
	}
	

}
