package com.yonyou.dms.repair.controller.basedata;

import java.util.List;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.BalanceDTO;
import com.yonyou.dms.repair.service.basedata.FreeSettlementService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/freeSettlement")
@SuppressWarnings("rawtypes")
public class FreeSettlementController extends BaseController {

	@Autowired
	private FreeSettlementService freeSettlementService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchRepairOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = freeSettlementService.searchRepairOrder(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/queryRoLabourByRoNO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRoLabourByRoNO(@PathVariable(value = "id") String id) {
		List<Map> list = freeSettlementService.queryRoLabourByRoNO(id);
		return list;
	}

	@RequestMapping(value = "/queryRoRepairPartByRoNO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRoRepairPartByRoNO(@PathVariable(value = "id") String id) {
		List<Map> list = freeSettlementService.queryRoRepairPartByRoNO(id);
		return list;
	}

	@RequestMapping(value = "/queryRoAddItemByRoNO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRoAddItemByRoNO(@PathVariable(value = "id") String id) {
		List<Map> list = freeSettlementService.queryRoAddItemByRoNO(id);
		return list;
	}

	@RequestMapping(value = "/queryForSettlement", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryForSettlement(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = freeSettlementService.queryForSettlement(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value = "/querySalesPartList/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySalesPartList(@PathVariable(value = "id") String id) {
		List<Map> list = freeSettlementService.querySalesPartList(id);
		return list;
	}
	
	@RequestMapping(value = "/queryOwnerAndCustomer", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOwnerAndCustomer(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = freeSettlementService.queryOwnerAndCustomer(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value = "/queryOtherCost", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryOtherCost() throws ServiceBizException {
		List<Map> list = freeSettlementService.queryOtherCost();
		return list;
	}
	
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BalanceDTO> addBalanceAccounts(@RequestBody BalanceDTO balanceDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        freeSettlementService.addBalanceAccounts(balanceDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/freeSettlement").buildAndExpand().toUriString());  
        return new ResponseEntity<BalanceDTO>(balanceDTO,headers, HttpStatus.CREATED);  
    }
	
}
