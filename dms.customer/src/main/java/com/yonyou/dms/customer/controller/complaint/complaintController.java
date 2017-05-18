package com.yonyou.dms.customer.controller.complaint;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.customer.InsuranceExpireRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.SuggestComplaintDTO;
import com.yonyou.dms.customer.service.complaint.customerComplaintService;
import com.yonyou.dms.customer.service.insurance.insuranceCustomerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/customer/complaint")
public class complaintController extends BaseController{

	@Autowired
	private customerComplaintService customerComplaintService;

	/**
	 * 
	 * 新增客户投诉
	 * 
	 * @param vin
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/btnSave", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SuggestComplaintDTO> addComplaint(@RequestBody SuggestComplaintDTO suggestComplaintDTO,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		customerComplaintService.addComplaint(suggestComplaintDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/customer/complaint/btnSave").buildAndExpand().toUriString());
		return new ResponseEntity<SuggestComplaintDTO>(suggestComplaintDTO, headers, HttpStatus.CREATED);
	}
	

	/**
	 * 查询部门
	 * @param queryParam
	 * @return
	 * @throws SQLException
	 * @throws ServiceBizException
	 */

	@RequestMapping(value="/search/organization",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStreamAnalysis(@RequestParam Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		return customerComplaintService.queryOrganization(queryParam);
	}
	
	/**
	 * 查询单号
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 */
	@RequestMapping(value="/repair",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchRepairOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = customerComplaintService.searchRepairOrder(queryParam);
		return pageInfoDto;
	}
	
}
