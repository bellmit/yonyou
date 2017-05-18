/**
 * 
 */
package com.yonyou.dms.customer.controller.checkCarAlarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.dms.customer.service.checkCarAlarm.CheckCarAlarmService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author sqh
 *
 */
@Controller
@TxnConn
@RequestMapping("/customer/checkCarAlarm")
public class CheckCarAlarmController extends BaseController{

	@Autowired
	private CheckCarAlarmService checkCarAlarmService;
	
	
}
