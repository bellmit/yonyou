package com.yonyou.dms.vehicle.controller.allot;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto2;
import com.yonyou.dms.vehicle.service.allot.ResourceAllotDealerMaintenanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/orderRepeal")
public class OrderRepealController {
	@Autowired
	ResourceAllotDealerMaintenanceService dealerMaintenanceService;
	private static final Logger logger = LoggerFactory.getLogger(OrderRepealController.class);

	/**
	 * 资源分配--大区总维护新增初始化查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/queryAddInt", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAddInt() {
		logger.info("==============大区总维护新增初始化查询=============");
		List<Map> list = dealerMaintenanceService.queryAddInt();
		return list;
	}

	/**
	 * 大区维护之大区新增
	 * 
	 */

	@RequestMapping(value = "/addRegion", method = RequestMethod.POST)
	public ResponseEntity<EditOrgDto2> addRegion(@RequestBody @Valid EditOrgDto2 pyDto, UriComponentsBuilder uriCB) {
		logger.info("============大区维护之大区新增===============");

		dealerMaintenanceService.addRegion(pyDto);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	// 大区总维护删除
	@RequestMapping(value = "/delOrderRepeal/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<EditOrgDto> delOrderRepeal(@PathVariable(value = "id") Long id) {
		logger.info("=============大区总维护删除==============");
		dealerMaintenanceService.delOrderRepeal(id);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// 大区维护之大区修改
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<EditOrgDto> editPayRemind(@RequestBody @Valid EditOrgDto Dto) {
		logger.info("============大区维护之大区修改===============");
		dealerMaintenanceService.editOrg(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);

	}

	// 回显修改页面信息
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") Long id) {
		logger.info("=============回显修改页面信息==============");
		Map map = dealerMaintenanceService.findById(id);
		return map;

	}

	// 回显修改页面信息
	@RequestMapping(value = "/queryAll/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAllById(@PathVariable(value = "id") Long id) {
		logger.info("============大区总维护查询queryAllById===============");
		List<Map> map = dealerMaintenanceService.queryAllById(id);
		return map;
	}

	/**
	 * 资源分配--大区总维护下拉框查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/totalOrg", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryTotalOrg(@RequestParam Map<String, String> queryParam) {
		logger.info("==============大区总维护下拉框查询=============");
		List<Map> list = dealerMaintenanceService.queryTotalOrg(queryParam);
		return list;
	}

	/**
	 * 资源分配--大区总维护下拉框查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/orgName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryOrgName(@RequestParam Map<String, String> queryParam) {
		logger.info("==============大区总维护下拉框查询=============");
		List<Map> list = dealerMaintenanceService.queryOrgName(queryParam);
		return list;
	}

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryAll(@RequestParam Map<String, String> queryParam) {
		logger.info("============大区总维护查询===============");
		List<Map> map = dealerMaintenanceService.queryAll(queryParam);
		return map;
	}

	/**
	 * 查询大区总 弹出框
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/queryTcUserName", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryTcUserName(@RequestParam Map<String, String> queryParam) {
		logger.info("==============查询大区总监 弹出框=============");
		List<Map> list = dealerMaintenanceService.queryTcUserName(queryParam);
		return list;
	}

	/**
	 * 资源分配经销商维护下载
	 */

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============资源分配经销商维护下载===============");
		dealerMaintenanceService.download(queryParam, response, request);
	}

}
