package com.yonyou.dms.vehicle.controller.orgright;

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
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.service.orgright.SysFunctionListService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 系统功能清单查询Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/sysFunctionListQuery")
public class SysFunctionListQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private SysFunctionListService service;
	
	/**
	 * 系统功能清单查询
	 * @author DC
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============系统功能清单查询===============");
		/**
		 * 当前登录信息
		 */
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.queryList(queryParam,loginInfo);
		return pageInfoDto;
	}
	

}
