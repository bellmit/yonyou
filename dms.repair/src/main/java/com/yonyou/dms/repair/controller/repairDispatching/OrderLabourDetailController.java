
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : OrderLabourDetailController.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月21日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月21日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.controller.repairDispatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.repair.domains.DTO.repairDispatching.OrderDispatchDetailTransFormDTO;
import com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 工单派工分配明细 TODO description
 * 
 * @author rongzoujie
 * @date 2016年9月21日
 */
@Controller
@TxnConn
@RequestMapping("/repairDispatcing/repair")
public class OrderLabourDetailController {

	@Autowired
	private OrderLabourDetailService orderLabourDetailService;

	@Autowired
	private ExcelGenerator excelService;

	/**
	 *
	 * 派工维修工单查询页面
	 * 
	 * @author rongzoujie
	 * @date 2016年10月14日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/queryRepairOrder/dispatch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRepairOrder(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = orderLabourDetailService.dispatchQueryRepairOrder(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询工单维修项目明细
	 * 
	 * @author rongzoujie
	 * @date 2016年9月27日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/{orderWorderId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderLabourDetail(@PathVariable("orderWorderId") Long orderWorderId) {
		PageInfoDto pageInfoDto = orderLabourDetailService.queryRepairProDetail(orderWorderId);
		return pageInfoDto;
	}
	
	/**
	 * 完工弹出窗口查询工单明细
	 * @param orderWorderId
	 * @return
	 */
	@RequestMapping(value = "/finishWork/{orderWorderId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderLabourDetailForFinish(@PathVariable("orderWorderId") Long orderWorderId) {
		PageInfoDto pageInfoDto = orderLabourDetailService.queryRepairProDetailForFinish(orderWorderId);
		return pageInfoDto;
	}

	/**
	 *
	 * 整单派工
	 * 
	 * @author rongzoujie
	 * @date 2016年10月10日
	 * @param oDDTFD
	 */
	@RequestMapping(value = "/allDispatch", method = RequestMethod.POST)
	@ResponseBody
	public void addAllOrderLabourDetail(@RequestBody @Valid OrderDispatchDetailTransFormDTO oDDTFD) {
		orderLabourDetailService.addAllOrderLabourDetail(oDDTFD);
	}

	/**
	 * 添加派工工单明细
	 * 
	 * @author rongzoujie
	 * @date 2016年9月27日x
	 * @param oDDTFD
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void addOrderLabourDetail(@RequestBody OrderDispatchDetailTransFormDTO oDDTFD, UriComponentsBuilder uriCB) {
		orderLabourDetailService.addOrderLabourDetail(oDDTFD);
	}

	/**
	 * 查询工单派工明细
	 * 
	 * @author rongzoujie
	 * @date 2016年9月28日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/{roLabourId}/queryOrderDispatchDetail", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderDispatchDetail(@PathVariable("roLabourId") Long roLabourId) {
		PageInfoDto pageInfoDto = orderLabourDetailService.queryRepairProDispatchDteail(roLabourId);
		return pageInfoDto;
	}

	/**
	 * 设置完工验收
	 * 
	 * @author rongzoujie
	 * @date 2016年10月12日
	 * @param DispatchMap
	 */
	@RequestMapping(value = "/setOrderFinishChecker", method = RequestMethod.PUT)
	@ResponseBody
	public void setOrderFinishChecker(@RequestBody Map DispatchMap) {
		updateFinishAndChecker(DispatchMap, "finishWorkList");
		List<Map> customerHourList = (List<Map>) DispatchMap.get("workTimeList");
		for (int i = 0; i < customerHourList.size(); i++) {
			Long assignId = Long.parseLong(customerHourList.get(i).get("ASSIGN_ID").toString());
			Double customerHour = null;
			if (customerHourList.get(i).get("FACT_LABOUR_HOUR") != null) {
				customerHour = Double.parseDouble(customerHourList.get(i).get("FACT_LABOUR_HOUR").toString());
			}
			orderLabourDetailService.setCustomerHour(assignId, customerHour);
		}

		List<Map> finishHourList = (List<Map>) DispatchMap.get("finishWorkList");
		if (finishHourList.size() > 0) {
			for (int i = 0; i < finishHourList.size(); i++) {
				Long assignId = Long.parseLong(finishHourList.get(i).get("ASSIGN_ID").toString());
				String finishHour = finishHourList.get(0).get("ITEM_END_TIME").toString();
				orderLabourDetailService.setFinishHour(assignId, finishHour);
			}
		}
	}

