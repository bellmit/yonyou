
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : SettleCollectionController.java
 *
 * @Author : xukl
 *
 * @Date : 2016年9月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月5日    xukl    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.controller.ordermanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.CustomerGatheringDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.GathringMaintainDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SettleCollectionWriteoffTagDTO;
import com.yonyou.dms.retail.service.ordermanage.SettleCollectionService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 结算收款Controller
 * 
 * @author xukl
 * @date 2016年9月5日
 */
@Controller
@TxnConn
@RequestMapping("/ordermanage/settleOrders")
public class SettleCollectionController extends BaseController {
	@Autowired
	private SettleCollectionService settleCollectionService;
	@Autowired
	private CommonNoService commonNoService;

	/**
	 * 查询收款记录根据销售单
	 * 
	 * @author gm
	 * @date 2016年9月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto qrySalesOrders(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = settleCollectionService.qryCustomerGathering(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询收款记录根据收款记录id
	 * 
	 * @author xukl
	 * @date 2016年9月8日
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}/settleCollection", method = RequestMethod.GET)
	@ResponseBody
	public Map qryCustomerGathering(@PathVariable("id") Long id) {
		return settleCollectionService.qryCustGathering(id);
	}

	/**
	 * 查询收款记录根据收款记录id
	 * 
	 * @author xukl
	 * @date 2016年9月8日
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{soNo}/but/{wsNo}/sales/{cusNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map qryCustomerAndSales(@PathVariable("soNo") String soNo, @PathVariable("wsNo") String wsNo,
			@PathVariable("cusNo") String cusNo) {
		Map pp = new HashMap();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Map> addressList = null;
		if(!"null".equals(wsNo)){
			addressList = settleCollectionService.qryCustomer(wsNo, dealerCode);			
		}else{
			addressList = settleCollectionService.qryCustomer2(cusNo, dealerCode);	
		}
		if (addressList.size() > 0) {
			pp.putAll(addressList.get(0));
		}
		List<Map> ownerlist = settleCollectionService.qrySales(soNo, dealerCode);
		if (ownerlist.size() > 0) {
			pp.putAll(ownerlist.get(0));
		}
		return pp;
	}

	@RequestMapping(value = "/{cusNo}/sum", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto qrySalesOrdersGa(@RequestParam Map<String, String> queryParam,
			@PathVariable("cusNo") String cusNo) {
		PageInfoDto pageInfoDto = settleCollectionService.qryCustomerGa(queryParam, cusNo);
		return pageInfoDto;
	}

	// 服务订单储值
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{vin}/vin/{license}/license", method = RequestMethod.GET)
	@ResponseBody
	public Map qrySupple(@PathVariable("vin") String vin, @PathVariable("license") String license) {
		Map pp = new HashMap();
		// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Map> addressList = settleCollectionService.qrySupple(vin, license);
		if (addressList.size() > 0) {
			pp.putAll(addressList.get(0));
		}

		return pp;
	}

	/**
	 * 潜在客户查询
	 * 
	 * @author zhanshiwei
	 * @date 2016年9月1日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "/{soNo}/AND/{receiveNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryreceiveNo(@PathVariable(value = "soNo") String soNo,
			@PathVariable(value = "receiveNo") String receiveNo) {
		List<Map> result = settleCollectionService.queryreceiveNo(soNo, receiveNo);

		return result.get(0);
	}

	/* 判断是否有退回单 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{soNo}/return", method = RequestMethod.GET)
	@ResponseBody
	public Map qryReturnSales(@PathVariable("soNo") String soNo) {
		Map pp = new HashMap();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		List<Map> addressList = settleCollectionService.qryReturn(soNo, dealerCode);
		return null;
	}

	/**
	 * 新增收款记录
	 * 
	 * @author xukl
	 * @date 2016年10月9日
	 * @param salesOrderDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/settleCollection", method = RequestMethod.POST)
	public ResponseEntity<CustomerGatheringDTO> addSellBack(
			@RequestBody @Valid CustomerGatheringDTO customerGatheringDTO, UriComponentsBuilder uriCB) {
		Long id = settleCollectionService.addCustomerGathering(customerGatheringDTO,
				commonNoService.getSystemOrderNo(CommonConstants.DERATE_NO));
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/ordermanage/settleOrders/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<CustomerGatheringDTO>(customerGatheringDTO, headers, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/maintian", method = RequestMethod.POST)
	public ResponseEntity<Map> addSellBackMaintain(@RequestBody @Valid GathringMaintainDTO gathringmaintaindto,
			UriComponentsBuilder uriCB) {
		System.out.println("_____________________________________________________1");
		if (gathringmaintaindto.getWriteoffTag() == 12781001) {
			System.out.println("_____________________________________________________2");

		}
		List<Map> id = settleCollectionService.addCustomerGatheringMain(gathringmaintaindto);

		if (gathringmaintaindto.getWriteoffTag() == 12781001) {
			System.out.println("_____________________________________________________2");

		}
		/*
		 * String idd = settleCollectionService.addCustomerGatheringWriteoffTag(
		 * gathringmaintaindto);
		 */
		Map pp = new HashMap();
		if (id.size() > 0) {
			pp.putAll(id.get(0));
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		// headers.set("Location",
		// uriCB.path("/ordermanage/settleOrders/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<Map>(pp, headers, HttpStatus.CREATED);

	}

	/**
	 * 收款编辑
	 * 
	 * @author xukl
	 * @date 2016年10月11日
	 * @param id
	 * @param salesOrderDTO
	 * @param uriCB
	 * @return
	 */

	@RequestMapping(value = "/{id}/settleCollection", method = RequestMethod.PUT)
	public ResponseEntity<CustomerGatheringDTO> modifySellBack(@PathVariable("id") Long id,
			@RequestBody @Valid CustomerGatheringDTO customerGatheringDTO, UriComponentsBuilder uriCB) {
		settleCollectionService.editCustomerGathering(id, customerGatheringDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/ordermanage/settleOrders/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<CustomerGatheringDTO>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/writeoffTag", method = RequestMethod.PUT)
	public ResponseEntity<GathringMaintainDTO> addSellBackWriteoffTag(
			@RequestBody @Valid GathringMaintainDTO SettleCollectionWriteoffTagdto, UriComponentsBuilder uriCB) {
		String id = settleCollectionService.addCustomerGatheringWriteoffTag(SettleCollectionWriteoffTagdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		// headers.set("Location",
		// uriCB.path("/ordermanage/settleOrders/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<GathringMaintainDTO>(SettleCollectionWriteoffTagdto, headers, HttpStatus.CREATED);

	}

	/**
	 * 查询收款记录根据销售单
	 * 
	 * @author wantao
	 * @date 2017年5月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/allVehiclePayManage",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAllVehiclePayManage(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = settleCollectionService.queryAllVehiclePayManage(queryParam);
		return pageInfoDto;
	}
}
