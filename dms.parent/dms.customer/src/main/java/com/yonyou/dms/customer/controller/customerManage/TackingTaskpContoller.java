package com.yonyou.dms.customer.controller.customerManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.customer.service.customerManage.TrackingTaskService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customerManage/trackingtaskp")
public class TackingTaskpContoller extends BaseController{
	
	@Autowired
    private TrackingTaskService trackingtaskservice;
	
	 /**
	    * 跟进任务定义 查询保客
	    * @author zhanshiwei
	    * @date 2016年9月7日
	    * @param queryParam
	    * @return
	    */
	    	
	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryTrackingTaskp(@RequestParam Map<String, String> queryParam) {
	        PageInfoDto pageInfoDto = trackingtaskservice.queryTrackingTaskp(queryParam);
	        return pageInfoDto;
	    }
}