	/**
	 * 派工明细单完工标志更新 TODO description
	 * 
	 * @author rongzoujie
	 * @date 2016年10月3日
	 * @param DispatchMap
	 */
	@RequestMapping(value = "/setOrderDispatchDetail", method = RequestMethod.PUT)
	@ResponseBody
	public void setOrderDispatchDetail(@RequestBody Map DispatchMap) {
		// //获取前端派工明细表格中的assignId
		// List<Map> listDispatchDetailMap =
		// (List<Map>)DispatchMap.get("repairOrderDispatchTable");
		// //获取前端派工明细的RO_LABOUR_ID
		// Long roLabourId =
		// Long.parseLong(DispatchMap.get("RO_LABOUR_ID").toString());
		// //获取完成派工明细的assign_id
		// List<String> finishDispatchDetailId = new ArrayList<String>();
		// //获取未完成派工明细assign_id
		// List<String> unFinishDispatchDetailId = new ArrayList<String>();
		// for(int i=0;i<listDispatchDetailMap.size();i++){
		// if(listDispatchDetailMap.get(i).get("FINISHED_TAG") != null){
		// finishDispatchDetailId.add(listDispatchDetailMap.get(i).get("ASSIGN_ID").toString());
		// }else{
		// unFinishDispatchDetailId.add(listDispatchDetailMap.get(i).get("ASSIGN_ID").toString());
		// }
		// }
		// String finishDispatchDetailIdsStr = null;
		// String unFinishDispatchDetailIdsStr = null;
		// if(finishDispatchDetailId.size()>0){
		// String[] finishListDispatchDetailIds = (String[])
		// finishDispatchDetailId.toArray(new
		// String[finishDispatchDetailId.size()]);
		// //格式化成字符串的形势
		// finishDispatchDetailIdsStr =
		// StringUtils.join(finishListDispatchDetailIds, "-");
		// }else{
		// String[] unFinishListDispatchDetailIds = (String[])
		// unFinishDispatchDetailId.toArray(new
		// String[unFinishDispatchDetailId.size()]);
		// //格式化成字符串的形势
		// unFinishDispatchDetailIdsStr =
		// StringUtils.join(unFinishListDispatchDetailIds, "-");
		// }
		// String checker = null;
		// if(!com.yonyou.dms.function.utils.common.StringUtils.isNullOrEmpty(DispatchMap.get("CHECKER"))){
		// checker = DispatchMap.get("CHECKER").toString();
		// }
		// orderLabourDetailService.setFinishDisPatch(finishDispatchDetailIdsStr,unFinishDispatchDetailIdsStr,checker);
		updateFinishAndChecker(DispatchMap, "repairOrderDispatchTable");
	}

	public void updateFinishAndChecker(Map tableMap, String getStr) {
		// 获取前端派工明细表格中的assignId
		List<Map> listMap = (List<Map>) tableMap.get(getStr);
		// 获取前端派工明细的RO_LABOUR_ID
		// Long roLabourId =
		// Long.parseLong(tableMap.get("RO_LABOUR_ID").toString());
		// 获取完成派工明细的assign_id
		List<String> finishDispatchDetailId = new ArrayList<String>();
		// 获取未完成派工明细assign_id
		List<String> unFinishDispatchDetailId = new ArrayList<String>();
		for (int i = 0; i < listMap.size(); i++) {
			if (listMap.get(i).get("FINISHED_TAG") != null) {
				finishDispatchDetailId.add(listMap.get(i).get("ASSIGN_ID").toString());
			} else {
				unFinishDispatchDetailId.add(listMap.get(i).get("ASSIGN_ID").toString());
			}
		}
		String finishDispatchDetailIdsStr = null;
		String unFinishDispatchDetailIdsStr = null;
		if (finishDispatchDetailId.size() > 0) {
			String[] finishListDispatchDetailIds = (String[]) finishDispatchDetailId
					.toArray(new String[finishDispatchDetailId.size()]);
			// 格式化成字符串的形势
			finishDispatchDetailIdsStr = StringUtils.join(finishListDispatchDetailIds, "-");
		} else {
			String[] unFinishListDispatchDetailIds = (String[]) unFinishDispatchDetailId
					.toArray(new String[unFinishDispatchDetailId.size()]);
			// 格式化成字符串的形势
			unFinishDispatchDetailIdsStr = StringUtils.join(unFinishListDispatchDetailIds, "-");
		}
		// String checker = null;
		// if(!com.yonyou.dms.function.utils.common.StringUtils.isNullOrEmpty(tableMap.get("CHECKER"))){
		// checker = tableMap.get("CHECKER").toString();
		// }
		orderLabourDetailService.setFinishDisPatch(finishDispatchDetailIdsStr, unFinishDispatchDetailIdsStr);

		for (int i = 0; i < listMap.size(); i++) {
			String checkUser = null;
			if (listMap.get(i).get("CHECKER") != null) {
				checkUser = listMap.get(i).get("CHECKER").toString();
			}
			Object itemEndTime=null;
			if(listMap.get(i).get("ITEM_END_TIME")!=null){
				itemEndTime=listMap.get(i).get("ITEM_END_TIME");
			}
			Long assignId = Long.parseLong(listMap.get(i).get("ASSIGN_ID").toString());
			orderLabourDetailService.setChecker(assignId, checkUser,itemEndTime);
		}
		List<Map> roLabourList=(List<Map>) tableMap.get("repairProForDispatch");
		if(!CommonUtils.isNullOrEmpty(roLabourList)){
			for(int i=0;i<roLabourList.size();i++){
				Long roLabourId=Long.parseLong(roLabourList.get(i).get("RO_LABOUR_ID").toString());
				orderLabourDetailService.setRepairProDetail(roLabourId);
			}
		}

	}

