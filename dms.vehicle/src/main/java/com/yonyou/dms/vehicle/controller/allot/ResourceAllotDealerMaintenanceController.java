package com.yonyou.dms.vehicle.controller.allot;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditAllotDerDto;
import com.yonyou.dms.vehicle.service.allot.ResourceAllotDealerMaintenanceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/allotDealerMaintenance")
public class ResourceAllotDealerMaintenanceController {
	@Autowired
	ResourceAllotDealerMaintenanceService dealerMaintenanceService;
	// @Autowired
	// private ExcelGenerator excelService;

	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotDealerMaintenanceController.class);

	// 资源分配港口维护修改
	@RequestMapping(value = "/editAllotDealer", method = RequestMethod.PUT)
	public ResponseEntity<EditAllotDerDto> editAllotDealer(@RequestBody @Valid EditAllotDerDto Dto) {
		logger.info("============资源分配港口维护修改===============");
		dealerMaintenanceService.editAllotDealer(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);

	}

	/**
	 * 资源分配港口维护修改查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") Long id) {
		logger.info("=============资源分配港口维护修改查询1111==============");
		Map map = dealerMaintenanceService.findByid(id);
		return map;

	}

	/**
	 * 资源分配经销商维护查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/allotDealerMaintenanceSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAllotDealerMaintenance(@RequestParam Map<String, String> queryParam) {
		logger.info("==============资源分配经销商维护查询=============");
		PageInfoDto pageInfoDto = dealerMaintenanceService.allotDealerMaintenanceQuery(queryParam);
		return pageInfoDto;
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
