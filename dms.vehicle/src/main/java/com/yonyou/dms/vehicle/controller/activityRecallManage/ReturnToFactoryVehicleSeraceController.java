package com.yonyou.dms.vehicle.controller.activityRecallManage;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.activityRecallManage.ReturnToFactoryVehicleSeraceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujm
* @date 2017年4月22日
*/

@Controller
@TxnConn
@RequestMapping("/returnToFactory")
public class ReturnToFactoryVehicleSeraceController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ReturnToFactoryVehicleSeraceController.class);
	
	@Autowired
	private ReturnToFactoryVehicleSeraceService retoService;
	
	
	
	/**
	 * 返厂未召回车辆查询 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto returnToFactoryVehicleSeraceQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============返厂未召回车辆查询查询08==============");
    	PageInfoDto pageInfoDto = retoService.returnToFactoryVehicleSeraceQuery(queryParam);   	
        return pageInfoDto;               
    }
	/**
	 * 返厂未召回车辆 下载
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void returnToFactoryVehicleSeraceDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============返厂未召回车辆查询 下载08===============");
    	retoService.returnToFactoryVehicleSeraceDownload(queryParam, request, response);
	}

}