	/**
	 * 删除派工明细 TODO description
	 * 
	 * @author rongzoujie
	 * @date 2016年10月6日
	 * @param assignId
	 */
	@RequestMapping(value = "/{assignId}/deleteDispatchDetail", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDispatchDetail(@PathVariable("assignId") Long assignId) {
		orderLabourDetailService.detleteDispatchDetail(assignId);
	}

	/**
	 * 整单删除 TODO description
	 * 
	 * @author rongzoujie
	 * @date 2016年10月11日
	 * @param roid
	 */
	@RequestMapping(value = "/{roid}/deleteAllDispatchDetail", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllDispatchDetail(@PathVariable("roid") Long roid) {
		orderLabourDetailService.deleteAllDispatchDetail(roid);
	}

	/**
	 * 
	 * TODO description
	 * 
	 * @author rongzoujie
	 * @date 2016年10月11日
	 * @param roid
	 */
	@RequestMapping(value = "/{labourIdStr}/deleteSelectDispatchDetail", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSelectDispatchDetail(@PathVariable("labourIdStr") String labourIdStr) {
		orderLabourDetailService.deleteSelectDispatchDetail(labourIdStr);
	}

	/**
	 *
	 * 完工验收页面
	 * 
	 * @author rongzoujie
	 * @date 2016年10月12日
	 * @param roId
	 * @return
	 */
	@RequestMapping(value = "/{roId}/findishWorkCheck", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findishWorkCheck(@PathVariable("roId") Long roId) {
		PageInfoDto pageInfoDto = orderLabourDetailService.queryFinishWorkCheck(roId);
		return pageInfoDto;
	}

	/**
	 *
	 * 维修工单修改页面查询维修项目明细
	 * 
	 * @author rongzoujie
	 * @date 2016年10月18日
	 * @param roId
	 * @return
	 */
	@RequestMapping(value = "/{roId}/queryLabourDetail", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryLabourDetail(@PathVariable("roId") Long roId) {
		List<Map> labourDetail = orderLabourDetailService.queryLabourDetail(roId);
		return labourDetail;
	}

	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void repairAssign(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList = orderLabourDetailService.queryRepairAssignForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("工单信息", resultList);

		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("RO_NO", "工单号"));
		exportColumnList.add(new ExcelExportColumn("RO_CREATE_DATE", "开单日期"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("ASSIGN_STATUS", "派工状态"));
		exportColumnList.add(new ExcelExportColumn("WAIT_INFO_TAG", "待信"));
		exportColumnList.add(new ExcelExportColumn("WAIT_PART_TAG", "待料"));
		exportColumnList.add(new ExcelExportColumn("COMPLETE_TAG", "竣工"));
		exportColumnList.add(new ExcelExportColumn("END_TIME_SUPPOSED", "预交车时间"));
		exportColumnList.add(new ExcelExportColumn("RO_TYPE", "工单类型", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE_NAME", "维修类型"));
		exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "终检人"));
		exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR_ASS", "服务顾问"));
		exportColumnList.add(new ExcelExportColumn("IN_MILEAGE", "进厂里程"));
		exportColumnList.add(new ExcelExportColumn("OUT_MILEAGE", "出厂里程"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
		exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
		excelService.generateExcel(excelData, exportColumnList, "工单信息.xls", request, response);

	}

}
