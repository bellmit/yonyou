package com.yonyou.dms.vehicle.controller.afterSales.workWeek;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmHolidayPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmHolidayDTO;
import com.yonyou.dms.vehicle.service.afterSales.workWeek.HolidayManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 假期维护
 * 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/holidayManage")
public class HolidayManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	HolidayManageService holidayManageService;

	/**
	 * 假期维护查询
	 */
	@RequestMapping(value = "/holidayManageSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querybasicCode(@RequestParam Map<String, String> queryParam) {
		logger.info("==============假期维护查询=============");
		PageInfoDto pageInfoDto = holidayManageService.holidayManageQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询得到所有年份信息
	 */
	@RequestMapping(value = "/yearSearch", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYear() {
		logger.info("==============查询得到所有年份信息=============");
		List<Map> getYears = holidayManageService.getYear();
		return getYears;
	}

	/**
	 * 新增操作年份初始化查询
	 */
	@RequestMapping(value = "/yearInitSearch", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearInit() {
		logger.info("==============新增操作年份初始化查询=============");
		List<Map> list = holidayManageService.getYearInit();
		return list;
	}

	/**
	 * 新增假期维护
	 */
	@RequestMapping(value = "/addHolidayManage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TmHolidayDTO> addDealerPayment(@RequestBody TmHolidayDTO ptdto, UriComponentsBuilder uriCB) {
		logger.info("=====   新增假期维护=====");
		Long id = holidayManageService.addHolidayManage(ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/holidayManage/addHolidayManage").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}

	/**
	 * 通过id进行信息回显
	 */
	@RequestMapping(value = "/getHoliday/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getDirectCustomerById(@PathVariable(value = "id") Long id) {
		logger.info("=====假期维护信息回显=====");
		TmHolidayPO ptPo = holidayManageService.getHolidayManageById(id);
		return ptPo.toMap();
	}

	/**
	 * 假期维护修改
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TmHolidayDTO> edit(@PathVariable(value = "id") Long id, @RequestBody TmHolidayDTO dto,
			UriComponentsBuilder uriCB) {
		logger.info("===== 假期维护修改=====");
		holidayManageService.edit(id, dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/holidayManage/edit").buildAndExpand().toUriString());
		return new ResponseEntity<TmHolidayDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 删除假期维护数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDirectCustomer(@PathVariable("id") Long id, UriComponentsBuilder uriCB, TmHolidayDTO ptdto) {
		logger.info("=====删除假期维护数据=====");
		holidayManageService.delete(id);
	}

}
