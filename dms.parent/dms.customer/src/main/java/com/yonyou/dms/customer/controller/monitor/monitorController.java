package com.yonyou.dms.customer.controller.monitor;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.DTO.customer.MontiorVehicleDTO;
import com.yonyou.dms.customer.service.monitor.monitorService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/customer/monitor")
public class monitorController extends BaseController {
	@Autowired
	private monitorService monitorService;
	@Autowired
	private ExcelGenerator excelGenerator;

	/**
	 * 查询车辆车主监控信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return monitorService.queryMonitorInfo(queryParam);
	}
	
	/**
	 * 
	 * 新增车辆车主监控信息
	 * 
	 * @param vin
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addMoitor", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MontiorVehicleDTO> addComplaint(@RequestBody MontiorVehicleDTO montiorVehicleDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		monitorService.addComplaint(montiorVehicleDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/monitor/addMoitor").buildAndExpand().toUriString());
		return new ResponseEntity<MontiorVehicleDTO>(montiorVehicleDTO, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * 修改车辆车主监控信息
	 * 
	 * @param id
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{monitorId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<MontiorVehicleDTO> updateInsurance(@PathVariable (value = "monitorId") String monitorId,
			@RequestBody MontiorVehicleDTO montiorVehicleDTO, UriComponentsBuilder uriCB) throws ServiceBizException {
		monitorService.updateComplaint(monitorId, montiorVehicleDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/monitor/{monitorId}").buildAndExpand(monitorId).toUriString());
		return new ResponseEntity<MontiorVehicleDTO>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 查询车辆车主监控历史信息
	 * 
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value="/historyMonitor",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryhistoryMonitor(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return monitorService.queryMonitorHistory(queryParam);
	}
	
	/**
	 * 根据ID删除车辆车主监控历史信息
	 * 
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */

	@RequestMapping(value = "/{monitorId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePlan(@PathVariable(value = "monitorId") String monitorId, UriComponentsBuilder uriCB) throws ServiceBizException {
		monitorService.deletePlanById(monitorId);
	}
}
