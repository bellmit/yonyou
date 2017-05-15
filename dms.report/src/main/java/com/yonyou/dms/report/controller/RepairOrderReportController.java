
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : RepairOrderReportController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.report.service.impl.RepairOrderReportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 维修
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */
@Controller
@TxnConn
@RequestMapping("/repair")
public class RepairOrderReportController extends BaseController {

	@Autowired
	private RepairOrderReportService repairorderreportservice;
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 维修业务查询
	 * 
	 * @author zhanshiwei
	 * @date 2016年9月27日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "/repairOrder", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRepairOrders(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = repairorderreportservice.queryRepairOrders(queryParam);
		return pageInfoDto;
	}

	/**
	 * 本月进厂
	 * 
	 * @author zhanshiwei
	 * @date 2016年9月27日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "enterFactory", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryEnterFactory(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = repairorderreportservice.queryEnterFactory(queryParam);
		return pageInfoDto;
	}

	/**
	 * 技师维修台次统计(天)
	 * 
	 * @author zhanshiwei
	 * @date 2016年10月9日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "employeeRepair/day", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryEemployeeemployeeRepair(@RequestParam Map<String, String> queryParam) {
		queryParam.put("statisticalPattern", "day");
		PageInfoDto pageInfoDto = repairorderreportservice.queryEemployeeemployeeRepair(queryParam);
		return pageInfoDto;
	}

	/**
	 * 技师维修台次统计(月)
	 * 
	 * @author zhanshiwei
	 * @date 2016年10月9日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "employeeRepair/month", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryEemployeeemployeeRepairByMonth(@RequestParam Map<String, String> queryParam) {
		queryParam.put("statisticalPattern", "month");
		PageInfoDto pageInfoDto = repairorderreportservice.queryEemployeeemployeeRepair(queryParam);
		return pageInfoDto;
	}

	/**
	 * 技师工时统计
	 * 
	 * @author zhanshiwei
	 * @date 2016年10月11日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value = "labourAmount", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querylabourAmount(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = repairorderreportservice.labourAmount(queryParam);
		return pageInfoDto;
	}


	/**车辆进厂月报列显示
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "quryRepairType", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryComplainCounts(@RequestParam Map<String, String> queryParam) {
		List<Map> listopetypeMap = repairorderreportservice.quRepairType(queryParam);
		return listopetypeMap;
	}

	/**车辆进厂月报导出
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/enterFactory/export", method = RequestMethod.GET)
	public void exportenterFactory(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList = repairorderreportservice.exportenterFactory(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车辆进厂月报", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("RO_CREATE_DATE", "开单日期"));
        exportColumnList.add(new ExcelExportColumn("CARS_NUM", "进场辆次"));
        exportColumnList.add(new ExcelExportColumn("CAR_NUM", "进场台次"));
        List<Map> listopetypeMap = repairorderreportservice.quRepairType(queryParam);
        for(Map map:listopetypeMap){
            exportColumnList.add(new ExcelExportColumn(map.get("REPAIR_TYPE_CODE").toString(), map.get("REPAIR_TYPE_NAME").toString()));

        }
		excelService.generateExcel(excelData, exportColumnList, "车辆进厂月报.xls", request, response);
	}
}
