package com.yonyou.dms.vehicle.controller.dlrinvtyManage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.service.dlrinvtyManage.DlrVehicleQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆综合查询(经销商端) Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/dlrVehicleQuery")
public class DlrVehicleQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DlrVehicleQueryService service;
	
	/**
	 * 车辆综合查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemInventoryTotalQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============车辆综合查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.query(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
	 * 车辆综合详细查询
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/dealer/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> oemVehicleDetailed(@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询===============");
		Map<String, Object> map = service.detailQuery(id);
		return map;
	}
	
	/**
	 * 车辆综合查询变更日志
	 * @param queryParam
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/vehicleVinDetailQuery/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto oemVehicleDetailed(@RequestParam Map<String, String> queryParam,
			@PathVariable(value = "id") Long id) {
		logger.info("============车辆综合详细查询--变更日志===============");
		queryParam.put("id", id.toString());
		PageInfoDto pageInfoDto = service.oemVehicleDetailed(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============车辆综合查询下载===============");
		service.findQuerySuccList(queryParam, response, request);
		
	}

}
