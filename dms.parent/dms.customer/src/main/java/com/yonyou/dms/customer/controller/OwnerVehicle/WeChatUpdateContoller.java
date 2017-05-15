package com.yonyou.dms.customer.controller.OwnerVehicle;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.OwnerVehicle.WeChatUpdateService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/customer/WeChatUpdate")
public class WeChatUpdateContoller extends BaseController{
	@Autowired
	private WeChatUpdateService weChatUpdateService;
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryVisitingRecordInfo(@RequestParam Map<String, String> queryParam) {
			return weChatUpdateService.queryWeChatInfo(queryParam);
	}
}
