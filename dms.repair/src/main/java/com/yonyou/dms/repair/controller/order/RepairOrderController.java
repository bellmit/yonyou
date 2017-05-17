
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairOrderController.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月11日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月11日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.order;

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

import com.yonyou.dms.common.domains.DTO.basedata.RepairOrderDetailsDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.basedata.RepairGroupService;
import com.yonyou.dms.repair.service.order.RepairOrderService;
import com.yonyou.dms.repair.service.order.SaveRepairOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 工单信息
 * 
 * @author ZhengHe
 * @date 2016年8月11日
 */

@Controller
@TxnConn
@RequestMapping("/order/repair")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RepairOrderController extends BaseController {

	@Autowired
	private RepairOrderService orderService;

	@Autowired
	private RepairGroupService repairGroupService;
	
	@Autowired
	private SaveRepairOrderService saveRepairOrderService;

	/**
	 * 维修组合-记录缺料明细
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/recordInDetail", method = RequestMethod.GET)
	@ResponseBody
	public String recordInDetail(@RequestParam Map param) {
		List<Map> repairPart = repairGroupService.findRepairPart(param);// 根据配件代码查询维修配件相关信息
		param.put("RO_NO", param.get("roNo"));
		return orderService.recordInDetail(repairPart, param);
	}

	/**
	 * 主页面-日平均行驶里程相关查询
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryVehicleforactivity", method = RequestMethod.GET)
	@ResponseBody
	public Map queryVehicleforactivity(@RequestParam Map param) {// QUERY_VEHICLEFORACTIVITY
		return orderService.queryVehicleforactivity(param);
	}

	/**
	 * 查询特定vin的会员卡是否存在
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryMemberCardExist", method = RequestMethod.GET)
	@ResponseBody
	public Map queryMemberCardExist(@RequestParam Map param) {
		return orderService.queryMemberCardExist(param);
	}
	
	/**
	 * 查询非4S站的车辆,在库存存在并且未开票的车
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/checkVehicleInvoice", method = RequestMethod.GET)
	@ResponseBody
	public Map checkVehicleInvoice(@RequestParam Map param) {
		return orderService.checkVehicleInvoice(param);
	}
	
	/**
	 * 根据工单号，车牌号查询工单信息
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/queryRepairOrderExists", method = RequestMethod.GET)
	@ResponseBody
	public Map queryRepairOrderExists(@RequestParam Map param) {
		return orderService.queryRepairOrderExists(param);
	}
	
//	/**
//	 * 查询车辆方案状态为”等待审核“的工单
//	 * 
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping(value = "/checkIsHaveAduitingOrder", method = RequestMethod.GET)
//	@ResponseBody
//	public Map checkIsHaveAduitingOrder(@RequestParam Map param) {
//		return orderService.checkIsHaveAduitingOrder(param);
//	}
//	
	/**
	 * 主页面-保养配件信息相关查询
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/getIsMaintenance", method = RequestMethod.GET)
	@ResponseBody
	public String getIsMaintenance(@RequestParam Map param) {// GET_IS_MAINTENANCE
		return orderService.getIsMaintenance(param);
	}

	/**
	 * 工单新增配件根据条件查询
	 * 
	 * @author zhengcong
	 * @date 2017年4月24日
	 */
	@RequestMapping(value = "/addPart/queryPart", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto newQueryPart(@RequestParam Map<String, String> qParam) {

		PageInfoDto pData = orderService.newQueryPart(qParam);
		return pData;

	}

	/**
	 * 工单新增配件根据条件查询出配件后，双击配件，带出配件库存信息
	 * 
	 * @author zhengcong
	 * @date 2017年4月25日
	 */
	@RequestMapping(value = "/addPart/queryPartStock/{PART_NO}/{STORAGE_CODE}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto newQueryPartStock(@PathVariable(value = "PART_NO") String PART_NO,
			@PathVariable(value = "STORAGE_CODE") String STORAGE_CODE) {
		PageInfoDto pageInfoDto = orderService.newQueryPartStock(PART_NO, STORAGE_CODE);
		return pageInfoDto;
	}

	/**
	 * 查询替代配件
	 * 
	 * @author zhengcong
	 * @date 2017年4月26日
	 */
	@RequestMapping(value = "/addPart/queryInsteadPart/{OPTION_NO}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInsteadPart(@PathVariable(value = "OPTION_NO") String OPTION_NO) {
		PageInfoDto pageInfoDto = orderService.queryInsteadPart(OPTION_NO);
		return pageInfoDto;
	}

	/**
	 * 技师选择
	 * 
	 * @author Zhl
	 * @date 2017年4月06日
	 */
	@RequestMapping(value = "/showRoAssign/selectTech", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto selectEmployee(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = orderService.selectEmployee(queryParam);
		return pageInfoDto;
	}

	/**
	 * 
	 * 提供给下拉框的方法(过滤valid) 员工
	 * 
	 * @author zhl
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/querytechnician", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querytechnician(@RequestParam Map<String, String> queryParam) {
		List<Map> list = orderService.querytechnician(queryParam);
		return list;
	}

	/**
	 * 
	 * 派工工位工位关联
	 * 
	 * @author zhl
	 * @date 2017年4月25日
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/queryrepairPosition/{[technicianname]}/item", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryrepairPosition(@PathVariable String techniciancode,
			@RequestParam Map<String, String> queryParam) {
		queryParam.put("code", techniciancode);
		List<Map> list = orderService.queryrepairPosition(queryParam);
		return list;
	}

	/**
	 * 
	 * 派工工位工位关联
	 * 
	 * @author zhl
	 * @date 2017年4月25日
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/querytechnicianWorkType/item", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querytechnicianWorkType() {
		return orderService.querytechnicianWorkType();
	}

	@RequestMapping(value = "/queryrepairPosition/{techniciancode}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Map> findById(@PathVariable String techniciancode, UriComponentsBuilder uriCB) {
		Map map = orderService.findtechnicianItem(techniciancode);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/querytechnician").buildAndExpand().toUriString());
		return new ResponseEntity<Map>(map, headers, HttpStatus.CREATED);
	}

	/**
	 * 查询订单明细
	 * @param roNo
	 * @return
	 */
	@RequestMapping(value = "/findOrderDetails", method = RequestMethod.GET)
	@ResponseBody
	public Map findOrderDetails(@RequestParam String roNo) {
		return orderService.findOrderDetails(roNo);
	}
	
	/**
	 * 保存按钮
	 * @author yangjie
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/btnSave", method=RequestMethod.POST)
	public ResponseEntity<String> btnSave(@RequestBody RepairOrderDetailsDTO dto, UriComponentsBuilder uriCB){
		System.err.println("进入保存方法---------------------------------------------");
		saveRepairOrderService.btnSave(dto);
		System.err.println("退出保存方法---------------------------------------------");
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/recordInDetail").buildAndExpand().toUriString());
		return new ResponseEntity<String>("", headers, HttpStatus.CREATED);
	}

	/**
	 * 工单保存获取可参加的所有服务活动信息
	 * @author yangjie
	 * @param query
	 * @return
	 */
	@RequestMapping(value="/getAllEnterableActivityInfo", method=RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllEnterableActivityInfo(@RequestParam Map<String, String> query){
		return saveRepairOrderService.getAllEnterableActivityInfo(query);
	}
	
	@RequestMapping(value="/queryVinByVin", method=RequestMethod.GET)
	@ResponseBody
	public List<Map> queryVinByVin(@RequestParam Map<String, String> query){
		return orderService.queryVinByVin(query);
	}
	
	/**
	 * 三包授权操作
	 * @author yangjie
	 * @param param
	 */
	@RequestMapping(value="/check3BaoAccredit",method=RequestMethod.POST)
	public void btn3BaoAccredit(@RequestBody Map<String, String> param){
		saveRepairOrderService.btn3BaoAccredit(param);
	}
	
	/**
	 * 工单保存时的三包预警判断
	 * @author yangjie
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getTripleInfo",method=RequestMethod.GET)
	public Map getTripleInfo(Map<String, String> param){
		return saveRepairOrderService.getTripleInfo(param);
	}
	
	/**
	 * 出险信息登记增、删、改
	 * @author yangjie
	 * @param param
	 */
	@RequestMapping(value="/occurInsuranceAbout",method = RequestMethod.GET)
	public void occurInsuranceAbout(Map<String, String> param){
		saveRepairOrderService.occurInsuranceAbout(param);
	}
	
	/**
	 * 保存工单时存入赔付、旧件处理信息
	 * @author yangjie
	 * @param param
	 */
	@RequestMapping(value="/saveSettlementOldpart",method = RequestMethod.GET)
	public void saveSettlementOldpart(Map<String, String> param){
		saveRepairOrderService.saveSettlementOldpart(param);
	}
	
	
  	/**
   	 * 	业务描述：查询参于活动的记录
   	 
   	 * @author Zhl
   	 * @throws Exception 
   	 * @date 2017年5月04日
   	 */
   	@RequestMapping(value = "/queryActivityResult", method = RequestMethod.GET)
   	@ResponseBody
   	public PageInfoDto queryActivityResult(@RequestParam Map<String, String> queryParam) throws Exception {
   		PageInfoDto pageInfoDto = orderService.queryActivityResult(queryParam);
   		return pageInfoDto;
   	}
   	
   	
  	/**
   	 * 	业务描述：查询出险信息
   	 
   	 * @author Zhl
   	 * @throws Exception 
   	 * @date 2017年5月04日
   	 */
   	@RequestMapping(value = "/queryOccurInsuranceByLicenseOrVin", method = RequestMethod.GET)
   	@ResponseBody
   	public PageInfoDto QueryOccurInsuranceByLicenseOrVin(@RequestParam Map<String, String> queryParam) throws Exception {
   		PageInfoDto pageInfoDto = orderService.QueryOccurInsuranceByLicenseOrVin(queryParam);
   		return pageInfoDto;
   	}
   	/**
   	 * 	业务描述：查询预约单
   	 
   	 * @author Zhl
   	 * @throws Exception 
   	 * @date 2017年4月06日
   	 */
   	@RequestMapping(value = "/queryBookingOrder", method = RequestMethod.GET)
   	@ResponseBody
   	public PageInfoDto queryBookingOrder(@RequestParam Map<String, String> queryParam) throws Exception {
   		PageInfoDto pageInfoDto = orderService.queryBookingOrder(queryParam);
   		return pageInfoDto;
   	}
   	
 	/**
   	 * 	 监控车主车辆
   	 
   	 * @author Zhl
   	 * @throws Exception 
   	 * @date 2017年4月06日
   	 */
  	@RequestMapping(value = "/retriveByControl", method = RequestMethod.GET)
   	@ResponseBody
   	public List<Map>  retriveByControl(@RequestParam Map<String, String> queryParam) throws Exception {
  		List<Map>  pageInfoDto = orderService.retriveByControl(queryParam);
   		return pageInfoDto;
   	}
	
	

  	/**
   	 * 	业务描述：查询服务活动工单监控
   	 
   	 * @author Zhl
   	 * @throws Exception 
   	 * @date 2017年5月04日
   	 */
   	@RequestMapping(value = "/queryActivityValid", method = RequestMethod.GET)
   	@ResponseBody
   	public List<Map> queryActivityValid(@RequestParam Map<String, String> queryParam) throws Exception {
   		List<Map> list = orderService.queryActivityValid(queryParam);
   		return  list;
   	}
   	
}
