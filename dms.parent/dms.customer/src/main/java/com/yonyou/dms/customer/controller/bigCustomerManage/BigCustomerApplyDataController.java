package com.yonyou.dms.customer.controller.bigCustomerManage;

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

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerApplyDataPO;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerApplyDataDTO;
import com.yonyou.dms.customer.service.bigCustomerManage.BigCustomerApplyDataService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/bigCustomerApplyDate")
@ResponseBody
/**
 * 政策申请数据定义
 * 
 * @author Administrator
 *
 */
public class BigCustomerApplyDataController extends BaseController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	BigCustomerApplyDataService applyDateService;

	/**
	 * 申请数据查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/applyDateSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryApplyDate(@RequestParam Map<String, String> queryParam) {
		logger.info("==============政策申请数据查询=============");
		PageInfoDto pageInfoDto = applyDateService.applyDateQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 通过政策类别查询客户类型
	 */
	@TxnConn
	@RequestMapping(value = "/{type}/searchEmpType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> applyDateService(@PathVariable String type, @RequestParam Map<String, String> queryParams) {
		logger.info("=====通过政策类别查询客户类型=====");
		List<Map> tenantMapping = applyDateService.getEmpType(type, queryParams);
		return tenantMapping;
	}

	/**
	 * 根据id 删除申请数据
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteApplyData(@PathVariable("id") Long id, UriComponentsBuilder uriCB,
			TtBigCustomerApplyDataDTO ptdto) {
		logger.info("=====根据id 删除申请数据=====");
		applyDateService.deleteApplyDataById(id, ptdto);
	}

	/**
	 * 通过id查询回显申请数据
	 * 
	 */
	@RequestMapping(value = "/getApplyData/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getApplyDataById(@PathVariable(value = "id") Long id) {
		logger.info("=====通过id查询回显申请数据=====");
		TtBigCustomerApplyDataPO ptPo = applyDateService.getApplyDataById(id);
		return ptPo.toMap();
	}

	/**
	 * 通过id修改申请数据信息
	 * 
	 * @param id
	 * @param ptdto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerApplyDataDTO> ModifyApplyData(@PathVariable(value = "id") Long id,
			@RequestBody TtBigCustomerApplyDataDTO ptdto, UriComponentsBuilder uriCB) {
		logger.info("=====通过id修改申请数据信息=====");
		applyDateService.modifyApplyData(id, ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}

	/**
	 * 新增申请数据信息
	 * 
	 * @param ptdto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addApplyData", method = RequestMethod.POST)
	public ResponseEntity<TtBigCustomerApplyDataDTO> addApplyData(@RequestBody TtBigCustomerApplyDataDTO ptdto,
			UriComponentsBuilder uriCB) {
		Long id = applyDateService.addApplyData(ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/bigCustomerApplyDate/applyDateSearch").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}

}
